package com.vis.android.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DocumentsActivity extends BaseActivity {

    TextView tv_header, tv_caseheader, tv_caseid;

    RelativeLayout back, rl_casedetail;

    Preferences pref;

    CustomLoader loader;

    ProgressBar Progress;

    RecyclerView recyclerView;

    ArrayList<HashMap<String, String>> doc_list_group = new ArrayList();
    ArrayList<ArrayList<HashMap<String, String>>> doc_list_docs = new ArrayList();
    ArrayList<HashMap<String ,String>> doc_temp_list = new ArrayList<>();

    ArrayList<HashMap<String, String>> doc_list_group_referred = new ArrayList();
    ArrayList<ArrayList<HashMap<String, String>>> doc_list_docs_referred = new ArrayList();
    ArrayList<HashMap<String ,String>> doc_temp_list_referred = new ArrayList<>();

    public static ArrayList<String> documentImagesList = new ArrayList<String>();
    public static ArrayList<String> captionList = new ArrayList<String>();

    public static int from = 0;
    public static int act = 0;

    private AdapterDocuments adapterDocuments;

    ExpandableListView simpleExpandableListDocuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        init();

    }

    private void init(){

        pref = new Preferences(this);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        simpleExpandableListDocuments = findViewById(R.id.simpleExpandableListDocuments);

        tv_header = findViewById(R.id.tv_header);
        rl_casedetail = findViewById(R.id.rl_casedetail);
        tv_caseheader = findViewById(R.id.tv_caseheader);
        tv_caseid = findViewById(R.id.tv_caseid);
        back = (RelativeLayout) findViewById(R.id.rl_back);

        Progress = findViewById(R.id.Progress);
        Progress.setVisibility(View.GONE);

        tv_header.setVisibility(View.GONE);
        rl_casedetail.setVisibility(View.VISIBLE);
        tv_caseheader.setText("Documents");
        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Utils.isNetworkConnectedMainThred(mActivity)){
            hitGetUploadedDocs();
        }else {
            Toast.makeText(mActivity, getString(R.string.noInternetConnection), Toast.LENGTH_SHORT).show();
        }

    }

    private void hitGetUploadedDocs() {

        String url = Utils.getCompleteApiUrl(this, R.string.GetUploadedDocuments);
        Log.v("hitGetUploadedDocs", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();


        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));
            json_data.put("VIS", jsonObject);

            Log.v("hitGetUploadedDocs", json_data.toString());

        } catch (JSONException e) {

            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJson(response);
                    }

                    @Override
                    public void onError(ANError error) {

                        // handle error
                        if (error.getErrorCode() != 0) {
                            loader.cancel();
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            loader.cancel();
                            Toast.makeText(mActivity, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJson(JSONObject response) {

        Log.v("res:hitGetUploadedDocs", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {

                if (jsonObject.has("documents")){

                    JSONArray arr = new JSONArray();

                    if (!jsonObject.getString("documents").equalsIgnoreCase("null")){

                        JSONArray array = jsonObject.getJSONArray("documents");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            HashMap<String, String> hashGroup = new HashMap<String, String>();
                            // HashMap<String, String> hashDoc = new HashMap<String, String>();

                            hashGroup.put("mandatory", obj.getString("mandatory"));
                            hashGroup.put("prior_to_survey", obj.getString("prior_to_survey"));
                            hashGroup.put("select_id", obj.getString("select_id"));
                            hashGroup.put("group_name", obj.getString("group_name"));
                            hashGroup.put("group_id", obj.getString("group_id"));

                            if (obj.getString("prior_to_survey").equalsIgnoreCase("1")){

                                doc_list_group.add(hashGroup);

                                arr = obj.getJSONArray("arr");

                                //  hashDoc.put("arr", arr.toString());

                                //doc_list_docs.add(hashDoc);

                                /*if (obj.has("document_url")){
                                    if (obj.getString("prior_to_survey").equalsIgnoreCase("1")){
                                        hash.put("document_url", String.valueOf(obj.getJSONArray("document_url")));
                                        doc_list.add(hash);
                                    }else {
                                        hash.put("count","0");
                                        hash.put("document_url", obj.getString("document_url"));
                                        //   hash.put("documentImages","");
                                        doc_list_referred.add(hash);
                                    }
                                }*/
                                doc_temp_list = new ArrayList();

                                for (int k =0;k<arr.length();k++){

                                    HashMap hashDoc = new HashMap<String, String>();

                                    JSONObject jsonObject1 = arr.getJSONObject(k);

                                    hashDoc.put("doc_id", jsonObject1.getString("doc_id"));
                                    hashDoc.put("doc_name", jsonObject1.getString("doc_name"));
                                    if (jsonObject1.has("document_url")){
                                        hashDoc.put("document_url", String.valueOf(jsonObject1.getJSONArray("document_url")));
                                    }

                                    doc_temp_list.add(hashDoc);

                                    if (obj.getString("prior_to_survey").equalsIgnoreCase("1")) {
                                    }else {
                                    }

                                }
                                doc_list_docs.add(doc_temp_list);

                            }else {

                                doc_list_group_referred.add(hashGroup);

                                arr = obj.getJSONArray("arr");

                                doc_temp_list_referred = new ArrayList();

                                for (int k =0;k<arr.length();k++){

                                    HashMap hashDoc = new HashMap<String, String>();

                                    JSONObject jsonObject1 = arr.getJSONObject(k);

                                    hashDoc.put("doc_id", jsonObject1.getString("doc_id"));
                                    hashDoc.put("doc_name", jsonObject1.getString("doc_name"));
                                    if (jsonObject1.has("document_url")){
                                        hashDoc.put("document_url", String.valueOf(jsonObject1.getJSONArray("document_url")));
                                    }
                                    hashDoc.put("count","0");
                                    hashDoc.put("documentImages","");

                                    doc_temp_list_referred.add(hashDoc);

                                    if (obj.getString("prior_to_survey").equalsIgnoreCase("1")) {
                                    }else {
                                    }

                                }
                                doc_list_docs_referred.add(doc_temp_list_referred);
                            }

                        }

                    }
                }

                adapterDocuments = new AdapterDocuments(mActivity, doc_list_group, doc_list_docs);
                simpleExpandableListDocuments.setAdapter(adapterDocuments);


                int item_size = (int) getResources().getDimension(R.dimen._30sdp);  // 50px
                final int sub_item_size = (int) getResources().getDimension(R.dimen._30sdp); ;  // 40px

                simpleExpandableListDocuments.getLayoutParams().height = item_size*adapterDocuments.getGroupCount();
                simpleExpandableListDocuments.setAdapter(adapterDocuments);
// ListView Group Expand Listener
                simpleExpandableListDocuments.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {
                        int nb_children = adapterDocuments.getChildrenCount(groupPosition);
                        simpleExpandableListDocuments.getLayoutParams().height += sub_item_size*nb_children;
                    }
                });

            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    public class AdapterDocuments extends BaseExpandableListAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> expandableListTitle;
        private ArrayList<ArrayList<HashMap<String, String>>> expandableListDetail;


        public AdapterDocuments(Context context, ArrayList<HashMap<String, String>> expandableListTitle, ArrayList<ArrayList<HashMap<String, String>>> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;
        }


        @Override
        public Object getChild(int listPosition, int expandedListPosition) {

            //return this.expandableListDetail.get(expandableListTitle.get(listPosition).get("group_name")).get(expandedListPosition);

            return this.expandableListDetail.get(listPosition).get(expandedListPosition).get("doc_name");

        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {

            return expandedListPosition;

        }

        @Override
        public View getChildView(final int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final String expandedListText = expandableListDetail.get(listPosition).get(expandedListPosition).get("doc_name");

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.inflate_documents_doc, null);
            }

            TextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);

            if (expandableListDetail.get(listPosition).get(expandedListPosition).containsKey("document_url")) {

                try {
                    JSONArray document_url_array = new JSONArray(expandableListDetail.get(listPosition).get(expandedListPosition).get("document_url"));

                    expandedListTextView.setText(expandedListText + " (" + document_url_array.length() + ")");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                expandedListTextView.setText(expandedListText + " (0)");
            }


            RelativeLayout rlMain = convertView.findViewById(R.id.rlMain);

            if (expandableListDetail.get(listPosition).get(expandedListPosition).containsKey("document_url")) {

                rlMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //  caption = alist.get(position).get("document_caption");

                        documentImagesList.clear();
                        captionList.clear();

                        try {

                            JSONArray document_url_array = new JSONArray(expandableListDetail.get(listPosition).get(expandedListPosition).get("document_url"));

                            for (int i = 0; i < document_url_array.length(); i++) {
                                JSONObject jsonObject = document_url_array.getJSONObject(i);

                                documentImagesList.add("http://trendytoday.in/vis/" + jsonObject.getString("url"));
                                captionList.add(jsonObject.getString("caption"));

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        from = 1;

                        if (expandableListDetail.get(listPosition).get(expandedListPosition).containsKey("document_url")) {
                            act = 1;
                            startActivity(new Intent(mActivity, GalleryActivity.class));

                        }
                    }
                });
            }

            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {

            return this.expandableListDetail.get(listPosition).size();

        }

        @Override
        public Object getGroup(int listPosition) {
            return this.expandableListTitle.get(listPosition);
        }

        @Override
        public int getGroupCount() {

            return this.expandableListTitle.size();
        }

        @Override
        public long getGroupId(int listPosition) {
            return listPosition;
        }

        @Override
        public View getGroupView(int listPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            String listTitle = expandableListTitle.get(listPosition).get("group_name");

            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.inflate_document_group_name, null);
            }

            TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
            listTitleTextView.setText(listTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {

            return false;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return true;
        }
    }

}
