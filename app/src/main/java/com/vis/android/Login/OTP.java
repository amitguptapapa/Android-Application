package com.vis.android.Login;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.SmsReceiver;
import com.vis.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity implements View.OnClickListener {
    Button submit,btn_resend;
    Intent intent;
    char[] chars;
    TextView tv_login;
    // static EditText otp;
    static String part1, part2;
    RelativeLayout back;
    TextView timer;
    TextView resend_otp;
    CustomLoader loader;
    Preferences pref;
    EditText et1, et2, et3, et4, et5, et6;
    String one, two, three, four, five, six, otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getid();
        tv_login.setText("Please enter the verification code send to your mobile number "+"+91-"+pref.get(Constants.mobile_no));
       // tvMobileNumber.setText(pref.get(Constants.mobile_no));
        setListener();
        textWatcher();

        new CountDownTimer(300000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                Log.v("MyTimer", String.valueOf(millisUntilFinished));
                timer.setText("" + String.format("%d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            public void onFinish() {
                timer.setText("done!");
                timer.setVisibility(View.GONE);
                //  Timer.setVisibility(View.VISIBLE);
            }
        }.start();


        resend_otp.postDelayed(new Runnable() {
            public void run() {
                resend_otp.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                btn_resend.setVisibility(View.VISIBLE);
                tv_login.setTextColor(getResources().getColor(R.color.red));

                //tvMobileNumber.setTextColor(getResources().getColor(R.color.red));
            }
        }, 300000);
    }

    public void getid() {
        pref = new Preferences(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        submit = (Button) findViewById(R.id.btn_submit);
        btn_resend = (Button) findViewById(R.id.btn_resend);
        back = (RelativeLayout) findViewById(R.id.rl_back);
        timer = (TextView) findViewById(R.id.tv_timer);
        resend_otp = (TextView) findViewById(R.id.resend_otp);
        //  otp = findViewById(R.id.et_otp);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        tv_login=findViewById(R.id.tv_login);
       // tvMobileNumber=findViewById(R.id.tvMobileNumber);
    }

    public void setListener() {
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        resend_otp.setOnClickListener(this);
        btn_resend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                checkpass();
                break;

            case R.id.btn_resend:
                if (Utils.isNetworkConnectedMainThred(OTP.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    Hit_ResendOtp_Api();

                } else {
                    Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.resend_otp:
                if (Utils.isNetworkConnectedMainThred(OTP.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    Hit_ResendOtp_Api();

                } else {
                    Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    //********************************** Otp api *********************************//

    public void checkpass() {
        if (et1.getText().toString().isEmpty()) {
            et1.setError(getString(R.string.cannot_be_blank));
        } else if (et2.getText().toString().isEmpty()) {
            et2.setError(getString(R.string.cannot_be_blank));
        } else if (et3.getText().toString().isEmpty()) {
            et3.setError(getString(R.string.cannot_be_blank));
        } else if (et4.getText().toString().isEmpty()) {
            et4.setError(getString(R.string.cannot_be_blank));
        } else if (et5.getText().toString().isEmpty()) {
            et5.setError(getString(R.string.cannot_be_blank));
        } else if (et6.getText().toString().isEmpty()) {
            et6.setError(getString(R.string.cannot_be_blank));
        } else {
            if (Utils.isNetworkConnectedMainThred(OTP.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                Hit_Otp_Api();

            } else {
                Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void Hit_Otp_Api() {
        add();
        String url = Utils.getCompleteApiUrl(this, R.string.verify_otp);
        Log.v("Hit_Otp_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("mobile_no", pref.get(Constants.mobile_no));
            jsonObject.put("otp", otp);
            //jsonObject.put("Device_id", Utils.getDeviceID(this));
            jsonObject.put("Device_id", pref.get(Constants.fcmId));
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
                            Toast.makeText(OTP.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, res_msg, Toast.LENGTH_SHORT).show();
                pref.set(Constants.user_id, jsonObject.getString("user_id"));
                pref.set(Constants.surveyor_id, jsonObject.getString("surveyor_id"));
                pref.set(Constants.surveyor_name, jsonObject.getString("surveyor_Name"));
                pref.set(Constants.surveyor_contact, jsonObject.getString("surveyor_contact"));

                pref.commit();
                intent = new Intent(OTP.this, Dashboard.class);
                startActivity(intent);

            } else {
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et5.setText("");
                et6.setText("");

                et1.requestFocus();
                et1.setCursorVisible(true);

                Toast.makeText(OTP.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(OTP.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    private void Hit_ResendOtp_Api() {
        //add();
        String url = Utils.getCompleteApiUrl(this, R.string.resent_otp);
        Log.v("Hit_ResendOtp_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("mobile_no", pref.get(Constants.mobile_no));
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
                        parseJsonResend(response);
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
                            Toast.makeText(OTP.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonResend(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("res_msg");
            String msg = jsonObject.getString("res_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                Toast.makeText(this, res_msg, Toast.LENGTH_SHORT).show();
                intent = new Intent(OTP.this, OTP.class);
                startActivity(intent);

            } else {
                Toast.makeText(OTP.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(OTP.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

/*    public static void recivedSms(String message) {
        try {

            String parts[] = message.split("This is your VIS OTP:");
            part1 = parts[0];
            part2 = parts[1];
            System.out.println("part is ==========================" + part1 + part2);
           // otp.setText(part2);
        } catch (Exception e) {
            Log.v("OTPEX", e.toString());
            System.out.println("part is ===========" + e.toString());
        }
    }*/

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void textWatcher() {

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    et1.clearFocus();
                    et2.requestFocus();
                    et2.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et2.clearFocus();
                    et1.requestFocus();
                    et1.setCursorVisible(true);
                } else {
                    et2.clearFocus();
                    et3.requestFocus();
                    et3.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et3.clearFocus();
                    et2.requestFocus();
                    et2.setCursorVisible(true);
                } else {
                    et3.clearFocus();
                    et4.requestFocus();
                    et4.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et4.clearFocus();
                    et3.requestFocus();
                    et3.setCursorVisible(true);
                } else {
                    et4.clearFocus();
                    et5.requestFocus();
                    et5.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et5.clearFocus();
                    et4.requestFocus();
                    et4.setCursorVisible(true);
                } else {
                    et5.clearFocus();
                    et6.requestFocus();
                    et6.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et6.clearFocus();
                    et5.requestFocus();
                    et5.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void add() {
        one = et1.getText().toString();
        two = et2.getText().toString();
        three = et3.getText().toString();
        four = et4.getText().toString();
        five = et5.getText().toString();
        six = et6.getText().toString();

        otp = one + two + three + four + five + six;
    }

    private SmsReceiver receiver = new SmsReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                Log.v("Message", message);
                chars = message.toCharArray();

                Log.v("Chars", String.valueOf(chars));

                Log.v("Chars0", String.valueOf(chars[0]));
                et1.setText(String.valueOf(chars[0]));

                Log.v("Chars1", String.valueOf(chars[1]));
                et2.setText(String.valueOf(chars[1]));

                Log.v("Chars1", String.valueOf(chars[2]));
                et3.setText(String.valueOf(chars[2]));

                Log.v("Chars5", String.valueOf(chars[3]));
                et4.setText(String.valueOf(chars[3]));

                et5.setText(String.valueOf(chars[4]));
                et6.setText(String.valueOf(chars[5]));

                //otp.setText(message);
                add();

                if (Utils.isNetworkConnectedMainThred(getApplicationContext())) {
                    Hit_Otp_Api();
                } else {
                    Toast.makeText(OTP.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        }
    };

    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
