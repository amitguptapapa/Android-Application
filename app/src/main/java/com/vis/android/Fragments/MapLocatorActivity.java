package com.vis.android.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.ReassignActivity;
import com.vis.android.Activities.ScheduleActivity;
import com.vis.android.Activities.SurveyNotRequiredActivity;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.Extras.DataParser;
import com.vis.android.Extras.GetLocation;
import com.vis.android.Extras.MySupportMapFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.vis.android.Fragments.CaseDetail.case_id;

public class MapLocatorActivity extends BaseFragment implements View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LinearLayout re_schedule;
    RelativeLayout back, rl_casedetail, dots;
    Button proceedd;
    Intent intent;
    TextView one;
    TextView two;
    TextView three;
    TextView tv_header, tv_caseheader, tv_caseid;
    ImageView iv_one;
    ImageView iv_two;
    ImageView iv_three;
    String image_status = "0";
    Boolean edit_page =true;
    Preferences pref;
    CustomLoader loader;
    ImageView nevigation;
    RelativeLayout land1, land2, land3;
    String land_status = "0";
    GoogleMap mMap;
    private int PROXIMITY_RADIUS = 10000;
    String lattitude, longitude;
    //String
    public String picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", encodedString1 = "", encodedString2 = "", encodedString3 = "", encodedString4 = "", setPic = "";

    //Uri
    Uri picUri, fileUri;

    //StaticString
    private static final String IMAGE_DIRECTORY_NAME = "VIS";

    //Static Int
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;

    //Bitmap
    Bitmap bitmap;
    //public static MapView mapView;
    //Context mContext;
    GetLocation Location;
    ArrayList<LatLng> markerPoints;
    public static Double Lat, Lng;
    public static Double LatImg1 = 0.0, LngImg1 = 0.0;
    public static Double LatImg2 = 0.0, LngImg2 = 0.0;
    public static Double LatImg3 = 0.0, LngImg3 = 0.0;

    LatLng latLng;
    Geocoder geocoder;
    List<List<HashMap<String, String>>> routes = null;
    Spinner spinner;

    AutoCompleteTextView etLandmark1,etLandmark2,etLandmark3;
    private Dialog emailDialog;

    ImageView ivAttachment;
    private int captureType = 0;
    private ArrayList<String> selectedMembersList = new ArrayList<>();

    View v;

    NestedScrollView nestedScrollView;

    TextView tvDistance1,tvDistance2,tvDistance3;
    private double measuredDistance = 0;
    private String base64 = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_map, container, false);

        getid(v);

      //  Dashboard.tv_caseheader.setText("Map Locator");

        setListener();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

      /*  SupportMapFragment mapFragment = (SupportMapFragment) mActivity.getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
*/
        MySupportMapFragment mSupportMapFragment;
        mSupportMapFragment = (MySupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(this);

        mSupportMapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                nestedScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });


