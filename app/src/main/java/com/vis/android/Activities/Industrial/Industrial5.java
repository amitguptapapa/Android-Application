package com.vis.android.Activities.Industrial;

import android.Manifest;
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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
import com.vis.android.Database.TableIndustrial;
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

import static com.vis.android.Fragments.CaseDetail.case_id;
import static com.vis.android.Activities.Industrial.Industrial1.hm;


public class Industrial5 extends AppCompatActivity implements View.OnClickListener {
    TextView submit;
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

    TextView tvSave;


    String image_status = "0",value="";
    //String
    public String picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", encodedString = "", setPic = "";
    ;

    //Uri
    Uri picUri, fileUri;

    //StaticString
    private static final String IMAGE_DIRECTORY_NAME = "VIS";

    //Static Int
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;

    //Bitmap
    Bitmap bitmap;
    CustomLoader loader;

    //ArrayList
    public static ArrayList<HashMap<String, String>> arrayListData = new ArrayList<>();

    public static JSONArray jsonArrayData;

    public static ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    Preferences pref;
    String status="0";

    private static final int REQUEST_PERMISSIONS = 1;
    private static String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industrial5);
        getid();
        setListener();
        getPermission();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPermission() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.
            Log.d("Permission", "Displaying permission rationale to provide additional context.");

            ActivityCompat.requestPermissions(Industrial5.this, PERMISSIONS, REQUEST_PERMISSIONS);
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    public void getid() {
        pref=new Preferences(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        submit = (TextView) findViewById(R.id.tv_submit);
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
        one = (TextView) findViewById(R.id.tv_one);
        two = (TextView) findViewById(R.id.tv_two);
        three = (TextView) findViewById(R.id.tv_three);
        four = (TextView) findViewById(R.id.tv_four);
        five = (TextView) findViewById(R.id.tv_five);
        six = (TextView) findViewById(R.id.tv_six);
        seven = (TextView) findViewById(R.id.tv_seven);
        eight = (TextView) findViewById(R.id.tv_eight);
        nine = (TextView) findViewById(R.id.tv_nine);
        ten = (TextView) findViewById(R.id.tv_ten);
        eleven = (TextView) findViewById(R.id.tv_eleven);
        twelve = (TextView) findViewById(R.id.tv_twelve);

        tvSave = findViewById(R.id.tvSave);

        pref = new Preferences(this);
    }

    public void setListener() {
        submit.setOnClickListener(this);
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
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (Utils.isNetworkConnectedMainThred(Industrial5.this)) {
                    status="2";
                    validation();
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    Hit_FormSubmission_Api();

                } else {
                    Toast.makeText(this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }
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
            case R.id.tvSave:
                status="1";
                validation();
                break;


        }
    }

    public void validation(){
        if (one.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Inventory Sheet of P&M", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Inventory Sheet of P&M", Snackbar.LENGTH_SHORT);
            snackbar.show();
            one.requestFocus();
        }

        else if (two.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Flow chart/Block Diagram", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Flow chart/Block Diagram", Snackbar.LENGTH_SHORT);
            snackbar.show();
            two.requestFocus();
        }
        else if (three.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Attach Plant Layout", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Plant Layout", Snackbar.LENGTH_SHORT);
            snackbar.show();
            three.requestFocus();
        }

        else if (four.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Attach Factories Registration", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Factories Registration", Snackbar.LENGTH_SHORT);
            snackbar.show();
            four.requestFocus();
        }
        else if (five.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Labour License", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Labour License", Snackbar.LENGTH_SHORT);
            snackbar.show();
            five.requestFocus();
        }
        else if (six.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Attach Fire NOC", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Fire NOC", Snackbar.LENGTH_SHORT);
            snackbar.show();
            six.requestFocus();
        }
        else if (seven.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach NOC from Pollution Control Board", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach NOC from Pollution Control Board", Snackbar.LENGTH_SHORT);
            snackbar.show();
            seven.requestFocus();
        }
        else if (eight.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Attach Environment Clearance", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Environment Clearance", Snackbar.LENGTH_SHORT);
            snackbar.show();
            eight.requestFocus();
        }
        else if (nine.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Petroleum Product Storage License", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Petroleum Product Storage License", Snackbar.LENGTH_SHORT);
            snackbar.show();
            nine.requestFocus();
        }
        else if (ten.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Explosive Product Storage License", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Explosive Product Storage License", Snackbar.LENGTH_SHORT);
            snackbar.show();
            ten.requestFocus();
        }
        else if (eleven.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Attach Export/Import Code", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Export/Import Code", Snackbar.LENGTH_SHORT);
            snackbar.show();
            eleven.requestFocus();
        }
        else if (twelve.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Attach Any other approval or NOC as per industry", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Please Attach Any other approval or NOC as per industry", Snackbar.LENGTH_SHORT);
            snackbar.show();
            twelve.requestFocus();
        }

        else
        {
            putIntoHm();
            //Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvSave, "Data Saved Successfully", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }
    }

    public void putIntoHm(){

        Log.d("hm",String.valueOf(hm));

        hm.put("inventorySheet", one.getText().toString());
        hm.put("flowChart", two.getText().toString());
        hm.put("plantLayout", three.getText().toString());
        hm.put("factoriesRegistration", four.getText().toString());
        hm.put("labourLicense", five.getText().toString());
        hm.put("fireNoc", six.getText().toString());
        hm.put("nocFromPollution", seven.getText().toString());
        hm.put("environmentalClearance", eight.getText().toString());
        hm.put("petroleumProduct", nine.getText().toString());
        hm.put("explosiveProduct", ten.getText().toString());
        hm.put("exportImport", eleven.getText().toString());
        hm.put("anyOtherApproval", twelve.getText().toString());
        Log.d("hm2",String.valueOf(hm));
        arrayListData.add(hm);
        Log.v("###ArrayData2", String.valueOf(arrayListData));
        jsonArrayData = new JSONArray(arrayListData);
        if(status.equals("1")){
            DatabaseController.removeTable(TableIndustrial.attachment);
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableIndustrial.industrialColumn.data.toString(), jsonArrayData.toString());
            Log.v("###contentValues", String.valueOf(contentValues));
            value=String.valueOf(contentValues);
            DatabaseController.insertData(contentValues, TableIndustrial.attachment);
        }
        else{
            Log.v("###jso",jsonArrayData.toString());
        }

    }

    public void selectDB() throws JSONException {
        sub_cat = DatabaseController.getIndustrial();
        Log.v("getfromdb2=====", String.valueOf(sub_cat));
        //value=String.valueOf(sub_cat);
        JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

        Log.v("getfromdbarray=====", String.valueOf(array));

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            one.setText(object.getString("inventorySheet"));
            two.setText(object.getString("flowChart"));
            three.setText(object.getString("plantLayout"));
            four.setText(object.getString("factoriesRegistration"));
            five.setText(object.getString("labourLicense"));
            six.setText(object.getString("fireNoc"));
            seven.setText(object.getString("nocFromPollution"));
            eight.setText(object.getString("environmentalClearance"));
            nine.setText(object.getString("petroleumProduct"));
            ten.setText(object.getString("explosiveProduct"));
            eleven.setText(object.getString("exportImport"));
            twelve.setText(object.getString("anyOtherApproval"));

            //GroupHousing1.etProjectName.setText(object.getString("projectName"));

           /* String projectName = object.getString("letterOfIntent");
            Log.e("!!!letter", projectName);*/

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
                encodedString = getEncoded64ImageStringFromBitmap(bitmap);
                Log.v("encodedstring", encodedString);

                Log.v("picture_path====", filename);
                setPictures(bitmap, setPic, encodedString);

            } else {
                Toast.makeText(Industrial5.this, "unable to select image", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setPictures(Bitmap b, String setPic, String base64) {
        if (image_status.equals("1")) {
            // iv_one.setImageBitmap(b);
            one.setText(filename);

        } else if (image_status.equals("2")) {
            // iv_two.setImageBitmap(b);
            two.setText(filename);

        } else if (image_status.equals("3")) {
            three.setText(filename);

        } else if (image_status.equals("4")) {
            four.setText(filename);

        } else if (image_status.equals("5")) {

            five.setText(filename);
        } else if (image_status.equals("6")) {
            six.setText(filename);

        } else if (image_status.equals("7")) {
            seven.setText(filename);

        } else if (image_status.equals("8")) {
            eight.setText(filename);

        } else if (image_status.equals("9")) {
            nine.setText(filename);

        } else if (image_status.equals("10")) {
            ten.setText(filename);

        } else if (image_status.equals("11")) {
            eleven.setText(filename);

        } else if (image_status.equals("12")) {
            twelve.setText(filename);

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
            json_data.put("case_id",case_id);
            json_data.put("form_id","3");
            json_data.put("form_data",jsonArrayData);

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
                            Toast.makeText(Industrial5.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
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
                intent = new Intent(Industrial5.this, Dashboard.class);
                startActivity(intent);

            } else {
                Toast.makeText(Industrial5.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Industrial5.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }
    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
