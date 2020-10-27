package com.vis.android.Activities;

import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.vis.android.Extras.GetLocation;
import com.vis.android.R;

import java.util.ArrayList;


public class ViewMapActivity extends AppCompatActivity {
    GoogleMap Gmap;
    GetLocation Location;
    ArrayList<LatLng> markerPoints;
    public static Double Lat, Lng;
    LatLng latLng;
    Geocoder geocoder;
    public static MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);

        /*Location = new GetLocation(this);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        Gmap = mapView.getMap();
        Gmap.setMyLocationEnabled(true);
        Gmap.getUiSettings().setMyLocationButtonEnabled(true);
        Gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = Gmap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        // Change loaction button position...
        View locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        // rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(30, 30, 30, 30);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this);
        markerPoints = new ArrayList<>();


        Lat = Location.getLatitude();
        Lng = Location.getLongitude();

        geocoder = new Geocoder(this, Locale.getDefault());
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 14);
        Gmap.animateCamera(cameraUpdate1);
        Gmap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
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
        });
        int height = 50;
        int width = 50;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.postal_address);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        Gmap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)))
                .title(latitude + "," + longitude)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        Gmap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.valueOf(landmark_lat), Double.valueOf(landmark_long)))
                .title(landmark_lat + "," + landmark_long)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.landmark_address)));*/
    }

    @Override
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
    }
}
