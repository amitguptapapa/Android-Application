package com.vis.android.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
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
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.GalleryActivity;
import com.vis.android.Activities.ScheduleActivity;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.HttpFileUploader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.Extras.GetBackFragment;
import com.vis.android.Extras.GetLocation;
import com.vis.android.Extras.MySupportMapFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.vis.android.Fragments.CaseDetail.case_id;

public class PriliminaryActivity extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, OnMapReadyCallback {
    private static final String IMAGE_DIRECTORY_NAME = "VIS";
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2, FILE_SELECT_CODE = 0;
    //private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    public static ArrayList<HashMap<String, String>> property_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> surveyor_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> unit_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> temp_unit_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> doc_list_referred = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> dropdownVal1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> dropdownVal2 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> stateList = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> districtList = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> cityList = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> tehsilList = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> villageList = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> co_number_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<String> documentImagesList = new ArrayList<String>();
    public static ArrayList<String> captionList = new ArrayList<String>();

    public static ArrayList<HashMap<String, String>> assest_nature_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<String> nature_list = new ArrayList<String>();
    public static ArrayList<HashMap<String, String>> assest_category_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<String> category_list = new ArrayList<String>();
    public static ArrayList<HashMap<String, String>> assest_type_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<String> asset_list = new ArrayList<String>();
    public static String caption = "";
    public static int from = 0;
    public static Double Lat, Lng;
    public String owner_name2 = "", colonyArea = "", address2 = "", picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", encodedString = "", encodedString1 = "", encodedString2 = "", encodedString3 = "", encodedString4 = "", encodedString5 = "", setPic = "";
    public String titleLand_area = "", titleLand_status = "0", mapLand_area = "", mapLand_status = "0", titleCovered_area = "", titleCovered_status = "0", mapCovered_area = "", mapCovered_status = "0";
    LinearLayout proceed, ll_view;
    //   RelativeLayout ll_landmark;
    LandAdapter land_adapter;
    MySupportMapFragment mSupportMapFragment;
    CoveredAdapter covered_adapter;
    Intent intent;
    Boolean stu=false,stu1=false;
    Preferences pref;
    LinearLayout dropdown, view_address;
    CustomLoader loader;
    //back, dots
    RelativeLayout rl_browse, rl_casedetail;
    Boolean edit_page = false;
    RelativeLayout rlOne, rlTwo, rlThree, rlFour;
    LinearLayout llDocsAttached, llPrelimDetails, llDocsToCollectExp, llSiteSurvey;
    ImageView ivOne, ivTwo, ivThree, ivFour;
    Spinner spinnerHeightFtMtr1, spinnerHeightFtMtr2, spinnerHeightFtMtr3, spinnerHeightFtMtr4;
    Spinner spinner_east, spinner_west, spinner_north, spinner_south, property_spinner, boundry_spinner, document_spinner, sp_land, sp_map, sp_cover, sp_map2;
    String land[] = {"Select Unit", "Square Meter", "Square Feet", "Square Inch", "Square Cm", "Hectare", "Acre", "Are"};
    Button browse, schedule_media1, view_media1, view_media2, view_media3, view_media4, view_media5;
    TextView property_name;
    TextView tv_caseheader, cover_name;
    RelativeLayout rlspinProp;
    int typPos=0,catpos=0;
    TextView document_name;
    EditText et_browse, map, et_northeast, et_northwest, et_southeast, et_southwest, onr_name, onr_number, onr_address, landarea, etAsPerMap, etAsPerMapCovered, coverarea, name, number, relationship;
    PropertyAdapter property_adapter;
    //    UnitAdapter unit_adapter;
    DocumentAdapter document_adapter;
    ImageView info, iv_add;//, iv_add2;
    // ListView lv_document;
    String color_status = "1";
    int stateVal = 0, distVal = 0;
    TextView tv_caseid, tv_header, tv_pdf1, tv_schedule_pdf, tv_pdf2, tv_pdf3, tv_pdf4, tv_pdf5;
    String property[] = {"Image1.jpg", "Image2.jpg", "Image3.png"};
    //public static ArrayList<HashMap<String, String>> covered_array_list = new ArrayList<HashMap<String, String>>();
    // public static ArrayList<JSONArray> doc_list = new ArrayList<JSONArray>();
    String east_ne[] = {"East", "North-East"};
    String west_nw[] = {"West", "North-West"};
    String north_se[] = {"North", "South-East"};
    String south_sw[] = {"South", "South-West"};
    String boundry[] = {"Select Boundry", "East", "West", "North", "South"};
    String document[] = {"Select Document", "Title Deed", "Cizra Map", "Approved Building Map", "Any other document", "Boundries are not mentioned on the provided document"};
    String status_ok = "", status_name = "", status_land = "", status_mland = "", status_tcland = "", status_mCmap = "", status_address = "", name_status = "0", address_status = "0", image_status = "0", schedule_status = "0", pdf1 = "", pdf2 = "", pdf3 = "", pdf4 = "", pdf5 = "";
    String asPerMapTitle = "", asPerMapCovered = "";
    Uri picUri, fileUri;
    Bitmap bitmap;
    EditText east, west, north, south;
    String doc_status = "0", address, selected_land, selected_covered, landareaunit, landareaunitmap, coveredareaunit, coveredareaunitmap, landareaunit1, landareaunitmap1, coveredareaunit1, coveredareaunitmap1, selected_property, selected_boundry, selected_document, array_asset, array_unit, surveyor_namelist;
    String dialog_status = "0";
    String boundry_status = "0";
    // DocumentsAdapter doc_adapter;
    EastAdapter east_adapter;
    WestAdapter west_adapter;
    NorthAdapter north_adapter;
    SouthAdapter south_adapter;
    String priliminary_status = "";
    int callstatus = 0;
    TextView numberAdap, tvProceed;
    ImageView iv_phn, iv_phn2, iv_phonecall;
    CallAdapter adater;
    RecyclerView recyclerViewReferredDoc;//,recyclerViewDocuments;
    ExpandableListView simpleExpandableListDocuments, simpleExpandableListReferredDoc, simpleExpandableListDocumentsToCollect;
    AdapterDocuments adapterDocuments;
    AdapterReferredDocuments adapterReferredDocuments;
    AdapterDocumentsToCollect adapterDocumentsToCollect;
    ArrayList<HashMap<String, String>> doc_list_group = new ArrayList();
    ArrayList<ArrayList<HashMap<String, String>>> doc_list_docs = new ArrayList();
    ArrayList<HashMap<String, String>> doc_temp_list = new ArrayList<>();
    ArrayList<HashMap<String, String>> doc_list_group_referred = new ArrayList();
    ArrayList<ArrayList<HashMap<String, String>>> doc_list_docs_referred = new ArrayList();
    ArrayList<HashMap<String, String>> doc_temp_list_referred = new ArrayList<>();
    ArrayList<HashMap<String, String>> doc_list_group_to_collect = new ArrayList();
    ArrayList<ArrayList<HashMap<String, String>>> doc_list_docs_to_collect = new ArrayList();
    ArrayList<HashMap<String, String>> doc_temp_list_to_collect = new ArrayList<>();
    Dialog emailDialog;
    ImageView ivAttachment;
    int captureType = 0;
    Spinner spTotalLandArea1, spTotalLandArea2, spTotalCoveredArea1, spTotalCoveredArea2,
            spinnerState, spinnerDistrict, spinnerCityVillage;
    ArrayList<HashMap<String, String>> landArray = new ArrayList<>();
    LinearLayout llTotalLandArea, llTotalCoveredArea;
    //ImageView ivLocation;
    String assetOwner = "";
    EditText etPropertyNo, etPincode, etColonyArea, etCityVillageTehsilOther;
    RadioGroup radioGroupCity;
    StateAdapter stateAdapter;
    RadioButton rbDocReferred, rbNoBoundary, rbNotApplicable;
    LinearLayout llDocRefCheck, llRemarkDocRef, llNotApplicable, llRemarkNotApplicable, llDocsToCollect;
    TextView tvSendEmail, tvDocsToCollect;
    EditText etRemarksDocRef, etRemarksNotApplicable;
    CheckBox cbTitle, cbSaleDeed, cbConveyanceDeed, cbPossessionCertificate, cbCizra, cbApproved, cbAnyOtherDocRef,
            cbFlat, cbVery, cbNpa, cbAnyOtherNotApplicable;
    LinearLayout llName;
    Geocoder geocoder;
    GoogleMap mMap;
    View v;
    String sendToNameEmail = "";
    Dialog emailDialogg;
    int assetPos = 0;
    private AutoCompleteTextView mAutocompleteTextView, et_landmark;
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<String> imagesEncodedList = new ArrayList<>();
    private GetLocation location;
    private String documentId = "";
    private String groupId = "";
    private String documentFileName = "";
    private int row_index = -1;
    private ArrayList<String> selectedMembersList = new ArrayList<>();
    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();
    private String city_type = "";
    private String city_val = "";
    private boolean firstTime = true;
    private boolean assestCheck = true;
    private String radio_val = "";
    private String check1 = "";
    private String check2 = "";
    private ScrollView scrollView;
    private String stateId;
    private String districtId;
    private String cityVillageTehsilText = "";
    private String districtText = "";
    String nature_type="",category_type="",asset_type="";
    String ass_natureId = "",ass_catId = "",ass_typeId="",ass_typename="";
    Boolean assestChanged=false;
    int valFirst=0;

