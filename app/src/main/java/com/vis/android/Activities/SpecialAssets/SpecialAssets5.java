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
import android.widget.TextView;
import android.widget.Toast;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vis.android.Activities.SpecialAssets.SpecialAssets1.hm;

public class SpecialAssets5 extends AppCompatActivity implements View.OnClickListener{
    TextView next;
    Intent intent;

    //EditText
    EditText etAgeOfBuilding, etBoundaryRunningMtr,etBoundaryHeight,etBoundaryWidth,etBoundaryFinish,etIntRoadsTypeOfConstruction,etIntRoadsLength,etIntRoadsWidth;
    EditText etACNumber,etACCapacity,etACMake,etACYearOfInstallation,etACCostOfCapi,etHvacMake,etHvacCapacity,etHvacYearOfInst,etHvacCostOfCapi,etLiftMake,etLiftCapacity;
    EditText etLiftYearOfInst,etLiftCostOfCapi,etPBNumber,etPBMake,etPBCapacity,etPBYearOfInst,etPBCostOfCapi,etPOFYearOfInst,etPOFCostOfCapi,etUOSCapacity,etUOSYearOfInst;
    EditText etUOSCostOfCapi,etPOSCapacity,etPOSYearOfInst,etPOSCostOfCapi,etPORCapacity,etPORYearOfInst,etPORCostOfCapi,etSpecialInst;

    //CheckBox
    CheckBox cbMaintenanceIssues,cbFinishingIssues,cbSeepageIssues,cbWaterSupplyIssues,cbElectricalIssues,cbStructuralIssues,cbVisibleCracks;
    CheckBox cbConstructionDoneWithoutMap,cbConstructionNotAsPer,cbExtraCovered,cbJoinedAdjacent,cbEncroachedAdjacent,cbAvailableWithin,cbOnGround,cbInBasement;
    CheckBox cbOnStilt,cbNotAvailableWithin,cbOnRoad,cbAcuteParking,cbWindows,cbSplit,cbCentralAir,cbWetRiser,cbAutomaticWater,cbFireHydrant,cbFireExting,cbNoFireFighting;

    int cbMaintenanceIssuesCheck,cbFinishingIssuesCheck,cbSeepageIssuesCheck,cbWaterSupplyIssuesCheck,cbElectricalIssuesCheck,cbStructuralIssuesCheck,cbVisibleCracksCheck;
    int cbConstructionDoneWithoutMapCheck,cbConstructionNotAsPerCheck,cbExtraCoveredCheck,cbJoinedAdjacentCheck,cbEncroachedAdjacentCheck,cbAvailableWithinCheck,cbOnGroundCheck,cbInBasementCheck;
    int cbOnStiltCheck,cbNotAvailableWithinCheck,cbOnRoadCheck,cbAcuteParkingCheck,cbWindowsCheck,cbSplitCheck,cbCentralAirCheck,cbWetRiserCheck,cbAutomaticWaterCheck,cbFireHydrantCheck,cbFireExtingCheck,cbNoFireFightingCheck;

