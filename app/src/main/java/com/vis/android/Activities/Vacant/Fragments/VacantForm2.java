package com.vis.android.Activities.Vacant.Fragments;

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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.Fragments.InitiateSurveyForm;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;

public class VacantForm2 extends BaseFragment implements View.OnClickListener {
    RelativeLayout rl_dispute, rl_details, rl_detailsGuarrentee,rl_detailsProp,rl_detailsWater,rl_detailsElect, rl_detailsNoticeOfAcquization, rl_detailsNoticeOfRoadWiden, rl_detailsIsMortgaged, rl_detailsHeritage_restrictions;
    LinearLayout dropdown, llTenant, llG, llimgProp1, llimgProp2, llimgProp3, llimgElect1, llimgElect2, llimgElect3,llimgWater1, llimgWater2, llimgWater3;
    TextView next, tvPrevious, tvQuesG, tvAnyDispute,tvAnyDispute1,tvHeritage_restrictions,tv_IsMortgaged,tvNoticeOfRoadWiden,tv_NoticeOfAcquization,tv_DetailsOfGuarrentee,tv_DetailsOfExisting, tv_dispute, tv_details,tvimgProp1,tvimgProp2,tvimgProp3,tvimgElect1,tvimgElect2,tvimgElect3,tvimgWater1,tvimgWater2,tvimgWater3;
    Intent intent;
    ProgressBar progressbar;
    String typeSurveyChecker;
    Boolean edit_page =true;
    public String picturePath = "", filename = "", ext = "", strVtype = "", strBrand = "", strModel = "", strColor = "", encodedString = "",encodedStringPropimg1 = "",encodedStringPropimg2 = "",encodedStringPropimg3 = "",encodedStringWaterimg1 = "",encodedStringWaterimg2 = "",encodedStringWaterimg3 = "",encodedStringElectimg1 = "",encodedStringElectimg2 = "",encodedStringElectimg3 = "", setPic = "";
    Bitmap bitmap;
    String selected_dispute, selected_details, selected_mortage;
    Spinner spinnerTime, spinnerDispute, spinnerDetails, spinnerGuarrentee, spinnerNoticeOfAcquization, spinnerNoticeOfRoadWiden, spinnerHeritage_restrictions, spinnerIsMortgaged;
    EditText etYearOfAcquistion;
    SpinnerAdapter spinnerAdapter;
    Button btnimgProp1,btnimgProp2,btnimgWater1,btnimgWater2,btnimgElect1,btnimgElect2;
    ImageView iv_imgProp1,ivimgShowProp1,iv_imgProp2,iv_imgProp3,ivimgShowProp2,ivimgShowProp3;
    ImageView iv_imgWater1,ivimgShowWater1,iv_imgWater2,iv_imgWater3,ivimgShowWater2,ivimgShowWater3;
    ImageView iv_imgElect1,ivimgShowElect1,iv_imgElect2,iv_imgElect3,ivimgShowElect2,ivimgShowElect3;
    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;

    private static final String IMAGE_DIRECTORY_NAME = "VIS";

    //Uri
    Uri picUri, fileUri;

    Spinner spinnerProperty;
    Spinner spinnerWater;
    Spinner spinnerElect;
    // String[] dispute = {"Choose an item", "Yes", "No", "No information available"};
    String[] arrayProp = {"Choose an option","Yes, last bill/ payment receipt attached","No information available.","No property tax paid since property not within municipal limits."};
    String[] arrayWater = {"Choose an option","Yes, last bill/ payment receipt attached","No information available.","No municipal water connection"};
    String[] arrayElect = {"Choose an option","Yes, last bill/ payment receipt attached","No information available.","No electrical water connection"};
    String[] dispute = {"Choose an item", "Yes", "No", "No information available since full/ internal survey of the property couldn’t be carried out"};
    String[] comment = {"Choose an item", "Yes as informed by owner/ owner representative", "No as per owner/ owner representative", "No information provided on request"};
    String[] comment1 = {"Choose an item", "Yes as informed by owner/ owner representative", "No as per owner/ owner representative"};
    String[] arrayIsMortgaged = {"Choose an item", "Yes as informed by owner/ owner representative", "No, only portion out of the entire property.", "No, only portion out of the entire property. This property is merged with a non mortgage property."};
    EditText etLegalOwnerNames, etPropertyPurchaser, etPropertyAddress, etPresentResidence, etInCasePossessed,
            etNoOfTenants, etLeasePeriod, etMonthlyRental, etAnyDisputeCame, etDetailsOfExisting, etDisputeDetails, etDetails, etDetailsOfGuarrentee, etDetailsHeritage_restrictions, etDetailsIsMortgaged, etDetailsGuarrentee, etDetailsNoticeOfRoadWiden,
            etDetailsNoticeOfAcquization,etDetailsProp,etPropertyId,etDetailsWater,etDetailsElect;

    RadioGroup rgPropertyConstitution, rgPropertyPossessedBy;
    int dispute_pos = 0, details_pos = 0, guarrentee_pos = 0, time_pos = 0, noticAq_pos = 0, roadWiden_pos = 0, mortage_pos = 0, heritage_pos = 0;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    Preferences pref;
    int spin_prop,spin_water,spin_Elect;
    Boolean first=false,firstwater=false,firstElect=false;
    String nature_assets;
    // TextView tv_caseheader,tv_caseid,tv_header;

    // RelativeLayout rl_casedetail;

    String[] year;
    String imgSource="";

    View v;
    String pageVisited="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_vacantform2, container, false);

        getid(v);
        setListener();

//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

//        progressbar.setMax(10);
//        progressbar.setProgress(2);


        ArrayList<String> years = new ArrayList<String>();
        years.add("Choose an item");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        years.add("No information available");

        year = new String[years.size()];
        for (int i = 0; i < years.size(); i++) {

            year[i] = years.get(i);
        }

        spinnerAdapter = new SpinnerAdapter(mActivity, year);
        spinnerTime.setAdapter(spinnerAdapter);

        /*spinnerAdapter = new SpinnerAdapter(mActivity, arrayProp);
        spinnerProperty.setAdapter(spinnerAdapter);
        spinnerProperty.setSelection(spin_prop);*/

       /* spinnerAdapter = new SpinnerAdapter(mActivity, arrayWater);
        spinnerWater.setAdapter(spinnerAdapter);
        spinnerWater.setSelection(spin_water);*/

     /*   spinnerAdapter = new SpinnerAdapter(mActivity, arrayElect);
        spinnerElect.setAdapter(spinnerAdapter);
        spinnerElect.setSelection(spin_Elect);*/

      /*  if(nature_assets.equalsIgnoreCase("Vacant Land")){
            llmunsciple.setVisibility(View.GONE);

        }*/


        spinnerAdapter = new SpinnerAdapter(mActivity, dispute);
        spinnerDispute.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayIsMortgaged);
        spinnerIsMortgaged.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, comment);
        spinnerDetails.setAdapter(spinnerAdapter);
        spinnerGuarrentee.setAdapter(spinnerAdapter);
        spinnerAdapter = new SpinnerAdapter(mActivity, comment1);
        spinnerNoticeOfAcquization.setAdapter(spinnerAdapter);
        spinnerNoticeOfRoadWiden.setAdapter(spinnerAdapter);
        spinnerHeritage_restrictions.setAdapter(spinnerAdapter);

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

//                selected_dispute = dispute[i];
                time_pos = i;

