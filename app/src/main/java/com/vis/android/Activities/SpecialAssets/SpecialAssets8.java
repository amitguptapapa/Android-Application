package com.vis.android.Activities.SpecialAssets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableSpecialAssets;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.vis.android.Fragments.CaseDetail.case_id;
import static com.vis.android.Activities.SpecialAssets.SpecialAssets1.hm;

public class SpecialAssets8 extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    TextView submit;
    //layout
    RelativeLayout rlTvsign;
    LinearLayout mContent;
    RelativeLayout rlCross;
    Dialog dialog1;
    public static String tempDir;
    private String uniqueId;
    public String current = null;
    private Bitmap mBitmap;
    View mView;
    File mypath;
    signature mSignature;
    ImageView mClear;
    Button mGetSign, mCancel;
    String encodedString;

    TextView tvSave;

    EditText etSurveyorName;

    byte[] byteFormat;
    String imgString = "";
    ImageView signImage;
    Preferences pref;
    CustomLoader loader;
    String status = "0";

    //ArrayList
    public static ArrayList<HashMap<String, String>> arrayListData = new ArrayList<>();

    public static JSONArray jsonArrayData;

    public static ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets8);
        getid();
        setListener();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rlTvsign = (RelativeLayout) findViewById(R.id.rlTvsign);
        rlTvsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignatureDialog();
            }
        });
    }

    public void getid() {
        pref = new Preferences(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        submit = (TextView) findViewById(R.id.tv_submit);
        tvSave = findViewById(R.id.tvSave);

        etSurveyorName = findViewById(R.id.etSurveyorName);

        signImage = findViewById(R.id.imageView1);
    }

    public void setListener() {
        submit.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (Utils.isNetworkConnectedMainThred(SpecialAssets8.this)) {
                    status = "2";
                    validation();
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    Hit_FormSubmission_Api();

                } else {
                    Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.tvSave:
                status = "1";
                validation();
                break;
        }
    }

    public void validation() {
        if (etSurveyorName.getText().toString().isEmpty()) {
            //Toast.makeText(SpecialAssets8.this, "Please Enter Surveyor Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Enter Surveyor Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (signImage.getDrawable() == null) {
            //Toast.makeText(SpecialAssets8.this, "Please Do Signature", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Do Signature", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
      /*  else if (imgString.isEmpty()){
            Toast.makeText(SpecialAssets8.this, "Please Do Signature", Toast.LENGTH_SHORT).show();
        }*/

        else {
            putIntoHm();
            // Toast.makeText(SpecialAssets8.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Data Saved Successfully", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }
    }

    public void putIntoHm() {
        signImage.buildDrawingCache();
        Bitmap bitmapp = signImage.getDrawingCache();
        ByteArrayOutputStream streamm = new ByteArrayOutputStream();
        bitmapp.compress(Bitmap.CompressFormat.PNG, 90, streamm);
        byte[] image = streamm.toByteArray();
        System.out.println("byte array:" + image);
        String img_str = Base64.encodeToString(image, 0);
        System.out.println("string:" + img_str);


        hm.put("undertakingSurveyor", etSurveyorName.getText().toString());
        hm.put("signature2", img_str);
        arrayListData.add(hm);
        Log.v("###ArrayData2", String.valueOf(arrayListData));
        jsonArrayData = new JSONArray(arrayListData);

        if (status.equals("1")) {
            DatabaseController.removeTable(TableSpecialAssets.attachment);
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableSpecialAssets.specialAssetColumn.data.toString(), jsonArrayData.toString());
            Log.v("###contentValues", String.valueOf(contentValues));
            DatabaseController.insertData(contentValues, TableSpecialAssets.attachment);

        } else {
            Log.v("###jso", jsonArrayData.toString());
        }


    }

    public void selectDB() throws JSONException {
        sub_cat = DatabaseController.getSpecialAssets();
        Log.v("getfromdb2=====", String.valueOf(sub_cat));

        JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

        Log.v("getfromdbarray=====", String.valueOf(array));

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            etSurveyorName.setText(object.getString("undertakingSurveyor"));

            byteFormat = Base64.decode(object.getString("signature2"), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
            signImage.setImageBitmap(decodedImage);


            //GroupHousing1.etProjectName.setText(object.getString("projectName"));

           /* String projectName = object.getString("letterOfIntent");
            Log.e("!!!letter", projectName);*/

        }
    }


    public class signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (mBitmap == null) {
                mBitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
                ;
            }
            Canvas canvas = new Canvas(mBitmap);
            try {
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);

                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                // String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                encodedString = getEncoded64ImageStringFromBitmap(mBitmap);

                signImage.setImageBitmap(mBitmap);


                Log.v("log_tag", "url: " + encodedString);


            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }
        }


        //method to convert string into base64
        public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byteFormat = stream.toByteArray();
            // get the base 64 string
            imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

            //////

            /////

            return imgString;
        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    private void SignatureDialog() {
        dialog1 = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.signature);
        Window window = dialog1.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams layoutParams = dialog1.getWindow().getAttributes();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath = new File(directory, current);

        rlCross = (RelativeLayout) dialog1.findViewById(R.id.rlCross);
        mContent = (LinearLayout) dialog1.findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mClear = (ImageView) dialog1.findViewById(R.id.clear);
        mGetSign = (Button) dialog1.findViewById(R.id.getsign);
        //mGetSign.setEnabled(false);
        mCancel = (Button) dialog1.findViewById(R.id.cancel);
        mView = mContent;


        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                imgString = "";
                // mGetSign.setEnabled(false);
            }
        });
        mGetSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Saved");
                boolean error = captureSignature();
                if (!error) {
                    mView.setDrawingCacheEnabled(true);
                    mSignature.save(mView);
                    Bundle b = new Bundle();
                    b.putString("status", "done");
                    Intent intent = new Intent();
                    intent.putExtras(b);
                    setResult(RESULT_OK, intent);
                    dialog1.dismiss();

                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");
                Bundle b = new Bundle();
                b.putString("status", "cancel");
                Intent intent = new Intent();
                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        rlCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });


        dialog1.show();
        dialog1.setCanceledOnTouchOutside(true);


    }


    private boolean captureSignature() {

        boolean error = false;
        String errorMessage = "";

        if (error) {
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 50);
            toast.show();
        }

        return error;
    }

    @SuppressLint("WrongConstant")
    private boolean prepareDirectory() {
        try {
            if (makedirs()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean makedirs() {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory()) {
            File[] files = tempdir.listFiles();
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }

    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate = (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:", String.valueOf(todaysDate));
        return (String.valueOf(todaysDate));

    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:", String.valueOf(currentTime));
        return (String.valueOf(currentTime));

    }


    //********************************** Hit FormSubmission Api *********************************//
    private void Hit_FormSubmission_Api() {

        String url = Utils.getCompleteApiUrl(this, R.string.form_submission);
        Log.v("Hit_FormSubmission_Api", url);

        JSONObject json_data = new JSONObject();

        try {
            json_data.put("surveyor_id", pref.get(Constants.surveyor_id));
            json_data.put("case_id", case_id);
            json_data.put("form_id", "4");
            json_data.put("form_data", jsonArrayData);

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
                            Toast.makeText(SpecialAssets8.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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
                intent = new Intent(SpecialAssets8.this, Dashboard.class);
                startActivity(intent);

            } else {
                Toast.makeText(SpecialAssets8.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }
            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SpecialAssets8.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