    private static File getOutputMediaFile(int type) {

        // External sdcard location

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

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

    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
            //  base64 = Base64.encodeToString(buffer,Base64.NO_WRAP);

            //  String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    //==================================Auto serach==================================//

  /*  private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);

            final String placeId = String.valueOf(item.placeId);


            Log.v("desc", "Selected: " + item.description);


            try {
                String CurrentString = String.valueOf(item.description);
                for (int i = 0; i < CurrentString.length(); i++) {
                    String[] separated = CurrentString.split(",");
                    address = separated[0];
                    mAutocompleteTextView.setText(CurrentString);

                }

            } catch (Exception exp) {

            }


            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.v("tag", "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.v("tag", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            Log.v("city*********", (String) place.getName());
            Log.v("address*********", (String) place.getAddress());

        }
    };*/

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
        v = inflater.inflate(R.layout.activity_priliminary, container, false);

        init(v);
        setListener();
        if (mAutocompleteTextView.getText().toString().equals("")) {
            iv_add.setVisibility(View.GONE);
        } else {
            iv_add.setVisibility(View.VISIBLE);
        }
        /*if (et_landmark.getText().toString().equals("")) {
            iv_add2.setVisibility(View.GONE);
        } else {
            iv_add2.setVisibility(View.VISIBLE);
        }*/

        tvProceed.setText("Proceed to schedule the survey");

        if (DashboardFragment.scheduleCheck.equalsIgnoreCase("1")) {
            tvProceed.setText("Proceed to schedule the survey");
        } else {
            tvProceed.setText("Proceed to start survey");
        }

        //    tv_header.setVisibility(View.GONE);
        //rl_casedetail.setVisibility(View.VISIBLE);
        //    tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));
        //  tv_caseheader.setText("Preliminary Form");

        east_adapter = new EastAdapter(mActivity, east_ne);
        spinner_east.setAdapter(east_adapter);

        west_adapter = new WestAdapter(mActivity, west_nw);
        spinner_west.setAdapter(west_adapter);

        north_adapter = new NorthAdapter(mActivity, north_se);
        spinner_north.setAdapter(north_adapter);

        south_adapter = new SouthAdapter(mActivity, south_sw);
        spinner_south.setAdapter(south_adapter);

        if (Utils.isNetworkConnectedMainThred(mActivity)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            hitGetPriliminaryApi();

            spinnerState.setSelection(stateVal);


        } else {
            Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

        }


        try {
            populateSpinner();
            array_asset = pref.get(Constants.asset_list);
            property_array_list.clear();
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("id", "00000000000");
            hash.put("name", "Select Type Of Property");
            property_array_list.add(hash);
            JSONArray array5 = new JSONArray(array_asset);
            for (int j = 0; j < array5.length(); j++) {
                JSONObject data_object = array5.getJSONObject(j);
                String id = data_object.getString("id");
                String asset = data_object.getString("name");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("id", id);
                hmap.put("name", asset);
                property_array_list.add(hmap);
                property_adapter = new PropertyAdapter(mActivity, property_array_list);
                property_spinner.setAdapter(property_adapter);

            }

          /*  if (!asset_type.equals("")) {
                for(int i=0;i<property_array_list.size();i++){
                    if(asset_type.equalsIgnoreCase(property_array_list.get(i).get("id"))){
                        property_spinner.setSelection(Integer.parseInt(asset_type));
                    }
                }
            }*/


            surveyor_namelist = pref.get(Constants.surveyor_data);

            JSONArray surveyor_list = new JSONArray(surveyor_namelist);
            for (int j = 0; j < surveyor_list.length(); j++) {
                JSONObject data_object = surveyor_list.getJSONObject(j);
                String name = data_object.getString("name");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("name", name);
                surveyor_array_list.add(hmap);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        document_adapter = new DocumentAdapter(mActivity, document);
        document_spinner.setAdapter(document_adapter);

        east_adapter = new EastAdapter(mActivity, east_ne);
        spinner_east.setAdapter(east_adapter);

        west_adapter = new WestAdapter(mActivity, west_nw);
        spinner_west.setAdapter(west_adapter);

        north_adapter = new NorthAdapter(mActivity, north_se);
        spinner_north.setAdapter(north_adapter);

        south_adapter = new SouthAdapter(mActivity, south_sw);
        spinner_south.setAdapter(south_adapter);

        // //Sq.yrd Sq.mtr Sq.mm Sq.km Sq.cm Sq.inch   to remove

        // remove();

        land_adapter = new LandAdapter(mActivity, unit_array_list);
        sp_land.setAdapter(land_adapter);
        sp_map.setAdapter(land_adapter);
        sp_map2.setAdapter(land_adapter);
        sp_cover.setAdapter(land_adapter);

        /*covered_adapter = new CoveredAdapter(this, covered_array_list);
        sp_cover.setAdapter(covered_adapter);*/

      /*  mGoogleApiClient = new GoogleApiClient.Builder(PriliminaryActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView.setThreshold(1);

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);

        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);*/

        spinner_east.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (east_ne[position].equals("East")) {
                    spinner_west.setSelection(0);
                    spinner_north.setSelection(0);
                    spinner_south.setSelection(0);
                } else {
                    spinner_west.setSelection(1);
                    spinner_north.setSelection(1);
                    spinner_south.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_west.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (west_nw[position].equals("West")) {
                    spinner_east.setSelection(0);
                    spinner_north.setSelection(0);
                    spinner_south.setSelection(0);

                } else {
                    spinner_east.setSelection(1);
                    spinner_north.setSelection(1);
                    spinner_south.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_north.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (north_se[position].equals("North")) {
                    spinner_east.setSelection(0);
                    spinner_west.setSelection(0);
                    spinner_south.setSelection(0);
                } else {
                    spinner_east.setSelection(1);
                    spinner_west.setSelection(1);
                    spinner_south.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_south.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (south_sw[position].equals("South")) {
                    spinner_east.setSelection(0);
                    spinner_west.setSelection(0);
                    spinner_north.setSelection(0);
                } else {
                    spinner_east.setSelection(1);
                    spinner_west.setSelection(1);
                    spinner_north.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        property_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                       @Override
                                                       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                           Log.v("item", String.valueOf(property_array_list.get(i)));
                                                           //selected_property = String.valueO  f(property_array_list.get(i));
                                                           selected_property = property_array_list.get(i).get("name");


                                                           Log.v("itemCountVal", selected_property);


                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id), property_array_list.get(i).get("id"));
                                                       }

                                                       @Override
                                                       public void onNothingSelected(AdapterView<?> adapterView) {

                                                       }
                                                   }
        );
        spinnerHeightFtMtr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                              Log.v("itemSelected", String.valueOf(unit_array_list.get(i).get("symbol")));

                                                              //selected_property = String.valueOf(property_array_list.get(i));
                                                              landareaunit = unit_array_list.get(i).get("symbol");

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                          }

                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> adapterView) {

                                                          }
                                                      }
        );
        spinnerHeightFtMtr4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                              Log.v("itemSelected", String.valueOf(unit_array_list.get(i).get("symbol")));

                                                              //selected_property = String.valueOf(property_array_list.get(i));
                                                              coveredareaunitmap = unit_array_list.get(i).get("symbol");

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                          }

                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> adapterView) {

                                                          }
                                                      }
        );
        spinnerHeightFtMtr2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                              Log.v("itemSelected", String.valueOf(unit_array_list.get(i).get("symbol")));

                                                              //selected_property = String.valueOf(property_array_list.get(i));
                                                              landareaunitmap = unit_array_list.get(i).get("symbol");

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                          }

                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> adapterView) {

                                                          }
                                                      }
        );
        spinnerHeightFtMtr3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                              Log.v("itemSelected", String.valueOf(unit_array_list.get(i).get("symbol")));

                                                              //selected_property = String.valueOf(property_array_list.get(i));
                                                              coveredareaunit = unit_array_list.get(i).get("symbol");

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                          }

                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> adapterView) {

                                                          }
                                                      }
        );
        boundry_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                          selected_boundry = boundry[i];

                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> adapterView) {

                                                      }
                                                  }
        );


        document_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                       @Override
                                                       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                           selected_document = document[i];
                                                           Log.v("selected_document----", selected_document);
                                                           if (selected_document.equals("Boundries are not mentioned on the provided document")) {
                                                               boundry_status = "1";
                                                               color_status = "2";
                                                               InfoDialog();
                                                           }

                                                       }

                                                       @Override
                                                       public void onNothingSelected(AdapterView<?> adapterView) {

                                                       }
                                                   }
        );

       /* sp_land.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selected_land = unit_array_list.get(i).get("symbol");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


    /*    sp_cover.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selected_covered = unit_array_list.get(i).get("symbol");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/

        if (!edit_page) {// false
            setEditiblity();
        }
        return v;

    }

    public void alertAssestChanged() {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_yes_no);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        TextView tvyes = dialog.findViewById(R.id.tvOk);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);

        tvMessage.setText(R.string.assestChangedWarning);
        tvyes.setText(R.string.yes);
        tvCancel.setText(R.string.no);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        tvyes.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                pref.set(Constants.assestStatus, "Original");
                pref.set(Constants.surveyChoosenPage, "");
                pref.commit();
                hitLastSavedAssetFormApi();
                assestChanged=false;
                dialog.dismiss();
                nature_type=ass_natureId;
                category_type=ass_catId;
                asset_type=ass_typeId;
                for (int i = 0; i < property_array_list.size(); i++) {
                    if (property_array_list.get(i).get("id").equalsIgnoreCase(asset_type)) {
                        assetPos = i;
                        break;
                    }
                }
                property_spinner.setSelection(assetPos);
                pref.set(Constants.selected_property, "");
                pref.commit();
//                pref.set(Constants.type_of_assets,selected_property);

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void alertAssestChooser() {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_asset_dial);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        TextView tvyes = dialog.findViewById(R.id.tvOk);

        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        stu=true;
        stu1=true;
        Spinner spinnerAssetNature = dialog.findViewById(R.id.spinnerAssetNature);
        final Spinner spinnerAssetCat = dialog.findViewById(R.id.spinnerAssetCat);
        final Spinner spinnerAssetType = dialog.findViewById(R.id.spinnerAssetType);
        spinnerAssetNature.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_new, assest_nature_list));
        if(!nature_type.isEmpty())
        for(int i=0; i<assest_nature_list.size();i++)
            if(assest_nature_list.get(i).get("id").equalsIgnoreCase(nature_type))
                spinnerAssetNature.setSelection(i);

        if(!category_type.isEmpty())
            for(int i=0; i<assest_category_list.size();i++)
            if(assest_category_list.get(i).get("id").equalsIgnoreCase(category_type)) {
                spinnerAssetCat.setSelection(i);
                catpos=i;
            }
        if(!asset_type.isEmpty())
            for(int i=0; i<assest_type_list.size();i++)
            if(assest_type_list.get(i).get("id").equalsIgnoreCase(asset_type)) {
                spinnerAssetType.setSelection(i);
                typPos=i;
            }
        /*ArrayAdapter<String> dataAdapterCat = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, assestCatList);
        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, assestTypeList);
        dataAdapterNat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAssetNature.setAdapter(dataAdapter);*/
        spinnerAssetNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ass_natureId = assest_nature_list.get(position).get("id");
                if(ass_natureId.equalsIgnoreCase("-1"))
                    ass_natureId="";
                if (!ass_natureId.isEmpty()) {
                    try {
                        assest_category_list.clear();
                        assest_type_list.clear();
                        int count=0;
                        JSONArray jsonArray = new JSONArray(pref.get(Constants.assest_category_list));
                        HashMap<String, String> hash = new HashMap<String, String>();
                        hash.put("id", "-1");
                        hash.put("name", "Select Category of Assets");
                        assest_category_list.add(hash);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap hashMap = new HashMap();
                            JSONObject obj = jsonArray.getJSONObject(i);
                            if (obj.getString("nature_id").equalsIgnoreCase(ass_natureId)) {
                                hashMap.put("id", obj.getString("id"));
                                hashMap.put("name", obj.getString("name"));
                                hashMap.put("nature_id", obj.getString("nature_id"));
                                assest_category_list.add(hashMap);
                                count++;
                            }

                        }

                        if (count == 0) {
                            assest_category_list.clear();
                            HashMap hashMap = new HashMap();
                            hashMap.put("id", "-1");
                            hashMap.put("name", "No Data Available");
                            hashMap.put("nature_id", "-1");
                            assest_category_list.add(hashMap);
                            assest_type_list.clear();
                            assest_type_list.add(hashMap);

                        }
                        spinnerAssetCat.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_new, assest_category_list));
                        if(stu1) {
                            spinnerAssetCat.setSelection(catpos);
                            stu1=false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    assest_category_list.clear();
                    HashMap hashMap = new HashMap();
                    hashMap.put("id", "-1");
                    hashMap.put("name", "No Data Available");
                    hashMap.put("nature_id", "-1");
                    assest_category_list.add(hashMap);
                    assest_type_list.clear();
                    assest_type_list.add(hashMap);
                    spinnerAssetCat.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_new, assest_category_list));
                    spinnerAssetType.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_new, assest_type_list));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerAssetCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ass_catId = assest_category_list.get(position).get("id");
                if(ass_catId.equalsIgnoreCase("-1"))
                    ass_catId="";
                if (!ass_catId.isEmpty()) {
                    try {
                        assest_type_list.clear();
                        int count=0;
                        JSONArray jsonArray = new JSONArray(pref.get(Constants.asset_list));
                        HashMap<String, String> hash = new HashMap<String, String>();
                        hash.put("id", "-1");
                        hash.put("name", "Select Type Of Assets");
                        assest_type_list.add(hash);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap hashMap = new HashMap();
                            JSONObject obj = jsonArray.getJSONObject(i);
                            if (obj.getString("nature_id").equalsIgnoreCase(ass_natureId)&&obj.getString("category_id").equalsIgnoreCase(ass_catId)) {
                                hashMap.put("id", obj.getString("id"));
                                hashMap.put("name", obj.getString("name"));
                                hashMap.put("nature_id", obj.getString("nature_id"));
                                hashMap.put("category_id", obj.getString("category_id"));
                                assest_type_list.add(hashMap);
                                count++;
                            }

                        }

                        if (count == 0) {
                            assest_type_list.clear();
                            HashMap hashMap = new HashMap();
                            hashMap.put("id", "-1");
                            hashMap.put("name", "No Data Available");
                            hashMap.put("nature_id", "-1");
                            assest_type_list.add(hashMap);
                        }
                        spinnerAssetType.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_new, assest_type_list));
                        if(stu) {
                            spinnerAssetType.setSelection(typPos);
                            stu=false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    HashMap hashMap = new HashMap();
                        hashMap.put("id", "-1");
                        hashMap.put("name", "No Data Available");
                        hashMap.put("nature_id", "-1");
                        assest_type_list.add(hashMap);
                    spinnerAssetType.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_new, assest_type_list));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerAssetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ass_typeId = assest_type_list.get(position).get("id");
                ass_typename = assest_type_list.get(position).get("name");
                if(ass_typeId.equalsIgnoreCase("-1"))
                    ass_typeId="";
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvyes.setText(R.string.yes);


        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        tvyes.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(ass_natureId.isEmpty()||ass_catId.isEmpty()||ass_typeId.isEmpty())
                    Toast.makeText(mActivity,"All Feilds are required",Toast.LENGTH_LONG).show();
                else {
                    assestChanged = true;
                    dialog.dismiss();
                }
                }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                assestChanged=false;
                for (int i = 0; i < property_array_list.size(); i++) {
                    if (property_array_list.get(i).get("id").equalsIgnoreCase(asset_type)) {
                        assetPos = i;
                        break;
                    }
                }
                property_spinner.setSelection(assetPos);
                ass_typename="";
                dialog.dismiss();
            }
        });
    }
    public void setDefaultSurvey() {
        switch (pref.get(Constants.type_of_assets).toLowerCase()) {
            case "residential apartment in multistoried building":
                pref.set(Constants.defAssest, "MultiStoried");
                break;
            case "residential apartment in low rise building":
                pref.set(Constants.defAssest, "MultiStoried");
                break;
            case "residential house (plotted development)":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "residential builder floor":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "residential plot/land":
                pref.set(Constants.defAssest, "vacantLand");

                break;
            case "mansion":
//                pref.set(Constants.defAssest, "generalSurvey");
                pref.set(Constants.defAssest, "MultiStoried");
                break;
            case "kothi":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "villa":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "group housing society":
                pref.set(Constants.defAssest, "groupHousing");

                break;
            case "commercial office unit":
                pref.set(Constants.defAssest, "Commercial");

                break;
            case "commercial shop unit":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial floor unit":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial office (independent)":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial shop (independent)":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial floor (independent plotted development)":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial land & building":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "hotel/ resort":
                pref.set(Constants.defAssest, "Hotel");
                break;
            case "shopping mall":
                pref.set(Constants.defAssest, "ShoppingMall");

                break;
            case "shopping complex":
                pref.set(Constants.defAssest, "ShoppingMall");

                break;
            case "anchor store":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "restaurant":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "amusement park":
                break;
            case "multiplex":
                pref.set(Constants.defAssest, "Multiplex");

                break;
            case "cinema hall":
                pref.set(Constants.defAssest, "Multiplex");

                break;
            case "godown":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "stadium":
                pref.set(Constants.defAssest, "LargeInfrastructure");


                break;
            case "club":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "institutional plot/land":
                pref.set(Constants.defAssest, "School");


                break;
            case "institutional land & building":
                pref.set(Constants.defAssest, "School");


                break;
            case "educational institution (school/ college/ university)":
                pref.set(Constants.defAssest, "School");


                break;
            case "hospital":
                pref.set(Constants.defAssest, "Hospital");


                break;
            case "multi speciality hospital":
                pref.set(Constants.defAssest, "Hospital");


                break;
            case "nursing home":
                pref.set(Constants.defAssest, "Hospital");


                break;
            case "old age home":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "industrial plot":
                pref.set(Constants.defAssest, "vacantLand");


                break;
            case "industrial project land & building":
                pref.set(Constants.defAssest, "IndustrialLand");


                break;
            case "manufacturing unit":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "small/ mid-scale manufacturing unit":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "industrial plant":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "industrial project":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "infrastructure project":
                pref.set(Constants.defAssest, "LargeInfrastructure");


                break;
            case "large industrial project":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "industrial plant & machinery":
                pref.set(Constants.defAssest, "IndustrialPlant");


                break;
            case "general machinery items":
                pref.set(Constants.defAssest, "IndustrialPlant");


                break;
            case "sez":
                pref.set(Constants.defAssest, "LargeInfrastructure");


                break;
            case "warehouse":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "agricultural land":
                pref.set(Constants.defAssest, "vacantLand");


                break;
            case "farm house":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "financial securities/ instruments":
                pref.set(Constants.defAssest, "Financial");


                break;
            case "current assets":
                pref.set(Constants.defAssest, "CurrentAssets");


                break;
            case "stocks":
                pref.set(Constants.defAssest, "InventoryStock");


                break;
            case "jewellery":
                pref.set(Constants.defAssest, "Jewellery");


                break;
            case "truck":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "bus":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "car":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "aircraft":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "helicopter":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "ship":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "boat":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "motorcycle":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "scooter":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "fittings & fixtures":
                pref.set(Constants.defAssest, "Furniture");


                break;
            case "furniture":
                pref.set(Constants.defAssest, "Furniture");


                break;
            case "brand":
                pref.set(Constants.defAssest, "Intangible");


                break;
            case "good will":
                pref.set(Constants.defAssest, "Intangible");


                break;
            case "highway/ expressway":
                pref.set(Constants.defAssest, "LargeInfrastructure");


                break;
            case "it/ office space":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "expo cum exhibition center":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "petrol pump":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "agri mart":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "cold storage":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "govt. department/ ministry office":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "public sector unit office":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "police station":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "fire station":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            default:
                pref.set(Constants.defAssest, "generalSurvey");
        }

        pref.commit();
    }

    private void hitLastSavedAssetFormApi() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.LastSavedAssetForm);

        Log.v("hitGetLastSaved", url);
        Log.e("url11>>>",url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        try {

            jsonObject.put("case_id", pref.get(Constants.case_id));
            jsonObject.put("type", "2");//1 get , 2 post.
            jsonObject1.put("surveyType", "2");//1 unchanged , 2 changed
            jsonObject1.put("surveyChoosenPage", "");//Survey Selected Page
            jsonObject1.put("assestChoosen", pref.get(Constants.type_of_assets));//which Asset is choosen
            jsonObject.put("lastSavedAssetForm", jsonObject1.toString());
            json_data.put("VIS", jsonObject);
            Log.v("hitGetLastSaved", json_data.toString());

        } catch (JSONException e) {
            Log.v("exception====", e.toString());
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonGetLastSaved(response);
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
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonGetLastSaved(JSONObject response) {

        loader.cancel();

        Log.v("resp:HitGetLastSaved", response.toString());

        try {

            /*JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String res_msg = jsonObject.getString("response_message");




            loader.cancel();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void populateSpinner() {
        try {
            array_unit = pref.get(Constants.array_units);
            Log.v("array_unit+++", array_unit);
            unit_array_list.clear();
            temp_unit_array_list.clear();
            JSONArray unit_list = new JSONArray(array_unit);
            int spinVal1 = 0, spinVal2 = 0, spinVal3 = 0, spinVal4 = 0;
            for (int j = 0; j < unit_list.length(); j++) {
                JSONObject data_object = unit_list.getJSONObject(j);
                String symbol = data_object.getString("name");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("symbol", symbol);
                unit_array_list.add(hmap);
                temp_unit_array_list.add(hmap);
                spinnerHeightFtMtr1.setAdapter(new adapter_spinner(mActivity, R.layout.spinner_textview_new, unit_array_list));
                spinnerHeightFtMtr2.setAdapter(new adapter_spinner(mActivity, R.layout.spinner_textview_new, unit_array_list));
                spinnerHeightFtMtr3.setAdapter(new adapter_spinner(mActivity, R.layout.spinner_textview_new, unit_array_list));
                spinnerHeightFtMtr4.setAdapter(new adapter_spinner(mActivity, R.layout.spinner_textview_new, unit_array_list));
                if (symbol.equalsIgnoreCase(landareaunit)) {
                    spinVal1 = j;
                }
                if (symbol.equalsIgnoreCase(landareaunitmap)) {
                    spinVal2 = j;
                }
                if (symbol.equalsIgnoreCase(coveredareaunit)) {
                    spinVal3 = j;
                }
                if (symbol.equalsIgnoreCase(coveredareaunitmap)) {
                    spinVal4 = j;
                }
            }
            spinnerHeightFtMtr1.setSelection(spinVal1);
            spinnerHeightFtMtr2.setSelection(spinVal2);
            spinnerHeightFtMtr3.setSelection(spinVal3);
            spinnerHeightFtMtr4.setSelection(spinVal4);
        } catch (Exception e) {

        }
    }

    public void setEditiblity() {
        onr_name.setEnabled(false);
        onr_name.setText(assetOwner);
        etPropertyNo.setEnabled(false);
        etPincode.setEnabled(false);
        etColonyArea.setEnabled(false);
        spinner_east.setEnabled(false);
        spinner_west.setEnabled(false);
        spinner_north.setEnabled(false);
        spinner_south.setEnabled(false);
        spinner_east.setClickable(false);
        spinner_west.setClickable(false);
        spinner_north.setClickable(false);
        spinner_south.setClickable(false);
        spinnerState.setEnabled(false);
        spinnerDistrict.setEnabled(false);
        spinnerDistrict.setClickable(false);
        spinnerState.setClickable(false);
        radioGroupCity.setClickable(false);
        spinnerState.setEnabled(false);
        spinnerCityVillage.setClickable(false);
        spinnerCityVillage.setEnabled(false);
        property_spinner.setEnabled(false);
        property_spinner.setClickable(false);
        landarea.setEnabled(false);
        etAsPerMap.setEnabled(false);
        etAsPerMapCovered.setEnabled(false);
        cbTitle.setClickable(false);
        cbSaleDeed.setClickable(false);
        cbConveyanceDeed.setClickable(false);
        cbPossessionCertificate.setClickable(false);
        cbCizra.setClickable(false);
        cbApproved.setClickable(false);
        cbAnyOtherDocRef.setClickable(false);
        cbFlat.setClickable(false);
        cbVery.setClickable(false);
        cbNpa.setClickable(false);
        cbAnyOtherNotApplicable.setClickable(false);
        rbDocReferred.setClickable(false);
        rbNoBoundary.setClickable(false);
        rbNotApplicable.setClickable(false);
        east.setEnabled(false);
        west.setEnabled(false);
        north.setEnabled(false);
        south.setEnabled(false);
        name.setEnabled(false);
        number.setEnabled(false);
        relationship.setEnabled(false);
        et_northeast.setEnabled(false);
        et_northwest.setEnabled(false);
        et_southeast.setEnabled(false);
        et_southwest.setEnabled(false);
        iv_phonecall.setVisibility(View.GONE);
        tvProceed.setText("Proceed to review the completed case");
        proceed.setClickable(true);
        for (int i = 0; i < radioGroupCity.getChildCount(); i++) {
            radioGroupCity.getChildAt(i).setEnabled(false);
        }
    }
/*
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }*/

  /*  @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }*/

    private void remove() {

        for (int i = 0; i < unit_array_list.size(); i++) {

            if (unit_array_list.get(i).get("symbol").equalsIgnoreCase("Sq.ft")) {
                //unit_array_list.remove(i);
                unit_array_list.remove(i);
                remove();
                break;


            } else if (unit_array_list.get(i).get("symbol").equalsIgnoreCase("Sq.yrd")) {

                unit_array_list.remove(i);
                remove();
                break;

            } else if (unit_array_list.get(i).get("symbol").equalsIgnoreCase("Sq.mtr")) {

                unit_array_list.remove(i);
                remove();
                break;

            } else if (unit_array_list.get(i).get("symbol").equalsIgnoreCase("Sq.mm")) {

                unit_array_list.remove(i);
                remove();
                break;

            } else if (unit_array_list.get(i).get("symbol").equalsIgnoreCase("Sq.km")) {

                unit_array_list.remove(i);
                remove();
                break;

            } else if (unit_array_list.get(i).get("symbol").equalsIgnoreCase("Sq.cm")) {

                unit_array_list.remove(i);
                remove();
                break;

            } else if (unit_array_list.get(i).get("symbol").equalsIgnoreCase("Sq.inch")) {

                unit_array_list.remove(i);
                remove();
                break;

            } else {
                land_adapter = new LandAdapter(mActivity, unit_array_list);
                sp_land.setAdapter(land_adapter);
                sp_map.setAdapter(land_adapter);
                sp_map2.setAdapter(land_adapter);
                sp_cover.setAdapter(land_adapter);
                break;
            }


        }


    }

    public void init(View v) {
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        try {
            assest_nature_list.clear();
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("id", "-1");
            hash.put("name", "Select Nature Of Assets");
            assest_nature_list.add(hash);
            JSONArray jsonArray = new JSONArray(pref.get(Constants.assest_nature_list));
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap hashMap = new HashMap();
                JSONObject obj = jsonArray.getJSONObject(i);
                hashMap.put("id", obj.getString("id"));
                hashMap.put("name", obj.getString("name"));
                assest_nature_list.add(hashMap);

            }
            Log.v("MyNatureList", assest_nature_list.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //  Dashboard.tv_caseheader.setText("Preliminary Details");


        mSupportMapFragment = (MySupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFrag);
        mSupportMapFragment.getMapAsync(this);

        mSupportMapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        // mapFragment.getMapAsync(this);

        MapsInitializer.initialize(mActivity);

        rbDocReferred = v.findViewById(R.id.rbDocReferred);
        rbNoBoundary = v.findViewById(R.id.rbNoBoundary);
        rbNotApplicable = v.findViewById(R.id.rbNotApplicable);

        llDocRefCheck = v.findViewById(R.id.llDocRefCheck);
        llRemarkDocRef = v.findViewById(R.id.llRemarkDocRef);
        llNotApplicable = v.findViewById(R.id.llNotApplicable);
        llRemarkNotApplicable = v.findViewById(R.id.llRemarkNotApplicable);
        llDocsToCollect = v.findViewById(R.id.llDocsToCollect);

        tvDocsToCollect = v.findViewById(R.id.tvDocsToCollect);

        tvSendEmail = v.findViewById(R.id.tvSendEmail);
        etRemarksDocRef = v.findViewById(R.id.etRemarksDocRef);
        etRemarksNotApplicable = v.findViewById(R.id.etRemarksNotApplicable);

        scrollView = v.findViewById(R.id.scrollView);
        llName = v.findViewById(R.id.llName);
        spinnerHeightFtMtr1 = v.findViewById(R.id.spinnerHeightFtMtr1);
        spinnerHeightFtMtr2 = v.findViewById(R.id.spinnerHeightFtMtr2);
        spinnerHeightFtMtr3 = v.findViewById(R.id.spinnerHeightFtMtr3);
        spinnerHeightFtMtr4 = v.findViewById(R.id.spinnerHeightFtMtr4);
        cbAnyOtherDocRef = v.findViewById(R.id.cbAnyOtherDocRef);
        cbTitle = v.findViewById(R.id.cbTitle);
        cbSaleDeed = v.findViewById(R.id.cbSaleDeed);
        cbConveyanceDeed = v.findViewById(R.id.cbConveyanceDeed);
        cbPossessionCertificate = v.findViewById(R.id.cbPossessionCertificate);
        cbCizra = v.findViewById(R.id.cbCizra);
        cbApproved = v.findViewById(R.id.cbApproved);
        cbFlat = v.findViewById(R.id.cbFlat);
        cbVery = v.findViewById(R.id.cbVery);
        cbNpa = v.findViewById(R.id.cbNpa);
        cbAnyOtherNotApplicable = v.findViewById(R.id.cbAnyOtherNotApplicable);

        tvProceed = v.findViewById(R.id.tvProceed);
        rlspinProp = v.findViewById(R.id.rlspinProp);

        iv_add = v.findViewById(R.id.iv_add);
        //   iv_add2 = v.findViewById(R.id.iv_add2);
        //rl_casedetail = v.findViewById(R.id.rl_casedetail);
        dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);
        //   dots = (RelativeLayout) v.findViewById(R.id.rl_dots);
        //  tv_header = v.findViewById(R.id.tv_header);
        ll_view = v.findViewById(R.id.ll_view);
        //lv_document = v.findViewById(R.id.lv_document);
        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        proceed = (LinearLayout) v.findViewById(R.id.ll_proceed);
        // back = (RelativeLayout) v.findViewById(R.id.rl_back);
        property_spinner = (Spinner) v.findViewById(R.id.spinner_property);
        boundry_spinner = (Spinner) v.findViewById(R.id.spinner_boundy);
        document_spinner = (Spinner) v.findViewById(R.id.spinner_document);
        info = (ImageView) v.findViewById(R.id.iv_info);
        browse = (Button) v.findViewById(R.id.btn_browse);
        rl_browse = v.findViewById(R.id.rl_browse);
        schedule_media1 = v.findViewById(R.id.btn_onee);
        mAutocompleteTextView = (AutoCompleteTextView) v.findViewById(R.id.et_address);
        et_landmark = (AutoCompleteTextView) v.findViewById(R.id.et_landmark);
        onr_name = v.findViewById(R.id.etOwnerName);

        onr_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                status_ok = "";
                status_name = "";
                onr_name.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        onr_number = v.findViewById(R.id.et_onrnumber);
        onr_address = v.findViewById(R.id.et_address);
        landarea = v.findViewById(R.id.lndarea);
        landarea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                status_ok = "";
                status_name = "";
                landarea.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etAsPerMap = v.findViewById(R.id.etAsPerMap);
        etAsPerMapCovered = v.findViewById(R.id.etAsPerMapCovered);
        coverarea = v.findViewById(R.id.coverarea);
        name = v.findViewById(R.id.et_name);
        number = v.findViewById(R.id.et_number);
        relationship = v.findViewById(R.id.et_relation);
        tv_pdf1 = v.findViewById(R.id.tv_pdf1);
        tv_pdf2 = v.findViewById(R.id.tv_pdf2);
        tv_pdf3 = v.findViewById(R.id.tv_pdf3);
        tv_pdf4 = v.findViewById(R.id.tv_pdf4);
        tv_pdf5 = v.findViewById(R.id.tv_pdf5);
        tv_schedule_pdf = v.findViewById(R.id.tv_pdfone);
        view_media1 = v.findViewById(R.id.btn_one);
        view_media2 = v.findViewById(R.id.btn_two);
        view_media3 = v.findViewById(R.id.btn_three);
        view_media4 = v.findViewById(R.id.btn_four);
        view_media5 = v.findViewById(R.id.btn_five);
        east = v.findViewById(R.id.et_east);
        west = v.findViewById(R.id.et_west);
        north = v.findViewById(R.id.et_north);
        south = v.findViewById(R.id.et_south);
        iv_phonecall = v.findViewById(R.id.iv_phonecall);

        et_northeast = v.findViewById(R.id.et_northeast);
        et_northwest = v.findViewById(R.id.et_northwest);
        et_southeast = v.findViewById(R.id.et_southeast);
        et_southwest = v.findViewById(R.id.et_southwest);
        map = v.findViewById(R.id.map);
        //tv_caseid = v.findViewById(R.id.tv_caseid);
        //  tv_caseheader = v.findViewById(R.id.tv_caseheader);
        et_browse = v.findViewById(R.id.et_browse);

        spinner_east = v.findViewById(R.id.sp_east);
        spinner_west = v.findViewById(R.id.sp_west);
        spinner_north = v.findViewById(R.id.sp_north);
        spinner_south = v.findViewById(R.id.sp_south);
        view_address = v.findViewById(R.id.ll_viewadd);
        //ll_landmark = v.findViewById(R.id.ll_landmark);
        sp_land = v.findViewById(R.id.sp_land);
        sp_map = v.findViewById(R.id.sp_map);
        sp_cover = v.findViewById(R.id.sp_cover);
        sp_map2 = v.findViewById(R.id.sp_map2);

        llTotalLandArea = v.findViewById(R.id.llTotalLandArea);
        llTotalCoveredArea = v.findViewById(R.id.llTotalCoveredArea);

        // ivLocation= v.findViewById(R.id.ivLocation);

        etPropertyNo = v.findViewById(R.id.etPropertyNo);
        etPincode = v.findViewById(R.id.etPincode);
        etColonyArea = v.findViewById(R.id.etColonyArea);
        etCityVillageTehsilOther = v.findViewById(R.id.etCityVillageTehsilOther);

        radioGroupCity = v.findViewById(R.id.radioGroupCity);

        spTotalLandArea1 = v.findViewById(R.id.spTotalLandArea1);
        spTotalLandArea2 = v.findViewById(R.id.spTotalLandArea2);
        spTotalCoveredArea1 = v.findViewById(R.id.spTotalCoveredArea1);
        spTotalCoveredArea2 = v.findViewById(R.id.spTotalCoveredArea2);

        spinnerState = v.findViewById(R.id.spinnerState);
        spinnerDistrict = v.findViewById(R.id.spinnerDistrict);
        spinnerCityVillage = v.findViewById(R.id.spinnerCityVillage);

        etColonyArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                status_ok = "";
                status_address = "";
            }

            @Override
            public void afterTextChanged(Editable editable) {
               /* if (!colonyArea.equals(editable.toString())){
                    address_status = "2";
                }*/
            }
        });

        HashMap hashMap = new HashMap();
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                hashMap = new HashMap();
                hashMap.put("symbol", "As per map");
            } else if (i == 1) {
                hashMap = new HashMap();
                hashMap.put("symbol", "As per title doc.");
            } else if (i == 2) {
                hashMap = new HashMap();
                hashMap.put("symbol", "As per title deed");
            }
            landArray.add(hashMap);
        }

        recyclerViewReferredDoc = v.findViewById(R.id.recyclerViewReferredDoc);
        recyclerViewReferredDoc.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerViewReferredDoc.setNestedScrollingEnabled(false);

        simpleExpandableListDocuments = v.findViewById(R.id.simpleExpandableListDocuments);
        simpleExpandableListReferredDoc = v.findViewById(R.id.simpleExpandableListReferredDoc);
        simpleExpandableListDocumentsToCollect = v.findViewById(R.id.simpleExpandableListDocumentsToCollect);


