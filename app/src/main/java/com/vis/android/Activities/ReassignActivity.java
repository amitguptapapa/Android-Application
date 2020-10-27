package com.vis.android.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.vis.android.Fragments.CaseDetail.case_id;

public class ReassignActivity extends BaseActivity {
    RelativeLayout back, rl_casedetail, dots;
    Spinner sp_surveyor, sp_reason;
    SurveyorAdapter adapter;
    LinearLayout proceed;
    String surveyor_namelist, cancel_array, selected_reason, selected_surveyor;
    Preferences pref;
    TextView tv_header, tv_caseheader;
    EditText et_remark;
    public static ArrayList<HashMap<String, String>> surveyor_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> cancel_array_list = new ArrayList<HashMap<String, String>>();
    ArrayList<String> Statelist;
    ReasonAdapter reason_adapter;
    CustomLoader loader;
    TextView tv_surveyor;
    TextView tv_reson,tv_caseid;
    Intent intent;

    Dialog emailDialog;
    private ArrayList<String> selectedMembersList = new ArrayList<>();
    ImageView ivAttachment;

    String encodedString1 = "",picturePath = "",filename = "", ext = "";

    Uri picUri, fileUri;

    private static final String IMAGE_DIRECTORY_NAME = "VIS";

    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;

    Bitmap bitmap;

