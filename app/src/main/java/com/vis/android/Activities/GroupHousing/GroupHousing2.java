package com.vis.android.Activities.GroupHousing;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
import com.vis.android.Fragments.PriliminaryActivity;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableGroupHousing;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.vis.android.Activities.GroupHousing.GroupHousing1.hm;

public class GroupHousing2 extends AppCompatActivity implements View.OnClickListener {
    TextView submit, tvSave;
    Intent intent;
    ImageView camera1;
    ImageView camera2;
    ImageView camera3;
    ImageView camera4;
    ImageView camera5;
    ImageView camera6;
    ImageView camera7;
    ImageView camera8;
    ImageView camera9;
    ImageView camera10;
    ImageView camera11;
    ImageView camera12;
    ImageView camera13;
    ImageView camera14;
    ImageView camera15;
    ImageView camera16;
    ImageView camera17;

    TextView one;
    TextView two;
    TextView three;
    TextView four;
    TextView five;
    TextView six;
    TextView seven;
    TextView eight;
    TextView nine;
    TextView ten;
    TextView eleven;
    TextView twelve;
    TextView thirteen;
    TextView fourteen;
    TextView fifteen;
    TextView sixteen;
    TextView seventeen;

    String encodedString1 = "", encodedString2 = "", encodedString3 = "", encodedString4 = "", encodedString5 = "", encodedString6 = "", encodedString7 = "", encodedString8 = "", encodedString9 = "", encodedString10 = "", encodedString11 = "", encodedString12 = "", encodedString13 = "", encodedString14 = "", encodedString15 = "", encodedString16 = "", encodedString17 = "";

    EditText etSurveyorName;
    String image_status = "0", status = "0";
    //String
    public String picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", setPic = "";
    ;

    //Uri
    Uri picUri, fileUri;

    //StaticString
    private static final String IMAGE_DIRECTORY_NAME = "VIS";

    //Static Int
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;

    //Bitmap
    Bitmap bitmap;
    Preferences pref;
    CustomLoader loader;


    //ArrayList
    public static ArrayList<HashMap<String, String>> arrayListData = new ArrayList<>();

    public static JSONArray jsonArrayData;

