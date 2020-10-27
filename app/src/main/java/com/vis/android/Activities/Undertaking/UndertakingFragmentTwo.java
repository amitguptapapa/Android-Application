package com.vis.android.Activities.Undertaking;

import android.Manifest;
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
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableGeneralForm;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;
import static com.vis.android.Fragments.CaseDetail.case_id;

public class UndertakingFragmentTwo extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS = 1;
    public static String tempDir;
    private static String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //String
    public String encodedString = "", status = "";
    public String current = null;
    TextView tvSave, tvPrevious, tv_submit;
    //Bitmap
    Bitmap bitmap;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    ArrayList abc = new ArrayList();
    EditText etSurveyorName, etSurveyorId, etDate;
    Boolean edit_page = true;
    RelativeLayout rlTvsign;
    LinearLayout mContent;
    RelativeLayout rlCross;
    Dialog dialog1;
    View mView;
    File mypath;
    //signature mSignature;
    ImageView mClear;
    Button mGetSign, mCancel;
    String encodedStringg;
    byte[] byteFormat;
    String imgString = "";
    ImageView signImage;
    CustomLoader loader;
    Intent intent;
    Preferences pref;
    View v;
    private String uniqueId;
    private Bitmap mBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_undertaking_two, container, false);

        getid(v);
        setListener();
        getPermission();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!edit_page) {
            setEditiblity();
        }

        return v;
    }

    public void setEditiblity() {
        etSurveyorName.setEnabled(false);
        etSurveyorId.setEnabled(false);
        etDate.setEnabled(false);
        signImage.setEnabled(false);
        tv_submit.setVisibility(View.INVISIBLE);
        tv_submit.setEnabled(false);

        tvSave.setVisibility(View.INVISIBLE);
        tvSave.setEnabled(false);
                rlTvsign.setEnabled(false);

    }

    public void getPermission() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            Log.d("Permission", "Displaying permission rationale to provide additional context.");

            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, REQUEST_PERMISSIONS);
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, REQUEST_PERMISSIONS);
        }
        // END_INCLUDE(contacts_permission_request)
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
        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        etSurveyorName = v.findViewById(R.id.etSurveyorName);
        etSurveyorId = v.findViewById(R.id.etSurveyorId);
        etDate = v.findViewById(R.id.etDate);

        etSurveyorName.setText(pref.get(Constants.surveyor_name));

        etSurveyorId.setText(pref.get(Constants.surveyor_id));

        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        etDate.setText(date);

        etDate.setEnabled(false);
        etSurveyorId.setEnabled(false);

        tvSave = v.findViewById(R.id.tvSave);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        tv_submit = v.findViewById(R.id.tv_submit);
        rlTvsign = (RelativeLayout) v.findViewById(R.id.rlTvsign);
        signImage = v.findViewById(R.id.imageView1);

    }

    public void setListener() {

        tvSave.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        rlTvsign.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvSave:
                status = "1";
                if (edit_page)
                    validation();
                break;

            case R.id.tvPrevious:

                mActivity.onBackPressed();

                break;


            case R.id.rlTvsign:
                SignatureDialog();
                break;
            case R.id.tv_submit:
                if (Utils.isNetworkConnectedMainThred(mActivity)) {
                    status = "2";
                    if (edit_page)
                        validation();
                    break;
                } else {

                    pref.set(Constants.submitStatus, "1");
                    pref.commit();

                    Toast.makeText(mActivity, "Data has been saved for submission.", Toast.LENGTH_SHORT).show();

//                    intent = new Intent(GeneralForm11.this, Dashboard.class);
//                    startActivity(intent);
                    ((Dashboard) mActivity).displayView(0);

                    // Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }

                break;

        }
    }

    public void validation() {

        if (etSurveyorName.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Inventory Sheet of P&M", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(etSurveyorName, "Please Enter Surveyor Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSurveyorName.requestFocus();
        } else if (etSurveyorId.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Flow chart/Block Diagram", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(etSurveyorId, "Please Enter Surveyor Id", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSurveyorId.requestFocus();
        } else if (etDate.getText().toString().isEmpty()) {
            // Toast.makeText(this, "Please Attach Plant Layout", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(etDate, "Please Enter Date", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDate.requestFocus();
        } else if (imgString.isEmpty()) {
            //else if(signImage.getDrawable() == null){
            Snackbar snackbar = Snackbar.make(etDate, "Please Do Signature", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (status.equalsIgnoreCase("2")) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            pref.set(Constants.submitStatus, "2");
            pref.commit();

            Hit_FormSubmission_Api();
        } else {
            putIntoHm();
        }
    }

    public void putIntoHm() {

        hm.put("signature", imgString);
        hm.put("name", etSurveyorName.getText().toString());
        hm.put("id", etSurveyorId.getText().toString());
        hm.put("date", etDate.getText().toString());

        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        if (status.equals("1")) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column13.toString(), jsonArrayData.toString());

            JSONObject obj = new JSONObject();
            try {
                obj.put("id_new", pref.get(Constants.case_id));
                obj.put("page", "13");

                contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
                contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));

            Snackbar snackbar = Snackbar.make(tvSave, "Data Saved Successfully", Snackbar.LENGTH_SHORT);
            snackbar.show();

        } else {
            Log.v("jsonArrayData=====", String.valueOf(jsonArrayData));
        }

    }

    public void selectDB() throws JSONException {

        //abc = DatabaseController.fetchAllDataFromValues("generalform");
        //Log.v("##data", String.valueOf(abc));

        sub_cat = DatabaseController.getTableOne("column13", pref.get(Constants.case_id));

        Log.v("getfromdb2=====", String.valueOf(sub_cat));

        JSONArray array = new JSONArray(sub_cat.get(0).get("column13"));

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            etDate.setText(object.getString("date"));
            etSurveyorId.setText(object.getString("id"));
            etSurveyorName.setText(object.getString("name"));

            imgString = object.getString("signature");

            byteFormat = Base64.decode(object.getString("signature"), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
            signImage.setImageBitmap(decodedImage);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void SignatureDialog() {
        dialog1 = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.signature);
        Window window = dialog1.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams layoutParams = dialog1.getWindow().getAttributes();
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(mActivity);
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath = new File(directory, current);

        final SignaturePad signaturePad = dialog1.findViewById(R.id.signaturePad);

        rlCross = (RelativeLayout) dialog1.findViewById(R.id.rlCross);
        mContent = (LinearLayout) dialog1.findViewById(R.id.linearLayout);
        //mSignature = new signature(mActivity, null);
        //mSignature.setBackgroundColor(Color.WHITE);
        //mContent.addView(mSignature, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mClear = (ImageView) dialog1.findViewById(R.id.clear);
        mGetSign = (Button) dialog1.findViewById(R.id.getsign);
        //mGetSign.setEnabled(false);
        mCancel = (Button) dialog1.findViewById(R.id.cancel);
        mView = mContent;

        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                signaturePad.clear();
                imgString = "";
                // mGetSign.setEnabled(false);
            }
        });

        mGetSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Saved");

                if (signaturePad.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(etDate, "Please Do Signature", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    ByteArrayOutputStream streamm = new ByteArrayOutputStream();
                    signaturePad.getSignatureBitmap().compress(Bitmap.CompressFormat.PNG, 90, streamm);
                    byte[] image = streamm.toByteArray();
                    imgString = Base64.encodeToString(image, 0);

                    signImage.setImageBitmap(signaturePad.getSignatureBitmap());

                    //imgString = String.valueOf(signaturePad.getSignatureBitmap());
                    Bundle b = new Bundle();
                    b.putString("status", "done");
                    Intent intent = new Intent();
                    intent.putExtras(b);
                    mActivity.setResult(RESULT_OK, intent);
                    dialog1.dismiss();
                }

                /*boolean error = captureSignature();
                if (!error) {
                    mView.setDrawingCacheEnabled(true);
                    mSignature.save(mView);


                }*/
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");
                Bundle b = new Bundle();
                b.putString("status", "cancel");
                Intent intent = new Intent();
                intent.putExtras(b);
                mActivity.setResult(RESULT_OK, intent);
                //finish();
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
            Toast toast = Toast.makeText(mActivity, errorMessage, Toast.LENGTH_SHORT);
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
            Toast.makeText(mActivity, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_SHORT).show();
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

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    private void Hit_FormSubmission_Api() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.form_submission);
        Log.v("Hit_FormSubmission_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();
        try {
            //json_data.put("surveyor_id", pref.get(Constants.surveyor_id));
            jsonObject.put("case_id", case_id);
            //  json_data.put("form_id", "0");
            jsonObject.put("value", jsonArrayData);//abc
            json_data.put("VIS", jsonObject);//abc

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
                            Toast.makeText(mActivity, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }


    //********************************** Hit FormSubmission Api *********************************//

    private void parseJson(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                ((Dashboard) mActivity).displayView(0);

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

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        //if Required then only
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.general_one:
                        ((Dashboard) mActivity).displayView(6);
                        /*intent=new Intent(GeneralForm3.this, GeneralForm1.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_two:
                        ((Dashboard) mActivity).displayView(7);
                        return true;

                    case R.id.general_three:
                        ((Dashboard) mActivity).displayView(8);
                        return true;

                    case R.id.general_four:
                        ((Dashboard) mActivity).displayView(9);
                        return true;

                    case R.id.general_five:
                        ((Dashboard) mActivity).displayView(10);
                        return true;

                    case R.id.general_six:
                        ((Dashboard) mActivity).displayView(11);
                        return true;

                    case R.id.general_seven:
                        ((Dashboard) mActivity).displayView(12);
                        return true;

                    case R.id.general_eight:
                        ((Dashboard) mActivity).displayView(13);
                        return true;

                    case R.id.general_nine:
                        ((Dashboard) mActivity).displayView(14);
                        return true;

                    case R.id.general_ten:
                        ((Dashboard) mActivity).displayView(15);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public class signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private final RectF dirtyRect = new RectF();
        private Paint paint = new Paint();
        private Path path = new Path();
        private float lastTouchX;
        private float lastTouchY;

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
                final FileOutputStream mFileOutStream = new FileOutputStream(mypath);

                v.draw(canvas);
                new Handler().post(new Runnable() {
                    public void run() {
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                    }
                });
                //mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                // String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                encodedStringg = getEncoded64ImageStringFromBitmap(mBitmap);

                signImage.setImageBitmap(mBitmap);

                Log.v("log_tag", "url: " + encodedStringg);


            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }
        }


        //method to convert string into base64
        public String getEncoded64ImageStringFromBitmap(final Bitmap bitmap) {

            final ByteArrayOutputStream stream = new ByteArrayOutputStream();

            new Handler().post(new Runnable() {
                public void run() {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                }
            });

            //   bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byteFormat = stream.toByteArray();
            // get the base 64 string
            imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

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

}
