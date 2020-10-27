package com.vis.android.Activities.General.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DecimalFormat;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.vis.android.Activities.CompasActivity;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.SpinnerAdapter;
import com.vis.android.BuildConfig;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.HttpFileUploader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.Extras.GetLocation;
import com.vis.android.Extras.MySupportMapFragment;
import com.vis.android.Fragments.PriliminaryActivity;
import com.vis.android.R;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.SENSOR_SERVICE;
import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;

public class InitiateSurveyForm extends BaseFragment implements View.OnClickListener, SensorEventListener, OnMapReadyCallback {
    private static final String IMAGE_DIRECTORY_NAME = "VIS";
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2, VIDEO_CAPTURE = 101;
    public static ArrayList<HashMap<String, String>> unit_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> temp_unit_array_list = new ArrayList<HashMap<String, String>>();
    public static HashMap<String, String> hm = new HashMap<>();
    public static ArrayList<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();
    public static JSONArray jsonArrayData;
    public static int surveyTypeCheck = 0;
    public static int referLayout = 0;
    public static int propertyMeasurementIndex;
    public static int cbItIsAFlatChecked = 0;
    public static int cbPropertyWasLockedChecked = 0;
    public static int cbOwnerPossesseeChecked = 0;
    public static int cbNpaPropertyChecked = 0;
    public static int cbVeryLargeIrregularChecked = 0;
    public static int cbAnyOtherReasonChecked = 0;
    public static String cbAnyOtherReasonValue = "";
    public static String sitePresence = "";
    static String media_status = "0";
    static Preferences pref;
    static RadioGroup rgPropertyShownBy;
    static EditText etName, etNameRep;
    static EditText etCoPersonEmail, etCoPersonEmailRep;
    static EditText etContactNumber, etContactNumberRep;
    static EditText etCoPersonRelation, etCoPersonRelationRep;
    static EditText etLesseeName, etLesseeContactNumber;
    static int countPhoto = 0, countExtPhoto = 0;
    static ArrayList<String> photographsList = new ArrayList<>();
    static ArrayList<String> dynamicPhotographsList = new ArrayList<>();
    static ArrayList<String> dynamicExtPhotographsList = new ArrayList<>();
    private static int videoCheck = 0;
    public String string_regulized, picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", encodedString = "", encodedString5 = "", encodedString6 = "", encodeSd = "", encodedString1 = "", encodedString2 = "", encodedString3 = "", encodedString4 = "", setPic = "",
            shownByImage = "", shownByImage2 = "", shownByImage1 = "";
    RelativeLayout footer, rl_east, rl_west, rl_north, rl_south;
    String array_unit;
    TextView tv_camera1, tv_camera2, tv_compass, tvRegularizationCerti, camera1, camera2, tvNext, tvSendReasonMail;
    ImageView ivRegularizationCerti, iv_camera1, iv_camera2, ivEast, ivWest, ivNorth, ivSouth;
    ImageView selfie_one, selfie_two, selfie_three, nameplate_one, nameplate_two, nameplate_three, approach_one, approach_two, approach_three, road_one, road_two, road_three, photo_one, photo_two, photo_three;
    ImageView Ephoto_one, Ephoto_two, Ephoto_three, Ephoto_four, Ephoto_five, Ephoto_six, Ephoto_seven, Mgate_one, Mgate_two, Mgate_three, Mgate_four, Mgate_five, Iphoto_one, Iphoto_two, Iphoto_three, Iphoto_four, Iphoto_five, ivAddMoreInterior;
    ImageView iv1;
    ImageView ivCameraCapture1, ivCameraCapture2, ivCompass;
    ImageView ivCameraLetter, ivCameraLetterImage, ivCameraLetterRep, ivCameraLetterImageRep, ivAuthLetterRep;
    EditText etname, etnumber, et_reason, et_coverage, et_one, et_two, et_three, et_one2, et_two2, et_three2, et_one3, et_two3, et_three3;
    EditText et_one4, et_two4, et_three4, etDirectionEast, etDirectionWest, etDirectionNorth, etDirectionSouth, etProceedReason;
    EditText etCoveredAreaAsPerTitle, etCoveredAreaAsPerMap, etCoveredAreaAsSiteSurvey, etCoveredAreaEast, etCoveredAreaWest,
            etCoveredAreaNorth, etCoveredAreaSouth, etCoveredFrontsetback, etCoveredBackSetback, etCoveredRightSideSetback,
            etCoveredLeftSideSetback, etInCaseOfDifferenceOf2, etGroundCoverage, etAnyOtherReason, etLandAreaAsSiteSurvey, etLandAreaAsPerTitle, etLandAreaAsPerMap;
    int showPop = 2;
    Intent intent;
    TextView tv_lenghtSideA, tv_lenghtSideB, tv_lenghtSideC, tv_lenghtSideD, tv_lenghtSideH;
    Boolean edit_page = false;
    String selectedUnit = "";
    RelativeLayout rl_SideValues;
    Boolean statusAreaMap = false;
    Boolean statusCovAreaMap = false;
    Uri capVidUri;
    Boolean statusAreaTitle = false;
    Boolean statusCovAreaTitle = false;
    TextView tv_SpinnerSideB, tv_SpinnerSideC, tv_SpinnerSideD, tv_SpinnerSideH, tvIncaseDifferenceTitle,
            tvIncaseDifferenceMap,tvInCasediffCoverTitle ,tvInCasediffCoverMap;
    Spinner unitspinnerSideA;
    LinearLayout ll_SideA, ll_SideB, ll_SideC, ll_SideD, ll_SideH;
    RadioGroup rgNamePlate, rg_enquiry, rg_direction, rg_boundries, rg_measurement, rg_landarea, rgCoveredBuiltup, rg_document,
            rgSurveyType, rgReasonForHalfSurvey;
    RadioButton checkedRadioButton, rb_plate, rb_noplate;
    LinearLayout ll_displayed, ll_notdisplayed, ll_nearby, llRegularizationCerti, llReasonForHalf;
    LinearLayout ll_selfieone, ll_selfietwo, ll_selfiethree;
    LinearLayout ll_plateone, ll_platetwo, ll_platethree, ll_identifyProperty;
    LinearLayout ll_approachone, ll_approachtwo, ll_approachthree;
    LinearLayout ll_roadone, ll_roadtwo, ll_roadthree;
    LinearLayout ll_photoone, ll_phototwo, ll_photothree;
    LinearLayout ll_Ephotoone, ll_Ephototwo, ll_Ephotothree, ll_Ephotofour, ll_Ephotofive, ll_Ephotosix, ll_Ephotoseven;
    LinearLayout ll_Mgateone, ll_Mgatetwo, ll_Mgatethree, ll_Mgatefour, ll_Mgatefive;
    LinearLayout ll_Iphotoone, ll_Iphototwo, ll_Iphotothree, ll_Iphotofour, ll_Iphotofive;
    // CheckBox cb_surveytype1, cb_surveytype2, cb_surveytype3, cb_surveytype4
    // CheckBox cb_reson1, cb_reson2, cb_reson3, cb_reson4, cb_reson5;
    CheckBox cb_summery1, cb_summery2, cb_summery3, cb_summery4, cb_summery5, cb_summery6, cbStillProceed;//, cb_summery7;
    CheckBox cbExtentConstructionWithoutMap, cbExtentExtraCoverageWithin, cbExtentExtraCoverageOut, cbExtentGroundCoverage, cbExtentMinorCondonable, cbExtentCondonableStructural, cbExtentNonCondonableStructural;
    CheckBox cbItIsAFlat, cbPropertyWasLocked, cbOwnerPossessee, cbNpaProperty, cbVeryLargeIrregular, cbAnyOtherReason, cbConstructionDoneWithoutMap, cbConstructionNotAsPer, cbExtraCovered, cbJoinedAdjacent, cbSetbackPortion, cbEnroachedAdjacent;
    String selected_document;

    //   RelativeLayout rl_casedetail;
    String selected_buildup="";
    String selected_boundries="";
    String selected_landarea="";
    String selected_direction="";
    String selected_measurement;
    String selected_property="", landarea="", maparea="";
    String selected_enquiry="";
    String camera_status = "0";
    String shapeSelected = "";
    Spinner spinnerIsThisBeingRegularized, spEast, spWest, spNorth, spSouth, spinnerLandShape;
    Uri picUri, fileUri;
    Spinner spinnerDirectA, spinnerDirectB, spinnerDirectC, spinnerDirectD, spinnerDirectH;
    Bitmap bitmap;
    Double sideA = 0.0, sideB = 0.0, sideC = 0.0, sideD = 0.0, sideH = 0.0, titleDiffPercent = 0.0, mapDiffPercent = 0.0, coveredDiffPercent = 0.0, mapCovDiffPercent = 0.0;
    String sideAUnit = "", sideADirect = "", sideBDirect = "", sideCDirect = "", sideDDirect = "", sideHDirect = "";
    SpinnerAdapter spinnerAdapter;
    String isThisBeingRegularizedArray[] = {"Choose an item", "Yes", "No", "No Information Provided", "Not Applicable"};
    CustomLoader loader;
    ScrollView scrollView;
    TextView tvPhotographsHeading, tvDegree, tvQuesE, tvQuesJ, tvAddMoreInterior, tvAddMoreExterior;
    int count = 0;
    FlowLayout flowLayout, flowExterLayout;
    LinearLayout llTemp, llTemp2;
    LinearLayout llPropertyName, llPropertyImage, llPropertyContact, llPropertyCoPersonEmail,
            llPropertyNameRep, llPropertyImageRep, llPropertyContactRep, llPropertyCoPersonEmailRep,
            llPropertyCoPersonRelation, llPropertyCoPersonRelationRep, llPropertyAuthLetterRep,
            llReasonNoMeasurement, llCoveredBuiltup2, llLesseeName, llLesseeContact, ll_caseDifference;
    TextView tvLetterImage, tvLetterImageRep, tvReasonFor, tvLandArea, tvInCase1, tvGroundCoverage, tvCovered, tvViewAuthLetter;
    TextView tvAsPer;
    EditText etAnyOtherReasonMeasurement;
    int selfiePhotoCount = 0;
    int namePlatePhotoCount = 0;
    int leftSidePhotoCount = 0;
    int rightSidePhotoCount = 0;
    int ownerPhotoCount = 0;
    int extPhotoCount = 0;
    int mainGatePhotoCount = 0;
    int intPhotoCount = 0;
    View v;
    GoogleMap mMap;
    Geocoder geocoder;
    GetLocation location;
    LinearLayout ll_landGroundCoverage, llLandArea;
    LinearLayout llCoveredAreaEast, llCoveredAreaWest, llCoveredAreaNorth, llCoveredAreaSouth, llCoveredAreaFs, llCoveredAreaBs, llCoveredAreaLs, llCoveredAreaRs;

    VideoView videoViewPreview1, videoViewPreview2;
    String east_ne[] = {"East", "North-East"};
    String west_nw[] = {"West", "North-West"};
    String north_se[] = {"North", "South-East"};
    String south_sw[] = {"South", "South-West"};
    String ownerName = "", bankManagerEmail = "", bankManagerName = "", ownerEmail = "", coPersonEmail = "",
            coPersonName = "", siteInspectorName = "", siteInspectorEmail = "", mailContent = "";
    LinearLayout ll_LandArea2;
    Dialog emailDialogContent;
    String typeSurveyChecker = "first";//generalPage 2
    String typeSurveyChecker8 = "first";//ggeneralPage 8
    CheckBox cbFlat, cbVery, cbNpa, cbAnyOtherNotApplicable;
    EditText etRemarksNotApplicable;
    LinearLayout llNotApplicable;
    String survType = "";
    private List<String> shapelist, directionsList;
    // record the compass picture angle turned
    private float currentDegree = 0f;
    // device sensor manager
    private SensorManager mSensorManager;
    private double Lat, Lng;
    private Marker marker = null;
    private String videoName = "";
    private String capturedPath;
    private String authLetter = "";
    private boolean firstTime = true;
    private int checkSummery = 1;
    private String landmark = "";

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
        File mediaFile = null;
        if (type == MEDIA_TYPE_IMAGE) {

            if (media_status.equalsIgnoreCase("1")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesA1.jpg");

            } else if (media_status.equalsIgnoreCase("2")) {

//                mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                        + pref.get(Constants.case_id)+"_"+"QuesA2_" + timeStamp + ".jpg");

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesA2.jpg");

            } else if (media_status.equalsIgnoreCase("3")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesA3.jpg");


            } else if (media_status.equalsIgnoreCase("4")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesB1.jpg");

            } else if (media_status.equalsIgnoreCase("5")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesB2.jpg");

            } else if (media_status.equalsIgnoreCase("6")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesB3.jpg");

            } else if (media_status.equalsIgnoreCase("7")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesC1.jpg");

            } else if (media_status.equalsIgnoreCase("8")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesC2.jpg");

            } else if (media_status.equalsIgnoreCase("9")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesC3.jpg");

            } else if (media_status.equalsIgnoreCase("10")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesD1.jpg");

            } else if (media_status.equalsIgnoreCase("11")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesD2.jpg");

            } else if (media_status.equalsIgnoreCase("12")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesD3.jpg");

            } else if (media_status.equalsIgnoreCase("13")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesE1.jpg");

            } else if (media_status.equalsIgnoreCase("14")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesE2.jpg");

            } else if (media_status.equalsIgnoreCase("15")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesE3.jpg");

            } else if (media_status.equalsIgnoreCase("16")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesF1.jpg");

            } else if (media_status.equalsIgnoreCase("17")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesF2.jpg");

            } else if (media_status.equalsIgnoreCase("18")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesF3.jpg");

            } else if (media_status.equalsIgnoreCase("19")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesF4.jpg");

            } else if (media_status.equalsIgnoreCase("20")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesG1.jpg");

            } else if (media_status.equalsIgnoreCase("21")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesG2.jpg");

            } else if (media_status.equalsIgnoreCase("22")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesG3.jpg");

            } else if (media_status.equalsIgnoreCase("23")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesG4.jpg");

            } else if (media_status.equalsIgnoreCase("24")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesG5.jpg");

            } else if (media_status.equalsIgnoreCase("25")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesH1.jpg");

            } else if (media_status.equalsIgnoreCase("26")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesH2.jpg");

            } else if (media_status.equalsIgnoreCase("27")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesH3.jpg");

            } else if (media_status.equalsIgnoreCase("28")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesH4.jpg");

            } else if (media_status.equalsIgnoreCase("29")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesH5.jpg");

            } else if (media_status.equalsIgnoreCase("32")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesF5.jpg");

            }/*else if (media_status.equalsIgnoreCase("33")){

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id)+"_"+"QuesF6.jpg");

            }*/ else if (media_status.equalsIgnoreCase("33")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesExtDyn" + countExtPhoto + ".jpg");

            } else if (media_status.equalsIgnoreCase("34")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesF7.jpg");

            } else if (media_status.equalsIgnoreCase("35")) {

                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "QuesDyn" + countPhoto + ".jpg");

            } else {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
            }
        } else if (type == MEDIA_TYPE_VIDEO) {

            if (videoCheck == 1) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "VID1.mp4");
            } else if (videoCheck == 2) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + pref.get(Constants.case_id) + "_" + "VID2.mp4");
            }
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
        } catch (Exception e) {
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
        v = inflater.inflate(R.layout.activity_initiate_survey, container, false);

        getid(v);
        setConditon();
        setListener();

        Log.v("array_unit+++", edit_page.toString());
        photographsList.clear();
        dynamicPhotographsList.clear();
        ll_LandArea2.setVisibility(View.GONE);
        ll_caseDifference.setVisibility(View.GONE);
        spinnerAdapter = new SpinnerAdapter(mActivity, east_ne);
        spEast.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, west_nw);
        spWest.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, north_se);
        spNorth.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, south_sw);
        spSouth.setAdapter(spinnerAdapter);
        addItemsOnSpinner();
        populateSpinner();
        if (pref.get(Constants.directionType).equalsIgnoreCase("0")) {
            spEast.setSelection(0);
            spWest.setSelection(0);
            spNorth.setSelection(0);
            spSouth.setSelection(0);
        } else {
            spEast.setSelection(1);
            spWest.setSelection(1);
            spNorth.setSelection(1);
            spSouth.setSelection(1);
        }
        etLandAreaAsPerTitle.setText(landarea);
        etLandAreaAsPerMap.setText(maparea);
        spinnerAdapter = new SpinnerAdapter(mActivity, isThisBeingRegularizedArray);
        spinnerIsThisBeingRegularized.setAdapter(spinnerAdapter);

        spinnerIsThisBeingRegularized.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                string_regulized = isThisBeingRegularizedArray[i];

                if (string_regulized.equals("Yes")) {
                    llRegularizationCerti.setVisibility(View.VISIBLE);
                } else {
                    llRegularizationCerti.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        unitspinnerSideA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                       @Override
                                                       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                           Log.v("itemSelected", String.valueOf(unit_array_list.get(i).get("symbol")));

                                                           //selected_property = String.valueOf(property_array_list.get(i));
                                                           selectedUnit = unit_array_list.get(i).get("symbol");
                                                           tv_SpinnerSideB.setText(selectedUnit);
                                                           tv_SpinnerSideC.setText(selectedUnit);
                                                           tv_SpinnerSideD.setText(selectedUnit);
                                                           tv_SpinnerSideH.setText(selectedUnit);
                                                           sideAUnit = selectedUnit;

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                       }

                                                       @Override
                                                       public void onNothingSelected(AdapterView<?> adapterView) {

                                                       }
                                                   }
        );
        spinnerDirectB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                         sideBDirect = spinnerDirectB.getSelectedItem().toString();


//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> adapterView) {

                                                     }
                                                 }
        );

        spinnerDirectC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                         sideCDirect = spinnerDirectC.getSelectedItem().toString();


//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> adapterView) {

                                                     }
                                                 }
        );

        spinnerDirectD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                         sideDDirect = spinnerDirectD.getSelectedItem().toString();


//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> adapterView) {

                                                     }
                                                 }
        );

        spinnerDirectH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                         sideHDirect = spinnerDirectH.getSelectedItem().toString();


//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> adapterView) {

                                                     }
                                                 }
        );

        spinnerDirectA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                         sideADirect = spinnerDirectA.getSelectedItem().toString();


