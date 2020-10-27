package com.vis.android.Activities.SpecialAssets;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SpecialAssets1 extends AppCompatActivity implements View.OnClickListener {

    //RelativeLayout
    RelativeLayout date;
    RelativeLayout time;
    RelativeLayout next;
    RelativeLayout back;
    Intent intent;

    //TextView
    TextView crnt_date;
    static TextView c_time;

    static final int DATE_DIALOG_ID = 999;
    SimpleDateFormat simpleDateFormat;
    private int myear;
    private int mmonth;
    private int mday;

    //EditText
    EditText etFileNo, etNameOfSurveyor, etName, etContactNumber, etLoanAmount;

    //RadioGroup
    RadioGroup rgPropertyShownBy, rgSurveyType, rgReasonForHalfSurvey, rgTypeOfProperty, rgPropertyMeasurement, rgReasonForNoMeasurement, rgPurposeOfValuation, rgTypeOfLoan;

    //CheckBox
    CheckBox cbEnquiredFromNearby, cbFromScheduleOfProperties, cbFromNamePlate, cbIdentifiesByOwner, cbIdentificationOfProperty, cbSurveyWasNot;
    int cbEnquiredFromNearbyCheck = 0, cbFromScheduleOfPropertiesCheck = 0, cbFromNamePlateCheck = 0, cbIdentifiesByOwnerCheck = 0, cbIdentificationOfPropertyCheck = 0, cbSurveyWasNotCheck = 0;

    //ArrayList
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    //HashMap
    public static HashMap<String, String> hm = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets1);
        getid();
        setListener();

        Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        c_time.setText(simpleDateFormat.format(calander.getTime()));
        setCurrentDateOnView();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getid() {
        next = (RelativeLayout) findViewById(R.id.footer);
        back = (RelativeLayout) findViewById(R.id.rl_back);
        crnt_date = (TextView) findViewById(R.id.tv_date);
        c_time = (TextView) findViewById(R.id.tv_tme);
        date = (RelativeLayout) findViewById(R.id.rl_date);
        time = (RelativeLayout) findViewById(R.id.rl_time);

        //EditText
        etFileNo = findViewById(R.id.etFileNo);
        etNameOfSurveyor = findViewById(R.id.etNameOfSurveyor);
        etName = findViewById(R.id.etName);
        etContactNumber = findViewById(R.id.etContactNumber);
        etLoanAmount = findViewById(R.id.etLoanAmount);

        //RadioGroup
        rgPropertyShownBy = findViewById(R.id.rgPropertyShownBy);
        rgSurveyType = findViewById(R.id.rgSurveyType);
        rgReasonForHalfSurvey = findViewById(R.id.rgReasonForHalfSurvey);
        rgTypeOfProperty = findViewById(R.id.rgTypeOfProperty);
        rgPropertyMeasurement = findViewById(R.id.rgPropertyMeasurement);
        rgReasonForNoMeasurement = findViewById(R.id.rgReasonForNoMeasurement);
        rgPurposeOfValuation = findViewById(R.id.rgPurposeOfValuation);
        rgTypeOfLoan = findViewById(R.id.rgTypeOfLoan);

        //CheckBox
        cbEnquiredFromNearby = findViewById(R.id.cbEnquiredFromNearby);
        cbFromScheduleOfProperties = findViewById(R.id.cbFromScheduleOfProperties);
        cbFromNamePlate = findViewById(R.id.cbFromNamePlate);
        cbIdentifiesByOwner = findViewById(R.id.cbIdentifiesByOwner);
        cbIdentificationOfProperty = findViewById(R.id.cbIdentificationOfProperty);
        cbSurveyWasNot = findViewById(R.id.cbSurveyWasNot);

    }

    public void setListener() {
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.footer:
                validation();
                break;

            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.rl_date:
                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.rl_time:
                showTimePickerDialog(view);
                break;
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @SuppressLint("ValidFragment")
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            c_time.setText(updateTime(hourOfDay, minute));
        }
    }


    // function to get am and pm from time
    private static String updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        return aTime;
    }

    // display current date
    public void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);
        crnt_date.setText(new StringBuilder().append(mday).append("-").append(mmonth + 1).append("-").append(myear).append(" "));

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, myear, mmonth,
                        mday) {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (year < myear)
                            view.updateDate(myear, mmonth, mday);

                        if (monthOfYear < mmonth && year == myear)
                            view.updateDate(myear, mmonth, mday);

                        if (dayOfMonth < mday && year == myear && monthOfYear == mmonth)
                            view.updateDate(myear, mmonth, mday);

                    }
                };
                return _date;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;

            // set selected date into textview
            crnt_date.setText(new StringBuilder().append(mday).append("-").append(mmonth + 1).append("-").append(myear).append(" "));

        }
    };

    public void validation(){
        if (etFileNo.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter File No.", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter File No.", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etFileNo.requestFocus();
        }
        else if (crnt_date.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Date", Snackbar.LENGTH_SHORT);
            snackbar.show();
            crnt_date.requestFocus();
        }
        else if (c_time.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Select Time", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Time", Snackbar.LENGTH_SHORT);
            snackbar.show();
            c_time.requestFocus();
        }
        else if (etNameOfSurveyor.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Surveyor Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Surveyor Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNameOfSurveyor.requestFocus();
        }
        else if (rgPropertyShownBy.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Property Shown By", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Property Shown By", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etName.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etName.requestFocus();
        }
        else if (etContactNumber.getText().toString().isEmpty()) {
           // Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Contact Number", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etContactNumber.requestFocus();
        }
        else if (rgSurveyType.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Survey Type", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Survey Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
            rgSurveyType.requestFocus();
        }
        else if (rgReasonForHalfSurvey.getCheckedRadioButtonId() == -1){
           // Toast.makeText(this, "Please Select Reason for Half Survey or only Photographs taken", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Reason for Half Survey or only Photographs taken", Snackbar.LENGTH_SHORT);
            snackbar.show();
            rgReasonForHalfSurvey.requestFocus();
        }
        else if (!cbEnquiredFromNearby.isChecked() && !cbFromNamePlate.isChecked() && !cbFromScheduleOfProperties.isChecked() && !cbIdentificationOfProperty.isChecked() && !cbIdentifiesByOwner.isChecked() && !cbSurveyWasNot.isChecked()) {
            //Toast.makeText(this, "Please Select How Property is Identified", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select How Property is Identified", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgTypeOfProperty.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Type of Property", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Type of Property", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgPropertyMeasurement.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Property Measurement", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Property Measurement", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgReasonForNoMeasurement.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Reason for No Measurement", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Reason for No Measurement", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgPurposeOfValuation.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Purpose of Valuation", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Purpose of Valuation", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (rgTypeOfLoan.getCheckedRadioButtonId() == -1){
            //Toast.makeText(this, "Please Select Type of Loan", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Type of Loan", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else if (etLoanAmount.getText().toString().isEmpty()) {
            //Toast.makeText(this, "Please Enter Loan Amount", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Loan Amount", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLoanAmount.requestFocus();
        }
        else
        {
            putIntoHm();
            Intent intent=new Intent(SpecialAssets1.this, SpecialAssets2.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm(){

        hm.put("fileNo", etFileNo.getText().toString());
        hm.put("date", crnt_date.getText().toString());
        hm.put("time", c_time.getText().toString());
        hm.put("nameOfSurveyor", etNameOfSurveyor.getText().toString());
        hm.put("name", etName.getText().toString());
        hm.put("contactNumber", etContactNumber.getText().toString());
        hm.put("loanAmount", etLoanAmount.getText().toString());


        if (cbEnquiredFromNearby.isChecked()){
            cbEnquiredFromNearbyCheck = 1;
            hm.put("cbEnquiredFromNearby", String.valueOf(cbEnquiredFromNearbyCheck));
        }else
        {
            cbEnquiredFromNearbyCheck = 0;
            hm.put("cbEnquiredFromNearby", String.valueOf(cbEnquiredFromNearbyCheck));
        }
        if (cbFromScheduleOfProperties.isChecked()){
            cbFromScheduleOfPropertiesCheck = 1;
            hm.put("cbFromScheduleOfProperties", String.valueOf(cbFromScheduleOfPropertiesCheck));
        }
        else
        {
            cbFromScheduleOfPropertiesCheck = 0;
            hm.put("cbFromScheduleOfProperties", String.valueOf(cbFromScheduleOfPropertiesCheck));
        }
        if (cbFromNamePlate.isChecked()){
            cbFromNamePlateCheck = 1;
            hm.put("cbFromNamePlate", String.valueOf(cbFromNamePlateCheck));
        }
        else
        {
            cbFromNamePlateCheck = 0;
            hm.put("cbFromNamePlate", String.valueOf(cbFromNamePlateCheck));
        }
        if (cbIdentifiesByOwner.isChecked()) {
            cbIdentifiesByOwnerCheck = 1;
            hm.put("cbIdentifiesByOwner", String.valueOf(cbIdentifiesByOwnerCheck));
        }
        else
        {
            cbIdentifiesByOwnerCheck = 0;
            hm.put("cbIdentifiesByOwner", String.valueOf(cbIdentifiesByOwnerCheck));
        }
        if (cbIdentificationOfProperty.isChecked()){
            cbIdentificationOfPropertyCheck = 1;
            hm.put("cbIdentificationOfProperty", String.valueOf(cbIdentificationOfPropertyCheck));
        }
        else
        {
            cbIdentificationOfPropertyCheck = 0;
            hm.put("cbIdentificationOfProperty", String.valueOf(cbIdentificationOfPropertyCheck));
        }

        if (cbSurveyWasNot.isChecked()){
            cbSurveyWasNotCheck = 1;
            hm.put("cbSurveyWasNot", String.valueOf(cbSurveyWasNotCheck));
        }
        else
        {
            cbSurveyWasNotCheck = 0;
            hm.put("cbSurveyWasNot", String.valueOf(cbSurveyWasNotCheck));
        }

        int selectedIdPropertyShownBy = rgPropertyShownBy.getCheckedRadioButtonId();
        View radioButtonPropertyShownBy = findViewById(selectedIdPropertyShownBy);
        int idx = rgPropertyShownBy.indexOfChild(radioButtonPropertyShownBy);
        RadioButton r = (RadioButton)  rgPropertyShownBy.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonPropertyShownBy", selectedText);

        int selectedIdSurveyType = rgSurveyType.getCheckedRadioButtonId();
        View radioButtonSurveyType = findViewById(selectedIdSurveyType);
        int idx2 = rgSurveyType.indexOfChild(radioButtonSurveyType);
        RadioButton r2 = (RadioButton)  rgSurveyType.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonSurveyType", selectedText2);

        int selectedIdHalfSurvey = rgReasonForHalfSurvey.getCheckedRadioButtonId();
        View radioButtonHalfSurvey = findViewById(selectedIdHalfSurvey);
        int idx3 = rgReasonForHalfSurvey.indexOfChild(radioButtonHalfSurvey);
        RadioButton r3 = (RadioButton)  rgReasonForHalfSurvey.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonHalfSurvey", selectedText3);

        int selectedIdTypeOfProperty = rgTypeOfProperty.getCheckedRadioButtonId();
        View radioButtonTypeOfProperty = findViewById(selectedIdTypeOfProperty);
        int idx4 = rgTypeOfProperty.indexOfChild(radioButtonTypeOfProperty);
        RadioButton r4 = (RadioButton)  rgTypeOfProperty.getChildAt(idx4);
        String selectedText4 = r4.getText().toString();
        hm.put("radioButtonTypeOfProperty", selectedText4);

        int selectedIdPropertyMeasurement = rgPropertyMeasurement.getCheckedRadioButtonId();
        View radioButtonPropertyMeasurement = findViewById(selectedIdPropertyMeasurement);
        int idx5 = rgPropertyMeasurement.indexOfChild(radioButtonPropertyMeasurement);
        RadioButton r5 = (RadioButton)  rgPropertyMeasurement.getChildAt(idx5);
        String selectedText5 = r5.getText().toString();
        hm.put("radioButtonPropertyMeasurement", selectedText5);

        int selectedIdReasonForNoMeasurement = rgReasonForNoMeasurement.getCheckedRadioButtonId();
        View radioButtonReasonForNoMeasurement = findViewById(selectedIdReasonForNoMeasurement);
        int idx6 = rgReasonForNoMeasurement.indexOfChild(radioButtonReasonForNoMeasurement);
        RadioButton r6 = (RadioButton)  rgReasonForNoMeasurement.getChildAt(idx6);
        String selectedText6 = r6.getText().toString();
        hm.put("radioButtonReasonForNoMeasurement", selectedText6);

        int selectedIdPurposeOfValuation = rgPurposeOfValuation.getCheckedRadioButtonId();
        View radioButtonPurposeOfValuation = findViewById(selectedIdPurposeOfValuation);
        int idx7 = rgPurposeOfValuation.indexOfChild(radioButtonPurposeOfValuation);
        RadioButton r7 = (RadioButton)  rgPurposeOfValuation.getChildAt(idx7);
        String selectedText7 = r7.getText().toString();
        hm.put("radioButtonPurposeOfValuation", selectedText7);

        int selectedIdTypeOfLoan = rgTypeOfLoan.getCheckedRadioButtonId();
        View radioButtonTypeOfLoan = findViewById(selectedIdTypeOfLoan);
        int idx8 = rgTypeOfLoan.indexOfChild(radioButtonTypeOfLoan);
        RadioButton r8 = (RadioButton)  rgTypeOfLoan.getChildAt(idx8);
        String selectedText8 = r8.getText().toString();
        hm.put("radioButtonTypeOfLoan", selectedText8);


    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getSpecialAssets();
        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);


                etFileNo.setText(object.getString("fileNo"));
                crnt_date.setText(object.getString("date"));
                c_time.setText(object.getString("time"));
                etNameOfSurveyor.setText(object.getString("nameOfSurveyor"));
                etName.setText(object.getString("name"));
                etContactNumber.setText(object.getString("contactNumber"));
                etLoanAmount.setText(object.getString("loanAmount"));


                //SetCheckBox
                if (object.getString("cbEnquiredFromNearby").equals("1")){
                    cbEnquiredFromNearby.setChecked(true);
                }else {
                    cbEnquiredFromNearby.setChecked(false);
                }
                if (object.getString("cbFromScheduleOfProperties").equals("1")){
                    cbFromScheduleOfProperties.setChecked(true);
                }else {
                    cbFromScheduleOfProperties.setChecked(false);
                }
                if (object.getString("cbFromNamePlate").equals("1")){
                    cbFromNamePlate.setChecked(true);
                }else {
                    cbFromNamePlate.setChecked(false);
                }
                if (object.getString("cbIdentifiesByOwner").equals("1")){
                    cbIdentifiesByOwner.setChecked(true);
                }else {
                    cbIdentifiesByOwner.setChecked(false);
                }
                if (object.getString("cbIdentificationOfProperty").equals("1")){
                    cbIdentificationOfProperty.setChecked(true);
                }else {
                    cbIdentificationOfProperty.setChecked(false);
                }
                if (object.getString("cbSurveyWasNot").equals("1")){
                    cbSurveyWasNot.setChecked(true);
                }else {
                    cbSurveyWasNot.setChecked(false);
                }


                //SetRadioButton
                if (object.getString("radioButtonPropertyShownBy").equals("Owner")){
                    ((RadioButton)rgPropertyShownBy.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyShownBy").equals("Representative")){
                    ((RadioButton)rgPropertyShownBy.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyShownBy").equals("No one was available")){
                    ((RadioButton)rgPropertyShownBy.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyShownBy").equals("Property is locked, survey could not be done from inside")){
                    ((RadioButton)rgPropertyShownBy.getChildAt(3)).setChecked(true);
                }


                if (object.getString("radioButtonSurveyType").equals("Only photographs taken (No measurements)")){
                    ((RadioButton)rgSurveyType.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonSurveyType").equals("Full survey (inside-out with measurements & photographs)")){
                    ((RadioButton)rgSurveyType.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonSurveyType").equals("Half Survey (Measurements from outside & photographs)")){
                    ((RadioButton)rgSurveyType.getChildAt(2)).setChecked(true);
                }


                if (object.getString("radioButtonHalfSurvey").equals("Property was locked")){
                    ((RadioButton)rgReasonForHalfSurvey.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonHalfSurvey").equals("Possessee didn’t allow to inspect the property")){
                    ((RadioButton)rgReasonForHalfSurvey.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonHalfSurvey").equals("NPA property so couldn’t be surveyed completely")){
                    ((RadioButton)rgReasonForHalfSurvey.getChildAt(2)).setChecked(true);
                }


                if (object.getString("radioButtonTypeOfProperty").equals("Residential Mansion")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Low Rise Apartment")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Residential Builder Floor")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Commercial Land & Building")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Commercial Office")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Commercial Shop")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Commercial Floor")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Shopping Mall")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Hotel")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(8)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Industrial")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(9)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("School")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(10)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("College")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(11)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Farm House")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(12)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfProperty").equals("Any other")){
                    ((RadioButton)rgTypeOfProperty.getChildAt(13)).setChecked(true);
                }


                if (object.getString("radioButtonPropertyMeasurement").equals("Self-measured")){
                    ((RadioButton)rgPropertyMeasurement.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyMeasurement").equals("Sample measurement only")){
                    ((RadioButton)rgPropertyMeasurement.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonPropertyMeasurement").equals("No measurement")){
                    ((RadioButton)rgPropertyMeasurement.getChildAt(2)).setChecked(true);
                }


                if (object.getString("radioButtonReasonForNoMeasurement").equals("Property was locked")){
                    ((RadioButton)rgReasonForNoMeasurement.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonReasonForNoMeasurement").equals("It’s a flat in multi storey building so measurement not required")){
                    ((RadioButton)rgReasonForNoMeasurement.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonReasonForNoMeasurement").equals("Owner/ possessee didn’t allow it")){
                    ((RadioButton)rgReasonForNoMeasurement.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonReasonForNoMeasurement").equals("NPA property so didn’t enter the property")){
                    ((RadioButton)rgReasonForNoMeasurement.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonReasonForNoMeasurement").equals("Very Large Property, practically not possible to measure the entire area")){
                    ((RadioButton)rgReasonForNoMeasurement.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonReasonForNoMeasurement").equals("Any other Reason")){
                    ((RadioButton)rgReasonForNoMeasurement.getChildAt(5)).setChecked(true);
                }


                if (object.getString("radioButtonPurposeOfValuation").equals("Distress sale for NPA A/c.")){
                    ((RadioButton)rgPurposeOfValuation.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonPurposeOfValuation").equals("Value assessment of the asset for creating collateral mortgage")){
                    ((RadioButton)rgPurposeOfValuation.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonPurposeOfValuation").equals("Periodic Re-Valuation for Bank")){
                    ((RadioButton)rgPurposeOfValuation.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonPurposeOfValuation").equals("For DRT Recovery purpose")){
                    ((RadioButton)rgPurposeOfValuation.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonPurposeOfValuation").equals("Capital Gains Wealth Tax purpose")){
                    ((RadioButton)rgPurposeOfValuation.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonPurposeOfValuation").equals("Partition purpose")){
                    ((RadioButton)rgPurposeOfValuation.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonPurposeOfValuation").equals("General Value Assessment")){
                    ((RadioButton)rgPurposeOfValuation.getChildAt(6)).setChecked(true);
                }


                if (object.getString("radioButtonTypeOfLoan").equals("Housing Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(0)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Housing Take Over Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(1)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Home Improvement Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(2)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Loan against Property")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(3)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Construction Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(4)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Educational Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(5)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Car Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(6)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Project Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(7)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Term Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(8)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("CC Limit enhancement")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(9)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Cash Credit Limit")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(10)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Industrial Loan")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(11)).setChecked(true);
                }
                else if (object.getString("radioButtonTypeOfLoan").equals("Not Applicable")){
                    ((RadioButton)rgTypeOfLoan.getChildAt(12)).setChecked(true);
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

