package com.vis.android.Activities.General.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.ScheduleActivity;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.vis.android.Fragments.CaseDetail.case_id;


/**
 * Created by ankita-pc on 5/3/18.
 */

public class GeneralForm1 extends BaseFragment implements View.OnClickListener {
    //StaticString
    private static final String IMAGE_DIRECTORY_NAME = "VIS";
    //Static Int
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;
    //ArrayList
    public static ArrayList<HashMap<String, String>> arrayListData = new ArrayList<>();
    public static JSONArray jsonArrayData;
    int firstTimer=0;
    //ProgressBar progressbar;
    //HashMap
    public static HashMap<String, String> hm = new HashMap<>();
    public static HashMap<String, String> newHm = new HashMap<>();
    public static ArrayList<HashMap<String, String>> newArray = new ArrayList<>();
    public static int surveyTypeCheck = 0;
    //String
    public String a = "", page = "", picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", setPic = "";
    //RelativeLayout back, dots;
    RelativeLayout footer, touch;
    LinearLayout dropdown;
    //  ImageView iv_camera1;
    Intent intent;
    Preferences pref;
    CustomLoader loader;
    TextView tvTypeOfLoan;
    Boolean edit_page = true;
    String encodedString1 = "";
    //Uri
    Uri picUri, fileUri;
    //Bitmap
    Bitmap bitmap;
    //RadioGroup
    //static RadioGroup rgPropertyShownBy;
    //   RadioGroup rgSurveyType;
    //   RadioGroup rgReasonForHalfSurvey;
    RadioGroup rgTypeOfLoan;
    //LinearLayout
    LinearLayout llReasonForHalf;
    //RadioButton
    RadioButton rbHalfSurvey;
    //EditText
//    static EditText etName;
//    static EditText etContactNumber;
    EditText etLoanAmount, etAnyOtherReason, etAnyOtherPurpose;
    RelativeLayout rlspinProp;
    //Spinner
    Spinner spinnerPurposeOfValuation, spinnerTypeOfProperty;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    ArrayList<String> sub_cat1 = new ArrayList<>();
    ArrayList<HashMap<String, String>> alistt = new ArrayList<HashMap<String, String>>();
    String[] purposeOfValuationArray = {"Choose an item",
            "For Company Merger & Amalgamation Purpose",
            "For Company Restructuring purpose",
            "For Computation of Capital Gains Wealth Tax Purpose",
            "For Debt Restructuring purpose",
            "For Distress Sale of mortgaged assets under NPA a/c",
            "For DRT Recovery purpose",
            "For IFRS purpose",
            "For Impairement study for IAS 36/Indian GAAP – AS 28 purpose",
            "For Insolvency proceedings",
            "For Insurance claim",
            "For Insurance Premium assessment",
            "For Net Worth Assessment purpose",
            "For Periodic Re-valuation of the mortgaged property",
            "For Property Partition purpose",
            "For Recovery under SARFAESI action",
            "For redistribution of ownership share in the asset",
            "For solvency certificate",
            "For the matter under litigation",
            "For Value assessment of the asset for creating collateral mortgage for Bank Loan purpose",
            "Project Tie-up for individual Flat Financing",
            "For any other purpose"};
    String[] typeOfPropertyArray = {"Choose an item", "Residential Apartment in multistoried building", "Residential apartment in low rise building",
            "Residential House (Plotted development)", "Residential Builder Floor", "Residential Plot/Land", "Mansion", "Kothi", "Villa", "Commercial Office",
            "Commercial Shop", "Commercial Floor", "Commercial Land & Building", "Institutional Land & Building", "Educational Institution (School/ College/ University)",
            "Industrial Plot", "Agricultural Land", "Group Housing Society", "SEZ", "Institutional Plot/Land", "Industrial Plant", "Small/ Mid-scale Industrial Land & Building",
            "Industrial Project Land & Building", "Industrial Project Land & Building and Plant & Machinery", "Industrial Plant & Machinery", "Hotel/ Resort",
            "Shopping Mall", "Shopping Complex", "Enterprise/ Business", "Financial Securities/ Instruments", "Stocks", "Jewellery", "Current Assets"};

