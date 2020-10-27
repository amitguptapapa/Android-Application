package com.vis.android.Activities.Industrial;

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

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Industrial1 extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout back;
    RelativeLayout footer;
    Intent intent;

    //EditText
    EditText etNameIndustry, etPlantAddress, etLegalOwnerName, etWardName, etZoneName, etMunicipalJurisdiction, etDevelopmentProspects, etSchool, etHospital, etMarket, etMetro, etRailwayStation, etAirport;
    EditText etIsPlantPartOfNotified, etBriefHistory, etTypeOfIndustry, etPlantInceptionDate;

    //CheckBox
    CheckBox cbCorner, cb2SideOpen, cb3SideOpen, cbParkFacing, cbRoadFacing, cbNone;
    int cbCornerCheck = 0, cb2SideOpenCheck = 0, cb3SideOpenCheck = 0, cbParkFacingCheck = 0, cbRoadFacingCheck = 0, cbNoneCheck = 0;

    //RadioGroup
    RadioGroup rgCharacteristicOfLocality, rgBoundariesOfProperty;

    //RadioButton
    RadioButton radioButtonCharacteristic, radioButtonBoundaries;

    //ArrayList
    public static ArrayList<HashMap<String, String>> arrayListData = new ArrayList<>();
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    //HashMap
    public static HashMap<String, String> hm = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industrial1);
        getid();
        setListener();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getid() {
        back = (RelativeLayout) findViewById(R.id.rl_back);
        footer = (RelativeLayout) findViewById(R.id.footer);

        etNameIndustry = findViewById(R.id.etNameIndustry);
        etPlantAddress = findViewById(R.id.etPlantAddress);
        etLegalOwnerName = findViewById(R.id.etLegalOwnerName);
        etWardName = findViewById(R.id.etWardName);
        etZoneName = findViewById(R.id.etZoneName);
        etMunicipalJurisdiction = findViewById(R.id.etMunicipalJurisdiction);
        etDevelopmentProspects = findViewById(R.id.etDevelopmentProspects);
        etSchool = findViewById(R.id.etSchool);
        etHospital = findViewById(R.id.etHospital);
        etMarket = findViewById(R.id.etMarket);
        etMetro = findViewById(R.id.etMetro);
        etRailwayStation = findViewById(R.id.etRailwayStation);
        etAirport = findViewById(R.id.etAirport);
        etIsPlantPartOfNotified = findViewById(R.id.etIsPartPlanOfNotified);
        etBriefHistory = findViewById(R.id.etBriefHistory);
        etTypeOfIndustry = findViewById(R.id.etTypeOfIndustry);
        etPlantInceptionDate = findViewById(R.id.etPlantIncpectionDate);

        cbCorner = findViewById(R.id.cbCorner);
        cb2SideOpen = findViewById(R.id.cb2SideOpen);
        cb3SideOpen = findViewById(R.id.cb3SideOpen);
        cbParkFacing = findViewById(R.id.cbParkFacing);
        cbRoadFacing = findViewById(R.id.cbRoadFacing);
        cbNone = findViewById(R.id.cbNone);

        rgCharacteristicOfLocality = findViewById(R.id.rgCharacteristicsOfLoyality);
        rgBoundariesOfProperty = findViewById(R.id.rgBoundariesOfProperty);


    }

    public void setListener() {
        back.setOnClickListener(this);
        footer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.footer:
                validation();
                break;
        }
    }

    public void validation() {
        if (etNameIndustry.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Name of The Industry", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Name of The Industry", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNameIndustry.requestFocus();
        } else if (!cbCorner.isChecked() && !cbNone.isChecked() && !cbRoadFacing.isChecked() && !cbParkFacing.isChecked() && !cb3SideOpen.isChecked() && !cb2SideOpen.isChecked()) {
            //Toast.makeText(this, "Please Select Location Attributes", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Select Location Attributes", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (rgCharacteristicOfLocality.getCheckedRadioButtonId() == -1) {
            //Toast.makeText(this, "Please Select Characteristic of The Locality", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Select Characteristic of The Locality", Snackbar.LENGTH_SHORT);
            snackbar.show();

        } else if (rgBoundariesOfProperty.getCheckedRadioButtonId() == -1) {
            // Toast.makeText(this, "Please Select Boundaries of The Property", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Select Boundaries of The Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (etPlantAddress.getText().toString().isEmpty()) {
            //    Toast.makeText(this, "Please Enter Plant Address", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Plant Address", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPlantAddress.requestFocus();
        } else if (etLegalOwnerName.getText().toString().isEmpty()) {
            // Toast.makeText(this, "Please Enter Legal Owner Name(s)", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Legal Owner Name(s)", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLegalOwnerName.requestFocus();
        } else if (etWardName.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Ward Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Ward Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etWardName.requestFocus();
        } else if (etZoneName.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Zone Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Zone Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etZoneName.requestFocus();
        } else if (etMunicipalJurisdiction.getText().toString().isEmpty()) {
            // Toast.makeText(this, "Please Enter Municipal Jurisdiction & Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Municipal Jurisdiction & Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMunicipalJurisdiction.requestFocus();
        } else if (etDevelopmentProspects.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Development Prospects of The Area", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Development Prospects of The Area", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDevelopmentProspects.requestFocus();
        } else if (etSchool.getText().toString().isEmpty()) {
            // Toast.makeText(this, "Please Enter Proximity To Civic Amenities: School ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Proximity To Civic Amenities: School", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSchool.requestFocus();
        } else if (etHospital.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Proximity To Civic Amenities: Hospital ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Proximity To Civic Amenities: Hospital", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etHospital.requestFocus();
        } else if (etMarket.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Proximity To Civic Amenities: Market ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Proximity To Civic Amenities: Market", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMarket.requestFocus();
        } else if (etMetro.getText().toString().isEmpty()) {
            // Toast.makeText(this, "Please Enter Proximity To Civic Amenities: Metro ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Proximity To Civic Amenities: Metro", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMetro.requestFocus();
        } else if (etRailwayStation.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Proximity To Civic Amenities: Railway Station ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Proximity To Civic Amenities: Railway Station", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRailwayStation.requestFocus();
        } else if (etAirport.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Proximity To Civic Amenities: Airport ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Proximity To Civic Amenities: Airport", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAirport.requestFocus();
        } else if (etBriefHistory.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Brief History & Description of The Plant ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Brief History & Description of The Plant", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBriefHistory.requestFocus();
        } else if (etTypeOfIndustry.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Type of Industry ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Type of Industry", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeOfIndustry.requestFocus();
        } else if (etPlantInceptionDate.getText().toString().isEmpty()) {
            // Toast.makeText(this, "Please Enter Plant Inception Date ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(footer, "Please Enter Plant Inception Date", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPlantInceptionDate.requestFocus();
        } else {
            putIntoHm();
            Intent intent = new Intent(Industrial1.this, Industrial2.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm() {

        hm.put("nameOfIndustry", etNameIndustry.getText().toString());
        hm.put("plantAddress", etPlantAddress.getText().toString());
        hm.put("legalOwnerName", etLegalOwnerName.getText().toString());
        hm.put("wardName", etWardName.getText().toString());
        hm.put("zoneName", etZoneName.getText().toString());
        hm.put("municipalJurisdiction", etMunicipalJurisdiction.getText().toString());
        hm.put("developmentProspects", etDevelopmentProspects.getText().toString());
        hm.put("school", etSchool.getText().toString());
        hm.put("hospital", etHospital.getText().toString());
        hm.put("market", etMarket.getText().toString());
        hm.put("metro", etMetro.getText().toString());
        hm.put("railwayStation", etRailwayStation.getText().toString());
        hm.put("airport", etAirport.getText().toString());
        hm.put("isPlantPartOfNotified", etIsPlantPartOfNotified.getText().toString());
        hm.put("briefHistory", etBriefHistory.getText().toString());
        hm.put("typeOfIndustry", etTypeOfIndustry.getText().toString());
        hm.put("plantInceptionDate", etPlantInceptionDate.getText().toString());

        if (cbCorner.isChecked()) {
            cbCornerCheck = 1;
            hm.put("cbCorner", String.valueOf(cbCornerCheck));
        } else {
            cbCornerCheck = 0;
            hm.put("cbCorner", String.valueOf(cbCornerCheck));
        }
        if (cb2SideOpen.isChecked()) {
            cb2SideOpenCheck = 1;
            hm.put("cb2SideOpen", String.valueOf(cb2SideOpenCheck));
        } else {
            cb2SideOpenCheck = 0;
            hm.put("cb2SideOpen", String.valueOf(cb2SideOpenCheck));
        }
        if (cb3SideOpen.isChecked()) {
            cb3SideOpenCheck = 1;
            hm.put("cb3SideOpen", String.valueOf(cb3SideOpenCheck));
        } else {
            cb3SideOpenCheck = 0;
            hm.put("cb3SideOpen", String.valueOf(cb3SideOpenCheck));
        }
        if (cbParkFacing.isChecked()) {
            cbParkFacingCheck = 1;
            hm.put("cbParkFacing", String.valueOf(cbParkFacingCheck));
        } else {
            cbParkFacingCheck = 0;
            hm.put("cbParkFacing", String.valueOf(cbParkFacingCheck));
        }
        if (cbRoadFacing.isChecked()) {
            cbRoadFacingCheck = 1;
            hm.put("cbRoadFacing", String.valueOf(cbRoadFacingCheck));
        } else {
            cbRoadFacingCheck = 0;
            hm.put("cbRoadFacing", String.valueOf(cbRoadFacingCheck));
        }

        if (cbNone.isChecked()) {
            cbNoneCheck = 1;
            hm.put("cbNone", String.valueOf(cbNoneCheck));
        } else {
            cbNoneCheck = 0;
            hm.put("cbNone", String.valueOf(cbNoneCheck));
        }


        //((RadioButton) rbtnGrp.getChildAt(2)).setText("your radio button data");

        int selectedIdCharacteristic = rgCharacteristicOfLocality.getCheckedRadioButtonId();
        View radioButtonCharacteristic = findViewById(selectedIdCharacteristic);
        int idx = rgCharacteristicOfLocality.indexOfChild(radioButtonCharacteristic);
        RadioButton r = (RadioButton) rgCharacteristicOfLocality.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonCharacteristic", selectedText);


        int selectedIdBoundaries = rgBoundariesOfProperty.getCheckedRadioButtonId();
        View radioButtonBoundaries = findViewById(selectedIdBoundaries);
        int idx2 = rgBoundariesOfProperty.indexOfChild(radioButtonBoundaries);
        RadioButton r2 = (RadioButton) rgBoundariesOfProperty.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonBoundaries", selectedText2);


    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getIndustrial();
        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                etNameIndustry.setText(object.getString("nameOfIndustry"));
                etPlantAddress.setText(object.getString("plantAddress"));
                etLegalOwnerName.setText(object.getString("legalOwnerName"));
                etWardName.setText(object.getString("wardName"));
                etZoneName.setText(object.getString("zoneName"));
                etMunicipalJurisdiction.setText(object.getString("municipalJurisdiction"));
                etDevelopmentProspects.setText(object.getString("developmentProspects"));
                etSchool.setText(object.getString("school"));
                etHospital.setText(object.getString("hospital"));
                etMarket.setText(object.getString("market"));
                etMetro.setText(object.getString("metro"));
                etRailwayStation.setText(object.getString("railwayStation"));
                etAirport.setText(object.getString("airport"));
                etIsPlantPartOfNotified.setText(object.getString("isPlantPartOfNotified"));
                etBriefHistory.setText(object.getString("briefHistory"));
                etTypeOfIndustry.setText(object.getString("typeOfIndustry"));
                etPlantInceptionDate.setText(object.getString("plantInceptionDate"));

                if (object.getString("cbCorner").equals("1")) {
                    cbCorner.setChecked(true);
                } else {
                    cbCorner.setChecked(false);
                }
                if (object.getString("cb2SideOpen").equals("1")) {
                    cb2SideOpen.setChecked(true);
                } else {
                    cb2SideOpen.setChecked(false);
                }
                if (object.getString("cb3SideOpen").equals("1")) {
                    cb3SideOpen.setChecked(true);
                } else {
                    cb3SideOpen.setChecked(false);
                }
                if (object.getString("cbParkFacing").equals("1")) {
                    cbParkFacing.setChecked(true);
                } else {
                    cbParkFacing.setChecked(false);
                }
                if (object.getString("cbRoadFacing").equals("1")) {
                    cbRoadFacing.setChecked(true);
                } else {
                    cbRoadFacing.setChecked(false);
                }
                if (object.getString("cbNone").equals("1")) {
                    cbNone.setChecked(true);
                } else {
                    cbNone.setChecked(false);
                }


                if (object.getString("radioButtonCharacteristic").equals("Urban Developed")) {
                    ((RadioButton) rgCharacteristicOfLocality.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristic").equals("Urban Under Development")) {
                    ((RadioButton) rgCharacteristicOfLocality.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristic").equals("Semi Urban")) {
                    ((RadioButton) rgCharacteristicOfLocality.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristic").equals("Rural")) {
                    ((RadioButton) rgCharacteristicOfLocality.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonCharacteristic").equals("Backward")) {
                    ((RadioButton) rgCharacteristicOfLocality.getChildAt(4)).setChecked(true);
                }


                if (object.getString("radioButtonBoundaries").equals("North")) {
                    ((RadioButton) rgBoundariesOfProperty.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonBoundaries").equals("South")) {
                    ((RadioButton) rgBoundariesOfProperty.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonBoundaries").equals("East")) {
                    ((RadioButton) rgBoundariesOfProperty.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonBoundaries").equals("West")) {
                    ((RadioButton) rgBoundariesOfProperty.getChildAt(3)).setChecked(true);
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