    //RadioGroup
    RadioGroup rgWaterArrangements,rgFixedWoodenWork,rgMaintenanceOfTheBuilding,rgBoundaryWall,rgGardening,rgLift,rgPowerBackup,rgUseOfSpecial,rgProvisionOfSolar,rgProvisionOfRain;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets5);
        getid();
        setListener();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getid(){
        next=(TextView)findViewById(R.id.next);

        etAgeOfBuilding = findViewById(R.id.etAgeOfBuilding);
        etBoundaryRunningMtr = findViewById(R.id.etBoundaryRunningMtr);
        etBoundaryHeight = findViewById(R.id.etBoundaryHeight);
        etBoundaryWidth = findViewById(R.id.etBoundaryWidth);
        etBoundaryFinish = findViewById(R.id.etBoundaryFinish);
        etIntRoadsTypeOfConstruction = findViewById(R.id.etIntRoadsTypeOfConstruction);
        etIntRoadsLength = findViewById(R.id.etIntRoadsLength);
        etIntRoadsWidth = findViewById(R.id.etIntRoadsWidth);
        etACNumber = findViewById(R.id.etACNumber);
        etACCapacity = findViewById(R.id.etACCapacity);
        etACMake = findViewById(R.id.etACMake);
        etACYearOfInstallation = findViewById(R.id.etACYearOfInstallation);
        etACCostOfCapi = findViewById(R.id.etACCostOfCapi);
        etHvacMake = findViewById(R.id.etHvacMake);
        etHvacCapacity = findViewById(R.id.etHvacCapacity);
        etHvacYearOfInst = findViewById(R.id.etHvacYearOfInst);
        etHvacCostOfCapi = findViewById(R.id.etHvacCostOfCapi);
        etLiftMake = findViewById(R.id.etLiftMake);
        etLiftCapacity = findViewById(R.id.etLiftCapacity);
        etLiftYearOfInst = findViewById(R.id.etLiftYearOfInst);
        etLiftCostOfCapi = findViewById(R.id.etLiftCostOfCapi);
        etPBNumber = findViewById(R.id.etPBNumber);
        etPBMake = findViewById(R.id.etPBMake);
        etPBCapacity = findViewById(R.id.etPBCapacity);
        etPBYearOfInst = findViewById(R.id.etPBYearOfInst);
        etPOSYearOfInst = findViewById(R.id.etPOSYearOfInst);
        etPBCostOfCapi = findViewById(R.id.etPBCostOfCapi);
        etPOFYearOfInst = findViewById(R.id.etPOFYearOfInst);
        etPOFCostOfCapi = findViewById(R.id.etPOFCostOfCapi);
        etUOSCapacity = findViewById(R.id.etUOSCapacity);
        etUOSYearOfInst = findViewById(R.id.etUOSYearOfInst);
        etUOSCostOfCapi = findViewById(R.id.etUOSCostOfCapi);
        etPOSCapacity = findViewById(R.id.etPOSCapacity);
        etPOSCapacity = findViewById(R.id.etPOSCapacity);
        etPOSCostOfCapi = findViewById(R.id.etPOSCostOfCapi);
        etPORCapacity = findViewById(R.id.etPORCapacity);
        etPORYearOfInst = findViewById(R.id.etPORYearOfInst);
        etPORCostOfCapi = findViewById(R.id.etPORCostOfCapi);
        etSpecialInst = findViewById(R.id.etSpecialInst);


        cbMaintenanceIssues = findViewById(R.id.cbMaintenanceIssues);
        cbFinishingIssues = findViewById(R.id.cbFinishingIssues);
        cbSeepageIssues = findViewById(R.id.cbSeepageIssues);
        cbWaterSupplyIssues = findViewById(R.id.cbWaterSupplyIssues);
        cbElectricalIssues = findViewById(R.id.cbElectricalIssues);
        cbStructuralIssues = findViewById(R.id.cbStructuralIssues);
        cbVisibleCracks = findViewById(R.id.cbVisibleCracks);
        cbConstructionDoneWithoutMap = findViewById(R.id.cbConstructionDoneWithoutMap);
        cbConstructionNotAsPer = findViewById(R.id.cbConstructionNotAsPer);
        cbExtraCovered = findViewById(R.id.cbExtraCovered);
        cbJoinedAdjacent = findViewById(R.id.cbJoinedAdjacent);
        cbEncroachedAdjacent = findViewById(R.id.cbEncroachedAdjacent);
        cbAvailableWithin = findViewById(R.id.cbAvailableWithin);
        cbOnGround = findViewById(R.id.cbOnGround);
        cbInBasement = findViewById(R.id.cbInBasement);
        cbOnStilt = findViewById(R.id.cbOnStilt);
        cbNotAvailableWithin = findViewById(R.id.cbNotAvailableWithin);
        cbOnRoad = findViewById(R.id.cbOnRoad);
        cbAcuteParking = findViewById(R.id.cbAcuteParking);
        cbWindows = findViewById(R.id.cbWindows);
        cbSplit = findViewById(R.id.cbSplit);
        cbCentralAir = findViewById(R.id.cbCentralAir);
        cbWetRiser = findViewById(R.id.cbWetRiser);
        cbAutomaticWater = findViewById(R.id.cbAutomaticWater);
        cbFireHydrant = findViewById(R.id.cbFireHydrant);
        cbFireExting = findViewById(R.id.cbFireExting);
        cbNoFireFighting = findViewById(R.id.cbNoFireFighting);


        rgWaterArrangements = findViewById(R.id.rgWaterArrangements);
        rgFixedWoodenWork = findViewById(R.id.rgFixedWoodenWork);
        rgMaintenanceOfTheBuilding = findViewById(R.id.rgMaintenanceOfTheBuilding);
        rgBoundaryWall = findViewById(R.id.rgBoundaryWall);
        rgGardening = findViewById(R.id.rgGardening);
        rgLift = findViewById(R.id.rgLift);
        rgPowerBackup = findViewById(R.id.rgPowerBackup);
        rgUseOfSpecial = findViewById(R.id.rgUseOfSpecial);
        rgProvisionOfSolar = findViewById(R.id.rgProvisionOfSolar);
        rgProvisionOfRain = findViewById(R.id.rgProvisionOfRain);



    }
    public void setListener(){
        next.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                // Toast.makeText(this, "Thanks.Your survey has been successfully completed.", Toast.LENGTH_SHORT).show();
                validation();
                break;
        }
    }

    public void validation(){

        if (rgWaterArrangements.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Water Arrangements", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Water Arrangements", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgFixedWoodenWork.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Fixed Wooden Work", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Fixed Wooden Work", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etAgeOfBuilding.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Age of Building", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Age of Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAgeOfBuilding.requestFocus();
        }
        else if (rgMaintenanceOfTheBuilding.getCheckedRadioButtonId() == -1){
           // Toast.makeText(this, "Please Select Maintenance of the Building", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Maintenance of the Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (!cbMaintenanceIssues.isChecked()
                && !cbFinishingIssues.isChecked()
                && !cbSeepageIssues.isChecked()
                && !cbWaterSupplyIssues.isChecked()
                && !cbElectricalIssues.isChecked()
                && !cbStructuralIssues.isChecked()
                && !cbVisibleCracks.isChecked()) {
          //  Toast.makeText(this, "Please Select Any Defects in the Building", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Any Defects in the Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (!cbConstructionDoneWithoutMap.isChecked()
                && !cbConstructionNotAsPer.isChecked()
                && !cbExtraCovered.isChecked()
                && !cbJoinedAdjacent.isChecked()
                && !cbEncroachedAdjacent.isChecked()) {
            //Toast.makeText(this, "Please Select Any Defects in the Building", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Any Defects in the Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgBoundaryWall.getCheckedRadioButtonId() == -1){
           // Toast.makeText(this, "Please Select Boundary Wall", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Boundary Wall", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etBoundaryRunningMtr.getText().toString().isEmpty()) {
          //  Toast.makeText(this, "Please Enter Boundary Running Mtr.", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Boundary Running Mtr.", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBoundaryRunningMtr.requestFocus();
        }
        else if (etBoundaryHeight.getText().toString().isEmpty()) {
          //  Toast.makeText(this, "Please Enter Boundary Height", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Boundary Height", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBoundaryHeight.requestFocus();
        }
        else if (etBoundaryWidth.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Boundary Width", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Boundary Width", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBoundaryWidth.requestFocus();
        }
        else if (etBoundaryFinish.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Boundary Finish", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Boundary Finish", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBoundaryFinish.requestFocus();
        }
        else if (etIntRoadsTypeOfConstruction.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Internal Roads Type of Construction", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Internal Roads Type of Construction", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etIntRoadsTypeOfConstruction.requestFocus();
        }
        else if (etIntRoadsLength.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Internal Roads Length", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Internal Roads Length", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etIntRoadsLength.requestFocus();
        }
        else if (etIntRoadsWidth.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Internal Roads Width", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Internal Roads Width", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etIntRoadsWidth.requestFocus();
        }
        else if (rgGardening.getCheckedRadioButtonId() == -1){
           // Toast.makeText(this, "Please Select Gardening", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Gardening", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (!cbAvailableWithin.isChecked()
                && !cbOnGround.isChecked()
                && !cbInBasement.isChecked()
                && !cbOnStilt.isChecked()
                && !cbNotAvailableWithin.isChecked()
                && !cbOnRoad.isChecked()
                && !cbAcuteParking.isChecked()) {
           // Toast.makeText(this, "Please Select Parking Facilities", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Parking Facilities", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (!cbWindows.isChecked()
                && !cbSplit.isChecked()
                && !cbCentralAir.isChecked()) {
            //Toast.makeText(this, "Please Select Air Conditioning", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Air Conditioning", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etACNumber.getText().toString().isEmpty()) {
          //  Toast.makeText(this, "Please Enter AC Number", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter AC Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etACNumber.requestFocus();
        }
        else if (etACCapacity.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter AC Capacity", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter AC Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etACCapacity.requestFocus();
        }
        else if (etACMake.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter AC Make", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter AC Make", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etACMake.requestFocus();
        }
        else if (etACYearOfInstallation.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter AC Year of Installation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter AC Year of Installation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etACYearOfInstallation.requestFocus();
        }
        else if (etACCostOfCapi.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter AC Cost of Capitalization", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter AC Cost of Capitalization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etACCostOfCapi.requestFocus();
        }
        else if (etHvacMake.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter HVAC Make", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter HVAC Make", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etHvacMake.requestFocus();
        }
        else if (etHvacCapacity.getText().toString().isEmpty()) {
          //  Toast.makeText(this, "Please Enter HVAC Capacity", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter HVAC Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etHvacCapacity.requestFocus();
        }
        else if (etHvacYearOfInst.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter HVAC Year of Installation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter HVAC Year of Installation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etHvacYearOfInst.requestFocus();
        }
        else if (etHvacCostOfCapi.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter HVAC Cost of Capitalization", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter HVAC Cost of Capitalization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etHvacCostOfCapi.requestFocus();
        }
        else if (rgLift.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Lift/Elevators", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Lift/Elevators", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etLiftMake.getText().toString().isEmpty()) {
          //  Toast.makeText(this, "Please Enter Lift Make", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Lift Make", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLiftMake.requestFocus();
        }
        else if (etLiftCapacity.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Lift Capacity", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Lift Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLiftCapacity.requestFocus();
        }
        else if (etLiftYearOfInst.getText().toString().isEmpty()) {
          //  Toast.makeText(this, "Please Enter Lift Year of Installation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Lift Year of Installation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLiftYearOfInst.requestFocus();
        }
        else if (etLiftCostOfCapi.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Lift Cost of Capitalization", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Lift Cost of Capitalization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLiftCostOfCapi.requestFocus();
        }
        else if (rgPowerBackup.getCheckedRadioButtonId() == -1){
          //  Toast.makeText(this, "Please Select Power Backup", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Power Backup", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etPBNumber.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Power Backup Number", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPBNumber.requestFocus();
        }
        else if (etPBMake.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Power Backup Make", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Make", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPBMake.requestFocus();
        }
        else if (etPBCapacity.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Power Backup Capacity", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPBCapacity.requestFocus();
        }
        else if (etPBYearOfInst.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Power Backup Year of Installation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Year of Installation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPBYearOfInst.requestFocus();
        }
        else if (etPBCostOfCapi.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Power Backup Cost of Capitalization", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Cost of Capitalization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPBCostOfCapi.requestFocus();
        }
        else if (!cbWetRiser.isChecked()
                && !cbAutomaticWater.isChecked()
                && !cbFireHydrant.isChecked()
                && !cbFireExting.isChecked()
                && !cbNoFireFighting.isChecked()) {
           // Toast.makeText(this, "Please Select Provision of Firefighting", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Provision of Firefighting", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etPOFYearOfInst.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Provision of Firefighting Year of Installation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Provision of Firefighting Year of Installation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPOFYearOfInst.requestFocus();
        }
        else if (etPOFCostOfCapi.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Provision of Firefighting Cost of Capitalization", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Provision of Firefighting Cost of Capitalization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPOFCostOfCapi.requestFocus();
        }

        else if (rgUseOfSpecial.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Use of Special Green Building Techniques", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Use of Special Green Building Techniques", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        else if (etUOSCapacity.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Use of Special Green Building Techniques Capacity", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Use of Special Green Building Techniques Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etUOSCapacity.requestFocus();
        }
        else if (etUOSYearOfInst.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Use of Special Green Building Techniques Year of Installation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Use of Special Green Building Techniques Year of Installation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etUOSYearOfInst.requestFocus();
        }
        else if (etUOSCostOfCapi.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Use of Special Green Building Techniques Cost of Capitalization", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Use of Special Green Building Techniques Cost of Capitalization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etUOSCostOfCapi.requestFocus();
        }

        else if (rgProvisionOfSolar.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Provision of Solar Panels", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Provision of Solar Panels", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etPOSCapacity.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Provision of Solar Panel Capacity", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Provision of Solar Panel Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPOSCapacity.requestFocus();
        }
        else if (etPOSYearOfInst.getText().toString().isEmpty()) {
          //  Toast.makeText(this, "Please Enter Provision of Solar Panel Year of Installation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Provision of Solar Panel Year of Installation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPOSYearOfInst.requestFocus();
        }
        else if (etPOSCostOfCapi.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Provision of Solar Panel Cost of Capitalization", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Provision of Solar Panel Cost of Capitalization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPOSCostOfCapi.requestFocus();
        }
        else if (rgProvisionOfRain.getCheckedRadioButtonId() == -1){
           // Toast.makeText(this, "Please Select Provision of Rainwater Harvesting", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Provision of Rainwater Harvesting", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etPORCapacity.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Provision of Rainwater Harvesting Capacity", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Provision of Rainwater Harvesting Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPORCapacity.requestFocus();
        }
        else if (etPORYearOfInst.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Provision of Rainwater Harvesting Year of Installation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Provision of Rainwater Harvesting Year of Installation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPORYearOfInst.requestFocus();
        }
        else if (etPORCostOfCapi.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Provision of Rainwater Harvesting Cost of Capitalization", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Provision of Rainwater Harvesting Cost of Capitalization", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPORCostOfCapi.requestFocus();
        }
        else
        {
            putIntoHm();
            Intent intent=new Intent(SpecialAssets5.this,SpecialAssetsDynamicView.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm(){

        hm.put("ageOfBuilding", etAgeOfBuilding.getText().toString());
        hm.put("boundaryRunningMtr", etBoundaryRunningMtr.getText().toString());
        hm.put("boundaryHeight", etBoundaryHeight.getText().toString());
        hm.put("boundaryWidth", etBoundaryWidth.getText().toString());
        hm.put("boundaryFinish", etBoundaryFinish.getText().toString());
        hm.put("intRoadsTypeOfCons", etIntRoadsTypeOfConstruction.getText().toString());
        hm.put("intRoadsLength", etIntRoadsLength.getText().toString());
        hm.put("intRoadsWidth", etIntRoadsWidth.getText().toString());
        hm.put("acNumber", etACNumber.getText().toString());
        hm.put("acCapacity", etACCapacity.getText().toString());
        hm.put("acMake", etACMake.getText().toString());
        hm.put("acYearOfInst", etACYearOfInstallation.getText().toString());
        hm.put("acCostOfCapi", etACCostOfCapi.getText().toString());
        hm.put("hvacMake", etHvacMake.getText().toString());
        hm.put("hvacCapacity", etHvacCapacity.getText().toString());
        hm.put("hvacYearOfInst", etHvacYearOfInst.getText().toString());
        hm.put("hvacCostOfCapi", etHvacCostOfCapi.getText().toString());
        hm.put("liftMake", etLiftMake.getText().toString());
        hm.put("liftCapacity", etLiftCapacity.getText().toString());
        hm.put("liftYearOfInst", etLiftYearOfInst.getText().toString());
        hm.put("listCostOfCapi", etLiftCostOfCapi.getText().toString());
        hm.put("pbNumber", etPBNumber.getText().toString());
        hm.put("pbMake", etPBMake.getText().toString());
        hm.put("pbCapacity", etPBCapacity.getText().toString());
        hm.put("pbYearOfInst", etPBYearOfInst.getText().toString());
        hm.put("pbCostOfCapi", etPBCostOfCapi.getText().toString());
        hm.put("pofYearOfInst", etPOFYearOfInst.getText().toString());
        hm.put("pofCostOfCapi", etPOFCostOfCapi.getText().toString());
        hm.put("uosCapacity", etUOSCapacity.getText().toString());
        hm.put("uosYearOfInst", etUOSYearOfInst.getText().toString());
        hm.put("uosCostOfCapi", etUOSCostOfCapi.getText().toString());
        hm.put("posCapacity", etPOSCapacity.getText().toString());
        hm.put("posYearOfInst", etPOSYearOfInst.getText().toString());
        hm.put("posCostOfCapi", etPOSCostOfCapi.getText().toString());
        hm.put("porCapacity", etPORCapacity.getText().toString());
        hm.put("porYearOfInst", etPORYearOfInst.getText().toString());
        hm.put("porCostOfCapi", etPORCostOfCapi.getText().toString());
        hm.put("specialInst", etSpecialInst.getText().toString());



        //CheckBox Any Defects
        if (cbMaintenanceIssues.isChecked()){
            cbMaintenanceIssuesCheck = 1;
            hm.put("cbMaintenanceIssues", String.valueOf(cbMaintenanceIssuesCheck));
        }else
        {
            cbMaintenanceIssuesCheck = 0;
            hm.put("cbMaintenanceIssues", String.valueOf(cbMaintenanceIssuesCheck));
        }
        if (cbFinishingIssues.isChecked()){
            cbFinishingIssuesCheck = 1;
            hm.put("cbFinishingIssues", String.valueOf(cbFinishingIssuesCheck));
        }
        else
        {
            cbFinishingIssuesCheck = 0;
            hm.put("cbFinishingIssues", String.valueOf(cbFinishingIssuesCheck));
        }
        if (cbSeepageIssues.isChecked()){
            cbSeepageIssuesCheck = 1;
            hm.put("cbSeepageIssues", String.valueOf(cbSeepageIssuesCheck));
        }
        else
        {
            cbSeepageIssuesCheck = 0;
            hm.put("cbSeepageIssues", String.valueOf(cbSeepageIssuesCheck));
        }
        if (cbWaterSupplyIssues.isChecked()) {
            cbWaterSupplyIssuesCheck = 1;
            hm.put("cbWaterSupplyIssues", String.valueOf(cbWaterSupplyIssuesCheck));
        }
        else
        {
            cbWaterSupplyIssuesCheck = 0;
            hm.put("cbWaterSupplyIssues", String.valueOf(cbWaterSupplyIssuesCheck));
        }
        if (cbElectricalIssues.isChecked()){
            cbElectricalIssuesCheck = 1;
            hm.put("cbElectricalIssues", String.valueOf(cbElectricalIssuesCheck));
        }
        else
        {
            cbElectricalIssuesCheck = 0;
            hm.put("cbElectricalIssues", String.valueOf(cbElectricalIssuesCheck));
        }
        if (cbStructuralIssues.isChecked()){
            cbStructuralIssuesCheck = 1;
            hm.put("cbStructuralIssues", String.valueOf(cbStructuralIssuesCheck));
        }
        else
        {
            cbStructuralIssuesCheck = 0;
            hm.put("cbStructuralIssues", String.valueOf(cbStructuralIssuesCheck));
        }
        if (cbVisibleCracks.isChecked()){
            cbVisibleCracksCheck = 1;
            hm.put("cbVisibleCracks", String.valueOf(cbVisibleCracksCheck));
        }else
        {
            cbVisibleCracksCheck = 0;
            hm.put("cbVisibleCracks", String.valueOf(cbVisibleCracks));
        }


        //CheckBox Any Violation
        if (cbConstructionDoneWithoutMap.isChecked()){
            cbConstructionDoneWithoutMapCheck = 1;
            hm.put("cbConstructionDoneWithoutMap", String.valueOf(cbConstructionDoneWithoutMapCheck));
        }
        else
        {
            cbConstructionDoneWithoutMapCheck = 0;
            hm.put("cbConstructionDoneWithoutMap", String.valueOf(cbConstructionDoneWithoutMap));
        }
        if (cbConstructionNotAsPer.isChecked()){
            cbConstructionNotAsPerCheck = 1;
            hm.put("cbConstructionNotAsPer", String.valueOf(cbConstructionNotAsPerCheck));
        }
        else
        {
            cbConstructionNotAsPerCheck = 0;
            hm.put("cbConstructionNotAsPer", String.valueOf(cbConstructionNotAsPerCheck));
        }
        if (cbExtraCovered.isChecked()) {
            cbExtraCoveredCheck = 1;
            hm.put("cbExtraCovered", String.valueOf(cbExtraCoveredCheck));
        }
        else
        {
            cbExtraCoveredCheck = 0;
            hm.put("cbExtraCovered", String.valueOf(cbExtraCoveredCheck));
        }
        if (cbJoinedAdjacent.isChecked()){
            cbJoinedAdjacentCheck = 1;
            hm.put("cbJoinedAdjacent", String.valueOf(cbJoinedAdjacentCheck));
        }
        else
        {
            cbJoinedAdjacentCheck = 0;
            hm.put("cbJoinedAdjacent", String.valueOf(cbJoinedAdjacentCheck));
        }
        if (cbEncroachedAdjacent.isChecked()){
            cbEncroachedAdjacentCheck = 1;
            hm.put("cbEncroachedAdjacent", String.valueOf(cbEncroachedAdjacentCheck));
        }else
        {
            cbEncroachedAdjacentCheck = 0;
            hm.put("cbEncroachedAdjacent", String.valueOf(cbEncroachedAdjacentCheck));
        }


        //CheckBox Parking Facilities
        if (cbAvailableWithin.isChecked()){
            cbAvailableWithinCheck = 1;
            hm.put("cbAvailableWithin", String.valueOf(cbAvailableWithinCheck));
        }
        else
        {
            cbAvailableWithinCheck = 0;
            hm.put("cbAvailableWithin", String.valueOf(cbAvailableWithinCheck));
        }
        if (cbOnGround.isChecked()){
            cbOnGroundCheck = 1;
            hm.put("cbOnGround", String.valueOf(cbOnGroundCheck));
        }
        else
        {
            cbOnGroundCheck = 0;
            hm.put("cbOnGround", String.valueOf(cbOnGroundCheck));
        }
        if (cbInBasement.isChecked()) {
            cbInBasementCheck = 1;
            hm.put("cbInBasement", String.valueOf(cbInBasementCheck));
        }
        else
        {
            cbInBasementCheck = 0;
            hm.put("cbInBasement", String.valueOf(cbInBasementCheck));
        }
        if (cbOnStilt.isChecked()){
            cbOnStiltCheck = 1;
            hm.put("cbOnStilt", String.valueOf(cbOnStiltCheck));
        }
        else
        {
            cbOnStiltCheck = 0;
            hm.put("cbOnStilt", String.valueOf(cbOnStiltCheck));
        }
        if (cbNotAvailableWithin.isChecked()){
            cbNotAvailableWithinCheck = 1;
            hm.put("cbNotAvailableWithin", String.valueOf(cbNotAvailableWithinCheck));
        }
        else
        {
            cbNotAvailableWithinCheck = 0;
            hm.put("cbNotAvailableWithin", String.valueOf(cbNotAvailableWithinCheck));
        }
        if (cbOnRoad.isChecked()){
            cbOnRoadCheck = 1;
            hm.put("cbOnRoad", String.valueOf(cbOnRoadCheck));
        }else
        {
            cbOnRoadCheck = 0;
            hm.put("cbOnRoad", String.valueOf(cbOnRoadCheck));
        }
        if (cbAcuteParking.isChecked()){
            cbAcuteParkingCheck = 1;
            hm.put("cbAcuteParking", String.valueOf(cbAcuteParkingCheck));
        }
        else
        {
            cbAcuteParkingCheck = 0;
            hm.put("cbAcuteParking", String.valueOf(cbAcuteParkingCheck));
        }


        //CheckBox Air Conditioning
        if (cbWindows.isChecked()){
            cbWindowsCheck = 1;
            hm.put("cbWindows", String.valueOf(cbWindowsCheck));
        }
        else
        {
            cbWindowsCheck = 0;
            hm.put("cbWindows", String.valueOf(cbWindowsCheck));
        }
        if (cbSplit.isChecked()) {
            cbSplitCheck = 1;
            hm.put("cbSplit", String.valueOf(cbSplitCheck));
        }
        else
        {
            cbSplitCheck = 0;
            hm.put("cbSplit", String.valueOf(cbSplitCheck));
        }
        if (cbCentralAir.isChecked()){
            cbCentralAirCheck = 1;
            hm.put("cbCentralAir", String.valueOf(cbCentralAirCheck));
        }
        else
        {
            cbCentralAirCheck = 0;
            hm.put("cbCentralAir", String.valueOf(cbCentralAirCheck));
        }

// ,,,,;

        //CheckBox Provision of Firefighting
        if (cbWetRiser.isChecked()){
            cbWetRiserCheck = 1;
            hm.put("cbWetRiser", String.valueOf(cbWetRiserCheck));
        }else
        {
            cbWetRiserCheck = 0;
            hm.put("cbWetRiser", String.valueOf(cbWetRiserCheck));
        }
        if (cbAutomaticWater.isChecked()){
            cbAutomaticWaterCheck = 1;
            hm.put("cbAutomaticWater", String.valueOf(cbAutomaticWaterCheck));
        }
        else
        {
            cbAutomaticWaterCheck = 0;
            hm.put("cbAutomaticWater", String.valueOf(cbAutomaticWaterCheck));
        }
        if (cbFireHydrant.isChecked()){
            cbFireHydrantCheck = 1;
            hm.put("cbFireHydrant", String.valueOf(cbFireHydrantCheck));
        }
        else
        {
            cbFireHydrantCheck = 0;
            hm.put("cbFireHydrant", String.valueOf(cbFireHydrantCheck));
        }
        if (cbFireExting.isChecked()) {
            cbFireExtingCheck = 1;
            hm.put("cbFireExting", String.valueOf(cbFireExtingCheck));
        }
        else
        {
            cbFireExtingCheck = 0;
            hm.put("cbFireExting", String.valueOf(cbFireExtingCheck));
        }
        if (cbNoFireFighting.isChecked()){
            cbNoFireFightingCheck = 1;
            hm.put("cbNoFireFighting", String.valueOf(cbNoFireFightingCheck));
        }
        else
        {
            cbNoFireFightingCheck = 0;
            hm.put("cbNoFireFighting", String.valueOf(cbNoFireFightingCheck));
        }


        int selectedIdWaterArrangements = rgWaterArrangements.getCheckedRadioButtonId();
        View radioButtonWaterArrangements = findViewById(selectedIdWaterArrangements);
        int idx = rgWaterArrangements.indexOfChild(radioButtonWaterArrangements);
        RadioButton r = (RadioButton)  rgWaterArrangements.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonWaterArrangements", selectedText);

        int selectedIdFixedWoodenWork = rgFixedWoodenWork.getCheckedRadioButtonId();
        View radioButtonFixedWoodenWork = findViewById(selectedIdFixedWoodenWork);
        int idx2 = rgFixedWoodenWork.indexOfChild(radioButtonFixedWoodenWork);
        RadioButton r2 = (RadioButton)  rgFixedWoodenWork.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonFixedWoodenWork", selectedText2);

        int selectedIdMaintenanceOfTheBuilding = rgMaintenanceOfTheBuilding.getCheckedRadioButtonId();
        View radioButtonMaintenanceOfTheBuilding = findViewById(selectedIdMaintenanceOfTheBuilding);
        int idx3 = rgMaintenanceOfTheBuilding.indexOfChild(radioButtonMaintenanceOfTheBuilding);
        RadioButton r3 = (RadioButton)  rgMaintenanceOfTheBuilding.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonMaintenanceOfTheBuildingg", selectedText3);

        int selectedIdBoundaryWall = rgBoundaryWall.getCheckedRadioButtonId();
        View radioButtonBoundaryWall = findViewById(selectedIdBoundaryWall);
        int idx4 = rgBoundaryWall.indexOfChild(radioButtonBoundaryWall);
        RadioButton r4 = (RadioButton)  rgBoundaryWall.getChildAt(idx4);
        String selectedText4 = r4.getText().toString();
        hm.put("radioButtonBoundaryWall", selectedText4);

        int selectedIdGardening = rgGardening.getCheckedRadioButtonId();
        View radioButtonGardening = findViewById(selectedIdGardening);
        int idx5 = rgGardening.indexOfChild(radioButtonGardening);
        RadioButton r5 = (RadioButton)  rgGardening.getChildAt(idx5);
        String selectedText5 = r5.getText().toString();
        hm.put("radioButtonGardening", selectedText5);

        int selectedIdLift = rgLift.getCheckedRadioButtonId();
        View radioButtonLift = findViewById(selectedIdLift);
        int idx6 = rgLift.indexOfChild(radioButtonLift);
        RadioButton r6 = (RadioButton)  rgLift.getChildAt(idx6);
        String selectedText6 = r6.getText().toString();
        hm.put("radioButtonLift", selectedText6);

        int selectedIdPowerBackup = rgPowerBackup.getCheckedRadioButtonId();
        View radioButtonPowerBackup = findViewById(selectedIdPowerBackup);
        int idx7 = rgPowerBackup.indexOfChild(radioButtonPowerBackup);
        RadioButton r7 = (RadioButton)  rgPowerBackup.getChildAt(idx7);
        String selectedText7 = r7.getText().toString();
        hm.put("radioButtonPowerBackup", selectedText7);


        int selectedIdUseOfSpecial = rgUseOfSpecial.getCheckedRadioButtonId();
        View radioButtonUseOfSpecial = findViewById(selectedIdUseOfSpecial);
        int idx8 = rgUseOfSpecial.indexOfChild(radioButtonUseOfSpecial);
        RadioButton r8 = (RadioButton)  rgUseOfSpecial.getChildAt(idx8);
        String selectedText8 = r8.getText().toString();
        hm.put("radioButtonUseOfSpecial", selectedText8);


        int selectedIdProvisionOfSolar = rgProvisionOfSolar.getCheckedRadioButtonId();
        View radioButtonProvisionOfSolar = findViewById(selectedIdProvisionOfSolar);
        int idx9 = rgProvisionOfSolar.indexOfChild(radioButtonProvisionOfSolar);
        RadioButton r9 = (RadioButton)  rgProvisionOfSolar.getChildAt(idx9);
        String selectedText9 = r9.getText().toString();
        hm.put("radioButtonProvisionOfSolar", selectedText9);


        int selectedIdProvisionOfRain = rgProvisionOfRain.getCheckedRadioButtonId();
        View radioButtonProvisionOfRain = findViewById(selectedIdProvisionOfRain);
        int idx10 = rgProvisionOfRain.indexOfChild(radioButtonProvisionOfRain);
        RadioButton r10 = (RadioButton)  rgProvisionOfRain.getChildAt(idx10);
        String selectedText10 = r10.getText().toString();
        hm.put("radioButtonProvisionOfRain", selectedText10);

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getSpecialAssets();
        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            //Check Long Log
            /*int maxLogSize = 1000;
            for(int i = 0; i <= array.toString().length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > array.toString().length() ? array.toString().length() : end;
                Log.v("getfromdb2===", array.toString().substring(start, end));
            }*/
            /////

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                etAgeOfBuilding.setText(object.getString("ageOfBuilding"));
                etBoundaryRunningMtr.setText(object.getString("boundaryRunningMtr"));
                etBoundaryHeight.setText(object.getString("boundaryHeight"));
                etBoundaryWidth.setText(object.getString("boundaryWidth"));
                etBoundaryFinish.setText(object.getString("boundaryFinish"));
                etIntRoadsTypeOfConstruction.setText(object.getString("intRoadsTypeOfCons"));
                etIntRoadsLength.setText(object.getString("intRoadsLength"));
                etIntRoadsWidth.setText(object.getString("intRoadsWidth"));
                etACNumber.setText(object.getString("acNumber"));
                etACCapacity.setText(object.getString("acCapacity"));
                etACMake.setText(object.getString("acMake"));
                etACYearOfInstallation.setText(object.getString("acYearOfInst"));
                etACCostOfCapi.setText(object.getString("acCostOfCapi"));
                etHvacMake.setText(object.getString("hvacMake"));
                etHvacCapacity.setText(object.getString("hvacCapacity"));
                etHvacYearOfInst.setText(object.getString("hvacYearOfInst"));
                etHvacCostOfCapi.setText(object.getString("hvacCostOfCapi"));
                etLiftMake.setText(object.getString("liftMake"));
                etLiftCapacity.setText(object.getString("liftCapacity"));
                etLiftYearOfInst.setText(object.getString("liftYearOfInst"));
                etLiftCostOfCapi.setText(object.getString("listCostOfCapi"));
                etPBNumber.setText(object.getString("pbNumber"));
                etPBMake.setText(object.getString("pbMake"));
                etPBCapacity.setText(object.getString("pbCapacity"));
                etPBYearOfInst.setText(object.getString("pbYearOfInst"));
                etPBCostOfCapi.setText(object.getString("pbCostOfCapi"));
                etPOFYearOfInst.setText(object.getString("pofYearOfInst"));
                etPOFCostOfCapi.setText(object.getString("pofCostOfCapi"));
                etUOSCapacity.setText(object.getString("uosCapacity"));
                etUOSYearOfInst.setText(object.getString("uosYearOfInst"));
                etUOSCostOfCapi.setText(object.getString("uosCostOfCapi"));
                etPOSCapacity.setText(object.getString("posCapacity"));
                etPOSYearOfInst.setText(object.getString("posYearOfInst"));
                etPOSCostOfCapi.setText(object.getString("posCostOfCapi"));
                etPORCapacity.setText(object.getString("porCapacity"));
                etPORYearOfInst.setText(object.getString("porYearOfInst"));
                etPORCostOfCapi.setText(object.getString("porCostOfCapi"));
                etSpecialInst.setText(object.getString("specialInst"));


                //CheckBox Any Defects
                if (object.getString("cbMaintenanceIssues").equals("1")){
                    cbMaintenanceIssues.setChecked(true);
                }else {
                    cbMaintenanceIssues.setChecked(false);
                }
                if (object.getString("cbFinishingIssues").equals("1")){
                    cbFinishingIssues.setChecked(true);
                }else {
                    cbFinishingIssues.setChecked(false);
                }
                if (object.getString("cbSeepageIssues").equals("1")){
                    cbSeepageIssues.setChecked(true);
                }else {
                    cbSeepageIssues.setChecked(false);
                }
                if (object.getString("cbWaterSupplyIssues").equals("1")){
                    cbWaterSupplyIssues.setChecked(true);
                }else {
                    cbWaterSupplyIssues.setChecked(false);
                }
                if (object.getString("cbElectricalIssues").equals("1")){
                    cbElectricalIssues.setChecked(true);
                }else {
                    cbElectricalIssues.setChecked(false);
                }
                if (object.getString("cbStructuralIssues").equals("1")){
                    cbStructuralIssues.setChecked(true);
                }else {
                    cbStructuralIssues.setChecked(false);
                }
                if (object.getString("cbVisibleCracks").equals("1")){
                    cbVisibleCracks.setChecked(true);
                }else {
                    cbVisibleCracks.setChecked(false);
                }


                //CheckBox Any Violation
                if (object.getString("cbConstructionDoneWithoutMap").equals("1")){
                    cbConstructionDoneWithoutMap.setChecked(true);
                }else {
                    cbConstructionDoneWithoutMap.setChecked(false);
                }
                if (object.getString("cbConstructionNotAsPer").equals("1")){
                    cbConstructionNotAsPer.setChecked(true);
                }else {
                    cbConstructionNotAsPer.setChecked(false);
                }
                if (object.getString("cbExtraCovered").equals("1")){
                    cbExtraCovered.setChecked(true);
                }else {
                    cbExtraCovered.setChecked(false);
                }
                if (object.getString("cbJoinedAdjacent").equals("1")){
                    cbJoinedAdjacent.setChecked(true);
                }else {
                    cbJoinedAdjacent.setChecked(false);
                }
                if (object.getString("cbEncroachedAdjacent").equals("1")){
                    cbEncroachedAdjacent.setChecked(true);
                }else {
                    cbEncroachedAdjacent.setChecked(false);
                }


                //CheckBox Parking
                if (object.getString("cbAvailableWithin").equals("1")){
                    cbAvailableWithin.setChecked(true);
                }else {
                    cbAvailableWithin.setChecked(false);
                }
                if (object.getString("cbOnGround").equals("1")){
                    cbOnGround.setChecked(true);
                }else {
                    cbOnGround.setChecked(false);
                }
                if (object.getString("cbInBasement").equals("1")){
                    cbInBasement.setChecked(true);
                }else {
                    cbInBasement.setChecked(false);
                }
                if (object.getString("cbOnStilt").equals("1")){
                    cbOnStilt.setChecked(true);
                }else {
                    cbOnStilt.setChecked(false);
                }
                if (object.getString("cbNotAvailableWithin").equals("1")){
                    cbNotAvailableWithin.setChecked(true);
                }else {
                    cbNotAvailableWithin.setChecked(false);
                }
                if (object.getString("cbOnRoad").equals("1")){
                    cbOnRoad.setChecked(true);
                }else {
                    cbOnRoad.setChecked(false);
                }
                if (object.getString("cbAcuteParking").equals("1")){
                    cbAcuteParking.setChecked(true);
                }else {
                    cbAcuteParking.setChecked(false);
                }


                //CheckBox AC
                if (object.getString("cbWindows").equals("1")){
                    cbWindows.setChecked(true);
                }else {
                    cbWindows.setChecked(false);
                }
                if (object.getString("cbSplit").equals("1")){
                    cbSplit.setChecked(true);
                }else {
                    cbSplit.setChecked(false);
                }
                if (object.getString("cbCentralAir").equals("1")){
                    cbCentralAir.setChecked(true);
                }else {
                    cbCentralAir.setChecked(false);
                }


                //CheckBox Provision of Firefighting
                if (object.getString("cbWetRiser").equals("1")){
                    cbWetRiser.setChecked(true);
                }else {
                    cbWetRiser.setChecked(false);
                }
                if (object.getString("cbAutomaticWater").equals("1")){
                    cbAutomaticWater.setChecked(true);
                }else {
                    cbAutomaticWater.setChecked(false);
                }
                if (object.getString("cbFireHydrant").equals("1")){
                    cbFireHydrant.setChecked(true);
                }else {
                    cbFireHydrant.setChecked(false);
                }
                if (object.getString("cbFireExting").equals("1")){
                    cbFireExting.setChecked(true);
                }else {
                    cbFireExting.setChecked(false);
                }
                if (object.getString("cbNoFireFighting").equals("1")){
                    cbNoFireFighting.setChecked(true);
                }else {
                    cbNoFireFighting.setChecked(false);
                }



                Log.v("checkcheck",object.getString("radioButtonWaterArrangements"));

                if (object.getString("radioButtonWaterArrangements").equals("Jet pump")){
                    ((RadioButton)rgWaterArrangements.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonWaterArrangements").equals("Submersible")){
                    ((RadioButton)rgWaterArrangements.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonWaterArrangements").equals("Jal board supply")){
                    ((RadioButton)rgWaterArrangements.getChildAt(2)).setChecked(true);
                }


                if (object.getString("radioButtonFixedWoodenWork").equals("Excellent")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonFixedWoodenWork").equals("Very Good")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonFixedWoodenWork").equals("Good")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonFixedWoodenWork").equals("Simple")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonFixedWoodenWork").equals("Ordinary")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonFixedWoodenWork").equals("Average")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonFixedWoodenWork").equals("Below Average")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonFixedWoodenWork").equals("No wooden work")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonFixedWoodenWork").equals("No Survey")){
                    ((RadioButton)rgFixedWoodenWork.getChildAt(8)).setChecked(true);
                }



                if (object.getString("radioButtonMaintenanceOfTheBuildingg").equals("Excellent")){
                    ((RadioButton)rgMaintenanceOfTheBuilding.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonMaintenanceOfTheBuildingg").equals("Average")){
                    ((RadioButton)rgMaintenanceOfTheBuilding.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonMaintenanceOfTheBuildingg").equals("Poor")){
                    ((RadioButton)rgMaintenanceOfTheBuilding.getChildAt(2)).setChecked(true);
                }


                if (object.getString("radioButtonBoundaryWall").equals("Yes")){
                    ((RadioButton)rgBoundaryWall.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonBoundaryWall").equals("No")){
                    ((RadioButton)rgBoundaryWall.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonBoundaryWall").equals("Common boundary wall of a complex")){
                    ((RadioButton)rgBoundaryWall.getChildAt(2)).setChecked(true);
                }




                if (object.getString("radioButtonGardening").equals("Yes")){
                    ((RadioButton)rgGardening.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonGardening").equals("No")){
                    ((RadioButton)rgGardening.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonGardening").equals("Beautiful")){
                    ((RadioButton)rgGardening.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonGardening").equals("Ordinary")){
                    ((RadioButton)rgGardening.getChildAt(3)).setChecked(true);
                }


                if (object.getString("radioButtonLift").equals("Passenger")){
                    ((RadioButton)rgLift.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonLift").equals("Commercial")){
                    ((RadioButton)rgLift.getChildAt(1)).setChecked(true);
                }


                if (object.getString("radioButtonPowerBackup").equals("Inverter")){
                    ((RadioButton)rgPowerBackup.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonPowerBackup").equals("DG Set")){
                    ((RadioButton)rgPowerBackup.getChildAt(1)).setChecked(true);
                }



                if (object.getString("radioButtonUseOfSpecial").equals("Yes")){
                    ((RadioButton)rgUseOfSpecial.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonUseOfSpecial").equals("No")){
                    ((RadioButton)rgUseOfSpecial.getChildAt(1)).setChecked(true);
                }



                if (object.getString("radioButtonProvisionOfSolar").equals("Yes")){
                    ((RadioButton)rgProvisionOfSolar.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonProvisionOfSolar").equals("No")){
                    ((RadioButton)rgProvisionOfSolar.getChildAt(1)).setChecked(true);
                }



                if (object.getString("radioButtonProvisionOfRain").equals("Yes")){
                    ((RadioButton)rgProvisionOfRain.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonProvisionOfRain").equals("No")){
                    ((RadioButton)rgProvisionOfRain.getChildAt(1)).setChecked(true);
                }



            }

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