//                if (selected_dispute.equals("Yes")) {
//                    rl_dispute.setVisibility(View.VISIBLE);
//                } else {
//                    rl_dispute.setVisibility(View.GONE);
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerDispute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selected_dispute = dispute[i];
                dispute_pos = i;

                if (selected_dispute.equals("Yes")) {
                    rl_dispute.setVisibility(View.VISIBLE);
                } else {
                    rl_dispute.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDetails.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selected_details = comment[i];
                details_pos = i;

                if (selected_details.equals("Yes as informed by owner/ owner representative")) {
                    rl_details.setVisibility(View.VISIBLE);
                } else {
                    rl_details.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerGuarrentee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selected_details = comment[i];
                guarrentee_pos = i;
                if (selected_details.equals("Yes as informed by owner/ owner representative")) {
                    rl_detailsGuarrentee.setVisibility(View.VISIBLE);
                } else {
                    rl_detailsGuarrentee.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerNoticeOfAcquization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selected_details = comment[i];
                noticAq_pos = i;
                if (selected_details.equals("Yes as informed by owner/ owner representative")) {
                    rl_detailsNoticeOfAcquization.setVisibility(View.VISIBLE);
                } else {
                    rl_detailsNoticeOfAcquization.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerNoticeOfRoadWiden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selected_details = comment[i];
                roadWiden_pos = i;
                if (selected_details.equals("Yes as informed by owner/ owner representative")) {
                    rl_detailsNoticeOfRoadWiden.setVisibility(View.VISIBLE);
                } else {
                    rl_detailsNoticeOfRoadWiden.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerIsMortgaged.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selected_mortage = arrayIsMortgaged[i];
                mortage_pos = i;
                if (!selected_mortage.equals("Yes as informed by owner/ owner representative")&&mortage_pos!=0) {
                    rl_detailsIsMortgaged.setVisibility(View.VISIBLE);
                } else {
                    rl_detailsIsMortgaged.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerHeritage_restrictions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));

                selected_details = comment[i];
                heritage_pos = i;
                if (selected_details.equals("Yes as informed by owner/ owner representative")) {
                    rl_detailsHeritage_restrictions.setVisibility(View.VISIBLE);
                } else {
                    rl_detailsHeritage_restrictions.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (InitiateSurveyForm.surveyTypeCheck == 1 && typeSurveyChecker.equalsIgnoreCase("true")) {

            etNoOfTenants.setText("No information available");
            etLeasePeriod.setText("No information available");
            etMonthlyRental.setText("No information available");

            spinnerTime.setSelection(year.length - 1);
            spinnerDispute.setSelection(3);
            spinnerDetails.setSelection(3);
            spinnerGuarrentee.setSelection(3);
            spinnerNoticeOfAcquization.setSelection(2);
            spinnerNoticeOfRoadWiden.setSelection(2);
            spinnerHeritage_restrictions.setSelection(2);
            spinnerIsMortgaged.setSelection(2);

        } else {
            spinnerGuarrentee.setSelection(guarrentee_pos);
            spinnerDetails.setSelection(details_pos);
            spinnerDispute.setSelection(dispute_pos);
            spinnerTime.setSelection(time_pos);
            spinnerNoticeOfAcquization.setSelection(noticAq_pos);
            spinnerNoticeOfRoadWiden.setSelection(roadWiden_pos);
            spinnerHeritage_restrictions.setSelection(heritage_pos);
            spinnerIsMortgaged.setSelection(mortage_pos);
            if(mortage_pos!=0){
                rl_detailsIsMortgaged.setVisibility(View.VISIBLE);
            }
            else{
                rl_detailsIsMortgaged.setVisibility(View.GONE);
            }
            if(heritage_pos!=0){
                rl_detailsHeritage_restrictions.setVisibility(View.VISIBLE);
            }
            else{
                rl_detailsHeritage_restrictions.setVisibility(View.GONE);
            }
        }
        if(!edit_page){
            setEditiblity();
        }

        return v;
    }

    public void getid(final View v) {

//        tv_header = v.findViewById(R.id.tv_header);
//        rl_casedetail = v.findViewById(R.id.rl_casedetail);
//        tv_caseheader = v.findViewById(R.id.tv_caseheader);
//        tv_caseid = v.findViewById(R.id.tv_caseid);

        //progressbar = v.findViewById(R.id.Progress);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page=false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page=true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        typeSurveyChecker = pref.get("typeSurveyChecker");
        nature_assets= pref.get(Constants.nature_assets);
        //   back = v.findViewById(R.id.rl_back);
        next = v.findViewById(R.id.next);
        dropdown = v.findViewById(R.id.ll_dropdown);
        llTenant = v.findViewById(R.id.llTenant);
        /*llmunsciple = v.findViewById(R.id.llmunsciple);*/
        llG = v.findViewById(R.id.llG);
        //  dots =  v.findViewById(R.id.rl_dots);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        tvQuesG = v.findViewById(R.id.tvQuesG);
        tvAnyDispute = v.findViewById(R.id.tvAnyDispute);
        tvAnyDispute1 = v.findViewById(R.id.tvAnyDispute1);
        tv_DetailsOfExisting = v.findViewById(R.id.tv_DetailsOfExisting);
        tv_DetailsOfGuarrentee = v.findViewById(R.id.tv_DetailsOfGuarrentee);
        tv_NoticeOfAcquization = v.findViewById(R.id.tv_NoticeOfAcquization);
        tvNoticeOfRoadWiden = v.findViewById(R.id.tvNoticeOfRoadWiden);
        tvHeritage_restrictions = v.findViewById(R.id.tvHeritage_restrictions);
        tv_IsMortgaged = v.findViewById(R.id.tv_IsMortgaged);
      /*  btnimgProp1 = v.findViewById(R.id.btnimgProp1);
        btnimgProp2 = v.findViewById(R.id.btnimgProp2);*/
     /*   llimgProp1 = v.findViewById(R.id.llimgProp1);
        llimgProp2 = v.findViewById(R.id.llimgProp2);
        llimgProp3 = v.findViewById(R.id.llimgProp3);*/
       /* btnimgElect1 = v.findViewById(R.id.btnimgElect1);
        btnimgElect2 = v.findViewById(R.id.btnimgElect2);
        llimgElect1 = v.findViewById(R.id.llimgElect1);
        llimgElect2 = v.findViewById(R.id.llimgElect2);
        llimgElect3 = v.findViewById(R.id.llimgElect3);
        btnimgWater1 = v.findViewById(R.id.btnimgWater1);
        btnimgWater2 = v.findViewById(R.id.btnimgWater2);
        llimgWater1 = v.findViewById(R.id.llimgWater1);
        llimgWater2 = v.findViewById(R.id.llimgWater2);
        llimgWater3 = v.findViewById(R.id.llimgWater3);*/
        tv_dispute = v.findViewById(R.id.tv_dispute);
        tv_details = v.findViewById(R.id.tv_details);
      /*  tvimgElect1 = v.findViewById(R.id.tvimgElect1);
        tvimgElect2 = v.findViewById(R.id.tvimgElect2);
        tvimgElect3 = v.findViewById(R.id.tvimgElect3);
        tvimgProp1 = v.findViewById(R.id.tvimgProp1);
        tvimgProp2 = v.findViewById(R.id.tvimgProp2);
        tvimgProp3 = v.findViewById(R.id.tvimgProp3);
        tvimgWater1 = v.findViewById(R.id.tvimgWater1);
        tvimgWater2 = v.findViewById(R.id.tvimgWater2);
        tvimgWater3 = v.findViewById(R.id.tvimgWater3);*/
        spinnerTime = v.findViewById(R.id.spinnerTime);
        spinnerDispute = v.findViewById(R.id.spinnerDispute);
        /*spinnerProperty = v.findViewById(R.id.spinnerProperty);*/
        iv_imgProp1 = v.findViewById(R.id.iv_imgProp1);
        ivimgShowProp1 = v.findViewById(R.id.ivimgShowProp1);
        iv_imgProp2 = v.findViewById(R.id.iv_imgProp2);
        iv_imgProp3 = v.findViewById(R.id.iv_imgProp3);
        ivimgShowProp2 = v.findViewById(R.id.ivimgShowProp2);
        ivimgShowProp3 = v.findViewById(R.id.ivimgShowProp3);
        etDetailsProp = v.findViewById(R.id.etDetailsProp);
        /*etPropertyId = v.findViewById(R.id.etPropertyId);*/
        spinnerElect = v.findViewById(R.id.spinnerElect);
        iv_imgElect1 = v.findViewById(R.id.iv_imgElect1);
        ivimgShowElect1 = v.findViewById(R.id.ivimgShowElect1);
        iv_imgElect2 = v.findViewById(R.id.iv_imgElect2);
        iv_imgElect3 = v.findViewById(R.id.iv_imgElect3);
        ivimgShowElect2 = v.findViewById(R.id.ivimgShowElect2);
        ivimgShowElect3 = v.findViewById(R.id.ivimgShowElect3);
        etDetailsElect = v.findViewById(R.id.etDetailsElect);
       /* spinnerWater = v.findViewById(R.id.spinnerWater);
        iv_imgWater1 = v.findViewById(R.id.iv_imgWater1);
        ivimgShowWater1 = v.findViewById(R.id.ivimgShowWater1);
        iv_imgWater2 = v.findViewById(R.id.iv_imgWater2);
        iv_imgWater3 = v.findViewById(R.id.iv_imgWater3);
        ivimgShowWater2 = v.findViewById(R.id.ivimgShowWater2);
        ivimgShowWater3 = v.findViewById(R.id.ivimgShowWater3);
        etDetailsWater = v.findViewById(R.id.etDetailsWater);
       */ spinnerDetails = v.findViewById(R.id.spinnerDetails);
        spinnerGuarrentee = v.findViewById(R.id.spinnerGuarrentee);
        spinnerNoticeOfAcquization = v.findViewById(R.id.spinnerNoticeOfAcquization);
        spinnerNoticeOfRoadWiden = v.findViewById(R.id.spinnerNoticeOfRoadWiden);
        spinnerHeritage_restrictions = v.findViewById(R.id.spinnerHeritage_restrictions);
        spinnerIsMortgaged = v.findViewById(R.id.spinnerIsMortgaged);
        rl_dispute = v.findViewById(R.id.rl_dispute);
        rl_details = v.findViewById(R.id.rl_details);
       /* rl_detailsProp = v.findViewById(R.id.rl_detailsProp);
        rl_detailsWater = v.findViewById(R.id.rl_detailsWater);
        rl_detailsElect = v.findViewById(R.id.rl_detailsElect);*/
        rl_detailsGuarrentee = v.findViewById(R.id.rl_detailsGuarrentee);
        rl_detailsNoticeOfAcquization = v.findViewById(R.id.rl_detailsNoticeOfAcquization);
        rl_detailsNoticeOfRoadWiden = v.findViewById(R.id.rl_detailsNoticeOfRoadWiden);
        rl_detailsHeritage_restrictions = v.findViewById(R.id.rl_detailsHeritage_restrictions);
        rl_detailsIsMortgaged = v.findViewById(R.id.rl_detailsIsMortgaged);
        etYearOfAcquistion = v.findViewById(R.id.etYearOfAcquistion);
        etLegalOwnerNames = v.findViewById(R.id.etLegalOwnerNames);
        etPropertyPurchaser = v.findViewById(R.id.etPropertyPurchaser);
        etPropertyAddress = v.findViewById(R.id.etPropertyAddress);
        etPresentResidence = v.findViewById(R.id.etPresentResidence);
        etInCasePossessed = v.findViewById(R.id.etInCasePossessed);
        etNoOfTenants = v.findViewById(R.id.etNoOfTenants);
        etLeasePeriod = v.findViewById(R.id.etLeasePeriod);
        etMonthlyRental = v.findViewById(R.id.etMonthlyRental);
        etAnyDisputeCame = v.findViewById(R.id.etAnyDisputeCame);
        etDetailsOfExisting = v.findViewById(R.id.etDetailsOfExisting);
        etDetailsOfGuarrentee = v.findViewById(R.id.etDetailsOfGuarrentee);
        etDetailsNoticeOfAcquization = v.findViewById(R.id.etDetailsNoticeOfAcquization);
        etDetailsNoticeOfRoadWiden = v.findViewById(R.id.etDetailsNoticeOfRoadWiden);
        etDetailsHeritage_restrictions = v.findViewById(R.id.etDetailsHeritage_restrictions);
        etDetailsIsMortgaged = v.findViewById(R.id.etDetailsIsMortgaged);
        etDisputeDetails = v.findViewById(R.id.etDisputeDetails);
        etDetails = v.findViewById(R.id.etDetails);
        etDetailsGuarrentee = v.findViewById(R.id.etDetailsGuarrentee);
        rgPropertyConstitution = v.findViewById(R.id.rgPropertyConstitution);
        rgPropertyPossessedBy = v.findViewById(R.id.rgPropertyPossessedBy);

        etPropertyAddress.setText(pref.get(Constants.assetAddress));

        etLegalOwnerNames.setText(pref.get(Constants.owner_name));
        etLegalOwnerNames.setSelection(etLegalOwnerNames.getText().toString().length());

        String detailsText = "Details<font color='red'>*</font>";
        tv_details.setText(Html.fromHtml(detailsText), TextView.BufferType.SPANNABLE);
        tv_dispute.setText(Html.fromHtml(detailsText), TextView.BufferType.SPANNABLE);


      /*  ((RadioButton) rgPropertyPossessedBy.getChildAt(2)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    llTenant.setVisibility(View.VISIBLE);

                    String rbText = "";
                    if (llG.getVisibility() == View.VISIBLE) {
                        rbText = "i. Any dispute came to knowledge with the tenants or any court case?<font color='red'>*</font>";
                    } else {
                        rbText = "h. Any dispute came to knowledge with the tenants or any court case?<font color='red'>*</font>";
                    }

                    tvAnyDispute.setText(Html.fromHtml(rbText), TextView.BufferType.SPANNABLE);

                } else {
                    llTenant.setVisibility(View.GONE);

                    if (llG.getVisibility() == View.VISIBLE) {
                        tvAnyDispute.setText(R.string.i_any_dispute_came_to_knowledge_with_the_tenants_or_any_court_case);
                    } else {
                        tvAnyDispute.setText(R.string.g_any_dispute_came_to_knowledge_with_the_tenants_or_any_court_case);
                    }


                }
            }
        });*/

        rgPropertyPossessedBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                //69 71
                int selectedIdProprtyCnstutn = rgPropertyPossessedBy.getCheckedRadioButtonId();
                View radioButtonProprtyCnstutn = v.findViewById(selectedIdProprtyCnstutn);
                int idx = rgPropertyPossessedBy.indexOfChild(radioButtonProprtyCnstutn);

                Log.v("Asfasfasxzvvd", String.valueOf(idx));

                RadioButton rb = (RadioButton) v.findViewById(checkedId);

                if (rb.isChecked()) {

                    if(idx ==2){
                        llG.setVisibility(View.VISIBLE);
                        llTenant.setVisibility(View.VISIBLE);
                        setNumber(2);

                    }else if (idx == 7) {
                        llG.setVisibility(View.GONE);
                        llTenant.setVisibility(View.GONE);
                       /* tvAnyDispute.setText(R.string.g_any_dispute_came_to_knowledge_with_the_tenants_or_any_court_case);

                        String rbText = "";

                        rbText = "h. Any dispute came to knowledge with the tenants or any court case?<font color='red'>*</font>";

                        tvAnyDispute.setText(Html.fromHtml(rbText), TextView.BufferType.SPANNABLE);
                        */
                       setNumber(1);
                    } else {
                        llG.setVisibility(View.VISIBLE);
                        llTenant.setVisibility(View.GONE);
                        setNumber(3);
                        /*tvAnyDispute.setText(R.string.i_any_dispute_came_to_knowledge_with_the_tenants_or_any_court_case);

                        String rbText = "";

                        rbText = "i. Any dispute came to knowledge with the tenants or any court case?<font color='red'>*</font>";

                        tvAnyDispute.setText(Html.fromHtml(rbText), TextView.BufferType.SPANNABLE);
*/
                    }

                    if (idx == 0 || idx == 2) {
                        String rbText = "g. In case possessed by " + rb.getText() + " since when?<font color='red'>*</font>";
                        tvQuesG.setText(Html.fromHtml(rbText), TextView.BufferType.SPANNABLE);
                    } else {
                        String rbText = "g. " + rb.getText() + " since when?<font color='red'>*</font>";
                        tvQuesG.setText(Html.fromHtml(rbText), TextView.BufferType.SPANNABLE);
                    }

                }

            }
        });


    }
    public void setEditiblity(){
        etLegalOwnerNames.setEnabled(false);
        etPropertyPurchaser.setEnabled(false);
        etPropertyAddress.setEnabled(false);
        etPresentResidence.setEnabled(false);
        etInCasePossessed.setEnabled(false);
        etNoOfTenants.setEnabled(false);
        etLeasePeriod.setEnabled(false);
        etMonthlyRental.setEnabled(false);
        etAnyDisputeCame.setEnabled(false);
        etDetailsOfExisting.setEnabled(false);
        etDisputeDetails.setEnabled(false);
        etDetails.setEnabled(false);
        etDetailsOfGuarrentee.setEnabled(false);
        etDetailsHeritage_restrictions.setEnabled(false);
        etDetailsIsMortgaged.setEnabled(false);
        etDetailsGuarrentee.setEnabled(false);
        etDetailsNoticeOfRoadWiden.setEnabled(false);
        etDetailsNoticeOfAcquization.setEnabled(false);
        etDetailsProp.setEnabled(false);
        /*etPropertyId.setEnabled(false);*/
       /* etDetailsWater.setEnabled(false);
        etDetailsElect.setEnabled(false);*/
        etYearOfAcquistion.setEnabled(false);
        for(int i=0;i<rgPropertyConstitution.getChildCount();i++)
            rgPropertyConstitution.getChildAt(i).setClickable(false);
        for(int i=0;i<rgPropertyPossessedBy.getChildCount();i++)
            rgPropertyPossessedBy.getChildAt(i).setClickable(false);
        /*spinnerProperty.setEnabled(false);
        spinnerWater.setEnabled(false);
        spinnerElect.setEnabled(false);*/
        spinnerTime.setEnabled(false);
        spinnerDispute.setEnabled(false);
        spinnerDetails.setEnabled(false);
        spinnerGuarrentee.setEnabled(false);
        spinnerNoticeOfAcquization.setEnabled(false);
        spinnerNoticeOfRoadWiden.setEnabled(false);
        spinnerHeritage_restrictions.setEnabled(false);
        spinnerIsMortgaged.setEnabled(false);
       /* iv_imgProp1.setEnabled(false);
        ivimgShowProp1.setEnabled(false);
        iv_imgProp2.setEnabled(false);
        iv_imgProp3.setEnabled(false);
        ivimgShowProp2.setEnabled(false);
        ivimgShowProp3.setEnabled(false);
        iv_imgWater1.setEnabled(false);
        ivimgShowWater1.setEnabled(false);
        iv_imgWater2.setEnabled(false);
        iv_imgWater3.setEnabled(false);
        ivimgShowWater2.setEnabled(false);
        ivimgShowWater3.setEnabled(false);*/
        /*iv_imgElect1.setEnabled(false);
        ivimgShowElect1.setEnabled(false);
        iv_imgElect2.setEnabled(false);
        iv_imgElect3.setEnabled(false);
        ivimgShowElect2.setEnabled(false);
        ivimgShowElect3.setEnabled(false);*/
      /*  btnimgProp1.setEnabled(false);
        btnimgProp2.setEnabled(false);*/
        /*btnimgWater1.setEnabled(false);
        btnimgWater2.setEnabled(false);
        btnimgElect1.setEnabled(false);
        btnimgElect2.setEnabled(false);*/







    }

    public void setNumber(int i) {
        if (i == 1) {
            String rbText = "g. Any dispute came to knowledge with the tenants or any court case?<font color='red'>*</font>";
            tvAnyDispute.setText(Html.fromHtml(rbText), TextView.BufferType.SPANNABLE);
            String rbText1 = "h. Year of Acquisition/ Purchase";
            tvAnyDispute1.setText(Html.fromHtml(rbText1), TextView.BufferType.SPANNABLE);
            String rbText2 = "i. Comment on existing mortgages/ charges/ encumbrances on the property, if any<font color='red'>*</font>";
            tv_DetailsOfExisting.setText(Html.fromHtml(rbText2), TextView.BufferType.SPANNABLE);
            String rbText3 = "j. Comment on whether the owners of the property have issued any guarantee(personal or corporate) as the case may be<font color='red'>*</font>";
            tv_DetailsOfGuarrentee.setText(Html.fromHtml(rbText3), TextView.BufferType.SPANNABLE);
            String rbText4 = "k. Notice of acquisition if any and area under acquisition<font color='red'>*</font>";
            tv_NoticeOfAcquization.setText(Html.fromHtml(rbText4), TextView.BufferType.SPANNABLE);
            String rbText5 = "l. Notification of road widening if any and area under acquisition<font color='red'>*</font>";
            tvNoticeOfRoadWiden.setText(Html.fromHtml(rbText5), TextView.BufferType.SPANNABLE);
            String rbText6 = "m. Heritage restrictions, if any<font color='red'>*</font>";
            tvHeritage_restrictions.setText(Html.fromHtml(rbText6), TextView.BufferType.SPANNABLE);
            String rbText7 = "n. Whether entire piece of land is mortgaged or to be mortgaged<font color='red'>*</font>";
            tv_IsMortgaged.setText(Html.fromHtml(rbText7), TextView.BufferType.SPANNABLE);
        }
        else if(i ==2){
            String rbText = "i. Any dispute came to knowledge with the tenants or any court case?<font color='red'>*</font>";
            tvAnyDispute.setText(Html.fromHtml(rbText), TextView.BufferType.SPANNABLE);
            String rbText1 = "j. Year of Acquisition/ Purchase";
            tvAnyDispute1.setText(Html.fromHtml(rbText1), TextView.BufferType.SPANNABLE);
            String rbText2 = "k. Comment on existing mortgages/ charges/ encumbrances on the property, if any<font color='red'>*</font>";
            tv_DetailsOfExisting.setText(Html.fromHtml(rbText2), TextView.BufferType.SPANNABLE);
            String rbText3 = "l. Comment on whether the owners of the property have issued any guarantee(personal or corporate) as the case may be<font color='red'>*</font>";
            tv_DetailsOfGuarrentee.setText(Html.fromHtml(rbText3), TextView.BufferType.SPANNABLE);
            String rbText4 = "m. Notice of acquisition if any and area under acquisition<font color='red'>*</font>";
            tv_NoticeOfAcquization.setText(Html.fromHtml(rbText4), TextView.BufferType.SPANNABLE);
            String rbText5 = "n. Notification of road widening if any and area under acquisition<font color='red'>*</font>";
            tvNoticeOfRoadWiden.setText(Html.fromHtml(rbText5), TextView.BufferType.SPANNABLE);
            String rbText6 = "o. Heritage restrictions, if any<font color='red'>*</font>";
            tvHeritage_restrictions.setText(Html.fromHtml(rbText6), TextView.BufferType.SPANNABLE);
            String rbText7 = "p. Whether entire piece of land is mortgaged or to be mortgaged<font color='red'>*</font>";
            tv_IsMortgaged.setText(Html.fromHtml(rbText7), TextView.BufferType.SPANNABLE);
        }
        else {
            String rbText = "h. Any dispute came to knowledge with the tenants or any court case?<font color='red'>*</font>";
            tvAnyDispute.setText(Html.fromHtml(rbText), TextView.BufferType.SPANNABLE);
            String rbText1 = "i. Year of Acquisition/ Purchase";
            tvAnyDispute1.setText(Html.fromHtml(rbText1), TextView.BufferType.SPANNABLE);
            String rbText2 = "j. Comment on existing mortgages/ charges/ encumbrances on the property, if any<font color='red'>*</font>";
            tv_DetailsOfExisting.setText(Html.fromHtml(rbText2), TextView.BufferType.SPANNABLE);
            String rbText3 = "k. Comment on whether the owners of the property have issued any guarantee(personal or corporate) as the case may be<font color='red'>*</font>";
            tv_DetailsOfGuarrentee.setText(Html.fromHtml(rbText3), TextView.BufferType.SPANNABLE);
            String rbText4 = "l. Notice of acquisition if any and area under acquisition<font color='red'>*</font>";
            tv_NoticeOfAcquization.setText(Html.fromHtml(rbText4), TextView.BufferType.SPANNABLE);
            String rbText5 = "m. Notification of road widening if any and area under acquisition<font color='red'>*</font>";
            tvNoticeOfRoadWiden.setText(Html.fromHtml(rbText5), TextView.BufferType.SPANNABLE);
            String rbText6 = "n. Heritage restrictions, if any<font color='red'>*</font>";
            tvHeritage_restrictions.setText(Html.fromHtml(rbText6), TextView.BufferType.SPANNABLE);
            String rbText7 = "o. Whether entire piece of land is mortgaged or to be mortgaged<font color='red'>*</font>";
            tv_IsMortgaged.setText(Html.fromHtml(rbText7), TextView.BufferType.SPANNABLE);

        }
    }









    public void setListener() {
        //  back.setOnClickListener(this);
        next.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
      /*  btnimgProp1.setOnClickListener(this);
        btnimgProp2.setOnClickListener(this);*/
       /* iv_imgProp1.setOnClickListener(this);
        iv_imgProp2.setOnClickListener(this);
        iv_imgProp3.setOnClickListener(this);
        btnimgWater1.setOnClickListener(this);
        btnimgWater2.setOnClickListener(this);
        iv_imgWater3.setOnClickListener(this);
        iv_imgWater1.setOnClickListener(this);
        iv_imgWater2.setOnClickListener(this);
        btnimgElect1.setOnClickListener(this);
        btnimgElect2.setOnClickListener(this);
        iv_imgElect3.setOnClickListener(this);
        iv_imgElect1.setOnClickListener(this);
        iv_imgElect2.setOnClickListener(this);*/

        //  dots.setOnClickListener(this);
      /*  spinnerProperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                spin_prop=i;
                if(first) {
                    switch (i) {
                        case 1:
                            llimgProp1.setVisibility(View.VISIBLE);
                            btnimgProp1.setVisibility(View.VISIBLE);
                            rl_detailsProp.setVisibility(View.GONE);

                            break;
                        case 2:
                            llimgProp1.setVisibility(View.GONE);
                            llimgProp2.setVisibility(View.GONE);
                            llimgProp3.setVisibility(View.GONE);
                            rl_detailsProp.setVisibility(View.VISIBLE);

                            break;
                        case 3:
                            llimgProp1.setVisibility(View.GONE);
                            llimgProp2.setVisibility(View.GONE);
                            rl_detailsProp.setVisibility(View.GONE);
                            llimgProp3.setVisibility(View.GONE);
                            break;
                        default:
                            llimgProp1.setVisibility(View.GONE);
                            llimgProp2.setVisibility(View.GONE);
                            llimgProp3.setVisibility(View.GONE);
                            rl_detailsProp.setVisibility(View.GONE);

                    }
                }
                else {
                    first = true;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerWater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                spin_water=i;
                if(firstwater) {
                    switch (i) {
                        case 1:
                            llimgWater1.setVisibility(View.VISIBLE);
                            btnimgWater2.setVisibility(View.VISIBLE);
                            rl_detailsWater.setVisibility(View.GONE);

                            break;
                        case 2:
                            llimgWater1.setVisibility(View.GONE);
                            llimgWater2.setVisibility(View.GONE);
                            llimgWater3.setVisibility(View.GONE);
                            rl_detailsWater.setVisibility(View.VISIBLE);

                            break;
                        case 3:
                            llimgWater1.setVisibility(View.GONE);
                            llimgWater2.setVisibility(View.GONE);
                            rl_detailsWater.setVisibility(View.GONE);
                            llimgWater3.setVisibility(View.GONE);
                            break;
                        default:
                            llimgWater1.setVisibility(View.GONE);
                            llimgWater2.setVisibility(View.GONE);
                            llimgWater3.setVisibility(View.GONE);
                            rl_detailsWater.setVisibility(View.GONE);

                    }
                }
                else {
                    firstwater = true;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerElect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                spin_Elect=i;
                if(firstElect) {
                    switch (i) {
                        case 1:
                            llimgElect1.setVisibility(View.VISIBLE);
                            btnimgElect2.setVisibility(View.VISIBLE);
                            rl_detailsElect.setVisibility(View.GONE);

                            break;
                        case 2:
                            llimgElect1.setVisibility(View.GONE);
                            llimgElect2.setVisibility(View.GONE);
                            llimgElect3.setVisibility(View.GONE);
                            rl_detailsElect.setVisibility(View.VISIBLE);

                            break;
                        case 3:
                            llimgElect1.setVisibility(View.GONE);
                            llimgElect2.setVisibility(View.GONE);
                            rl_detailsElect.setVisibility(View.GONE);
                            llimgElect3.setVisibility(View.GONE);
                            break;
                        default:
                            llimgElect1.setVisibility(View.GONE);
                            llimgElect2.setVisibility(View.GONE);
                            llimgElect3.setVisibility(View.GONE);
                            rl_detailsElect.setVisibility(View.GONE);

                    }
                }
                else {
                    firstElect = true;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
          /*  case R.id.rl_back:
                onBackPressed();
                break;
*/
            case R.id.tvPrevious:
              /*  intent = new Intent(GeneralForm2.this, GeneralForm1.class);
                startActivity(intent);*/

                //((Dashboard)mActivity).displayView(6);
                mActivity.onBackPressed();
                //    onBackPressed();
                break;

           /* case R.id.btnimgProp1:
                llimgProp2.setVisibility(View.VISIBLE);
                btnimgProp2.setVisibility(View.VISIBLE);
                btnimgProp1.setVisibility(View.GONE);
                break;

            case R.id.btnimgProp2:
                llimgProp3.setVisibility(View.VISIBLE);
                btnimgProp2.setVisibility(View.GONE);
                break;*/
           /* case R.id.btnimgWater1:
                llimgWater2.setVisibility(View.VISIBLE);
                btnimgWater2.setVisibility(View.VISIBLE);
                btnimgWater1.setVisibility(View.GONE);
                break;

            case R.id.btnimgWater2:
                llimgWater3.setVisibility(View.VISIBLE);
                btnimgWater2.setVisibility(View.GONE);
                break;
            case R.id.btnimgElect1:
                llimgElect2.setVisibility(View.VISIBLE);
                btnimgElect2.setVisibility(View.VISIBLE);
                btnimgElect1.setVisibility(View.GONE);
                break;

            case R.id.btnimgElect2:
                llimgElect3.setVisibility(View.VISIBLE);
                btnimgElect2.setVisibility(View.GONE);
                break;
*/
            case R.id.next:
                if(edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(21);
                pref.set("typeSurveyChecker", "false");
                pref.commit();
                break;

           /* case R.id.iv_imgProp1:
                imgSource="imgProp1";
                showCameraGalleryDialog();
                break;
            case R.id.iv_imgProp2:
                imgSource="imgProp2";
                showCameraGalleryDialog();
                break;
            case R.id.iv_imgProp3:
                imgSource="imgProp3";
                showCameraGalleryDialog();
                break;
            case R.id.iv_imgWater1:
                imgSource="imgWater1";
                showCameraGalleryDialog();
                break;
            case R.id.iv_imgWater2:
                imgSource="imgWater2";
                showCameraGalleryDialog();
                break;
            case R.id.iv_imgWater3:
                imgSource="imgWater3";
                showCameraGalleryDialog();
                break;
            case R.id.iv_imgElect1:
                imgSource="imgElect1";
                showCameraGalleryDialog();
                break;
            case R.id.iv_imgElect2:
                imgSource="imgElect2";
                showCameraGalleryDialog();
                break;
            case R.id.iv_imgElect3:
                imgSource="imgElect3";
                showCameraGalleryDialog();
                break;*/
         /*   case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;*/
        }
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




























    private void validation() {
        if (etLegalOwnerNames.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Legal Owner Name/s", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLegalOwnerNames.requestFocus();
        }/* else if (etPropertyPurchaser.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Property Purchaser Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPropertyPurchaser.requestFocus();
        }*/ else if (etPropertyAddress.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Property Address under Valuation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPropertyAddress.requestFocus();
        }
        else if (etPresentResidence.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Present Residence Address of the Owner", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPresentResidence.requestFocus();
        } else if (rgPropertyConstitution.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Property Constitution", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvPropertyCons);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgPropertyPossessedBy.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Property possessed by at the time of survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvPropertyPoss);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerTime.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerTime);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (etInCasePossessed.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter In case possessed by tenants or sealed then since when?", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etInCasePossessed.requestFocus();
        } else if (llTenant.getVisibility() == View.VISIBLE && etNoOfTenants.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter No. of Tenants", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNoOfTenants.requestFocus();
        } else if (llTenant.getVisibility() == View.VISIBLE && etLeasePeriod.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Lease Period", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLeasePeriod.requestFocus();
        } else if (llTenant.getVisibility() == View.VISIBLE && etMonthlyRental.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Monthly Rental", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMonthlyRental.requestFocus();
        } else if (spinnerDispute.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerDispute);
            targetView.getParent().requestChildFocus(targetView, targetView);
        } else if (rl_dispute.getVisibility() == View.VISIBLE && etDisputeDetails.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Dispute Details", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDisputeDetails.requestFocus();
        } else if (rl_details.getVisibility() == View.VISIBLE && etDetails.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Details of Existing Mortages", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetails.requestFocus();
        } else if (rl_detailsGuarrentee.getVisibility() == View.VISIBLE && etDetailsGuarrentee.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Details of Existing Mortages", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetailsGuarrentee.requestFocus();
        } else if (rl_detailsNoticeOfAcquization.getVisibility() == View.VISIBLE && etDetailsNoticeOfAcquization.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Details of Existing Mortages", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetailsNoticeOfAcquization.requestFocus();
        } else if (rl_detailsNoticeOfRoadWiden.getVisibility() == View.VISIBLE && etDetailsNoticeOfRoadWiden.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Details of Existing Mortages", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetailsNoticeOfRoadWiden.requestFocus();
        } else if (rl_detailsHeritage_restrictions.getVisibility() == View.VISIBLE && etDetailsHeritage_restrictions.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Details of Existing Mortages", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetailsHeritage_restrictions.requestFocus();
        }else if (rl_detailsIsMortgaged.getVisibility() == View.VISIBLE && etDetailsIsMortgaged.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Details of Existing Mortages", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetailsIsMortgaged.requestFocus();
        } else if (spinnerDetails.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerDetails);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerGuarrentee.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerGuarrentee);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerNoticeOfAcquization.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerNoticeOfAcquization);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerNoticeOfRoadWiden.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerNoticeOfRoadWiden);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (spinnerHeritage_restrictions.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerHeritage_restrictions);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }/*else if (llimgProp1.getVisibility() == View.VISIBLE && tvimgProp1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach property receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgProp1.requestFocus();
        } else if (llimgProp2.getVisibility() == View.VISIBLE && tvimgProp2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach property receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgProp2.requestFocus();
        }else if (llimgProp3.getVisibility() == View.VISIBLE && tvimgProp3.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach property receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgProp3.requestFocus();
        }*/
        /*else if (llimgWater1.getVisibility() == View.VISIBLE && tvimgWater1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach Water receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgWater1.requestFocus();
        } else if (llimgWater2.getVisibility() == View.VISIBLE && tvimgWater2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach Water receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgWater2.requestFocus();
        }else if (llimgWater3.getVisibility() == View.VISIBLE && tvimgWater3.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach Water receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgWater3.requestFocus();
        }else if (llimgElect1.getVisibility() == View.VISIBLE && tvimgElect1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach Electricity receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgElect1.requestFocus();
        } else if (llimgElect2.getVisibility() == View.VISIBLE && tvimgElect2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach Electricity receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgElect2.requestFocus();
        }else if (llimgElect3.getVisibility() == View.VISIBLE && tvimgElect3.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Attach Electricity receipt/ bill", Snackbar.LENGTH_SHORT);
            snackbar.show();
            tvimgElect3.requestFocus();
        }else if (rl_detailsElect.getVisibility() == View.VISIBLE && etDetailsElect.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please provide Electricity details", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetailsElect.requestFocus();
        }else if (rl_detailsWater.getVisibility() == View.VISIBLE && etDetailsWater.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please provide Water details", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetailsWater.requestFocus();
        }else if (rl_detailsProp.getVisibility() == View.VISIBLE && etDetailsProp.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please provide Property details", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDetailsProp.requestFocus();
        }*/
        else if (spinnerIsMortgaged.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerIsMortgaged);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }
       /* else if (etPropertyId.getText().toString().equalsIgnoreCase("") && llmunsciple.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter property Id", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.etPropertyId);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }*/
       /* else if (spin_prop <= 0 && spinnerProperty.getVisibility() == View.VISIBLE && llmunsciple.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an option for property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerProperty);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }*/
      /*  else if (spin_water <= 0 && spinnerWater.getVisibility() == View.VISIBLE&& llmunsciple.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an option for Water", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerWater);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }else if (spin_Elect <= 0 && spinnerElect.getVisibility() == View.VISIBLE&& llmunsciple.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an option for Electricity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.spinnerElect);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }*/else {
            putIntoHm();

            ((Dashboard) mActivity).displayView(21);

            return;
        }
    }

    public void putIntoHm() {
        String possedtalent = String.valueOf(spinnerTime.getSelectedItemPosition());
        String dispute = String.valueOf(spinnerDispute.getSelectedItemPosition());
        String existing = String.valueOf(spinnerDetails.getSelectedItemPosition());
        String guarrentee = String.valueOf(spinnerGuarrentee.getSelectedItemPosition());
        hm.put(Constants.pageVisited,"vacantform2");
        hm.put("legalOwnerNames", etLegalOwnerNames.getText().toString());
        hm.put("propertyPurchaserName", etPropertyPurchaser.getText().toString());
        hm.put("propertyAddressUnder", etPropertyAddress.getText().toString());
        hm.put("propertyResidence", etPresentResidence.getText().toString());
        hm.put("inCasePossessedTenants", possedtalent);
        hm.put("noOfTenants", etNoOfTenants.getText().toString());
        hm.put("leasePeriod", etLeasePeriod.getText().toString());
        hm.put("monthlyRental", etMonthlyRental.getText().toString());
        hm.put("anyDisputeTenants", dispute);
        hm.put("detailsOfExistingMortgages", existing);
        hm.put("disputeDetails", etDisputeDetails.getText().toString());
        hm.put("DetailsNoticeOfAcquization", etDetailsNoticeOfAcquization.getText().toString());
        hm.put("DetailsNoticeOfRoadWiden", etDetailsNoticeOfRoadWiden.getText().toString());
        hm.put("DetailsHeritage_restrictions", etDetailsHeritage_restrictions.getText().toString());
        hm.put("DetailsIsMortgaged", etDetailsIsMortgaged.getText().toString());
        hm.put("detailsOfExisting", etDetails.getText().toString());
        hm.put("detailsOfGuarrentee", etDetailsGuarrentee.getText().toString());
        hm.put("YearOfAcquistion", etYearOfAcquistion.getText().toString());
        hm.put("details_pos", Integer.toString(details_pos));
        hm.put("guarrentee_pos", Integer.toString(guarrentee_pos));
        hm.put("dispute_pos", Integer.toString(dispute_pos));
        hm.put("time_pos", Integer.toString(time_pos));
        hm.put("noticAq_pos", Integer.toString(noticAq_pos));
        hm.put("roadWiden_pos", Integer.toString(roadWiden_pos));
        hm.put("mortage_pos", Integer.toString(mortage_pos));
        hm.put("heritage_pos", Integer.toString(heritage_pos));
       /* hm.put("imgProperty1", tvimgProp1.getText().toString());
        hm.put("imgProperty2", tvimgProp2.getText().toString());
        hm.put("imgProperty3", tvimgProp3.getText().toString());
        hm.put("Propertyimage1", encodedStringPropimg1);
        hm.put("Propertyimage2", encodedStringPropimg2);
        hm.put("Propertyimage3", encodedStringPropimg3);
        hm.put("spin_prop", Integer.toString(spin_prop));
        hm.put("detailsProp", etDetailsProp.getText().toString());
        *//*hm.put("propId", etPropertyId.getText().toString());*//*

        hm.put("imgWater1", tvimgWater1.getText().toString());
        hm.put("imgWater2", tvimgWater2.getText().toString());
        hm.put("imgWater3", tvimgWater3.getText().toString());
        hm.put("Waterimage1", encodedStringWaterimg1);
        hm.put("Waterimage2", encodedStringWaterimg2);
        hm.put("Waterimage3", encodedStringWaterimg3);
        hm.put("spin_water", Integer.toString(spin_water));
        hm.put("detailsWater", etDetailsWater.getText().toString());
        hm.put("imgElect1", tvimgElect1.getText().toString());
        hm.put("imgElect2", tvimgElect2.getText().toString());
        hm.put("imgElect3", tvimgElect3.getText().toString());
        hm.put("Electimage1", encodedStringElectimg1);
        hm.put("Electimage2", encodedStringElectimg2);
        hm.put("Electimage3", encodedStringElectimg3);
        hm.put("spin_Elect", Integer.toString(spin_Elect));
        hm.put("detailsElect", etDetailsElect.getText().toString());

*/



        int selectedIdProprtyCnstutn = rgPropertyConstitution.getCheckedRadioButtonId();
        View radioButtonProprtyCnstutn = v.findViewById(selectedIdProprtyCnstutn);
        int idx = rgPropertyConstitution.indexOfChild(radioButtonProprtyCnstutn);
        RadioButton r = (RadioButton) rgPropertyConstitution.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonPropertyConstitution", selectedText);

        int selectedIdCharacteristic = rgPropertyPossessedBy.getCheckedRadioButtonId();
        View radioButtonCharacteristic = v.findViewById(selectedIdCharacteristic);
        int idx2 = rgPropertyPossessedBy.indexOfChild(radioButtonCharacteristic);
        RadioButton r2 = (RadioButton) rgPropertyPossessedBy.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonPropertyPossessedByAt", selectedText2);

        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        ContentValues contentValues = new ContentValues();

        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column2.toString(), jsonArrayData.toString());

        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "2");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getTableOne("column2", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column2"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if(object.has(Constants.pageVisited)){
                    pageVisited=object.getString(Constants.pageVisited);
                }
                etLegalOwnerNames.setText(object.getString("legalOwnerNames"));
                etPropertyPurchaser.setText(object.getString("propertyPurchaserName"));
                etPropertyAddress.setText(object.getString("propertyAddressUnder"));
                etPresentResidence.setText(object.getString("propertyResidence"));
                etInCasePossessed.setText(object.getString("inCasePossessedTenants"));
                etNoOfTenants.setText(object.getString("noOfTenants"));
                etLeasePeriod.setText(object.getString("leasePeriod"));
                etMonthlyRental.setText(object.getString("monthlyRental"));
                etAnyDisputeCame.setText(object.getString("anyDisputeTenants"));
                etDetailsOfExisting.setText(object.getString("detailsOfExistingMortgages"));
                if (object.has("detailsOfGuarrentee")) {
                    etDetailsGuarrentee.setText(object.getString("detailsOfGuarrentee"));
                }
                if (object.has("DetailsNoticeOfAcquization")) {
                    etDetailsNoticeOfAcquization.setText(object.getString("DetailsNoticeOfAcquization"));
                }
                if (object.has("DetailsNoticeOfRoadWiden")) {
                    etDetailsNoticeOfRoadWiden.setText(object.getString("DetailsNoticeOfRoadWiden"));
                }
                if (object.has("DetailsHeritage_restrictions")) {
                    etDetailsHeritage_restrictions.setText(object.getString("DetailsHeritage_restrictions"));
                }if (object.has("DetailsIsMortgaged")) {
                    etDetailsIsMortgaged.setText(object.getString("DetailsIsMortgaged"));
                }
                if (object.has("details_pos")) {
                    details_pos = Integer.parseInt(object.getString("details_pos"));
                }
                if (object.has("guarrentee_pos")) {
                    guarrentee_pos = Integer.parseInt(object.getString("guarrentee_pos"));
                }
                if (object.has("dispute_pos")) {
                    dispute_pos = Integer.parseInt(object.getString("dispute_pos"));
                }
                if (object.has("time_pos")) {
                    time_pos = Integer.parseInt(object.getString("time_pos"));
                }
                if (object.has("noticAq_pos")) {
                    noticAq_pos = Integer.parseInt(object.getString("noticAq_pos"));
                }
                if (object.has("roadWiden_pos")) {
                    roadWiden_pos = Integer.parseInt(object.getString("roadWiden_pos"));
                }
                if (object.has("mortage_pos")) {
                    mortage_pos = Integer.parseInt(object.getString("mortage_pos"));
                }
                if (object.has("heritage_pos")) {
                    heritage_pos = Integer.parseInt(object.getString("heritage_pos"));
                }
                etDisputeDetails.setText(object.getString("disputeDetails"));
                etDetails.setText(object.getString("detailsOfExisting"));
                if (object.has("YearOfAcquistion")) {
                    etYearOfAcquistion.setText(object.getString("YearOfAcquistion"));
                }
