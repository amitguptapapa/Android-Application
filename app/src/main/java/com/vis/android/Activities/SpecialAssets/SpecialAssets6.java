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
import android.widget.TextView;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vis.android.Activities.SpecialAssets.SpecialAssets1.hm;

public class SpecialAssets6 extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    TextView next;

    RadioGroup rgDemandSupply;

    EditText etYearOfPurchase, etPurchasePrice, etMinimumRate, etMaximumRate, etName1, etContactNumber1, etSalePurchase1, etRentalRate1, etComments1;
    EditText etName2, etContactNumber2, etSalePurchase2, etRentalRate2, etComments2;
    EditText etName3, etContactNumber3, etSalePurchase3, etRentalRate3, etComments3;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets6);
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

        rgDemandSupply = findViewById(R.id.rgDemandSupply);

        etYearOfPurchase = findViewById(R.id.etYearOfPurchase);
        etPurchasePrice = findViewById(R.id.etPurchasePrice);
        etMinimumRate = findViewById(R.id.etMinimunRate);
        etMaximumRate= findViewById(R.id.etMaximumRate);

        etName1 = findViewById(R.id.etName1);
        etContactNumber1 = findViewById(R.id.etContactNumber1);
        etSalePurchase1 = findViewById(R.id.etSalePurchase1);
        etRentalRate1 = findViewById(R.id.etRentalRate1);
        etComments1 = findViewById(R.id.etComments1);

        etName2 = findViewById(R.id.etName2);
        etContactNumber2 = findViewById(R.id.etContactNumber2);
        etSalePurchase2 = findViewById(R.id.etSalePurchase2);
        etRentalRate2 = findViewById(R.id.etRentalRate2);
        etComments2 = findViewById(R.id.etComments2);

        etName3 = findViewById(R.id.etName3);
        etContactNumber3 = findViewById(R.id.etContactNumber3);
        etSalePurchase3 = findViewById(R.id.etSalePurchase3);
        etRentalRate3 = findViewById(R.id.etRentalRate3);
        etComments3 = findViewById(R.id.etComments3);

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
        if (rgDemandSupply.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Demand & Supply Condition", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Demand & Supply Condition", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etYearOfPurchase.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Year of Purchase", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Year of Purchase", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etYearOfPurchase.requestFocus();
        }
        else if (etPurchasePrice.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Purchase Price", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Purchase Price", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPurchasePrice.requestFocus();
        }
        else if (etMinimumRate.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Minimum Rate in the locality", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Minimum Rate in the locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMinimumRate.requestFocus();
        }
        else if (etMaximumRate.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Maximum Rate in the locality", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Maximum Rate in the locality", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etMaximumRate.requestFocus();
        }
        else if (etName1.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Name ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etName1.requestFocus();
        }
        else if (etContactNumber1.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber1.requestFocus();
        }
        else if (etSalePurchase1.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Sale Purchase Rate", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Sale Purchase Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSalePurchase1.requestFocus();
        }
        else if (etRentalRate1.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Rental Rate", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Rental Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRentalRate1.requestFocus();
        }
        else if (etComments1.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Comments", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Comments", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etComments1.requestFocus();
        }

        else if (etName2.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Name ", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etName2.requestFocus();
        }
        else if (etContactNumber2.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber2.requestFocus();
        }
        else if (etSalePurchase2.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Sale Purchase Rate", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Sale Purchase Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSalePurchase2.requestFocus();
        }
        else if (etRentalRate2.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Rental Rate", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Rental Rate", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRentalRate2.requestFocus();
        }
        else if (etComments2.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Comments", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Comments", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etComments2.requestFocus();
        }
        else
        {
            putIntoHm();
            Intent intent=new Intent(SpecialAssets6.this,SpecialAssets7.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm(){

        //int selectedIdLandType = rgLandType.getCheckedRadioButtonId();
        View radioButtonDemandSupply = findViewById(rgDemandSupply.getCheckedRadioButtonId());
        int idx = rgDemandSupply.indexOfChild(radioButtonDemandSupply);
        RadioButton r = (RadioButton)  rgDemandSupply.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonDemandSupply", selectedText);

        hm.put("yearOfPurchase", etYearOfPurchase.getText().toString());
        hm.put("purchasePrice", etPurchasePrice.getText().toString());
        hm.put("minimumRate", etMinimumRate.getText().toString());
        hm.put("maximumRate", etMaximumRate.getText().toString());
        hm.put("name1", etName1.getText().toString());
        hm.put("contactNumber1", etContactNumber1.getText().toString());
        hm.put("salePurchase1", etSalePurchase1.getText().toString());
        hm.put("rentalRate1", etRentalRate1.getText().toString());
        hm.put("comments1", etComments1.getText().toString());

        hm.put("name2", etName2.getText().toString());
        hm.put("contactNumber2", etContactNumber2.getText().toString());
        hm.put("salePurchase2", etSalePurchase2.getText().toString());
        hm.put("rentalRate2", etRentalRate2.getText().toString());
        hm.put("comments2", etComments2.getText().toString());

        hm.put("name3", etName3.getText().toString());
        hm.put("contactNumber3", etContactNumber3.getText().toString());
        hm.put("salePurchase3", etSalePurchase3.getText().toString());
        hm.put("rentalRate3", etRentalRate3.getText().toString());
        hm.put("comments3", etComments3.getText().toString());

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getSpecialAssets();
        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);


                etYearOfPurchase.setText(object.getString("yearOfPurchase"));
                etPurchasePrice.setText(object.getString("purchasePrice"));
                etMinimumRate.setText(object.getString("minimumRate"));
                etMaximumRate.setText(object.getString("maximumRate"));
                etName1.setText(object.getString("name1"));
                etContactNumber1.setText(object.getString("contactNumber1"));
                etSalePurchase1.setText(object.getString("salePurchase1"));
                etRentalRate1.setText(object.getString("rentalRate1"));
                etComments1.setText(object.getString("comments1"));
                etName2.setText(object.getString("name2"));
                etContactNumber2.setText(object.getString("contactNumber2"));
                etSalePurchase2.setText(object.getString("salePurchase2"));
                etRentalRate2.setText(object.getString("rentalRate2"));
                etComments2.setText(object.getString("comments2"));
                etName3.setText(object.getString("name3"));
                etContactNumber3.setText(object.getString("contactNumber3"));
                etSalePurchase3.setText(object.getString("salePurchase3"));
                etRentalRate3.setText(object.getString("rentalRate3"));
                etComments3.setText(object.getString("comments3"));



                if (object.getString("radioButtonDemandSupply").equals("Very Good")){
                    ((RadioButton)rgDemandSupply.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonDemandSupply").equals("Good")){
                    ((RadioButton)rgDemandSupply.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonDemandSupply").equals("Average")){
                    ((RadioButton)rgDemandSupply.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonDemandSupply").equals("Low")){
                    ((RadioButton)rgDemandSupply.getChildAt(3)).setChecked(true);
                }

            }

        }
    }
    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
