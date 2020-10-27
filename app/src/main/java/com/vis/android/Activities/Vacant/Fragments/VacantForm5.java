package com.vis.android.Activities.Vacant.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.Fragments.InitiateSurveyForm;
import com.vis.android.Activities.General.SpinnerAdapter;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableGeneralForm;
import com.vis.android.Extras.BaseFragment;
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

import static android.app.Activity.RESULT_OK;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;

public class VacantForm5 extends BaseFragment implements View.OnClickListener {

    RelativeLayout rl_detail;
    LinearLayout dropdown;
    TextView next, tvPrevious;
    Intent intent;
    String selected_landmerged;
    Preferences pref;
    Boolean edit_page = true;
    String pageVisited="";
    /// ProgressBar progressbar;

    //RelativeLayout
    RelativeLayout rlPartlyDemarcated, rlRemark1, rlRemark2;

    //RadioGroup
    RadioGroup rgIndependentAccess, rgIsPropertyClearly, rgSub;

    //EditText
    EditText et_details, etRemark1, etRemark2, etPartlyDemarcated;

    //Spinner
    Spinner spinnerIsLandMerged, spinnerInCaseProperty1, spinnerInCaseProperty2;

    //String
    String isLandMergedArray[] = {"Choose an item", "Yes", " No. It is an independent single bounded property"," Any other", "No information available since full survey of the property couldn't be carried out"};
    //    String inCaseProperty1Array[] = {"Choose an item", "Yes", "No", "No information available since full survey of the property couldn't be carried out"};
//    String inCaseProperty2Array[] = {"Choose an item", "Yes", "No", "No information available since full survey of the property couldn't be carried out"};
    String inCaseProperty1Array[] = {"Choose an item", "Yes", "No", "Can't comment since full survey of the property couldn't be carried out"};
    String inCaseProperty2Array[] = {"Choose an item", "Yes", "No", "Can't comment since full survey of the property couldn't be carried out"};

    //RadioButton
    RadioButton rbPartlyDemarc, radioButton;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    SpinnerAdapter spinnerAdapter;

    //   TextView tv_caseheader,tv_caseid,tv_header;

    //   RelativeLayout rl_casedetail;

    View v;

    Button btnUploadAgreement;

    ImageView ivUploadAgreement;

    String encodedString1 = "",picturePath = "",filename = "", ext = "";

    Uri picUri, fileUri;

    Bitmap bitmap;

    private static final String IMAGE_DIRECTORY_NAME = "VIS";

    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_vacantform5, container, false);

        getid(v);
        setListener();
        populateSinner();
        condition();

//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

