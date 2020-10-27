package com.vis.android.Activities.MultiStory.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.DrawPlotActivity;
import com.vis.android.Activities.General.Fragments.InitiateSurveyForm;
import com.vis.android.Activities.General.MeasureDistanceActivity;
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

public class MSForm6 extends BaseFragment implements View.OnClickListener {
    //StaticString
    private static final String IMAGE_DIRECTORY_NAME = "VIS";
    //Static Int
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;
    private static final int REQUEST_PERMISSIONS = 1;
    private static String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //String
    public String picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", encodedString = "", setPic = "";
    String pageVisited="";
    LinearLayout dropdown;
    TextView next, tvPrevious, tvInCasediffCoverTitle ,tvInCasediffCoverMap,
            tvRegularizationCerti, tvInCaseHeading, tvCoveredHeading,  tvNatureHeading, tvExtentHeading, tvIsThisHeading, tvDifference ;
    Intent intent;
    Preferences pref;
    //RadioGroup
    RadioGroup  rgCoveredBuiltup, rgPropertyMeasurement;
    //LinearLayout
    LinearLayout  llCoveredBuiltup, llRegularizationCerti,  llCoveredBuiltup2, llReasonNoMeasurement;
    Boolean edit_page = true;
    //EditText
    EditText et_detail,
             etGroundCoverage, etInCaseOfDifferenceOf2;
    //RelativeLayout
    RelativeLayout rlLandAreaEast, rlLandAreaWest, rlLandAreaNorth, rlLandAreaSouth, rlLandAreaAsPerTitle, rlLandAreaAsPerMap, rlLandAreaAsSiteSurvey;
    //EditText
    EditText etCoveredAreaAsPerTitle, etCoveredAreaAsPerMap, etCoveredAreaAsSiteSurvey;
    //RelativeLayout
    RelativeLayout rlCoveredAreaAsPerTitle, rlCoveredAreaAsPerMap, rlCoveredAreaAsSiteSurvey;
    //Spinner
    Spinner spinnerIsThisBeingRegularized,  spinnerDisplayUnitCovered;

    String isThisBeingRegularizedArray[] = {"Choose an item", "Yes", "No", "No Information Provided", "Not Applicable", "No Information Available"};
    String[] arrayFtMtr = {"Ft.", "Mtr."};
    //CheckBox
    //cbDetail1, cbDetail2, cbDetail3,
    CheckBox  cbItIsAFlat, cbPropertyWasLocked, cbOwnerPossessee, cbNpaProperty, cbVeryLargeIrregular, cbAnyOtherReason, cbConstructionDoneWithoutMap,
            cbConstructionNotAsPer, cbExtraCovered, cbJoinedAdjacent, cbEnroachedAdjacent,  cbMinorStructural, cbMajorStructural, cbExtentConstructionWithoutMap,
            cbExtentExtraCoverageWithin, cbExtentExtraCoverageOut, cbExtentGroundCoverage, cbExtentMinorCondonable, cbExtentCondonableStructural,
            cbExtentNonCondonableStructural, cbNoInfoAvailable, cbNoInfoAvail;
    int cbItIsAFlatCheck, cbPropertyWasLockedCheck, cbOwnerPossesseeCheck, cbNpaPropertyCheck, cbVeryLargeIrregularCheck, cbAnyOtherReasonCheck,
            cbConstructionDoneWithoutMapCheck, cbConstructionNotAsPerCheck, cbExtraCoveredCheck, cbJoinedAdjacentCheck, cbEnroachedAdjacentCheck,
            cbMinorStructuralCheck, cbMajorStructuralCheck, cbExtentConstructionWithoutMapCheck, cbExtentExtraCoverageWithinCheck, cbExtentExtraCoverageOutCheck,
            cbExtentGroundCoverageCheck, cbExtentMinorCondonableCheck, cbExtentCondonableStructuralCheck, cbExtentNonCondonableStructuralCheck, cbNoInfoAvailableCheck, cbNoInfoAvailCheck;
    //RadioButton
    RadioButton  rbApplicable2;
    ImageView ivRegularizationCerti;
    String image_status = "0";
    //Uri
    Uri picUri, fileUri;
    //Bitmap
    Bitmap bitmap;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    String regImage = "";

    //ProgressBar progressbar;
    SpinnerAdapter spinnerAdapter;

    ScrollView scrollView;

    //  TextView tv_caseheader,tv_caseid,tv_header;

    // RelativeLayout rl_casedetail;

    TextView tv1, tv2, tv3, tv4;


    View v;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_msform6, container, false);

        getid(v);
        condition();
        setListener();
        getPermission();
        populateSpinner();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        if (!pref.get(Constants.land_area_as_deed).equalsIgnoreCase("not_selected") &&
                !pref.get(Constants.land_area_as_deed).equalsIgnoreCase("not applicable")) {
            etLandAreaAsPerTitle.setText(pref.get(Constants.land_area_as_deed));
        }

        if (!pref.get(Constants.land_area_as_map).equalsIgnoreCase("not_selected") &&
                !pref.get(Constants.land_area_as_map).equalsIgnoreCase("not applicable")) {
            etLandAreaAsPerMap.setText(pref.get(Constants.land_area_as_map));
        }*/
//        if (Double.parseDouble(pref.get(Constants.titleDiffPercent)) == 0.0) {
//            tvIncaseDifferenceTitle.setVisibility(View.GONE);
//            ;
//        } else {
//            tvIncaseDifferenceTitle.setText("Area(Title Deed) has difference of " + pref.get(Constants.titleDiffPercent) + " %");
//            tvIncaseDifferenceTitle.setVisibility(View.VISIBLE);
//        }
//        if (Double.parseDouble(pref.get(Constants.mapDiffPercent)) == 0.0) {
//            tvIncaseDifferenceMap.setVisibility(View.GONE);
//            ;
//        } else {
//            tvIncaseDifferenceMap.setText("Area(Map Area) has difference of " + pref.get(Constants.mapDiffPercent) + " %");
//            tvIncaseDifferenceMap.setVisibility(View.VISIBLE);
//        }


        if (!pref.get(Constants.land_area_as_deed_covered).equalsIgnoreCase("not_selected") &&
                !pref.get(Constants.land_area_as_deed_covered).equalsIgnoreCase("not applicable")) {
            etCoveredAreaAsPerTitle.setText(pref.get(Constants.land_area_as_deed_covered));
        }
        rgCoveredBuiltup.getChildAt(1).setSelected(true);

        if (!pref.get(Constants.land_area_as_map_covered).equalsIgnoreCase("not_selected") &&
                !pref.get(Constants.land_area_as_map_covered).equalsIgnoreCase("not applicable")) {
            etCoveredAreaAsPerMap.setText(pref.get(Constants.land_area_as_map_covered));
        }

        if (Double.parseDouble(pref.get(Constants.covtitleDiffPercent)) < 5) {
            tvInCasediffCoverTitle.setVisibility(View.GONE);

        } else {
            tvInCasediffCoverTitle.setText("Area(Title Deed) has difference of " + pref.get(Constants.covtitleDiffPercent) + " %");
            tvInCasediffCoverTitle.setVisibility(View.VISIBLE);
        }
        if (Double.parseDouble(pref.get(Constants.covmapDiffPercent)) <5) {
            tvInCasediffCoverMap.setVisibility(View.GONE);

        } else {
            tvInCasediffCoverMap.setText("Area(Map Area) has difference of " + pref.get(Constants.covmapDiffPercent) + " %");
            tvInCasediffCoverMap.setVisibility(View.VISIBLE);
        }

        etInCaseOfDifferenceOf2.setText(pref.get(Constants.covdiffReason));
        setDefaults();
        if (!edit_page) {
            setEditiblity();
        }
        return v;
    }
    public void setDefaults(){
        Log.v("MyListedVal",pref.get(Constants.etCoveredAreaEast));
        Log.v("MyListedVal",pref.get(Constants.coveredAreaVal));
        Log.v("MyListedVal",pageVisited);
        if(!pageVisited.equalsIgnoreCase("Msform6")){
//            etCoveredAreaEast.setText(pref.get(Constants.etCoveredAreaEast));
            Log.v("MyListedVal",pref.get(Constants.etCoveredAreaEast));
//            etCoveredAreaWest.setText(pref.get(Constants.etCoveredAreaWest));
//            etCoveredAreaNorth.setText(pref.get(Constants.etCoveredAreaNorth));
//            etCoveredAreaSouth.setText(pref.get(Constants.etCoveredAreaSouth));
//            etCoveredFrontsetback.setText(pref.get(Constants.etCoveredFrontsetback));
//            etCoveredBackSetback.setText(pref.get(Constants.etCoveredBackSetback));
//            etCoveredRightSideSetback.setText(pref.get(Constants.etCoveredRightSideSetback));
//            etCoveredLeftSideSetback.setText(pref.get(Constants.etCoveredLeftSideSetback));
//            etInCaseOfDifferenceOf.setText(pref.get(Constants.etInCaseOfDifferenceOf2));
            etGroundCoverage.setText(pref.get(Constants.etGroundCoverage));
            etInCaseOfDifferenceOf2.setText(pref.get(Constants.etInCaseOfDifferenceOf2));
            etCoveredAreaAsPerMap.setText(pref.get(Constants.etCoveredAreaAsPerMap));
            etCoveredAreaAsPerTitle.setText(pref.get(Constants.etCoveredAreaAsPerTitle));
            etCoveredAreaAsSiteSurvey.setText(pref.get(Constants.etCoveredAreaAsSiteSurvey));

            if (pref.get(Constants.coveredAreaVal).equals("Applicable")) {
                ((RadioButton) rgCoveredBuiltup.getChildAt(0)).setChecked(true);
                llCoveredBuiltup2.setVisibility(View.VISIBLE);
            } else if (pref.get(Constants.coveredAreaVal).equals("Not Applicable")) {
                ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                llCoveredBuiltup2.setVisibility(View.GONE);
            }

          /*  if (pref.get(Constants.landAreaVal).equals("Applicable")) {
                ((RadioButton) rgLandArea.getChildAt(0)).setChecked(true);
                llLandArea2.setVisibility(View.VISIBLE);
            } else if (pref.get(Constants.landAreaVal).equals("Not Applicable")) {
                ((RadioButton) rgLandArea.getChildAt(1)).setChecked(true);
                llLandArea2.setVisibility(View.GONE);
            }*/
        }
    }
    public void setEditiblity() {
        //RadioGroup
        // rgCoveredBuiltup, rgPropertyMeasurement,rgLandAreaOptions;
/*
        for (int i = 0; i < rgLandArea.getChildCount(); i++)
            rgLandArea.getChildAt(i).setClickable(false);
        */
        for (int i = 0; i < rgCoveredBuiltup.getChildCount(); i++)
            rgCoveredBuiltup.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgPropertyMeasurement.getChildCount(); i++)
            rgPropertyMeasurement.getChildAt(i).setClickable(false);
        /*
        for (int i = 0; i < rgLandAreaOptions.getChildCount(); i++)
            rgLandAreaOptions.getChildAt(i).setClickable(false);
*/
/*

        etLandAreaAsPerTitle.setEnabled(false);
        etLandAreaAsPerMap.setEnabled(false);
        etLandAreaAsSiteSurvey.setEnabled(false);
*/

