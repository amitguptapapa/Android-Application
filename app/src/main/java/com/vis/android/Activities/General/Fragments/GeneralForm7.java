package com.vis.android.Activities.General.Fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;

public class GeneralForm7 extends BaseFragment implements View.OnClickListener {
  //  RelativeLayout back, dots;
  //  LinearLayout dropdown;
    TextView next, tvPrevious, tvAnyConversionToTheLandUse;
    Intent intent;
    ImageView ivAnyConversionToTheLandUse;

    LinearLayout llConversionCertiDet, llConversionCerti;

    RadioGroup rgAnyConversionToTheLandUse;

    RadioButton rbYes1;
    Boolean edit_page = true;


    EditText etDetails ,etAnyOtherZoning,etAnyOtherSurrounding,etAnyOtherProperty,etAnyOtherCurrentBest,etAnyOtherCurrentActivity;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    Spinner spinnerZoningRegulations, spinnerPropertyLandUse, spinnerCurrentBestUseOfProperty, spinnerCurrentActivityCarried,spinnerSurroundingLand;

    String[] arrayZoningRegulations = {"Choose an item.","Residential colony","Group Housing Society","Commercial","Industrial","Institutional","Agricultural","SEZ Land",
            "Frieght corridor land","Industrial corridor land","Special Purpose Vehicle Land","Mixed use (Residential cum commercial)","Rural","Lal Dora Land","For Hotel/ Resort",
            "Exhibition cum Convention center","Exhibition center","Convention Center","Farm House","For School","For College","For University","For Hospital","Amusement Park",
            "Nursing Home","Old Age Home","Park","Shopping Mall","Shopping Complex","For Public Ameneties","Army Cantonment Area","Government Land","Mining Land","Tea Estate",
            "Coastal Regulatory Zone","Forest Land","Not yet under zoning regulation","Any other"};
// String[] arrayZoningRegulations = {"Choose an item", "Residential colony", "Group Housing Society", "Commercial", "Industrial", "Institutional", "Agricultural",
//            "SEZ Land", "Frieght corridor land", "Industrial corridor land", "Special Purpose Vehicle Land", "Mixed use (Residential cum commercial)",
//            "Rural", "Lal Dora Land", "For Hotel/ Resort", "Convention Center", "Farm House", "For School", "For College", "For University", "For Hospital", "Amusement Park",
//            "Nursing Home", "Old Age Home", "Park", "Shopping Mall", "Shopping Complex", "For Public Ameneties", "Army Cantonment Area", "Government Land", "Mining Land",
//            "Tea Estate", "Coastal Regulatory Zone", "Forest Land", "Not yet under zoning regulation", "Any other"};

    String[] arrayPropertyLandUse = {"Choose an item","All adjacent properties are used for residential purpose","Its a residential colony and all adjacent properties are used for residential purpose",
            "All adjacent properties are used for commercial purpose","Its a Commercial complex/ market and all adjoining properties are used for commercial purpose",
            "Notified Industrial area so all adjacent land use is Industrial","Not an Industrial zone but many Industries are setup nearby","Majorly all nearby lands are used for Agriculture purpose",
            "Rural area and most of the nearby land is lying barron","No proper zoning regulations imposed. Nearby properties are of mixed use.","Residential zone but all nearby plots are currently vacant",
            "Institutional Area","Public ameneties","It is a mixed used area. On Ground floor properties are used as commercial and above that as residential.",
            "It is a mixed used area. On main road properties are used as commercial and internal roads as residential.","It is a mixed used area, commercial & residential.","Any Other"};

//String[] arrayPropertyLandUse = {"Choose an item", "All adjacent properties are used for residential purpose", "Its a residential colony and all adjacent properties are used for residential purpose",
//            "All adjacent properties are used for commercial purpose", "Its a Commercial complex/ market and all adjoining properties are used for commercial purpose",
//            "Notified Industrial area so all adjacent land use is Industrial", "Not an Industrial zone but many Industries are setup nearby",
//            "Majorly all nearby lands are used for Agriculture purpose", "Rural area and most of the nearby land is barron", "No proper zoning regulations imposed. Nearby properties are of mixed use",
//            "Residential zone but all nearby plots are currently vacant", "Institutional", "Public amenities", "Any Other"};

