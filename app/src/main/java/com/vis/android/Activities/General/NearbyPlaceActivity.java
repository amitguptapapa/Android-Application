package com.vis.android.Activities.General;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.model.LatLng;
import com.vis.android.Common.AppUtils;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.Extras.GetLocation;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.internal.Util;

public class NearbyPlaceActivity extends BaseActivity implements View.OnClickListener {

    RecyclerView recyclerView;

    RelativeLayout rl_casedetail,back;

    TextView tv_caseheader,tv_caseid,tv_header,tvNotAvailable;

    NearbyPlaceAdapter nearbyPlaceAdapter;

    private String mAddressOutput,mAreaOutput,mCityOutput,mStateOutput,mState,mZipCode,mFeature,mCountry,mPremises,mSubAdminArea,
            mSubLocality,mThoroughfare,mAddressOutputNew;

    Preferences pref;

    private ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

    CustomLoader loader;

    public static int checkPage = 0;

    RelativeLayout rl_dots;

    GetLocation location;
    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_place);

        init();
        setListeners();

        if (Utils.isNetworkConnectedMainThred(mActivity)){
            hitPlacesApi(pref.get(Constants.keyword));
        }
    }

    private void init(){
        pref = new Preferences(mActivity);

        location = new GetLocation(mActivity);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        rl_casedetail = findViewById(R.id.rl_casedetail);
        rl_dots = findViewById(R.id.rl_dots);
        rl_dots.setVisibility(View.GONE);
        back = findViewById(R.id.rl_back);

        tvNotAvailable = findViewById(R.id.tvNotAvailable);

        tv_header = findViewById(R.id.tv_header);
        tv_caseheader = findViewById(R.id.tv_caseheader);
        tv_caseid = findViewById(R.id.tv_caseid);

        tv_header.setVisibility(View.GONE);
        rl_casedetail.setVisibility(View.VISIBLE);

        if (pref.get(Constants.keyword).equalsIgnoreCase("school")){
            tv_caseheader.setText("Nearby Schools");
        }else if (pref.get(Constants.keyword).equalsIgnoreCase("hospital")){
            tv_caseheader.setText("Nearby Hospitals");
        }else if (pref.get(Constants.keyword).equalsIgnoreCase("supermarket")){
            tv_caseheader.setText("Nearby Market");
        }else if (pref.get(Constants.keyword).equalsIgnoreCase("airport")){
            tv_caseheader.setText("Nearby Airports");
        }else if (pref.get(Constants.keyword).equalsIgnoreCase("train_station")){
            tv_caseheader.setText("Nearby Railway Stations");
        }else if (pref.get(Constants.keyword).equalsIgnoreCase("subway_station")){
            tv_caseheader.setText("Nearby Metro Stations");
        }else if (pref.get(Constants.keyword).equalsIgnoreCase("supermarket")){
            tv_caseheader.setText("Nearby Metro Markets");
        }

        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));
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

    private void hitPlacesApi(final String keyword) {

        dataList.clear();

        loader.setCanceledOnTouchOutside(false);
        loader.setCancelable(false);
        loader.show();

        //  String url = Utils.getCompleteApiUrl(this, R.string.GetGeneralSurveyInfo);
        Log.v("hitPlacesApi", "hit");

        try {
            mLatitude = getLatLongFromAddress(getApplicationContext(),pref.get(Constants.assetAddress)).latitude;
            mLongitude = getLatLongFromAddress(getApplicationContext(),pref.get(Constants.assetAddress)).longitude;

            AndroidNetworking.post("https://maps.googleapis.com/maps/api/place/nearbysearch/json?&location="+mLatitude+","+mLongitude+"&rankby=distance&types="+keyword+"&sensor=true&key=AIzaSyALTsVJy_Py_INw-TFmLqdU9lGHQHiqX2k")
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            parseJsonGet(response,keyword);
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

        }catch (Exception e){
            e.printStackTrace();

            loader.cancel();

            Toast.makeText(mActivity, "Latitude & Longitude not available", Toast.LENGTH_SHORT).show();
        }


    }

    private void parseJsonGet(JSONObject response, String keyword) {

        Log.v("res:hitPlacesApi", response.toString());

        try {
            if (response.getString("status").equalsIgnoreCase("OK")){

                recyclerView.setVisibility(View.VISIBLE);
                tvNotAvailable.setVisibility(View.GONE);

                JSONArray jsonArrayResult = response.getJSONArray("results");

                for (int i =0;i<jsonArrayResult.length();i++){
                    JSONObject jsonObject = jsonArrayResult.getJSONObject(i);

                    JSONObject geometry = jsonObject.getJSONObject("geometry");

                    JSONObject location = geometry.getJSONObject("location");

                    String lat = location.getString("lat");
                    String lng = location.getString("lng");

                    String types = jsonObject.getJSONArray("types").getString(0);

                    if (types.equalsIgnoreCase(keyword)){

                        HashMap hashMap = new HashMap();

                        hashMap.put("name",jsonObject.getString("name"));
                        hashMap.put("vicinity",jsonObject.getString("vicinity"));
                        hashMap.put("lat",lat);
                        hashMap.put("lng",lng);

                        dataList.add(hashMap);
                    }

                }

                nearbyPlaceAdapter = new NearbyPlaceAdapter(dataList);
                recyclerView.setAdapter(nearbyPlaceAdapter);

            }else {
                recyclerView.setVisibility(View.GONE);
                tvNotAvailable.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        loader.cancel();

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
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public class NearbyPlaceAdapter extends RecyclerView.Adapter<HolderNearbyPlace> {
        ArrayList<HashMap<String ,String >> alist = new ArrayList<>();

        public NearbyPlaceAdapter(ArrayList<HashMap<String ,String >> banner) {
            alist = banner;
        }

        public HolderNearbyPlace onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderNearbyPlace(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_neaby_place_layout, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderNearbyPlace holder, final int position) {

            holder.tvTitle.setText(alist.get(position).get("name"));
            holder.tvAddress.setText(alist.get(position).get("vicinity"));

            getDistance(mLatitude,mLongitude,Double.valueOf(alist.get(position).get("lat")),Double.valueOf(alist.get(position).get("lng")),holder.tvDistance);
//            double measuredDistance = Utils.calculateDistance(mLatitude,
//                    mLongitude,
//                    Double.valueOf(alist.get(position).get("lat")),
//                    Double.valueOf(alist.get(position).get("lng")));
//
////            if (measuredDistance >= 1000){
////
////                Log.v("measureDist",measuredDistance/1000+" km");
////
////                double inKm = measuredDistance/1000;
////
////                holder.tvDistance.setText(String.format("%.2f",inKm)+" km");
////            }else {
////                //   Log.v("measureDist",measuredDistance+" m");
////                holder.tvDistance.setText(String.format("%.2f",measuredDistance)+" m");
////            }


            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (pref.get(Constants.keyword).equalsIgnoreCase("school")){
                        pref.set(Constants.schoolName,alist.get(position).get("name")+"   ("+holder.tvDistance.getText()+")");
                    }else if (pref.get(Constants.keyword).equalsIgnoreCase("hospital")){
                        pref.set(Constants.hospitalName,alist.get(position).get("name")+"   ("+holder.tvDistance.getText()+")");
                    }else if (pref.get(Constants.keyword).equalsIgnoreCase("airport")){
                        pref.set(Constants.airportName,alist.get(position).get("name")+"   ("+holder.tvDistance.getText()+")");
                    }else if (pref.get(Constants.keyword).equalsIgnoreCase("supermarket")){
                        pref.set(Constants.marketName,alist.get(position).get("name")+"   ("+holder.tvDistance.getText()+")");
                    }else if (pref.get(Constants.keyword).equalsIgnoreCase("subway_station")){
                        pref.set(Constants.metroName,alist.get(position).get("name")+"   ("+holder.tvDistance.getText()+")");
                    }else if (pref.get(Constants.keyword).equalsIgnoreCase("train_station")){
                        pref.set(Constants.railwayStationName,alist.get(position).get("name")+"   ("+holder.tvDistance.getText()+")");
                    }

                    checkPage = 1;

                    pref.commit();

                    finish();

                    // startActivity(new Intent(mActivity,GeneralForm3.class));

                }
            });

        }

        public int getItemCount() {
            return alist.size();
        }
        private void getDistance(Double latitude, Double longitude, Double placeLat, Double placeLng, final TextView textView) {
            String url = makeURL(latitude, longitude,  placeLat, placeLng);
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
                                textView.setText(distance);

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
    }

    private static class HolderNearbyPlace extends RecyclerView.ViewHolder {

        TextView tvTitle,tvAddress,tvDistance;
        RelativeLayout rlMain;
        ImageView ivImage;

        public HolderNearbyPlace(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            rlMain = itemView.findViewById(R.id.rlMain);

        }
    }
}
