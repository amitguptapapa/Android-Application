package com.vis.android.Activities.OtherThanFlats;

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

import static com.vis.android.Activities.OtherThanFlats.Other1.hm;

public class Other5 extends AppCompatActivity implements View.OnClickListener {
    TextView next,previous;
    Intent intent;
    EditText bAge, running_mtr, height, width, finish, lMake, lCapacity, pMake, pCapacity, sComments;
    RadioGroup water, wooden_wok, maintanence, wall, liftes, garden;
    String selected_water, selected_wooden_wok, selected_maintanence, selected_wall, selected_liftes, selected_garden;
    RadioButton checkedRadioButton, rb_water1, rb_water2, rb_water3, rb_woodenwork1, rb_woodenwork2, rb_woodenwork3, rb_woodenwork4, rb_woodenwork5, rb_woodenwork6, rb_woodenwork7, rb_woodenwork8, rb_woodenwork9, rb_maintanence1, rb_maintanence2, rb_maintanence3, rb_maintanence4, rb_boundries1, rb_boundries2, rb_lifts1, rb_lifts2, rb_garden1, rb_garden2, rb_garden3, rb_garden4, rb_bWall1, rb_bWall2;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    CheckBox cb_defectes1, cb_defectes2, cb_defectes3, cb_defectes4, cb_defectes5, cb_defectes6, cb_defectes7;
    CheckBox cb_vio1, cb_vio2, cb_vio3, cb_vio4, cb_vio5;
    CheckBox cb_backup1, cb_backup2;
    CheckBox cb_facility1, cb_facility2, cb_facility3, cb_facility4, cb_facility5, cb_facility6, cb_facility7;
    int cb_facility1Check, cb_facility2Check, cb_facility3Check, cb_facility4Check, cb_facility5Check, cb_facility6Check, cb_facility7Check;
    int cb_backup1Check, cb_backup2Check;
    int cb_vio1Check, cb_vio2Check, cb_vio3Check, cb_vio4Check, cb_vio5Check;
    int cb_defectes1Check, cb_defectes2Check, cb_defectes3Check, cb_defectes4Check, cb_defectes5Check, cb_defectes6Check, cb_defectes7Check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other5);
        getid();
        setListener();
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("hashmap====", String.valueOf(hm));
        water.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_water = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        wooden_wok.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_wooden_wok = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        maintanence.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_maintanence = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        wall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_wall = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        liftes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_liftes = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        garden.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_garden = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

    }

    public void getid() {
        next = (TextView) findViewById(R.id.next);
        previous = (TextView) findViewById(R.id.tv_previous);
        water = (RadioGroup) findViewById(R.id.rg_water);
        wooden_wok = (RadioGroup) findViewById(R.id.rg_woodenwork);
        maintanence = (RadioGroup) findViewById(R.id.rg_bMaintenence);
        wall = (RadioGroup) findViewById(R.id.rg_bWall);
        liftes = (RadioGroup) findViewById(R.id.rg_lifts);
        garden = (RadioGroup) findViewById(R.id.rg_garden);

        rb_water1 = (RadioButton) findViewById(R.id.rb_water1);
        rb_water2 = (RadioButton) findViewById(R.id.rb_water2);
        rb_water3 = (RadioButton) findViewById(R.id.rb_water3);
        rb_woodenwork1 = (RadioButton) findViewById(R.id.rb_woodenwork1);
        rb_woodenwork2 = (RadioButton) findViewById(R.id.rb_woodenwork2);
        rb_woodenwork3 = (RadioButton) findViewById(R.id.rb_woodenwork3);
        rb_woodenwork4 = (RadioButton) findViewById(R.id.rb_woodenwork4);
        rb_woodenwork5 = (RadioButton) findViewById(R.id.rb_woodenwork5);
        rb_woodenwork6 = (RadioButton) findViewById(R.id.rb_woodenwork6);
        rb_woodenwork7 = (RadioButton) findViewById(R.id.rb_woodenwork7);
        rb_woodenwork8 = (RadioButton) findViewById(R.id.rb_woodenwork8);
        rb_woodenwork9 = (RadioButton) findViewById(R.id.rb_woodenwork9);
        rb_maintanence1 = (RadioButton) findViewById(R.id.rb_maintenence1);
        rb_maintanence2 = (RadioButton) findViewById(R.id.rb_maintenence2);
        rb_maintanence3 = (RadioButton) findViewById(R.id.rb_maintenence3);
        rb_maintanence4 = (RadioButton) findViewById(R.id.rb_maintenence4);
        rb_boundries1 = (RadioButton) findViewById(R.id.rb_boundries1);
        rb_boundries2 = (RadioButton) findViewById(R.id.rb_boundries2);
        rb_boundries1 = (RadioButton) findViewById(R.id.rb_boundries1);
        rb_lifts1 = (RadioButton) findViewById(R.id.rb_lifts1);
        rb_lifts2 = (RadioButton) findViewById(R.id.rb_lifts2);
        rb_garden1 = (RadioButton) findViewById(R.id.rb_garden1);
        rb_garden2 = (RadioButton) findViewById(R.id.rb_garden2);
        rb_garden3 = (RadioButton) findViewById(R.id.rb_garden3);
        rb_garden4 = (RadioButton) findViewById(R.id.rb_garden4);

        rb_bWall1 = (RadioButton) findViewById(R.id.rb_bWall1);
        rb_bWall2 = (RadioButton) findViewById(R.id.rb_bWall2);

        bAge = (EditText) findViewById(R.id.et_bAge);
        running_mtr = (EditText) findViewById(R.id.et_rMtr);
        height = (EditText) findViewById(R.id.et_height);
        width = (EditText) findViewById(R.id.et_width);
        finish = (EditText) findViewById(R.id.et_finish);
        lMake = (EditText) findViewById(R.id.et_make);
        lCapacity = (EditText) findViewById(R.id.et_capacity);
        pMake = (EditText) findViewById(R.id.et_makee);
        pCapacity = (EditText) findViewById(R.id.et_capacityy);
        sComments = (EditText) findViewById(R.id.sCommnets);

        cb_defectes1 = (CheckBox) findViewById(R.id.cb_defectes1);
        cb_defectes2 = (CheckBox) findViewById(R.id.cb_defectes2);
        cb_defectes3 = (CheckBox) findViewById(R.id.cb_defectes3);
        cb_defectes4 = (CheckBox) findViewById(R.id.cb_defectes4);
        cb_defectes5 = (CheckBox) findViewById(R.id.cb_defectes5);
        cb_defectes6 = (CheckBox) findViewById(R.id.cb_defectes6);
        cb_defectes7 = (CheckBox) findViewById(R.id.cb_defectes7);

        cb_vio1 = (CheckBox) findViewById(R.id.cb_vio1);
        cb_vio2 = (CheckBox) findViewById(R.id.cb_vio2);
        cb_vio3 = (CheckBox) findViewById(R.id.cb_vio3);
        cb_vio4 = (CheckBox) findViewById(R.id.cb_vio4);
        cb_vio5 = (CheckBox) findViewById(R.id.cb_vio5);

        cb_backup1 = (CheckBox) findViewById(R.id.cb_backup1);
        cb_backup2 = (CheckBox) findViewById(R.id.cb_backup2);

        cb_facility1 = (CheckBox) findViewById(R.id.cb_facility1);
        cb_facility2 = (CheckBox) findViewById(R.id.cb_facility2);
        cb_facility3 = (CheckBox) findViewById(R.id.cb_facility3);
        cb_facility4 = (CheckBox) findViewById(R.id.cb_facility4);
        cb_facility5 = (CheckBox) findViewById(R.id.cb_facility5);
        cb_facility6 = (CheckBox) findViewById(R.id.cb_facility6);
        cb_facility7 = (CheckBox) findViewById(R.id.cb_facility7);

    }

    public void setListener() {
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                checkpass();
                break;

            case R.id.tv_previous:
                onBackPressed();
                break;


        }
    }

    public void checkpass() {
        if (water.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select water arrangement", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (wooden_wok.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select wooden work", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (bAge.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter building age", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (running_mtr.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter running mtr", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (height.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter height", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (height.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter width", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (height.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter finish", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (lMake.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter lift make", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (lCapacity.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter lift capacity", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (pMake.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter power make", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (pCapacity.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter power capacity", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (maintanence.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select maintenance of building", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (wall.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select boundry wall", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (liftes.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select lift/elevator", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (garden.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select garden/landscaping", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (!cb_defectes1.isChecked() && !cb_defectes2.isChecked() && !cb_defectes3.isChecked() && !cb_defectes4.isChecked() && !cb_defectes5.isChecked() && !cb_defectes6.isChecked() && !cb_defectes7.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select defect", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (!cb_vio1.isChecked() && !cb_vio2.isChecked() && !cb_vio3.isChecked() && !cb_vio4.isChecked() && !cb_vio5.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select violation", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else {
            putintoHashMap();
            intent = new Intent(Other5.this, Other6.class);
            startActivity(intent);
        }
    }

    public void putintoHashMap() {
        hm.put("selected_water", selected_water);
        hm.put("selected_wooden_wok", selected_wooden_wok);
        hm.put("building_age", bAge.getText().toString());
        hm.put("selected_maintanence", selected_maintanence);
        hm.put("selected_wall", selected_wall);
        hm.put("running_mtr", running_mtr.getText().toString());
        hm.put("height", height.getText().toString());
        hm.put("width", width.getText().toString());
        hm.put("finish", finish.getText().toString());
        hm.put("selected_liftes", selected_liftes);
        hm.put("lift_make", lMake.getText().toString());
        hm.put("lift_capacity", lCapacity.getText().toString());
        hm.put("power_make", pMake.getText().toString());
        hm.put("power_capacity", pCapacity.getText().toString());
        hm.put("selected_garden", selected_garden);
        hm.put("spacial_comment", sComments.getText().toString());

        if (cb_defectes1.isChecked()) {
            cb_defectes1Check = 1;
            hm.put("cb_defectes1", String.valueOf(cb_defectes1Check));
        } else {
            cb_defectes1Check = 0;
            hm.put("cb_defectes1", String.valueOf(cb_defectes1Check));
        }

        if (cb_defectes2.isChecked()) {
            cb_defectes2Check = 1;
            hm.put("cb_defectes2", String.valueOf(cb_defectes2Check));
        } else {
            cb_defectes2Check = 0;
            hm.put("cb_defectes2", String.valueOf(cb_defectes1Check));
        }

        if (cb_defectes3.isChecked()) {
            cb_defectes3Check = 1;
            hm.put("cb_defectes3", String.valueOf(cb_defectes3Check));
        } else {
            cb_defectes3Check = 0;
            hm.put("cb_defectes3", String.valueOf(cb_defectes3Check));
        }

        if (cb_defectes4.isChecked()) {
            cb_defectes4Check = 1;
            hm.put("cb_defectes4", String.valueOf(cb_defectes4Check));
        } else {
            cb_defectes4Check = 0;
            hm.put("cb_defectes4", String.valueOf(cb_defectes4Check));
        }

        if (cb_defectes5.isChecked()) {
            cb_defectes5Check = 1;
            hm.put("cb_defectes5", String.valueOf(cb_defectes5Check));
        } else {
            cb_defectes5Check = 0;
            hm.put("cb_defectes5", String.valueOf(cb_defectes5Check));
        }

        if (cb_defectes6.isChecked()) {
            cb_defectes6Check = 1;
            hm.put("cb_defectes6", String.valueOf(cb_defectes6Check));
        } else {
            cb_defectes6Check = 0;
            hm.put("cb_defectes6", String.valueOf(cb_defectes6Check));
        }

        if (cb_defectes7.isChecked()) {
            cb_defectes7Check = 1;
            hm.put("cb_defectes7", String.valueOf(cb_defectes7Check));
        } else {
            cb_defectes7Check = 0;
            hm.put("cb_defectes7", String.valueOf(cb_defectes7Check));
        }

        //======================

        if (cb_vio1.isChecked()) {
            cb_vio1Check = 1;
            hm.put("cb_vio1", String.valueOf(cb_vio1Check));
        } else {
            cb_vio1Check = 0;
            hm.put("cb_vio1", String.valueOf(cb_vio1Check));
        }

        if (cb_vio2.isChecked()) {
            cb_vio2Check = 1;
            hm.put("cb_vio2", String.valueOf(cb_vio2Check));
        } else {
            cb_vio2Check = 0;
            hm.put("cb_vio2", String.valueOf(cb_vio2Check));
        }

        if (cb_vio3.isChecked()) {
            cb_vio3Check = 1;
            hm.put("cb_vio3", String.valueOf(cb_vio3Check));
        } else {
            cb_vio3Check = 0;
            hm.put("cb_vio3", String.valueOf(cb_vio3Check));
        }

        if (cb_vio4.isChecked()) {
            cb_vio4Check = 1;
            hm.put("cb_vio4", String.valueOf(cb_vio4Check));
        } else {
            cb_vio4Check = 0;
            hm.put("cb_vio4", String.valueOf(cb_vio4Check));
        }

        //=========================


        if (cb_backup1.isChecked()) {
            cb_backup1Check = 1;
            hm.put("cb_backup1", String.valueOf(cb_backup1Check));
        } else {
            cb_backup1Check = 0;
            hm.put("cb_backup1", String.valueOf(cb_backup1Check));

        }

        if (cb_backup2.isChecked()) {
            cb_backup2Check = 1;
            hm.put("cb_backup2", String.valueOf(cb_backup2Check));

        } else {
            cb_backup2Check = 0;
            hm.put("cb_backup2", String.valueOf(cb_backup2Check));

        }
        //=========================

        if (cb_facility1.isChecked()) {
            cb_facility1Check = 1;
            hm.put("cb_facility1", String.valueOf(cb_facility1Check));

        } else {
            cb_facility1Check = 0;
            hm.put("cb_facility1", String.valueOf(cb_facility1Check));

        }

        if (cb_facility2.isChecked()) {
            cb_facility2Check = 1;
            hm.put("cb_facility2", String.valueOf(cb_facility2Check));

        } else {
            cb_facility2Check = 0;
            hm.put("cb_facility2", String.valueOf(cb_facility2Check));

        }

        if (cb_facility3.isChecked()) {
            cb_facility3Check = 1;
            hm.put("cb_facility3", String.valueOf(cb_facility3Check));

        } else {
            cb_facility3Check = 0;
            hm.put("cb_facility3", String.valueOf(cb_facility3Check));

        }

        if (cb_facility4.isChecked()) {
            cb_facility4Check = 1;
            hm.put("cb_facility4", String.valueOf(cb_facility4Check));

        } else {
            cb_facility4Check = 0;
            hm.put("cb_facility4", String.valueOf(cb_facility4Check));

        }

        if (cb_facility5.isChecked()) {
            cb_facility5Check = 1;
            hm.put("cb_facility5", String.valueOf(cb_facility5Check));

        } else {
            cb_facility5Check = 0;
            hm.put("cb_facility5", String.valueOf(cb_facility5Check));

        }

        if (cb_facility6.isChecked()) {
            cb_facility6Check = 1;
            hm.put("cb_facility6", String.valueOf(cb_facility6Check));

        } else {
            cb_facility6Check = 0;
            hm.put("cb_facility6", String.valueOf(cb_facility6Check));

        }

        if (cb_facility7.isChecked()) {
            cb_facility7Check = 1;
            hm.put("cb_facility7", String.valueOf(cb_facility7Check));

        } else {
            cb_facility7Check = 0;
            hm.put("cb_facility7", String.valueOf(cb_facility7Check));

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

                bAge.setText(object.getString("building_age"));
                running_mtr.setText(object.getString("running_mtr"));
                height.setText(object.getString("height"));
                width.setText(object.getString("width"));
                finish.setText(object.getString("finish"));
                lMake.setText(object.getString("lift_make"));
                lCapacity.setText(object.getString("lift_capacity"));
                pMake.setText(object.getString("power_make"));
                pCapacity.setText(object.getString("power_capacity"));
                sComments.setText(object.getString("spacial_comment"));
                pMake.setText(object.getString("power_make"));

                //===================


                if (object.getString("cb_backup1").equals("1")) {
                    cb_backup1.setChecked(true);
                } else {
                    cb_backup1.setChecked(false);
                }

                if (object.getString("cb_backup2").equals("1")) {
                    cb_backup2.setChecked(true);
                } else {
                    cb_backup2.setChecked(false);
                }


                if (object.getString("cb_facility1").equals("1")) {
                    cb_facility1.setChecked(true);
                } else {
                    cb_facility1.setChecked(false);
                }

                if (object.getString("cb_facility2").equals("1")) {
                    cb_facility2.setChecked(true);
                } else {
                    cb_facility2.setChecked(false);
                }

                if (object.getString("cb_facility3").equals("1")) {
                    cb_facility3.setChecked(true);
                } else {
                    cb_facility3.setChecked(false);
                }

                if (object.getString("cb_facility4").equals("1")) {
                    cb_facility4.setChecked(true);
                } else {
                    cb_facility4.setChecked(false);
                }

                if (object.getString("cb_facility5").equals("1")) {
                    cb_facility5.setChecked(true);
                } else {
                    cb_facility5.setChecked(false);
                }

                if (object.getString("cb_facility6").equals("1")) {
                    cb_facility6.setChecked(true);
                } else {
                    cb_facility6.setChecked(false);
                }

                if (object.getString("cb_facility7").equals("1")) {
                    cb_facility7.setChecked(true);
                } else {
                    cb_facility7.setChecked(false);
                }


                if (object.getString("selected_water").equals("Jet pump")) {
                    rb_water1.setChecked(true);
                } else if (object.getString("selected_property").equals("Submersible")) {
                    rb_water2.setChecked(true);

                } else if (object.getString("selected_property").equals("Jal board supply")) {
                    rb_water3.setChecked(true);

                } else {

                }


                if (object.getString("selected_wooden_wok").equals("Excellent")) {
                    rb_woodenwork1.setChecked(true);
                } else if (object.getString("selected_wooden_wok").equals("Very Good")) {
                    rb_woodenwork2.setChecked(true);

                } else if (object.getString("selected_wooden_wok").equals("Good")) {
                    rb_woodenwork3.setChecked(true);

                } else if (object.getString("selected_wooden_wok").equals("Simple")) {
                    rb_woodenwork4.setChecked(true);

                } else if (object.getString("selected_wooden_wok").equals("Ordinary")) {
                    rb_woodenwork5.setChecked(true);

                } else if (object.getString("selected_wooden_wok").equals("Average")) {
                    rb_woodenwork6.setChecked(true);

                } else if (object.getString("selected_wooden_wok").equals("Below Average")) {
                    rb_woodenwork7.setChecked(true);

                } else if (object.getString("selected_wooden_wok").equals("No wooden work")) {
                    rb_woodenwork8.setChecked(true);

                } else if (object.getString("selected_wooden_wok").equals("No survey")) {
                    rb_woodenwork9.setChecked(true);

                } else {

                }


                if (object.getString("selected_maintanence").equals("Excellent")) {
                    rb_maintanence1.setChecked(true);
                } else if (object.getString("selected_maintanence").equals("Average")) {
                    rb_maintanence2.setChecked(true);

                } else if (object.getString("selected_maintanence").equals("Poor")) {
                    rb_maintanence3.setChecked(true);

                } else {

                }


                if (object.getString("selected_wall").equals("Yes")) {
                    rb_bWall1.setChecked(true);
                } else if (object.getString("selected_wall").equals("No")) {
                    rb_bWall2.setChecked(true);
                } else {
                }


                if (object.getString("selected_liftes").equals("Passenger")) {
                    rb_lifts1.setChecked(true);
                } else if (object.getString("selected_liftes").equals("Commercial")) {
                    rb_lifts2.setChecked(true);
                } else {
                }

                if (object.getString("selected_garden").equals("Yes")) {
                    rb_garden1.setChecked(true);
                } else if (object.getString("selected_garden").equals("No")) {
                    rb_garden2.setChecked(true);
                } else if (object.getString("selected_garden").equals("Beautiful")) {
                    rb_garden3.setChecked(true);
                } else if (object.getString("selected_garden").equals("Ordinary")) {
                    rb_garden4.setChecked(true);
                } else {
                }


                if (object.getString("cb_defectes1").equals("1")) {
                    cb_defectes1.setChecked(true);
                } else {
                    cb_defectes1.setChecked(false);
                }

                if (object.getString("cb_defectes2").equals("1")) {
                    cb_defectes2.setChecked(true);
                } else {
                    cb_defectes2.setChecked(false);
                }

                if (object.getString("cb_defectes3").equals("1")) {
                    cb_defectes3.setChecked(true);
                } else {
                    cb_defectes3.setChecked(false);
                }

                if (object.getString("cb_defectes4").equals("1")) {
                    cb_defectes4.setChecked(true);
                } else {
                    cb_defectes4.setChecked(false);
                }

                if (object.getString("cb_defectes5").equals("1")) {
                    cb_defectes5.setChecked(true);
                } else {
                    cb_defectes5.setChecked(false);
                }

                if (object.getString("cb_defectes6").equals("1")) {
                    cb_defectes6.setChecked(true);
                } else {
                    cb_defectes6.setChecked(false);
                }

                if (object.getString("cb_defectes7").equals("1")) {
                    cb_defectes7.setChecked(true);
                } else {
                    cb_defectes7.setChecked(false);
                }

                if (object.getString("cb_vio1").equals("1")) {
                    cb_vio1.setChecked(true);
                } else {
                    cb_vio1.setChecked(false);
                }

                if (object.getString("cb_vio2").equals("1")) {
                    cb_vio2.setChecked(true);
                } else {
                    cb_vio2.setChecked(false);
                }

                if (object.getString("cb_vio3").equals("1")) {
                    cb_vio3.setChecked(true);
                } else {
                    cb_vio3.setChecked(false);
                }

                if (object.getString("cb_vio4").equals("1")) {
                    cb_vio4.setChecked(true);
                } else {
                    cb_vio4.setChecked(false);
                }

                if (object.getString("cb_vio5").equals("1")) {
                    cb_vio5.setChecked(true);
                } else {
                    cb_vio5.setChecked(false);
                }


            }


        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