//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> adapterView) {

                                                     }
                                                 }
        );

        rgNamePlate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    selected_property = (String) checkedRadioButton.getText();

                    if (selected_property.equals("Proper name plate displayed on the property.")) {
                        ll_displayed.setVisibility(View.VISIBLE);
                        ll_notdisplayed.setVisibility(View.GONE);
                        cb_summery2.setEnabled(true);
                        cb_summery2.setChecked(true);
                        ivCameraCapture1.setVisibility(View.VISIBLE);
                        ivCameraCapture2.setVisibility(View.GONE);
                        ivCameraCapture2.setImageBitmap(null);
                        encodedString6 = "";
                        tv_camera2.setText("");
                    } else if (selected_property.equals("No name plate displayed on the property.")) {
                        ll_notdisplayed.setVisibility(View.VISIBLE);
                        ll_displayed.setVisibility(View.GONE);
                        ivCameraCapture1.setImageBitmap(null);
                        cb_summery2.setEnabled(false);
                        cb_summery2.setChecked(false);
                        ivCameraCapture2.setVisibility(View.VISIBLE);
                        ivCameraCapture1.setVisibility(View.GONE);
                        encodedString5 = "";
                        tv_camera1.setText("");
                    } else {
                        ll_displayed.setVisibility(View.GONE);
                        ll_notdisplayed.setVisibility(View.GONE);
                        ivCameraCapture1.setVisibility(View.GONE);
                        ivCameraCapture2.setVisibility(View.GONE);
                        encodedString6 = "";
                        encodedString5 = "";
                        tv_camera2.setText("");
                        tv_camera1.setText("");
                        cb_summery2.setEnabled(false);
                        ivCameraCapture2.setImageBitmap(null);
                        ivCameraCapture1.setImageBitmap(null);
                        cb_summery2.setChecked(false);
                    }

                }
            }
        });

        rg_direction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    selected_direction = (String) checkedRadioButton.getText();
                }
            }
        });

        rg_measurement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    selected_measurement = (String) checkedRadioButton.getText();
                }

                RadioButton radioButton = (RadioButton) v.findViewById(checkedId);
                int pos = group.indexOfChild(radioButton);

                propertyMeasurementIndex = pos;

                if (radioButton.isChecked()) {
                    if (pos == 3) {
                        llReasonNoMeasurement.setVisibility(View.VISIBLE);

                        tvLandArea.setText((R.string.b_land_area_init_));
                        tvInCase1.setText((R.string.d_in_case_of_difference_of_init_));
                        tvGroundCoverage.setText((R.string.d_ground_coverage_init_));
                        tvCovered.setText((R.string.f_covered_built_up_area_init_));
                    } else {
                        llReasonNoMeasurement.setVisibility(View.GONE);
                        tvLandArea.setText((R.string.b_land_area_init));
                        tvInCase1.setText((R.string.d_in_case_of_difference_of_init));
                        tvGroundCoverage.setText((R.string.d_ground_coverage_init));
                        tvCovered.setText((R.string.f_covered_built_up_area_init));
                    }
                }
            }
        });

        rg_document.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = (RadioButton) v.findViewById(checkedId);
                int a = group.indexOfChild(radioButton);

                String text1 = "Owner confirmed to me that this is the same property";
                String text2 = "Owner representative confirmed to me that this is the same property after confirming from the owner";

                switch (a) {
                    case 0:
                        cb_summery4.setEnabled(true);
                        cb_summery4.setChecked(true);

                        selected_document = ((RadioButton) rg_document.getChildAt(0)).getText().toString();

                        videoViewPreview2.setVisibility(View.GONE);

                        ((RadioButton) rg_document.getChildAt(2)).setText(text2);

                        //Owner confirmed to me that this is the same property
                        ((RadioButton) rg_document.getChildAt(0)).setText(text1 + " Capture video");
                        String textMain = ((RadioButton) rg_document.getChildAt(0)).getText().toString();
                        SpannableString ss = new SpannableString(textMain);
                        ClickableSpan clickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View textView) {

                                videoCheck = 1;

                                captureVideo("owner_confirmed_video");

                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                                ds.setColor(getResources().getColor(R.color.app_header));
                            }
                        };
                        ss.setSpan(clickableSpan, ((RadioButton) rg_document.getChildAt(0))
                                        .getText().toString().indexOf("Capture video"),
                                ((RadioButton) rg_document.getChildAt(0)).getText().toString().indexOf("Capture video") + String.valueOf("Capture video").length(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        ((RadioButton) rg_document.getChildAt(0)).setText(ss);
                        ((RadioButton) rg_document.getChildAt(0)).setMovementMethod(LinkMovementMethod.getInstance());
                        ((RadioButton) rg_document.getChildAt(0)).setHighlightColor(Color.TRANSPARENT);

                        break;

                    case 2:
                        cb_summery4.setEnabled(true);
                        cb_summery4.setChecked(true);

                        videoCheck = 2;

                        videoViewPreview1.setVisibility(View.GONE);

                        selected_document = ((RadioButton) rg_document.getChildAt(2)).getText().toString();

                        ((RadioButton) rg_document.getChildAt(0)).setText(text1);

                        //Owner confirmed to me that this is the same property
                        ((RadioButton) rg_document.getChildAt(2)).setText(text2 + " Capture video");
                        textMain = ((RadioButton) rg_document.getChildAt(2)).getText().toString();
                        ss = new SpannableString(textMain);
                        clickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View textView) {

                                captureVideo("owner_confirmed_video");

                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(false);
                                ds.setColor(getResources().getColor(R.color.app_header));
                            }
                        };
                        ss.setSpan(clickableSpan, ((RadioButton) rg_document.getChildAt(2))
                                        .getText().toString().indexOf("Capture video"),
                                ((RadioButton) rg_document.getChildAt(2)).getText().toString().indexOf("Capture video") + String.valueOf("Capture video").length(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        ((RadioButton) rg_document.getChildAt(2)).setText(ss);
                        ((RadioButton) rg_document.getChildAt(2)).setMovementMethod(LinkMovementMethod.getInstance());
                        ((RadioButton) rg_document.getChildAt(2)).setHighlightColor(Color.TRANSPARENT);


                        break;

                    case 4:
                        cb_summery4.setEnabled(false);
                        cb_summery4.setChecked(false);

                        selected_document = ((RadioButton) rg_document.getChildAt(4)).getText().toString();

                        videoViewPreview1.setVisibility(View.GONE);
                        videoViewPreview2.setVisibility(View.GONE);

                        ((RadioButton) rg_document.getChildAt(0)).setText(text1);
                        ((RadioButton) rg_document.getChildAt(2)).setText(text2);

                        break;

                    case 5:
                        cb_summery4.setEnabled(false);
                        cb_summery4.setChecked(false);

                        selected_document = ((RadioButton) rg_document.getChildAt(5)).getText().toString();

                        videoViewPreview1.setVisibility(View.GONE);
                        videoViewPreview2.setVisibility(View.GONE);

                        ((RadioButton) rg_document.getChildAt(0)).setText(text1);
                        ((RadioButton) rg_document.getChildAt(2)).setText(text2);

                        break;
                }

               /* checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    selected_document = (String) checkedRadioButton.getText();
                }*/
            }
        });


        rg_enquiry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    selected_enquiry = (String) checkedRadioButton.getText();

                    if (selected_enquiry.equals("Nearby/ neighbouring people confirmed about the property")) {
                        ll_nearby.setVisibility(View.VISIBLE);

                        cb_summery5.setEnabled(true);
                        cb_summery5.setChecked(true);
                    } else {
                        ll_nearby.setVisibility(View.GONE);

                        cb_summery5.setEnabled(false);
                        cb_summery5.setChecked(false);
                    }

                }
            }
        });

        rg_boundries.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = v.findViewById(checkedId);
                int a = group.indexOfChild(radioButton);

                llNotApplicable.setVisibility(View.GONE);

                switch (a) {
                    case 0:
                        cb_summery1.setEnabled(true);
                        cb_summery1.setChecked(true);
                        break;
                    case 4:
                        cb_summery1.setEnabled(true);
                        cb_summery1.setChecked(true);
                        llNotApplicable.setVisibility(View.VISIBLE);
                        break;
                    default:
                        cb_summery1.setChecked(false);
                        cb_summery1.setEnabled(false);
                        break;
                }

                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    selected_boundries = (String) checkedRadioButton.getText();
                }
            }
        });

        rgCoveredBuiltup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    selected_buildup = (String) checkedRadioButton.getText();
                }

                RadioButton radioButton = (RadioButton) v.findViewById(checkedId);
                int pos = group.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (pos == 1) {
                        llCoveredBuiltup2.setVisibility(View.GONE);
                    } else {
                        llCoveredBuiltup2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        spinnerLandShape.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                       @Override
                                                       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                           statusAreaMap = false;
                                                           statusAreaTitle = false;
                                                           shapeSelected = spinnerLandShape.getSelectedItem().toString();
                                                           Log.v("shapeSelected", shapeSelected);
                                                           showPop++;
                                                           if (showPop > 2) {
                                                               switch (shapeSelected) {
                                                                   case "Square": {
                                                                       rl_SideValues.setVisibility(View.GONE);
                                                                       SquareShapeImagePreview();
                                                                       etLandAreaAsSiteSurvey.setEnabled(false);
                                                                       etLandAreaAsSiteSurvey.setText("");
                                                                       ll_SideA.setVisibility(View.VISIBLE);
                                                                       ll_SideB.setVisibility(View.VISIBLE);
                                                                       ll_SideC.setVisibility(View.VISIBLE);
                                                                       ll_SideD.setVisibility(View.VISIBLE);
                                                                       ll_SideH.setVisibility(View.GONE);
//                                                                      imageSideView.setImageResource(R.mipmap.square);

                                                                       break;
                                                                   }
                                                                   case "Rectangle": {
                                                                       rl_SideValues.setVisibility(View.GONE);
                                                                       RectangleShapeImagePreview();
                                                                       etLandAreaAsSiteSurvey.setEnabled(false);
                                                                       etLandAreaAsSiteSurvey.setText("");
                                                                       ll_SideA.setVisibility(View.VISIBLE);
                                                                       ll_SideB.setVisibility(View.VISIBLE);
                                                                       ll_SideC.setVisibility(View.VISIBLE);
                                                                       ll_SideD.setVisibility(View.VISIBLE);
                                                                       ll_SideH.setVisibility(View.GONE);
//                                                                      imageSideView.setImageResource(R.mipmap.rectangle);

                                                                       break;
                                                                   }
                                                                   case "Triangle": {
                                                                       rl_SideValues.setVisibility(View.GONE);
                                                                       TriangleShapeImagePreview();
                                                                       ll_SideA.setVisibility(View.VISIBLE);
                                                                       ll_SideB.setVisibility(View.VISIBLE);
                                                                       ll_SideC.setVisibility(View.VISIBLE);
                                                                       ll_SideH.setVisibility(View.VISIBLE);
                                                                       ll_SideD.setVisibility(View.GONE);

                                                                       etLandAreaAsSiteSurvey.setEnabled(false);
                                                                       etLandAreaAsSiteSurvey.setText("");
//                                                                      imageSideView.setImageResource(R.mipmap.triangle);

                                                                       break;
                                                                   }
                                                                   case "Trapezium": {
                                                                       rl_SideValues.setVisibility(View.GONE);
                                                                       TrapeziumShapeImagePreview();
                                                                       etLandAreaAsSiteSurvey.setEnabled(false);
                                                                       etLandAreaAsSiteSurvey.setText("");

                                                                       ll_SideA.setVisibility(View.VISIBLE);
                                                                       ll_SideB.setVisibility(View.VISIBLE);
                                                                       ll_SideC.setVisibility(View.VISIBLE);
                                                                       ll_SideD.setVisibility(View.VISIBLE);
                                                                       ll_SideH.setVisibility(View.VISIBLE);
//                                                                      imageSideView.setImageResource(R.mipmap.trapezium);

                                                                       break;
                                                                   }
                                                                   case "Trapezoid": {
                                                                       rl_SideValues.setVisibility(View.GONE);
                                                                       TraperzoidShapeImagePreview();
                                                                       etLandAreaAsSiteSurvey.setEnabled(false);
                                                                       etLandAreaAsSiteSurvey.setText("");

                                                                       ll_SideA.setVisibility(View.VISIBLE);
                                                                       ll_SideB.setVisibility(View.VISIBLE);
                                                                       ll_SideC.setVisibility(View.VISIBLE);
                                                                       ll_SideD.setVisibility(View.VISIBLE);
                                                                       ll_SideH.setVisibility(View.VISIBLE);
//                                                                      imageSideView.setImageResource(R.mipmap.trapezoid);
                                                                       break;
                                                                   }
                                                                   case "Irregular": {
                                                                       rl_SideValues.setVisibility(View.GONE);
                                                                       etLandAreaAsSiteSurvey.setEnabled(true);
                                                                       etLandAreaAsSiteSurvey.setText("");
                                                                       break;
                                                                   }
                                                                   default: {
                                                                       shapeSelected = "";
                                                                       rl_SideValues.setVisibility(View.GONE);
                                                                       etLandAreaAsSiteSurvey.setEnabled(false);
                                                                       etLandAreaAsSiteSurvey.setText("");
                                                                       tvIncaseDifferenceTitle.setText("");
                                                                       tvIncaseDifferenceMap.setText("");
                                                                       tvIncaseDifferenceTitle.setVisibility(View.GONE);
                                                                       tvIncaseDifferenceMap.setVisibility(View.GONE);
                                                                       System.out.println();
                                                                   }
                                                               }
                                                           }//selected_property = String.valueOf(property_array_list.get(i));
//                                                              selected_unit3 = unit_array_list.get(i).get("symbol");

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                       }

                                                       @Override
                                                       public void onNothingSelected(AdapterView<?> adapterView) {

                                                       }
                                                   }
        );


        etLandAreaAsSiteSurvey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                calculateDifference();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rg_landarea.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();

                if (isChecked) {
                    selected_landarea = (String) checkedRadioButton.getText();
                }
                RadioButton radioButton = (RadioButton) v.findViewById(checkedId);
                int pos = group.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (pos == 1) {
                        ll_LandArea2.setVisibility(View.GONE);
                        ll_caseDifference.setVisibility(View.GONE);
                        shapeSelected = "Not Applicable";

                    } else {
                        ll_LandArea2.setVisibility(View.VISIBLE);
                        ll_caseDifference.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        if (Utils.isNetworkConnectedMainThred(mActivity)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            Hit_GetBasicInfo_Api();

            hitSendEmailOne("1");
        } else {
            Toast.makeText(mActivity, getString(R.string.noInternetConnection), Toast.LENGTH_SHORT).show();

        }
        if (!edit_page) {
            setEditiblity();
        }
        return v;
    }

    public void setConditon() {
        if (survType.equalsIgnoreCase("MultiStoried")) {
            ll_landGroundCoverage.setVisibility(View.GONE);
            ll_caseDifference.setVisibility(View.GONE);
            llLandArea.setVisibility(View.GONE);
            llCoveredAreaEast.setVisibility(View.GONE);
            llCoveredAreaWest.setVisibility(View.GONE);
            llCoveredAreaNorth.setVisibility(View.GONE);
            llCoveredAreaSouth.setVisibility(View.GONE);
            llCoveredAreaFs.setVisibility(View.GONE);
            llCoveredAreaBs.setVisibility(View.GONE);
            llCoveredAreaLs.setVisibility(View.GONE);
            llCoveredAreaRs.setVisibility(View.GONE);

        }
    }

    public void calculateDifference() {
        Double areaA, areaB;
        if (!etLandAreaAsPerTitle.getText().toString().equalsIgnoreCase("") && (!etLandAreaAsSiteSurvey.getText().toString().equalsIgnoreCase(""))) {
            areaA = Double.parseDouble(etLandAreaAsPerTitle.getText().toString());
            areaB = Double.parseDouble(etLandAreaAsSiteSurvey.getText().toString());
            titleDiffPercent = Math.abs(areaA - areaB) * 100 / areaA;
            titleDiffPercent = Math.round(titleDiffPercent * 100.0) / 100.0;
            if (titleDiffPercent > 5) {
                statusAreaTitle = true;
//                statusArea=true;

                tvIncaseDifferenceTitle.setVisibility(View.VISIBLE);
                tvIncaseDifferenceTitle.setText("Area(Title Deed) has difference of " + titleDiffPercent + " %");
//                et_reason.setText("Title");

            } else {
                tvIncaseDifferenceTitle.setVisibility(View.GONE);
                tvIncaseDifferenceTitle.setText("");
                statusAreaTitle = false;
            }
        }
        if (!etLandAreaAsPerMap.getText().toString().equalsIgnoreCase("") && (!etLandAreaAsSiteSurvey.getText().toString().equalsIgnoreCase(""))) {
            areaA = Double.parseDouble(etLandAreaAsPerMap.getText().toString());
            areaB = Double.parseDouble(etLandAreaAsSiteSurvey.getText().toString());
            mapDiffPercent = Math.abs(areaA - areaB) * 100 / areaA;
            mapDiffPercent = Math.round(mapDiffPercent * 100.0) / 100.0;
            if (mapDiffPercent > 5) {
                statusAreaMap = true;
                tvIncaseDifferenceMap.setVisibility(View.VISIBLE);
                tvIncaseDifferenceMap.setText("Area(Map) has difference of " + mapDiffPercent + " %");
//                et_reason.setText("Map");
            } else {
                tvIncaseDifferenceMap.setVisibility(View.GONE);
                tvIncaseDifferenceMap.setText("");
                statusAreaMap = false;
            }
        }

    }
    public void calculateDifferenceCovered() {
        Double areaA, areaB;
        if (!etCoveredAreaAsPerTitle.getText().toString().equalsIgnoreCase("") && (!etCoveredAreaAsSiteSurvey.getText().toString().equalsIgnoreCase(""))) {
            areaA = Double.parseDouble(etCoveredAreaAsPerTitle.getText().toString());
            areaB = Double.parseDouble(etCoveredAreaAsSiteSurvey.getText().toString());
            coveredDiffPercent = Math.abs(areaA - areaB) * 100 / areaA;
            coveredDiffPercent = Math.round(coveredDiffPercent * 100.0) / 100.0;
            if (coveredDiffPercent > 5.0) {
                statusCovAreaTitle = true;
//                statusArea=true;

                tvInCasediffCoverTitle.setVisibility(View.VISIBLE);
                tvInCasediffCoverTitle.setText("Area(Title Deed) has difference of " + coveredDiffPercent + " %");
//                et_reason.setText("Title");

            } else {
                tvInCasediffCoverTitle.setVisibility(View.GONE);
                tvInCasediffCoverTitle.setText("");
                statusCovAreaTitle = false;
            }
        }
        if (!etCoveredAreaAsPerMap.getText().toString().equalsIgnoreCase("") && (!etCoveredAreaAsSiteSurvey.getText().toString().equalsIgnoreCase(""))) {
            areaA = Double.parseDouble(etCoveredAreaAsPerMap.getText().toString());
            areaB = Double.parseDouble(etCoveredAreaAsSiteSurvey.getText().toString());
            mapCovDiffPercent = Math.abs(areaA - areaB) * 100 / areaA;
            mapCovDiffPercent = Math.round(mapCovDiffPercent * 100.0) / 100.0;
            if (mapCovDiffPercent > 5.0) {
                statusCovAreaMap = true;
                tvInCasediffCoverMap.setVisibility(View.VISIBLE);
                tvInCasediffCoverMap.setText("Area(Map) has difference of " + mapCovDiffPercent + " %");
//                et_reason.setText("Map");
            } else {
                tvInCasediffCoverMap.setVisibility(View.GONE);
                tvInCasediffCoverMap.setText("");
                statusCovAreaMap = false;
            }
        }

    }

    public void addItemsOnSpinner() {
        shapelist = new ArrayList<>();
        shapelist.add("Select a Shape");
        shapelist.add("Square");
        shapelist.add("Rectangle");
        shapelist.add("Triangle");
        shapelist.add("Trapezium");
        shapelist.add("Trapezoid");
        shapelist.add("Irregular");
//        shapelist.add("Not_Applicable");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_spinner_item, shapelist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLandShape.setAdapter(dataAdapter);
        directionsList = new ArrayList<>();
        directionsList.add("(E)  East");
        directionsList.add("(W)  West");
        directionsList.add("(N)  North");
        directionsList.add("(S)  South");
        directionsList.add("(N/E) North-East");
        directionsList.add("(S/E) South-East");
        directionsList.add("(N/W) North-West");
        directionsList.add("(S/W) South-West");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_spinner_item, directionsList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDirectA.setAdapter(dataAdapter1);
        spinnerDirectB.setAdapter(dataAdapter1);
        spinnerDirectC.setAdapter(dataAdapter1);
        spinnerDirectD.setAdapter(dataAdapter1);
        spinnerDirectH.setAdapter(dataAdapter1);


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
                unitspinnerSideA.setAdapter(new adapter_spinner(mActivity, R.layout.spinner_textview_new, unit_array_list));
                if (symbol.equalsIgnoreCase(sideAUnit)) {
                    spinVal1 = j;
                }
            }
            unitspinnerSideA.setSelection(spinVal1);
        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        //tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        ivCompass.startAnimation(ra);
        //currentDegree = -degree;
        currentDegree = -degree;

        updateTextDirection(degree);

    }

    public void setEditiblity() {
        etname.setEnabled(false);
        etName.setEnabled(false);
        etName.setClickable(false);
        etNameRep.setEnabled(false);
        etNameRep.setClickable(false);
        etContactNumber.setClickable(false);
        etContactNumber.setEnabled(false);
        etCoPersonEmail.setClickable(false);
        etCoPersonEmailRep.setClickable(false);
        etContactNumber.setClickable(false);
        etContactNumberRep.setClickable(false);
        etCoPersonRelation.setClickable(false);
        etCoPersonRelationRep.setClickable(false);
        etLesseeName.setClickable(false);
        etLesseeContactNumber.setClickable(false);
        etCoPersonEmail.setEnabled(false);
        etCoPersonEmailRep.setEnabled(false);
        etContactNumber.setEnabled(false);
        etContactNumberRep.setEnabled(false);
        etCoPersonRelation.setEnabled(false);
        etCoPersonRelationRep.setEnabled(false);
        etLesseeName.setEnabled(false);
        etLesseeContactNumber.setEnabled(false);
        etLandAreaAsSiteSurvey.setEnabled(false);
        etLandAreaAsPerMap.setEnabled(false);
        etLandAreaAsPerTitle.setEnabled(false);
        etnumber.setEnabled(false);
        et_reason.setEnabled(false);
        et_coverage.setEnabled(false);
        et_one.setEnabled(false);
        et_two.setEnabled(false);
        et_three.setEnabled(false);
        et_one2.setEnabled(false);
        et_two2.setEnabled(false);
        et_three2.setEnabled(false);
        et_one3.setEnabled(false);
        et_two3.setEnabled(false);
        et_three3.setEnabled(false);
        et_one4.setEnabled(false);
        et_two4.setEnabled(false);
        et_three4.setEnabled(false);
        etDirectionEast.setEnabled(false);
        etDirectionWest.setEnabled(false);
        etDirectionNorth.setEnabled(false);
        etDirectionSouth.setEnabled(false);
        etProceedReason.setEnabled(false);
        etCoveredAreaAsPerTitle.setEnabled(false);
        etCoveredAreaAsPerMap.setEnabled(false);
        etCoveredAreaAsSiteSurvey.setEnabled(false);
        etCoveredAreaEast.setEnabled(false);
        etCoveredAreaWest.setEnabled(false);
        etCoveredAreaNorth.setEnabled(false);
        etCoveredAreaSouth.setEnabled(false);
        etCoveredFrontsetback.setEnabled(false);
        etCoveredBackSetback.setEnabled(false);
        etCoveredLeftSideSetback.setEnabled(false);
        etCoveredRightSideSetback.setEnabled(false);
        etInCaseOfDifferenceOf2.setEnabled(false);
        etname.setClickable(false);
        etnumber.setClickable(false);
        et_reason.setClickable(false);
        et_coverage.setClickable(false);
        et_one.setClickable(false);
        et_two.setClickable(false);
        et_three.setClickable(false);
        et_one2.setClickable(false);
        et_two2.setClickable(false);
        et_three2.setClickable(false);
        et_one3.setClickable(false);
        et_two3.setClickable(false);
        et_three3.setClickable(false);
        et_one4.setClickable(false);
        et_two4.setClickable(false);
        et_three4.setClickable(false);
        etDirectionEast.setClickable(false);
        etDirectionWest.setClickable(false);
        etDirectionNorth.setClickable(false);
        etDirectionSouth.setClickable(false);
        etProceedReason.setClickable(false);
        etCoveredAreaAsPerTitle.setClickable(false);
        etCoveredAreaAsPerMap.setClickable(false);
        etCoveredAreaAsSiteSurvey.setClickable(false);
        etCoveredAreaEast.setClickable(false);
        etCoveredAreaWest.setClickable(false);
        etCoveredAreaNorth.setClickable(false);
        etCoveredAreaSouth.setClickable(false);
        etCoveredFrontsetback.setClickable(false);
        etCoveredBackSetback.setClickable(false);
        etCoveredLeftSideSetback.setClickable(false);
        etCoveredRightSideSetback.setClickable(false);
        etInCaseOfDifferenceOf2.setClickable(false);
        cbFlat.setClickable(false);
        cbVery.setClickable(false);
        cbNpa.setClickable(false);
        cbAnyOtherNotApplicable.setClickable(false);
        cbItIsAFlat.setClickable(false);
        cbPropertyWasLocked.setClickable(false);
        cbOwnerPossessee.setClickable(false);
        cbNpaProperty.setClickable(false);
        cbVeryLargeIrregular.setClickable(false);
        cbAnyOtherReason.setClickable(false);
        cbConstructionDoneWithoutMap.setClickable(false);
        cbConstructionNotAsPer.setClickable(false);
        cbExtraCovered.setClickable(false);
        cbJoinedAdjacent.setClickable(false);
        cbSetbackPortion.setClickable(false);
        cbEnroachedAdjacent.setClickable(false);
//
//        v.findViewById(R.id.cb_one).setClickable(false);
//        v.findViewById(R.id.cb_ownDoesntHaveInfo).setClickable(false);
        cbExtentConstructionWithoutMap.setClickable(false);
        cbExtentExtraCoverageWithin.setClickable(false);
        cbExtentExtraCoverageOut.setClickable(false);
        cbExtentGroundCoverage.setClickable(false);
        cbExtentMinorCondonable.setClickable(false);
        cbExtentCondonableStructural.setClickable(false);
        cbExtentNonCondonableStructural.setClickable(false);
        cb_summery1.setClickable(false);
        cb_summery2.setClickable(false);
        cb_summery3.setClickable(false);
        cb_summery4.setClickable(false);
        cb_summery5.setClickable(false);
        cb_summery6.setClickable(false);

        v.findViewById(R.id.rbOwner).setClickable(false);
        v.findViewById(R.id.rbOwnerRep).setClickable(false);
        v.findViewById(R.id.rbNoOneAvailable).setClickable(false);
        v.findViewById(R.id.rbPropertyInspected).setClickable(false);
        v.findViewById(R.id.rbvacant).setClickable(false);
        v.findViewById(R.id.rb_plate).setClickable(false);
        v.findViewById(R.id.rb_noplate).setClickable(false);
        v.findViewById(R.id.rbaNpaProperty).setClickable(false);
        v.findViewById(R.id.rb_east).setClickable(false);
        v.findViewById(R.id.rb_west).setClickable(false);
        v.findViewById(R.id.rb_Neast).setClickable(false);
        v.findViewById(R.id.rb_Nwest).setClickable(false);
        v.findViewById(R.id.rb_Seast).setClickable(false);
        v.findViewById(R.id.rb_Swest).setClickable(false);
        v.findViewById(R.id.rb_north).setClickable(false);
        v.findViewById(R.id.rb_south).setClickable(false);
        v.findViewById(R.id.rb_BoundNo).setClickable(false);
        v.findViewById(R.id.rb_BoundYes).setClickable(false);
        v.findViewById(R.id.rb_BoundNotMention).setClickable(false);
        v.findViewById(R.id.rb_NoRevPaper).setClickable(false);
        v.findViewById(R.id.rb_NotApplicable).setClickable(false);
        v.findViewById(R.id.rb_SmComplete).setClickable(false);
        v.findViewById(R.id.rb_SmfromOutside).setClickable(false);
        v.findViewById(R.id.rb_SmNoMeasurement).setClickable(false);
        v.findViewById(R.id.rb_SmRandomMeasurement).setClickable(false);
        v.findViewById(R.id.rbNotApplicable1).setClickable(false);
        v.findViewById(R.id.rbApplicable1).setClickable(false);
        v.findViewById(R.id.rbNotApplicable2).setClickable(false);
        v.findViewById(R.id.rbApplicable2).setClickable(false);
        v.findViewById(R.id.rb_ownConProp).setClickable(false);
        v.findViewById(R.id.rb_ownRepConProp).setClickable(false);
        v.findViewById(R.id.rb_ownNotConProp).setClickable(false);
        v.findViewById(R.id.rb_NoConPropShown).setClickable(false);
        v.findViewById(R.id.rb_NeighConProp).setClickable(false);
        v.findViewById(R.id.rb_tryEnqNeigh).setClickable(false);
        v.findViewById(R.id.rb_NoEnqNeigh).setClickable(false);
        v.findViewById(R.id.rb_FullSurv).setClickable(false);
        v.findViewById(R.id.rbHalfSurvey).setClickable(false);
        v.findViewById(R.id.rb_FullDetSurv).setClickable(false);
        v.findViewById(R.id.rb_PicOutsideOnly).setClickable(false);
        v.findViewById(R.id.rb_PropLocked).setClickable(false);
        v.findViewById(R.id.rb_NpaProp).setClickable(false);
        v.findViewById(R.id.rb_PossDidntallow).setClickable(false);
        v.findViewById(R.id.rb_PropIrreg).setClickable(false);
        v.findViewById(R.id.rb_PropUndCons).setClickable(false);
        v.findViewById(R.id.rb_AnyOtherReas).setClickable(false);
        v.findViewById(R.id.spinnerIsThisBeingRegularized).setClickable(false);
        v.findViewById(R.id.spinnerIsThisBeingRegularized).setEnabled(false);
        v.findViewById(R.id.spEast).setClickable(false);
        v.findViewById(R.id.spEast).setEnabled(false);
        v.findViewById(R.id.spSouth).setClickable(false);
        v.findViewById(R.id.spSouth).setEnabled(false);
        v.findViewById(R.id.spWest).setClickable(false);
        v.findViewById(R.id.spWest).setEnabled(false);
        v.findViewById(R.id.spNorth).setClickable(false);
        v.findViewById(R.id.spNorth).setEnabled(false);
        v.findViewById(R.id.spinnerIsThisBeingRegularized).setClickable(false);
        v.findViewById(R.id.spinnerIsThisBeingRegularized).setEnabled(false);

        ivRegularizationCerti.setEnabled(false);
        iv_camera1.setEnabled(false);
        iv_camera2.setEnabled(false);
        ivEast.setEnabled(false);
        ivWest.setEnabled(false);
        ivNorth.setEnabled(false);
        ivSouth.setEnabled(false);
        selfie_one.setEnabled(false);
        selfie_two.setEnabled(false);
        selfie_three.setEnabled(false);
        nameplate_one.setEnabled(false);
        nameplate_two.setEnabled(false);
        nameplate_three.setEnabled(false);
        approach_one.setEnabled(false);
        approach_two.setEnabled(false);
        approach_three.setEnabled(false);
        road_one.setEnabled(false);
        road_two.setEnabled(false);
        road_three.setEnabled(false);
        photo_one.setEnabled(false);
        photo_two.setEnabled(false);
        photo_three.setEnabled(false);
        Ephoto_one.setEnabled(false);
        Ephoto_two.setEnabled(false);
        Ephoto_three.setEnabled(false);
        Ephoto_four.setEnabled(false);
        Ephoto_five.setEnabled(false);
        Ephoto_six.setEnabled(false);
        Ephoto_seven.setEnabled(false);
        Mgate_one.setEnabled(false);
        Mgate_two.setEnabled(false);
        Mgate_three.setEnabled(false);
        Mgate_four.setEnabled(false);
        Mgate_five.setEnabled(false);
        Iphoto_one.setEnabled(false);
        Iphoto_two.setEnabled(false);
        Iphoto_three.setEnabled(false);
        Iphoto_four.setEnabled(false);
        Iphoto_five.setEnabled(false);
        ivAddMoreInterior.setEnabled(false);
//        iv1.setEnabled(false);
        ivCameraCapture1.setEnabled(false);
        ivCameraCapture2.setEnabled(false);
        ivCompass.setEnabled(false);

        ivCameraLetter.setEnabled(false);
        ivCameraLetterImage.setEnabled(false);
        ivCameraLetterRep.setEnabled(false);
        ivCameraLetterImageRep.setEnabled(false);
        ivAuthLetterRep.setEnabled(false);
        tvAddMoreInterior.setEnabled(false);
        tvAddMoreExterior.setEnabled(false);


    }

    private void updateTextDirection(double bearing) {
        int range = (int) (bearing / (360f / 16f));
        String dirTxt = "";

        if (range == 15 || range == 0)
            dirTxt = "N";
        if (range == 1 || range == 2)
            dirTxt = "NE";
        if (range == 3 || range == 4)
            dirTxt = "E";
        if (range == 5 || range == 6)
            dirTxt = "SE";
        if (range == 7 || range == 8)
            dirTxt = "S";
        if (range == 9 || range == 10)
            dirTxt = "SW";
        if (range == 11 || range == 12)
            dirTxt = "W";
        if (range == 13 || range == 14)
            dirTxt = "NW";

        tvDegree.setText("" + ((int) bearing) + ((char) 176) + " "
                + dirTxt); // char 176 ) = degrees ...
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    public void getid(View v) {
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        survType = pref.get(Constants.defAssest);
        Log.v("MyChoosenSrvy", survType);

        cbFlat = v.findViewById(R.id.cbFlat);
        cbVery = v.findViewById(R.id.cbVery);
        cbNpa = v.findViewById(R.id.cbNpa);
        cbAnyOtherNotApplicable = v.findViewById(R.id.cbAnyOtherNotApplicable);
        unitspinnerSideA = v.findViewById(R.id.unitspinnerSideA);
        tvIncaseDifferenceTitle = v.findViewById(R.id.tvInCasedifferenceTitle);
        tvInCasediffCoverMap = v.findViewById(R.id.tvInCasediffCoverMap);
        tvInCasediffCoverTitle = v.findViewById(R.id.tvInCasediffCoverTitle);
        tvIncaseDifferenceMap = v.findViewById(R.id.tvInCasedifferenceMap);
        ll_landGroundCoverage = v.findViewById(R.id.ll_landGroundCoverage);


        llCoveredAreaEast = v.findViewById(R.id.llCoveredAreaEast);
        llCoveredAreaWest = v.findViewById(R.id.llCoveredAreaWest);
        llCoveredAreaNorth = v.findViewById(R.id.llCoveredAreaNorth);
        llCoveredAreaSouth = v.findViewById(R.id.llCoveredAreaSouth);
        llCoveredAreaFs = v.findViewById(R.id.llCoveredAreaFs);
        llCoveredAreaBs = v.findViewById(R.id.llCoveredAreaBs);
        llCoveredAreaLs = v.findViewById(R.id.llCoveredAreaLs);
        llCoveredAreaRs = v.findViewById(R.id.llCoveredAreaRs);


        llLandArea = v.findViewById(R.id.llLandArea);
        landarea = pref.get(Constants.land_area_as_deed);
        maparea = pref.get(Constants.land_area_as_map);
        tv_SpinnerSideB = v.findViewById(R.id.tv_spinnerSideB);
        tv_SpinnerSideC = v.findViewById(R.id.tv_spinnerSideC);
        tv_SpinnerSideD = v.findViewById(R.id.tv_spinnerSideD);
        tv_SpinnerSideH = v.findViewById(R.id.tv_spinnerSideH);
        ll_LandArea2 = v.findViewById(R.id.ll_LandArea2);
        etLandAreaAsSiteSurvey = v.findViewById(R.id.etLandAreaAsSiteSurvey);
        etLandAreaAsPerMap = v.findViewById(R.id.etLandAreaAsPerMap);
        etLandAreaAsPerTitle = v.findViewById(R.id.etLandAreaAsPerTitle);
        etRemarksNotApplicable = v.findViewById(R.id.etRemarksNotApplicable);
        llNotApplicable = v.findViewById(R.id.llNotApplicable);
        location = new GetLocation(mActivity);
        rl_SideValues = v.findViewById(R.id.rl_SideValues);
        ll_SideA = v.findViewById(R.id.ll_SideA);
        ll_SideB = v.findViewById(R.id.ll_SideB);
        ll_SideC = v.findViewById(R.id.ll_SideC);
        ll_SideD = v.findViewById(R.id.ll_SideD);
        ll_SideH = v.findViewById(R.id.ll_SideH);
        scrollView = v.findViewById(R.id.scrollView);
        spinnerDirectA = v.findViewById(R.id.spinnerDirectA);
        spinnerDirectB = v.findViewById(R.id.spinnerDirectB);
        spinnerDirectC = v.findViewById(R.id.spinnerDirectC);
        spinnerDirectD = v.findViewById(R.id.spinnerDirectD);
        spinnerDirectH = v.findViewById(R.id.spinnerDirectH);


        videoViewPreview1 = v.findViewById(R.id.videoViewPreview1);
        videoViewPreview2 = v.findViewById(R.id.videoViewPreview2);

        MediaController mediaController = new MediaController(mActivity);

        videoViewPreview1.setMediaController(mediaController);
        videoViewPreview2.setMediaController(mediaController);
        mediaController.setAnchorView(videoViewPreview1);
        mediaController.setAnchorView(videoViewPreview2);

        MySupportMapFragment mSupportMapFragment;
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

        ivCameraCapture1 = v.findViewById(R.id.ivCameraCapture1);
        ivCameraCapture2 = v.findViewById(R.id.ivCameraCapture2);
        ivCompass = v.findViewById(R.id.ivCompass);

        tvViewAuthLetter = v.findViewById(R.id.tvViewAuthLetter);

        flowLayout = v.findViewById(R.id.flowLayout);
        flowExterLayout = v.findViewById(R.id.flowExterLayout);

        tvDegree = v.findViewById(R.id.tvDegree);
        tvQuesE = v.findViewById(R.id.tvQuesE);
        tvQuesJ = v.findViewById(R.id.tvQuesJ);


        SpannableStringBuilder ssBuilder = null;
        String quesE = "e. Check the identification from schedule of the property mentioned in the legal document";
        String text = "(Take photographs of each side and mark each side in the system in the Table below)";

        tvQuesE.setText(quesE + " " + text);

        ssBuilder = new SpannableStringBuilder(tvQuesE.getText().toString());

        ssBuilder.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.app_header)),
                tvQuesE.getText().toString().indexOf(text),
                tvQuesE.getText().toString().indexOf(text) + String.valueOf(text).length(),
                0);

        ssBuilder.setSpan(
                new StyleSpan(ITALIC),
                tvQuesE.getText().toString().indexOf(text),
                tvQuesE.getText().toString().indexOf(text) + String.valueOf(text).length(),
                0);

        ssBuilder.setSpan(new RelativeSizeSpan(.8f),
                tvQuesE.getText().toString().indexOf(text),
                tvQuesE.getText().toString().indexOf(text) + String.valueOf(text).length(), 0); // set size

        tvQuesE.setText(ssBuilder);
        tvQuesE.setMovementMethod(LinkMovementMethod.getInstance());

        ssBuilder = null;
        String quesJ = "j. Google Map Location";
        String text2 = "(Pin at center of the property or at main gate)";

        tvQuesJ.setText(quesJ + " " + text2);

        ssBuilder = new SpannableStringBuilder(tvQuesJ.getText().toString());
        ssBuilder.setSpan(new RelativeSizeSpan(.8f),
                tvQuesJ.getText().toString().indexOf(text2),
                tvQuesJ.getText().toString().indexOf(text2) + String.valueOf(text2).length(), 0); // set size

        ssBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)),
                tvQuesJ.getText().toString().indexOf(text2),
                tvQuesJ.getText().toString().indexOf(text2) + String.valueOf(text2).length(), 0); // set size

        tvQuesJ.setText(ssBuilder);
        tvQuesJ.setMovementMethod(LinkMovementMethod.getInstance());

        mSensorManager = (SensorManager) mActivity.getSystemService(SENSOR_SERVICE);

        tvPhotographsHeading = v.findViewById(R.id.tvPhotographsHeading);

