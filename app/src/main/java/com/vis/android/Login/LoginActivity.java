package com.vis.android.Login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button login;
    Intent intent;
    EditText mobile;
    CustomLoader loader;
    Preferences pref;
    TextView tv_help,tv_help_number;
    //Permissions
    private static final int READ_EXTRENAL_MEDIA_PERMISSIONS_REQUEST = 1;
    private static final int REQUEST_PERMISSIONS = 1;
    private static String[] PERMISSIONS = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    //private BroadcastReceiver mRegistrationBroadcastReceiver;
    //public static String regId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getid();
        setListener();
       // String text = "<font color='black'>Please contact Help desk - </font><font color='blue'>+91-9958 632707</font>";
        //tv_help.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getRunTimePermission();
        broadcast();
    }

    public void getid() {
        pref = new Preferences(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        login = (Button) findViewById(R.id.btn_login);
        mobile = findViewById(R.id.et_mobile);
        tv_help = findViewById(R.id.tv_help);
        tv_help_number = findViewById(R.id.tv_help_number);
    }

    public void setListener() {
        login.setOnClickListener(this);
        tv_help_number.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkpass();
                break;
            case R.id.tv_help_number:

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tv_help.getText().toString()));//"tel:"+number.getText().toString();
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
                startActivity(callIntent);

                break;
        }
    }

    public void checkpass() {
        if (mobile.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter mobile number", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (mobile.getText().toString().length() < 10) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Mobile number can not be less than 10", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else {
            if (Utils.isNetworkConnectedMainThred(LoginActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                Hit_Login_Api();

            } else {
                Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

            }
        }
    }

    //********************************** Login api *********************************//
    private void Hit_Login_Api() {

        String url = Utils.getCompleteApiUrl(this, R.string.login);
        Log.e("url",url);
        Log.v("Hit_Login_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {

            jsonObject.put("mobile_no", mobile.getText().toString());
            //jsonObject.put("device_id", Utils.getDeviceID(this));
            jsonObject.put("device_id", pref.get(Constants.fcmId));
            jsonObject.put("latitude", "26.2020");
            jsonObject.put("longitude", "25.3625");
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
                            Toast.makeText(LoginActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJson(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                pref.set(Constants.mobile_no, jsonObject.getString("mobile_no"));
                pref.commit();
                Toast.makeText(this, res_msg, Toast.LENGTH_SHORT).show();
                intent = new Intent(LoginActivity.this, OTP.class);
                startActivity(intent);

            } else {
                Toast.makeText(LoginActivity.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    public void getRunTimePermission() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            Log.d("Permission for contacts", "Displaying contacts permission rationale to provide additional context.");

            ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS, REQUEST_PERMISSIONS);
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(Drawer_Activity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(Drawer_Activity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_EXTRENAL_MEDIA_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoginActivity.this, "EXTRENAL_MEDIA Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "EXTRENAL_MEDIA Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void broadcast() {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

                pref.set(Constants.fcmId,newToken);
                pref.commit();
            }
        });

        /*mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    //FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();
                    Log.v("frm_id", regId);
                    Toast.makeText(context, regId, Toast.LENGTH_SHORT).show();
                    // AppSettings.putString(AppSettings.fcmId, regId);


                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {

                    String message = intent.getStringExtra("message");
                    Log.v("msg", message);
                }
            }
        };*/

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("Key: ", key + " Value: " + value);
            }
        }
    }

    /*private void displayFirebaseRegId() {

        //SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.get(Constants.fcmId);
        Log.e("Firebase reg id: ", regId);

        if (!TextUtils.isEmpty(regId)) {            // txtRegId.setText("Firebase Reg Id: " + regId);
            Log.v("id", "Firebase Reg Id: " + regId);
        } else {
            Log.v("not_received", "Firebase Reg Id is not received yet!");
        }


    }*/
}
