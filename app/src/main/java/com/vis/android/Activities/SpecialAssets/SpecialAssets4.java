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

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vis.android.Activities.SpecialAssets.SpecialAssets1.hm;

public class SpecialAssets4 extends AppCompatActivity implements View.OnClickListener{
    TextView next;
    Intent intent;

    //EditText
    EditText etAsPerTitleDeed, etAsPerMap, etAsPerSiteSurvey, etTotalNumberOfFloors, etFloorOnWhich, etTypeOfUnit, etRoofHeight;

    //RadioGroup
    RadioGroup rgConstructionStatus, rgCoveredBuilt, rgBuildingType, rgRoofMake, rgRoofFinish, rgAppearanceInt, rgAppearanceExt, rgMaintenanceOfTheBuilding;
    RadioGroup rgInteriorDecoration, rgKitchen, rgClassOfElectrical, rgClassOfSanitary;

    //CheckBox
    CheckBox cbVitrified,cbCeramic,cbSimpleMarble,cbMarbleChips,cbMosaic,cbGranite,cbItalianMarble,cbKotaStone,cbWooden,cbPcc,cbImportedMarble,cbPavers;
    CheckBox cbChequered,cbBrickTiles,cbNoFlooring,cbUnderConstruction,cbAnyOtherType, cbIntSimplePlastered,cbIntBrickWalls,cbIntDesignerTextured,cbIntPopPunning;
    CheckBox cbIntBrickTileCladding,cbExtSimplePlastered,cbExtBrickWalls,cbExtDesigner,cbExtPopPunning,cbExtBrickTile;