//        tv_header = v.findViewById(R.id.tv_header);
//        rl_casedetail = v.findViewById(R.id.rl_casedetail);
//        tv_caseheader = v.findViewById(R.id.tv_caseheader);
//        tv_caseid = v.findViewById(R.id.tv_caseid);

        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        rg_direction = v.findViewById(R.id.rg_direction);
        rg_boundries = v.findViewById(R.id.rg_boundries);
        rg_measurement = v.findViewById(R.id.rgPropertyMeasurement);
        rg_landarea = v.findViewById(R.id.rgLandArea);
        rgCoveredBuiltup = v.findViewById(R.id.rgCoveredBuiltup);
        rg_document = v.findViewById(R.id.rg_document);
        et_reason = v.findViewById(R.id.etInCaseOfDifferenceOf);
        et_coverage = v.findViewById(R.id.etGroundCoverage);
        // rl_dots = v.findViewById(R.id.rl_dots);
        rg_enquiry = v.findViewById(R.id.rg_enquiry);
        ll_nearby = v.findViewById(R.id.ll_nearby);
        tv_compass = v.findViewById(R.id.tv_compass);
        footer = v.findViewById(R.id.footer);
        tvNext = v.findViewById(R.id.tvNext);
        ll_identifyProperty = v.findViewById(R.id.ll_identifyProperty);
        ll_displayed = v.findViewById(R.id.ll_platedisplayed);
        ll_notdisplayed = v.findViewById(R.id.ll_platenotdisplayed);
        rgNamePlate = v.findViewById(R.id.rgNamePlate);
        rb_plate = v.findViewById(R.id.rbOwner);
        rb_noplate = v.findViewById(R.id.rb_noplate);
        camera1 = v.findViewById(R.id.tv_camera1);
        camera2 = v.findViewById(R.id.tv_camera2);
        iv_camera1 = v.findViewById(R.id.iv_camera1);
        iv_camera2 = v.findViewById(R.id.iv_camera2);
        tv_camera1 = v.findViewById(R.id.tv_camera1);
        tv_camera2 = v.findViewById(R.id.tv_camera2);

        ivEast = v.findViewById(R.id.ivEast);
        ivWest = v.findViewById(R.id.ivWest);
        ivNorth = v.findViewById(R.id.ivNorth);
        ivSouth = v.findViewById(R.id.ivSouth);

        et_one = v.findViewById(R.id.et_one);
        et_two = v.findViewById(R.id.et_two);
        et_three = v.findViewById(R.id.et_three);

        et_one2 = v.findViewById(R.id.et_one2);
        et_two2 = v.findViewById(R.id.et_two2);
        et_three2 = v.findViewById(R.id.et_three2);

        et_one3 = v.findViewById(R.id.et_one3);
        et_two3 = v.findViewById(R.id.et_two3);
        et_three3 = v.findViewById(R.id.et_three3);

        et_one4 = v.findViewById(R.id.et_one4);
        et_two4 = v.findViewById(R.id.et_two4);
        et_three4 = v.findViewById(R.id.et_three4);

        etDirectionEast = v.findViewById(R.id.etDirectionEast);
        etDirectionWest = v.findViewById(R.id.etDirectionWest);
        etDirectionNorth = v.findViewById(R.id.etDirectionNorth);
        etDirectionSouth = v.findViewById(R.id.etDirectionSouth);

        etProceedReason = v.findViewById(R.id.etProceedReason);
        tvSendReasonMail = v.findViewById(R.id.tvSendReasonMail);

        rl_east = v.findViewById(R.id.rl_east);
        rl_west = v.findViewById(R.id.rl_west);
        rl_north = v.findViewById(R.id.rl_north);
        rl_south = v.findViewById(R.id.rl_south);

        ivRegularizationCerti = v.findViewById(R.id.ivRegularizationCerti);
        tvRegularizationCerti = v.findViewById(R.id.tvRegularizationCerti);

        iv1 = v.findViewById(R.id.iv1);

        selfie_one = v.findViewById(R.id.selfie_one);
        selfie_two = v.findViewById(R.id.selfie_two);
        selfie_three = v.findViewById(R.id.selfie_three);
        nameplate_one = v.findViewById(R.id.nameplate_one);
        nameplate_two = v.findViewById(R.id.nameplate_two);
        nameplate_three = v.findViewById(R.id.nameplate_three);
        approach_one = v.findViewById(R.id.approach_one);
        approach_two = v.findViewById(R.id.approach_two);
        approach_three = v.findViewById(R.id.approach_three);
        road_one = v.findViewById(R.id.road_one);
        road_two = v.findViewById(R.id.road_two);
        road_three = v.findViewById(R.id.road_three);
        photo_one = v.findViewById(R.id.photo_one);
        photo_two = v.findViewById(R.id.photo_two);
        photo_three = v.findViewById(R.id.photo_three);
        Ephoto_one = v.findViewById(R.id.Ephoto_one);
        Ephoto_two = v.findViewById(R.id.Ephoto_two);
        Ephoto_three = v.findViewById(R.id.Ephoto_three);
        Ephoto_four = v.findViewById(R.id.Ephoto_four);
        Ephoto_five = v.findViewById(R.id.Ephoto_five);
        Ephoto_six = v.findViewById(R.id.Ephoto_six);
        Ephoto_seven = v.findViewById(R.id.Ephoto_seven);
        Mgate_one = v.findViewById(R.id.Mgate_one);
        Mgate_two = v.findViewById(R.id.Mgate_two);
        Mgate_three = v.findViewById(R.id.Mgate_three);
        Mgate_four = v.findViewById(R.id.Mgate_four);
        Mgate_four = v.findViewById(R.id.Mgate_four);
        Mgate_five = v.findViewById(R.id.Mgate_five);
        Iphoto_one = v.findViewById(R.id.Iphoto_one);
        Iphoto_two = v.findViewById(R.id.Iphoto_two);
        Iphoto_three = v.findViewById(R.id.Iphoto_three);
        Iphoto_four = v.findViewById(R.id.Iphoto_four);
        Iphoto_five = v.findViewById(R.id.Iphoto_five);
        ivAddMoreInterior = v.findViewById(R.id.ivAddMoreInterior);


        tvAddMoreInterior = v.findViewById(R.id.tvAddMoreInterior);
        tvAddMoreExterior = v.findViewById(R.id.tvAddMoreExterior);

        ll_selfieone = v.findViewById(R.id.ll_selfieone);
        ll_selfietwo = v.findViewById(R.id.ll_selfietwo);
        ll_selfiethree = v.findViewById(R.id.ll_selfiethree);

        ll_plateone = v.findViewById(R.id.ll_plateone);
        ll_platetwo = v.findViewById(R.id.ll_platetwo);
        ll_platethree = v.findViewById(R.id.ll_platethree);

        ll_approachone = v.findViewById(R.id.ll_approachone);
        ll_approachtwo = v.findViewById(R.id.ll_approachtwo);
        ll_approachthree = v.findViewById(R.id.ll_approachthree);

        ll_roadone = v.findViewById(R.id.ll_roadone);
        ll_roadtwo = v.findViewById(R.id.ll_roadtwo);
        ll_roadthree = v.findViewById(R.id.ll_roadthree);

        ll_photoone = v.findViewById(R.id.ll_photoone);
        ll_phototwo = v.findViewById(R.id.ll_phototwo);
        ll_photothree = v.findViewById(R.id.ll_photothree);

        ll_Ephotoone = v.findViewById(R.id.ll_Ephotoone);
        ll_Ephototwo = v.findViewById(R.id.ll_Ephototwo);
        ll_Ephotothree = v.findViewById(R.id.ll_Ephotothree);
        ll_Ephotofour = v.findViewById(R.id.ll_Ephotofour);
        ll_Ephotofive = v.findViewById(R.id.ll_Ephotofive);
        ll_Ephotosix = v.findViewById(R.id.ll_Ephotosix);
        ll_Ephotoseven = v.findViewById(R.id.ll_Ephotoseven);

        ll_Mgateone = v.findViewById(R.id.ll_Mgateone);
        ll_Mgatetwo = v.findViewById(R.id.ll_Mgatetwo);
        ll_Mgatethree = v.findViewById(R.id.ll_Mgatethree);
        ll_Mgatefour = v.findViewById(R.id.ll_Mgatefour);
        ll_Mgatefive = v.findViewById(R.id.ll_Mgatefive);

        ll_Iphotoone = v.findViewById(R.id.ll_Iphotoone);
        ll_Iphototwo = v.findViewById(R.id.ll_Iphototwo);
        ll_Iphotothree = v.findViewById(R.id.ll_Iphotothree);
        ll_Iphotofour = v.findViewById(R.id.ll_Iphotofour);
        ll_Iphotofive = v.findViewById(R.id.ll_Iphotofive);

        spEast = v.findViewById(R.id.spEast);
        spWest = v.findViewById(R.id.spWest);
        spNorth = v.findViewById(R.id.spNorth);
        spSouth = v.findViewById(R.id.spSouth);

        spinnerIsThisBeingRegularized = v.findViewById(R.id.spinnerIsThisBeingRegularized);
        llRegularizationCerti = v.findViewById(R.id.llRegularizationCerti);

        llReasonForHalf = v.findViewById(R.id.llReasonForHalf);

        //rl_back = v.findViewById(R.id.rl_back);
        tv_lenghtSideA = v.findViewById(R.id.tv_lenghtSideA);
        tv_lenghtSideB = v.findViewById(R.id.tv_lenghtSideB);
        tv_lenghtSideC = v.findViewById(R.id.tv_lenghtSideC);
        tv_lenghtSideD = v.findViewById(R.id.tv_lenghtSideD);
        tv_lenghtSideH = v.findViewById(R.id.tv_lenghtSideH);

        etCoveredAreaAsPerTitle = v.findViewById(R.id.etCoveredAreaAsPerTitle);
        etCoveredAreaAsPerMap = v.findViewById(R.id.etCoveredAreaAsPerMap);
        etCoveredAreaAsSiteSurvey = v.findViewById(R.id.etCoveredAreaAsSiteSurvey);
        etCoveredAreaEast = v.findViewById(R.id.etCoveredAreaEast);
        etCoveredAreaWest = v.findViewById(R.id.etCoveredAreaWest);
        etCoveredAreaNorth = v.findViewById(R.id.etCoveredAreaNorth);
        etCoveredAreaSouth = v.findViewById(R.id.etCoveredAreaSouth);
        etCoveredFrontsetback = v.findViewById(R.id.etCoveredFrontsetback);
        etCoveredBackSetback = v.findViewById(R.id.etCoveredBackSetback);
        etCoveredRightSideSetback = v.findViewById(R.id.etCoveredRightSideSetback);
        etCoveredLeftSideSetback = v.findViewById(R.id.etCoveredLeftSideSetback);
        etInCaseOfDifferenceOf2 = v.findViewById(R.id.etInCaseOfDifferenceOf2);
        etGroundCoverage = v.findViewById(R.id.etGroundCoverage);
        etAnyOtherReason = v.findViewById(R.id.etAnyOtherReason);

        cbItIsAFlat = v.findViewById(R.id.cbItIsAFlat);
        cbPropertyWasLocked = v.findViewById(R.id.cbPropertyWasLocked);
        cbOwnerPossessee = v.findViewById(R.id.cbOwnerPossessee);
        cbNpaProperty = v.findViewById(R.id.cbNpaProperty);
        cbVeryLargeIrregular = v.findViewById(R.id.cbVeryLargeIrregular);
        cbAnyOtherReason = v.findViewById(R.id.cbAnyOtherReason);

        cbConstructionDoneWithoutMap = v.findViewById(R.id.cbConstructionDoneWithoutMap);
        cbConstructionNotAsPer = v.findViewById(R.id.cbConstructionNotAsPer);
        cbExtraCovered = v.findViewById(R.id.cbExtraCovered);
        cbJoinedAdjacent = v.findViewById(R.id.cbJoinedAdjacent);
        cbSetbackPortion = v.findViewById(R.id.cbSetbackPortion);
        cbEnroachedAdjacent = v.findViewById(R.id.cbEnroachedAdjacent);

        cbExtentConstructionWithoutMap = v.findViewById(R.id.cbExtentConstructionWithoutMap);
        cbExtentExtraCoverageWithin = v.findViewById(R.id.cbExtentExtraCoverageWithin);
        cbExtentExtraCoverageOut = v.findViewById(R.id.cbExtentExtraCoverageOut);
        cbExtentGroundCoverage = v.findViewById(R.id.cbExtentGroundCoverage);
        cbExtentMinorCondonable = v.findViewById(R.id.cbExtentMinorCondonable);
        cbExtentCondonableStructural = v.findViewById(R.id.cbExtentCondonableStructural);
        cbExtentNonCondonableStructural = v.findViewById(R.id.cbExtentNonCondonableStructural);

        cb_summery1 = v.findViewById(R.id.cb_summery1);
        cb_summery2 = v.findViewById(R.id.cb_summery2);
        cb_summery3 = v.findViewById(R.id.cb_summery3);
        cb_summery4 = v.findViewById(R.id.cb_summery4);
        cb_summery5 = v.findViewById(R.id.cb_summery5);
        cb_summery6 = v.findViewById(R.id.cb_summery6);
        cbStillProceed = v.findViewById(R.id.cbStillProceed);
        //    cb_summery7 = v.findViewById(R.id.cb_summery7);

        cb_summery1.setEnabled(false);
        cb_summery2.setEnabled(false);
        cb_summery4.setEnabled(false);
        cb_summery5.setEnabled(false);


//        cb_reson1 = v.findViewById(R.id.cb_reson1);
//        cb_reson2 = v.findViewById(R.id.cb_reson2);
//        cb_reson3 = v.findViewById(R.id.cb_reson3);
//        cb_reson4 = v.findViewById(R.id.cb_reson4);
//        cb_reson5 = v.findViewById(R.id.cb_reson5);

        rgSurveyType = v.findViewById(R.id.rgSurveyType);
        rgReasonForHalfSurvey = v.findViewById(R.id.rgReasonForHalfSurvey);