//        etInCaseOfDifferenceOf.setEnabled(false);
        et_detail.setEnabled(false);
        etGroundCoverage.setEnabled(false);
        etInCaseOfDifferenceOf2.setEnabled(false);
        etCoveredAreaAsPerTitle.setEnabled(false);
        etCoveredAreaAsPerMap.setEnabled(false);
        etCoveredAreaAsSiteSurvey.setEnabled(false);
        /*etCoveredAreaEast.setEnabled(false);
        etCoveredAreaWest.setEnabled(false);
        etCoveredAreaNorth.setEnabled(false);
        etCoveredAreaSouth.setEnabled(false);

        etCoveredFrontsetback.setEnabled(false);
        etCoveredBackSetback.setEnabled(false);
        etCoveredRightSideSetback.setEnabled(false);
        etCoveredLeftSideSetback.setEnabled(false);
*/
     /*   etLandEastDocument.setEnabled(false);
        etLandWestDocument.setEnabled(false);
        etLandNorthDocument.setEnabled(false);
        etLandSouthDocument.setEnabled(false);
        etLandEastSurvey.setEnabled(false);
        etLandWestSurvey.setEnabled(false);
        etLandNorthSurvey.setEnabled(false);
        etLandSouthSurvey.setEnabled(false);
        rlEast.setEnabled(false);*/
        spinnerIsThisBeingRegularized.setEnabled(false);
        spinnerDisplayUnitCovered.setEnabled(false);


        cbItIsAFlat.setClickable(false);
        cbPropertyWasLocked.setClickable(false);
        cbOwnerPossessee.setClickable(false);
        cbNpaProperty.setClickable(false);
        cbVeryLargeIrregular.setClickable(false);
        cbAnyOtherReason.setClickable(false);
        cbConstructionDoneWithoutMap.setClickable(false);
//        cbAnyLandNotCLear.setClickable(false);

        cbConstructionNotAsPer.setClickable(false);
        cbExtraCovered.setClickable(false);
        cbJoinedAdjacent.setClickable(false);
        cbEnroachedAdjacent.setClickable(false);
        /*cbSetbackPortion.setClickable(false);*/
        cbMinorStructural.setClickable(false);
        cbMajorStructural.setClickable(false);
        cbExtentConstructionWithoutMap.setClickable(false);

        cbExtentExtraCoverageWithin.setClickable(false);
        cbExtentExtraCoverageOut.setClickable(false);
        cbExtentGroundCoverage.setClickable(false);
        cbExtentMinorCondonable.setClickable(false);
        cbExtentCondonableStructural.setClickable(false);

        cbExtentNonCondonableStructural.setClickable(false);
        cbNoInfoAvailable.setClickable(false);
        cbNoInfoAvail.setClickable(false);
        /*rbApplicable1.setClickable(false);*/
        rbApplicable2.setClickable(false);
    /*    cbDetail4.setClickable(false);
        cb_one.setClickable(false);
        cb_two.setClickable(false);
    */    ivRegularizationCerti.setEnabled(false);


    }

    public void getPermission() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            Log.d("Permission", "Displaying permission rationale to provide additional context.");

            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, REQUEST_PERMISSIONS);
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, REQUEST_PERMISSIONS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    public void getid(View v) {
//        tv_header = findViewById(R.id.tv_header);
//        rl_casedetail = findViewById(R.id.rl_casedetail);
//        tv_caseheader = findViewById(R.id.tv_caseheader);
//        tv_caseid = findViewById(R.id.tv_caseid);
/*
        etLandEastDocument = v.findViewById(R.id.etLandEastDocument);
        etLandWestDocument = v.findViewById(R.id.etLandWestDocument);
        etLandNorthDocument = v.findViewById(R.id.etLandNorthDocument);
        etLandSouthDocument = v.findViewById(R.id.etLandSouthDocument);
        etLandEastSurvey = v.findViewById(R.id.etLandEastSurvey);
        etLandWestSurvey = v.findViewById(R.id.etLandWestSurvey);
        etLandNorthSurvey = v.findViewById(R.id.etLandNorthSurvey);
        etLandSouthSurvey = v.findViewById(R.id.etLandSouthSurvey);*/

        scrollView = v.findViewById(R.id.scrollView);

        //  progressbar = (ProgressBar) v.findViewById(R.id.Progress);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        //  back = v.findViewById(R.id.rl_back);
        next = v.findViewById(R.id.next);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);
        //   dots = (RelativeLayout) v.findViewById(R.id.rl_dots);

        //RadioGroup
        rgCoveredBuiltup = v.findViewById(R.id.rgCoveredBuiltup);
        rgPropertyMeasurement = v.findViewById(R.id.rgPropertyMeasurement);
       /* rgLandAreaOptions = v.findViewById(R.id.rgLandAreaOptions);
        rgLandArea = v.findViewById(R.id.rgLandArea);
*/
        //LinearLayout
        llCoveredBuiltup = v.findViewById(R.id.llCoveredBuiltup);
        llRegularizationCerti = v.findViewById(R.id.llRegularizationCerti);
        llCoveredBuiltup2 = v.findViewById(R.id.llCoveredBuiltup2);
        llReasonNoMeasurement = v.findViewById(R.id.llReasonNoMeasurement);
     /*   llLandArea2 = v.findViewById(R.id.llLandArea2);
        llLandArea = v.findViewById(R.id.llLandArea);
*/
        //EditText
        tvInCasediffCoverMap = v.findViewById(R.id.tvInCasediffCoverMap);
        tvInCasediffCoverTitle = v.findViewById(R.id.tvInCasediffCoverTitle);

        etCoveredAreaAsPerTitle = v.findViewById(R.id.etCoveredAreaAsPerTitle);
        etCoveredAreaAsPerMap = v.findViewById(R.id.etCoveredAreaAsPerMap);
        etCoveredAreaAsSiteSurvey = v.findViewById(R.id.etCoveredAreaAsSiteSurvey);
       /* etCoveredAreaEast = v.findViewById(R.id.etCoveredAreaEast);
        etCoveredAreaWest = v.findViewById(R.id.etCoveredAreaWest);
        etCoveredAreaNorth = v.findViewById(R.id.etCoveredAreaNorth);
        etCoveredAreaSouth = v.findViewById(R.id.etCoveredAreaSouth);
        etCoveredFrontsetback = v.findViewById(R.id.etCoveredFrontsetback);
        etCoveredBackSetback = v.findViewById(R.id.etCoveredBackSetback);
        etCoveredRightSideSetback = v.findViewById(R.id.etCoveredRightSideSetback);
        etCoveredLeftSideSetback = v.findViewById(R.id.etCoveredLeftSideSetback);*/
      /*  etLandAreaAsSiteSurvey.setText(pref.get(Constants.land_area_as_site_survey));
        etLandAreaAsPerTitle = v.findViewById(R.id.etLandAreaAsPerTitle);
        etLandAreaAsPerMap = v.findViewById(R.id.etLandAreaAsPerMap);
        etLandAreaAsSiteSurvey = v.findViewById(R.id.etLandAreaAsSiteSurvey);
      */  et_detail = v.findViewById(R.id.et_detail);
//        etInCaseOfDifferenceOf = v.findViewById(R.id.etInCaseOfDifferenceOf);
//        etInCaseOfDifferenceOf.setText(pref.get(Constants.diffReason));
        etGroundCoverage = v.findViewById(R.id.etGroundCoverage);
        etInCaseOfDifferenceOf2 = v.findViewById(R.id.etInCaseOfDifferenceOf2);

        //RelativeLayout