/*
                if (object.has("spin_prop")){
                    spin_prop=Integer.parseInt(object.getString("spin_prop"));
                    spinnerProperty.setSelection(spin_prop);
                    if(spin_prop==1){
                        llimgProp1.setVisibility(View.VISIBLE);
                        if(object.has("imgProperty1")){
                            tvimgProp1.setText(object.getString("imgProperty1"));
                            encodedStringPropimg1 = object.getString("Propertyimage1");
                            if(!encodedStringPropimg1.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringPropimg1, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                ivimgShowProp1.setImageBitmap(decodedImage);
                                ivimgShowProp1.setVisibility(View.VISIBLE);
                            }
                        }
                        if(object.has("imgProperty2")){
                            tvimgProp2.setText(object.getString("imgProperty2"));
                            encodedStringPropimg2 = object.getString("Propertyimage2");
                            if(!encodedStringPropimg2.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringPropimg2, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                btnimgProp1.setVisibility(View.GONE);
                                llimgProp2.setVisibility(View.VISIBLE);
                                ivimgShowProp2.setImageBitmap(decodedImage);
                                ivimgShowProp2.setVisibility(View.VISIBLE);
                            }
                        }
                        if(object.has("imgProperty3")){
                            tvimgProp3.setText(object.getString("imgProperty3"));
                            encodedStringPropimg3 = object.getString("Propertyimage3");
                            if(!encodedStringPropimg3.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringPropimg3, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                btnimgProp2.setVisibility(View.GONE);
                                llimgProp3.setVisibility(View.VISIBLE);
                                ivimgShowProp3.setImageBitmap(decodedImage);
                                ivimgShowProp3.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    if(spin_prop==2){
                        if(object.has("detailsProp")){
                            rl_detailsProp.setVisibility(View.VISIBLE);
                            etDetailsProp.setText(object.getString("detailsProp"));
                        }
                    }
                }
*/
                /*if(object.has("propId")){
                    etPropertyId.setText(object.getString("propId"));
                }*/