//    RelativeLayout rlOne,rlTwo,rlThree,rlFour;
//    LinearLayout llDocsAttached,llPrelimDetails,llDocsToCollectExp,llSiteSurvey;
//    ImageView ivOne,ivTwo,ivThree,ivFour;

        rlOne = v.findViewById(R.id.rlOne);
        rlTwo = v.findViewById(R.id.rlTwo);
        rlThree = v.findViewById(R.id.rlThree);
        rlFour = v.findViewById(R.id.rlFour);

        llDocsAttached = v.findViewById(R.id.llDocsAttached);
        llPrelimDetails = v.findViewById(R.id.llPrelimDetails);
        llDocsToCollectExp = v.findViewById(R.id.llDocsToCollectExp);
        llSiteSurvey = v.findViewById(R.id.llSiteSurvey);

        ivOne = v.findViewById(R.id.ivOne);
        ivTwo = v.findViewById(R.id.ivTwo);
        ivThree = v.findViewById(R.id.ivThree);
        ivFour = v.findViewById(R.id.ivFour);


        ivOne.setBackgroundResource(R.mipmap.down_arrow);
        ivTwo.setBackgroundResource(R.mipmap.down_arrow);
        ivThree.setBackgroundResource(R.mipmap.down_arrow);
        ivFour.setBackgroundResource(R.mipmap.down_arrow);

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firstTime = false;
            }
            },5000);*/

        ((Dashboard) mActivity).setUserInteractionListener(new Dashboard.UserInteractionListener() {
            @Override
            public void onUserInteraction() {

                // Log.v("asfasfasf","asfasfasfcvdeeee");
                firstTime = false;

                // Do whatever you want here, during user interaction
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        switch (compoundButton.getId()) {
            case R.id.rbDocReferred:
                if (isChecked) {

                    east.setText("");
                    west.setText("");
                    north.setText("");
                    south.setText("");

                    east.setEnabled(edit_page);
                    west.setEnabled(edit_page);
                    north.setEnabled(edit_page);
                    south.setEnabled(edit_page);

                    radio_val = "1";

                    llDocRefCheck.setVisibility(View.VISIBLE);
                    llNotApplicable.setVisibility(View.GONE);
                    tvSendEmail.setVisibility(View.GONE);
                    rbNoBoundary.setChecked(false);
                    rbNotApplicable.setChecked(false);

                    proceed.setBackgroundColor(getResources().getColor(R.color.app_header));
                    proceed.setClickable(true);
                    tvProceed.setTextColor(getResources().getColor(R.color.white));
                }
                break;

            case R.id.rbNoBoundary:

                if (isChecked) {
                    east.setText(R.string.no_boundary_schedule_mail);
                    west.setText(R.string.no_boundary_schedule_mail);
                    north.setText(R.string.no_boundary_schedule_mail);
                    south.setText(R.string.no_boundary_schedule_mail);

                    east.setEnabled(edit_page);
                    west.setEnabled(edit_page);
                    north.setEnabled(edit_page);
                    south.setEnabled(edit_page);

                    radio_val = "2";

                    rbNoBoundary.setText(R.string.no_boundary_schedule_mail);

                    pref.set(Constants.documentNoBoun, radio_val);
                    pref.commit();

                    llDocRefCheck.setVisibility(View.GONE);
                    llNotApplicable.setVisibility(View.GONE);
                    rbDocReferred.setChecked(false);
                    rbNotApplicable.setChecked(false);
                    //  tvSendEmail.setVisibility(View.VISIBLE);
                    proceed.setBackgroundColor(getResources().getColor(R.color.progressgrey));
                    proceed.setClickable(false);
                    tvProceed.setTextColor(getResources().getColor(R.color.black));

                    SpannableString ss = new SpannableString(rbNoBoundary.getText().toString());
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {

                            AlertEmailContentPopup();

                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                            ds.setColor(getResources().getColor(R.color.app_header));
                        }
                    };
                    ss.setSpan(clickableSpan, 53, 63, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    rbNoBoundary.setText(ss);
                    rbNoBoundary.setMovementMethod(LinkMovementMethod.getInstance());
                    rbNoBoundary.setHighlightColor(Color.TRANSPARENT);
                } else {
                    pref.set(Constants.documentNoBoun, "");
                    pref.commit();
                    rbNoBoundary.setText(R.string.no_boundary_schedule);
                }


                break;
            case R.id.rbNotApplicable:
                if (isChecked) {

                    east.setText(R.string.not_applicable);
                    west.setText(R.string.not_applicable);
                    north.setText(R.string.not_applicable);
                    south.setText(R.string.not_applicable);


                    east.setEnabled(edit_page);
                    west.setEnabled(edit_page);
                    north.setEnabled(edit_page);
                    south.setEnabled(edit_page);

                    radio_val = "3";

                    llDocRefCheck.setVisibility(View.GONE);
                    llNotApplicable.setVisibility(View.VISIBLE);
                    tvSendEmail.setVisibility(View.GONE);
                    rbDocReferred.setChecked(false);
                    rbNoBoundary.setChecked(false);

                    proceed.setBackgroundColor(getResources().getColor(R.color.app_header));
                    proceed.setClickable(true);
                    tvProceed.setTextColor(getResources().getColor(R.color.white));
                }
                break;

            case R.id.cbTitle:
                if (isChecked) {
                    if (check1.isEmpty() && !check1.contains("1")) {
                        check1 = "1";
                    } else if (!check1.isEmpty() && !check1.contains("1")) {
                        // check1 = check1+","+"1";
                        check1 = String.join(",", check1, "1");
                    }
                } else {
                    if (check1.contains("1")) {
                        check1 = check1.replace("1", "");
                    }
                }
                break;

            case R.id.cbSaleDeed:
                if (isChecked) {
                    if (check1.isEmpty() && !check1.contains("2")) {
                        check1 = "2";
                    } else if (!check1.isEmpty() && !check1.contains("2")) {
                        //check1 = check1+","+"2";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            check1 = String.join(",", check1, "2");
                        } else {
                            check1 = check1 + "," + "1";
                        }
                    }
                } else {
                    if (check1.contains("2")) {
                        check1 = check1.replace("2", "");
                    }
                }
                break;

            case R.id.cbConveyanceDeed:
                if (isChecked) {
                    if (check1.isEmpty() && !check1.contains("3")) {
                        check1 = "3";
                    } else if (!check1.isEmpty() && !check1.contains("3")) {
                        //check1 = check1+","+"3";
                        check1 = String.join(",", check1, "3");
                    }
                } else {
                    if (check1.contains("3")) {
                        check1 = check1.replace("3", "");
                    }
                }
                break;

            case R.id.cbPossessionCertificate:
                if (isChecked) {
                    if (check1.isEmpty() && !check1.contains("4")) {
                        check1 = "4";
                    } else if (!check1.isEmpty() && !check1.contains("4")) {
                        // check1 = check1+","+"4";
                        check1 = String.join(",", check1, "4");
                    }
                } else {
                    if (check1.contains("4")) {
                        check1 = check1.replace("4", "");
                    }
                }
                break;

            case R.id.cbCizra:
                if (isChecked) {
                    if (check1.isEmpty() && !check1.contains("5")) {
                        check1 = "5";
                    } else if (!check1.isEmpty() && !check1.contains("5")) {
                        //check1 = check1+","+"5";
                        check1 = String.join(",", check1, "5");
                    }
                } else {
                    if (check1.contains("5")) {
                        check1 = check1.replace("5", "");
                    }
                }
                break;

            case R.id.cbApproved:
                if (isChecked) {
                    if (check1.isEmpty() && !check1.contains("6")) {
                        check1 = "6";
                    } else if (!check1.isEmpty() && !check1.contains("6")) {
                        //  check1 = check1+","+"6";
                        check1 = String.join(",", check1, "6");
                    }
                } else {
                    if (check1.contains("6")) {
                        check1 = check1.replace("6", "");
                    }
                }
                break;

            case R.id.cbAnyOtherDocRef:

                if (isChecked) {
                    llRemarkDocRef.setVisibility(View.VISIBLE);
                    if (check1.isEmpty() && !check1.contains("7")) {
                        check1 = "7";
                    } else if (!check1.isEmpty() && !check1.contains("7")) {
                        // check1 = check1+","+"7";

                        check1 = String.join(",", check1, "7");

                    }
                } else {
                    llRemarkDocRef.setVisibility(View.GONE);
                    if (check1.contains("7")) {
                        check1 = check1.replace("7", "");
                    }
                }

            case R.id.cbFlat:
                if (isChecked) {
                    if (check2.isEmpty() && !check2.contains("1")) {
                        check2 = "1";
                    } else if (!check2.isEmpty() && !check2.contains("1")) {
                        //  check1 = check1+","+"6";
                        check2 = String.join(",", check2, "1");
                    }
                } else {
                    if (check2.contains("1")) {
                        check2 = check2.replace("1", "");
                    }
                }
                break;

            case R.id.cbVery:
                if (isChecked) {
                    if (check2.isEmpty() && !check2.contains("2")) {
                        check2 = "2";
                    } else if (!check2.isEmpty() && !check2.contains("2")) {
                        //  check1 = check1+","+"6";
                        check2 = String.join(",", check2, "2");
                    }
                } else {
                    if (check2.contains("2")) {
                        check2 = check2.replace("2", "");
                    }
                }
                break;

            case R.id.cbNpa:
                if (isChecked) {
                    if (check2.isEmpty() && !check2.contains("3")) {
                        check2 = "3";
                    } else if (!check2.isEmpty() && !check2.contains("3")) {
                        //  check1 = check1+","+"6";
                        check2 = String.join(",", check2, "3");
                    }
                } else {
                    if (check2.contains("3")) {
                        check2 = check2.replace("3", "");
                    }
                }
                break;

            case R.id.cbAnyOtherNotApplicable:

                if (isChecked) {
                    llRemarkNotApplicable.setVisibility(View.VISIBLE);
                    if (check2.isEmpty() && !check2.contains("4")) {
                        check2 = "4";
                    } else if (!check2.isEmpty() && !check2.contains("4")) {
                        // check1 = check1+","+"7";

                        check2 = String.join(",", check2, "4");

                    }
                } else {
                    llRemarkNotApplicable.setVisibility(View.GONE);
                    if (check2.contains("4")) {
                        check2 = check2.replace("4", "");
                    }
                }

        }
    }

    public void setListener() {
        tvSendEmail.setOnClickListener(this);
        rlspinProp.setOnClickListener(this);
        property_spinner.setClickable(false);
        property_spinner.setEnabled(false);
        rlOne.setOnClickListener(this);
        rlTwo.setOnClickListener(this);
        rlThree.setOnClickListener(this);
        rlFour.setOnClickListener(this);

        //dots.setOnClickListener(this);
        ll_view.setOnClickListener(this);
        proceed.setOnClickListener(this);
        //  back.setOnClickListener(this);
        info.setOnClickListener(this);
        browse.setOnClickListener(this);
        tv_pdf1.setOnClickListener(this);
        tv_pdf2.setOnClickListener(this);
        tv_pdf3.setOnClickListener(this);
        tv_pdf4.setOnClickListener(this);
        tv_pdf5.setOnClickListener(this);
        view_media1.setOnClickListener(this);
        view_media2.setOnClickListener(this);
        view_media3.setOnClickListener(this);
        view_media4.setOnClickListener(this);
        view_media5.setOnClickListener(this);
        rl_browse.setOnClickListener(this);
        schedule_media1.setOnClickListener(this);
        view_address.setOnClickListener(this);
        // ll_landmark.setOnClickListener(this);
        iv_phonecall.setOnClickListener(this);
        // ivLocation.setOnClickListener(this);
        rbDocReferred.setOnCheckedChangeListener(this);
        rbNotApplicable.setOnCheckedChangeListener(this);
        rbNoBoundary.setOnCheckedChangeListener(this);

        cbAnyOtherDocRef.setOnCheckedChangeListener(this);
        cbTitle.setOnCheckedChangeListener(this);
        cbSaleDeed.setOnCheckedChangeListener(this);
        cbConveyanceDeed.setOnCheckedChangeListener(this);
        cbPossessionCertificate.setOnCheckedChangeListener(this);
        cbCizra.setOnCheckedChangeListener(this);
        cbApproved.setOnCheckedChangeListener(this);
        cbFlat.setOnCheckedChangeListener(this);
        cbVery.setOnCheckedChangeListener(this);
        cbNpa.setOnCheckedChangeListener(this);
        cbAnyOtherNotApplicable.setOnCheckedChangeListener(this);

        ((RadioButton) radioGroupCity.getChildAt(0)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    city_type = "1";

                    if (!firstTime) {
                        setCities(districtList.get(spinnerDistrict.getSelectedItemPosition()).get("id"), "");
                    }

                }
            }
        });

        ((RadioButton) radioGroupCity.getChildAt(1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    city_type = "2";

                    if (!firstTime) {
                        setVillages(districtList.get(spinnerDistrict.getSelectedItemPosition()).get("id"), "");
                    }

                }

            }
        });

        ((RadioButton) radioGroupCity.getChildAt(2)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    city_type = "3";

                    if (!firstTime) {
                        setTehsils(districtList.get(spinnerDistrict.getSelectedItemPosition()).get("id"), "");
                    }
                }

            }
        });


        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                stateId = stateList.get(i).get("id");

                if (!firstTime) {
                    setDistrict("", stateList.get(i).get("id"));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                districtId = districtList.get(i).get("id");

                if (!firstTime) {
                    if (city_type.equals("1")) {
                        setCities(districtList.get(i).get("id"), "");
                    } else if (city_type.equals("2")) {
                        setVillages(districtList.get(i).get("id"), "");
                    } else if (city_type.equals("3")) {
                        setTehsils(districtList.get(i).get("id"), "");
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCityVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (city_type.equalsIgnoreCase("1")) {

                    city_val = cityList.get(i).get("id");

                    if (city_val.equals(cityList.size())) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                    } else if (cityList.get(i).get("name").equalsIgnoreCase("Other")) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                    } else {
                        etCityVillageTehsilOther.setVisibility(View.GONE);
                    }
                } else if (city_type.equalsIgnoreCase("2")) {

                    city_val = villageList.get(i).get("id");

                    if (city_val.equals(villageList.size())) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                    } else if (villageList.get(i).get("name").equalsIgnoreCase("Other")) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                    } else {
                        etCityVillageTehsilOther.setVisibility(View.GONE);
                    }
                } else if (city_type.equalsIgnoreCase("3")) {

                    city_val = tehsilList.get(i).get("id");

                    if (city_val.equals(tehsilList.size())) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                    } else if (tehsilList.get(i).get("name").equalsIgnoreCase("Other")) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                    } else {
                        etCityVillageTehsilOther.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
//    **********************************************************************************************************************************************************

    /*  @Override
      public void onUserInteraction() {
          super.onUserInteraction();
          firstTime = false;
      }
  */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlOne:

                if (llDocsAttached.getVisibility() == View.VISIBLE) {
                    llDocsAttached.setVisibility(View.GONE);
                    ivOne.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    llDocsAttached.setVisibility(View.VISIBLE);
                    ivOne.setBackgroundResource(R.mipmap.up_arrow);

                }

                if (llPrelimDetails.getVisibility() == View.VISIBLE) {
                    llPrelimDetails.setVisibility(View.GONE);
                    ivTwo.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (llDocsToCollectExp.getVisibility() == View.VISIBLE) {
                    llDocsToCollectExp.setVisibility(View.GONE);
                    ivThree.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (llSiteSurvey.getVisibility() == View.VISIBLE) {
                    llSiteSurvey.setVisibility(View.GONE);
                    ivFour.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;
            case R.id.rlTwo:

                if (llPrelimDetails.getVisibility() == View.VISIBLE) {
                    llPrelimDetails.setVisibility(View.GONE);
                    ivTwo.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    llPrelimDetails.setVisibility(View.VISIBLE);
                    ivTwo.setBackgroundResource(R.mipmap.up_arrow);

                }

                if (llDocsAttached.getVisibility() == View.VISIBLE) {
                    llDocsAttached.setVisibility(View.GONE);
                    ivOne.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (llDocsToCollectExp.getVisibility() == View.VISIBLE) {
                    llDocsToCollectExp.setVisibility(View.GONE);
                    ivThree.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (llSiteSurvey.getVisibility() == View.VISIBLE) {
                    llSiteSurvey.setVisibility(View.GONE);
                    ivFour.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;
            case R.id.rlThree:

                if (llDocsToCollectExp.getVisibility() == View.VISIBLE) {
                    llDocsToCollectExp.setVisibility(View.GONE);
                    ivThree.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    llDocsToCollectExp.setVisibility(View.VISIBLE);
                    ivThree.setBackgroundResource(R.mipmap.up_arrow);
                }

                if (llDocsAttached.getVisibility() == View.VISIBLE) {
                    llDocsAttached.setVisibility(View.GONE);
                    ivOne.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (llPrelimDetails.getVisibility() == View.VISIBLE) {
                    llPrelimDetails.setVisibility(View.GONE);
                    ivTwo.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (llSiteSurvey.getVisibility() == View.VISIBLE) {
                    llSiteSurvey.setVisibility(View.GONE);
                    ivFour.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }


                break;
            case R.id.rlFour:

                if (llSiteSurvey.getVisibility() == View.VISIBLE) {
                    llSiteSurvey.setVisibility(View.GONE);
                    ivFour.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    llSiteSurvey.setVisibility(View.VISIBLE);
                    ivFour.setBackgroundResource(R.mipmap.up_arrow);
                }

                if (llDocsAttached.getVisibility() == View.VISIBLE) {
                    llDocsAttached.setVisibility(View.GONE);
                    ivOne.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (llPrelimDetails.getVisibility() == View.VISIBLE) {
                    llPrelimDetails.setVisibility(View.GONE);
                    ivOne.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (llDocsToCollectExp.getVisibility() == View.VISIBLE) {
                    llDocsToCollectExp.setVisibility(View.GONE);
                    ivThree.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;

            case R.id.tvSendEmail:

                if (Utils.isNetworkConnectedMainThred(mActivity)) {
                    hitSendBoundarySchEmail();
                } else {
                    Toast.makeText(mActivity, R.string.noInternetConnection, Toast.LENGTH_SHORT).show();
                }

                break;

          /*  case R.id.ivLocation:
                if (!pref.get(Constants.landmark_lat).isEmpty() &&
                        !pref.get(Constants.landmark_long).isEmpty()){
                    startActivity(new Intent(this,LandmarkMapActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), "Latitude and Longitude not available", Toast.LENGTH_SHORT).show();
                }
                break;*/

            case R.id.iv_phonecall:
                callstatus = 0;
                CallingDialog();
                break;
            case R.id.rlspinProp:
                alertAssestChooser();
                Log.v("SpinnerChoosen", "SpinnerClickec");

                break;
            case R.id.ll_proceed:

                if(assestChanged)
                alertAssestChanged();
                else
                if (edit_page) {
                    if (priliminary_status.equals("0")) {

                        checkpass();

                    } else {
                        if(!ass_typeId.isEmpty()) {
                            pref.set(Constants.type_of_assets, ass_typename);
                            pref.commit();
                        }
                            setDefaultSurvey();
                        if (status_ok.equals("1")) {

                            if (Utils.isNetworkConnectedMainThred(mActivity)) {
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(false);
                                hitPostPriliminaryApi();

                            } else {
                                Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Log.v("stststst333", "status:" + status_ok);

                            if (onr_name.getText().toString().equals("") && edit_page) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter name of the Asset Owner", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();

                                onr_name.setError("Please enter name of the Asset Owner");

                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        scrollView.scrollTo(0, llName.getTop());
                                        onr_name.requestFocus();
                                    }
                                });


                                llPrelimDetails.setVisibility(View.VISIBLE);
                                ivTwo.setBackgroundResource(R.mipmap.up_arrow);

                                llDocsAttached.setVisibility(View.GONE);
                                ivOne.setBackgroundResource(R.mipmap.down_arrow);

                                llDocsToCollectExp.setVisibility(View.GONE);
                                ivThree.setBackgroundResource(R.mipmap.down_arrow);

                                llSiteSurvey.setVisibility(View.GONE);
                                ivFour.setBackgroundResource(R.mipmap.down_arrow);

                            }/*else if (et_landmark.getText().toString().isEmpty()){
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                                    "Please enter Landmark Address", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();
                        }*/ else if (etPropertyNo.getText().toString().isEmpty()) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter Property No.", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if (etColonyArea.getText().toString().isEmpty()) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter Colony/Area", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if (spinnerCityVillage.getSelectedItemPosition() == 0) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please choose City/ Village/ Tehsil", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if (etCityVillageTehsilOther.getVisibility() == View.VISIBLE
                                    && etCityVillageTehsilOther.getText().toString().isEmpty()) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter Other City/ Village/ Tehsil", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if (etPincode.getText().toString().isEmpty()) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter Pincode", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if (property_spinner.getSelectedItemPosition() == 0) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please select type of property", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if ((llTotalLandArea.getVisibility() == View.VISIBLE
                                    && landarea.getText().toString().isEmpty()) && (llTotalLandArea.getVisibility() == View.VISIBLE
                                    && etAsPerMap.getText().toString().isEmpty())) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter total land area as per title deed or as per Map", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
//                        } else if (llTotalLandArea.getVisibility() == View.VISIBLE
//                                && etAsPerMap.getText().toString().isEmpty()) {
//                            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
//                                    "Please enter total land area as per map", Snackbar.LENGTH_SHORT);
//                            mySnackbar.show();
                            } else if ((llTotalCoveredArea.getVisibility() == View.VISIBLE
                                    && coverarea.getText().toString().isEmpty()) && (llTotalCoveredArea.getVisibility() == View.VISIBLE
                                    && etAsPerMapCovered.getText().toString().isEmpty())) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter total covered area as per title deed or as per Map", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
//                        } else if (llTotalCoveredArea.getVisibility() == View.VISIBLE
//                                && etAsPerMapCovered.getText().toString().isEmpty()) {
//                            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
//                                    "Please enter total covered area as per map", Snackbar.LENGTH_SHORT);
//                            mySnackbar.show();
                            } else if (east.getText().toString().isEmpty()) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter boundary schedule of the property", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if (west.getText().toString().isEmpty()) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter boundary schedule of the property", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if (north.getText().toString().isEmpty()) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter boundary schedule of the property", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else if (south.getText().toString().isEmpty()) {
                                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                        "Please enter boundary schedule of the property", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } else {
                                //checkpass();

                                if (onr_name.getText().toString().equals("")) {
                                    Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                                            "Please enter name of the Asset Owner", Snackbar.LENGTH_SHORT);
                                    mySnackbar.show();

                                    onr_name.setError("Please enter name of the Asset Owner");

                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            scrollView.scrollTo(0, llName.getTop());
                                            onr_name.requestFocus();
                                        }
                                    });

                                    llPrelimDetails.setVisibility(View.VISIBLE);
                                    ivTwo.setBackgroundResource(R.mipmap.up_arrow);

                                    llDocsAttached.setVisibility(View.GONE);
                                    ivOne.setBackgroundResource(R.mipmap.down_arrow);

                                    llDocsToCollectExp.setVisibility(View.GONE);
                                    ivThree.setBackgroundResource(R.mipmap.down_arrow);

                                    llSiteSurvey.setVisibility(View.GONE);
                                    ivFour.setBackgroundResource(R.mipmap.down_arrow);
                                } else {
                                /*if (!(owner_name2.equals(onr_name.getText().toString())) && !(address2.equals(onr_address.getText().toString()))) {
                                    name_status = "1";
                                    SurveyorList();
                                    Log.v("sttus=====1", name_status);
                                }*/

                                    //   iname.setText(owner_name2);
                                    //        uname.setText(onr_name.getText().toString());
                                    //        iaddress.setText(colonyArea);
                                    //        uaddress.setText(etColonyArea.getText().toString());

                                    if (!owner_name2.equalsIgnoreCase(onr_name.getText().toString())) {
                                        name_status = "2";
                                    }
                                    if (llTotalLandArea.getVisibility() == View.VISIBLE) {
                                        if (!asPerMapTitle.equalsIgnoreCase(etAsPerMap.getText().toString()) || (!landareaunitmap.equalsIgnoreCase(landareaunitmap1))) {
                                            mapLand_status = "2";
                                        }
                                        if (!titleLand_area.equalsIgnoreCase(landarea.getText().toString()) || (!landareaunit.equalsIgnoreCase(landareaunit1))) {
                                            titleLand_status = "2";
                                        }

                                    }
                                    if (llTotalCoveredArea.getVisibility() == View.VISIBLE) {
                                        if (!titleCovered_area.equalsIgnoreCase(coverarea.getText().toString()) || (!coveredareaunit.equalsIgnoreCase(coveredareaunit1))) {
                                            titleCovered_status = "2";
                                        }
                                        if (!asPerMapCovered.equalsIgnoreCase(etAsPerMapCovered.getText().toString()) || (!coveredareaunitmap.equalsIgnoreCase(coveredareaunitmap1))) {
                                            mapCovered_status = "2";
                                        }
                                    }
//                                    if (!titleLand_area.equalsIgnoreCase(landarea.getText().toString())||(!landareaunit.equalsIgnoreCase(landareaunit1))) {
//                                        titleLand_status = "2";
//                                    }

                                    if (!colonyArea.equals(etColonyArea.getText().toString())) {
                                        address_status = "2";
                                    }

                                /*if (!colonyArea.isEmpty()){
                                    if (!colonyArea.equals(etColonyArea.getText().toString())){
                                        address_status = "2";
                                    }
                                }*/

                                    if ((name_status.equalsIgnoreCase("2") || address_status.equalsIgnoreCase("2") || (mapCovered_status.equalsIgnoreCase("2")))) {
                                        SurveyorList();
                                    } else if (titleLand_status.equalsIgnoreCase("2") || ((mapLand_status.equalsIgnoreCase("2") || titleCovered_status.equalsIgnoreCase("2")))) {
                                        SurveyorList();
                                    } else {
                                        checkpass();
                                    }


/*
                                if (!(owner_name2.equals(onr_name.getText().toString()))) {
                                    name_status = "2";
                                    SurveyorList();
                                    Log.v("sttus=====2", name_status);
                                } else if (!(colonyArea.equals(etColonyArea.getText().toString()))) {
                                    name_status = "3";
                                    SurveyorList();
                                    Log.v("sttus=====3", name_status);
                                } else {
                                    checkpass();
                                }*/
                                }
                            }
                        }
                    }
                } else ((Dashboard) mActivity).displayView(3);






                /*if (status_ok.equals("1")) {
                    checkpass();
                } else {
                    if (!(owner_name2.equals(onr_name.getText().toString())) && !(address2.equals(onr_address.getText().toString()))) {
                        name_status = "1";
                        SurveyorList();
                        Log.v("sttus=====1", name_status);
                    } else if (!(owner_name2.equals(onr_name.getText().toString()))) {
                        name_status = "2";
                        SurveyorList();
                        Log.v("sttus=====2", name_status);
                    } else if (!(address2.equals(onr_address.getText().toString()))) {
                        name_status = "3";
                        SurveyorList();
                        Log.v("sttus=====3", name_status);
                    } else {
                        checkpass();
                    }
                }*/

                try {
                    pref.set(Constants.colony, etColonyArea.getText().toString());
                    pref.set(Constants.cityVillageTehsil, cityVillageTehsilText);
                    pref.set(Constants.district, districtText);

                    if (spinner_east.getSelectedItemPosition() == 0) {
                        pref.set(Constants.directionType, "0");
                    } else {
                        pref.set(Constants.directionType, "1");
                    }

                    pref.set(Constants.directionOne, east.getText().toString());
                    pref.set(Constants.directionTwo, west.getText().toString());
                    pref.set(Constants.directionThree, north.getText().toString());
                    pref.set(Constants.directionFour, south.getText().toString());
                    pref.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.ll_view:
                //  doc_status = "1";
                //   ViewDocumentDialog();
                break;

            case R.id.rl_back:
                //   onBackPressed();
                break;


            case R.id.iv_info:
                dialog_status = "0";
                color_status = "3";
                InfoDialog();
                break;

            case R.id.btn_browse:
               /* if (image_status.equals("5")) {
                    Toast.makeText(this, "Sorry,cant browse images more than 5.", Toast.LENGTH_SHORT).show();
                } else {
                    selectImage();
                }*/
                break;

            case R.id.rl_browse:
                schedule_status = "1";
                selectImage();
                break;


            case R.id.tv_pdfone:
              /*  if (pdf1.equals("")) {
                    Toast.makeText(this, " No Pdf Found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf1.toString()));
                    startActivity(intent);
                }*/
                break;

            case R.id.btn_one:
                if (pdf1.equals("")) {
                    Toast.makeText(mActivity, "No Pdf Found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf1.toString()));
                    startActivity(intent);
                }
                break;

            case R.id.btn_two:
                if (pdf2.equals("")) {
                    Toast.makeText(mActivity, "No Pdf Found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf2.toString()));
                    startActivity(intent);
                }
                break;

            case R.id.btn_three:
                if (pdf3.equals("")) {
                    Toast.makeText(mActivity, "No Pdf Found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf3.toString()));
                    startActivity(intent);
                }
                break;

            case R.id.btn_four:
                if (pdf4.equals("")) {
                    Toast.makeText(mActivity, " No Pdf Found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf4.toString()));
                    startActivity(intent);
                }
                break;

            case R.id.btn_five:
                if (pdf5.equals("")) {
                    Toast.makeText(mActivity, " No Pdf Found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf5.toString()));
                    startActivity(intent);
                }
                break;

         /*   case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;*/
            case R.id.ll_viewadd:
                //   intent = new Intent(PriliminaryActivity.this, ViewMapActivity.class);
                //    startActivity(intent);
                break;


        }
    }

    public void CallingDialog() {

        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.calling_dialog);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //final int poss;
        ImageView close = dialog.findViewById(R.id.iv_close);
        final Button cal = dialog.findViewById(R.id.btn_call);
        final Button dnt_cal = dialog.findViewById(R.id.btn_notcall);
        TextView header = dialog.findViewById(R.id.tv_header);
        final ListView lv_call = dialog.findViewById(R.id.lv_calls);

        {
            co_number_list.clear();

            HashMap<String, String> hmap = new HashMap<>();
            hmap.put("number", number.getText().toString());
            co_number_list.add(hmap);

            header.setText("Coordinating Person Number");
            adater = new CallAdapter(mActivity, co_number_list);
            lv_call.setAdapter(adater);
            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + alternat_list.get(position).get("number")));//"tel:"+number.getText().toString();
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
                    startActivity(callIntent);*/

                    //pos = position;
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);

                    try {
                        for (int i = 0; i < lv_call.getChildCount(); i++) {
                            if (position == i) {
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                                callstatus = 1;
                                lv_call.setAdapter(adater);


                                // iv_phn.setBackgroundResource(R.mipmap.phone_white);
                            } else {
                                callstatus = 2;
                                lv_call.setAdapter(adater);
                                lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                //   iv_phn.setBackgroundResource(R.drawable.ic_telephone);

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);

                callIntent.setData(Uri.parse("tel:" + number.getText().toString()));//"tel:"+number.getText().toString();

                if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    //StartConfirmation popup
    public void ViewDocumentDialog() {

        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.view_dialog);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView cancel = (ImageView) dialog.findViewById(R.id.iv_close);
        ListView doc = dialog.findViewById(R.id.lv_docs);
        //  DocumentsAdapter doc_adapter;

        //doc_adapter = new DocumentsAdapter(this, doc_list);
        //  doc.setAdapter(doc_adapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //StartConfirmation popup
    public void InfoDialog() {
        if (pref.get(Constants.page_editable).equals("true")) {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.info_dialog);

            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            //   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            ImageView cancel = (ImageView) dialog.findViewById(R.id.iv_close);
            final TextView content = (TextView) dialog.findViewById(R.id.tv_info);
            TextView ok = (TextView) dialog.findViewById(R.id.tv_ok);
            RelativeLayout rl_header = dialog.findViewById(R.id.rl_header);
            if (color_status.equals("1")) {
                rl_header.setBackgroundColor(getResources().getColor(R.color.light_red));
            } else {

            }


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (boundry_status.equals("1")) {
                        content.setText(R.string.mail);
                        if (Utils.isNetworkConnectedMainThred(mActivity)) {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            Hit_PostDocument_Api();

                        } else {
                            Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                        }
                    } else {

                    }
                    dialog.dismiss();
                }
            });
            if (dialog_status.equals("1") && pref.get(Constants.page_editable).equals("true")) {
                content.setText(R.string.priliminary_dailog);
            } else {

            }

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }

    //Surveyorlist popup
    public void SurveyorList() {

        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.surveyor_dialog);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        //   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ImageView close = dialog.findViewById(R.id.iv_close);
        TextView iname = dialog.findViewById(R.id.tv_iname);
        TextView iaddress = dialog.findViewById(R.id.tv_iaddress);
        TextView uname = dialog.findViewById(R.id.tv_uname);
        TextView uaddress = dialog.findViewById(R.id.tv_uaddress);
        TextView ok = dialog.findViewById(R.id.tv_ok);
        TextView iTitleLand = dialog.findViewById(R.id.tv_iTitleLand);
        TextView uTitleLand = dialog.findViewById(R.id.tv_uTitleLand);
        TextView iMapLand = dialog.findViewById(R.id.tv_iMapLand);
        TextView uMapLand = dialog.findViewById(R.id.tv_uMapLand);
        TextView iTitleCovered = dialog.findViewById(R.id.tv_iLandCovered);
        TextView uTitleCovered = dialog.findViewById(R.id.tv_uLandCovered);
        TextView iMapCovered = dialog.findViewById(R.id.tv_iMapCovered);
        TextView uMapCovered = dialog.findViewById(R.id.tv_uMapCovered);
        TextView iTitleLandUnit = dialog.findViewById(R.id.tv_iTitleLandUnit);
        LinearLayout llname = dialog.findViewById(R.id.ll_name);
        LinearLayout lladdress = dialog.findViewById(R.id.ll_address);
        LinearLayout lltitleLandDeed = dialog.findViewById(R.id.ll_titleLandDeed);
        LinearLayout llMapLand = dialog.findViewById(R.id.ll_MapLand);
        LinearLayout lltitleLandCovered = dialog.findViewById(R.id.ll_titleLandCovered);
        LinearLayout llMapLandCovered = dialog.findViewById(R.id.ll_MapLandCovered);
        Log.v("name_status====one", name_status);
        iname.setText(owner_name2);
        uname.setText(onr_name.getText().toString());
        Log.v("jfdd", landareaunit + "   " + landareaunit1);
        iaddress.setText(colonyArea);
        uaddress.setText(etColonyArea.getText().toString());

        iTitleLand.setText(titleLand_area + " " + landareaunit1);
        uTitleLand.setText(landarea.getText().toString() + " " + landareaunit);
//        iTitleLandUnit.setText(landareaunit1);
//        uTitleLandUnit.setText(landareaunit);

        iMapLand.setText(asPerMapTitle + " " + landareaunitmap1);
        uMapLand.setText(etAsPerMap.getText().toString() + " " + landareaunitmap);

        iTitleCovered.setText(titleCovered_area + " " + coveredareaunit1);
        uTitleCovered.setText(coverarea.getText().toString() + " " + coveredareaunit);

        iMapCovered.setText(asPerMapCovered + " " + coveredareaunitmap1);
        uMapCovered.setText(etAsPerMapCovered.getText().toString() + " " + coveredareaunitmap);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_ok = "1";

                if (name_status.equalsIgnoreCase("2")) {
                    status_name = "1";
                } else {
                    status_name = "";
                }

                if (address_status.equalsIgnoreCase("2")) {
                    status_address = "1";
                } else {
                    status_address = "";
                }
                if (titleLand_status.equalsIgnoreCase("2")) {
                    status_land = "1";
                } else {
                    status_land = "";
                }
                if (mapLand_status.equalsIgnoreCase("2")) {
                    status_mland = "1";
                } else {
                    status_mland = "";
                }
                if (titleCovered_status.equalsIgnoreCase("2")) {
                    status_tcland = "1";
                } else {
                    status_tcland = "";
                }
                if (mapCovered_status.equalsIgnoreCase("2")) {
                    status_mCmap = "1";
                } else {
                    status_mCmap = "";
                }

                dialog.dismiss();
            }
        });

        if (name_status.equalsIgnoreCase("2") && status_name.equalsIgnoreCase("")) {
            llname.setVisibility(View.VISIBLE);
        } else {
            llname.setVisibility(View.GONE);
        }
        //***********************************************************************************************************************
