package com.vis.android.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ankita-pc on 25/4/18.
 */

public class ComSurveyFragment extends BaseFragment implements View.OnClickListener {
    View v;
    ListView list;
    CustomLoader loader;
    Preferences pref;
    Fragment fragment;
    RelativeLayout rl_casedetail;
    Intent intentComSurv;
    TextView tv_header_text;
    ComSurveyAdapter adapter;
    public static ArrayList<HashMap<String, String>> lead_array_list = new ArrayList<HashMap<String, String>>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.completed_survey, container, false);
        getid();
        setListener();
        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            Hit_GetLeads_Api();

        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

        }
        return v;
    }

    @Override
    public void onClick(View view) {

    }

    public void getid() {
        list = v.findViewById(R.id.lv_comsurvey);
        pref = new Preferences(getActivity());
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//        tv_header_text = (TextView) findViewById(R.id.tv_header_text);
//        tv_caseheader = findViewById(R.id.tv_caseheader);

    }

    public void setListener() {

    }


    //********************************** GetLeads api *********************************//
    private void Hit_GetLeads_Api() {
        String url = Utils.getCompleteApiUrl(getActivity(), R.string.GetCompletedCases);
//        String url = Utils.getCompleteApiUrl(getActivity(), R.string.getLeads);
        Log.v("Hit_GetLeads_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("surveyor_id", pref.get(Constants.surveyor_id));
            json_data.put("VIS", jsonObject);

            Log.v("request", json_data.toString());

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
                            Toast.makeText(getActivity(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJson(JSONObject response) {

        Log.v("response", response.toString());
        lead_array_list.clear();
        try {

            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String res_msg = jsonObject.getString("response_message");


            if (res_code.equals("1")) {
                JSONArray array_case_datails = jsonObject.getJSONArray("case_datails");

                for (int i = 0; i < array_case_datails.length(); i++) {

                    JSONObject data_object = array_case_datails.getJSONObject(i);
                    JSONObject object_Pdetail = data_object.getJSONObject("Personal_Detail");
                    JSONObject object_Cdetail = data_object.getJSONObject("case_datail");
                    JSONObject object_Comdetail = data_object.getJSONObject("Commercial_detail");

                    HashMap<String, String> hash = new HashMap<String, String>();

                    if (object_Pdetail.get("status").toString().equals("4")) {
                        hash.put("case_id", object_Pdetail.get("case_id").toString());
                        hash.put("case_u_id", object_Pdetail.get("case_u_id").toString());
                        hash.put("customer_name", object_Pdetail.get("customer_name").toString());
                        hash.put("person_name", object_Pdetail.get("person_name").toString());
                        hash.put("user_email", object_Pdetail.get("user_email").toString());
                        hash.put("person_number", object_Pdetail.get("person_number").toString());
                        hash.put("alternate_mobile", object_Pdetail.get("alternate_mobile").toString());
                        hash.put("comment", object_Pdetail.get("comment").toString());
                        hash.put("asset_ownername", object_Pdetail.get("asset_ownername").toString());
                        hash.put("asset_address", object_Pdetail.get("asset_address").toString());
                        hash.put("status", object_Pdetail.get("status").toString());
                        hash.put("schedule_date", object_Pdetail.get("schedule_date").toString());
                        hash.put("nature_assets", object_Cdetail.get("nature_assets").toString());
                        hash.put("type_of_assets", object_Pdetail.get("type_of_valuation").toString());
                        //hash.put("pincode", object_Pdetail.get("pincode").toString());
                        hash.put("assigned_date", object_Pdetail.get("assigned_date").toString());
                        hash.put("lead_schedule_status", object_Pdetail.get("lead_schedule_status").toString());

                        hash.put("land_area", object_Cdetail.get("land_area").toString());
                        hash.put("covered_area", object_Cdetail.get("covered_area").toString());
                        //hash.pu t("assignment", object_Cdetail.get("purpose_of_assignment").toString());

                        //hash.put("machinery_gross", object_Comdetail.get("machinery_gross").toString());
                        //hash.put("plant", object_Comdetail.get("plant_other").toString());
                        //hash.put("net_block", object_Comdetail.get("net_block").toString());

                        lead_array_list.add(hash);

                        adapter = new ComSurveyAdapter(getActivity(), lead_array_list);

                        list.setAdapter(adapter);
                    } else {

                    }
                }

            } else {
                Toast.makeText(getActivity(), res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }


    public class ComSurveyAdapter extends BaseAdapter {
        LayoutInflater inflter;
        Context context;
        ArrayList<HashMap<String, String>> alist;
        ImageView image;
        RelativeLayout rl_main;
        Preferences pref;
        View status_view;
        TextView Oner_name, asset_type, asset_add, assignd_date, status, schedule_date, state, assigned_date, case_Id;

        public ComSurveyAdapter(Context context, ArrayList<HashMap<String, String>> alist) {

            inflter = (LayoutInflater.from(context));
            this.context = context;
            this.alist = alist;


        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int i) {
            return alist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.com_survey_adapter, null);

            getid(view);
            setValues(i);

            rl_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + alist.get(i).get("case_u_id"), Toast.LENGTH_SHORT).show();
                    pref.set(Constants.case_id, alist.get(i).get("case_id"));
                    pref.set(Constants.page_editable, "false");
                    pref.commit();
                    ((Dashboard) getActivity()).displayView(1);

                }
            });


            return view;
        }

        public void getid(View v) {
            pref = new Preferences(context);
            Oner_name = v.findViewById(R.id.tv_onrname);
            case_Id = v.findViewById(R.id.tv_case_Id);
            asset_type = v.findViewById(R.id.tv_type);
            asset_add = v.findViewById(R.id.tv_aadd);
            assignd_date = v.findViewById(R.id.tv_date);
            status = v.findViewById(R.id.tv_status);
            schedule_date = v.findViewById(R.id.sdate);
            state = v.findViewById(R.id.tv_state);
            status_view = v.findViewById(R.id.view);
            assigned_date = v.findViewById(R.id.asign_date);
            rl_main = v.findViewById(R.id.rl_main);
        }

        public void setValues(final int position) {
            case_Id.setText(alist.get(position).get("case_u_id"));
            Oner_name.setText(alist.get(position).get("asset_ownername"));
            asset_type.setText(alist.get(position).get("nature_assets"));
            asset_add.setText(alist.get(position).get("asset_address"));
            status.setText(alist.get(position).get("status"));
            assigned_date.setText(alist.get(position).get("assigned_date"));


//            if(alist.get(position).get("status").equals("0")){
//                Log.v("status1",alist.get(position).get("status"));
//                status_view.setBackgroundColor(Color.YELLOW);
//            }
//            else if(alist.get(position).get("status").equals("1")){
//                Log.v("status2",alist.get(position).get("status"));
//                status_view.setBackgroundColor(Color.GREEN);
//            }
//            else{
//                Log.v("status3",alist.get(position).get("status"));
//                status_view.setBackgroundColor(Color.RED);
//            }


//            if(alist.get(position).get("schedule_date").equals("null")){
//                schedule_date.setText("Not scheduled yet");
//            }
//            else{
//                schedule_date.setText(alist.get(position).get("schedule_date"));
//            }


        }

    }

}