/*

                if (object.has("spin_water")){
                    spin_water=Integer.parseInt(object.getString("spin_water"));
                    spinnerWater.setSelection(spin_water);
                    if(spin_water==1){
                        llimgWater1.setVisibility(View.VISIBLE);
                        if(object.has("imgWater1")){
                            tvimgWater1.setText(object.getString("imgWater1"));
                            encodedStringWaterimg1 = object.getString("Waterimage1");
                            if(!encodedStringWaterimg1.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringWaterimg1, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                ivimgShowWater1.setImageBitmap(decodedImage);
                                ivimgShowWater1.setVisibility(View.VISIBLE);
                            }
                        }
                        if(object.has("imgWater2")){
                            tvimgWater2.setText(object.getString("imgWater2"));
                            encodedStringWaterimg2 = object.getString("Waterimage2");
                            if(!encodedStringWaterimg2.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringWaterimg2, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                btnimgWater1.setVisibility(View.GONE);
                                llimgWater2.setVisibility(View.VISIBLE);
                                ivimgShowWater2.setImageBitmap(decodedImage);
                                ivimgShowWater2.setVisibility(View.VISIBLE);
                            }
                        }
                        if(object.has("imgWater3")){
                            tvimgWater3.setText(object.getString("imgWater3"));
                            encodedStringWaterimg3 = object.getString("Waterimage3");
                            if(!encodedStringWaterimg3.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringWaterimg3, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                btnimgWater2.setVisibility(View.GONE);
                                llimgWater3.setVisibility(View.VISIBLE);
                                ivimgShowWater3.setImageBitmap(decodedImage);
                                ivimgShowWater3.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    if(spin_water==2){
                        if(object.has("detailsWater")){
                            rl_detailsWater.setVisibility(View.VISIBLE);
                            etDetailsWater.setText(object.getString("detailsWater"));
                        }
                    }
                }
                if (object.has("spin_Elect")){
                    spin_Elect=Integer.parseInt(object.getString("spin_Elect"));
                    spinnerElect.setSelection(spin_Elect);
                    if(spin_Elect==1){
                        llimgElect1.setVisibility(View.VISIBLE);
                        if(object.has("imgElect1")){
                            tvimgElect1.setText(object.getString("imgElect1"));
                            encodedStringElectimg1 = object.getString("Electimage1");
                            if(!encodedStringElectimg1.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringElectimg1, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                ivimgShowElect1.setImageBitmap(decodedImage);
                                ivimgShowElect1.setVisibility(View.VISIBLE);
                            }
                        }
                        if(object.has("imgElect2")){
                            tvimgElect2.setText(object.getString("imgElect2"));
                            encodedStringElectimg2 = object.getString("Electimage2");
                            if(!encodedStringElectimg2.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringElectimg2, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                btnimgElect1.setVisibility(View.GONE);
                                llimgElect2.setVisibility(View.VISIBLE);
                                ivimgShowElect2.setImageBitmap(decodedImage);
                                ivimgShowElect2.setVisibility(View.VISIBLE);
                            }
                        }
                        if(object.has("imgElect3")){
                            tvimgElect3.setText(object.getString("imgElect3"));
                            encodedStringElectimg3 = object.getString("Electimage3");
                            if(!encodedStringElectimg3.equalsIgnoreCase("")) {
                                byte[] byteFormat = Base64.decode(encodedStringElectimg3, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                                btnimgElect2.setVisibility(View.GONE);
                                llimgElect3.setVisibility(View.VISIBLE);
                                ivimgShowElect3.setImageBitmap(decodedImage);
                                ivimgShowElect3.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    if(spin_Elect==2){
                        if(object.has("detailsElect")){
                            rl_detailsElect.setVisibility(View.VISIBLE);
                            etDetailsElect.setText(object.getString("detailsElect"));
                        }
                    }
                }
*/


