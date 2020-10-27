package com.vis.android.Activities.General;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.Extras.GetLocation;
import com.vis.android.R;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MeasureDistanceActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback {

    RelativeLayout back,rl_casedetail,rl_dots;

    TextView tv_caseheader,tv_caseid,tv_header,tvDistance,tvArea;

    Preferences pref;

    CustomLoader loader;

    GoogleMap mMap;

    GetLocation getLocation;

    public static Double Lat, Lng,PrevLat,PrevLng;
    private boolean firstAdd = true;

    List<Polyline> polyLines = new ArrayList<Polyline>();
    List<Marker> markers = new ArrayList<>();
    Polyline polyline;
    private double measuredDistance = 0;
    List<LatLng> latLngs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_distance);

        init();
        setListeners();

    }

    private void init(){

        latLngs.clear();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MapsInitializer.initialize(this);

        pref = new Preferences(mActivity);

        getLocation = new GetLocation(mActivity);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        back = findViewById(R.id.rl_back);
        rl_dots = findViewById(R.id.rl_dots);

        tv_header = findViewById(R.id.tv_header);
        tv_caseheader = findViewById(R.id.tv_caseheader);
        tv_caseid = findViewById(R.id.tv_caseid);
        rl_casedetail = findViewById(R.id.rl_casedetail);
        tvDistance = findViewById(R.id.tvDistance);
        tvArea = findViewById(R.id.tvArea);

        rl_casedetail.setVisibility(View.VISIBLE);
        tv_header.setVisibility(View.GONE);
        rl_dots.setVisibility(View.GONE);

        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

        tv_caseheader.setText("Measure Distance");



    }

    private void setListeners(){
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.rl_back:
                onBackPressed();
                break;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        markers.clear();
        polyLines.clear();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mMap.getUiSettings().setZoomControlsEnabled(false);

        final LatLng latLng = new LatLng(getLocation.getLatitude(),getLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getAddress(getApplicationContext(),getLocation.getLatitude(),getLocation.getLongitude()));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(getLocation.getLatitude(),getLocation.getLongitude()), 16f);
        mMap.animateCamera(cameraUpdate1);

        //Lat = getLocation.getLatitude();
       // Lng = getLocation.getLongitude();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                latLngs.add(latLng);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(getAddress(getApplicationContext(),latLng.latitude,latLng.longitude));
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                //mMap.addMarker(markerOptions).setDraggable(true);
                Marker marker = mMap.addMarker(markerOptions);
                markers.add(marker);

                CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), mMap.getCameraPosition().zoom);
                mMap.animateCamera(cameraUpdate1);

                if (!firstAdd){

                    PrevLat = Lat;
                    PrevLng = Lng;

                    drawPolyLine(Lat,Lng,latLng.latitude,latLng.longitude);

                    polyLines.add(polyline);

                    for (int i =0; i<markers.size();i++){

                        if (i == markers.size()-1){
                            markers.get(i).setDraggable(true);
                        }else {
                            markers.get(i).setDraggable(false);
                        }

                    }

                   // float[] results = new float[2];
                   // Location.distanceBetween(Lat,Lng,latLng.latitude,latLng.longitude,results);

                    try {

                        latLngs.add(new LatLng(latLng.latitude, latLng.longitude));

                        Log.i("areaassf", "computeArea " + SphericalUtil.computeArea(latLngs));

                        tvArea.setText("Measured Area: "+String.format("%.2f", SphericalUtil.computeArea(latLngs)));

                        measuredDistance = measuredDistance + calculateDistance(Lat,Lng,latLng.latitude,latLng.longitude);

                        if (measuredDistance >= 1000){

                            Log.v("measureDist",measuredDistance/1000+" km");

                            double inKm = measuredDistance/1000;

                            tvDistance.setText("Measured Distance: "+String.format("%.2f",inKm)+" km");
                        }else {
                            Log.v("measureDist",measuredDistance+" m");
                            tvDistance.setText("Measured Distance: "+String.format("%.2f",measuredDistance)+" m");
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }

                Lat = latLng.latitude;
                Lng = latLng.longitude;

                firstAdd = false;


            }

        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
             //   Log.d("System out", "onMarkerDragStart...");
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker marker) {
            //    Log.d("System out", "onMarkerDragEnd...");
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                for (int i =0; i<polyLines.size();i++){

                    polyline.remove();

                    polyLines.remove(i);

                    break;

                }



                drawPolyLine(PrevLat,PrevLng,marker.getPosition().latitude,marker.getPosition().longitude);


                latLngs.remove(latLngs.size());

                latLngs.add(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));

                Log.i("areaassf", "computeArea " + SphericalUtil.computeArea(latLngs));

                tvArea.setText("Measured Area: "+String.format("%.2f", SphericalUtil.computeArea(latLngs)));

                measuredDistance = measuredDistance + calculateDistance(PrevLat,PrevLng,marker.getPosition().latitude,marker.getPosition().longitude);

                if (measuredDistance >= 1000){
                    Log.v("measureDist",measuredDistance/1000+" km");

                    double inKm = measuredDistance/1000;

                    tvDistance.setText("Measured Distance: "+String.format("%.2f",inKm)+" km");

                }else {
                    Log.v("measureDist",measuredDistance+" m");

                    tvDistance.setText("Measured Distance: "+String.format("%.2f",measuredDistance)+" m");
                }


                Lat = marker.getPosition().latitude;
                Lng = marker.getPosition().longitude;

                polyLines.add(polyline);

            }

            @Override
            public void onMarkerDrag(Marker arg0) {
            }
        });
    }

    private void drawPolyLine(Double oldLat, Double oldLng,Double newLatitude, Double newLongitude){
        PatternItem DOT = new Dot();
        //   PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
        PatternItem GAP = new Gap(15);
        List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(DOT,GAP);

        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(getResources().getColor(R.color.blueMapLine));
        polyOptions.add(new LatLng(oldLat, oldLng), new LatLng(newLatitude, newLongitude));
        polyOptions.pattern(PATTERN_POLYGON_ALPHA);
        polyOptions.width(15);

        polyline = mMap.addPolyline(polyOptions);
    }

    public double calculateDistance(Double startLat,Double startLng,Double endLat,Double endLng) {
        int Radius=6371000;//radius of earth in mtr
        double dLat = Math.toRadians(endLat-startLat);
        double dLon = Math.toRadians(endLng-startLng);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));

        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
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

}