//        if (name_status.equalsIgnoreCase("2") && status_name.equalsIgnoreCase("")){
//            llname.setVisibility(View.VISIBLE);
//        }else {
//            llname.setVisibility(View.GONE);
//        }
        //***********************************************************************************************************************
        if (address_status.equalsIgnoreCase("2") && status_address.equalsIgnoreCase("")) {
            lladdress.setVisibility(View.VISIBLE);
        } else {
            lladdress.setVisibility(View.GONE);
        }
        if (titleLand_status.equalsIgnoreCase("2") && status_land.equalsIgnoreCase("")) {
            lltitleLandDeed.setVisibility(View.VISIBLE);
        } else {
            lltitleLandDeed.setVisibility(View.GONE);
        }
        if (mapLand_status.equalsIgnoreCase("2") && status_land.equalsIgnoreCase("")) {
            llMapLand.setVisibility(View.VISIBLE);
        } else {
            llMapLand.setVisibility(View.GONE);
        }
        if (titleCovered_status.equalsIgnoreCase("2") && status_land.equalsIgnoreCase("")) {
            lltitleLandCovered.setVisibility(View.VISIBLE);
        } else {
            lltitleLandCovered.setVisibility(View.GONE);
        }
        if (mapCovered_status.equalsIgnoreCase("2") && status_land.equalsIgnoreCase("")) {
            llMapLandCovered.setVisibility(View.VISIBLE);
        } else {
            llMapLandCovered.setVisibility(View.GONE);
        }
       /* if (name_status.equals("2")) {
            lladdress.setVisibility(View.VISIBLE);
        } else if (name_status.equals("3")) {
            llname.setVisibility(View.VISIBLE);
        } else {
            lladdress.setVisibility(View.VISIBLE);
            llname.setVisibility(View.VISIBLE);
        }*/


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void selectImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PICTURE);
    }

    public void checkpass() {

        if (onr_name.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter name of the Asset Owner", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } /*else if (onr_address.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter Postal Address", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }*/ else if (property_name.getText().toString().equals("Select Type Of Property")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter property type", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } /*else if (landarea.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter land area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (coverarea.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter cover area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }*/ else if (east.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter east area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (west.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter west area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (north.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter north area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (south.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter south area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } /*else if (document_name.getText().toString().equals("Select Document")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter document type", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } */ else if (!rbDocReferred.isChecked() && !rbNoBoundary.isChecked() && !rbNotApplicable.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please select document detail", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rbDocReferred.isChecked() && check1.isEmpty()) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please select document detail", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rbNotApplicable.isChecked() && check2.isEmpty()) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please select Not Applicable Reason", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (name.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (number.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter number", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (relationship.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter relationship", Snackbar.LENGTH_SHORT);
            mySnackbar.show();

        } else {

          /*  if (onr_name.getText().toString().equals("")) {
                Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                        "Please enter name of the Asset Owner", Snackbar.LENGTH_SHORT);
                mySnackbar.show();

                onr_name.setError("Please enter name of the Asset Owner");

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, llName.getTop());
                        onr_name.requestFocus();
                    }
                });

                llPrelimDetails.setVisibility(View.VISIBLE);
                ivTwo.setBackgroundResource(R.mipmap.up_arrow);

                llDocsAttached.setVisibility(View.GONE);
                ivOne.setBackgroundResource(R.mipmap.down_arrow);

                llDocsToCollectExp.setVisibility(View.GONE);
                ivThree.setBackgroundResource(R.mipmap.down_arrow);

                llSiteSurvey.setVisibility(View.GONE);
                ivFour.setBackgroundResource(R.mipmap.down_arrow);
            } else {

                if (!owner_name2.equalsIgnoreCase(onr_name.getText().toString())){
                    name_status = "2";
                }

                if (!colonyArea.equals(etColonyArea.getText().toString())){
                    address_status = "2";
                }

                if (name_status.equalsIgnoreCase("2") || address_status.equalsIgnoreCase("2")){
                    SurveyorList();
                }
                else {
                    checkpass();
                }

            }*/


            if ((!landarea.getText().toString().equalsIgnoreCase("not_selected") && !landarea.getText().toString().equalsIgnoreCase("not_selected") &&
                    !landarea.getText().toString().equalsIgnoreCase("not applicable")) &&
                    (!coverarea.getText().toString().equalsIgnoreCase("not_selected") &&
                            !coverarea.getText().toString().equalsIgnoreCase("not applicable") &&
                            !coverarea.getText().toString().equalsIgnoreCase(""))) {
                try {
                    int maxVal = Integer.valueOf(landarea.getText().toString().trim());
                    int minVal = Integer.valueOf(coverarea.getText().toString().trim());
                    if (minVal > maxVal) {
                        Toast.makeText(mActivity, "Covered area could not be more than Land area.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Utils.isNetworkConnectedMainThred(mActivity)) {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            hitPostPriliminaryApi();

                        } else {
                            Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                if (Utils.isNetworkConnectedMainThred(mActivity)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    hitPostPriliminaryApi();

                } else {
                    Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }

            }


        }
    }

    private void hitGetPriliminaryApi() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.get_preliminary_data);

        Log.v("hitGetPriliminary", url);
        Log.e("url1234>>>",url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));
            json_data.put("VIS", jsonObject);
            if (json_data.has("state"))
                stateVal = json_data.getInt("state");
            if(json_data.has("district"))
            distVal = json_data.getInt("district");
            Log.v("hitGetPriliminary", json_data.toString());
            Log.e("json>>>",json_data.toString());

        } catch (JSONException e) {
            Log.v("exception====", e.toString());
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonGet(response);
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
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonGet(JSONObject response) {

        Log.v("res:hitGetPriliminary", response.toString());
        Log.e("response>>>",response.toString());
        //doc_list.clear();
        doc_list_group.clear();
        doc_list_docs.clear();

        doc_list_docs_to_collect.clear();
        doc_temp_list_to_collect.clear();

        doc_list_referred.clear();
        dropdownVal1.clear();
        dropdownVal2.clear();
        try {
            final JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");

            if (msg.equals("1")) {


                sendToNameEmail = jsonObject.getString("boundary_schd_name") + " (" + jsonObject.getString("boundary_schd_email") + ")";
                /*JSONArray array = new JSONArray(jsonObject.getString("download"));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    HashMap<String, String> hash = new HashMap<String, String>();
                    hash.put("doc_name", obj.getString("doc_name"));
                    hash.put("doc_image", obj.getString("doc_image"));
                    doc_list.add(hash);
                    doc_adapter = new DocumentsAdapter(getApplicationContext(), doc_list);
                    lv_document.setAdapter(doc_adapter);
                }*/

                if (jsonObject.has("radio_val")) {

                    if (jsonObject.get("radio_val").equals("1")) {
                        rbDocReferred.setChecked(true);

                        String check1_val = jsonObject.getString("check1");
                        if (check1_val.contains("1")) {
                            cbTitle.setChecked(true);
                        }
                        if (check1_val.contains("2")) {
                            cbSaleDeed.setChecked(true);
                        }
                        if (check1_val.contains("3")) {
                            cbConveyanceDeed.setChecked(true);
                        }
                        if (check1_val.contains("4")) {
                            cbPossessionCertificate.setChecked(true);
                        }
                        if (check1_val.contains("5")) {
                            cbCizra.setChecked(true);
                        }
                        if (check1_val.contains("6")) {
                            cbApproved.setChecked(true);
                        }
                        if (check1_val.contains("7")) {
                            cbAnyOtherDocRef.setChecked(true);
                            etRemarksDocRef.setText(jsonObject.getString("check1_remark"));
                        }


                    } else if (jsonObject.get("radio_val").equals("2")) {
                        rbNoBoundary.setChecked(true);
                        east.setText(R.string.no_boundary_schedule_mail);
                        west.setText(R.string.no_boundary_schedule_mail);
                        north.setText(R.string.no_boundary_schedule_mail);
                        south.setText(R.string.no_boundary_schedule_mail);
                    } else if (jsonObject.get("radio_val").equals("3")) {
                        rbNotApplicable.setChecked(true);

                        String check1_val = jsonObject.getString("check2");
                        if (check1_val.contains("1")) {
                            cbFlat.setChecked(true);
                        }
                        if (check1_val.contains("2")) {
                            cbVery.setChecked(true);
                        }
                        if (check1_val.contains("3")) {
                            cbNpa.setChecked(true);
                        }
                        if (check1_val.contains("4")) {
                            cbAnyOtherNotApplicable.setChecked(true);
                            etRemarksNotApplicable.setText(jsonObject.getString("check2_remark"));
                        }

                    }
                }

                if (jsonObject.getString("no_boundary_schd").equalsIgnoreCase("1")) {
                    proceed.setBackgroundColor(getResources().getColor(R.color.progressgrey));
                    proceed.setClickable(false);
                    tvProceed.setTextColor(getResources().getColor(R.color.black));
                    rbNoBoundary.setChecked(true);
                    tvSendEmail.setVisibility(View.GONE);
                } else if (jsonObject.getString("no_boundary_schd").equalsIgnoreCase("3")) {
                    proceed.setBackgroundColor(getResources().getColor(R.color.app_header));
                    proceed.setClickable(true);
                    tvProceed.setTextColor(getResources().getColor(R.color.white));
                }

                if(jsonObject.has("asset_type")){
                    asset_type=jsonObject.getString("asset_type");
                } if(jsonObject.has("asset_type")){
                    asset_type=jsonObject.getString("asset_type");
                }
                if(jsonObject.has("nature_type")){
                    nature_type=jsonObject.getString("nature_type");
                }
                if(jsonObject.has("category_type")){
                    category_type=jsonObject.getString("category_type");
                }

                if ((jsonObject.getString("land_area").equalsIgnoreCase("not_selected"))
                        || (jsonObject.getString("land_area").equalsIgnoreCase("not applicable")) || (jsonObject.getString("land_area").equalsIgnoreCase(""))) {
                    llTotalLandArea.setVisibility(View.GONE);
                } else {
                    llTotalLandArea.setVisibility(View.VISIBLE);
                }

                if ((jsonObject.getString("covered_area").equalsIgnoreCase("not_selected"))
                        || (jsonObject.getString("covered_area").equalsIgnoreCase("not applicable")) || (jsonObject.getString("covered_area").equalsIgnoreCase(""))) {
                    llTotalCoveredArea.setVisibility(View.GONE);
                } else {
                    llTotalCoveredArea.setVisibility(View.VISIBLE);
                }


                city_type = jsonObject.getString("city_type");
                city_val = jsonObject.getString("city_val");
                asPerMapTitle = jsonObject.getString("as_per_map_land");
                asPerMapCovered = jsonObject.getString("as_per_map_covered");
                landareaunit = jsonObject.getString("land_area_unit");
                coveredareaunit = jsonObject.getString("covered_area_unit");
                landareaunitmap = jsonObject.getString("land_area_unit_map");
                coveredareaunitmap = jsonObject.getString("covered_area_unit_map");
                landareaunit1 = jsonObject.getString("land_area_unit");
                coveredareaunit1 = jsonObject.getString("covered_area_unit");
                landareaunitmap1 = jsonObject.getString("land_area_unit_map");
                coveredareaunitmap1 = jsonObject.getString("covered_area_unit_map");
                populateSpinner();
                /*JSONArray stateArray = jsonObject.getJSONArray("state_list");
                final JSONArray districtArray = jsonObject.getJSONArray("district_list");
                JSONArray cityArray = jsonObject.getJSONArray("cities_list");
                JSONArray villageArray = jsonObject.getJSONArray("village_list");
                JSONArray tehsilArray = jsonObject.getJSONArray("tehsil_list");*/


                setStates(jsonObject.getString("state"));


                setDistrict(jsonObject.getString("district"), jsonObject.getString("state"));


                if (city_type.equalsIgnoreCase("1")) {

                    setCities(jsonObject.getString("district"), jsonObject.getString("city_val"));

                    if (!TextUtils.isDigitsOnly(jsonObject.getString("city_val"))) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                        etCityVillageTehsilOther.setText(jsonObject.getString("city_val"));
                    }


                } else if (city_type.equalsIgnoreCase("2")) {

                    setVillages(jsonObject.getString("district"), jsonObject.getString("city_val"));

                    if (!TextUtils.isDigitsOnly(jsonObject.getString("city_val"))) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                        etCityVillageTehsilOther.setText(jsonObject.getString("city_val"));
                    }


                } else if (city_type.equalsIgnoreCase("3")) {

                    setTehsils(jsonObject.getString("district"), jsonObject.getString("city_val"));

                    if (!TextUtils.isDigitsOnly(jsonObject.getString("city_val"))) {
                        etCityVillageTehsilOther.setVisibility(View.VISIBLE);
                        etCityVillageTehsilOther.setText(jsonObject.getString("city_val"));
                    }


                }

                if (jsonObject.getString("landmark").isEmpty()) {
                    et_landmark.setText("NA");
                } else {
                    et_landmark.setText(jsonObject.getString("landmark"));
                    pref.set(Constants.landmark_address, jsonObject.getString("landmark"));
                    pref.commit();
                }

                // onr_name.setText(jsonObject.getString("owner_name"));
                assetOwner = jsonObject.getString("owner_name");
                onr_name.setText(assetOwner);
                //et
                etPropertyNo.setText(jsonObject.getString("property_no"));
                etColonyArea.setText(jsonObject.getString("colony"));
                etPincode.setText(jsonObject.getString("pincode"));

                if (!pref.get(Constants.owner_name_check).equalsIgnoreCase(jsonObject.getString("owner_name"))) {
                    onr_name.setText(jsonObject.getString("owner_name"));
                    landarea.setText(jsonObject.getString("land_area"));
                    coverarea.setText(jsonObject.getString("covered_area"));
                }
                if (pref.get(Constants.land_area).isEmpty() || pref.get(Constants.land_area).equalsIgnoreCase(null)) {
                    llTotalLandArea.setVisibility(View.GONE);
                }
                if (pref.get(Constants.cover_area).isEmpty() || pref.get(Constants.cover_area).equalsIgnoreCase(null)) {
                    llTotalLandArea.setVisibility(View.GONE);
                }
//..............alpha
                if (city_type.equalsIgnoreCase("1")) {
                    ((RadioButton) radioGroupCity.getChildAt(0)).setChecked(true);
                } else if (city_type.equalsIgnoreCase("2")) {
                    ((RadioButton) radioGroupCity.getChildAt(1)).setChecked(true);
                } else if (city_type.equalsIgnoreCase("3")) {
                    ((RadioButton) radioGroupCity.getChildAt(2)).setChecked(true);
                }

                if (jsonObject.has("document_to_be_collected")) {


                    //llDocsToCollect.setVisibility(View.VISIBLE);

                    JSONArray arr = new JSONArray();

                    if (!jsonObject.getString("document_to_be_collected").equalsIgnoreCase("null")) {

                        //  llDocsToCollect.setVisibility(View.VISIBLE);

                        JSONArray array = jsonObject.getJSONArray("document_to_be_collected");

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject obj = array.getJSONObject(i);

                            HashMap<String, String> hashGroup = new HashMap<String, String>();

                            hashGroup.put("group_name", obj.getString("group_name"));
                            hashGroup.put("group_id", obj.getString("group_id"));

                            doc_list_group_to_collect.add(hashGroup);

                            doc_temp_list_to_collect = new ArrayList();

                            if (obj.has("arr")) {

                                arr = obj.getJSONArray("arr");

                                doc_temp_list_to_collect = new ArrayList();

                                for (int k = 0; k < arr.length(); k++) {

                                    HashMap hashDoc = new HashMap<String, String>();

                                    JSONObject jsonObject1 = arr.getJSONObject(k);

                                    hashDoc.put("doc_id", jsonObject1.getString("doc_id"));
                                    hashDoc.put("doc_name", jsonObject1.getString("doc_name"));

                                    doc_temp_list_to_collect.add(hashDoc);

                                }

                                doc_list_docs_to_collect.add(doc_temp_list_to_collect);
                            } else {
                                doc_list_docs_to_collect.add(doc_temp_list_to_collect);
                            }


                        }

                    } else {
                        //  simpleExpandableListDocumentsToCollect.setVisibility(View.GONE);
                        //  tvDocsToCollect.setVisibility(View.GONE);
                        //   llDocsToCollect.setVisibility(View.GONE);
                    }
                } else {
                    // simpleExpandableListDocumentsToCollect.setVisibility(View.GONE);
                    //  tvDocsToCollect.setVisibility(View.GONE);
                    //    llDocsToCollect.setVisibility(View.GONE);
                }

                if (jsonObject.has("documents")) {

                    JSONArray arr = new JSONArray();

                    if (!jsonObject.getString("documents").equalsIgnoreCase("null")) {

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

                            if (obj.getString("prior_to_survey").equalsIgnoreCase("1")) {

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

                                for (int k = 0; k < arr.length(); k++) {

                                    HashMap hashDoc = new HashMap<String, String>();

                                    JSONObject jsonObject1 = arr.getJSONObject(k);

                                    hashDoc.put("doc_id", jsonObject1.getString("doc_id"));
                                    hashDoc.put("doc_name", jsonObject1.getString("doc_name"));
                                    if (jsonObject1.has("document_url")) {
                                        hashDoc.put("document_url", String.valueOf(jsonObject1.getJSONArray("document_url")));
                                    }

                                    doc_temp_list.add(hashDoc);

                                    if (obj.getString("prior_to_survey").equalsIgnoreCase("1")) {
                                    } else {
                                    }

                                }
                                doc_list_docs.add(doc_temp_list);

                            } else {


                                doc_list_group_referred.add(hashGroup);

                                arr = obj.getJSONArray("arr");

                                doc_temp_list_referred = new ArrayList();

                                for (int k = 0; k < arr.length(); k++) {

                                    HashMap hashDoc = new HashMap<String, String>();

                                    JSONObject jsonObject1 = arr.getJSONObject(k);

                                    hashDoc.put("doc_id", jsonObject1.getString("doc_id"));
                                    hashDoc.put("doc_name", jsonObject1.getString("doc_name"));
                                    if (jsonObject1.has("document_url")) {
                                        hashDoc.put("document_url", String.valueOf(jsonObject1.getJSONArray("document_url")));
                                    }
                                    hashDoc.put("count", "0");
                                    hashDoc.put("documentImages", "");

                                    doc_temp_list_referred.add(hashDoc);

                                    if (obj.getString("prior_to_survey").equalsIgnoreCase("1")) {
                                    } else {
                                    }

                                }
                                doc_list_docs_referred.add(doc_temp_list_referred);
                            }

                        }

                    }
                }

                adapterDocuments = new AdapterDocuments(mActivity, doc_list_group, doc_list_docs);
                simpleExpandableListDocuments.setAdapter(adapterDocuments);

                adapterReferredDocuments = new AdapterReferredDocuments(mActivity, doc_list_group_referred, doc_list_docs_referred);
                simpleExpandableListReferredDoc.setAdapter(adapterReferredDocuments);

                adapterDocumentsToCollect = new AdapterDocumentsToCollect(mActivity, doc_list_group_to_collect, doc_list_docs_to_collect);
                simpleExpandableListDocumentsToCollect.setAdapter(adapterDocumentsToCollect);


                int item_size = (int) getResources().getDimension(R.dimen._30sdp);  // 50px
                final int sub_item_size = (int) getResources().getDimension(R.dimen._30sdp);
                ;  // 40px

                simpleExpandableListDocuments.getLayoutParams().height = item_size * adapterDocuments.getGroupCount();
                simpleExpandableListDocuments.setAdapter(adapterDocuments);
