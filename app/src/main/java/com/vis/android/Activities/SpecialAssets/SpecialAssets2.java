package com.vis.android.Activities.SpecialAssets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vis.android.Activities.SpecialAssets.SpecialAssets1.hm;

public class SpecialAssets2 extends AppCompatActivity implements View.OnClickListener{
TextView next;
    RelativeLayout back;
    Intent intent;

    EditText etLegalOwnerNames,etPropertyPurchaser,etPropertyAddress,etPresentResidence,etNorth,etSouth,etEast,etWest,etLandmark,etWardName,etZoneName;
    EditText etName,etWidth,etDistanceFromProp,etApproachRoadName,etSchool,etHospital,etMarket,etMetro,etRailwayStation,etAirport,etAnyNewDevelopment;

    CheckBox cbWithinMainCity,cbWithinGoodUrban,cbWithinSociety,cbHighlyPosh,cbVeryGood,cbGood,cbOrdinary,cbInInteriors,cbRemoteArea,cbBackward,cbPoor;
    CheckBox cbParkFacing,cbPoolFacing,cbRoadFacing,cbEntranceNorth,cbSunlightFacing;

    int cbWithinMainCityCheck,cbWithinGoodUrbanCheck,cbWithinSocietyCheck,cbHighlyPoshCheck,cbVeryGoodCheck,cbGoodCheck,cbOrdinaryCheck,cbInInteriorsCheck;
    int cbRemoteAreaCheck,cbBackwardCheck,cbPoorCheck,cbParkFacingCheck,cbPoolFacingCheck,cbRoadFacingCheck,cbEntranceNorthCheck,cbSunlightFacingCheck;

