package com.vis.android.Activities.SpecialAssets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

public class SpecialAssets3 extends AppCompatActivity implements View.OnClickListener{
    TextView next;
    RelativeLayout back;
    Intent intent;

    EditText etAsPerTitle, etAsPerMap, etAsPerSurvey, etAnyConversion, etIsTheProp;

    RadioGroup rgLandType, rgShapeOfTheLand, rgLevelOfLand, rgFrontageToDepth, rgAreBoundaries, rgIndependentAccess, rgIsPropertyClearly, rgPropertyCurrentlyPossess;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets3);
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
        back=(RelativeLayout)findViewById(R.id.rl_back);
    }

    public void setListener(){
        next.setOnClickListener(this);
        back.setOnClickListener(this);

        etAsPerTitle = findViewById(R.id.etAsPerTitle);
        etAsPerMap = findViewById(R.id.etAsPerMap);
        etAsPerSurvey = findViewById(R.id.etAsPerSurvey);
        etAnyConversion = findViewById(R.id.etAnyConversion);
        etIsTheProp = findViewById(R.id.etIsTheProp);

        rgLandType = findViewById(R.id.rgLandType);
        rgShapeOfTheLand = findViewById(R.id.rgShapeOfTheLand);
        rgLevelOfLand = findViewById(R.id.rgLevelOfLand);
        rgFrontageToDepth = findViewById(R.id.rgFrontageToDepth);
        rgAreBoundaries = findViewById(R.id.rgAreBoundaries);
        rgIndependentAccess = findViewById(R.id.rgIndependentAccess);
        rgIsPropertyClearly = findViewById(R.id.rgIsPropertyClearly);
        rgPropertyCurrentlyPossess = findViewById(R.id.rgPropertyCurrentlyPossess);
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
        if (etAsPerTitle.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Land Area As Per Title", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area As Per Title", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAsPerTitle.requestFocus();
        }
        else if (etAsPerMap.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Land Area As Per Map", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area As Per Map", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAsPerMap.requestFocus();
        }
        else if (etAsPerSurvey.getText().toString().isEmpty()) {
         //   Toast.makeText(this, "Please Enter Land Area As Per Site Survey", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Land Area As Per Site Survey", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAsPerSurvey.requestFocus();
        }
        else if (etAnyConversion.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Any Conversion To The Land Use", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Any Conversion To The Land Use", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAnyConversion.requestFocus();
        }
        else if (rgLandType.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Land Type", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Land Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgShapeOfTheLand.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Shape of the Land", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Shape of the Land", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgLevelOfLand.getCheckedRadioButtonId() == -1){
           // Toast.makeText(this, "Please Select Level of the Land", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Level of the Land", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgFrontageToDepth.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Frontage to depth ratio", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Frontage to depth ratio", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgAreBoundaries.getCheckedRadioButtonId() == -1){
         //   Toast.makeText(this, "Please Select Are Boundaries Matched", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Are Boundaries Matched", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgIndependentAccess.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Independent Access Available To The Property", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Independent Access Available To The Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgIsPropertyClearly.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Is Property Clearly Demarcated", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Is Property Clearly Demarcated", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etIsTheProp.getText().toString().isEmpty()) {
         //   Toast.makeText(this, "Please Enter Is The Property Merged or Colluded ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Is The Property Merged or Colluded", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etIsTheProp.requestFocus();
        }
        else if (rgPropertyCurrentlyPossess.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Property Currently Possessed By", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Property Currently Possessed By", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else
        {
            putIntoHm();
            Intent intent=new Intent(SpecialAssets3.this,SpecialAssets4.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm(){


        hm.put("asPerTitle", etAsPerTitle.getText().toString());
        hm.put("asPerMap", etAsPerMap.getText().toString());
        hm.put("asPerSurvey", etAsPerSurvey.getText().toString());
        hm.put("anyConversion", etAnyConversion.getText().toString());
        hm.put("isThePropertyMerged", etIsTheProp.getText().toString());



        //((RadioButton) rbtnGrp.getChildAt(2)).setText("your radio button data");

        int selectedIdLandType = rgLandType.getCheckedRadioButtonId();
        View radioButtonLandType = findViewById(selectedIdLandType);
        int idx = rgLandType.indexOfChild(radioButtonLandType);
        RadioButton r = (RadioButton)  rgLandType.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonLandType", selectedText);

        int selectedIdShape = rgShapeOfTheLand.getCheckedRadioButtonId();
        View radioButtonShape = findViewById(selectedIdShape);
        int idx2 = rgShapeOfTheLand.indexOfChild(radioButtonShape);
        RadioButton r2 = (RadioButton)  rgShapeOfTheLand.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonShape", selectedText2);

        int selectedIdLevelOfLand = rgLevelOfLand.getCheckedRadioButtonId();
        View radioButtonLevelOfLand = findViewById(selectedIdLevelOfLand);
        int idx3 = rgLevelOfLand.indexOfChild(radioButtonLevelOfLand);
        RadioButton r3 = (RadioButton)  rgLevelOfLand.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonLevelOfLand", selectedText3);

        int selectedIdFrontageToDepth = rgFrontageToDepth.getCheckedRadioButtonId();
        View radioButtonFrontageToDepth = findViewById(selectedIdFrontageToDepth);
        int idx4 = rgFrontageToDepth.indexOfChild(radioButtonFrontageToDepth);
        RadioButton r4 = (RadioButton)  rgFrontageToDepth.getChildAt(idx4);
        String selectedText4 = r4.getText().toString();
        hm.put("radioButtonFrontageToDepth", selectedText4);

        int selectedIdAreBoundaries = rgAreBoundaries.getCheckedRadioButtonId();
        View radioButtonAreBoundaries = findViewById(selectedIdAreBoundaries);
        int idx5 = rgAreBoundaries.indexOfChild(radioButtonAreBoundaries);
        RadioButton r5 = (RadioButton)  rgAreBoundaries.getChildAt(idx5);
        String selectedText5 = r5.getText().toString();
        hm.put("radioButtonAreBoundaries", selectedText5);

        int selectedIdIndependentAccess = rgIndependentAccess.getCheckedRadioButtonId();
        View radioButtonIndependentAccess = findViewById(selectedIdIndependentAccess);
        int idx6 = rgIndependentAccess.indexOfChild(radioButtonIndependentAccess);
        RadioButton r6 = (RadioButton)  rgIndependentAccess.getChildAt(idx6);
        String selectedText6 = r6.getText().toString();
        hm.put("radioButtonIndependentAccess", selectedText6);

        int selectedIdIsPropertyClearly = rgIsPropertyClearly.getCheckedRadioButtonId();
        View radioButtonIsPropertyClearly = findViewById(selectedIdIsPropertyClearly);
        int idx7 = rgIsPropertyClearly.indexOfChild(radioButtonIsPropertyClearly);
        RadioButton r7 = (RadioButton)  rgIsPropertyClearly.getChildAt(idx7);
        String selectedText7 = r7.getText().toString();
        hm.put("radioButtonIsPropertyClearly", selectedText7);

        int selectedIdPropertyCurrentlyPossess = rgPropertyCurrentlyPossess.getCheckedRadioButtonId();
        View radioButtonPropertyCurrentlyPossess = findViewById(selectedIdPropertyCurrentlyPossess);
        int idx8 = rgPropertyCurrentlyPossess.indexOfChild(radioButtonPropertyCurrentlyPossess);
        RadioButton r8 = (RadioButton)  rgPropertyCurrentlyPossess.getChildAt(idx8);
        String selectedText8 = r8.getText().toString();
        hm.put("radioButtonPropertyCurrentlyPossess", selectedText8);
    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getSpecialAssets();
        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);


                etAsPerTitle.setText(object.getString("asPerTitle"));
                etAsPerMap.setText(object.getString("asPerMap"));
                etAsPerSurvey.setText(object.getString("asPerSurvey"));
                etAnyConversion.setText(object.getString("anyConversion"));
                etIsTheProp.setText(object.getString("isThePropertyMerged"));



                if (object.getString("radioButtonLandType").equals("Solid")){
                    ((RadioButton)rgLandType.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonLandType").equals("Rocky")){
                    ((RadioButton)rgLandType.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonLandType").equals("Marsh Land")){
                    ((RadioButton)rgLandType.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonLandType").equals("Reclaimed Land")){
                    ((RadioButton)rgLandType.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonLandType").equals("Water logged")){
                    ((RadioButton)rgLandType.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonLandType").equals("Land locked")){
                    ((RadioButton)rgLandType.getChildAt(5)).setChecked(true);
                }



                if (object.getString("radioButtonShape").equals("Square")){
                    ((RadioButton)rgShapeOfTheLand.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonShape").equals("Rectangular")){
                    ((RadioButton)rgShapeOfTheLand.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonShape").equals("Trapezium")){
                    ((RadioButton)rgShapeOfTheLand.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonShape").equals("Triangular")){
                    ((RadioButton)rgShapeOfTheLand.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonShape").equals("Trapezoid")){
                    ((RadioButton)rgShapeOfTheLand.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonShape").equals("Irregular")){
                    ((RadioButton)rgShapeOfTheLand.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonShape").equals("Not Applicable")){
                    ((RadioButton)rgShapeOfTheLand.getChildAt(6)).setChecked(true);
                }


                if (object.getString("radioButtonLevelOfLand").equals("On road level")){
                    ((RadioButton)rgLevelOfLand.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonLevelOfLand").equals("Below road level")){
                    ((RadioButton)rgLevelOfLand.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonLevelOfLand").equals("Above road level")){
                    ((RadioButton)rgLevelOfLand.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonLevelOfLand").equals("Not Applicable")){
                    ((RadioButton)rgLevelOfLand.getChildAt(3)).setChecked(true);
                }


                if (object.getString("radioButtonFrontageToDepth").equals("Normal frontage")){
                    ((RadioButton)rgFrontageToDepth.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonFrontageToDepth").equals("Less frontage")){
                    ((RadioButton)rgFrontageToDepth.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonFrontageToDepth").equals("Large frontage")){
                    ((RadioButton)rgFrontageToDepth.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonFrontageToDepth").equals("Not Applicable")){
                    ((RadioButton)rgFrontageToDepth.getChildAt(3)).setChecked(true);
                }



                if (object.getString("radioButtonAreBoundaries").equals("Yes")){
                    ((RadioButton)rgAreBoundaries.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonAreBoundaries").equals("No")){
                    ((RadioButton)rgAreBoundaries.getChildAt(1)).setChecked(true);
                }



                if (object.getString("radioButtonIndependentAccess").equals("Clear independent access is available")){
                    ((RadioButton)rgIndependentAccess.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonIndependentAccess").equals("Access available in sharing of other adjoining property")){
                    ((RadioButton)rgIndependentAccess.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonIndependentAccess").equals("No clear access is available")){
                    ((RadioButton)rgIndependentAccess.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonIndependentAccess").equals("Access is closed due to dispute")){
                    ((RadioButton)rgIndependentAccess.getChildAt(3)).setChecked(true);
                }


                if (object.getString("radioButtonIsPropertyClearly").equals("Yes")){
                    ((RadioButton)rgIsPropertyClearly.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonIsPropertyClearly").equals("No")){
                    ((RadioButton)rgIsPropertyClearly.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonIsPropertyClearly").equals("Only with Temporary boundaries")){
                    ((RadioButton)rgIsPropertyClearly.getChildAt(2)).setChecked(true);
                }



                if (object.getString("radioButtonPropertyCurrentlyPossess").equals("Owner")){
                    ((RadioButton)rgPropertyCurrentlyPossess.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyCurrentlyPossess").equals("Vacant")){
                    ((RadioButton)rgPropertyCurrentlyPossess.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyCurrentlyPossess").equals("Lessee")){
                    ((RadioButton)rgPropertyCurrentlyPossess.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyCurrentlyPossess").equals("Under Construction")){
                    ((RadioButton)rgPropertyCurrentlyPossess.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyCurrentlyPossess").equals("Couldn’t be Surveyed")){
                    ((RadioButton)rgPropertyCurrentlyPossess.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyCurrentlyPossess").equals("Property was locked")){
                    ((RadioButton)rgPropertyCurrentlyPossess.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyCurrentlyPossess").equals("Bank sealed")){
                    ((RadioButton)rgPropertyCurrentlyPossess.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyCurrentlyPossess").equals("Court sealed")){
                    ((RadioButton)rgPropertyCurrentlyPossess.getChildAt(7)).setChecked(true);
                }


            }

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