    int captureType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reassign);
        pref = new Preferences(this);
        Statelist = new ArrayList<>();
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        back = (RelativeLayout) findViewById(R.id.rl_back);
        sp_surveyor = (Spinner) findViewById(R.id.spinner_surveyor);
        sp_reason = (Spinner) findViewById(R.id.spinner_reason);
        rl_casedetail = findViewById(R.id.rl_casedetail);
        tv_header = findViewById(R.id.tv_header);
        tv_caseheader = findViewById(R.id.tv_caseheader);
        tv_caseid = findViewById(R.id.tv_caseid);
        et_remark = findViewById(R.id.et_remark);
        proceed = findViewById(R.id.ll_proceed);
        dots = findViewById(R.id.rl_dots);

        tv_caseheader.setText("Reassignment");
        tv_header.setVisibility(View.GONE);
        rl_casedetail.setVisibility(View.VISIBLE);
        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

        surveyor_namelist = pref.get(Constants.surveyor_data);
        cancel_array = pref.get(Constants.array_reassign);


        try {
            surveyor_array_list.clear();
            cancel_array_list.clear();
            JSONArray surveyor_list = new JSONArray(surveyor_namelist);
            JSONArray cancel_list = new JSONArray(cancel_array);

            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("name", "Select Surveyor");
            surveyor_array_list.add(hash);


            for (int j = 0; j < surveyor_list.length(); j++) {
                JSONObject data_object = surveyor_list.getJSONObject(j);
                String id = data_object.getString("id");
                String name = data_object.getString("name");
                String employee_id = data_object.getString("employee_id");
                HashMap<String, String> hmap = new HashMap<>();
                if (pref.get(Constants.surveyor_name).equals(name)) {

                } else {
                    hmap.put("id", id);
                    hmap.put("name", name);
                    hmap.put("employee_id", employee_id);
                    surveyor_array_list.add(hmap);
                    adapter = new SurveyorAdapter(this, surveyor_array_list);
                    sp_surveyor.setAdapter(adapter);
                }

            }
            HashMap<String, String> hash1 = new HashMap<String, String>();
            hash1.put("reassign_reason", "Select Reason");
            cancel_array_list.add(hash1);

            for (int j = 0; j < cancel_list.length(); j++) {
                JSONObject data_object = cancel_list.getJSONObject(j);
                String Cancel_reason = data_object.getString("reassign_reason");
                String id = data_object.getString("id");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("reassign_reason", Cancel_reason);
                hmap.put("id", id);
                cancel_array_list.add(hmap);

            }

            reason_adapter = new ReasonAdapter(this, cancel_array_list);
            sp_reason.setAdapter(reason_adapter);

        } catch (Exception e) {

        }

        sp_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             //   selected_reason = cancel_array_list.get(position).get("Cancel_reason");
                selected_reason = cancel_array_list.get(position).get("id");
                // Toast.makeText(ReassignActivity.this, cancel_array_list.get(position).get("Cancel_reason"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_surveyor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_surveyor = surveyor_array_list.get(position).get("id");
                // Toast.makeText(ReassignActivity.this, cancel_array_list.get(position).get("Cancel_reason"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (surveyor_array_list.get(sp_surveyor.getSelectedItemPosition()).get("name").equals("Select Surveyor")) {
                    Toast.makeText(ReassignActivity.this, "Please select Surveyor", Toast.LENGTH_SHORT).show();
                } else*/ if (cancel_array_list.get(sp_reason.getSelectedItemPosition()).get("reassign_reason").equals("Select Reason")) {
                    Toast.makeText(ReassignActivity.this, "Please select cancel reason", Toast.LENGTH_SHORT).show();

                } else {
                   // Toast.makeText(ReassignActivity.this, "hit api", Toast.LENGTH_SHORT).show();
                    if (Utils.isNetworkConnectedMainThred(ReassignActivity.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        Hit_Reassign_Api();

                    } else {
                        Toast.makeText(ReassignActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        if (pref.get(Constants.survey_not_required_status).equalsIgnoreCase("1")){
            popup.getMenu().getItem(4).setVisible(false);
        }else {
            //  popup.getMenu().getItem(4).setVisible(false);
        }
        popup.getMenu().getItem(5).setVisible(false);
        popup.getMenu().getItem(6).setVisible(false);
        popup.getMenu().getItem(7).setVisible(false);
        popup.getMenu().getItem(8).setVisible(false);
        popup.getMenu().getItem(9).setVisible(false);
        popup.getMenu().getItem(10).setVisible(false);
        popup.getMenu().getItem(11).setVisible(false);
        popup.getMenu().getItem(12).setVisible(false);
        popup.getMenu().getItem(13).setVisible(false);
        popup.getMenu().getItem(14).setVisible(false);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homee:
                        intent = new Intent(ReassignActivity.this, Dashboard.class);
                        startActivity(intent);
                        return true;

                    case R.id.survey_not_required:
                        intent = new Intent(ReassignActivity.this, SurveyNotRequiredActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.reassign:
                        intent = new Intent(ReassignActivity.this, ReassignActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.reschedule:
                        intent = new Intent(ReassignActivity.this, ScheduleActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.calling:
                        CallDialog();
                        return true;

                    /*case R.id.doc:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://valuerservice.com/uploads/0692351837.jpg"));
                        startActivity(intent);
                        return true;*/

                    default:
                        return false;
                }
            }
        });
    }

    public class SurveyorAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String, String>> alist;
        LayoutInflater inflter;


        public SurveyorAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflter.inflate(R.layout.surveyor_adapter, null);
            tv_surveyor = (TextView) convertView.findViewById(R.id.tv_name);
            tv_surveyor.setText(alist.get(position).get("name"));
          if(position==0){
              tv_surveyor.setText(alist.get(position).get("name"));
          }
          else{
              tv_surveyor.setText(alist.get(position).get("name")+" - "+alist.get(position).get("employee_id"));

          }



            return convertView;
        }
    }


    public class ReasonAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String, String>> alist;
        LayoutInflater inflter;


        public ReasonAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflter.inflate(R.layout.surveyor_adapter, null);
            tv_reson = (TextView) convertView.findViewById(R.id.tv_name);
            tv_reson.setText(alist.get(position).get("reassign_reason"));
            return convertView;
        }
    }


    //********************************** Reassign api *********************************//

    private void Hit_Reassign_Api() {

        String url = Utils.getCompleteApiUrl(this, R.string.reassignCase);
        Log.v("Hit_Reassign_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();


        try {

            jsonObject.put("sur_id", pref.get(Constants.surveyor_id));
            jsonObject.put("case_id", case_id);
            jsonObject.put("re_assign_reason", selected_reason);
            jsonObject.put("remark", et_remark.getText().toString());
            json_data.put("VIS", jsonObject);

            Log.v("request_reassign", json_data.toString());

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
                            Toast.makeText(ReassignActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJson(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                Intent intent = new Intent(ReassignActivity.this, Dashboard.class);
                startActivity(intent);
                Toast.makeText(this, res_msg, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(ReassignActivity.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ReassignActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    //Call popup
    public void CallDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.call_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        LinearLayout manager = dialog.findViewById(R.id.ll_manager);
        LinearLayout owner = dialog.findViewById(R.id.ll_owner);
        ImageView close = dialog.findViewById(R.id.iv_close);
        final TextView tvName1,tvName2,tvName3,tvName4,tvMobile1,tvMobile2,tvMobile3,tvMobile4;

        ImageView ivEmail1,ivEmail2,ivEmail3,ivEmail4,iv_call1,iv_call2,iv_call3,iv_call4;
        ivEmail1 = dialog.findViewById(R.id.ivEmail1);
        ivEmail2 = dialog.findViewById(R.id.ivEmail2);
        ivEmail3 = dialog.findViewById(R.id.ivEmail3);
        ivEmail4 = dialog.findViewById(R.id.ivEmail4);
        iv_call1 = dialog.findViewById(R.id.iv_call1);
        iv_call2 = dialog.findViewById(R.id.iv_call2);
        iv_call3 = dialog.findViewById(R.id.iv_call3);
        iv_call4 = dialog.findViewById(R.id.iv_call4);
        tvName1 = dialog.findViewById(R.id.tvName1);
        tvName2 = dialog.findViewById(R.id.tvName2);
        tvName3 = dialog.findViewById(R.id.tvName3);
        tvName4 = dialog.findViewById(R.id.tvName4);
        tvMobile1 = dialog.findViewById(R.id.tvMobile1);
        tvMobile2 = dialog.findViewById(R.id.tvMobile2);
        tvMobile3 = dialog.findViewById(R.id.tvMobile3);
        tvMobile4 = dialog.findViewById(R.id.tvMobile4);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Intent intent = getIntent();

        try {
            if (pref.get(Constants.support_manager_name).equals("")
                    || pref.get(Constants.support_manager_name).equals("null")){
                tvName1.setText("NA");
            }else {
                tvName1.setText(pref.get(Constants.support_manager_name));
            }

            if (pref.get(Constants.support_owner_name).equals("")
                    || pref.get(Constants.support_owner_name).equals("null")){
                tvName2.setText("NA");
            }else {
                tvName2.setText(pref.get(Constants.support_owner_name));
            }

            if (pref.get(Constants.support_co_person_name).equals("")
                    || pref.get(Constants.support_co_person_name).equals("null")){
                tvName4.setText("NA");
            }else {
                tvName4.setText(pref.get(Constants.support_co_person_name));
            }

            if (pref.get(Constants.support_bus_asso_name).equals("")
                    || pref.get(Constants.support_bus_asso_name).equals("null")){
                tvName3.setText("NA");
            }else {
                tvName3.setText(pref.get(Constants.support_bus_asso_name));
            }

            if (pref.get(Constants.support_manager_phone).equals("")
                    || pref.get(Constants.support_manager_phone).equals("null")){
                tvMobile1.setText("NA");
            }else {
                tvMobile1.setText(pref.get(Constants.support_manager_phone));
            }

            if (pref.get(Constants.support_owner_phone).equals("")
                    || pref.get(Constants.support_owner_phone).equals("null")){
                tvMobile2.setText("NA");
            }else {
                tvMobile2.setText(pref.get(Constants.support_owner_phone));
            }

            if (pref.get(Constants.support_co_person_phone).equals("")
                    || pref.get(Constants.support_co_person_phone).equals("null")){
                tvMobile4.setText("NA");
            }else {
                tvMobile4.setText(pref.get(Constants.support_co_person_phone));
            }

            if (pref.get(Constants.support_bus_asso_phone).equals("")
                    || pref.get(Constants.support_bus_asso_phone).equals("null")){
                tvMobile3.setText("NA");
            }else {
                tvMobile3.setText(pref.get(Constants.support_bus_asso_phone));
            }

            if (!tvMobile1.getText().toString().equalsIgnoreCase("NA")){
                call(iv_call1, tvMobile1.getText().toString());
            }
            if (!tvMobile2.getText().toString().equalsIgnoreCase("NA")){
                call(iv_call2, tvMobile2.getText().toString());
            }
            if (!tvMobile3.getText().toString().equalsIgnoreCase("NA")){
                call(iv_call3, tvMobile3.getText().toString());
            }
            if (!tvMobile4.getText().toString().equalsIgnoreCase("NA")){
                call(iv_call4, tvMobile4.getText().toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ivEmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName1.getText().toString().equalsIgnoreCase("NA")){
                    dialog.dismiss();
                    AlertEmailPopup("1",tvName1.getText().toString());
                }
            }
        });
        ivEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName2.getText().toString().equalsIgnoreCase("NA")){
                    dialog.dismiss();
                    AlertEmailPopup("2",tvName2.getText().toString());
                }
            }
        });
        ivEmail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName3.getText().toString().equalsIgnoreCase("NA")){
                    dialog.dismiss();
                    AlertEmailPopup("3",tvName3.getText().toString());
                }
            }
        });
        ivEmail4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName4.getText().toString().equalsIgnoreCase("NA")){
                    dialog.dismiss();
                    AlertEmailPopup("4",tvName4.getText().toString());
                }
            }
        });

    }

    //Email popup
    public void AlertEmailPopup(final String type, String name) {
        emailDialog = new Dialog(this);
        emailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailDialog.setCancelable(true);
        emailDialog.setContentView(R.layout.alert_email_popup);
        Window window = emailDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        emailDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        emailDialog.show();

        ImageView close;
        final ChipsInput chipInputTo;
        final EditText etSubject,etMessage;
        Button btnBrowse,btnSubmit;

        close = emailDialog.findViewById(R.id.iv_close);
        chipInputTo = emailDialog.findViewById(R.id.chipInputTo);
        etSubject = emailDialog.findViewById(R.id.etSubject);
        etMessage = emailDialog.findViewById(R.id.etMessage);
        btnBrowse = emailDialog.findViewById(R.id.btnBrowse);
        btnSubmit = emailDialog.findViewById(R.id.btnSubmit);
        ivAttachment = emailDialog.findViewById(R.id.ivAttachment);

        selectedMembersList.clear();

        chipInputTo.setChipDeletable(true);
        chipInputTo.setChipHasAvatarIcon(false);
        chipInputTo.setShowChipDetailed(false);
        chipInputTo.addChip(name,"");

        chipInputTo.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chipInterface, int i) {
                selectedMembersList.add(String.valueOf(chipInterface.getLabel()));

            }

            @Override
            public void onChipRemoved(ChipInterface chipInterface, int i) {
                selectedMembersList.remove(chipInterface.getLabel());
            }

            @Override
            public void onTextChanged(CharSequence charSequence) {

                if (charSequence.toString().contains(" ")){
                    if (Utils.isValidEmail(charSequence.toString().trim())){
                        chipInputTo.addChip(charSequence.toString().trim(),"");
                    } else {
                        String[] seqSplit = charSequence.toString().split(" ");

                        //  charSequence.toString().replace(" ","");

                        //    chipInputTo.getEditText().setText(charSequence);

                        if (seqSplit.length == 1){
                            Toast.makeText(ReassignActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!Utils.isValidEmail(chipInputTo.getEditText().getText().toString())){
                    Toast.makeText(CaseDetail.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else*/ if (etSubject.getText().toString().isEmpty()){
                    Toast.makeText(ReassignActivity.this, "Please enter subject", Toast.LENGTH_SHORT).show();
                }else if (etMessage.getText().toString().isEmpty()){
                    Toast.makeText(ReassignActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                }else {
                    if (Utils.isNetworkConnectedMainThred(getApplicationContext())){
                        hitEmailApi(type,etSubject.getText().toString().trim(),etMessage.getText().toString().trim());
                    }else {
                        Toast.makeText(ReassignActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraGalleryDialogAttachment();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialog.dismiss();
                CallDialog();
            }
        });

    }

    private void call(View view, final String number){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));//"tel:"+number.getText().toString();
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
    }

    private void hitEmailApi(String type,String subject, String content) {

        loader.show();

        String url = Utils.getCompleteApiUrl(this, R.string.SendSupportEmail);

        Log.v("hitEmailApi", url);

        // selectedMembersList.remove(0);

        JSONArray emailArray = new JSONArray();

        //emailArray.put(selectedMembersList);

        for (int i = 0;i<selectedMembersList.size();i++){
            try {
                emailArray.put(i,selectedMembersList.get(i).trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
           /* int chunkCount = encodedString1.length() / 4000;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= encodedString1.length()) {
                    Log.v("###encodedString-1", encodedString1.substring(4000 * i));
                } else {

                    Log.v("###encodedString-2", encodedString1.substring(4000 * i, max));
                }
            }*/

            jsonObject.put("type", type);
            jsonObject.put("email_array", emailArray);
            jsonObject.put("subject", subject);
            jsonObject.put("content", content);
            jsonObject.put("attachment", encodedString1);
            jsonObject.put("case_id", case_id);
            jsonObject.put("surveyor_id", pref.get(Constants.surveyor_id));

            json_data.put("VIS", jsonObject);

            Log.v("hitEmailApi", json_data.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonEmail(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        loader.cancel();
                        // handle error
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonEmail(JSONObject response) {

        Log.v("res:hitEmailApi", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String res_code = jsonObject.getString("response_code");

            if (res_code.equals("1")) {
                Toast.makeText(ReassignActivity.this, res_msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ReassignActivity.this, res_msg, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ReassignActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        loader.cancel();
        emailDialog.dismiss();
    }

    public void showCameraGalleryDialogAttachment() {

        captureType = 2;

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.camera_gallery_popup);

        dialog.show();

        RelativeLayout rrCancel = (RelativeLayout) dialog.findViewById(R.id.rr_cancel);
        LinearLayout llCamera = (LinearLayout) dialog.findViewById(R.id.ll_camera);
        LinearLayout llGallery = (LinearLayout) dialog.findViewById(R.id.ll_gallery);

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                dialog.dismiss();
            }
        });


        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                dialog.dismiss();
            }
        });

        rrCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // AppUtils.showToastSort(mActivity,getString(R.string.permission_camera));

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        }

        else {

            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
        // start the image capture Intent
    }

    public Uri getOutputMediaFileUri(int type) {
        //return Uri.fromFile(getOutputMediaFile(type));

        return FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".provider", getOutputMediaFile(type));

    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    //method to convert string into base64
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        //String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);

        return imgString;
    }

    public static String getFileType(String path) {
        String fileType = null;
        fileType = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase();
        return fileType;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                picturePath = fileUri.getPath().toString();
                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                Log.d("filename_camera", filename);
                String selectedImagePath = picturePath;
                Uri uri = Uri.parse(picturePath);
                ext = "jpg";
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 500;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(picturePath));
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();



                encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                if (captureType == 2){
                    ivAttachment.setVisibility(View.VISIBLE);
                    ivAttachment.setImageBitmap(rotatedBitmap);
                }

            }
        }else if (requestCode == SELECT_PICTURE) {
            if (data != null) {

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    encodedString1  = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);

                    //  encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mArrayUri.get(0));

                        if (captureType == 2){
                            ivAttachment.setVisibility(View.VISIBLE);
                            ivAttachment.setImageBitmap(bitmap);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //   encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                }


            } else {
                Toast.makeText(ReassignActivity.this, "Unable to select image", Toast.LENGTH_LONG).show();
            }

        }
    }

}