//        rlLandAreaEast = v.findViewById(R.id.rlLandAreaEast);
//        rlLandAreaWest = v.findViewById(R.id.rlLandAreaWest);
//        rlLandAreaNorth = v.findViewById(R.id.rlLandAreaNorth);
//        rlLandAreaSouth = v.findViewById(R.id.rlLandAreaSouth);
//        rlLandAreaAsPerTitle = v.findViewById(R.id.rlLandAreaAsPerTitle);
//        rlLandAreaAsPerMap = v.findViewById(R.id.rlLandAreaAsPerMap);
//        rlLandAreaAsSiteSurvey = v.findViewById(R.id.rlLandAreaAsSiteSurvey);
    /*    rl_vacantSub = v.findViewById(R.id.rl_vacantSub);
*/
        rlCoveredAreaAsPerTitle = v.findViewById(R.id.rlCoveredAreaAsPerTitle);
        rlCoveredAreaAsPerMap = v.findViewById(R.id.rlCoveredAreaAsPerMap);
        rlCoveredAreaAsSiteSurvey = v.findViewById(R.id.rlCoveredAreaAsSiteSurvey);
        /*rlCoveredAreaEast = v.findViewById(R.id.rlCoveredAreaEast);
        rlCoveredAreaWest = v.findViewById(R.id.rlCoveredAreaWest);
        rlCoveredAreaNorth = v.findViewById(R.id.rlCoveredAreaNorth);
        rlCoveredAreaSouth = v.findViewById(R.id.rlCoveredAreaSouth);
        rlCoveredFrontsetback = v.findViewById(R.id.rlCoveredFrontsetback);
        rlCoveredBackSetback = v.findViewById(R.id.rlCoveredBackSetback);
        rlCoveredRightSideSetback = v.findViewById(R.id.rlCoveredRightSideSetback);
        rlCoveredLeftSideSetback = v.findViewById(R.id.rlCoveredLeftSideSetback);*/

        //Spinner
        spinnerIsThisBeingRegularized = v.findViewById(R.id.spinnerIsThisBeingRegularized);
        spinnerDisplayUnitCovered = v.findViewById(R.id.spinnerDisplayUnitCovered);
       /* spinnerDisplayUnit = v.findViewById(R.id.spinnerDisplayUnit);
*/
        /*rbApplicable1 = v.findViewById(R.id.rbApplicable1);*/
        rbApplicable2 = v.findViewById(R.id.rbApplicable2);

        //CheckBox
        cbItIsAFlat = v.findViewById(R.id.cbItIsAFlat);
        cbPropertyWasLocked = v.findViewById(R.id.cbPropertyWasLocked);
        cbOwnerPossessee = v.findViewById(R.id.cbOwnerPossessee);
        cbNpaProperty = v.findViewById(R.id.cbNpaProperty);
//        cbAnyLandNotCLear = v.findViewById(R.id.cbAnyLandNotCLear);
        cbVeryLargeIrregular = v.findViewById(R.id.cbVeryLargeIrregular);
        cbAnyOtherReason = v.findViewById(R.id.cbAnyOtherReason);
        cbConstructionDoneWithoutMap = v.findViewById(R.id.cbConstructionDoneWithoutMap);
        cbConstructionNotAsPer = v.findViewById(R.id.cbConstructionNotAsPer);
        cbExtraCovered = v.findViewById(R.id.cbExtraCovered);
        cbJoinedAdjacent = v.findViewById(R.id.cbJoinedAdjacent);
        cbEnroachedAdjacent = v.findViewById(R.id.cbEnroachedAdjacent);
        /*cbSetbackPortion = v.findViewById(R.id.cbSetbackPortion);*/
        cbMinorStructural = v.findViewById(R.id.cbMinorStructural);
        cbMajorStructural = v.findViewById(R.id.cbMajorStructural);
        cbExtentConstructionWithoutMap = v.findViewById(R.id.cbExtentConstructionWithoutMap);
        cbExtentExtraCoverageWithin = v.findViewById(R.id.cbExtentExtraCoverageWithin);
        cbExtentExtraCoverageOut = v.findViewById(R.id.cbExtentExtraCoverageOut);
        cbExtentGroundCoverage = v.findViewById(R.id.cbExtentGroundCoverage);
        cbExtentMinorCondonable = v.findViewById(R.id.cbExtentMinorCondonable);
        cbExtentCondonableStructural = v.findViewById(R.id.cbExtentCondonableStructural);
        cbExtentNonCondonableStructural = v.findViewById(R.id.cbExtentNonCondonableStructural);
        cbNoInfoAvailable = v.findViewById(R.id.cbNoInfoAvailable);
        cbNoInfoAvail = v.findViewById(R.id.cbNoInfoAvail);

        if (InitiateSurveyForm.surveyTypeCheck == 1) {
            cbNoInfoAvail.setChecked(true);
            cbNoInfoAvailable.setChecked(true);
        }

//        cbDetail1 = v.findViewById(R.id.cbDetail1);
//        cbDetail2 = v.findViewById(R.id.cbDetail2);
//        cbDetail3 = v.findViewById(R.id.cbDetail3);
    /*    cbDetail4 = v.findViewById(R.id.cbDetail4);
        cb_one = v.findViewById(R.id.cb_one);
        cb_two = v.findViewById(R.id.cb_two);
*/
        ivRegularizationCerti = v.findViewById(R.id.ivRegularizationCerti);
        tvRegularizationCerti = v.findViewById(R.id.tvRegularizationCerti);
        /*tvIncaseDifferenceTitle = v.findViewById(R.id.tvInCasedifferenceTitle1);
        tvIncaseDifferenceMap = v.findViewById(R.id.tvInCasedifferenceMap1);*/

//        tvLandArea = v.findViewById(R.id.tvLandArea);

        tvInCaseHeading = v.findViewById(R.id.tvInCaseHeading);
        //tvGroundCoverageHeading = v.findViewById(R.id.tvGroundCoverageHeading);
        tvCoveredHeading = v.findViewById(R.id.tvCoveredHeading);
        //   tvInCase2Heading = v.findViewById(R.id.tvInCase2Heading);
        tvNatureHeading = v.findViewById(R.id.tvNatureHeading);
        tvExtentHeading = v.findViewById(R.id.tvExtentHeading);
        tvIsThisHeading = v.findViewById(R.id.tvIsThisHeading);
        tvDifference = v.findViewById(R.id.tvDifference);
