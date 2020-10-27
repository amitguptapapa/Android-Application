package com.vis.android.Activities.Vacant.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.squareup.picasso.Picasso;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.NearbyPlaceActivity;
import com.vis.android.Activities.General.SpinnerAdapter;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableGeneralForm;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.Extras.MySupportMapFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;

//import com.google.android.gms.location.places.AutocompletePrediction;
//import com.google.android.gms.location.places.Places;

public class VacantForm3 extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, OnMapReadyCallback {

    private static final String IMAGE_DIRECTORY_NAME = "VIS";
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;
    public static Double Lat, Lng;
    public String picturePath = "", filename = "", ext = "", encodedString = "", setPic = "";
    //  RelativeLayout back,dots;
    TextView next, tvPrevious, tvViewAll, tvAnyNewDevelopment, tvAnyNewDevelopmentUrl;
    Intent intent;
    Preferences pref;
    EditText etPostalAddress, etWardName, etTehsil, etMainRoadName, etMainRoadWidth, etDistanceFromProp,//etTypeofApproach,
            etApproachRoadName, etApproachRoadWidth, etAnyNewDevelopment, etAnyOtherDevAut, etAnyOtherMunicipal, etAreaNotWithinLocal, etAreaNotWithinDevelopment, etAreaNotWithinMunicipal;
    Boolean firstTimer = false;
    AutoCompleteTextView etConfirmLandmark;
    Geocoder geocoder;
    TextView tv_MapDistance;
    Double conf_landmark_lat = 0.0, conf_landmark_lng = 0.0;
    RadioGroup rgClassificationOfLocality, rgCharacteristicsOfLocality, rgLocalBodyJurisdiction, rgDevelopmentAuthority, rgMunicipalCorporation, rgCategoryOfLocality;
    //CheckBox cbHighEnd,cbNormal,cbAverage,cbAffordable,cbEws,cbHig,cbMig,cbLig
    CheckBox cbCornerPlot, cb3SideOpen,cbOnHighway,cbAny_Other,cbOrdinaryLocationWithinLocality,cbGoodLocationWithinLocality,  cbNeartoHighway, cbNearToMarket, cbNearToMetroStation, cbOnWideRoad, cb2SideOpen, cbParkFacing, cbRoadFacing, cbEntranceNorth, cbSunlightFacing, cbSeaFacing, cbNormalLocation,
            cbGoodLocation, cbAverageLocation, cbPoorLocation, cbPropertyToward, cbLifts, cbGarden, cbLandscaping, cbSwimmingPool, cbGym,
            cbClubHouse, cbWalkingTrails, cbKidsPlay, cb100Power, cbWaterSupplyIssues, cbFrequentPower, cbPoorRoads, cbPoorDrainage, cbParkingIssues,
            cbNarrowLanes, cbNewlyDevelopingArea, cbLocalMarket, cbNormalArea, cbUtilitiesNone;
    //cbHighEndCheck,cbNormalCheck,cbAverageCheck,cbAffordableCheck,cbEwsCheck,cbHigCheck,cbMigCheck,cbLigCheck
    int cbCornerPlotCheck, cb3SideOpenCheck,cbOnHighwayCheck,cbAny_OtherCheck,cbGoodLocationWithinLocalityCheck,cbOrdinaryLocationWithinLocalityCheck,cbNeartoHighwayCheck, cbNearToMarketCheck, cbNearToMetroStationCheck, cbOnWideRoadCheck, cb2SideOpenCheck, cbParkFacingCheck, cbRoadFacingCheck, cbEntranceNorthCheck, cbSunlightFacingCheck,
            cbSeaFacingCheck, cbNormalLocationCheck, cbGoodLocationCheck, cbAverageLocationCheck, cbPoorLocationCheck, cbPropertyTowardCheck,
            cbLiftsCheck, cbGardenCheck, cbLandscapingCheck, cbSwimmingPoolCheck, cbGymCheck, cbClubHouseCheck, cbWalkingTrailsCheck,
            cbKidsPlayCheck, cb100PowerCheck, cbWaterSupplyIssuesCheck, cbFrequentPowerCheck, cbPoorRoadsCheck,
            cbPoorDrainageCheck, cbParkingIssuesCheck, cbNarrowLanesCheck, cbNewlyDevelopingAreaCheck, cbLocalMarketCheck, cbNormalAreaCheck, cbUtilitiesNoneCheck;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    Spinner spinnerTypeofApproach;
    int firstTime = 0;
    ArrayList<String> typeOfApproachArray = new ArrayList<>();
    SpinnerAdapter spinnerAdapter;
    SpinnerListAdapter  spinnerAdapter1;
    //  ProgressBar progressbar;
    LinearLayout dropdown;

    // RelativeLayout rl_casedetail;
    TextView tv_camera2, tv_camera1, tvSchoolClick, tvHospitalClick, tvMarketClick, tvMetroClick,
            tvRailwayStationClick, tvAirportClick;
    String mainRoadPicBase64 = "", approachRoadBase64 = "";
    Spinner spinnerCharOfLocal;
    int spinCharOfLoc=0;
    Uri picUri, fileUri;
    Boolean edit_page=true;
    Bitmap bitmap;
    String pageVisited="";
    ImageView ivCameraCapture2, iv_camera2, ivCameraCapture1, iv_camera1;
    CustomLoader loader;
    Dialog dialog;
    GoogleSearchAdapter googleSearchAdapter;
    AutoCompleteTextView etSchool, etHospital, etMarket, etMetro, etRailwayStation, etAirport;
    MySupportMapFragment mSupportMapFragment;
    ArrayAdapter<String> adapterSearchSchool;
    ArrayAdapter<String> adapterSearchHospital;
    ArrayAdapter<String> adapterSearchMetro;
    ArrayAdapter<String> adapterSearchMarket;
    ArrayAdapter<String> adapterSearchRailway;

    String[] arrayCharOfLocal = {"Choose an item","Excellent","Very Good","Good","Ordinary","Average","Poor","Backward","Not Applicable"};


    ArrayAdapter<String> adapterSearchAirport;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayListFav = new ArrayList<>();
    View v;
    RecyclerView rvAutocomplete;
    GoogleMap mMap;
    CheckBox cbNoNewDev;
    private ScrollView scrollView;
    private double measuredDistance = 0;
    private int captureType;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    private String mAddressOutput, mAreaOutput, mCityOutput, mStateOutput, mState, mZipCode, mFeature, mCountry, mPremises, mSubAdminArea,
            mSubLocality, mThoroughfare, mAddressOutputNew;
    private ArrayList<String> dataListSearchSchool = new ArrayList<>();
    private ArrayList<String> dataListSearchHospital = new ArrayList<>();
    private ArrayList<String> dataListSearchMetro = new ArrayList<>();
    private ArrayList<String> dataListSearchMarket = new ArrayList<>();
    private ArrayList<String> dataListSearchRailway = new ArrayList<>();
    private ArrayList<String> dataListSearchAirport = new ArrayList<>();
    private ArrayList<HashMap<String, String>> dataListGoogleSearch = new ArrayList<>();

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