    String[] arrayCurrentBestUseOfProperty = {"Choose an item","Residential Apartment in multistoried building","Residential apartment in low rise building","Residential House (Plotted development)",
            "Residential Builder Floor","Residential Plot/Land","Mansion","Kothi","Villa","Group Housing Society","Commercial Office Unit","Commercial Shop Unit","Commercial Floor Unit",
            "Commercial Office (Independent)","Commercial Shop (Independent)","Commercial Floor (Independent Plotted Development)","Hotel/ Resort","Shopping Mall","Shopping Complex","Anchor Store",
            "Restaurant","Amusement Park","Multiplex","Cinema Hall","Institutional","Educational Institution (School/ College/ University)","Hospital","Nursing Home","Old Age Home","Industrial","SEZ",
            "Agricultural Land","Farm House","Mining Land","For Public Ameneties & Utilities","Exhibition cum Convention Center","Exhibition Center","Convention Center","Coastal Regulatory Zone",
            "Frieght corridor land","Industrial corridor land","Special Purpose Vehicle Land","Lal Dora Land","For Metro Railway development","For Airport development","Railway Land",
            "Area not notified under Master Plan","Railway"};
// String[] arrayCurrentBestUseOfProperty = {"Choose an item", "Residential Apartment in multistoried building", "Residential apartment in low rise building",
//            "Residential House (Plotted development)", "Residential Builder Floor", "Residential Plot/Land", "Mansion", "Kothi", "Villa", "Group Housing Society",
//            "Commercial Office unit", "Commercial Shop unit", "Commercial Floor unit", "Commercial Office (Independent)", "Commercial Shop (Independent)",
//            "Commercial Floor (Independent Plotted Development)", "Hotel/ Resort", "Shopping Mall", "Shopping Complex", "Anchor Store", "Restaurant", "Amusement Park",
//            "Multiplex", "Cinema Hall", "Institutional", "Educational Institution (School/ College/ University)", "Hospital", "Nursing Home", "Old Age Home", "Industrial",
//            "SEZ", "Agricultural Land", "Farm House", "Mining Land", "For Public Amenities", "Any other"};

    String[] arrayCurrentActivityCarried = {"Choose an item", "Residential purpose", "Commercial purpose", "Institutional purpose", "Industrial purpose",
            "Official purpose", "Hotel/ Resort", "Shopping Mall", "Shopping Complex", "Anchor Store", "Restaurant", "Amusement Park", "Multiplex", "Cinema Hall",
            "Educational Institution (School/ College/ University)", "Hospital", "Nursing Home", "Old Age Home", "SEZ", "Agricultural Land", "Farm House", "Mining Land",
            "For Public Ameneties", "Vacant Land", "Mixed Use (Residential cum Commercial)", "Godown purpose", "Warehouse purpose", "Property is locked", "Any other Use"};


    SpinnerAdapter spinnerAdapter;
    Preferences pref;
   // ProgressBar progressbar;
    ImageView ivCameraCertify;
    //String
    public String picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", encodedString = "", setPic = "";


    //Uri
    Uri picUri, fileUri;

    //StaticString
    private static final String IMAGE_DIRECTORY_NAME = "VIS";

    //Static Int
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;

    //Bitmap
    Bitmap bitmap;

  //  TextView tv_caseheader,tv_caseid,tv_header;

  //  RelativeLayout rl_casedetail;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_generalform7, container, false);

        getid(v);
        setListener();
        populateSinner();
        condition();

//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));
//
//        progressbar.setMax(10);
//        progressbar.setProgress(7);

      //  Arrays.sort(arrayZoningRegulations, 1, arrayZoningRegulations.length-2);

