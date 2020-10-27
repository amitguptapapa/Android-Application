package com.vis.android.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.squareup.picasso.Picasso;
import com.vis.android.Activities.Dashboard;
import com.vis.android.CallingService.CallRecordingService;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableCities;
import com.vis.android.Database.TableDistricts;
import com.vis.android.Database.TableStates;
import com.vis.android.Database.TableTehsils;
import com.vis.android.Database.TableVillages;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CaseDetail extends BaseFragment implements View.OnClickListener {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String IMAGE_DIRECTORY_NAME = "VIS";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int SELECT_PICTURE = 1;
    public static String case_id;
    public static String landmark_lat, landmark_long, lead_schedule_staus = "", schedule_date, schedule_status = "";
    public static String person_number, latitude, longitude;
    //  ******************************************************************************************************
    public static String status;
    public static ArrayList<HashMap<String, String>> cancel_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> alternat_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> co_number_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> cal_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> case_data_list = new ArrayList<HashMap<String, String>>();
    public static String status_lead = "";
    public static ArrayList<String> selectedMembersList = new ArrayList<>();
    static public Uri fileUri, picUri;
    public static Bitmap bitmap;
    public static ImageView ivAttachment;
    public static String support_manager_name, support_manager_email, support_manager_phone, support_owner_name,
            support_owner_email, support_owner_phone, support_co_person_name, support_co_person_email, support_co_person_phone,
            support_bus_asso_name, support_bus_asso_email, support_bus_asso_phone;
    static Preferences pref;
    static CustomLoader loader;
    static String encodedString1 = "";
    static Dialog emailDialog;
    static Context context;
    static Activity activity;
    LinearLayout accept;
    LinearLayout reject;
    LinearLayout footer, llMain, llHideLayout, llHideLayout2;
    //  RelativeLayout back, dots;
    ImageView iv_phonecall, iv_phonecall1, iv_phonecall2, iv_one, iv_two, ivAreaMeasurementHeading, ivCreatedByHeading, ivSiteInspectionHeading, iv_three, iv_four,
            ivSiteNumber;
    RelativeLayout rl_indProject, rl_nature, rl_land, rl_plant, rl_pricerange, rl_coverarea, rl_address, rl_fitting, rl_casedetail, rl_Pdetail, rl_CPdetail, rl_Cdetail,
            rlAreaMeasurement, rlCreatedBy, rlSiteInspection, rl_otherinfo, rlCreateByName, rlCreateByBank, rlCreateByDesignation, rlCreatedByNumber, rlCreatedByEmail, rlCreatedByCompany,
            rlNameOfTheProprietorHead, rlDesignationHead, rlWhoWillBeAvailable;
    TextView tv_vehicle, tv_other, tv_casedetail, tvAreaMeasurementHeading, tvCreatedByHeading, tvSiteInspectionHeading, tv_personaldetail, tv_CoPersonaldetail, tv_proceed, tv_valuation, tv_pincode, tv_purpose, tv_caste;
    EditText et_owner, et_land, et_mobilee, et_fitting, et_natureasset, et_addresslocated, et_status, et_estprice, et_machinary, et_Pasignment, et_caseNature, et_caseType, et_indproject, caseLand, caseMachinary, caseVehicle, caseCategory, caseAddress, caseCovered, caseMovable, caseStock, et_assetno, et_acctype, et_payment, et_service, et_price, etBusinessAssociateName, et_security, et_comment, et_coordinationNumber, et_relation, et_ownername, etCustomerEmail, et_coordinatingPerson, et_mobile, et_email, et_block;
    TextView tvCreateByName, tvCreatedByEmail, tvIndividualOwnerName, tvNameOfTheProprietor;
    EditText etCreateByName, etCreatedByType, etCreatedByNumber, etCreatedByEmail, etCreateByBank, etCreateByDesignation, etCreatedByCompany,
            etWhoWillBeAvailable, etTypeOfOwner, etIndividualOwnerName, etNameOfTheProprietor, etDesignation, etCoordinatingPerson,
            etSiteNumber, etSiteEmail, etRelationship;
    Intent intent;
    RejectAdapter r_adapter;
    String check_status = "1";
    String status_contact1 = "", state = "0", customer_mobile_number_arr, co_alternate_mobile, co_person_number;
    Dialog dialog;
    CallAdapter adater;
    int pos;
    //    ****************************************************************************************************
    Boolean edit_page;
    String id, assets_no, type_of_account, comment, relationship, customer_name, customerEmail, coordinating_Pname, purpose_of_assignment, type_property, type_valuation, pin_code, selected_reason, array_Pincode, array_Type_of_property, array_Assignment_prpose, ownrname, machinery_gross, plant, net_block, array_valdetail, asset_address, asset_type, date, user_email, land_area, covered_area, array_cdetail;
    ImageView iv_phn, iv_phn2, ivMobileCreated;
    TextView number;
    int callstatus = 0;
    //Camera
    String picturePath = "", filename = "", ext = "";
    LinearLayout llAreaMeasurementMain, llPropertyAuthLetterRep;
    TextView tvViewAuthLetter;
    //CallRecord
    //Permissions
    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAPTURE_AUDIO_OUTPUT,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
    };
    View v;
    private String authLetter = "";

    //Call popup
    public static void CallDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.call_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        LinearLayout manager = dialog.findViewById(R.id.ll_manager);
        LinearLayout owner = dialog.findViewById(R.id.ll_owner);
        ImageView close = dialog.findViewById(R.id.iv_close);
        final TextView tvName1, tvName2, tvName3, tvName4, tvMobile1, tvMobile2, tvMobile3, tvMobile4;

        ImageView ivEmail1, ivEmail2, ivEmail3, ivEmail4, iv_call1, iv_call2, iv_call3, iv_call4;
        ivEmail1 = dialog.findViewById(R.id.ivEmail1);
        ivEmail2 = dialog.findViewById(R.id.ivEmail2);
        ivEmail3 = dialog.findViewById(R.id.ivEmail3);
        ivEmail4 = dialog.findViewById(R.id.ivEmail4);
        iv_call1 = dialog.findViewById(R.id.iv_call1);
        iv_call2 = dialog.findViewById(R.id.iv_call2);
        iv_call3 = dialog.findViewById(R.id.iv_call3);
        iv_call4 = dialog.findViewById(R.id.iv_call4);
        tvName1 = dialog.findViewById(R.id.tvName1);
        tvName2 = dialog.findViewById(R.id.tvName2);
        tvName3 = dialog.findViewById(R.id.tvName3);
        tvName4 = dialog.findViewById(R.id.tvName4);
        tvMobile1 = dialog.findViewById(R.id.tvMobile1);
        tvMobile2 = dialog.findViewById(R.id.tvMobile2);
        tvMobile3 = dialog.findViewById(R.id.tvMobile3);
        tvMobile4 = dialog.findViewById(R.id.tvMobile4);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        try {
            if (support_manager_name.equals("")
                    || support_manager_name.equals("null")) {
                tvName1.setText("NA");
            } else {
                tvName1.setText(support_manager_name);
            }

            if (support_owner_name.equals("")
                    || support_owner_name.equals("null")) {
                tvName2.setText("NA");
            } else {
                tvName2.setText(support_owner_name);
            }

            if (support_co_person_name.equals("")
                    || support_co_person_name.equals("null")) {
                tvName4.setText("NA");
            } else {
                tvName4.setText(support_co_person_name);
            }

            if (support_bus_asso_name.equals("")
                    || support_bus_asso_name.equals("null")) {
                tvName3.setText("NA");
            } else {
                tvName3.setText(support_bus_asso_name);
            }

            if (support_manager_phone.equals("")
                    || support_manager_phone.equals("null")) {
                tvMobile1.setText("NA");
            } else {
                tvMobile1.setText(support_manager_phone);
            }

            if (support_owner_phone.equals("")
                    || support_owner_phone.equals("null")) {
                tvMobile2.setText("NA");
            } else {
                tvMobile2.setText(support_owner_phone);
            }

            if (support_co_person_phone.equals("")
                    || support_co_person_phone.equals("null")) {
                tvMobile4.setText("NA");
            } else {
                tvMobile4.setText(support_co_person_phone);
            }

            if (support_bus_asso_phone.equals("")
                    || support_bus_asso_phone.equals("null")) {
                tvMobile3.setText("NA");
            } else {
                tvMobile3.setText(support_bus_asso_phone);
            }

            if (!tvMobile1.getText().toString().equalsIgnoreCase("NA")) {
                call(iv_call1, tvMobile1.getText().toString());
            }
            if (!tvMobile2.getText().toString().equalsIgnoreCase("NA")) {
                call(iv_call2, tvMobile2.getText().toString());
            }
            if (!tvMobile3.getText().toString().equalsIgnoreCase("NA")) {
                call(iv_call3, tvMobile3.getText().toString());
            }
            if (!tvMobile4.getText().toString().equalsIgnoreCase("NA")) {
                call(iv_call4, tvMobile4.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ivEmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName1.getText().toString().equalsIgnoreCase("NA")) {
                    dialog.dismiss();
                    AlertEmailPopup("1", tvName1.getText().toString());
                }
            }
        });
        ivEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName2.getText().toString().equalsIgnoreCase("NA")) {
                    dialog.dismiss();
                    AlertEmailPopup("2", tvName2.getText().toString());
                }
            }
        });
        ivEmail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName3.getText().toString().equalsIgnoreCase("NA")) {
                    dialog.dismiss();
                    AlertEmailPopup("3", tvName3.getText().toString());
                }
            }
        });
        ivEmail4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName4.getText().toString().equalsIgnoreCase("NA")) {
                    dialog.dismiss();
                    AlertEmailPopup("4", tvName4.getText().toString());
                }
            }
        });

    }

    //Email popup
    public static void AlertEmailPopup(final String type, String name) {
        emailDialog = new Dialog(context);
        emailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailDialog.setCancelable(true);
        emailDialog.setContentView(R.layout.alert_email_popup);
        Window window = emailDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        emailDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        emailDialog.show();

        ImageView close;
        final ChipsInput chipInputTo;
        final EditText etSubject, etMessage;
        Button btnBrowse, btnSubmit;

        close = emailDialog.findViewById(R.id.iv_close);
        chipInputTo = emailDialog.findViewById(R.id.chipInputTo);
        etSubject = emailDialog.findViewById(R.id.etSubject);
        etMessage = emailDialog.findViewById(R.id.etMessage);
        btnBrowse = emailDialog.findViewById(R.id.btnBrowse);
        btnSubmit = emailDialog.findViewById(R.id.btnSubmit);
        ivAttachment = emailDialog.findViewById(R.id.ivAttachment);

        selectedMembersList.clear();

        chipInputTo.setChipDeletable(true);
        chipInputTo.setChipHasAvatarIcon(false);
        chipInputTo.setShowChipDetailed(false);
        chipInputTo.addChip(name, "");

        chipInputTo.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chipInterface, int i) {
                selectedMembersList.add(String.valueOf(chipInterface.getLabel()));

            }

            @Override
            public void onChipRemoved(ChipInterface chipInterface, int i) {
                selectedMembersList.remove(chipInterface.getLabel());
            }

            @Override
            public void onTextChanged(CharSequence charSequence) {

                if (charSequence.toString().contains(" ")) {
                    if (Utils.isValidEmail(charSequence.toString().trim())) {
                        chipInputTo.addChip(charSequence.toString().trim(), "");
                    } else {
                        String[] seqSplit = charSequence.toString().split(" ");

                        //  charSequence.toString().replace(" ","");

                        //    chipInputTo.getEditText().setText(charSequence);

                        if (seqSplit.length == 1) {
                            Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!Utils.isValidEmail(chipInputTo.getEditText().getText().toString())){
                    Toast.makeText(CaseDetail.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else*/
                if (etSubject.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter subject", Toast.LENGTH_SHORT).show();
                } else if (etMessage.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter message", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.isNetworkConnectedMainThred(context)) {
                        hitEmailApi(type, etSubject.getText().toString().trim(), etMessage.getText().toString().trim());
                    } else {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraGalleryDialog();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialog.dismiss();
                CallDialog();
            }
        });

    }

    public static void showCameraGalleryDialog() {

        final Dialog dialog = new Dialog(context);

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
                activity.startActivityForResult(photoPickerIntent, SELECT_PICTURE);
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

    public static void call(View view, final String number) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));//"tel:"+number.getText().toString();
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }
        });
    }

    private static void hitEmailApi(String type, String subject, String content) {

        loader.show();

        String url = Utils.getCompleteApiUrl(context, R.string.SendSupportEmail);

        Log.v("hitEmailApi", url);

        // selectedMembersList.remove(0);

        JSONArray emailArray = new JSONArray();

        //emailArray.put(selectedMembersList);

        for (int i = 0; i < selectedMembersList.size(); i++) {
            try {
                emailArray.put(i, selectedMembersList.get(i).trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
           /* int chunkCount = encodedString1.length() / 4000;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= encodedString1.length()) {
                    Log.v("###encodedString-1", encodedString1.substring(4000 * i));
                } else {

                    Log.v("###encodedString-2", encodedString1.substring(4000 * i, max));
                }
            }*/

            jsonObject.put("type", type);
            jsonObject.put("email_array", emailArray);
            jsonObject.put("subject", subject);
            jsonObject.put("content", content);
            jsonObject.put("attachment", encodedString1);
            jsonObject.put("case_id", case_id);
            jsonObject.put("surveyor_id", pref.get(Constants.surveyor_id));

            json_data.put("VIS", jsonObject);

            Log.v("hitEmailApi", json_data.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonEmail(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        loader.cancel();
                        // handle error
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private static void parseJsonEmail(JSONObject response) {

        Log.v("res:hitEmailApi", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String res_code = jsonObject.getString("response_code");

            if (res_code.equals("1")) {
                Toast.makeText(context, res_msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, res_msg, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        loader.cancel();
        emailDialog.dismiss();
    }

    public static void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // AppUtils.showToastSort(mActivity,getString(R.string.permission_camera));

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        } else {
            activity.startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
        // start the image capture Intent
    }

    public static Uri getOutputMediaFileUri(int type) {
        //return Uri.fromFile(getOutputMediaFile(type));

        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", getOutputMediaFile(type));

    }

/*
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        if (DashboardFragment.scheduleCheck.equalsIgnoreCase("1")){
            popup.getMenu().getItem(2).setVisible(false);
        }else {
            popup.getMenu().getItem(2).setVisible(true);
        }

        if (pref.get(Constants.survey_not_required_status).equalsIgnoreCase("1")){
            popup.getMenu().getItem(4).setVisible(false);
        }else {
            //  popup.getMenu().getItem(4).setVisible(false);
        }

        popup.getMenu().getItem(5).setVisible(false);
        popup.getMenu().getItem(6).setVisible(false);
        popup.getMenu().getItem(7).setVisible(false);
        popup.getMenu().getItem(8).setVisible(false);
        popup.getMenu().getItem(9).setVisible(false);
        popup.getMenu().getItem(10).setVisible(false);
        popup.getMenu().getItem(11).setVisible(false);
        popup.getMenu().getItem(12).setVisible(false);
        popup.getMenu().getItem(13).setVisible(false);
        popup.getMenu().getItem(14).setVisible(false);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homee:
                        intent = new Intent(mActivity, Dashboard.class);
                        startActivity(intent);
                        return true;

                    case R.id.survey_not_required:
                        intent = new Intent(mActivity, SurveyNotRequiredActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.reassign:
                        intent = new Intent(mActivity, ReassignActivity.class);
                        startActivity(intent);
                        //SurveyorList();
                        // Toast.makeText(PriliminaryActivity.this, "re-assign", Toast.LENGTH_SHORT).show();

                        return true;

                    case R.id.reschedule:
                        intent = new Intent(mActivity, ScheduleActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.calling:
                        CallDialog();
                        return true;
// Document changes
                    */
/*case R.id.doc:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://valuerservice.com/uploads/0692351837.jpg"));
                        startActivity(intent);
                        return true;*//*


                    default:
                        return false;
                }
            }
        });
    }
*/

    private static File getOutputMediaFile(int type) {

        // External sdcard location

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

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

    /*
     Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact_no));//"tel:"+number.getText().toString();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
     */

    public static String getFileType(String path) {
        String fileType = null;
        fileType = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase();
        return fileType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_case_detail, container, false);

        getid(v);
        setListener();
        checkPermissions();

        //  tv_header.setVisibility(View.GONE);
        //  rl_casedetail.setVisibility(View.VISIBLE);
        iv_one.setBackgroundResource(R.mipmap.up_arrow);
        iv_four.setBackgroundResource(R.mipmap.down_arrow);
        iv_two.setBackgroundResource(R.mipmap.down_arrow);
        ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.down_arrow);
        ivCreatedByHeading.setBackgroundResource(R.mipmap.down_arrow);
        ivSiteInspectionHeading.setBackgroundResource(R.mipmap.down_arrow);
        iv_three.setBackgroundResource(R.mipmap.down_arrow);

        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            if (edit_page)
                HitGetLeadByIdApi(R.string.GetLeadById);
            else
                HitGetLeadByIdApi(R.string.GetCompleteLeadById);
        } else {
            Toast.makeText(mActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        if (!edit_page) {// false
            setEditiblity();
        }
        pref.set(Constants.assestStatus, "Original");
        pref.commit();
        return v;
    }

    public void getid(View v) {
        context = mActivity;
        activity = mActivity;
        pref = new Preferences(mActivity);
        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        //Dashboard.tv_caseheader.setText("Case Details");

        tvViewAuthLetter = v.findViewById(R.id.tvViewAuthLetter);
        llPropertyAuthLetterRep = v.findViewById(R.id.llPropertyAuthLetterRep);

        ivMobileCreated = v.findViewById(R.id.ivMobileCreated);

        //    back = (RelativeLayout) v.findViewById(R.id.rl_back);
        accept = (LinearLayout) v.findViewById(R.id.ll_accept);
        reject = (LinearLayout) v.findViewById(R.id.ll_reject);
        et_ownername = v.findViewById(R.id.et_ownername);
        etCustomerEmail = v.findViewById(R.id.etCustomerEmail);
        et_mobile = v.findViewById(R.id.et_mobile);
        et_email = v.findViewById(R.id.et_email);
        et_block = v.findViewById(R.id.et_block);
        tv_pincode = v.findViewById(R.id.tv_pincode);
        tv_proceed = v.findViewById(R.id.tv_proceed);
        footer = v.findViewById(R.id.ll_footer);
        llMain = v.findViewById(R.id.llMain);
        llHideLayout = v.findViewById(R.id.llHideLayout);
        llHideLayout2 = v.findViewById(R.id.llHideLayout2);
        tv_personaldetail = v.findViewById(R.id.tv_personaldetail);
        tv_CoPersonaldetail = v.findViewById(R.id.tv_CoPersonaldetail);
        tv_casedetail = v.findViewById(R.id.tv_casedetail);
        tvAreaMeasurementHeading = v.findViewById(R.id.tvAreaMeasurementHeading);
        tvCreatedByHeading = v.findViewById(R.id.tvCreatedByHeading);
        tvSiteInspectionHeading = v.findViewById(R.id.tvSiteInspectionHeading);
        tv_other = v.findViewById(R.id.tv_other);
        rl_Pdetail = v.findViewById(R.id.rl_Pdetail);
        rl_CPdetail = v.findViewById(R.id.rl_CPdetail);
        rl_Cdetail = v.findViewById(R.id.rl_Cdetail);
        rlAreaMeasurement = v.findViewById(R.id.rlAreaMeasurement);
        //rlCoveredArea = v.findViewById(R.id.rlCoveredArea);
        //rlLandArea= v.findViewById(R.id.rlLandArea);
        rlNameOfTheProprietorHead = v.findViewById(R.id.rlNameOfTheProprietorHead);
        rlDesignationHead = v.findViewById(R.id.rlDesignationHead);
        rlWhoWillBeAvailable = v.findViewById(R.id.rlWhoWillBeAvailable);
        rlCreatedBy = v.findViewById(R.id.rlCreatedBy);
        rlSiteInspection = v.findViewById(R.id.rlSiteInspection);
        rl_otherinfo = v.findViewById(R.id.rl_otherinfo);
        iv_one = v.findViewById(R.id.iv_one);
        iv_four = v.findViewById(R.id.iv_four);
        iv_two = v.findViewById(R.id.iv_two);
        ivAreaMeasurementHeading = v.findViewById(R.id.ivAreaMeasurementHeading);
        ivCreatedByHeading = v.findViewById(R.id.ivCreatedByHeading);
        ivSiteInspectionHeading = v.findViewById(R.id.ivSiteInspectionHeading);
        iv_three = v.findViewById(R.id.iv_three);
        et_coordinatingPerson = v.findViewById(R.id.et_personname);
        et_relation = v.findViewById(R.id.et_relation);
        et_coordinationNumber = v.findViewById(R.id.et_alternate);
        et_mobilee = v.findViewById(R.id.et_mobilee);
        et_comment = v.findViewById(R.id.et_comment);
        et_machinary = v.findViewById(R.id.et_machinary);
        et_price = v.findViewById(R.id.et_price);
        etBusinessAssociateName = v.findViewById(R.id.etBusinessAssociateName);
        et_service = v.findViewById(R.id.et_service);
        et_Pasignment = v.findViewById(R.id.et_Pasignment);
        et_estprice = v.findViewById(R.id.et_estprice);
        et_status = v.findViewById(R.id.et_status);
        et_addresslocated = v.findViewById(R.id.et_addresslocated);
        et_acctype = v.findViewById(R.id.et_acctype);
        et_caseNature = v.findViewById(R.id.et_nature);
        et_caseType = v.findViewById(R.id.et_type);
        et_indproject = v.findViewById(R.id.et_project);
        caseLand = v.findViewById(R.id.et_landarea);
        caseVehicle = v.findViewById(R.id.et_vehicle);
        caseCategory = v.findViewById(R.id.et_assetcat);
        caseCovered = v.findViewById(R.id.et_coverarea);
        caseMovable = v.findViewById(R.id.et_moveasset);
        caseStock = v.findViewById(R.id.et_stock);
        // tv_header = v.findViewById(R.id.tv_header);
        // dots = v.findViewById(R.id.rl_dots);
        // rl_casedetail = v.findViewById(R.id.rl_casedetail);
        //  tv_caseid = v.findViewById(R.id.tv_caseid);

        rlCreatedByNumber = v.findViewById(R.id.rlCreatedByNumber);
        rlCreateByName = v.findViewById(R.id.rlCreateByName);
        rlCreateByBank = v.findViewById(R.id.rlCreateByBank);
        rlCreateByDesignation = v.findViewById(R.id.rlCreateByDesignation);
        rlCreatedByEmail = v.findViewById(R.id.rlCreatedByEmail);
        rlCreatedByCompany = v.findViewById(R.id.rlCreatedByCompany);

        llAreaMeasurementMain = v.findViewById(R.id.llAreaMeasurementMain);

        rl_indProject = v.findViewById(R.id.rl_indProject);
        rl_nature = v.findViewById(R.id.rl_nature);
        rl_fitting = v.findViewById(R.id.rl_fitting);
        rl_address = v.findViewById(R.id.rl_address);
        rl_land = v.findViewById(R.id.rl_land);
        rl_coverarea = v.findViewById(R.id.rl_coverarea);
        rl_plant = v.findViewById(R.id.rl_plant);
        rl_pricerange = v.findViewById(R.id.rl_pricerange);
        et_natureasset = v.findViewById(R.id.et_natureasset);
        et_fitting = v.findViewById(R.id.et_fitting);
        et_land = v.findViewById(R.id.et_land);
        tv_vehicle = v.findViewById(R.id.tv_vehicle);
        et_owner = v.findViewById(R.id.et_owner);
        //etCoveredArea = v.findViewById(R.id.etCoveredArea);
        //etLandArea = v.findViewById(R.id.etLandArea);
        etCreateByName = v.findViewById(R.id.etCreateByName);
        etCreatedByType = v.findViewById(R.id.etCreatedByType);
        etCreatedByNumber = v.findViewById(R.id.etCreatedByNumber);
        etCreatedByEmail = v.findViewById(R.id.etCreatedByEmail);
        etCreateByBank = v.findViewById(R.id.etCreateByBank);
        etCreateByDesignation = v.findViewById(R.id.etCreateByDesignation);
        etCreatedByCompany = v.findViewById(R.id.etCreatedByCompany);
        etWhoWillBeAvailable = v.findViewById(R.id.etWhoWillBeAvailable);
        etTypeOfOwner = v.findViewById(R.id.etTypeOfOwner);
        etIndividualOwnerName = v.findViewById(R.id.etIndividualOwnerName);
        etNameOfTheProprietor = v.findViewById(R.id.etNameOfTheProprietor);
        etDesignation = v.findViewById(R.id.etDesignation);
        etCoordinatingPerson = v.findViewById(R.id.etCoordinatingPerson);
        etSiteNumber = v.findViewById(R.id.etSiteNumber);
        etSiteEmail = v.findViewById(R.id.etSiteEmail);
        etRelationship = v.findViewById(R.id.etRelationship);
        iv_phonecall = v.findViewById(R.id.iv_phonecall);
        iv_phonecall2 = v.findViewById(R.id.iv_phonecall2);
        iv_phonecall1 = v.findViewById(R.id.iv_phonecall1);
        ivSiteNumber = v.findViewById(R.id.ivSiteNumber);

        tvCreateByName = v.findViewById(R.id.tvCreateByName);
        tvCreatedByEmail = v.findViewById(R.id.tvCreatedByEmail);
        tvIndividualOwnerName = v.findViewById(R.id.tvIndividualOwnerName);
        tvNameOfTheProprietor = v.findViewById(R.id.tvNameOfTheProprietor);

    }

    public void setEditiblity() {

        ivMobileCreated.setEnabled(false);
        iv_phonecall.setEnabled(false);
        iv_phonecall2.setEnabled(false);
        iv_phonecall1.setEnabled(false);
        ivSiteNumber.setEnabled(false);

    }

    //******************************* Lead status *******************************//

    public void setListener() {
        accept.setOnClickListener(this);
        reject.setOnClickListener(this);
        //  back.setOnClickListener(this);
        tv_proceed.setOnClickListener(this);
        tv_personaldetail.setOnClickListener(this);
        tv_CoPersonaldetail.setOnClickListener(this);
        tv_casedetail.setOnClickListener(this);
        tvAreaMeasurementHeading.setOnClickListener(this);
        tvCreatedByHeading.setOnClickListener(this);
        tvSiteInspectionHeading.setOnClickListener(this);
        tv_other.setOnClickListener(this);
//        dots.setOnClickListener(this);
        tv_vehicle.setOnClickListener(this);
        iv_phonecall.setOnClickListener(this);
        iv_phonecall2.setOnClickListener(this);
        iv_phonecall1.setOnClickListener(this);
        ivMobileCreated.setOnClickListener(this);
        ivSiteNumber.setOnClickListener(this);

        tvViewAuthLetter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvViewAuthLetter:

                if (!authLetter.equalsIgnoreCase("")) {
                    AlertAuthLetter();
                } else {
                    Toast.makeText(mActivity, "No Authorization Letter Available", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.llMain:
                hideKeyboard(view);
                break;

            case R.id.ll_accept:
                checkpass(v);
                break;

            case R.id.ll_reject:
                RejectDialog();
                break;

           /* case R.id.rl_back:
                intent = new Intent(mActivity, Dashboard.class);

                if (status == "1"){
                    intent.putExtra("NewCasesFragment","NewCasesFragment");
                }else {
                    intent.putExtra("DashboardFragment","DashboardFragment");
                }

                startActivity(intent);
                // onBackPressed();
                break;*/

            case R.id.tv_vehicle:
                intent = new Intent(mActivity, MapLocatorActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_proceed:
                pref.set(Constants.mobile_no_siteInspection, etSiteNumber.getText().toString());
                pref.set(Constants.co_name, etCoordinatingPerson.getText().toString());
                pref.commit();
                setDefaultSurvey();
                Log.e("click12>>>>","click");
                if (schedule_status.equalsIgnoreCase("not_schedule_yet")) {
                    Log.e("click12>>>>","click");
                    ((Dashboard) mActivity).displayView(2);
                } else {
                    ((Dashboard) mActivity).displayView(3);
                    Log.e("click13>>>>","click");
                }



               /* Intent intentNew = new Intent(mActivity, PriliminaryActivity.class);

                pref.set(Constants.case_id, id);
                pref.commit();

                intentNew.putExtra("support_manager_name", support_manager_name);
                intentNew.putExtra("support_manager_email", support_manager_email);
                intentNew.putExtra("support_manager_phone", support_manager_phone);
                intentNew.putExtra("support_owner_name", support_owner_name);
                intentNew.putExtra("support_owner_email", support_owner_email);
                intentNew.putExtra("support_owner_phone", support_owner_phone);
                intentNew.putExtra("support_co_person_name", support_co_person_name);
                intentNew.putExtra("support_co_person_email", support_co_person_email);
                intentNew.putExtra("support_co_person_phone", support_co_person_phone);
                intentNew.putExtra("support_bus_asso_name", support_bus_asso_name);
                intentNew.putExtra("support_bus_asso_email", support_bus_asso_email);
                intentNew.putExtra("support_bus_asso_phone", support_bus_asso_phone);

                startActivity(intentNew);*/
                break;

            case R.id.tv_personaldetail:
                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_one.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rl_Pdetail.setVisibility(View.VISIBLE);
                    iv_one.setBackgroundResource(R.mipmap.up_arrow);

                }

                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_Cdetail.getVisibility() == View.VISIBLE) {
                    rl_Cdetail.setVisibility(View.GONE);
                    iv_two.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (rl_otherinfo.getVisibility() == View.VISIBLE) {
                    rl_otherinfo.setVisibility(View.GONE);
                    iv_three.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlAreaMeasurement.getVisibility() == View.VISIBLE) {
                    rlAreaMeasurement.setVisibility(View.GONE);
                    ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlCreatedBy.getVisibility() == View.VISIBLE) {
                    rlCreatedBy.setVisibility(View.GONE);
                    ivCreatedByHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlSiteInspection.getVisibility() == View.VISIBLE) {
                    rlSiteInspection.setVisibility(View.GONE);
                    ivSiteInspectionHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;

            case R.id.tv_casedetail:
                if (rl_Cdetail.getVisibility() == View.VISIBLE) {
                    rl_Cdetail.setVisibility(View.GONE);
                    iv_two.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rl_Cdetail.setVisibility(View.VISIBLE);
                    iv_two.setBackgroundResource(R.mipmap.up_arrow);
                }

                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_one.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_otherinfo.getVisibility() == View.VISIBLE) {
                    rl_otherinfo.setVisibility(View.GONE);
                    iv_three.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlAreaMeasurement.getVisibility() == View.VISIBLE) {
                    rlAreaMeasurement.setVisibility(View.GONE);
                    ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlCreatedBy.getVisibility() == View.VISIBLE) {
                    rlCreatedBy.setVisibility(View.GONE);
                    ivCreatedByHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlSiteInspection.getVisibility() == View.VISIBLE) {
                    rlSiteInspection.setVisibility(View.GONE);
                    ivSiteInspectionHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }


                break;

            case R.id.tvAreaMeasurementHeading:
                if (rlAreaMeasurement.getVisibility() == View.VISIBLE) {
                    rlAreaMeasurement.setVisibility(View.GONE);
                    ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rlAreaMeasurement.setVisibility(View.VISIBLE);
                    ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.up_arrow);
                }

                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_one.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_Cdetail.getVisibility() == View.VISIBLE) {
                    rl_Cdetail.setVisibility(View.GONE);
                    iv_two.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_otherinfo.getVisibility() == View.VISIBLE) {
                    rl_otherinfo.setVisibility(View.GONE);
                    iv_three.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlCreatedBy.getVisibility() == View.VISIBLE) {
                    rlCreatedBy.setVisibility(View.GONE);
                    ivCreatedByHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlSiteInspection.getVisibility() == View.VISIBLE) {
                    rlSiteInspection.setVisibility(View.GONE);
                    ivSiteInspectionHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;

            case R.id.tvCreatedByHeading:
                if (rlCreatedBy.getVisibility() == View.VISIBLE) {
                    rlCreatedBy.setVisibility(View.GONE);
                    ivCreatedByHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rlCreatedBy.setVisibility(View.VISIBLE);
                    ivCreatedByHeading.setBackgroundResource(R.mipmap.up_arrow);
                }

                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_one.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_Cdetail.getVisibility() == View.VISIBLE) {
                    rl_Cdetail.setVisibility(View.GONE);
                    iv_two.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_otherinfo.getVisibility() == View.VISIBLE) {
                    rl_otherinfo.setVisibility(View.GONE);
                    iv_three.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlAreaMeasurement.getVisibility() == View.VISIBLE) {
                    rlAreaMeasurement.setVisibility(View.GONE);
                    ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlSiteInspection.getVisibility() == View.VISIBLE) {
                    rlSiteInspection.setVisibility(View.GONE);
                    ivSiteInspectionHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;

            case R.id.tvSiteInspectionHeading:
                if (rlSiteInspection.getVisibility() == View.VISIBLE) {
                    rlSiteInspection.setVisibility(View.GONE);
                    ivSiteInspectionHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rlSiteInspection.setVisibility(View.VISIBLE);
                    ivSiteInspectionHeading.setBackgroundResource(R.mipmap.up_arrow);
                }

                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_one.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_Cdetail.getVisibility() == View.VISIBLE) {
                    rl_Cdetail.setVisibility(View.GONE);
                    iv_two.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_otherinfo.getVisibility() == View.VISIBLE) {
                    rl_otherinfo.setVisibility(View.GONE);
                    iv_three.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlAreaMeasurement.getVisibility() == View.VISIBLE) {
                    rlAreaMeasurement.setVisibility(View.GONE);
                    ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlCreatedBy.getVisibility() == View.VISIBLE) {
                    rlCreatedBy.setVisibility(View.GONE);
                    ivCreatedByHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;

            case R.id.tv_other:
                if (rl_otherinfo.getVisibility() == View.VISIBLE) {
                    rl_otherinfo.setVisibility(View.GONE);
                    iv_three.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rl_otherinfo.setVisibility(View.VISIBLE);
                    iv_three.setBackgroundResource(R.mipmap.up_arrow);
                }

                if (rl_Cdetail.getVisibility() == View.VISIBLE) {
                    rl_Cdetail.setVisibility(View.GONE);
                    iv_two.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_one.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlAreaMeasurement.getVisibility() == View.VISIBLE) {
                    rlAreaMeasurement.setVisibility(View.GONE);
                    ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlCreatedBy.getVisibility() == View.VISIBLE) {
                    rlCreatedBy.setVisibility(View.GONE);
                    ivCreatedByHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;

            case R.id.tv_CoPersonaldetail:
                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rl_CPdetail.setVisibility(View.VISIBLE);
                    iv_four.setBackgroundResource(R.mipmap.up_arrow);

                }
                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rl_Cdetail.getVisibility() == View.VISIBLE) {
                    rl_Cdetail.setVisibility(View.GONE);
                    iv_two.setBackgroundResource(R.mipmap.down_arrow);

                } else {

                }

                if (rl_otherinfo.getVisibility() == View.VISIBLE) {
                    rl_otherinfo.setVisibility(View.GONE);
                    iv_three.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlAreaMeasurement.getVisibility() == View.VISIBLE) {
                    rlAreaMeasurement.setVisibility(View.GONE);
                    ivAreaMeasurementHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                if (rlCreatedBy.getVisibility() == View.VISIBLE) {
                    rlCreatedBy.setVisibility(View.GONE);
                    ivCreatedByHeading.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;
/*
            case R.id.rl_dots:
                if (status.equals("1")) {
                    Toast.makeText(mActivity, "Please accept the case first", Toast.LENGTH_SHORT).show();
                } else {
                    showPopup(view);
                }
                break;*/
            case R.id.iv_phonecall:
                status_contact1 = "1";
                callstatus = 0;
                CallingDialog();
                break;

            case R.id.iv_phonecall2:
                status_contact1 = "2";
                callstatus = 0;
                CallingDialog();
                break;

            case R.id.iv_phonecall1:
                status_contact1 = "3";
                callstatus = 0;
                CallingDialog();
                break;

            case R.id.ivMobileCreated:
                status_contact1 = "4";
                callstatus = 0;
                CallingDialog();
                break;
            case R.id.ivSiteNumber:
//                status_contact1 = "5";
                makeACall(v);
                break;
        }
    }

    public void makeACall(View v) {
        // Find the EditText by its unique ID
        EditText e = v.findViewById(R.id.etSiteNumber);

        // show() method display the toast with message
        // "clicked"
//        Toast.makeText(mActivity, "clicked", Toast.LENGTH_LONG)
//                .show();

        // Use format with "tel:" and phoneNumber created is
        // stored in u.
        Uri u = Uri.parse("tel:" + e.getText().toString());

        // Create the intent and set the data for the
        // intent as the phone number.
        Intent i = new Intent(Intent.ACTION_DIAL, u);

        try {
            // Launch the Phone app's dialer with a phone
            // number to dial a call.
            startActivity(i);
        } catch (SecurityException s) {
            // show() method display the toast with
            // exception message.
            Toast.makeText(mActivity, s.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void CallingDialog() {

        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.calling_dialog);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //final int poss;
        ImageView close = dialog.findViewById(R.id.iv_close);
        final Button cal = dialog.findViewById(R.id.btn_call);
        final Button dnt_cal = dialog.findViewById(R.id.btn_notcall);
        TextView header = dialog.findViewById(R.id.tv_header);
        final ListView lv_call = dialog.findViewById(R.id.lv_calls);

        if (status_contact1.equals("1")) {
            header.setText("Customer Calling Numbers");
            adater = new CallAdapter(mActivity, cal_list);
            lv_call.setAdapter(adater);

            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);
                    for (int i = 0; i < lv_call.getChildCount(); i++) {
                        if (position == i) {
                            // lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.red));
                            callstatus = 1;
                            lv_call.setAdapter(adater);
                           /* view.setTag(R.color.view_colour);
                            int ColorId = Integer.parseInt(view.getTag().toString());
                            if (ColorId == R.color.view_colour) {
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.trans_white));
                            }
                            else{
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                            }*/


                        } else {
                            callstatus = 2;
                            lv_call.setAdapter(adater);
                            //   lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);


                        }
                    }

                }
            });

        } else if (status_contact1.equals("2")) {
            header.setText("Coordinating Person Alternate Numbers");
            adater = new CallAdapter(mActivity, alternat_list);
            lv_call.setAdapter(adater);
            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + alternat_list.get(position).get("number")));//"tel:"+number.getText().toString();
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
                    startActivity(callIntent);*/

                    pos = position;
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);
                    for (int i = 0; i < lv_call.getChildCount(); i++) {
                        if (position == i) {
                            lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                            callstatus = 1;
                            lv_call.setAdapter(adater);


                            // iv_phn.setBackgroundResource(R.mipmap.phone_white);
                        } else {
                            callstatus = 2;
                            lv_call.setAdapter(adater);
                            lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            //   iv_phn.setBackgroundResource(R.drawable.ic_telephone);

                        }
                    }
                }
            });
        } else if (status_contact1.equals("3")) {
            header.setText("Coordinating Person Numbers");
            adater = new CallAdapter(mActivity, co_number_list);
            lv_call.setAdapter(adater);
            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + alternat_list.get(position).get("number")));//"tel:"+number.getText().toString();
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
                    startActivity(callIntent);*/

                    pos = position;
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);

                    try {
                        for (int i = 0; i < lv_call.getChildCount(); i++) {
                            if (position == i) {
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                                callstatus = 1;
                                lv_call.setAdapter(adater);


                                // iv_phn.setBackgroundResource(R.mipmap.phone_white);
                            } else {
                                callstatus = 2;
                                lv_call.setAdapter(adater);
                                lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                //   iv_phn.setBackgroundResource(R.drawable.ic_telephone);

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (status_contact1.equals("4")) {
            header.setText("Mobile Number");
            ArrayList<HashMap<String, String>> number = new ArrayList<>();
            HashMap hashMap = new HashMap();
            hashMap.put("number", etCreatedByNumber.getText().toString());
            number.add(hashMap);

            adater = new CallAdapter(mActivity, number);
            lv_call.setAdapter(adater);
            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + alternat_list.get(position).get("number")));//"tel:"+number.getText().toString();
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
                    startActivity(callIntent);*/

                    pos = position;
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);

                    try {
                        for (int i = 0; i < lv_call.getChildCount(); i++) {
                            if (position == i) {
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                                callstatus = 1;
                                lv_call.setAdapter(adater);


                                // iv_phn.setBackgroundResource(R.mipmap.phone_white);
                            } else {
                                callstatus = 2;
                                lv_call.setAdapter(adater);
                                lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                //   iv_phn.setBackgroundResource(R.drawable.ic_telephone);

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                if (status_contact1.equals("1")) {
                    //callIntent.setData(Uri.parse("tel:" + cal_list.get(pos).get("number")));//"tel:"+number.getText().toString();
                    callIntent.setData(Uri.parse("tel:" + "9026906202"));//"tel:"+number.getText().toString();
                } else if (status_contact1.equals("2")) {
                    callIntent.setData(Uri.parse("tel:" + alternat_list.get(pos).get("number")));//"tel:"+number.getText().toString();
                } else if (status_contact1.equals("3")) {
                    callIntent.setData(Uri.parse("tel:" + co_number_list.get(pos).get("number")));//"tel:"+number.getText().toString();
                } else if (status_contact1.equals("4")) {
                    callIntent.setData(Uri.parse("tel:" + etCreatedByNumber.getText().toString()));//"tel:"+number.getText().toString();
                }
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

                Intent intent = new Intent(mActivity, CallRecordingService.class);
                mActivity.startService(intent);

            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    //StartConfirmation popup
    public void ConfirmationDialog() {

        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.accept);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        LinearLayout tv_yes = (LinearLayout) dialog.findViewById(R.id.ll_yes);
        LinearLayout tv_no = (LinearLayout) dialog.findViewById(R.id.ll_no);
        TextView accept_msg = (TextView) dialog.findViewById(R.id.tvAlertMsg);

        SpannableString ss1 = new SpannableString(pref.get(Constants.case_u_id));
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);

        accept_msg.setText("Are you sure you want to accept this Case:" + ss1 + "?");
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (Utils.isNetworkConnectedMainThred(mActivity)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    Hit_LeadStatus_Api("3", "");

                } else {
                    Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    //StartConfirmation popup
    public void RejectDialog() {

        dialog = new Dialog(mActivity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.reject_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView submit = (TextView) dialog.findViewById(R.id.tv_submit);
        ImageView cancel = (ImageView) dialog.findViewById(R.id.iv_close);
        ListView container = dialog.findViewById(R.id.lv_container);
        String cancel_reason;
        cancel_reason = pref.get(Constants.cancel_array);

        try {
            cancel_array_list.clear();
            JSONArray array5 = new JSONArray(cancel_reason);
            for (int j = 0; j < array5.length(); j++) {

                JSONObject data_object = array5.getJSONObject(j);
                String Cancel_reason = data_object.getString("Cancel_reason");
                String id = data_object.getString("id");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("Cancel_reason", Cancel_reason);
                hmap.put("id", id);
                cancel_array_list.add(hmap);

            }
            r_adapter = new RejectAdapter(mActivity, cancel_array_list);
            container.setAdapter(r_adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                state = "1";
                if (check_status.equals("0")) {
                    Toast.makeText(mActivity, "Please select reason for skipping", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.isNetworkConnectedMainThred(mActivity)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        Hit_LeadStatus_Api("2", selected_reason);

                    } else {
                        Toast.makeText(mActivity, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void Hit_LeadStatus_Api(String status, final String reason) {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.caseAcceptReject);
        Log.v("Hit_LeadStatus_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("sur_id", pref.get(Constants.user_id));
            jsonObject.put("case_id", id);
            jsonObject.put("status", status);
            jsonObject.put("reasion", reason);
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
                        Log.e("response>>>",reason);
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

    private void parseJson(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                if (state.equals("1")) {
                    dialog.dismiss();
                    pref.set(Constants.lead_status, jsonObject.getString("status"));
                    pref.commit();
                    Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                    intent = new Intent(mActivity, Dashboard.class);
                    startActivity(intent);
                } else if (state.equals("0")) {
                    pref.set(Constants.lead_status, jsonObject.getString("status"));
                    pref.commit();
                    if (jsonObject.get("status").equals("3")) {
                        status_lead = "2";
                        tv_proceed.setVisibility(View.VISIBLE);
                        footer.setVisibility(View.GONE);

                    } else {
                        status_lead = "1";
                        tv_proceed.setVisibility(View.GONE);
                        footer.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                    ((Dashboard) mActivity).displayView(2);
                    //  intent = new Intent(mActivity, PriliminaryActivity.class);
                    // startActivity(intent);
                } else {

                }


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

    public void checkpass(View v) {
        if (et_ownername.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(v.findViewById(R.id.rl_main),
                    "Please enter owner name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }/* else if (et_mobile.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter owner mobile number", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_email.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter owner email", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (tv_valuation.getText().toString().equals("Select Type Of Valuation")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select type of valuation", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_Larea.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter land area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_Carea.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter covered area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (tv_valuation.getText().toString().equals("Select Type Of Valuation")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select type of valuation", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (tv_assignmnet.getText().toString().equals("Select Purpose Of Assignment")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please Select Purpose Of Assignment", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (tv_pincode.getText().toString().equals("Select Pincode")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please Select Pincode", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (tv_type.getText().toString().equals("Select Property Type")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please Select Property Type", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_add.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter property address", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_gross.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter machinary gross", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_block.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter net block", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_plant.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter plant detail", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } */ else {
            ConfirmationDialog();

        }

    }

    //********************************** Preliminary api get *********************************//

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void Hit_Calling_Api() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.calling);

        Log.v("Hit_Calling_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", case_id);
            json_data.put("VIS", jsonObject);


            Log.v("requestget", json_data.toString());

        } catch (JSONException e) {
            Log.v("exception====", e.toString());
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
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonGet(JSONObject response) {

        Log.v("responseget", response.toString());
        cal_list.clear();
        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String res_code = jsonObject.getString("response_code");

            if (res_code.equals("1")) {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();

                JSONObject jsonObject1 = jsonObject.getJSONObject("coordinator_details");

                JSONArray jsonArray = jsonObject1.getJSONArray("contact_no");
                JSONObject creator_details = jsonObject1.getJSONObject("creator_details");
                JSONObject assignd_details = jsonObject1.getJSONObject("assignd_details");
                JSONObject support_team_details = jsonObject1.getJSONObject("support_team_details");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                }
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

    //method to convert string into base64
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == mActivity.RESULT_OK) {

                picturePath = fileUri.getPath().toString();
                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                Log.d("filename_camera", filename);
                String selectedImagePath = picturePath;
                Uri uri = Uri.parse(picturePath);
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
                encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                ivAttachment.setVisibility(View.VISIBLE);
                ivAttachment.setImageBitmap(rotatedBitmap);

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

                encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                ivAttachment.setVisibility(View.VISIBLE);
                ivAttachment.setImageBitmap(rotatedBitmap);

            } else {
                Toast.makeText(mActivity, "Unable to select image", Toast.LENGTH_LONG).show();
            }

        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(mActivity, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    private void HitGetLeadByIdApi(int hit_api) {

        loader.show();

        String url = Utils.getCompleteApiUrl(mActivity, hit_api);
        Log.v("HitGetLeadByIdApi", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));
            json_data.put("VIS", jsonObject);

            Log.v("HitGetLeadByIdApi", json_data.toString());

        } catch (JSONException e) {

            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonGetLeadById(response);
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

    private void parseJsonGetLeadById(JSONObject response) {

        loader.cancel();

        Log.v("resp:HitGetLeadByIdApi", response.toString());

        case_data_list.clear();

        try {

            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String res_msg = jsonObject.getString("response_message");


            if (res_code.equals("1")) {
                JSONArray array_case_datails = jsonObject.getJSONArray("case_datails");

                for (int i = 0; i < array_case_datails.length(); i++) {

                    JSONObject data_object = array_case_datails.getJSONObject(i);
                    JSONObject object_Pdetail = data_object.getJSONObject("Personal_Detail");
                    JSONObject object_Cdetail = data_object.getJSONObject("case_datail");
                    JSONObject object_Comdetail = data_object.getJSONObject("Commercial_detail");
                    JSONObject objectCreatedBy = data_object.getJSONObject("created_by");
                    JSONObject objectSupport = data_object.getJSONObject("support");
                    JSONObject objectSiteInspection = data_object.getJSONObject("site_inspection_info");
                    pref.set(Constants.type_of_assets, object_Cdetail.getString("type_of_assets"));
                    pref.commit();
                    try {

                        if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("2")
                                && !objectSiteInspection.getString("auth_letter").equalsIgnoreCase("")) {

                            llPropertyAuthLetterRep.setVisibility(View.VISIBLE);
                            authLetter = objectSiteInspection.getString("auth_letter");

                        }

                      /*  if (){
                            llPropertyAuthLetterRep.setVisibility(View.VISIBLE);
                            authLetter = jsonObject.getString("auth_letter");
                            // ivAuthLetterRep.setVisibility(View.VISIBLE);
                            // Picasso.with(mActivity).load(jsonObject.getString("auth_letter")).into(ivAuthLetterRep);
                        }*/

                        if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.has("approval_status")) {

                            if (objectSiteInspection.getString("approval_status").equalsIgnoreCase("0")) {
                                tv_proceed.setTextColor(getResources().getColor(R.color.black));
                                tv_proceed.setBackgroundColor(getResources().getColor(R.color.progressgrey));
                                tv_proceed.setClickable(true);
                            } else {
                                tv_proceed.setTextColor(getResources().getColor(R.color.white));
                                tv_proceed.setBackgroundColor(getResources().getColor(R.color.app_header));
                                tv_proceed.setClickable(true);
                            }

                        } else if (objectSiteInspection.getString("status").equalsIgnoreCase("0")) {
                            tv_proceed.setTextColor(getResources().getColor(R.color.black));
                            tv_proceed.setBackgroundColor(getResources().getColor(R.color.progressgrey));
                            tv_proceed.setClickable(true);

                            llHideLayout.setVisibility(View.GONE);
                            llHideLayout2.setVisibility(View.GONE);

                            // etWhoWillBeAvailable.setText("No one can be available");
                            etWhoWillBeAvailable.setText("Assignment is done by the customer once case is accepted");
                        }


                        if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("1")) {
                            llHideLayout.setVisibility(View.GONE);

                            etWhoWillBeAvailable.setText("Owner");
                            etCoordinatingPerson.setText(objectSiteInspection.getString("co_person_name"));
                            etSiteEmail.setText(objectSiteInspection.getString("co_person_email"));
                            etSiteNumber.setText(objectSiteInspection.getString("co_person_number"));
                            etRelationship.setText(objectSiteInspection.getString("rel_with_owner"));
                        } else if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("2")
                                && objectSiteInspection.getString("type_of_owner").equalsIgnoreCase("1")) {
                            llHideLayout.setVisibility(View.VISIBLE);

                            etWhoWillBeAvailable.setText("Person other than owner");
                            etTypeOfOwner.setText("Individual");
                            tvIndividualOwnerName.setText("Individual Owner Name:");
                            etIndividualOwnerName.setText(objectSiteInspection.getString("indi_owner_name"));
                            rlNameOfTheProprietorHead.setVisibility(View.GONE);
                            rlDesignationHead.setVisibility(View.GONE);

                            etCoordinatingPerson.setText(objectSiteInspection.getString("co_person_name"));
                            etSiteEmail.setText(objectSiteInspection.getString("co_person_email"));
                            etSiteNumber.setText(objectSiteInspection.getString("co_person_number"));
                            etRelationship.setText(objectSiteInspection.getString("rel_with_owner"));
                        } else if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("2")
                                && objectSiteInspection.getString("type_of_owner").equalsIgnoreCase("2")) {
                            llHideLayout.setVisibility(View.VISIBLE);

                            etWhoWillBeAvailable.setText("Person other than owner");
                            etTypeOfOwner.setText("Proprietorship");
                            tvIndividualOwnerName.setText("Name of the Proprietorship firm:");
                            etIndividualOwnerName.setText(objectSiteInspection.getString("proprietorship_firm_name"));
                            etNameOfTheProprietor.setText(objectSiteInspection.getString("proprietor_name"));
                            rlNameOfTheProprietorHead.setVisibility(View.VISIBLE);
                            rlDesignationHead.setVisibility(View.GONE);

                            etCoordinatingPerson.setText(objectSiteInspection.getString("co_person_name"));
                            etSiteEmail.setText(objectSiteInspection.getString("co_person_email"));
                            etSiteNumber.setText(objectSiteInspection.getString("co_person_number"));
                            etRelationship.setText(objectSiteInspection.getString("rel_with_owner"));
                        } else if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("2")
                                && objectSiteInspection.getString("type_of_owner").equalsIgnoreCase("3")) {
                            llHideLayout.setVisibility(View.VISIBLE);

                            etWhoWillBeAvailable.setText("Person other than owner");
                            etTypeOfOwner.setText("HUF");
                            tvIndividualOwnerName.setText("Name of the HUF firm:");
                            tvNameOfTheProprietor.setText("Name of Kartas/ Owners:");
                            etIndividualOwnerName.setText(objectSiteInspection.getString("huf_firm_name"));
                            etNameOfTheProprietor.setText(objectSiteInspection.getString("kartas_name"));
                            rlNameOfTheProprietorHead.setVisibility(View.VISIBLE);
                            rlDesignationHead.setVisibility(View.GONE);

                            etCoordinatingPerson.setText(objectSiteInspection.getString("co_person_name"));
                            etSiteEmail.setText(objectSiteInspection.getString("co_person_email"));
                            etSiteNumber.setText(objectSiteInspection.getString("co_person_number"));
                            etRelationship.setText(objectSiteInspection.getString("rel_with_owner"));
                        } else if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("2")
                                && objectSiteInspection.getString("type_of_owner").equalsIgnoreCase("4")) {
                            llHideLayout.setVisibility(View.VISIBLE);

                            etWhoWillBeAvailable.setText("Person other than owner");
                            etTypeOfOwner.setText("LLP");
                            tvIndividualOwnerName.setText("Name of the LLP/ Partnership firm:");
                            tvNameOfTheProprietor.setText("Name of Partners/ Directors:");
                            etIndividualOwnerName.setText(objectSiteInspection.getString("llp_firm_name"));
                            etNameOfTheProprietor.setText(objectSiteInspection.getString("partner_name"));
                            rlNameOfTheProprietorHead.setVisibility(View.VISIBLE);
                            rlDesignationHead.setVisibility(View.GONE);

                            etCoordinatingPerson.setText(objectSiteInspection.getString("co_person_name"));
                            etSiteEmail.setText(objectSiteInspection.getString("co_person_email"));
                            etSiteNumber.setText(objectSiteInspection.getString("co_person_number"));
                            etRelationship.setText(objectSiteInspection.getString("rel_with_owner"));
                        } else if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("2")
                                && objectSiteInspection.getString("type_of_owner").equalsIgnoreCase("5")) {
                            llHideLayout.setVisibility(View.VISIBLE);

                            etWhoWillBeAvailable.setText("Person other than owner");
                            etTypeOfOwner.setText("Private Limited Company");
                            tvIndividualOwnerName.setText("Name of the Private Limited company:");
                            tvNameOfTheProprietor.setText("Name of the Executive Directors:");
                            etIndividualOwnerName.setText(objectSiteInspection.getString("private_company_name"));
                            etNameOfTheProprietor.setText(objectSiteInspection.getString("exe_director_name"));
                            rlNameOfTheProprietorHead.setVisibility(View.VISIBLE);
                            rlDesignationHead.setVisibility(View.GONE);

                            etCoordinatingPerson.setText(objectSiteInspection.getString("co_person_name"));
                            etSiteEmail.setText(objectSiteInspection.getString("co_person_email"));
                            etSiteNumber.setText(objectSiteInspection.getString("co_person_number"));
                            etRelationship.setText(objectSiteInspection.getString("rel_with_owner"));
                        } else if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("2")
                                && objectSiteInspection.getString("type_of_owner").equalsIgnoreCase("6")) {
                            llHideLayout.setVisibility(View.VISIBLE);

                            etWhoWillBeAvailable.setText("Person other than owner");
                            etTypeOfOwner.setText("Limited Company");
                            tvIndividualOwnerName.setText("Name of the Limited company:");
                            tvNameOfTheProprietor.setText("Name of the Officer In-charge:");
                            etIndividualOwnerName.setText(objectSiteInspection.getString("limited_company_name"));
                            etNameOfTheProprietor.setText(objectSiteInspection.getString("officer_incharge_name"));
                            etDesignation.setText(objectSiteInspection.getString("designation_name"));
                            rlNameOfTheProprietorHead.setVisibility(View.VISIBLE);
                            rlDesignationHead.setVisibility(View.VISIBLE);

                            etCoordinatingPerson.setText(objectSiteInspection.getString("co_person_name"));
                            etSiteEmail.setText(objectSiteInspection.getString("co_person_email"));
                            etSiteNumber.setText(objectSiteInspection.getString("co_person_number"));
                            etRelationship.setText(objectSiteInspection.getString("rel_with_owner"));
                        } else if (objectSiteInspection.getString("status").equalsIgnoreCase("1")
                                && objectSiteInspection.getString("site_inspection_presence").equalsIgnoreCase("3")) {
                            llHideLayout.setVisibility(View.GONE);
                            llHideLayout2.setVisibility(View.GONE);

                            etWhoWillBeAvailable.setText("No one can be available");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // if (object_Pdetail.getString("status").equalsIgnoreCase("3"))
                    {

                        pref.set(Constants.survey_not_required_status, object_Cdetail.getString("survey_not_required_status"));
                        pref.commit();

                        support_manager_name = objectSupport.get("manager_name").toString();
                        support_manager_email = objectSupport.get("manager_email").toString();
                        support_manager_phone = objectSupport.get("manager_phone").toString();
                        support_owner_name = objectSupport.get("owner_name").toString();
                        support_owner_email = objectSupport.get("owner_email").toString();
                        support_owner_phone = objectSupport.get("owner_phone").toString();
                        support_co_person_name = objectSupport.get("co_person_name").toString();
                        support_co_person_email = objectSupport.get("co_person_email").toString();
                        support_co_person_phone = objectSupport.get("co_person_phone").toString();
                        support_bus_asso_name = objectSupport.get("bus_asso_name").toString();
                        support_bus_asso_email = objectSupport.get("bus_asso_email").toString();
                        support_bus_asso_phone = objectSupport.get("bus_asso_phone").toString();

                        pref.set(Constants.support_manager_name, support_manager_name);
                        pref.set(Constants.support_manager_email, support_manager_email);
                        pref.set(Constants.support_manager_phone, support_manager_phone);
                        pref.set(Constants.support_owner_name, support_owner_name);
                        pref.set(Constants.support_owner_email, support_owner_email);
                        pref.set(Constants.support_owner_phone, support_owner_phone);
                        pref.set(Constants.support_co_person_name, support_co_person_name);
                        pref.set(Constants.support_co_person_email, support_co_person_email);
                        pref.set(Constants.support_co_person_phone, support_co_person_phone);
                        pref.set(Constants.support_bus_asso_name, support_bus_asso_name);
                        pref.set(Constants.support_bus_asso_email, support_bus_asso_email);
                        pref.set(Constants.support_bus_asso_phone, support_bus_asso_phone);
                        pref.commit();

                        // if (object_Pdetail.get("status").toString().equals("1") || object_Pdetail.get("status").toString().equals("3")) {
                        Dashboard.tv_caseid.setText("Case ID: " + object_Pdetail.get("case_u_id").toString());
                        customer_name = object_Pdetail.get("customer_name").toString();
                        customerEmail = object_Pdetail.get("user_email").toString();
                        person_number = object_Pdetail.get("customer_mobile_number").toString();

                        pref.set(Constants.customerName, customer_name);
                        pref.set(Constants.personNumber, person_number);
                        pref.set(Constants.customerEmail, customerEmail);

                        pref.commit();

                        schedule_status = object_Pdetail.getString("schedule_status");

                        if (schedule_status.equalsIgnoreCase("not_schedule_yet")) {
                            // ((Dashboard)mActivity).displayView(2);
                        } else {
                            tv_proceed.setText("Proceed to start the survey");
                            //   ((Dashboard)mActivity).displayView(3);
                        }

                        id = object_Cdetail.get("id").toString();
                        case_id = object_Pdetail.get("case_id").toString();
                        user_email = object_Pdetail.get("user_email").toString();
                        lead_schedule_staus = object_Pdetail.get("lead_schedule_status").toString();
                        ownrname = object_Pdetail.get("asset_ownername").toString();
                        asset_address = object_Pdetail.get("asset_address").toString();
                        status = object_Pdetail.get("status").toString();
                        date = object_Pdetail.get("assigned_date").toString();
                        //  type_property = intent.getStringExtra("type_of_property");
                        type_valuation = object_Pdetail.get("type_of_valuation").toString();
                        //pin_code = intent.getStringExtra("pincode");
                        land_area = object_Cdetail.get("land_area").toString();
                        covered_area = object_Cdetail.get("covered_area").toString();

                        if (status.equals("1")) {
                            footer.setVisibility(View.VISIBLE);
                            tv_proceed.setVisibility(View.GONE);
                        } else {
                            footer.setVisibility(View.GONE);
                            tv_proceed.setVisibility(View.VISIBLE);
                        }


                        /*if (land_area.equalsIgnoreCase("not_selected")){
                            rlLandArea.setVisibility(View.GONE);
                        }else {
                            rlLandArea.setVisibility(View.VISIBLE);
                        }

                        if (covered_area.equalsIgnoreCase("not_selected")){
                            rlCoveredArea.setVisibility(View.GONE);
                        }else {
                            rlCoveredArea.setVisibility(View.VISIBLE);
                        }*/

                        if (land_area.equalsIgnoreCase("not_selected") &&
                                covered_area.equalsIgnoreCase("not_selected")) {
                            llAreaMeasurementMain.setVisibility(View.GONE);
                        } else {
                            // llAreaMeasurementMain.setVisibility(View.VISIBLE);
                            llAreaMeasurementMain.setVisibility(View.VISIBLE);
                        }

                        purpose_of_assignment = object_Pdetail.get("purpose_of_assignment").toString();
                        //machinery_gross = intent.getStringExtra("machinery_gross");
                        // plant = intent.getStringExtra("plant");
                        // net_block = intent.getStringExtra("net_block");
                        schedule_date = object_Pdetail.get("schedule_date").toString();
                        coordinating_Pname = object_Pdetail.get("person_name").toString();
                        relationship = object_Pdetail.get("relation_with_owner").toString();
                        comment = object_Pdetail.get("comment").toString();
                        type_of_account = object_Pdetail.get("type_of_account").toString();
                        // assets_no = intent.getStringExtra("assets_no");
                        latitude = object_Pdetail.get("latitude").toString();
                        longitude = object_Pdetail.get("longitude").toString();
                        landmark_lat = object_Cdetail.get("landmark_lat").toString();
                        landmark_long = object_Cdetail.get("landmark_long").toString();
                        co_alternate_mobile = object_Pdetail.get("co_alternate_mobile").toString();

                        et_service.setText(object_Comdetail.get("reporting_services").toString());
                        //et_price.setText(object_Pdetail.get("business_associate_id").toString());
                        et_price.setText(object_Pdetail.get("business_associate_name") + " (" +
                                object_Pdetail.get("business_associate_empid") + ")");
                        //etBusinessAssociateName.setText(object_Pdetail.get("business_associate_name").toString());

                        pref.set(Constants.case_id, id);
                        pref.set(Constants.lead_id, object_Pdetail.getString("lead_id"));
                        pref.set(Constants.owner_name, ownrname);
                        pref.set(Constants.owner_name_check, ownrname);
                        pref.set(Constants.owner_number, person_number);
                        pref.set(Constants.owner_address, asset_address);
                        pref.set(Constants.land_area, land_area);
                        pref.set(Constants.cover_area, covered_area);
                        pref.set(Constants.coordinating_Pname, coordinating_Pname);
                        pref.set(Constants.coordinating_Pnumber, person_number);
                        pref.set(Constants.coordinating_Pnumber, person_number);
                        pref.set(Constants.case_u_id, object_Pdetail.get("case_u_id").toString());
                        pref.commit();

                        et_ownername.setText(customer_name);
                        etCustomerEmail.setText(customerEmail);
                        et_coordinatingPerson.setText(coordinating_Pname);
                        et_mobile.setText(person_number);
                        et_email.setText(user_email);
                        et_Pasignment.setText(purpose_of_assignment);
                        et_relation.setText(relationship);
                        et_coordinationNumber.setText(object_Pdetail.get("alternate_mobile").toString());
                        et_mobilee.setText(object_Pdetail.get("person_number").toString());
                        et_comment.setText(comment);
                        et_acctype.setText(type_of_account);
                        et_owner.setText(ownrname);
                        //etCoveredArea.setText(object_Cdetail.get("covered_area").toString()+" "+object_Cdetail.get("covered_area_unit").toString());
                        //etLandArea.setText(object_Cdetail.get("land_area").toString()+" "+object_Cdetail.get("land_area_unit").toString());


                        if (objectCreatedBy.get("category_id").toString().equals("1")) {
                            etCreatedByType.setText("Commercial Bank Customer");
                            etCreatedByEmail.setText(objectCreatedBy.get("manager_off_email").toString());
                            etCreateByName.setText(objectCreatedBy.get("manager_name").toString());
                            etCreateByBank.setText(objectCreatedBy.get("bank_name").toString());
                            etCreateByDesignation.setText(objectCreatedBy.get("designation").toString());
                            etCreatedByNumber.setText(objectCreatedBy.get("mobile_no").toString());
                            // rlCreatedByNumber.setVisibility(View.vi);
                        } else if (objectCreatedBy.get("category_id").toString().equals("2")) {
                            etCreatedByType.setText("NBFC Customer");

                            etCreatedByEmail.setText(objectCreatedBy.get("email").toString());
                            etCreateByName.setText(objectCreatedBy.get("manager_name").toString());

                            etCreatedByNumber.setText(objectCreatedBy.get("mobile_no").toString());
                            rlCreateByDesignation.setVisibility(View.GONE);
                            tvCreatedByEmail.setText("Email-Id");
                            rlCreateByBank.setVisibility(View.GONE);

                        } else if (objectCreatedBy.get("category_id").toString().equals("3")) {
                            etCreatedByType.setText("Corporate Customer");

                            etCreatedByEmail.setText(objectCreatedBy.get("email").toString());
                            etCreateByName.setText(objectCreatedBy.get("manager_name").toString());

                            etCreatedByNumber.setText(objectCreatedBy.get("mobile_no").toString());
                            etCreateByDesignation.setText(objectCreatedBy.get("designation").toString());
                            etCreatedByCompany.setText(objectCreatedBy.get("company_name").toString());
                            rlCreatedByCompany.setVisibility(View.VISIBLE);
                            tvCreatedByEmail.setText("Email-Id");
                            rlCreateByBank.setVisibility(View.GONE);

                        } else if (objectCreatedBy.get("category_id").toString().equals("4")) {
                            etCreatedByType.setText("Individual Private Customer");

                            etCreatedByEmail.setText(objectCreatedBy.get("email").toString());
                            etCreateByName.setText(objectCreatedBy.get("manager_name").toString());

                            etCreatedByNumber.setText(objectCreatedBy.get("mobile_no").toString());
                            tvCreatedByEmail.setText("Email-Id");
                            rlCreateByBank.setVisibility(View.GONE);
                            rlCreatedByCompany.setVisibility(View.GONE);
                            rlCreateByDesignation.setVisibility(View.GONE);
                        }

                        et_caseNature.setText(object_Cdetail.get("nature_assets").toString());
                        pref.set(Constants.nature_assets, object_Cdetail.get("nature_assets").toString());
                        pref.commit();

                        caseCategory.setText(object_Cdetail.get("category_assets").toString());
                        et_status.setText(object_Cdetail.get("lead_status").toString());
                        et_caseType.setText(object_Cdetail.get("type_of_assets").toString());
                        pref.set(Constants.type_of_assets, object_Cdetail.get("type_of_assets").toString());
                        pref.commit();

                        if (object_Cdetail.get("in_case_type").toString().equals("not_selected")) {
                            rl_indProject.setVisibility(View.GONE);

                        } else {
                            et_indproject.setText(object_Cdetail.get("in_case_type").toString());
                        }

                        if (object_Cdetail.get("nat_of_misc").toString().equals("not_selected")) {
                            rl_nature.setVisibility(View.GONE);

                        } else {
                            et_natureasset.setText(object_Cdetail.get("nat_of_misc").toString());
                        }

                        if (object_Cdetail.get("fitting_assets").toString().equals("not_selected")) {
                            rl_fitting.setVisibility(View.GONE);

                        } else {
                            et_fitting.setText(object_Cdetail.get("fitting_assets").toString());
                        }

                        if (object_Cdetail.get("address_located").toString().equals("not_selected")) {
                            rl_address.setVisibility(View.GONE);

                        } else {
                            et_addresslocated.setText(object_Cdetail.get("address_located").toString());
                        }

                        if (object_Cdetail.get("land_area").toString().equals("not_selected")) {
                            rl_land.setVisibility(View.GONE);
                        } else {
                            rl_land.setVisibility(View.VISIBLE);
                            et_land.setText(object_Cdetail.get("land_area").toString());
                        }

                        if (object_Cdetail.get("covered_area").toString().equals("not_selected")) {
                            rl_coverarea.setVisibility(View.GONE);

                        } else {
                            rl_coverarea.setVisibility(View.VISIBLE);
                            caseCovered.setText(object_Cdetail.get("covered_area").toString());
                        }

                        if (object_Cdetail.get("plant_other").toString().equals("not_selected")) {
                            rl_plant.setVisibility(View.GONE);

                        } else {
                            et_machinary.setText(object_Cdetail.get("plant_other").toString());

                        }

                        if (object_Cdetail.get("estimated_prise").toString().equals("not_selected")) {
                            rl_pricerange.setVisibility(View.GONE);

                        } else {
                            et_estprice.setText(object_Cdetail.get("estimated_prise").toString());

                        }


                        try {
                            cal_list.clear();
                            alternat_list.clear();
                            co_number_list.clear();
                            customer_mobile_number_arr = object_Pdetail.get("customer_mobile_number_arr").toString();
                            co_alternate_mobile = object_Pdetail.get("co_alternate_mobile").toString();
                            co_person_number = object_Pdetail.get("co_person_number").toString();

                            JSONArray call_list = new JSONArray(customer_mobile_number_arr);
                            JSONArray alternate_list = new JSONArray(co_alternate_mobile);
                            JSONArray phone_list = new JSONArray(co_person_number);
                            for (int j = 0; j < call_list.length(); j++) {
                                JSONObject data_object1 = call_list.getJSONObject(j);
                                String number = data_object1.getString("number");
                                HashMap<String, String> hmap = new HashMap<>();
                                hmap.put("number", number);
                                cal_list.add(hmap);
                            }

                            for (int j = 0; j < alternate_list.length(); j++) {
                                JSONObject data_object1 = alternate_list.getJSONObject(j);
                                String number = data_object1.getString("number");
                                HashMap<String, String> hmap = new HashMap<>();
                                hmap.put("number", number);
                                alternat_list.add(hmap);
                            }

                            for (int j = 0; j < phone_list.length(); j++) {
                                JSONObject data_object1 = phone_list.getJSONObject(j);
                                String number = data_object1.getString("number");
                                HashMap<String, String> hmap = new HashMap<>();
                                hmap.put("number", number);
                                co_number_list.add(hmap);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();
            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            loader.cancel();
        }

        hitGetPreliminaryApi();
    }

    private void hitGetPreliminaryApi() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.get_preliminary_data);

        Log.v("hitGetPreliminary", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));
            json_data.put("VIS", jsonObject);
            Log.v("hitGetPreliminary", json_data.toString());

        } catch (JSONException e) {
            Log.v("exception====", e.toString());
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonPrelim(response);
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
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonPrelim(JSONObject response) {

        Log.v("res:hitGetPreliminary", response.toString());
        //doc_list.clear();

        DatabaseController.removeTable(TableStates.attachment);
        DatabaseController.removeTable(TableDistricts.attachment);
        DatabaseController.removeTable(TableCities.attachment);
        DatabaseController.removeTable(TableVillages.attachment);
        DatabaseController.removeTable(TableTehsils.attachment);

        try {
            final JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");

//            setDistrict(jsonObject.getString("district"),jsonObject.getString("state"));
//            setCities(jsonObject.getString("district"),jsonObject.getString("city_val"));
//            setVillages(jsonObject.getString("district"),jsonObject.getString("city_val"));
//            setTehsils(jsonObject.getString("district"),jsonObject.getString("city_val"));

            if (msg.equals("1")) {
                hitLastSavedAssetFormApi();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONArray stateArray = jsonObject.getJSONArray("state_list");
                            JSONArray districtArray = jsonObject.getJSONArray("district_list");
                            JSONArray cityArray = jsonObject.getJSONArray("cities_list");
                            JSONArray villageArray = jsonObject.getJSONArray("village_list");
                            JSONArray tehsilArray = jsonObject.getJSONArray("tehsil_list");
                            if (jsonObject.has("asset_type")) {
                                asset_type = jsonObject.getString("asset_type");
                            }
                            pref.set(Constants.asset_type, asset_type);
                            pref.commit();
                            if(jsonObject.has("land_area")){
                                pref.set(Constants.land_area_as_deed,jsonObject.get("land_area").toString());
                                pref.commit();
                            }
                            if(jsonObject.has("covered_area")){
                               /* pref.set(Constants.land_area_as_deed,jsonObject.get("covered_area").toString());
                                pref.commit();*/
                            }
                            if(jsonObject.has("as_per_map_land")){
                                pref.set(Constants.land_area_as_map,jsonObject.get("as_per_map_land").toString());
                                pref.commit();
                            }
                            /*if (jsonObject.has("property_type")) {
                                String typeOfAssests = jsonObject.getString("property_type");
                                if (!typeOfAssests.isEmpty()) {
                                    et_caseType.setText(jsonObject.getString("property_type"));
                                    pref.set(Constants.type_of_assets, jsonObject.getString("property_type"));
                                    pref.commit();
                                }
                            }*/
                            for (int i = 0; i < stateArray.length(); i++) {
                                JSONObject object = stateArray.getJSONObject(i);

                                final ContentValues contentValues = new ContentValues();

                                contentValues.put(TableStates.statesColumn.id.toString(), object.getString("id"));
                                contentValues.put(TableStates.statesColumn.name.toString(), object.getString("name"));

                                DatabaseController.insertData(contentValues, TableStates.attachment);
                            }


                            for (int i = 0; i < districtArray.length(); i++) {
                                JSONObject object = districtArray.getJSONObject(i);

                                final ContentValues contentValues = new ContentValues();

                                contentValues.put(TableDistricts.districtsColumn.id.toString(), object.getString("id"));
                                contentValues.put(TableDistricts.districtsColumn.state_id.toString(), object.getString("state_id"));
                                contentValues.put(TableDistricts.districtsColumn.name.toString(), object.getString("name"));


                                DatabaseController.insertData(contentValues, TableDistricts.attachment);


                            }


                            for (int i = 0; i < cityArray.length(); i++) {
                                JSONObject object = cityArray.getJSONObject(i);

                                final ContentValues contentValues = new ContentValues();

                                contentValues.put(TableCities.citiesColumn.id.toString(), object.getString("id"));
                                contentValues.put(TableCities.citiesColumn.state_id.toString(), object.getString("state_id"));
                                contentValues.put(TableCities.citiesColumn.district_id.toString(), object.getString("district_id"));
                                contentValues.put(TableCities.citiesColumn.other.toString(), object.getString("other"));
                                contentValues.put(TableCities.citiesColumn.name.toString(), object.getString("name"));


                                DatabaseController.insertData(contentValues, TableCities.attachment);

                            }

                            for (int i = 0; i < villageArray.length(); i++) {
                                JSONObject object = villageArray.getJSONObject(i);

                                final ContentValues contentValues = new ContentValues();

                                contentValues.put(TableVillages.villagesColumn.id.toString(), object.getString("id"));
                                contentValues.put(TableVillages.villagesColumn.state_id.toString(), object.getString("state_id"));
                                contentValues.put(TableVillages.villagesColumn.district_id.toString(), object.getString("district_id"));
                                contentValues.put(TableVillages.villagesColumn.other.toString(), object.getString("other"));
                                contentValues.put(TableVillages.villagesColumn.name.toString(), object.getString("name"));

                                DatabaseController.insertData(contentValues, TableVillages.attachment);

                            }

                            for (int i = 0; i < tehsilArray.length(); i++) {
                                JSONObject object = tehsilArray.getJSONObject(i);

                                final ContentValues contentValues = new ContentValues();

                                contentValues.put(TableTehsils.tehsilsColumn.id.toString(), object.getString("id"));
                                contentValues.put(TableTehsils.tehsilsColumn.state_id.toString(), object.getString("state_id"));
                                contentValues.put(TableTehsils.tehsilsColumn.district_id.toString(), object.getString("district_id"));
                                contentValues.put(TableTehsils.tehsilsColumn.other.toString(), object.getString("other"));
                                contentValues.put(TableTehsils.tehsilsColumn.name.toString(), object.getString("name"));

                                DatabaseController.insertData(contentValues, TableTehsils.attachment);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loader.cancel();
                }
            }, 4000);

        } catch (Exception e) {
            e.printStackTrace();
            loader.cancel();
        }

        //  loader.cancel();
    }

    private void hitLastSavedAssetFormApi() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.LastSavedAssetForm);

        Log.v("hitGetLastSaved", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", pref.get(Constants.case_id));
            jsonObject.put("type", "1");
            jsonObject.put("lastSavedAssetForm", "");

            json_data.put("VIS", jsonObject);
            Log.v("hitGetLastSaved", json_data.toString());

        } catch (JSONException e) {
            Log.v("exception====", e.toString());
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonGetLastSaved(response);
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
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonGetLastSaved(JSONObject response) {

        loader.cancel();

        Log.v("resp:HitGetLastSaved", response.toString());

        try {

            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String res_msg = jsonObject.getString("response_message");


            if (res_code.equals("1")) {
                String lastSavedAssetForm = jsonObject.getString("lastSavedAssetForm");
                if (!lastSavedAssetForm.isEmpty()) {
                    JSONObject arrayGetLastSaved = new JSONObject(lastSavedAssetForm);
                    if (arrayGetLastSaved.has("assestChoosen"))//1 unchanged , 2 changed
                    {
                        pref.set(Constants.types_of_assets, arrayGetLastSaved.getString("assestChoosen"));
                        pref.commit();

                    }

                    if (arrayGetLastSaved.has("surveyChoosenPage")) {
                        pref.set(Constants.surveyChoosenPage, arrayGetLastSaved.getString("surveyChoosenPage"));
                        pref.commit();
                    }
                    Log.v("arrayGetLastSaved", arrayGetLastSaved.toString());
                } else {
//                    pref.set(Constants.type_of_assets, "");
                    pref.set(Constants.surveyChoosenPage, "");
                    pref.set(Constants.defAssest, "");
                    pref.set(Constants.selectedshape, "");
                    pref.set(Constants.titleDiffPercent, "");
                    pref.set(Constants.mapDiffPercent, "");
                    pref.set(Constants.diffReason, "");
                    pref.set(Constants.land_area_as_site_survey, "");
                   pref.commit();
                }
            } else {
                Toast.makeText(mActivity, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();
            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void AlertAuthLetter() {
        final Dialog dialog = new Dialog(mActivity, R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.alert_auth_letter_preview);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView close;
        Button btnSubmit;
        WebView webView;
        ImageView ivImage;
        final ProgressBar progressBar;

        close = dialog.findViewById(R.id.iv_close);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);
        webView = dialog.findViewById(R.id.webView);
        progressBar = dialog.findViewById(R.id.progressBar);
        ivImage = dialog.findViewById(R.id.ivImage);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //  activity.setTitle("Loading...");
                //  activity.setProgress(progress * 100);
                if (progress == 100)
                    progressBar.setVisibility(View.GONE);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(mActivity, description, Toast.LENGTH_SHORT).show();
            }
        });

        //   webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+authLetter);

        if (authLetter.contains(".pdf") || authLetter.contains(".doc")) {
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + authLetter);
        } else {
            // webView.loadDataWithBaseURL(null, "<html><head></head><body><table style=\"width:100%; height:100%;\"><tr><td style=\"vertical-align:middle;\"><img src=\"" + authLetter + "\"></td></tr></table></body></html>", "html/css", "utf-8", null);
            //webView.loadUrl(authLetter);
            webView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            ivImage.setVisibility(View.VISIBLE);

            Picasso.with(mActivity).load(authLetter).into(ivImage);

        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void setDefaultSurvey() {

        Log.v("MyChoosenSrvy","Case "+pref.get(Constants.type_of_assets).toLowerCase());
        switch (pref.get(Constants.type_of_assets).toLowerCase()) {
            case "residential apartment in multistoried building":
                pref.set(Constants.defAssest, "MultiStoried");
                break;
            case "residential apartment in low rise building":
                pref.set(Constants.defAssest, "MultiStoried");
                break;
            case "residential house (plotted development)":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "residential builder floor":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "residential plot/land":
                pref.set(Constants.defAssest, "vacantLand");

                break;
            case "mansion":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "kothi":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "villa":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "group housing society":
                pref.set(Constants.defAssest, "groupHousing");

                break;
            case "commercial office unit":
                pref.set(Constants.defAssest, "Commercial");

                break;
            case "commercial shop unit":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial floor unit":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial office (independent)":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial shop (independent)":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial floor (independent plotted development)":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "commercial land & building":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "hotel/ resort":
                pref.set(Constants.defAssest, "Hotel");
                break;
            case "shopping mall":
                pref.set(Constants.defAssest, "ShoppingMall");

                break;
            case "shopping complex":
                pref.set(Constants.defAssest, "ShoppingMall");

                break;
            case "anchor store":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "restaurant":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "amusement park":
                break;
            case "multiplex":
                pref.set(Constants.defAssest, "Multiplex");

                break;
            case "cinema hall":
                pref.set(Constants.defAssest, "Multiplex");

                break;
            case "godown":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "stadium":
                pref.set(Constants.defAssest, "LargeInfrastructure");


                break;
            case "club":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "institutional plot/land":
                pref.set(Constants.defAssest, "School");


                break;
            case "institutional land & building":
                pref.set(Constants.defAssest, "School");


                break;
            case "educational institution (school/ college/ university)":
                pref.set(Constants.defAssest, "School");


                break;
            case "hospital":
                pref.set(Constants.defAssest, "Hospital");


                break;
            case "multi speciality hospital":
                pref.set(Constants.defAssest, "Hospital");


                break;
            case "nursing home":
                pref.set(Constants.defAssest, "Hospital");


                break;
            case "old age home":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "industrial plot":
                pref.set(Constants.defAssest, "vacantLand");


                break;
            case "industrial project land & building":
                pref.set(Constants.defAssest, "IndustrialLand");


                break;
            case "manufacturing unit":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "small/ mid-scale manufacturing unit":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "industrial plant":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "industrial project":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "infrastructure project":
                pref.set(Constants.defAssest, "LargeInfrastructure");


                break;
            case "large industrial project":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "industrial plant & machinery":
                pref.set(Constants.defAssest, "IndustrialPlant");


                break;
            case "general machinery items":
                pref.set(Constants.defAssest, "IndustrialPlant");


                break;
            case "sez":
                pref.set(Constants.defAssest, "LargeInfrastructure");


                break;
            case "warehouse":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "agricultural land":
                pref.set(Constants.defAssest, "vacantLand");


                break;
            case "farm house":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "financial securities/ instruments":
                pref.set(Constants.defAssest, "Financial");


                break;
            case "current assets":
                pref.set(Constants.defAssest, "CurrentAssets");


                break;
            case "stocks":
                pref.set(Constants.defAssest, "InventoryStock");


                break;
            case "jewellery":
                pref.set(Constants.defAssest, "Jewellery");


                break;
            case "truck":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "bus":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "car":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "aircraft":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "helicopter":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "ship":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "boat":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "motorcycle":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "scooter":
                pref.set(Constants.defAssest, "Vehicles");


                break;
            case "fittings & fixtures":
                pref.set(Constants.defAssest, "Furniture");


                break;
            case "furniture":
                pref.set(Constants.defAssest, "Furniture");


                break;
            case "brand":
                pref.set(Constants.defAssest, "Intangible");


                break;
            case "good will":
                pref.set(Constants.defAssest, "Intangible");


                break;
            case "highway/ expressway":
                pref.set(Constants.defAssest, "LargeInfrastructure");


                break;
            case "it/ office space":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "expo cum exhibition center":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "petrol pump":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "agri mart":
                pref.set(Constants.defAssest, "Commercial");


                break;
            case "cold storage":
                pref.set(Constants.defAssest, "Industrial");


                break;
            case "govt. department/ ministry office":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "public sector unit office":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "police station":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            case "fire station":
                pref.set(Constants.defAssest, "generalSurvey");
                break;
            default:
                pref.set(Constants.defAssest, "generalSurvey");
        }

        pref.commit();
    }

    public class CallAdapter extends BaseAdapter {
        LayoutInflater inflter;
        Context context;

        ArrayList<HashMap<String, String>> alist;


        public CallAdapter(Context context, ArrayList<HashMap<String, String>> alist) {
            inflter = (LayoutInflater.from(context));
            this.context = context;
            this.alist = alist;
        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int i) {
            return alist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.call_adapter, null);
            number = view.findViewById(R.id.tv_number);
            iv_phn = view.findViewById(R.id.iv_phn);
            iv_phn2 = view.findViewById(R.id.iv_phn2);

            if (callstatus == 1) {
                view.setBackgroundColor(getResources().getColor(R.color.greyLight));
                iv_phn2.setVisibility(View.VISIBLE);
                iv_phn.setVisibility(View.GONE);
                number.setTextColor(getResources().getColor(R.color.app_header));
            } else {
                iv_phn2.setVisibility(View.GONE);
                iv_phn.setVisibility(View.VISIBLE);
            }
            iv_phn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status_contact1.equals("1")) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + cal_list.get(i).get("number")));//"tel:"+number.getText().toString();
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
                    } else if (status_contact1.equals("2")) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + alternat_list.get(i).get("number")));//"tel:"+number.getText().toString();
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
                    } else if (status_contact1.equals("3")) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + co_number_list.get(i).get("number")));//"tel:"+number.getText().toString();
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
                    } else if (status_contact1.equals("4")) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + etCreatedByNumber.getText().toString()));//"tel:"+number.getText().toString();
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
                }
            });

            number.setText(alist.get(i).get("number"));
            return view;
        }

    }

    public class RejectAdapter extends BaseAdapter {
        LayoutInflater inflter;
        Context context;
        ArrayList<HashMap<String, String>> alist;
        RadioGroup rg_reject;
        RadioButton checkedRadioButton, rb_one;
        private CheckBox checkBox;
        private TextView reason;
        private int selectedPosition = -1;


        public RejectAdapter(Context context, ArrayList<HashMap<String, String>> alist) {

            inflter = (LayoutInflater.from(context));
            this.context = context;
            this.alist = alist;


        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int i) {
            return alist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.reject_dialog_adapter, null);

            getid(view);
            checkBox = (CheckBox) view.findViewById(R.id.check);
            reason = (TextView) view.findViewById(R.id.name);
            //  setValues(i);
            reason.setText(alist.get(i).get("Cancel_reason"));
            checkBox.setTag(i);

            if (selectedPosition == i) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }


            checkBox.setOnClickListener(onStateChangedListener(checkBox, i));

            return view;
        }

        private View.OnClickListener onStateChangedListener(final CheckBox checkBox, final int position) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        check_status = "1";
                        selectedPosition = position;
                        Log.v("cancel_reason========", alist.get(selectedPosition).get("Cancel_reason"));
                        selected_reason = alist.get(selectedPosition).get("id");
                    } else {
                        check_status = "0";
                        selectedPosition = -1;
                    }
                    notifyDataSetChanged();
                }
            };
        }

        public void getid(View v) {
            //rg_reject = v.findViewById(R.id.radiogroup);
            //rb_one = v.findViewById(R.id.rb_one);


        }

      /*  public void setValues(final int position) {
            rb_one.setText(alist.get(position).get("Cancel_reason"));
            rg_reject.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                    // This puts the value (true/false) into the variable
                    boolean isChecked = checkedRadioButton.isChecked();
                    // If the radiobutton that has changed in check state is now checked...
                    if (isChecked) {
                        selected_reason = (String) checkedRadioButton.getText();
                        Log.v("*******checkedradio", String.valueOf(checkedRadioButton.getText()));

                    }
                }
            });

        }*/
    }

    public class PinAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflter;
        ArrayList<HashMap<String, String>> alist;


        public PinAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
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
            return alist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflter.inflate(R.layout.pincode_adapter, null);
            tv_pincode = (TextView) convertView.findViewById(R.id.tv_pincode);
            tv_pincode.setText(alist.get(position).get("pincode"));
            return convertView;
        }
    }

    public class TypeAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflter;
        ArrayList<HashMap<String, String>> alist;


        public TypeAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
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
            return alist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflter.inflate(R.layout.valuation_adapter, null);
            //tv_type = (TextView) convertView.findViewById(R.id.tv_valuation);
            //tv_type.setText(alist.get(position).get("property"));
            return convertView;
        }
    }

    public class AssignmentAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflter;
        ArrayList<HashMap<String, String>> alist;


        public AssignmentAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
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
            return alist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflter.inflate(R.layout.assignment_adapter, null);
            //tv_assignmnet = (TextView) convertView.findViewById(R.id.tv_assignmnet);
            //tv_assignmnet.setText(alist.get(position).get("Assignment_purpose"));
            return convertView;
        }
    }

    public class CaseDetail_adapter extends RecyclerView.Adapter<CaseDetail_adapter.MyViewHolder> {

        Context context;
        ArrayList<HashMap<String, String>> alist;
        ArrayList<HashMap<String, String>> state;
        Preferences pref;
        int i;

        public CaseDetail_adapter(Context context, ArrayList<HashMap<String, String>> alist) {
            this.context = context;
            this.alist = alist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.case_detail_adapter, parent, false);


            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            pref = new Preferences(context);
            holder.et_nature.setText(alist.get(position).get("nature_assets"));
            holder.et_type.setText(alist.get(position).get("type_of_assets"));
            holder.et_coverarea.setText(alist.get(position).get("covered_area"));
            holder.et_moveasset.setText(alist.get(position).get("moveable_assets"));
            holder.et_vehicle.setText(alist.get(position).get("vehicle"));
            holder.et_stock.setText(alist.get(position).get("stocks"));
            holder.et_landarea.setText(alist.get(position).get("land_area"));
            holder.et_assetcat.setText(alist.get(position).get("category_assets"));
            holder.et_project.setText(alist.get(position).get("in_case_type"));
            holder.et_block.setText(alist.get(position).get("plant_other"));
            holder.et_address.setText(alist.get(position).get("address_located"));

            holder.heading.setText("Case " + (position + 1));

           /* Picasso.with(context).load(alist.get(position).get("category_image")).fit().into(holder.image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in=new Intent(context,SubCategory.class);
                    in.putExtra("cat_id",alist.get(position).get("category_id"));
                    context.startActivity(in);
                    Dashboard.db_status="1";

                    pref.set(Constants.category_name,alist.get(position).get("category_name"));
                    pref.commit();
                }
            });
*/
        }

        @Override
        public int getItemCount() {
            return alist.size();
            // return 2;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public EditText et_nature, et_type, et_project, et_landarea, et_block, et_vehicle, et_assetcat, et_address, et_coverarea, et_moveasset, et_stock;
            public TextView heading;


            public MyViewHolder(View view) {
                super(view);
                et_nature = (EditText) view.findViewById(R.id.et_nature);
                et_type = (EditText) view.findViewById(R.id.et_type);
                et_project = (EditText) view.findViewById(R.id.et_project);
                et_landarea = (EditText) view.findViewById(R.id.et_landarea);
                et_block = (EditText) view.findViewById(R.id.et_block);
                et_vehicle = (EditText) view.findViewById(R.id.et_vehicle);
                et_assetcat = (EditText) view.findViewById(R.id.et_assetcat);
                et_address = (EditText) view.findViewById(R.id.et_address);
                et_coverarea = (EditText) view.findViewById(R.id.et_coverarea);
                et_moveasset = (EditText) view.findViewById(R.id.et_moveasset);
                et_stock = (EditText) view.findViewById(R.id.et_stock);
                heading = (TextView) view.findViewById(R.id.tv_headingg);

            }

        }


    }

}
