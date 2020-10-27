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

import static com.vis.android.Activities.Industrial.Industrial1.hm;

public class Industrial2 extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout back;
    TextView tvNext;
    Intent intent;

    //EditText
    EditText etNoOfProductionLines, etDateOfInception,etTotalBlockValue,etIfPlantIsNotInOp,etNameAndFunction,etTypeOfConstruction,etTypeOfFloor,etMainMachines,etConditionOfMachines,etRemainingLifeOfMachine,etRecordOfAnyRecent,etProductionCapacity,etNoTypeHeight;

    //RadioGroup
    RadioGroup rgEstablishmentType,rgPlantType,rgPlantOverallCondition,rgPlantStatus,rgMachineMake;

    public static ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industrial2);
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
        tvNext = findViewById(R.id.tv_Next);

        etNoOfProductionLines = findViewById(R.id.etNoOfProductionLines);
        etDateOfInception = findViewById(R.id.etDateOfInception);
        etTotalBlockValue = findViewById(R.id.etTotalBlockValue);
        etIfPlantIsNotInOp = findViewById(R.id.etIfPlantIsNotInOp);
        etNameAndFunction = findViewById(R.id.etNameAndFunction);
        etTypeOfConstruction = findViewById(R.id.etTypeOfConstruction);
        etTypeOfFloor = findViewById(R.id.etTypeOfFloor);
        etMainMachines = findViewById(R.id.etMainMachines);
        etConditionOfMachines = findViewById(R.id.etConditionOfMachines);
        etRemainingLifeOfMachine = findViewById(R.id.etRemainingLifeOfMachine);
        etRecordOfAnyRecent = findViewById(R.id.etRecordOfAnyRecent);
        etProductionCapacity = findViewById(R.id.etProductionCapacity);
        etNoTypeHeight = findViewById(R.id.etNoTypeHeight);

        rgEstablishmentType = findViewById(R.id.rgEstablishmentType);
        rgPlantType = findViewById(R.id.rgPlantType);
        rgPlantOverallCondition = findViewById(R.id.rgPlantOverallCondition);
        rgPlantStatus = findViewById(R.id.rgPlantStatus);
        rgMachineMake = findViewById(R.id.rgMachineMake);


    }

    public void setListener() {
        back.setOnClickListener(this);
        tvNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.tv_Next:
                validation();
                break;
        }
    }

    private void validation(){
        if (etNoOfProductionLines.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter No. of Production Lines", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter No. of Production Lines", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNoOfProductionLines.requestFocus();
        }
        else if(etDateOfInception.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter Date of Inception of each Production Line", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Date of Inception of each Production Line", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etDateOfInception.requestFocus();
        }
        else if(etTotalBlockValue.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter Total Block Value of The Machines", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Total Block Value of The Machines", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTotalBlockValue.requestFocus();
        }
        else if (rgEstablishmentType.getCheckedRadioButtonId() == -1){
           // Toast.makeText(this, "Please Select Establishment Type", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Select Establishment Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgPlantType.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Plant Type", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Select Plant Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgPlantOverallCondition.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Plant Overall Condition", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Select Plant Overall Condition", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgPlantStatus.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Plant Status", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Select Plant Status", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if(etIfPlantIsNotInOp.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter If Plant is not in Operation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter If Plant is not in Operation", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etIfPlantIsNotInOp.requestFocus();
        }
        else if(etNameAndFunction.getText().toString().isEmpty()){
           // Toast.makeText(this, "Please Enter Name & Function of each Block in Plant", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Name & Function of each Block in Plant", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNameAndFunction.requestFocus();
        }
        else if(etTypeOfConstruction.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter Type of Construction", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Type of Construction", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeOfConstruction.requestFocus();
        }
        else if(etTypeOfFloor.getText().toString().isEmpty()){
           // Toast.makeText(this, "Please Enter Type of Floor", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Type of Floor", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeOfFloor.requestFocus();
        }else if(etMainMachines.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter Main Machines", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Main Machines", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMainMachines.requestFocus();
        }
        else if (rgMachineMake.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Machine Make", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Select Machine Make", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if(etConditionOfMachines.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter Condition of Machines", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Condition of Machines", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etConditionOfMachines.requestFocus();
        }
        else if(etRemainingLifeOfMachine.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter Remaining Life of Machines", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Remaining Life of Machines", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRemainingLifeOfMachine.requestFocus();
        }
        else if(etRecordOfAnyRecent.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter Record of Any Recent Maintenance", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Record of Any Recent Maintenance", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRecordOfAnyRecent.requestFocus();
        }
        else if(etProductionCapacity.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter Production Capacity", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter Production Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etProductionCapacity.requestFocus();
        }
        else if(etNoTypeHeight.getText().toString().isEmpty()){
            //Toast.makeText(this, "Please Enter No./Type/Height of Exhaust/Chimney", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(tvNext, "Please Enter No./Type/Height of Exhaust/Chimney", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNoTypeHeight.requestFocus();
        }

        else {
            putIntoHm();
            intent=new Intent(Industrial2.this,Industrial3.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm(){

        hm.put("noOfProductionLines", etNoOfProductionLines.getText().toString());
        hm.put("dateOfInceptionOfProductionLine", etDateOfInception.getText().toString());
        hm.put("totalBlockValueOfMachines", etTotalBlockValue.getText().toString());
        hm.put("isPlantInOperation", etIfPlantIsNotInOp.getText().toString());
        hm.put("nameAndFunction", etNameAndFunction.getText().toString());
        hm.put("typeOfConstruction", etTypeOfConstruction.getText().toString());
        hm.put("typeOfFloorInProduction", etTypeOfFloor.getText().toString());
        hm.put("mainMachines", etMainMachines.getText().toString());
        hm.put("conditionOfMachines", etConditionOfMachines.getText().toString());
        hm.put("remainingLifeOfMachines", etRemainingLifeOfMachine.getText().toString());
        hm.put("recordOfAnyRecentMaintenance", etRecordOfAnyRecent.getText().toString());
        hm.put("productionCapacity", etProductionCapacity.getText().toString());
        hm.put("noTypeHeight", etNoTypeHeight.getText().toString());




        int selectedIdEstablishment = rgEstablishmentType.getCheckedRadioButtonId();
        View radioButtonEstablishmentType = findViewById(selectedIdEstablishment);
        int idx = rgEstablishmentType.indexOfChild(radioButtonEstablishmentType);
        RadioButton r = (RadioButton)  rgEstablishmentType.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonEstablishmentType", selectedText);


        int selectedIdPlantType = rgPlantType.getCheckedRadioButtonId();
        View radioButtonPlantType = findViewById(selectedIdPlantType);
        int idx2 = rgPlantType.indexOfChild(radioButtonPlantType);
        RadioButton r2 = (RadioButton)  rgPlantType.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonPlantType", selectedText2);

        int selectedIdPlantOverallCondition = rgPlantOverallCondition.getCheckedRadioButtonId();
        View radioButtonPlantOverallCondition = findViewById(selectedIdPlantOverallCondition);
        int idx3 = rgPlantOverallCondition.indexOfChild(radioButtonPlantOverallCondition);
        RadioButton r3 = (RadioButton)  rgPlantOverallCondition.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonPlantOverallCondition", selectedText3);

        int selectedIdPlantStatus = rgPlantStatus.getCheckedRadioButtonId();
        View radioButtonPlantStatus = findViewById(selectedIdPlantStatus);
        int idx4 = rgPlantStatus.indexOfChild(radioButtonPlantStatus);
        RadioButton r4 = (RadioButton)  rgPlantStatus.getChildAt(idx4);
        String selectedText4 = r4.getText().toString();
        hm.put("radioButtonPlantStatus", selectedText4);

        int selectedIdMachineMake = rgMachineMake.getCheckedRadioButtonId();
        View radioButtonMachineMake = findViewById(selectedIdMachineMake);
        int idx5 = rgMachineMake.indexOfChild(radioButtonMachineMake);
        RadioButton r5 = (RadioButton)  rgMachineMake.getChildAt(idx5);
        String selectedText5 = r5.getText().toString();
        hm.put("radioButtonMachineMake", selectedText5);


    }

    public void selectDB() throws JSONException {
        sub_cat = DatabaseController.getIndustrial();
        Log.v("getfromdb2=====", String.valueOf(sub_cat));

        JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

        Log.v("getfromdbarray=====", String.valueOf(array));

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            etNoOfProductionLines.setText(object.getString("noOfProductionLines"));
            etDateOfInception.setText(object.getString("dateOfInceptionOfProductionLine"));
            etTotalBlockValue.setText(object.getString("totalBlockValueOfMachines"));
            etIfPlantIsNotInOp.setText(object.getString("isPlantInOperation"));
            etNameAndFunction.setText(object.getString("nameAndFunction"));
            etTypeOfConstruction.setText(object.getString("typeOfConstruction"));
            etTypeOfFloor.setText(object.getString("typeOfFloorInProduction"));
            etMainMachines.setText(object.getString("mainMachines"));
            etConditionOfMachines.setText(object.getString("conditionOfMachines"));
            etRemainingLifeOfMachine.setText(object.getString("remainingLifeOfMachines"));
            etRecordOfAnyRecent.setText(object.getString("recordOfAnyRecentMaintenance"));
            etProductionCapacity.setText(object.getString("productionCapacity"));
            etNoTypeHeight.setText(object.getString("noTypeHeight"));


            if (object.getString("radioButtonEstablishmentType").equals("Indigenous moulding")){
                ((RadioButton)rgEstablishmentType.getChildAt(0)).setChecked(true);
            }
            else if (object.getString("radioButtonEstablishmentType").equals("EPC contractor")){
                ((RadioButton)rgEstablishmentType.getChildAt(1)).setChecked(true);
            }
            else if (object.getString("radioButtonEstablishmentType").equals("Local Contractor")){
                ((RadioButton)rgEstablishmentType.getChildAt(2)).setChecked(true);
            }



            if (object.getString("radioButtonPlantType").equals("Manual")){
                ((RadioButton)rgPlantType.getChildAt(0)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantType").equals("Semi-automatic")){
                ((RadioButton)rgPlantType.getChildAt(1)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantType").equals("Fully-automatic")){
                ((RadioButton)rgPlantType.getChildAt(2)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantType").equals("Conventional")){
                ((RadioButton)rgPlantType.getChildAt(3)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantType").equals("Non-Conventional")){
                ((RadioButton)rgPlantType.getChildAt(4)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantType").equals("Computerized controlled")){
                ((RadioButton)rgPlantType.getChildAt(5)).setChecked(true);
            }



            if (object.getString("radioButtonPlantOverallCondition").equals("Newly Commisioned")){
                ((RadioButton)rgPlantOverallCondition.getChildAt(0)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantOverallCondition").equals("Excellent")){
                ((RadioButton)rgPlantOverallCondition.getChildAt(1)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantOverallCondition").equals("Very Good")){
                ((RadioButton)rgPlantOverallCondition.getChildAt(2)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantOverallCondition").equals("Good")){
                ((RadioButton)rgPlantOverallCondition.getChildAt(3)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantOverallCondition").equals("Average")){
                ((RadioButton)rgPlantOverallCondition.getChildAt(4)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantOverallCondition").equals("Poor")){
                ((RadioButton)rgPlantOverallCondition.getChildAt(5)).setChecked(true);
            }



            if (object.getString("radioButtonPlantStatus").equals("In Operation")){
                ((RadioButton)rgPlantStatus.getChildAt(0)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantStatus").equals("Not Running")){
                ((RadioButton)rgPlantStatus.getChildAt(1)).setChecked(true);
            }
            else if (object.getString("radioButtonPlantStatus").equals("Stopped For Maintenance")){
                ((RadioButton)rgPlantStatus.getChildAt(2)).setChecked(true);
            }


            if (object.getString("radioButtonMachineMake").equals("Domestic Branded")){
                ((RadioButton)rgMachineMake.getChildAt(0)).setChecked(true);
            }
            else if (object.getString("radioButtonMachineMake").equals("Local made")){
                ((RadioButton)rgMachineMake.getChildAt(1)).setChecked(true);
            }
            else if (object.getString("radioButtonMachineMake").equals("Imported")){
                ((RadioButton)rgMachineMake.getChildAt(2)).setChecked(true);
            }



            //GroupHousing1.etProjectName.setText(object.getString("projectName"));

            //String projectName = object.getString("letterOfIntent");
            //Log.e("!!!letter", projectName);

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