//        cb_surveytype1 = v.findViewById(R.id.cb_surveytype1);
//        cb_surveytype2 = v.findViewById(R.id.cb_surveytype2);
//        cb_surveytype3 = v.findViewById(R.id.cb_surveytype3);
//        cb_surveytype4 = v.findViewById(R.id.cb_surveytype4);


        etname = v.findViewById(R.id.etname);
        etnumber = v.findViewById(R.id.etnumber);

        rgPropertyShownBy = v.findViewById(R.id.rgPropertyShownBy);

        //LinearLayout
        ll_caseDifference = v.findViewById(R.id.ll_caseDifference);
        llPropertyContact = v.findViewById(R.id.llPropertyContact);
        llPropertyCoPersonEmail = v.findViewById(R.id.llPropertyCoPersonEmail);
        llPropertyCoPersonRelation = v.findViewById(R.id.llPropertyCoPersonRelation);
        llPropertyName = v.findViewById(R.id.llPropertyName);
        llLesseeName = v.findViewById(R.id.llLesseeName);
        llLesseeContact = v.findViewById(R.id.llLesseeContact);
        llPropertyImage = v.findViewById(R.id.llPropertyImage);
        llPropertyContactRep = v.findViewById(R.id.llPropertyContactRep);
        llPropertyNameRep = v.findViewById(R.id.llPropertyNameRep);
        llPropertyCoPersonEmailRep = v.findViewById(R.id.llPropertyCoPersonEmailRep);
        llPropertyCoPersonRelationRep = v.findViewById(R.id.llPropertyCoPersonRelationRep);
        llPropertyAuthLetterRep = v.findViewById(R.id.llPropertyAuthLetterRep);
        llPropertyImageRep = v.findViewById(R.id.llPropertyImageRep);

        llReasonNoMeasurement = v.findViewById(R.id.llReasonNoMeasurement);
        llCoveredBuiltup2 = v.findViewById(R.id.llCoveredBuiltup2);

        etName = v.findViewById(R.id.etName);
        etCoPersonRelation = v.findViewById(R.id.etCoPersonRelation);
        etCoPersonEmail = v.findViewById(R.id.etCoPersonEmail);
        etContactNumber = v.findViewById(R.id.etContactNumber);
        etNameRep = v.findViewById(R.id.etNameRep);
        etContactNumberRep = v.findViewById(R.id.etContactNumberRep);
        etCoPersonRelationRep = v.findViewById(R.id.etCoPersonRelationRep);
        etCoPersonEmailRep = v.findViewById(R.id.etCoPersonEmailRep);
        etLesseeName = v.findViewById(R.id.etLesseeName);
        etLesseeContactNumber = v.findViewById(R.id.etLesseeContactNumber);

        tvLetterImage = v.findViewById(R.id.tvLetterImage);
        tvLetterImageRep = v.findViewById(R.id.tvLetterImageRep);
        tvAsPer = v.findViewById(R.id.tvAsPer);
        spinnerLandShape = v.findViewById(R.id.spinnerLandShape);

        tvReasonFor = v.findViewById(R.id.tvReasonFor);
        tvLandArea = v.findViewById(R.id.tvLandArea);
        tvInCase1 = v.findViewById(R.id.tvInCase1);
        tvGroundCoverage = v.findViewById(R.id.tvGroundCoverage);
        tvCovered = v.findViewById(R.id.tvCovered);

        etAnyOtherReasonMeasurement = v.findViewById(R.id.etAnyOtherReasonMeasurement);

        ivCameraLetter = v.findViewById(R.id.ivCameraLetter);
        ivCameraLetterImage = v.findViewById(R.id.ivCameraLetterImage);
        ivCameraLetterRep = v.findViewById(R.id.ivCameraLetterRep);
        ivCameraLetterImageRep = v.findViewById(R.id.ivCameraLetterImageRep);
        ivAuthLetterRep = v.findViewById(R.id.ivAuthLetterRep);


        ////////////
      /*  {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.inflate_add_photographs, null);
            // ViewGroup main = (ViewGroup) findViewById(R.id.flowLayout);

            final LinearLayout llPhoto;
            ImageView ivAdd;

            llPhoto = view.findViewById(R.id.llPhoto);

            ivAdd = view.findViewById(R.id.ivAdd);

            ivAdd.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    //  BitmapDrawable background = new BitmapDrawable(b);
                    //  llPhoto.setBackgroundColor(mActivity.getColor(R.color.accept));

                    //    referLayout = llPhoto.getId();

                  //  llTemp = llPhoto.findViewById(llPhoto.getId());

                 //   captureImage(llPhoto);

                    setLayout();
                }
            });

            flowLayout.addView(view);

        }*/

        String result = "";
        if (!pref.get(Constants.documentYes).isEmpty()) {
            result = "";
            String str = pref.get(Constants.documentYes);
            if (str.contains("1")) {
                result = getString(R.string.title_deed);
            }
            if (str.contains("2")) {
                if (result.isEmpty()) {
                    result = getString(R.string.sale_deed);
                } else {
                    result = result + ", " + getString(R.string.sale_deed);
                }
            }
            if (str.contains("3")) {
                if (result.isEmpty()) {
                    result = getString(R.string.conveyance_deed);
                } else {
                    result = result + ", " + getString(R.string.conveyance_deed);
                }
            }
            if (str.contains("4")) {
                if (result.isEmpty()) {
                    result = getString(R.string.possession_certificate);
                } else {
                    result = result + ", " + getString(R.string.possession_certificate);
                }
            }
            if (str.contains("5")) {
                if (result.isEmpty()) {
                    result = getString(R.string.cizra_map);
                } else {
                    result = result + ", " + getString(R.string.cizra_map);
                }
            }
            if (str.contains("6")) {
                if (result.isEmpty()) {
                    result = getString(R.string.approved_building_map);
                } else {
                    result = result + ", " + getString(R.string.approved_building_map);
                }
            }
            if (str.contains("7")) {

                if (result.isEmpty()) {
                    result = getString(R.string.any_other_document);
                } else {
                    result = result + ", " + getString(R.string.any_other_document);
                }
            }

            tvAsPer.setText("As per " + result);
            et_one.setEnabled(true);
            et_one2.setEnabled(true);
            et_one3.setEnabled(true);
            et_one4.setEnabled(true);
            et_one.setText(pref.get(Constants.directionOne));
            et_one2.setText(pref.get(Constants.directionTwo));
            et_one3.setText(pref.get(Constants.directionThree));
            et_one4.setText(pref.get(Constants.directionFour));
        } else if (!pref.get(Constants.documentNA).isEmpty()) {
            result = "";
            String str = pref.get(Constants.documentNA);
            if (str.contains("1")) {
                result = getString(R.string.flat_in_multi_storied_building);
            }
            if (str.contains("2")) {
                if (result.isEmpty()) {
                    result = getString(R.string.very_large_land_comprising_of_multiple_land_parcels);
                } else {
                    result = result + ", " + getString(R.string.very_large_land_comprising_of_multiple_land_parcels);
                }
            }
            if (str.contains("3")) {
                if (result.isEmpty()) {
                    result = getString(R.string.npa_property_so_no_more_details_can_be_provided);
                } else {
                    result = result + ", " + getString(R.string.npa_property_so_no_more_details_can_be_provided);
                }
            }
            if (str.contains("4")) {
                if (result.isEmpty()) {
                    result = getString(R.string.any_other_reason);
                } else {
                    result = result + ", " + getString(R.string.any_other_reason);
                }
            }

            tvAsPer.setText("N/A as " + result);
            et_one.setEnabled(false);
            et_one2.setEnabled(false);
            et_one3.setEnabled(false);
            et_one4.setEnabled(false);
            et_one.setText("Not Applicable");
            et_one2.setText("Not Applicable");
            et_one3.setText("Not Applicable");
            et_one4.setText("Not Applicable");

            ((RadioButton) rg_boundries.getChildAt(4)).setChecked(true);

            llNotApplicable.setVisibility(View.VISIBLE);

            String check1_val = pref.get(Constants.documentNA);
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
                //etRemarksNotApplicable.setText(jsonObject.getString("check2_remark"));
            }
        } else if (!pref.get(Constants.documentNoBoun).isEmpty()) {
            ((RadioButton) rg_boundries.getChildAt(3)).setChecked(true);

            tvAsPer.setText(getString(R.string.no_boundary_schedule));

            et_one.setEnabled(false);
            et_one2.setEnabled(false);
            et_one3.setEnabled(false);
            et_one4.setEnabled(false);
            et_one.setText("No Boundaries");
            et_one2.setText("No Boundaries");
            et_one3.setText("No Boundaries");
            et_one4.setText("No Boundaries");
        }

        ((Dashboard) mActivity).setUserInteractionListener(new Dashboard.UserInteractionListener() {
            @Override
            public void onUserInteraction() {

                // Log.v("asfasfasf","asfasfasfcvdeeee");
                firstTime = false;

                checkSummery = 1;

                // Do whatever you want here, during user interaction
            }
        });

    }

    public void setListener() {

        footer.setOnClickListener(this);
        //tvNext.setOnClickListener(this);
        tvSendReasonMail.setOnClickListener(this);
        iv_camera1.setOnClickListener(this);
        iv_camera2.setOnClickListener(this);
        ivRegularizationCerti.setOnClickListener(this);
        tv_compass.setOnClickListener(this);
        tvViewAuthLetter.setOnClickListener(this);

        ivEast.setOnClickListener(this);
        ivWest.setOnClickListener(this);
        ivNorth.setOnClickListener(this);
        ivSouth.setOnClickListener(this);

        selfie_one.setOnClickListener(this);
        selfie_two.setOnClickListener(this);
        selfie_three.setOnClickListener(this);
        ll_selfieone.setOnClickListener(this);
        ll_selfietwo.setOnClickListener(this);
        ll_selfiethree.setOnClickListener(this);

        Ephoto_one.setOnClickListener(this);
        Ephoto_two.setOnClickListener(this);
        Ephoto_three.setOnClickListener(this);
        Ephoto_four.setOnClickListener(this);
        Ephoto_five.setOnClickListener(this);
        Ephoto_six.setOnClickListener(this);
        Ephoto_seven.setOnClickListener(this);
        ll_Ephotoone.setOnClickListener(this);
        ll_Ephototwo.setOnClickListener(this);
        ll_Ephotothree.setOnClickListener(this);
        ll_Ephotofour.setOnClickListener(this);
        ll_Ephotofive.setOnClickListener(this);
        ll_Ephotosix.setOnClickListener(this);
        ll_Ephotoseven.setOnClickListener(this);

        Mgate_one.setOnClickListener(this);
        Mgate_two.setOnClickListener(this);
        Mgate_three.setOnClickListener(this);
        Mgate_four.setOnClickListener(this);
        Mgate_five.setOnClickListener(this);
        ll_Mgateone.setOnClickListener(this);
        ll_Mgatetwo.setOnClickListener(this);
        ll_Mgatethree.setOnClickListener(this);
        ll_Mgatefour.setOnClickListener(this);
        ll_Mgatefive.setOnClickListener(this);

        Iphoto_one.setOnClickListener(this);
        Iphoto_two.setOnClickListener(this);
        Iphoto_three.setOnClickListener(this);
        Iphoto_four.setOnClickListener(this);
        Iphoto_five.setOnClickListener(this);
        ll_Iphotoone.setOnClickListener(this);
        ll_Iphototwo.setOnClickListener(this);
        ll_Iphotothree.setOnClickListener(this);
        ll_Iphotofour.setOnClickListener(this);
        ll_Iphotofive.setOnClickListener(this);
        ivAddMoreInterior.setOnClickListener(this);
        tvAddMoreInterior.setOnClickListener(this);
        tvAddMoreExterior.setOnClickListener(this);

        nameplate_one.setOnClickListener(this);
        nameplate_two.setOnClickListener(this);
        nameplate_three.setOnClickListener(this);
        ll_plateone.setOnClickListener(this);
        ll_platetwo.setOnClickListener(this);
        ll_platethree.setOnClickListener(this);

        approach_one.setOnClickListener(this);
        approach_two.setOnClickListener(this);
        approach_three.setOnClickListener(this);
        ll_approachone.setOnClickListener(this);
        ll_approachtwo.setOnClickListener(this);
        ll_approachthree.setOnClickListener(this);

        road_one.setOnClickListener(this);
        road_two.setOnClickListener(this);
        road_three.setOnClickListener(this);
        ll_roadone.setOnClickListener(this);
        ll_roadtwo.setOnClickListener(this);
        ll_roadthree.setOnClickListener(this);

        photo_one.setOnClickListener(this);
        photo_two.setOnClickListener(this);
        photo_three.setOnClickListener(this);
        ll_photoone.setOnClickListener(this);
        ll_phototwo.setOnClickListener(this);
        ll_photothree.setOnClickListener(this);

        //   rl_back.setOnClickListener(this);
        ivCameraLetter.setOnClickListener(this);
        ivAuthLetterRep.setOnClickListener(this);
        ivCameraLetterImage.setOnClickListener(this);
        ivCameraLetterRep.setOnClickListener(this);
        ivCameraLetterImageRep.setOnClickListener(this);

        rgSurveyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);
                if (typeSurveyChecker.equalsIgnoreCase("first")) {
                    typeSurveyChecker = "false";
                    typeSurveyChecker8 = "false";

                } else {
                    typeSurveyChecker = "true";
                    typeSurveyChecker8 = "true";
                }
                Log.v("typeSurveyChecker", typeSurveyChecker);
                switch (a) {
                    case 0:
                        llReasonForHalf.setVisibility(View.GONE);
                        llReasonForHalf.setVisibility(View.GONE);
                        tvPhotographsHeading.setText("C. Photographs of the Property");

                        pref.set(Constants.surveyTypeCheck, "0");
                        pref.commit();

                        surveyTypeCheck = 0;

                        break;
                    case 1:
                        llReasonForHalf.setVisibility(View.GONE);
                        llReasonForHalf.setVisibility(View.GONE);
                        tvPhotographsHeading.setText("C. Photographs of the Property");

                        pref.set(Constants.surveyTypeCheck, "0");
                        pref.commit();

                        surveyTypeCheck = 0;

                        break;
                    case 2:
                        llReasonForHalf.setVisibility(View.VISIBLE);
                        llReasonForHalf.setVisibility(View.VISIBLE);
                        tvPhotographsHeading.setText("D. Photographs of the Property");

                        pref.set(Constants.surveyTypeCheck, "1");
                        pref.commit();

                        surveyTypeCheck = 1;

                        break;
                    case 3:
                        llReasonForHalf.setVisibility(View.VISIBLE);
                        llReasonForHalf.setVisibility(View.VISIBLE);
                        tvPhotographsHeading.setText("D. Photographs of the Property");

                        pref.set(Constants.surveyTypeCheck, "1");
                        pref.commit();

                        surveyTypeCheck = 1;

                        break;
                }
            }
        });

        ((RadioButton) rgReasonForHalfSurvey.getChildAt(5)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    etAnyOtherReason.setVisibility(View.VISIBLE);
                } else {
                    etAnyOtherReason.setVisibility(View.GONE);
                }
            }
        });

        rgPropertyShownBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);

                switch (a) {
                    case 0:
                        llPropertyName.setVisibility(View.VISIBLE);
                        llPropertyContact.setVisibility(View.VISIBLE);
                        llPropertyCoPersonEmail.setVisibility(View.VISIBLE);
                        llPropertyCoPersonRelation.setVisibility(View.VISIBLE);
                        llPropertyImage.setVisibility(View.VISIBLE);

                        llPropertyNameRep.setVisibility(View.GONE);
                        llPropertyContactRep.setVisibility(View.GONE);
                        llPropertyCoPersonEmailRep.setVisibility(View.GONE);
                        llPropertyCoPersonRelationRep.setVisibility(View.GONE);
                        llPropertyAuthLetterRep.setVisibility(View.GONE);
                        llPropertyImageRep.setVisibility(View.GONE);

                        llLesseeContact.setVisibility(View.GONE);
                        llLesseeName.setVisibility(View.GONE);

                        if (checkSummery != 0) {
                            ((RadioButton) rg_document.getChildAt(0)).setChecked(true);
                        }

                        sitePresence = "1";

                        pref.set(Constants.sitePresence, "1");
                        pref.commit();

                        break;
                    case 1:
                        llPropertyNameRep.setVisibility(View.VISIBLE);
                        llPropertyContactRep.setVisibility(View.VISIBLE);
                        llPropertyCoPersonEmailRep.setVisibility(View.VISIBLE);
                        llPropertyCoPersonRelationRep.setVisibility(View.VISIBLE);
                        llPropertyImageRep.setVisibility(View.VISIBLE);

                        //llPropertyAuthLetterRep.setVisibility(View.VISIBLE);
                        llPropertyAuthLetterRep.setVisibility(View.GONE);

                        llPropertyName.setVisibility(View.GONE);
                        llPropertyContact.setVisibility(View.GONE);
                        llPropertyCoPersonEmail.setVisibility(View.GONE);
                        llPropertyCoPersonRelation.setVisibility(View.GONE);
                        llPropertyImage.setVisibility(View.GONE);
                        llLesseeContact.setVisibility(View.GONE);
                        llLesseeName.setVisibility(View.GONE);

                        if (checkSummery != 0) {
                            ((RadioButton) rg_document.getChildAt(2)).setChecked(true);
                        }

                        sitePresence = "2";

                        pref.set(Constants.sitePresence, "2");
                        pref.commit();

                        break;
                    case 2:
                        llPropertyName.setVisibility(View.GONE);
                        llPropertyContact.setVisibility(View.GONE);
                        llPropertyCoPersonEmail.setVisibility(View.GONE);
                        llPropertyImage.setVisibility(View.GONE);
                        llPropertyNameRep.setVisibility(View.GONE);
                        llPropertyContactRep.setVisibility(View.GONE);
                        llPropertyCoPersonEmailRep.setVisibility(View.GONE);
                        llPropertyImageRep.setVisibility(View.GONE);
                        llPropertyCoPersonRelation.setVisibility(View.GONE);
                        llPropertyCoPersonRelationRep.setVisibility(View.GONE);
                        llPropertyAuthLetterRep.setVisibility(View.GONE);
                        llLesseeContact.setVisibility(View.GONE);
                        llLesseeName.setVisibility(View.GONE);

                        if (checkSummery != 0) {
                            ((RadioButton) rg_document.getChildAt(5)).setChecked(true);
                        }

                        sitePresence = "3";

                        pref.set(Constants.sitePresence, "3");
                        pref.commit();

                        break;
                    case 3:
                        llPropertyName.setVisibility(View.GONE);
                        llPropertyContact.setVisibility(View.GONE);
                        llPropertyCoPersonEmail.setVisibility(View.GONE);
                        llPropertyImage.setVisibility(View.GONE);
                        llPropertyNameRep.setVisibility(View.GONE);
                        llPropertyContactRep.setVisibility(View.GONE);
                        llPropertyCoPersonEmailRep.setVisibility(View.GONE);
                        llPropertyImageRep.setVisibility(View.GONE);
                        llPropertyCoPersonRelation.setVisibility(View.GONE);
                        llPropertyCoPersonRelationRep.setVisibility(View.GONE);
                        llPropertyAuthLetterRep.setVisibility(View.GONE);
                        llLesseeContact.setVisibility(View.VISIBLE);
                        llLesseeName.setVisibility(View.VISIBLE);

                      /*  if (checkSummery != 0) {
                            ((RadioButton) rg_document.getChildAt(5)).setChecked(true);
                        }*/

                        if (checkSummery != 0) {
                            ((RadioButton) rg_document.getChildAt(0)).setChecked(false);
                            ((RadioButton) rg_document.getChildAt(2)).setChecked(false);
                            ((RadioButton) rg_document.getChildAt(4)).setChecked(false);
                            ((RadioButton) rg_document.getChildAt(5)).setChecked(false);
                        }

                        sitePresence = "";

                        pref.set(Constants.sitePresence, "");
                        pref.commit();

                        break;
                    default:
                        llPropertyName.setVisibility(View.GONE);
                        llPropertyContact.setVisibility(View.GONE);
                        llPropertyCoPersonEmail.setVisibility(View.GONE);
                        llPropertyImage.setVisibility(View.GONE);
                        llPropertyNameRep.setVisibility(View.GONE);
                        llPropertyContactRep.setVisibility(View.GONE);
                        llPropertyCoPersonEmailRep.setVisibility(View.GONE);
                        llPropertyImageRep.setVisibility(View.GONE);
                        llPropertyCoPersonRelation.setVisibility(View.GONE);
                        llPropertyCoPersonRelationRep.setVisibility(View.GONE);
                        llPropertyAuthLetterRep.setVisibility(View.GONE);
                        llLesseeContact.setVisibility(View.GONE);
                        llLesseeName.setVisibility(View.GONE);

                        if (checkSummery != 0) {
                            ((RadioButton) rg_document.getChildAt(0)).setChecked(false);
                            ((RadioButton) rg_document.getChildAt(2)).setChecked(false);
                            ((RadioButton) rg_document.getChildAt(4)).setChecked(false);
                            ((RadioButton) rg_document.getChildAt(5)).setChecked(false);
                        }

                        sitePresence = "";

                        pref.set(Constants.sitePresence, "");
                        pref.commit();

                        break;


                }
            }
        });

        cbAnyOtherReason.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etAnyOtherReasonMeasurement.setVisibility(View.VISIBLE);
                } else {
                    etAnyOtherReasonMeasurement.setVisibility(View.GONE);
                }
            }
        });
        etCoveredAreaAsSiteSurvey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateDifferenceCovered();
            }
        });
        spEast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (east_ne[position].equals("East")) {
                    spWest.setSelection(0);
                    spNorth.setSelection(0);
                    spSouth.setSelection(0);
                } else {
                    spWest.setSelection(1);
                    spNorth.setSelection(1);
                    spSouth.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spWest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (west_nw[position].equals("West")) {
                    spEast.setSelection(0);
                    spNorth.setSelection(0);
                    spSouth.setSelection(0);

                } else {
                    spEast.setSelection(1);
                    spNorth.setSelection(1);
                    spSouth.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNorth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (north_se[position].equals("North")) {
                    spEast.setSelection(0);
                    spWest.setSelection(0);
                    spSouth.setSelection(0);
                } else {
                    spEast.setSelection(1);
                    spWest.setSelection(1);
                    spSouth.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSouth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (south_sw[position].equals("South")) {
                    spEast.setSelection(0);
                    spWest.setSelection(0);
                    spNorth.setSelection(0);
                } else {
                    spEast.setSelection(1);
                    spWest.setSelection(1);
                    spNorth.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cb_summery6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbStillProceed.setVisibility(View.VISIBLE);
                    if (edit_page) {
                        footer.setBackgroundColor(getResources().getColor(R.color.progressgrey));
                        footer.setClickable(false);
                        tvNext.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        footer.setClickable(true);
                    }
                    if (firstTime == false) {
//                        AlertEmailContentPopup();
                    }
                } else {
                    cbStillProceed.setVisibility(View.GONE);
                    etProceedReason.setVisibility(View.GONE);
                    tvSendReasonMail.setVisibility(View.GONE);

                    footer.setBackgroundColor(getResources().getColor(R.color.app_header));
                    footer.setClickable(true);
                    tvNext.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });

        cbStillProceed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    etProceedReason.setVisibility(View.VISIBLE);
                    tvSendReasonMail.setVisibility(View.VISIBLE);
                } else {
                    etProceedReason.setVisibility(View.GONE);
                    tvSendReasonMail.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSendReasonMail:

                if (etProceedReason.getText().toString().isEmpty()) {
                    Toast.makeText(mActivity, "Please enter reason", Toast.LENGTH_SHORT).show();
                    etProceedReason.requestFocus();
                } else {
                    if (Utils.isNetworkConnectedMainThred(mActivity)) {
                        hitSendEmailTwo();
                    } else {
                        Toast.makeText(mActivity, getString(R.string.noInternetConnection), Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.tvViewAuthLetter:

                if (!authLetter.equalsIgnoreCase("")) {
                    AlertAuthLetter();
                } else {
                    Toast.makeText(mActivity, "No Authorization Letter Available", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ivCameraLetter:

                camera_status = "4";

                showCameraGalleryDialog();

                break;
            case R.id.ivCameraLetterImage:

                camera_status = "4";

                showCameraGalleryDialog();

                break;
            case R.id.ivCameraLetterRep:

                camera_status = "5";

                showCameraGalleryDialog();

                break;
            case R.id.ivCameraLetterImageRep:

                camera_status = "5";

                showCameraGalleryDialog();

                break;

            case R.id.footer:
                if (edit_page) {

                    putintoHashMap();
                    pref.set("typeSurveyChecker", typeSurveyChecker);
                    pref.set("typeSurveyChecker8", typeSurveyChecker8);
                    pref.commit();
                    if (et_reason.getText().toString().equalsIgnoreCase("") && (tvIncaseDifferenceTitle.getVisibility() == View.VISIBLE || tvIncaseDifferenceMap.getVisibility() == View.VISIBLE) && v.findViewById(R.id.rbApplicable1).isSelected()) {
                        Toast.makeText(mActivity, "Please enter reason for difference", Toast.LENGTH_SHORT).show();
                        et_reason.requestFocus();


                        View targetView = v.findViewById(R.id.etInCaseOfDifferenceOf);
                        targetView.getParent().requestChildFocus(targetView, targetView);

                    } if (etInCaseOfDifferenceOf2.getText().toString().equalsIgnoreCase("") && (tvInCasediffCoverTitle.getVisibility() == View.VISIBLE || tvInCasediffCoverMap.getVisibility() == View.VISIBLE) && v.findViewById(R.id.rbApplicable2).isSelected()) {
                        Toast.makeText(mActivity, "Please enter reason for difference", Toast.LENGTH_SHORT).show();
                        etInCaseOfDifferenceOf2.requestFocus();


                        View targetView = v.findViewById(R.id.etInCaseOfDifferenceOf2);
                        targetView.getParent().requestChildFocus(targetView, targetView);

                    } else
//                        Log.v("textbox", et_reason.getText().toString());
//                        Log.v("textbox", Boolean.toString(tvIncaseDifferenceTitle.getVisibility() == View.VISIBLE));
//                        Log.v("textbox", Boolean.toString(tvIncaseDifferenceMap.getVisibility() == View.VISIBLE));
                        if (llPropertyName.getVisibility() == View.VISIBLE && etName.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Name", Toast.LENGTH_SHORT).show();
                            etName.requestFocus();

                            View targetView = v.findViewById(R.id.etName);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyNameRep.getVisibility() == View.VISIBLE && etNameRep.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Name", Toast.LENGTH_SHORT).show();
                            etNameRep.requestFocus();

                            View targetView = v.findViewById(R.id.etNameRep);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyContact.getVisibility() == View.VISIBLE && etContactNumber.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                            etContactNumber.requestFocus();

                            View targetView = v.findViewById(R.id.etContactNumber);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyContactRep.getVisibility() == View.VISIBLE && etContactNumberRep.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                            etContactNumberRep.requestFocus();

                            View targetView = v.findViewById(R.id.etContactNumber);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyContact.getVisibility() == View.VISIBLE && etContactNumber.getText().toString().length() < 10) {
                            Toast.makeText(mActivity, "Please Enter Valid Contact Number", Toast.LENGTH_SHORT).show();
                            etContactNumber.requestFocus();

                            View targetView = v.findViewById(R.id.etContactNumber);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyContactRep.getVisibility() == View.VISIBLE && etContactNumberRep.getText().toString().length() < 10) {
                            Toast.makeText(mActivity, "Please Enter Valid Contact Number", Toast.LENGTH_SHORT).show();
                            etContactNumberRep.requestFocus();

                            View targetView = v.findViewById(R.id.etContactNumberRep);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyCoPersonEmail.getVisibility() == View.VISIBLE && etCoPersonEmail.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Coordinating Person Email", Toast.LENGTH_SHORT).show();
                            etCoPersonEmail.requestFocus();

                            View targetView = v.findViewById(R.id.etCoPersonEmail);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyCoPersonEmailRep.getVisibility() == View.VISIBLE && etCoPersonEmailRep.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Coordinating Person Email", Toast.LENGTH_SHORT).show();
                            etCoPersonEmailRep.requestFocus();

                            View targetView = v.findViewById(R.id.etCoPersonEmailRep);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyCoPersonRelation.getVisibility() == View.VISIBLE && etCoPersonRelation.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Relation", Toast.LENGTH_SHORT).show();
                            etCoPersonRelation.requestFocus();

                            View targetView = v.findViewById(R.id.etCoPersonRelation);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyCoPersonRelationRep.getVisibility() == View.VISIBLE && etCoPersonRelationRep.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Relation", Toast.LENGTH_SHORT).show();
                            etCoPersonRelationRep.requestFocus();

                            View targetView = v.findViewById(R.id.etCoPersonRelationRep);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyImage.getVisibility() == View.VISIBLE && ivCameraLetterImage.getDrawable() == null) {
                            Toast.makeText(mActivity, "Please capture owner photograph", Toast.LENGTH_SHORT).show();
                            etContactNumber.requestFocus();

                            View targetView = v.findViewById(R.id.tvLetterImage);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llPropertyImageRep.getVisibility() == View.VISIBLE && ivCameraLetterImageRep.getDrawable() == null) {
                            Toast.makeText(mActivity, "Please capture owner representative photograph", Toast.LENGTH_SHORT).show();
                            etContactNumberRep.requestFocus();

                            View targetView = v.findViewById(R.id.tvLetterImageRep);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llLesseeName.getVisibility() == View.VISIBLE && etLesseeName.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Lessee Name", Toast.LENGTH_SHORT).show();
                            etLesseeName.requestFocus();

                            View targetView = v.findViewById(R.id.etLesseeName);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llLesseeContact.getVisibility() == View.VISIBLE && etLesseeContactNumber.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please Enter Lessee Contact Number", Toast.LENGTH_SHORT).show();
                            etLesseeContactNumber.requestFocus();

                            View targetView = v.findViewById(R.id.etLesseeContactNumber);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (ll_displayed.getVisibility() == View.VISIBLE && tv_camera1.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please capture photograph", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.tv_camera1);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (ll_notdisplayed.getVisibility() == View.VISIBLE && tv_camera2.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please capture photograph", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.tv_camera2);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (et_one.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter as per property documents", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.et_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                            et_one.requestFocus();

                        } else if (et_two.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter actual found at site", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.et_two);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                            et_two.requestFocus();

                        } else if (ivEast.getDrawable() == null) {
                            Toast.makeText(mActivity, "Please capture photograph", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.ivEast);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (et_one2.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter as per property documents", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.et_one2);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                            et_one2.requestFocus();

                        } else if (et_two2.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter actual found at site", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.et_two2);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                            et_two2.requestFocus();

                        } else if (ivWest.getDrawable() == null) {
                            Toast.makeText(mActivity, "Please capture photograph", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.ivWest);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (et_one3.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter as per property documents", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.et_one3);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                            et_one3.requestFocus();

                        } else if (et_two3.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter actual found at site", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.et_two3);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                            et_two3.requestFocus();

                        } else if (ivNorth.getDrawable() == null) {
                            Toast.makeText(mActivity, "Please capture photograph", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.ivNorth);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (et_one4.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter as per property documents", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.et_one4);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                            et_one4.requestFocus();

                        } else if (et_two4.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter actual found at site", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.et_two4);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                            et_two4.requestFocus();

                        } else if (ivSouth.getDrawable() == null) {
                            Toast.makeText(mActivity, "Please capture photograph", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.ivSouth);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (rg_measurement.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(mActivity, "Please select property measurement", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.rgPropertyMeasurement);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llReasonNoMeasurement.getVisibility() == View.VISIBLE
                                && !cbItIsAFlat.isChecked()
                                && !cbPropertyWasLocked.isChecked()
                                && !cbOwnerPossessee.isChecked()
                                && !cbNpaProperty.isChecked()
                                && !cbVeryLargeIrregular.isChecked()
                                && !cbAnyOtherReason.isChecked()) {
                            Toast.makeText(mActivity, "Please select reason for no measurement", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.llReasonNoMeasurement);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (llReasonNoMeasurement.getVisibility() == View.VISIBLE
                                && cbAnyOtherReason.isChecked()
                                && etAnyOtherReasonMeasurement.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter any other reason for no measurement", Toast.LENGTH_SHORT).show();
                            etAnyOtherReasonMeasurement.requestFocus();

                            View targetView = v.findViewById(R.id.cbAnyOtherReason);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (rg_landarea.getCheckedRadioButtonId() == -1 && llLandArea.getVisibility() == View.VISIBLE) {
                            Toast.makeText(mActivity, "Please select land area", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.rgLandArea);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (rgSurveyType.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(mActivity, "Please select Types of Survey", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.rgSurveyType);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (ll_LandArea2.getVisibility() == View.VISIBLE && (shapeSelected.equalsIgnoreCase("") || shapeSelected.equalsIgnoreCase("Select a Shape"))) {

                            Toast.makeText(mActivity, "Select a LandShape", Toast.LENGTH_SHORT).show();
                            spinnerLandShape.requestFocus();

                            View targetView = v.findViewById(R.id.spinnerLandShape);
                            targetView.getParent().requestChildFocus(targetView, targetView);
                        } else if (etLandAreaAsSiteSurvey.getText().toString().isEmpty() && ll_LandArea2.getVisibility() == View.VISIBLE) {
                            Toast.makeText(mActivity, "Area As per Site Survey is required", Toast.LENGTH_SHORT).show();
                            et_reason.requestFocus();


                            View targetView = v.findViewById(R.id.etLandAreaAsSiteSurvey);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (etGroundCoverage.getText().toString().isEmpty() && ll_landGroundCoverage.getVisibility() == View.VISIBLE) {
                            Toast.makeText(mActivity, "Please enter ground coverage", Toast.LENGTH_SHORT).show();
                            etGroundCoverage.requestFocus();

                            View targetView = v.findViewById(R.id.etGroundCoverage);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if (rgCoveredBuiltup.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(mActivity, "Please select covered built-up area", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.rgCoveredBuiltup);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } /*else if (llCoveredBuiltup2.getVisibility() == View.VISIBLE && etInCaseOfDifferenceOf2.getText().toString().isEmpty()) {
                            Toast.makeText(mActivity, "Please enter difference", Toast.LENGTH_SHORT).show();
                            etInCaseOfDifferenceOf2.requestFocus();

                            View targetView = v.findViewById(R.id.etInCaseOfDifferenceOf2);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        }*/ else if (videoViewPreview1.getVisibility() == View.GONE
                                && videoViewPreview2.getVisibility() == View.GONE
                                && !(((RadioButton) rg_document.getChildAt(4)).isChecked())
                                && !(((RadioButton) rg_document.getChildAt(5)).isChecked())) {
                            Toast.makeText(mActivity, "Please capture video", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.rg_document);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if ((((RadioButton) rgSurveyType.getChildAt(0)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(1)).isChecked()) &&
                                (selfiePhotoCount() < 1)) {

                            Toast.makeText(mActivity, "Please capture at least one selfie in front of the main gate of the property", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.ll_selfieone);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if ((((RadioButton) rgSurveyType.getChildAt(0)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(1)).isChecked()) &&
                                (namePlatePhotoCount() < 2)) {
                            Toast.makeText(mActivity, "Please capture at least two name plate along with the front elevation of the property", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.ll_plateone);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if ((((RadioButton) rgSurveyType.getChildAt(0)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(1)).isChecked()) &&
                                (leftSidePhotoCount() < 2)) {
                            Toast.makeText(mActivity, "Please capture at least two left side approach road/ lane", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.approach_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if ((((RadioButton) rgSurveyType.getChildAt(0)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(1)).isChecked()) &&
                                (rightSidePhotoCount() < 2)) {
                            Toast.makeText(mActivity, "Please capture at least two right side approach road/ lane", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.road_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if ((((RadioButton) rgSurveyType.getChildAt(0)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(1)).isChecked()) &&
                                (ownerPhotoCount() < 2)) {
                            Toast.makeText(mActivity, "Please capture at least two photograph of owner/ representative inside of the property", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.photo_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if ((((RadioButton) rgSurveyType.getChildAt(0)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(1)).isChecked()) &&
                                (extPhotoCount() < 4)) {
                            Toast.makeText(mActivity, "Please capture at least four exterior photographs (2 Front, Right Side, and Left Side)", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.Ephoto_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);
                        } else if ((((RadioButton) rgSurveyType.getChildAt(0)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(1)).isChecked()) &&
                                (mainGatePhotoCount() < 3)) {
                            Toast.makeText(mActivity, "Please capture at least three main gate photographs along with the front elevation", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.Mgate_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);
                        } else if ((((RadioButton) rgSurveyType.getChildAt(0)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(1)).isChecked()) &&
                                (intPhotoCount() < 5)) {
                            Toast.makeText(mActivity, "Please capture at least five interior photographs", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.Iphoto_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);
                        } else if ((((RadioButton) rgSurveyType.getChildAt(2)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(3)).isChecked()) &&
                                (selfiePhotoCount() < 1)) {

                            Toast.makeText(mActivity, "Please capture at least one selfie in front of the main gate of the property", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.ll_selfieone);
                            targetView.getParent().requestChildFocus(targetView, targetView);

                        } else if ((((RadioButton) rgSurveyType.getChildAt(2)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(3)).isChecked()) &&
                                (extPhotoCount() < 4)) {
                            Toast.makeText(mActivity, "Please capture at least four exterior photographs (2 Front, Right Side, and Left Side)", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.Ephoto_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);
                        } else if ((((RadioButton) rgSurveyType.getChildAt(2)).isChecked() ||
                                ((RadioButton) rgSurveyType.getChildAt(3)).isChecked()) &&
                                (mainGatePhotoCount() < 3)) {
                            Toast.makeText(mActivity, "Please capture at least three main gate photographs along with the front elevation", Toast.LENGTH_SHORT).show();

                            View targetView = v.findViewById(R.id.Mgate_one);
                            targetView.getParent().requestChildFocus(targetView, targetView);
                        } else {
                            if (Utils.isNetworkConnectedMainThred(mActivity)) {
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(false);
                                Hit_PostBasicInfo_Api();

                                if (cbItIsAFlat.isChecked()) {
                                    cbItIsAFlatChecked = 1;
                                } else {
                                    cbItIsAFlatChecked = 0;
                                }

                                if (cbPropertyWasLocked.isChecked()) {
                                    cbPropertyWasLockedChecked = 1;
                                } else {
                                    cbPropertyWasLockedChecked = 0;
                                }

                                if (cbOwnerPossessee.isChecked()) {
                                    cbOwnerPossesseeChecked = 1;
                                } else {
                                    cbOwnerPossesseeChecked = 0;
                                }

                                if (cbNpaProperty.isChecked()) {
                                    cbNpaPropertyChecked = 1;
                                } else {
                                    cbNpaPropertyChecked = 0;
                                }

                                if (cbVeryLargeIrregular.isChecked()) {
                                    cbVeryLargeIrregularChecked = 1;
                                } else {
                                    cbVeryLargeIrregularChecked = 0;
                                }

                                if (cbAnyOtherReason.isChecked()) {
                                    cbAnyOtherReasonChecked = 1;
                                    cbAnyOtherReasonValue = etAnyOtherReasonMeasurement.getText().toString();
                                } else {
                                    cbAnyOtherReasonChecked = 0;
                                }

                            } else {
                                Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                            }
                        }
                } else
                    ((Dashboard) mActivity).displayView(5);
                break;

            case R.id.iv_camera1:
                media_status = "30";
                camera_status = "1";

                camera();

                /*intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/
                break;

            case R.id.iv_camera2:
                media_status = "31";
                camera_status = "2";

                camera();
               /* intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/
                break;

            case R.id.ivRegularizationCerti:
                camera_status = "3";

                camera();
               /* intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/
                break;

            case R.id.tv_compass:
                intent = new Intent(mActivity, CompasActivity.class);
                startActivity(intent);
                break;

            case R.id.selfie_one:
                media_status = "1";
                captureImage();
                break;

            case R.id.selfie_two:
                media_status = "2";
                captureImage();
                break;

            case R.id.selfie_three:
                media_status = "3";
                captureImage();
                break;

            case R.id.nameplate_one:
                media_status = "4";
                captureImage();
                break;

            case R.id.nameplate_two:
                media_status = "5";
                captureImage();
                break;

            case R.id.nameplate_three:
                media_status = "6";
                captureImage();
                break;

            case R.id.approach_one:
                media_status = "7";
                captureImage();
                break;

            case R.id.approach_two:
                media_status = "8";
                captureImage();
                break;

            case R.id.approach_three:
                media_status = "9";
                captureImage();
                break;

            case R.id.road_one:
                media_status = "10";
                captureImage();
                break;

            case R.id.road_two:
                media_status = "11";
                captureImage();
                break;

            case R.id.road_three:
                media_status = "12";
                captureImage();
                break;

            case R.id.photo_one:
                media_status = "13";
                captureImage();
                break;

            case R.id.photo_two:
                media_status = "14";
                captureImage();
                break;

            case R.id.photo_three:
                media_status = "15";
                captureImage();
                break;

            case R.id.Ephoto_one:
                media_status = "16";
                captureImage();
                break;

            case R.id.Ephoto_two:
                media_status = "17";
                captureImage();
                break;

            case R.id.Ephoto_three:
                media_status = "18";
                captureImage();
                break;

            case R.id.Ephoto_four:
                media_status = "19";
                captureImage();
                break;

            case R.id.Ephoto_five:
                media_status = "32";
                captureImage();
                break;

            case R.id.Ephoto_six:
                media_status = "33";
                captureImage();
                break;

            case R.id.Ephoto_seven:
                media_status = "34";
                captureImage();
                break;

            case R.id.Mgate_one:
                media_status = "20";
                captureImage();
                break;

            case R.id.Mgate_two:
                media_status = "21";
                captureImage();
                break;

            case R.id.Mgate_three:
                media_status = "22";
                captureImage();
                break;

            case R.id.Mgate_four:
                media_status = "23";
                captureImage();
                break;

            case R.id.Mgate_five:
                media_status = "24";
                captureImage();
                break;

            case R.id.Iphoto_one:
                media_status = "25";
                captureImage();
                break;

            case R.id.Iphoto_two:
                media_status = "26";
                captureImage();
                break;

            case R.id.Iphoto_three:
                media_status = "27";
                captureImage();
                break;

            case R.id.Iphoto_four:
                media_status = "28";
                captureImage();
                break;

            case R.id.Iphoto_five:
                media_status = "29";
                captureImage();
                break;

            case R.id.ll_selfieone:

                if (ll_selfieone.getTag() != null) {
                    media_status = "1";
                    AlertImagePreview((Bitmap) ll_selfieone.getTag());
                }

                break;

            case R.id.ll_selfietwo:
                if (ll_selfietwo.getTag() != null) {
                    media_status = "2";
                    AlertImagePreview((Bitmap) ll_selfietwo.getTag());
                }
                break;

            case R.id.ll_selfiethree:
                if (ll_selfiethree.getTag() != null) {
                    media_status = "3";
                    AlertImagePreview((Bitmap) ll_selfiethree.getTag());
                }
                break;

            case R.id.ll_plateone:
                if (ll_plateone.getTag() != null) {
                    media_status = "4";
                    AlertImagePreview((Bitmap) ll_plateone.getTag());
                }
                break;

            case R.id.ll_platetwo:
                if (ll_platetwo.getTag() != null) {
                    media_status = "5";
                    AlertImagePreview((Bitmap) ll_platetwo.getTag());
                }
                break;

            case R.id.ll_platethree:
                if (ll_platethree.getTag() != null) {
                    media_status = "6";
                    AlertImagePreview((Bitmap) ll_platethree.getTag());
                }
                break;

            case R.id.ll_approachone:
                if (ll_approachone.getTag() != null) {
                    media_status = "7";
                    AlertImagePreview((Bitmap) ll_approachone.getTag());
                }
                break;

            case R.id.ll_approachtwo:
                if (ll_approachtwo.getTag() != null) {
                    media_status = "8";
                    AlertImagePreview((Bitmap) ll_approachtwo.getTag());
                }
                break;

            case R.id.ll_approachthree:
                if (ll_approachthree.getTag() != null) {
                    media_status = "9";
                    AlertImagePreview((Bitmap) ll_approachthree.getTag());
                }
                break;

            case R.id.ll_roadone:
                if (ll_roadone.getTag() != null) {
                    media_status = "10";
                    AlertImagePreview((Bitmap) ll_roadone.getTag());
                }
                break;

            case R.id.ll_roadtwo:
                if (ll_roadtwo.getTag() != null) {
                    media_status = "11";
                    AlertImagePreview((Bitmap) ll_roadtwo.getTag());
                }
                break;

            case R.id.ll_roadthree:
                if (ll_roadthree.getTag() != null) {
                    media_status = "12";
                    AlertImagePreview((Bitmap) ll_roadthree.getTag());
                }
                break;

            case R.id.ll_photoone:
                if (ll_photoone.getTag() != null) {
                    media_status = "13";
                    AlertImagePreview((Bitmap) ll_photoone.getTag());
                }
                break;

            case R.id.ll_phototwo:
                if (ll_phototwo.getTag() != null) {
                    media_status = "14";
                    AlertImagePreview((Bitmap) ll_phototwo.getTag());
                }
                break;

            case R.id.ll_photothree:
                if (ll_photothree.getTag() != null) {
                    media_status = "15";
                    AlertImagePreview((Bitmap) ll_photothree.getTag());
                }
                break;

            case R.id.ll_Ephotoone:
                if (ll_Ephotoone.getTag() != null) {
                    media_status = "16";
                    AlertImagePreview((Bitmap) ll_Ephotoone.getTag());
                }
                break;

            case R.id.ll_Ephototwo:
                if (ll_Ephototwo.getTag() != null) {
                    media_status = "17";
                    AlertImagePreview((Bitmap) ll_Ephototwo.getTag());
                }
                break;

            case R.id.ll_Ephotothree:
                if (ll_Ephotothree.getTag() != null) {
                    media_status = "18";
                    AlertImagePreview((Bitmap) ll_Ephotothree.getTag());
                }
                break;

            case R.id.ll_Ephotofour:
                if (ll_Ephotofour.getTag() != null) {
                    media_status = "19";
                    AlertImagePreview((Bitmap) ll_Ephotofour.getTag());
                }
                break;

            case R.id.ll_Ephotofive:
                if (ll_Ephotofive.getTag() != null) {
                    media_status = "32";
                    AlertImagePreview((Bitmap) ll_Ephotofive.getTag());
                }
                break;

            case R.id.ll_Ephotosix:
                if (ll_Ephotosix.getTag() != null) {
                    media_status = "33";
                    AlertImagePreview((Bitmap) ll_Ephotosix.getTag());
                }
                break;

            case R.id.ll_Ephotoseven:
                if (ll_Ephotoseven.getTag() != null) {
                    media_status = "34";
                    AlertImagePreview((Bitmap) ll_Ephotoseven.getTag());
                }
                break;

            case R.id.ll_Mgateone:
                if (ll_Mgateone.getTag() != null) {
                    media_status = "20";
                    AlertImagePreview((Bitmap) ll_Mgateone.getTag());
                }
                break;

            case R.id.ll_Mgatetwo:
                if (ll_Mgatetwo.getTag() != null) {
                    media_status = "21";
                    AlertImagePreview((Bitmap) ll_Mgatetwo.getTag());
                }
                break;

            case R.id.ll_Mgatethree:
                if (ll_Mgatethree.getTag() != null) {
                    media_status = "22";
                    AlertImagePreview((Bitmap) ll_Mgatethree.getTag());
                }
                break;

            case R.id.ll_Mgatefour:
                if (ll_Mgatefour.getTag() != null) {
                    media_status = "23";
                    AlertImagePreview((Bitmap) ll_Mgatefour.getTag());
                }
                break;

            case R.id.ll_Mgatefive:
                if (ll_Mgatefive.getTag() != null) {
                    media_status = "24";
                    AlertImagePreview((Bitmap) ll_Mgatefive.getTag());
                }
                break;

            case R.id.ll_Iphotoone:
                if (ll_Iphotoone.getTag() != null) {
                    media_status = "25";
                    AlertImagePreview((Bitmap) ll_Iphotoone.getTag());
                }
                break;

            case R.id.ll_Iphototwo:
                if (ll_Iphototwo.getTag() != null) {
                    media_status = "26";
                    AlertImagePreview((Bitmap) ll_Iphototwo.getTag());
                }
                break;

            case R.id.ll_Iphotothree:
                if (ll_Iphotothree.getTag() != null) {
                    media_status = "27";
                    AlertImagePreview((Bitmap) ll_Iphotothree.getTag());
                }
                break;

            case R.id.ll_Iphotofour:
                if (ll_Iphotofour.getTag() != null) {
                    media_status = "28";
                    AlertImagePreview((Bitmap) ll_Iphotofour.getTag());
                }
                break;

            case R.id.ll_Iphotofive:
                if (ll_Iphotofive.getTag() != null) {
                    media_status = "29";
                    AlertImagePreview((Bitmap) ll_Iphotofive.getTag());
                }
                break;


            case R.id.ivEast:
                camera_status = "0";
                media_status = "36";
                captureImage();
                break;

            case R.id.ivWest:
                camera_status = "0";
                media_status = "37";
                captureImage();
                break;

            case R.id.ivNorth:
                camera_status = "0";
                media_status = "38";
                captureImage();
                break;

            case R.id.ivSouth:
                camera_status = "0";
                media_status = "39";
                captureImage();
                break;

            case R.id.ivAddMoreInterior:

                //   setLayout();

                break;

            case R.id.tvAddMoreInterior:

                try {
                    if (((ColorDrawable) llTemp.getBackground()).getColor() !=
                            (getResources().getColor(R.color.light_grey))) {

                    }
                } catch (Exception e) {
                    countPhoto = dynamicPhotographsList.size() + 1;
                    setLayout();
                    e.printStackTrace();
                }


                break;


            case R.id.tvAddMoreExterior:

                try {
                    if (((ColorDrawable) llTemp2.getBackground()).getColor() !=
                            (getResources().getColor(R.color.light_grey))) {

                    }
                } catch (Exception e) {
                    countExtPhoto = dynamicExtPhotographsList.size() + 1;
                    setExtLayout();
                    e.printStackTrace();
                }


                break;


        }
    }

    private void camera() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            fileUri = FileProvider.getUriForFile(mActivity, BuildConfig.APPLICATION_ID + ".provider", getOutputMediaFile(MEDIA_TYPE_IMAGE));

            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            it.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(it, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } else {
            // create Intent to take a picture and return control to the calling application
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
            // start the image capture Intent
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }

    }

    private void setLayout() {

        media_status = "35";

        // for (int i = 0;i<ductList.get(position).size();i++)
        {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.inflate_interior_photographs, null);
            // ViewGroup main = (ViewGroup) findViewById(R.id.flowLayout);

            final LinearLayout llPhoto;
            final ImageView ivPhoto;

            llPhoto = view.findViewById(R.id.llPhoto);

            ivPhoto = view.findViewById(R.id.ivPhoto);

            llTemp = llPhoto.findViewById(llPhoto.getId());

            ivPhoto.setTag(countPhoto);

            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    //  BitmapDrawable background = new BitmapDrawable(b);
                    //  llPhoto.setBackgroundColor(mActivity.getColor(R.color.accept));

                    String tag = ivPhoto.getTag().toString();

                    Log.d("countPhoto", tag);
                    //referLayout = llPhoto.getId();

                    countPhoto = Integer.parseInt(tag);

                    llTemp = llPhoto.findViewById(llPhoto.getId());

                    captureImage(llPhoto);
                }
            });

            flowLayout.addView(view);

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    //   scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });

        }
    }

    private void setExtLayout() {

        media_status = "33";

        // for (int i = 0;i<ductList.get(position).size();i++)
        {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.inflate_interior_photographs, null);
            // ViewGroup main = (ViewGroup) findViewById(R.id.flowLayout);

            final LinearLayout llPhoto;
            final ImageView ivPhoto;

            llPhoto = view.findViewById(R.id.llPhoto);

            ivPhoto = view.findViewById(R.id.ivPhoto);

            llTemp2 = llPhoto.findViewById(llPhoto.getId());

            ivPhoto.setTag(countExtPhoto);

            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    //  BitmapDrawable background = new BitmapDrawable(b);
                    //  llPhoto.setBackgroundColor(mActivity.getColor(R.color.accept));

                    String tag = ivPhoto.getTag().toString();

                    Log.d("countPhoto", tag);
                    //referLayout = llPhoto.getId();

                    countExtPhoto = Integer.parseInt(tag);

                    llTemp2 = llPhoto.findViewById(llPhoto.getId());

                    captureImage(llPhoto);
                }
            });

            flowExterLayout.addView(view);

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    //   scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });

        }
    }

    private void captureImage() {
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/

     /*   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (media_status.equals("1") || media_status.equals("2") || media_status.equals("3")) {


            //  intent.putExtra("android.intent.extras.CAMERA_FACING", 0);
            //   intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            } else {
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            }

//            intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 0);
//            intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            } else {
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            }*/

            //  intent.putExtra("android.intent.extras.CAMERA_FACING",    Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            intent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_BACK);

        }

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(mActivity, R.string.camera_permission_required, Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        } else {

            camera();
        }
    }

    private void captureVideo(String videoName) {
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/

     /*   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/

    /*    File mediaFile =
                new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/"+videoName+".mp4");
*/
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        //  Uri videoUri = Uri.fromFile(mediaFile);
        Uri videoUri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".provider", getOutputMediaFile(MEDIA_TYPE_VIDEO));
        capVidUri = videoUri;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); //0 lower quality 1 higher quality

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(mActivity, R.string.camera_permission_required, Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        } else {

            startActivityForResult(intent, VIDEO_CAPTURE);
        }

    }

    private void captureImage(LinearLayout llPhoto) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (media_status.equals("1") || media_status.equals("2") || media_status.equals("3")) {

            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            } else {
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            }*/

            //  intent.putExtra("android.intent.extras.CAMERA_FACING",    Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            intent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_BACK);

        }

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(mActivity, R.string.camera_permission_required, Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        } else {
            //startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

            camera();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        //   return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".provider", getOutputMediaFile(type));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
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

                    encodedString = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                    if (camera_status.equals("1")) {
                        media_status = "0";
                        camera1.setText(filename);
                        ivCameraCapture1.setVisibility(View.VISIBLE);
                        ivCameraCapture1.setImageBitmap(rotatedBitmap);
                        shownByImage1 = encodedString;
                        encodedString5 = encodedString;

                    } else if (camera_status.equals("2")) {
                        media_status = "0";
                        camera2.setText(filename);
                        ivCameraCapture2.setVisibility(View.VISIBLE);
                        ivCameraCapture2.setImageBitmap(rotatedBitmap);
                        shownByImage2 = encodedString;
                        encodedString6 = encodedString;
                    } else if (camera_status.equals("3")) {
                        media_status = "0";
                        tvRegularizationCerti.setText(filename);
                    } else if (camera_status.equals("4")) {
                        media_status = "0";
                        tvLetterImage.setText(filename);
                        ivCameraLetter.setVisibility(View.GONE);
                        ivCameraLetterImage.setImageBitmap(rotatedBitmap);
                        shownByImage = encodedString;
                    } else if (camera_status.equals("5")) {
                        media_status = "0";
                        tvLetterImageRep.setText(filename);
                        ivCameraLetterRep.setVisibility(View.GONE);
                        ivCameraLetterImageRep.setImageBitmap(rotatedBitmap);
                        shownByImage = encodedString;
                    } else {

                    }

                    setPictures(rotatedBitmap, setPic, encodedString);

                    if (media_status.equalsIgnoreCase("1")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("2")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("3")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("4")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("5")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("6")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("7")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("8")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("9")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("10")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("11")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("12")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("13")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("14")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("15")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("16")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("17")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("18")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("19")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("20")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("21")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("22")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("23")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("24")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("25")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("26")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("27")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("28")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("29")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("32")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("33")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("34")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    } else if (media_status.equalsIgnoreCase("35")) {

                        //uploadPhotoFile(selectedImagePath);
                        uploadPhotoFile(capturedPath);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //setPictures(timestampItAndSave(rotatedBitmap), setPic, encodedString);

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

                if (camera_status.equals("4")) {
                    media_status = "0";
                    tvLetterImage.setText(filename);
                    ivCameraLetter.setVisibility(View.GONE);
                    ivCameraLetterImage.setImageBitmap(rotatedBitmap);
                } else if (camera_status.equals("5")) {
                    media_status = "0";
                    tvLetterImageRep.setText(filename);
                    ivCameraLetterRep.setVisibility(View.GONE);
                    ivCameraLetterImageRep.setImageBitmap(rotatedBitmap);
                }

            } else {
                Toast.makeText(mActivity, "unable to select image", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (videoCheck == 1) {
                        try {
                            videoViewPreview1.setVisibility(View.VISIBLE);
                            videoViewPreview1.setVideoURI(data.getData());
                            videoViewPreview1.start();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (videoCheck == 2) {
                        try {
                            videoViewPreview2.setVisibility(View.VISIBLE);
                            videoViewPreview2.setVideoURI(data.getData());
                            videoViewPreview2.start();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    //Log.v("filelelelname",data.getData().getPath().substring(data.getData().getPath().lastIndexOf("/") + 1));

                    //  uploadVideoFile(data.getData().getPath().substring(data.getData().getPath().lastIndexOf("/") + 1));
                    uploadVideoFile(data.getData().getPath());
                } else if (capVidUri != null) {
                    if (videoCheck == 1) {
                        try {
                            videoViewPreview1.setVisibility(View.VISIBLE);
                            videoViewPreview1.setVideoURI(capVidUri);
                            videoViewPreview1.start();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (videoCheck == 2) {
                        try {
                            videoViewPreview2.setVisibility(View.VISIBLE);
                            videoViewPreview2.setVideoURI(capVidUri);
                            videoViewPreview2.start();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    uploadVideoFile(capVidUri.getPath());
                } else {
                    Toast.makeText(mActivity, "unable to capture Video", Toast.LENGTH_LONG).show();
//                    Log.v("CaptureVid",data.getExtras().toString());
                }

//                    Toast.makeText(mActivity, "Video saved to:\n" +
//                            data.getData(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(mActivity, "Video recording cancelled.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, "Failed to record video",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void setPictures(Bitmap b, String setPic, String base64) {

        if (media_status.equals("1")) {

            //  BitmapDrawable background = new BitmapDrawable(b);
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_selfieone));
            ll_selfieone.setBackgroundDrawable(background);
            //    ll_selfieone.setTag(base64);

        } else if (media_status.equals("2")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_selfietwo));
            ll_selfietwo.setBackgroundDrawable(background);
            // ll_selfietwo.setTag(base64);

        } else if (media_status.equals("3")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_selfiethree));
            ll_selfiethree.setBackgroundDrawable(background);
            //   ll_selfiethree.setTag(base64);

        } else if (media_status.equals("4")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_plateone));
            ll_plateone.setBackgroundDrawable(background);
            //   ll_plateone.setTag(base64);

        } else if (media_status.equals("5")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_platetwo));
            ll_platetwo.setBackgroundDrawable(background);
            // ll_platetwo.setTag(base64);

        } else if (media_status.equals("6")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_platethree));
            ll_platethree.setBackgroundDrawable(background);
            // ll_platethree.setTag(base64);

        } else if (media_status.equals("7")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_approachone));
            ll_approachone.setBackgroundDrawable(background);
            // ll_approachone.setTag(base64);

        } else if (media_status.equals("8")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_approachtwo));
            ll_approachtwo.setBackgroundDrawable(background);
            // ll_approachtwo.setTag(base64);

        } else if (media_status.equals("9")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_approachthree));
            ll_approachthree.setBackgroundDrawable(background);
            //ll_approachthree.setTag(base64);

        } else if (media_status.equals("10")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_roadone));
            ll_roadone.setBackgroundDrawable(background);
            // ll_roadone.setTag(base64);

        } else if (media_status.equals("11")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_roadtwo));
            ll_roadtwo.setBackgroundDrawable(background);
            //     ll_roadtwo.setTag(base64);

        } else if (media_status.equals("12")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_roadthree));
            ll_roadthree.setBackgroundDrawable(background);
            // ll_roadthree.setTag(base64);

        } else if (media_status.equals("13")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_photoone));
            ll_photoone.setBackgroundDrawable(background);
            //   ll_photoone.setTag(base64);

        } else if (media_status.equals("14")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_phototwo));
            ll_phototwo.setBackgroundDrawable(background);
            //   ll_phototwo.setTag(base64);

        } else if (media_status.equals("15")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_photothree));
            ll_photothree.setBackgroundDrawable(background);
            // ll_photothree.setTag(base64);

        } else if (media_status.equals("16")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Ephotoone));
            ll_Ephotoone.setBackgroundDrawable(background);
            //    ll_Ephotoone.setTag(base64);

        } else if (media_status.equals("17")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Ephototwo));
            ll_Ephototwo.setBackgroundDrawable(background);
