package com.vis.android.Activities.General.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.vis.android.Activities.Dashboard;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableGeneralForm;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;

public class GeneralForm10 extends BaseFragment implements View.OnClickListener {
    public static ArrayList<HashMap<String, String>> unit_array_list = new ArrayList<HashMap<String, String>>();
    // RelativeLayout back,dots;
    //   LinearLayout dropdown;
    TextView tvNext, tvPrevious, tvSale;
    Intent intent;
    Boolean edit_page = true;
    //RadioGroup
    RadioGroup rgSimilarKind, rgMarketConditions;
    //EditText
    EditText etAnySpecificNegativity, etAtWhatYearOfPurchase, etAtWhatPurchasePrice, etMinimumRateInTheLoc, etMaximumRateInTheLoc, etName1,
            etContactNumber1, etSalePurchase1, etSalePurchaseRate, etRentalRate1, etDiscussion1, etName2, etContactNumber2, etSalePurchase2, etRentalRate2, etDiscussion2,
            etName3, etContactNumber3, etSalePurchase3, etRentalRate3, etDiscussion3, etAnyOtherComments, etEstimatedValueOFProp, etReasonConclude,
            etTypeOfProperty1, etArea1, etSalePurchasePrice1, etYearOfDeal1,
            etTypeOfProperty2, etArea2, etSalePurchasePrice2, etYearOfDeal2,
            etTypeOfProperty3, etArea3, etSalePurchasePrice3, etYearOfDeal3;
    //ArrayList
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    Preferences pref;
    //  ProgressBar progressbar;

    //   TextView tv_caseheader,tv_caseid,tv_header;

    Spinner spinnerUnit;
    SpinnerAdapter spinnerAdapter;
    SpinnerUnitAdapter spinnerUnitAdapter;
    String[] arrayUnit = {"Per Sq.ft", "Per Sq yrd", "Per Sq. mtr", "Per Acre", "Per Hectare", "Per Bigha", "Per Kanal"};
    Spinner spinnerUnit1, spinnerUnit2, spinnerUnit31, spinnerUnit32, spinnerUnit33, spinnerUnit4, spinnerUnit5, spinnerUnit6;
    View v;
    int sale = 1;
    //LinearLayout
    LinearLayout llRecent1, llRecent2, llRecent3;
    //   RelativeLayout rl_casedetail;
    private String array_unit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_generalform10, container, false);

        getid(v);
        setListener();

