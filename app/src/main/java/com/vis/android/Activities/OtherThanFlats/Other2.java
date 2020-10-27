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

public class Other2 extends AppCompatActivity implements View.OnClickListener {
    TextView next;
    Intent intent;
    TextView previous;
    EditText et_owner, et_purchase, et_Paddress, et_Raddress, et_north, et_south, et_east, et_west, et_landmark, et_wardname, et_zname, et_name, et_width, et_distance, et_approch, et_school, et_hosp, et_market, metro, railway, airport, new_developmnt;
    RadioGroup rg_constitution, rg_characteristic, rg_limits, rg_development, rg_municipal, rg_category, rg_facilities;
    String selected_cons, selected_char, selected_limits, selected_development, selected_municipal, selected_category, selected_facility;
    RadioButton checkedRadioButton, rb_cons1, rb_cons2, rb_char1, rb_char2, rb_char3, rb_char4, rb_char5, rb_char6, rb_char7, rb_limit1, rb_limit2, rb_limit3, rb_limit4, rb_limit5, rb_developmnt1, rb_developmnt2, rb_developmnt3, rb_developmnt4, rb_developmnt5, rb_developmnt6, rb_developmnt7, rb_developmnt8, rb_developmnt9, rb_developmnt10, rb_municipal1, rb_municipal2, rb_municipal3, rb_municipal4, rb_municipal5, rb_municipal6, rb_municipal7, rb_municipal8, rb_municipal9, rb_municipal10, rb_cat1, rb_cat2, rb_cat3, rb_cat4, rb_cat5, rb_cat6, rb_cat7, rb_facility1, rb_facility2, rb_facility3, rb_facility4, rb_facility5, rb_facility6, rb_facility7, rb_facility8, rb_facility9;
    CheckBox cb_loc1, cb_loc2, cb_loc3, cb_loc4, cb_loc5, cb_loc6, cb_loc7, cb_loc8, cb_loc9, cb_loc10, cb_loc11;
    CheckBox cb_locFlat1, cb_locFlat2, cb_locFlat3, cb_locFlat4, cb_locFlat5;
    int cb_locFlat1Check, cb_locFlat2Check, cb_locFlat3Check, cb_locFlat4Check, cb_locFlat5Check;
    int cb_loc1Check, cb_loc2Check, cb_loc3Check, cb_loc4Check, cb_loc5Check, cb_loc6Check, cb_loc7Check, cb_loc8Check, cb_loc9Check, cb_loc10Check, cb_loc11Check;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other2);
        getid();
        setListener();
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("hashmap====", String.valueOf(hm));

        rg_constitution.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_cons = (String) checkedRadioButton.getText();

                }
            }
        });

        rg_characteristic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_char = (String) checkedRadioButton.getText();

                }
            }
        });

        rg_limits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_limits = (String) checkedRadioButton.getText();

                }
            }
        });

        rg_development.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_development = (String) checkedRadioButton.getText();

                }
            }
        });

        rg_municipal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_municipal = (String) checkedRadioButton.getText();

                }
            }
        });

        rg_category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_category = (String) checkedRadioButton.getText();

                }
            }
        });

        rg_facilities.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_facility = (String) checkedRadioButton.getText();

                }
            }
        });
    }

    public void getid() {
        next = (TextView) findViewById(R.id.next);
        previous = (TextView) findViewById(R.id.tv_previous);

        et_owner = (EditText) findViewById(R.id.et_ownrname);
        et_purchase = (EditText) findViewById(R.id.et_prchasename);
        et_Paddress = (EditText) findViewById(R.id.et_Padd);
        et_Raddress = (EditText) findViewById(R.id.et_Radd);
        et_north = (EditText) findViewById(R.id.et_north);
        et_south = (EditText) findViewById(R.id.et_south);
        et_east = (EditText) findViewById(R.id.et_east);
        et_west = (EditText) findViewById(R.id.et_west);
        et_landmark = (EditText) findViewById(R.id.et_Lmark);
        et_wardname = (EditText) findViewById(R.id.et_Wname);
        et_zname = (EditText) findViewById(R.id.et_Zname);
        et_name = (EditText) findViewById(R.id.et_Rname);
        et_width = (EditText) findViewById(R.id.et_Rwidth);
        et_distance = (EditText) findViewById(R.id.et_Pdistance);
        et_approch = (EditText) findViewById(R.id.et_ApproachName);
        et_school = (EditText) findViewById(R.id.et_school);
        et_hosp = (EditText) findViewById(R.id.et_hosp);
        et_market = (EditText) findViewById(R.id.et_market);
        metro = (EditText) findViewById(R.id.et_metro);
        railway = (EditText) findViewById(R.id.et_railway);
        airport = (EditText) findViewById(R.id.et_airport);
        new_developmnt = (EditText) findViewById(R.id.et_develpmnt);

        rg_constitution = (RadioGroup) findViewById(R.id.rg_cons);
        rg_characteristic = (RadioGroup) findViewById(R.id.rg_char);
        rg_limits = (RadioGroup) findViewById(R.id.rg_limits);
        rg_development = (RadioGroup) findViewById(R.id.rg_develpment);
        rg_municipal = (RadioGroup) findViewById(R.id.rg_municipal);
        rg_category = (RadioGroup) findViewById(R.id.rg_category);
        rg_facilities = (RadioGroup) findViewById(R.id.rg_facilities);

        rb_cons1 = (RadioButton) findViewById(R.id.rb_freeHold);
        rb_cons2 = (RadioButton) findViewById(R.id.rb_leaseHold);

        rb_char1 = (RadioButton) findViewById(R.id.rb_char1);
        rb_char2 = (RadioButton) findViewById(R.id.rb_char2);
        rb_char3 = (RadioButton) findViewById(R.id.rb_char3);
        rb_char4 = (RadioButton) findViewById(R.id.rb_char4);
        rb_char5 = (RadioButton) findViewById(R.id.rb_char5);
        rb_char6 = (RadioButton) findViewById(R.id.rb_char6);
        rb_char7 = (RadioButton) findViewById(R.id.rb_char7);

        rb_limit1 = (RadioButton) findViewById(R.id.rb_limit1);
        rb_limit2 = (RadioButton) findViewById(R.id.rb_limit2);
        rb_limit3 = (RadioButton) findViewById(R.id.rb_limit3);
        rb_limit4 = (RadioButton) findViewById(R.id.rb_limit4);
        rb_limit5 = (RadioButton) findViewById(R.id.rb_limit5);

        rb_developmnt1 = (RadioButton) findViewById(R.id.rb_develp1);
        rb_developmnt2 = (RadioButton) findViewById(R.id.rb_develp2);
        rb_developmnt3 = (RadioButton) findViewById(R.id.rb_develp3);
        rb_developmnt4 = (RadioButton) findViewById(R.id.rb_develp4);
        rb_developmnt5 = (RadioButton) findViewById(R.id.rb_develp5);
        rb_developmnt6 = (RadioButton) findViewById(R.id.rb_develp6);
        rb_developmnt7 = (RadioButton) findViewById(R.id.rb_develp7);
        rb_developmnt8 = (RadioButton) findViewById(R.id.rb_develp8);
        rb_developmnt9 = (RadioButton) findViewById(R.id.rb_develp9);
        rb_developmnt10 = (RadioButton) findViewById(R.id.rb_develp10);

        rb_municipal1 = (RadioButton) findViewById(R.id.rb_munisipal1);
        rb_municipal2 = (RadioButton) findViewById(R.id.rb_munisipal2);
        rb_municipal3 = (RadioButton) findViewById(R.id.rb_munisipal3);
        rb_municipal4 = (RadioButton) findViewById(R.id.rb_munisipal4);
        rb_municipal5 = (RadioButton) findViewById(R.id.rb_munisipal5);
        rb_municipal6 = (RadioButton) findViewById(R.id.rb_munisipal6);
        rb_municipal7 = (RadioButton) findViewById(R.id.rb_munisipal7);
        rb_municipal8 = (RadioButton) findViewById(R.id.rb_munisipal8);
        rb_municipal9 = (RadioButton) findViewById(R.id.rb_munisipal9);
        rb_municipal10 = (RadioButton) findViewById(R.id.rb_munisipal10);

        rb_cat1 = (RadioButton) findViewById(R.id.rb_cat1);
        rb_cat2 = (RadioButton) findViewById(R.id.rb_cat2);
        rb_cat3 = (RadioButton) findViewById(R.id.rb_cat3);
        rb_cat4 = (RadioButton) findViewById(R.id.rb_cat4);
        rb_cat5 = (RadioButton) findViewById(R.id.rb_cat5);
        rb_cat6 = (RadioButton) findViewById(R.id.rb_cat6);
        rb_cat7 = (RadioButton) findViewById(R.id.rb_cat7);

        rb_facility1 = (RadioButton) findViewById(R.id.rb_facility1);
        rb_facility2 = (RadioButton) findViewById(R.id.rb_facility2);
        rb_facility3 = (RadioButton) findViewById(R.id.rb_facility3);
        rb_facility4 = (RadioButton) findViewById(R.id.rb_facility4);
        rb_facility5 = (RadioButton) findViewById(R.id.rb_facility5);
        rb_facility6 = (RadioButton) findViewById(R.id.rb_facility6);
        rb_facility7 = (RadioButton) findViewById(R.id.rb_facility7);
        rb_facility8 = (RadioButton) findViewById(R.id.rb_facility8);
        rb_facility9 = (RadioButton) findViewById(R.id.rb_facility9);

        cb_loc1 = (CheckBox) findViewById(R.id.cb_loc1);
        cb_loc2 = (CheckBox) findViewById(R.id.cb_loc2);
        cb_loc3 = (CheckBox) findViewById(R.id.cb_loc3);
        cb_loc4 = (CheckBox) findViewById(R.id.cb_loc4);
        cb_loc5 = (CheckBox) findViewById(R.id.cb_loc5);
        cb_loc6 = (CheckBox) findViewById(R.id.cb_loc6);
        cb_loc7 = (CheckBox) findViewById(R.id.cb_loc7);
        cb_loc8 = (CheckBox) findViewById(R.id.cb_loc8);
        cb_loc9 = (CheckBox) findViewById(R.id.cb_loc9);
        cb_loc10 = (CheckBox) findViewById(R.id.cb_loc10);
        cb_loc11 = (CheckBox) findViewById(R.id.cb_loc11);

        cb_locFlat1 = (CheckBox) findViewById(R.id.cb_locFlat1);
        cb_locFlat2 = (CheckBox) findViewById(R.id.cb_locFlat2);
        cb_locFlat3 = (CheckBox) findViewById(R.id.cb_locFlat3);
        cb_locFlat4 = (CheckBox) findViewById(R.id.cb_locFlat4);
        cb_locFlat5 = (CheckBox) findViewById(R.id.cb_locFlat5);

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
        if (et_owner.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter owner name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_purchase.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter purchase name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_Paddress.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter property address", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_constitution.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select property constitution", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_Raddress.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter residence address", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_north.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter north property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_east.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter east property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_west.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter west property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_landmark.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter landmark", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_wardname.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter ward name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_zname.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter zone name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_name.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter main road name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_width.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter main road width", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_distance.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter main road distance", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_approch.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter approach road name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_characteristic.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select characteristic of the Locality", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_school.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter school name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_hosp.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter hospital name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_market.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter market name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (metro.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter metro name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (railway.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter railway name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (airport.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter airport name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_limits.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select Jurisdiction limit", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_development.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select Jurisdiction development", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_municipal.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select municipal corporation name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_category.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select category of society", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_facilities.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select facility", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (!cb_loc1.isChecked() && !!cb_loc2.isChecked() && !cb_loc3.isChecked() && !cb_loc4.isChecked() &&
                !cb_loc5.isChecked() && !cb_loc6.isChecked() && !cb_loc7.isChecked() && !cb_loc8.isChecked() &&
                !cb_loc9.isChecked() && !cb_loc10.isChecked() && !cb_loc11.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select checkbox", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (!cb_locFlat1.isChecked() && !!cb_locFlat2.isChecked() && !cb_locFlat3.isChecked() && !cb_locFlat4.isChecked() &&
                !cb_locFlat5.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select location of flat", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else {
            putintoHashMap();
            intent = new Intent(Other2.this, Other3.class);
            startActivity(intent);
        }
    }

    public void putintoHashMap() {

        hm.put("owner_name", et_owner.getText().toString());
        hm.put("purchase_name", et_purchase.getText().toString());
        hm.put("property_address", et_Paddress.getText().toString());
        hm.put("residence_address", et_Raddress.getText().toString());
        hm.put("selected_cons", selected_cons);
        hm.put("north", et_north.getText().toString());
        hm.put("south", et_south.getText().toString());
        hm.put("east", et_east.getText().toString());
        hm.put("west", et_west.getText().toString());
        hm.put("landmark", et_landmark.getText().toString());
        hm.put("wardname", et_wardname.getText().toString());
        hm.put("zone_name", et_zname.getText().toString());
        hm.put("name", et_name.getText().toString());
        hm.put("width", et_width.getText().toString());
        hm.put("distance", et_distance.getText().toString());
        hm.put("approach", et_approch.getText().toString());
        hm.put("selected_char", selected_char);
        hm.put("school", et_school.getText().toString());
        hm.put("hospital", et_hosp.getText().toString());
        hm.put("market", et_market.getText().toString());
        hm.put("metro", metro.getText().toString());
        hm.put("railway", railway.getText().toString());
        hm.put("airport", airport.getText().toString());
        hm.put("new_developmnt", new_developmnt.getText().toString());
        hm.put("selected_limits", selected_limits);
        hm.put("selected_development", selected_development);
        hm.put("selected_municipal", selected_municipal);
        hm.put("selected_category", selected_category);
        hm.put("selected_facility", selected_facility);
        if (cb_loc1.isChecked()) {
            cb_loc1Check = 1;
            hm.put("cb_loc1", String.valueOf(cb_loc1Check));
        } else {
            cb_loc1Check = 0;
            hm.put("cb_loc1", String.valueOf(cb_loc1Check));
        }

        if (cb_loc2.isChecked()) {
            cb_loc2Check = 1;
            hm.put("cb_loc2", String.valueOf(cb_loc2Check));
        } else {
            cb_loc2Check = 0;
            hm.put("cb_loc2", String.valueOf(cb_loc2Check));
        }

        if (cb_loc3.isChecked()) {
            cb_loc3Check = 1;
            hm.put("cb_loc3", String.valueOf(cb_loc3Check));
        } else {
            cb_loc3Check = 0;
            hm.put("cb_loc3", String.valueOf(cb_loc3Check));
        }

        if (cb_loc4.isChecked()) {
            cb_loc4Check = 1;
            hm.put("cb_loc4", String.valueOf(cb_loc4Check));
        } else {
            cb_loc4Check = 0;
            hm.put("cb_loc4", String.valueOf(cb_loc4Check));
        }

        if (cb_loc5.isChecked()) {
            cb_loc5Check = 1;
            hm.put("cb_loc5", String.valueOf(cb_loc5Check));
        } else {
            cb_loc5Check = 0;
            hm.put("cb_loc5", String.valueOf(cb_loc5Check));
        }

        if (cb_loc6.isChecked()) {
            cb_loc6Check = 1;
            hm.put("cb_loc6", String.valueOf(cb_loc6Check));
        } else {
            cb_loc6Check = 0;
            hm.put("cb_loc6", String.valueOf(cb_loc6Check));
        }

        if (cb_loc7.isChecked()) {
            cb_loc7Check = 1;
            hm.put("cb_loc7", String.valueOf(cb_loc7Check));
        } else {
            cb_loc7Check = 0;
            hm.put("cb_loc7", String.valueOf(cb_loc7Check));
        }
        if (cb_loc8.isChecked()) {
            cb_loc8Check = 1;
            hm.put("cb_loc8", String.valueOf(cb_loc8Check));
        } else {
            cb_loc8Check = 0;
            hm.put("cb_loc8", String.valueOf(cb_loc8Check));
        }

        if (cb_loc9.isChecked()) {
            cb_loc9Check = 1;
            hm.put("cb_loc9", String.valueOf(cb_loc9Check));
        } else {
            cb_loc9Check = 0;
            hm.put("cb_loc9", String.valueOf(cb_loc9Check));
        }

        if (cb_loc10.isChecked()) {
            cb_loc10Check = 1;
            hm.put("cb_loc10", String.valueOf(cb_loc10Check));
        } else {
            cb_loc10Check = 0;
            hm.put("cb_loc10", String.valueOf(cb_loc10Check));
        }

        if (cb_loc11.isChecked()) {
            cb_loc11Check = 1;
            hm.put("cb_loc11", String.valueOf(cb_loc11Check));
        } else {
            cb_loc11Check = 0;
            hm.put("cb_loc11", String.valueOf(cb_loc11Check));
        }

        //  Flat Loction

        if (cb_locFlat1.isChecked()) {
            cb_locFlat1Check = 1;
            hm.put("cb_locFlat1", String.valueOf(cb_locFlat1Check));
        } else {
            cb_locFlat1Check = 0;
            hm.put("cb_locFlat1", String.valueOf(cb_locFlat1Check));
        }

        if (cb_locFlat2.isChecked()) {
            cb_locFlat2Check = 1;
            hm.put("cb_locFlat2", String.valueOf(cb_locFlat2Check));
        } else {
            cb_locFlat2Check = 0;
            hm.put("cb_locFlat2", String.valueOf(cb_locFlat2Check));
        }

        if (cb_locFlat3.isChecked()) {
            cb_locFlat3Check = 1;
            hm.put("cb_locFlat3", String.valueOf(cb_locFlat3Check));
        } else {
            cb_locFlat3Check = 0;
            hm.put("cb_locFlat3", String.valueOf(cb_locFlat3Check));
        }

        if (cb_locFlat4.isChecked()) {
            cb_locFlat4Check = 1;
            hm.put("cb_locFlat4", String.valueOf(cb_locFlat4Check));
        } else {
            cb_locFlat4Check = 0;
            hm.put("cb_locFlat4", String.valueOf(cb_locFlat4Check));
        }

        if (cb_locFlat5.isChecked()) {
            cb_locFlat5Check = 1;
            hm.put("cb_locFlat5", String.valueOf(cb_locFlat5Check));
        } else {
            cb_locFlat5Check = 0;
            hm.put("cb_locFlat5", String.valueOf(cb_locFlat5Check));
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

                et_owner.setText(object.getString("owner_name"));
                et_purchase.setText(object.getString("purchase_name"));

                et_Paddress.setText(object.getString("property_address"));
                et_Raddress.setText(object.getString("residence_address"));
                et_north.setText(object.getString("north"));
                et_south.setText(object.getString("south"));
                et_east.setText(object.getString("east"));
                et_west.setText(object.getString("west"));
                et_landmark.setText(object.getString("landmark"));
                et_wardname.setText(object.getString("wardname"));
                et_zname.setText(object.getString("zone_name"));
                et_name.setText(object.getString("name"));
                et_width.setText(object.getString("width"));
                et_distance.setText(object.getString("distance"));

                et_approch.setText(object.getString("approach"));
                et_school.setText(object.getString("school"));
                et_hosp.setText(object.getString("hospital"));
                et_market.setText(object.getString("market"));
                metro.setText(object.getString("metro"));
                railway.setText(object.getString("railway"));
                airport.setText(object.getString("airport"));
                new_developmnt.setText(object.getString("new_developmnt"));

                if (object.getString("selected_char").equals("Urban developed")) {
                    rb_char1.setChecked(true);
                } else if (object.getString("selected_char").equals("Urban developing")) {
                    rb_char2.setChecked(true);
                } else if (object.getString("selected_char").equals("Semi Urban")) {
                    rb_char3.setChecked(true);
                } else if (object.getString("selected_char").equals("Rural")) {
                    rb_char4.setChecked(true);
                } else if (object.getString("selected_char").equals("Backward")) {
                    rb_char5.setChecked(true);
                } else if (object.getString("selected_char").equals("Industrial")) {
                    rb_char6.setChecked(true);
                } else if (object.getString("selected_char").equals("Institutional")) {
                    rb_char7.setChecked(true);
                } else {

                }

                if (object.getString("selected_cons").equals("Free Hold")) {
                    rb_cons1.setChecked(true);
                } else if (object.getString("selected_cons").equals("Lease Hold")) {
                    rb_cons2.setChecked(true);
                } else {

                }


                if (object.getString("selected_limits").equals("Nagar Nigam")) {
                    rb_limit1.setChecked(true);
                } else if (object.getString("selected_limits").equals("Nagar Panchayat")) {
                    rb_limit2.setChecked(true);

                } else if (object.getString("selected_limits").equals("Gram Panchayat")) {
                    rb_limit3.setChecked(true);

                } else if (object.getString("selected_limits").equals("Nagar Palika Parishad")) {
                    rb_limit4.setChecked(true);

                } else if (object.getString("selected_limits").equals("Area not within any municipal limits")) {
                    rb_limit5.setChecked(true);

                } else {

                }


                if (object.getString("selected_development").equals("DDA")) {
                    rb_developmnt1.setChecked(true);
                } else if (object.getString("selected_development").equals("GDA")) {
                    rb_developmnt2.setChecked(true);

                } else if (object.getString("selected_development").equals("NOIDA")) {
                    rb_developmnt3.setChecked(true);

                } else if (object.getString("selected_development").equals("GNIDA")) {
                    rb_developmnt4.setChecked(true);

                } else if (object.getString("selected_development").equals("YEIDA")) {
                    rb_developmnt5.setChecked(true);

                } else if (object.getString("selected_development").equals("HUDA")) {
                    rb_developmnt6.setChecked(true);

                } else if (object.getString("selected_development").equals("KMDA")) {
                    rb_developmnt7.setChecked(true);

                } else if (object.getString("selected_development").equals("MDDA")) {
                    rb_developmnt8.setChecked(true);

                } else if (object.getString("selected_development").equals("Any other Development Authority")) {
                    rb_developmnt9.setChecked(true);

                } else if (object.getString("selected_development").equals("Area not within any development authority limits")) {
                    rb_developmnt10.setChecked(true);

                } else {

                }


                if (object.getString("selected_municipal").equals("NDMC")) {
                    rb_municipal1.setChecked(true);
                } else if (object.getString("selected_municipal").equals("SDMC")) {
                    rb_municipal2.setChecked(true);
                } else if (object.getString("selected_municipal").equals("EDMC")) {
                    rb_municipal3.setChecked(true);
                } else if (object.getString("selected_municipal").equals("Ghaziabad Municipal Corporation")) {
                    rb_municipal4.setChecked(true);
                } else if (object.getString("selected_municipal").equals("Gurgaon Municipal Corporation")) {
                    rb_municipal5.setChecked(true);
                } else if (object.getString("selected_municipal").equals("Faridabad Municipal Corporation")) {
                    rb_municipal6.setChecked(true);
                } else if (object.getString("selected_municipal").equals("Kolkata Municipal Corporation")) {
                    rb_municipal7.setChecked(true);
                } else if (object.getString("selected_municipal").equals("Dehradun Municipal Corporation")) {
                    rb_municipal8.setChecked(true);
                } else if (object.getString("selected_municipal").equals("Area not within any municipal limits")) {
                    rb_municipal9.setChecked(true);
                } else if (object.getString("selected_municipal").equals("Any other Municipal Corporation/ Municipality")) {
                    rb_municipal10.setChecked(true);
                } else {
                }

                if (object.getString("selected_category").equals("High End")) {
                    rb_cat1.setChecked(true);
                } else if (object.getString("selected_category").equals("Normal")) {
                    rb_cat2.setChecked(true);
                } else if (object.getString("selected_category").equals("Affordable Group Housing")) {
                    rb_cat3.setChecked(true);
                } else if (object.getString("selected_category").equals("EWS")) {
                    rb_cat4.setChecked(true);
                } else if (object.getString("selected_category").equals("HIG")) {
                    rb_cat5.setChecked(true);
                } else if (object.getString("selected_category").equals("MIG")) {
                    rb_cat6.setChecked(true);
                } else if (object.getString("selected_category").equals("LIG")) {
                    rb_cat7.setChecked(true);
                } else {
                }


                if (object.getString("selected_facility").equals("Lifts")) {
                    rb_facility1.setChecked(true);
                } else if (object.getString("selected_facility").equals("Garden")) {
                    rb_facility2.setChecked(true);
                } else if (object.getString("selected_facility").equals("Landscaping")) {
                    rb_facility3.setChecked(true);
                } else if (object.getString("selected_facility").equals("Swimming Pool")) {
                    rb_facility4.setChecked(true);
                } else if (object.getString("selected_facility").equals("Gym")) {
                    rb_facility5.setChecked(true);
                } else if (object.getString("selected_facility").equals("Club House")) {
                    rb_facility6.setChecked(true);
                } else if (object.getString("selected_facility").equals("Walk Trails")) {
                    rb_facility7.setChecked(true);
                } else if (object.getString("selected_facility").equals("Kids play zone")) {
                    rb_facility8.setChecked(true);
                } else if (object.getString("selected_facility").equals("100% Power Backup")) {
                    rb_facility9.setChecked(true);
                } else {
                }
                if (object.getString("cb_loc1").equals("1")) {
                    cb_loc1.setChecked(true);
                } else {
                    cb_loc1.setChecked(false);
                }

                if (object.getString("cb_loc2").equals("1")) {
                    cb_loc2.setChecked(true);
                } else {
                    cb_loc2.setChecked(false);
                }

                if (object.getString("cb_loc3").equals("1")) {
                    cb_loc3.setChecked(true);
                } else {
                    cb_loc3.setChecked(false);
                }

                if (object.getString("cb_loc4").equals("1")) {
                    cb_loc4.setChecked(true);
                } else {
                    cb_loc4.setChecked(false);
                }

                if (object.getString("cb_loc5").equals("1")) {
                    cb_loc5.setChecked(true);
                } else {
                    cb_loc5.setChecked(false);
                }

                if (object.getString("cb_loc6").equals("1")) {
                    cb_loc6.setChecked(true);
                } else {
                    cb_loc6.setChecked(false);
                }

                if (object.getString("cb_loc7").equals("1")) {
                    cb_loc7.setChecked(true);
                } else {
                    cb_loc7.setChecked(false);
                }

                if (object.getString("cb_loc8").equals("1")) {
                    cb_loc8.setChecked(true);
                } else {
                    cb_loc8.setChecked(false);
                }

                if (object.getString("cb_loc9").equals("1")) {
                    cb_loc9.setChecked(true);
                } else {
                    cb_loc9.setChecked(false);
                }

                if (object.getString("cb_loc10").equals("1")) {
                    cb_loc10.setChecked(true);
                } else {
                    cb_loc10.setChecked(false);
                }

                if (object.getString("cb_loc11").equals("1")) {
                    cb_loc11.setChecked(true);
                } else {
                    cb_loc11.setChecked(false);
                }

                // flat location

                if (object.getString("cb_locFlat1").equals("1")) {
                    cb_locFlat1.setChecked(true);
                } else {
                    cb_locFlat1.setChecked(false);
                }

                if (object.getString("cb_locFlat2").equals("1")) {
                    cb_locFlat2.setChecked(true);
                } else {
                    cb_locFlat2.setChecked(false);
                }

                if (object.getString("cb_locFlat3").equals("1")) {
                    cb_locFlat3.setChecked(true);
                } else {
                    cb_locFlat3.setChecked(false);
                }

                if (object.getString("cb_locFlat4").equals("1")) {
                    cb_locFlat4.setChecked(true);
                } else {
                    cb_locFlat4.setChecked(false);
                }

                if (object.getString("cb_locFlat5").equals("1")) {
                    cb_locFlat5.setChecked(true);
                } else {
                    cb_locFlat5.setChecked(false);
                }
            }
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}


