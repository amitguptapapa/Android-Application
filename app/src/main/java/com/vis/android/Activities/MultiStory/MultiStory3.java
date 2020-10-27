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

public class MultiStory3 extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout back;
    TextView next;
    Intent intent;
    TextView previous;
    RadioGroup rg_buildArea, rg_boundries, rg_access, rg_consstatus, rg_typeGroup, rg_internal, rg_external, rg_proprty, rg_cActivty, rg_woodenwork, rg_decorator, rg_bMaintenence;
    RadioButton checkedRadioButton, rb_build1, rb_build2, rb_build3, rb_build4, rb_yes, rb_no, rb_access1, rb_access2, rb_consstatus1, rb_consstatus2, rb_consstatus3, rb_typeGroup1, rb_typeGroup2, rb_typeGroup3, rbinternal1, rbinternal2, rbinternal3, rbinternal4, rbinternal5, rbinternal6, rbinternal7, rbinternal8, rbinternal9, rb_excellent1, rb_excellent2, rb_excellent3, rb_excellent4, rb_excellent5, rb_excellent6, rb_excellent7, rb_excellent8, rb_maintenence1, rb_maintenence2, rb_maintenence3, rb_wooden1, rb_wooden2, rb_wooden3, rb_wooden4, rb_wooden5, rb_wooden6, rb_wooden7, rb_wooden8, rb_wooden9, rb_decorator1, rb_decorator2, rb_decorator3, rb_decorator4, rb_decorator5, rb_decorator6, rb_decorator7, rb_decorator8, rb_decorator9, rb_property1, rb_property2, rb_property3, rb_property4, rb_property5, rb_property6, rb_property7, rb_property8, rb_activity1, rb_activity2, rb_activity3, rb_activity4, rb_activity5, rb_activity6, rb_activity7;
    EditText et_deed, et_map, et_survey, et_property, et_nofloor, et_floorflat, et_typeflat, et_bAge, et_defects, et_violation, et_sComments;
    String selected_area, selected_boundries, selected_access, selected_status, selected_type, selected_internal, selected_external, selected_property, selected_activity, selected_woodenwork, selected_decorator, selected_mainternance;
    ArrayList<HashMap<String, String>> alistt = new ArrayList<HashMap<String, String>>();
    CheckBox cb_utility1,cb_utility2,cb_utility3,cb_utility4,cb_utility5,cb_utility6,cb_utility7,cb_utility8,cb_utility9;
    int utilityCheck1,utilityCheck2,utilityCheck3,utilityCheck4,utilityCheck5,utilityCheck6,utilityCheck7,utilityCheck8,utilityCheck9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_story3);
        getid();
        setListener();
        Log.v("hashmap===", String.valueOf(hmap));
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        rg_buildArea.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_area = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));


                }
            }
        });

        rg_boundries.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_boundries = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_access.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_access = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_consstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_status = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_type = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_internal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_internal = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_external.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_external = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_proprty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        rg_cActivty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_activity = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_woodenwork.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_woodenwork = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_decorator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_decorator = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_bMaintenence.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_mainternance = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });
    }

    public void getid() {
        back = (RelativeLayout) findViewById(R.id.rl_back);
        next = (TextView) findViewById(R.id.next);
        previous = (TextView) findViewById(R.id.tv_previous);
        et_deed = (EditText) findViewById(R.id.et_deed);
        et_map = (EditText) findViewById(R.id.et_map);
        et_survey = (EditText) findViewById(R.id.et_survey);
        et_property = (EditText) findViewById(R.id.et_property);
        et_nofloor = (EditText) findViewById(R.id.et_nofloor);
        et_floorflat = (EditText) findViewById(R.id.et_floorflat);
        et_typeflat = (EditText) findViewById(R.id.et_typeflat);
        et_bAge = (EditText) findViewById(R.id.et_bAge);
        et_defects = (EditText) findViewById(R.id.et_defects);
        et_violation = (EditText) findViewById(R.id.et_violation);
        et_sComments = (EditText) findViewById(R.id.et_sComments);

        rg_buildArea = (RadioGroup) findViewById(R.id.rg_buildArea);
        rg_boundries = (RadioGroup) findViewById(R.id.rg_boundries);
        rg_access = (RadioGroup) findViewById(R.id.rg_access);
        rg_consstatus = (RadioGroup) findViewById(R.id.rg_consstatus);
        rg_typeGroup = (RadioGroup) findViewById(R.id.rg_typeGroup);
        rg_internal = (RadioGroup) findViewById(R.id.rg_internal);
        rg_external = (RadioGroup) findViewById(R.id.rg_external);
        rg_proprty = (RadioGroup) findViewById(R.id.rg_proprty);
        rg_cActivty = (RadioGroup) findViewById(R.id.rg_cActivty);
        rg_woodenwork = (RadioGroup) findViewById(R.id.rg_woodenwork);
        rg_decorator = (RadioGroup) findViewById(R.id.rg_decorator);
        rg_bMaintenence = (RadioGroup) findViewById(R.id.rg_bMaintenence);

        rb_build1 = (RadioButton) findViewById(R.id.rb_build1);
        rb_build2 = (RadioButton) findViewById(R.id.rb_build2);
        rb_build3 = (RadioButton) findViewById(R.id.rb_build3);
        rb_build4 = (RadioButton) findViewById(R.id.rb_build4);
        rb_yes = (RadioButton) findViewById(R.id.rb_yes);
        rb_no = (RadioButton) findViewById(R.id.rb_no);

        rb_access1 = (RadioButton) findViewById(R.id.rb_access1);
        rb_access2 = (RadioButton) findViewById(R.id.rb_access2);
        rb_consstatus1 = (RadioButton) findViewById(R.id.rb_status1);
        rb_consstatus2 = (RadioButton) findViewById(R.id.rb_status2);
        rb_consstatus3 = (RadioButton) findViewById(R.id.rb_status3);

        rb_typeGroup1 = (RadioButton) findViewById(R.id.rb_tGroup1);
        rb_typeGroup2 = (RadioButton) findViewById(R.id.rb_tGroup2);
        rb_typeGroup3 = (RadioButton) findViewById(R.id.rb_tGroup3);

        rbinternal1 = (RadioButton) findViewById(R.id.rb_internal1);
        rbinternal2 = (RadioButton) findViewById(R.id.rb_internal2);
        rbinternal3 = (RadioButton) findViewById(R.id.rb_internal3);
        rbinternal4 = (RadioButton) findViewById(R.id.rb_internal4);
        rbinternal5 = (RadioButton) findViewById(R.id.rb_internal5);
        rbinternal6 = (RadioButton) findViewById(R.id.rb_internal6);
        rbinternal7 = (RadioButton) findViewById(R.id.rb_internal7);
        rbinternal8 = (RadioButton) findViewById(R.id.rb_internal8);
        rbinternal9 = (RadioButton) findViewById(R.id.rb_internal9);

        rb_excellent1 = (RadioButton) findViewById(R.id.rb_excellent1);
        rb_excellent2 = (RadioButton) findViewById(R.id.rb_excellent2);
        rb_excellent3 = (RadioButton) findViewById(R.id.rb_excellent3);
        rb_excellent4 = (RadioButton) findViewById(R.id.rb_excellent4);
        rb_excellent5 = (RadioButton) findViewById(R.id.rb_excellent5);
        rb_excellent6 = (RadioButton) findViewById(R.id.rb_excellent6);
        rb_excellent7 = (RadioButton) findViewById(R.id.rb_excellent7);
        rb_excellent8 = (RadioButton) findViewById(R.id.rb_excellent8);

        rb_maintenence1 = (RadioButton) findViewById(R.id.rb_maintenence1);
        rb_maintenence2 = (RadioButton) findViewById(R.id.rb_maintenence2);
        rb_maintenence3 = (RadioButton) findViewById(R.id.rb_maintenence3);

        rb_wooden1 = (RadioButton) findViewById(R.id.rb_woodenwork1);
        rb_wooden2 = (RadioButton) findViewById(R.id.rb_woodenwork2);
        rb_wooden3 = (RadioButton) findViewById(R.id.rb_woodenwork3);
        rb_wooden4 = (RadioButton) findViewById(R.id.rb_woodenwork4);
        rb_wooden5 = (RadioButton) findViewById(R.id.rb_woodenwork5);
        rb_wooden6 = (RadioButton) findViewById(R.id.rb_woodenwork6);
        rb_wooden7 = (RadioButton) findViewById(R.id.rb_woodenwork7);
        rb_wooden8 = (RadioButton) findViewById(R.id.rb_woodenwork8);
        rb_wooden9 = (RadioButton) findViewById(R.id.rb_woodenwork9);

        rb_decorator1 = (RadioButton) findViewById(R.id.rb_decorator1);
        rb_decorator2 = (RadioButton) findViewById(R.id.rb_decorator2);
        rb_decorator3 = (RadioButton) findViewById(R.id.rb_decorator3);
        rb_decorator4 = (RadioButton) findViewById(R.id.rb_decorator4);
        rb_decorator5 = (RadioButton) findViewById(R.id.rb_decorator5);
        rb_decorator6 = (RadioButton) findViewById(R.id.rb_decorator6);
        rb_decorator7 = (RadioButton) findViewById(R.id.rb_decorator7);
        rb_decorator8 = (RadioButton) findViewById(R.id.rb_decorator8);
        rb_decorator9 = (RadioButton) findViewById(R.id.rb_decorator9);

        rb_property1 = (RadioButton) findViewById(R.id.rb_proprty1);
        rb_property2 = (RadioButton) findViewById(R.id.rb_proprty2);
        rb_property3 = (RadioButton) findViewById(R.id.rb_proprty3);
        rb_property4 = (RadioButton) findViewById(R.id.rb_proprty4);
        rb_property5 = (RadioButton) findViewById(R.id.rb_proprty5);
        rb_property6 = (RadioButton) findViewById(R.id.rb_proprty6);
        rb_property7 = (RadioButton) findViewById(R.id.rb_proprty7);
        rb_property8 = (RadioButton) findViewById(R.id.rb_proprty8);

        rb_activity1 = (RadioButton) findViewById(R.id.rb_cActivty1);
        rb_activity2 = (RadioButton) findViewById(R.id.rb_cActivty2);
        rb_activity3 = (RadioButton) findViewById(R.id.rb_cActivty3);
        rb_activity4 = (RadioButton) findViewById(R.id.rb_cActivty4);
        rb_activity5 = (RadioButton) findViewById(R.id.rb_cActivty5);
        rb_activity6 = (RadioButton) findViewById(R.id.rb_cActivty6);
        rb_activity7 = (RadioButton) findViewById(R.id.rb_cActivty7);

        cb_utility1=findViewById(R.id.cb_utility1);
        cb_utility2=findViewById(R.id.cb_utility2);
        cb_utility3=findViewById(R.id.cb_utility3);
        cb_utility4=findViewById(R.id.cb_utility4);
        cb_utility5=findViewById(R.id.cb_utility5);
        cb_utility6=findViewById(R.id.cb_utility6);
        cb_utility7=findViewById(R.id.cb_utility7);
        cb_utility8=findViewById(R.id.cb_utility8);
        cb_utility9=findViewById(R.id.cb_utility9);

    }

    public void setListener() {
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.next:
             checkpass();
                break;

            case R.id.tv_previous:
                onBackPressed();
                break;
        }
    }

    public void checkpass() {
        if (rg_buildArea.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select Build-up area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_deed.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select Build-up area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_map.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select map area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_survey.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select site servey", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_boundries.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select if Boundry matched", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_access.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select if Access available to property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_property.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select if the property is merged", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_consstatus.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select construction status", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_nofloor.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter total no of floors", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_floorflat.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter floors on which flat is situated", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_typeflat.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter type of flat", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_bAge.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter age of building", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_typeGroup.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select type of Group Housing Society", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_internal.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select internal appearance of the building", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_external.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select external appearance of the building", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_bMaintenence.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select maintenance of the building", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_woodenwork.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select wooden work", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_decorator.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select interior decorator", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_defects.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter if any defect in group housing society", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_violation.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter if any violation in flat", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_proprty.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select property is possesed by", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_cActivty.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select current activity carried in the property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (rg_woodenwork.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select wooden work", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (rg_decorator.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select interior decoration", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else {
            insertDB();
            intent = new Intent(MultiStory3.this, MultiStory4.class);
            startActivity(intent);
        }
    }

    public void insertDB() {
        hmap.put("selected_area", selected_area);
        hmap.put("deed", et_deed.getText().toString());
        hmap.put("map", et_map.getText().toString());
        hmap.put("site_survey", et_survey.getText().toString());
        hmap.put("selected_boundries", selected_boundries);
        hmap.put("selected_access", selected_access);
        hmap.put("property_merged", et_property.getText().toString());
        hmap.put("selected_status", selected_status);
        hmap.put("total_floor", et_nofloor.getText().toString());
        hmap.put("floorflat", et_floorflat.getText().toString());
        hmap.put("type_flats", et_typeflat.getText().toString());
        hmap.put("age_flat", et_bAge.getText().toString());
        hmap.put("selected_type", selected_type);
        hmap.put("selected_internal", selected_internal);
        hmap.put("selected_external", selected_external);
        hmap.put("selected_mainternance", selected_mainternance);
        hmap.put("selected_woodenwork", selected_woodenwork);
        hmap.put("selected_decorator", selected_decorator);
        hmap.put("defectes", et_defects.getText().toString());
        hmap.put("violation", et_violation.getText().toString());
        hmap.put("selected_property", selected_property);
        hmap.put("selected_activity", selected_activity);
        hmap.put("special_comments", et_sComments.getText().toString());


        if (cb_utility1.isChecked()) {
            utilityCheck1 = 1;
            hmap.put("cb_utility1", String.valueOf(utilityCheck1));
        } else {
            utilityCheck1 = 0;
            hmap.put("cb_utility1", String.valueOf(utilityCheck1));
        }

        if (cb_utility2.isChecked()) {
            utilityCheck2 = 1;
            hmap.put("cb_utility2", String.valueOf(utilityCheck2));
        } else {
            utilityCheck2 = 0;
            hmap.put("cb_utility2", String.valueOf(utilityCheck2));
        }

        if (cb_utility3.isChecked()) {
            utilityCheck3 = 1;
            hmap.put("cb_utility3", String.valueOf(utilityCheck3));
        } else {
            utilityCheck3 = 0;
            hmap.put("cb_utility3", String.valueOf(utilityCheck3));
        }

        if (cb_utility4.isChecked()) {
            utilityCheck4 = 1;
            hmap.put("cb_utility4", String.valueOf(utilityCheck4));
        } else {
            utilityCheck4 = 0;
            hmap.put("cb_utility4", String.valueOf(utilityCheck4));
        }

        if (cb_utility5.isChecked()) {
            utilityCheck5 = 1;
            hmap.put("cb_utility5", String.valueOf(utilityCheck5));
        } else {
            utilityCheck5 = 0;
            hmap.put("cb_utility5", String.valueOf(utilityCheck5));
        }

        if (cb_utility6.isChecked()) {
            utilityCheck6 = 1;
            hmap.put("cb_utility6", String.valueOf(utilityCheck6));
        } else {
            utilityCheck6 = 0;
            hmap.put("cb_utility6", String.valueOf(utilityCheck6));
        }

        if (cb_utility7.isChecked()) {
            utilityCheck7 = 1;
            hmap.put("cb_utility7", String.valueOf(utilityCheck7));
        } else {
            utilityCheck7 = 0;
            hmap.put("cb_utility7", String.valueOf(utilityCheck7));
        }

        if (cb_utility8.isChecked()) {
            utilityCheck8 = 1;
            hmap.put("cb_utility8", String.valueOf(utilityCheck8));
        } else {
            utilityCheck8 = 0;
            hmap.put("cb_utility8", String.valueOf(utilityCheck8));
        }

        if (cb_utility9.isChecked()) {
            utilityCheck9 = 1;
            hmap.put("cb_utility9", String.valueOf(utilityCheck9));
        } else {
            utilityCheck9 = 0;
            hmap.put("cb_utility9", String.valueOf(utilityCheck9));
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
                if (object.getString("selected_area").equals("Covered Area")) {
                    rb_build1.setChecked(true);
                } else if (object.getString("selected_area").equals("Floor Area")) {
                    rb_build2.setChecked(true);
                } else if (object.getString("selected_area").equals("Super Area")) {
                    rb_build3.setChecked(true);
                } else if (object.getString("selected_area").equals("Carpet Area")) {
                    rb_build4.setChecked(true);
                } else {

                }

                if (object.getString("selected_boundries").equals("Yes")) {
                    rb_yes.setChecked(true);
                } else if (object.getString("selected_area").equals("No")) {
                    rb_no.setChecked(true);
                } else {

                }

                if (object.getString("selected_access").equals("Access is closed due to dispute")) {
                    rb_access1.setChecked(true);
                } else if (object.getString("selected_access").equals("Clear independent access is available, Access available in sharing of other adjoining property")) {
                    rb_access2.setChecked(true);
                } else {

                }

                if (object.getString("selected_status").equals("Built-up property in use")) {
                    rb_consstatus1.setChecked(true);
                } else if (object.getString("selected_status").equals("Under construction")) {
                    rb_consstatus2.setChecked(true);
                } else if (object.getString("selected_status").equals("Construction not started")) {
                    rb_consstatus3.setChecked(true);
                } else {

                }

                if (object.getString("selected_type").equals("High End")) {
                    rb_typeGroup1.setChecked(true);
                } else if (object.getString("selected_type").equals("Normal")) {
                    rb_typeGroup2.setChecked(true);
                } else if (object.getString("selected_type").equals("Affordable Group Housing")) {
                    rb_typeGroup3.setChecked(true);
                } else {

                }

                if (object.getString("selected_internal").equals("Excellent")) {
                    rbinternal1.setChecked(true);
                } else if (object.getString("selected_internal").equals("Very Good")) {
                    rbinternal2.setChecked(true);
                } else if (object.getString("selected_internal").equals("Good")) {
                    rbinternal3.setChecked(true);
                } else if (object.getString("selected_internal").equals("Ordinary")) {
                    rbinternal4.setChecked(true);
                } else if (object.getString("selected_internal").equals("Average")) {
                    rbinternal5.setChecked(true);
                } else if (object.getString("selected_internal").equals("Poor")) {
                    rbinternal6.setChecked(true);
                } else if (object.getString("selected_internal").equals("Under construction")) {
                    rbinternal7.setChecked(true);
                } else if (object.getString("selected_internal").equals("No construction")) {
                    rbinternal8.setChecked(true);
                } else if (object.getString("selected_internal").equals("No Survey")) {
                    rbinternal9.setChecked(true);
                } else {

                }

                if (object.getString("selected_external").equals("Excellent")) {
                    rb_excellent1.setChecked(true);
                } else if (object.getString("selected_external").equals("Very Good")) {
                    rb_excellent2.setChecked(true);
                } else if (object.getString("selected_external").equals("Good")) {
                    rb_excellent3.setChecked(true);
                } else if (object.getString("selected_external").equals("Ordinary")) {
                    rb_excellent4.setChecked(true);
                } else if (object.getString("selected_external").equals("Average")) {
                    rb_excellent5.setChecked(true);
                } else if (object.getString("selected_external").equals("Poor")) {
                    rb_excellent6.setChecked(true);
                } else if (object.getString("selected_external").equals("Under construction")) {
                    rb_excellent7.setChecked(true);
                } else if (object.getString("selected_external").equals("No construction")) {
                    rb_excellent8.setChecked(true);
                } else {

                }

                if (object.getString("selected_mainternance").equals("Very Good")) {
                    rb_maintenence1.setChecked(true);
                } else if (object.getString("selected_mainternance").equals("Average")) {
                    rb_maintenence2.setChecked(true);
                } else if (object.getString("selected_mainternance").equals("Poor")) {
                    rb_maintenence3.setChecked(true);
                } else {

                }

                if (object.getString("selected_woodenwork").equals("Excellent")) {
                    rb_wooden1.setChecked(true);
                } else if (object.getString("selected_woodenwork").equals("Very Good")) {
                    rb_wooden2.setChecked(true);
                } else if (object.getString("selected_woodenwork").equals("Good")) {
                    rb_wooden3.setChecked(true);
                } else if (object.getString("selected_woodenwork").equals("Simple")) {
                    rb_wooden4.setChecked(true);
                } else if (object.getString("selected_woodenwork").equals("Ordinary")) {
                    rb_wooden5.setChecked(true);
                } else if (object.getString("selected_woodenwork").equals("Average")) {
                    rb_wooden6.setChecked(true);
                } else if (object.getString("selected_woodenwork").equals("Below Average")) {
                    rb_wooden7.setChecked(true);
                } else if (object.getString("selected_woodenwork").equals("No wooden work")) {
                    rb_wooden8.setChecked(true);
                } else if (object.getString("selected_woodenwork").equals("No survey")) {
                    rb_wooden9.setChecked(true);
                } else {

                }

                if (object.getString("selected_decorator").equals("Excellent")) {
                    rb_decorator1.setChecked(true);
                } else if (object.getString("selected_decorator").equals("Very Good")) {
                    rb_decorator2.setChecked(true);
                } else if (object.getString("selected_decorator").equals("Good")) {
                    rb_decorator3.setChecked(true);
                } else if (object.getString("selected_decorator").equals("Simple")) {
                    rb_decorator4.setChecked(true);
                } else if (object.getString("selected_decorator").equals("Ordinary")) {
                    rb_decorator5.setChecked(true);
                } else if (object.getString("selected_decorator").equals("Average")) {
                    rb_decorator6.setChecked(true);
                } else if (object.getString("selected_decorator").equals("Below Average")) {
                    rb_decorator7.setChecked(true);
                } else if (object.getString("selected_decorator").equals("No wooden work")) {
                    rb_decorator8.setChecked(true);
                } else if (object.getString("selected_decorator").equals("No survey")) {
                    rb_decorator9.setChecked(true);
                } else {

                }

                if (object.getString("selected_property").equals("Owner")) {
                    rb_property1.setChecked(true);
                } else if (object.getString("selected_property").equals("Vacant")) {
                    rb_property2.setChecked(true);
                } else if (object.getString("selected_property").equals("Lessee")) {
                    rb_property3.setChecked(true);
                } else if (object.getString("selected_property").equals("Under Construction")) {
                    rb_property4.setChecked(true);
                } else if (object.getString("selected_property").equals("Couldn’t be Surveyed")) {
                    rb_property5.setChecked(true);
                } else if (object.getString("selected_property").equals("Property was locked")) {
                    rb_property6.setChecked(true);
                } else if (object.getString("selected_property").equals("Bank sealed")) {
                    rb_property7.setChecked(true);
                } else if (object.getString("selected_property").equals("Court sealed")) {
                    rb_property8.setChecked(true);
                } else {

                }

                if (object.getString("selected_activity").equals("Residential purpose")) {
                    rb_activity1.setChecked(true);
                } else if (object.getString("selected_activity").equals("Commercial purpose")) {
                    rb_activity2.setChecked(true);
                } else if (object.getString("selected_activity").equals("Godown")) {
                    rb_activity3.setChecked(true);
                } else if (object.getString("selected_activity").equals("Office")) {
                    rb_activity4.setChecked(true);
                } else if (object.getString("selected_activity").equals("Vacant")) {
                    rb_activity5.setChecked(true);
                } else if (object.getString("selected_activity").equals("Locked")) {
                    rb_activity6.setChecked(true);
                } else if (object.getString("selected_activity").equals("Any other use")) {
                    rb_activity7.setChecked(true);
                } else {

                }

                if (object.getString("cb_utility1").equals("1")) {
                    cb_utility1.setChecked(true);
                } else {
                    cb_utility1.setChecked(false);
                }

                if (object.getString("cb_utility2").equals("1")) {
                    cb_utility2.setChecked(true);
                } else {
                    cb_utility2.setChecked(false);
                }

                if (object.getString("cb_utility3").equals("1")) {
                    cb_utility3.setChecked(true);
                } else {
                    cb_utility3.setChecked(false);
                }

                if (object.getString("cb_utility4").equals("1")) {
                    cb_utility4.setChecked(true);
                } else {
                    cb_utility4.setChecked(false);
                }

                if (object.getString("cb_utility5").equals("1")) {
                    cb_utility5.setChecked(true);
                } else {
                    cb_utility5.setChecked(false);
                }

                if (object.getString("cb_utility6").equals("1")) {
                    cb_utility6.setChecked(true);
                } else {
                    cb_utility6.setChecked(false);
                }

                if (object.getString("cb_utility7").equals("1")) {
                    cb_utility7.setChecked(true);
                } else {
                    cb_utility7.setChecked(false);
                }

                if (object.getString("cb_utility8").equals("1")) {
                    cb_utility8.setChecked(true);
                } else {
                    cb_utility8.setChecked(false);
                }

                if (object.getString("cb_utility9").equals("1")) {
                    cb_utility9.setChecked(true);
                } else {
                    cb_utility9.setChecked(false);
                }


                et_deed.setText(object.getString("deed"));
                et_map.setText(object.getString("map"));
                et_survey.setText(object.getString("site_survey"));
                et_defects.setText(object.getString("defectes"));
                et_violation.setText(object.getString("violation"));
                et_sComments.setText(object.getString("special_comments"));
                et_nofloor.setText(object.getString("total_floor"));
                et_floorflat.setText(object.getString("floorflat"));
                et_typeflat.setText(object.getString("type_flats"));
                et_bAge.setText(object.getString("age_flat"));
                et_property.setText(object.getString("property_merged"));
            }

        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