//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));
//
//        progressbar.setMax(10);
//        progressbar.setProgress(8);
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
        spinnerUnit1.setEnabled(false);
        spinnerUnit2.setEnabled(false);
        spinnerUnit31.setEnabled(false);
        spinnerUnit32.setEnabled(false);
        spinnerUnit33.setEnabled(false);
        spinnerUnit4.setEnabled(false);
        spinnerUnit5.setEnabled(false);
        spinnerUnit6.setEnabled(false);

        spinnerUnit.setEnabled(false);

        etAnySpecificNegativity.setEnabled(false);
        etAtWhatYearOfPurchase.setEnabled(false);
        etAtWhatPurchasePrice.setEnabled(false);
        etMinimumRateInTheLoc.setEnabled(false);
        etMaximumRateInTheLoc.setEnabled(false);
        etName1.setEnabled(false);

        etContactNumber1.setEnabled(false);
        etSalePurchase1.setEnabled(false);
        etSalePurchaseRate.setEnabled(false);
        etRentalRate1.setEnabled(false);
        etDiscussion1.setEnabled(false);
        etName2.setEnabled(false);
        etContactNumber2.setEnabled(false);
        etSalePurchase2.setEnabled(false);
        etRentalRate2.setEnabled(false);
        etDiscussion2.setEnabled(false);

        etName3.setEnabled(false);
        etContactNumber3.setEnabled(false);
        etSalePurchase3.setEnabled(false);
        etRentalRate3.setEnabled(false);
        etDiscussion3.setEnabled(false);
        etAnyOtherComments.setEnabled(false);
        etEstimatedValueOFProp.setEnabled(false);
        etReasonConclude.setEnabled(false);

        etTypeOfProperty1.setEnabled(false);
        etArea1.setEnabled(false);
        etSalePurchasePrice1.setEnabled(false);
        etYearOfDeal1.setEnabled(false);

        etTypeOfProperty2.setEnabled(false);
        etArea2.setEnabled(false);
        etSalePurchasePrice2.setEnabled(false);
        etYearOfDeal2.setEnabled(false);

        etTypeOfProperty3.setEnabled(false);
        etArea3.setEnabled(false);
        etSalePurchasePrice3.setEnabled(false);
        etYearOfDeal3.setEnabled(false);
        for (int i = 0; i < rgSimilarKind.getChildCount(); i++)
            rgSimilarKind.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgMarketConditions.getChildCount(); i++)
            rgMarketConditions.getChildAt(i).setClickable(false);
        tvSale.setEnabled(false);



    }

    public void getid(View v) {
        spinnerUnit = v.findViewById(R.id.spinnerUnit);
        spinnerUnit1 = v.findViewById(R.id.spinnerUnit1);
        spinnerUnit2 = v.findViewById(R.id.spinnerUnit2);
        spinnerUnit31 = v.findViewById(R.id.spinnerUnit31);
        spinnerUnit32 = v.findViewById(R.id.spinnerUnit32);
        spinnerUnit33 = v.findViewById(R.id.spinnerUnit33);
        spinnerUnit4 = v.findViewById(R.id.spinnerUnit4);
        spinnerUnit5 = v.findViewById(R.id.spinnerUnit5);
        spinnerUnit6 = v.findViewById(R.id.spinnerUnit6);

        llRecent1 = v.findViewById(R.id.llRecent1);
        llRecent2 = v.findViewById(R.id.llRecent2);
        llRecent3 = v.findViewById(R.id.llRecent3);

//        tv_header = v.findViewById(R.id.tv_header);
//        rl_casedetail = v.findViewById(R.id.rl_casedetail);
//        tv_caseheader = v.findViewById(R.id.tv_caseheader);
//        tv_caseid = v.findViewById(R.id.tv_caseid);
//
//        progressbar = (ProgressBar)v.findViewById(R.id.Progress);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        //   back = v.findViewById(R.id.rl_back);
        tvNext = v.findViewById(R.id.tvNext);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        tvSale = v.findViewById(R.id.tvSale);
        //   dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);
        //   dots = (RelativeLayout) v.findViewById(R.id.rl_dots);

        rgSimilarKind = v.findViewById(R.id.rgSimilarKind);
        rgMarketConditions = v.findViewById(R.id.rgMarketConditions);


        etAnySpecificNegativity = v.findViewById(R.id.etAnySpecificNegativity);
        etAtWhatYearOfPurchase = v.findViewById(R.id.etAtWhatYearOfPurchase);
        etAtWhatPurchasePrice = v.findViewById(R.id.etAtWhatPurchasePrice);
        etMinimumRateInTheLoc = v.findViewById(R.id.etMinimumRateInTheLoc);
        etMaximumRateInTheLoc = v.findViewById(R.id.etMaximumRateInTheLoc);
        etName1 = v.findViewById(R.id.etName1);
        etContactNumber1 = v.findViewById(R.id.etContactNumber1);
        etSalePurchase1 = v.findViewById(R.id.etSalePurchase1);
        etSalePurchaseRate = v.findViewById(R.id.etSalePurchaseRate);
        etRentalRate1 = v.findViewById(R.id.etRentalRate1);
        etDiscussion1 = v.findViewById(R.id.etDiscussion1);
        etName2 = v.findViewById(R.id.etName2);
        etContactNumber2 = v.findViewById(R.id.etContactNumber2);
        etSalePurchase2 = v.findViewById(R.id.etSalePurchase2);
        etRentalRate2 = v.findViewById(R.id.etRentalRate2);
        etDiscussion2 = v.findViewById(R.id.etDiscussion2);
        etName3 = v.findViewById(R.id.etName3);
        etContactNumber3 = v.findViewById(R.id.etContactNumber3);
        etSalePurchase3 = v.findViewById(R.id.etSalePurchase3);
        etRentalRate3 = v.findViewById(R.id.etRentalRate3);
        etDiscussion3 = v.findViewById(R.id.etDiscussion3);
        etAnyOtherComments = v.findViewById(R.id.etAnyOtherComments);

        etTypeOfProperty1 = v.findViewById(R.id.etTypeOfProperty1);
        etArea1 = v.findViewById(R.id.etArea1);
        etSalePurchasePrice1 = v.findViewById(R.id.etSalePurchasePrice1);
        etYearOfDeal1 = v.findViewById(R.id.etYearOfDeal1);

        etTypeOfProperty2 = v.findViewById(R.id.etTypeOfProperty2);
        etArea2 = v.findViewById(R.id.etArea2);
        etSalePurchasePrice2 = v.findViewById(R.id.etSalePurchasePrice2);
        etYearOfDeal2 = v.findViewById(R.id.etYearOfDeal2);

        etTypeOfProperty3 = v.findViewById(R.id.etTypeOfProperty3);
        etArea3 = v.findViewById(R.id.etArea3);
        etSalePurchasePrice3 = v.findViewById(R.id.etSalePurchasePrice3);
        etYearOfDeal3 = v.findViewById(R.id.etYearOfDeal3);

        etEstimatedValueOFProp = v.findViewById(R.id.etEstimatedValueOFProp);
        etReasonConclude = v.findViewById(R.id.etReasonConclude);

        try {
            array_unit = pref.get(Constants.array_units);
            Log.v("array_unit+++", array_unit);
            unit_array_list.clear();
            JSONArray unit_list = new JSONArray(array_unit);
            for (int j = 0; j < unit_list.length(); j++) {
                JSONObject data_object = unit_list.getJSONObject(j);
                String name = data_object.getString("name");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("name", name);
                unit_array_list.add(hmap);

            }

            spinnerAdapter = new SpinnerAdapter(mActivity, unit_array_list);
            spinnerUnit.setAdapter(spinnerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }


        spinnerUnitAdapter = new SpinnerUnitAdapter(mActivity, arrayUnit);

        spinnerUnit1.setAdapter(spinnerUnitAdapter);
        spinnerUnit2.setAdapter(spinnerUnitAdapter);
        spinnerUnit31.setAdapter(spinnerUnitAdapter);
        spinnerUnit32.setAdapter(spinnerUnitAdapter);
        spinnerUnit33.setAdapter(spinnerUnitAdapter);
        spinnerUnit4.setAdapter(spinnerUnitAdapter);
        spinnerUnit5.setAdapter(spinnerUnitAdapter);
        spinnerUnit6.setAdapter(spinnerUnitAdapter);
    }

    public void setListener() {
        tvNext.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        tvSale.setOnClickListener(this);
        //  dots.setOnClickListener(this);
        //  back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvSale:

                if (sale == 1) {
                    sale = 2;
                    llRecent2.setVisibility(View.VISIBLE);
                } else if (sale == 2) {
                    sale = 3;
                    llRecent3.setVisibility(View.VISIBLE);
                    tvSale.setVisibility(View.GONE);
                }

                break;

            case R.id.tvPrevious:
//                intent=new Intent(GeneralForm10.this, GeneralForm9.class);
//                startActivity(intent);
//               onBackPressed();
                //((Dashboard)mActivity).displayView(14);
                mActivity.onBackPressed();
                break;


            case R.id.tvNext:
                if (edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(16);
                break;

          /*  case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;*/
        }
    }

    private void validation() {
        if (rgSimilarKind.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Select Similar kind of properties", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvSimilar);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgMarketConditions.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Select Market Conditions related to demand and supply", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvMarket);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (etAnySpecificNegativity.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Any Specific Negativity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAnySpecificNegativity.requestFocus();
        } else if (etAtWhatYearOfPurchase.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Year of Purchase", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAtWhatYearOfPurchase.requestFocus();
        } else if (etAtWhatPurchasePrice.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Purchase Price", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAtWhatPurchasePrice.requestFocus();
        }
       /* else if (etSalePurchaseRate.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Sale Purchase Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSalePurchaseRate.requestFocus();
        }*/
        else if (etMinimumRateInTheLoc.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Minimum Rate in the locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMinimumRateInTheLoc.requestFocus();
        } else if (etMaximumRateInTheLoc.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Maximum Rate in the locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMaximumRateInTheLoc.requestFocus();
        } else if (etTypeOfProperty1.getText().toString().isEmpty() && llRecent1.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Type of Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeOfProperty1.requestFocus();
        } else if (etArea1.getText().toString().isEmpty() && llRecent1.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etArea1.requestFocus();
        } else if (etSalePurchasePrice1.getText().toString().isEmpty() && llRecent1.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Sale Purchase Price", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSalePurchasePrice1.requestFocus();
        } else if (etYearOfDeal1.getText().toString().isEmpty() && llRecent1.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Year of Deal", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etYearOfDeal1.requestFocus();
        } else if (etTypeOfProperty2.getText().toString().isEmpty() && llRecent2.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Type of Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeOfProperty2.requestFocus();
        } else if (etArea2.getText().toString().isEmpty() && llRecent2.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etArea2.requestFocus();
        } else if (etSalePurchasePrice2.getText().toString().isEmpty() && llRecent2.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Sale Purchase Price", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSalePurchasePrice2.requestFocus();
        } else if (etYearOfDeal2.getText().toString().isEmpty() && llRecent2.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Year of Deal", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etYearOfDeal2.requestFocus();
        } else if (etTypeOfProperty3.getText().toString().isEmpty() && llRecent3.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Type of Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeOfProperty3.requestFocus();
        } else if (etArea3.getText().toString().isEmpty() && llRecent3.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etArea3.requestFocus();
        } else if (etSalePurchasePrice3.getText().toString().isEmpty() && llRecent3.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Sale Purchase Price", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSalePurchasePrice3.requestFocus();
        } else if (etYearOfDeal3.getText().toString().isEmpty() && llRecent3.getVisibility() == View.VISIBLE) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Year of Deal", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etYearOfDeal3.requestFocus();
        } else if (etName1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etName1.requestFocus();
        } else if (etContactNumber1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber1.requestFocus();
        } else if (etContactNumber1.getText().toString().length() < 10) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Valid Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber1.requestFocus();
        } else if (etSalePurchase1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Sale Purchase Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSalePurchase1.requestFocus();
        } else if (etRentalRate1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Rental Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRentalRate1.requestFocus();
        } else if (etDiscussion1.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Discussion held", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDiscussion1.requestFocus();
        } else if (etName2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etName2.requestFocus();
        } else if (etContactNumber2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber2.requestFocus();
        } else if (etContactNumber2.getText().toString().length() < 10) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Valid Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber2.requestFocus();
        } else if (etSalePurchase2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Sale Purchase Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSalePurchase2.requestFocus();
        } else if (etRentalRate2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Rental Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRentalRate2.requestFocus();
        } else if (etDiscussion2.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Discussion held", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDiscussion2.requestFocus();
        } else if (!etContactNumber3.getText().toString().isEmpty()
                && etContactNumber3.getText().toString().length() < 10) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Valid Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber3.requestFocus();
        } else if (etEstimatedValueOFProp.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Estimated Value of the Property as per surveyor", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etEstimatedValueOFProp.requestFocus();
        } else if (etReasonConclude.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Reason for this Value/ Concluding comments", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etReasonConclude.requestFocus();
        } else {
            putIntoHm();
            ((Dashboard) mActivity).displayView(16);
            //Snackbar snackbar = Snackbar.make(tvNext, "Data Saved Successfully", Snackbar.LENGTH_SHORT);
            //snackbar.show();
            return;
        }
    }

    public void putIntoHm() {
        hm.put("anySpecificNegativity", etAnySpecificNegativity.getText().toString().trim());
        hm.put("atWhatYearOfPurchase", etAtWhatYearOfPurchase.getText().toString().trim());
        hm.put("atWhatPurchasePrice", etAtWhatPurchasePrice.getText().toString().trim());
        hm.put("minimumRateInLocalityy", etMinimumRateInTheLoc.getText().toString().trim());
        hm.put("maximumRateInLocalityy", etMaximumRateInTheLoc.getText().toString().trim());
        hm.put("localName1", etName1.getText().toString().trim());
        hm.put("localContact1", etContactNumber1.getText().toString().trim());
        hm.put("localSalePurchase1", etSalePurchase1.getText().toString().trim());
        hm.put("localRentalRate1", etRentalRate1.getText().toString().trim());
        hm.put("localDiscussionHeld1", etDiscussion1.getText().toString().trim());
        hm.put("localName2", etName2.getText().toString().trim());
        hm.put("localContact2", etContactNumber2.getText().toString().trim());
        hm.put("localSalePurchase2", etSalePurchase2.getText().toString().trim());
        hm.put("localRentalRate2", etRentalRate2.getText().toString().trim());
        hm.put("localDiscussionHeld2", etDiscussion2.getText().toString().trim());
        hm.put("localName3", etName3.getText().toString().trim());
        hm.put("localContact3", etContactNumber3.getText().toString().trim());
        hm.put("localSalePurchase3", etSalePurchase3.getText().toString().trim());
        hm.put("localRentalRate3", etRentalRate3.getText().toString().trim());
        hm.put("localDiscussionHeld3", etDiscussion3.getText().toString().trim());
        hm.put("anyOtherComments", etAnyOtherComments.getText().toString().trim());

        hm.put("typeOfPropertyRecent1", etTypeOfProperty1.getText().toString().trim());
        hm.put("areaRecent1", etArea1.getText().toString().trim());
        hm.put("salePurchaseRecent1", etSalePurchasePrice1.getText().toString().trim());
        hm.put("yearRecent1", etYearOfDeal1.getText().toString().trim());


        hm.put("typeOfPropertyRecent2", etTypeOfProperty2.getText().toString().trim());
        hm.put("areaRecent2", etArea2.getText().toString().trim());
        hm.put("salePurchaseRecent2", etSalePurchasePrice2.getText().toString().trim());
        hm.put("yearRecent2", etYearOfDeal2.getText().toString().trim());

        hm.put("typeOfPropertyRecent3", etTypeOfProperty3.getText().toString().trim());
        hm.put("areaRecent3", etArea3.getText().toString().trim());
        hm.put("salePurchaseRecent3", etSalePurchasePrice3.getText().toString().trim());
        hm.put("yearRecent3", etYearOfDeal3.getText().toString().trim());

        hm.put("reasonConclude", etReasonConclude.getText().toString().trim());
        hm.put("estimatedValueOFProp", etEstimatedValueOFProp.getText().toString().trim());

        hm.put("unit31", String.valueOf(spinnerUnit31.getSelectedItemPosition()));
        hm.put("unit32", String.valueOf(spinnerUnit32.getSelectedItemPosition()));
        hm.put("unit33", String.valueOf(spinnerUnit33.getSelectedItemPosition()));


        int selectedIdSimilarKind = rgSimilarKind.getCheckedRadioButtonId();
        View radioButtonSimilarKind = v.findViewById(selectedIdSimilarKind);
        int idx = rgSimilarKind.indexOfChild(radioButtonSimilarKind);
        RadioButton r = (RadioButton) rgSimilarKind.getChildAt(idx);
        //   String selectedText = r.getText().toString();
        hm.put("radioButtonSimilarKind", String.valueOf(idx));

        int selectedIdMarketConditions = rgMarketConditions.getCheckedRadioButtonId();
        View radioButtonMarketConditions = v.findViewById(selectedIdMarketConditions);
        int idx2 = rgMarketConditions.indexOfChild(radioButtonMarketConditions);
        RadioButton r2 = (RadioButton) rgMarketConditions.getChildAt(idx2);
        //  String selectedText2 = r2.getText().toString();
        hm.put("radioButtonMarketConditions", String.valueOf(idx2));


        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        //DatabaseController.removeTable(TableGeneralForm.attachment);
        // DatabaseController.deleteColumnData("column1");

        ContentValues contentValues = new ContentValues();

        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column10.toString(), jsonArrayData.toString());

        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "10");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));


    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getTableOne("column10", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column10"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);


                etAnySpecificNegativity.setText(object.getString("anySpecificNegativity"));
                etAtWhatYearOfPurchase.setText(object.getString("atWhatYearOfPurchase"));
                etAtWhatPurchasePrice.setText(object.getString("atWhatPurchasePrice"));
                etMinimumRateInTheLoc.setText(object.getString("minimumRateInLocalityy"));
                etMaximumRateInTheLoc.setText(object.getString("maximumRateInLocalityy"));
                etName1.setText(object.getString("localName1"));
                etContactNumber1.setText(object.getString("localContact1"));
                etSalePurchase1.setText(object.getString("localSalePurchase1"));
                etRentalRate1.setText(object.getString("localRentalRate1"));
                etDiscussion1.setText(object.getString("localDiscussionHeld1"));
                etName2.setText(object.getString("localName2"));
                etContactNumber2.setText(object.getString("localContact2"));
                etSalePurchase2.setText(object.getString("localSalePurchase2"));
                etRentalRate2.setText(object.getString("localRentalRate2"));
                etDiscussion2.setText(object.getString("localDiscussionHeld2"));
                etName3.setText(object.getString("localName3"));
                etContactNumber3.setText(object.getString("localContact3"));
                etSalePurchase3.setText(object.getString("localSalePurchase3"));
                etRentalRate3.setText(object.getString("localRentalRate3"));
                etDiscussion3.setText(object.getString("localDiscussionHeld3"));
                etAnyOtherComments.setText(object.getString("anyOtherComments"));

                etTypeOfProperty1.setText(object.getString("typeOfPropertyRecent1"));
                etArea1.setText(object.getString("areaRecent1"));
                etSalePurchasePrice1.setText(object.getString("salePurchaseRecent1"));
                etYearOfDeal1.setText(object.getString("yearRecent1"));

                etTypeOfProperty2.setText(object.getString("typeOfPropertyRecent2"));
                etArea2.setText(object.getString("areaRecent2"));
                etSalePurchasePrice2.setText(object.getString("salePurchaseRecent2"));
                etYearOfDeal2.setText(object.getString("yearRecent2"));

                etTypeOfProperty3.setText(object.getString("typeOfPropertyRecent3"));
                etArea3.setText(object.getString("areaRecent3"));
                etSalePurchasePrice3.setText(object.getString("salePurchaseRecent3"));
                etYearOfDeal3.setText(object.getString("yearRecent3"));

                etReasonConclude.setText(object.getString("reasonConclude"));
                etEstimatedValueOFProp.setText(object.getString("estimatedValueOFProp"));

                spinnerUnit31.setSelection(Integer.parseInt(object.getString("unit31")));
                spinnerUnit32.setSelection(Integer.parseInt(object.getString("unit32")));
                spinnerUnit33.setSelection(Integer.parseInt(object.getString("unit33")));

                try {
                    ((RadioButton) rgSimilarKind.getChildAt(Integer.parseInt(object.getString("radioButtonSimilarKind")))).setChecked(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

              /*  if (object.getString("radioButtonSimilarKind").equals("Ample availability")){
                    ((RadioButton)rgSimilarKind.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonSimilarKind").equals("Easily available")){
                    ((RadioButton)rgSimilarKind.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonSimilarKind").equals("Not so easy available")){
                    ((RadioButton)rgSimilarKind.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonSimilarKind").equals("Very less availability")){
                    ((RadioButton)rgSimilarKind.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonSimilarKind").equals("No availability")){
                    ((RadioButton)rgSimilarKind.getChildAt(4)).setChecked(true);
                }*/


                try {
                    ((RadioButton) rgMarketConditions.getChildAt(Integer.parseInt(object.getString("radioButtonMarketConditions")))).setChecked(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*if (object.getString("radioButtonMarketConditions").equals("Very high demand of such kind of properties in the Market due to its nature and current use")){
                    ((RadioButton)rgMarketConditions.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("High demand for such kind of properties but availability is less")){
                    ((RadioButton)rgMarketConditions.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Less demand for such kind of properties")){
                    ((RadioButton)rgMarketConditions.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Demand is related to the current use of the property only and only limited to the selected type of buyers")){
                    ((RadioButton)rgMarketConditions.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Very hot cake property and will always remain in good demand")){
                    ((RadioButton)rgMarketConditions.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Poor demand of the property because of its condition")){
                    ((RadioButton)rgMarketConditions.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Poor demand of the property because of its location")){
                    ((RadioButton)rgMarketConditions.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Poor demand of the property because of its condition & location")){
                    ((RadioButton)rgMarketConditions.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Moderate demand of the property because of its large size")){
                    ((RadioButton)rgMarketConditions.getChildAt(8)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Moderate demand of the property limited to selected buyers only because of its high value")){
                    ((RadioButton)rgMarketConditions.getChildAt(9)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Moderate demand of the property because of its low usability factor")){
                    ((RadioButton)rgMarketConditions.getChildAt(10)).setChecked(true);
                }
                else if (object.getString("radioButtonMarketConditions").equals("Poor demand of the property because of its low usability factor")){
                    ((RadioButton)rgMarketConditions.getChildAt(11)).setChecked(true);
                }*/


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

    public class SpinnerAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflter;
        ArrayList<HashMap<String, String>> alist;

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

            convertView = inflter.inflate(R.layout.land_adapter, null);
            TextView textView = convertView.findViewById(R.id.tv_surveyor);
            //textView.setTextSize(10f);
            try {
                textView.setText(alist.get(position).get("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public class SpinnerUnitAdapter extends BaseAdapter {

        Context context;
        String alist[];
        LayoutInflater inflter;

        TextView tvSpinnerText;


        public SpinnerUnitAdapter(Context applicationContext, String alist[]) {
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
//        convertView = inflter.inflate(R.layout.spinnerlayout, null);
//        tvSpinnerText = (TextView) convertView.findViewById(R.id.tvList);
//        tvSpinnerText.setText(alist[position]);
            convertView = inflter.inflate(R.layout.profession_adapter, null);
            TextView name = (TextView) convertView.findViewById(R.id.tv_profession);
            name.setText(alist[position]);
            return convertView;

            //            profession.setSelection(Integer.parseInt(profession_array_list.get(position).get("id")));
        }
    }
}
