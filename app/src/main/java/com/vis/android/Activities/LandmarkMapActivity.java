package com.vis.android.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LandmarkMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView tv_header, tv_caseheader, tv_caseid;

    RelativeLayout back, rl_casedetail, dots;

    public static Double Lat, Lng;

    Geocoder geocoder;

    GoogleMap mMap;

    Preferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_map);

        init();

    }

    private void init(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pref = new Preferences(this);

        Lat = Double.valueOf(pref.get(Constants.landmark_lat));
        Lng = Double.valueOf(pref.get(Constants.landmark_long));

        MapsInitializer.initialize(this);

        tv_header = findViewById(R.id.tv_header);
        rl_casedetail = findViewById(R.id.rl_casedetail);
        tv_caseheader = findViewById(R.id.tv_caseheader);
        tv_caseid = findViewById(R.id.tv_caseid);
        back = (RelativeLayout) findViewById(R.id.rl_back);

        tv_header.setVisibility(View.GONE);
        rl_casedetail.setVisibility(View.VISIBLE);
        tv_caseheader.setText("Landmark Address");
        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);



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

        //mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        LatLng latLng = new LatLng(Lat, Lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getAddress(getApplicationContext(),Lat,Lng));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);



        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        geocoder = new Geocoder(this, Locale.getDefault());
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 14);
        mMap.animateCamera(cameraUpdate1);

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