    RadioGroup rgPropertyConstitution,rgCharacteristicsOfLocality,rgJurisdictionLimits,rgJurisdictionDevelopment,rgMunicipalCorporation,rgCategoryOfSociety,rgUtilities;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets2);
        getid();
        setListener();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getid(){
        next=(TextView) findViewById(R.id.next);
        back=(RelativeLayout)findViewById(R.id.rl_back);

        //EditText
        etLegalOwnerNames = findViewById(R.id.etLegalOwnerNames);
        etPropertyPurchaser = findViewById(R.id.etPropertyPurchaser);
        etPropertyAddress = findViewById(R.id.etPropertyAddress);
        etPresentResidence = findViewById(R.id.etPresentResidence);
        etNorth = findViewById(R.id.etNorth);
        etSouth = findViewById(R.id.etSouth);
        etEast = findViewById(R.id.etEast);
        etWest = findViewById(R.id.etWest);
        etLandmark = findViewById(R.id.etLandmark);
        etWardName = findViewById(R.id.etWardName);
        etZoneName = findViewById(R.id.etZoneName);
        etName = findViewById(R.id.etName);
        etWidth = findViewById(R.id.etWidth);
        etDistanceFromProp = findViewById(R.id.etDistanceFromProp);
        etApproachRoadName = findViewById(R.id.etApproachRoadName);
        etSchool = findViewById(R.id.etSchool);
        etHospital = findViewById(R.id.etHospital);
        etMarket = findViewById(R.id.etMarket);
        etMetro = findViewById(R.id.etMetro);
        etRailwayStation = findViewById(R.id.etRailwayStation);
        etAirport = findViewById(R.id.etAirport);
        etAnyNewDevelopment = findViewById(R.id.etAnyNewDevelopment);


        //CheckBox
        cbWithinMainCity = findViewById(R.id.cbWithinMainCity);
        cbWithinGoodUrban = findViewById(R.id.cbWithinGoodUrban);
        cbWithinSociety = findViewById(R.id.cbWithinSociety);
        cbHighlyPosh = findViewById(R.id.cbHighlyPosh);
        cbVeryGood = findViewById(R.id.cbVeryGood);
        cbGood = findViewById(R.id.cbGood);
        cbOrdinary = findViewById(R.id.cbOrdinary);
        cbInInteriors = findViewById(R.id.cbInInteriors);
        cbRemoteArea = findViewById(R.id.cbRemoteArea);
        cbBackward = findViewById(R.id.cbBackward);
        cbPoor = findViewById(R.id.cbPoor);
        cbParkFacing = findViewById(R.id.cbParkFacing);
        cbPoolFacing = findViewById(R.id.cbPoolFacing);
        cbRoadFacing = findViewById(R.id.cbRoadFacing);
        cbEntranceNorth = findViewById(R.id.cbEntranceNorth);
        cbSunlightFacing = findViewById(R.id.cbSunlightFacing);


        //RadioGroup
        rgPropertyConstitution = findViewById(R.id.rgPropertyConstitution);
        rgCharacteristicsOfLocality = findViewById(R.id.rgCharacteristicsOfLocality);
        rgJurisdictionLimits = findViewById(R.id.rgJurisdictionLimits);
        rgJurisdictionDevelopment = findViewById(R.id.rgJurisdictionDevelopment);
        rgMunicipalCorporation = findViewById(R.id.rgMunicipalCorporation);
        rgCategoryOfSociety = findViewById(R.id.rgCategoryOfSociety);
        rgUtilities = findViewById(R.id.rgUtilities);





    }

    public void setListener(){
        next.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                validation();
                break;

            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }

    public void validation(){
        if (etLegalOwnerNames.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Legal Owner Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Legal Owner Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLegalOwnerNames.requestFocus();
        }
        else if (etPropertyPurchaser.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Property Purchaser Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Property Purchaser Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPropertyPurchaser.requestFocus();
        }
        else if (etPropertyAddress.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Property Address under Valuation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Property Address under Valuation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPropertyAddress.requestFocus();
        }
        else if (etPresentResidence.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Present Residence Address of the Owner/Purchaser", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Present Residence Address of the Owner/Purchaser", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPresentResidence.requestFocus();
        }
        else if (rgPropertyConstitution.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Property Constitution", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Property Constitution", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etNorth.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter North Adjoining Property", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter North Adjoining Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNorth.requestFocus();
        }
        else if (etSouth.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter South Adjoining Property", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter South Adjoining Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSouth.requestFocus();
        }
        else if (etEast.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter East Adjoining Property", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter East Adjoining Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etEast.requestFocus();
        }
        else if (etWest.getText().toString().isEmpty()) {
         //   Toast.makeText(this, "Please Enter West Adjoining Property", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter West Adjoining Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etWest.requestFocus();
        }
        else if (etLandmark.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Landmark", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Landmark", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLandmark.requestFocus();
        }
        else if (etWardName.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Ward Name/No.", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Ward Name/No.", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etWardName.requestFocus();
        }
        else if (etZoneName.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Zone Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Zone Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etZoneName.requestFocus();
        }
        else if (etName.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Main Road Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Main Road Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etName.requestFocus();
        }
        else if (etWidth.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Main Road Width", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Main Road Width", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etWidth.requestFocus();
        }
        else if (etDistanceFromProp.getText().toString().isEmpty()) {
         //   Toast.makeText(this, "Please Enter Main Road Distance from Property", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Main Road Distance from Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDistanceFromProp.requestFocus();
        }
        else if (etApproachRoadName.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Approach Road Name and Width", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Approach Road Name and Width", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etApproachRoadName.requestFocus();
        }
        else if (!cbWithinMainCity.isChecked()
                && !cbWithinSociety.isChecked()
                && !cbWithinGoodUrban.isChecked()
                && !cbHighlyPosh.isChecked()
                && !cbVeryGood.isChecked()
                && !cbGood.isChecked()
                && !cbOrdinary.isChecked()
                && !cbInInteriors.isChecked()
                && !cbRemoteArea.isChecked()
                && !cbBackward.isChecked()
                && !cbPoor.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Location Consideration of the Society", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (!cbParkFacing.isChecked()
                && !cbPoolFacing.isChecked()
                && !cbRoadFacing.isChecked()
                && !cbEntranceNorth.isChecked()
                && !cbSunlightFacing.isChecked()) {
         //   Toast.makeText(this, "Please Select Location of the Flat", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Location of the Flat", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgCharacteristicsOfLocality.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Characteristics of the Locality", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Characteristics of the Locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etSchool.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Category of Society: School", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Category of Society: School", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSchool.requestFocus();
        }
        else if (etHospital.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Category of Society: Hospital", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Category of Society: Hospital", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etHospital.requestFocus();
        }
        else if (etMarket.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Category of Society: Market", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Category of Society: Market", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMarket.requestFocus();
        }
        else if (etMetro.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Category of Society: Metro", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Category of Society: Metro", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMetro.requestFocus();
        }
        else if (etRailwayStation.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Category of Society: Railway Station", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Category of Society: Railway Station", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRailwayStation.requestFocus();
        }
        else if (etAirport.getText().toString().isEmpty()) {
        //    Toast.makeText(this, "Please Enter Category of Society: Airport", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Category of Society: Airport", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAirport.requestFocus();
        }
        else if (etAnyNewDevelopment.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Any New Development in surrounding area", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Any New Development in surrounding area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAnyNewDevelopment.requestFocus();
        }
        else if (rgJurisdictionLimits.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Jurisdiction Limit", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Jurisdiction Limit", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgJurisdictionDevelopment.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Jurisdiction Development Authority Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Jurisdiction Development Authority Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgMunicipalCorporation.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Municipal Corporation Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Municipal Corporation Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgCategoryOfSociety.getCheckedRadioButtonId() == -1){
         //   Toast.makeText(this, "Please Select Category of Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Category of Society", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgUtilities.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Utilities", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Utilities", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        else
        {
            putIntoHm();
            Intent intent=new Intent(SpecialAssets2.this, SpecialAssets3.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm(){


        hm.put("legalOwnerName", etLegalOwnerNames.getText().toString());
        hm.put("propertyPurchaserName", etPropertyPurchaser.getText().toString());
        hm.put("propertyAddress", etPropertyAddress.getText().toString());
        hm.put("propertyResidence", etPresentResidence.getText().toString());
        hm.put("adjoiningNorth", etNorth.getText().toString());
        hm.put("adjoiningSouth", etSouth.getText().toString());
        hm.put("adjoiningEast", etEast.getText().toString());
        hm.put("adjoiningWest", etWest.getText().toString());
        hm.put("adjoiningLandmark", etLandmark.getText().toString());
        hm.put("adjoiningWardName", etWardName.getText().toString());
        hm.put("adjoiningZoneName", etZoneName.getText().toString());
        hm.put("mainRoadName", etName.getText().toString());
        hm.put("mainRoadWidth", etWidth.getText().toString());
        hm.put("mainRoadDistance", etDistanceFromProp.getText().toString());
        hm.put("approachRoadNameAndWidth", etApproachRoadName.getText().toString());
        hm.put("categorySchool", etSchool.getText().toString());
        hm.put("categoryHospital", etHospital.getText().toString());
        hm.put("categoryMarket", etMarket.getText().toString());
        hm.put("categoryMetro", etMetro.getText().toString());
        hm.put("categoryRailwayStation", etRailwayStation.getText().toString());
        hm.put("categoryAirport", etAirport.getText().toString());
        hm.put("anyNewDevelopmentInSurr", etAnyNewDevelopment.getText().toString());

        ////CheckBox Location Consideration
        if (cbWithinMainCity.isChecked()){
            cbWithinMainCityCheck = 1;
            hm.put("cbWithinMainCity", String.valueOf(cbWithinMainCityCheck));
        }else
        {
            cbWithinMainCityCheck = 0;
            hm.put("cbWithinMainCity", String.valueOf(cbWithinMainCityCheck));
        }
        if (cbWithinGoodUrban.isChecked()){
            cbWithinGoodUrbanCheck = 1;
            hm.put("cbWithinGoodUrban", String.valueOf(cbWithinGoodUrbanCheck));
        }
        else
        {
            cbWithinGoodUrbanCheck = 0;
            hm.put("cbWithinGoodUrban", String.valueOf(cbWithinGoodUrbanCheck));
        }
        if (cbWithinSociety.isChecked()){
            cbWithinSocietyCheck = 1;
            hm.put("cbWithinSociety", String.valueOf(cbWithinSocietyCheck));
        }
        else
        {
            cbWithinSocietyCheck = 0;
            hm.put("cbWithinSociety", String.valueOf(cbWithinSocietyCheck));
        }
        if (cbHighlyPosh.isChecked()) {
            cbHighlyPoshCheck = 1;
            hm.put("cbHighlyPosh", String.valueOf(cbHighlyPoshCheck));
        }
        else
        {
            cbHighlyPoshCheck = 0;
            hm.put("cbHighlyPosh", String.valueOf(cbHighlyPoshCheck));
        }
        if (cbVeryGood.isChecked()){
            cbVeryGoodCheck = 1;
            hm.put("cbVeryGood", String.valueOf(cbVeryGoodCheck));
        }
        else
        {
            cbVeryGoodCheck = 0;
            hm.put("cbVeryGood", String.valueOf(cbVeryGoodCheck));
        }
        if (cbGood.isChecked()){
            cbGoodCheck = 1;
            hm.put("cbGood", String.valueOf(cbGoodCheck));
        }
        else
        {
            cbGoodCheck = 0;
            hm.put("cbGood", String.valueOf(cbGoodCheck));
        }
        if (cbOrdinary.isChecked()){
            cbOrdinaryCheck = 1;
            hm.put("cbOrdinary", String.valueOf(cbOrdinaryCheck));
        }else
        {
            cbOrdinaryCheck = 0;
            hm.put("cbOrdinary", String.valueOf(cbOrdinaryCheck));
        }
        if (cbInInteriors.isChecked()){
            cbInInteriorsCheck = 1;
            hm.put("cbInInteriors", String.valueOf(cbInInteriorsCheck));
        }
        else
        {
            cbInInteriorsCheck = 0;
            hm.put("cbInInteriors", String.valueOf(cbInInteriorsCheck));
        }
        if (cbRemoteArea.isChecked()){
            cbRemoteAreaCheck = 1;
            hm.put("cbRemoteArea", String.valueOf(cbRemoteAreaCheck));
        }
        else
        {
            cbRemoteAreaCheck = 0;
            hm.put("cbRemoteArea", String.valueOf(cbRemoteAreaCheck));
        }
        if (cbBackward.isChecked()) {
            cbBackwardCheck = 1;
            hm.put("cbBackward", String.valueOf(cbBackwardCheck));
        }
        else
        {
            cbBackwardCheck = 0;
            hm.put("cbBackward", String.valueOf(cbBackwardCheck));
        }
        if (cbPoor.isChecked()){
            cbPoorCheck = 1;
            hm.put("cbPoor", String.valueOf(cbPoorCheck));
        }
        else
        {
            cbPoorCheck = 0;
            hm.put("cbPoor", String.valueOf(cbPoorCheck));
        }

        //CheckBox Location of Flat
        if (cbParkFacing.isChecked()){
            cbParkFacingCheck = 1;
            hm.put("cbParkFacing", String.valueOf(cbParkFacingCheck));
        }else
        {
            cbParkFacingCheck = 0;
            hm.put("cbParkFacing", String.valueOf(cbParkFacingCheck));
        }
        if (cbPoolFacing.isChecked()){
            cbPoolFacingCheck = 1;
            hm.put("cbPoolFacing", String.valueOf(cbPoolFacingCheck));
        }
        else
        {
            cbPoolFacingCheck = 0;
            hm.put("cbPoolFacing", String.valueOf(cbPoolFacingCheck));
        }
        if (cbRoadFacing.isChecked()){
            cbRoadFacingCheck = 1;
            hm.put("cbRoadFacing", String.valueOf(cbRoadFacingCheck));
        }
        else
        {
            cbRoadFacingCheck = 0;
            hm.put("cbRoadFacing", String.valueOf(cbRoadFacingCheck));
        }
        if (cbEntranceNorth.isChecked()) {
            cbEntranceNorthCheck = 1;
            hm.put("cbEntranceNorth", String.valueOf(cbEntranceNorthCheck));
        }
        else
        {
            cbEntranceNorthCheck = 0;
            hm.put("cbEntranceNorth", String.valueOf(cbEntranceNorthCheck));
        }
        if (cbSunlightFacing.isChecked()){
            cbSunlightFacingCheck = 1;
            hm.put("cbSunlightFacing", String.valueOf(cbSunlightFacingCheck));
        }
        else
        {
            cbSunlightFacingCheck = 0;
            hm.put("cbSunlightFacing", String.valueOf(cbSunlightFacingCheck));
        }


        //((RadioButton) rbtnGrp.getChildAt(2)).setText("your radio button data");

        int selectedIdProprtyCnstutn = rgPropertyConstitution.getCheckedRadioButtonId();
        View radioButtonProprtyCnstutn = findViewById(selectedIdProprtyCnstutn);
        int idx = rgPropertyConstitution.indexOfChild(radioButtonProprtyCnstutn);
        RadioButton r = (RadioButton)  rgPropertyConstitution.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonProprtyCnstutn", selectedText);

        int selectedIdCharacteristic = rgCharacteristicsOfLocality.getCheckedRadioButtonId();
        View radioButtonCharacteristic = findViewById(selectedIdCharacteristic);
        int idx2 = rgCharacteristicsOfLocality.indexOfChild(radioButtonCharacteristic);
        RadioButton r2 = (RadioButton)  rgCharacteristicsOfLocality.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonCharacteristic", selectedText2);

        int selectedIdJurisdictionLimit = rgJurisdictionLimits.getCheckedRadioButtonId();
        View radioButtonJurisdictionLimit = findViewById(selectedIdJurisdictionLimit);
        int idx3 = rgJurisdictionLimits.indexOfChild(radioButtonJurisdictionLimit);
        RadioButton r3 = (RadioButton)  rgJurisdictionLimits.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonJurisdictionLimit", selectedText3);

        int selectedIdJurisdictionDevelopment = rgJurisdictionDevelopment.getCheckedRadioButtonId();
        View radioButtonJurisdictionDevelopment = findViewById(selectedIdJurisdictionDevelopment);
        int idx4 = rgJurisdictionDevelopment.indexOfChild(radioButtonJurisdictionDevelopment);
        RadioButton r4 = (RadioButton)  rgJurisdictionDevelopment.getChildAt(idx4);
        String selectedText4 = r4.getText().toString();
        hm.put("radioButtonJurisdictionDevelopment", selectedText4);

        int selectedIdMunicipalCorp = rgMunicipalCorporation.getCheckedRadioButtonId();
        View radioButtonMunicipalCorp = findViewById(selectedIdMunicipalCorp);
        int idx5 = rgMunicipalCorporation.indexOfChild(radioButtonMunicipalCorp);
        RadioButton r5 = (RadioButton)  rgMunicipalCorporation.getChildAt(idx5);
        String selectedText5 = r5.getText().toString();
        hm.put("radioButtonMunicipalCorp", selectedText5);

        int selectedIdCategoryOfSociety = rgCategoryOfSociety.getCheckedRadioButtonId();
        View radioButtonCategoryOfSociety = findViewById(selectedIdCategoryOfSociety);
        int idx6 = rgCategoryOfSociety.indexOfChild(radioButtonCategoryOfSociety);
        RadioButton r6 = (RadioButton)  rgCategoryOfSociety.getChildAt(idx6);
        String selectedText6 = r6.getText().toString();
        hm.put("radioButtonCategoryOfSociety", selectedText6);

        int selectedIdUtilities = rgUtilities.getCheckedRadioButtonId();
        View radioButtonUtilities = findViewById(selectedIdUtilities);
        int idx7 = rgUtilities.indexOfChild(radioButtonUtilities);
        RadioButton r7 = (RadioButton)  rgUtilities.getChildAt(idx7);
        String selectedText7 = r7.getText().toString();
        hm.put("radioButtonUtilities", selectedText7);
    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getSpecialAssets();
        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                etLegalOwnerNames.setText(object.getString("legalOwnerName"));
                etPropertyPurchaser.setText(object.getString("propertyPurchaserName"));
                etPropertyAddress.setText(object.getString("propertyAddress"));
                etPresentResidence.setText(object.getString("propertyResidence"));
                etNorth.setText(object.getString("adjoiningNorth"));
                etSouth.setText(object.getString("adjoiningSouth"));
                etEast.setText(object.getString("adjoiningEast"));
                etWest.setText(object.getString("adjoiningWest"));
                etLandmark.setText(object.getString("adjoiningLandmark"));
                etWardName.setText(object.getString("adjoiningWardName"));
                etZoneName.setText(object.getString("adjoiningZoneName"));
                etName.setText(object.getString("mainRoadName"));
                etWidth.setText(object.getString("mainRoadWidth"));
                etDistanceFromProp.setText(object.getString("mainRoadDistance"));
                etApproachRoadName.setText(object.getString("approachRoadNameAndWidth"));
                etSchool.setText(object.getString("categorySchool"));
                etHospital.setText(object.getString("categoryHospital"));
                etMarket.setText(object.getString("categoryMarket"));
                etMetro.setText(object.getString("categoryMetro"));
                etRailwayStation.setText(object.getString("categoryRailwayStation"));
                etAirport.setText(object.getString("categoryAirport"));
                etAnyNewDevelopment.setText(object.getString("anyNewDevelopmentInSurr"));

                //CheckBox Location Consideration
                if (object.getString("cbWithinMainCity").equals("1")){
                    cbWithinMainCity.setChecked(true);
                }else {
                    cbWithinMainCity.setChecked(false);
                }
                if (object.getString("cbWithinGoodUrban").equals("1")){
                    cbWithinGoodUrban.setChecked(true);
                }else {
                    cbWithinGoodUrban.setChecked(false);
                }
                if (object.getString("cbWithinSociety").equals("1")){
                    cbWithinSociety.setChecked(true);
                }else {
                    cbWithinSociety.setChecked(false);
                }
                if (object.getString("cbHighlyPosh").equals("1")){
                    cbHighlyPosh.setChecked(true);
                }else {
                    cbHighlyPosh.setChecked(false);
                }
                if (object.getString("cbVeryGood").equals("1")){
                    cbVeryGood.setChecked(true);
                }else {
                    cbVeryGood.setChecked(false);
                }
                if (object.getString("cbGood").equals("1")){
                    cbGood.setChecked(true);
                }else {
                    cbGood.setChecked(false);
                }
                if (object.getString("cbOrdinary").equals("1")){
                    cbOrdinary.setChecked(true);
                }else {
                    cbOrdinary.setChecked(false);
                }
                if (object.getString("cbInInteriors").equals("1")){
                    cbInInteriors.setChecked(true);
                }else {
                    cbInInteriors.setChecked(false);
                }
                if (object.getString("cbRemoteArea").equals("1")){
                    cbRemoteArea.setChecked(true);
                }else {
                    cbRemoteArea.setChecked(false);
                }
                if (object.getString("cbBackward").equals("1")){
                    cbBackward.setChecked(true);
                }else {
                    cbBackward.setChecked(false);
                }
                if (object.getString("cbPoor").equals("1")){
                    cbPoor.setChecked(true);
                }else {
                    cbPoor.setChecked(false);
                }


                //CheckBox Location of Flat
                if (object.getString("cbParkFacing").equals("1")){
                    cbParkFacing.setChecked(true);
                }else {
                    cbParkFacing.setChecked(false);
                }
                if (object.getString("cbPoolFacing").equals("1")){
                    cbPoolFacing.setChecked(true);
                }else {
                    cbPoolFacing.setChecked(false);
                }
                if (object.getString("cbRoadFacing").equals("1")){
                    cbRoadFacing.setChecked(true);
                }else {
                    cbRoadFacing.setChecked(false);
                }
                if (object.getString("cbEntranceNorth").equals("1")){
                    cbEntranceNorth.setChecked(true);
                }else {
                    cbEntranceNorth.setChecked(false);
                }
                if (object.getString("cbSunlightFacing").equals("1")){
                    cbSunlightFacing.setChecked(true);
                }else {
                    cbSunlightFacing.setChecked(false);
                }



                if (object.getString("radioButtonProprtyCnstutn").equals("Free Hold")){
                    ((RadioButton)rgPropertyConstitution.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonProprtyCnstutn").equals("Lease Hold")){
                    ((RadioButton)rgPropertyConstitution.getChildAt(1)).setChecked(true);
                }



                if (object.getString("radioButtonCharacteristic").equals("Urban developed")){
                    ((RadioButton)rgCharacteristicsOfLocality.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonCharacteristic").equals("Urban developing")){
                    ((RadioButton)rgCharacteristicsOfLocality.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonCharacteristic").equals("Semi Urban")){
                    ((RadioButton)rgCharacteristicsOfLocality.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonCharacteristic").equals("Rural")){
                    ((RadioButton)rgCharacteristicsOfLocality.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonCharacteristic").equals("Backward")){
                    ((RadioButton)rgCharacteristicsOfLocality.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonCharacteristic").equals("Industrial")){
                    ((RadioButton)rgCharacteristicsOfLocality.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonCharacteristic").equals("Institutional")){
                    ((RadioButton)rgCharacteristicsOfLocality.getChildAt(6)).setChecked(true);
                }


                if (object.getString("radioButtonJurisdictionLimit").equals("Nagar Nigam")){
                    ((RadioButton)rgJurisdictionLimits.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionLimit").equals("Nagar Panchayat")){
                    ((RadioButton)rgJurisdictionLimits.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionLimit").equals("Gram Panchayat")){
                    ((RadioButton)rgJurisdictionLimits.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionLimit").equals("Nagar Palika Parishad")){
                    ((RadioButton)rgJurisdictionLimits.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionLimit").equals("Area not within any municipal limits")){
                    ((RadioButton)rgJurisdictionLimits.getChildAt(4)).setChecked(true);
                }


                if (object.getString("radioButtonJurisdictionDevelopment").equals("DDA")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("GDA")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("NOIDA")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("GNIDA")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("YEIDA")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("HUDA")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("KMDA")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("MDDA")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("Any other Development Authority")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(8)).setChecked(true);
                }
                else if (object.getString("radioButtonJurisdictionDevelopment").equals("Area not within any development authority limits")){
                    ((RadioButton)rgJurisdictionDevelopment.getChildAt(9)).setChecked(true);
                }


                if (object.getString("radioButtonMunicipalCorp").equals("NDMC")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("SDMC")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("EDMC")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("Ghaziabad Municipal Corporation")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("Gurgaon Municipal Corporation")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("Faridabad Municipal Corporation")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("Kolkata Municipal Corporation")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("Dehradun Municipal Corporation")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("Area not within any municipal limits")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(8)).setChecked(true);
                }
                else if (object.getString("radioButtonMunicipalCorp").equals("Any other Municipal Corporation/ Municipality")){
                    ((RadioButton)rgMunicipalCorporation.getChildAt(9)).setChecked(true);
                }


                if (object.getString("radioButtonCategoryOfSociety").equals("High End")){
                    ((RadioButton)rgCategoryOfSociety.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonCategoryOfSociety").equals("Normal")){
                    ((RadioButton)rgCategoryOfSociety.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonCategoryOfSociety").equals("Affordable Group Housing")){
                    ((RadioButton)rgCategoryOfSociety.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonCategoryOfSociety").equals("EWS")){
                    ((RadioButton)rgCategoryOfSociety.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonCategoryOfSociety").equals("HIG")){
                    ((RadioButton)rgCategoryOfSociety.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonCategoryOfSociety").equals("MIG")){
                    ((RadioButton)rgCategoryOfSociety.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonCategoryOfSociety").equals("LIG")){
                    ((RadioButton)rgCategoryOfSociety.getChildAt(6)).setChecked(true);
                }

                //////
                if (object.getString("radioButtonUtilities").equals("Lifts")){
                    ((RadioButton)rgUtilities.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonUtilities").equals("Garden")){
                    ((RadioButton)rgUtilities.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonUtilities").equals("Landscaping")){
                    ((RadioButton)rgUtilities.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonUtilities").equals("Swimming Pool")){
                    ((RadioButton)rgUtilities.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonUtilities").equals("Gym")){
                    ((RadioButton)rgUtilities.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonUtilities").equals("Club House")){
                    ((RadioButton)rgUtilities.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonUtilities").equals("Walk Trails")){
                    ((RadioButton)rgUtilities.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonUtilities").equals("Kids play zone")){
                    ((RadioButton)rgUtilities.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonUtilities").equals("100% Power Backup")){
                    ((RadioButton)rgUtilities.getChildAt(8)).setChecked(true);
                }


                // String projectName = object.getString("projectName");
                // Log.e("!!!projectName", projectName);
            }

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
