package com.vis.android.Activities.General.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.vis.android.Database.TableGeneralForm;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class GeneralForm11 extends BaseFragment implements View.OnClickListener {

    //StaticString
    private static final String IMAGE_DIRECTORY_NAME = "VIS";
    //Static Int
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;
    private static final int REQUEST_PERMISSIONS = 1;
    public static String tempDir;
    private static String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //String
    public String picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", encodedString = "", setPic = "";
    public String current = null;
    ImageView ivCompletionCerti, ivMunicipalTaxCerti, ivElectricityBill, ivWaterSewerage, ivRegularization, ivPending;
    TextView tvNext, tvPrevious;
    TextView tvCompletionCerti, tvMunicipalTaxCerti, tvElectricityBill, tvWaterSewerage, tvRegularization, tvPending;
    String image_status = "0";
    //Uri
    Uri picUri, fileUri;
    //Bitmap
    Bitmap bitmap;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    ArrayList abc = new ArrayList();
    RelativeLayout rlTvsign;
    //  LinearLayout dropdown;
    LinearLayout mContent;
    RelativeLayout rlCross;
    Dialog dialog1;
    View mView;
    File mypath;
    signature mSignature;
    Boolean edit_page = true;
    ImageView mClear;
    Button mGetSign, mCancel;
    String encodedStringg;
    byte[] byteFormat;
    String imgString = "";
    ImageView signImage;
    CustomLoader loader;
    Intent intent;
    Preferences pref;
    String status = "", regCertImage = "", comCertImg = "", municipalCertImg = "", electricityBillImg = "", waterBillImg = "", pendingDoc = "";
    View v;
    private String uniqueId;
    //  ProgressBar progressbar;

    //  TextView tv_caseheader,tv_caseid,tv_header;

    //  RelativeLayout rl_casedetail;
    private Bitmap mBitmap;

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static String getFileType(String path) {
        String fileType = null;
        fileType = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase();
        return fileType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_generalform11, container, false);

        getid(v);
        setListener();
        getPermission();

//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

        //progressbar.setMax(10);
        //  progressbar.setProgress(8);

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
        ivCompletionCerti.setEnabled(false);
        ivMunicipalTaxCerti.setEnabled(false);
        ivElectricityBill.setEnabled(false);
        ivWaterSewerage.setEnabled(false);
        ivRegularization.setEnabled(false);
        ivPending.setEnabled(false);

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
//        tv_header = findViewById(R.id.tv_header);
//        rl_casedetail = findViewById(R.id.rl_casedetail);
//        tv_caseheader = findViewById(R.id.tv_caseheader);
//        tv_caseid = findViewById(R.id.tv_caseid);
//
//        progressbar = (ProgressBar) findViewById(R.id.Progress);
//        dropdown = (LinearLayout) findViewById(R.id.ll_dropdown);
//        dots = (RelativeLayout) findViewById(R.id.rl_dots);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        ivCompletionCerti = v.findViewById(R.id.ivCompletionCerti);
        ivMunicipalTaxCerti = v.findViewById(R.id.ivMunicipalTaxCerti);
        ivElectricityBill = v.findViewById(R.id.ivElectricityBill);
        ivWaterSewerage = v.findViewById(R.id.ivWaterSewerage);
        ivRegularization = v.findViewById(R.id.ivRegularization);
        ivPending = v.findViewById(R.id.ivPending);
        tvCompletionCerti = v.findViewById(R.id.tvCompletionCerti);
        tvMunicipalTaxCerti = v.findViewById(R.id.tvMunicipalTaxCerti);
        tvElectricityBill = v.findViewById(R.id.tvElectricityBill);
        tvWaterSewerage = v.findViewById(R.id.tvWaterSewerage);
        tvRegularization = v.findViewById(R.id.tvRegularization);
        tvPending = v.findViewById(R.id.tvPendingDocument);
        //tvSave = v.findViewById(R.id.tvSave);
        //tv_submit = v.findViewById(R.id.tv_submit);
        tvNext = v.findViewById(R.id.tvNext);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        rlTvsign = (RelativeLayout) v.findViewById(R.id.rlTvsign);
        signImage = v.findViewById(R.id.imageView1);
        //back = v.findViewById(R.id.rl_back);

    }

    public void setListener() {
        ivCompletionCerti.setOnClickListener(this);
        ivMunicipalTaxCerti.setOnClickListener(this);
        ivElectricityBill.setOnClickListener(this);
        ivWaterSewerage.setOnClickListener(this);
        ivRegularization.setOnClickListener(this);
        ivPending.setOnClickListener(this);
        //  tvSave.setOnClickListener(this);
        //   tv_submit.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        rlTvsign.setOnClickListener(this);
        //dots.setOnClickListener(this);
        //back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivCompletionCerti:
                image_status = "1";
                showCameraGalleryDialog();
                break;
            case R.id.ivMunicipalTaxCerti:
                image_status = "2";
                showCameraGalleryDialog();
                break;
            case R.id.ivElectricityBill:
                image_status = "3";
                showCameraGalleryDialog();
                break;
            case R.id.ivWaterSewerage:
                image_status = "4";
                showCameraGalleryDialog();
                break;
            case R.id.ivRegularization:
                image_status = "5";
                showCameraGalleryDialog();
                break;
            case R.id.ivPending:
                image_status = "6";
                showCameraGalleryDialog();
                break;

            case R.id.tvSave:
                status = "1";
                if (edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(17);
                break;


            case R.id.tvNext:
                status = "1";
                if (edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(17);
                break;

            case R.id.tvPrevious:

                mActivity.onBackPressed();
                break;

            /*case R.id.rl_back:
                onBackPressed();
                break;*/

            case R.id.rlTvsign:
                SignatureDialog();
                break;
            case R.id.tv_submit:
                if (Utils.isNetworkConnectedMainThred(mActivity)) {
                    status = "2";
                    if (edit_page)
                        validation();
                    else
                        ((Dashboard) mActivity).displayView(17);
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    pref.set(Constants.submitStatus, "2");
                    pref.commit();

                    Hit_FormSubmission_Api();

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

            /*case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;*/
        }
    }

    public void validation() {
        if (tvCompletionCerti.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Inventory Sheet of P&M", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvCompletionCerti, "Please Attach Completion Certificate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvCompletionCerti.requestFocus();
        } else if (tvMunicipalTaxCerti.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Flow chart/Block Diagram", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvMunicipalTaxCerti, "Please Attach Municipal Tax Certificate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvMunicipalTaxCerti.requestFocus();
        } else if (tvElectricityBill.getText().toString().isEmpty()) {
            // Toast.makeText(this, "Please Attach Plant Layout", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvElectricityBill, "Please Attach Electricity Bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvElectricityBill.requestFocus();
        } else if (tvWaterSewerage.getText().toString().isEmpty()) {
            // Toast.makeText(this, "Please Attach Factories Registration", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvWaterSewerage, "Please Attach Water/ Sewerage Bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvWaterSewerage.requestFocus();
        } else if (tvRegularization.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Labour License", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvRegularization, "Please Attach Regularization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvRegularization.requestFocus();
        } else if (tvPending.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Labour License", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvPending, "Please Pending Documents", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvPending.requestFocus();
        } else if (signImage.getDrawable() == null) {
            //Toast.makeText(SpecialAssets8.this, "Please Do Signature", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Do Signature", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            putIntoHm();

            return;
        }
    }

    public void putIntoHm() {

       /* signImage.buildDrawingCache();
        Bitmap bitmapp = signImage.getDrawingCache();

        ByteArrayOutputStream streamm = new ByteArrayOutputStream();
        bitmapp.compress(Bitmap.CompressFormat.PNG, 90, streamm);
        byte[] image = streamm.toByteArray();
        System.out.println("byte array:" + image);

        String img_str = Base64.encodeToString(image, 0);
        System.out.println("string:" + img_str);*/

        //  hm.put("signature3", img_str);

        hm.put("completionCertificate", tvCompletionCerti.getText().toString());
        hm.put("municipalCertificate", tvMunicipalTaxCerti.getText().toString());
        hm.put("electricityBill", tvElectricityBill.getText().toString());
        hm.put("waterSewerageBill", tvWaterSewerage.getText().toString());
        hm.put("regularizationCerti", tvRegularization.getText().toString());
        hm.put("pendingDocument", tvPending.getText().toString());
        hm.put("completionCertificateImage", comCertImg);
        hm.put("municipalCertificateImage", municipalCertImg);
        hm.put("electricityBillImage", electricityBillImg);
        hm.put("waterSewerageBillImage", waterBillImg);
        hm.put("regularizationCertiImage", regCertImage);
        hm.put("pendingDocumentImage", pendingDoc);

        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        if (status.equals("1")) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column11.toString(), jsonArrayData.toString());

            JSONObject obj = new JSONObject();
            try {
                obj.put("id_new", pref.get(Constants.case_id));
                obj.put("page", "11");

                contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
                contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));


            ((Dashboard) mActivity).displayView(17);
//            Snackbar snackbar = Snackbar.make(tvNext, "Data Saved Successfully", Snackbar.LENGTH_SHORT);
//            snackbar.show();

           /* int maxLogSize = 1000;
            for (int i = 0; i <= contentValues.toString().length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > contentValues.toString().length() ? contentValues.toString().length() : end;
                Log.v("#databseinsert", contentValues.toString().substring(start, end));
            }*/
        } else {
            Log.v("jsonArrayData=====", String.valueOf(jsonArrayData));
        }

    }

    public void selectDB() throws JSONException {

        //abc = DatabaseController.fetchAllDataFromValues("generalform");
        //abc = DatabaseController.getTableOne("column11",pref.get(Constants.case_id));
        //Log.v("##data", String.valueOf(abc));

        sub_cat = DatabaseController.getTableOne("column11", pref.get(Constants.case_id));

        Log.v("getfromdb2=====", String.valueOf(sub_cat));

        JSONArray array = new JSONArray(sub_cat.get(0).get("column11"));

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            comCertImg = object.getString("completionCertificateImage");
            municipalCertImg = object.getString("municipalCertificateImage");
            electricityBillImg = object.getString("electricityBillImage");
            waterBillImg = object.getString("waterSewerageBillImage");
            regCertImage = object.getString("regularizationCertiImage");
            pendingDoc = object.getString("pendingDocumentImage");
            tvCompletionCerti.setText(object.getString("completionCertificate"));
            tvMunicipalTaxCerti.setText(object.getString("municipalCertificate"));
            tvElectricityBill.setText(object.getString("electricityBill"));
            tvWaterSewerage.setText(object.getString("waterSewerageBill"));
            tvPending.setText(object.getString("pendingDocument"));
            tvRegularization.setText(object.getString("regularizationCerti"));
         /*   byteFormat = Base64.decode(object.getString("signature3"), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
            signImage.setImageBitmap(decodedImage);*/

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

        rlCross = (RelativeLayout) dialog1.findViewById(R.id.rlCross);
        mContent = (LinearLayout) dialog1.findViewById(R.id.linearLayout);
        mSignature = new signature(mActivity, null);
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
                    mActivity.setResult(RESULT_OK, intent);
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

    public void showCameraGalleryDialog() {

        final Dialog dialog = new Dialog(mActivity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.camera_gallery_popup);

        dialog.show();

        RelativeLayout rrCancel = (RelativeLayout) dialog.findViewById(R.id.rr_cancel);
        LinearLayout llCamera = (LinearLayout) dialog.findViewById(R.id.ll_camera);
        LinearLayout llGallery = (LinearLayout) dialog.findViewById(R.id.ll_gallery);

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                dialog.dismiss();
            }
        });


        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);