/*        tv_header.setVisibility(View.GONE);
        rl_casedetail.setVisibility(View.VISIBLE);
        tv_caseheader.setText("Map Locator");
        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));*/
        //===================================================  for Map Call   ======================================================//

        checkLocationPermission();

        Location = new GetLocation(mActivity);

        MapsInitializer.initialize(mActivity);

        /*if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //    int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/

      /*  mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);*/

        // Change loaction button position...
      /*  View locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        // rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(30, 30, 30, 30);*/

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls

        markerPoints = new ArrayList<>();

        Lat = Location.getLatitude();
        Lng = Location.getLongitude();
        // drawline(Lat, Lng);

        /*mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        geocoder = new Geocoder(this, Locale.getDefault());
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 14);
        mMap.animateCamera(cameraUpdate1);

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                latLng = cameraPosition.target;
                Log.e("===============+", String.valueOf(latLng));

                try {
                    Log.e("Latitudee", latLng.latitude + "");
                    Log.e("Longitudee", latLng.longitude + "");

                    Lat = latLng.latitude;
                    Lng = latLng.longitude;


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });*/

        if (Utils.isNetworkConnectedMainThred(mActivity)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            hitGetMediaApi();

        } else {
            Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

        }
        if(!edit_page){// false
            setEditiblity();
        }


        return v;
    }
    public void setEditiblity(){
        one.setEnabled(false);
        two.setEnabled(false);
        three.setEnabled(false);
        nevigation.setEnabled(false);
        iv_one.setEnabled(false);
        iv_two.setEnabled(false);
        iv_three.setEnabled(false);
        land1.setEnabled(false);
        land2.setEnabled(false);
        land3.setEnabled(false);
        spinner.setEnabled(false);
        etLandmark1.setEnabled(false);
        etLandmark2.setEnabled(false);
        etLandmark3.setEnabled(false);
        etLandmark1.setEnabled(false);
        etLandmark2.setEnabled(false);
        etLandmark3.setEnabled(false);
        tvDistance1.setEnabled(false);
        tvDistance2.setEnabled(false);
        tvDistance3.setEnabled(false);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.v("Background Task data", data.toString());
                Log.v("backgroundtask+++++++", data.toString());
            } catch (Exception e) {
                Log.v("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            // List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                Log.v("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.v("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.v("ParserTask", "Executing routes");
                Log.v("ParserTask", routes.toString());


            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;

        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;


            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {

                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    Log.v("position+++++", String.valueOf(position));
                    points.add(position);
                    // status = "1";

                }


                Log.v("pathsize*****", String.valueOf(path.size()));

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }


    }

    public void drawline(Double lat, Double lng) {
        try {

            System.out.println("lattitude******" + lat + "longitute*******" + lng);
            // Origin of route

            String str_origin = "origin=" + lattitude + "," + longitude;//26.8723,80.9824
            // String str_origin = "origin=" + Lat + "," + Lng;

            // Destination of route
            String str_dest = "destination=" + lat + "," + lng;

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = str_origin + "&" + str_dest + "&" + sensor;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
            Log.v("hellooooo********", url);
            FetchUrl FetchUrl = new FetchUrl();

            // Start downloading json data from Google Directions API
            FetchUrl.execute(url);
        } catch (Exception exp) {

        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(mActivity)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(mActivity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(mActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(mActivity, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_rescd:
                RescheduleDialog();
                break;

            /*case R.id.rl_back:
                onBackPressed();
                break;*/

            case R.id.btn_proceed:
                if (iv_one.getDrawable() == null && iv_two.getDrawable() == null && iv_three.getDrawable() == null) {
                    Toast.makeText(mActivity, "Please capture at least one landmark", Toast.LENGTH_SHORT).show();
                }
               /*if (encodedString1.equals("") || encodedString2.equals("") || encodedString3.equals("")){
                   Toast.makeText(MapLocatorActivity.this, "Please capture at least one landmark", Toast.LENGTH_SHORT).show();
               }*/else {
                    if (Utils.isNetworkConnectedMainThred(mActivity)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);

                        Hit_PostMedia_Api();

                    } else {
                        Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                    }

                  //  ((Dashboard)mActivity).displayView(4);

                  /* intent = new Intent(mActivity, InitiateSurveyForm.class);
                   startActivity(intent);*/
               }
                   /* if (Utils.isNetworkConnectedMainThred(MapLocatorActivity.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        Hit_PostMedia_Api();

                    } else {
                        Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                    }*/
              //  }

                break;

            case R.id.tvone:
                captureType =1;
                image_status = "1";
                showCameraGalleryDialog();
                break;

          /*  case R.id.iv1:
                captureType =1;
                image_status = "1";
                showCameraGalleryDialog();
                break;*/

            case R.id.tvtwo:
                captureType =1;
                image_status = "2";
                showCameraGalleryDialog();
                break;

            case R.id.tvthree:
                captureType =1;
                image_status = "3";
                showCameraGalleryDialog();
                break;

            case R.id.iv1:

                captureType =1;
                image_status = "1";
                showCameraGalleryDialog();

                CameraUpdate cameraUpdate1;
                try {
                    cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(LatImg1,LngImg1), 14);
                    mMap.animateCamera(cameraUpdate1);
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case R.id.iv2:

                captureType =1;
                image_status = "2";
                showCameraGalleryDialog();

                try {
                    cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(LatImg2,LngImg2), 14);
                    mMap.animateCamera(cameraUpdate1);
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case R.id.iv3:

                captureType =1;
                image_status = "3";
                showCameraGalleryDialog();

                try {
                    cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(LatImg3,LngImg3), 14);
                    mMap.animateCamera(cameraUpdate1);
                }catch (Exception e){
                    e.printStackTrace();
                }


                break;

            case R.id.iv_nevigation:
                navigation();
                break;

            case R.id.ll_land1:
                land_status = "1";
                InfoDialog();
                break;

            case R.id.ll_land2:
                land_status = "2";
                InfoDialog();
                break;

            case R.id.ll_land3:
                land_status = "3";
                InfoDialog();
                break;

            case R.id.rl_dots:
                showPopup(view);
                break;



        }
    }

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
       // popup.getMenu().getItem(4).setVisible(false);
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

                   /* case R.id.doc:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://valuerservice.com/uploads/0692351837.jpg"));
                        startActivity(intent);
                        return true;*/

                    default:
                        return false;
                }
            }
        });
    }

    public void getid(View v) {
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page=false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page=true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        re_schedule = (LinearLayout) v.findViewById(R.id.ll_rescd);
        //back = (RelativeLayout) v.findViewById(R.id.rl_back);
        proceedd = (Button) v.findViewById(R.id.btn_proceed);
        one = (TextView) v.findViewById(R.id.tvone);
        two = (TextView) v.findViewById(R.id.tvtwo);
        three = (TextView) v.findViewById(R.id.tvthree);
        iv_one = (ImageView) v.findViewById(R.id.iv1);
        iv_two = (ImageView) v.findViewById(R.id.iv2);
        iv_three = (ImageView) v.findViewById(R.id.iv3);
        land1 = v.findViewById(R.id.ll_land1);
        land2 = v.findViewById(R.id.ll_land2);
        land3 = v.findViewById(R.id.ll_land3);
        nevigation = (ImageView) v.findViewById(R.id.iv_nevigation);
        spinner = v.findViewById(R.id.spinner);
       // tv_header = v.findViewById(R.id.tv_header);
        //rl_casedetail = v.findViewById(R.id.rl_casedetail);
       // tv_caseheader = v.findViewById(R.id.tv_caseheader);
       // tv_caseid = v.findViewById(R.id.tv_caseid);
        //dots = v.findViewById(R.id.rl_dots);
        etLandmark1 = v.findViewById(R.id.etLandmark1);
        etLandmark2 = v.findViewById(R.id.etLandmark2);
        etLandmark3 = v.findViewById(R.id.etLandmark3);

        CustomAutoCompleteAdapter adapterAutoComplete =  new CustomAutoCompleteAdapter(mActivity);
        etLandmark1.setAdapter(adapterAutoComplete);
        etLandmark2.setAdapter(adapterAutoComplete);
        etLandmark3.setAdapter(adapterAutoComplete);

        nestedScrollView = v.findViewById(R.id.nestedScrollView);

        tvDistance1 = v.findViewById(R.id.tvDistance1);
        tvDistance2 = v.findViewById(R.id.tvDistance2);
        tvDistance3 = v.findViewById(R.id.tvDistance3);

    }


    public void setListener() {
        re_schedule.setOnClickListener(this);
        //back.setOnClickListener(this);
        proceedd.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        nevigation.setOnClickListener(this);
        land1.setOnClickListener(this);
        land2.setOnClickListener(this);
        land3.setOnClickListener(this);
       // dots.setOnClickListener(this);
        iv_one.setOnClickListener(this);
        iv_two.setOnClickListener(this);
        iv_three.setOnClickListener(this);
    }

    public void InfoDialog() {

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
        dialog.show();
        ImageView cancel = (ImageView) dialog.findViewById(R.id.iv_close);
        TextView info = dialog.findViewById(R.id.tv_info);
        TextView ok = dialog.findViewById(R.id.tv_ok);
        if (land_status.equals("1")) {
            info.setText(getString(R.string.land1));
        } else if (land_status.equals("2")) {
            info.setText(getString(R.string.land2));
        } else {
            info.setText(getString(R.string.land3));
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //StartConfirmation popup
    public void RescheduleDialog() {

        final Dialog dialog = new Dialog(mActivity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.reschedule_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView submit = (TextView) dialog.findViewById(R.id.tv_submit);
        ImageView cancel = (ImageView) dialog.findViewById(R.id.iv_close);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
        TextView tv_2 = dialog.findViewById(R.id.tv_2);

        SpannableStringBuilder ssBuilder = null;

        if (image_status.equalsIgnoreCase("1")){

            String text = "(Any important known place like nearest market, shopping complex, important shop, showroom, metro station, bus stand, hospital, etc.)";

            tv_2.setText("Camera\n"+text+"\nClick here");

            tv_2.setGravity(Gravity.CENTER);

            ssBuilder = new SpannableStringBuilder(tv_2.getText().toString());

            ClickableSpan redClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    // Do something
                    //   ((MainActivity)mActivity).displayView(18);

                }
            };

           // ss1.setSpan(new StyleSpan(Typeface.ITALIC), 0, ss1.length(), 0);
        //    ss1.setSpan(new RelativeSizeSpan(2f), 0, ss1.length, 0);

            ssBuilder.setSpan(
                    new StyleSpan(Typeface.ITALIC), // Span to add
                    tv_2.getText().toString().indexOf(text), // Start of the span (inclusive)
                    tv_2.getText().toString().indexOf(text) + text.length(), // End of the span (exclusive)
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
            );

            ssBuilder.setSpan(new ForegroundColorSpan(Color.BLUE),  tv_2.getText().toString().indexOf("Click here"),
                    tv_2.getText().toString().indexOf("Click here") + String.valueOf("Click here").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        else if (image_status.equalsIgnoreCase("2")){
            tv_2.setText("Camera\n(Main road)\nClick here");
            tv_2.setGravity(Gravity.CENTER);

            ssBuilder = new SpannableStringBuilder(tv_2.getText().toString());

            ssBuilder.setSpan(
                    new StyleSpan(Typeface.ITALIC), // Span to add
                    tv_2.getText().toString().indexOf("(Main road)"), // Start of the span (inclusive)
                    tv_2.getText().toString().indexOf("(Main road)") + String.valueOf("(Main road)").length(), // End of the span (exclusive)
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
            );

            ssBuilder.setSpan(new ForegroundColorSpan(Color.BLUE),  tv_2.getText().toString().indexOf("Click here"),
                    tv_2.getText().toString().indexOf("Click here") + String.valueOf("Click here").length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        else if (image_status.equalsIgnoreCase("3")){

            String text = "(Any other point of importance within the locality of subject property like park, local community shop, community centre, etc.)";

            tv_2.setText("Camera\n"+text+"\nClick here");

            tv_2.setGravity(Gravity.CENTER);

            ssBuilder = new SpannableStringBuilder(tv_2.getText().toString());

            ClickableSpan redClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    // Do something
                    //   ((MainActivity)mActivity).displayView(18);

                }
            };

            // ss1.setSpan(new StyleSpan(Typeface.ITALIC), 0, ss1.length(), 0);
            //    ss1.setSpan(new RelativeSizeSpan(2f), 0, ss1.length, 0);

            ssBuilder.setSpan(
                    new StyleSpan(Typeface.ITALIC), // Span to add
                    tv_2.getText().toString().indexOf(text), // Start of the span (inclusive)
                    tv_2.getText().toString().indexOf(text) + text.length(), // End of the span (exclusive)
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
            );

            ssBuilder.setSpan(new ForegroundColorSpan(Color.BLUE),  tv_2.getText().toString().indexOf("Click here"),
                    tv_2.getText().toString().indexOf("Click here") + String.valueOf("Click here").length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        }


        tv_2.setText(ssBuilder);
        tv_2.setMovementMethod(LinkMovementMethod.getInstance());

        llGallery.setVisibility(View.GONE);

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                dialog.dismiss();
            }
        });

        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/

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

    }

    public Uri getOutputMediaFileUri(int type) {
        //return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(mActivity, mActivity.getPackageName()+".provider", getOutputMediaFile(type));

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

                if (captureType == 2){
                    ivAttachment.setVisibility(View.VISIBLE);
                    ivAttachment.setImageBitmap(rotatedBitmap);
                }else {

                    if (image_status.equals("1")) {
                        encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);
                        setPictures(rotatedBitmap, setPic, encodedString1);
                    } else if (image_status.equals("2")) {
                        encodedString2 = getEncoded64ImageStringFromBitmap(rotatedBitmap);
                        setPictures(rotatedBitmap, setPic, encodedString2);
                    } else if (image_status.equals("3")) {
                        encodedString3 = getEncoded64ImageStringFromBitmap(rotatedBitmap);
                        setPictures(rotatedBitmap, setPic, encodedString3);
                    } else {
                        encodedString4 = getEncoded64ImageStringFromBitmap(rotatedBitmap);
                        setPictures(rotatedBitmap, setPic, encodedString4);
                    }
                }

                //setPic="1";


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

                if (captureType == 2){
                    ivAttachment.setVisibility(View.VISIBLE);
                    ivAttachment.setImageBitmap(bitmap);
                }else {
                    if (image_status.equals("1")) {
                        encodedString1 = getEncoded64ImageStringFromBitmap(bitmap);
                        setPictures(rotatedBitmap, setPic, encodedString1);
                    } else if (image_status.equals("2")) {
                        encodedString2 = getEncoded64ImageStringFromBitmap(bitmap);
                        setPictures(rotatedBitmap, setPic, encodedString2);
                    } else if (image_status.equals("3")) {
                        encodedString3 = getEncoded64ImageStringFromBitmap(bitmap);
                        setPictures(rotatedBitmap, setPic, encodedString3);
                    } else {
                        encodedString4 = getEncoded64ImageStringFromBitmap(bitmap);
                        setPictures(rotatedBitmap, setPic, encodedString4);
                    }
                }


            } else {
                Toast.makeText(mActivity, "unable to select image", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setPictures(Bitmap b, String setPic, String base64) {

        if (image_status.equals("1")) {

            LatImg1 = Location.getLatitude();
            LngImg1 = Location.getLongitude();

            Log.d("##current lat:", String.valueOf(LatImg1));
            Log.d("##current long:", String.valueOf(LngImg1));
            Log.d("##asset lat", pref.get(Constants.endLat));
            Log.d("##asset long", pref.get(Constants.endLng));


            hitGetDistance(LatImg1.toString(),LngImg1.toString(),pref.get(Constants.endLat),pref.get(Constants.endLng));

           // Log.d("##distance0", String.valueOf(results[0]));
          //  Log.d("##distance1", String.valueOf(results[1]));

            String address = getAddress(mActivity,LatImg1,LngImg1);

            iv_one.setVisibility(View.VISIBLE);
            iv_one.setImageBitmap(b);
            one.setVisibility(View.GONE);
            Bitmap smallMarker = b.createScaledBitmap(b, 100, 100, false);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(LatImg1, LngImg1));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            markerOptions.title(address);
            mMap.addMarker(markerOptions);

            etLandmark1.setText(address);

            getDistance(LatImg1,LngImg1,tvDistance1);
//            measuredDistance = Utils.calculateDistance(LatImg1,LngImg1,Double.valueOf(pref.get(Constants.endLat)),Double.valueOf(pref.get(Constants.endLng)));

//            if (measuredDistance >= 1000){
//
//                Log.v("measureDist",measuredDistance/1000+" km");
//
//                double inKm = measuredDistance/1000;
//
//                tvDistance1.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
//            }else {
//                //   Log.v("measureDist",measuredDistance+" m");
//                tvDistance1.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");
//            }

        } else if (image_status.equals("2")) {

            LatImg2 = Location.getLatitude();
            LngImg2 = Location.getLongitude();

            String address = getAddress(mActivity,LatImg2,LngImg2);

            iv_two.setVisibility(View.VISIBLE);
            iv_two.setImageBitmap(b);
            two.setVisibility(View.GONE);
            Bitmap smallMarker = b.createScaledBitmap(b, 100, 100, false);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(LatImg2, LngImg2));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            markerOptions.title(address);
            mMap.addMarker(markerOptions);

            etLandmark2.setText(address);
            getDistance(LatImg2,LngImg2,tvDistance2);

//            measuredDistance = Utils.calculateDistance(LatImg2,LngImg2,Double.valueOf(pref.get(Constants.endLat)),Double.valueOf(pref.get(Constants.endLng)));
//
//            if (measuredDistance >= 1000){
//
//                Log.v("measureDist",measuredDistance/1000+" km");
//
//                double inKm = measuredDistance/1000;
//
//                tvDistance2.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
//            }else {
//                //   Log.v("measureDist",measuredDistance+" m");
//                tvDistance2.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");
//            }

        } else if (image_status.equals("3")) {

            LatImg3 = Location.getLatitude();
            LngImg3 = Location.getLongitude();

            String address = getAddress(mActivity,LatImg3,LngImg3);

            iv_three.setVisibility(View.VISIBLE);
            iv_three.setImageBitmap(b);
            three.setVisibility(View.GONE);

            Bitmap smallMarker = b.createScaledBitmap(b, 100, 100, false);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Lat, Lng));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            markerOptions.title(address);
            mMap.addMarker(markerOptions);

            etLandmark3.setText(address);
            getDistance(LatImg3,LngImg3,tvDistance3);