//            ll_Ephototwo.setTag(base64);

        } else if (media_status.equals("18")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Ephotothree));
            ll_Ephotothree.setBackgroundDrawable(background);
//            ll_Ephotothree.setTag(base64);

        } else if (media_status.equals("19")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Ephotofour));
            ll_Ephotofour.setBackgroundDrawable(background);
//            ll_Ephotofour.setTag(base64);

        } else if (media_status.equals("32")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Ephotofive));
            ll_Ephotofive.setBackgroundDrawable(background);
//            ll_Ephotofive.setTag(base64);

        } /*else if (media_status.equals("33")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b,ll_Ephotosix));
            ll_Ephotosix.setBackgroundDrawable(background);
//            ll_Ephotosix.setTag(base64);

        } else if (media_status.equals("34")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b,ll_Ephotoseven));
            ll_Ephotoseven.setBackgroundDrawable(background);
//            ll_Ephotoseven.setTag(base64);

        }*/ else if (media_status.equals("20")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Mgateone));
            ll_Mgateone.setBackgroundDrawable(background);
//            ll_Mgateone.setTag(base64);

        } else if (media_status.equals("21")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Mgatetwo));
            ll_Mgatetwo.setBackgroundDrawable(background);
            //   ll_Mgatetwo.setTag(base64);

        } else if (media_status.equals("22")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Mgatethree));
            ll_Mgatethree.setBackgroundDrawable(background);
            // ll_Mgatethree.setTag(base64);

        } else if (media_status.equals("23")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Mgatefour));
            ll_Mgatefour.setBackgroundDrawable(background);
            // ll_Mgatefour.setTag(base64);

        } else if (media_status.equals("24")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Mgatefive));
            ll_Mgatefive.setBackgroundDrawable(background);
            //   ll_Mgatefive.setTag(base64);

        } else if (media_status.equals("25")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Iphotoone));
            ll_Iphotoone.setBackgroundDrawable(background);
            //ll_Iphotoone.setTag(base64);

        } else if (media_status.equals("26")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Iphototwo));
            ll_Iphototwo.setBackgroundDrawable(background);
            //  ll_Iphototwo.setTag(base64);

        } else if (media_status.equals("27")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Iphotothree));
            ll_Iphotothree.setBackgroundDrawable(background);
            // ll_Iphotothree.setTag(base64);

        } else if (media_status.equals("28")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Iphotofour));
            ll_Iphotofour.setBackgroundDrawable(background);
            //  ll_Iphotofour.setTag(base64);

        } else if (media_status.equals("29")) {
            BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, ll_Iphotofive));
            ll_Iphotofive.setBackgroundDrawable(background);
            //    ll_Iphotofive.setTag(base64);

        } else if (media_status.equals("30")) {
            ivCameraCapture1.setVisibility(View.VISIBLE);
            ivCameraCapture1.setImageBitmap(b);
            encodedString5 = base64;

        } else if (media_status.equals("31")) {
            ivCameraCapture2.setVisibility(View.VISIBLE);
            ivCameraCapture2.setImageBitmap(b);
            encodedString6 = base64;

        } else if (media_status.equals("36")) {

            ivEast.setImageBitmap(b);

            encodedString1 = base64;

        } else if (media_status.equals("37")) {

            ivWest.setImageBitmap(b);

            encodedString2 = base64;

        } else if (media_status.equals("38")) {

            ivNorth.setImageBitmap(b);

            encodedString3 = base64;

        } else if (media_status.equals("39")) {

            ivSouth.setImageBitmap(b);

            encodedString4 = base64;

        } else if (media_status.equals("35")) {
            // ivPhoto.setImageBitmap(b);

            //  if (llPhoto.getTag() == llPhoto)
            {
                //BitmapDrawable background = new BitmapDrawable(b);
                BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, llTemp));
                //llPhoto.setBackgroundDrawable(background);
                llTemp.setBackgroundDrawable(background);
                //  llTemp.setTag(base64);

            }

        } else if (media_status.equals("33")) {
            // ivPhoto.setImageBitmap(b);

            //  if (llPhoto.getTag() == llPhoto)
            {
                //BitmapDrawable background = new BitmapDrawable(b);
                BitmapDrawable background = new BitmapDrawable(timestampItAndSave(b, llTemp2));
                //llPhoto.setBackgroundDrawable(background);
                llTemp2.setBackgroundDrawable(background);
                //  llTemp.setTag(base64);

            }

        } else {

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

    private void Hit_GetBasicInfo_Api() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.GetBasicSurveyInfo);
        Log.v("Hit_GetBasicInfo_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));
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
                            loader.cancel();
                            //   Toast.makeText(MapLocatorActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonGet(JSONObject response) {

        Log.v("response", response.toString());

        try {
            final JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                if (!jsonObject.getString("basic_survey_value").equalsIgnoreCase("")) {
                    try {
                        JSONArray jsonarray = jsonObject.getJSONArray("basic_survey_value");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject obj = jsonarray.getJSONObject(i);
                            tv_camera1.setText(obj.getString("tv_camera1"));
                            tv_camera2.setText(obj.getString("tv_camera2"));
                            if (obj.has("showByImage1"))
                                shownByImage1 = obj.getString("showByImage1");
                            if (obj.has("showByImage2"))
                                shownByImage2 = obj.getString("showByImage2");
                            et_reason.setText(obj.getString("et_reason"));
                            if (obj.has("titleDiffPercent")) {
                                String val = pref.get(Constants.titleDiffPercent);
                                if (!val.isEmpty()) {
                                    if (Double.parseDouble(val) > 5.0) {
                                        tvIncaseDifferenceTitle.setVisibility(View.VISIBLE);
                                        tvIncaseDifferenceTitle.setText("Title Area has difference of " + obj.getString("titleDiffPercent") + "%");
                                        titleDiffPercent = Double.parseDouble(obj.getString("titleDiffPercent"));
                                        statusAreaTitle = true;
                                    }
                                } else {
                                    tvIncaseDifferenceTitle.setVisibility(View.GONE);
                                    statusAreaTitle = false;
                                }
                            } else {
                                tvIncaseDifferenceTitle.setVisibility(View.GONE);
                                statusAreaTitle = false;
                            }

                            if (obj.has("coveredDiffPercent")) {
                                String val = obj.getString("coveredDiffPercent");
                                if (!val.isEmpty()) {
                                    if (Double.parseDouble(val) > 5.0) {
                                        tvInCasediffCoverTitle.setVisibility(View.VISIBLE);
                                        tvInCasediffCoverTitle.setText("Title Area has difference of " + obj.getString("coveredDiffPercent") + "%");
                                        coveredDiffPercent = Double.parseDouble(obj.getString("coveredDiffPercent"));
                                        statusCovAreaTitle = true;
                                    }
                                } else {
                                    tvInCasediffCoverTitle.setVisibility(View.GONE);
                                    statusCovAreaTitle = false;
                                }
                            } else {
                                tvInCasediffCoverTitle.setVisibility(View.GONE);
                                statusCovAreaTitle = false;
                            }
                            if (obj.has("mapCovDiffPercent")) {
                                String val = obj.getString("mapCovDiffPercent");
                                if (!val.isEmpty()) {
                                    if (Double.parseDouble(val) > 5.0) {
                                        tvInCasediffCoverMap.setText("Map Area has difference of " + obj.getString("mapCovDiffPercent") + "%");
                                        mapCovDiffPercent = Double.parseDouble(obj.getString("mapCovDiffPercent"));
                                        statusCovAreaMap = true;
                                    }
                                } else {
                                    tvInCasediffCoverMap.setVisibility(View.GONE);
                                    statusCovAreaMap = false;
                                }

                            } else {
                                tvInCasediffCoverMap.setVisibility(View.GONE);
                                statusCovAreaMap = false;
                            }
                            if (obj.has("mapDiffPercent")) {
                                String val = obj.getString("mapDiffPercent");
                                if (!val.isEmpty()) {
                                    if (Double.parseDouble(val) > 5.0) {
                                        tvIncaseDifferenceMap.setText("Map Area has difference of " + obj.getString("mapCovDiffPercent") + "%");
                                        mapDiffPercent = Double.parseDouble(obj.getString("mapDiffPercent"));
                                        statusAreaMap = true;
                                    }
                                } else {
                                    tvIncaseDifferenceMap.setVisibility(View.GONE);
                                    statusAreaMap = false;
                                }

                            } else {
                                tvIncaseDifferenceMap.setVisibility(View.GONE);
                                statusAreaMap = false;
                            }
                            et_coverage.setText(obj.getString("et_coverage"));

                            if (obj.has("sideAUnit")) {
                                sideAUnit = obj.getString("sideAUnit");
                            }
                            Log.v("selected_landarea", obj.getString("selected_landarea"));
//                    if(obj.getString("selected_landarea").equalsIgnoreCase("Applicable")){
//                        ((RadioButton) rg_landarea.getChildAt(0)).setChecked(true);
//                    }else{
//                        ((RadioButton) rg_landarea.getChildAt(1)).setChecked(true);
//                    }

                            et_one.setText(obj.getString("et_one"));
                            et_one2.setText(obj.getString("et_one2"));
                            et_one3.setText(obj.getString("et_one3"));
                            et_one4.setText(obj.getString("et_one4"));


                            et_two.setText(obj.getString("et_two"));
                            et_two2.setText(obj.getString("et_two2"));
                            et_two3.setText(obj.getString("et_two3"));
                            et_two4.setText(obj.getString("et_two4"));
                            et_three.setText(obj.getString("et_three"));
                            //et_four.setText(obj.getString("et_four"));
                            //  etname.setText(obj.getString("etname"));
                            // etnumber.setText(obj.getString("etnumber"));


                            etCoveredAreaAsPerMap.setText(obj.getString("etCoveredAreaAsPerMap"));
                            etCoveredAreaAsSiteSurvey.setText(obj.getString("etCoveredAreaAsSiteSurvey"));
                            etCoveredAreaEast.setText(obj.getString("etCoveredAreaEast"));
                            etCoveredAreaWest.setText(obj.getString("etCoveredAreaWest"));
                            etCoveredAreaNorth.setText(obj.getString("etCoveredAreaNorth"));

                            etCoveredAreaAsPerTitle.setText(obj.getString("etCoveredAreaAsPerTitle"));
                            etCoveredAreaSouth.setText(obj.getString("etCoveredAreaSouth"));
                            etCoveredFrontsetback.setText(obj.getString("etCoveredFrontsetback"));
                            etCoveredBackSetback.setText(obj.getString("etCoveredBackSetback"));
                            etCoveredRightSideSetback.setText(obj.getString("etCoveredRightSideSetback"));
                            etCoveredLeftSideSetback.setText(obj.getString("etCoveredLeftSideSetback"));
                            etInCaseOfDifferenceOf2.setText(obj.getString("etInCaseOfDifferenceOf2"));

                            //et_one2.setText(obj.getString("et_one2"));
                            et_two2.setText(obj.getString("et_two2"));
                            et_three2.setText(obj.getString("et_three2"));
                            //et_four2.setText(obj.getString("et_four2"));

                            // et_one3.setText(obj.getString("et_one3"));
                            et_two3.setText(obj.getString("et_two3"));
                            et_three3.setText(obj.getString("et_three3"));

                            // et_one4.setText(obj.getString("et_one4"));
                            et_two4.setText(obj.getString("et_two4"));
                            et_three4.setText(obj.getString("et_three4"));

                            etDirectionEast.setText(obj.getString("etDirectionEast"));
                            etDirectionWest.setText(obj.getString("etDirectionWest"));
                            etDirectionNorth.setText(obj.getString("etDirectionNorth"));
                            etDirectionSouth.setText(obj.getString("etDirectionSouth"));

                            if (obj.getString("selected_direction").equals("East")) {
                                ((RadioButton) rg_direction.getChildAt(0)).setChecked(true);
                            } else if (obj.getString("selected_direction").equals("West")) {
                                ((RadioButton) rg_direction.getChildAt(1)).setChecked(true);
                            } else if (obj.getString("selected_direction").equals("North")) {
                                ((RadioButton) rg_direction.getChildAt(2)).setChecked(true);
                            } else if (obj.getString("selected_direction").equals("South")) {
                                ((RadioButton) rg_direction.getChildAt(3)).setChecked(true);
                            } else if (obj.getString("selected_direction").equals("North-East")) {
                                ((RadioButton) rg_direction.getChildAt(4)).setChecked(true);
                            } else if (obj.getString("selected_direction").equals("North-West")) {
                                ((RadioButton) rg_direction.getChildAt(5)).setChecked(true);
                            } else if (obj.getString("selected_direction").equals("South-East")) {
                                ((RadioButton) rg_direction.getChildAt(6)).setChecked(true);

                            } else if (obj.getString("selected_direction").equals("South-West")) {
                                ((RadioButton) rg_direction.getChildAt(7)).setChecked(true);
                            }
                            if (obj.has("shape_selected")) {
                                tv_lenghtSideA.setText(obj.getString("sideALength"));
                                tv_lenghtSideB.setText(obj.getString("sideBLength"));
                                tv_lenghtSideC.setText(obj.getString("sideCLength"));
                                tv_lenghtSideD.setText(obj.getString("sideDLength"));
                                tv_lenghtSideH.setText(obj.getString("sideHLength"));
                                shapeSelected = obj.getString("shape_selected");
                                switch (obj.getString("shape_selected")) {
                                    case "Square": {
                                        spinnerLandShape.setSelection(1);
                                        rl_SideValues.setVisibility(View.VISIBLE);
                                        ll_SideA.setVisibility(View.VISIBLE);
                                        ll_SideB.setVisibility(View.VISIBLE);
                                        ll_SideC.setVisibility(View.VISIBLE);
                                        ll_SideD.setVisibility(View.VISIBLE);
                                        ll_SideH.setVisibility(View.GONE);

                                        showPop = 1;
                                        break;
                                    }
                                    case "Rectangle": {
                                        spinnerLandShape.setSelection(2);
                                        rl_SideValues.setVisibility(View.VISIBLE);
                                        ll_SideA.setVisibility(View.VISIBLE);
                                        ll_SideB.setVisibility(View.VISIBLE);
                                        ll_SideC.setVisibility(View.VISIBLE);
                                        ll_SideD.setVisibility(View.VISIBLE);
                                        ll_SideH.setVisibility(View.GONE);
                                        showPop = 1;
//                            showPop=true;
                                        break;
                                    }
                                    case "Triangle": {
                                        spinnerLandShape.setSelection(3);
                                        rl_SideValues.setVisibility(View.VISIBLE);
                                        ll_SideA.setVisibility(View.VISIBLE);
                                        ll_SideB.setVisibility(View.VISIBLE);
                                        ll_SideC.setVisibility(View.VISIBLE);
                                        ll_SideD.setVisibility(View.GONE);
                                        showPop = 1;
                                        ll_SideH.setVisibility(View.VISIBLE);
//                            showPop=true;
                                        break;
                                    }
                                    case "Trapezium": {
                                        spinnerLandShape.setSelection(4);
                                        ll_SideA.setVisibility(View.VISIBLE);
                                        rl_SideValues.setVisibility(View.VISIBLE);
                                        showPop = 1;
                                        ll_SideB.setVisibility(View.VISIBLE);
                                        ll_SideC.setVisibility(View.VISIBLE);
                                        ll_SideD.setVisibility(View.VISIBLE);
                                        ll_SideH.setVisibility(View.VISIBLE);
//                            showPop=true;
                                        break;
                                    }
                                    case "Trapezoid": {
                                        spinnerLandShape.setSelection(5);
                                        ll_SideA.setVisibility(View.VISIBLE);
                                        rl_SideValues.setVisibility(View.VISIBLE);
                                        ll_SideB.setVisibility(View.VISIBLE);
                                        ll_SideC.setVisibility(View.VISIBLE);
                                        ll_SideD.setVisibility(View.VISIBLE);
                                        showPop = 1;
                                        ll_SideH.setVisibility(View.VISIBLE);
//                            showPop=true;
                                        break;
                                    }
                                    case "Irregular": {
                                        spinnerLandShape.setSelection(6);
                                        rl_SideValues.setVisibility(View.GONE);
                                        showPop = 1;
                                        etLandAreaAsSiteSurvey.setEnabled(true);
//                            showPop=true;
                                        break;
                                    }
                                    default: {
                                        spinnerLandShape.setSelection(0);
                                        rl_SideValues.setVisibility(View.GONE);
                                        showPop = 2;
//                            showPop=true;
                                    }
                                }
                            } else {
                                showPop = 2;
                            }
                            spinnerDirectA.setSelection(directionChooser(obj.getString("sideADirection")));
                            spinnerDirectB.setSelection(directionChooser(obj.getString("sideBDirection")));
                            spinnerDirectC.setSelection(directionChooser(obj.getString("sideCDirection")));
                            spinnerDirectD.setSelection(directionChooser(obj.getString("sideDDirection")));
                            spinnerDirectH.setSelection(directionChooser(obj.getString("sideHDirection")));


                            try {
                                if (obj.has("selected_property"))
                                    if (!(obj.getString("selected_property").equalsIgnoreCase("")))

                                        ((RadioButton) rgNamePlate.getChildAt(Integer.parseInt(obj.getString("selected_property")))).setChecked(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    /*if (obj.getString("selected_property").equals("Proper name plate displayed on the property.")) {
                        ((RadioButton) rgNamePlate.getChildAt(0)).setChecked(true);
                    } else if (obj.getString("selected_property").equals("No name plate displayed on the property.")) {
                        ((RadioButton) rgNamePlate.getChildAt(2)).setChecked(true);
                    } else if (obj.getString("selected_property").equals("Vacant undemarcated open land public plot.")) {
                        ((RadioButton) rgNamePlate.getChildAt(4)).setChecked(true);
                    }*/


                            try {
                                ((RadioButton) rg_boundries.getChildAt(Integer.parseInt(obj.getString("selected_boundries")))).setChecked(true);

                                if (Integer.parseInt(obj.getString("selected_boundries")) == 4) {
                                    llNotApplicable.setVisibility(View.VISIBLE);
                                } else {
                                    llNotApplicable.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                 /*   if (obj.getString("selected_boundries").equals("Yes")) {
                        ((RadioButton) rg_boundries.getChildAt(0)).setChecked(true);
                    } else if (obj.getString("selected_boundries").equals("No")) {
                        ((RadioButton) rg_boundries.getChildAt(1)).setChecked(true);
                    } else if (obj.getString("selected_boundries").equals("No relevant papers available to match the boundaries")) {
                        ((RadioButton) rg_boundries.getChildAt(2)).setChecked(true);
                    } else if (obj.getString("selected_boundries").equals("Boundaries not mentioned in available documents")) {
                        ((RadioButton) rg_boundries.getChildAt(3)).setChecked(true);

                    } else if (obj.getString("selected_boundries").equals("Couldn’t go near the property since it is NPA account")) {
                        ((RadioButton) rg_boundries.getChildAt(4)).setChecked(true);

                    }*/

                            try {
                                ((RadioButton) rg_document.getChildAt(Integer.parseInt(obj.getString("selected_measurement")))).setChecked(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                ((RadioButton) rg_measurement.getChildAt(Integer.parseInt(obj.getString("selected_measurement_area")))).setChecked(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                   /* if (obj.getString("selected_measurement").equals("Self-measured (Complete)")) {
                        ((RadioButton) rg_boundries.getChildAt(0)).setChecked(true);
                    } else if (obj.getString("selected_measurement").equals("Self-measured (from outside only)")) {
                        ((RadioButton) rg_boundries.getChildAt(1)).setChecked(true);
                    } else if (obj.getString("selected_measurement").equals("Sample random measurement only")) {
                        ((RadioButton) rg_boundries.getChildAt(2)).setChecked(true);
                    } else if (obj.getString("selected_measurement").equals("No measurement")) {
                        ((RadioButton) rg_boundries.getChildAt(3)).setChecked(true);
                    }*/

                            if (obj.getString("selected_landarea").equals("Applicable")) {
                                ((RadioButton) rg_landarea.getChildAt(0)).setChecked(true);
                                ll_LandArea2.setVisibility(View.VISIBLE);
                                Log.v("landDetail", obj.getString("landAreaAsSiteSurvey"));
                                etLandAreaAsSiteSurvey.setText(obj.getString("landAreaAsSiteSurvey"));
//                            rl_SideValues.setVisibility(View.VISIBLE);
                            } else if (obj.getString("selected_landarea").equals("Not Applicable")) {
                                ((RadioButton) rg_landarea.getChildAt(1)).setChecked(true);

                            }

                            if (obj.getString("selected_buildup").equals("Applicable")) {
                                ((RadioButton) rgCoveredBuiltup.getChildAt(0)).setChecked(true);
                            } else if (obj.getString("selected_buildup").equals("Not Applicable")) {
                                ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                            } else {
                                if (survType.equalsIgnoreCase("vacantLand")) {
                                    ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                                }
                                if (survType.equalsIgnoreCase("MultiStoried")) {
                                    ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                                }
                            }

                            if (obj.getString("selected_document").equals("Applicable")) {
                                ((RadioButton) rgCoveredBuiltup.getChildAt(0)).setChecked(true);
                            } else if (obj.getString("selected_document").equals("Not Applicable")) {
                                ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                            } else {
                                if (survType.equalsIgnoreCase("vacantLand")) {
                                    ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                                }
                                if (survType.equalsIgnoreCase("MultiStoried")) {
                                    ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                                }
                            }

                            if (obj.getString("selected_buildup").equals("Applicable")) {
                                ((RadioButton) rgCoveredBuiltup.getChildAt(0)).setChecked(true);
                            } else if (obj.getString("selected_buildup").equals("Not Applicable")) {
                                ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                            } else {
                                if (survType.equalsIgnoreCase("vacantLand")) {
                                    ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                                }
                                if (survType.equalsIgnoreCase("MultiStoried")) {
                                    ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                                }
                            }

                            if (obj.getString("selected_document").equals("Owner confirmed to me that this is the same property")) {
                                ((RadioButton) rg_document.getChildAt(0)).setChecked(true);

                                try {
                                    videoName = obj.getString("videoName");
                                    Uri uri = Uri.parse(getString(R.string.videoPath) +
                                            pref.get(Constants.lead_id) + "_" + pref.get(Constants.case_id) +
                                            "/" + videoName);

                                    videoViewPreview1.setVisibility(View.VISIBLE);
                                    videoViewPreview1.setVideoURI(uri);
                                    videoViewPreview1.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else if (obj.getString("selected_document").equals("Owner representative confirmed to me that this is the same property after confirming from the owner")) {
                                ((RadioButton) rg_document.getChildAt(2)).setChecked(true);

                                //http://trendytoday.in/vis/assets/admin/clien_doc/VIS0_178_411/411_VID1_20190328_201739.mp4
                                try {
                                    Uri uri = Uri.parse(getString(R.string.videoPath) +
                                            pref.get(Constants.lead_id) + "_" + pref.get(Constants.case_id) +
                                            "/" + obj.getString("videoName"));

                                    videoViewPreview2.setVisibility(View.VISIBLE);
                                    videoViewPreview2.setVideoURI(uri);
                                    videoViewPreview2.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else if (obj.getString("selected_document").equals("Owner/ owner representative didn’t confirm clearly")) {
                                ((RadioButton) rg_document.getChildAt(4)).setChecked(true);
                            }

                            if (obj.getString("selected_enquiry").equals("Nearby/ neighbouring people confirmed about the property")) {
                                ((RadioButton) rg_enquiry.getChildAt(0)).setChecked(true);
                            } else if (obj.getString("selected_enquiry").equals("Tried to enquire from nearby/ neighbouring people but no one could confirm")) {
                                ((RadioButton) rg_enquiry.getChildAt(2)).setChecked(true);
                            } else if (obj.getString("selected_enquiry").equals("Authentic enquiries could not be made since no one was available nearby")) {
                                ((RadioButton) rg_enquiry.getChildAt(3)).setChecked(true);
                            }

                            if (obj.getString("cbItIsAFlat").equals("It is a flat in multi storey building so measurement not required")) {
                                cbItIsAFlat.setChecked(true);
                            } else {
                                cbItIsAFlat.setChecked(false);
                            }
                            if (obj.getString("cbPropertyWasLocked").equals("Property was locked")) {
                                cbPropertyWasLocked.setChecked(true);
                            } else {
                                cbPropertyWasLocked.setChecked(false);
                            }
                            if (obj.getString("cbOwnerPossessee").equals("Owner/ possessee didn’t allow to do survey")) {
                                cbOwnerPossessee.setChecked(true);
                            } else {
                                cbOwnerPossessee.setChecked(false);
                            }
                            if (obj.getString("cbNpaProperty").equals("NPA property so didn’t enter the property")) {
                                cbNpaProperty.setChecked(true);
                            } else {
                                cbNpaProperty.setChecked(false);
                            }
                            if (obj.getString("cbVeryLargeIrregular").equals("Very Large irregular Property, practically not possible to measure the entire area")) {
                                cbVeryLargeIrregular.setChecked(true);
                            } else {
                                cbVeryLargeIrregular.setChecked(false);
                            }
                            if (obj.getString("cbAnyOtherReason").equals("Any other Reason")) {
                                cbAnyOtherReason.setChecked(true);
                            } else {
                                cbAnyOtherReason.setChecked(false);
                            }
                            if (obj.getString("cbConstructionDoneWithoutMap").equals("Construction done without Map")) {
                                cbConstructionDoneWithoutMap.setChecked(true);
                            } else {
                                cbConstructionDoneWithoutMap.setChecked(false);
                            }
                            if (obj.getString("cbConstructionNotAsPer").equals("Construction not as per approved Map")) {
                                cbConstructionNotAsPer.setChecked(true);
                            } else {
                                cbConstructionNotAsPer.setChecked(false);
                            }
                            if (obj.getString("cbExtraCovered").equals("Joined adjacent property")) {
                                cbExtraCovered.setChecked(true);
                            } else {
                                cbExtraCovered.setChecked(false);
                            }
                            if (obj.getString("cbJoinedAdjacent").equals("Setback portion covered by construction")) {
                                cbJoinedAdjacent.setChecked(true);
                            } else {
                                cbJoinedAdjacent.setChecked(false);
                            }
                            if (obj.getString("cbSetbackPortion").equals("Encroached adjacent area illegally")) {
                                cbSetbackPortion.setChecked(true);
                            } else {
                                cbSetbackPortion.setChecked(false);
                            }
                            if (obj.getString("cbEnroachedAdjacent").equals("Encroached adjacent area illegally")) {
                                cbEnroachedAdjacent.setChecked(true);
                            } else {
                                cbEnroachedAdjacent.setChecked(false);
                            }
                            if (obj.getString("cbExtentConstructionWithoutMap").equals("Construction without Map")) {
                                cbExtentConstructionWithoutMap.setChecked(true);
                            } else {
                                cbExtentConstructionWithoutMap.setChecked(false);
                            }
                            if (obj.getString("cbExtentExtraCoverageWithin").equals("Extra coverage within permissible FAR limits")) {
                                cbExtentExtraCoverageWithin.setChecked(true);
                            } else {
                                cbExtentExtraCoverageWithin.setChecked(false);
                            }
                            if (obj.getString("cbExtentExtraCoverageOut").equals("Extra coverage out of permissible FAR limits")) {
                                cbExtentExtraCoverageOut.setChecked(true);
                            } else {
                                cbExtentExtraCoverageOut.setChecked(false);
                            }
                            if (obj.getString("cbExtentCondonableStructural").equals("Ground coverage out of permissible limits")) {
                                cbExtentCondonableStructural.setChecked(true);
                            } else {
                                cbExtentCondonableStructural.setChecked(false);
                            }
                            if (obj.getString("cbExtentNonCondonableStructural").equals("Non Condonable Structural Design changes")) {
                                cbExtentNonCondonableStructural.setChecked(true);
                            } else {
                                cbExtentNonCondonableStructural.setChecked(false);
                            }

                            //  if (llReasonForHalf.getVisibility() == View.VISIBLE)
                            {

                                try {
                                    if (obj.has("radioButtonHalfSurvey"))
                                        if (!(obj.getString("radioButtonHalfSurvey").equalsIgnoreCase("")))
                                            ((RadioButton) rgReasonForHalfSurvey.getChildAt(Integer.parseInt(obj.getString("radioButtonHalfSurvey")))).setChecked(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

/*
                    if (llReasonForHalf.getVisibility() == View.VISIBLE) {
                        if (obj.getString("radioButtonHalfSurvey").equals("Property was locked")) {
                            ((RadioButton) rgReasonForHalfSurvey.getChildAt(0)).setChecked(true);
                        } else if (obj.getString("radioButtonHalfSurvey").equals("Possessee didn’t allow to inspect the property")) {
                            ((RadioButton) rgReasonForHalfSurvey.getChildAt(1)).setChecked(true);
                        } else if (obj.getString("radioButtonHalfSurvey").equals("NPA property so owner were hostile and survey couldn’t be carried out")) {
                            ((RadioButton) rgReasonForHalfSurvey.getChildAt(2)).setChecked(true);
                        } else if (obj.getString("radioButtonHalfSurvey").equals("Under construction property")) {
                            ((RadioButton) rgReasonForHalfSurvey.getChildAt(3)).setChecked(true);
                        } else if (obj.getString("radioButtonHalfSurvey").equals("Very large irregular Property not possible to measure entire area")) {
                            ((RadioButton) rgReasonForHalfSurvey.getChildAt(4)).setChecked(true);
                        } else if (obj.getString("radioButtonHalfSurvey").equals("Any other Reason")) {
                            ((RadioButton) rgReasonForHalfSurvey.getChildAt(5)).setChecked(true);
                        }
                    }
*/

                            if (obj.getString("cb_summery1").equals("Checked from schedule of the properties mentioned in the deed")) {

                                checkSummery = 0;

                                cb_summery1.setChecked(true);
                            } else {
                                cb_summery1.setChecked(false);

                            }
                            if (obj.getString("cb_summery2").equals("Checked from name plate displayed on the property")) {
                                checkSummery = 0;

                                cb_summery2.setChecked(true);
                            } else {
                                cb_summery2.setChecked(false);
                            }
                            if (obj.getString("cb_summery3").equals("Checked from area measurement of the property")) {
                                checkSummery = 0;

                                cb_summery3.setChecked(true);
                            } else {
                                cb_summery3.setChecked(false);
                            }
                            if (obj.getString("cb_summery4").equals("Identified by the owner/ owner representative")) {
                                checkSummery = 0;

                                cb_summery4.setChecked(true);
                            } else {
                                cb_summery4.setChecked(false);
                            }

                            if (obj.getString("cb_summery5").equals("Enquired from nearby people")) {
                                checkSummery = 0;

                                cb_summery5.setChecked(true);
                            } else {
                                cb_summery5.setChecked(false);
                            }

                            if (obj.getString("cb_summery6").contains("Identification of the property could not be done properly")) {
                                checkSummery = 0;

                                cb_summery6.setChecked(true);
                            } else {
                                cb_summery6.setChecked(false);
                            }

                            try {
                                ((RadioButton) rgPropertyShownBy.getChildAt(Integer.parseInt(obj.getString("radioButtonPropertyShownBy")))).setChecked(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                   /* if (obj.getString("cb_summery7").equals("Survey was not done")) {
                        cb_summery7.setChecked(true);
                    } else {
                        cb_summery7.setChecked(false);
                    }*/


                    /*if (obj.getString("radioButtonSurveyType").equals("Full detailed survey (inside-out with full measurement & photographs)")) {
                        ((RadioButton) rgSurveyType.getChildAt(0)).setChecked(true);
                    } else if (obj.getString("radioButtonSurveyType").equals("Full survey (inside-out with sample random measurements & photographs)")) {
                        ((RadioButton) rgSurveyType.getChildAt(1)).setChecked(true);
                    } else if (obj.getString("radioButtonSurveyType").equals("Half Survey (Sample random measurements from outside & photographs)")) {
                        ((RadioButton) rgSurveyType.getChildAt(2)).setChecked(true);
                    } else if (obj.getString("radioButtonSurveyType").equals("Only photographs taken (No measurements)")) {
                        ((RadioButton) rgSurveyType.getChildAt(3)).setChecked(true);
                    }*/

                            try {
                                if (obj.has("radioButtonSurveyType"))
                                    if (!(obj.getString("radioButtonSurveyType").equalsIgnoreCase("")))
                                        ((RadioButton) rgSurveyType.getChildAt(Integer.parseInt(obj.getString("radioButtonSurveyType")))).setChecked(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                encodedString1 = obj.getString("imageEast");

                                byte[] byteFormat = Base64.decode(obj.getString("imageEast"), Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                ivEast.setImageBitmap(decodedImage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                encodedString2 = obj.getString("imageWest");

                                byte[] byteFormat = Base64.decode(obj.getString("imageWest"), Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                ivWest.setImageBitmap(decodedImage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                encodedString3 = obj.getString("imageNorth");

                                byte[] byteFormat = Base64.decode(obj.getString("imageNorth"), Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                ivNorth.setImageBitmap(decodedImage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                encodedString4 = obj.getString("imageSouth");

                                byte[] byteFormat = Base64.decode(obj.getString("imageSouth"), Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                ivSouth.setImageBitmap(decodedImage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (obj.has("imageCam1")) {
                                    encodedString5 = obj.getString("imageCam1");
                                    byte[] byteFormat = Base64.decode(obj.getString("imageCam1"), Base64.DEFAULT);
                                    Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                    ivCameraCapture1.setImageBitmap(decodedImage);
                                    ivCameraCapture1.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (obj.has("imageCam2")) {
                                    encodedString6 = obj.getString("imageCam2");
                                    byte[] byteFormat = Base64.decode(obj.getString("imageCam2"), Base64.DEFAULT);
                                    Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                    ivCameraCapture2.setImageBitmap(decodedImage);
                                    ivCameraCapture2.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


               /*     if (obj.getString("radioButtonSurveyType").equals("Full detailed survey (inside-out with full measurement & photographs)")) {
                        ((RadioButton) rgSurveyType.getChildAt(0)).setChecked(true);
                    } else if (obj.getString("radioButtonSurveyType").equals("Full survey (inside-out with sample random measurements & photographs)")) {
                        ((RadioButton) rgSurveyType.getChildAt(1)).setChecked(true);
                    } else if (obj.getString("radioButtonSurveyType").equals("Half Survey (Sample random measurements from outside & photographs)")) {
                        ((RadioButton) rgSurveyType.getChildAt(2)).setChecked(true);
                    } else if (obj.getString("radioButtonSurveyType").equals("Only photographs taken (No measurements)")) {
                        ((RadioButton) rgSurveyType.getChildAt(3)).setChecked(true);
                    }
*/

                            photographsList.clear();
                            dynamicPhotographsList.clear();
                            countPhoto = 0;
                            JSONArray jsonArrayDyn = new JSONArray(obj.getString("dynamicPhotographsArray"));

                            //    Log.v("asfazxvsvx",jsonArrayDyn.toString());

                            for (int k = 0; k < jsonArrayDyn.length(); k++) {

                                dynamicPhotographsList.add(jsonArrayDyn.getString(k));

                                countPhoto = k + 1;

                                setLayout();

                                setLayoutBackground(jsonArrayDyn.getString(k), llTemp);

                            }

                            try {
                                JSONArray jsonArrayExtDyn = new JSONArray(obj.getString("dynamicExtPhotographsArray"));

                                //    Log.v("asfazxvsvx",jsonArrayDyn.toString());

                                for (int k = 0; k < jsonArrayExtDyn.length(); k++) {

                                    dynamicExtPhotographsList.add(jsonArrayExtDyn.getString(k));

                                    countExtPhoto = k + 1;

                                    setExtLayout();

                                    setLayoutBackground(jsonArrayExtDyn.getString(k), llTemp2);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //Log.v("asfazxvsvxdddddddddd",dynamicPhotographsList.toString());

                            JSONArray jsonArray = new JSONArray(obj.getString("photographsArray"));

                            for (int k = 0; k < jsonArray.length(); k++) {

                                try {
                                    photographsList.add(jsonArray.getString(k));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (jsonArray.getString(k).contains("QuesA1")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_selfieone);

                                } else if (jsonArray.getString(k).contains("QuesA2")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_selfietwo);

                                } else if (jsonArray.getString(k).contains("QuesA3")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_selfiethree);

                                } else if (jsonArray.getString(k).contains("QuesB1")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_plateone);

                                } else if (jsonArray.getString(k).contains("QuesB2")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_platetwo);

                                } else if (jsonArray.getString(k).contains("QuesB3")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_platethree);

                                } else if (jsonArray.getString(k).contains("QuesC1")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_approachone);

                                } else if (jsonArray.getString(k).contains("QuesC2")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_approachtwo);

                                } else if (jsonArray.getString(k).contains("QuesC3")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_approachthree);

                                } else if (jsonArray.getString(k).contains("QuesD1")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_roadone);

                                } else if (jsonArray.getString(k).contains("QuesD2")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_roadtwo);

                                } else if (jsonArray.getString(k).contains("QuesD3")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_roadthree);

                                } else if (jsonArray.getString(k).contains("QuesE1")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_photoone);

                                } else if (jsonArray.getString(k).contains("QuesE2")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_phototwo);

                                } else if (jsonArray.getString(k).contains("QuesE3")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_photothree);

                                } else if (jsonArray.getString(k).contains("QuesF1")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Ephotoone);

                                } else if (jsonArray.getString(k).contains("QuesF2")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Ephototwo);

                                } else if (jsonArray.getString(k).contains("QuesF3")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Ephotothree);

                                } else if (jsonArray.getString(k).contains("QuesF4")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Ephotofour);

                                } else if (jsonArray.getString(k).contains("QuesF5")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Ephotofive);

                                } else if (jsonArray.getString(k).contains("QuesF6")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Ephotosix);

                                } else if (jsonArray.getString(k).contains("QuesF7")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Ephotoseven);

                                } else if (jsonArray.getString(k).contains("QuesG1")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Mgateone);

                                } else if (jsonArray.getString(k).contains("QuesG2")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Mgatetwo);

                                } else if (jsonArray.getString(k).contains("QuesG3")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Mgatethree);

                                } else if (jsonArray.getString(k).contains("QuesG4")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Mgatefour);

                                } else if (jsonArray.getString(k).contains("QuesG5")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Mgatefive);

                                } else if (jsonArray.getString(k).contains("QuesH1")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Iphotoone);

                                } else if (jsonArray.getString(k).contains("QuesH2")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Iphototwo);

                                } else if (jsonArray.getString(k).contains("QuesH3")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Iphotothree);

                                } else if (jsonArray.getString(k).contains("QuesH4")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Iphotofour);

                                } else if (jsonArray.getString(k).contains("QuesH5")) {

                                    setLayoutBackground(jsonArray.getString(k), ll_Iphotofive);

                                }

                            }

                            try {
                                if (!jsonObject.getString("East").equalsIgnoreCase("")) {
                                    et_one.setText(jsonObject.getString("East"));
                                    et_one2.setText(jsonObject.getString("West"));
                                    et_one3.setText(jsonObject.getString("North"));
                                    et_one4.setText(jsonObject.getString("South"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    /*et_one.setText(obj.getString("et_one"));
                    et_one2.setText(obj.getString("et_one2"));
                    et_one3.setText(obj.getString("et_one3"));
                    et_one4.setText(obj.getString("et_one4"));*/

                            try {
                                etLesseeName.setText(obj.getString("lesseeName"));
                                etLesseeContactNumber.setText(obj.getString("lesseeContact"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (jsonObject.getString("site_inspectior_type").equalsIgnoreCase("1")) {
                                try {
                                    shownByImage = obj.getString("shownByImage");
                                    byte[] byteFormat = Base64.decode(obj.getString("shownByImage"), Base64.DEFAULT);
                                    Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                    ivCameraLetterImage.setImageBitmap(decodedImage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (jsonObject.getString("site_inspectior_type").equalsIgnoreCase("2")) {

                                try {
                                    shownByImage = obj.getString("shownByImage");
                                    byte[] byteFormat = Base64.decode(obj.getString("shownByImage"), Base64.DEFAULT);
                                    Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                    ivCameraLetterImageRep.setImageBitmap(decodedImage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (survType.equalsIgnoreCase("vacantLand")) {
                        ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                    }
                    if (survType.equalsIgnoreCase("MultiStoried")) {
                        ((RadioButton) rgCoveredBuiltup.getChildAt(1)).setChecked(true);
                    }
                }

                if (jsonObject.getString("approval_status").equalsIgnoreCase("1")) {
                    footer.setBackgroundColor(getResources().getColor(R.color.progressgrey));
                    footer.setClickable(false);
                    tvNext.setTextColor(getResources().getColor(R.color.black));
                } else {
                    footer.setBackgroundColor(getResources().getColor(R.color.app_header));
                    footer.setClickable(true);
                    tvNext.setTextColor(getResources().getColor(R.color.white));
                }

                landmark = jsonObject.getString("landmark");

                try {
                    Lat = Double.valueOf(pref.get(Constants.landmark_lat));
                    Lng = Double.valueOf(pref.get(Constants.landmark_long));

                    setLandMark();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!jsonObject.getString("mail_reply").equalsIgnoreCase("")) {
                    String text = "Identification of the property could not be done properly";

                    cb_summery6.setText(text + " View Reply");

                    //   tv_2.setGravity(Gravity.CENTER);

                    SpannableString ss = new SpannableString(cb_summery6.getText().toString());
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View textView) {

                            try {
                                textView.cancelPendingInputEvents();
                                AlertViewReply(jsonObject.getString("mail_reply"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                            ds.setColor(getResources().getColor(R.color.app_header));
                        }
                    };
                    ss.setSpan(clickableSpan, cb_summery6.getText().toString().indexOf("View Reply"),
                            cb_summery6.getText().toString().indexOf("View Reply") + String.valueOf("View Reply").length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    cb_summery6.setText(ss);
                    cb_summery6.setMovementMethod(LinkMovementMethod.getInstance());

                }

                if (jsonObject.getString("site_inspectior_type").equalsIgnoreCase("1")) {
                    etName.setText(jsonObject.getString("co_person_name"));
                    etContactNumber.setText(jsonObject.getString("co_person_number"));

                    etCoPersonEmail.setText(jsonObject.getString("co_person_email"));
                    etCoPersonRelation.setText(jsonObject.getString("co_person_relation"));

                  /*  if (!jsonObject.getString("auth_letter").equalsIgnoreCase("")){
                        ivCameraLetter.setVisibility(View.GONE);
                        Picasso.with(mActivity).load(jsonObject.getString("auth_letter")).into(ivCameraLetterImage);
                    }*/

                    ((RadioButton) rgPropertyShownBy.getChildAt(0)).setChecked(true);
                } else if (jsonObject.getString("site_inspectior_type").equalsIgnoreCase("2")) {
                    etNameRep.setText(jsonObject.getString("co_person_name"));
                    etContactNumberRep.setText(jsonObject.getString("co_person_number"));
                    etCoPersonEmailRep.setText(jsonObject.getString("co_person_email"));
                    etCoPersonRelationRep.setText(jsonObject.getString("co_person_relation"));

                    if (!jsonObject.getString("auth_letter").equalsIgnoreCase("") &&
                            !jsonObject.getString("auth_letter").equalsIgnoreCase(null)) {
                        //   llPropertyAuthLetterRep.setVisibility(View.VISIBLE);
                        llPropertyAuthLetterRep.setVisibility(View.GONE);
                        authLetter = jsonObject.getString("auth_letter");
                        // ivAuthLetterRep.setVisibility(View.VISIBLE);
                        // Picasso.with(mActivity).load(jsonObject.getString("auth_letter")).into(ivAuthLetterRep);
                    }

                    ((RadioButton) rgPropertyShownBy.getChildAt(1)).setChecked(true);
                } else if (jsonObject.getString("site_inspectior_type").equalsIgnoreCase("3")) {
                    ((RadioButton) rgPropertyShownBy.getChildAt(2)).setChecked(true);
                }

            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();
            }
            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            loader.cancel();
        }

        setValue();
    }

    private void setValue() {

        if (!cb_summery1.isChecked() &&
                !cb_summery2.isChecked() &&
                !cb_summery3.isChecked() &&
                !cb_summery4.isChecked() &&
                !cb_summery5.isChecked()) {
            cb_summery6.setChecked(true);
        }

    }

    private void Hit_PostBasicInfo_Api() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.PostBasicSurveyInfo);
        Log.v("Hit_PostBasicInfo_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));

            if (llPropertyName.getVisibility() == View.VISIBLE) {
                jsonObject.put("co_person_name", etName.getText().toString());
                jsonObject.put("co_person_number", etContactNumber.getText().toString());
                jsonObject.put("co_person_email", etCoPersonEmail.getText().toString());
                jsonObject.put("co_person_relation", etCoPersonRelation.getText().toString());
            } else if (llPropertyNameRep.getVisibility() == View.VISIBLE) {
                jsonObject.put("co_person_name", etNameRep.getText().toString());
                jsonObject.put("co_person_number", etContactNumberRep.getText().toString());
                jsonObject.put("co_person_email", etCoPersonEmailRep.getText().toString());
                jsonObject.put("co_person_relation", etCoPersonRelationRep.getText().toString());
            } else {
                jsonObject.put("co_person_name", "");
                jsonObject.put("co_person_number", "");
                jsonObject.put("co_person_email", "");
                jsonObject.put("co_person_relation", "");
            }


            jsonObject.put("site_presence", sitePresence);

            jsonObject.put("value", jsonArrayData);

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
                        parseJsonPost(response);
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
                            //   Toast.makeText(MapLocatorActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonPost(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                ((Dashboard) mActivity).displayView(5);

             /*   intent = new Intent(mActivity, Survey_typeActivitiy.class);
                startActivity(intent);*/

            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();
            }
            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            loader.cancel();
        }
    }

    public int directionChooser(String value) {
        switch (value) {
            case "(E)  East": {
                return 0;
            }
            case "(W)  West": {
                return 1;
            }
            case "(N)  North": {
                return 2;
            }
            case "(S)  South": {
                return 3;
            }
            case "(N/E) North-East": {
                return 4;
            }
            case "(S/E) South-East": {
                return 5;
            }
            case "(N/W) North-West": {
                return 6;
            }
            case "(S/W) South-West": {
                return 7;
            }
        }
        return 0;
    }

    public void putintoHashMap() {

        alist.clear();

        hm.put("imageEast", encodedString1);
        hm.put("imageWest", encodedString2);
        hm.put("imageNorth", encodedString3);
        hm.put("imageSouth", encodedString4);
        hm.put("imageCam1", encodedString5);
        hm.put("imageCam2", encodedString6);
        hm.put("shape_selected", shapeSelected);
        hm.put("sideALength", Double.toString(sideA));
        hm.put("sideBLength", Double.toString(sideB));
        hm.put("sideCLength", Double.toString(sideC));
        hm.put("sideDLength", Double.toString(sideD));
        hm.put("sideHLength", Double.toString(sideH));
        hm.put("titleDiffPercent", Double.toString(titleDiffPercent));
        hm.put("mapDiffPercent", Double.toString(mapDiffPercent));
        hm.put("coveredDiffPercent", Double.toString(coveredDiffPercent));
        hm.put("mapCovDiffPercent", Double.toString(mapCovDiffPercent));
        pref.set(Constants.selectedshape, shapeSelected);
        pref.set(Constants.titleDiffPercent, Double.toString(titleDiffPercent));
        pref.set(Constants.mapDiffPercent, Double.toString(mapDiffPercent));
        pref.set(Constants.diffReason, et_reason.getText().toString());


        pref.set(Constants.covtitleDiffPercent, Double.toString(coveredDiffPercent));
        pref.set(Constants.covmapDiffPercent, Double.toString(mapCovDiffPercent));
        pref.set(Constants.covdiffReason, etInCaseOfDifferenceOf2.getText().toString());

        if (etLandAreaAsSiteSurvey.getText().toString().equalsIgnoreCase("")) {
            hm.put("landAreaAsSiteSurvey", "");
            pref.set(Constants.land_area_as_site_survey, "");
        } else {
            pref.set(Constants.land_area_as_site_survey, etLandAreaAsSiteSurvey.getText().toString());
            hm.put("landAreaAsSiteSurvey", etLandAreaAsSiteSurvey.getText().toString());
        }
        pref.commit();

        hm.put("sideAUnit", sideAUnit);

        hm.put("sideADirection", sideADirect);
        hm.put("sideBDirection", sideBDirect);
        hm.put("sideCDirection", sideCDirect);
        hm.put("sideDDirection", sideDDirect);
        hm.put("sideHDirection", sideHDirect);
        Log.v("shape_selected", shapeSelected);

        hm.put("tv_camera1", tv_camera1.getText().toString());
        hm.put("selected_direction", selected_direction);
        hm.put("tv_camera2", tv_camera2.getText().toString());
        hm.put("etname", etname.getText().toString());
        hm.put("etnumber", etnumber.getText().toString());

        hm.put("et_one", et_one.getText().toString());
        hm.put("et_two", et_two.getText().toString());
        hm.put("et_three", et_three.getText().toString());
        // hm.put("et_four", et_four.getText().toString());

        hm.put("et_one2", et_one2.getText().toString());
        hm.put("et_two2", et_two2.getText().toString());
        hm.put("et_three2", et_three2.getText().toString());
        // hm.put("et_four2", et_four2.getText().toString());

        hm.put("et_one3", et_one3.getText().toString());
        hm.put("et_two3", et_two3.getText().toString());
        hm.put("et_three3", et_three3.getText().toString());

        hm.put("et_one4", et_one4.getText().toString());
        hm.put("et_two4", et_two4.getText().toString());
        hm.put("et_three4", et_three4.getText().toString());

        hm.put("etDirectionEast", etDirectionEast.getText().toString());
        hm.put("etDirectionWest", etDirectionWest.getText().toString());
        hm.put("etDirectionNorth", etDirectionNorth.getText().toString());
        hm.put("etDirectionSouth", etDirectionSouth.getText().toString());

        hm.put("selected_landarea", selected_landarea);
        hm.put("et_reason", et_reason.getText().toString());
        hm.put("et_coverage", et_coverage.getText().toString());
        hm.put("selected_buildup", selected_buildup);
        hm.put("selected_document", selected_document);
        hm.put("selected_enquiry", selected_enquiry);

        hm.put("etCoveredAreaAsPerTitle", etCoveredAreaAsPerTitle.getText().toString());
        hm.put("etCoveredAreaAsPerMap", etCoveredAreaAsPerMap.getText().toString());
        hm.put("etCoveredAreaAsSiteSurvey", etCoveredAreaAsSiteSurvey.getText().toString());
        hm.put("etCoveredAreaEast", etCoveredAreaEast.getText().toString());
        hm.put("etCoveredAreaWest", etCoveredAreaWest.getText().toString());
        hm.put("etCoveredAreaNorth", etCoveredAreaNorth.getText().toString());
        hm.put("etCoveredAreaSouth", etCoveredAreaSouth.getText().toString());
        hm.put("etCoveredFrontsetback", etCoveredFrontsetback.getText().toString());
        hm.put("etCoveredBackSetback", etCoveredBackSetback.getText().toString());
        hm.put("etCoveredRightSideSetback", etCoveredRightSideSetback.getText().toString());
        hm.put("etCoveredLeftSideSetback", etCoveredLeftSideSetback.getText().toString());
        hm.put("etInCaseOfDifferenceOf2", etInCaseOfDifferenceOf2.getText().toString());
        hm.put("etVisImportant", etInCaseOfDifferenceOf2.getText().toString());
        pref.set(Constants.etCoveredAreaAsPerTitle, etCoveredAreaAsPerTitle.getText().toString());
        pref.set(Constants.etCoveredAreaAsPerMap, etCoveredAreaAsPerMap.getText().toString());
        pref.set(Constants.etCoveredAreaAsSiteSurvey, etCoveredAreaAsSiteSurvey.getText().toString());
        pref.set(Constants.etCoveredAreaEast, etCoveredAreaEast.getText().toString());
        pref.set(Constants.etCoveredAreaWest, etCoveredAreaWest.getText().toString());
        pref.set(Constants.etCoveredAreaNorth, etCoveredAreaNorth.getText().toString());
        pref.set(Constants.etCoveredAreaSouth, etCoveredAreaSouth.getText().toString());
        pref.set(Constants.etCoveredFrontsetback, etCoveredFrontsetback.getText().toString());
        pref.set(Constants.etCoveredBackSetback, etCoveredBackSetback.getText().toString());
        pref.set(Constants.etCoveredRightSideSetback, etCoveredRightSideSetback.getText().toString());
        pref.set(Constants.etCoveredLeftSideSetback, etCoveredLeftSideSetback.getText().toString());
        pref.set(Constants.etInCaseOfDifferenceOf2, etInCaseOfDifferenceOf2.getText().toString());
        pref.set(Constants.etGroundCoverage, etGroundCoverage.getText().toString());
        pref.set(Constants.etInCaseOfDifferenceOf2, etInCaseOfDifferenceOf2.getText().toString());
        if (selected_buildup.equalsIgnoreCase("Applicable"))
            pref.set(Constants.coveredAreaVal, "Applicable");
        else
            pref.set(Constants.coveredAreaVal, "Not Applicable");
        if (selected_landarea.equalsIgnoreCase("Applicable"))
            pref.set(Constants.landAreaVal, "Applicable");
        else
            pref.set(Constants.landAreaVal, "Not Applicable");

        pref.commit();

        if (cbItIsAFlat.isChecked()) {
            hm.put("cbItIsAFlat", cbItIsAFlat.getText().toString());
        } else {
            hm.put("cbItIsAFlat", "");

        }

        if (cbPropertyWasLocked.isChecked()) {
            hm.put("cbPropertyWasLocked", cbPropertyWasLocked.getText().toString());
        } else {
            hm.put("cbPropertyWasLocked", "");

        }

        if (cbOwnerPossessee.isChecked()) {
            hm.put("cbOwnerPossessee", cbOwnerPossessee.getText().toString());
        } else {
            hm.put("cbOwnerPossessee", "");

        }

        if (cbNpaProperty.isChecked()) {
            hm.put("cbNpaProperty", cbNpaProperty.getText().toString());
        } else {
            hm.put("cbNpaProperty", "");

        }

        if (cbVeryLargeIrregular.isChecked()) {
            hm.put("cbVeryLargeIrregular", cbVeryLargeIrregular.getText().toString());
        } else {
            hm.put("cbVeryLargeIrregular", "");

        }

        if (cbAnyOtherReason.isChecked()) {
            hm.put("cbAnyOtherReason", cbAnyOtherReason.getText().toString());
        } else {
            hm.put("cbAnyOtherReason", "");

        }


        if (cbConstructionDoneWithoutMap.isChecked()) {
            hm.put("cbConstructionDoneWithoutMap", cbConstructionDoneWithoutMap.getText().toString());
        } else {
            hm.put("cbConstructionDoneWithoutMap", "");

        }

        if (cbConstructionNotAsPer.isChecked()) {
            hm.put("cbConstructionNotAsPer", cbConstructionNotAsPer.getText().toString());
        } else {
            hm.put("cbConstructionNotAsPer", "");
        }

        if (cbExtraCovered.isChecked()) {
            hm.put("cbExtraCovered", cbExtraCovered.getText().toString());
        } else {
            hm.put("cbExtraCovered", "");
        }

        if (cbJoinedAdjacent.isChecked()) {
            hm.put("cbJoinedAdjacent", cbJoinedAdjacent.getText().toString());
        } else {
            hm.put("cbJoinedAdjacent", "");
        }

        if (cbSetbackPortion.isChecked()) {
            hm.put("cbSetbackPortion", cbSetbackPortion.getText().toString());
        } else {
            hm.put("cbSetbackPortion", "");
        }

        if (cbEnroachedAdjacent.isChecked()) {
            hm.put("cbEnroachedAdjacent", cbEnroachedAdjacent.getText().toString());
        } else {
            hm.put("cbEnroachedAdjacent", "");
        }

        if (cbExtentConstructionWithoutMap.isChecked()) {
            hm.put("cbExtentConstructionWithoutMap", cbExtentConstructionWithoutMap.getText().toString());
        } else {
            hm.put("cbExtentConstructionWithoutMap", "");
        }

        if (cbExtentExtraCoverageWithin.isChecked()) {
            hm.put("cbExtentExtraCoverageWithin", cbExtentExtraCoverageWithin.getText().toString());
        } else {
            hm.put("cbExtentExtraCoverageWithin", "");
        }

        if (cbExtentExtraCoverageWithin.isChecked()) {
            hm.put("cbExtentExtraCoverageWithin", cbExtentExtraCoverageWithin.getText().toString());
        } else {
            hm.put("cbExtentExtraCoverageWithin", "");
        }

        if (cbExtentExtraCoverageOut.isChecked()) {
            hm.put("cbExtentExtraCoverageOut", cbExtentExtraCoverageOut.getText().toString());
        } else {
            hm.put("cbExtentExtraCoverageOut", "");
        }

        if (cbExtentCondonableStructural.isChecked()) {
            hm.put("cbExtentCondonableStructural", cbExtentCondonableStructural.getText().toString());
        } else {
            hm.put("cbExtentCondonableStructural", "");
        }

        if (cbExtentNonCondonableStructural.isChecked()) {
            hm.put("cbExtentNonCondonableStructural", cbExtentNonCondonableStructural.getText().toString());
        } else {
            hm.put("cbExtentNonCondonableStructural", "");
        }


        if (cb_summery1.isChecked()) {
            hm.put("cb_summery1", cb_summery1.getText().toString());
        } else {
            hm.put("cb_summery1", "");
        }
        if (cb_summery2.isChecked()) {
            hm.put("cb_summery2", cb_summery2.getText().toString());
        } else {
            hm.put("cb_summery2", "");
        }
        if (cb_summery3.isChecked()) {
            hm.put("cb_summery3", cb_summery3.getText().toString());
        } else {
            hm.put("cb_summery3", "");
        }
        if (cb_summery4.isChecked()) {
            hm.put("cb_summery4", cb_summery4.getText().toString());
        } else {
            hm.put("cb_summery4", "");
        }
        if (cb_summery5.isChecked()) {
            hm.put("cb_summery5", cb_summery5.getText().toString());
        } else {
            hm.put("cb_summery5", "");
        }
        if (cb_summery6.isChecked()) {
            hm.put("cb_summery6", cb_summery6.getText().toString());
        } else {
            hm.put("cb_summery6", "");
        }

        /*if (cb_summery7.isChecked()) {
            hm.put("cb_summery7", cb_summery7.getText().toString());
        } else {
            hm.put("cb_summery7", "");
        }*/

        try {
            if (rgNamePlate.getCheckedRadioButtonId() != -1) {
                int selectedIdSurveyType = rgNamePlate.getCheckedRadioButtonId();
                View radioButtonSurveyType = v.findViewById(selectedIdSurveyType);
                int idx2 = rgNamePlate.indexOfChild(radioButtonSurveyType);
                RadioButton r2 = (RadioButton) rgNamePlate.getChildAt(idx2);
                String selectedText2 = r2.getText().toString();
                hm.put("selected_property", String.valueOf(idx2));
            } else {
                hm.put("selected_property", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (rg_measurement.getCheckedRadioButtonId() != -1) {
                int selectedIdSurveyType = rg_measurement.getCheckedRadioButtonId();
                View radioButtonSurveyType = v.findViewById(selectedIdSurveyType);
                int idx2 = rg_measurement.indexOfChild(radioButtonSurveyType);
                RadioButton r2 = (RadioButton) rg_measurement.getChildAt(idx2);
                // String selectedText2 = r2.getText().toString();
                hm.put("selected_measurement_area", String.valueOf(idx2));
            } else {
                hm.put("selected_measurement_area", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (rg_document.getCheckedRadioButtonId() != -1) {
                int selectedIdSurveyType = rg_document.getCheckedRadioButtonId();
                View radioButtonSurveyType = v.findViewById(selectedIdSurveyType);
                int idx2 = rg_document.indexOfChild(radioButtonSurveyType);
                RadioButton r2 = (RadioButton) rg_document.getChildAt(idx2);
                String selectedText2 = r2.getText().toString();
                hm.put("selected_measurement", String.valueOf(idx2));
            } else {
                hm.put("selected_measurement", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (rg_boundries.getCheckedRadioButtonId() != -1) {
                int selectedIdSurveyType = rg_boundries.getCheckedRadioButtonId();
                View radioButtonSurveyType = v.findViewById(selectedIdSurveyType);
                int idx2 = rg_boundries.indexOfChild(radioButtonSurveyType);
                RadioButton r2 = (RadioButton) rg_boundries.getChildAt(idx2);
                String selectedText2 = r2.getText().toString();
                hm.put("selected_boundries", String.valueOf(idx2));
            } else {
                hm.put("selected_boundries", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if (rgSurveyType.getCheckedRadioButtonId() != -1) {
                int selectedIdSurveyType = rgSurveyType.getCheckedRadioButtonId();
                View radioButtonSurveyType = v.findViewById(selectedIdSurveyType);
                int idx2 = rgSurveyType.indexOfChild(radioButtonSurveyType);
                RadioButton r2 = (RadioButton) rgSurveyType.getChildAt(idx2);
                String selectedText2 = r2.getText().toString();
                hm.put("radioButtonSurveyType", String.valueOf(idx2));
            } else {
                hm.put("radioButtonSurveyType", "");
            }

            if (llReasonForHalf.getVisibility() == View.VISIBLE) {
                int selectedIdHalfSurvey = rgReasonForHalfSurvey.getCheckedRadioButtonId();
                View radioButtonHalfSurvey = v.findViewById(selectedIdHalfSurvey);
                int idx3 = rgReasonForHalfSurvey.indexOfChild(radioButtonHalfSurvey);
                RadioButton r3 = (RadioButton) rgReasonForHalfSurvey.getChildAt(idx3);
                //String selectedText3 = r3.getText().toString();
                hm.put("radioButtonHalfSurvey", String.valueOf(idx3));
            } else {
                hm.put("radioButtonHalfSurvey", "");
            }
            for (int i = 0; i < rgPropertyShownBy.getChildCount(); i++) {
                ((RadioButton) rgPropertyShownBy.getChildAt(i)).setEnabled(false);
            }
            if (rgPropertyShownBy.getCheckedRadioButtonId() != -1) {
                int selectedIdPropertyShownBy = rgPropertyShownBy.getCheckedRadioButtonId();
                View radioButtonPropertyShownBy = v.findViewById(selectedIdPropertyShownBy);
                int idx = rgPropertyShownBy.indexOfChild(radioButtonPropertyShownBy);
                //   RadioButton r = (RadioButton) rgPropertyShownBy.getChildAt(idx);
                //  String selectedText = r.getText().toString();
                hm.put("radioButtonPropertyShownBy", String.valueOf(idx));

                if (idx == 2 || idx == 3 || idx == 4 || idx == 5) {
                    hm.put("propertyShownByName", "");
                    hm.put("propertyShownByContact", "");
                } else if (idx == 0) {
                    hm.put("propertyShownByName", etName.getText().toString());
                    hm.put("propertyShownByContact", etContactNumber.getText().toString());
                } else if (idx == 1) {
                    hm.put("propertyShownByName", etNameRep.getText().toString());
                    hm.put("propertyShownByContact", etContactNumberRep.getText().toString());
                }

            } else {
                hm.put("radioButtonPropertyShownBy", "");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        hm.put("videoName", videoName);

        hm.put("photographsArray", String.valueOf(new JSONArray(photographsList)));
        hm.put("dynamicPhotographsArray", String.valueOf(new JSONArray(dynamicPhotographsList)));
        hm.put("dynamicExtPhotographsArray", String.valueOf(new JSONArray(dynamicExtPhotographsList)));

        hm.put("shownByImage", shownByImage);
        hm.put("shownByImage1", shownByImage1);
        hm.put("shownByImage2", shownByImage2);
        hm.put("lesseeName", etLesseeName.getText().toString());
        hm.put("lesseeContact", etLesseeContactNumber.getText().toString());

        alist.add(hm);

        jsonArrayData = new JSONArray(alist);
        Log.v("jsonArrayData++++++", String.valueOf(jsonArrayData));

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
        mMap.getUiSettings().setZoomControlsEnabled(edit_page);
        mMap.getUiSettings().setScrollGesturesEnabled(edit_page);
        mMap.getUiSettings().setMapToolbarEnabled(edit_page);
       /* try {
            setLandMark();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(edit_page);
        uiSettings.setZoomControlsEnabled(edit_page);
        mMap.getUiSettings().setScrollGesturesEnabled(edit_page);

        /*try {
            Lat = Double.valueOf(pref.get(Constants.landmark_lat));
            Lng = Double.valueOf(pref.get(Constants.landmark_long));

            setLandMark();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (edit_page) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);

                    //     pref.get(Constants.)
                    markerOptions.title(pref.get(Constants.case_u_id)).snippet(getAddress(mActivity, latLng.latitude, latLng.longitude));
                    //    markerOptions.title(pref.get(Constants.case_u_id)).snippet(pref.get(Constants.landmark_address));

                    //markerOptions.title(getAddress(mActivity,latLng.latitude,latLng.longitude));
                    //   markerOptions.title(pref.get(Constants.case_u_id)+"\n"+getAddress(mActivity,latLng.latitude,latLng.longitude));
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    //mMap.addMarker(markerOptions).setDraggable(true);

                    //mMap.clear();

                    if (marker != null) {
                        marker.remove();
                    }

                    marker = mMap.addMarker(markerOptions);
                    //  markers.add(marker);

                    // CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), mMap.getCameraPosition().zoom);
                    CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 18f);
                    mMap.animateCamera(cameraUpdate1);

                    Lat = latLng.latitude;
                    Lng = latLng.longitude;
                    Log.v("Latslongs", Double.toString(Lat));
                    Log.v("Latslongs", Double.toString(Lng));

                    pref.set(Constants.landmark_lat, Double.toString(Lat));
                    pref.set(Constants.landmark_long, Double.toString(Lng));
                    pref.commit();
                }
            }

        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(mActivity);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mActivity);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mActivity);
                snippet.setTextColor(Color.BLACK);
                snippet.setText(marker.getSnippet());
                snippet.setGravity(Gravity.CENTER);

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

    }

    private void setLandMark() {
        if (!pref.get(Constants.landmark_lat).isEmpty() &&
                !pref.get(Constants.landmark_long).isEmpty()) {

            LatLng latLng = new LatLng(Lat, Lng);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);

            // markerOptions.title(pref.get(Constants.case_u_id)).snippet(getAddress(mActivity,latLng.latitude,latLng.longitude));
            markerOptions.title(pref.get(Constants.case_u_id)).snippet(landmark);

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);

            geocoder = new Geocoder(mActivity, Locale.getDefault());
            CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 18f);
            mMap.animateCamera(cameraUpdate1);

        }
    }

    //orig_code+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private Bitmap timestampItAndSave(Bitmap bitmap, LinearLayout linearLayout) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        File file = null;

        //   Bitmap bitmap = BitmapFactory.decodeFile(getOutputMediaFile(MEDIA_TYPE_IMAGE).getAbsolutePath());

        //        Bitmap src = BitmapFactory.decodeResource(); // the original file is cuty.jpg i added in resources
        Bitmap dest = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

        Canvas cs = new Canvas(dest);
        Paint tPaint = new Paint();
        tPaint.setTextSize(35);
        tPaint.setColor(Color.BLUE);
        tPaint.setStyle(Paint.Style.FILL);
        //cs.drawBitmap(dest, 0f, 0f, null);
        cs.drawBitmap(bitmap, 0f, 0f, null);
        float height = tPaint.measureText("yY");
        cs.drawText(dateTime, 20f, height + 15f, tPaint);

        //add lat and long in next line
        cs.drawText(String.format("%.3f", location.getLatitude()) + ", "
                + String.format("%.3f", location.getLongitude()), 20f, height + 65f, tPaint);

        try {
            file = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            //   dest.compress(Bitmap.CompressFormat.JPEG, 70, new FileOutputStream(new File("/sdcard/timeStampedImage.jpg")));
            dest.compress(Bitmap.CompressFormat.JPEG, 70, new FileOutputStream(file));
            linearLayout.setTag(dest);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        capturedPath = file.getPath();

        return dest;
    }

    private String timeStampItAndGetPath(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //   Bitmap bitmap = BitmapFactory.decodeFile(getOutputMediaFile(MEDIA_TYPE_IMAGE).getAbsolutePath());

        //        Bitmap src = BitmapFactory.decodeResource(); // the original file is cuty.jpg i added in resources
        Bitmap dest = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        File file = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

        Canvas cs = new Canvas(dest);
        Paint tPaint = new Paint();
        tPaint.setTextSize(35);
        tPaint.setColor(Color.BLUE);
        tPaint.setStyle(Paint.Style.FILL);
        //cs.drawBitmap(dest, 0f, 0f, null);
        cs.drawBitmap(bitmap, 0f, 0f, null);
        float height = tPaint.measureText("yY");
        cs.drawText(dateTime, 20f, height + 15f, tPaint);

        //add lat and long in next line
        cs.drawText(String.format("%.3f", location.getLatitude()) + ", "
                + String.format("%.3f", location.getLongitude()), 20f, height + 65f, tPaint);

        try {
            // file = new File("/sdcard/timeStampedImage.jpg");

            file = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            dest.compress(Bitmap.CompressFormat.JPEG, 70, new FileOutputStream(file));
            // linearLayout.setTag(dest);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return file.getPath();
    }
//    orig_code*********************************************************************

    private int selfiePhotoCount() {

        selfiePhotoCount = 0;

        try {
            if (((ColorDrawable) ll_selfieone.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            selfiePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_selfietwo.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            selfiePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_selfiethree.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {
                selfiePhotoCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.v("asfasasfas", String.valueOf(selfiePhotoCount));

        return selfiePhotoCount;
    }

    private int namePlatePhotoCount() {

        namePlatePhotoCount = 0;

        try {
            if (((ColorDrawable) ll_plateone.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            namePlatePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_platetwo.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            namePlatePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_platethree.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            namePlatePhotoCount++;
            e.printStackTrace();
        }

        Log.v("asfasasfas", String.valueOf(namePlatePhotoCount));

        return namePlatePhotoCount;
    }

    private int leftSidePhotoCount() {

        leftSidePhotoCount = 0;

        try {
            if (((ColorDrawable) ll_approachone.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            leftSidePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_approachtwo.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            leftSidePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_approachthree.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            leftSidePhotoCount++;
            e.printStackTrace();
        }


        Log.v("asfasasfas", String.valueOf(leftSidePhotoCount));

        return leftSidePhotoCount;
    }

    private int rightSidePhotoCount() {

        rightSidePhotoCount = 0;

        try {
            if (((ColorDrawable) ll_roadone.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            rightSidePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_roadtwo.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            rightSidePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_roadthree.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            rightSidePhotoCount++;
            e.printStackTrace();
        }


        Log.v("asfasasfas", String.valueOf(rightSidePhotoCount));

        return rightSidePhotoCount;
    }

    private int ownerPhotoCount() {

        ownerPhotoCount = 0;

        try {
            if (((ColorDrawable) ll_photoone.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            ownerPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_phototwo.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            ownerPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_photothree.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            ownerPhotoCount++;
            e.printStackTrace();
        }

        Log.v("asfasasfas", String.valueOf(ownerPhotoCount));

        return ownerPhotoCount;

    }

    private int extPhotoCount() {

        extPhotoCount = 0;

        try {
            if (((ColorDrawable) ll_Ephotoone.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            extPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Ephototwo.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            extPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Ephotothree.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            extPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Ephotofour.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            extPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Ephotofive.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            extPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Ephotosix.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            extPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Ephotoseven.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            extPhotoCount++;
            e.printStackTrace();
        }

        return extPhotoCount;
    }

    private int mainGatePhotoCount() {

        mainGatePhotoCount = 0;

        try {
            if (((ColorDrawable) ll_Mgateone.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            mainGatePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Mgatetwo.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            mainGatePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Mgatethree.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            mainGatePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Mgatefour.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            mainGatePhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Mgatefive.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            mainGatePhotoCount++;
            e.printStackTrace();
        }

        return mainGatePhotoCount;
    }

    private int intPhotoCount() {

        intPhotoCount = 0;

        try {
            if (((ColorDrawable) ll_Iphotoone.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            intPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Iphototwo.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            intPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Iphotothree.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            intPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Iphotofour.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            intPhotoCount++;
            e.printStackTrace();
        }

        try {
            if (((ColorDrawable) ll_Iphotofive.getBackground()).getColor() !=
                    (getResources().getColor(R.color.light_grey))) {

            }
        } catch (Exception e) {
            intPhotoCount++;
            e.printStackTrace();
        }

        return intPhotoCount;
    }

    private void uploadVideoFile(final String filePath) {

        final String url = Utils.getCompleteApiUrl(mActivity, R.string.UploadPropertyImage);

        Log.v("##url", url);

        try {
            //  loader.show();
            new AsyncTask<String, Void, String>() {
                protected void onPreExecute() {
                    loader.show();
                    //LOOK at Picture #2 for my screen in this state
                }

                protected String doInBackground(String... args) {
                    try {

                        //Your code goes here
                        FileInputStream fis;

                        //     File auxFile = new File(mediaFiles.get(0).getPath());
                        File auxFile = new File(filePath);

                        fis = new FileInputStream(auxFile);

                        filename = filePath.substring(filePath.lastIndexOf("/") + 1);

                        videoName = filename;

                        // HttpFileUploader htfu = new HttpFileUploader("", Media.IMAGE_DIR, filee[0].getName(), UserId, FeedId, Type, Upload_type);
                        //   HttpFileUploader htfu = new HttpFileUploader(url,pref.get(Constants.case_id)+"_"+filename+"_video1");
                        HttpFileUploader htfu = new HttpFileUploader(url, filename);

                        htfu.doStart(fis);

                        String response = htfu.updateCall();

                        Log.d("Response", response);


                        if (response.equalsIgnoreCase("success")) {
                            loader.cancel();
                            if (Utils.isNetworkConnectedMainThred(mActivity)) {

                            } else {

                            }
                            return response;
                        } else {
                            loader.cancel();
                            return response;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loader.cancel();
                        return "Error";
                    }
                }

                protected void onPostExecute() {
                    loader.cancel();

                }
            }.execute();

        } catch (Exception e) {
            loader.cancel();
            e.printStackTrace();
            // receiveError();
        }
    }

    private void uploadPhotoFile(final String filePath) {

        final String url = Utils.getCompleteApiUrl(mActivity, R.string.UploadPropertyImage);

        Log.v("##url", url);

        try {
            //  loader.show();
            new AsyncTask<String, Void, String>() {
                protected void onPreExecute() {
                    loader.show();
                    //LOOK at Picture #2 for my screen in this state
                }

                protected String doInBackground(String... args) {
                    try {

                        //Your code goes here
                        FileInputStream fis;

                        //     File auxFile = new File(mediaFiles.get(0).getPath());
                        File auxFile = new File(filePath);

                        fis = new FileInputStream(auxFile);

                        filename = filePath.substring(filePath.lastIndexOf("/") + 1);

//                    Bitmap bitmap = BitmapFactory.decodeFile (filePath);
//                    bitmap.compress (Bitmap.CompressFormat.JPEG, 70, new FileOutputStream (auxFile)); //1 == originalImage, 2 = 50% compression, 4=25% compress

                        //  videoName = filename;

                        // HttpFileUploader htfu = new HttpFileUploader("", Media.IMAGE_DIR, filee[0].getName(), UserId, FeedId, Type, Upload_type);
                        //   HttpFileUploader htfu = new HttpFileUploader(url,pref.get(Constants.case_id)+"_"+filename+"_video1");
                        //HttpFileUploader htfu = new HttpFileUploader(url,filename);

                        Log.v("#imageName", filename);

                        if (media_status.equalsIgnoreCase("1")) {

                            addPhotoInList("QuesA1", filename);

                      /*  if (photographsList.size()>0){
                            for (int i=0;i<photographsList.size();i++){
                                if (photographsList.get(i).contains("QuesA1")){
                                    photographsList.set(i,filename);
                                    break;
                                }else {
                                    photographsList.add(filename);
                                }
                            }
                        }else {
                            photographsList.add(filename);
                        }*/

                        } else if (media_status.equalsIgnoreCase("2")) {

                            addPhotoInList("QuesA2", filename);

                        } else if (media_status.equalsIgnoreCase("3")) {

                            addPhotoInList("QuesA3", filename);

                        } else if (media_status.equalsIgnoreCase("4")) {

                            addPhotoInList("QuesB1", filename);

                        } else if (media_status.equalsIgnoreCase("5")) {

                            addPhotoInList("QuesB2", filename);

                        } else if (media_status.equalsIgnoreCase("6")) {

                            addPhotoInList("QuesB3", filename);

                        } else if (media_status.equalsIgnoreCase("7")) {

                            addPhotoInList("QuesC1", filename);

                        } else if (media_status.equalsIgnoreCase("8")) {

                            addPhotoInList("QuesC2", filename);

                        } else if (media_status.equalsIgnoreCase("9")) {

                            addPhotoInList("QuesC3", filename);

                        } else if (media_status.equalsIgnoreCase("10")) {

                            addPhotoInList("QuesD1", filename);

                        } else if (media_status.equalsIgnoreCase("11")) {

                            addPhotoInList("QuesD2", filename);

                        } else if (media_status.equalsIgnoreCase("12")) {

                            addPhotoInList("QuesD3", filename);

                        } else if (media_status.equalsIgnoreCase("13")) {

                            addPhotoInList("QuesE1", filename);

                        } else if (media_status.equalsIgnoreCase("14")) {

                            addPhotoInList("QuesE2", filename);

                        } else if (media_status.equalsIgnoreCase("15")) {

                            addPhotoInList("QuesE3", filename);

                        } else if (media_status.equalsIgnoreCase("16")) {

                            addPhotoInList("QuesF1", filename);

                        } else if (media_status.equalsIgnoreCase("17")) {

                            addPhotoInList("QuesF2", filename);

                        } else if (media_status.equalsIgnoreCase("18")) {

                            addPhotoInList("QuesF3", filename);

                        } else if (media_status.equalsIgnoreCase("19")) {

                            addPhotoInList("QuesF4", filename);

                        } else if (media_status.equalsIgnoreCase("20")) {

                            addPhotoInList("QuesG1", filename);

                        } else if (media_status.equalsIgnoreCase("21")) {

                            addPhotoInList("QuesG2", filename);

                        } else if (media_status.equalsIgnoreCase("22")) {

                            addPhotoInList("QuesG3", filename);

                        } else if (media_status.equalsIgnoreCase("23")) {

                            addPhotoInList("QuesG4", filename);

                        } else if (media_status.equalsIgnoreCase("24")) {

                            addPhotoInList("QuesG5", filename);

                        } else if (media_status.equalsIgnoreCase("25")) {

                            addPhotoInList("QuesH1", filename);

                        } else if (media_status.equalsIgnoreCase("26")) {

                            addPhotoInList("QuesH2", filename);

                        } else if (media_status.equalsIgnoreCase("27")) {

                            addPhotoInList("QuesH3", filename);

                        } else if (media_status.equalsIgnoreCase("28")) {

                            addPhotoInList("QuesH4", filename);

                        } else if (media_status.equalsIgnoreCase("29")) {

                            addPhotoInList("QuesH5", filename);

                        } else if (media_status.equalsIgnoreCase("32")) {

                            addPhotoInList("QuesF5", filename);

                        } else if (media_status.equalsIgnoreCase("33")) {

                            //addPhotoInList("QuesExtDyn"+countExtPhoto,filename);
                            addExtDynamicPhotoInList("QuesDyn" + countExtPhoto, filename);

                        } else if (media_status.equalsIgnoreCase("34")) {

                            addPhotoInList("QuesF7", filename);

                        } else if (media_status.equalsIgnoreCase("35")) {

                            addDynamicPhotoInList("QuesDyn" + countPhoto, filename);

                        }

                        HttpFileUploader htfu = new HttpFileUploader(url, auxFile.getName());

                        htfu.doStart(fis);

                        String response = htfu.updateCall();

                        Log.d("Response", response);

                        if (response.equalsIgnoreCase("success")) {
                            loader.cancel();
                            if (Utils.isNetworkConnectedMainThred(mActivity)) {

                            } else {

                            }
                            return response;
                        } else {
                            loader.cancel();
                            return response;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loader.cancel();
                        return "Error";
                    }
                }

                protected void onPostExecute() {
                    loader.cancel();

                }
            }.execute();

        } catch (Exception e) {
            loader.cancel();
            e.printStackTrace();
            // receiveError();
        }
    }

    private void addPhotoInList(String question, String filename) {
        if (photographsList.size() > 0) {
            for (int i = 0; i < photographsList.size(); i++) {
                if (photographsList.get(i).contains(question)) {
                    photographsList.set(i, filename);
                    break;
                } else {
                    photographsList.add(filename);
                }
            }
        } else {
            photographsList.add(filename);
        }
    }

    private void addDynamicPhotoInList(String question, String filename) {

        int c = 0;

        if (dynamicPhotographsList.size() > 0 && dynamicPhotographsList.size() >= countPhoto) {

            Log.d("adadadadquestion", filename);
            Log.d("adadadadrrrrrrr", dynamicPhotographsList.get(countPhoto - 1));

            if (dynamicPhotographsList.get(countPhoto - 1).equalsIgnoreCase(filename)) {
                dynamicPhotographsList.set(countPhoto - 1, filename);
                c = 1;
                //    break;
            } else {
                dynamicPhotographsList.add(filename);
            }
        } else {
            dynamicPhotographsList.add(filename);
        }

        Log.d("ddudadasfaudud", dynamicPhotographsList.toString());

        /*for (int i=0;i<dynamicPhotographsList.size();i++){
            if (dynamicPhotographsList.get(i).equalsIgnoreCase(question)){
                dynamicPhotographsList.set(i,filename);
                c = 1;
                break;
            }
        }*/

        // if(c==0)
       /* {
            dynamicPhotographsList.add(filename);
        }*/
    }

    private void addExtDynamicPhotoInList(String question, String filename) {

        int c = 0;

        if (dynamicExtPhotographsList.size() > 0 && dynamicExtPhotographsList.size() >= countExtPhoto) {

            Log.d("adadadadquestion", filename);
            Log.d("adadadadrrrrrrr", dynamicExtPhotographsList.get(countExtPhoto - 1));

            if (dynamicExtPhotographsList.get(countExtPhoto - 1).equalsIgnoreCase(filename)) {
                dynamicExtPhotographsList.set(countExtPhoto - 1, filename);
                c = 1;
                //    break;
            } else {
                dynamicExtPhotographsList.add(filename);
            }
        } else {
            dynamicExtPhotographsList.add(filename);
        }

        Log.d("ddudadasfaudud", dynamicExtPhotographsList.toString());

        /*for (int i=0;i<dynamicExtPhotographsList.size();i++){
            if (dynamicExtPhotographsList.get(i).equalsIgnoreCase(question)){
                dynamicExtPhotographsList.set(i,filename);
                c = 1;
                break;
            }
        }*/

        // if(c==0)
       /* {
            dynamicExtPhotographsList.add(filename);
        }*/
    }

    public void SquareShapeImagePreview() {
        final Dialog landDialog = new Dialog(mActivity);
        landDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        landDialog.setCancelable(true);
        landDialog.setContentView(R.layout.square_layout_preview);
        Window window = landDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        landDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        landDialog.show();

        ImageView close;
        Button btnSubmit;
        final EditText squareSideA, squareSideB, squareSideC, squareSideD;
        squareSideA = landDialog.findViewById(R.id.et_squareSideA);
        squareSideB = landDialog.findViewById(R.id.et_squareSideB);
        squareSideC = landDialog.findViewById(R.id.et_squareSideC);
        squareSideD = landDialog.findViewById(R.id.et_squareSideD);

        squareSideA.setText("");
        squareSideB.setText("");
        squareSideC.setText("");
        squareSideD.setText("");
        squareSideA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideA.isFocused()) {
                    squareSideB.setText(squareSideA.getText().toString());
                    squareSideC.setText(squareSideA.getText().toString());
                    squareSideD.setText(squareSideA.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        squareSideB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideB.isFocused()) {
                    squareSideA.setText(squareSideB.getText().toString());
                    squareSideC.setText(squareSideB.getText().toString());
                    squareSideD.setText(squareSideB.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        squareSideC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideC.isFocused()) {
                    squareSideB.setText(squareSideC.getText().toString());
                    squareSideA.setText(squareSideC.getText().toString());
                    squareSideD.setText(squareSideC.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        squareSideD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideD.isFocused()) {
                    squareSideB.setText(squareSideD.getText().toString());
                    squareSideC.setText(squareSideD.getText().toString());
                    squareSideA.setText(squareSideD.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        close = landDialog.findViewById(R.id.ivClose);
        btnSubmit = landDialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (squareSideA.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mActivity, "All feilds required", Toast.LENGTH_SHORT).show();
                } else {
                    rl_SideValues.setVisibility(View.VISIBLE);
                    tv_lenghtSideA.setText(squareSideA.getText().toString());
                    tv_lenghtSideB.setText(squareSideA.getText().toString());
                    tv_lenghtSideC.setText(squareSideA.getText().toString());
                    tv_lenghtSideD.setText(squareSideA.getText().toString());
                    sideA = Double.parseDouble(squareSideA.getText().toString());
                    sideB = Double.parseDouble(squareSideA.getText().toString());
                    sideC = Double.parseDouble(squareSideA.getText().toString());
                    sideD = Double.parseDouble(squareSideA.getText().toString());
                    etLandAreaAsSiteSurvey.setText(Double.toString(sideA * sideA));
                    landDialog.dismiss();
                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landDialog.dismiss();
                rl_SideValues.setVisibility(View.GONE);
            }
        });

    }

    public void TriangleShapeImagePreview() {
        final Dialog landDialog = new Dialog(mActivity);
        landDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        landDialog.setCancelable(true);
        landDialog.setContentView(R.layout.triangle_layout_preview);
        Window window = landDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        landDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        landDialog.show();


        ImageView close;
        Button btnSubmit;

        final EditText squareSideA, squareSideB, squareSideC, squareSideH;
        squareSideA = landDialog.findViewById(R.id.et_squareSideA);
        squareSideB = landDialog.findViewById(R.id.et_squareSideB);
        squareSideC = landDialog.findViewById(R.id.et_squareSideC);
        squareSideH = landDialog.findViewById(R.id.et_squareSideH);

        squareSideA.setText("");
        squareSideB.setText("");
        squareSideC.setText("");
        squareSideH.setText("");

        close = landDialog.findViewById(R.id.ivClose);
        btnSubmit = landDialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (squareSideA.getText().toString().equalsIgnoreCase("") || squareSideB.getText().toString().equalsIgnoreCase("") || squareSideC.getText().toString().equalsIgnoreCase("") || squareSideH.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mActivity, "All feilds required", Toast.LENGTH_SHORT).show();
                } else {
                    rl_SideValues.setVisibility(View.VISIBLE);
                    tv_lenghtSideA.setText(squareSideA.getText().toString());
                    tv_lenghtSideB.setText(squareSideB.getText().toString());
                    tv_lenghtSideC.setText(squareSideC.getText().toString());
                    tv_lenghtSideH.setText(squareSideH.getText().toString());
                    sideA = Double.parseDouble(squareSideA.getText().toString());
                    sideB = Double.parseDouble(squareSideB.getText().toString());
                    sideC = Double.parseDouble(squareSideC.getText().toString());
                    sideH = Double.parseDouble(squareSideH.getText().toString());
                    etLandAreaAsSiteSurvey.setText(Double.toString(Double.parseDouble(squareSideB.getText().toString()) * Double.parseDouble(squareSideH.getText().toString()) / 2));
                    landDialog.dismiss();
                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landDialog.dismiss();
                rl_SideValues.setVisibility(View.GONE);
            }
        });

    }

    public void TrapeziumShapeImagePreview() {
        final Dialog landDialog = new Dialog(mActivity);
        landDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        landDialog.setCancelable(true);
        landDialog.setContentView(R.layout.trapezium_layout_preview);
        Window window = landDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        landDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        landDialog.show();

        ImageView close;
        Button btnSubmit;

        final EditText squareSideA, squareSideB, squareSideC, squareSideD, squareSideH;
        squareSideA = landDialog.findViewById(R.id.et_squareSideA);
        squareSideB = landDialog.findViewById(R.id.et_squareSideB);
        squareSideC = landDialog.findViewById(R.id.et_squareSideC);
        squareSideD = landDialog.findViewById(R.id.et_squareSideD);
        squareSideH = landDialog.findViewById(R.id.et_squareSideH);

        squareSideA.setText("");
        squareSideB.setText("");
        squareSideC.setText("");
        squareSideD.setText("");
        squareSideH.setText("");
        squareSideA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideA.isFocused()) {
                    squareSideC.setText(squareSideA.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        squareSideC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideC.isFocused()) {
                    squareSideA.setText(squareSideC.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        close = landDialog.findViewById(R.id.ivClose);
        btnSubmit = landDialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (squareSideD.getText().toString().equalsIgnoreCase("") || squareSideA.getText().toString().equalsIgnoreCase("") || squareSideB.getText().toString().equalsIgnoreCase("") || squareSideC.getText().toString().equalsIgnoreCase("") || squareSideH.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mActivity, "All feilds required", Toast.LENGTH_SHORT).show();
                } else {
                    rl_SideValues.setVisibility(View.VISIBLE);
                    tv_lenghtSideA.setText(squareSideA.getText().toString());
                    tv_lenghtSideB.setText(squareSideB.getText().toString());
                    tv_lenghtSideC.setText(squareSideC.getText().toString());
                    tv_lenghtSideD.setText(squareSideD.getText().toString());
                    tv_lenghtSideH.setText(squareSideH.getText().toString());
                    sideA = Double.parseDouble(squareSideA.getText().toString());
                    sideB = Double.parseDouble(squareSideB.getText().toString());
                    sideC = Double.parseDouble(squareSideC.getText().toString());
                    sideD = Double.parseDouble(squareSideD.getText().toString());
                    sideH = Double.parseDouble(squareSideH.getText().toString());
                    etLandAreaAsSiteSurvey.setText(Double.toString((Double.parseDouble(squareSideB.getText().toString()) + Double.parseDouble(squareSideD.getText().toString())) * Integer.parseInt(squareSideH.getText().toString()) / 2));
                    landDialog.dismiss();
                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landDialog.dismiss();
                rl_SideValues.setVisibility(View.GONE);
            }
        });

    }

    public void TraperzoidShapeImagePreview() {
        final Dialog landDialog = new Dialog(mActivity);
        landDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        landDialog.setCancelable(true);
        landDialog.setContentView(R.layout.taperzoid_layout_preview);
        Window window = landDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        landDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        landDialog.show();

        ImageView close;
        Button btnSubmit;

        final EditText squareSideA, squareSideB, squareSideC, squareSideD, squareSideH;
        squareSideA = landDialog.findViewById(R.id.et_squareSideA);
        squareSideB = landDialog.findViewById(R.id.et_squareSideB);
        squareSideC = landDialog.findViewById(R.id.et_squareSideC);
        squareSideD = landDialog.findViewById(R.id.et_squareSideD);
        squareSideH = landDialog.findViewById(R.id.et_squareSideH);

        squareSideA.setText("");
        squareSideB.setText("");
        squareSideC.setText("");
        squareSideD.setText("");
        squareSideH.setText("");

        close = landDialog.findViewById(R.id.ivClose);
        btnSubmit = landDialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (squareSideD.getText().toString().equalsIgnoreCase("") || squareSideA.getText().toString().equalsIgnoreCase("") || squareSideB.getText().toString().equalsIgnoreCase("") || squareSideC.getText().toString().equalsIgnoreCase("") || squareSideH.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mActivity, "All feilds required", Toast.LENGTH_SHORT).show();
                } else {
                    rl_SideValues.setVisibility(View.VISIBLE);
                    tv_lenghtSideA.setText(squareSideA.getText().toString());
                    tv_lenghtSideB.setText(squareSideB.getText().toString());
                    tv_lenghtSideC.setText(squareSideC.getText().toString());
                    tv_lenghtSideD.setText(squareSideD.getText().toString());
                    tv_lenghtSideH.setText(squareSideH.getText().toString());
                    sideA = Double.parseDouble(squareSideA.getText().toString());
                    sideB = Double.parseDouble(squareSideB.getText().toString());
                    sideC = Double.parseDouble(squareSideC.getText().toString());
                    sideD = Double.parseDouble(squareSideD.getText().toString());
                    sideH = Double.parseDouble(squareSideH.getText().toString());
                    etLandAreaAsSiteSurvey.setText(Double.toString((Double.parseDouble(squareSideB.getText().toString()) + Double.parseDouble(squareSideD.getText().toString())) * Double.parseDouble(squareSideH.getText().toString()) / 2));
                    landDialog.dismiss();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landDialog.dismiss();
                rl_SideValues.setVisibility(View.GONE);
            }
        });

    }

    public void RectangleShapeImagePreview() {
        final Dialog landDialog = new Dialog(mActivity);
        landDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        landDialog.setCancelable(true);
        landDialog.setContentView(R.layout.rectangle_layout_preview);
        Window window = landDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        landDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        landDialog.show();

        ImageView close, ivPreviewImage;
        Button btnSubmit;

        final EditText squareSideA, squareSideB, squareSideC, squareSideD;
        squareSideA = landDialog.findViewById(R.id.et_squareSideA);
        squareSideB = landDialog.findViewById(R.id.et_squareSideB);
        squareSideC = landDialog.findViewById(R.id.et_squareSideC);
        squareSideD = landDialog.findViewById(R.id.et_squareSideD);

        squareSideA.setText("");
        squareSideB.setText("");
        squareSideC.setText("");
        squareSideD.setText("");
        squareSideA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideA.isFocused()) {
                    squareSideD.setText(squareSideA.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        squareSideB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideB.isFocused()) {
                    squareSideC.setText(squareSideB.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        squareSideC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideC.isFocused()) {
                    squareSideB.setText(squareSideC.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        squareSideD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (squareSideD.isFocused()) {
                    squareSideC.setText(squareSideD.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        close = landDialog.findViewById(R.id.ivClose);
        btnSubmit = landDialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (squareSideD.getText().toString().equalsIgnoreCase("") || squareSideA.getText().toString().equalsIgnoreCase("") || squareSideB.getText().toString().equalsIgnoreCase("") || squareSideC.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(mActivity, "All feilds required", Toast.LENGTH_SHORT).show();
                } else {
                    rl_SideValues.setVisibility(View.VISIBLE);
                    tv_lenghtSideA.setText(squareSideA.getText().toString());
                    tv_lenghtSideB.setText(squareSideB.getText().toString());
                    tv_lenghtSideC.setText(squareSideC.getText().toString());
                    tv_lenghtSideD.setText(squareSideD.getText().toString());
                    sideA = Double.parseDouble(squareSideA.getText().toString());
                    sideB = Double.parseDouble(squareSideB.getText().toString());
                    sideC = Double.parseDouble(squareSideC.getText().toString());
                    sideD = Double.parseDouble(squareSideD.getText().toString());
                    etLandAreaAsSiteSurvey.setText(Double.toString(Double.parseDouble(squareSideA.getText().toString()) * Double.parseDouble(squareSideB.getText().toString())));
                    landDialog.dismiss();
                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landDialog.dismiss();
                rl_SideValues.setVisibility(View.GONE);
            }
        });

    }

    public void AlertImagePreview(Bitmap bitmap) {
        final Dialog emailDialog = new Dialog(mActivity);
        emailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailDialog.setCancelable(true);
        emailDialog.setContentView(R.layout.alert_image_preview);
        Window window = emailDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        emailDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        emailDialog.show();

        ImageView close, ivPreviewImage;
        Button btnSubmit;

        close = emailDialog.findViewById(R.id.ivClose);
        btnSubmit = emailDialog.findViewById(R.id.btnSubmit);
        ivPreviewImage = emailDialog.findViewById(R.id.ivPreviewImage);

        //  byte[] byteFormat = Base64.decode(base64, Base64.DEFAULT);
        //   Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
        //   ivPreviewImage.setImageBitmap(decodedImage);
        ivPreviewImage.setImageBitmap(bitmap);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialog.dismiss();
            }
        });

    }

    private void setLayoutBackground(String imageName, final LinearLayout linearLayout) {

        final ImageView img = new ImageView(mActivity);

     /*   Log.v("zxvzxvzxvrfv",getString(R.string.videoPath)+
                pref.get(Constants.lead_id)+"_"+pref.get(Constants.case_id)+
                "/"+imageName);*/

        //Picasso.with(mContext).load(search.get(i).get("profilepic")).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(ivProfilePic);

        Picasso.with(mActivity).load(getString(R.string.videoPath) +
                pref.get(Constants.lead_id) + "_" + pref.get(Constants.case_id) +
                "/" + imageName).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(img, new Callback() {
            @Override
            public void onSuccess() {
                // img.setDrawingCacheEnabled(true);
                linearLayout.setBackgroundDrawable(img.getDrawable());
                // Bitmap bmap = img.getDrawingCache();

                linearLayout.setDrawingCacheEnabled(true);
                linearLayout.setTag(linearLayout.getDrawingCache());
            }

            @Override
            public void onError() {

            }
        });

/*
        Picasso.with(mActivity).load(getString(R.string.videoPath)+
                pref.get(Constants.lead_id)+"_"+pref.get(Constants.case_id)+
                "/"+imageName).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d("Picasso", "Success");
                BitmapDrawable background = new BitmapDrawable(bitmap);
                linearLayout.setBackgroundDrawable(background);

               // linearLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("Picasso", "FAILED");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d("Picasso", "Prepare Load");
            }
        });
*/
    }

    private void hitSendEmailOne(final String type) {

        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

        String url = Utils.getCompleteApiUrl(mActivity, R.string.SendPropertyNotIdentifiedMail);
        Log.v("hitSendEmailOne", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));
            jsonObject.put("sur_id", pref.get(Constants.surveyor_id));
            jsonObject.put("type", type);

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
                        parseJsonSendEmailOne(response, type);
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
                            //   Toast.makeText(MapLocatorActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonSendEmailOne(JSONObject response, String type) {

        Log.v("res:hitSendEmailOne", response.toString());
        loader.cancel();
        loader.dismiss();

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                if (type.equalsIgnoreCase("2")) {
                    Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                    ((Dashboard) mActivity).displayView(0);

                    emailDialogContent.dismiss();

                    footer.setBackgroundColor(getResources().getColor(R.color.progressgrey));
                    footer.setClickable(false);
                    tvNext.setTextColor(getResources().getColor(R.color.black));
                }
                if (jsonObject.has("owner_name")) {
                    ownerName = jsonObject.getString("owner_name");
                    ownerEmail = jsonObject.getString("owner_mail");
                    bankManagerName = jsonObject.getString("bank_manager_name");
                    bankManagerEmail = jsonObject.getString("bank_manager_email");
                    coPersonName = jsonObject.getString("co_person_name");
                    coPersonEmail = jsonObject.getString("co_person_mail");
                    siteInspectorName = jsonObject.getString("site_inspector_name");
                    siteInspectorEmail = jsonObject.getString("site_inspector_mail");
                    if (jsonObject.has("mail_content")) {
                        mailContent = jsonObject.getString("mail_content");
                    }
                }

            } else {
                if (type.equalsIgnoreCase("2")) {
                    Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                }
                loader.cancel();
            }

            loader.cancel();

        } catch (Exception e) {
            loader.cancel();
            e.printStackTrace();
        }
    }

    private void hitSendEmailTwo() {

        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

        String url = Utils.getCompleteApiUrl(mActivity, R.string.SendReasonForApproval);
        Log.v("hitSendEmailTwo", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));
            jsonObject.put("sur_id", pref.get(Constants.surveyor_id));
            jsonObject.put("reason", etProceedReason.getText().toString());

            json_data.put("VIS", jsonObject);

            Log.v("hitSendEmailTwo", json_data.toString());

        } catch (JSONException e) {

            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonSendEmailTwo(response);
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
                            //   Toast.makeText(MapLocatorActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonSendEmailTwo(JSONObject response) {

        Log.v("res:hitSendEmailTwo", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                ((Dashboard) mActivity).displayView(0);

                emailDialogContent.dismiss();

                footer.setBackgroundColor(getResources().getColor(R.color.app_header));
                footer.setClickable(true);
                tvNext.setTextColor(getResources().getColor(R.color.white));

            } else {

                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                loader.cancel();
            }

            loader.cancel();

        } catch (Exception e) {
            loader.cancel();
            e.printStackTrace();
        }
    }

    //Email content popup
    public void AlertEmailContentPopup() {
        emailDialogContent = new Dialog(mActivity);
        emailDialogContent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailDialogContent.setCancelable(true);
        emailDialogContent.setContentView(R.layout.alert_email_content_popup);
        Window window = emailDialogContent.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        emailDialogContent.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        emailDialogContent.show();

        ImageView close;
        TextView tvMessage;
        Button btnSubmit;
        TextView tvEmailName;

        close = emailDialogContent.findViewById(R.id.iv_close);
        tvMessage = emailDialogContent.findViewById(R.id.tvMessage);
        btnSubmit = emailDialogContent.findViewById(R.id.btnSubmit);
        tvEmailName = emailDialogContent.findViewById(R.id.tvEmailName);

        //  mailContent = "Dear Sir,\n\n"+ "Please refer to the "+pref.get(Constants.case_id)+" in regard to the Valuation of ";

        // etMessage.setText("Dear Sir,\n\n"+ "Please refer to the "+pref.get(Constants.case_id)+" in regard to the Valuation of ");
        //  etMessage.setText(mailContent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvMessage.setText(Html.fromHtml(mailContent, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvMessage.setText(Html.fromHtml(mailContent));
        }

        tvEmailName.setText(bankManagerName + " (" + bankManagerEmail + "),\n" +
                ownerName + " (" + ownerEmail + "),\n" +
                coPersonName + " (" + coPersonEmail + "),\n" +
                siteInspectorName + " (" + siteInspectorEmail + ")");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(mActivity)) {

                    if (Utils.isNetworkConnectedMainThred(mActivity)) {
                        hitSendEmailOne("2");
                        emailDialogContent.dismiss();
                    } else {
                        Toast.makeText(mActivity, getString(R.string.noInternetConnection), Toast.LENGTH_SHORT).show();
                    }
                    //Log.v("asasaxzceds","send");
                } else {
                    Toast.makeText(mActivity, R.string.noInternetConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialogContent.dismiss();
            }
        });

    }

    //Authorization Letter Popup
    public void AlertAuthLetter() {
        final Dialog dialog = new Dialog(mActivity, R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.alert_auth_letter_preview);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView close;
        Button btnSubmit;
        WebView webView;
        ImageView ivImage;
        final ProgressBar progressBar;

        close = dialog.findViewById(R.id.iv_close);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);
        webView = dialog.findViewById(R.id.webView);
        progressBar = dialog.findViewById(R.id.progressBar);
        ivImage = dialog.findViewById(R.id.ivImage);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //  activity.setTitle("Loading...");
                //  activity.setProgress(progress * 100);
                if (progress == 100)
                    progressBar.setVisibility(View.GONE);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(mActivity, description, Toast.LENGTH_SHORT).show();
            }
        });

        //   webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+authLetter);

        if (authLetter.contains(".pdf") || authLetter.contains(".doc")) {
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + authLetter);
        } else {
            // webView.loadDataWithBaseURL(null, "<html><head></head><body><table style=\"width:100%; height:100%;\"><tr><td style=\"vertical-align:middle;\"><img src=\"" + authLetter + "\"></td></tr></table></body></html>", "html/css", "utf-8", null);
            //webView.loadUrl(authLetter);
            webView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            ivImage.setVisibility(View.VISIBLE);

            Picasso.with(mActivity).load(authLetter).into(ivImage);

        }


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


    //View Reply popup
    public void AlertViewReply(String reply) {
        final Dialog emailDialog = new Dialog(mActivity);
        emailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailDialog.setCancelable(true);
        emailDialog.setContentView(R.layout.alert_view_reply_popup);
        Window window = emailDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        emailDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        emailDialog.show();

        ImageView close;
        TextView tvMessage;
        Button btnSubmit;

        close = emailDialog.findViewById(R.id.iv_close);
        tvMessage = emailDialog.findViewById(R.id.tvMessage);
        btnSubmit = emailDialog.findViewById(R.id.btnSubmit);

        tvMessage.setText(reply);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(mActivity)) {

                    emailDialog.dismiss();

                } else {
                    Toast.makeText(mActivity, R.string.noInternetConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialog.dismiss();
            }
        });

    }

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
}