    int cbVitrifiedCheck,cbCeramicCheck,cbSimpleMarbleCheck,cbMarbleChipsCheck,cbMosaicCheck,cbGraniteCheck,cbItalianMarbleCheck,cbKotaStoneCheck,cbWoodenCheck,cbPccCheck,cbImportedMarbleCheck,cbPaversCheck;
    int cbChequeredCheck,cbBrickTilesCheck,cbNoFlooringCheck,cbUnderConstructionCheck,cbAnyOtherTypeCheck, cbIntSimplePlasteredCheck,cbIntBrickWallsCheck,cbIntDesignerTexturedCheck,cbIntPopPunningCheck;
    int cbIntBrickTileCladdingCheck,cbExtSimplePlasteredCheck,cbExtBrickWallsCheck,cbExtDesignerCheck,cbExtPopPunningCheck,cbExtBrickTileCheck;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets4);
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

        etAsPerTitleDeed = findViewById(R.id.etAsPerTitleDeed);
        etAsPerMap = findViewById(R.id.etAsPerMap);
        etAsPerSiteSurvey = findViewById(R.id.etAsPerSiteSurvey);
        etTotalNumberOfFloors = findViewById(R.id.etTotalNumberOfFloors);
        etFloorOnWhich = findViewById(R.id.etFloorOnWhich);
        etTypeOfUnit = findViewById(R.id.etTypeOfUnit);
        etRoofHeight = findViewById(R.id.etRoofHeight);

        rgConstructionStatus = findViewById(R.id.rgConstructionStatus);
        rgCoveredBuilt = findViewById(R.id.rgCoveredBuilt);
        rgBuildingType = findViewById(R.id.rgBuildingType);
        rgRoofMake = findViewById(R.id.rgRoofMake);
        rgRoofFinish = findViewById(R.id.rgRoofFinish);
        rgAppearanceInt = findViewById(R.id.rgAppearanceInternal);
        rgAppearanceExt = findViewById(R.id.rgAppearanceExternal);
        rgMaintenanceOfTheBuilding = findViewById(R.id.rgMaintenanceOfTheBuilding);
        rgInteriorDecoration = findViewById(R.id.rgInteriorDecoration);
        rgKitchen = findViewById(R.id.rgKitchen);
        rgClassOfElectrical = findViewById(R.id.rgClassOfElectrical);
        rgClassOfSanitary = findViewById(R.id.rgClassOfSanitary);


        cbVitrified = findViewById(R.id.cbVitrified);
        cbCeramic = findViewById(R.id.cbCeramic);
        cbSimpleMarble = findViewById(R.id.cbSimpleMarble);
        cbMarbleChips = findViewById(R.id.cbMarbleChips);
        cbMosaic = findViewById(R.id.cbMosaic);
        cbGranite = findViewById(R.id.cbGranite);
        cbItalianMarble = findViewById(R.id.cbItalianMarble);
        cbKotaStone = findViewById(R.id.cbKotaStone);
        cbWooden = findViewById(R.id.cbWooden);
        cbPcc = findViewById(R.id.cbPcc);
        cbImportedMarble = findViewById(R.id.cbImportedMarble);
        cbPavers = findViewById(R.id.cbPavers);
        cbChequered = findViewById(R.id.cbChequered);
        cbBrickTiles = findViewById(R.id.cbBrickTiles);
        cbNoFlooring = findViewById(R.id.cbNoFlooring);
        cbUnderConstruction = findViewById(R.id.cbUnderConstruction);
        cbAnyOtherType = findViewById(R.id.cbAnyOtherType);
        cbIntSimplePlastered = findViewById(R.id.cbIntSimplePlastered);
        cbIntBrickWalls = findViewById(R.id.cbIntBrickWalls);
        cbIntDesignerTextured = findViewById(R.id.cbIntDesignerTextured);
        cbIntPopPunning = findViewById(R.id.cbIntPopPunning);
        cbIntBrickTileCladding = findViewById(R.id.cbIntBrickTileCladding);
        cbExtSimplePlastered = findViewById(R.id.cbExtSimplePlastered);
        cbExtBrickWalls = findViewById(R.id.cbExtBrickWalls);
        cbExtDesigner = findViewById(R.id.cbExtDesigner);
        cbExtPopPunning = findViewById(R.id.cbExtPopPunning);
        cbExtBrickTile = findViewById(R.id.cbExtBrickTile);





    }
    public void setListener(){
        next.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                validation();
                break;
        }
    }

    public void validation(){

        if (rgConstructionStatus.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Construction Status", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Construction Status", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgCoveredBuilt.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Covered Built-up Area", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Covered Built-up Area", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etAsPerTitleDeed.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Covered Built-up Area As Per Title Deed", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area As Per Title Deed", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAsPerTitleDeed.requestFocus();
        }
        else if (etAsPerMap.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Covered Built-up Area As Per Map", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area As Per Map", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAsPerMap.requestFocus();
        }
        else if (etAsPerSiteSurvey.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Covered Built-up Area As Per Site Survey", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Covered Built-up Area As Per Site Survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAsPerSiteSurvey.requestFocus();
        }
        else if (etTotalNumberOfFloors.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Total Number Of Floors In The Building", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Total Number Of Floors In The Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTotalNumberOfFloors.requestFocus();
        }
        else if (etFloorOnWhich.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Floor on which property is situated", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Floor on which property is situated", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etFloorOnWhich.requestFocus();
        }
        else if (etTypeOfUnit.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Type of Unit", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Type of Unit", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeOfUnit.requestFocus();
        }
        else if (rgBuildingType.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Building Type", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Building Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgRoofMake.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Roof Make", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Roof Make", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etRoofHeight.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Roof Height", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Roof Height", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRoofHeight.requestFocus();
        }
        else if (rgRoofFinish.getCheckedRadioButtonId() == -1){
         //   Toast.makeText(this, "Please Select Roof Finish", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Roof Finish", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (!cbVitrified.isChecked()
                && !cbCeramic.isChecked()
                && !cbSimpleMarble.isChecked()
                && !cbMarbleChips.isChecked()
                && !cbMosaic.isChecked()
                && !cbGranite.isChecked()
                && !cbItalianMarble.isChecked()
                && !cbKotaStone.isChecked()
                && !cbWooden.isChecked()
                && !cbPcc.isChecked()
                && !cbImportedMarble.isChecked()
                && !cbPavers.isChecked()
                && !cbChequered.isChecked()
                && !cbBrickTiles.isChecked()
                && !cbNoFlooring.isChecked()
                && !cbUnderConstruction.isChecked()
                && !cbAnyOtherType.isChecked()) {
            //Toast.makeText(this, "Please Select Flooring", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Flooring", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgAppearanceInt.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Appearance Internal", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Appearance Internal", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgAppearanceExt.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Appearance External", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Appearance External", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgMaintenanceOfTheBuilding.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Maintenance of the Building", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Maintenance of the Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgInteriorDecoration.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Interior Decoration", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Decoration", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (!cbIntSimplePlastered.isChecked()
                && !cbIntBrickWalls.isChecked()
                && !cbIntDesignerTextured.isChecked()
                && !cbIntPopPunning.isChecked()
                && !cbIntBrickTileCladding.isChecked()) {
            //Toast.makeText(this, "Please Select Interior Finishing", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Finishing", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (!cbExtSimplePlastered.isChecked()
                && !cbExtBrickWalls.isChecked()
                && !cbExtDesigner.isChecked()
                && !cbExtPopPunning.isChecked()
                && !cbExtBrickTile.isChecked()) {
            //Toast.makeText(this, "Please Select Exterior Finishing", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Exterior Finishing", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgKitchen.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Kitchen", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Kitchen", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgClassOfElectrical.getCheckedRadioButtonId() == -1){
         //   Toast.makeText(this, "Please Select Class of Electrical Fittings", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Electrical Fittings", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgClassOfSanitary.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Class of Sanitary", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Sanitary", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        else
        {
            putIntoHm();
            Intent intent=new Intent(SpecialAssets4.this,SpecialAssets5.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm(){


        hm.put("asPerTitleDeed", etAsPerTitleDeed.getText().toString());
        hm.put("asPerMapp", etAsPerMap.getText().toString());
        hm.put("asPerSiteSurveyy", etAsPerSiteSurvey.getText().toString());
        hm.put("totalNoOfFloors", etTotalNumberOfFloors.getText().toString());
        hm.put("floorOnWhich", etFloorOnWhich.getText().toString());
        hm.put("typeOfUnitt", etTypeOfUnit.getText().toString());
        hm.put("roofHeight", etRoofHeight.getText().toString());


        ////CheckBox Flooring
        if (cbVitrified.isChecked()){
            cbVitrifiedCheck = 1;
            hm.put("cbVitrified", String.valueOf(cbVitrifiedCheck));
        }else
        {
            cbVitrifiedCheck = 0;
            hm.put("cbVitrified", String.valueOf(cbVitrifiedCheck));
        }
        if (cbCeramic.isChecked()){
            cbCeramicCheck = 1;
            hm.put("cbCeramic", String.valueOf(cbCeramicCheck));
        }
        else
        {
            cbCeramicCheck = 0;
            hm.put("cbCeramic", String.valueOf(cbCeramicCheck));
        }
        if (cbSimpleMarble.isChecked()){
            cbSimpleMarbleCheck = 1;
            hm.put("cbSimpleMarble", String.valueOf(cbSimpleMarbleCheck));
        }
        else
        {
            cbSimpleMarbleCheck = 0;
            hm.put("cbSimpleMarble", String.valueOf(cbSimpleMarbleCheck));
        }
        if (cbMarbleChips.isChecked()) {
            cbMarbleChipsCheck = 1;
            hm.put("cbMarbleChips", String.valueOf(cbMarbleChipsCheck));
        }
        else
        {
            cbMarbleChipsCheck = 0;
            hm.put("cbMarbleChips", String.valueOf(cbMarbleChipsCheck));
        }
        if (cbMosaic.isChecked()){
            cbMosaicCheck = 1;
            hm.put("cbMosaic", String.valueOf(cbMosaicCheck));
        }
        else
        {
            cbMosaicCheck = 0;
            hm.put("cbMosaic", String.valueOf(cbMosaicCheck));
        }
        if (cbGranite.isChecked()){
            cbGraniteCheck = 1;
            hm.put("cbGranite", String.valueOf(cbGraniteCheck));
        }
        else
        {
            cbGraniteCheck = 0;
            hm.put("cbGranite", String.valueOf(cbGraniteCheck));
        }
        if (cbItalianMarble.isChecked()){
            cbItalianMarbleCheck = 1;
            hm.put("cbItalianMarble", String.valueOf(cbItalianMarbleCheck));
        }else
        {
            cbItalianMarbleCheck = 0;
            hm.put("cbItalianMarble", String.valueOf(cbItalianMarbleCheck));
        }
        if (cbKotaStone.isChecked()){
            cbKotaStoneCheck = 1;
            hm.put("cbKotaStone", String.valueOf(cbKotaStoneCheck));
        }
        else
        {
            cbKotaStoneCheck = 0;
            hm.put("cbKotaStone", String.valueOf(cbKotaStoneCheck));
        }
        if (cbWooden.isChecked()){
            cbWoodenCheck = 1;
            hm.put("cbWooden", String.valueOf(cbWoodenCheck));
        }
        else
        {
            cbWoodenCheck = 0;
            hm.put("cbWooden", String.valueOf(cbWoodenCheck));
        }
        if (cbPcc.isChecked()) {
            cbPccCheck = 1;
            hm.put("cbPcc", String.valueOf(cbPccCheck));
        }
        else
        {
            cbPccCheck = 0;
            hm.put("cbPcc", String.valueOf(cbPccCheck));
        }
        if (cbImportedMarble.isChecked()){
            cbImportedMarbleCheck = 1;
            hm.put("cbImportedMarble", String.valueOf(cbImportedMarbleCheck));
        }
        else
        {
            cbImportedMarbleCheck = 0;
            hm.put("cbImportedMarble", String.valueOf(cbImportedMarbleCheck));
        }
        if (cbPavers.isChecked()){
            cbPaversCheck = 1;
            hm.put("cbPavers", String.valueOf(cbPaversCheck));
        }else
        {
            cbPaversCheck = 0;
            hm.put("cbPavers", String.valueOf(cbPaversCheck));
        }
        if (cbChequered.isChecked()){
            cbChequeredCheck = 1;
            hm.put("cbChequered", String.valueOf(cbChequeredCheck));
        }
        else
        {
            cbChequeredCheck = 0;
            hm.put("cbChequered", String.valueOf(cbChequeredCheck));
        }
        if (cbBrickTiles.isChecked()){
            cbBrickTilesCheck = 1;
            hm.put("cbBrickTiles", String.valueOf(cbBrickTilesCheck));
        }
        else
        {
            cbBrickTilesCheck = 0;
            hm.put("cbBrickTiles", String.valueOf(cbBrickTilesCheck));
        }
        if (cbNoFlooring.isChecked()) {
            cbNoFlooringCheck = 1;
            hm.put("cbNoFlooring", String.valueOf(cbNoFlooringCheck));
        }
        else
        {
            cbNoFlooringCheck = 0;
            hm.put("cbNoFlooring", String.valueOf(cbNoFlooringCheck));
        }
        if (cbUnderConstruction.isChecked()){
            cbUnderConstructionCheck = 1;
            hm.put("cbUnderConstruction", String.valueOf(cbUnderConstructionCheck));
        }
        else
        {
            cbUnderConstructionCheck = 0;
            hm.put("cbUnderConstruction", String.valueOf(cbUnderConstructionCheck));
        }
        if (cbAnyOtherType.isChecked()){
            cbAnyOtherTypeCheck = 1;
            hm.put("cbAnyOtherType", String.valueOf(cbAnyOtherTypeCheck));
        }
        else
        {
            cbAnyOtherTypeCheck = 0;
            hm.put("cbAnyOtherType", String.valueOf(cbAnyOtherTypeCheck));
        }


        //CheckBox Interior Finishing

        if (cbIntSimplePlastered.isChecked()){
            cbIntSimplePlasteredCheck = 1;
            hm.put("cbIntSimplePlastered", String.valueOf(cbIntSimplePlasteredCheck));
        }else
        {
            cbIntSimplePlasteredCheck = 0;
            hm.put("cbIntSimplePlastered", String.valueOf(cbIntSimplePlasteredCheck));
        }
        if (cbIntBrickWalls.isChecked()){
            cbIntBrickWallsCheck = 1;
            hm.put("cbIntBrickWalls", String.valueOf(cbIntBrickWallsCheck));
        }
        else
        {
            cbIntBrickWallsCheck = 0;
            hm.put("cbIntBrickWalls", String.valueOf(cbIntBrickWallsCheck));
        }
        if (cbIntDesignerTextured.isChecked()){
            cbIntDesignerTexturedCheck = 1;
            hm.put("cbIntDesignerTextured", String.valueOf(cbIntDesignerTexturedCheck));
        }
        else
        {
            cbIntDesignerTexturedCheck = 0;
            hm.put("cbIntDesignerTextured", String.valueOf(cbIntDesignerTexturedCheck));
        }
        if (cbIntPopPunning.isChecked()) {
            cbIntPopPunningCheck = 1;
            hm.put("cbIntPopPunning", String.valueOf(cbIntPopPunningCheck));
        }
        else
        {
            cbIntPopPunningCheck = 0;
            hm.put("cbIntPopPunning", String.valueOf(cbIntPopPunningCheck));
        }
        if (cbIntBrickTileCladding.isChecked()){
            cbIntBrickTileCladdingCheck = 1;
            hm.put("cbIntBrickTileCladding", String.valueOf(cbIntBrickTileCladdingCheck));
        }
        else
        {
            cbIntBrickTileCladdingCheck = 0;
            hm.put("cbIntBrickTileCladding", String.valueOf(cbIntBrickTileCladdingCheck));
        }


        //CheckBox Exterior Finishing
        if (cbExtSimplePlastered.isChecked()){
            cbExtSimplePlasteredCheck = 1;
            hm.put("cbExtSimplePlastered", String.valueOf(cbExtSimplePlasteredCheck));
        }else
        {
            cbExtSimplePlasteredCheck = 0;
            hm.put("cbExtSimplePlastered", String.valueOf(cbExtSimplePlasteredCheck));
        }
        if (cbExtBrickWalls.isChecked()){
            cbExtBrickWallsCheck = 1;
            hm.put("cbExtBrickWalls", String.valueOf(cbExtBrickWallsCheck));
        }
        else
        {
            cbExtBrickWallsCheck = 0;
            hm.put("cbExtBrickWalls", String.valueOf(cbExtBrickWallsCheck));
        }
        if (cbExtDesigner.isChecked()){
            cbExtDesignerCheck = 1;
            hm.put("cbExtDesigner", String.valueOf(cbExtDesignerCheck));
        }
        else
        {
            cbExtDesignerCheck = 0;
            hm.put("cbExtDesigner", String.valueOf(cbExtDesignerCheck));
        }
        if (cbExtPopPunning.isChecked()) {
            cbExtPopPunningCheck = 1;
            hm.put("cbExtPopPunning", String.valueOf(cbExtPopPunningCheck));
        }
        else
        {
            cbExtPopPunningCheck = 0;
            hm.put("cbExtPopPunning", String.valueOf(cbExtPopPunningCheck));
        }
        if (cbExtBrickTile.isChecked()){
            cbExtBrickTileCheck = 1;
            hm.put("cbExtBrickTile", String.valueOf(cbExtBrickTileCheck));
        }
        else
        {
            cbExtBrickTileCheck = 0;
            hm.put("cbExtBrickTile", String.valueOf(cbExtBrickTileCheck));
        }




        int selectedIdConstructionStatus = rgConstructionStatus.getCheckedRadioButtonId();
        View radioButtonConstructionStatus = findViewById(selectedIdConstructionStatus);
        int idx = rgConstructionStatus.indexOfChild(radioButtonConstructionStatus);
        RadioButton r = (RadioButton)  rgConstructionStatus.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonConstructionStatus", selectedText);

        int selectedIdCoveredBuilt = rgCoveredBuilt.getCheckedRadioButtonId();
        View radioButtonCoveredBuilt = findViewById(selectedIdCoveredBuilt);
        int idx2 = rgCoveredBuilt.indexOfChild(radioButtonCoveredBuilt);
        RadioButton r2 = (RadioButton)  rgCoveredBuilt.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonCoveredBuilt", selectedText2);

        int selectedIdBuildingType = rgBuildingType.getCheckedRadioButtonId();
        View radioButtonBuildingType = findViewById(selectedIdBuildingType);
        int idx3 = rgBuildingType.indexOfChild(radioButtonBuildingType);
        RadioButton r3 = (RadioButton)  rgBuildingType.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonBuildingType", selectedText3);

        int selectedIdRoofMake = rgRoofMake.getCheckedRadioButtonId();
        View radioButtonRoofMake = findViewById(selectedIdRoofMake);
        int idx4 = rgRoofMake.indexOfChild(radioButtonRoofMake);
        RadioButton r4 = (RadioButton)  rgRoofMake.getChildAt(idx4);
        String selectedText4 = r4.getText().toString();
        hm.put("radioButtonRoofMake", selectedText4);

        int selectedIdRoofFinish = rgRoofFinish.getCheckedRadioButtonId();
        View radioButtonRoofFinish = findViewById(selectedIdRoofFinish);
        int idx5 = rgRoofFinish.indexOfChild(radioButtonRoofFinish);
        RadioButton r5 = (RadioButton)  rgRoofFinish.getChildAt(idx5);
        String selectedText5 = r5.getText().toString();
        hm.put("radioButtonRoofFinish", selectedText5);

        int selectedIdAppearanceInt = rgAppearanceInt.getCheckedRadioButtonId();
        View radioButtonAppearanceInt = findViewById(selectedIdAppearanceInt);
        int idx6 = rgAppearanceInt.indexOfChild(radioButtonAppearanceInt);
        RadioButton r6 = (RadioButton)  rgAppearanceInt.getChildAt(idx6);
        String selectedText6 = r6.getText().toString();
        hm.put("radioButtonAppearanceInt", selectedText6);

        int selectedIdAppearanceExt = rgAppearanceExt.getCheckedRadioButtonId();
        View radioButtonAppearanceExt = findViewById(selectedIdAppearanceExt);
        int idx7 = rgAppearanceExt.indexOfChild(radioButtonAppearanceExt);
        RadioButton r7 = (RadioButton)  rgAppearanceExt.getChildAt(idx7);
        String selectedText7 = r7.getText().toString();
        hm.put("radioButtonAppearanceExt", selectedText7);


        int selectedIdMaintenanceOfTheBuilding = rgMaintenanceOfTheBuilding.getCheckedRadioButtonId();
        View radioButtonMaintenanceOfTheBuilding = findViewById(selectedIdMaintenanceOfTheBuilding);
        int idx8 = rgMaintenanceOfTheBuilding.indexOfChild(radioButtonMaintenanceOfTheBuilding);
        RadioButton r8 = (RadioButton)  rgMaintenanceOfTheBuilding.getChildAt(idx8);
        String selectedText8 = r8.getText().toString();
        hm.put("radioButtonMaintenanceOfTheBuilding", selectedText8);


        int selectedIdInteriorDecoration = rgInteriorDecoration.getCheckedRadioButtonId();
        View radioButtonInteriorDecoration = findViewById(selectedIdInteriorDecoration);
        int idx9 = rgInteriorDecoration.indexOfChild(radioButtonInteriorDecoration);
        RadioButton r9 = (RadioButton)  rgInteriorDecoration.getChildAt(idx9);
        String selectedText9 = r9.getText().toString();
        hm.put("radioButtonInteriorDecoration", selectedText9);


        int selectedIdKitchen = rgKitchen.getCheckedRadioButtonId();
        View radioButtonKitchen = findViewById(selectedIdKitchen);
        int idx10 = rgKitchen.indexOfChild(radioButtonKitchen);
        RadioButton r10 = (RadioButton)  rgKitchen.getChildAt(idx10);
        String selectedText10 = r10.getText().toString();
        hm.put("radioButtonKitchen", selectedText10);

        int selectedIdClassOfElectrical = rgClassOfElectrical.getCheckedRadioButtonId();
        View radioButtonClassOfElectrical = findViewById(selectedIdClassOfElectrical);
        int idx11 = rgClassOfElectrical.indexOfChild(radioButtonClassOfElectrical);
        RadioButton r11 = (RadioButton)  rgClassOfElectrical.getChildAt(idx11);
        String selectedText11 = r11.getText().toString();
        hm.put("radioButtonClassOfElectrical", selectedText11);

        int selectedIdClassOfSanitary = rgClassOfSanitary.getCheckedRadioButtonId();
        View radioButtonClassOfSanitary = findViewById(selectedIdClassOfSanitary);
        int idx12 = rgClassOfSanitary.indexOfChild(radioButtonClassOfSanitary);
        RadioButton r12 = (RadioButton)  rgClassOfSanitary.getChildAt(idx12);
        String selectedText12 = r12.getText().toString();
        hm.put("radioButtonClassOfSanitary", selectedText12);

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getSpecialAssets();
        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                etAsPerTitleDeed.setText(object.getString("asPerTitleDeed"));
                etAsPerMap.setText(object.getString("asPerMapp"));
                etAsPerSiteSurvey.setText(object.getString("asPerSiteSurveyy"));
                etTotalNumberOfFloors.setText(object.getString("totalNoOfFloors"));
                etFloorOnWhich.setText(object.getString("floorOnWhich"));
                etTypeOfUnit.setText(object.getString("typeOfUnitt"));
                etRoofHeight.setText(object.getString("roofHeight"));


                //CheckBox Flooring
                if (object.getString("cbVitrified").equals("1")){
                    cbVitrified.setChecked(true);
                }else {
                    cbVitrified.setChecked(false);
                }
                if (object.getString("cbCeramic").equals("1")){
                    cbCeramic.setChecked(true);
                }else {
                    cbCeramic.setChecked(false);
                }
                if (object.getString("cbSimpleMarble").equals("1")){
                    cbSimpleMarble.setChecked(true);
                }else {
                    cbSimpleMarble.setChecked(false);
                }
                if (object.getString("cbMarbleChips").equals("1")){
                    cbMarbleChips.setChecked(true);
                }else {
                    cbMarbleChips.setChecked(false);
                }
                if (object.getString("cbMosaic").equals("1")){
                    cbMosaic.setChecked(true);
                }else {
                    cbMosaic.setChecked(false);
                }
                if (object.getString("cbGranite").equals("1")){
                    cbGranite.setChecked(true);
                }else {
                    cbGranite.setChecked(false);
                }
                if (object.getString("cbItalianMarble").equals("1")){
                    cbItalianMarble.setChecked(true);
                }else {
                    cbItalianMarble.setChecked(false);
                }
                if (object.getString("cbKotaStone").equals("1")){
                    cbKotaStone.setChecked(true);
                }else {
                    cbKotaStone.setChecked(false);
                }
                if (object.getString("cbWooden").equals("1")){
                    cbWooden.setChecked(true);
                }else {
                    cbWooden.setChecked(false);
                }
                if (object.getString("cbPcc").equals("1")){
                    cbPcc.setChecked(true);
                }else {
                    cbPcc.setChecked(false);
                }
                if (object.getString("cbImportedMarble").equals("1")){
                    cbImportedMarble.setChecked(true);
                }else {
                    cbImportedMarble.setChecked(false);
                }
                if (object.getString("cbPavers").equals("1")){
                    cbPavers.setChecked(true);
                }else {
                    cbPavers.setChecked(false);
                }
                if (object.getString("cbChequered").equals("1")){
                    cbChequered.setChecked(true);
                }else {
                    cbChequered.setChecked(false);
                }
                if (object.getString("cbBrickTiles").equals("1")){
                    cbBrickTiles.setChecked(true);
                }else {
                    cbBrickTiles.setChecked(false);
                }
                if (object.getString("cbNoFlooring").equals("1")){
                    cbNoFlooring.setChecked(true);
                }else {
                    cbNoFlooring.setChecked(false);
                }
                if (object.getString("cbUnderConstruction").equals("1")){
                    cbUnderConstruction.setChecked(true);
                }else {
                    cbUnderConstruction.setChecked(false);
                }
                if (object.getString("cbAnyOtherType").equals("1")){
                    cbAnyOtherType.setChecked(true);
                }else {
                    cbAnyOtherType.setChecked(false);
                }

                //CheckBox Interior Finishing
                if (object.getString("cbIntSimplePlastered").equals("1")){
                    cbIntSimplePlastered.setChecked(true);
                }else {
                    cbIntSimplePlastered.setChecked(false);
                }
                if (object.getString("cbIntBrickWalls").equals("1")){
                    cbIntBrickWalls.setChecked(true);
                }else {
                    cbIntBrickWalls.setChecked(false);
                }
                if (object.getString("cbIntDesignerTextured").equals("1")){
                    cbIntDesignerTextured.setChecked(true);
                }else {
                    cbIntDesignerTextured.setChecked(false);
                }
                if (object.getString("cbIntPopPunning").equals("1")){
                    cbIntPopPunning.setChecked(true);
                }else {
                    cbIntPopPunning.setChecked(false);
                }
                if (object.getString("cbIntBrickTileCladding").equals("1")){
                    cbIntBrickTileCladding.setChecked(true);
                }else {
                    cbIntBrickTileCladding.setChecked(false);
                }

                //CheckBox Exterior Finishing
                if (object.getString("cbExtSimplePlastered").equals("1")){
                    cbExtSimplePlastered.setChecked(true);
                }else {
                    cbExtSimplePlastered.setChecked(false);
                }
                if (object.getString("cbExtBrickWalls").equals("1")){
                    cbExtBrickWalls.setChecked(true);
                }else {
                    cbExtBrickWalls.setChecked(false);
                }
                if (object.getString("cbExtDesigner").equals("1")){
                    cbExtDesigner.setChecked(true);
                }else {
                    cbExtDesigner.setChecked(false);
                }
                if (object.getString("cbExtPopPunning").equals("1")){
                    cbExtPopPunning.setChecked(true);
                }else {
                    cbExtPopPunning.setChecked(false);
                }
                if (object.getString("cbExtBrickTile").equals("1")){
                    cbExtBrickTile.setChecked(true);
                }else {
                    cbExtBrickTile.setChecked(false);
                }

                //Construction Status
                if (object.getString("radioButtonConstructionStatus").equals("Built-up property in use")){
                    ((RadioButton)rgConstructionStatus.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonConstructionStatus").equals("Under construction")){
                    ((RadioButton)rgConstructionStatus.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonConstructionStatus").equals("No construction")){
                    ((RadioButton)rgConstructionStatus.getChildAt(2)).setChecked(true);
                }



                if (object.getString("radioButtonCoveredBuilt").equals("Covered Area")){
                    ((RadioButton)rgCoveredBuilt.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonCoveredBuilt").equals("Floor Area")){
                    ((RadioButton)rgCoveredBuilt.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonCoveredBuilt").equals("Super Area")){
                    ((RadioButton)rgCoveredBuilt.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonCoveredBuilt").equals("Carpet Area")){
                    ((RadioButton)rgCoveredBuilt.getChildAt(3)).setChecked(true);
                }



                if (object.getString("radioButtonBuildingType").equals("RCC Framed Structure")){
                    ((RadioButton)rgBuildingType.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonBuildingType").equals("Load bearing Pillar Beam column")){
                    ((RadioButton)rgBuildingType.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonBuildingType").equals("Ordinary brick wall structure")){
                    ((RadioButton)rgBuildingType.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonBuildingType").equals("Iron trusses & Pillars")){
                    ((RadioButton)rgBuildingType.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonBuildingType").equals("Scrap abandoned structure")){
                    ((RadioButton)rgBuildingType.getChildAt(4)).setChecked(true);
                }



                if (object.getString("radioButtonRoofMake").equals("RBC")){
                    ((RadioButton)rgRoofMake.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonRoofMake").equals("RCC")){
                    ((RadioButton)rgRoofMake.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonRoofMake").equals("Tin Shed")){
                    ((RadioButton)rgRoofMake.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonRoofMake").equals("GI Shed")){
                    ((RadioButton)rgRoofMake.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonRoofMake").equals("Stone Patla")){
                    ((RadioButton)rgRoofMake.getChildAt(4)).setChecked(true);
                }



                if (object.getString("radioButtonRoofFinish").equals("Simple plaster")){
                    ((RadioButton)rgRoofFinish.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonRoofFinish").equals("POP Punning")){
                    ((RadioButton)rgRoofFinish.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonRoofFinish").equals("POP False Ceiling")){
                    ((RadioButton)rgRoofFinish.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonRoofFinish").equals("Coved roof")){
                    ((RadioButton)rgRoofFinish.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonRoofFinish").equals("No Plaster")){
                    ((RadioButton)rgRoofFinish.getChildAt(4)).setChecked(true);
                }



                if (object.getString("radioButtonAppearanceInt").equals("Excellent")){
                    ((RadioButton)rgAppearanceInt.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceInt").equals("Very Good")){
                    ((RadioButton)rgAppearanceInt.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceInt").equals("Good")){
                    ((RadioButton)rgAppearanceInt.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceInt").equals("Ordinary")){
                    ((RadioButton)rgAppearanceInt.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceInt").equals("Average")){
                    ((RadioButton)rgAppearanceInt.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceInt").equals("Poor")){
                    ((RadioButton)rgAppearanceInt.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceInt").equals("Under construction")){
                    ((RadioButton)rgAppearanceInt.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceInt").equals("No construction")){
                    ((RadioButton)rgAppearanceInt.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceInt").equals("No Survey")){
                    ((RadioButton)rgAppearanceInt.getChildAt(8)).setChecked(true);
                }



                if (object.getString("radioButtonAppearanceExt").equals("Excellent")){
                    ((RadioButton)rgAppearanceExt.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceExt").equals("Very Good")){
                    ((RadioButton)rgAppearanceExt.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceExt").equals("Good")){
                    ((RadioButton)rgAppearanceExt.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceExt").equals("Ordinary")){
                    ((RadioButton)rgAppearanceExt.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceExt").equals("Average")){
                    ((RadioButton)rgAppearanceExt.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceExt").equals("Poor")){
                    ((RadioButton)rgAppearanceExt.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceExt").equals("Under construction")){
                    ((RadioButton)rgAppearanceExt.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonAppearanceExt").equals("No construction")){
                    ((RadioButton)rgAppearanceExt.getChildAt(7)).setChecked(true);
                }


                if (object.getString("radioButtonMaintenanceOfTheBuilding").equals("Very Good")){
                    ((RadioButton)rgMaintenanceOfTheBuilding.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonMaintenanceOfTheBuilding").equals("Average")){
                    ((RadioButton)rgMaintenanceOfTheBuilding.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonMaintenanceOfTheBuilding").equals("Poor")){
                    ((RadioButton)rgMaintenanceOfTheBuilding.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonMaintenanceOfTheBuilding").equals("Under Construction")){
                    ((RadioButton)rgMaintenanceOfTheBuilding.getChildAt(3)).setChecked(true);
                }


                if (object.getString("radioButtonInteriorDecoration").equals("Excellent")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonInteriorDecoration").equals("Very Good")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonInteriorDecoration").equals("Good")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonInteriorDecoration").equals("Ordinary")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonInteriorDecoration").equals("Average")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonInteriorDecoration").equals("Poor")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonInteriorDecoration").equals("Under construction")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonInteriorDecoration").equals("No construction")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonInteriorDecoration").equals("No Survey")){
                    ((RadioButton)rgInteriorDecoration.getChildAt(8)).setChecked(true);
                }


                if (object.getString("radioButtonKitchen").equals("Simple with no cupboard")){
                    ((RadioButton)rgKitchen.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonKitchen").equals("Ordinary with cupboard")){
                    ((RadioButton)rgKitchen.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonKitchen").equals("Normal Modular with chimney")){
                    ((RadioButton)rgKitchen.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonKitchen").equals("High end Modular with chimney")){
                    ((RadioButton)rgKitchen.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonKitchen").equals("Under construction")){
                    ((RadioButton)rgKitchen.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonKitchen").equals("No Survey")){
                    ((RadioButton)rgKitchen.getChildAt(5)).setChecked(true);
                }


                if (object.getString("radioButtonClassOfElectrical").equals("External")){
                    ((RadioButton)rgClassOfElectrical.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfElectrical").equals("Internal")){
                    ((RadioButton)rgClassOfElectrical.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfElectrical").equals("Ordinary fixtures & fittings")){
                    ((RadioButton)rgClassOfElectrical.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfElectrical").equals("Fancy lights")){
                    ((RadioButton)rgClassOfElectrical.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfElectrical").equals("Chandeliers")){
                    ((RadioButton)rgClassOfElectrical.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfElectrical").equals("Concealed lightning")){
                    ((RadioButton)rgClassOfElectrical.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfElectrical").equals("Under construction")){
                    ((RadioButton)rgClassOfElectrical.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfElectrical").equals("No Survey")){
                    ((RadioButton)rgClassOfElectrical.getChildAt(7)).setChecked(true);
                }


                if (object.getString("radioButtonClassOfSanitary").equals("External")){
                    ((RadioButton)rgClassOfSanitary.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfSanitary").equals("Internal")){
                    ((RadioButton)rgClassOfSanitary.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfSanitary").equals("Excellent")){
                    ((RadioButton)rgClassOfSanitary.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfSanitary").equals("Very Good")){
                    ((RadioButton)rgClassOfSanitary.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfSanitary").equals("Good")){
                    ((RadioButton)rgClassOfSanitary.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfSanitary").equals("Simple")){
                    ((RadioButton)rgClassOfSanitary.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfSanitary").equals("Under construction")){
                    ((RadioButton)rgClassOfSanitary.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonClassOfSanitary").equals("No Survey")){
                    ((RadioButton)rgClassOfSanitary.getChildAt(7)).setChecked(true);
                }


            }

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