    public static ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_housing2);
        getid();

        //arrayListData = (ArrayList<HashMap<String,String>>) getIntent().getSerializableExtra("arrayList");

        //Log.e("arrayListData1",""+arrayListData);
        setListener();

        Log.d("hm", String.valueOf(hm));

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getid() {
        pref = new Preferences(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        submit = (TextView) findViewById(R.id.tv_submit);
        tvSave = findViewById(R.id.tvSave);

        camera1 = (ImageView) findViewById(R.id.iv_camera1);
        camera2 = (ImageView) findViewById(R.id.iv_camera2);
        camera3 = (ImageView) findViewById(R.id.iv_camera3);
        camera4 = (ImageView) findViewById(R.id.iv_camera4);
        camera5 = (ImageView) findViewById(R.id.iv_camera5);
        camera6 = (ImageView) findViewById(R.id.iv_camera6);
        camera7 = (ImageView) findViewById(R.id.iv_camera7);
        camera8 = (ImageView) findViewById(R.id.iv_camera8);
        camera9 = (ImageView) findViewById(R.id.iv_camera9);
        camera10 = (ImageView) findViewById(R.id.iv_camera10);
        camera11 = (ImageView) findViewById(R.id.iv_camera11);
        camera12 = (ImageView) findViewById(R.id.iv_camera12);
        camera13 = (ImageView) findViewById(R.id.iv_camera13);
        camera14 = (ImageView) findViewById(R.id.iv_camera14);
        camera15 = (ImageView) findViewById(R.id.iv_camera15);
        camera16 = (ImageView) findViewById(R.id.iv_camera16);
        camera17 = (ImageView) findViewById(R.id.iv_camera17);
        one = (TextView) findViewById(R.id.tvLetterOfIntent);
        two = (TextView) findViewById(R.id.tvFormLCIV);
        three = (TextView) findViewById(R.id.tvApprovalOfBuildingPlans);
        four = (TextView) findViewById(R.id.tvSanctionedMap);
        five = (TextView) findViewById(R.id.tvNocFromAirport);
        six = (TextView) findViewById(R.id.tvNocFromPollution);
        seven = (TextView) findViewById(R.id.tvNocFromSeiaa);
        eight = (TextView) findViewById(R.id.tvNocFromFireDepartment);
        nine = (TextView) findViewById(R.id.tvNocFromMinistry);
        ten = (TextView) findViewById(R.id.tvNocFromForest);
        eleven = (TextView) findViewById(R.id.tvStructuralStabilityCertificate);
        twelve = (TextView) findViewById(R.id.tvSitePlan);
        thirteen = (TextView) findViewById(R.id.tvLocationMap);
        fourteen = (TextView) findViewById(R.id.tvFloorPlans);
        fifteen = (TextView) findViewById(R.id.tvFlatsStockList);
        sixteen = (TextView) findViewById(R.id.tvSpecifications);
        seventeen = (TextView) findViewById(R.id.tvPhotographs);

        etSurveyorName = findViewById(R.id.etSurveyorName);

    }

    public void setListener() {
        submit.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        camera1.setOnClickListener(this);
        camera2.setOnClickListener(this);
        camera3.setOnClickListener(this);
        camera4.setOnClickListener(this);
        camera5.setOnClickListener(this);
        camera6.setOnClickListener(this);
        camera7.setOnClickListener(this);
        camera8.setOnClickListener(this);
        camera9.setOnClickListener(this);
        camera10.setOnClickListener(this);
        camera11.setOnClickListener(this);
        camera12.setOnClickListener(this);
        camera13.setOnClickListener(this);
        camera14.setOnClickListener(this);
        camera15.setOnClickListener(this);
        camera16.setOnClickListener(this);
        camera17.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:

                if (Utils.isNetworkConnectedMainThred(GroupHousing2.this)) {
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

            case R.id.iv_camera1:
                image_status = "1";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera2:
                image_status = "2";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera3:
                image_status = "3";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera4:
                image_status = "4";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera5:
                image_status = "5";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera6:
                image_status = "6";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera7:
                image_status = "7";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera8:
                image_status = "8";
                showCameraGalleryDialog();
                break;
            case R.id.iv_camera9:
                image_status = "9";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera10:
                image_status = "10";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera11:
                image_status = "11";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera12:
                image_status = "12";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera13:
                image_status = "13";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera14:
                image_status = "14";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera15:
                image_status = "15";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera16:
                image_status = "16";
                showCameraGalleryDialog();
                break;

            case R.id.iv_camera17:
                image_status = "17";
                showCameraGalleryDialog();
                break;
        }
    }

    private void validation() {
        if (one.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Letter of Intent", Toast.LENGTH_SHORT).show();
            one.requestFocus();
        } else if (two.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Form LC-IV License No.", Toast.LENGTH_SHORT).show();
            two.requestFocus();
        } else if (three.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Approval of Building Plans Letter", Toast.LENGTH_SHORT).show();
            three.requestFocus();
        } else if (four.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Sanctioned Map/Building Plans", Toast.LENGTH_SHORT).show();
            four.requestFocus();
        } else if (five.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach NOC From Airport", Toast.LENGTH_SHORT).show();
            five.requestFocus();
        } else if (six.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach NOC From Pollution Control Board", Toast.LENGTH_SHORT).show();
            six.requestFocus();
        } else if (seven.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach NOC From SEIAA for Environmental clearances", Toast.LENGTH_SHORT).show();
            seven.requestFocus();
        } else if (eight.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach NOC From Fire Department", Toast.LENGTH_SHORT).show();
            eight.requestFocus();
        } else if (nine.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach NOC From Ministry of Environment and Forest", Toast.LENGTH_SHORT).show();
            nine.requestFocus();
        } else if (ten.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach NOC From Forest Officer", Toast.LENGTH_SHORT).show();
            ten.requestFocus();
        } else if (eleven.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Structural Stability Certificate", Toast.LENGTH_SHORT).show();
            eleven.requestFocus();
        } else if (twelve.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Site Plan", Toast.LENGTH_SHORT).show();
            twelve.requestFocus();
        } else if (thirteen.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Location Map", Toast.LENGTH_SHORT).show();
            thirteen.requestFocus();
        } else if (fourteen.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Floor Plans", Toast.LENGTH_SHORT).show();
            fourteen.requestFocus();
        } else if (fifteen.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Flats Stock List", Toast.LENGTH_SHORT).show();
            fifteen.requestFocus();
        } else if (sixteen.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Specifications", Toast.LENGTH_SHORT).show();
            sixteen.requestFocus();
        } else if (seventeen.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Attach Photographs", Toast.LENGTH_SHORT).show();
            seventeen.requestFocus();
        } else if (etSurveyorName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Surveyor Name", Toast.LENGTH_SHORT).show();
            etSurveyorName.requestFocus();
        } else {
            putintoHashMap();
            Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public void putintoHashMap() {
        Log.d("hm", String.valueOf(hm));
        hm.put("letterOfIntent", encodedString1);
        hm.put("formLcIv", encodedString2);
        hm.put("approvalOfBuilding", encodedString3);
        hm.put("sanctionedMap", encodedString4);
        hm.put("nocFromAirport", encodedString5);
        hm.put("nocFromPollution", encodedString6);
        hm.put("nocFromSeiaa", encodedString7);
        hm.put("nocFromFireDepartment", encodedString8);
        hm.put("nocFromMinistry", encodedString9);
        hm.put("nocFromForest", encodedString10);
        hm.put("structuralStability", encodedString11);
        hm.put("sitePlan", encodedString12);
        hm.put("locationMap", encodedString13);
        hm.put("floorPlans", encodedString14);
        hm.put("flatsStockList", encodedString15);
        hm.put("specifications", encodedString16);
        hm.put("photographs", encodedString17);
        hm.put("surveyorName", etSurveyorName.getText().toString());

        Log.d("hm2", String.valueOf(hm));
        arrayListData.add(hm);
        Log.v("###ArrayData2", String.valueOf(arrayListData));
        jsonArrayData = new JSONArray(arrayListData);


        if (status.equals("1")) {
            DatabaseController.removeTable(TableGroupHousing.attachment);
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableGroupHousing.groupHousingColumn.data.toString(), jsonArrayData.toString());
            Log.v("###contentValues", String.valueOf(contentValues));
            DatabaseController.insertData(contentValues, TableGroupHousing.attachment);

        } else {
            Log.v("###jso", jsonArrayData.toString());
        }

    }

    public void selectDB() throws JSONException {
        sub_cat = DatabaseController.getGroupHousing();
        Log.v("getfromdb2=====", String.valueOf(sub_cat));

        JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

        Log.v("getfromdbarray=====", String.valueOf(array));

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

          /*  one.setText(object.getString("letterOfIntent"));
            two.setText(object.getString("formLcIv"));
            three.setText(object.getString("approvalOfBuilding"));
            four.setText(object.getString("sanctionedMap"));
            five.setText(object.getString("nocFromAirport"));
            six.setText(object.getString("nocFromPollution"));
            seven.setText(object.getString("nocFromSeiaa"));
            eight.setText(object.getString("nocFromFireDepartment"));
            nine.setText(object.getString("nocFromMinistry"));
            ten.setText(object.getString("nocFromForest"));
            eleven.setText(object.getString("structuralStability"));
            twelve.setText(object.getString("sitePlan"));
            thirteen.setText(object.getString("locationMap"));
            fourteen.setText(object.getString("floorPlans"));
            fifteen.setText(object.getString("flatsStockList"));
            sixteen.setText(object.getString("specifications"));
            seventeen.setText(object.getString("photographs"));
            etSurveyorName.setText(object.getString("surveyorName"));*/

            one.setText(pref.get(Constants.file_one));
            two.setText(pref.get(Constants.file_two));
            three.setText(pref.get(Constants.file_three));
            four.setText(pref.get(Constants.file_four));
            five.setText(pref.get(Constants.file_five));
            six.setText(pref.get(Constants.file_six));
            seven.setText(pref.get(Constants.file_seven));
            eight.setText(pref.get(Constants.file_eight));
            nine.setText(pref.get(Constants.file_nine));
            ten.setText(pref.get(Constants.file_ten));
            eleven.setText(pref.get(Constants.file_eleven));
            twelve.setText(pref.get(Constants.file_twelve));
            thirteen.setText(pref.get(Constants.file_thirteen));
            fourteen.setText(pref.get(Constants.file_fourteen));
            fifteen.setText(pref.get(Constants.file_fifteen));
            sixteen.setText(pref.get(Constants.file_sixteen));
            seventeen.setText(pref.get(Constants.file_seventeen));
            etSurveyorName.setText(object.getString("surveyorName"));
            //GroupHousing1.etProjectName.setText(object.getString("projectName"));

            String projectName = object.getString("letterOfIntent");
            Log.e("!!!letter", projectName);

        }
    }

    public void showCameraGalleryDialog() {

        final Dialog dialog = new Dialog(this);

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

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

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

                if (image_status.equals("1")) {
                    encodedString1 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString1);
                } else if (image_status.equals("2")) {
                    encodedString2 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString2);
                } else if (image_status.equals("3")) {
                    encodedString3 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString3);
                } else if (image_status.equals("4")) {
                    encodedString4 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString4);
                } else if (image_status.equals("5")) {
                    encodedString5 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString5);
                } else if (image_status.equals("6")) {
                    encodedString6 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString6);
                } else if (image_status.equals("7")) {
                    encodedString7 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString7);
                } else if (image_status.equals("8")) {
                    encodedString8 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString8);
                } else if (image_status.equals("9")) {
                    encodedString9 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString9);
                } else if (image_status.equals("10")) {
                    encodedString10 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString10);
                } else if (image_status.equals("11")) {
                    encodedString11 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString11);
                } else if (image_status.equals("12")) {
                    encodedString12 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString12);
                } else if (image_status.equals("13")) {
                    encodedString13 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString13);
                } else if (image_status.equals("14")) {
                    encodedString14 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString14);
                } else if (image_status.equals("15")) {
                    encodedString15 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString15);
                } else if (image_status.equals("16")) {
                    encodedString16 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString16);
                } else {
                    encodedString17 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString17);
                }
            }
        } else if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                Uri contentURI = data.getData();
                //get the Uri for the captured image
                picUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
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
                if (image_status.equals("1")) {
                    encodedString1 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString1);
                } else if (image_status.equals("2")) {
                    encodedString2 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString2);
                } else if (image_status.equals("3")) {
                    encodedString3 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString3);
                } else if (image_status.equals("4")) {
                    encodedString4 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString4);
                } else if (image_status.equals("5")) {
                    encodedString5 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString5);
                } else if (image_status.equals("6")) {
                    encodedString6 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString6);
                } else if (image_status.equals("7")) {
                    encodedString7 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString7);
                } else if (image_status.equals("8")) {
                    encodedString8 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString8);
                } else if (image_status.equals("9")) {
                    encodedString9 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString9);
                } else if (image_status.equals("10")) {
                    encodedString10 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString10);
                } else if (image_status.equals("11")) {
                    encodedString11 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString11);
                } else if (image_status.equals("12")) {
                    encodedString12 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString12);
                } else if (image_status.equals("13")) {
                    encodedString13 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString13);
                } else if (image_status.equals("14")) {
                    encodedString14 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString14);
                } else if (image_status.equals("15")) {
                    encodedString15 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString15);
                } else if (image_status.equals("16")) {
                    encodedString16 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString16);
                } else {
                    encodedString17 = getEncoded64ImageStringFromBitmap(bitmap);
                    setPictures(bitmap, setPic, encodedString17);
                }
            } else {
                Toast.makeText(GroupHousing2.this, "unable to select image", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setPictures(Bitmap b, String setPic, String base64) {
        if (image_status.equals("1")) {
            // iv_one.setImageBitmap(b);
            one.setText(filename);
            pref.set(Constants.file_one,filename);
            pref.commit();

        } else if (image_status.equals("2")) {
            // iv_two.setImageBitmap(b);
            two.setText(filename);
            pref.set(Constants.file_two,filename);
            pref.commit();

        } else if (image_status.equals("3")) {
            three.setText(filename);
            pref.set(Constants.file_three,filename);
            pref.commit();

        } else if (image_status.equals("4")) {
            four.setText(filename);
            pref.set(Constants.file_four,filename);
            pref.commit();

        } else if (image_status.equals("5")) {
            five.setText(filename);
            pref.set(Constants.file_five,filename);
            pref.commit();

        } else if (image_status.equals("6")) {
            six.setText(filename);
            pref.set(Constants.file_six,filename);
            pref.commit();

        } else if (image_status.equals("7")) {
            seven.setText(filename);
            pref.set(Constants.file_seven,filename);
            pref.commit();

        } else if (image_status.equals("8")) {
            eight.setText(filename);
            pref.set(Constants.file_eight,filename);
            pref.commit();

        } else if (image_status.equals("9")) {
            nine.setText(filename);
            pref.set(Constants.file_nine,filename);
            pref.commit();

        } else if (image_status.equals("10")) {
            ten.setText(filename);
            pref.set(Constants.file_ten,filename);
            pref.commit();

        } else if (image_status.equals("11")) {
            eleven.setText(filename);
            pref.set(Constants.file_eleven,filename);
            pref.commit();

        } else if (image_status.equals("12")) {
            twelve.setText(filename);
            pref.set(Constants.file_twelve,filename);
            pref.commit();

        } else if (image_status.equals("13")) {
            thirteen.setText(filename);
            pref.set(Constants.file_thirteen,filename);
            pref.commit();

        } else if (image_status.equals("14")) {
            fourteen.setText(filename);
            pref.set(Constants.file_fourteen,filename);
            pref.commit();
        } else if (image_status.equals("15")) {
            fifteen.setText(filename);
            pref.set(Constants.file_fifteen,filename);
            pref.commit();

        } else if (image_status.equals("16")) {
            sixteen.setText(filename);
            pref.set(Constants.file_sixteen,filename);
            pref.commit();

        } else if (image_status.equals("17")) {
            seventeen.setText(filename);
            pref.set(Constants.file_seventeen,filename);
            pref.commit();

        } else {
            //  iv_four.setImageBitmap(b);

        }

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

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }


    //********************************** Hit FormSubmission Api *********************************//
    private void Hit_FormSubmission_Api() {

        String url = Utils.getCompleteApiUrl(this, R.string.form_submission);
        Log.v("Hit_FormSubmission_Api", url);

        JSONObject json_data = new JSONObject();

        try {
            json_data.put("surveyor_id",pref.get(Constants.surveyor_id));
            json_data.put("case_id",pref.get(Constants.case_id));
            json_data.put("form_id", "5");
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
                            Toast.makeText(GroupHousing2.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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
                intent = new Intent(GroupHousing2.this, Dashboard.class);
                startActivity(intent);

            } else {
                Toast.makeText(GroupHousing2.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(GroupHousing2.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
