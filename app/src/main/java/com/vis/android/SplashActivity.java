package com.vis.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {
    Intent intent;
    private static int TIME_OUT = 4000;
    CustomLoader loader;
    Preferences pref;
    RelativeLayout relMain;
    RelativeLayout rlView;
    public static ArrayList<HashMap<String, String>> master_array_list = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = new Preferences(this);
        Log.v("device_id===", Utils.getDeviceID(this));

        relMain = findViewById(R.id.relMain);
        rlView = findViewById(R.id.rlView);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        if (Utils.isNetworkConnectedMainThred(SplashActivity.this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            Hit_MasterData_Api();

        } else {
            Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

        }
    }


    private void Hit_MasterData_Api() {

        String url = Utils.getCompleteApiUrl(this, R.string.get_master_data);

        Log.v("Hit_MasterData_Api", url);

        AndroidNetworking.get(url).setPriority(Priority.HIGH).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseJsonpro(response);
                } catch (Exception e) {
                }
            }

            @Override

            public void onError(ANError error) {
                // handle error
                if (error.getErrorCode() != 0) {
                    loader.cancel();
                    Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                    Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                    Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                    Toast.makeText(SplashActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                } else {
                    loader.cancel();
                    // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                    Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                    Toast.makeText(SplashActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void parseJsonpro(JSONObject response) {

        Log.v("Splashresponse", response.toString());
        master_array_list.clear();
        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String msg = jsonObject.getString("response_message");
            JSONArray array_asset= new JSONArray();
            if (res_code.equals("1")) {
                array_asset= jsonObject.getJSONArray("Company_asset");
                JSONArray array_purpose = jsonObject.getJSONArray("Assignment_purpose_data");
                JSONArray surveyor_data = jsonObject.getJSONArray("surveyor_data");
                JSONArray array_reassign = jsonObject.getJSONArray("reassign_reason_data");
                JSONArray array_cancel = jsonObject.getJSONArray("Cancel_reason_data");
                JSONArray array_units = jsonObject.getJSONArray("units_data");
//                JSONArray array_property = jsonObject.getJSONArray("type_of_property_data");
                JSONArray array_survey_not_req = jsonObject.getJSONArray("survey_not_req");
                JSONArray assest_nature_list = jsonObject.getJSONArray("nature_list");
                JSONArray assest_category_list = jsonObject.getJSONArray("category");
                JSONArray asset_list = jsonObject.getJSONArray("asset_list");

                HashMap<String, String> cancle_hash = new HashMap<String, String>();
                cancle_hash.put("array_cancel", String.valueOf(array_cancel));
                cancle_hash.put("array_asset", String.valueOf(array_asset));
                cancle_hash.put("array_purpose", String.valueOf(array_purpose));
                cancle_hash.put("surveyor_data", String.valueOf(surveyor_data));
                cancle_hash.put("array_reassign", String.valueOf(array_reassign));
                cancle_hash.put("array_units", String.valueOf(array_units));
//                cancle_hash.put("array_property", String.valueOf(array_property));
                cancle_hash.put("array_survey_not_req", String.valueOf(array_survey_not_req));

                pref.set(Constants.cancel_array, String.valueOf(array_cancel));
                pref.set(Constants.array_asset, String.valueOf(array_asset));
                pref.set(Constants.array_purpose, String.valueOf(array_purpose));
                pref.set(Constants.surveyor_data, String.valueOf(surveyor_data));
                pref.set(Constants.array_reassign, String.valueOf(array_reassign));
                pref.set(Constants.array_units, String.valueOf(array_units));
                pref.set(Constants.assest_nature_list, String.valueOf(assest_nature_list));
                pref.set(Constants.assest_category_list, String.valueOf(assest_category_list));
                pref.set(Constants.asset_list, String.valueOf(asset_list));
                pref.set(Constants.array_survey_not_req, String.valueOf(array_survey_not_req));
                pref.commit();
                Log.v("rechedule_array=====", pref.get(Constants.array_reassign));
                master_array_list.add(cancle_hash);
                // viewMenu();
                if (pref.get(Constants.user_id).equals("")) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                } else {
                    intent = new Intent(SplashActivity.this, Dashboard.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                }
            } else {
                Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }

            loader.cancel();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(SplashActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }

    }

   /* private void viewMenu() {

        int x = rlView.getRight();
        int y = rlView.getBottom();

        int startRadius = 0;

        int endRadius = (int) Math.hypot(relMain.getWidth(), relMain.getHeight());

        final Animator anim = ViewAnimationUtils.createCircularReveal(relMain, x, y, startRadius, endRadius);
        anim.setDuration(2500);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (pref.get(Constants.user_id).equals("")) {
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            intent = new Intent(SplashActivity.this, Dashboard.class);
                            startActivity(intent);
                        }
                       *//* Intent intent = new Intent(getApplicationContext(), Da.class);
                        startActivity(intent);
                        SplashActivity.this.finish();*//*

                    }
                }, 100);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();
    }*/
}