// ListView Group Expand Listener
                simpleExpandableListDocuments.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {
                        int nb_children = adapterDocuments.getChildrenCount(groupPosition);
                        simpleExpandableListDocuments.getLayoutParams().height += sub_item_size * nb_children;
                    }
                });

// Listview Group collapsed listener
                simpleExpandableListDocuments.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                    @Override
                    public void onGroupCollapse(int groupPosition) {
                        int nb_children = adapterDocuments.getChildrenCount(groupPosition);
                        simpleExpandableListDocuments.getLayoutParams().height -= sub_item_size * nb_children;
                    }
                });


                //Referred Doc Expandable list view
                simpleExpandableListReferredDoc.getLayoutParams().height = item_size * adapterReferredDocuments.getGroupCount();
                simpleExpandableListReferredDoc.setAdapter(adapterReferredDocuments);
// ListView Group Expand Listener
                simpleExpandableListReferredDoc.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {
                        int nb_children = adapterReferredDocuments.getChildrenCount(groupPosition);
                        simpleExpandableListReferredDoc.getLayoutParams().height += sub_item_size * nb_children;
                    }
                });

// Listview Group collapsed listener
                simpleExpandableListReferredDoc.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                    @Override
                    public void onGroupCollapse(int groupPosition) {
                        int nb_children = adapterReferredDocuments.getChildrenCount(groupPosition);
                        simpleExpandableListReferredDoc.getLayoutParams().height -= sub_item_size * nb_children;
                    }
                });


                //Doc To Collect Expandable list view
                simpleExpandableListDocumentsToCollect.getLayoutParams().height = item_size * adapterDocumentsToCollect.getGroupCount();
                simpleExpandableListDocumentsToCollect.setAdapter(adapterDocumentsToCollect);
