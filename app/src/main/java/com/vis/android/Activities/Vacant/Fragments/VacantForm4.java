package com.vis.android.Activities.Vacant.Fragments;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.SpinnerAdapter;
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

public class VacantForm4 extends BaseFragment implements View.OnClickListener {
    //   RelativeLayout back,dots;
    TextView next, tvPrevious;
    Intent intent;
    // ProgressBar progressbar;
    LinearLayout dropdown;
    Preferences pref;
    String selectedText;

    Boolean edit_page = true;
    //RelativeLayout
    RelativeLayout rlBelowRoadLevel, rlAboveRoadLevel, rlSpinnerSides;

    Spinner spinnerSides;

    SpinnerAdapter spinnerAdapter;

    String noOfSidesArray[] = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    //RadioGroup

    //RadioButton
    RadioButton rbBelowRoad;

    CheckBox cbPlainLand, cbSolidLand, cbRockyLand, cbMarshLand, cbReclaimedLand, cbWaterLogged, cbTerrainLand, cbLandLocked, cbSandyLand, cbDryLand, cbNotApp;

    int cbPlainLandCheck, cbSolidLandCheck, cbRockyLandCheck, cbMarshLandCheck, cbReclaimedLandCheck, cbWaterLoggedCheck, cbTerrainLandCheck,
            cbLandLockedCheck, cbSandyLandCheck, cbDryLandCheck, cbNotAppCheck;

    RadioGroup rgShapeOfTheLand, rgLevelOfLand, rgFrontageToDepth;

    EditText etBelowRoadLevel, etAboveRoadLevel, etLargeFrontage, etLessFrontage, etNormalFrontage;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    // TextView tv_caseheader,tv_caseid,tv_header;

    // RelativeLayout rl_casedetail;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_vacantform4, container, false);

        getid(v);
        setListener();
        condition();


//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

        //progressbar.setMax(10);
        // progressbar.setProgress(4);
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
    private void  setEditiblity() {

        cbPlainLand.setClickable(false);
        cbSolidLand.setClickable(false);
        cbRockyLand.setClickable(false);
        cbMarshLand.setClickable(false);
        cbReclaimedLand.setClickable(false);
        cbWaterLogged.setClickable(false);
        cbTerrainLand.setClickable(false);
        cbLandLocked.setClickable(false);
        cbSandyLand.setClickable(false);
        cbDryLand.setClickable(false);
        cbNotApp.setClickable(false);

        for(int i=0;i<rgShapeOfTheLand.getChildCount();i++)
            rgShapeOfTheLand.getChildAt(i).setClickable(false);
        for(int i=0;i<rgFrontageToDepth.getChildCount();i++)
            rgFrontageToDepth.getChildAt(i).setClickable(false);
        for(int i=0;i<rgLevelOfLand.getChildCount();i++)
            rgLevelOfLand.getChildAt(i).setClickable(false);

    }
    public void getid(View v) {
//        tv_header = v.findViewById(R.id.tv_header);
//        rl_casedetail = v.findViewById(R.id.rl_casedetail);
//        tv_caseheader = v.findViewById(R.id.tv_caseheader);
//        tv_caseid = v.findViewById(R.id.tv_caseid);
//
//        progressbar = (ProgressBar)v.findViewById(R.id.Progress);
        pref=new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page=false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page=true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        //   back = v.findViewById(R.id.rl_back);
        selectedText=pref.get(Constants.selectedshape);
        next = v.findViewById(R.id.next);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        //   dots = (RelativeLayout) v.findViewById(R.id.rl_dots);
        dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);

        //EditText

        etBelowRoadLevel = v.findViewById(R.id.etBelowRoadLevel);
        etAboveRoadLevel = v.findViewById(R.id.etAboveRoadLevel);
        etNormalFrontage = v.findViewById(R.id.etNormalFrontage);
        etLargeFrontage = v.findViewById(R.id.etLargeFrontage);
        etLessFrontage = v.findViewById(R.id.etLessFrontage);

        //RelativeLayout
        rlBelowRoadLevel = v.findViewById(R.id.rlBelowRoadLevel);
        rlAboveRoadLevel = v.findViewById(R.id.rlAboveRoadLevel);
//        rlSpinnerSides = v.findViewById(R.id.rlSpinnerSides);

