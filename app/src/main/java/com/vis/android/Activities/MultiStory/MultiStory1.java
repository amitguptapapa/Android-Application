package com.vis.android.Activities.MultiStory;

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

public class MultiStory1 extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout footer, back;

    EditText filename, surveyr_name, name, contact, loan_ammount;

    RadioGroup property, survey, reason, measure, purpose, loan_type;

    RadioButton checkedRadioButton, rg_property1, rg_property2, rg_property3, rg_property4, rg_survey1, rg_survey2, rg_survey3, rg_reason1, rg_reason2, rg_reason3, rg_measure1, rg_measure2, rg_measure3, rg_valuation1, rg_valuation2, rg_valuation3, rg_valuation4, rg_valuation5, rg_valuation6, rg_valuation7, rg_loantype1, rg_loantype2, rg_loantype3, rg_loantype4, rg_loantype5, rg_loantype6, rg_loantype7, rg_loantype8, rg_loantype9, rg_loantype10, rg_loantype11, rg_loantype12, rg_loantype13;

    CheckBox proprty1, proprty2, proprty3, proprty4, proprty5, proprty6;
    int proprty1Check, proprty2Check, proprty3Check, proprty4Check, proprty5Check, proprty6Check;

    TextView crnt_date;

    static TextView crnt_time;

    RelativeLayout date, time;

    Intent intent;
    static final int DATE_DIALOG_ID = 999;
    SimpleDateFormat simpleDateFormat;
    private int myear, mmonth, mday;
    String selected_property, selected_survey, selected_reason, selected_measure, selected_purpose, selected_loantype;

    public static ArrayList<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();
    public static HashMap<String, String> hmap = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> alistt = new ArrayList<HashMap<String, String>>();

    JSONArray jsonArrayData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_story1);
        getid();
        setListener();
        try {
            selectDB();
            Log.v("catch=====", "try");

        } catch (Exception e) {
            Log.v("catch===", e.toString());
            e.printStackTrace();
        }
        Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        crnt_time.setText(simpleDateFormat.format(calander.getTime()));

        setCurrentDateOnView();

        property.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_property = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        survey.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_survey = (String) checkedRadioButton.getText();
                    Log.v("*******checkedSurvey", selected_survey);

                }
            }
        });

        reason.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_reason = (String) checkedRadioButton.getText();
                    Log.v("*******checkedSurvey", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        measure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_measure = (String) checkedRadioButton.getText();
                    Log.v("*******checkedSurvey", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        purpose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_purpose = (String) checkedRadioButton.getText();
                    Log.v("*******checkedSurvey", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        loan_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_loantype = (String) checkedRadioButton.getText();
                    Log.v("*******checkedSurvey", selected_loantype);

                }
            }
        });


    }

    public void getid() {
        footer = (RelativeLayout) findViewById(R.id.footer);
        back = (RelativeLayout) findViewById(R.id.rl_back);
        date = (RelativeLayout) findViewById(R.id.rl_date);
        time = (RelativeLayout) findViewById(R.id.rl_time);

        surveyr_name = (EditText) findViewById(R.id.et_sname);
        name = (EditText) findViewById(R.id.et_name);
        contact = (EditText) findViewById(R.id.et_contact);
        loan_ammount = (EditText) findViewById(R.id.et_amt);
        filename = (EditText) findViewById(R.id.et_filename);

        crnt_date = (TextView) findViewById(R.id.tv_date);
        crnt_time = (TextView) findViewById(R.id.tv_ctime);

        property = (RadioGroup) findViewById(R.id.rg_property);
        survey = (RadioGroup) findViewById(R.id.rg_survey);
        reason = (RadioGroup) findViewById(R.id.rg_reason);
        measure = (RadioGroup) findViewById(R.id.rg_measure);
        purpose = (RadioGroup) findViewById(R.id.rg_purpose);
        loan_type = (RadioGroup) findViewById(R.id.rg_loantype);

        rg_property1 = (RadioButton) findViewById(R.id.rb_owner);
        rg_property2 = (RadioButton) findViewById(R.id.rb_reprsntative);
        rg_property3 = (RadioButton) findViewById(R.id.rb_available);
        rg_property4 = (RadioButton) findViewById(R.id.rb_locked);

        rg_survey1 = (RadioButton) findViewById(R.id.rb_styper1);
        rg_survey2 = (RadioButton) findViewById(R.id.rb_styper2);
        rg_survey3 = (RadioButton) findViewById(R.id.rb_styper3);

        rg_reason1 = (RadioButton) findViewById(R.id.rb_reason1);
        rg_reason2 = (RadioButton) findViewById(R.id.rb_reason2);
        rg_reason3 = (RadioButton) findViewById(R.id.rb_reason3);

        rg_measure1 = (RadioButton) findViewById(R.id.rb_measure1);
        rg_measure2 = (RadioButton) findViewById(R.id.rb_measure2);
        rg_measure3 = (RadioButton) findViewById(R.id.rb_measure3);

        rg_valuation1 = (RadioButton) findViewById(R.id.rb_purpose1);
        rg_valuation2 = (RadioButton) findViewById(R.id.rb_purpose2);
        rg_valuation3 = (RadioButton) findViewById(R.id.rb_purpose3);
        rg_valuation4 = (RadioButton) findViewById(R.id.rb_purpose4);
        rg_valuation5 = (RadioButton) findViewById(R.id.rb_purpose5);
        rg_valuation6 = (RadioButton) findViewById(R.id.rb_purpose6);
        rg_valuation7 = (RadioButton) findViewById(R.id.rb_purpose7);

        proprty1 = (CheckBox) findViewById(R.id.cb_proprty1);
        proprty2 = (CheckBox) findViewById(R.id.cb_proprty2);
        proprty3 = (CheckBox) findViewById(R.id.cb_proprty3);
        proprty4 = (CheckBox) findViewById(R.id.cb_proprty4);
        proprty5 = (CheckBox) findViewById(R.id.cb_proprty5);
        proprty6 = (CheckBox) findViewById(R.id.cb_proprty6);

        rg_loantype1 = (RadioButton) findViewById(R.id.rb_loan1);
        rg_loantype2 = (RadioButton) findViewById(R.id.rb_loan2);
        rg_loantype3 = (RadioButton) findViewById(R.id.rb_loan3);
        rg_loantype4 = (RadioButton) findViewById(R.id.rb_loan4);
        rg_loantype5 = (RadioButton) findViewById(R.id.rb_loan5);
        rg_loantype6 = (RadioButton) findViewById(R.id.rb_loan6);
        rg_loantype7 = (RadioButton) findViewById(R.id.rb_loan7);
        rg_loantype8 = (RadioButton) findViewById(R.id.rb_loan8);
        rg_loantype9 = (RadioButton) findViewById(R.id.rb_loan9);
        rg_loantype10 = (RadioButton) findViewById(R.id.rb_loan10);
        rg_loantype11 = (RadioButton) findViewById(R.id.rb_loan11);
        rg_loantype12 = (RadioButton) findViewById(R.id.rb_loan12);
        rg_loantype13 = (RadioButton) findViewById(R.id.rb_loan13);
    }

    public void setListener() {
        footer.setOnClickListener(this);
        back.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.footer:
                checkpass();
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

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.cb_proprty1:
                if (checked) {
                    Log.v("property1===", proprty1.getText().toString());
                }
                // Put some meat on the sandwich
                else {

                }
                // Remove the meat
                break;
            case R.id.cb_proprty2:
                if (checked) {
                    Log.v("property2===", proprty2.getText().toString());
                } else {

                }
                break;
            case R.id.cb_proprty3:
                if (checked) {
                    Log.v("property3===", proprty3.getText().toString());
                } else {

                }
                break;
            case R.id.cb_proprty4:
                if (checked) {
                    Log.v("property4===", proprty4.getText().toString());
                } else {

                }
                break;
            case R.id.cb_proprty5:
                if (checked) {
                    Log.v("property5===", proprty5.getText().toString());
                } else {

                }
                break;
            case R.id.cb_proprty6:
                if (checked) {
                    Log.v("property6===", proprty6.getText().toString());
                } else {

                }
                break;

            // TODO: Veggie sandwich
        }
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
            crnt_time.setText(updateTime(hourOfDay, minute));
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

    public void checkpass() {
        if (filename.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter filename", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (surveyr_name.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter Surveyer name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (name.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (contact.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter contact number", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (survey.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select survey type", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (reason.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select reason for survey", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (!proprty1.isChecked() && !!proprty2.isChecked() && !proprty3.isChecked() && !proprty4.isChecked() &&
                !proprty5.isChecked() && !proprty6.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select how property is identified", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }else if (measure.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select any measurement.", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (purpose.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select purpose for valuation", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (loan_type.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select loan type", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (loan_ammount.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter loan ammount", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else {
            insertDB();
            intent = new Intent(MultiStory1.this, MultiStory2.class);
            startActivity(intent);
        }
    }

    public void insertDB() {
        hmap.put("filename", filename.getText().toString());
        hmap.put("crnt_date", crnt_date.getText().toString());
        hmap.put("crnt_time", crnt_time.getText().toString());
        hmap.put("surveyr_name", surveyr_name.getText().toString());
        hmap.put("selected_property", selected_property);
        hmap.put("name", name.getText().toString());
        hmap.put("contact", contact.getText().toString());
        hmap.put("selected_survey", selected_survey);
        hmap.put("selected_reason", selected_reason);
        hmap.put("selected_measure", selected_measure);
        hmap.put("selected_purpose", selected_purpose);
        hmap.put("selected_loantype", selected_loantype);
        hmap.put("loan_ammount", loan_ammount.getText().toString());

        if (proprty1.isChecked()) {
            proprty1Check = 1;
            hmap.put("cb_proprty1", String.valueOf(proprty1Check));
        } else {
            proprty1Check = 0;
            hmap.put("cb_proprty1", String.valueOf(proprty1Check));
        }

        if (proprty2.isChecked()) {
            proprty2Check = 1;
            hmap.put("cb_proprty2", String.valueOf(proprty2Check));
        } else {
            proprty2Check = 0;
            hmap.put("cb_proprty2", String.valueOf(proprty2Check));
        }

        if (proprty3.isChecked()) {
            proprty3Check = 1;
            hmap.put("cb_proprty3", String.valueOf(proprty3Check));
        } else {
            proprty3Check = 0;
            hmap.put("cb_proprty3", String.valueOf(proprty3Check));
        }

        if (proprty4.isChecked()) {
            proprty4Check = 1;
            hmap.put("cb_proprty4", String.valueOf(proprty4Check));
        } else {
            proprty4Check = 0;
            hmap.put("cb_proprty4", String.valueOf(proprty4Check));
        }

        if (proprty5.isChecked()) {
            proprty5Check = 1;
            hmap.put("cb_proprty5", String.valueOf(proprty5Check));
        } else {
            proprty5Check = 0;
            hmap.put("cb_proprty5", String.valueOf(proprty5Check));
        }

        if (proprty6.isChecked()) {
            proprty6Check = 1;
            hmap.put("cb_proprty6", String.valueOf(proprty6Check));
        } else {
            proprty6Check = 0;
            hmap.put("cb_proprty6", String.valueOf(proprty6Check));
        }
    }

    public void selectDB() throws JSONException {

        alistt = DatabaseController.getSubCat();

        if (alistt != null) {

            Log.v("getfromdb=====", String.valueOf(alistt));

            JSONArray array = new JSONArray(alistt.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                filename.setText(object.getString("filename"));
                crnt_date.setText(object.getString("crnt_date"));
                crnt_time.setText(object.getString("crnt_time"));
                surveyr_name.setText(object.getString("surveyr_name"));
                name.setText(object.getString("name"));
                contact.setText(object.getString("contact"));
                loan_ammount.setText(object.getString("loan_ammount"));

                if (object.getString("selected_property").equals("Owner")) {
                    rg_property1.setChecked(true);
                } else if (object.getString("selected_property").equals("Representative")) {
                    rg_property2.setChecked(true);
                } else if (object.getString("selected_property").equals("No one was available")) {
                    rg_property3.setChecked(true);
                } else if (object.getString("selected_property").equals("Property is locked, survey could not be done from inside")) {
                    rg_property4.setChecked(true);
                } else {

                }

                if (object.getString("selected_survey").equals("Only photographs taken (No measurements)")) {
                    rg_survey1.setChecked(true);
                } else if (object.getString("selected_survey").equals("Full survey (inside-out with measurements  &amp; photographs)  ")) {
                    rg_survey2.setChecked(true);

                } else if (object.getString("selected_survey").equals("Half Survey (Measurements from outside &amp;  photographs)")) {
                    rg_survey3.setChecked(true);

                } else {

                }

                if (object.getString("selected_reason").equals("Property was locked")) {
                    rg_reason1.setChecked(true);
                } else if (object.getString("selected_reason").equals("Possessee didn’t allow to inspect the property")) {
                    rg_reason2.setChecked(true);
                } else if (object.getString("selected_reason").equals("NPA property so couldn’t be surveyed completely")) {
                    rg_reason3.setChecked(true);
                } else {

                }

                if (object.getString("selected_measure").equals("Self-measured")) {
                    rg_measure1.setChecked(true);
                } else if (object.getString("selected_measure").equals("Sample measurement only")) {
                    rg_measure2.setChecked(true);

                } else if (object.getString("selected_measure").equals("No measurement")) {
                    rg_measure3.setChecked(true);

                } else {

                }

                if (object.getString("selected_purpose").equals("Distress sale for NPA A/c.")) {
                    rg_valuation1.setChecked(true);
                } else if (object.getString("selected_purpose").equals("Value assessment of the asset for creating collateral mortgage")) {
                    rg_valuation2.setChecked(true);
                } else if (object.getString("selected_purpose").equals("Periodic Re-Valuation for Bank")) {
                    rg_valuation3.setChecked(true);
                } else if (object.getString("selected_purpose").equals("For DRT Recovery purpose")) {
                    rg_valuation4.setChecked(true);
                } else if (object.getString("selected_purpose").equals("Capital Gains Wealth Tax purpose")) {
                    rg_valuation5.setChecked(true);
                } else if (object.getString("selected_purpose").equals("Partition purpose")) {
                    rg_valuation6.setChecked(true);
                } else if (object.getString("selected_purpose").equals("General Value Assessment")) {
                    rg_valuation7.setChecked(true);
                } else {

                }


                if (object.getString("selected_loantype").equals("Housing Loan")) {
                    rg_loantype1.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Housing Take Over Loan")) {
                    rg_loantype2.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Home Improvement Loan")) {
                    rg_loantype3.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Loan against Property")) {
                    rg_loantype4.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Construction Loan")) {
                    rg_loantype5.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Educational Loan")) {
                    rg_loantype6.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Car Loan")) {
                    rg_loantype7.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Project Loan")) {
                    rg_loantype8.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Term Loan")) {
                    rg_loantype9.setChecked(true);
                } else if (object.getString("selected_loantype").equals("CC Limit enhancement")) {
                    rg_loantype10.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Cash Credit Limit")) {
                    rg_loantype11.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Industrial Loan")) {
                    rg_loantype12.setChecked(true);
                } else if (object.getString("selected_loantype").equals("Not Applicable")) {
                    rg_loantype13.setChecked(true);
                } else {

                }

                if (object.getString("cb_proprty1").equals("1")) {
                    proprty1.setChecked(true);
                } else {
                    proprty1.setChecked(false);
                }

                if (object.getString("cb_proprty2").equals("1")) {
                    proprty2.setChecked(true);
                } else {
                    proprty2.setChecked(false);
                }

                if (object.getString("cb_proprty3").equals("1")) {
                    proprty3.setChecked(true);
                } else {
                    proprty3.setChecked(false);
                }

                if (object.getString("cb_proprty4").equals("1")) {
                    proprty4.setChecked(true);
                } else {
                    proprty4.setChecked(false);
                }

                if (object.getString("cb_proprty5").equals("1")) {
                    proprty5.setChecked(true);
                } else {
                    proprty5.setChecked(false);
                }

                if (object.getString("cb_proprty6").equals("1")) {
                    proprty6.setChecked(true);
                } else {
                    proprty6.setChecked(false);
                }
            }

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