// ListView Group Expand Listener
                simpleExpandableListDocumentsToCollect.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {
                        int nb_children = adapterDocumentsToCollect.getChildrenCount(groupPosition);
                        simpleExpandableListDocumentsToCollect.getLayoutParams().height += sub_item_size * nb_children;
                    }
                });

// Listview Group collapsed listener
                simpleExpandableListDocumentsToCollect.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                    @Override
                    public void onGroupCollapse(int groupPosition) {
                        int nb_children = adapterDocumentsToCollect.getChildrenCount(groupPosition);
                        simpleExpandableListDocumentsToCollect.getLayoutParams().height -= sub_item_size * nb_children;
                    }
                });


                try {
                    for (int j = 0; j < jsonObject.getJSONArray("dropdown_val1").length(); j++) {

                        HashMap hashMap = new HashMap();

                        hashMap.put("symbol", jsonObject.getJSONArray("dropdown_val1").getString(j));

                        dropdownVal1.add(hashMap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    for (int k = 0; k < jsonObject.getJSONArray("dropdown_val2").length(); k++) {

                        HashMap hashMap = new HashMap();

                        hashMap.put("symbol", jsonObject.getJSONArray("dropdown_val2").getString(k));

                        dropdownVal2.add(hashMap);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                land_adapter = new LandAdapter(mActivity, dropdownVal1);
                spTotalLandArea1.setAdapter(land_adapter);
                spTotalLandArea2.setAdapter(land_adapter);
                land_adapter = new LandAdapter(mActivity, dropdownVal2);
                spTotalCoveredArea1.setAdapter(land_adapter);
                spTotalCoveredArea2.setAdapter(land_adapter);


                // adapterDocuments = new AdapterDocuments(doc_list);
                //recyclerViewDocuments.setAdapter(adapterDocuments);

                //  adapterReferredDocuments = new AdapterReferredDocuments(doc_list_referred);
                //recyclerViewReferredDoc.setAdapter(adapterReferredDocuments);

                dialog_status = "1";
                InfoDialog();
                Log.v("owner_name=======", jsonObject.getString("owner_name"));

                if (!jsonObject.getString("north_east").equalsIgnoreCase("")) {
                    spinner_east.setSelection(1);
                    east.setText(jsonObject.getString("north_east"));
                    west.setText(jsonObject.getString("north_west"));
                    north.setText(jsonObject.getString("south_east"));
                    south.setText(jsonObject.getString("south_west"));
                } else if (!jsonObject.getString("east").equalsIgnoreCase("")) {
                    spinner_east.setSelection(0);
                    east.setText(jsonObject.getString("east"));
                    west.setText(jsonObject.getString("west"));
                    north.setText(jsonObject.getString("north"));
                    south.setText(jsonObject.getString("south"));
                }




               /* et_northeast.setText(jsonObject.getString("north_east"));
                et_northwest.setText(jsonObject.getString("north_west"));
                et_southeast.setText(jsonObject.getString("south_east"));
                et_southwest.setText(jsonObject.getString("south_west"));*/
//
                etAsPerMapCovered.setText(jsonObject.getString("as_per_map_covered"));
                etAsPerMap.setText(jsonObject.getString("as_per_map_land"));

                // as_per_doc.setText(jsonObject.getString("document_title"));

                pref.set(Constants.landmark_lat, jsonObject.getString("landmark_lat"));
                pref.set(Constants.landmark_long, jsonObject.getString("landmark_long"));
                pref.commit();

                if (!pref.get(Constants.landmark_lat).isEmpty() &&
                        !pref.get(Constants.landmark_long).isEmpty()) {
                    Lat = Double.valueOf(pref.get(Constants.landmark_lat));
                    Lng = Double.valueOf(pref.get(Constants.landmark_long));
                }

                try {
                    setLandMark();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (jsonObject.getString("owner_name").equals("")) {

                    //  onr_name.setText(pref.get(Constants.owner_name));
                } else {
                    //  onr_name.setText(jsonObject.getString("owner_name"));
                    owner_name2 = jsonObject.getString("owner_name");


                }
                if (jsonObject.getString("land_area").equals("")) {

                    //  onr_name.setText(pref.get(Constants.owner_name));
                } else {
                    //  onr_name.setText(jsonObject.getString("owner_name"));
                    titleLand_area = jsonObject.getString("land_area");


                }
                if (jsonObject.getString("covered_area").equals("")) {

                    //  onr_name.setText(pref.get(Constants.owner_name));
                } else {
                    //  onr_name.setText(jsonObject.getString("owner_name"));
                    titleCovered_area = jsonObject.getString("covered_area");


                }
                if (jsonObject.getString("as_per_map_land").equals("")) {

                    //  onr_name.setText(pref.get(Constants.owner_name));
                } else {
                    //  onr_name.setText(jsonObject.getString("owner_name"));
                    asPerMapTitle = jsonObject.getString("as_per_map_land");


                }
                if (jsonObject.getString("as_per_map_covered").equals("")) {

                    //  onr_name.setText(pref.get(Constants.owner_name));
                } else {
                    //  onr_name.setText(jsonObject.getString("owner_name"));
                    asPerMapCovered = jsonObject.getString("as_per_map_covered");


                }
                if (jsonObject.getString("contact_name").equals("")) {
                    onr_number.setText(pref.get(Constants.owner_number));
                } else {
                    onr_number.setText(jsonObject.getString("contact_name"));
                }

                if (jsonObject.getString("address").equals("")) {
                    //  onr_address.setText(pref.get(Constants.owner_address));
                } else {
                    // onr_address.setText(jsonObject.getString("address"));
                    address2 = jsonObject.getString("address");
                }

                if (jsonObject.getString("colony").equals("")) {
                    //  onr_address.setText(pref.get(Constants.owner_address));
                } else {
                    // onr_address.setText(jsonObject.getString("address"));
                    colonyArea = jsonObject.getString("colony");
                }

                /*if (jsonObject.getString("total_landarea").equals("")) {
                    landarea.setText(pref.get(Constants.land_area));
                } else {
                    landarea.setText(jsonObject.getString("total_landarea"));
                }*/
                if (jsonObject.getString("land_area").equals("")) {

                    //  onr_name.setText(pref.get(Constants.owner_name));
                } else {
                    //  onr_name.setText(jsonObject.getString("owner_name"));
                    titleLand_area = jsonObject.getString("land_area");


                }


                /*if (jsonObject.getString("total_coverarea").equals("")) {
                    coverarea.setText(pref.get(Constants.cover_area));
                } else {
                    coverarea.setText(jsonObject.getString("total_coverarea"));
                }
*/
                if (jsonObject.getString("covered_area").equals("")) {

                    //  onr_name.setText(pref.get(Constants.owner_name));
                } else {
                    //  onr_name.setText(jsonObject.getString("owner_name"));
                    titleCovered_area = jsonObject.getString("covered_area");


                }
//                coverarea.setText(jsonObject.getString("covered_area"));

                pref.set(Constants.land_area_as_deed, landarea.getText().toString());
                pref.set(Constants.land_area_as_map, etAsPerMap.getText().toString());

                pref.set(Constants.land_area_as_deed_covered, coverarea.getText().toString());
                pref.set(Constants.land_area_as_map_covered, etAsPerMapCovered.getText().toString());
                pref.commit();

                if (jsonObject.getString("contact_name").equals("")) {
                    name.setText(pref.get(Constants.coordinating_Pname));
                } else {
                    name.setText(jsonObject.getString("contact_name"));
                }

                if (jsonObject.getString("contact_number").equals("")) {
                    number.setText(pref.get(Constants.coordinating_Pnumber));
                } else {
                    number.setText(jsonObject.getString("contact_number"));
                }

                if (jsonObject.getString("relationship").equals("")) {
                } else {
                    relationship.setText(jsonObject.getString("relationship"));
                }


                for (int i = 0; i < property_array_list.size(); i++) {
                    if (property_array_list.get(i).get("id").equalsIgnoreCase(jsonObject.getString("asset_type"))) {
                        assetPos = i;
                        break;
                    }
                }

                property_spinner.setSelection(assetPos);


              /*  if (jsonObject.getString("document_name").equals("Title Deed")) {
                    pref.set(Constants.uploads, "1");
                    pref.commit();
                    if (!pref.get(Constants.uploads).equals("")) {
                        document_spinner.setSelection(Integer.parseInt(pref.get(Constants.uploads)));

                    } else {
                        Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    }
                } else if (jsonObject.getString("document_name").equals("Cizra Map")) {
                    pref.set(Constants.uploads, "2");
                    pref.commit();
                    if (!pref.get(Constants.uploads).equals("")) {
                        document_spinner.setSelection(Integer.parseInt(pref.get(Constants.uploads)));
                    } else {
                        Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    }
                } else if (jsonObject.getString("document_name").equals("Approved Building Map")) {
                    pref.set(Constants.uploads, "3");
                    pref.commit();
                    if (!pref.get(Constants.uploads).equals("")) {
                        document_spinner.setSelection(Integer.parseInt(pref.get(Constants.uploads)));
                    } else {
                        Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    }
                } else if (jsonObject.getString("document_name").equals("Any other document")) {
                    pref.set(Constants.uploads, "4");
                    pref.commit();
                    if (!pref.get(Constants.uploads).equals("")) {
                        document_spinner.setSelection(Integer.parseInt(pref.get(Constants.uploads)));
                    } else {
                        Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    }
                } else if (jsonObject.getString("document_name").equals("Boundries are not mentioned on the provided document")) {
                    pref.set(Constants.uploads, "5");
                    pref.commit();
                    if (!pref.get(Constants.uploads).equals("")) {
                        document_spinner.setSelection(Integer.parseInt(pref.get(Constants.uploads)));
                    } else {
                        Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    document_spinner.setSelection(Integer.parseInt("0"));

                }*/

                pdf1 = jsonObject.getString("document1");
                if (!(jsonObject.getString("document1").equals(""))) {
                    //   tv_schedule_pdf.setVisibility(View.VISIBLE);
                    et_browse.setText(jsonObject.getString("document1"));
                }

            } else {
                priliminary_status = "0";
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }
//v xdv

            if (jsonObject.getString("land_area_unit").equalsIgnoreCase("2")) {
                pref.set(Constants.land_area_unit, "1");
                pref.commit();
                if (!pref.get(Constants.land_area_unit).equals("")) {
                    sp_land.setSelection(Integer.parseInt(pref.get(Constants.land_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("land_area_unit").equalsIgnoreCase("3")) {
                pref.set(Constants.land_area_unit, "2");
                pref.commit();
                if (!pref.get(Constants.land_area_unit).equals("")) {
                    sp_land.setSelection(Integer.parseInt(pref.get(Constants.land_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("land_area_unit").equalsIgnoreCase("4")) {
                pref.set(Constants.land_area_unit, "3");
                pref.commit();
                if (!pref.get(Constants.land_area_unit).equals("")) {
                    sp_land.setSelection(Integer.parseInt(pref.get(Constants.land_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("land_area_unit").equalsIgnoreCase("5")) {
                pref.set(Constants.land_area_unit, "4");
                pref.commit();
                if (!pref.get(Constants.land_area_unit).equals("")) {
                    sp_land.setSelection(Integer.parseInt(pref.get(Constants.land_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("land_area_unit").equalsIgnoreCase("6")) {
                pref.set(Constants.land_area_unit, "5");
                pref.commit();
                if (!pref.get(Constants.land_area_unit).equals("")) {
                    sp_land.setSelection(Integer.parseInt(pref.get(Constants.land_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("land_area_unit").equalsIgnoreCase("7")) {
                pref.set(Constants.land_area_unit, "6");
                pref.commit();
                if (!pref.get(Constants.land_area_unit).equals("")) {
                    sp_land.setSelection(Integer.parseInt(pref.get(Constants.land_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("land_area_unit").equalsIgnoreCase("10")) {
                pref.set(Constants.land_area_unit, "7");
                pref.commit();
                if (!pref.get(Constants.land_area_unit).equals("")) {
                    sp_land.setSelection(Integer.parseInt(pref.get(Constants.land_area_unit)));
                } else {

                }
            } else {
                pref.set(Constants.land_area_unit, "1");
                pref.commit();
                if (!pref.get(Constants.land_area_unit).equals("")) {
                    sp_land.setSelection(Integer.parseInt(pref.get(Constants.land_area_unit)));
                } else {

                }
            }

            if (jsonObject.getString("covered_area_unit").equalsIgnoreCase("2")) {
                pref.set(Constants.covered_area_unit, "1");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equalsIgnoreCase("3")) {
                pref.set(Constants.covered_area_unit, "2");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equalsIgnoreCase("4")) {
                pref.set(Constants.covered_area_unit, "3");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equalsIgnoreCase("5")) {
                pref.set(Constants.covered_area_unit, "4");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equalsIgnoreCase("6")) {
                pref.set(Constants.covered_area_unit, "5");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equalsIgnoreCase("7")) {
                pref.set(Constants.covered_area_unit, "6");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equalsIgnoreCase("10")) {
                pref.set(Constants.covered_area_unit, "7");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else {
                pref.set(Constants.covered_area_unit, "1");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            }

           /* if (jsonObject.getString("covered_area_unit").equals("Square Inch")) {
                pref.set(Constants.covered_area_unit, "1");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equals("Square Foot")) {
                pref.set(Constants.covered_area_unit, "2");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }

            } else if (jsonObject.getString("covered_area_unit").equals("Square Yard")) {
                pref.set(Constants.covered_area_unit, "3");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equals("Square Mile")) {
                pref.set(Constants.covered_area_unit, "4");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equals("Acre")) {
                pref.set(Constants.covered_area_unit, "5");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equals("Hectare")) {
                pref.set(Constants.covered_area_unit, "6");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equals("Square Millimeter")) {
                pref.set(Constants.covered_area_unit, "7");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equals("Square Centimeter")) {
                pref.set(Constants.covered_area_unit, "8");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equals("Square Meter")) {
                pref.set(Constants.covered_area_unit, "9");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else if (jsonObject.getString("covered_area_unit").equals("Square Kilometer")) {
                pref.set(Constants.covered_area_unit, "10");
                pref.commit();
                if (!pref.get(Constants.covered_area_unit).equals("")) {
                    sp_cover.setSelection(Integer.parseInt(pref.get(Constants.covered_area_unit)));
                } else {

                }
            } else {

            }*/
            loader.cancel();
            //    firstTime = false;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    private void hitPostPriliminaryApi() {
        String url = Utils.getCompleteApiUrl(mActivity, R.string.PostPreliminary);
        Log.v("hitPostPriliminaryApi", url);
        Log.e("url>>>",url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {

            jsonObject.put("case_id", case_id);
            jsonObject.put("ownername", onr_name.getText().toString());
            jsonObject.put("landmark_address", et_landmark.getText().toString());
            jsonObject.put("property_no", etPropertyNo.getText().toString());
            jsonObject.put("colony_area", etColonyArea.getText().toString());
            try {
                jsonObject.put("state", stateId);
            } catch (Exception e) {
                jsonObject.put("state", "state");
            }
            try {
                jsonObject.put("district", districtId);
            } catch (Exception e) {
                jsonObject.put("district", "district");
            }
            jsonObject.put("city_village_tehsil_select", city_type);
            try {
                // jsonObject.put("city_village_tehsil", city_val);

                if (etCityVillageTehsilOther.getVisibility() == View.VISIBLE) {
                    jsonObject.put("city_village_tehsil", etCityVillageTehsilOther.getText().toString());
                } else {
                    jsonObject.put("city_village_tehsil", city_val);
                }

            } catch (Exception e) {
                jsonObject.put("city_village_tehsil", "city_village_tehsil");
            }
            jsonObject.put("pincode", etPincode.getText().toString());
            jsonObject.put("property_type", asset_type);
            jsonObject.put("land_area", landarea.getText().toString());
            jsonObject.put("land_area_unit", landareaunit);
            jsonObject.put("as_per_map_land", etAsPerMap.getText().toString());
            jsonObject.put("as_per_map_covered", etAsPerMapCovered.getText().toString());
            jsonObject.put("covered_area", coverarea.getText().toString());
            jsonObject.put("covered_area_unit", coveredareaunit);
            jsonObject.put("land_area_unit_map", landareaunitmap);
            jsonObject.put("covered_area_unit_map", coveredareaunitmap);

            jsonObject.put("east", east.getText().toString());
            jsonObject.put("west", west.getText().toString());
            jsonObject.put("north", north.getText().toString());
            jsonObject.put("south", south.getText().toString());

            if (spinner_east.getSelectedItemPosition() == 1) {
                jsonObject.put("north_east", east.getText().toString());
                jsonObject.put("north_west", west.getText().toString());
                jsonObject.put("south_east", north.getText().toString());
                jsonObject.put("south_west", south.getText().toString());
            } else {
                jsonObject.put("north_east", "");
                jsonObject.put("north_west", "");
                jsonObject.put("south_east", "");
                jsonObject.put("south_west", "");
            }

            jsonObject.put("co_ordinate_contact_no", number.getText().toString());
            jsonObject.put("relationship", relationship.getText().toString());
            jsonObject.put("contactname", name.getText().toString());

            jsonObject.put("radio_val", radio_val);
            if (rbDocReferred.isChecked())
            {
                String str = check1.replaceAll("^(,|\\s)*|(,|\\s)*$", "").replaceAll("(\\,\\s*)+", ",");
                jsonObject.put("check1", str);
                if (cbAnyOtherDocRef.isChecked()) {
                    jsonObject.put("check1_remark", etRemarksDocRef.getText().toString());
                } else {
                    jsonObject.put("check1_remark", "");
                }
                pref.set(Constants.documentYes, str);
                pref.commit();
            }
            else {
                jsonObject.put("check1", "");
                jsonObject.put("check1_remark", "");
            }

            if (rbNotApplicable.isChecked()) {
                String str = check2.replaceAll("^(,|\\s)*|(,|\\s)*$", "").replaceAll("(\\,\\s*)+", ",");
                jsonObject.put("check2", str);
                if (cbAnyOtherNotApplicable.isChecked()) {
                    jsonObject.put("check2_remark", etRemarksNotApplicable.getText().toString());
                } else {
                    jsonObject.put("check2_remark", "");
                }
                pref.set(Constants.documentNA, str);
                pref.commit();
            } else {
                jsonObject.put("check2", "");
                jsonObject.put("check2_remark", "");
            }


            // jsonObject.put("as_per_map", map.getText().toString());
//            jsonObject.put("as_per_map_land", etAsPerMap.getText().toString());
//            jsonObject.put("as_per_map_covered", etAsPerMapCovered.getText().toString());

            // jsonObject.put("city_village_tehsil_select", "1");



          //  jsonObject.put("asset_type", asset_type);
            pref.set(Constants.asset_type,asset_type);
            pref.commit();
            jsonObject.put("nature_type", nature_type);
            jsonObject.put("category_type", category_type);
           /* pref.set(Constants.type_of_assets,selected_property);
            pref.commit();*/

            jsonObject.put("document_title", "abbbc");
           /* if (spinner_east.getSelectedItemPosition() == 0){
                jsonObject.put("east", east.getText().toString());
                jsonObject.put("west", west.getText().toString());
                jsonObject.put("north", north.getText().toString());
                jsonObject.put("south", south.getText().toString());
            }else {
                jsonObject.put("east", "");
                jsonObject.put("west", "");
                jsonObject.put("north", "");
                jsonObject.put("south", "");
            }*/





            //  jsonObject.put("document_name", "addad");
            //  jsonObject.put("image1", "");



            pref.set(Constants.documentYes, "");
            pref.set(Constants.documentNA, "");

            pref.set(Constants.directionOne, east.getText().toString());
            pref.set(Constants.directionTwo, west.getText().toString());
            pref.set(Constants.directionThree, north.getText().toString());
            pref.set(Constants.directionFour, south.getText().toString());
            pref.set(Constants.land_area_as_map, etAsPerMap.getText().toString());
            pref.set(Constants.land_area_as_map_covered, etAsPerMapCovered.getText().toString());
            pref.commit();



            json_data.put("VIS", jsonObject);

            Log.v("hitPostPriliminaryApi", json_data.toString());
            Log.e("hitPostPriliminaryApi", json_data.toString());

        } catch (Exception e) {

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
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJson(JSONObject response) {

        Log.v("resp:hitPostPriliminary", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");//response_message
            String msg = jsonObject.getString("response_code");


            if (msg.equals("1")) {
                pref.set(Constants.owner_name, onr_name.getText().toString());
                pref.set(Constants.owner_address, onr_address.getText().toString());
                //   pref.set(Constants.selected_property, jsonObject.getString("Type_of_property"));


                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                //  intent = new Intent(PriliminaryActivity.this, ScheduleActivity.class);

                if (CaseDetail.schedule_status.equalsIgnoreCase("not_schedule_yet")) {
                    Log.e("schedule>>>","not schdule yet");
                    intent = new Intent(mActivity, ScheduleActivity.class);
                    startActivity(intent);
                } else {
                    Log.e("schedule>>>","not schdule");
                    ((Dashboard) mActivity).displayView(3);
                    // intent = new Intent(mActivity, MapLocatorActivity.class);
                }


            } else {
                //Toast.makeText(PriliminaryActivity.this, "Error in submitting report", Toast.LENGTH_SHORT).show();
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

    //********************************** Preliminary api get *********************************//
    private void Hit_PostDocument_Api() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.upload_doc);
        Log.v("Hit_UploadDocument_Api", url);

        JSONObject json_data = new JSONObject();

        try {
            json_data.put("case_id", case_id);

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
                        parseJsonDocument(response);
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
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    //=================================== Gallery ====================================//

    private void parseJsonDocument(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");

            if (msg.equals("1")) {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
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

    public void view(View v) {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "maven.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mActivity, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    //********************************** Preliminary api get *********************************//

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showCameraGalleryDialog() {

        captureType = 1;

        final Dialog dialog = new Dialog(mActivity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.alert_camera_gallery_file_popup);

        dialog.show();

        RelativeLayout rrCancel = (RelativeLayout) dialog.findViewById(R.id.rr_cancel);
        LinearLayout llCamera = (LinearLayout) dialog.findViewById(R.id.ll_camera);
        LinearLayout llGallery = (LinearLayout) dialog.findViewById(R.id.ll_gallery);
        LinearLayout llFile = (LinearLayout) dialog.findViewById(R.id.llFile);

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
              /*  Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                dialog.dismiss();*/

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                dialog.dismiss();
            }
        });

        llFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

//                String[] mimeTypes =
//                        {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
//                                "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
//                                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
//                                "text/plain",
//                                "application/pdf",
//                                "application/zip"};
//
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
//                    if (mimeTypes.length > 0) {
//                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//                    }
//                } else {
//                    String mimeTypesStr = "";
//                    for (String mimeType : mimeTypes) {
//                        mimeTypesStr += mimeType + "|";
//                    }
//                    intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
//                }
//
//                // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                // intent.setType("*/*");
//                // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                //   intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//                try {
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select a File to Upload"),
//                            FILE_SELECT_CODE);
//                } catch (ActivityNotFoundException ex) {
//                    // Potentially direct the user to the Market with a Dialog
//                    Toast.makeText(mActivity, "Please install a File Manager.",
//                            Toast.LENGTH_SHORT).show();
//                }


                //FilePicker
                Intent intent = new Intent(mActivity, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setSelectedMediaFiles(mediaFiles)
                        .setShowFiles(true)
                        .setShowImages(false)
                        .setSingleClickSelection(true)
                        .setSkipZeroSizeFiles(true)
                        .setShowVideos(false)
                        .setMaxSelection(1)
                        .setSuffixes("txt", "pdf", "html", "rtf", "csv", "xml",
                                "doc", "docx", "odt", "ott",
                                "ppt", "pptx", "pps",
                                "xls", "xlsx", "ods", "ots")
                        .build());
                startActivityForResult(intent, FILE_SELECT_CODE);
            }
        });

        rrCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //********************************** Preliminary api post*********************************//

    public void showCameraGalleryDialogAttachment() {

        captureType = 2;

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
        } else {

            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
        // start the image capture Intent
    }

    public Uri getOutputMediaFileUri(int type) {
        //return Uri.fromFile(getOutputMediaFile(type));

        return FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".provider", getOutputMediaFile(type));

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imagesEncodedList.clear();

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == mActivity.RESULT_OK) {
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

                imagesEncodedList.add(getEncoded64ImageStringFromBitmap(rotatedBitmap));

                encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                if (captureType == 1) {
                    if (Utils.isNetworkConnectedMainThred(mActivity)) {
                        hitPostDocumentsApi();
                    } else {
                        Toast.makeText(mActivity, "No Internet Connection. Unable to upload images.", Toast.LENGTH_SHORT).show();
                    }
                } else if (captureType == 2) {
                    ivAttachment.setVisibility(View.VISIBLE);
                    ivAttachment.setImageBitmap(rotatedBitmap);
                }

            }
        } else if (requestCode == SELECT_PICTURE) {
            if (data != null) {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                imagesEncodedList = new ArrayList<String>();

                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = mActivity.getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    encodedString1 = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);

                    //  encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), mArrayUri.get(0));

                        if (captureType == 1) {
                            imagesEncodedList.add(getEncoded64ImageStringFromBitmap(bitmap));
                        } else if (captureType == 2) {
                            ivAttachment.setVisibility(View.VISIBLE);
                            ivAttachment.setImageBitmap(bitmap);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //   encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                } else {
                    if (captureType == 1) {
                        if (data.getClipData() != null) {

                            ClipData mClipData = data.getClipData();
                            ;

                            ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                            for (int i = 0; i < mClipData.getItemCount(); i++) {

                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();
                                mArrayUri.add(uri);
                                // Get the cursor
                                Cursor cursor = mActivity.getContentResolver().query(uri, filePathColumn, null, null, null);
                                // Move to first row
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                encodedString1 = cursor.getString(columnIndex);
                                // imagesEncodedList.add(encodedString1);
                                cursor.close();

                            }


                            for (int i = 0; i < mArrayUri.size(); i++) {
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), mArrayUri.get(i));

                                    imagesEncodedList.add(getEncoded64ImageStringFromBitmap(bitmap));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }

                }

                if (Utils.isNetworkConnectedMainThred(mActivity)) {

                    hitPostDocumentsApi();

                    for (int k = 0; k < doc_list_docs_referred.get(0).size(); k++) {

                        HashMap hashDoc = new HashMap<String, String>();

                        hashDoc.put("doc_id", doc_list_docs_referred.get(0).get(k).get("doc_id"));
                        hashDoc.put("doc_name", doc_list_docs_referred.get(0).get(k).get("doc_name"));
                        if (doc_list_docs_referred.get(0).get(k).containsKey("document_url")) {
                            hashDoc.put("document_url", doc_list_docs_referred.get(0).get(k).get("document_url"));
                        }
                        hashDoc.put("count", doc_list_docs_referred.get(0).get(k).get("count"));
                        hashDoc.put("documentImages", doc_list_docs_referred.get(0).get(k).get("documentImages"));

                        if (documentId.equalsIgnoreCase(doc_list_docs_referred.get(0).get(k).get("doc_id"))) {
                            hashDoc.put("count", String.valueOf(imagesEncodedList.size()));
                        } else {
                            hashDoc.put("count", doc_list_docs_referred.get(0).get(k).get("count"));
                        }

                        //doc_temp_list_referred.add(hashDoc);
                        doc_temp_list_referred.set(k, hashDoc);

                    }
                    doc_list_docs_referred.add(doc_temp_list_referred);

                   /* for (int i = 0; i < doc_list_referred.size();i++){

                        HashMap hashMap = new HashMap();

                        hashMap.put("doc_id", doc_list_referred.get(i).get("doc_id"));
                        hashMap.put("doc_name", doc_list_referred.get(i).get("doc_name"));
                        hashMap.put("mandatory", doc_list_referred.get(i).get("mandatory"));
                        hashMap.put("prior_to_survey", doc_list_referred.get(i).get("prior_to_survey"));
                        hashMap.put("select_id", doc_list_referred.get(i).get("select_id"));
                        hashMap.put("document_caption", doc_list_referred.get(i).get("document_caption"));
                        hashMap.put("document_url", doc_list_referred.get(i).get("document_url"));
                     //   hashMap.put("documentImages", doc_list_referred.get(i).get("documentImages"));
                      //  hashMap.put("documentImages","");

                        if (documentId.equalsIgnoreCase(doc_list_referred.get(i).get("doc_id"))){
                            hashMap.put("count",String.valueOf(imagesEncodedList.size()));
                        }else {
                            hashMap.put("count",doc_list_referred.get(i).get("count"));
                        }

                        doc_list_referred.set(i,hashMap);
                    }*/

                    if (adapterReferredDocuments != null) {
                        adapterReferredDocuments.notifyDataSetChanged();
                    }


                } else {
                    Toast.makeText(mActivity, "No Internet Connection. Unable to upload images.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(mActivity, "Unable to select image", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == mActivity.RESULT_OK) {
                // Get the Uri of the selected file
                if (data != null) {

                    mediaFiles.clear();
                    mediaFiles.addAll(data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES));

                    sendDocumentFile(mediaFiles);

                }
            }
        }
    }

    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;

        return lastVal;
    }


/*
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        if (DashboardFragment.scheduleCheck.equalsIgnoreCase("1")){
            popup.getMenu().getItem(2).setVisible(false);
        }else {
            popup.getMenu().getItem(2).setVisible(true);
        }

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
                        intent = new Intent(mActivity, Dashboard.class);
                        startActivity(intent);
                        return true;

                    case R.id.survey_not_required:
                        intent = new Intent(mActivity, SurveyNotRequiredActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.reassign:
                        intent = new Intent(mActivity, ReassignActivity.class);
                        startActivity(intent);

                        return true;

                    case R.id.reschedule:

                        intent = new Intent(mActivity, ScheduleActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.calling:
                        CallDialog();
                        return true;

                   */
/* case R.id.doc:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://valuerservice.com/uploads/0692351837.jpg"));
                        startActivity(intent);
                        return true;*//*


                    default:
                        return false;
                }
            }
        });
    }
*/

    private void hitPostDocumentsApi() {

        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

        String url = Utils.getCompleteApiUrl(mActivity, R.string.PostDocuments);
        Log.v("hitPostDocumentsApi", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {

            location = new GetLocation(mActivity);

            JSONArray documentArray = new JSONArray();

            for (int i = 0; i < imagesEncodedList.size(); i++) {
                try {
                    documentArray.put(i, imagesEncodedList.get(i).trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            json_data.put("case_id", case_id);
            json_data.put("doc_id", documentId);
            json_data.put("group_id", groupId);
            json_data.put("document_url", documentArray);
            json_data.put("document_file_name", documentFileName);
            //json_data.put("document_caption", "");
            json_data.put("device_id", Utils.getDeviceID(mActivity));
            json_data.put("lat", location.getLatitude());
            json_data.put("long", location.getLongitude());

            jsonObject.put("VIS", json_data);

            Log.v("hitPostDocumentsApi", jsonObject.toString());

        } catch (Exception e) {

            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonDocumentPost(response);
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
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonDocumentPost(JSONObject response) {

        Log.v("res:hitPostDocumentsApi", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");

            if (msg.equals("1")) {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

               /* for (int i = 0; i < doc_list_referred.size(); i++) {
                    HashMap<String, String> hash = new HashMap<String, String>();
                    hash.put("doc_id", doc_list_referred.get(i).get("doc_id"));
                    hash.put("doc_name", doc_list_referred.get(i).get("doc_name"));
                    hash.put("mandatory", doc_list_referred.get(i).get("mandatory"));
                    hash.put("prior_to_survey", doc_list_referred.get(i).get("prior_to_survey"));
                    hash.put("select_id", doc_list_referred.get(i).get("select_id"));
                    hash.put("document_caption", doc_list_referred.get(i).get("document_caption"));
                    hash.put("count",doc_list_referred.get(i).get("count"));
                    hash.put("document_url", doc_list_referred.get(i).get("document_url"));
                    hash.put("documentImages","");
                   *//* if (documentId.equalsIgnoreCase(doc_list_referred.get(i).get("doc_id"))){
                       // hash.put("documentImages",imagesEncodedList.toString().trim());
                        hash.put("documentImages","");
                    }else {
                        hash.put("documentImages",doc_list_referred.get(i).get("documentImages"));
                    }*//*

                    doc_list_referred.add(hash);

                }*/

                 /*   doc_adapter = new DocumentsAdapter(getApplicationContext(), doc_list);
                    lv_document.setAdapter(doc_adapter);*/

            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        loader.cancel();
    }

    //Call popup
    public void CallDialog() {
        final Dialog dialog = new Dialog(mActivity);
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
        final TextView tvName1, tvName2, tvName3, tvName4, tvMobile1, tvMobile2, tvMobile3, tvMobile4;

        ImageView ivEmail1, ivEmail2, ivEmail3, ivEmail4, iv_call1, iv_call2, iv_call3, iv_call4;
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

        Intent intent = mActivity.getIntent();

        try {
            if (pref.get(Constants.support_manager_name).equals("")
                    || pref.get(Constants.support_manager_name).equals("null")) {
                tvName1.setText("NA");
            } else {
                tvName1.setText(pref.get(Constants.support_manager_name));
            }

            if (pref.get(Constants.support_owner_name).equals("")
                    || pref.get(Constants.support_owner_name).equals("null")) {
                tvName2.setText("NA");
            } else {
                tvName2.setText(pref.get(Constants.support_owner_name));
            }

            if (pref.get(Constants.support_co_person_name).equals("")
                    || pref.get(Constants.support_co_person_name).equals("null")) {
                tvName4.setText("NA");
            } else {
                tvName4.setText(pref.get(Constants.support_co_person_name));
            }

            if (pref.get(Constants.support_bus_asso_name).equals("")
                    || pref.get(Constants.support_bus_asso_name).equals("null")) {
                tvName3.setText("NA");
            } else {
                tvName3.setText(pref.get(Constants.support_bus_asso_name));
            }

            if (pref.get(Constants.support_manager_phone).equals("")
                    || pref.get(Constants.support_manager_phone).equals("null")) {
                tvMobile1.setText("NA");
            } else {
                tvMobile1.setText(pref.get(Constants.support_manager_phone));
            }

            if (pref.get(Constants.support_owner_phone).equals("")
                    || pref.get(Constants.support_owner_phone).equals("null")) {
                tvMobile2.setText("NA");
            } else {
                tvMobile2.setText(pref.get(Constants.support_owner_phone));
            }

            if (pref.get(Constants.support_co_person_phone).equals("")
                    || pref.get(Constants.support_co_person_phone).equals("null")) {
                tvMobile4.setText("NA");
            } else {
                tvMobile4.setText(pref.get(Constants.support_co_person_phone));
            }

            if (pref.get(Constants.support_bus_asso_phone).equals("")
                    || pref.get(Constants.support_bus_asso_phone).equals("null")) {
                tvMobile3.setText("NA");
            } else {
                tvMobile3.setText(pref.get(Constants.support_bus_asso_phone));
            }

            if (!tvMobile1.getText().toString().equalsIgnoreCase("NA")) {
                call(iv_call1, tvMobile1.getText().toString());
            }
            if (!tvMobile2.getText().toString().equalsIgnoreCase("NA")) {
                call(iv_call2, tvMobile2.getText().toString());
            }
            if (!tvMobile3.getText().toString().equalsIgnoreCase("NA")) {
                call(iv_call3, tvMobile3.getText().toString());
            }
            if (!tvMobile4.getText().toString().equalsIgnoreCase("NA")) {
                call(iv_call4, tvMobile4.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ivEmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName1.getText().toString().equalsIgnoreCase("NA")) {
                    dialog.dismiss();
                    AlertEmailPopup("1", tvName1.getText().toString());
                }
            }
        });
        ivEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName2.getText().toString().equalsIgnoreCase("NA")) {
                    dialog.dismiss();
                    AlertEmailPopup("2", tvName2.getText().toString());
                }
            }
        });
        ivEmail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName3.getText().toString().equalsIgnoreCase("NA")) {
                    dialog.dismiss();
                    AlertEmailPopup("3", tvName3.getText().toString());
                }
            }
        });
        ivEmail4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName4.getText().toString().equalsIgnoreCase("NA")) {
                    dialog.dismiss();
                    AlertEmailPopup("4", tvName4.getText().toString());
                }
            }
        });

    }

    //Email popup
    public void AlertEmailPopup(final String type, String name) {
        emailDialog = new Dialog(mActivity);
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
        final EditText etSubject, etMessage;
        Button btnBrowse, btnSubmit;

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
        chipInputTo.addChip(name, "");

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

                if (charSequence.toString().contains(" ")) {
                    if (Utils.isValidEmail(charSequence.toString().trim())) {
                        chipInputTo.addChip(charSequence.toString().trim(), "");
                    } else {
                        String[] seqSplit = charSequence.toString().split(" ");

                        //  charSequence.toString().replace(" ","");

                        //    chipInputTo.getEditText().setText(charSequence);

                        if (seqSplit.length == 1) {
                            Toast.makeText(mActivity, "Please enter valid email", Toast.LENGTH_SHORT).show();
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
                }else*/
                if (etSubject.getText().toString().isEmpty()) {
                    Toast.makeText(mActivity, "Please enter subject", Toast.LENGTH_SHORT).show();
                } else if (etMessage.getText().toString().isEmpty()) {
                    Toast.makeText(mActivity, "Please enter message", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.isNetworkConnectedMainThred(mActivity)) {
                        hitEmailApi(type, etSubject.getText().toString().trim(), etMessage.getText().toString().trim());
                    } else {
                        Toast.makeText(mActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
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

    private void call(View view, final String number) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));//"tel:"+number.getText().toString();
                if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

    private void hitEmailApi(String type, String subject, String content) {

        loader.show();

        String url = Utils.getCompleteApiUrl(mActivity, R.string.SendSupportEmail);

        Log.v("hitEmailApi", url);

        // selectedMembersList.remove(0);

        JSONArray emailArray = new JSONArray();

        //emailArray.put(selectedMembersList);

        for (int i = 0; i < selectedMembersList.size(); i++) {
            try {
                emailArray.put(i, selectedMembersList.get(i).trim());
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
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        loader.cancel();
        emailDialog.dismiss();
    }

    private void sendDocumentFile(final ArrayList<MediaFile> mediaFiles) {

        final String url = Utils.getCompleteApiUrl(mActivity, R.string.UploadFile);

        Log.v("##url", url);


        try {

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        //Your code goes here
                        FileInputStream fis;

                        File auxFile = new File(mediaFiles.get(0).getPath());
                        //File auxFile = new File(filePath);

                        fis = new FileInputStream(auxFile);

                        filename = mediaFiles.get(0).getPath().substring(mediaFiles.get(0).getPath().lastIndexOf("/") + 1);

                        // HttpFileUploader htfu = new HttpFileUploader("", Media.IMAGE_DIR, filee[0].getName(), UserId, FeedId, Type, Upload_type);
                        HttpFileUploader htfu = new HttpFileUploader(url, filename);
                        // HttpFileUploader htfu = new HttpFileUploader(url,fileName);

                        htfu.doStart(fis);

                        String response = htfu.updateCall();

                        Log.d("Response", response);

                        if (response.equalsIgnoreCase("success")) {
                            if (Utils.isNetworkConnectedMainThred(mActivity)) {
                                // sendChannelMsgApi("3","");

                                documentFileName = mediaFiles.get(0).getName();
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hitPostDocumentsApi();
                                    }
                                });


                                //   etMessage.setText("");
                            } else {
                                //AppUtils.showToastSort(mActivity, getString(R.string.errorInternet));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();


        } catch (Exception e) {

            e.printStackTrace();
            // receiveError();
        }
    }

    private void hitGetAreaMeasurementApi(String case_id, String type_of_asset_id) {

        loader.show();

        String url = Utils.getCompleteApiUrl(mActivity, R.string.GetAreaMeasurement);

        Log.v("hitGetAreaMeasureme", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", case_id);
            jsonObject.put("type_of_asset_id", type_of_asset_id);

            json_data.put("VIS", jsonObject);

            Log.v("hitGetAreaMeasureme", json_data.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseGetAreaMeasurement(response);
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

    private void parseGetAreaMeasurement(JSONObject response) {

        Log.v("res:hitGetAreaMeasureme", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String res_code = jsonObject.getString("response_code");

            if (res_code.equals("1")) {

/*
                if (!firstTime){
                    if (jsonObject.getString("land_area").equalsIgnoreCase("0")){
                        llTotalLandArea.setVisibility(View.GONE);
                    }else {
                        llTotalLandArea.setVisibility(View.VISIBLE);
                        landarea.setText("");
                    }

                    if (jsonObject.getString("covered_area").equalsIgnoreCase("0")){
                        llTotalCoveredArea.setVisibility(View.GONE);
                    }else {
                        llTotalCoveredArea.setVisibility(View.VISIBLE);
                        coverarea.setText("");
                    }
                }
*/
                //coverarea.setText("");


            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        loader.cancel();
    }

    private void hitSendBoundarySchEmail() {

        loader.show();

        String url = Utils.getCompleteApiUrl(mActivity, R.string.SendBoundarySchdMail);

        Log.v("hitSendBoundarySchEmail", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", case_id);

            json_data.put("VIS", jsonObject);

            Log.v("hitSendBoundarySchEmail", json_data.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonSendEmail(response);
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

    private void parseJsonSendEmail(JSONObject response) {

        Log.v("res:hitSendBoundarySchE", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String res_code = jsonObject.getString("response_code");

            if (res_code.equals("1")) {

                try {
                    emailDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    emailDialogg.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                GetBackFragment.ClearStack();
                ((Dashboard) mActivity).displayView(0);

                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        loader.cancel();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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
        mMap.getUiSettings().setZoomControlsEnabled(false);

       /* try {
            setLandMark();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(edit_page);
        uiSettings.setZoomControlsEnabled(edit_page);
        uiSettings.setAllGesturesEnabled(edit_page);
        uiSettings.setScrollGesturesEnabled(edit_page);


    }

    private void setLandMark() {
        if (!pref.get(Constants.landmark_lat).isEmpty() &&
                !pref.get(Constants.landmark_long).isEmpty()) {

            LatLng latLng = new LatLng(Lat, Lng);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(getAddress(mActivity, Lat, Lng));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mMap.addMarker(markerOptions);

            geocoder = new Geocoder(mActivity, Locale.getDefault());
            CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 16f);
            mMap.animateCamera(cameraUpdate1);

        }
    }

    //Email content popup
    public void AlertEmailContentPopup() {
        emailDialogg = new Dialog(mActivity);
        emailDialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailDialogg.setCancelable(true);
        emailDialogg.setContentView(R.layout.alert_email_content_popup);
        Window window = emailDialogg.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        emailDialogg.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        emailDialogg.show();

        ImageView close;
        TextView tvMessage;
        Button btnSubmit;
        TextView tvEmailName;

        close = emailDialogg.findViewById(R.id.iv_close);
        tvMessage = emailDialogg.findViewById(R.id.tvMessage);
        btnSubmit = emailDialogg.findViewById(R.id.btnSubmit);
        tvEmailName = emailDialogg.findViewById(R.id.tvEmailName);

        tvEmailName.setText(sendToNameEmail);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(mActivity)) {

                    hitSendBoundarySchEmail();
                    //Log.v("asasaxzceds","send");
                } else {
                    Toast.makeText(mActivity, R.string.noInternetConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialogg.dismiss();
            }
        });

    }

    private void setDistrict(String districtId, String stateId) {
        districtList.clear();

        HashMap hashMap = new HashMap<>();
        hashMap.put("id", "0");
        hashMap.put("name", "Choose an item");
        hashMap.put("state_id", "0");

        districtList.add(hashMap);

        districtList = DatabaseController.getDistrict(stateId);
        stateAdapter = new StateAdapter(mActivity, districtList);
        spinnerDistrict.setAdapter(stateAdapter);

        if (!districtId.equalsIgnoreCase("")) {
            int disPos = 0;
            for (int k = 0; k < districtList.size(); k++) {
                try {
                    if (districtList.get(k).get("id").equalsIgnoreCase(districtId)) {

                        disPos = k;

                        districtText = districtList.get(disPos).get("name");

                        spinnerDistrict.setSelection(disPos);

                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void setStates(String stateId) {
        stateList.clear();

        HashMap hashMap = new HashMap<>();
        hashMap.put("id", "0");
        hashMap.put("name", "Choose an item");

        stateList.add(hashMap);

        stateList.addAll(DatabaseController.getStates());

        stateAdapter = new StateAdapter(mActivity, stateList);
        spinnerState.setAdapter(stateAdapter);

        if (!stateId.equalsIgnoreCase("")) {
            int disPos = 0;
            for (int k = 0; k < stateList.size(); k++) {
                try {
                    if (stateList.get(k).get("id").equalsIgnoreCase(stateId)) {

                        disPos = k;

                        spinnerState.setSelection(disPos);

                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

/*
        if (!stateId.equalsIgnoreCase("")){
            spinnerState.setSelection(Integer.parseInt(stateId)-1);
        }
*/
    }

    private void setCities(String districtId, String cityVal) {

        cityList.clear();

        HashMap hashMapp = new HashMap<>();
        hashMapp.put("id", "00");
        hashMapp.put("name", "Choose an item");
        hashMapp.put("state_id", "00");
        hashMapp.put("district_id", "00");
        hashMapp.put("other", "");

        cityList.add(hashMapp);

        cityList.addAll(DatabaseController.getCities(districtId));

        //    if (cityList.size() == 0)
        {

            HashMap hashMap = new HashMap<>();
            hashMap.put("id", "0");
            hashMap.put("name", "Other");
            hashMap.put("state_id", "0");
            hashMap.put("district_id", "0");
            hashMap.put("other", "");

            cityList.add(hashMap);

            //  etCityVillageTehsilOther.setVisibility(View.VISIBLE);
        }/*else

            {
            etCityVillageTehsilOther.setVisibility(View.GONE);
        }*/

        stateAdapter = new StateAdapter(mActivity, cityList);
        spinnerCityVillage.setAdapter(stateAdapter);

        if (!cityVal.equalsIgnoreCase("")) {
            int disPos = 0;
            for (int k = 0; k < cityList.size(); k++) {
                try {

                    if (!TextUtils.isDigitsOnly(cityVal)) {
                        disPos = cityList.size() - 1;
                        cityVillageTehsilText = cityVal;
                        break;
                    } else if (cityList.get(k).get("id").equalsIgnoreCase(cityVal)) {
                        disPos = k;
                        cityVillageTehsilText = cityList.get(disPos).get("name");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            spinnerCityVillage.setSelection(disPos);
        }

        //  Log.v("asxzzxczxc",spinnerCityVillage.getSelectedItem().toString());

    }

    private void setVillages(String districtId, String cityVal) {

        //  Log.v("asfasfas", "villlll");
        //  Log.v("asfasfas", districtId);

        villageList.clear();

        HashMap hashMapp = new HashMap<>();
        hashMapp.put("id", "00");
        hashMapp.put("name", "Choose an item");
        hashMapp.put("state_id", "00");
        hashMapp.put("district_id", "00");
        hashMapp.put("other", "");

        villageList.add(hashMapp);

        villageList.addAll(DatabaseController.getVillages(districtId));

        HashMap hashMap = new HashMap<>();
        hashMap.put("id", "0");
        hashMap.put("name", "Other");
        hashMap.put("state_id", "0");
        hashMap.put("district_id", "0");
        hashMap.put("other", "");

        villageList.add(hashMap);

        stateAdapter = new StateAdapter(mActivity, villageList);
        spinnerCityVillage.setAdapter(stateAdapter);

        if (!cityVal.equalsIgnoreCase("")) {
            int disPos = 0;
            for (int k = 0; k < villageList.size(); k++) {
                try {
                    //     if (!TextUtils.isDigitsOnly(jsonObject.getString("city_val"))){

                    if (!TextUtils.isDigitsOnly(cityVal)) {
                        disPos = villageList.size() - 1;
                        cityVillageTehsilText = cityVal;
                        break;
                    } else if (villageList.get(k).get("id").equalsIgnoreCase(cityVal)) {
                        disPos = k;
                        cityVillageTehsilText = villageList.get(disPos).get("name");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            spinnerCityVillage.setSelection(disPos);
        }
    }

    private void setTehsils(String districtId, String cityVal) {

        //Log.v("asfasfas", "tehssss");
        // Log.v("asfasfas", districtId);

        tehsilList.clear();

        HashMap hashMapp = new HashMap<>();
        hashMapp.put("id", "00");
        hashMapp.put("name", "Choose an item");
        hashMapp.put("state_id", "00");
        hashMapp.put("district_id", "00");
        hashMapp.put("other", "");

        tehsilList.add(hashMapp);

        tehsilList.addAll(DatabaseController.getTehsils(districtId));

        HashMap hashMap = new HashMap<>();
        hashMap.put("id", "0");
        hashMap.put("name", "Other");
        hashMap.put("state_id", "0");
        hashMap.put("district_id", "0");
        hashMap.put("other", "");

        tehsilList.add(hashMap);

        stateAdapter = new StateAdapter(mActivity, tehsilList);
        spinnerCityVillage.setAdapter(stateAdapter);

        if (!cityVal.equalsIgnoreCase("")) {
            int disPos = 0;
            for (int k = 0; k < tehsilList.size(); k++) {
                try {
                    if (!TextUtils.isDigitsOnly(cityVal)) {
                        disPos = tehsilList.size() - 1;
                        cityVillageTehsilText = cityVal;
                        break;
                    } else if (tehsilList.get(k).get("id").equalsIgnoreCase(cityVal)) {
                        disPos = k;
                        cityVillageTehsilText = tehsilList.get(disPos).get("name");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            spinnerCityVillage.setSelection(disPos);
        }
    }

    public class CallAdapter extends BaseAdapter {
        LayoutInflater inflter;
        Context context;

        ArrayList<HashMap<String, String>> alist;


        public CallAdapter(Context context, ArrayList<HashMap<String, String>> alist) {
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
            view = inflter.inflate(R.layout.call_adapter, null);
            numberAdap = view.findViewById(R.id.tv_number);
            iv_phn = view.findViewById(R.id.iv_phn);
            iv_phn2 = view.findViewById(R.id.iv_phn2);

            if (callstatus == 1) {
                view.setBackgroundColor(getResources().getColor(R.color.greyLight));
                iv_phn2.setVisibility(View.VISIBLE);
                iv_phn.setVisibility(View.GONE);
                numberAdap.setTextColor(getResources().getColor(R.color.app_header));
            } else {
                iv_phn2.setVisibility(View.GONE);
                iv_phn.setVisibility(View.VISIBLE);
            }

            iv_phn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + number.getText().toString()));//"tel:"+number.getText().toString();
                        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                }
            });

            numberAdap.setText(alist.get(i).get("number"));
            return view;
        }

    }

    public class StateAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String, String>> alist;
        LayoutInflater inflter;


        public StateAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
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
            convertView = inflter.inflate(R.layout.profession_adapter, null);
            property_name = (TextView) convertView.findViewById(R.id.tv_profession);
            property_name.setText(alist.get(position).get("name"));
            //   Log.v("property_array=", alist.get(position).get("name"));
            return convertView;
        }
    }

    //    **********************************************************************************************************************************************************
    public class adapter_spinner extends ArrayAdapter<HashMap<String, String>> {

        ArrayList<HashMap<String, String>> data;

        public adapter_spinner(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> arraySpinner_time) {

            super(context, textViewResourceId, arraySpinner_time);

            this.data = arraySpinner_time;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row = inflater.inflate(R.layout.spinner_textview_new, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tv_spinner_name);

            label.setText(data.get(position).get("symbol").toString());

            //label.setTypeface(typeface);

            return row;
        }
    }

    public class adapter_spinner1 extends ArrayAdapter<HashMap<String, String>> {

        ArrayList<HashMap<String, String>> data;

        public adapter_spinner1(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> arraySpinner_time) {

            super(context, textViewResourceId, arraySpinner_time);

            this.data = arraySpinner_time;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row = inflater.inflate(R.layout.spinner_textview_new, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tv_spinner_name);

            label.setText(data.get(position).get("name").toString());

            //label.setTypeface(typeface);

            return row;
        }
    }

    public class PropertyAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String, String>> alist;
        LayoutInflater inflter;


        public PropertyAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
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
            convertView = inflter.inflate(R.layout.profession_adapter, null);
            property_name = (TextView) convertView.findViewById(R.id.tv_profession);
            property_name.setText(alist.get(position).get("name"));
            Log.v("property_array=", alist.get(position).get("name"));
            return convertView;
        }
    }

    public class LandAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflter;
        ArrayList<HashMap<String, String>> alist;

        public LandAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
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

            convertView = inflter.inflate(R.layout.land_adapter, null);
            TextView textView = convertView.findViewById(R.id.tv_surveyor);
            //textView.setTextSize(10f);
            try {
                textView.setText(alist.get(position).get("symbol"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public class CoveredAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String, String>> alist;
        LayoutInflater inflter;


        public CoveredAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
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
            convertView = inflter.inflate(R.layout.cover_adapter, null);
            cover_name = (TextView) convertView.findViewById(R.id.tv_surveyor);
            cover_name.setText(alist.get(position).get("symbol"));
            return convertView;
        }
    }

    public class DocumentAdapter extends BaseAdapter {

        Context context;
        String alist[];
        LayoutInflater inflter;


        public DocumentAdapter(Context applicationContext, String alist[]) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.length;
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
            convertView = inflter.inflate(R.layout.document_adapter, null);
            document_name = (TextView) convertView.findViewById(R.id.tv_profession);
            document_name.setText(alist[position]);
            return convertView;
        }
    }

    public class EastAdapter extends BaseAdapter {

        Context context;
        String alist[];
        LayoutInflater inflter;


        public EastAdapter(Context applicationContext, String alist[]) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.length;
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
            convertView = inflter.inflate(R.layout.document_adapter, null);
            document_name = (TextView) convertView.findViewById(R.id.tv_profession);
            document_name.setText(alist[position]);
            return convertView;
        }
    }

    public class WestAdapter extends BaseAdapter {

        Context context;
        String alist[];
        LayoutInflater inflter;


        public WestAdapter(Context applicationContext, String alist[]) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.length;
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
            convertView = inflter.inflate(R.layout.document_adapter, null);
            document_name = (TextView) convertView.findViewById(R.id.tv_profession);
            document_name.setText(alist[position]);
            return convertView;
        }
    }

    public class NorthAdapter extends BaseAdapter {

        Context context;
        String alist[];
        LayoutInflater inflter;


        public NorthAdapter(Context applicationContext, String alist[]) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.length;
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
            convertView = inflter.inflate(R.layout.document_adapter, null);
            document_name = (TextView) convertView.findViewById(R.id.tv_profession);
            document_name.setText(alist[position]);
            return convertView;
        }
    }

    public class SouthAdapter extends BaseAdapter {

        Context context;
        String alist[];
        LayoutInflater inflter;


        public SouthAdapter(Context applicationContext, String alist[]) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.length;
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
            convertView = inflter.inflate(R.layout.document_adapter, null);
            document_name = (TextView) convertView.findViewById(R.id.tv_profession);
            document_name.setText(alist[position]);
            return convertView;
        }
    }

    public class SurveyorAdapter extends BaseAdapter {
        LayoutInflater inflter;
        Context context;
        ArrayList<HashMap<String, String>> alist;
        TextView name;

        public SurveyorAdapter(Context context, ArrayList<HashMap<String, String>> alist) {

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
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.surveyor_adapter, null);

            getid(view);
            setValues(i);

            return view;
        }

        public void getid(View v) {
            name = v.findViewById(R.id.tv_name);
        }

        public void setValues(final int position) {
            name.setText(alist.get(position).get("name"));
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

            Log.d("expandableListTitle", String.valueOf(expandableListTitle.size()));
            Log.d("expandableListDetail", String.valueOf(expandableListDetail.size()));
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
                            startActivity(new Intent(mActivity, GalleryActivity.class));
                        }
                    }
                });
            }

            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {

            Log.d("ListPosition", String.valueOf(expandableListDetail.get(listPosition).size()));

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

    public class AdapterReferredDocuments extends BaseExpandableListAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> expandableListTitle;
        private ArrayList<ArrayList<HashMap<String, String>>> expandableListDetail;


        public AdapterReferredDocuments(Context context, ArrayList<HashMap<String, String>> expandableListTitle, ArrayList<ArrayList<HashMap<String, String>>> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;

            Log.d("expandableListTitle", String.valueOf(expandableListTitle.size()));
            Log.d("expandableListDetail", String.valueOf(expandableListDetail.size()));
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

                    /*if (expandableListDetail.get(listPosition).get(expandedListPosition).get("mandatory").equalsIgnoreCase("1")){

                        //holder.tv_name.setText(alist.get(position).get("doc_name")+"*"+" ("+alist.get(position).get("count")+")");

                        expandedListTextView.setText(expandedListText + "* (" + document_url_array.length() + ")");

                    }else {*/

                    //      expandedListTextView.setText(expandedListText + "(" + expandableListDetail.get(listPosition).get(expandedListPosition).get("count") + ")");
                    //   holder.tv_name.setText(alist.get(position).get("doc_name")+" ("+alist.get(position).get("count")+")");
                    //  }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                expandedListTextView.setText(expandedListText + " (" + expandableListDetail.get(listPosition).get(expandedListPosition).get("count") + ")");

                // expandedListTextView.setText(expandedListText + " (0)");

            }


            ImageView iv_download = convertView.findViewById(R.id.iv_download);

            iv_download.setImageResource(R.drawable.ic_upload);

            iv_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    documentId = expandableListDetail.get(listPosition).get(expandedListPosition).get("doc_id");

                    showCameraGalleryDialog();

                }
            });


            RelativeLayout rlMain = convertView.findViewById(R.id.rlMain);

            if (expandableListDetail.get(listPosition).get(expandedListPosition).containsKey("document_url")) {

                rlMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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

                        from = 2;
                        if (expandableListDetail.get(listPosition).get(expandedListPosition).containsKey("document_url")) {

                            startActivity(new Intent(mActivity, GalleryActivity.class));
                        }
                    }
                });

            }

            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {

            Log.d("ListPosition", String.valueOf(expandableListDetail.get(listPosition).size()));

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
        public View getGroupView(final int listPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            final String listTitle = expandableListTitle.get(listPosition).get("group_name");

            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.inflate_document_group_name, null);
            }

            TextView listTitleTextView = convertView.findViewById(R.id.listTitle);


            if (expandableListTitle.get(listPosition).get("mandatory").equalsIgnoreCase("1")) {
                listTitleTextView.setText(listTitle + "*");
            } else {
                listTitleTextView.setText(listTitle);
            }

            if (isExpanded) {
                groupId = expandableListTitle.get(listPosition).get("group_id");
                Log.v("asfasvxvzxvas", groupId);
            }

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

    public class AdapterDocumentsToCollect extends BaseExpandableListAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> expandableListTitle;
        private ArrayList<ArrayList<HashMap<String, String>>> expandableListDetail;


        public AdapterDocumentsToCollect(Context context, ArrayList<HashMap<String, String>> expandableListTitle, ArrayList<ArrayList<HashMap<String, String>>> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;

            Log.d("expandableListTitle", String.valueOf(expandableListTitle.size()));
            Log.d("expandableListDetail", String.valueOf(expandableListDetail.size()));
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
            expandedListTextView.setText(expandedListText);


            RelativeLayout rlMain = convertView.findViewById(R.id.rlMain);
            ImageView iv_download = convertView.findViewById(R.id.iv_download);
            iv_download.setVisibility(View.GONE);


            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {

            Log.d("ListPosition", String.valueOf(listPosition));

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
