package com.vis.android.Activities.Industrial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vis.android.Activities.Industrial.Industrial1.hm;

public class Industrial3 extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout back;
    TextView next;
    Intent intent;

    //EditText
    EditText etDescriptionOfProduct,etRawMaterialUsed,etWhetherStp,etEstablishmentType,etSourceOfPrimaryRaw,etWhetherEtp,etFireFighting,etNoOfResourcesWorking,etPowerSupply,etAuxillaryPower,etHvacSystem,etCoolingSystem,etWaterArrangements,etIsPropertyDemarcated;

    public static ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industrial3);
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
        next=(TextView) findViewById(R.id.next);

        etDescriptionOfProduct = findViewById(R.id.etDescriptionOfProduct);
        etRawMaterialUsed = findViewById(R.id.etRawMaterialUsed);
        etWhetherStp = findViewById(R.id.etWhetherStp);
        etSourceOfPrimaryRaw = findViewById(R.id.etSourceOfPrimaryRaw);
        etWhetherEtp = findViewById(R.id.etWhetherEtp);
        etFireFighting = findViewById(R.id.etFireFighting);
        etNoOfResourcesWorking = findViewById(R.id.etNoOfResourcesWorking);
        etPowerSupply = findViewById(R.id.etPowerSupply);
        etAuxillaryPower = findViewById(R.id.etAuxillaryPower);
        etHvacSystem = findViewById(R.id.etHvacSystem);
        etCoolingSystem = findViewById(R.id.etCoolingSystem);
        etWaterArrangements = findViewById(R.id.etWaterArrangements);
        etIsPropertyDemarcated = findViewById(R.id.etIsPropertyDemarcated);

    }

    public void setListener() {
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.next:
                validation();
                break;
        }
    }

    public void validation(){
        if (etDescriptionOfProduct.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Description of Products Manufactured", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Description of Products Manufactured", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDescriptionOfProduct.requestFocus();
        }

        else if (etRawMaterialUsed.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Raw Material Used", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Raw Material Used", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRawMaterialUsed.requestFocus();
        }
        else if (etWhetherStp.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Whether ETP is Installed", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Whether ETP is Installed", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etWhetherStp.requestFocus();
        }

        else if (etFireFighting.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Fire Fighting System", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Fire Fighting System", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etFireFighting.requestFocus();
        }
        else if (etNoOfResourcesWorking.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter No. of Resources Working in the Plant", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter No. of Resources Working in the Plant", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNoOfResourcesWorking.requestFocus();
        }
        else if (etPowerSupply.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Power Supply Arrangements in the Plant", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Supply Arrangements in the Plant", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPowerSupply.requestFocus();
        }
        else if (etAuxillaryPower.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Auxilliary Power Arrangements in the Plant", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Auxilliary Power Arrangements in the Plant", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAuxillaryPower.requestFocus();
        }
        else if (etHvacSystem.getText().toString().isEmpty()) {
          //  Toast.makeText(this, "Please Enter HVAC System in the Plant", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter HVAC System in the Plant", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etHvacSystem.requestFocus();
        }
        else if (etCoolingSystem.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Cooling System in the Plant", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Cooling System in the Plant", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCoolingSystem.requestFocus();
        }
        else if (etWaterArrangements.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Water Arrangements", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Water Arrangements", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etWaterArrangements.requestFocus();
        }
        else if (etIsPropertyDemarcated.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Is Property Demarcated By Fixed Boundary Wall?", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Is Property Demarcated By Fixed Boundary Wall?", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etIsPropertyDemarcated.requestFocus();
        }
        else
        {
            putIntoHm();
            Intent intent=new Intent(Industrial3.this,Industrial4.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm(){

        hm.put("descriptionOfProducts", etDescriptionOfProduct.getText().toString());
        hm.put("rawMaterialUsed", etRawMaterialUsed.getText().toString());
        hm.put("whetherEtpIsInstalled", etWhetherEtp.getText().toString());
        hm.put("whetherStpIsInstalled",etWhetherStp.getText().toString());
        hm.put("sourcesOfPrimaryRawMaterial", etSourceOfPrimaryRaw.getText().toString());
        hm.put("fireFightingSystem", etFireFighting.getText().toString());
        hm.put("noOfResourcesWorking", etNoOfResourcesWorking.getText().toString());
        hm.put("powerSupplyArrangements", etPowerSupply.getText().toString());
        hm.put("auxilliaryPowerArrangements", etAuxillaryPower.getText().toString());
        hm.put("hvacSystem", etHvacSystem.getText().toString());
        hm.put("coolingSystem", etCoolingSystem.getText().toString());
        hm.put("waterArrangements", etWaterArrangements.getText().toString());
        hm.put("isPropertyDemarcated", etIsPropertyDemarcated.getText().toString());

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getIndustrial();
        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                etDescriptionOfProduct.setText(object.getString("descriptionOfProducts"));
                etRawMaterialUsed.setText(object.getString("rawMaterialUsed"));
                etWhetherEtp.setText(object.getString("whetherEtpIsInstalled"));
                etWhetherStp.setText(object.getString("whetherStpIsInstalled"));
                etSourceOfPrimaryRaw.setText(object.getString("sourcesOfPrimaryRawMaterial"));
                etFireFighting.setText(object.getString("fireFightingSystem"));
                etNoOfResourcesWorking.setText(object.getString("noOfResourcesWorking"));
                etPowerSupply.setText(object.getString("powerSupplyArrangements"));
                etAuxillaryPower.setText(object.getString("auxilliaryPowerArrangements"));
                etHvacSystem.setText(object.getString("hvacSystem"));
                etCoolingSystem.setText(object.getString("coolingSystem"));
                etWaterArrangements.setText(object.getString("waterArrangements"));
                etIsPropertyDemarcated.setText(object.getString("isPropertyDemarcated"));


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