    public static String getAddress(Context context, double LATITUDE, double LONGITUDE) {

        //Set Address
        String address = "";
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {

                try {

                    address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_vacantform3, container, false);

        getid(v);
        setListener();

        populateSinner();
//****************************************************Map***************************************************************************
        mSupportMapFragment = (MySupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFrag);
        mSupportMapFragment.getMapAsync(this);

        mSupportMapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
//                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        MapsInitializer.initialize(mActivity);


//****************************************************Map***************************************************************************

        Places.initialize(mActivity, getString(R.string.google_maps_key));
        placesClient = Places.createClient(mActivity);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        etConfirmLandmark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etConfirmLandmark.isFocusable() && firstTime != 0) {
                    rvAutocomplete.setVisibility(View.VISIBLE);
                } else {
                    rvAutocomplete.setVisibility(View.GONE);
                    firstTime++;
                }
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        // .setCountry("in")
//                         .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(s.toString())
                        .build();


                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if (task.isSuccessful()) {
                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.getAutocompletePredictions();
                                //List<String> suggestionsList = new ArrayList<>();

                                arrayList.clear();
                                for (int i = 0; i < predictionList.size(); i++) {

                                    AutocompletePrediction prediction = predictionList.get(i);
                                    HashMap hashMap = new HashMap();
                                    hashMap.put("fullAdd", prediction.getFullText(null).toString());
                                    hashMap.put("primaryAdd", prediction.getPrimaryText(null).toString());
                                    arrayList.add(hashMap);

                                    Log.v("suddd", prediction.getFullText(null).toString());
                                    Log.v("suddd", prediction.getPrimaryText(null).toString());
                                    Log.v("suddd", prediction.getSecondaryText(null).toString());
                                }

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
                                rvAutocomplete.setLayoutManager(linearLayoutManager);
                                rvAutocomplete.setAdapter(new AutocompleteAdapter(mActivity, arrayList));

                            }
                        } else {
                            Log.i("mytag", "prediction fetching task unsuccessful");
                        }
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

//        progressbar.setMax(10);
//        progressbar.setProgress(3);

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Utils.isNetworkConnectedMainThred(mActivity)) {

            // getNearbyAddresses();

            dataListGoogleSearch.clear();

            hitGoogleCustomSearch("new infrastructure development projects near", 1);
            hitGoogleCustomSearch("new road development projects near", 2);
            hitGoogleCustomSearch("new municipal development projects near", 3);

        }
        setDefaults();

        if(!edit_page){
            setEditiblity();
        }
        return v;
    }

    public void setDefaults(){
        /*if(!pageVisited.equalsIgnoreCase("vacantForm3")){
            ((RadioButton) rgCategoryOfLocality.getChildAt(8)).setChecked(true);
        }*/
    }
    private void  setEditiblity() {
        etPostalAddress.setEnabled(false);
        etWardName.setEnabled(false);
        etTehsil.setEnabled(false);
        etMainRoadName.setEnabled(false);
        etMainRoadWidth.setEnabled(false);
        etDistanceFromProp.setEnabled(false);
        etApproachRoadName.setEnabled(false);
        etApproachRoadWidth.setEnabled(false);
        etAnyNewDevelopment.setEnabled(false);
        etAnyOtherDevAut.setEnabled(false);
        etAnyOtherMunicipal.setEnabled(false);
        etAreaNotWithinLocal.setEnabled(false);
        etAreaNotWithinDevelopment.setEnabled(false);
        etAreaNotWithinMunicipal.setEnabled(false);
        ivCameraCapture2.setEnabled(false);
        iv_camera2.setEnabled(false);
        ivCameraCapture1.setEnabled(false);
        iv_camera1.setEnabled(false);
        spinnerCharOfLocal.setEnabled(false);
        spinnerTypeofApproach.setEnabled(false);

        for(int i=0;i<rgClassificationOfLocality.getChildCount();i++)
            rgClassificationOfLocality.getChildAt(i).setClickable(false);

        for(int i=0;i<rgLocalBodyJurisdiction.getChildCount();i++)
            rgLocalBodyJurisdiction.getChildAt(i).setClickable(false);

        for(int i=0;i<rgDevelopmentAuthority.getChildCount();i++)
            rgDevelopmentAuthority.getChildAt(i).setClickable(false);

        for(int i=0;i<rgMunicipalCorporation.getChildCount();i++)
            rgMunicipalCorporation.getChildAt(i).setClickable(false);

        for(int i=0;i<rgCategoryOfLocality.getChildCount();i++)
            rgCategoryOfLocality.getChildAt(i).setClickable(false);

        for(int i=0;i<rgCharacteristicsOfLocality.getChildCount();i++)
            rgCharacteristicsOfLocality.getChildAt(i).setClickable(false);

        cbCornerPlot.setClickable(false);

        cb3SideOpen.setClickable(false);

        cbOnHighway.setClickable(false);

        cbAny_Other.setClickable(false);

        cbOrdinaryLocationWithinLocality.setClickable(false);

        cbGoodLocationWithinLocality.setClickable(false);

        cbNeartoHighway.setClickable(false);

        cbNearToMarket.setClickable(false);

        cbNearToMetroStation.setClickable(false);

        cbOnWideRoad.setClickable(false);

        cb2SideOpen.setClickable(false);

        cbParkFacing.setClickable(false);

        cbRoadFacing.setClickable(false);

        cbEntranceNorth.setClickable(false);

        cbSunlightFacing.setClickable(false);

        cbSeaFacing.setClickable(false);

        cbNormalLocation.setClickable(false);

        cbGoodLocation.setClickable(false);

        cbAverageLocation.setClickable(false);

        cbPoorLocation.setClickable(false);

        cbPropertyToward.setClickable(false);

        cbLifts.setClickable(false);

        cbGarden.setClickable(false);

        cbLandscaping.setClickable(false);

        cbSwimmingPool.setClickable(false);

        cbGym.setClickable(false);


        cbClubHouse.setClickable(false);

        cbWalkingTrails.setClickable(false);

        cbKidsPlay.setClickable(false);

        cb100Power.setClickable(false);

        cbWaterSupplyIssues.setClickable(false);

        cbFrequentPower.setClickable(false);

        cbPoorRoads.setClickable(false);

        cbPoorDrainage.setClickable(false);

        cbParkingIssues.setClickable(false);


        cbNarrowLanes.setClickable(false);

        cbNewlyDevelopingArea.setClickable(false);

        cbLocalMarket.setClickable(false);

        cbNormalArea.setClickable(false);

        cbUtilitiesNone.setClickable(false);



        cbNoNewDev.setEnabled(false);
        etSchool.setEnabled(false);
        etHospital.setEnabled(false);
        etMarket.setEnabled(false);
        etMetro.setEnabled(false);
        etRailwayStation.setEnabled(false);
        etAirport.setEnabled(false);
        tvSchoolClick.setEnabled(false);
        tvHospitalClick.setEnabled(false);
        tvMarketClick.setEnabled(false);
        tvMetroClick.setEnabled(false);
        tvRailwayStationClick.setEnabled(false);
        tvAirportClick.setEnabled(false);
        rvAutocomplete.setEnabled(false);
        tvViewAll.setEnabled(false);
        tvAnyNewDevelopmentUrl.setEnabled(false);
        etConfirmLandmark.setEnabled(false);



    }

    @Override
    public void onResume() {
        super.onResume();

        if (NearbyPlaceActivity.checkPage == 1) {
            etSchool.setText(pref.get(Constants.schoolName));
            etHospital.setText(pref.get(Constants.hospitalName));
            etAirport.setText(pref.get(Constants.airportName));
            etMarket.setText(pref.get(Constants.marketName));
            etRailwayStation.setText(pref.get(Constants.railwayStationName));
            etMetro.setText(pref.get(Constants.metroName));
            //etMarket.setText(pref.get(Constants.marketName));
        }
    }

    public void getid(View v) {

        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        typeOfApproachArray.clear();

        typeOfApproachArray.add("Choose an item");
        typeOfApproachArray.add("Bituminous Road");
        typeOfApproachArray.add("Metalled Road");
        typeOfApproachArray.add("Cement Concrete Road");
        typeOfApproachArray.add("Concrete Paver Block Road");
        typeOfApproachArray.add("Brick Khadanja Road");
        typeOfApproachArray.add("Broken Pothole Metalled Road");
        typeOfApproachArray.add("Very narrow approach road towards the subject property");
        typeOfApproachArray.add("No proper approach road available");
        rvAutocomplete = v.findViewById(R.id.rvAutocomplete);
        tv_camera2 = v.findViewById(R.id.tv_camera2);
        tv_camera1 = v.findViewById(R.id.tv_camera1);
        tvSchoolClick = v.findViewById(R.id.tvSchoolClick);
        tvHospitalClick = v.findViewById(R.id.tvHospitalClick);
        tvMarketClick = v.findViewById(R.id.tvMarketClick);
        tvMetroClick = v.findViewById(R.id.tvMetroClick);
        tvRailwayStationClick = v.findViewById(R.id.tvRailwayStationClick);
        tvAirportClick = v.findViewById(R.id.tvAirportClick);
        spinnerCharOfLocal = v.findViewById(R.id.spinnerCharOfLocal);

        iv_camera2 = v.findViewById(R.id.iv_camera2);
        iv_camera1 = v.findViewById(R.id.iv_camera1);
        ivCameraCapture2 = v.findViewById(R.id.ivCameraCapture2);
        ivCameraCapture1 = v.findViewById(R.id.ivCameraCapture1);

//        tv_header = v.findViewById(R.id.tv_header);
//        rl_casedetail = v.findViewById(R.id.rl_casedetail);
//        tv_caseheader = v.findViewById(R.id.tv_caseheader);
//        tv_caseid = v.findViewById(R.id.tv_caseid);

        // progressbar = (ProgressBar)v.findViewById(R.id.Progress);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page=false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page=true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        // back = v.findViewById(R.id.rl_back);
        next = v.findViewById(R.id.next);
        //  dots = (RelativeLayout) v.findViewById(R.id.rl_dots);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        tvViewAll = v.findViewById(R.id.tvViewAll);
        dropdown = v.findViewById(R.id.ll_dropdown);

        etPostalAddress = v.findViewById(R.id.etPostalAddress);
        etConfirmLandmark = v.findViewById(R.id.etConfirmLandmark);
        tv_MapDistance = v.findViewById(R.id.tv_MapDistance);

//        CustomAutoCompleteAdapter adapterAutoComplete =  new CustomAutoCompleteAdapter(mActivity);
//        etConfirmLandmark.setAdapter(adapterAutoComplete);

        etWardName = v.findViewById(R.id.etWardName);
        etTehsil = v.findViewById(R.id.etTehsil);
        etMainRoadName = v.findViewById(R.id.etMainRoadName);
        etMainRoadWidth = v.findViewById(R.id.etMainRoadWidth);
        etDistanceFromProp = v.findViewById(R.id.etDistanceFromProp);
        // etTypeofApproach = v.findViewById(R.id.etTypeofApproach);
        etApproachRoadName = v.findViewById(R.id.etApproachRoadName);
        etApproachRoadWidth = v.findViewById(R.id.etApproachRoadWidth);
        etSchool = v.findViewById(R.id.etSchool);
        etHospital = v.findViewById(R.id.etHospital);
        etMarket = v.findViewById(R.id.etMarket);
        etMetro = v.findViewById(R.id.etMetro);
        etRailwayStation = v.findViewById(R.id.etRailwayStation);
        etAirport = v.findViewById(R.id.etAirport);
        etAnyNewDevelopment = v.findViewById(R.id.etAnyNewDevelopment);
        etAnyOtherDevAut = v.findViewById(R.id.etAnyOtherDevAut);
        etAnyOtherMunicipal = v.findViewById(R.id.etAnyOtherMunicipal);
        etAreaNotWithinLocal = v.findViewById(R.id.etAreaNotWithinLocal);
        etAreaNotWithinDevelopment = v.findViewById(R.id.etAreaNotWithinDevelopment);
        etAreaNotWithinMunicipal = v.findViewById(R.id.etAreaNotWithinMunicipal);

        tvAnyNewDevelopment = v.findViewById(R.id.tvAnyNewDevelopment);
        tvAnyNewDevelopmentUrl = v.findViewById(R.id.tvAnyNewDevelopmentUrl);

        rgClassificationOfLocality = v.findViewById(R.id.rgClassificationOfLocality);
        rgCharacteristicsOfLocality = v.findViewById(R.id.rgCharacteristicsOfLocality);
        rgLocalBodyJurisdiction = v.findViewById(R.id.rgLocalBodyJurisdiction);
        rgDevelopmentAuthority = v.findViewById(R.id.rgDevelopmentAuthority);
        rgMunicipalCorporation = v.findViewById(R.id.rgMunicipalCorporation);
        rgCategoryOfLocality = v.findViewById(R.id.rgCategoryOfLocality);

//        cbHighEnd = v.findViewById(R.id.cbHighEnd);
//        cbNormal = v.findViewById(R.id.cbNormal);
//        cbAverage = v.findViewById(R.id.cbAverage);
//        cbAffordable = v.findViewById(R.id.cbAffordable);
//        cbEws = v.findViewById(R.id.cbEws);
//        cbHig = v.findViewById(R.id.cbHig);
//        cbMig = v.findViewById(R.id.cbMig);
//        cbLig = v.findViewById(R.id.cbLig);

        cbCornerPlot = v.findViewById(R.id.cbCornerPlot);
        cb3SideOpen = v.findViewById(R.id.cb3SideOpen);
        cbAny_Other = v.findViewById(R.id.cbAny_Other);
        cbOnHighway = v.findViewById(R.id.cbOnHighway);
        cbGoodLocationWithinLocality = v.findViewById(R.id.cbGoodLocationWithinLocality);
        cbOrdinaryLocationWithinLocality = v.findViewById(R.id.cbOrdinaryLocationWithinLocality);
//        cbEntranceNorthEastFacing = v.findViewById(R.id.cbEntranceNorthEastFacing);
        cbNeartoHighway = v.findViewById(R.id.cbNeartoHighway);
        cbNearToMetroStation = v.findViewById(R.id.cbNearToMetroStation);
        cbNearToMarket = v.findViewById(R.id.cbNearToMarket);
        cbOnWideRoad = v.findViewById(R.id.cbOnWideRoad);
        cb2SideOpen = v.findViewById(R.id.cb2SideOpen);
        cbParkFacing = v.findViewById(R.id.cbParkFacing);
        cbRoadFacing = v.findViewById(R.id.cbRoadFacing);
        cbEntranceNorth = v.findViewById(R.id.cbEntranceNorth);
        cbSunlightFacing = v.findViewById(R.id.cbSunlightFacing);
        cbSeaFacing = v.findViewById(R.id.cbSeaFacing);
        cbNormalLocation = v.findViewById(R.id.cbNormalLocation);
        cbGoodLocation = v.findViewById(R.id.cbGoodLocation);
        cbAverageLocation = v.findViewById(R.id.cbAverageLocation);
        cbPoorLocation = v.findViewById(R.id.cbPoorLocation);
        cbPropertyToward = v.findViewById(R.id.cbPropertyToward);
        cbLifts = v.findViewById(R.id.cbLifts);
        cbGarden = v.findViewById(R.id.cbGarden);
        cbLandscaping = v.findViewById(R.id.cbLandscaping);
        cbSwimmingPool = v.findViewById(R.id.cbSwimmingPool);
        cbGym = v.findViewById(R.id.cbGym);
        cbClubHouse = v.findViewById(R.id.cbClubHouse);
        cbWalkingTrails = v.findViewById(R.id.cbWalkingTrails);
        cbKidsPlay = v.findViewById(R.id.cbKidsPlay);
        cb100Power = v.findViewById(R.id.cb100Power);
        cbWaterSupplyIssues = v.findViewById(R.id.cbWaterSupplyIssues);
        cbFrequentPower = v.findViewById(R.id.cbFrequentPower);
        cbPoorRoads = v.findViewById(R.id.cbPoorRoads);
        cbPoorDrainage = v.findViewById(R.id.cbPoorDrainage);
        cbParkingIssues = v.findViewById(R.id.cbParkingIssues);
        cbNarrowLanes = v.findViewById(R.id.cbNarrowLanes);
        cbNewlyDevelopingArea = v.findViewById(R.id.cbNewlyDevelopingArea);
        cbLocalMarket = v.findViewById(R.id.cbLocalMarket);
        cbNormalArea = v.findViewById(R.id.cbNormalArea);
        cbUtilitiesNone = v.findViewById(R.id.cbUtilitiesNone);

        cbNoNewDev = v.findViewById(R.id.cbNoNewDev);

        spinnerTypeofApproach = v.findViewById(R.id.spinnerTypeofApproach);

        etPostalAddress.setText(pref.get(Constants.assetAddress));

        adapterSearchSchool = new ArrayAdapter<String>
                (mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchSchool);
        etSchool.setThreshold(1);//will start working from first character
        etSchool.setAdapter(adapterSearchSchool);//setting the adapter data into the AutoCompleteTextView
        adapterSearchSchool.setNotifyOnChange(true);

        adapterSearchHospital = new ArrayAdapter<String>
                (mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchHospital);
        etHospital.setThreshold(1);//will start working from first character
        etHospital.setAdapter(adapterSearchHospital);//setting the adapter data into the AutoCompleteTextView
        adapterSearchHospital.setNotifyOnChange(true);

        adapterSearchMetro = new ArrayAdapter<String>
                (mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchMetro);
        etMetro.setThreshold(1);//will start working from first character
        etMetro.setAdapter(adapterSearchMetro);//setting the adapter data into the AutoCompleteTextView
        adapterSearchMetro.setNotifyOnChange(true);

        adapterSearchMarket = new ArrayAdapter<String>
                (mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchMarket);
        etMarket.setThreshold(1);//will start working from first character
        etMarket.setAdapter(adapterSearchMarket);//setting the adapter data into the AutoCompleteTextView
        adapterSearchMarket.setNotifyOnChange(true);

        adapterSearchRailway = new ArrayAdapter<String>
                (mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchRailway);
        etRailwayStation.setThreshold(1);//will start working from first character
        etRailwayStation.setAdapter(adapterSearchRailway);//setting the adapter data into the AutoCompleteTextView
        adapterSearchRailway.setNotifyOnChange(true);

        adapterSearchAirport = new ArrayAdapter<String>
                (mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchAirport);
        etAirport.setThreshold(1);//will start working from first character
        etAirport.setAdapter(adapterSearchAirport);//setting the adapter data into the AutoCompleteTextView
        adapterSearchAirport.setNotifyOnChange(true);

    }

    public void setListener() {
        //    back.setOnClickListener(this);
        next.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        //     dots.setOnClickListener(this);
        iv_camera2.setOnClickListener(this);
        iv_camera1.setOnClickListener(this);
        tvViewAll.setOnClickListener(this);
        tvSchoolClick.setOnClickListener(this);
        tvHospitalClick.setOnClickListener(this);
        tvMarketClick.setOnClickListener(this);
        tvMetroClick.setOnClickListener(this);
        tvRailwayStationClick.setOnClickListener(this);
        tvAirportClick.setOnClickListener(this);
        tvAnyNewDevelopmentUrl.setOnClickListener(this);

        etSchool.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {

                    if (!etSchool.getText().toString().isEmpty()) {

//                        pref.set(Constants.searchKeyword,etSchool.getText().toString());
//                        pref.commit();

                        dataListSearchSchool.clear();

                        hitSearchApi("school", etSchool.getText().toString());

                        hideKeyboard(etSchool);

                    } else {

                        Toast.makeText(mActivity, "Enter search keyword", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
                return false;
            }
        });
        spinnerCharOfLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinCharOfLoc=spinnerCharOfLocal.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etHospital.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {

                    if (!etHospital.getText().toString().isEmpty()) {

//                        pref.set(Constants.searchKeyword,etSchool.getText().toString());
//                        pref.commit();

                        dataListSearchHospital.clear();

                        hitSearchApi("hospital", etHospital.getText().toString());

                        hideKeyboard(etHospital);

                    } else {

                        Toast.makeText(mActivity, "Enter search keyword", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
                return false;
            }
        });

        etMetro.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {

                    if (!etMetro.getText().toString().isEmpty()) {

//                        pref.set(Constants.searchKeyword,etSchool.getText().toString());
//                        pref.commit();

                        dataListSearchMetro.clear();

                        hitSearchApi("subway_station", etMetro.getText().toString());

                        hideKeyboard(etMetro);

                    } else {

                        Toast.makeText(mActivity, "Enter search keyword", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
                return false;
            }
        });

        etRailwayStation.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {

                    if (!etRailwayStation.getText().toString().isEmpty()) {

//                        pref.set(Constants.searchKeyword,etSchool.getText().toString());
//                        pref.commit();

                        dataListSearchRailway.clear();

                        hitSearchApi("train_station", etRailwayStation.getText().toString());

                        hideKeyboard(etRailwayStation);

                    } else {

                        Toast.makeText(mActivity, "Enter search keyword", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
                return false;
            }
        });

        etAirport.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {

                    if (!etAirport.getText().toString().isEmpty()) {

//                        pref.set(Constants.searchKeyword,etSchool.getText().toString());
//                        pref.commit();

                        dataListSearchAirport.clear();

                        hitSearchApi("airport", etAirport.getText().toString());

                        hideKeyboard(etAirport);

                    } else {

                        Toast.makeText(mActivity, "Enter search keyword", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
                return false;
            }
        });

        etMarket.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {

                    if (!etMarket.getText().toString().isEmpty()) {

//                        pref.set(Constants.searchKeyword,etSchool.getText().toString());
//                        pref.commit();

                        dataListSearchAirport.clear();

                        hitSearchApi("supermarket", etMarket.getText().toString());

                        hideKeyboard(etMarket);

                    } else {

                        Toast.makeText(mActivity, "Enter search keyword", Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
                return false;
            }
        });

        rgLocalBodyJurisdiction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (a == 4) {
                        //   etAreaNotWithinLocal.setVisibility(View.VISIBLE);
                        etAreaNotWithinLocal.setVisibility(View.GONE);
                    } else {
                        etAreaNotWithinLocal.setVisibility(View.GONE);
                    }
                }

            }
        });

        rgDevelopmentAuthority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (a == 8) {
                        // etAreaNotWithinDevelopment.setVisibility(View.VISIBLE);
                        etAreaNotWithinDevelopment.setVisibility(View.GONE);
                        etAnyOtherDevAut.setVisibility(View.GONE);
                    } else if (a == 10) {
                        etAnyOtherDevAut.setVisibility(View.VISIBLE);
                        etAreaNotWithinDevelopment.setVisibility(View.GONE);
                    } else {
                        etAreaNotWithinDevelopment.setVisibility(View.GONE);
                        etAnyOtherDevAut.setVisibility(View.GONE);

                    }
                }

            }
        });

        rgMunicipalCorporation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (a == 8) {
                        //  etAreaNotWithinMunicipal.setVisibility(View.VISIBLE);
                        etAreaNotWithinMunicipal.setVisibility(View.GONE);
                        etAnyOtherMunicipal.setVisibility(View.GONE);
                    } else if (a == 10) {
                        etAnyOtherMunicipal.setVisibility(View.VISIBLE);
                        etAreaNotWithinMunicipal.setVisibility(View.GONE);
                    } else {
                        etAreaNotWithinMunicipal.setVisibility(View.GONE);
                        etAnyOtherMunicipal.setVisibility(View.GONE);

                    }
                }

            }
        });


        cbUtilitiesNone.setOnCheckedChangeListener(this);
        cbLifts.setOnCheckedChangeListener(this);
        cbGarden.setOnCheckedChangeListener(this);
        cbLandscaping.setOnCheckedChangeListener(this);
        cbSwimmingPool.setOnCheckedChangeListener(this);
        cbGym.setOnCheckedChangeListener(this);
        cbClubHouse.setOnCheckedChangeListener(this);
        cbWalkingTrails.setOnCheckedChangeListener(this);
        cbKidsPlay.setOnCheckedChangeListener(this);
        cb100Power.setOnCheckedChangeListener(this);
        cbWaterSupplyIssues.setOnCheckedChangeListener(this);
        cbFrequentPower.setOnCheckedChangeListener(this);
        cbPoorRoads.setOnCheckedChangeListener(this);
        cbPoorDrainage.setOnCheckedChangeListener(this);
        cbParkingIssues.setOnCheckedChangeListener(this);
        cbNarrowLanes.setOnCheckedChangeListener(this);
        cbNewlyDevelopingArea.setOnCheckedChangeListener(this);
        cbLocalMarket.setOnCheckedChangeListener(this);
        cbNormalArea.setOnCheckedChangeListener(this);
        cbNoNewDev.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.cbUtilitiesNone:
                if (isChecked) {
                    cbLifts.setChecked(false);
                    cbGarden.setChecked(false);
                    cbLandscaping.setChecked(false);
                    cbSwimmingPool.setChecked(false);
                    cbGym.setChecked(false);
                    cbClubHouse.setChecked(false);
                    cbWalkingTrails.setChecked(false);
                    cbKidsPlay.setChecked(false);
                    cb100Power.setChecked(false);
                    cbWaterSupplyIssues.setChecked(false);
                    cbFrequentPower.setChecked(false);
                    cbPoorRoads.setChecked(false);
                    cbPoorDrainage.setChecked(false);
                    cbParkingIssues.setChecked(false);
                    cbNarrowLanes.setChecked(false);
                    cbNewlyDevelopingArea.setChecked(false);
                    cbLocalMarket.setChecked(false);
                    cbNormalArea.setChecked(false);
                }

                break;

            case R.id.cbLifts:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbGarden:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbLandscaping:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbSwimmingPool:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbGym:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbClubHouse:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbWalkingTrails:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbKidsPlay:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cb100Power:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbWaterSupplyIssues:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbFrequentPower:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbPoorRoads:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbPoorDrainage:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbParkingIssues:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbNarrowLanes:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbNewlyDevelopingArea:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbLocalMarket:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;
            case R.id.cbNormalArea:
                if (isChecked) {
                    cbUtilitiesNone.setChecked(false);
                }
                break;

            case R.id.cbNoNewDev:
                if (isChecked) {
                    tvViewAll.setEnabled(false);
                    etAnyNewDevelopment.setEnabled(false);
                    tvAnyNewDevelopmentUrl.setEnabled(false);
                    tvAnyNewDevelopment.setEnabled(false);
                    tvAnyNewDevelopmentUrl.setText("");
                    tvAnyNewDevelopment.setText("");
                    etAnyNewDevelopment.setText("");
                } else {
                    tvViewAll.setEnabled(true);
                    etAnyNewDevelopment.setEnabled(true);
                    tvAnyNewDevelopmentUrl.setEnabled(true);
                    tvAnyNewDevelopment.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvAnyNewDevelopmentUrl:

                if (!tvAnyNewDevelopmentUrl.getText().toString().isEmpty()) {

                }

                break;

            case R.id.iv_camera2:

                captureType = 2;

                captureImage();

                break;

            case R.id.iv_camera1:

                captureType = 1;

                captureImage();

                break;
/*
            case R.id.rl_back:
                onBackPressed();
                break;*/
            case R.id.tvPrevious:
              /*  intent=new Intent(GeneralForm3.this,GeneralForm2.class);
                startActivity(intent);*/
                //onBackPressed();
                //((Dashboard)mActivity).displayView(7);
                mActivity.onBackPressed();

                break;

            case R.id.next:
                if(edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(22);

                pref.set(Constants.schoolName, etSchool.getText().toString());
                pref.set(Constants.hospitalName, etHospital.getText().toString());
                pref.set(Constants.marketName, etMarket.getText().toString());
                pref.set(Constants.metroName, etMetro.getText().toString());
                pref.set(Constants.railwayStationName, etRailwayStation.getText().toString());
                pref.set(Constants.airportName, etAirport.getText().toString());
                pref.commit();

                break;

            case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;

            case R.id.tvViewAll:

                AlertAddressedPopup();

                break;

            case R.id.tvSchoolClick:

                // hitPlacesApi("school");
                ////            hitPlacesApi("hospital");
                ////
                ////            //hitPlacesApi("supermarket");
                ////
                ////            hitPlacesApi("train_station");
                ////            hitPlacesApi("airport");

                pref.set(Constants.keyword, "school");
                pref.commit();

                startActivity(new Intent(mActivity, NearbyPlaceActivity.class));

                break;

            case R.id.tvHospitalClick:

                pref.set(Constants.keyword, "hospital");
                pref.commit();

                startActivity(new Intent(mActivity, NearbyPlaceActivity.class));

                break;

            case R.id.tvMetroClick:

                pref.set(Constants.keyword, "subway_station");
                pref.commit();

                startActivity(new Intent(mActivity, NearbyPlaceActivity.class));

                break;

            case R.id.tvRailwayStationClick:

                pref.set(Constants.keyword, "train_station");
                pref.commit();

                startActivity(new Intent(mActivity, NearbyPlaceActivity.class));

                break;

            case R.id.tvAirportClick:

                pref.set(Constants.keyword, "airport");
                pref.commit();

                startActivity(new Intent(mActivity, NearbyPlaceActivity.class));

                break;

            case R.id.tvMarketClick:

                pref.set(Constants.keyword, "supermarket");
                pref.commit();

                startActivity(new Intent(mActivity, NearbyPlaceActivity.class));

                break;
        }
    }

    private void validation() {
        if (etPostalAddress.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Postal Address of the property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPostalAddress.requestFocus();
        } else if (etConfirmLandmark.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Confirm the landmark from property owner", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etConfirmLandmark.requestFocus();
        } else if (etWardName.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Ward Name/ No.", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etWardName.requestFocus();
        } else if (etTehsil.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Tehsil/ Block Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTehsil.requestFocus();
        } else if (etMainRoadName.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Main Road Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMainRoadName.requestFocus();
        } else if (etMainRoadWidth.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Main Road Width", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMainRoadWidth.requestFocus();
        } else if (etDistanceFromProp.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Main Road Distance from the property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDistanceFromProp.requestFocus();
        } else if (spinnerTypeofApproach.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvTypeOfAppr);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }
        else if (spinCharOfLoc==0) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Characterstics values", Snackbar.LENGTH_SHORT);
            snackbar.show();
            spinnerCharOfLocal.requestFocus();


        }
       /* else if (etTypeofApproach.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(next, "Please Enter Type of Approach Road", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeofApproach.requestFocus();
        }*/
        else if (etApproachRoadName.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Approach Road Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etApproachRoadName.requestFocus();
        } else if (etApproachRoadWidth.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Approach Road Width", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etApproachRoadWidth.requestFocus();
        } else if (tv_camera2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Capture Photograph of the Road", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etApproachRoadWidth.requestFocus();
        } else if (rgClassificationOfLocality.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Classification of Locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvClassification);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgCharacteristicsOfLocality.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Characteristics of Locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvCharac);
            targetView.getParent().requestChildFocus(targetView, targetView);
        } else if (rgCategoryOfLocality.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Category of Locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvCharac);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }/*
        else if (!cbHighEnd.isChecked()
                && !cbNormal.isChecked()
                && !cbAverage.isChecked()
                && !cbAffordable.isChecked()
                && !cbEws.isChecked()
                && !cbHig.isChecked()
                && !cbMig.isChecked()
                && !cbLig.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Category of Locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvCategory);
            targetView.getParent().requestChildFocus(targetView,targetView);
        }*/ else if (!cbCornerPlot.isChecked()
                && !cb3SideOpen.isChecked()
                && !cbAny_Other.isChecked()
                && !cbOnHighway.isChecked()
                && !cbGoodLocationWithinLocality.isChecked()
                && !cbOrdinaryLocationWithinLocality.isChecked()
//                && !cbEntranceNorthEastFacing.isChecked()
                && !cbNeartoHighway.isChecked()
                && !cbNearToMarket.isChecked()
                && !cbNearToMetroStation.isChecked()
                && !cbOnWideRoad.isChecked()
                && !cb2SideOpen.isChecked()
                && !cbParkFacing.isChecked()
                && !cbRoadFacing.isChecked()
                && !cbEntranceNorth.isChecked()
                && !cbSunlightFacing.isChecked()
                && !cbSeaFacing.isChecked()
                && !cbNormalLocation.isChecked()
                && !cbGoodLocation.isChecked()
                && !cbAverageLocation.isChecked()
                && !cbPoorLocation.isChecked()
                && !cbPropertyToward.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Location characteristics of the Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvLocationCharac);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbLifts.isChecked()
                && !cbGarden.isChecked()
                && !cbLandscaping.isChecked()
                && !cbSwimmingPool.isChecked()
                && !cbGym.isChecked()
                && !cbClubHouse.isChecked()
                && !cbWalkingTrails.isChecked()
                && !cbKidsPlay.isChecked()
                && !cb100Power.isChecked()
                && !cbWaterSupplyIssues.isChecked()
                && !cbFrequentPower.isChecked()
                && !cbPoorRoads.isChecked()
                && !cbPoorDrainage.isChecked()
                && !cbParkingIssues.isChecked()
                && !cbNarrowLanes.isChecked()
                && !cbNewlyDevelopingArea.isChecked()
                && !cbLocalMarket.isChecked()
                && !cbNormalArea.isChecked()
                && !cbUtilitiesNone.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Utilities", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvUtilities);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (etSchool.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Proximity to civic amenities: School", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSchool.requestFocus();
        } else if (etHospital.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Proximity to civic amenities: Hospital", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etHospital.requestFocus();
        } else if (etMarket.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Proximity to civic amenities: Market", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMarket.requestFocus();
        } else if (etMetro.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Proximity to civic amenities: Metro", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMetro.requestFocus();
        } else if (etRailwayStation.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Proximity to civic amenities: Railway Station", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRailwayStation.requestFocus();
        } else if (etAirport.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Proximity to civic amenities: Airport", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAirport.requestFocus();
        }
        /*else if (etAnyNewDevelopment.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(next, "Please Enter Any new development in surrounding area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAnyNewDevelopment.requestFocus();
        }*/

        else if (!cbNoNewDev.isChecked() && tvAnyNewDevelopment.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Any new development in surrounding area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvAnyNewDevelopment.requestFocus();
        } else if (rgLocalBodyJurisdiction.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Local Body Jurisdiction limits", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvLocalBody);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgDevelopmentAuthority.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Development Authority Jurisdiction Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvDevAuth);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgMunicipalCorporation.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Municipal Corporation Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvMunicipalCorp);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else {
            putIntoHm();
            ((Dashboard) mActivity).displayView(22);
            return;
        }
    }

    public void putIntoHm() {

        String typeofApproach = String.valueOf(spinnerTypeofApproach.getSelectedItemPosition());
        hm.put(Constants.pageVisited, "vacantForm3");
        hm.put("typeofApproachSpinner", typeofApproach);
        hm.put("postalAddress", etPostalAddress.getText().toString());
        hm.put("confirmLandmark", etConfirmLandmark.getText().toString());
        hm.put("conf_landmark_lat", Double.toString(conf_landmark_lat));
        hm.put("conf_landmark_lng", Double.toString(conf_landmark_lng));
        hm.put("conf_landmark_dis", Double.toString(measuredDistance));
        hm.put("locationWardName", etWardName.getText().toString());
        hm.put("tehsilBlock", etTehsil.getText().toString());
        hm.put("mainRoadName", etMainRoadName.getText().toString());
        hm.put("mainRoadWidth", etMainRoadWidth.getText().toString());
        hm.put("mainRoadDistance", etDistanceFromProp.getText().toString());
        hm.put("approachRoadName", etApproachRoadName.getText().toString());
        hm.put("approachRoadWidth", etApproachRoadWidth.getText().toString());
        hm.put("proximitySchool", etSchool.getText().toString());
        hm.put("proximityHospital", etHospital.getText().toString());
        hm.put("proximityMarket", etMarket.getText().toString());
        hm.put("proximityMetro", etMetro.getText().toString());
        hm.put("proximityRailwayStation", etRailwayStation.getText().toString());
        hm.put("proximityAirport", etAirport.getText().toString());
        // hm.put("anyNewDevelopmentInSurround", etAnyNewDevelopment.getText().toString());
        hm.put("anyNewDevelopmentInSurround", tvAnyNewDevelopment.getText().toString());
        hm.put("anyNewDevelopmentInSurroundUrl", tvAnyNewDevelopmentUrl.getText().toString());
        if (cbNoNewDev.isChecked()) {
            hm.put("noNewDev", "1");
        } else {
            hm.put("noNewDev", "0");
        }
        hm.put("spinCharOfLoc",Integer.toString(spinCharOfLoc));
        hm.put("tv_camera1", tv_camera1.getText().toString());
        hm.put("mainRoadPicBase64", mainRoadPicBase64);
        hm.put("tv_camera2", tv_camera2.getText().toString());
        hm.put("approachRoadBase64", approachRoadBase64);


        int selectedIdCategoryOfLocality = rgCategoryOfLocality.getCheckedRadioButtonId();
        View radioButtonCategoryOfLocality = v.findViewById(selectedIdCategoryOfLocality);
        int idxx = rgCategoryOfLocality.indexOfChild(radioButtonCategoryOfLocality);
        RadioButton rr = (RadioButton) rgCategoryOfLocality.getChildAt(idxx);
        String selectedTextt = rr.getText().toString();
        hm.put("rgCategoryOfLocality", String.valueOf(idxx));

        //CheckBox Location of Flat
        if (cbParkFacing.isChecked()) {
            cbParkFacingCheck = 1;
            hm.put("cbParkFacing", String.valueOf(cbParkFacingCheck));
        } else {
            cbParkFacingCheck = 0;
            hm.put("cbParkFacing", String.valueOf(cbParkFacingCheck));
        }
        if (cbCornerPlot.isChecked()) {
            cbCornerPlotCheck = 1;
            hm.put("cbCornerPlot", String.valueOf(cbCornerPlotCheck));
        } else {
            cbCornerPlotCheck = 0;
            hm.put("cbCornerPlot", String.valueOf(cbCornerPlotCheck));
        }
        if (cbRoadFacing.isChecked()) {
            cbRoadFacingCheck = 1;
            hm.put("cbRoadFacing", String.valueOf(cbRoadFacingCheck));
        } else {
            cbRoadFacingCheck = 0;
            hm.put("cbRoadFacing", String.valueOf(cbRoadFacingCheck));
        }
        if (cbEntranceNorth.isChecked()) {
            cbEntranceNorthCheck = 1;
            hm.put("cbEntranceNorth", String.valueOf(cbEntranceNorthCheck));
        } else {
            cbEntranceNorthCheck = 0;
            hm.put("cbEntranceNorth", String.valueOf(cbEntranceNorthCheck));
        }

        if (cbSunlightFacing.isChecked()) {
            cbSunlightFacingCheck = 1;
            hm.put("cbSunlightFacing", String.valueOf(cbSunlightFacingCheck));
        } else {
            cbSunlightFacingCheck = 0;
            hm.put("cbSunlightFacing", String.valueOf(cbSunlightFacingCheck));
        }
        if (cbSeaFacing.isChecked()) {
            cbSeaFacingCheck = 1;
            hm.put("cbSeaFacing", String.valueOf(cbSeaFacingCheck));
        } else {
            cbSeaFacingCheck = 0;
            hm.put("cbSeaFacing", String.valueOf(cbSeaFacingCheck));
        }
        if (cbNormalLocation.isChecked()) {
            cbNormalLocationCheck = 1;
            hm.put("cbNormalLocation", String.valueOf(cbNormalLocationCheck));
        } else {
            cbNormalLocationCheck = 0;
            hm.put("cbNormalLocation", String.valueOf(cbNormalLocationCheck));
        }


        if (cb3SideOpen.isChecked()) {
            cb3SideOpenCheck = 1;
            hm.put("cb3SideOpen", String.valueOf(cb3SideOpenCheck));
        } else {
            cb3SideOpenCheck = 0;
            hm.put("cb3SideOpen", String.valueOf(cb3SideOpenCheck));
        }  if (cbAny_Other.isChecked()) {
            cbAny_OtherCheck = 1;
            hm.put("cbAny_Other", String.valueOf(cbAny_OtherCheck));
        } else {
            cbAny_OtherCheck = 0;
            hm.put("cbAny_Other", String.valueOf(cbAny_OtherCheck));
        } if (cbOnHighway.isChecked()) {
            cbOnHighwayCheck = 1;
            hm.put("cbOnHighway", String.valueOf(cbOnHighwayCheck));
        } else {
            cbOnHighwayCheck = 0;
            hm.put("cbOnHighway", String.valueOf(cbOnHighwayCheck));
        }

        if (cbGoodLocationWithinLocality.isChecked()) {
            cbGoodLocationWithinLocalityCheck = 1;
            hm.put("cbGoodLocationWithinLocality", String.valueOf(cbGoodLocationWithinLocalityCheck));
        } else {
            cbGoodLocationWithinLocalityCheck = 0;
            hm.put("cbGoodLocationWithinLocality", String.valueOf(cbGoodLocationWithinLocalityCheck));
        }




        if (cbOrdinaryLocationWithinLocality.isChecked()) {
            cbOrdinaryLocationWithinLocalityCheck = 1;
            hm.put("cbOrdinaryLocationWithinLocality", String.valueOf(cbOrdinaryLocationWithinLocalityCheck));
        } else {
            cbOrdinaryLocationWithinLocalityCheck = 0;
            hm.put("cbOrdinaryLocationWithinLocality", String.valueOf(cbOrdinaryLocationWithinLocalityCheck));
        }
//        if (cbEntranceNorthEastFacing.isChecked()) {
//            cbEntranceNorthEastFacingCheck = 1;
//            hm.put("cbEntranceNorthEastFacing", String.valueOf(cbEntranceNorthEastFacingCheck));
//        } else {
//            cbEntranceNorthEastFacingCheck = 0;
//            hm.put("cbEntranceNorthEastFacing", String.valueOf(cbEntranceNorthEastFacingCheck));
//        }
        if (cbNeartoHighway.isChecked()) {
            cbNeartoHighwayCheck = 1;
            hm.put("cbNeartoHighway", String.valueOf(cbNeartoHighway));
        } else {
            cbNeartoHighwayCheck = 0;
            hm.put("cbNeartoHighway", String.valueOf(cbNeartoHighwayCheck));
        }
        if (cbNearToMarket.isChecked()) {
            cbNearToMarketCheck = 1;
            hm.put("cbNearToMarket", String.valueOf(cbNearToMarketCheck));
        } else {
            cbNearToMarketCheck = 0;
            hm.put("cbNearToMarket", String.valueOf(cbNearToMarketCheck));
        }
        if (cbOnWideRoad.isChecked()) {
            cbOnWideRoadCheck = 1;
            hm.put("cbOnWideRoad", String.valueOf(cbOnWideRoadCheck));
        }
        if (cbNearToMetroStation.isChecked()) {
            cbNearToMetroStationCheck = 1;
            hm.put("cbNearToMetroStation", String.valueOf(cbNearToMetroStationCheck));
        } else {
            cbNearToMetroStationCheck = 0;
            hm.put("cbNearToMetroStation", String.valueOf(cbNearToMetroStationCheck));
        }
        if (cbNearToMetroStation.isChecked()) {
            cbNearToMetroStationCheck = 1;
            hm.put("cbNearToMetroStation", String.valueOf(cbNearToMetroStationCheck));
        } else {
            cbOnWideRoadCheck = 0;
            hm.put("cbOnWideRoad", String.valueOf(cbOnWideRoadCheck));
        }
        if (cb2SideOpen.isChecked()) {
            cb2SideOpenCheck = 1;
            hm.put("cb2SideOpen", String.valueOf(cb2SideOpenCheck));
        } else {
            cb2SideOpenCheck = 0;
            hm.put("cb2SideOpen", String.valueOf(cb2SideOpenCheck));
        }

        if (cbGoodLocation.isChecked()) {
            cbGoodLocationCheck = 1;
            hm.put("cbGoodLocation", String.valueOf(cbGoodLocationCheck));
        } else {
            cbGoodLocationCheck = 0;
            hm.put("cbGoodLocation", String.valueOf(cbGoodLocationCheck));
        }
        if (cbAverageLocation.isChecked()) {
            cbAverageLocationCheck = 1;
            hm.put("cbAverageLocation", String.valueOf(cbAverageLocationCheck));
        } else {
            cbAverageLocationCheck = 0;
            hm.put("cbAverageLocation", String.valueOf(cbAverageLocationCheck));
        }
        if (cbPoorLocation.isChecked()) {
            cbPoorLocationCheck = 1;
            hm.put("cbPoorLocation", String.valueOf(cbPoorLocationCheck));
        } else {
            cbPoorLocationCheck = 0;
            hm.put("cbPoorLocation", String.valueOf(cbPoorLocationCheck));
        }
        if (cbPropertyToward.isChecked()) {
            cbPropertyTowardCheck = 1;
            hm.put("cbPropertyToward", String.valueOf(cbPropertyTowardCheck));
        } else {
            cbPropertyTowardCheck = 0;
            hm.put("cbPropertyToward", String.valueOf(cbPropertyTowardCheck));
        }


        if (cbLifts.isChecked()) {
            cbLiftsCheck = 1;
            hm.put("cbLifts", String.valueOf(cbLiftsCheck));
        } else {
            cbLiftsCheck = 0;
            hm.put("cbLifts", String.valueOf(cbLiftsCheck));
        }
        if (cbGarden.isChecked()) {
            cbGardenCheck = 1;
            hm.put("cbGarden", String.valueOf(cbGardenCheck));
        } else {
            cbGardenCheck = 0;
            hm.put("cbGarden", String.valueOf(cbGardenCheck));
        }
        if (cbLandscaping.isChecked()) {
            cbLandscapingCheck = 1;
            hm.put("cbLandscaping", String.valueOf(cbLandscapingCheck));
        } else {
            cbLandscapingCheck = 0;
            hm.put("cbLandscaping", String.valueOf(cbLandscapingCheck));
        }
        if (cbSwimmingPool.isChecked()) {
            cbSwimmingPoolCheck = 1;
            hm.put("cbSwimmingPool", String.valueOf(cbSwimmingPoolCheck));
        } else {
            cbSwimmingPoolCheck = 0;
            hm.put("cbSwimmingPool", String.valueOf(cbSwimmingPoolCheck));
        }
        if (cbGym.isChecked()) {
            cbGymCheck = 1;
            hm.put("cbGym", String.valueOf(cbGymCheck));
        } else {
            cbGymCheck = 0;
            hm.put("cbGym", String.valueOf(cbGymCheck));
        }
        if (cbClubHouse.isChecked()) {
            cbClubHouseCheck = 1;
            hm.put("cbClubHouse", String.valueOf(cbClubHouseCheck));
        } else {
            cbClubHouseCheck = 0;
            hm.put("cbClubHouse", String.valueOf(cbClubHouseCheck));
        }
        if (cbWalkingTrails.isChecked()) {
            cbWalkingTrailsCheck = 1;
            hm.put("cbWalkingTrails", String.valueOf(cbWalkingTrailsCheck));
        } else {
            cbWalkingTrailsCheck = 0;
            hm.put("cbWalkingTrails", String.valueOf(cbWalkingTrailsCheck));
        }
        if (cbKidsPlay.isChecked()) {
            cbKidsPlayCheck = 1;
            hm.put("cbKidsPlay", String.valueOf(cbKidsPlayCheck));
        } else {
            cbKidsPlayCheck = 0;
            hm.put("cbKidsPlay", String.valueOf(cbKidsPlayCheck));
        }
        if (cb100Power.isChecked()) {
            cb100PowerCheck = 1;
            hm.put("cb100Power", String.valueOf(cb100PowerCheck));
        } else {
            cb100PowerCheck = 0;
            hm.put("cb100Power", String.valueOf(cb100PowerCheck));
        }

        if (cbWaterSupplyIssues.isChecked()) {
            cbWaterSupplyIssuesCheck = 1;
            hm.put("cbWaterSupplyIssues", String.valueOf(cbWaterSupplyIssuesCheck));
        } else {
            cbWaterSupplyIssuesCheck = 0;
            hm.put("cbWaterSupplyIssues", String.valueOf(cbWaterSupplyIssuesCheck));
        }
        if (cbFrequentPower.isChecked()) {
            cbFrequentPowerCheck = 1;
            hm.put("cbFrequentPower", String.valueOf(cbFrequentPowerCheck));
        } else {
            cbFrequentPowerCheck = 0;
            hm.put("cbFrequentPower", String.valueOf(cbFrequentPowerCheck));
        }
        if (cbPoorRoads.isChecked()) {
            cbPoorRoadsCheck = 1;
            hm.put("cbPoorRoads", String.valueOf(cbPoorRoadsCheck));
        } else {
            cbPoorRoadsCheck = 0;
            hm.put("cbPoorRoads", String.valueOf(cbPoorRoadsCheck));
        }
        if (cbPoorDrainage.isChecked()) {
            cbPoorDrainageCheck = 1;
            hm.put("cbPoorDrainage", String.valueOf(cbPoorDrainageCheck));
        } else {
            cbPoorDrainageCheck = 0;
            hm.put("cbPoorDrainage", String.valueOf(cbPoorDrainageCheck));
        }
        if (cbParkingIssues.isChecked()) {
            cbParkingIssuesCheck = 1;
            hm.put("cbParkingIssues", String.valueOf(cbParkingIssuesCheck));
        } else {
            cbParkingIssuesCheck = 0;
            hm.put("cbParkingIssues", String.valueOf(cbParkingIssuesCheck));
        }
        if (cbNarrowLanes.isChecked()) {
            cbNarrowLanesCheck = 1;
            hm.put("cbNarrowLanes", String.valueOf(cbNarrowLanesCheck));
        } else {
            cbNarrowLanesCheck = 0;
            hm.put("cbNarrowLanes", String.valueOf(cbNarrowLanesCheck));
        }
        if (cbNewlyDevelopingArea.isChecked()) {
            cbNewlyDevelopingAreaCheck = 1;
            hm.put("cbNewlyDevelopingArea", String.valueOf(cbNewlyDevelopingAreaCheck));
        } else {
            cbNewlyDevelopingAreaCheck = 0;
            hm.put("cbNewlyDevelopingArea", String.valueOf(cbNewlyDevelopingAreaCheck));
        }

        if (cbLocalMarket.isChecked()) {
            cbLocalMarketCheck = 1;
            hm.put("cbLocalMarket", String.valueOf(cbLocalMarketCheck));
        } else {
            cbLocalMarketCheck = 0;
            hm.put("cbLocalMarket", String.valueOf(cbLocalMarketCheck));
        }

        if (cbNormalArea.isChecked()) {
            cbNormalAreaCheck = 1;
            hm.put("cbNormalArea", String.valueOf(cbNormalAreaCheck));
        } else {
            cbNormalAreaCheck = 0;
            hm.put("cbNormalArea", String.valueOf(cbNormalAreaCheck));
        }

        if (cbUtilitiesNone.isChecked()) {
            cbUtilitiesNoneCheck = 1;
            hm.put("cbUtilitiesNone", String.valueOf(cbUtilitiesNoneCheck));
        } else {
            cbUtilitiesNoneCheck = 0;
            hm.put("cbUtilitiesNone", String.valueOf(cbUtilitiesNoneCheck));
        }


        int selectedIdClassificationOfLocality = rgClassificationOfLocality.getCheckedRadioButtonId();
        View radioButtonClassificationOfLocality = v.findViewById(selectedIdClassificationOfLocality);
        int idx = rgClassificationOfLocality.indexOfChild(radioButtonClassificationOfLocality);
        RadioButton r = (RadioButton) rgClassificationOfLocality.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonClassificationOfLocality", selectedText);

        int selectedIdCharacteristic = rgCharacteristicsOfLocality.getCheckedRadioButtonId();
        View radioButtonCharacteristic = v.findViewById(selectedIdCharacteristic);
        int idx2 = rgCharacteristicsOfLocality.indexOfChild(radioButtonCharacteristic);
        RadioButton r2 = (RadioButton) rgCharacteristicsOfLocality.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonCharacteristicOfLocalityy", selectedText2);

        int selectedIdLocalBodyJurisdiction = rgLocalBodyJurisdiction.getCheckedRadioButtonId();
        View radioButtonLocalBodyJurisdiction = v.findViewById(selectedIdLocalBodyJurisdiction);
        int idx3 = rgLocalBodyJurisdiction.indexOfChild(radioButtonLocalBodyJurisdiction);
        RadioButton r3 = (RadioButton) rgLocalBodyJurisdiction.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonLocalBodyJurisdiction", selectedText3);
        hm.put("radioButtonLocalBodyJurisdictionAnyOther", etAreaNotWithinLocal.getText().toString());

        int selectedIdDevelopmentAuthority = rgDevelopmentAuthority.getCheckedRadioButtonId();
        View radioButtonDevelopmentAuthority = v.findViewById(selectedIdDevelopmentAuthority);
        int idx4 = rgDevelopmentAuthority.indexOfChild(radioButtonDevelopmentAuthority);
        RadioButton r4 = (RadioButton) rgDevelopmentAuthority.getChildAt(idx4);
        String selectedText4 = r4.getText().toString();
        hm.put("radioButtonDevelopmentAuthority", selectedText4);

        hm.put("radioButtonDevelopmentAuthorityAnyOther", etAnyOtherDevAut.getText().toString());
        hm.put("radioButtonDevelopmentAuthorityAreaNot", etAreaNotWithinDevelopment.getText().toString());

        int selectedIdMunicipalCorp = rgMunicipalCorporation.getCheckedRadioButtonId();
        View radioButtonMunicipalCorp = v.findViewById(selectedIdMunicipalCorp);
        int idx5 = rgMunicipalCorporation.indexOfChild(radioButtonMunicipalCorp);
        RadioButton r5 = (RadioButton) rgMunicipalCorporation.getChildAt(idx5);
        String selectedText5 = r5.getText().toString();
        hm.put("radioButtonMunicipalCorporation", selectedText5);

        hm.put("radioButtonMunicipalCorporationAnyOther", etAnyOtherMunicipal.getText().toString());
        hm.put("radioButtonMunicipalCorporationAreaNot", etAreaNotWithinMunicipal.getText().toString());

        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        //DatabaseController.removeTable(TableGeneralForm.attachment);
        // DatabaseController.deleteColumnData("column1");

        ContentValues contentValues = new ContentValues();

        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column3.toString(), jsonArrayData.toString());

        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "3");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));


    }

    public void selectDB() throws JSONException {
        sub_cat = DatabaseController.getTableOne("column3", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column3"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if(object.has(Constants.pageVisited)){
                    pageVisited= object.getString(Constants.pageVisited);
                }

                etPostalAddress.setText(object.getString("postalAddress"));
                etConfirmLandmark.setText(object.getString("confirmLandmark"));
                if (object.has("conf_landmark_lat") && object.has("conf_landmark_lng")) {
                    firstTimer = true;
                    conf_landmark_lat = Double.parseDouble(object.getString("conf_landmark_lat"));
                    conf_landmark_lng = Double.parseDouble(object.getString("conf_landmark_lng"));
                }
                if(object.has("spinCharOfLoc")){
                    spinCharOfLoc=Integer.parseInt(object.getString("spinCharOfLoc"));
                    spinnerCharOfLocal.setSelection(spinCharOfLoc);
                }
                else
                    spinCharOfLoc=0;

                etWardName.setText(object.getString("locationWardName"));
                etTehsil.setText(object.getString("tehsilBlock"));
                etMainRoadName.setText(object.getString("mainRoadName"));
                etMainRoadWidth.setText(object.getString("mainRoadWidth"));
                etDistanceFromProp.setText(object.getString("mainRoadDistance"));
                etApproachRoadName.setText(object.getString("approachRoadName"));
                etApproachRoadWidth.setText(object.getString("approachRoadWidth"));

                etAreaNotWithinLocal.setText(object.getString("radioButtonLocalBodyJurisdictionAnyOther"));
                etAnyOtherDevAut.setText(object.getString("radioButtonDevelopmentAuthorityAnyOther"));
                etAnyOtherMunicipal.setText(object.getString("radioButtonMunicipalCorporationAnyOther"));
                etAreaNotWithinDevelopment.setText(object.getString("radioButtonDevelopmentAuthorityAreaNot"));
                etAreaNotWithinMunicipal.setText(object.getString("radioButtonMunicipalCorporationAreaNot"));

                etSchool.setText(object.getString("proximitySchool"));
                etHospital.setText(object.getString("proximityHospital"));
                etMarket.setText(object.getString("proximityMarket"));
                etMetro.setText(object.getString("proximityMetro"));
                etRailwayStation.setText(object.getString("proximityRailwayStation"));
                etAirport.setText(object.getString("proximityAirport"));
                //   etAnyNewDevelopment.setText(object.getString("anyNewDevelopmentInSurround"));
                tvAnyNewDevelopment.setText(object.getString("anyNewDevelopmentInSurround"));
                tvAnyNewDevelopmentUrl.setText(object.getString("anyNewDevelopmentInSurroundUrl"));

                try {
                    if (object.getString("noNewDev").equalsIgnoreCase("1")) {
                        cbNoNewDev.setChecked(true);
                    } else {
                        cbNoNewDev.setChecked(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (object.getString("typeofApproachSpinner").equals("0")) {
                    spinnerTypeofApproach.setSelection(0);
                } else if (object.getString("typeofApproachSpinner").equals("1")) {
                    spinnerTypeofApproach.setSelection(1);
                } else if (object.getString("typeofApproachSpinner").equals("2")) {
                    spinnerTypeofApproach.setSelection(2);
                } else if (object.getString("typeofApproachSpinner").equals("3")) {
                    spinnerTypeofApproach.setSelection(3);
                } else if (object.getString("typeofApproachSpinner").equals("4")) {
                    spinnerTypeofApproach.setSelection(4);
                } else if (object.getString("typeofApproachSpinner").equals("5")) {
                    spinnerTypeofApproach.setSelection(5);
                } else if (object.getString("typeofApproachSpinner").equals("6")) {
                    spinnerTypeofApproach.setSelection(6);
                } else if (object.getString("typeofApproachSpinner").equals("7")) {
                    spinnerTypeofApproach.setSelection(7);
                } else if (object.getString("typeofApproachSpinner").equals("8")) {
                    spinnerTypeofApproach.setSelection(8);
                }


                if (object.getString("cbCornerPlot").equals("1")) {
                    cbCornerPlot.setChecked(true);
                } else {
                    cbCornerPlot.setChecked(false);
                }
                if (object.getString("cb3SideOpen").equals("1")) {
                    cb3SideOpen.setChecked(true);
                } else {
                    cb3SideOpen.setChecked(false);
                }  if (object.getString("cbAny_Other").equals("1")) {
                    cbAny_Other.setChecked(true);
                } else {
                    cbAny_Other.setChecked(false);
                }  if (object.getString("cbOnHighway").equals("1")) {
                    cbOnHighway.setChecked(true);
                } else {
                    cbOnHighway.setChecked(false);
                } if (object.getString("cbGoodLocationWithinLocality").equals("1")) {
                    cbGoodLocationWithinLocality.setChecked(true);
                } else {
                    cbGoodLocationWithinLocality.setChecked(false);
                }  if (object.getString("cbOrdinaryLocationWithinLocality").equals("1")) {
                    cbOrdinaryLocationWithinLocality.setChecked(true);
                } else {
                    cbOrdinaryLocationWithinLocality.setChecked(false);
                }
//                if (object.getString("cbEntranceNorthEastFacing").equals("1")) {
//                    cbEntranceNorthEastFacing.setChecked(true);
//                } else {
//                    cbEntranceNorthEastFacing.setChecked(false);
//                }
                if (object.getString("cbNeartoHighway").equals("1")) {
                    cbNeartoHighway.setChecked(true);
                } else {
                    cbNeartoHighway.setChecked(false);
                }
                if (object.getString("cbNearToMarket").equals("1")) {
                    cbNearToMarket.setChecked(true);
                } else {
                    cbNearToMarket.setChecked(false);
                }
                if (object.getString("cbNearToMetroStation").equals("1")) {
                    cbNearToMetroStation.setChecked(true);
                } else {
                    cbNearToMetroStation.setChecked(false);
                }
                if (object.getString("cbOnWideRoad").equals("1")) {
                    cbOnWideRoad.setChecked(true);
                } else {
                    cbOnWideRoad.setChecked(false);
                }
                if (object.getString("cb2SideOpen").equals("1")) {
                    cb2SideOpen.setChecked(true);
                } else {
                    cb2SideOpen.setChecked(false);
                }
                if (object.getString("cbParkFacing").equals("1")) {
                    cbParkFacing.setChecked(true);
                } else {
                    cbParkFacing.setChecked(false);
                }
                if (object.getString("cbRoadFacing").equals("1")) {
                    cbRoadFacing.setChecked(true);
                } else {
                    cbRoadFacing.setChecked(false);
                }
                if (object.getString("cbEntranceNorth").equals("1")) {
                    cbEntranceNorth.setChecked(true);
                } else {
                    cbEntranceNorth.setChecked(false);
                }
                if (object.getString("cbSunlightFacing").equals("1")) {
                    cbSunlightFacing.setChecked(true);
                } else {
                    cbSunlightFacing.setChecked(false);
                }
                if (object.getString("cbSeaFacing").equals("1")) {
                    cbSeaFacing.setChecked(true);
                } else {
                    cbSeaFacing.setChecked(false);
                }
                if (object.getString("cbNormalLocation").equals("1")) {
                    cbNormalLocation.setChecked(true);
                } else {
                    cbNormalLocation.setChecked(false);
                }
                if (object.getString("cbGoodLocation").equals("1")) {
                    cbGoodLocation.setChecked(true);
                } else {
                    cbGoodLocation.setChecked(false);
                }
                if (object.getString("cbAverageLocation").equals("1")) {
                    cbAverageLocation.setChecked(true);
                } else {
                    cbAverageLocation.setChecked(false);
                }
                if (object.getString("cbPoorLocation").equals("1")) {
                    cbPoorLocation.setChecked(true);
                } else {
                    cbPoorLocation.setChecked(false);
                }
                if (object.getString("cbPropertyToward").equals("1")) {
                    cbPropertyToward.setChecked(true);
                } else {
                    cbPropertyToward.setChecked(false);
                }

                if(object.has("cbLifts"))
                if (object.getString("cbLifts").equals("1")) {
                    cbLifts.setChecked(true);
                } else {
                    cbLifts.setChecked(false);
                }
                if(object.has("cbGarden"))
                    if (object.getString("cbGarden").equals("1")) {
                    cbGarden.setChecked(true);
                } else {
                    cbGarden.setChecked(false);
                }
                if(object.has("cbLandscaping"))
    if (object.getString("cbLandscaping").equals("1")) {
                    cbLandscaping.setChecked(true);
                } else {
                    cbLandscaping.setChecked(false);
                }
                if(object.has("cbSwimmingPool"))
                if (object.getString("cbSwimmingPool").equals("1")) {
                    cbSwimmingPool.setChecked(true);
                } else {
                    cbSwimmingPool.setChecked(false);
                }  if(object.has("cbGym"))
                if (object.getString("cbGym").equals("1")) {
                    cbGym.setChecked(true);
                } else {
                    cbGym.setChecked(false);
                }  if(object.has("cbClubHouse"))
                if (object.getString("cbClubHouse").equals("1")) {
                    cbClubHouse.setChecked(true);
                } else {
                    cbClubHouse.setChecked(false);
                }  if(object.has("cbWalkingTrails"))
                if (object.getString("cbWalkingTrails").equals("1")) {
                    cbWalkingTrails.setChecked(true);
                } else {
                    cbWalkingTrails.setChecked(false);
                }  if(object.has("cbKidsPlay"))
                if (object.getString("cbKidsPlay").equals("1")) {
                    cbKidsPlay.setChecked(true);
                } else {
                    cbKidsPlay.setChecked(false);
                }  if(object.has("cbWaterSupplyIssues"))
                if (object.getString("cbWaterSupplyIssues").equals("1")) {
                    cbWaterSupplyIssues.setChecked(true);
                } else {
                    cbWaterSupplyIssues.setChecked(false);
                }  if(object.has("cbFrequentPower"))
                if (object.getString("cbFrequentPower").equals("1")) {
                    cbFrequentPower.setChecked(true);
                } else {
                    cbFrequentPower.setChecked(false);
                }  if(object.has("cbPoorRoads"))
                if (object.getString("cbPoorRoads").equals("1")) {
                    cbPoorRoads.setChecked(true);
                } else {
                    cbPoorRoads.setChecked(false);
                }  if(object.has("cbPoorDrainage"))
                if (object.getString("cbPoorDrainage").equals("1")) {
                    cbPoorDrainage.setChecked(true);
                } else {
                    cbPoorDrainage.setChecked(false);
                }  if(object.has("cbParkingIssues"))
                if (object.getString("cbParkingIssues").equals("1")) {
                    cbParkingIssues.setChecked(true);
                } else {
                    cbParkingIssues.setChecked(false);
                }  if(object.has("cbNarrowLanes"))
                if (object.getString("cbNarrowLanes").equals("1")) {
                    cbNarrowLanes.setChecked(true);
                } else {
                    cbNarrowLanes.setChecked(false);
                }  if(object.has("cbNewlyDevelopingArea"))
                if (object.getString("cbNewlyDevelopingArea").equals("1")) {
                    cbNewlyDevelopingArea.setChecked(true);
                } else {
                    cbNewlyDevelopingArea.setChecked(false);
                }
                if(object.has("cbLocalMarket"))
                if (object.getString("cbLocalMarket").equals("1")) {
                    cbLocalMarket.setChecked(true);
                } else {
                    cbLocalMarket.setChecked(false);
                }
                if(object.has("cbNormalArea"))
                if (object.getString("cbNormalArea").equals("1")) {
                    cbNormalArea.setChecked(true);
                } else {
                    cbNormalArea.setChecked(false);
                }
                if(object.has("cbUtilitiesNone"))
                if (object.getString("cbUtilitiesNone").equals("1")) {
                    cbUtilitiesNone.setChecked(true);
                } else {
                    cbUtilitiesNone.setChecked(false);
                }


                if (object.getString("radioButtonClassificationOfLocality").equals("Urban developed")) {
                    ((RadioButton) rgClassificationOfLocality.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonClassificationOfLocality").equals("Urban developing")) {
                    ((RadioButton) rgClassificationOfLocality.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonClassificationOfLocality").equals("Urban Village")) {
                    ((RadioButton) rgClassificationOfLocality.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonClassificationOfLocality").equals("Semi Urban")) {
                    ((RadioButton) rgClassificationOfLocality.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonClassificationOfLocality").equals("Rural")) {
                    ((RadioButton) rgClassificationOfLocality.getChildAt(4)).setChecked(true);
                } else if (object.getString("radioButtonClassificationOfLocality").equals("Backward")) {
                    ((RadioButton) rgClassificationOfLocality.getChildAt(5)).setChecked(true);
                } else if (object.getString("radioButtonClassificationOfLocality").equals("Industrial")) {
                    ((RadioButton) rgClassificationOfLocality.getChildAt(6)).setChecked(true);
                } else if (object.getString("radioButtonClassificationOfLocality").equals("Institutional")) {
                    ((RadioButton) rgClassificationOfLocality.getChildAt(7)).setChecked(true);
                }

                if (object.getString("rgCategoryOfLocality").equals("0")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(0)).setChecked(true);
                } else if (object.getString("rgCategoryOfLocality").equals("1")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(1)).setChecked(true);
                } else if (object.getString("rgCategoryOfLocality").equals("2")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(2)).setChecked(true);
                } else if (object.getString("rgCategoryOfLocality").equals("3")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(3)).setChecked(true);
                } else if (object.getString("rgCategoryOfLocality").equals("4")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(4)).setChecked(true);
                } else if (object.getString("rgCategoryOfLocality").equals("5")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(5)).setChecked(true);
                } else if (object.getString("rgCategoryOfLocality").equals("6")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(6)).setChecked(true);
                } else if (object.getString("rgCategoryOfLocality").equals("7")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(7)).setChecked(true);
                } else if (object.getString("rgCategoryOfLocality").equals("8")) {
                    ((RadioButton) rgCategoryOfLocality.getChildAt(8)).setChecked(true);
                }


                if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within Main City")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within Good Urban developed Area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within developing area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within Urban undeveloped area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within City suburbs")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(4)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within urban remote area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(5)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Highly posh locality")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(6)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("In interiors")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(7)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Remote area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(8)).setChecked(true);

                }
                //Backward
                else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Notified Industrial Area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(9)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within High end commercial market")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(10)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within high end Institutional zone")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(11)).setChecked(true);
                }


                else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within urban developed Area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(12)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within urban developing zone")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(13)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within urban remote area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(14)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within posh residential locality")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(15)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within developing residential zone")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(16)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within ordinary mid-scale residential locality")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(17)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within low scale residential locality")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(18)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within unregularised residential locality")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(19)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within unauthorized colony")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(20)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within posh commercial market")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(21)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within congested commercial market")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(22)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within clustered commercial activity market")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(23)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within high foot fall commercial market")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(24)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within well-developed notified Industrial area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(25)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within averagely maintained Industrial area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(26)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within unnotified Industrial area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(27)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within Institutional area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(28)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Out of municipal limits")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(29)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("no civic infrastructure available")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(30)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within good village area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(31)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("With backward village area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(32)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within Backward area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(33)).setChecked(true);
                }else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Within Remote area")) {
                    ((RadioButton) rgCharacteristicsOfLocality.getChildAt(34)).setChecked(true);
                }

                /*
                else if (object.getString("radioButtonCharacteristicOfLocalityy").equals("Normal area with no modern facilities")){
                    ((RadioButton)rgCharacteristicsOfLocality.getChildAt(17)).setChecked(true);
                }*/


                if (object.getString("radioButtonLocalBodyJurisdiction").equals("Nagar Nigam")) {
                    ((RadioButton) rgLocalBodyJurisdiction.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonLocalBodyJurisdiction").equals("Nagar Palika Parishad")) {
                    ((RadioButton) rgLocalBodyJurisdiction.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonLocalBodyJurisdiction").equals("Nagar Panchayat")) {
                    ((RadioButton) rgLocalBodyJurisdiction.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonLocalBodyJurisdiction").equals("Gram Panchayat")) {
                    ((RadioButton) rgLocalBodyJurisdiction.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonLocalBodyJurisdiction").equals("Area not within any municipal limits")) {
                    ((RadioButton) rgLocalBodyJurisdiction.getChildAt(4)).setChecked(true);
                }


                if (object.getString("radioButtonDevelopmentAuthority").equals("DDA")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("GDA")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("NOIDA")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("GNIDA")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("YEIDA")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(4)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("HUDA")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(5)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("KMDA")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(6)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("MDDA")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(7)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("Any other Development Authority")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(8)).setChecked(true);
                } else if (object.getString("radioButtonDevelopmentAuthority").equals("Area not within any development authority limits")) {
                    ((RadioButton) rgDevelopmentAuthority.getChildAt(10)).setChecked(true);
                }


                if (object.getString("radioButtonMunicipalCorporation").equals("NDMC")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("SDMC")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("EDMC")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("Ghaziabad Municipal Corporation")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("Gurgaon Municipal Corporation")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(4)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("Faridabad Municipal Corporation")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(5)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("Kolkata Municipal Corporation")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(6)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("Dehradun Municipal Corporation")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(7)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("Area not within any municipal limits")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(8)).setChecked(true);
                } else if (object.getString("radioButtonMunicipalCorporation").equals("Any other Municipal Corporation/ Municipality")) {
                    ((RadioButton) rgMunicipalCorporation.getChildAt(10)).setChecked(true);
                }


                try {
                    approachRoadBase64 = object.getString("approachRoadBase64");
                    tv_camera2.setText(object.getString("tv_camera2"));

                    byte[] byteFormat = Base64.decode(object.getString("approachRoadBase64"), Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);

                    ivCameraCapture2.setVisibility(View.VISIBLE);
                    ivCameraCapture2.setImageBitmap(decodedImage);

                    mainRoadPicBase64 = object.getString("mainRoadPicBase64");
                    tv_camera1.setText(object.getString("tv_camera1"));

                    byteFormat = Base64.decode(object.getString("mainRoadPicBase64"), Base64.DEFAULT);
                    decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);

                    ivCameraCapture1.setVisibility(View.VISIBLE);
                    ivCameraCapture1.setImageBitmap(decodedImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //  hm.put("mainRoadPicBase64", mainRoadPicBase64);
                //  hm.put("approachRoadBase64", approachRoadBase64);

                // String projectName = object.getString("projectName");
                // Log.e("!!!projectName", projectName);
            }

        }
    }

    private void populateSinner() {

        //Spinner TypeofApproachRoad
        spinnerAdapter1 = new SpinnerListAdapter(mActivity, typeOfApproachArray);
        spinnerTypeofApproach.setAdapter(spinnerAdapter1);

        spinnerTypeofApproach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
//                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
//                if (spinnerTypeofApproach.getSelectedItemPosition() == 0) {
//                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAdapter = new SpinnerAdapter(mActivity,arrayCharOfLocal);
        spinnerCharOfLocal.setAdapter(spinnerAdapter);
        spinnerCharOfLocal.setSelection(spinCharOfLoc);
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
                        ((Dashboard) mActivity).displayView(12);
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

    private void captureImage() {
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(mActivity, R.string.camera_permission_required, Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        } else {

            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        //   return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".provider", getOutputMediaFile(type));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                picturePath = fileUri.getPath();
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

                encodedString = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                if (captureType == 1) {

                    tv_camera1.setText(filename);
                } else if (captureType == 2) {

                    tv_camera2.setText(filename);
                }

                setPictures(rotatedBitmap, setPic, encodedString);

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

            } else {
                Toast.makeText(mActivity, "unable to select image", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setPictures(Bitmap b, String setPic, String base64) {

        if (captureType == 1) {

            mainRoadPicBase64 = base64;

            ivCameraCapture1.setVisibility(View.VISIBLE);
            ivCameraCapture1.setImageBitmap(b);
        } else if (captureType == 2) {

            approachRoadBase64 = base64;

            ivCameraCapture2.setVisibility(View.VISIBLE);
            ivCameraCapture2.setImageBitmap(b);
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

    public LatLng getLatLongFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    //*******************************************************Map************************************************************************************************************
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (firstTimer) {
            setLandMark(conf_landmark_lat, conf_landmark_lng);
            firstTimer = false;
        }

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //mMap.setMyLocationEnabled(true);


    }

    private void setLandMark(Double latitude, Double longitude) {
//        if (!pref.get(Constants.landmark_lat).isEmpty() &&
//                !pref.get(Constants.landmark_long).isEmpty()){

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getAddress(mActivity, latitude, longitude));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);
//            geocoder = new Geocoder(mActivity,Locale.CANADA);
        geocoder = new Geocoder(mActivity, Locale.getDefault());
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16f);
        mMap.animateCamera(cameraUpdate1);
        getDistance(latitude, longitude);

//        measuredDistance = Utils.calculateDistance(latitude,longitude,Double.valueOf(pref.get(Constants.landmark_lat)),Double.valueOf(pref.get(Constants.landmark_long)));
//
//        if (measuredDistance >= 1000){
//
//            Log.v("measureDist",measuredDistance/1000+" km");
//
//            double inKm = measuredDistance/1000;
//
//            tv_MapDistance.setText("Distance =: "+String.format("%.2f",inKm)+" km");
//        }else {
//            //   Log.v("measureDist",measuredDistance+" m");
//            tv_MapDistance.setText("Distance = "+String.format("%.2f",measuredDistance)+" m");
//        }

//        }
    }

    private void getDistance(Double latitude, Double longitude) {
        String url="";
        try {
            url = makeURL(latitude, longitude, Double.valueOf(pref.get(Constants.landmark_lat)), Double.valueOf(pref.get(Constants.landmark_long)));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.v("sitanceUrl", url);


        AndroidNetworking.post(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.v("respDistance", String.valueOf(response));

                        JSONObject jsonRespRouteDistance = null;
                        try {
                            jsonRespRouteDistance = new JSONObject(String.valueOf(response))
                                    .getJSONArray("rows")
                                    .getJSONObject(0)
                                    .getJSONArray("elements")
                                    .getJSONObject(0)
                                    .getJSONObject("distance");
                            String distance = jsonRespRouteDistance.get("text").toString();
                            tv_MapDistance.setText(distance);
                            distance = distance.replace("km", "");
                            if (distance.equalsIgnoreCase("")) {
                                tv_MapDistance.setText("Route Not Available");

                            }
                            Log.v("distanceee", distance);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    //for getting distance from google distance matrix api
    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/distancematrix/json");
        urlString.append("?origins=");// from
        urlString.append(sourcelat);
        urlString.append(",");
        urlString.append(sourcelog);
        urlString.append("&destinations=");// to
        urlString.append(destlat);
        urlString.append(",");
        urlString.append(destlog);
        urlString.append("&mode=driving");
        /*urlString.append("&sensor=false&mode=driving&alternatives=true");*/
        urlString.append("&key=AIzaSyALTsVJy_Py_INw-TFmLqdU9lGHQHiqX2k");
        return urlString.toString();
    }

//*******************************************************Map************************************************************************************************************

    private List<Address> getNearbyAddresses() {
        Geocoder geocoder = new Geocoder(mActivity, Locale.getDefault());

        // Address found using the Geocoder.
        List<Address> addresses = null;

        double mLatitude = getLatLongFromAddress(mActivity, pref.get(Constants.assetAddress)).latitude;
        double mLongitude = getLatLongFromAddress(mActivity, pref.get(Constants.assetAddress)).longitude;


        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    mLatitude,
                    mLongitude,
                    // In this sample, we get just a single address.
                    10);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            //errorMessage = getString(R.string.service_not_available);
            // Log.e(TAG, errorMessage, ioException);

        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            // errorMessage = getString(R.string.invalid_lat_long_used);

/*
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " + location.getLongitude(), illegalArgumentException);
*/
        }

        if (addresses != null) {
            Log.v("###nearby", addresses.toString());
        }

        return addresses;
        // Handle case where no address was found.
      /*  if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }

            deliverResultToReceiver(MapUtils.LocationConstants.FAILURE_RESULT, null);

        } else {

            deliverResultToReceiver(MapUtils.LocationConstants.SUCCESS_RESULT, addresses);
            //TextUtils.split(TextUtils.join(System.getProperty("line.separator"), addressFragments), System.getProperty("line.separator"));

        }*/
    }

    public void AlertAddressedPopup() {
        dialog = new Dialog(mActivity, R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.alert_addresses_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView close;
        RecyclerView recyclerView;
        Button btnSubmit;

        close = dialog.findViewById(R.id.iv_close);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        removeDuplicate();

        googleSearchAdapter = new GoogleSearchAdapter(dataListGoogleSearch);
        recyclerView.setAdapter(googleSearchAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void removeDuplicate() {
        try {

            for (int i = 0; i < dataListGoogleSearch.size(); i++) {

                for (int j = i + 1; j < dataListGoogleSearch.size(); j++) {

                    if (dataListGoogleSearch.get(i).get("title").equalsIgnoreCase(dataListGoogleSearch.get(j).get("title"))) {

                        Log.v("duplicate 1", dataListGoogleSearch.get(i).get("title"));
                        Log.v("duplicate 2", dataListGoogleSearch.get(j).get("title"));

                        dataListGoogleSearch.remove(i);

                        //  break;
                    } else {

                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hitSearchApi
    private void hitSearchApi(final String types, String searchKeyword) {

        adapterSearchSchool.clear();

        loader.setCanceledOnTouchOutside(false);
        loader.setCancelable(false);
        loader.show();

        //  String url = Utils.getCompleteApiUrl(this, R.string.GetGeneralSurveyInfo);
        Log.v("hitSearchApi", "hit");

        double mLatitude = getLatLongFromAddress(mActivity, pref.get(Constants.assetAddress)).latitude;
        double mLongitude = getLatLongFromAddress(mActivity, pref.get(Constants.assetAddress)).longitude;

        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=25.9548043,80.15262709999999&radius=5000&type=school&keyword=New%20Delhi%20Public&key=AIzaSyC95vkobBiC5UhZn4ChUnKWz8cH6ECbb5s

        AndroidNetworking.post("https://maps.googleapis.com/maps/api/place/nearbysearch/json?&location=" + mLatitude + "," + mLongitude + "&radius=5000&types=" + types + "&keyword=" + searchKeyword + "&key=AIzaSyALTsVJy_Py_INw-TFmLqdU9lGHQHiqX2k")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonSeacrh(response, types);
                    }

                    @Override
                    public void onError(ANError error) {

                        // handle error
                        if (error.getErrorCode() != 0) {

                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {

                            //   Toast.makeText(MapLocatorActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonSeacrh(JSONObject response, String type) {

        Log.v("res:hitSearchApi", response.toString());

        try {
            if (response.getString("status").equalsIgnoreCase("OK")) {

                JSONArray jsonArrayResult = response.getJSONArray("results");

                for (int i = 0; i < jsonArrayResult.length(); i++) {
                    JSONObject jsonObject = jsonArrayResult.getJSONObject(i);

                    String types = jsonObject.getJSONArray("types").getString(0);

                    if (type.equalsIgnoreCase("school")) {
                        if (types.equalsIgnoreCase(type)) {

                            dataListSearchSchool.add(jsonObject.getString("name"));
                        }
                    } else if (type.equalsIgnoreCase("hospital")) {
                        if (types.equalsIgnoreCase(type)) {

                            dataListSearchHospital.add(jsonObject.getString("name"));
                        }
                    } else if (type.equalsIgnoreCase("airport")) {
                        if (types.equalsIgnoreCase(type)) {

                            dataListSearchAirport.add(jsonObject.getString("name"));
                        }
                    } else if (type.equalsIgnoreCase("train_station")) {
                        if (types.equalsIgnoreCase(type)) {

                            dataListSearchRailway.add(jsonObject.getString("name"));
                        }
                    } else if (type.equalsIgnoreCase("subway_station")) {
                        if (types.equalsIgnoreCase(type)) {

                            dataListSearchMetro.add(jsonObject.getString("name"));
                        }
                    } else if (type.equalsIgnoreCase("market")) {
                        if (types.equalsIgnoreCase(type)) {

                            dataListSearchMarket.add(jsonObject.getString("name"));
                        }
                    }

                }

                if (type.equalsIgnoreCase("school")) {
                    adapterSearchSchool = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchSchool);
                    etSchool.setThreshold(1);
                    etSchool.setAdapter(adapterSearchSchool);
                    adapterSearchSchool.notifyDataSetChanged();
                    etSchool.setSelection(etSchool.getText().toString().length());
                } else if (type.equalsIgnoreCase("hospital")) {
                    adapterSearchHospital = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchHospital);
                    etHospital.setThreshold(1);
                    etHospital.setAdapter(adapterSearchHospital);
                    adapterSearchHospital.notifyDataSetChanged();
                    etHospital.setSelection(etHospital.getText().toString().length());
                } else if (type.equalsIgnoreCase("market")) {
                    adapterSearchMarket = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchMarket);
                    etMarket.setThreshold(1);
                    etMarket.setAdapter(adapterSearchMarket);
                    adapterSearchMarket.notifyDataSetChanged();
                    etMarket.setSelection(etMarket.getText().toString().length());
                } else if (type.equalsIgnoreCase("airport")) {
                    adapterSearchAirport = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchAirport);
                    etAirport.setThreshold(1);
                    etAirport.setAdapter(adapterSearchAirport);
                    adapterSearchAirport.notifyDataSetChanged();
                    etAirport.setSelection(etAirport.getText().toString().length());
                } else if (type.equalsIgnoreCase("train_station")) {
                    adapterSearchRailway = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchRailway);
                    etRailwayStation.setThreshold(1);
                    etRailwayStation.setAdapter(adapterSearchRailway);
                    adapterSearchRailway.notifyDataSetChanged();
                    etRailwayStation.setSelection(etRailwayStation.getText().toString().length());
                } else if (type.equalsIgnoreCase("subway_station")) {
                    adapterSearchMetro = new ArrayAdapter<String>(mActivity, android.R.layout.simple_dropdown_item_1line, dataListSearchMetro);
                    etMetro.setThreshold(1);
                    etMetro.setAdapter(adapterSearchMetro);
                    adapterSearchMetro.notifyDataSetChanged();
                    etMetro.setSelection(etMetro.getText().toString().length());
                }


            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loader.cancel();

    }

    //hitGoogleCustomSearchApi
    private void hitGoogleCustomSearch(String searchText, final int type) {

        // dataListGoogleSearch.clear();
//        dataListGoogleSearch1.clear();
//        dataListGoogleSearch2.clear();
//        dataListGoogleSearch3.clear();

        loader.setCanceledOnTouchOutside(false);
        loader.setCancelable(false);
        loader.show();

        //  String url = Utils.getCompleteApiUrl(this, R.string.GetGeneralSurveyInfo);
        Log.v("hitGoogleCustomSearch", "hit");


        //  cx:   007405392111555177011:15dg8bxp2de
        //    //GET https://www.googleapis.com/customsearch/v1?key=AIzaSyC95vkobBiC5UhZn4ChUnKWz8cH6ECbb5s&cx=007405392111555177011:15dg8bxp2de&q=new local development projects near preet vihar delhi

        AndroidNetworking.get("https://www.googleapis.com/customsearch/v1?key=AIzaSyALTsVJy_Py_INw-TFmLqdU9lGHQHiqX2k&cx=007405392111555177011:15dg8bxp2de&q="
                + searchText + " " + pref.get(Constants.colony) + " " + pref.get(Constants.cityVillageTehsil) + " " + pref.get(Constants.district))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonGoogle(response, type);
                    }

                    @Override
                    public void onError(ANError error) {

                        // handle error
                        if (error.getErrorCode() != 0) {

                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {

                            //   Toast.makeText(MapLocatorActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonGoogle(JSONObject response, int type) {

        Log.v("res:hitGoogleCustomSear", response.toString());

        try {
            if (response.has("items") && response.getJSONArray("items") != null) {

                JSONArray jsonArrayItems = response.getJSONArray("items");

                for (int i = 0; i < jsonArrayItems.length(); i++) {
                    JSONObject jsonObject = jsonArrayItems.getJSONObject(i);
                    JSONArray jsonArray = null;

                    HashMap hashMap = new HashMap();

                    if (jsonObject.has("pagemap")) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("pagemap");

                        if (jsonObject1.has("cse_thumbnail")) {

                            jsonArray = jsonObject1.getJSONArray("cse_thumbnail");
                            hashMap.put("imageUrl", jsonArray.getJSONObject(0).getString("src"));
                        } else {
                            hashMap.put("imageUrl", "");
                        }

                    } else {
                        hashMap.put("imageUrl", "");
                    }


                    hashMap.put("title", jsonObject.getString("title"));
                    hashMap.put("link", jsonObject.getString("link"));
                    hashMap.put("description", jsonObject.getString("snippet"));

                   /* if (type == 1){
                        dataListGoogleSearch1.add(hashMap);
                    } else if (type == 2){
                        dataListGoogleSearch2.add(hashMap);
                    } else if (type == 3){
                        dataListGoogleSearch3.add(hashMap);
                    }*/

                    dataListGoogleSearch.add(hashMap);
                }

                //  googleSearchAdapter = new GoogleSearchAdapter(dataListGoogleSearch);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loader.cancel();

    }

    private static class HolderReferredDocuments extends RecyclerView.ViewHolder {

        TextView tvTitle, tvUrl, tvDescription;
        RelativeLayout rlMain;
        ImageView ivImage;

        public HolderReferredDocuments(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvUrl = itemView.findViewById(R.id.tvUrl);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            rlMain = itemView.findViewById(R.id.rlMain);

        }
    }

    public class SpinnerListAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> alist = new ArrayList<>();
        LayoutInflater inflter;

        TextView tvSpinnerText;


        public SpinnerListAdapter(Context applicationContext, ArrayList<String> alist) {
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
//        convertView = inflter.inflate(R.layout.spinnerlayout, null);
//        tvSpinnerText = (TextView) convertView.v.findViewById(R.id.tvList);
//        tvSpinnerText.setText(alist[position]);
            convertView = inflter.inflate(R.layout.profession_adapter, null);
            TextView name = convertView.findViewById(R.id.tv_profession);
            name.setText(alist.get(position));
            return convertView;

            //            profession.setSelection(Integer.parseInt(profession_array_list.get(position).get("id")));
        }
    }

    public class GoogleSearchAdapter extends RecyclerView.Adapter<HolderReferredDocuments> {
        ArrayList<HashMap<String, String>> alist = new ArrayList<>();

        public GoogleSearchAdapter(ArrayList<HashMap<String, String>> banner) {
            alist = banner;
        }

        public HolderReferredDocuments onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderReferredDocuments(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_addresses_layout, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderReferredDocuments holder, final int position) {

            holder.tvTitle.setText(alist.get(position).get("title"));
            holder.tvUrl.setText(alist.get(position).get("link"));
            holder.tvDescription.setText(alist.get(position).get("description"));

            try {
                Picasso.with(mActivity).load(alist.get(position).get("imageUrl")).into(holder.ivImage);
            } catch (Exception e) {
                e.printStackTrace();
            }


            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  etAnyNewDevelopment.setText(alist.get(position).get("title"));
                    tvAnyNewDevelopment.setText(alist.get(position).get("title"));
                    tvAnyNewDevelopmentUrl.setText(alist.get(position).get("link"));
                    dialog.dismiss();
                }
            });

        }

        public int getItemCount() {
            return alist.size();
        }
    }

//    public class CustomAutoCompleteAdapter extends ArrayAdapter {
//        public static final String TAG = "CustomAutoCompAdapter";
//        private List<Place> dataList;
//        private Context mContext;
//        private GeoDataClient geoDataClient;
//
//        private CustomAutoCompleteAdapter.CustomAutoCompleteFilter listFilter =
//                new CustomAutoCompleteAdapter.CustomAutoCompleteFilter();
//
//        private TextView country;
//        //   private Spinner placeType;
//        //   private int[] placeTypeValues;
//
//        public CustomAutoCompleteAdapter(Context context) {
//            super(context, android.R.layout.simple_dropdown_item_1line, new ArrayList<Place>());
//            mContext = context;
//
//            //get GeoDataClient
//            geoDataClient = Places.getGeoDataClient(mContext);
//
//        }
//
//        @Override
//        public int getCount() {
//            return dataList.size();
//        }
//
//        @Override
//        public Place getItem(int position) {
//            return dataList.get(position);
//        }
//
//        @Override
//        public View getView(int position, View view, @NonNull ViewGroup parent) {
//
//            if (view == null) {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(android.R.layout.simple_dropdown_item_1line,
//                                parent, false);
//            }
//
//            TextView textOne = view.findViewById(android.R.id.text1);
//            textOne.setText(dataList.get(position).getPlaceText());
//
//            etConfirmLandmark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    Utils.hideSoftKeyboard(mActivity);
//
//                    geoDataClient.getPlaceById(dataList.get(position).getPlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
//                        @Override
//                        public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
//                            if (task.isSuccessful()) {
//                                PlaceBufferResponse places = task.getResult();
//                                com.google.android.gms.location.places.Place myPlace = places.get(0);
//                                Log.i(TAG, "Place found: " + myPlace.getLatLng());
//
//                                String str = String.valueOf(myPlace.getLatLng());
//                                Log.i(TAG, "str: " + myPlace.getLatLng());
//                                str = str.replaceAll("lat/lng:","").trim();
//                                str = str.replaceAll("\\(","").trim();
//                                str = str.replaceAll("\\)","").trim();
//                                Log.i(TAG, "str: " + str);
//
//                                if(str.contains(","))
//                                {
//
//                                    String[] splitAddress = str.split(",");
//                                    Location mLocation = new Location("");
//                                    mLocation.setLatitude(Double.parseDouble(splitAddress[0]));
//                                    mLocation.setLongitude(Double.parseDouble(splitAddress[1]));
//                                  //  currentLocation = mLocation;
//                                    //changeMap(mLocation);
//                                   // startIntentService(mLocation);
//
//                                }
//
//                                places.release();
//                            } else {
//                                Log.e(TAG, "Place not found.");
//                            }
//                        }
//                    });
//                }
//            });
//
//            return view;
//        }
//
//        @NonNull
//        @Override
//        public Filter getFilter() {
//            return listFilter;
//        }
//
//        public class CustomAutoCompleteFilter extends Filter {
//            private Object lock = new Object();
//            private Object lockTwo = new Object();
//            private boolean placeResults = false;
//
//            @Override
//            protected FilterResults performFiltering(CharSequence prefix) {
//                FilterResults results = new FilterResults();
//                placeResults = false;
//                final List<Place> placesList = new ArrayList<>();
//
//                if (prefix == null || prefix.length() == 0) {
//                    synchronized (lock) {
//                        results.values = new ArrayList<Place>();
//                        results.count = 0;
//                    }
//                } else {
//                    final String searchStrLowerCase = prefix.toString().toLowerCase();
//
//                    Task<AutocompletePredictionBufferResponse> task
//                            = getAutoCompletePlaces(searchStrLowerCase);
//
//                    task.addOnCompleteListener(new OnCompleteListener<AutocompletePredictionBufferResponse>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AutocompletePredictionBufferResponse> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "Auto complete prediction successful");
//                                AutocompletePredictionBufferResponse predictions = task.getResult();
//
//                                Place autoPlace;
//                                for (AutocompletePrediction prediction : predictions) {
//                                    autoPlace = new Place();
//                                    autoPlace.setPlaceId(prediction.getPlaceId());
//                                    autoPlace.setPlaceText(prediction.getFullText(null).toString());
//                                    autoPlace.setPrimaryText(prediction.getPrimaryText(null).toString());
//                                    placesList.add(autoPlace);
//                                }
//                                predictions.release();
//                                Log.d(TAG, "Auto complete predictions size " + placesList.size());
//                            } else {
//                                Log.d(TAG, String.valueOf(task.getException()));
//                                Log.d(TAG, "Auto complete prediction unsuccessful");
//                            }
//                            //inform waiting thread about api call completion
//                            placeResults = true;
//                            synchronized (lockTwo) {
//                                lockTwo.notifyAll();
//                            }
//                        }
//                    });
//
//                    //wait for the results from asynchronous API call
//                    while (!placeResults) {
//                        synchronized (lockTwo) {
//                            try {
//                                lockTwo.wait();
//                            } catch (InterruptedException e) {
//
//                            }
//                        }
//                    }
//                    results.values = placesList;
//                    results.count = placesList.size();
//                    Log.d(TAG, "Autocomplete predictions size after wait" + results.count);
//                }
//
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                if (results.values != null) {
//                    dataList = (ArrayList<Place>) results.values;
//                } else {
//                    dataList = null;
//                }
//                if (results.count > 0) {
//                    notifyDataSetChanged();
//                } else {
//                    notifyDataSetInvalidated();
//                }
//            }
//
//            private Task<AutocompletePredictionBufferResponse> getAutoCompletePlaces(String query) {
//                //create autocomplete filter using data from filter Views
//                AutocompleteFilter.Builder filterBuilder = new AutocompleteFilter.Builder();
//                //  filterBuilder.setCountry(country.getText().toString());
//                // filterBuilder.setTypeFilter(placeTypeValues[placeType.getSelectedItemPosition()]);
//
//                Task<AutocompletePredictionBufferResponse> results =
//                        geoDataClient.getAutocompletePredictions(query, null,
//                                filterBuilder.build());
//
//                return results;
//            }
//        }
//    }

    //    public class Place {
//        private String placeId;
//        private String placeText;
//        private String primaryText;
//
//        public String getPlaceId() {
//            return placeId;
//        }
//
//        public void setPlaceId(String placeId) {
//            this.placeId = placeId;
//        }
//
//        public String getPlaceText() {
//            return placeText;
//        }
//
//        public void setPlaceText(String placeText) {
//            this.placeText = placeText;
//        }
//
//        public String getPrimaryText() {
//            return primaryText;
//        }
//
//        public void setPrimaryText(String primaryText) {
//            this.primaryText = primaryText;
//        }
//        public String toString(){
//            return placeText;
//        }
//    }
    private class AutocompleteAdapter extends RecyclerView.Adapter<AutocompleteAdapter.MyViewHolder> {

        Activity activity;
        ArrayList<HashMap<String, String>> resultList;


        public AutocompleteAdapter(Activity mActivity, ArrayList<HashMap<String, String>> suggestionsList) {

            activity = mActivity;
            resultList = suggestionsList;

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_autocomplete, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AutocompleteAdapter.MyViewHolder holder, final int i) {

            holder.tvAddress.setText(resultList.get(i).get("fullAdd"));
            //    holder.tvSubAddress.setText(resultList.get(i).get("primaryAdd"));

            holder.llRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AutocompletePrediction selectedPrediction = predictionList.get(i);
                    final String placeId = selectedPrediction.getPlaceId();
                    List<com.google.android.libraries.places.api.model.Place.Field> placeFields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.LAT_LNG);

                    FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                    placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @Override
                        public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                            com.google.android.libraries.places.api.model.Place place = fetchPlaceResponse.getPlace();
                            LatLng latLngOfPlace = place.getLatLng();
                            conf_landmark_lat = latLngOfPlace.latitude;
                            conf_landmark_lng = latLngOfPlace.longitude;
                            mMap.clear();
                            setLandMark(conf_landmark_lat, conf_landmark_lng);
                            etConfirmLandmark.setText(resultList.get(i).get("fullAdd"));
                            Log.v("adapterarray", resultList.get(i).get("fullAdd"));
                            rvAutocomplete.setVisibility(View.GONE);


                        }
                    });
                }
            });

//            Log.v("adapterarray",resultList.get(i).get("fullAdd"));

        }

        @Override
        public int getItemCount() {
            return resultList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvAddress, tvSubAddress;
            LinearLayout llRow;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tvAddress = itemView.findViewById(R.id.tvAddresss);
//                tvSubAddress=itemView.findViewById(R.id.tvSubAddress);

                llRow = itemView.findViewById(R.id.llRow);
            }
        }
    }
}