        //RadioGroup
        rgLevelOfLand = v.findViewById(R.id.rgLevelOfLand);
        rgShapeOfTheLand = v.findViewById(R.id.rgShapeOfTheLand);
        rgFrontageToDepth = v.findViewById(R.id.rgFrontageToDepth);

        //RadioButton
        rbBelowRoad = v.findViewById(R.id.rbBelowRoad);

        //CheckBox
        cbPlainLand = v.findViewById(R.id.cbPlainLand);
        cbSolidLand = v.findViewById(R.id.cbSolidLand);
        cbRockyLand = v.findViewById(R.id.cbRockyLand);
        cbMarshLand = v.findViewById(R.id.cbMarshLand);
        cbReclaimedLand = v.findViewById(R.id.cbReclaimedLand);
        cbWaterLogged = v.findViewById(R.id.cbWaterLogged);
        cbTerrainLand = v.findViewById(R.id.cbTerrainLand);
        cbLandLocked = v.findViewById(R.id.cbLandLocked);
        cbSandyLand = v.findViewById(R.id.cbSandyLand);
        cbDryLand = v.findViewById(R.id.cbDryLand);
        cbNotApp = v.findViewById(R.id.cbNotApp);

        if (selectedText.equalsIgnoreCase("Square")){
            ((RadioButton)rgShapeOfTheLand.getChildAt(0)).setChecked(true);
        }
        else if (selectedText.equalsIgnoreCase("Rectangle")){
            ((RadioButton)rgShapeOfTheLand.getChildAt(1)).setChecked(true);
        }
        else if (selectedText.equalsIgnoreCase("Trapezium")){
            ((RadioButton)rgShapeOfTheLand.getChildAt(2)).setChecked(true);
        }
        else if (selectedText.equalsIgnoreCase("Triangle")){
            ((RadioButton)rgShapeOfTheLand.getChildAt(3)).setChecked(true);
        }
        else if (selectedText.equalsIgnoreCase("Trapezoid")){
            ((RadioButton)rgShapeOfTheLand.getChildAt(4)).setChecked(true);
        }
        else if (selectedText.equalsIgnoreCase("Irregular")){
            ((RadioButton)rgShapeOfTheLand.getChildAt(5)).setChecked(true);
        }
        else if (selectedText.equals("Not Applicable")){
            ((RadioButton)rgShapeOfTheLand.getChildAt(6)).setChecked(true);
            for(int c=0;c<rgShapeOfTheLand.getChildCount();c++){
                ((RadioButton)rgShapeOfTheLand.getChildAt(c)).setClickable(true);
            }
        }
        spinnerAdapter = new SpinnerAdapter(mActivity,noOfSidesArray);
//        spinnerSides.setAdapter(spinnerAdapter);

    }

    public void setListener() {
        //   back.setOnClickListener(this);
        next.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        //   dots.setOnClickListener(this);

        rgFrontageToDepth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int pos = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()){
                    if (pos == 0){
                        etNormalFrontage.setVisibility(View.VISIBLE);
                        etLessFrontage.setVisibility(View.GONE);
                        etLargeFrontage.setVisibility(View.GONE);
                    } else if (pos == 2){
                        etNormalFrontage.setVisibility(View.GONE);
                        etLessFrontage.setVisibility(View.VISIBLE);
                        etLargeFrontage.setVisibility(View.GONE);
                    } else if (pos == 4){
                        etNormalFrontage.setVisibility(View.GONE);
                        etLessFrontage.setVisibility(View.GONE);
                        etLargeFrontage.setVisibility(View.VISIBLE);
                    } else {
                        etNormalFrontage.setVisibility(View.GONE);
                        etLessFrontage.setVisibility(View.GONE);
                        etLargeFrontage.setVisibility(View.GONE);
                    }
                }

            }
        });

        cbNotApp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                switch (compoundButton.getId()) {

                    //    CheckBox ,,,,
                    // ,,,,,,;
                    case R.id.cbNotApp:
                        if (isChecked) {
                            cbPlainLand.setChecked(false);
                            cbSolidLand.setChecked(false);
                            cbRockyLand.setChecked(false);
                            cbMarshLand.setChecked(false);
                            cbReclaimedLand.setChecked(false);
                            cbWaterLogged.setChecked(false);
                            cbTerrainLand.setChecked(false);
                            cbLandLocked.setChecked(false);
                            cbSandyLand.setChecked(false);
                            cbDryLand.setChecked(false);

                        }

                        break;

                    case R.id.cbPlainLand:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbSolidLand:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbRockyLand:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbMarshLand:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbReclaimedLand:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbWaterLogged:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbTerrainLand:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbLandLocked:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbSandyLand:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                    case R.id.cbDryLand:
                        if (isChecked) {
                            cbNotApp.setChecked(false);
                        }
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
       /*     case R.id.rl_back:
                onBackPressed();
                break;*/

            case R.id.tvPrevious:
//                intent=new Intent(GeneralForm4.this,GeneralForm3.class);
//                startActivity(intent);
//               onBackPressed();

                //((Dashboard)mActivity).displayView(8);
                mActivity.onBackPressed();
                break;

            case R.id.next:
                if(edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(23);
                break;

            case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
        }
    }


    private void validation(){
        if (!cbPlainLand.isChecked()
                && !cbSolidLand.isChecked()
                && !cbRockyLand.isChecked()
                && !cbMarshLand.isChecked()
                && !cbReclaimedLand.isChecked()
                && !cbWaterLogged.isChecked()
                && !cbTerrainLand.isChecked()
                && !cbLandLocked.isChecked()
                && !cbSandyLand.isChecked()
                && !cbDryLand.isChecked()
                && !cbNotApp.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Land Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvLandType);
            targetView.getParent().requestChildFocus(targetView,targetView);

        }
        else if (rgShapeOfTheLand.getCheckedRadioButtonId()==-1){
            Snackbar snackbar = Snackbar.make(next, "Please Select Shape of Land", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvShape);
            targetView.getParent().requestChildFocus(targetView,targetView);

        }
        else if (rgLevelOfLand.getCheckedRadioButtonId()==-1){
            Snackbar snackbar = Snackbar.make(next, "Please Select Level of Land", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvLevel);
            targetView.getParent().requestChildFocus(targetView,targetView);

        }
        else if(rlBelowRoadLevel.getVisibility()==View.VISIBLE && etBelowRoadLevel.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(next, "Please Enter Below road level depth", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBelowRoadLevel.requestFocus();
        }
        else if(rlAboveRoadLevel.getVisibility()==View.VISIBLE && etAboveRoadLevel.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(next, "Please Enter Above road level height", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAboveRoadLevel.requestFocus();
        }
        else if(etNormalFrontage.getVisibility()==View.VISIBLE && etNormalFrontage.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(next, "Please Enter Normal Frontage to Depth Ratio", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNormalFrontage.requestFocus();
        }
        else if(etLessFrontage.getVisibility()==View.VISIBLE && etLessFrontage.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(next, "Please Enter Less Frontage to Depth Ratio", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLessFrontage.requestFocus();
        }
        else if(etLargeFrontage.getVisibility()==View.VISIBLE && etLargeFrontage.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(next, "Please Enter Large Frontage to Depth Ratio", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLargeFrontage.requestFocus();
        }
        else if (rgFrontageToDepth.getCheckedRadioButtonId()==-1){
            Snackbar snackbar = Snackbar.make(next, "Please Select Frontage to depth ratio", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvFrontage);
            targetView.getParent().requestChildFocus(targetView,targetView);

        }


        else {
            putIntoHm();
            /*intent = new Intent(mActivity, GeneralForm5.class);
            startActivity(intent);*/
            ((Dashboard)mActivity).displayView(23);
            return;
        }
    }

    public void putIntoHm(){

        if (rlBelowRoadLevel.getVisibility()==View.VISIBLE){
            hm.put("belowRoadDepth", etBelowRoadLevel.getText().toString());
        }else {
            hm.put("belowRoadDepth", "");
        }


        //if (rlAboveRoadLevel.getVisibility()==View.VISIBLE){
        if (rlAboveRoadLevel.getVisibility()==View.VISIBLE){
            hm.put("aboveRoadHeight", etAboveRoadLevel.getText().toString());
        }else {
            hm.put("aboveRoadHeight", "");
        }

        if (etNormalFrontage.getVisibility()==View.VISIBLE){
            hm.put("normalFrontageRatio", etNormalFrontage.getText().toString());
        }else {
            hm.put("normalFrontageRatio", "");
        }

        if (etLessFrontage.getVisibility()==View.VISIBLE){
            hm.put("lessFrontageRatio", etLessFrontage.getText().toString());
        }else {
            hm.put("lessFrontageRatio", "");
        }

        if (etLargeFrontage.getVisibility()==View.VISIBLE){
            hm.put("largeFrontageRatio", etLargeFrontage.getText().toString());
        }else {
            hm.put("largeFrontageRatio", "");
        }
        //}

        ////CheckBox Location Consideration
        if (cbPlainLand.isChecked()){
            cbPlainLandCheck = 1;
            hm.put("cbPlainLand", String.valueOf(cbPlainLandCheck));
        }else
        {
            cbPlainLandCheck = 0;
            hm.put("cbPlainLand", String.valueOf(cbPlainLandCheck));
        }
        if (cbSolidLand.isChecked()){
            cbSolidLandCheck = 1;
            hm.put("cbSolidLand", String.valueOf(cbSolidLandCheck));
        }
        else
        {
            cbSolidLandCheck = 0;
            hm.put("cbSolidLand", String.valueOf(cbSolidLandCheck));
        }
        if (cbRockyLand.isChecked()){
            cbRockyLandCheck = 1;
            hm.put("cbRockyLand", String.valueOf(cbRockyLandCheck));
        }
        else
        {
            cbRockyLandCheck = 0;
            hm.put("cbRockyLand", String.valueOf(cbRockyLandCheck));
        }
        if (cbMarshLand.isChecked()) {
            cbMarshLandCheck = 1;
            hm.put("cbMarshLand", String.valueOf(cbMarshLandCheck));
        }
        else
        {
            cbMarshLandCheck = 0;
            hm.put("cbMarshLand", String.valueOf(cbMarshLandCheck));
        }
        if (cbReclaimedLand.isChecked()){
            cbReclaimedLandCheck = 1;
            hm.put("cbReclaimedLand", String.valueOf(cbReclaimedLandCheck));
        }
        else
        {
            cbReclaimedLandCheck = 0;
            hm.put("cbReclaimedLand", String.valueOf(cbReclaimedLandCheck));
        }
        if (cbWaterLogged.isChecked()){
            cbWaterLoggedCheck = 1;
            hm.put("cbWaterLogged", String.valueOf(cbWaterLoggedCheck));
        }
        else
        {
            cbWaterLoggedCheck = 0;
            hm.put("cbWaterLogged", String.valueOf(cbWaterLoggedCheck));
        }
        if (cbTerrainLand.isChecked()){
            cbTerrainLandCheck = 1;
            hm.put("cbTerrainLand", String.valueOf(cbTerrainLandCheck));
        }else
        {
            cbTerrainLandCheck = 0;
            hm.put("cbTerrainLand", String.valueOf(cbTerrainLandCheck));
        }
        if (cbLandLocked.isChecked()){
            cbLandLockedCheck = 1;
            hm.put("cbLandLocked", String.valueOf(cbLandLockedCheck));
        }
        else
        {
            cbLandLockedCheck = 0;
            hm.put("cbLandLocked", String.valueOf(cbLandLockedCheck));
        }
        if (cbSandyLand.isChecked()){
            cbSandyLandCheck = 1;
            hm.put("cbSandyLand", String.valueOf(cbSandyLandCheck));
        }
        else
        {
            cbSandyLandCheck = 0;
            hm.put("cbSandyLand", String.valueOf(cbSandyLandCheck));
        }
        if (cbDryLand.isChecked()) {
            cbDryLandCheck = 1;
            hm.put("cbDryLand", String.valueOf(cbDryLandCheck));
        }
        else
        {
            cbDryLandCheck = 0;
            hm.put("cbDryLand", String.valueOf(cbDryLandCheck));
        }
        if (cbNotApp.isChecked()) {
            cbNotAppCheck = 1;
            hm.put("cbNotApp", String.valueOf(cbNotAppCheck));
        }
        else
        {
            cbNotAppCheck = 0;
            hm.put("cbNotApp", String.valueOf(cbNotAppCheck));
        }


        int selectedIdShapeOfTheLandd = rgShapeOfTheLand.getCheckedRadioButtonId();
        View radioButtonShapeOfTheLand = v.findViewById(selectedIdShapeOfTheLandd);
        int idx = rgShapeOfTheLand.indexOfChild(radioButtonShapeOfTheLand);
        RadioButton r = (RadioButton)  rgShapeOfTheLand.getChildAt(idx);
//        selectedText = r.getText().toString();
        hm.put("radioButtonShapeOfTheLandd", selectedText);
        hm.put("radioButtonShapeOfTheLanddId", Integer.toString(idx));
        int selectedIdLevelOfLand = rgLevelOfLand.getCheckedRadioButtonId();
        View radioButtonLevelOfLand = v.findViewById(selectedIdLevelOfLand);
        int idx2 = rgLevelOfLand.indexOfChild(radioButtonLevelOfLand);
        RadioButton r2 = (RadioButton)  rgLevelOfLand.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonLevelOfLandd", selectedText2);
        hm.put("radioButtonLevelOfLanddId", Integer.toString(idx2));

        int selectedIdFrontageToDepth = rgFrontageToDepth.getCheckedRadioButtonId();
        View radioButtonFrontageToDepth = v.findViewById(selectedIdFrontageToDepth);
        int idx3 = rgFrontageToDepth.indexOfChild(radioButtonFrontageToDepth);
        RadioButton r3 = (RadioButton)  rgFrontageToDepth.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonFrontageToDepth", selectedText3);
        hm.put("radioButtonFrontageToDepthId", Integer.toString(idx3));

        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        //DatabaseController.removeTable(TableGeneralForm.attachment);
        // DatabaseController.deleteColumnData("column1");

        ContentValues contentValues = new ContentValues();

        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(),pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column4.toString(), jsonArrayData.toString());
        JSONObject obj=new JSONObject();
        try {
            obj.put("id_new",pref.get(Constants.case_id));
            obj.put("page","4");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(),pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(),obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment,"id",pref.get(Constants.case_id));
    }

    public void selectDB() throws JSONException {
        sub_cat = DatabaseController.getTableOne("column4",pref.get(Constants.case_id));

        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column4"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
//                if (object.getString("radioButtonLevelOfLandd").equals("On road level")){
//                    ((RadioButton)rgLevelOfLand.getChildAt(0)).setChecked(true);
//                }
//                else if (object.getString("radioButtonLevelOfLandd").equals("Below road level")){
//                    ((RadioButton)rgLevelOfLand.getChildAt(1)).setChecked(true);
//                }
//                else if (object.getString("radioButtonLevelOfLandd").equals("Above road level")){
//                    ((RadioButton)rgLevelOfLand.getChildAt(3)).setChecked(true);
//                }
//                else if (object.getString("radioButtonLevelOfLandd").equals("Low lying land")){
//                    ((RadioButton)rgLevelOfLand.getChildAt(5)).setChecked(true);
//                }
//                else if (object.getString("radioButtonLevelOfLandd").equals("Not Applicable")){
//                    ((RadioButton)rgLevelOfLand.getChildAt(6)).setChecked(true);
//                }
                if (object.has("radioButtonLevelOfLanddId")){
                    ((RadioButton)rgLevelOfLand.getChildAt(Integer.parseInt(object.getString("radioButtonLevelOfLanddId")))).setChecked(true);
                }
                if (object.has("radioButtonFrontageToDepthId")){
                    ((RadioButton)rgFrontageToDepth.getChildAt(Integer.parseInt(object.getString("radioButtonFrontageToDepthId")))).setChecked(true);
                }


//                if (object.getString("radioButtonFrontageToDepth").equalsIgnoreCase("Normal frontage")){
//                    ((RadioButton)rgFrontageToDepth.getChildAt(0)).setChecked(true);
//                }
//                else if (object.getString("radioButtonFrontageToDepth").equalsIgnoreCase("Less frontage")){
//                    ((RadioButton)rgFrontageToDepth.getChildAt(2)).setChecked(true);
//                }
//                else if (object.getString("radioButtonFrontageToDepth").equalsIgnoreCase("Large frontage")){
//                    ((RadioButton)rgFrontageToDepth.getChildAt(4)).setChecked(true);
//                }
//                else if (object.getString("radioButtonFrontageToDepth").equalsIgnoreCase("Not Applicable")){
//                    ((RadioButton)rgFrontageToDepth.getChildAt(6)).setChecked(true);
//                }


                etBelowRoadLevel.setText(object.getString("belowRoadDepth"));
                etAboveRoadLevel.setText(object.getString("aboveRoadHeight"));

                etNormalFrontage.setText(object.getString("normalFrontageRatio"));
                etLessFrontage.setText(object.getString("lessFrontageRatio"));
                etLargeFrontage.setText(object.getString("largeFrontageRatio"));

                //CheckBox Location Consideration
                if (object.getString("cbPlainLand").equals("1")){
                    cbPlainLand.setChecked(true);
                }else {
                    cbPlainLand.setChecked(false);
                }
                if (object.getString("cbSolidLand").equals("1")){
                    cbSolidLand.setChecked(true);
                }else {
                    cbSolidLand.setChecked(false);
                }
                if (object.getString("cbRockyLand").equals("1")){
                    cbRockyLand.setChecked(true);
                }else {
                    cbRockyLand.setChecked(false);
                }
                if (object.getString("cbMarshLand").equals("1")){
                    cbMarshLand.setChecked(true);
                }else {
                    cbMarshLand.setChecked(false);
                }
                if (object.getString("cbReclaimedLand").equals("1")){
                    cbReclaimedLand.setChecked(true);
                }else {
                    cbReclaimedLand.setChecked(false);
                }
                if (object.getString("cbWaterLogged").equals("1")){
                    cbWaterLogged.setChecked(true);
                }else {
                    cbWaterLogged.setChecked(false);
                }
                if (object.getString("cbTerrainLand").equals("1")){
                    cbTerrainLand.setChecked(true);
                }else {
                    cbTerrainLand.setChecked(false);
                }
                if (object.getString("cbLandLocked").equals("1")){
                    cbLandLocked.setChecked(true);
                }else {
                    cbLandLocked.setChecked(false);
                }
                if (object.getString("cbSandyLand").equals("1")){
                    cbSandyLand.setChecked(true);
                }else {
                    cbSandyLand.setChecked(false);
                }
                if (object.getString("cbDryLand").equals("1")){
                    cbDryLand.setChecked(true);
                }else {
                    cbDryLand.setChecked(false);
                }
                if (object.getString("cbNotApp").equals("1")){
                    cbNotApp.setChecked(true);
                }else {
                    cbNotApp.setChecked(false);
                }


                if (object.has("radioButtonShapeOfTheLanddId")){
                    ((RadioButton)rgShapeOfTheLand.getChildAt(Integer.parseInt(object.getString("radioButtonShapeOfTheLanddId")))).setChecked(true);
                }
                else {


                    if (selectedText.equalsIgnoreCase("Square")) {
                        ((RadioButton) rgShapeOfTheLand.getChildAt(0)).setChecked(true);
                    } else if (selectedText.equalsIgnoreCase("Rectangle")) {
                        ((RadioButton) rgShapeOfTheLand.getChildAt(1)).setChecked(true);
                    } else if (selectedText.equalsIgnoreCase("Trapezium")) {
                        ((RadioButton) rgShapeOfTheLand.getChildAt(2)).setChecked(true);
                    } else if (selectedText.equalsIgnoreCase("Triangle")) {
                        ((RadioButton) rgShapeOfTheLand.getChildAt(3)).setChecked(true);
                    } else if (selectedText.equalsIgnoreCase("Trapezoid")) {
                        ((RadioButton) rgShapeOfTheLand.getChildAt(4)).setChecked(true);
                    } else if (selectedText.equalsIgnoreCase("Irregular")) {
                        ((RadioButton) rgShapeOfTheLand.getChildAt(5)).setChecked(true);
                    } else if (selectedText.equals("Not Applicable")) {
                        ((RadioButton) rgShapeOfTheLand.getChildAt(7)).setChecked(true);
                        for (int c = 0; c < rgShapeOfTheLand.getChildCount(); c++) {
                            ((RadioButton) rgShapeOfTheLand.getChildAt(c)).setClickable(true);

                        }
                    }

                }


//                if (object.getString("radioButtonShapeOfTheLandd").equals("Square")){
//                    ((RadioButton)rgShapeOfTheLand.getChildAt(0)).setChecked(true);
//                }
//                else if (object.getString("radioButtonShapeOfTheLandd").equals("Rectangle")){
//                    ((RadioButton)rgShapeOfTheLand.getChildAt(1)).setChecked(true);
//                }
//                else if (object.getString("radioButtonShapeOfTheLandd").equals("Trapezium")){
//                    ((RadioButton)rgShapeOfTheLand.getChildAt(2)).setChecked(true);
//                }
//                else if (object.getString("radioButtonShapeOfTheLandd").equals("Triangle")){
//                    ((RadioButton)rgShapeOfTheLand.getChildAt(3)).setChecked(true);
//                }
//                else if (object.getString("radioButtonShapeOfTheLandd").equals("Trapezoid")){
//                    ((RadioButton)rgShapeOfTheLand.getChildAt(4)).setChecked(true);
//                }
//                else if (object.getString("radioButtonShapeOfTheLandd").equals("Irregular")){
//                    ((RadioButton)rgShapeOfTheLand.getChildAt(5)).setChecked(true);
//                }
//                else if (object.getString("radioButtonShapeOfTheLandd").equals("Not Applicable")){
//                    ((RadioButton)rgShapeOfTheLand.getChildAt(7)).setChecked(true);
//                }




                // String projectName = object.getString("projectName");
                // Log.e("!!!projectName", projectName);
            }

        }
    }



    public void condition(){

        /*rbBelowRoad.setChecked(true);
        rlBelowRoadLevel.setVisibility(View.VISIBLE);
*/
//        rgShapeOfTheLand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                RadioButton radioButton = (RadioButton) v.findViewById(i);
//                int a = radioGroup.indexOfChild(radioButton);
//
//                Log.v("getcheckk", String.valueOf(a));
//
//                switch (a) {
//                    case 5:
//                        rlSpinnerSides.setVisibility(View.VISIBLE);
//                        break;
//                    default:
//                        rlSpinnerSides.setVisibility(View.GONE);
//                        break;
//                }
//            }
//        });

        rgLevelOfLand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int a = radioGroup.indexOfChild(radioButton);
                Log.v("getcheckk", String.valueOf(a));

                switch (a){
                    case 1:
                        rlBelowRoadLevel.setVisibility(View.VISIBLE);
                        rlAboveRoadLevel.setVisibility(View.GONE);
                        break;
                    case 3:
                        rlAboveRoadLevel.setVisibility(View.VISIBLE);
                        rlBelowRoadLevel.setVisibility(View.GONE);
                        break;
                    default:
                        rlAboveRoadLevel.setVisibility(View.GONE);
                        rlBelowRoadLevel.setVisibility(View.GONE);
                        break;

                }
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
                    case R.id.general_one:
                        ((Dashboard)mActivity).displayView(6);
                        /*intent=new Intent(GeneralForm3.this, GeneralForm1.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_two:
                        ((Dashboard)mActivity).displayView(7);
                        return true;

                    case R.id.general_three:
                        ((Dashboard)mActivity).displayView(8);
                        return true;

                    case R.id.general_four:
                        ((Dashboard)mActivity).displayView(9);
                         return true;

                    case R.id.general_five:
                        ((Dashboard)mActivity).displayView(10);
                        return true;

                    case R.id.general_six:
                        ((Dashboard)mActivity).displayView(11);
                        return true;

                    case R.id.general_seven:
                        ((Dashboard)mActivity).displayView(12);
                        return true;

                    case R.id.general_eight:
                        ((Dashboard)mActivity).displayView(13);
                        return true;

                    case R.id.general_nine:
                        ((Dashboard)mActivity).displayView(14);
                        return true;

                    case R.id.general_ten:
                        ((Dashboard)mActivity).displayView(15);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
