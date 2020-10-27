package com.vis.android.Activities.OtherThanFlats;

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

import static com.vis.android.Activities.OtherThanFlats.Other1.hm;

public class Other3 extends AppCompatActivity implements View.OnClickListener{
    TextView next;
    Intent intent;
    TextView previous;
    EditText et_deed, et_map, et_survey,et_conversation,et_merged;
    RadioGroup land_type,land_shape,land_level,ratio,access,demerged,possessed,current,marched_boundries;
    RadioButton checkedRadioButton,rb_landtype1,rb_landtype2,rb_landtype3,rb_landtype4,rb_landtype5,rb_landtype6,rb_landshape1,rb_landshape2,rb_landshape3,rb_landshape4,rb_landshape5,rb_landshape6,rb_landshape7,rb_landlevel1,rb_landlevel2,rb_landlevel3,rb_landlevel4,rb_ratio1,rb_ratio2,rb_ratio3,rb_ratio4,rb_boundries1,rb_boundries2,rb_boundries3,rb_boundries4,rb_access1,rb_access2,rb_access3,rb_access4,rb_demerged1,rb_demerged2,rb_demerged3,rb_possesed1,rb_possesed2,rb_possesed3,rb_possesed4,rb_possesed5,rb_possesed6,rb_possesed7,rb_possesed8;
    RadioButton rb_current1,rb_current2,rb_current3,rb_current4,rb_current5,rb_current6,rb_current7,rb_current8;
    String selected_landtype,selected_landshape,selected_landlevel,selected_ratio,selected_access,selected_demerged,selected_possesed,selected_current,selected_boundries;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other3);
        getid();
        setListener();
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("hashmap====", String.valueOf(hm));
        land_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_landtype = (String) checkedRadioButton.getText();

                }
            }
        });

        land_shape.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_landshape = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        land_level.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_landlevel = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        ratio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_ratio = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        access.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        demerged.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_demerged = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        possessed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_possesed = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        current.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_current = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        marched_boundries.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
    }

    public void getid(){
        next=(TextView)findViewById(R.id.next);
        previous = (TextView) findViewById(R.id.tv_previous);
        et_deed = (EditText) findViewById(R.id.et_deed);
        et_map = (EditText) findViewById(R.id.et_map);
        et_survey = (EditText) findViewById(R.id.et_survey);
        et_conversation = (EditText) findViewById(R.id.et_conversation);
        et_merged = (EditText) findViewById(R.id.et_merged);

        land_type=(RadioGroup)findViewById(R.id.rg_landtype);
        land_shape=(RadioGroup)findViewById(R.id.rg_shape);
        land_level=(RadioGroup)findViewById(R.id.rg_landlavel);
        ratio=(RadioGroup)findViewById(R.id.rg_ratio);
        access=(RadioGroup)findViewById(R.id.rg_access);
        demerged=(RadioGroup)findViewById(R.id.rg_demarcated);
        possessed=(RadioGroup)findViewById(R.id.rg_possessed);
        current=(RadioGroup)findViewById(R.id.rg_current);
        marched_boundries=(RadioGroup)findViewById(R.id.rg_boundries);

        rb_landtype1=(RadioButton)findViewById(R.id.rb_land1);
        rb_landtype2=(RadioButton)findViewById(R.id.rb_land2);
        rb_landtype3=(RadioButton)findViewById(R.id.rb_land3);
        rb_landtype4=(RadioButton)findViewById(R.id.rb_land4);
        rb_landtype5=(RadioButton)findViewById(R.id.rb_land5);
        rb_landtype6=(RadioButton)findViewById(R.id.rb_land6);

        rb_landlevel1=(RadioButton)findViewById(R.id.rb_landlavel1);
        rb_landlevel2=(RadioButton)findViewById(R.id.rb_landlavel2);
        rb_landlevel3=(RadioButton)findViewById(R.id.rb_landlavel3);
        rb_landlevel4=(RadioButton)findViewById(R.id.rb_landlavel4);

        rb_ratio1=(RadioButton)findViewById(R.id.rb_ratio1);
        rb_ratio2=(RadioButton)findViewById(R.id.rb_ratio2);
        rb_ratio3=(RadioButton)findViewById(R.id.rb_ratio3);
        rb_ratio4=(RadioButton)findViewById(R.id.rb_ratio4);

        rb_boundries1=(RadioButton)findViewById(R.id.rb_boundries1);
        rb_boundries2=(RadioButton)findViewById(R.id.rb_boundries2);
        rb_boundries3=(RadioButton)findViewById(R.id.rb_boundries3);
        rb_boundries4=(RadioButton)findViewById(R.id.rb_boundries4);

        rb_access1=(RadioButton)findViewById(R.id.rb_access1);
        rb_access2=(RadioButton)findViewById(R.id.rb_access2);
        rb_access3=(RadioButton)findViewById(R.id.rb_access3);
        rb_access4=(RadioButton)findViewById(R.id.rb_access4);

        rb_demerged1=(RadioButton)findViewById(R.id.rb_demarcated1);
        rb_demerged2=(RadioButton)findViewById(R.id.rb_demarcated2);
        rb_demerged3=(RadioButton)findViewById(R.id.rb_demarcated3);

        rb_possesed1=(RadioButton)findViewById(R.id.rb_possessed1);
        rb_possesed2=(RadioButton)findViewById(R.id.rb_possessed2);
        rb_possesed3=(RadioButton)findViewById(R.id.rb_possessed3);
        rb_possesed4=(RadioButton)findViewById(R.id.rb_possessed4);
        rb_possesed5=(RadioButton)findViewById(R.id.rb_possessed5);
        rb_possesed6=(RadioButton)findViewById(R.id.rb_possessed6);
        rb_possesed7=(RadioButton)findViewById(R.id.rb_possessed7);
        rb_possesed8=(RadioButton)findViewById(R.id.rb_possessed8);

        rb_landshape1=(RadioButton)findViewById(R.id.rb_shape1);
        rb_landshape2=(RadioButton)findViewById(R.id.rb_shape2);
        rb_landshape3=(RadioButton)findViewById(R.id.rb_shape3);
        rb_landshape4=(RadioButton)findViewById(R.id.rb_shape4);
        rb_landshape5=(RadioButton)findViewById(R.id.rb_shape5);
        rb_landshape6=(RadioButton)findViewById(R.id.rb_shape6);
        rb_landshape7=(RadioButton)findViewById(R.id.rb_shape7);

        rb_current1=(RadioButton)findViewById(R.id.rb_current1);
        rb_current2=(RadioButton)findViewById(R.id.rb_current2);
        rb_current3=(RadioButton)findViewById(R.id.rb_current3);
        rb_current4=(RadioButton)findViewById(R.id.rb_current4);
        rb_current5=(RadioButton)findViewById(R.id.rb_current5);
        rb_current6=(RadioButton)findViewById(R.id.rb_current6);
        rb_current7=(RadioButton)findViewById(R.id.rb_current7);
        rb_current8=(RadioButton)findViewById(R.id.rb_current8);




    }

    public void setListener(){
        next.setOnClickListener(this);
        previous.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
               checkpass();
                break;

            case R.id.tv_previous:
                onBackPressed();
                break;

        }
    }

    public void checkpass(){
        if(et_deed.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter title deed", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_map.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter map area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();

            }
        else if(et_survey.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter site survey", Snackbar.LENGTH_SHORT);
            mySnackbar.show();

        }
        else if(et_conversation.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter about conversation", Snackbar.LENGTH_SHORT);
            mySnackbar.show();

        }
        else if(et_conversation.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter if the property merged with any other property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();

        }
        else if (land_type.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select land type", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (land_shape.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select land shape", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (land_level.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select land level", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (ratio.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter depth ratio", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (marched_boundries.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select if boundry matched", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (access.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select if independent access available to the property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }

        else if (demerged.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select if property clearly demerged with boundries", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if (et_merged.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select if property merged with any other property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }

        else if (current.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select current activity carried out in the property", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else {
            putintoHashMap();
            intent = new Intent(Other3.this, Other4.class);
            startActivity(intent);
        }
    }

    public void putintoHashMap() {
        hm.put("deed", et_deed.getText().toString());
        hm.put("map", et_map.getText().toString());
        hm.put("survey", et_survey.getText().toString());
        hm.put("conversation", et_conversation.getText().toString());
        hm.put("selected_landtype", selected_landtype);
        hm.put("selected_landshape", selected_landshape);
        hm.put("selected_landlevel", selected_landlevel);
        hm.put("selected_ratio",selected_ratio);
        hm.put("selected_boundries", selected_boundries);
        hm.put("selected_access", selected_access);
        hm.put("selected_demerged", selected_demerged);
        hm.put("merged", et_merged.getText().toString());
        hm.put("selected_possesed", selected_possesed);
        hm.put("selected_current", selected_current);
    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getOtherFlats();
        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                et_deed.setText(object.getString("deed"));
                et_map.setText(object.getString("map"));
                et_survey.setText(object.getString("survey"));
                et_conversation.setText(object.getString("conversation"));
                et_merged.setText(object.getString("merged"));

                if (object.getString("selected_landtype").equals("Solid")) {
                    rb_landtype1.setChecked(true);
                } else if (object.getString("selected_landtype").equals("Rocky")) {
                    rb_landtype2.setChecked(true);

                } else if (object.getString("selected_landtype").equals("Marsh Land")) {
                    rb_landtype3.setChecked(true);

                } else if (object.getString("selected_landtype").equals("Reclaimed Land")) {
                    rb_landtype4.setChecked(true);

                }
                else if (object.getString("selected_landtype").equals("Water logged")) {
                    rb_landtype5.setChecked(true);

                }
                else if (object.getString("selected_landtype").equals("Land locked")) {
                    rb_landtype6.setChecked(true);

                }
               else {

                }


                if (object.getString("selected_landshape").equals("Square")) {
                    rb_landshape1.setChecked(true);
                } else if (object.getString("selected_landshape").equals("Rectangular")) {
                    rb_landshape2.setChecked(true);

                } else if (object.getString("selected_landshape").equals("Trapezium")) {
                    rb_landshape3.setChecked(true);

                } else if (object.getString("selected_landshape").equals("Triangular")) {
                    rb_landshape4.setChecked(true);

                } else if (object.getString("selected_landshape").equals("Trapezoid")) {
                    rb_landshape5.setChecked(true);

                } else if (object.getString("selected_landshape").equals("Irregular")) {
                    rb_landshape6.setChecked(true);

                } else if (object.getString("selected_landshape").equals("Not Applicable")) {
                    rb_landshape7.setChecked(true);

                } else {

                }


                if (object.getString("selected_landlevel").equals("On road level")) {
                    rb_landlevel1.setChecked(true);
                } else if (object.getString("selected_landlevel").equals("Below road level")) {
                    rb_landlevel2.setChecked(true);

                } else if (object.getString("selected_landlevel").equals("Above road level")) {
                    rb_landlevel3.setChecked(true);

                }
                else if (object.getString("selected_landlevel").equals("Not Applicable")) {
                    rb_landlevel4.setChecked(true);

                } else {

                }


                if (object.getString("selected_ratio").equals("Normal frontage")) {
                    rb_ratio1.setChecked(true);
                } else if (object.getString("selected_ratio").equals("Less frontage")) {
                    rb_ratio2.setChecked(true);
                } else if (object.getString("selected_ratio").equals("Large frontage")) {
                    rb_ratio3.setChecked(true);
                }
                else if (object.getString("selected_ratio").equals("Not Applicable")) {
                    rb_ratio4.setChecked(true);

                }else {
                }

                if (object.getString("selected_boundries").equals("Yes")) {
                    rb_boundries1.setChecked(true);
                } else if (object.getString("selected_boundries").equals("No")) {
                    rb_boundries2.setChecked(true);
                } else if (object.getString("selected_boundries").equals("No relevant papers available to match the\n" +
                        "boundaries")) {
                    rb_boundries3.setChecked(true);
                } else if (object.getString("selected_boundries").equals("Boundaries not mentioned in available documents")) {
                    rb_boundries4.setChecked(true);
                } else {
                }


                if (object.getString("selected_access").equals("Clear independent access is available")) {
                    rb_access1.setChecked(true);
                } else if (object.getString("selected_access").equals("Access available in sharing of other adjoining property")) {
                    rb_access2.setChecked(true);
                } else if (object.getString("selected_access").equals("No clear access is available")) {
                    rb_access3.setChecked(true);
                } else if (object.getString("selected_access").equals("Access is closed due to dispute")) {
                    rb_access4.setChecked(true);
                }else {
                }


                if (object.getString("selected_demerged").equals("Yes")) {
                    rb_demerged1.setChecked(true);
                } else if (object.getString("selected_demerged").equals("No")) {
                    rb_demerged2.setChecked(true);
                } else if (object.getString("selected_demerged").equals("Only with Temporary boundaries")) {
                    rb_demerged3.setChecked(true);
                } else {
                }


            if (object.getString("selected_possesed").equals("Owner")) {
                rb_possesed1.setChecked(true);
            } else if (object.getString("selected_possesed").equals("Vacant")) {
                rb_possesed2.setChecked(true);
            } else if (object.getString("selected_possesed").equals("Lessee")) {
                rb_possesed3.setChecked(true);
            }
            else if (object.getString("selected_possesed").equals("Under Construction")) {
                rb_possesed4.setChecked(true);
            }
            else if (object.getString("selected_possesed").equals("Couldn’t be Surveyed")) {
                rb_possesed5.setChecked(true);
            }
            else if (object.getString("selected_possesed").equals("Property was locked")) {
                rb_possesed6.setChecked(true);
            }
            else if (object.getString("selected_possesed").equals("Bank sealed")) {
                rb_possesed7.setChecked(true);
            }
            else if (object.getString("selected_possesed").equals("Court sealed")) {
                rb_possesed8.setChecked(true);
            }else {
            }

                if (object.getString("selected_current").equals("Residential purpose")) {
                    rb_current1.setChecked(true);
                } else if (object.getString("selected_current").equals("Commercial purpose")) {
                    rb_current2.setChecked(true);
                } else if (object.getString("selected_current").equals("Godown")) {
                    rb_current3.setChecked(true);
                }
                else if (object.getString("selected_current").equals("Office")) {
                    rb_current4.setChecked(true);
                }
                else if (object.getString("selected_current").equals("Industrial")) {
                    rb_current5.setChecked(true);
                }
                else if (object.getString("selected_current").equals("Vacant")) {
                    rb_current6.setChecked(true);
                }
                else if (object.getString("selected_current").equals("Locked")) {
                    rb_current7.setChecked(true);
                }
                else if (object.getString("selected_current").equals("Any other use")) {
                    rb_current8.setChecked(true);
                } else {
                }
        }
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    }