//        tvDrawPlotLines = v.findViewById(R.id.tvDrawPlotLines);
//        tvMeasureDistance = v.findViewById(R.id.tvMeasureDistance);

        tv1 = v.findViewById(R.id.tv1);
        tv2 = v.findViewById(R.id.tv2);
        tv3 = v.findViewById(R.id.tv3);
        tv4 = v.findViewById(R.id.tv4);
    }

    public void setListener() {
        //    back.setOnClickListener(this);
        next.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        //     dots.setOnClickListener(this);
        ivRegularizationCerti.setOnClickListener(this);
//        tvDrawPlotLines.setOnClickListener(this);
//        tvMeasureDistance.setOnClickListener(this);

        rgPropertyMeasurement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int pos = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (pos == 0) {
                        llReasonNoMeasurement.setVisibility(View.GONE);
//                        tvLandArea.setText(R.string.c_land_area2);
                        tvInCaseHeading.setText(R.string.d_in_case_of_difference);
                        // tvGroundCoverageHeading.setText(R.string.e_ground_coverage);
                        tvCoveredHeading.setText(R.string.f_covered_built_up_area);
                        // tvInCase2Heading.setText(R.string.g_in_case_of_difference_of);
                        tvNatureHeading.setText(R.string.h_nature_of_violation_done_in_the_property);
                        tvExtentHeading.setText(R.string.i_extent_of_violation);
                        tvIsThisHeading.setText(R.string.j_is_this_being_regularized);

                    } else if (pos == 1) {
                        llReasonNoMeasurement.setVisibility(View.GONE);
//                        tvLandArea.setText(R.string.c_land_area2);
                        tvInCaseHeading.setText(R.string.d_in_case_of_difference);
                        //   tvGroundCoverageHeading.setText(R.string.e_ground_coverage);
                        tvCoveredHeading.setText(R.string.f_covered_built_up_area);
                        //  tvInCase2Heading.setText(R.string.g_in_case_of_difference_of);
                        tvNatureHeading.setText(R.string.h_nature_of_violation_done_in_the_property);
                        tvExtentHeading.setText(R.string.i_extent_of_violation);
                        tvIsThisHeading.setText(R.string.j_is_this_being_regularized);

                    } else if (pos == 2) {
                        llReasonNoMeasurement.setVisibility(View.GONE);
//                        tvLandArea.setText(R.string.c_land_area2);
                        tvInCaseHeading.setText(R.string.d_in_case_of_difference);
                        // tvGroundCoverageHeading.setText(R.string.e_ground_coverage);
                        tvCoveredHeading.setText(R.string.f_covered_built_up_area);
                        //tvInCase2Heading.setText(R.string.g_in_case_of_difference_of);
                        tvNatureHeading.setText(R.string.h_nature_of_violation_done_in_the_property);
                        tvExtentHeading.setText(R.string.i_extent_of_violation);
                        tvIsThisHeading.setText(R.string.j_is_this_being_regularized);

                    } else {
                        llReasonNoMeasurement.setVisibility(View.VISIBLE);
//                        tvLandArea.setText(R.string.c_land_area);
                        tvInCaseHeading.setText(R.string.d_in_case_of_difference_);
                        // tvGroundCoverageHeading.setText(R.string.e_ground_coverage_);
                        tvCoveredHeading.setText(R.string.f_covered_built_up_area_);
                        // tvInCase2Heading.setText(R.string.g_in_case_of_difference_of_);
                        tvNatureHeading.setText(R.string.h_nature_of_violation_done_in_the_property_);
                        tvExtentHeading.setText(R.string.i_extent_of_violation_);
                        tvIsThisHeading.setText(R.string.j_is_this_being_regularized_);
                    }
                }

            }
        });

        cbAnyOtherReason.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (String.valueOf(b).equals("true")) {
                    et_detail.setVisibility(View.VISIBLE);
                } else {
                    et_detail.setVisibility(View.GONE);
                }
            }
        });

        try {
            ((RadioButton) rgPropertyMeasurement.getChildAt(InitiateSurveyForm.propertyMeasurementIndex)).setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (InitiateSurveyForm.cbItIsAFlatChecked == 1) {
            cbItIsAFlat.setChecked(true);
        }
        if (InitiateSurveyForm.cbPropertyWasLockedChecked == 1) {
            cbPropertyWasLocked.setChecked(true);
        }
        if (InitiateSurveyForm.cbOwnerPossesseeChecked == 1) {
            cbOwnerPossessee.setChecked(true);
        }
        if (InitiateSurveyForm.cbNpaPropertyChecked == 1) {
            cbNpaProperty.setChecked(true);
        } /* if (InitiateSurveyForm.cbNpaPropertyChecked == 1) {
            cbAnyLandNotCLear.setChecked(true);
        }*/
        if (InitiateSurveyForm.cbVeryLargeIrregularChecked == 1) {
            cbVeryLargeIrregular.setChecked(true);
        }
        if (InitiateSurveyForm.cbAnyOtherReasonChecked == 1) {
            cbAnyOtherReason.setChecked(true);
            et_detail.setText(InitiateSurveyForm.cbAnyOtherReasonValue);
        }
/*

        rgLandAreaOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int pos = radioGroup.indexOfChild(radioButton);

                tv1.setText(getString(R.string.as_per_site_survey));
                tv2.setText(getString(R.string.as_per_site_survey));
                tv3.setText(getString(R.string.as_per_site_survey));
                tv4.setText(getString(R.string.as_per_site_survey));

                if (pos == 0) {
                    rl_vacantSub.setVisibility(View.GONE);
                    //etLandAreaAsSiteSurvey.setText(radioButton.getText().toString());

//                    etLandEastSurvey.setText(radioButton.getText().toString());
//                    etLandWestSurvey.setText(radioButton.getText().toString());
//                    etLandNorthSurvey.setText(radioButton.getText().toString());
//                    etLandSouthSurvey.setText(radioButton.getText().toString());

                    etLandEastSurvey.setText("");
                    etLandWestSurvey.setText("");
                    etLandNorthSurvey.setText("");
                    etLandSouthSurvey.setText("");

                    etLandEastSurvey.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    etLandWestSurvey.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    etLandNorthSurvey.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    etLandSouthSurvey.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    etLandEastSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandWestSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandNorthSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandSouthSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));

                    etLandEastSurvey.setSelection(etLandEastSurvey.getText().length());
                    etLandWestSurvey.setSelection(etLandWestSurvey.getText().length());
                    etLandNorthSurvey.setSelection(etLandNorthSurvey.getText().length());
                    etLandSouthSurvey.setSelection(etLandSouthSurvey.getText().length());

                } else if (pos == 1) {
                    rl_vacantSub.setVisibility(View.GONE);
                    // etLandAreaAsSiteSurvey.setText(radioButton.getText().toString());
                    etLandEastSurvey.setText(radioButton.getText().toString());
                    etLandWestSurvey.setText(radioButton.getText().toString());
                    etLandNorthSurvey.setText(radioButton.getText().toString());
                    etLandSouthSurvey.setText(radioButton.getText().toString());

                    etLandEastSurvey.setInputType(InputType.TYPE_CLASS_TEXT);
                    etLandWestSurvey.setInputType(InputType.TYPE_CLASS_TEXT);
                    etLandNorthSurvey.setInputType(InputType.TYPE_CLASS_TEXT);
                    etLandSouthSurvey.setInputType(InputType.TYPE_CLASS_TEXT);

                    etLandEastSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandWestSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandNorthSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandSouthSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));

                    etLandEastSurvey.setSelection(etLandEastSurvey.getText().length());
                    etLandWestSurvey.setSelection(etLandWestSurvey.getText().length());
                    etLandNorthSurvey.setSelection(etLandNorthSurvey.getText().length());
                    etLandSouthSurvey.setSelection(etLandSouthSurvey.getText().length());

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, tvMeasureDistance.getTop());
                        }
                    });

                } else if (pos == 2) {
                    rl_vacantSub.setVisibility(View.VISIBLE);
                    // etLandAreaAsSiteSurvey.setText(radioButton.getText().toString());
                    etLandEastSurvey.setText(radioButton.getText().toString());
                    etLandWestSurvey.setText(radioButton.getText().toString());
                    etLandNorthSurvey.setText(radioButton.getText().toString());
                    etLandSouthSurvey.setText(radioButton.getText().toString());

                    etLandEastSurvey.setInputType(InputType.TYPE_CLASS_TEXT);
                    etLandWestSurvey.setInputType(InputType.TYPE_CLASS_TEXT);
                    etLandNorthSurvey.setInputType(InputType.TYPE_CLASS_TEXT);
                    etLandSouthSurvey.setInputType(InputType.TYPE_CLASS_TEXT);

                    etLandEastSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandWestSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandNorthSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
                    etLandSouthSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));

                    etLandEastSurvey.setSelection(etLandEastSurvey.getText().length());
                    etLandWestSurvey.setSelection(etLandWestSurvey.getText().length());
                    etLandNorthSurvey.setSelection(etLandNorthSurvey.getText().length());
                    etLandSouthSurvey.setSelection(etLandSouthSurvey.getText().length());

                    tv1.setText(getString(R.string.landArea3));
                    tv2.setText(getString(R.string.landArea3));
                    tv3.setText(getString(R.string.landArea3));
                    tv4.setText(getString(R.string.landArea3));
                }
            }
        });

        cbDetail4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etLandEastDocument.setText(cbDetail4.getText().toString());
                    etLandWestDocument.setText(cbDetail4.getText().toString());
                    etLandNorthDocument.setText(cbDetail4.getText().toString());
                    etLandSouthDocument.setText(cbDetail4.getText().toString());
                } else {

                    etLandEastDocument.setText("");
                    etLandWestDocument.setText("");
                    etLandNorthDocument.setText("");
                    etLandSouthDocument.setText("");
                }
            }
        });


        cb_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                setHeading();
            }
        });

        cb_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                setHeading();
            }
        });

        etLandEastSurvey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                calculationArea();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        etLandAreaAsPerTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Difference % between document & site survey
                //        (x-y)/y*(100)
                //(x-z)/z*(100)

                float val1, val2;
                float diff;

                if (TextUtils.isDigitsOnly(charSequence)
                        && TextUtils.isDigitsOnly(etLandAreaAsPerMap.getText().toString())
                        && TextUtils.isDigitsOnly(etLandAreaAsSiteSurvey.getText().toString())
                        && !etLandAreaAsPerTitle.getText().toString().isEmpty()
                        && !etLandAreaAsPerMap.getText().toString().isEmpty()
                        && !etLandAreaAsSiteSurvey.getText().toString().isEmpty()) {

                    float x = Integer.parseInt(etLandAreaAsPerTitle.getText().toString());
                    float y = Integer.parseInt(etLandAreaAsPerMap.getText().toString());
                    float z = Integer.parseInt(etLandAreaAsSiteSurvey.getText().toString());

                    val1 = (x - y) / y * (100);
                    val2 = (x - z) / z * (100);

                    diff = val1 - val2;

                    if (diff > 5) {
                        tvDifference.setVisibility(View.VISIBLE);

                        tvDifference.setText("Difference % between document & site survey: " + String.format("%.2f", diff));
                    } else {
                        tvDifference.setVisibility(View.GONE);
                    }


                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etLandAreaAsPerMap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                float val1, val2;
                float diff;

                if (TextUtils.isDigitsOnly(charSequence)
                        && TextUtils.isDigitsOnly(etLandAreaAsPerTitle.getText().toString())
                        && TextUtils.isDigitsOnly(etLandAreaAsSiteSurvey.getText().toString())
                        && !etLandAreaAsPerTitle.getText().toString().isEmpty()
                        && !etLandAreaAsPerMap.getText().toString().isEmpty()
                        && !etLandAreaAsSiteSurvey.getText().toString().isEmpty()) {

                    float x = Integer.parseInt(etLandAreaAsPerTitle.getText().toString());
                    float y = Integer.parseInt(etLandAreaAsPerMap.getText().toString());
                    float z = Integer.parseInt(etLandAreaAsSiteSurvey.getText().toString());

                    val1 = (x - y) / y * (100);
                    val2 = (x - z) / z * (100);

                    diff = val1 - val2;

                    if (diff > 5) {
                        tvDifference.setVisibility(View.VISIBLE);

                        tvDifference.setText("Difference % between document & site survey: " + String.format("%.2f", diff));
                    } else {
                        tvDifference.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etLandAreaAsSiteSurvey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                float val1, val2;
                float diff;

                if (TextUtils.isDigitsOnly(charSequence)
                        && TextUtils.isDigitsOnly(etLandAreaAsPerTitle.getText().toString())
                        && TextUtils.isDigitsOnly(etLandAreaAsPerMap.getText().toString())
                        && !etLandAreaAsPerTitle.getText().toString().isEmpty()
                        && !etLandAreaAsPerMap.getText().toString().isEmpty()
                        && !etLandAreaAsSiteSurvey.getText().toString().isEmpty()) {

                    float x = Integer.parseInt(etLandAreaAsPerTitle.getText().toString());
                    float y = Integer.parseInt(etLandAreaAsPerMap.getText().toString());
                    float z = Integer.parseInt(etLandAreaAsSiteSurvey.getText().toString());

                    try {

                        val1 = (x - y) / y * (100);
                        val2 = (x - z) / z * (100);

                        diff = val1 - val2;

                        if (diff > 5) {
                            tvDifference.setVisibility(View.VISIBLE);


                            tvDifference.setText("Difference % between document & site survey: " + String.format("%.2f", diff));
                        } else {
                            tvDifference.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/

    }

    private void setHeading() {

    /*    etLandEastSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
        etLandWestSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
        etLandNorthSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));
        etLandSouthSurvey.setMinimumHeight((int) getResources().getDimension(R.dimen._30sdp));

        if (cb_one.isChecked() && cb_two.isChecked()) {
            etLandEastSurvey.setText(cb_one.getText().toString() + "\n" + cb_two.getText().toString().trim());
            etLandWestSurvey.setText(cb_one.getText().toString() + "\n" + cb_two.getText().toString().trim());
            etLandNorthSurvey.setText(cb_one.getText().toString() + "\n" + cb_two.getText().toString().trim());
            etLandSouthSurvey.setText(cb_one.getText().toString() + "\n" + cb_two.getText().toString().trim());
        } else if (cb_one.isChecked()) {
            etLandEastSurvey.setText(cb_one.getText().toString());
            etLandWestSurvey.setText(cb_one.getText().toString());
            etLandNorthSurvey.setText(cb_one.getText().toString());
            etLandSouthSurvey.setText(cb_one.getText().toString());
        } else if (cb_two.isChecked()) {
            etLandEastSurvey.setText(cb_two.getText().toString());
            etLandWestSurvey.setText(cb_two.getText().toString());
            etLandNorthSurvey.setText(cb_two.getText().toString());
            etLandSouthSurvey.setText(cb_two.getText().toString());
        } else {
            etLandEastSurvey.setText("");
            etLandWestSurvey.setText("");
            etLandNorthSurvey.setText("");
            etLandSouthSurvey.setText("");
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
          /*  case R.id.rl_back:
                onBackPressed();
                break;*/

            case R.id.tvPrevious:
               /* intent = new Intent(GeneralForm6.this, GeneralForm5.class);
                startActivity(intent);*/
                // ((Dashboard)mActivity).displayView(10);
                mActivity.onBackPressed();
                break;

            case R.id.next:
                if (edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(38);
                break;

            case R.id.ivRegularizationCerti:
                image_status = "1";
                showCameraGalleryDialog();
                break;

            case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;

//            case R.id.tvDrawPlotLines:
//
//                startActivity(new Intent(mActivity, DrawPlotActivity.class));
//
//                break;
//
//            case R.id.tvMeasureDistance:
//
//                startActivity(new Intent(mActivity, MeasureDistanceActivity.class));
//
//                break;
        }
    }

    private void validation() {
        if (rgPropertyMeasurement.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Property Measurement", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvPropertyMeas);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llReasonNoMeasurement.getVisibility() == View.VISIBLE && !cbItIsAFlat.isChecked()
                && !cbPropertyWasLocked.isChecked()
                && !cbOwnerPossessee.isChecked()
                && !cbNpaProperty.isChecked()
                && !cbVeryLargeIrregular.isChecked()
                && !cbAnyOtherReason.isChecked()) {

            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Reason for No Measurement", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvReasonFor);
            targetView.getParent().requestChildFocus(targetView, targetView);


        } /*else if (rgLandArea.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Land Area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvLandArea);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llLandArea2.getVisibility() == View.VISIBLE && rgLandAreaOptions.getCheckedRadioButtonId() == -1
                && !cbDetail4.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Land Area Options", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.rgLandAreaOptions);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llLandArea2.getVisibility() == View.VISIBLE && etLandEastDocument.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area East: As Per Document", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandEastDocument.requestFocus();

        } else if (llLandArea2.getVisibility() == View.VISIBLE && (rgLandAreaOptions.getCheckedRadioButtonId() != -1) && etLandEastSurvey.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area East: As Per Site Survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandEastSurvey.requestFocus();

        } else if (llLandArea2.getVisibility() == View.VISIBLE && etLandWestDocument.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area West: As Per Document", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandWestDocument.requestFocus();

        } else if (llLandArea2.getVisibility() == View.VISIBLE && (rgLandAreaOptions.getCheckedRadioButtonId() != -1) && etLandWestSurvey.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area West: As Per Site Survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandWestSurvey.requestFocus();

        } else if (llLandArea2.getVisibility() == View.VISIBLE && etLandNorthDocument.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area North: As Per Document", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandNorthDocument.requestFocus();

        } else if (llLandArea2.getVisibility() == View.VISIBLE && (rgLandAreaOptions.getCheckedRadioButtonId() != -1) && etLandNorthSurvey.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area North: As Per Site Survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandNorthSurvey.requestFocus();

        } else if (llLandArea2.getVisibility() == View.VISIBLE && etLandSouthDocument.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area South: As Per Document", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandSouthDocument.requestFocus();

        } else if (llLandArea2.getVisibility() == View.VISIBLE && (rgLandAreaOptions.getCheckedRadioButtonId() != -1) && etLandSouthSurvey.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area South: As Per Site Survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandSouthSurvey.requestFocus();

        } else if (llLandArea2.getVisibility() == View.VISIBLE && etLandAreaAsPerTitle.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area: As Per Title Deed", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandAreaAsPerTitle.requestFocus();
        } else if (llLandArea2.getVisibility() == View.VISIBLE && etLandAreaAsPerMap.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area: As Per Map", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandAreaAsPerMap.requestFocus();
        } else if (llLandArea2.getVisibility() == View.VISIBLE && etLandAreaAsSiteSurvey.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area: As Per Site Survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandAreaAsSiteSurvey.requestFocus();
        } else if (llLandArea2.getVisibility() == View.VISIBLE && etInCaseOfDifferenceOf.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Reason", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etInCaseOfDifferenceOf.requestFocus();
        } else if (rgCoveredBuiltup.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Covered Built-up Area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvCoveredHeading);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredAreaAsPerTitle.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: As per title deed", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredAreaAsPerTitle.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredAreaAsPerMap.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: As per map", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredAreaAsPerMap.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredAreaAsSiteSurvey.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: As per site survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredAreaAsSiteSurvey.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredAreaEast.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: East", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredAreaEast.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredAreaWest.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: West", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredAreaWest.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredAreaNorth.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: North", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredAreaNorth.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredAreaSouth.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: South", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredAreaSouth.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredFrontsetback.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: Front setback", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredFrontsetback.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredBackSetback.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: Back setback", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredBackSetback.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredRightSideSetback.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: Right side setback", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredRightSideSetback.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etCoveredLeftSideSetback.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area: Left side setback", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoveredLeftSideSetback.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etGroundCoverage.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Ground Coverage", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etInCaseOfDifferenceOf2.requestFocus();
        } else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etInCaseOfDifferenceOf2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Difference if applicable", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etInCaseOfDifferenceOf2.requestFocus();
        }*/ else if (!cbConstructionDoneWithoutMap.isChecked()
                && !cbConstructionNotAsPer.isChecked()
                && !cbExtraCovered.isChecked()
                && !cbJoinedAdjacent.isChecked()
                && !cbEnroachedAdjacent.isChecked()
/*
                && !cbSetbackPortion.isChecked()
*/
                && !cbMinorStructural.isChecked()
                && !cbMajorStructural.isChecked()
                && !cbNoInfoAvailable.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Nature of violation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvNatureHeading);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbExtentConstructionWithoutMap.isChecked()
                && !cbExtentExtraCoverageWithin.isChecked()
                && !cbExtentExtraCoverageOut.isChecked()
                && !cbExtentGroundCoverage.isChecked()
                && !cbExtentMinorCondonable.isChecked()
                && !cbExtentCondonableStructural.isChecked()
                && !cbExtentNonCondonableStructural.isChecked()
                && !cbNoInfoAvailable.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Extent of violation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvExtentHeading);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerIsThisBeingRegularized.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvIsThisHeading);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llRegularizationCerti.getVisibility() == View.VISIBLE && tvRegularizationCerti.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Labour License", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvRegularizationCerti, "Please Attach Regularization Certificate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvRegularizationCerti.requestFocus();
        } else {
            putIntoHm();
            ((Dashboard) mActivity).displayView(38);
            return;
        }
    }

    public void putIntoHm() {
//        hm.put("reason1", etInCaseOfDifferenceOf.getText().toString());
        hm.put("groundCoveragee", etGroundCoverage.getText().toString());
        hm.put("reason2", etInCaseOfDifferenceOf2.getText().toString());
        hm.put(Constants.pageVisited, "Msform6");

        if (cbItIsAFlat.isChecked()) {
            cbItIsAFlatCheck = 1;
            hm.put("cbItIsAFlat", String.valueOf(cbItIsAFlatCheck));
        } else {
            cbItIsAFlatCheck = 0;
            hm.put("cbItIsAFlat", String.valueOf(cbItIsAFlatCheck));
        }
        if (cbPropertyWasLocked.isChecked()) {
            cbPropertyWasLockedCheck = 1;
            hm.put("cbPropertyWasLockedd", String.valueOf(cbPropertyWasLockedCheck));
        } else {
            cbPropertyWasLockedCheck = 0;
            hm.put("cbPropertyWasLockedd", String.valueOf(cbPropertyWasLockedCheck));
        }
        if (cbOwnerPossessee.isChecked()) {
            cbOwnerPossesseeCheck = 1;
            hm.put("cbOwnerPossessee", String.valueOf(cbOwnerPossesseeCheck));
        } else {
            cbOwnerPossesseeCheck = 0;
            hm.put("cbOwnerPossessee", String.valueOf(cbOwnerPossesseeCheck));
        }
        if (cbNpaProperty.isChecked()) {
            cbNpaPropertyCheck = 1;
            hm.put("cbNpaProperty", String.valueOf(cbNpaPropertyCheck));
        } else {
            cbNpaPropertyCheck = 0;
            hm.put("cbNpaProperty", String.valueOf(cbNpaPropertyCheck));
        }
//        if (cbAnyLandNotCLear.isChecked()) {
//            cbAnyLandNotCLearCheck = 1;
//            hm.put("cbAnyLandNotCLear", String.valueOf(cbAnyLandNotCLearCheck));
//        } else {
//            cbAnyLandNotCLearCheck = 0;
//            hm.put("cbAnyLandNotCLear", String.valueOf(cbAnyLandNotCLearCheck));
//        }
        if (cbVeryLargeIrregular.isChecked()) {
            cbVeryLargeIrregularCheck = 1;
            hm.put("cbVeryLargeIrregular", String.valueOf(cbVeryLargeIrregularCheck));
        } else {
            cbVeryLargeIrregularCheck = 0;
            hm.put("cbVeryLargeIrregular", String.valueOf(cbVeryLargeIrregularCheck));
        }
        if (cbAnyOtherReason.isChecked()) {
            cbAnyOtherReasonCheck = 1;
            hm.put("cbAnyOtherReasonn", String.valueOf(cbAnyOtherReasonCheck));
        } else {
            cbAnyOtherReasonCheck = 0;
            hm.put("cbAnyOtherReasonn", String.valueOf(cbAnyOtherReasonCheck));
        }
        if (cbConstructionDoneWithoutMap.isChecked()) {
            cbConstructionDoneWithoutMapCheck = 1;
            hm.put("cbConstructionDoneWithoutMapp", String.valueOf(cbConstructionDoneWithoutMapCheck));
        } else {
            cbConstructionDoneWithoutMapCheck = 0;
            hm.put("cbConstructionDoneWithoutMapp", String.valueOf(cbConstructionDoneWithoutMapCheck));
        }
        if (cbConstructionNotAsPer.isChecked()) {
            cbConstructionNotAsPerCheck = 1;
            hm.put("cbConstructionNotAsPer", String.valueOf(cbConstructionNotAsPerCheck));
        } else {
            cbConstructionNotAsPerCheck = 0;
            hm.put("cbConstructionNotAsPer", String.valueOf(cbConstructionNotAsPerCheck));
        }
        if (cbExtraCovered.isChecked()) {
            cbExtraCoveredCheck = 1;
            hm.put("cbExtraCovered", String.valueOf(cbExtraCoveredCheck));
        } else {
            cbExtraCoveredCheck = 0;
            hm.put("cbExtraCovered", String.valueOf(cbExtraCoveredCheck));
        }
        if (cbJoinedAdjacent.isChecked()) {
            cbJoinedAdjacentCheck = 1;
            hm.put("cbJoinedAdjacent", String.valueOf(cbJoinedAdjacentCheck));
        } else {
            cbJoinedAdjacentCheck = 0;
            hm.put("cbJoinedAdjacent", String.valueOf(cbJoinedAdjacentCheck));
        }
        if (cbEnroachedAdjacent.isChecked()) {
            cbEnroachedAdjacentCheck = 1;
            hm.put("cbEnroachedAdjacent", String.valueOf(cbEnroachedAdjacentCheck));
        } else {
            cbEnroachedAdjacentCheck = 0;
            hm.put("cbEnroachedAdjacent", String.valueOf(cbEnroachedAdjacentCheck));
        }
        /*if (cbSetbackPortion.isChecked()) {
            cbSetbackPortionCheck = 1;
            hm.put("cbSetbackPortion", String.valueOf(cbSetbackPortionCheck));
        } else {
            cbSetbackPortionCheck = 0;
            hm.put("cbSetbackPortion", String.valueOf(cbSetbackPortionCheck));
        }*/
        if (cbMinorStructural.isChecked()) {
            cbMinorStructuralCheck = 1;
            hm.put("cbMinorStructural", String.valueOf(cbMinorStructuralCheck));
        } else {
            cbMinorStructuralCheck = 0;
            hm.put("cbMinorStructural", String.valueOf(cbMinorStructuralCheck));
        }
        if (cbMajorStructural.isChecked()) {
            cbMajorStructuralCheck = 1;
            hm.put("cbMajorStructural", String.valueOf(cbMajorStructuralCheck));
        } else {
            cbMajorStructuralCheck = 0;
            hm.put("cbMajorStructural", String.valueOf(cbMajorStructuralCheck));
        }


        if (cbExtentConstructionWithoutMap.isChecked()) {
            cbExtentConstructionWithoutMapCheck = 1;
            hm.put("cbExtentConstructionWithoutMap", String.valueOf(cbExtentConstructionWithoutMapCheck));
        } else {
            cbExtentConstructionWithoutMapCheck = 0;
            hm.put("cbExtentConstructionWithoutMap", String.valueOf(cbExtentConstructionWithoutMapCheck));
        }
        if (cbExtentExtraCoverageWithin.isChecked()) {
            cbExtentExtraCoverageWithinCheck = 1;
            hm.put("cbExtentExtraCoverageWithin", String.valueOf(cbExtentExtraCoverageWithinCheck));
        } else {
            cbExtentExtraCoverageWithinCheck = 0;
            hm.put("cbExtentExtraCoverageWithin", String.valueOf(cbExtentExtraCoverageWithinCheck));
        }
        if (cbExtentExtraCoverageOut.isChecked()) {
            cbExtentExtraCoverageOutCheck = 1;
            hm.put("cbExtentExtraCoverageOut", String.valueOf(cbExtentExtraCoverageOutCheck));
        } else {
            cbExtentExtraCoverageOutCheck = 0;
            hm.put("cbExtentExtraCoverageOut", String.valueOf(cbExtentExtraCoverageOutCheck));
        }
        if (cbExtentGroundCoverage.isChecked()) {
            cbExtentGroundCoverageCheck = 1;
            hm.put("cbExtentGroundCoverage", String.valueOf(cbExtentGroundCoverageCheck));
        } else {
            cbExtentGroundCoverageCheck = 0;
            hm.put("cbExtentGroundCoverage", String.valueOf(cbExtentGroundCoverageCheck));
        }
        if (cbExtentMinorCondonable.isChecked()) {
            cbExtentMinorCondonableCheck = 1;
            hm.put("cbExtentMinorCondonable", String.valueOf(cbExtentMinorCondonableCheck));
        } else {
            cbExtentMinorCondonableCheck = 0;
            hm.put("cbExtentMinorCondonable", String.valueOf(cbExtentMinorCondonableCheck));
        }
        if (cbExtentCondonableStructural.isChecked()) {
            cbExtentCondonableStructuralCheck = 1;
            hm.put("cbExtentCondonableStructural", String.valueOf(cbExtentCondonableStructuralCheck));
        } else {
            cbExtentCondonableStructuralCheck = 0;
            hm.put("cbExtentCondonableStructural", String.valueOf(cbExtentCondonableStructuralCheck));
        }
        if (cbExtentNonCondonableStructural.isChecked()) {
            cbExtentNonCondonableStructuralCheck = 1;
            hm.put("cbExtentNonCondonableStructural", String.valueOf(cbExtentNonCondonableStructuralCheck));
        } else {
            cbExtentNonCondonableStructuralCheck = 0;
            hm.put("cbExtentNonCondonableStructural", String.valueOf(cbExtentNonCondonableStructuralCheck));
        }

        if (cbNoInfoAvailable.isChecked()) {
            cbNoInfoAvailableCheck = 1;
            hm.put("cbNoInfoAvailable", String.valueOf(cbNoInfoAvailableCheck));
        } else {
            cbNoInfoAvailableCheck = 0;
            hm.put("cbNoInfoAvailable", String.valueOf(cbNoInfoAvailableCheck));
        }


        int selectedIdPropertyMeasurement = rgPropertyMeasurement.getCheckedRadioButtonId();
        View radioButtonPropertyMeasurement = v.findViewById(selectedIdPropertyMeasurement);
        int idx = rgPropertyMeasurement.indexOfChild(radioButtonPropertyMeasurement);
        RadioButton r = (RadioButton) rgPropertyMeasurement.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonPropertyMeasurement", selectedText);

       /* int selectedIdLandArea = rgLandArea.getCheckedRadioButtonId();
        View radioButtonLandArea = v.findViewById(selectedIdLandArea);
        int idx2 = rgLandArea.indexOfChild(radioButtonLandArea);
        RadioButton r2 = (RadioButton) rgLandArea.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonLandAreaa", selectedText2);
        Log.v("idx==========", String.valueOf(idx2));

        int selectedOptionLandArea = rgLandAreaOptions.getCheckedRadioButtonId();
        View radioButtonSelectedLandArea = v.findViewById(selectedOptionLandArea);
        try {
            int idx4 = rgLandAreaOptions.indexOfChild(radioButtonSelectedLandArea);
            RadioButton r4 = (RadioButton) rgLandAreaOptions.getChildAt(idx4);
            String selectedText4 = r4.getText().toString();
            hm.put("radioButtonOptionLandAreaa", selectedText4);
            Log.v("idx==========", String.valueOf(idx4));

        } catch (NullPointerException e) {
            System.out.println();
        }

        if (cb_one.isChecked() && cb_two.isChecked()) {
            hm.put("subOptions", "1");
        } else if (cb_one.isChecked()) {
            hm.put("subOptions", "2");
        } else if (cb_two.isChecked()) {
            hm.put("subOptions", "3");
        } else {
            hm.put("subOptions", "");
        }
*/
    /*    if (idx2 == 1) {
          *//*  hm.put("landAreaEastt", "");
            hm.put("landAreaWestt", "");
            hm.put("landAreaNorthh", "");
            hm.put("landAreaSouthh", "");
            hm.put("landAreaAsPerTitlee", "");
            hm.put("landAreaAsPeMapp", "");
            hm.put("landAreaAsPerSitee", "");*//*

            hm.put("etLandEastDocument", "");
            hm.put("etLandWestDocument", "");
            hm.put("etLandNorthDocument", "");
            hm.put("etLandSouthDocument", "");
            hm.put("etLandEastSurvey", "");
            hm.put("etLandWestSurvey", "");
            hm.put("etLandNorthSurvey", "");
            hm.put("etLandSouthSurvey", "");
            hm.put("landAreaAsPerTitlee", "");
            hm.put("landAreaAsPeMapp", "");
            hm.put("landAreaAsPerSitee", "");

        } else {

            hm.put("etLandEastDocument", etLandEastDocument.getText().toString());
            hm.put("etLandWestDocument", etLandWestDocument.getText().toString());
            hm.put("etLandNorthDocument", etLandNorthDocument.getText().toString());
            hm.put("etLandSouthDocument", etLandSouthDocument.getText().toString());
            hm.put("etLandEastSurvey", etLandEastSurvey.getText().toString());
            hm.put("etLandWestSurvey", etLandWestSurvey.getText().toString());
            hm.put("etLandNorthSurvey", etLandNorthSurvey.getText().toString());
            hm.put("etLandSouthSurvey", etLandSouthSurvey.getText().toString());
            hm.put("landAreaAsPerTitlee", etLandAreaAsPerTitle.getText().toString());
            hm.put("landAreaAsPeMapp", etLandAreaAsPerMap.getText().toString());
            hm.put("landAreaAsPerSitee", etLandAreaAsSiteSurvey.getText().toString());
        }*/


        int selectedIdCoveredBuiltup = rgCoveredBuiltup.getCheckedRadioButtonId();
        View radioButtonCoveredBuiltup = v.findViewById(selectedIdCoveredBuiltup);
        int idx3 = rgCoveredBuiltup.indexOfChild(radioButtonCoveredBuiltup);
        RadioButton r3 = (RadioButton) rgCoveredBuiltup.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonCoveredBuiltup", selectedText3);

        if (idx3 == 1) {
            hm.put("coveredAsPerTitlee", "");
            hm.put("coveredAsPeMapp", "");
            hm.put("coveredAsPerSitee", "");
            hm.put("coveredEastt", "");
            hm.put("coveredWestt", "");
            hm.put("coveredNorthh", "");
            hm.put("coveredSouthh", "");
            hm.put("coveredFrontSetBack", "");
            hm.put("coveredBackSetBack", "");
            hm.put("coveredRightSetBack", "");
            hm.put("coveredLeftSetBack", "");
        } else {
            hm.put("coveredAsPerTitlee", etCoveredAreaAsPerTitle.getText().toString());
            hm.put("coveredAsPeMapp", etCoveredAreaAsPerMap.getText().toString());
            hm.put("coveredAsPerSitee", etCoveredAreaAsSiteSurvey.getText().toString());
          /*  hm.put("coveredEastt", etCoveredAreaEast.getText().toString());
            hm.put("coveredWestt", etCoveredAreaWest.getText().toString());
            hm.put("coveredNorthh", etCoveredAreaNorth.getText().toString());
            hm.put("coveredSouthh", etCoveredAreaSouth.getText().toString());
            hm.put("coveredFrontSetBack", etCoveredFrontsetback.getText().toString());
            hm.put("coveredBackSetBack", etCoveredBackSetback.getText().toString());
            hm.put("coveredRightSetBack", etCoveredRightSideSetback.getText().toString());
            hm.put("coveredLeftSetBack", etCoveredLeftSideSetback.getText().toString());*/
        }


        String isThisBeingRegularizedSpinner = String.valueOf(spinnerIsThisBeingRegularized.getSelectedItemPosition());

        hm.put("isThisBeingRegularizedSpinner", isThisBeingRegularizedSpinner);

        Log.v("tagtag", isThisBeingRegularizedSpinner);

        if (isThisBeingRegularizedSpinner.equals("1")) {
            hm.put("regularizationCertificatee", tvRegularizationCerti.getText().toString());
        } else {
            hm.put("regularizationCertificatee", "");
        }

        if (encodedString.equals("")) {
            hm.put("regularizationCertificateeImage", "");
        } else {
            hm.put("regularizationCertificateeImage", encodedString);
        }


        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column6.toString(), jsonArrayData.toString());

        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "6");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));

        Log.v("#databaseinsert", String.valueOf(contentValues));


    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getTableOne("column6", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column6"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                encodedString = object.getString("regularizationCertificateeImage");
                tvRegularizationCerti.setText(object.getString("regularizationCertificatee"));
              /*  etLandEastDocument.setText(object.getString("etLandEastDocument"));
                etLandWestDocument.setText(object.getString("etLandWestDocument"));
                etLandNorthDocument.setText(object.getString("etLandNorthDocument"));
                etLandSouthDocument.setText(object.getString("etLandSouthDocument"));
                etLandEastSurvey.setText(object.getString("etLandEastSurvey"));
                etLandWestSurvey.setText(object.getString("etLandWestSurvey"));
                etLandNorthSurvey.setText(object.getString("etLandNorthSurvey"));
                etLandSouthSurvey.setText(object.getString("etLandSouthSurvey"));
                etLandAreaAsPerTitle.setText(object.getString("landAreaAsPerTitlee"));
                etLandAreaAsPerMap.setText(object.getString("landAreaAsPeMapp"));
                etLandAreaAsSiteSurvey.setText(object.getString("landAreaAsPerSitee"));*/
                etCoveredAreaAsPerTitle.setText(object.getString("coveredAsPerTitlee"));
                etCoveredAreaAsPerMap.setText(object.getString("coveredAsPeMapp"));
                etCoveredAreaAsSiteSurvey.setText(object.getString("coveredAsPerSitee"));
                /*etCoveredAreaEast.setText(object.getString("coveredEastt"));
                etCoveredAreaWest.setText(object.getString("coveredWestt"));
                etCoveredAreaNorth.setText(object.getString("coveredNorthh"));
                etCoveredAreaSouth.setText(object.getString("coveredSouthh"));
                etCoveredFrontsetback.setText(object.getString("coveredFrontSetBack"));
                etCoveredBackSetback.setText(object.getString("coveredBackSetBack"));
                etCoveredRightSideSetback.setText(object.getString("coveredRightSetBack"));
                etCoveredLeftSideSetback.setText(object.getString("coveredLeftSetBack"));*/
//                etInCaseOfDifferenceOf.setText(object.getString("reason1"));
                etGroundCoverage.setText(object.getString("groundCoveragee"));
                etInCaseOfDifferenceOf2.setText(object.getString("reason2"));
                pageVisited=object.getString(Constants.pageVisited);

              /*  try {
                    if (object.getString("radioButtonOptionLandAreaa").equals(getString(R.string.land1))) {
                        ((RadioButton) rgLandAreaOptions.getChildAt(0)).setChecked(true);

                        setHeading();

                        try {
                            if (object.getString("subOptions").equals("1")) {
                                cb_one.setChecked(true);
                                cb_two.setChecked(true);
                            } else if (object.getString("subOptions").equals("2")) {
                                cb_one.setChecked(true);
                                cb_two.setChecked(false);
                            } else if (object.getString("subOptions").equals("3")) {
                                cb_two.setChecked(true);
                                cb_one.setChecked(false);
                            } else {
                                cb_one.setChecked(true);
                                cb_two.setChecked(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (object.getString("radioButtonOptionLandAreaa").equals(getString(R.string.land1))) {
                        ((RadioButton) rgLandAreaOptions.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonOptionLandAreaa").equals(getString(R.string.land1))) {
                        ((RadioButton) rgLandAreaOptions.getChildAt(2)).setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                if (object.getString("isThisBeingRegularizedSpinner").equals("0")) {
                    spinnerIsThisBeingRegularized.setSelection(0);
                } else if (object.getString("isThisBeingRegularizedSpinner").equals("1")) {
                    spinnerIsThisBeingRegularized.setSelection(1);
                } else if (object.getString("isThisBeingRegularizedSpinner").equals("2")) {
                    spinnerIsThisBeingRegularized.setSelection(2);
                } else if (object.getString("isThisBeingRegularizedSpinner").equals("3")) {
                    spinnerIsThisBeingRegularized.setSelection(3);
                } else if (object.getString("isThisBeingRegularizedSpinner").equals("4")) {
                    spinnerIsThisBeingRegularized.setSelection(4);
                } else if (object.getString("isThisBeingRegularizedSpinner").equals("5")) {
                    spinnerIsThisBeingRegularized.setSelection(5);
                }

                if (InitiateSurveyForm.surveyTypeCheck == 1) {
                    spinnerIsThisBeingRegularized.setSelection(5);
                }


                if (object.getString("radioButtonPropertyMeasurement").equals("Self-measured (Complete)")) {
                    ((RadioButton) rgPropertyMeasurement.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonPropertyMeasurement").equals("Self-measured (from outside only)")) {
                    ((RadioButton) rgPropertyMeasurement.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonPropertyMeasurement").equals("Sample random measurement only")) {
                    ((RadioButton) rgPropertyMeasurement.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonPropertyMeasurement").equals("No measurement")) {
                    ((RadioButton) rgPropertyMeasurement.getChildAt(3)).setChecked(true);
                }


               /* if (object.getString("radioButtonLandAreaa").equals("Applicable")) {
                    ((RadioButton) rgLandArea.getChildAt(0)).setChecked(true);
                    llLandArea2.setVisibility(View.VISIBLE);
                } else if (object.getString("radioButtonLandAreaa").equals("Not Applicable")) {
                    ((RadioButton) rgLandArea.getChildAt(1)).setChecked(true);
                    llLandArea2.setVisibility(View.GONE);
                }*/


                if (object.getString("radioButtonCoveredBuiltup").equals("Applicable")) {
                    ((RadioButton) rgCoveredBuiltup.getChildAt(0)).setChecked(true);
                    llCoveredBuiltup2.setVisibility(View.VISIBLE);
                } else if (object.getString("radioButtonCoveredBuiltup").equals("Not Applicable")) {
                    ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                    llCoveredBuiltup2.setVisibility(View.GONE);
                }


                if (object.getString("cbItIsAFlat").equals("1")) {
                    cbItIsAFlat.setChecked(true);
                } else {
                    cbItIsAFlat.setChecked(false);
                }
                if (object.getString("cbPropertyWasLockedd").equals("1")) {
                    cbPropertyWasLocked.setChecked(true);
                } else {
                    cbPropertyWasLocked.setChecked(false);
                }
                if (object.getString("cbOwnerPossessee").equals("1")) {
                    cbOwnerPossessee.setChecked(true);
                } else {
                    cbOwnerPossessee.setChecked(false);
                }
//                if (object.getString("cbAnyLandNotCLear").equals("1")) {
//                    cbAnyLandNotCLear.setChecked(true);
//                } else {
//                    cbAnyLandNotCLear.setChecked(false);
//                }
                if (object.getString("cbVeryLargeIrregular").equals("1")) {
                    cbVeryLargeIrregular.setChecked(true);
                } else {
                    cbVeryLargeIrregular.setChecked(false);
                }
                if (object.getString("cbAnyOtherReasonn").equals("1")) {
                    cbAnyOtherReason.setChecked(true);
                } else {
                    cbAnyOtherReason.setChecked(false);
                }
                if (object.getString("cbConstructionDoneWithoutMapp").equals("1")) {
                    cbConstructionDoneWithoutMap.setChecked(true);
                } else {
                    cbConstructionDoneWithoutMap.setChecked(false);
                }
                if (object.getString("cbConstructionNotAsPer").equals("1")) {
                    cbConstructionNotAsPer.setChecked(true);
                } else {
                    cbConstructionNotAsPer.setChecked(false);
                }
                if (object.getString("cbExtraCovered").equals("1")) {
                    cbExtraCovered.setChecked(true);
                } else {
                    cbExtraCovered.setChecked(false);
                }
                if (object.getString("cbJoinedAdjacent").equals("1")) {
                    cbJoinedAdjacent.setChecked(true);
                } else {
                    cbJoinedAdjacent.setChecked(false);
                }
                if (object.getString("cbEnroachedAdjacent").equals("1")) {
                    cbEnroachedAdjacent.setChecked(true);
                } else {
                    cbEnroachedAdjacent.setChecked(false);
                }
               /* if (object.getString("cbSetbackPortion").equals("1")) {
                    cbSetbackPortion.setChecked(true);
                } else {
                    cbSetbackPortion.setChecked(false);
                }*/
                if (object.getString("cbMinorStructural").equals("1")) {
                    cbMinorStructural.setChecked(true);
                } else {
                    cbMinorStructural.setChecked(false);
                }
                if (object.getString("cbMajorStructural").equals("1")) {
                    cbMajorStructural.setChecked(true);
                } else {
                    cbMajorStructural.setChecked(false);
                }
                if (object.getString("cbExtentConstructionWithoutMap").equals("1")) {
                    cbExtentConstructionWithoutMap.setChecked(true);
                } else {
                    cbExtentConstructionWithoutMap.setChecked(false);
                }
                if (object.getString("cbExtentExtraCoverageWithin").equals("1")) {
                    cbExtentExtraCoverageWithin.setChecked(true);
                } else {
                    cbExtentExtraCoverageWithin.setChecked(false);
                }
                if (object.getString("cbExtentExtraCoverageOut").equals("1")) {
                    cbExtentExtraCoverageOut.setChecked(true);
                } else {
                    cbExtentExtraCoverageOut.setChecked(false);
                }
                if (object.getString("cbExtentGroundCoverage").equals("1")) {
                    cbExtentGroundCoverage.setChecked(true);
                } else {
                    cbExtentGroundCoverage.setChecked(false);
                }
                if (object.getString("cbExtentMinorCondonable").equals("1")) {
                    cbExtentMinorCondonable.setChecked(true);
                } else {
                    cbExtentMinorCondonable.setChecked(false);
                }
                if (object.getString("cbExtentCondonableStructural").equals("1")) {
                    cbExtentCondonableStructural.setChecked(true);
                } else {
                    cbExtentCondonableStructural.setChecked(false);
                }

                if (object.getString("cbExtentNonCondonableStructural").equals("1")) {
                    cbExtentNonCondonableStructural.setChecked(true);
                } else {
                    cbExtentNonCondonableStructural.setChecked(false);
                }

                if (object.getString("cbNoInfoAvailable").equals("1")) {
                    cbNoInfoAvailable.setChecked(true);
                } else {
                    cbNoInfoAvailable.setChecked(false);
                }


            }

        }
    }

    private void condition() {
/*

        rgLandArea.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);
                Log.v("getcheckk", String.valueOf(a));
                switch (a) {
                    case 1:
                        llLandArea2.setVisibility(View.GONE);
                        break;
                    default:
                        llLandArea2.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });
*/

        rgCoveredBuiltup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NewApi")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);
                Log.v("getcheckk", String.valueOf(a));

                switch (a) {
                    case 1:
                        llCoveredBuiltup2.setVisibility(View.GONE);


                        break;
                    default:
                        llCoveredBuiltup2.setVisibility(View.VISIBLE);


                        break;

                }
            }
        });

        spinnerIsThisBeingRegularized.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int s = adapterView.getSelectedItemPosition();

                if (s == 1) {
                    llRegularizationCerti.setVisibility(View.VISIBLE);
                } else
                    llRegularizationCerti.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void populateSpinner() {

        spinnerAdapter = new SpinnerAdapter(mActivity, isThisBeingRegularizedArray);
        spinnerIsThisBeingRegularized.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayFtMtr);
       /* spinnerDisplayUnit.setAdapter(spinnerAdapter);*/
        spinnerDisplayUnitCovered.setAdapter(spinnerAdapter);

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
        if (image_status.equals("1")) {
            // iv_one.setImageBitmap(b);
            tvRegularizationCerti.setText(filename);

        } else {
            //  iv_four.setImageBitmap(b);

        }

    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
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
                        ((Dashboard) mActivity).displayView(6);
                        /*intent=new Intent(GeneralForm3.this, GeneralForm1.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_two:
                        ((Dashboard) mActivity).displayView(7);
                        return true;

                    case R.id.general_three:
                        ((Dashboard) mActivity).displayView(8);
                        return true;

                    case R.id.general_four:
                        ((Dashboard) mActivity).displayView(9);
                        return true;

                    case R.id.general_five:
                        ((Dashboard) mActivity).displayView(10);
                        return true;

                    case R.id.general_six:
                        ((Dashboard) mActivity).displayView(11);
                        return true;

                    case R.id.general_seven:
                        ((Dashboard) mActivity).displayView(25);
                        return true;

                    case R.id.general_eight:
                        ((Dashboard) mActivity).displayView(13);
                        return true;

                    case R.id.general_nine:
                        ((Dashboard) mActivity).displayView(14);
                        return true;

                    case R.id.general_ten:
                        ((Dashboard) mActivity).displayView(15);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

/*
    private void calculationArea() {
        int east = 0, west = 2, north = 0, south = 0;

        try {
            east = Integer.parseInt(etLandEastSurvey.getText().toString().trim());
            west = Integer.parseInt(etLandWestSurvey.getText().toString().trim());
            north = Integer.parseInt(etLandNorthSurvey.getText().toString().trim());
            south = Integer.parseInt(etLandSouthSurvey.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String area = "";
        if (pref.get(Constants.shape).equals("Square")) {
            area = String.valueOf(east * east);
        } else if (pref.get(Constants.shape).equals("Rectangular")) {
            area = String.valueOf(east * north);
        }*/
/*
        else if (pref.get(Constants.shape).equals("Trapezium")){
            float a = ((east+west)/2)*north;
            area = String.valueOf(a);
        }
        else if (pref.get(Constants.shape).equals("Triangular")){
            float a = (east*south)/2;
            area = String.valueOf(a);
        }
        else if (pref.get(Constants.shape).equals("Trapezoid")){
            float a = ((east+west)/2)*south;
            area = String.valueOf(a);
        }*//*


        etLandAreaAsSiteSurvey.setText(area);
    }
*/


}