    // TextView tv_caseheader,tv_caseid,tv_header;
    SpinnerAdapter spinnerAdapter;
    View v;
    //   RelativeLayout rl_casedetail;
    private String array_property, array_purpose;
    private ArrayList<HashMap<String, String>> property_array_list = new ArrayList<>();
    private ArrayList<HashMap<String, String>> purpose_array_list = new ArrayList<>();

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
        v = inflater.inflate(R.layout.activity_generalform1, container, false);

        getid(v);
        setListener();
        populateSinner();
        conditions();

     /*   tv_header.setVisibility(View.GONE);
        rl_casedetail.setVisibility(View.VISIBLE);
        tv_caseheader.setText("General Survey Form");
        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));*/

        Arrays.sort(purposeOfValuationArray, 1, purposeOfValuationArray.length);
        Arrays.sort(typeOfPropertyArray, 1, typeOfPropertyArray.length);

        ContentValues contentValues = new ContentValues();

        Boolean data = DatabaseController.isDataExit("generalform");

        if (data == false) {

            contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), "1");
            contentValues.put(TableGeneralForm.generalFormColumn.column1.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column2.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column3.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column4.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column5.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column6.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column7.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column8.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column9.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column10.toString(), "");
            contentValues.put(TableGeneralForm.generalFormColumn.column11.toString(), "");

            DatabaseController.insertData(contentValues, TableGeneralForm.attachment);
        }

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Utils.isNetworkConnectedMainThred(mActivity)) {
            loader.dismiss();
            loader.cancel();
            loader.show();
//            loader.setCancelable(false);
//            loader.setCanceledOnTouchOutside(false);
            Hit_GetGeneralInfo_Api();

        } else {
            Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

        }
        if (!edit_page) {
            setEditiblity();
        }
        return v;
    }

    public void getid(View v) {


        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }

        footer = (RelativeLayout) v.findViewById(R.id.footer);
        touch = (RelativeLayout) v.findViewById(R.id.rl_touch);

        //TextView
        tvTypeOfLoan = v.findViewById(R.id.tvTypeOfLoan);

        //RadioGroup
        rgTypeOfLoan = v.findViewById(R.id.rgTypeOfLoan);

        //LinearLayout
        llReasonForHalf = v.findViewById(R.id.llReasonForHalf);

        //RadioButton
        rbHalfSurvey = v.findViewById(R.id.rbHalfSurvey);

        //EditText
        etLoanAmount = v.findViewById(R.id.etLoanAmount);
        etAnyOtherReason = v.findViewById(R.id.etAnyOtherReason);
        etAnyOtherPurpose = v.findViewById(R.id.etAnyOtherPurpose);

        spinnerPurposeOfValuation = v.findViewById(R.id.spinnerPurposeOfValuation);
        spinnerTypeOfProperty = v.findViewById(R.id.spinnerTypeOfProperty);
        rlspinProp = v.findViewById(R.id.rlspinProp);

    }

    public void setEditiblity() {


        //EditText
        etLoanAmount.setEnabled(false);
        etAnyOtherReason.setEnabled(false);
        etAnyOtherPurpose.setEnabled(false);

        spinnerPurposeOfValuation.setEnabled(false);
        spinnerTypeOfProperty.setEnabled(false);
        for (int i = 0; i < rgTypeOfLoan.getChildCount(); i++)
            rgTypeOfLoan.getChildAt(i).setClickable(false);


    }

    public void setListener() {
        footer.setOnClickListener(this);
        rlspinProp.setOnClickListener(this);
        spinnerTypeOfProperty.setClickable(false);
        spinnerTypeOfProperty.setEnabled(false);
//        spinnerTypeOfProperty.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.footer:
                validation();
                break;
            case R.id.rlspinProp:
                alertAssestChanged();
                Log.v("SpinnerChoosen", "SpinnerClickec");

                break;
        }
    }
    public void alertAssestChanged() {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_yes_no);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        TextView tvyes = dialog.findViewById(R.id.tvOk);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);

        tvMessage.setText(R.string.assestChangedWarning1);
        tvyes.setText(R.string.yes);
        tvCancel.setText(R.string.no);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        tvyes.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                ((Dashboard) mActivity).displayView(2);
                dialog.dismiss();