//                hm.put("YearOfAcquistion",etYearOfAcquistion.getText().toString());


                if (object.getString("radioButtonPropertyConstitution").equals("Free Hold")) {
                    ((RadioButton) rgPropertyConstitution.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonPropertyConstitution").equals("Perpetual Lease Hold")) {
                    ((RadioButton) rgPropertyConstitution.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonPropertyConstitution").equals("Short Term Lease Deed")) {
                    ((RadioButton) rgPropertyConstitution.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonPropertyConstitution").equals("Private Lease")) {
                    ((RadioButton) rgPropertyConstitution.getChildAt(3)).setChecked(true);
                }

                if (object.getString("radioButtonPropertyPossessedByAt").equals("Owner")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Vacant")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Tenants")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Under Construction")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Property was locked")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(4)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Bank sealed")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(5)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Court sealed")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(6)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Can't say since full / internal survey couldn't be done")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(7)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Lessee")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(8)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Sub Lessee")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(9)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Illegally Occupied")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(10)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Partially occupied by owners and partially by tenants")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(11)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Builder")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(12)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Developer")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(13)).setChecked(true);
                } else if (object.getString("radioButtonPropertyPossessedByAt").equals("Acquired by Govt. Authority")) {
                    ((RadioButton) rgPropertyPossessedBy.getChildAt(14)).setChecked(true);
                } else {
                    System.out.println("invalid Choice");
                }
                Log.v("Mychoice", object.getString("radioButtonPropertyPossessedByAt"));