      //  Arrays.sort(arrayPropertyLandUse, 1, arrayPropertyLandUse.length);
      //  Arrays.sort(arrayCurrentBestUseOfProperty, 1, arrayCurrentBestUseOfProperty.length-1);
      //  Arrays.sort(arrayCurrentActivityCarried, 1, arrayCurrentActivityCarried.length);

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!edit_page) {
            setEditiblity();
        }


        return v;
    }

    public void setEditiblity(){
        ivAnyConversionToTheLandUse.setEnabled(false);

        for (int i = 0; i < rgAnyConversionToTheLandUse.getChildCount(); i++)
            rgAnyConversionToTheLandUse.getChildAt(i).setClickable(false);

        rbYes1.setClickable(false);
        ivCameraCertify.setEnabled(false);

        spinnerZoningRegulations.setEnabled(false);
 spinnerPropertyLandUse.setEnabled(false);
 spinnerCurrentBestUseOfProperty.setEnabled(false);
 spinnerCurrentActivityCarried.setEnabled(false);
spinnerSurroundingLand.setEnabled(false);

        etDetails .setEnabled(false);
etAnyOtherZoning.setEnabled(false);
etAnyOtherSurrounding.setEnabled(false);
etAnyOtherProperty.setEnabled(false);
etAnyOtherCurrentBest.setEnabled(false);
etAnyOtherCurrentActivity.setEnabled(false);



    }
    public void getid(View v)
    {

//        tv_header = findViewById(R.id.tv_header);
//        rl_casedetail = findViewById(R.id.rl_casedetail);
//        tv_caseheader = findViewById(R.id.tv_caseheader);
//        tv_caseid = findViewById(R.id.tv_caseid);

        ivAnyConversionToTheLandUse = v.findViewById(R.id.ivAnyConversionToTheLandUse);
        tvAnyConversionToTheLandUse = v.findViewById(R.id.tvAnyConversionToTheLandUse);
        ivCameraCertify = v.findViewById(R.id.ivCameraCertify);
      //  progressbar = (ProgressBar) v.findViewById(R.id.Progress);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
       // back = v.findViewById(R.id.rl_back);
        next = v.findViewById(R.id.next);
        tvPrevious = v.findViewById(R.id.tvPrevious);
      //  dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);
      //  dots = (RelativeLayout) v.findViewById(R.id.rl_dots);

        llConversionCerti = v.findViewById(R.id.llConversionCerti);
        llConversionCertiDet = v.findViewById(R.id.llConversionCertiDet);

        rgAnyConversionToTheLandUse = v.findViewById(R.id.rgAnyConversionToTheLandUse);

        rbYes1 = v.findViewById(R.id.rbYes1);

      //  etSurroundingLandUses = v.findViewById(R.id.etSurroundingLandUses);


        spinnerZoningRegulations = v.findViewById(R.id.spinnerZoningRegulations);
        spinnerPropertyLandUse = v.findViewById(R.id.spinnerPropertyLandUse);
        spinnerCurrentBestUseOfProperty = v.findViewById(R.id.spinnerCurrentBestUseOfProperty);
        spinnerCurrentActivityCarried = v.findViewById(R.id.spinnerCurrentActivityCarried);
        spinnerSurroundingLand = v.findViewById(R.id.spinnerSurroundingLand);

        //,,,,
        etDetails = v.findViewById(R.id.etDetails);

        etAnyOtherZoning = v.findViewById(R.id.etAnyOtherZoning);
        etAnyOtherSurrounding = v.findViewById(R.id.etAnyOtherSurrounding);
        etAnyOtherProperty = v.findViewById(R.id.etAnyOtherProperty);
        etAnyOtherCurrentBest = v.findViewById(R.id.etAnyOtherCurrentBest);
        etAnyOtherCurrentActivity = v.findViewById(R.id.etAnyOtherCurrentActivity);

    }

    public void setListener() {
        //back.setOnClickListener(this);
        next.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
       // dots.setOnClickListener(this);
        ivAnyConversionToTheLandUse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
          /*  case R.id.rl_back:
                onBackPressed();
                break;*/

            case R.id.tvPrevious:
               // ((Dashboard)mActivity).displayView(11);
                mActivity.onBackPressed();

              /*  intent=new Intent(GeneralForm7.this,GeneralForm6.class);
                startActivity(intent);*/
                break;

            case R.id.next:
                if (edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(13);
                break;


           /* case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;*/

            case R.id.ivAnyConversionToTheLandUse:
                showCameraGalleryDialog();
                break;
        }
    }

    private void validation() {

        if (spinnerZoningRegulations.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvZoning);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }/* else if (etSurroundingLandUses.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Surrounding land uses", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSurroundingLandUses.requestFocus();
        } */else if (spinnerSurroundingLand.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerSurroundingLand);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerPropertyLandUse.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvPropertyLand);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgAnyConversionToTheLandUse.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Any Conversion to Land Use", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvAny);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llConversionCertiDet.getVisibility() == View.VISIBLE && etDetails.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Details", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetails.requestFocus();
        } else if (llConversionCertiDet.getVisibility() == View.VISIBLE && tvAnyConversionToTheLandUse.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach conversion certificate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvAnyConversionToTheLandUse.requestFocus();
        } else if (spinnerCurrentBestUseOfProperty.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvCurrent);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerCurrentActivityCarried.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvCurrentAct);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }
      /* else if (tvAnyConversionToTheLandUse.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvAnyConversionToTheLandUse, "Please Attach Completion Certificate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvAnyConversionToTheLandUse.requestFocus();
        }*/
        else {
            putIntoHm();
            ((Dashboard)mActivity).displayView(13);
            return;
        }
    }

    public void putIntoHm() {
        hm.put("tvAnyConversionToTheLandUse", tvAnyConversionToTheLandUse.getText().toString());
        hm.put("certificateImage", encodedString);

        int selectedIdAnyConversionToTheLandUse = rgAnyConversionToTheLandUse.getCheckedRadioButtonId();
        View radioButtonAnyConversionToTheLandUse = v.findViewById(selectedIdAnyConversionToTheLandUse);
        int idx = rgAnyConversionToTheLandUse.indexOfChild(radioButtonAnyConversionToTheLandUse);
        RadioButton r = (RadioButton) rgAnyConversionToTheLandUse.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonAnyConversionToTheLandUse", selectedText);

        if (idx == 1) {
            hm.put("anyConversionDetails", "");
        } else {
            hm.put("anyConversionDetails", etDetails.getText().toString());
        }


        String zoningRegulations = String.valueOf(spinnerZoningRegulations.getSelectedItemPosition());
        String surroundingLandUses = String.valueOf(spinnerSurroundingLand.getSelectedItemPosition());
        String propertyLandUse = String.valueOf(spinnerPropertyLandUse.getSelectedItemPosition());
        String currentBestUseOfProperty = String.valueOf(spinnerCurrentBestUseOfProperty.getSelectedItemPosition());
        String currentActivityCarried = String.valueOf(spinnerCurrentActivityCarried.getSelectedItemPosition());


        hm.put("zoningRegulationsSpinner", zoningRegulations);
        hm.put("surroundingLandUses", surroundingLandUses);
        hm.put("propertyLandUseSpinner", propertyLandUse);
        hm.put("currentBestUseOfPropertySpinner", currentBestUseOfProperty);
        hm.put("currentActivityCarriedSpinner", currentActivityCarried);


        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        //DatabaseController.removeTable(TableGeneralForm.attachment);
        // DatabaseController.deleteColumnData("column1");

        ContentValues contentValues = new ContentValues();

        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column7.toString(), jsonArrayData.toString());
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "7");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));


    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getTableOne("column7", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column7"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);


            //    etSurroundingLandUses.setText(object.getString("surroundingLandUses"));
                etDetails.setText(object.getString("anyConversionDetails"));
                tvAnyConversionToTheLandUse.setText(object.getString("tvAnyConversionToTheLandUse"));
                if(object.has("certificateImage")){
                    encodedString = object.getString("certificateImage");
                    byte[] byteFormat = Base64.decode(encodedString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                    ivCameraCertify.setImageBitmap(decodedImage);
                    ivCameraCertify.setVisibility(View.VISIBLE);
                }

                if (object.getString("radioButtonAnyConversionToTheLandUse").equals("Yes")) {
                    ((RadioButton) rgAnyConversionToTheLandUse.getChildAt(0)).setChecked(true);
                    llConversionCertiDet.setVisibility(View.VISIBLE);
                } else if (object.getString("radioButtonAnyConversionToTheLandUse").equals("No")) {
                    ((RadioButton) rgAnyConversionToTheLandUse.getChildAt(1)).setChecked(true);
                    llConversionCertiDet.setVisibility(View.GONE);
                }

                spinnerSurroundingLand.setSelection(Integer.parseInt(object.getString("surroundingLandUses")));

                spinnerZoningRegulations.setSelection(Integer.parseInt(object.getString("zoningRegulationsSpinner")));

                spinnerPropertyLandUse.setSelection(Integer.parseInt(object.getString("propertyLandUseSpinner")));

                spinnerCurrentBestUseOfProperty.setSelection(Integer.parseInt(object.getString("currentBestUseOfPropertySpinner")));

                spinnerCurrentActivityCarried.setSelection(Integer.parseInt(object.getString("currentActivityCarriedSpinner")));

                // String projectName = object.getString("projectName");
                // Log.e("!!!projectName", projectName);
            }

        }
    }

    private void populateSinner() {

   /*     ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayZoningRegulations);

        spinnerZoningRegulations.setAdapter(adapter);*/

   // "Not yet under zoning regulation", "Any other"

//        arrayZoningRegulations[arrayZoningRegulations.length-1] = "Not yet under zoning regulation";
//        arrayZoningRegulations[arrayZoningRegulations.length-1] = "Any other";

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayZoningRegulations);
        spinnerZoningRegulations.setAdapter(spinnerAdapter);

        spinnerZoningRegulations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerZoningRegulations.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/
                if (arrayZoningRegulations[i].equalsIgnoreCase("Any other")) {
                    //   Toast.makeText(SurveyNotRequiredActivity.this, "Please select cancel reason", Toast.LENGTH_SHORT).show();

                    etAnyOtherZoning.setVisibility(View.VISIBLE);


                }else {
                    etAnyOtherZoning.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSurroundingLand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerZoningRegulations.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/
                if (arrayPropertyLandUse[i].equalsIgnoreCase("Any other")) {
                    //   Toast.makeText(SurveyNotRequiredActivity.this, "Please select cancel reason", Toast.LENGTH_SHORT).show();

                    etAnyOtherSurrounding.setVisibility(View.VISIBLE);


                }else {
                    etAnyOtherSurrounding.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //////
        /*ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayPropertyLandUse);

        spinnerPropertyLandUse.setAdapter(adapter1);*/
        spinnerAdapter = new SpinnerAdapter(mActivity, arrayPropertyLandUse);
        spinnerSurroundingLand.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayCurrentBestUseOfProperty);
        spinnerPropertyLandUse.setAdapter(spinnerAdapter);

        spinnerPropertyLandUse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            /*    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerPropertyLandUse.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/

                if (arrayCurrentBestUseOfProperty[i].equalsIgnoreCase("Any other")) {
                    //   Toast.makeText(SurveyNotRequiredActivity.this, "Please select cancel reason", Toast.LENGTH_SHORT).show();

                    etAnyOtherProperty.setVisibility(View.VISIBLE);


                }else {
                    etAnyOtherProperty.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /////
      /*  ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayCurrentBestUseOfProperty);

        spinnerCurrentBestUseOfProperty.setAdapter(adapter2);*/

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayCurrentBestUseOfProperty);
        spinnerCurrentBestUseOfProperty.setAdapter(spinnerAdapter);


        spinnerCurrentBestUseOfProperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerCurrentBestUseOfProperty.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/

                if (arrayCurrentBestUseOfProperty[i].equalsIgnoreCase("Any other")) {
                    //   Toast.makeText(SurveyNotRequiredActivity.this, "Please select cancel reason", Toast.LENGTH_SHORT).show();

                    etAnyOtherCurrentBest.setVisibility(View.VISIBLE);


                }else {
                    etAnyOtherCurrentBest.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerAdapter = new SpinnerAdapter(mActivity, arrayCurrentActivityCarried);
        spinnerCurrentActivityCarried.setAdapter(spinnerAdapter);

        spinnerCurrentActivityCarried.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               /* ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerCurrentActivityCarried.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/

                if (arrayCurrentActivityCarried[i].equalsIgnoreCase("Any other use")) {
                    //   Toast.makeText(SurveyNotRequiredActivity.this, "Please select cancel reason", Toast.LENGTH_SHORT).show();

                    etAnyOtherCurrentActivity.setVisibility(View.VISIBLE);


                }else {
                    etAnyOtherCurrentActivity.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void condition() {
        //rbYes1.setChecked(true);

        rgAnyConversionToTheLandUse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);
                Log.v("getcheckk", String.valueOf(a));
                switch (a) {
                    case 0:
                        llConversionCerti.setVisibility(View.VISIBLE);
                        llConversionCertiDet.setVisibility(View.VISIBLE);
                        break;
                    default:
                        llConversionCerti.setVisibility(View.GONE);
                        llConversionCertiDet.setVisibility(View.GONE);
                        break;

                }
            }
        });
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


    public void showCameraGalleryDialog() {

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

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == mActivity.RESULT_OK) {

                picturePath = fileUri.getPath().toString();
                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);

                String selectedImagePath = picturePath;

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

                encodedString = getEncoded64ImageStringFromBitmap(bitmap);

                Log.v("encodedstring", encodedString);
                //setPic="1";
                setPictures(bitmap, setPic, encodedString);


            }
        } else if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                Uri contentURI = data.getData();
                //get the Uri for the captured image
                picUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = mActivity.getContentResolver().query(contentURI, filePathColumn, null, null, null);
                cursor.moveToFirst();
                Log.v("piccc", "pic");
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                System.out.println("Image Path : " + picturePath);
                cursor.close();
                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                ext = getFileType(picturePath);
                String selectedImagePath = picturePath;
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
                encodedString = getEncoded64ImageStringFromBitmap(bitmap);
                Log.v("encodedstring", encodedString);

                Log.v("picture_path====", filename);
                setPictures(bitmap, setPic, encodedString);

            } else {
                Toast.makeText(mActivity, "unable to select image", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setPictures(Bitmap b, String setPic, String base64) {
        tvAnyConversionToTheLandUse.setText(filename);
        ivCameraCertify.setImageBitmap(b);
        ivCameraCertify.setVisibility(View.VISIBLE);
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

    public static String getFileType(String path) {
        String fileType = null;
        fileType = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase();
        return fileType;
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }
}
