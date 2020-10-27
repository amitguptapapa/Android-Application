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

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.Fragments.InitiateSurveyForm;
import com.vis.android.Common.Constants;
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

public class UndertakingFragmentOne extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_PERMISSIONS = 1;
    public static String tempDir;
    private static String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public String current = null;
    // RelativeLayout back,dots;
    //   LinearLayout dropdown;
    TextView tvNext, tvPrevious, tvSign;
    //EditText
    EditText etName, etDate, etMobileNo;
    Boolean edit_page = true;
    //ArrayList
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    Preferences pref;
    LinearLayout mContent;
    RelativeLayout rlTvsign;
    RelativeLayout rlCross;
    Dialog dialog1;
    //signature mSignature;
    ImageView mClear;
    Button mGetSign, mCancel;
    byte[] byteFormat;
    String imgString = "", encodedString = "";
    ImageView signImage;
    View mView;
    File mypath;
    View v;
    private String uniqueId;
    private Bitmap mBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_undertaking_one, container, false);

        getid(v);
        setListener();
        getPermission();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*if (InitiateSurveyForm.sitePresence.equalsIgnoreCase("3")){
            AlertView();
        }*/
        if (!edit_page) {
            setEditiblity();
        }

        return v;
    }

    public void setEditiblity() {
        signImage.setEnabled(false);
        etName.setEnabled(false);
        etDate.setEnabled(false);
        etMobileNo.setEnabled(false);
        rlTvsign.setEnabled(false);
//        mClear.setEnabled(false);
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
        tvNext = v.findViewById(R.id.tvNext);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        tvSign = v.findViewById(R.id.tvSign);

        etName = v.findViewById(R.id.etName);
        etDate = v.findViewById(R.id.etDate);
        etMobileNo = v.findViewById(R.id.etMobileNo);
        etMobileNo.setText(pref.get(Constants.mobile_no_siteInspection));
        Log.v("mobile_no_", pref.get(Constants.mobile_no_siteInspection));
        etName.setText(pref.get(Constants.co_name));

        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        etDate.setText(date);

        etDate.setEnabled(false);

        rlTvsign = v.findViewById(R.id.rlTvsign);
        signImage = v.findViewById(R.id.imageView1);

        if (InitiateSurveyForm.sitePresence.equalsIgnoreCase("3")) {
            tvSign.setText("No one was Available");
        }

    }

    public void setListener() {
        tvNext.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        rlTvsign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlTvsign:

                if (!InitiateSurveyForm.sitePresence.equalsIgnoreCase("3")) {
                    SignatureDialog();
                }

                break;

            case R.id.tvPrevious:
                mActivity.onBackPressed();
                break;

            case R.id.tvNext:
                if (edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(18);
                break;
        }
    }

    private void validation() {

        /*signImage.buildDrawingCache();
        Bitmap bitmapp = signImage.getDrawingCache();

        ByteArrayOutputStream streamm = new ByteArrayOutputStream();
        bitmapp.compress(Bitmap.CompressFormat.PNG, 90, streamm);
        byte[] image = streamm.toByteArray();
        String img_str = Base64.encodeToString(image, 0);*/

        if (etName.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etName.requestFocus();
        } else if (etDate.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Date", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDate.requestFocus();
        } else if (etMobileNo.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Mobile Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMobileNo.requestFocus();
        } else if (etMobileNo.getText().toString().length() < 10) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Proper Mobile Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMobileNo.requestFocus();
        }/* else if (signImage.getDrawable() == null) {
           //Toast.makeText(SpecialAssets8.this, "Please Do Signature", Toast.LENGTH_SHORT).show();
           Snackbar snackbar = Snackbar.make(signImage, "Please Do Signature", Snackbar.LENGTH_SHORT);
           snackbar.show();
       }*/ else if (imgString.isEmpty() && !InitiateSurveyForm.sitePresence.equalsIgnoreCase("3")) {
            Snackbar snackbar = Snackbar.make(etDate, "Please Do Signature", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            putIntoHm();
        }
    }

    public void putIntoHm() {

        hm.put("signature", imgString);

        hm.put("name", etName.getText().toString().trim());
        hm.put("date", etDate.getText().toString().trim());
        hm.put("mobileNo", etMobileNo.getText().toString().trim());

        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        ContentValues contentValues = new ContentValues();

        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column12.toString(), jsonArrayData.toString());

        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "12");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));


        ((Dashboard) mActivity).displayView(18);

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getTableOne("column12", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column12"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                etName.setText(object.getString("name"));
                etDate.setText(object.getString("date"));
                etMobileNo.setText(object.getString("mobileNo"));

                imgString = object.getString("signature");

                byteFormat = Base64.decode(object.getString("signature"), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                signImage.setImageBitmap(decodedImage);
            }

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    public void AlertView() {
        final Dialog emailDialog = new Dialog(mActivity);
        emailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailDialog.setCancelable(true);
        emailDialog.setContentView(R.layout.alert_view_reply_popup);
        Window window = emailDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        emailDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        emailDialog.show();

        ImageView close;
        TextView tvMessage;
        Button btnSubmit;

        close = emailDialog.findViewById(R.id.iv_close);
        tvMessage = emailDialog.findViewById(R.id.tvMessage);
        btnSubmit = emailDialog.findViewById(R.id.btnSubmit);

        tvMessage.setText("No one was available to show the property");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectedMainThred(mActivity)) {

                    emailDialog.dismiss();

                } else {
                    Toast.makeText(mActivity, R.string.noInternetConnection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialog.dismiss();
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
                encodedString = getEncoded64ImageStringFromBitmap(mBitmap);

                signImage.setImageBitmap(mBitmap);


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
            //imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

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