//                if (InitiateSurveyForm.surveyTypeCheck == 1){
//
//                    etNoOfTenants.setText(R.string.noInformationAvailable);
//                    etLeasePeriod.setText(R.string.noInformationAvailable);
//                    etMonthlyRental.setText(R.string.noInformationAvailable);
//
//                    spinnerTime.setSelection(year.length-1);
//                    spinnerDispute.setSelection(3);
//                    spinnerDetails.setSelection(3);
//                    spinnerGuarrentee.setSelection(3);
//
//                }else {
//
////                    spinnerTime.setSelection(Integer.parseInt(object.getString("inCasePossessedTenants")));
////                    spinnerDispute.setSelection(Integer.parseInt(object.getString("anyDisputeTenants")));
////                    spinnerDetails.setSelection(Integer.parseInt(object.getString("detailsOfExistingMortgages")));
//
//                    etNoOfTenants.setText("");
//                    etLeasePeriod.setText("");
//                    etMonthlyRental.setText("");
//
//                    spinnerTime.setSelection(0);
//                    spinnerDispute.setSelection(0);
//                    spinnerDetails.setSelection(0);
//                    spinnerGuarrentee.setSelection(0);
//
//                }

            }

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void setPictures(Bitmap b, String setPic, String base64) {
       /* if(imgSource.equalsIgnoreCase("imgProp1")) {
            tvimgProp1.setText(filename);
            ivimgShowProp1.setImageBitmap(b);
            ivimgShowProp1.setVisibility(View.VISIBLE);
            encodedStringPropimg1=base64;
        }if(imgSource.equalsIgnoreCase("imgProp2")) {
            tvimgProp2.setText(filename);
            ivimgShowProp2.setImageBitmap(b);
            ivimgShowProp2.setVisibility(View.VISIBLE);
            encodedStringPropimg2=base64;
        }if(imgSource.equalsIgnoreCase("imgProp3")) {
            tvimgProp3.setText(filename);
            ivimgShowProp3.setImageBitmap(b);
            ivimgShowProp3.setVisibility(View.VISIBLE);
            encodedStringPropimg3=base64;
        }if(imgSource.equalsIgnoreCase("imgElect1")) {
            tvimgElect1.setText(filename);
            ivimgShowElect1.setImageBitmap(b);
            ivimgShowElect1.setVisibility(View.VISIBLE);
            encodedStringElectimg1=base64;
        }if(imgSource.equalsIgnoreCase("imgElect2")) {
            tvimgElect2.setText(filename);
            ivimgShowElect2.setImageBitmap(b);
            ivimgShowElect2.setVisibility(View.VISIBLE);
            encodedStringElectimg2=base64;
        }if(imgSource.equalsIgnoreCase("imgElect3")) {
            tvimgElect3.setText(filename);
            ivimgShowElect3.setImageBitmap(b);
            ivimgShowElect3.setVisibility(View.VISIBLE);
            encodedStringElectimg3=base64;
        }if(imgSource.equalsIgnoreCase("imgWater1")) {
            tvimgWater1.setText(filename);
            ivimgShowWater1.setImageBitmap(b);
            ivimgShowWater1.setVisibility(View.VISIBLE);
            encodedStringWaterimg1=base64;
        }if(imgSource.equalsIgnoreCase("imgWater2")) {
            tvimgWater2.setText(filename);
            ivimgShowWater2.setImageBitmap(b);
            ivimgShowWater2.setVisibility(View.VISIBLE);
            encodedStringWaterimg2=base64;
        }if(imgSource.equalsIgnoreCase("imgWater3")) {
            tvimgWater3.setText(filename);
            ivimgShowWater3.setImageBitmap(b);
            ivimgShowWater3.setVisibility(View.VISIBLE);
            encodedStringWaterimg3=base64;
        }*/
    }

  /*  public class SpinnerAdapter extends BaseAdapter {

        Context context;
        String alist[];
        LayoutInflater inflter;
        TextView tvSpinnerText;


        public SpinnerAdapter(Context applicationContext, String alist[]) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.length;
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
            convertView = inflter.inflate(R.layout.spinnerlayout, null);
            tvSpinnerText = (TextView) convertView.findViewById(R.id.tvList);
            tvSpinnerText.setText(alist[position]);
            return convertView;
        }
    }*/

    public class SpinnerAdapter extends BaseAdapter {

        Context context;
        String alist[];
        LayoutInflater inflter;


        public SpinnerAdapter(Context applicationContext, String alist[]) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.length;
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
            name.setText(alist[position]);
            return convertView;

            /*convertView = inflter.inflate(R.layout.spinnerlayout, null);
            tvSpinnerText = (TextView) convertView.findViewById(R.id.tvList);
            tvSpinnerText.setText(alist[position]);
            return convertView;*/
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
//                        intent = new Intent(GeneralForm2.this, GeneralForm1.class);
//                        startActivity(intent);
                        return true;

                    case R.id.general_two:
                        ((Dashboard) mActivity).displayView(7);
                        /*intent = new Intent(GeneralForm2.this, GeneralForm2.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_three:
                        ((Dashboard) mActivity).displayView(8);
                     /*   intent = new Intent(GeneralForm2.this, GeneralForm3.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_four:
                        ((Dashboard) mActivity).displayView(9);
//                        intent = new Intent(GeneralForm2.this, GeneralForm4.class);
//                        startActivity(intent);
                        return true;

                    case R.id.general_five:
                        ((Dashboard) mActivity).displayView(10);
                       /* intent = new Intent(GeneralForm2.this, GeneralForm5.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_six:
                        ((Dashboard) mActivity).displayView(11);
//                        intent = new Intent(GeneralForm2.this, GeneralForm6.class);
//                        startActivity(intent);
                        return true;

                    case R.id.general_seven:
                        ((Dashboard) mActivity).displayView(12);
//                        intent = new Intent(GeneralForm2.this, GeneralForm7.class);
//                        startActivity(intent);
                        return true;

                    case R.id.general_eight:
                        ((Dashboard) mActivity).displayView(13);
//                        intent = new Intent(GeneralForm2.this, GeneralForm8.class);
//                        startActivity(intent);
                        return true;

                    case R.id.general_nine:
                        ((Dashboard) mActivity).displayView(14);
//                        intent = new Intent(GeneralForm2.this, GeneralForm9.class);
//                        startActivity(intent);
                        return true;

                    case R.id.general_ten:
                        ((Dashboard) mActivity).displayView(15);
//                        intent = new Intent(GeneralForm2.this, GeneralForm10.class);
//                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }

            }
        });
    }
}