//                pref.set(Constants.type_of_assets,selected_property);

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
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

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(mActivity, R.string.camera_permission_required, Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        } else {

            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        //   return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".provider", getOutputMediaFile(type));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == mActivity.RESULT_OK) {

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
                encodedString1 = getEncoded64ImageStringFromBitmap(bitmap);
//                tvLetterImage.setText(filename);
//                pref.set(Constants.tvLetterImage, filename);
//                pref.commit();
                setPictures(bitmap, setPic, encodedString1);

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
                encodedString1 = getEncoded64ImageStringFromBitmap(bitmap);
                // tvLetterImage.setText(filename);
                // pref.set(Constants.tvLetterImage, filename);
                //  pref.commit();
                setPictures(bitmap, setPic, encodedString1);

            } else {
                Toast.makeText(mActivity, "unable to select image", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setPictures(Bitmap b, String setPic, String base64) {


    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    public void conditions() {

/*
        rgPropertyShownBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);
                Log.v("getcheckk", String.valueOf(a));


                switch (a) {
                    case 0:
                        llPropertyName.setVisibility(View.VISIBLE);
                        llPropertyContact.setVisibility(View.VISIBLE);
                        llPropertyImage.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        llPropertyName.setVisibility(View.VISIBLE);
                        llPropertyContact.setVisibility(View.VISIBLE);
                        llPropertyImage.setVisibility(View.VISIBLE);

                        break;
                    default:
                        llPropertyName.setVisibility(View.GONE);
                        llPropertyContact.setVisibility(View.GONE);
                        llPropertyImage.setVisibility(View.GONE);

                        break;


                }
            }
        });
*/


       /* rgSurveyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);

                switch (a) {
                    case 0:
                        llReasonForHalf.setVisibility(View.GONE);
                        llReasonForHalf.setVisibility(View.GONE);
                        tvTypeOfLoan.setText("e. Type of Loan");

                        surveyTypeCheck = 0;

                        break;
                    case 1:
                        llReasonForHalf.setVisibility(View.GONE);
                        llReasonForHalf.setVisibility(View.GONE);
                        tvTypeOfLoan.setText("e. Type of Loan");

                        surveyTypeCheck = 0;

                        break;
                    case 2:
                        llReasonForHalf.setVisibility(View.VISIBLE);
                        llReasonForHalf.setVisibility(View.VISIBLE);
                        tvTypeOfLoan.setText("f. Type of Loan");

                        surveyTypeCheck = 1;

                        break;
                    case 3:
                        llReasonForHalf.setVisibility(View.VISIBLE);
                        llReasonForHalf.setVisibility(View.VISIBLE);
                        tvTypeOfLoan.setText("f. Type of Loan");

                        surveyTypeCheck = 1;

                        break;
                }
            }
        });*/
    }

    private void validation() {

        if (spinnerPurposeOfValuation.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(footer, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();

            View targetView = v.findViewById(R.id.tvPurposeOfValuation);
            targetView.getParent().requestChildFocus(targetView, targetView);
        } /*else if (rgPropertyShownBy.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(footer, "Please Select Property Shown By", Snackbar.LENGTH_SHORT);
            snackbar.show();

            View targetView = findViewById(R.id.tvPropertyShown);
            targetView.getParent().requestChildFocus(targetView, targetView);
        }*/ /*else if (llPropertyName.getVisibility() == View.VISIBLE && etName.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etName.requestFocus();
        } else if (llPropertyContact.getVisibility() == View.VISIBLE && etContactNumber.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber.requestFocus();
        } else if (llPropertyContact.getVisibility() == View.VISIBLE && etContactNumber.getText().toString().length() < 10) {
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Valid Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber.requestFocus();
        } */ else if (spinnerTypeOfProperty.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(footer, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvTypeOfProp);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } /*else if (rgSurveyType.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(footer, "Please Select Survey Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = findViewById(R.id.tvSurveyType);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llReasonForHalf.getVisibility() == View.VISIBLE && rgReasonForHalfSurvey.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(footer, "Please Select Reason For Half Survey or Only photographs taken", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = findViewById(R.id.tvHalfSurvey);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }*/ else {
            putIntoHm();
            ((Dashboard) mActivity).displayView(7);
            return;
        }
    }

    @SuppressLint("ResourceType")
    public void putIntoHm() {

        String purposeOfValuation = String.valueOf(spinnerPurposeOfValuation.getSelectedItemPosition());
        String typeOfProperty = String.valueOf(spinnerTypeOfProperty.getSelectedItemPosition());

        hm.put("ownerPhotograph", encodedString1);
        hm.put("purposeOfValuationSpinner", purposeOfValuation);
        hm.put("typeOfPropertySpinner", typeOfProperty);
        hm.put("generalLoanAmount", etLoanAmount.getText().toString());

       /* int selectedIdPropertyShownBy = rgPropertyShownBy.getCheckedRadioButtonId();
        View radioButtonPropertyShownBy = findViewById(selectedIdPropertyShownBy);
        int idx = rgPropertyShownBy.indexOfChild(radioButtonPropertyShownBy);
        RadioButton r = (RadioButton) rgPropertyShownBy.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonPropertyShownBy", selectedText);

        if (idx == 2 || idx == 3) {
            hm.put("propertyShownByName", "");
            hm.put("propertyShownByContact", "");
        } else {
            hm.put("propertyShownByName", etName.getText().toString());
            hm.put("propertyShownByContact", etContactNumber.getText().toString());
        }*/

        /*int selectedIdSurveyType = rgSurveyType.getCheckedRadioButtonId();
        View radioButtonSurveyType = findViewById(selectedIdSurveyType);
        int idx2 = rgSurveyType.indexOfChild(radioButtonSurveyType);
        RadioButton r2 = (RadioButton) rgSurveyType.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonSurveyType", selectedText2);*/

        /*if (llReasonForHalf.getVisibility() == View.VISIBLE) {
            int selectedIdHalfSurvey = rgReasonForHalfSurvey.getCheckedRadioButtonId();
            View radioButtonHalfSurvey = findViewById(selectedIdHalfSurvey);
            int idx3 = rgReasonForHalfSurvey.indexOfChild(radioButtonHalfSurvey);
            RadioButton r3 = (RadioButton) rgReasonForHalfSurvey.getChildAt(idx3);
            String selectedText3 = r3.getText().toString();
            hm.put("radioButtonHalfSurvey", selectedText3);
        }*/

        if (rgTypeOfLoan.getCheckedRadioButtonId() > -1) {
            int selectedIdTypeOfLoan = rgTypeOfLoan.getCheckedRadioButtonId();
            View radioButtonTypeOfLoan = v.findViewById(selectedIdTypeOfLoan);
            int idx4 = rgTypeOfLoan.indexOfChild(radioButtonTypeOfLoan);
            RadioButton r4 = (RadioButton) rgTypeOfLoan.getChildAt(idx4);
            String selectedText4 = r4.getText().toString();
            hm.put("radioButtonTypeOfLoan", selectedText4);
        }
        arrayListData.add(hm);
        jsonArrayData = new JSONArray(arrayListData);

        ContentValues contentValues = new ContentValues();

        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column1.toString(), jsonArrayData.toString());


        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "1");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getTableOne("column1", pref.get(Constants.case_id));

        Log.v("###values", String.valueOf(sub_cat));


        if (sub_cat != null && sub_cat.size() > 0) {
            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column1"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

//                etName.setText(object.getString("propertyShownByName"));
//                etContactNumber.setText(object.getString("propertyShownByContact"));

                etLoanAmount.setText(object.getString("generalLoanAmount"));
               /* if (!(object.getString("ownerPhotograph").equals(""))) {
                    tvLetterImage.setText(pref.get(Constants.tvLetterImage));
                }*/

               /* //SetRadioButton
                if (object.getString("radioButtonPropertyShownBy").equals("Owner")) {
                    ((RadioButton) rgPropertyShownBy.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonPropertyShownBy").equals("Owner's Representative")) {
                    ((RadioButton) rgPropertyShownBy.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonPropertyShownBy").equals("No one was available")) {
                    ((RadioButton) rgPropertyShownBy.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonPropertyShownBy").equals("Property inspected in presence of lessee")) {
                    ((RadioButton) rgPropertyShownBy.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonPropertyShownBy").equals("NPA property, survey done from some distance of the property")) {
                    ((RadioButton) rgPropertyShownBy.getChildAt(4)).setChecked(true);
                }
*/

              /*  if (object.getString("radioButtonSurveyType").equals("Full detailed survey (inside-out with full measurement & photographs)")) {
                    ((RadioButton) rgSurveyType.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonSurveyType").equals("Full survey (inside-out with sample random measurements & photographs)")) {
                    ((RadioButton) rgSurveyType.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonSurveyType").equals("Half Survey (Sample random measurements from outside & photographs)")) {
                    ((RadioButton) rgSurveyType.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonSurveyType").equals("Only photographs taken (No measurements)")) {
                    ((RadioButton) rgSurveyType.getChildAt(3)).setChecked(true);
                }*/

/*
                if (llReasonForHalf.getVisibility() == View.VISIBLE) {
                    if (object.getString("radioButtonHalfSurvey").equals("Property was locked")) {
                        ((RadioButton) rgReasonForHalfSurvey.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonHalfSurvey").equals("Possessee didn’t allow to inspect the property")) {
                        ((RadioButton) rgReasonForHalfSurvey.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonHalfSurvey").equals("NPA property so owner were hostile and survey couldn’t be carried out")) {
                        ((RadioButton) rgReasonForHalfSurvey.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonHalfSurvey").equals("Under construction property")) {
                        ((RadioButton) rgReasonForHalfSurvey.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonHalfSurvey").equals("Very large irregular Property not possible to measure entire area")) {
                        ((RadioButton) rgReasonForHalfSurvey.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonHalfSurvey").equals("Any other Reason")) {
                        ((RadioButton) rgReasonForHalfSurvey.getChildAt(5)).setChecked(true);
                    }
                }*/


                try {
                    if (object.getString("radioButtonTypeOfLoan").equals("Housing Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Housing Take Over Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Home Improvement Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Loan against Property")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Construction Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Educational Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(5)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Car Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(6)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Project Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(7)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Term Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(8)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("CC Limit enhancement")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(9)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Cash Credit Limit")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(10)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Industrial Loan")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(11)).setChecked(true);
                    } else if (object.getString("radioButtonTypeOfLoan").equals("Not Applicable")) {
                        ((RadioButton) rgTypeOfLoan.getChildAt(12)).setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


               /* if (object.getString("purposeOfValuationSpinner").equals("0")) {
                    spinnerPurposeOfValuation.setSelection(0);
                } else if (object.getString("purposeOfValuationSpinner").equals("1")) {
                    spinnerPurposeOfValuation.setSelection(1);
                } else if (object.getString("purposeOfValuationSpinner").equals("2")) {
                    spinnerPurposeOfValuation.setSelection(2);
                } else if (object.getString("purposeOfValuationSpinner").equals("3")) {
                    spinnerPurposeOfValuation.setSelection(3);
                } else if (object.getString("purposeOfValuationSpinner").equals("4")) {
                    spinnerPurposeOfValuation.setSelection(4);
                } else if (object.getString("purposeOfValuationSpinner").equals("5")) {
                    spinnerPurposeOfValuation.setSelection(5);
                } else if (object.getString("purposeOfValuationSpinner").equals("6")) {
                    spinnerPurposeOfValuation.setSelection(6);
                } else if (object.getString("purposeOfValuationSpinner").equals("7")) {
                    spinnerPurposeOfValuation.setSelection(7);
                } else if (object.getString("purposeOfValuationSpinner").equals("8")) {
                    spinnerPurposeOfValuation.setSelection(8);
                } else if (object.getString("purposeOfValuationSpinner").equals("9")) {
                    spinnerPurposeOfValuation.setSelection(9);
                } else if (object.getString("purposeOfValuationSpinner").equals("10")) {
                    spinnerPurposeOfValuation.setSelection(10);
                } else if (object.getString("purposeOfValuationSpinner").equals("11")) {
                    spinnerPurposeOfValuation.setSelection(11);
                } else if (object.getString("purposeOfValuationSpinner").equals("12")) {
                    spinnerPurposeOfValuation.setSelection(12);
                } else if (object.getString("purposeOfValuationSpinner").equals("13")) {
                    spinnerPurposeOfValuation.setSelection(13);
                } else if (object.getString("purposeOfValuationSpinner").equals("14")) {
                    spinnerPurposeOfValuation.setSelection(14);
                } else if (object.getString("purposeOfValuationSpinner").equals("15")) {
                    spinnerPurposeOfValuation.setSelection(15);
                } else if (object.getString("purposeOfValuationSpinner").equals("16")) {
                    spinnerPurposeOfValuation.setSelection(16);
                } else if (object.getString("purposeOfValuationSpinner").equals("17")) {
                    spinnerPurposeOfValuation.setSelection(17);
                } else if (object.getString("purposeOfValuationSpinner").equals("18")) {
                    spinnerPurposeOfValuation.setSelection(18);
                } else if (object.getString("purposeOfValuationSpinner").equals("19")) {
                    spinnerPurposeOfValuation.setSelection(19);
                } else if (object.getString("purposeOfValuationSpinner").equals("20")) {
                    spinnerPurposeOfValuation.setSelection(20);
                } else if (object.getString("purposeOfValuationSpinner").equals("21")) {
                    spinnerPurposeOfValuation.setSelection(21);
                }


                if (object.getString("typeOfPropertySpinner").equals("0")) {
                    spinnerTypeOfProperty.setSelection(0);
                } else if (object.getString("typeOfPropertySpinner").equals("1")) {
                    spinnerTypeOfProperty.setSelection(1);
                } else if (object.getString("typeOfPropertySpinner").equals("2")) {
                    spinnerTypeOfProperty.setSelection(2);
                } else if (object.getString("typeOfPropertySpinner").equals("3")) {
                    spinnerTypeOfProperty.setSelection(3);
                } else if (object.getString("typeOfPropertySpinner").equals("4")) {
                    spinnerTypeOfProperty.setSelection(4);
                } else if (object.getString("typeOfPropertySpinner").equals("5")) {
                    spinnerTypeOfProperty.setSelection(5);
                } else if (object.getString("typeOfPropertySpinner").equals("6")) {
                    spinnerTypeOfProperty.setSelection(6);
                } else if (object.getString("typeOfPropertySpinner").equals("7")) {
                    spinnerTypeOfProperty.setSelection(7);
                } else if (object.getString("typeOfPropertySpinner").equals("8")) {
                    spinnerTypeOfProperty.setSelection(8);
                } else if (object.getString("typeOfPropertySpinner").equals("9")) {
                    spinnerTypeOfProperty.setSelection(9);
                } else if (object.getString("typeOfPropertySpinner").equals("10")) {
                    spinnerTypeOfProperty.setSelection(10);
                } else if (object.getString("typeOfPropertySpinner").equals("11")) {
                    spinnerTypeOfProperty.setSelection(11);
                } else if (object.getString("typeOfPropertySpinner").equals("12")) {
                    spinnerTypeOfProperty.setSelection(12);
                } else if (object.getString("typeOfPropertySpinner").equals("13")) {
                    spinnerTypeOfProperty.setSelection(13);
                } else if (object.getString("typeOfPropertySpinner").equals("14")) {
                    spinnerTypeOfProperty.setSelection(14);
                } else if (object.getString("typeOfPropertySpinner").equals("15")) {
                    spinnerTypeOfProperty.setSelection(15);
                } else if (object.getString("typeOfPropertySpinner").equals("16")) {
                    spinnerTypeOfProperty.setSelection(16);
                } else if (object.getString("typeOfPropertySpinner").equals("17")) {
                    spinnerTypeOfProperty.setSelection(17);
                } else if (object.getString("typeOfPropertySpinner").equals("18")) {
                    spinnerTypeOfProperty.setSelection(18);
                } else if (object.getString("typeOfPropertySpinner").equals("19")) {
                    spinnerTypeOfProperty.setSelection(19);
                } else if (object.getString("typeOfPropertySpinner").equals("20")) {
                    spinnerTypeOfProperty.setSelection(20);
                } else if (object.getString("typeOfPropertySpinner").equals("21")) {
                    spinnerTypeOfProperty.setSelection(21);
                } else if (object.getString("typeOfPropertySpinner").equals("22")) {
                    spinnerTypeOfProperty.setSelection(22);
                } else if (object.getString("typeOfPropertySpinner").equals("23")) {
                    spinnerTypeOfProperty.setSelection(23);
                } else if (object.getString("typeOfPropertySpinner").equals("24")) {
                    spinnerTypeOfProperty.setSelection(24);
                } else if (object.getString("typeOfPropertySpinner").equals("25")) {
                    spinnerTypeOfProperty.setSelection(25);
                } else if (object.getString("typeOfPropertySpinner").equals("26")) {
                    spinnerTypeOfProperty.setSelection(26);
                } else if (object.getString("typeOfPropertySpinner").equals("27")) {
                    spinnerTypeOfProperty.setSelection(27);
                } else if (object.getString("typeOfPropertySpinner").equals("28")) {
                    spinnerTypeOfProperty.setSelection(28);
                } else if (object.getString("typeOfPropertySpinner").equals("29")) {
                    spinnerTypeOfProperty.setSelection(29);
                } else if (object.getString("typeOfPropertySpinner").equals("30")) {
                    spinnerTypeOfProperty.setSelection(30);
                } else if (object.getString("typeOfPropertySpinner").equals("31")) {
                    spinnerTypeOfProperty.setSelection(31);
                } else if (object.getString("typeOfPropertySpinner").equals("32")) {
                    spinnerTypeOfProperty.setSelection(32);
                }*/

            }

        }
    }

    private void populateSinner() {

        try {
            array_purpose = pref.get(Constants.array_purpose);
            purpose_array_list.clear();
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("id", "00000000000");
            hash.put("name", "Choose an item");
            purpose_array_list.add(hash);
            JSONArray array5 = new JSONArray(array_purpose);

            for (int j = 0; j < array5.length(); j++) {
                JSONObject data_object = array5.getJSONObject(j);
                String id = data_object.getString("id");
                String asset = data_object.getString("name");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("id", id);
                hmap.put("name", asset);
                purpose_array_list.add(hmap);
            }

            spinnerAdapter = new SpinnerAdapter(mActivity, purpose_array_list);
            spinnerPurposeOfValuation.setAdapter(spinnerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //SpinnerPurposeOfValuation
        //   spinnerAdapter = new SpinnerAdapter(this, purposeOfValuationArray);
        //  spinnerPurposeOfValuation.setAdapter(spinnerAdapter);

        spinnerPurposeOfValuation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                /*if (spinnerPurposeOfValuation.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/


                if (purpose_array_list.get(i).get("name").equalsIgnoreCase("For any other purpose")) {
                    //   Toast.makeText(SurveyNotRequiredActivity.this, "Please select cancel reason", Toast.LENGTH_SHORT).show();

                    etAnyOtherPurpose.setVisibility(View.VISIBLE);


                } else {
                    etAnyOtherPurpose.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        try {
            array_property = pref.get(Constants.asset_list);
            property_array_list.clear();
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("id", "00000000000");
            hash.put("name", "Choose an item");
            property_array_list.add(hash);
            JSONArray array5 = new JSONArray(array_property);
            for (int j = 0; j < array5.length(); j++) {
                JSONObject data_object = array5.getJSONObject(j);
                String id = data_object.getString("id");
                String asset = data_object.getString("name");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("id", id);
                hmap.put("name", asset);
                property_array_list.add(hmap);
            }

            spinnerAdapter = new SpinnerAdapter(mActivity, property_array_list);
            spinnerTypeOfProperty.setAdapter(spinnerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        spinnerTypeOfProperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               /* if(firstTimer>=2)
                ((Dashboard) mActivity).displayView(2);
                firstTimer++;*/
               Log.v("SpinnerChoosen", property_array_list.get(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                    case R.id.homee:
                        intent = new Intent(mActivity, Dashboard.class);
                        startActivity(intent);
                        return true;

                    case R.id.reschedule:
                        intent = new Intent(mActivity, ScheduleActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.calling:
                        CallDialog();
                        return true;

                    case R.id.changeSurvey:
                        CallDialog();
                        return true;

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

    //StartConfirmation popup
    public void CallDialog() {

        final Dialog dialog = new Dialog(mActivity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.call_dialog);

        dialog.show();

        LinearLayout manager = dialog.findViewById(R.id.ll_manager);
        LinearLayout owner = dialog.findViewById(R.id.ll_owner);
        ImageView close = dialog.findViewById(R.id.iv_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + pref.get(Constants.coordinating_Pnumber)));//"tel:"+number.getText().toString();
                if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
            }
        });

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + pref.get(Constants.coordinating_Pnumber)));//"tel:"+number.getText().toString();
                if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
            }
        });

    }

    private void Hit_GetGeneralInfo_Api() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.GetGeneralSurveyInfo);
        Log.v("Hit_GetGeneralInfo_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", case_id);
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
                        parseJsonGet(response);
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
                            //   Toast.makeText(MapLocatorActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonGet(JSONObject response) {

        Log.v("response", response.toString());
        loader.dismiss();
        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                spinnerPurposeOfValuation.setSelection(Integer.parseInt(jsonObject.getString("purpose_of_assignment")));
                Log.v("My_AssetVal",pref.get(Constants.asset_type));


                int posProp = 0;
                for (int i = 0; i < property_array_list.size(); i++) {
                    if (property_array_list.get(i).get("id").equalsIgnoreCase(pref.get(Constants.asset_type))) {
                        posProp = i;
                        break;
                    }
                }

                spinnerTypeOfProperty.setSelection(posProp);


            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();
            }
            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            loader.cancel();
        }
    }

    public class SpinnerAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String, String>> alist;
        LayoutInflater inflter;


        public SpinnerAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflter.inflate(R.layout.profession_adapter, null);
            TextView name = (TextView) convertView.findViewById(R.id.tv_profession);
            name.setText(alist.get(position).get("name"));
            return convertView;
        }
    }
}