//            measuredDistance = Utils.calculateDistance(LatImg3,LngImg3,Double.valueOf(pref.get(Constants.endLat)),Double.valueOf(pref.get(Constants.endLng)));
//
//            if (measuredDistance >= 1000){
//
//                Log.v("measureDist",measuredDistance/1000+" km");
//
//                double inKm = measuredDistance/1000;
//
//                tvDistance3.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
//            }else {
//                //   Log.v("measureDist",measuredDistance+" m");
//                tvDistance3.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");
//            }

        } else {
            Bitmap smallMarker = b.createScaledBitmap(b, 50, 50, false);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Lat, Lng));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            mMap.addMarker(markerOptions);
        }

    }
    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/distancematrix/json");
        urlString.append("?origins=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelog));
        urlString.append("&destinations=");// to
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&mode=driving");
        /*urlString.append("&sensor=false&mode=driving&alternatives=true");*/
        urlString.append("&key=AIzaSyALTsVJy_Py_INw-TFmLqdU9lGHQHiqX2k");
        return urlString.toString();
    }
    private void getDistance(Double latitude, Double longitude, final TextView tv_MapDistance) {
        String url = makeURL(latitude, longitude,  Double.valueOf(Double.valueOf(pref.get(Constants.endLat))), Double.valueOf(pref.get(Constants.endLng)));
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
                            Log.v("distanceee", distance);
                            tv_MapDistance.setText("Distance from the property: "+distance);
                            Log.v("distanceee", distance);
                            distance = distance.replace("km", "");
                            Log.v("distanceee", distance);

                            distance = distance.replace(",", "");
                            Log.v("distanceee", distance);
                            measuredDistance=Double.parseDouble(distance);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
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
       // String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        //encodedString1 = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        Log.v("bmap=========2", encodedString1);

        return imgString;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
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
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(edit_page);
        mMap.getUiSettings().setZoomControlsEnabled(edit_page);

        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        Lat = location.getLatitude();
        Lng = location.getLongitude();
        LatLng latLng = new LatLng(Lat, Lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        mMap.animateCamera(cameraUpdate1);
        Toast.makeText(mActivity, "Your Current Location", Toast.LENGTH_LONG).show();

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", Lat, Lng));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

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
        buildGoogleApiClient();

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);

     //   mMap.setMyLocationEnabled(true);
     //   mMap.getUiSettings().setMyLocationButtonEnabled(true);
     //   mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(edit_page);
        uiSettings.setZoomControlsEnabled(edit_page);

        geocoder = new Geocoder(mActivity, Locale.getDefault());
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 14);
        mMap.animateCamera(cameraUpdate1);

       /* measuredDistance = Utils.calculateDistance(Lat,Lng,Double.valueOf(pref.get(Constants.endLat)),Double.valueOf(pref.get(Constants.endLng)));

        if (measuredDistance >= 1000){

            Log.v("measureDist",measuredDistance/1000+" km");

            double inKm = measuredDistance/1000;

            tvDistance1.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
            tvDistance2.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
            tvDistance3.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
        }else {
            //   Log.v("measureDist",measuredDistance+" m");
            tvDistance1.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");
            tvDistance2.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");
            tvDistance3.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");

        }*/


        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                latLng = mMap.getCameraPosition().target;

                try {
                   // Log.e("Latitudee", latLng.latitude + "");
                   // Log.e("Longitudee", latLng.longitude + "");

                    Lat = latLng.latitude;
                    Lng = latLng.longitude;


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

   /* @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }*/


    //********************************** GetMedia api *********************************//
    private void hitGetMediaApi() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.get_media);
        Log.v("hitGetMediaApi", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", case_id);
            jsonObject.put("surveyor_id", pref.get(Constants.surveyor_id));
            json_data.put("VIS", jsonObject);

            Log.v("hitGetMediaApi", json_data.toString());

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
                            //   Toast.makeText(MapLocatorActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJson(JSONObject response) {

        Log.v("res:hitGetMediaApi", response.toString());

        try {
            final JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {

                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                pref.set(Constants.colony,jsonObject.getString("colony"));
                pref.set(Constants.cityVillageTehsil,jsonObject.getString("city"));
                pref.set(Constants.district,jsonObject.getString("district"));
                pref.commit();

                if (!(jsonObject.getString("image1").equals(""))) {
                    one.setVisibility(View.GONE);
                    iv_one.setVisibility(View.VISIBLE);

                    Picasso.with(mActivity).load(jsonObject.getString("image1")).into(iv_one, new Callback() {
                        @Override
                        public void onSuccess() {

                         /*   iv_one.setDrawingCacheEnabled(true);
                            Bitmap bitmap = iv_one.getDrawingCache();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                          //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), bmap);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                            byte[] imageBytes = baos.toByteArray();
                            String imageString = Base64.encodeToString(imageBytes, 0);

                            try {
                                encodedString1=imageString;
                                addMarker(imageString,jsonObject.getString("landmark1"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
*/
                        }

                        @Override
                        public void onError() {

                        }
                    });

                    try {

                        LatImg1 = Double.valueOf(jsonObject.getString("lat1"));
                        LngImg1= Double.valueOf(jsonObject.getString("long1"));

                        Log.d("##current lat:", String.valueOf(LatImg1));
                        Log.d("##current long:", String.valueOf(LngImg1));
                        Log.d("##asset lat", pref.get(Constants.endLat));
                        Log.d("##asset long", pref.get(Constants.endLng));

                        etLandmark1.setText(jsonObject.getString("landmark1"));

                        measuredDistance = Utils.calculateDistance(LatImg1,LngImg1,Double.valueOf(pref.get(Constants.endLat)),Double.valueOf(pref.get(Constants.endLng)));

                        if (measuredDistance >= 1000){

                            Log.v("measureDist",measuredDistance/1000+" km");

                            double inKm = measuredDistance/1000;

                            tvDistance1.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
                        }else {
                            //   Log.v("measureDist",measuredDistance+" m");
                            tvDistance1.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");
                        }

                        getByteArrayFromImageURL(jsonObject.getString("image1"),
                                jsonObject.getString("landmark1"),"1");

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                if (!(jsonObject.getString("image2").equals(""))) {
                    two.setVisibility(View.GONE);
                    iv_two.setVisibility(View.VISIBLE);
                    Picasso.with(mActivity).load(jsonObject.getString("image2")).into(iv_two);

                    try {

                        LatImg2 = Double.valueOf(jsonObject.getString("lat2"));
                        LngImg2= Double.valueOf(jsonObject.getString("long2"));

                       // etLandmark2.setText(getAddress(mActivity,LatImg2,LngImg2));
                        etLandmark2.setText(jsonObject.getString("landmark2"));

                        measuredDistance = Utils.calculateDistance(LatImg2,LngImg2,Double.valueOf(pref.get(Constants.endLat)),Double.valueOf(pref.get(Constants.endLng)));

                        if (measuredDistance >= 1000){

                            Log.v("measureDist",measuredDistance/1000+" km");

                            double inKm = measuredDistance/1000;

                            tvDistance2.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
                        }else {
                            //   Log.v("measureDist",measuredDistance+" m");
                            tvDistance2.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");
                        }

                        getByteArrayFromImageURL(jsonObject.getString("image2"),
                                jsonObject.getString("landmark2"),"2");
                       // addMarker(encodedString2,jsonObject.getString("landmark2"));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (!(jsonObject.getString("image3").equals(""))) {
                    three.setVisibility(View.GONE);
                    iv_three.setVisibility(View.VISIBLE);
                    Picasso.with(mActivity).load(jsonObject.getString("image3")).into(iv_three);

                    try {

                        LatImg3 = Double.valueOf(jsonObject.getString("lat3"));
                        LngImg3= Double.valueOf(jsonObject.getString("long3"));

                      //  etLandmark3.setText(getAddress(mActivity,LatImg3,LngImg3));
                        etLandmark3.setText(jsonObject.getString("landmark3"));

                        measuredDistance = Utils.calculateDistance(LatImg3,LngImg3,Double.valueOf(pref.get(Constants.endLat)),Double.valueOf(pref.get(Constants.endLng)));

                        if (measuredDistance >= 1000){

                            Log.v("measureDist",measuredDistance/1000+" km");

                            double inKm = measuredDistance/1000;

                            tvDistance3.setText("Distance from the property: "+String.format("%.2f",inKm)+" km");
                        }else {
                            //   Log.v("measureDist",measuredDistance+" m");
                            tvDistance3.setText("Distance from the property: "+String.format("%.2f",measuredDistance)+" m");
                        }

                        getByteArrayFromImageURL(jsonObject.getString("image3"),
                                jsonObject.getString("landmark3"),"3");
                        //addMarker(encodedString3,jsonObject.getString("landmark3"));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                //lattitude = jsonObject.getString("lat");
              //  longitude = jsonObject.getString("long");


            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();
            }
            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(MapLocatorActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    private void getByteArrayFromImageURL(final String imageUrl, final String landmark, String type) {

        if (android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

                // Do network action in this function
        try {
            // Reading a Image file from file system
          /*  InputStream inputStream = new FileInputStream(imageUrl);//You can get an inputStream using any IO API
            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = output.toByteArray();
            String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);*/




            URL url = new URL(imageUrl);
            /*InputStream is = url.openStream();

            BufferedInputStream imageInFile = new BufferedInputStream(url.openConnection().getInputStream());

            //    FileInputStream imageInFile = new FileInputStream(is.toString());
            //byte imageData[] = new byte[2048];
            byte imageData[] = new byte[3086];
            imageInFile.read(imageData);

            // Converting Image byte array into Base64 String
            base64 = encodeImage(imageData);*/

            Bitmap image = null;

            try {
                //URL url = new URL(imageUrl);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch(Exception e) {
                System.out.println(e);
            }

            if (type.equalsIgnoreCase("1")){


                encodedString1 = getEncoded64ImageStringFromBitmap(image);

              //  encodedString1 = base64;

            }else if (type.equalsIgnoreCase("2")){
                encodedString2 = getEncoded64ImageStringFromBitmap(image);

                //encodedString2 = base64;
            } else if (type.equalsIgnoreCase("3")){

                encodedString3 = getEncoded64ImageStringFromBitmap(image);

               // encodedString3 = base64;
            }

        //    System.out.println("imageDataString: " + base64);

            addMarker(getEncoded64ImageStringFromBitmap(image),landmark);

/*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            },3000);
*/

            System.out.println("Image Successfully Manipulated!");

          //  return base64;
        } /*catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } */catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        } catch (Exception e){
            e.printStackTrace();
        }

       //return base64;

    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray,Base64.NO_WRAP);
    }

    //********************************** PostMedia api *********************************//
    private void Hit_PostMedia_Api() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.post_locator);
        Log.v("Hit_PostMedia_Api", url);
        Log.e("url>>>",url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();
        try {

            jsonObject.put("case_id", case_id);
            jsonObject.put("surveyor_id", pref.get(Constants.surveyor_id));
            jsonObject.put("image1", encodedString1);
            jsonObject.put("image2", encodedString2);
            jsonObject.put("image3", encodedString3);
            if (LatImg1 == 0.0){
                jsonObject.put("lat1", "");
                jsonObject.put("long1", "");
            }else {
                jsonObject.put("lat1", LatImg1);
                jsonObject.put("long1", LngImg1);
            }

            if (LatImg2 == 0.0){
                jsonObject.put("lat2", "");
                jsonObject.put("long2", "");
            }else {
                jsonObject.put("lat2", LatImg2.toString());
                jsonObject.put("long2", LngImg2.toString());
            }

            if (LatImg3 == 0.0){
                jsonObject.put("lat3", "");
                jsonObject.put("long3", "");
            }else {
                jsonObject.put("lat3", LatImg3.toString());
                jsonObject.put("long3", LngImg3.toString());
            }
            jsonObject.put("landmark1", etLandmark1.getText().toString());
            jsonObject.put("landmark2", etLandmark2.getText().toString());
            jsonObject.put("landmark3", etLandmark3.getText().toString());
            json_data.put("VIS", jsonObject);


            Log.v("request_media", json_data.toString());

        } catch (Exception e) {

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
                            Toast.makeText(mActivity, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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

                ((Dashboard)mActivity).displayView(4);
               /* intent = new Intent(mActivity, InitiateSurveyForm.class);
                startActivity(intent);*/
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

    public void navigation() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&daddr=29.4438,75.6703"));
        // Uri.parse("http://maps.google.com/maps?&daddr=26.8554973,80.9463664"));
        startActivity(intent);
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    //Call popup
    public void CallDialog(){
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

       // Intent intent = getIntent();

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
                }else*/ if (etSubject.getText().toString().isEmpty()){
                    Toast.makeText(mActivity, "Please enter subject", Toast.LENGTH_SHORT).show();
                }else if (etMessage.getText().toString().isEmpty()){
                    Toast.makeText(mActivity, "Please enter message", Toast.LENGTH_SHORT).show();
                }else {
                    if (Utils.isNetworkConnectedMainThred(mActivity)){
                        hitEmailApi(type,etSubject.getText().toString().trim(),etMessage.getText().toString().trim());
                    }else {
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

    private void call(View view, final String number){
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

    private void hitEmailApi(String type,String subject, String content) {

        loader.show();

        String url = Utils.getCompleteApiUrl(mActivity, R.string.SendSupportEmail);

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

    public class CustomAutoCompleteAdapter extends ArrayAdapter {
        public static final String TAG = "CustomAutoCompAdapter";
        private List<Place> dataList;
        private Context mContext;
        private GeoDataClient geoDataClient;

        private CustomAutoCompleteAdapter.CustomAutoCompleteFilter listFilter =
                new CustomAutoCompleteAdapter.CustomAutoCompleteFilter();

        private TextView country;
        //   private Spinner placeType;
        //   private int[] placeTypeValues;

        public CustomAutoCompleteAdapter(Context context) {
            super(context, android.R.layout.simple_dropdown_item_1line,
                    new ArrayList<Place>());
            mContext = context;

            //get GeoDataClient
            geoDataClient = Places.getGeoDataClient(mContext);

        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Place getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {

            if (view == null) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_dropdown_item_1line,
                                parent, false);
            }

            TextView textOne = view.findViewById(android.R.id.text1);
            textOne.setText(dataList.get(position).getPlaceText());

            etLandmark1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Utils.hideSoftKeyboard(mActivity);

                    geoDataClient.getPlaceById(dataList.get(position).getPlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                            if (task.isSuccessful()) {
                                PlaceBufferResponse places = task.getResult();
                                com.google.android.gms.location.places.Place myPlace = places.get(0);
                                Log.i(TAG, "Place found: " + myPlace.getLatLng());

                                String str = String.valueOf(myPlace.getLatLng());
                                Log.i(TAG, "str: " + myPlace.getLatLng());
                                str = str.replaceAll("lat/lng:","").trim();
                                str = str.replaceAll("\\(","").trim();
                                str = str.replaceAll("\\)","").trim();
                                Log.i(TAG, "str: " + str);

                                if(str.contains(","))
                                {

                                    String[] splitAddress = str.split(",");
                                    Location mLocation = new Location("");
                                    mLocation.setLatitude(Double.parseDouble(splitAddress[0]));
                                    mLocation.setLongitude(Double.parseDouble(splitAddress[1]));
                                    //  currentLocation = mLocation;
                                    //changeMap(mLocation);
                                    // startIntentService(mLocation);

                                }

                                places.release();
                            } else {
                                Log.e(TAG, "Place not found.");
                            }
                        }
                    });
                }
            });

            etLandmark2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Utils.hideSoftKeyboard(mActivity);

                    geoDataClient.getPlaceById(dataList.get(position).getPlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                            if (task.isSuccessful()) {
                                PlaceBufferResponse places = task.getResult();
                                com.google.android.gms.location.places.Place myPlace = places.get(0);
                                Log.i(TAG, "Place found: " + myPlace.getLatLng());

                                String str = String.valueOf(myPlace.getLatLng());
                                Log.i(TAG, "str: " + myPlace.getLatLng());
                                str = str.replaceAll("lat/lng:","").trim();
                                str = str.replaceAll("\\(","").trim();
                                str = str.replaceAll("\\)","").trim();
                                Log.i(TAG, "str: " + str);

                                if(str.contains(","))
                                {

                                    String[] splitAddress = str.split(",");
                                    Location mLocation = new Location("");
                                    mLocation.setLatitude(Double.parseDouble(splitAddress[0]));
                                    mLocation.setLongitude(Double.parseDouble(splitAddress[1]));
                                    //  currentLocation = mLocation;
                                    //changeMap(mLocation);
                                    // startIntentService(mLocation);

                                }

                                places.release();
                            } else {
                                Log.e(TAG, "Place not found.");
                            }
                        }
                    });
                }
            });

            etLandmark3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Utils.hideSoftKeyboard(mActivity);

                    geoDataClient.getPlaceById(dataList.get(position).getPlaceId()).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                            if (task.isSuccessful()) {
                                PlaceBufferResponse places = task.getResult();
                                com.google.android.gms.location.places.Place myPlace = places.get(0);
                                Log.i(TAG, "Place found: " + myPlace.getLatLng());

                                String str = String.valueOf(myPlace.getLatLng());
                                Log.i(TAG, "str: " + myPlace.getLatLng());
                                str = str.replaceAll("lat/lng:","").trim();
                                str = str.replaceAll("\\(","").trim();
                                str = str.replaceAll("\\)","").trim();
                                Log.i(TAG, "str: " + str);

                                if(str.contains(","))
                                {

                                    String[] splitAddress = str.split(",");
                                    Location mLocation = new Location("");
                                    mLocation.setLatitude(Double.parseDouble(splitAddress[0]));
                                    mLocation.setLongitude(Double.parseDouble(splitAddress[1]));
                                    //  currentLocation = mLocation;
                                    //changeMap(mLocation);
                                    // startIntentService(mLocation);

                                }

                                places.release();
                            } else {
                                Log.e(TAG, "Place not found.");
                            }
                        }
                    });
                }
            });

            return view;
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return listFilter;
        }

        public class CustomAutoCompleteFilter extends Filter {
            private Object lock = new Object();
            private Object lockTwo = new Object();
            private boolean placeResults = false;

            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();
                placeResults = false;
                final List<Place> placesList = new ArrayList<>();

                if (prefix == null || prefix.length() == 0) {
                    synchronized (lock) {
                        results.values = new ArrayList<Place>();
                        results.count = 0;
                    }
                } else {
                    final String searchStrLowerCase = prefix.toString().toLowerCase();

                    Task<AutocompletePredictionBufferResponse> task
                            = getAutoCompletePlaces(searchStrLowerCase);

                    task.addOnCompleteListener(new OnCompleteListener<AutocompletePredictionBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<AutocompletePredictionBufferResponse> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Auto complete prediction successful");
                                AutocompletePredictionBufferResponse predictions = task.getResult();

                                Place autoPlace;
                                for (AutocompletePrediction prediction : predictions) {
                                    autoPlace = new Place();
                                    autoPlace.setPlaceId(prediction.getPlaceId());
                                    autoPlace.setPlaceText(prediction.getFullText(null).toString());
                                    autoPlace.setPrimaryText(prediction.getPrimaryText(null).toString());
                                    placesList.add(autoPlace);
                                }
                                predictions.release();
                                Log.d(TAG, "Auto complete predictions size " + placesList.size());
                            } else {
                                Log.d(TAG, String.valueOf(task.getException()));
                                Log.d(TAG, "Auto complete prediction unsuccessful");
                            }
                            //inform waiting thread about api call completion
                            placeResults = true;
                            synchronized (lockTwo) {
                                lockTwo.notifyAll();
                            }
                        }
                    });

                    //wait for the results from asynchronous API call
                    while (!placeResults) {
                        synchronized (lockTwo) {
                            try {
                                lockTwo.wait();
                            } catch (InterruptedException e) {

                            }
                        }
                    }
                    results.values = placesList;
                    results.count = placesList.size();
                    Log.d(TAG, "Autocomplete predictions size after wait" + results.count);
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    dataList = (ArrayList<Place>) results.values;
                } else {
                    dataList = null;
                }
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            private Task<AutocompletePredictionBufferResponse> getAutoCompletePlaces(String query) {
                //create autocomplete filter using data from filter Views
                AutocompleteFilter.Builder filterBuilder = new AutocompleteFilter.Builder();
                //  filterBuilder.setCountry(country.getText().toString());
                // filterBuilder.setTypeFilter(placeTypeValues[placeType.getSelectedItemPosition()]);

                Task<AutocompletePredictionBufferResponse> results =
                        geoDataClient.getAutocompletePredictions(query, null,
                                filterBuilder.build());

                return results;
            }
        }
    }

    public class Place {
        private String placeId;
        private String placeText;
        private String primaryText;

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public String getPlaceText() {
            return placeText;
        }

        public void setPlaceText(String placeText) {
            this.placeText = placeText;
        }

        public String getPrimaryText() {
            return primaryText;
        }

        public void setPrimaryText(String primaryText) {
            this.primaryText = primaryText;
        }
        public String toString(){
            return placeText;
        }
    }


    private void addMarker(String encodedBase64String, String address){

        byte[] decodedString = Base64.decode(encodedBase64String, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Bitmap smallMarker = decodedBitmap.createScaledBitmap(decodedBitmap, 100, 100, false);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(LatImg1, LngImg1));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        markerOptions.title(address);
        mMap.addMarker(markerOptions);
    }


    private void hitGetDistance(String startLat, String startLng, String endLat, String endLng){

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + startLat + "," + startLng + "&destination=" + endLat + "," + endLng + "&mode=driving&sensor=false&key="+getString(R.string.api_key);

        Log.v("hitGetDistance", url);

        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        parseDistance(response);
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

    private void parseDistance(JSONObject response){

        Log.v("res:hitGetDistance",response.toString());

        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = response.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){

                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");

                /** Traversing all legs */

                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */

                    for(int k=0;k<jSteps.length();k++){
                        String distance = "";
                        distance = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("distance")).get("text");

                        Log.v("##distance text:",distance);

                        distance = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("distance")).get("value");

                        Log.v("##distance value:",distance);

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        /*JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(stringBuilder.toString());

            JSONArray array = jsonObject.getJSONArray("routes");

            JSONObject routes = array.getJSONObject(0);

            JSONArray legs = routes.getJSONArray("legs");

            JSONObject steps = legs.getJSONObject(0);

            JSONObject distance = steps.getJSONObject("distance");

            Log.i("Distance", distance.toString());
            dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]","") );

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/



    }
/*    private double getDistanceInfo(double lat1, double lng1, String destinationAddress) {
        StringBuilder stringBuilder = new StringBuilder();
        Double dist = 0.0;
        try {

            destinationAddress = destinationAddress.replaceAll(" ","%20");
            String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + latFrom + "," + lngFrom + "&destination=" + latTo + "," + lngTo + "&mode=driving&sensor=false";

            HttpPost httppost = new HttpPost(url);

            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(stringBuilder.toString());

            JSONArray array = jsonObject.getJSONArray("routes");

            JSONObject routes = array.getJSONObject(0);

            JSONArray legs = routes.getJSONArray("legs");

            JSONObject steps = legs.getJSONObject(0);

            JSONObject distance = steps.getJSONObject("distance");

            Log.i("Distance", distance.toString());
            dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]","") );

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dist;
    }*/
}