*/
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                dialog.dismiss();
            }
        });

        rrCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                picturePath = fileUri.getPath().toString();
                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);

                String selectedImagePath = picturePath;

                ext = "jpg";

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 500;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(picturePath));
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();

                encodedString = getEncoded64ImageStringFromBitmap(bitmap);

                Log.v("encodedstring", encodedString);
                //setPic="1";
                setPictures(bitmap, setPic, encodedString);


            }
        } else if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                Uri contentURI = data.getData();
                //get the Uri for the captured image
                picUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = mActivity.getContentResolver().query(contentURI, filePathColumn, null, null, null);
                cursor.moveToFirst();
                Log.v("piccc", "pic");
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                System.out.println("Image Path : " + picturePath);
                cursor.close();
                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                ext = getFileType(picturePath);
                String selectedImagePath = picturePath;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 500;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(picturePath));
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();
                encodedString = getEncoded64ImageStringFromBitmap(bitmap);
                Log.v("encodedstring", encodedString);

                Log.v("picture_path====", filename);
                setPictures(bitmap, setPic, encodedString);

            } else {
                Toast.makeText(mActivity, "unable to select image", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setPictures(Bitmap b, String setPic, String base64) {


        if (image_status.equals("1")) {
            // iv_one.setImageBitmap(b);
            regCertImage = encodedString;

            tvCompletionCerti.setText(filename);

        } else if (image_status.equals("2")) {
            // iv_two.setImageBitmap(b);
            comCertImg = encodedString;

            tvMunicipalTaxCerti.setText(filename);

        } else if (image_status.equals("3")) {

            municipalCertImg = encodedString;

            tvElectricityBill.setText(filename);

        } else if (image_status.equals("4")) {

            electricityBillImg = encodedString;

            tvWaterSewerage.setText(filename);

        } else if (image_status.equals("5")) {

            waterBillImg = encodedString;

            tvRegularization.setText(filename);
        } else if (image_status.equals("6")) {

            pendingDoc = encodedString;

            tvPending.setText(filename);
        } else {
            //  iv_four.setImageBitmap(b);

        }

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
