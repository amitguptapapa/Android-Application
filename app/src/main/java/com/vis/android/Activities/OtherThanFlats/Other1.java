package com.vis.android.Activities.OtherThanFlats;

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
import android.widget.Toast;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Other1 extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout footer;
    Intent intent;
    TextView crnt_date;
    static TextView timee;
    static final int DATE_DIALOG_ID = 999;
    SimpleDateFormat simpleDateFormat;
    private int myear;
    private int mmonth;
    private int mday;
    EditText filename, surveyr_name, name, contact, loan_ammount;

    RadioGroup property, survey, reason, purpose, loan_type, reason_half, propery_type, measurement;

    RadioButton checkedRadioButton, rg_property1, rg_property2, rg_property3, rg_property4, rg_survey1, rg_survey2, rg_survey3, rg_reason1, rg_reason2, rg_reason3, rg_reason4, rg_reason5, rg_reason6, rg_measure1, rg_measure2, rg_measure3, rg_valuation1, rg_valuation2, rg_valuation3, rg_valuation4, rg_valuation5, rg_valuation6, rg_valuation7, rg_loantype1, rg_loantype2, rg_loantype3, rg_loantype4, rg_loantype5, rg_loantype6, rg_loantype7, rg_loantype8, rg_loantype9, rg_loantype10, rg_loantype11, rg_loantype12, rg_loantype13, rg_pType1, rg_pType2, rg_pType3, rg_pType4, rg_pType5, rg_pType6, rg_pType7, rg_pType8, rg_pType9, rg_pType10, rg_pType11, rg_pType12, rg_pType13, rg_pType14, rg_pType15, rg_pType16, rb_reasonH1, rb_reasonH2, rb_reasonH3;

    String selected_property, selected_survey, selected_reason, selected_reasonH, selected_purpose, selected_loantype, selected_type, selected_measure;

    RelativeLayout date, time;

    CheckBox cb_proprty1, cb_proprty2, cb_proprty3, cb_proprty4, cb_proprty5, cb_proprty6;
    int cb_proprty1Check, cb_proprty2Check, cb_proprty3Check, cb_proprty4Check, cb_proprty5Check, cb_proprty6Check;

    public static HashMap<String, String> hm = new HashMap<>();
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> check_list = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hmap;
    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other1);
        getid();
        setListener();
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        timee.setText(simpleDateFormat.format(calander.getTime()));
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


                }
            }
        });

        reason_half.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_reasonH = (String) checkedRadioButton.getText();

                }
            }
        });
        propery_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_type = (String) checkedRadioButton.getText();


                }
            }
        });

        measurement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_measure = (String) checkedRadioButton.getText();


                }
            }
        });
    }

    public void getid() {
        footer = (RelativeLayout) findViewById(R.id.footer);
        crnt_date = (TextView) findViewById(R.id.tv_date);
        timee = (TextView) findViewById(R.id.tv_timee);
        date = (RelativeLayout) findViewById(R.id.rl_date);
        time = (RelativeLayout) findViewById(R.id.rl_time);

        surveyr_name = (EditText) findViewById(R.id.et_sname);
        name = (EditText) findViewById(R.id.et_name);
        contact = (EditText) findViewById(R.id.et_contact);
        loan_ammount = (EditText) findViewById(R.id.et_amt);
        filename = (EditText) findViewById(R.id.et_filename);

        property = (RadioGroup) findViewById(R.id.rg_property);
        survey = (RadioGroup) findViewById(R.id.rg_survey);
        reason = (RadioGroup) findViewById(R.id.rg_reason);
        purpose = (RadioGroup) findViewById(R.id.rg_purpose);
        loan_type = (RadioGroup) findViewById(R.id.rg_loantype);
        reason_half = (RadioGroup) findViewById(R.id.rg_reasonH);
        propery_type = (RadioGroup) findViewById(R.id.rg_properytype);
        measurement = (RadioGroup) findViewById(R.id.rg_measurement);

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
        rg_reason4 = (RadioButton) findViewById(R.id.rb_reason4);
        rg_reason5 = (RadioButton) findViewById(R.id.rb_reason5);
        rg_reason6 = (RadioButton) findViewById(R.id.rb_reason6);

        rg_valuation1 = (RadioButton) findViewById(R.id.rb_purpose1);
        rg_valuation2 = (RadioButton) findViewById(R.id.rb_purpose2);
        rg_valuation3 = (RadioButton) findViewById(R.id.rb_purpose3);
        rg_valuation4 = (RadioButton) findViewById(R.id.rb_purpose4);
        rg_valuation5 = (RadioButton) findViewById(R.id.rb_purpose5);
        rg_valuation6 = (RadioButton) findViewById(R.id.rb_purpose6);
        rg_valuation7 = (RadioButton) findViewById(R.id.rb_purpose7);

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

        rg_pType1 = (RadioButton) findViewById(R.id.rb_pType1);
        rg_pType2 = (RadioButton) findViewById(R.id.rb_pType2);
        rg_pType3 = (RadioButton) findViewById(R.id.rb_pType3);
        rg_pType4 = (RadioButton) findViewById(R.id.rb_pType4);
        rg_pType5 = (RadioButton) findViewById(R.id.rb_pType5);
        rg_pType6 = (RadioButton) findViewById(R.id.rb_pType6);
        rg_pType7 = (RadioButton) findViewById(R.id.rb_pType7);
        rg_pType8 = (RadioButton) findViewById(R.id.rb_pType8);
        rg_pType9 = (RadioButton) findViewById(R.id.rb_pType9);
        rg_pType10 = (RadioButton) findViewById(R.id.rb_pType10);
        rg_pType11 = (RadioButton) findViewById(R.id.rb_pType11);
        rg_pType12 = (RadioButton) findViewById(R.id.rb_pType12);
        rg_pType13 = (RadioButton) findViewById(R.id.rb_pType13);
        rg_pType14 = (RadioButton) findViewById(R.id.rb_pType14);
        rg_pType15 = (RadioButton) findViewById(R.id.rb_pType15);
        rg_pType16 = (RadioButton) findViewById(R.id.rb_pType16);

        rg_measure1 = (RadioButton) findViewById(R.id.rb_measure1);
        rg_measure2 = (RadioButton) findViewById(R.id.rb_measure2);
        rg_measure3 = (RadioButton) findViewById(R.id.rb_measure3);

        rb_reasonH1 = (RadioButton) findViewById(R.id.rb_reasonH1);
        rb_reasonH2 = (RadioButton) findViewById(R.id.rb_reasonH2);
        rb_reasonH3 = (RadioButton) findViewById(R.id.rb_reasonH3);

        cb_proprty1 = (CheckBox) findViewById(R.id.cb_proprty1);
        cb_proprty2 = (CheckBox) findViewById(R.id.cb_proprty2);
        cb_proprty3 = (CheckBox) findViewById(R.id.cb_proprty3);
        cb_proprty4 = (CheckBox) findViewById(R.id.cb_proprty4);
        cb_proprty5 = (CheckBox) findViewById(R.id.cb_proprty5);
        cb_proprty6 = (CheckBox) findViewById(R.id.cb_proprty6);
    }

    public void setListener() {
        footer.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.footer:
                //  array=new JSONArray(check_list);
                // Log.v("array----", array.toString());
                checkpass();
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

    /*public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        Log.v("checkedvalue=====", String.valueOf(checked));


        switch (view.getId()) {
            case R.id.cb_proprty1:
                hmap = new HashMap<String, String>();
                hmap.put("checkbox_key", cb_proprty1.getTag().toString());
                check_list.add(hmap);
                break;
            case R.id.cb_proprty2:
                hmap = new HashMap<String, String>();
                hmap.put("checkbox_key", cb_proprty2.getTag().toString());
                check_list.add(hmap);
                break;

            case R.id.cb_proprty3:
                hmap = new HashMap<String, String>();
                hmap.put("checkbox_key", cb_proprty3.getTag().toString());
                check_list.add(hmap);
                break;
            case R.id.cb_proprty4:
                hmap = new HashMap<String, String>();
                hmap.put("checkbox_key", cb_proprty4.getTag().toString());
                check_list.add(hmap);
                break;
            case R.id.cb_proprty5:
                hmap = new HashMap<String, String>();
                hmap.put("checkbox_key", cb_proprty5.getTag().toString());
                check_list.add(hmap);
                break;
            case R.id.cb_proprty6:
                hmap = new HashMap<String, String>();
                hmap.put("checkbox_key", cb_proprty6.getTag().toString());
                check_list.add(hmap);
                break;


        }


    }*/

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
            timee.setText(updateTime(hourOfDay, minute));
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
        } else if (property.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select property shown by", Snackbar.LENGTH_SHORT);
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
        } else if (!cb_proprty1.isChecked() && !!cb_proprty2.isChecked() && !cb_proprty3.isChecked() && !cb_proprty4.isChecked() &&
                !cb_proprty5.isChecked() && !cb_proprty6.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select proprty shown by", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else {
            putintoHashMap();
            intent = new Intent(Other1.this, Other2.class);
            startActivity(intent);
        }
    }

    public void putintoHashMap() {

        hm.put("filename", filename.getText().toString());
        hm.put("crnt_date", crnt_date.getText().toString());
        hm.put("timee", timee.getText().toString());
        hm.put("surveyr_name", surveyr_name.getText().toString());
        hm.put("selected_property", selected_property);
        hm.put("name", name.getText().toString());
        hm.put("contact", contact.getText().toString());
        hm.put("selected_survey", selected_survey);
        hm.put("selected_reasonH", selected_reasonH);
        hm.put("selected_reason", selected_reason);
        hm.put("selected_type", selected_type);
        hm.put("selected_measure", selected_measure);
        hm.put("selected_reason", selected_reason);
        hm.put("selected_purpose", selected_purpose);
        hm.put("selected_loantype", selected_loantype);
        hm.put("selected_type", selected_type);
        hm.put("loan_ammount", loan_ammount.getText().toString());


        if (cb_proprty1.isChecked()) {
            cb_proprty1Check = 1;
            hm.put("cb_proprty1", String.valueOf(cb_proprty1Check));
        } else {
            cb_proprty1Check = 0;
            hm.put("cb_proprty1", String.valueOf(cb_proprty1Check));
        }

        if (cb_proprty2.isChecked()) {
            cb_proprty2Check = 1;
            hm.put("cb_proprty2", String.valueOf(cb_proprty2Check));
        } else {
            cb_proprty2Check = 0;
            hm.put("cb_proprty2", String.valueOf(cb_proprty2Check));
        }

        if (cb_proprty3.isChecked()) {
            cb_proprty3Check = 1;
            hm.put("cb_proprty3", String.valueOf(cb_proprty3Check));
        } else {
            cb_proprty3Check = 0;
            hm.put("cb_proprty3", String.valueOf(cb_proprty3Check));
        }

        if (cb_proprty4.isChecked()) {
            cb_proprty4Check = 1;
            hm.put("cb_proprty4", String.valueOf(cb_proprty4Check));
        } else {
            cb_proprty4Check = 0;
            hm.put("cb_proprty4", String.valueOf(cb_proprty4Check));
        }

        if (cb_proprty5.isChecked()) {
            cb_proprty5Check = 1;
            hm.put("cb_proprty5", String.valueOf(cb_proprty5Check));
        } else {
            cb_proprty5Check = 0;
            hm.put("cb_proprty5", String.valueOf(cb_proprty5Check));
        }

        if (cb_proprty6.isChecked()) {
            cb_proprty6Check = 1;
            hm.put("cb_proprty6", String.valueOf(cb_proprty6Check));
        } else {
            cb_proprty6Check = 0;
            hm.put("cb_proprty6", String.valueOf(cb_proprty6Check));
        }
    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getOtherFlats();
        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                filename.setText(object.getString("filename"));
                crnt_date.setText(object.getString("crnt_date"));
                timee.setText(object.getString("timee"));
                surveyr_name.setText(object.getString("surveyr_name"));
                name.setText(object.getString("name"));
                contact.setText(object.getString("contact"));
                loan_ammount.setText(object.getString("loan_ammount"));

                if (object.getString("selected_survey").equals("Only photographs taken (No measurements)")) {
                    rg_survey1.setChecked(true);
                } else if (object.getString("selected_survey").equals("Full survey (inside-out with measurements  & photographs)")) {
                    rg_survey2.setChecked(true);

                } else if (object.getString("selected_survey").equals("Half Survey (Measurements from outside &  photographs)")) {
                    rg_survey3.setChecked(true);

                } else {
                    Toast.makeText(this, "else executed", Toast.LENGTH_SHORT).show();
                }

                if (object.getString("selected_reasonH").equals("Property was locked")) {
                    rb_reasonH1.setChecked(true);
                } else if (object.getString("selected_reasonH").equals("Possessee didn’t allow to inspect the property")) {
                    rb_reasonH2.setChecked(true);

                } else if (object.getString("selected_reasonH").equals("NPA property so couldn’t be surveyed completely")) {
                    rb_reasonH3.setChecked(true);

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


                if (object.getString("selected_type").equals("Flat in Multistoried Apartment")) {
                    rg_pType1.setChecked(true);
                } else if (object.getString("selected_type").equals("Residential House")) {
                    rg_pType2.setChecked(true);

                } else if (object.getString("selected_type").equals("Low Rise Apartment")) {
                    rg_pType3.setChecked(true);

                } else if (object.getString("selected_type").equals("Residential Builder Floor")) {
                    rg_pType4.setChecked(true);

                } else if (object.getString("selected_type").equals("Commercial Land &amp; Building")) {
                    rg_pType5.setChecked(true);

                } else if (object.getString("selected_type").equals("Commercial Office")) {
                    rg_pType6.setChecked(true);

                } else if (object.getString("selected_type").equals("Commercial Shop")) {
                    rg_pType7.setChecked(true);

                } else if (object.getString("selected_type").equals("Commercial Floor")) {
                    rg_pType8.setChecked(true);

                } else if (object.getString("selected_type").equals("Shopping Mall")) {
                    rg_pType9.setChecked(true);

                } else if (object.getString("selected_type").equals("Hotel")) {
                    rg_pType10.setChecked(true);

                } else if (object.getString("selected_type").equals("Industrial")) {
                    rg_pType11.setChecked(true);

                } else if (object.getString("selected_type").equals("Institutional")) {
                    rg_pType12.setChecked(true);

                } else if (object.getString("selected_type").equals("School Building")) {
                    rg_pType13.setChecked(true);

                } else if (object.getString("selected_type").equals("Vacant Residential Plot")) {
                    rg_pType14.setChecked(true);

                } else if (object.getString("selected_type").equals("Vacant Industrial Plot")) {
                    rg_pType15.setChecked(true);

                } else if (object.getString("selected_type").equals("Agricultural Land")) {
                    rg_pType16.setChecked(true);

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


                if (object.getString("selected_reason").equals("Property was locked")) {
                    rg_reason1.setChecked(true);
                } else if (object.getString("selected_reason").equals("It’s a flat in multi storey building so measurement not required")) {
                    rg_reason2.setChecked(true);
                } else if (object.getString("selected_reason").equals("Owner/ possessee didn’t allow it")) {
                    rg_reason3.setChecked(true);
                } else if (object.getString("selected_reason").equals("NPA property so didn’t enter the property")) {
                    rg_reason4.setChecked(true);
                } else if (object.getString("selected_reason").equals("Very Large Property, practically not possible to measure the entire area")) {
                    rg_reason5.setChecked(true);
                } else if (object.getString("selected_reason").equals("Any other Reason")) {
                    rg_reason6.setChecked(true);
                } else {
                }


                if (object.getString("selected_purpose").equals("Distress sale for NPA A/c")) {
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


                if (object.getString("selected_type").equals("Housing Loan")) {
                    rg_loantype1.setChecked(true);
                } else if (object.getString("selected_type").equals("Housing Take Over Loan")) {
                    rg_loantype2.setChecked(true);
                } else if (object.getString("selected_type").equals("Home Improvement Loan")) {
                    rg_loantype3.setChecked(true);
                } else if (object.getString("selected_type").equals("Loan against Property")) {
                    rg_loantype4.setChecked(true);
                } else if (object.getString("selected_type").equals("Construction Loan")) {
                    rg_loantype5.setChecked(true);
                } else if (object.getString("selected_type").equals("Educational Loan")) {
                    rg_loantype6.setChecked(true);
                } else if (object.getString("selected_type").equals("Car Loan")) {
                    rg_loantype7.setChecked(true);
                } else if (object.getString("selected_type").equals("Project Loan")) {
                    rg_loantype8.setChecked(true);
                } else if (object.getString("selected_type").equals("Term Loan")) {
                    rg_loantype9.setChecked(true);
                } else if (object.getString("selected_type").equals("CC Limit enhancement")) {
                    rg_loantype10.setChecked(true);
                } else if (object.getString("selected_type").equals("Cash Credit Limit")) {
                    rg_loantype11.setChecked(true);
                } else if (object.getString("selected_type").equals("Industrial Loan")) {
                    rg_loantype12.setChecked(true);
                } else if (object.getString("selected_type").equals("Not Applicable")) {
                    rg_loantype13.setChecked(true);
                } else {
                }


                if (object.getString("cb_proprty1").equals("1")) {
                    cb_proprty1.setChecked(true);
                } else {
                    cb_proprty1.setChecked(false);
                }


                if (object.getString("cb_proprty2").equals("1")) {
                    cb_proprty2.setChecked(true);
                } else {
                    cb_proprty2.setChecked(false);
                }


                if (object.getString("cb_proprty3").equals("1")) {
                    cb_proprty3.setChecked(true);
                } else {
                    cb_proprty3.setChecked(false);
                }


                if (object.getString("cb_proprty4").equals("1")) {
                    cb_proprty4.setChecked(true);
                } else {
                    cb_proprty4.setChecked(false);
                }


                if (object.getString("cb_proprty5").equals("1")) {
                    cb_proprty5.setChecked(true);
                } else {
                    cb_proprty5.setChecked(false);
                }


                if (object.getString("cb_proprty6").equals("1")) {
                    cb_proprty6.setChecked(true);
                } else {
                    cb_proprty6.setChecked(false);
                }
            }


        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
