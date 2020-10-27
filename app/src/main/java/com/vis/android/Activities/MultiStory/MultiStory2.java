package com.vis.android.Activities.MultiStory;

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
import android.widget.TextView;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vis.android.Activities.MultiStory.MultiStory1.hmap;

public class MultiStory2 extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout back;
    Intent intent;
    TextView previous;
    TextView next;

    EditText et_owner, et_purchase, et_Paddress, et_Raddress, et_north, et_south, et_east, et_west, et_landmark, et_wardname, et_zname, et_name, et_width, et_distance, et_approch, et_school, et_hosp, et_market, metro, railway, airport, new_developmnt;
    RadioGroup rg_constitution, rg_characteristic, rg_limits, rg_development, rg_municipal;
    RadioButton checkedRadioButton, rb_cons1, rb_cons2, rb_char1, rb_char2, rb_char3, rb_char4, rb_char5, rb_char6, rb_char7, rb_limit1, rb_limit2, rb_limit3, rb_limit4, rb_limit5, rb_developmnt1, rb_developmnt2, rb_developmnt3, rb_developmnt4, rb_developmnt5, rb_developmnt6, rb_developmnt7, rb_developmnt8, rb_developmnt9, rb_developmnt10, rb_municipal1, rb_municipal2, rb_municipal3, rb_municipal4, rb_municipal5, rb_municipal6, rb_municipal7, rb_municipal8, rb_municipal9, rb_municipal10;
    String selected_cons, selected_char, selected_limits, selected_development, selected_municipal;
    ArrayList<HashMap<String, String>> alistt = new ArrayList<HashMap<String, String>>();
    CheckBox locationC1, locationC2, locationC3, locationC4, locationC5, locationC6, locationC7, locationC8, locationC9, locationC10, locationC11;
    int locationCheck1, locationCheck2, locationCheck3, locationCheck4, locationCheck5, locationCheck6, locationCheck7, locationCheck8, locationCheck9, locationCheck10, locationCheck11;
    CheckBox locflat1, locflat2, locflat3, locflat4, locflat5;
    int locflatCheck1, locflatCheck2, locflatCheck3, locflatCheck4, locflatCheck5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_story2);
        getid();
        setListener();
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("hashmap===2", String.valueOf(hmap));
        rg_constitution.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_cons = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

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
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

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
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

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
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

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
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });
    }


    public void getid() {
        next = (TextView) findViewById(R.id.next);
        back = (RelativeLayout) findViewById(R.id.rl_back);
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

        locationC1 = (CheckBox) findViewById(R.id.cb_loc1);
        locationC2 = (CheckBox) findViewById(R.id.cb_loc2);
        locationC3 = (CheckBox) findViewById(R.id.cb_loc3);
        locationC4 = (CheckBox) findViewById(R.id.cb_loc4);
        locationC5 = (CheckBox) findViewById(R.id.cb_loc5);
        locationC6 = (CheckBox) findViewById(R.id.cb_loc6);
        locationC7 = (CheckBox) findViewById(R.id.cb_loc7);
        locationC8 = (CheckBox) findViewById(R.id.cb_loc8);
        locationC9 = (CheckBox) findViewById(R.id.cb_loc9);
        locationC10 = (CheckBox) findViewById(R.id.cb_loc10);
        locationC11 = (CheckBox) findViewById(R.id.cb_loc11);

        locflat1 = (CheckBox) findViewById(R.id.cb_locFlat1);
        locflat2 = (CheckBox) findViewById(R.id.cb_locFlat2);
        locflat3 = (CheckBox) findViewById(R.id.cb_locFlat3);
        locflat4 = (CheckBox) findViewById(R.id.cb_locFlat4);
        locflat5 = (CheckBox) findViewById(R.id.cb_locFlat5);
    }

    public void setListener() {
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                checkpass();
                break;

            case R.id.rl_back:
                onBackPressed();
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
        } else {
            insertDB();
            intent = new Intent(MultiStory2.this, MultiStory3.class);
            startActivity(intent);
        }
    }

    public void insertDB() {
        hmap.put("ownername", et_owner.getText().toString());
        hmap.put("purchasename", et_purchase.getText().toString());
        hmap.put("p_address", et_Paddress.getText().toString());
        hmap.put("r_address", et_Raddress.getText().toString());
        hmap.put("north", et_north.getText().toString());
        hmap.put("south", et_south.getText().toString());
        hmap.put("east", et_east.getText().toString());
        hmap.put("west", et_west.getText().toString());
        hmap.put("landmark", et_landmark.getText().toString());
        hmap.put("wardname", et_wardname.getText().toString());
        hmap.put("zonename", et_zname.getText().toString());
        hmap.put("name", et_name.getText().toString());
        hmap.put("width", et_width.getText().toString());
        hmap.put("distance", et_distance.getText().toString());
        hmap.put("approach", et_approch.getText().toString());
        hmap.put("school", et_school.getText().toString());
        hmap.put("hospital", et_hosp.getText().toString());
        hmap.put("market", et_market.getText().toString());
        hmap.put("metro", metro.getText().toString());
        hmap.put("railway", railway.getText().toString());
        hmap.put("airport", airport.getText().toString());
        hmap.put("development", new_developmnt.getText().toString());
        hmap.put("selected_cons", selected_cons);
        hmap.put("selected_char", selected_char);
        hmap.put("selected_limits", selected_limits);
        hmap.put("selected_development", selected_development);
        hmap.put("selected_municipal", selected_municipal);

        if (locationC1.isChecked()) {
            locationCheck1 = 1;
            hmap.put("locationC1", String.valueOf(locationCheck1));
        } else {
            locationCheck1 = 0;
            hmap.put("locationC1", String.valueOf(locationCheck1));
        }

        if (locationC2.isChecked()) {
            locationCheck2 = 1;
            hmap.put("locationC2", String.valueOf(locationCheck2));
        } else {
            locationCheck2 = 0;
            hmap.put("locationC2", String.valueOf(locationCheck2));
        }

        if (locationC3.isChecked()) {
            locationCheck3 = 1;
            hmap.put("locationC3", String.valueOf(locationCheck3));
        } else {
            locationCheck3 = 0;
            hmap.put("locationC3", String.valueOf(locationCheck3));
        }

        if (locationC4.isChecked()) {
            locationCheck4 = 1;
            hmap.put("locationC4", String.valueOf(locationCheck4));
        } else {
            locationCheck4 = 0;
            hmap.put("locationC4", String.valueOf(locationCheck4));
        }

        if (locationC5.isChecked()) {
            locationCheck5 = 1;
            hmap.put("locationC5", String.valueOf(locationCheck5));
        } else {
            locationCheck5 = 0;
            hmap.put("locationC5", String.valueOf(locationCheck5));
        }

        if (locationC6.isChecked()) {
            locationCheck6 = 1;
            hmap.put("locationC6", String.valueOf(locationCheck6));
        } else {
            locationCheck6 = 0;
            hmap.put("locationC6", String.valueOf(locationCheck6));
        }

        if (locationC7.isChecked()) {
            locationCheck7 = 1;
            hmap.put("locationC7", String.valueOf(locationCheck7));
        } else {
            locationCheck7 = 0;
            hmap.put("locationC7", String.valueOf(locationCheck7));
        }

        if (locationC8.isChecked()) {
            locationCheck8 = 1;
            hmap.put("locationC8", String.valueOf(locationCheck8));
        } else {
            locationCheck8 = 0;
            hmap.put("locationC8", String.valueOf(locationCheck8));
        }

        if (locationC9.isChecked()) {
            locationCheck9 = 1;
            hmap.put("locationC9", String.valueOf(locationCheck9));
        } else {
            locationCheck9 = 0;
            hmap.put("locationC9", String.valueOf(locationCheck9));
        }

        if (locationC10.isChecked()) {
            locationCheck10 = 1;
            hmap.put("locationC10", String.valueOf(locationCheck10));
        } else {
            locationCheck10 = 0;
            hmap.put("locationC10", String.valueOf(locationCheck10));
        }

        if (locationC11.isChecked()) {
            locationCheck11 = 1;
            hmap.put("locationC11", String.valueOf(locationCheck11));
        } else {
            locationCheck11 = 0;
            hmap.put("locationC11", String.valueOf(locationCheck11));
        }


        if (locflat1.isChecked()) {
            locflatCheck1 = 1;
            hmap.put("locflat1", String.valueOf(locflatCheck1));
        } else {
            locflatCheck1 = 0;
            hmap.put("locflat1", String.valueOf(locflatCheck1));
        }

        if (locflat2.isChecked()) {
            locflatCheck2 = 1;
            hmap.put("locflat2", String.valueOf(locflatCheck2));
        } else {
            locflatCheck2 = 0;
            hmap.put("locflat2", String.valueOf(locflatCheck2));
        }

        if (locflat3.isChecked()) {
            locflatCheck3 = 1;
            hmap.put("locflat3", String.valueOf(locflatCheck3));
        } else {
            locflatCheck3 = 0;
            hmap.put("locflat3", String.valueOf(locflatCheck3));
        }

        if (locflat4.isChecked()) {
            locflatCheck4 = 1;
            hmap.put("locflat4", String.valueOf(locflatCheck4));
        } else {
            locflatCheck1 = 0;
            hmap.put("locflat4", String.valueOf(locflatCheck4));
        }

        if (locflat5.isChecked()) {
            locflatCheck5 = 1;
            hmap.put("locflat5", String.valueOf(locflatCheck5));
        } else {
            locflatCheck1 = 0;
            hmap.put("locflat5", String.valueOf(locflatCheck5));
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
                et_owner.setText(object.getString("ownername"));
                et_purchase.setText(object.getString("purchasename"));
                et_Paddress.setText(object.getString("p_address"));
                et_Raddress.setText(object.getString("r_address"));
                et_north.setText(object.getString("north"));
                et_south.setText(object.getString("south"));
                et_east.setText(object.getString("east"));
                et_west.setText(object.getString("west"));
                et_landmark.setText(object.getString("landmark"));
                et_wardname.setText(object.getString("wardname"));
                et_zname.setText(object.getString("zonename"));
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
                new_developmnt.setText(object.getString("development"));

                if (object.getString("selected_cons").equals("Free Hold")) {
                    rb_cons1.setChecked(true);
                } else if (object.getString("selected_cons").equals("Lease Hold")) {
                    rb_cons2.setChecked(true);
                } else {

                }

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

                } else if (object.getString("selected_development").equals("MDDA")) {
                    rb_developmnt7.setChecked(true);

                } else if (object.getString("selected_development").equals("Any other Development Authority")) {
                    rb_developmnt8.setChecked(true);

                } else if (object.getString("selected_development").equals("Area not within any development authority limits")) {
                    rb_developmnt9.setChecked(true);

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

                if (object.getString("locationC1").equals("1")) {
                    locationC1.setChecked(true);
                } else {
                    locationC1.setChecked(false);
                }

                if (object.getString("locationC2").equals("1")) {
                    locationC2.setChecked(true);
                } else {
                    locationC2.setChecked(false);
                }

                if (object.getString("locationC3").equals("1")) {
                    locationC3.setChecked(true);
                } else {
                    locationC3.setChecked(false);
                }

                if (object.getString("locationC4").equals("1")) {
                    locationC4.setChecked(true);
                } else {
                    locationC4.setChecked(false);
                }

                if (object.getString("locationC5").equals("1")) {
                    locationC5.setChecked(true);
                } else {
                    locationC5.setChecked(false);
                }

                if (object.getString("locationC6").equals("1")) {
                    locationC6.setChecked(true);
                } else {
                    locationC6.setChecked(false);
                }

                if (object.getString("locationC7").equals("1")) {
                    locationC7.setChecked(true);
                } else {
                    locationC7.setChecked(false);
                }

                if (object.getString("locationC8").equals("1")) {
                    locationC8.setChecked(true);
                } else {
                    locationC8.setChecked(false);
                }

                if (object.getString("locationC9").equals("1")) {
                    locationC9.setChecked(true);
                } else {
                    locationC9.setChecked(false);
                }

                if (object.getString("locationC10").equals("1")) {
                    locationC10.setChecked(true);
                } else {
                    locationC10.setChecked(false);
                }

                if (object.getString("locationC11").equals("1")) {
                    locationC11.setChecked(true);
                } else {
                    locationC11.setChecked(false);
                }



                if (object.getString("locflat1").equals("1")) {
                    locflat1.setChecked(true);
                } else {
                    locflat1.setChecked(false);
                }
                if (object.getString("locflat2").equals("1")) {
                    locflat2.setChecked(true);
                } else {
                    locflat2.setChecked(false);
                }
                if (object.getString("locflat3").equals("1")) {
                    locflat3.setChecked(true);
                } else {
                    locflat3.setChecked(false);
                }
                if (object.getString("locflat4").equals("1")) {
                    locflat4.setChecked(true);
                } else {
                    locflat4.setChecked(false);
                }
                if (object.getString("locflat5").equals("1")) {
                    locflat5.setChecked(true);
                } else {
                    locflat5.setChecked(false);
                }
            }

        }

    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}