//        progressbar.setMax(10);
//        progressbar.setProgress(5);
        rgIndependentAccess.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) v.findViewById(selectedId);
                if (radioButton.getText().toString().equalsIgnoreCase("Access is available from another property with no formal easement rights available")) {
                    rgSub.setVisibility(View.VISIBLE);
                } else {
                    rgSub.setVisibility(View.GONE);
                }
            }
        });

        rgSub.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (a == 0) {
                        btnUploadAgreement.setVisibility(View.VISIBLE);
                    }else{
                        btnUploadAgreement.setVisibility(View.GONE);
                    }
                }


            }
        });

        spinnerIsLandMerged.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_landmerged = isLandMergedArray[i];
                if (selected_landmerged.equals("Yes")) {
                    rl_detail.setVisibility(View.VISIBLE);
                } else {
                    rl_detail.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (InitiateSurveyForm.surveyTypeCheck == 1){
            spinnerIsLandMerged.setSelection(3);
            spinnerInCaseProperty1.setSelection(3);
            spinnerInCaseProperty2.setSelection(3);

        }else {
//            spinnerIsLandMerged.setSelection(0);
//            spinnerInCaseProperty1.setSelection(0);
//            spinnerInCaseProperty2.setSelection(0);
        }
        setDefaults();
        if (!edit_page) {
            setEditiblity();
        }
        return v;
    }
    public void setDefaults(){
     /*   if(!pageVisited.equalsIgnoreCase("vacantForm5")){
            ((RadioButton) rgIsPropertyClearly.getChildAt(1)).setChecked(true);
        }*/
    }
    public void setEditiblity(){

        et_details.setEnabled(false);
        etRemark1.setEnabled(false);
        etRemark2.setEnabled(false);
        etPartlyDemarcated.setEnabled(false);

        rbPartlyDemarc.setClickable(false);
        radioButton.setClickable(false);
        spinnerIsLandMerged.setEnabled(false);
        spinnerInCaseProperty1.setEnabled(false);
        spinnerInCaseProperty2.setEnabled(false);





        for(int i=0;i<rgIndependentAccess.getChildCount();i++)
            rgIndependentAccess.getChildAt(i).setClickable(false);
        for(int i=0;i<rgIsPropertyClearly.getChildCount();i++)
            rgIsPropertyClearly.getChildAt(i).setClickable(false);
        for(int i=0;i<rgSub.getChildCount();i++)
            rgSub.getChildAt(i).setClickable(false);

    }
    public void getid(View v) {

//        tv_header = v.findViewById(R.id.tv_header);
//        rl_casedetail = v.findViewById(R.id.rl_casedetail);
//        tv_caseheader = v.findViewById(R.id.tv_caseheader);
//        tv_caseid = v.findViewById(R.id.tv_caseid);

        //  progressbar = (ProgressBar) v.findViewById(R.id.Progress);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page=false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page=true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        //  back = v.findViewById(R.id.rl_back);
        next = v.findViewById(R.id.next);

        btnUploadAgreement = v.findViewById(R.id.btnUploadAgreement);
        ivUploadAgreement = v.findViewById(R.id.ivUploadAgreement);

        tvPrevious = v.findViewById(R.id.tvPrevious);
        dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);
        //   dots = (RelativeLayout) v.findViewById(R.id.rl_dots);

        //RelativeLayout
        rlPartlyDemarcated = v.findViewById(R.id.rlPartlyDemarcated);
        rlRemark1 = v.findViewById(R.id.rlRemark1);
        rlRemark2 = v.findViewById(R.id.rlRemark2);

        //RadioGroup
        rgIsPropertyClearly = v.findViewById(R.id.rgIsPropertyClearly);
        rgIndependentAccess = v.findViewById(R.id.rgIndependentAccess);
        rgSub = v.findViewById(R.id.rgSub);

        //Spinner
        spinnerIsLandMerged = v.findViewById(R.id.spinnerIsLandMerged);
        spinnerInCaseProperty1 = v.findViewById(R.id.spinnerInCaseProperty1);
        spinnerInCaseProperty2 = v.findViewById(R.id.spinnerInCaseProperty2);

        //RadioButton
        rbPartlyDemarc = v.findViewById(R.id.rbPartlyDemarc);

        //EditText
        etRemark1 = v.findViewById(R.id.etRemark1);
        rl_detail = v.findViewById(R.id.rl_detail);
        etRemark2 = v.findViewById(R.id.etRemark2);
        etPartlyDemarcated = v.findViewById(R.id.etPartlyDemarcated);
        et_details = v.findViewById(R.id.et_details);
    }

    public void setListener() {
        //   back.setOnClickListener(this);
        next.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        btnUploadAgreement.setOnClickListener(this);
        // dots.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
         /*   case R.id.rl_back:
                onBackPressed();
                break;
*/
            case R.id.tvPrevious:
               /* intent = new Intent(GeneralForm5.this, GeneralForm4.class);
                startActivity(intent);*/
                //  ((Dashboard)mActivity).displayView(9);
                mActivity.onBackPressed();
                break;

            case R.id.next:
                if(edit_page)
                    validation();
                else
                        ((Dashboard) mActivity).displayView(24);
                break;
            case R.id.btnUploadAgreement:

                showCameraGalleryDialogAttachment();

                break;

           /* case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;*/
        }
    }

    private void validation() {
        if (rgIndependentAccess.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Is Independent access available to the property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvIsIndep);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgIsPropertyClearly.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Is property clearly demarcated with permanent boundaries?", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvIsProp);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rlPartlyDemarcated.getVisibility() == View.VISIBLE && etPartlyDemarcated.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Partly demarcated details", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPartlyDemarcated.requestFocus();
        } else if (rlRemark1.getVisibility() == View.VISIBLE && etRemark1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Remarks", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRemark1.requestFocus();
        } else if (rlRemark2.getVisibility() == View.VISIBLE && etRemark2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Remarks", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRemark2.requestFocus();
        } else if (spinnerIsLandMerged.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvIsLand);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerInCaseProperty1.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvInCase1);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerInCaseProperty2.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvInCase2);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else {
            putIntoHm();
//            intent = new Intent(GeneralForm5.this, GeneralForm6.class);
//            startActivity(intent);
            ((Dashboard) mActivity).displayView(24);
            return;
        }
    }

    public void putIntoHm() {
        hm.put(Constants.pageVisited,"pageVisited5");
        if (rl_detail.getVisibility() == View.VISIBLE) {
            hm.put("et_details", et_details.getText().toString());
        } else {

        }
        if (rlRemark1.getVisibility() == View.VISIBLE) {
            hm.put("remark1", etRemark1.getText().toString());
        } else {
            hm.put("remark1", "");
        }

        if (rlRemark1.getVisibility() == View.VISIBLE) {
            hm.put("remark2", etRemark2.getText().toString());
        } else {
            hm.put("remark2", "");
        }

        int selectedIdIndependentAccess = rgIndependentAccess.getCheckedRadioButtonId();
        View radioButtonIndependentAccess = v.findViewById(selectedIdIndependentAccess);
        int idx = rgIndependentAccess.indexOfChild(radioButtonIndependentAccess);
        RadioButton r = (RadioButton) rgIndependentAccess.getChildAt(idx);
        //  String selectedText = r.getText().toString();
        hm.put("radioButtonIndependentAccess", String.valueOf(idx));

        int selectedIdIsPropertyClearly = rgIsPropertyClearly.getCheckedRadioButtonId();
        View radioButtonIsPropertyClearly = v.findViewById(selectedIdIsPropertyClearly);
        int idx2 = rgIsPropertyClearly.indexOfChild(radioButtonIsPropertyClearly);
        RadioButton r2 = (RadioButton) rgIsPropertyClearly.getChildAt(idx2);
        //  String selectedText2 = r2.getText().toString();
        hm.put("radioButtonIsPropertyClearly", String.valueOf(idx2));

        if (idx2 == 0 || idx2 == 1 || idx2 == 4) {
            hm.put("partlyDemarcatedDetails", "");
        } else {
            hm.put("partlyDemarcatedDetails", etPartlyDemarcated.getText().toString());
        }


        String inCaseProperty1 = String.valueOf(spinnerInCaseProperty1.getSelectedItemPosition());
        String inCaseProperty2 = String.valueOf(spinnerInCaseProperty2.getSelectedItemPosition());
        String isLandMerged = String.valueOf(spinnerIsLandMerged.getSelectedItemPosition());

        hm.put("inCaseProperty1Spinner", inCaseProperty1);
        hm.put("inCaseProperty2Spinner", inCaseProperty2);
        hm.put("isLandMergedSpinner", isLandMerged);

        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        //DatabaseController.removeTable(TableGeneralForm.attachment);
        // DatabaseController.deleteColumnData("column1");

        ContentValues contentValues = new ContentValues();

        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column5.toString(), jsonArrayData.toString());
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "5");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));


    }

    public void selectDB() throws JSONException {
        sub_cat = DatabaseController.getTableOne("column5", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column5"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if(object.has(Constants.pageVisited)){
                    pageVisited=object.getString(Constants.pageVisited);
                }

                etPartlyDemarcated.setText(object.getString("partlyDemarcatedDetails"));
                etRemark1.setText(object.getString("remark1"));
                etRemark2.setText(object.getString("remark2"));
               /* if(!(object.getString("et_details").equals(""))){
                    et_details.setVisibility(View.VISIBLE);
                    et_details.setText(object.getString("et_details"));
                }*/


                try {
                    ((RadioButton) rgIndependentAccess.getChildAt(Integer.parseInt(object.getString("radioButtonIndependentAccess")))).setChecked(true);
                }catch (Exception e){
                    e.printStackTrace();
                }


             /*   if (object.getString("radioButtonIndependentAccess").equals("Clear independent access is available")) {
                    ((RadioButton) rgIndependentAccess.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonIndependentAccess").equals("Access is available in sharing of other adjoining property")) {
                    ((RadioButton) rgIndependentAccess.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonIndependentAccess").equals("Temporary access created from other properties")) {
                    ((RadioButton) rgIndependentAccess.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonIndependentAccess").equals("Access is available from other property with easement rights available")) {
                    ((RadioButton) rgIndependentAccess.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonIndependentAccess").equals("Access is available from another property with no formal easement rights available")) {
                    ((RadioButton) rgIndependentAccess.getChildAt(4)).setChecked(true);
                } else if (object.getString("radioButtonIndependentAccess").equals("No clear access is available")) {
                    ((RadioButton) rgIndependentAccess.getChildAt(6)).setChecked(true);
                } else if (object.getString("radioButtonIndependentAccess").equals("Access is closed due to dispute")) {
                    ((RadioButton) rgIndependentAccess.getChildAt(7)).setChecked(true);
                }*/


               /* if (object.getString("radioButtonIsPropertyClearly").equals("Yes demarcated properly")) {
                    ((RadioButton) rgIsPropertyClearly.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonIsPropertyClearly").equals("No demarcation done and mixed with other adjoining Lands")) {
                    ((RadioButton) rgIsPropertyClearly.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonIsPropertyClearly").equals("Partly demarcated only")) {
                    ((RadioButton) rgIsPropertyClearly.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonIsPropertyClearly").equals("Demarcated only with Temporary boundaries")) {
                    ((RadioButton) rgIsPropertyClearly.getChildAt(3)).setChecked(true);
                }*/

                try {
                    ((RadioButton) rgIsPropertyClearly.getChildAt(Integer.parseInt(object.getString("radioButtonIsPropertyClearly")))).setChecked(true);
                }catch (Exception e){
                    e.printStackTrace();
                }


                if (object.getString("isLandMergedSpinner").equals("0")) {
                    spinnerIsLandMerged.setSelection(0);
                } else if (object.getString("isLandMergedSpinner").equals("1")) {
                    spinnerIsLandMerged.setSelection(1);
                } else if (object.getString("isLandMergedSpinner").equals("2")) {
                    spinnerIsLandMerged.setSelection(2);
                } else if (object.getString("isLandMergedSpinner").equals("3")) {
                    spinnerIsLandMerged.setSelection(3);
                }


                if (object.getString("inCaseProperty1Spinner").equals("0")) {
                    spinnerInCaseProperty1.setSelection(0);
                } else if (object.getString("inCaseProperty1Spinner").equals("1")) {
                    spinnerInCaseProperty1.setSelection(1);
                } else if (object.getString("inCaseProperty1Spinner").equals("2")) {
                    spinnerInCaseProperty1.setSelection(2);
                } else if (object.getString("inCaseProperty1Spinner").equals("3")) {
                    spinnerInCaseProperty1.setSelection(3);
                }

                if (object.getString("inCaseProperty2Spinner").equals("0")) {
                    spinnerInCaseProperty2.setSelection(0);
                } else if (object.getString("inCaseProperty2Spinner").equals("1")) {
                    spinnerInCaseProperty2.setSelection(1);
                } else if (object.getString("inCaseProperty2Spinner").equals("2")) {
                    spinnerInCaseProperty2.setSelection(2);
                } else if (object.getString("inCaseProperty2Spinner").equals("3")) {
                    spinnerInCaseProperty2.setSelection(3);
                }

                /*if (InitiateSurveyForm.surveyTypeCheck == 1){
                    spinnerIsLandMerged.setSelection(3);
                    spinnerInCaseProperty1.setSelection(3);
                    spinnerInCaseProperty2.setSelection(3);

                }else {
                    spinnerIsLandMerged.setSelection(0);
                    spinnerInCaseProperty1.setSelection(0);
                    spinnerInCaseProperty2.setSelection(0);

                }*/
            }

        }
    }

    public void condition() {

        rgIsPropertyClearly.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);
                Log.v("getcheckk", String.valueOf(a));
                switch (a) {
                    case 2:
                        rlPartlyDemarcated.setVisibility(View.VISIBLE);
                        break;
                    default:
                        rlPartlyDemarcated.setVisibility(View.GONE);
                        break;

                }
            }
        });

        spinnerInCaseProperty1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int s = adapterView.getSelectedItemPosition();
                if (s == 1) {
                    rlRemark1.setVisibility(View.VISIBLE);
                } else if (s == 2) {
                    rlRemark1.setVisibility(View.VISIBLE);
                } else {
                    rlRemark1.setVisibility(View.GONE);

                }

                /*((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerInCaseProperty1.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerInCaseProperty2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int s = adapterView.getSelectedItemPosition();
                if (s == 1) {
                    rlRemark2.setVisibility(View.VISIBLE);
                } else if (s == 0 || s == 2) {
                    rlRemark2.setVisibility(View.GONE);
                }

              /*  ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerInCaseProperty2.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void populateSinner() {

        spinnerAdapter = new SpinnerAdapter(mActivity, isLandMergedArray);
        spinnerIsLandMerged.setAdapter(spinnerAdapter);

        spinnerIsLandMerged.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
//                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
//                if (spinnerIsLandMerged.getSelectedItemPosition() == 0) {
//                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
//                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner InCaseProperty1
        spinnerAdapter = new SpinnerAdapter(mActivity, inCaseProperty1Array);
        spinnerInCaseProperty1.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, inCaseProperty2Array);
        spinnerInCaseProperty2.setAdapter(spinnerAdapter);

    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        //if Required then only
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.general_one:
                        ((Dashboard)mActivity).displayView(6);
                        /*intent=new Intent(GeneralForm3.this, GeneralForm1.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_two:
                        ((Dashboard)mActivity).displayView(7);
                        return true;

                    case R.id.general_three:
                        ((Dashboard)mActivity).displayView(8);
                        return true;

                    case R.id.general_four:
                        ((Dashboard)mActivity).displayView(9);
                        return true;

                    case R.id.general_five:
                        ((Dashboard)mActivity).displayView(10);
                        return true;

                    case R.id.general_six:
                        ((Dashboard)mActivity).displayView(11);
                        return true;

                    case R.id.general_seven:
                        ((Dashboard)mActivity).displayView(12);
                        return true;

                    case R.id.general_eight:
                        ((Dashboard)mActivity).displayView(13);
                        return true;

                    case R.id.general_nine:
                        ((Dashboard)mActivity).displayView(14);
                        return true;

                    case R.id.general_ten:
                        ((Dashboard)mActivity).displayView(15);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void showCameraGalleryDialogAttachment() {

        final Dialog dialog = new Dialog(mActivity);

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

        return FileProvider.getUriForFile(mActivity, mActivity.getPackageName()+".provider", getOutputMediaFile(type));

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

                ivUploadAgreement.setVisibility(View.VISIBLE);
                ivUploadAgreement.setImageBitmap(rotatedBitmap);

            }
        }else if (requestCode == SELECT_PICTURE) {
            if (data != null) {

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = mActivity.getContentResolver().query(mImageUri,
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
                        bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), mArrayUri.get(0));

                        ivUploadAgreement.setVisibility(View.VISIBLE);
                        ivUploadAgreement.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //   encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                }


            } else {
                Toast.makeText(mActivity, "Unable to select image", Toast.LENGTH_LONG).show();
            }

        }
    }
}
