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

public class Other4 extends AppCompatActivity implements View.OnClickListener {
    TextView next;
    Intent intent;
    TextView previous;
    RadioGroup rg_status, rg_buildarea, rg_Btype, rg_internal, rg_external, rg_Sclass, rg_kitchen, rg_Idecorator, rg_Bmaintenance, rg_class;
    String selected_status, selected_buildarea, selected_Btype, selected_internal, selected_external, selected_Sclass, selected_Eclass, selected_kitchen, selected_Idecorator, selected_Bmaintenance, selected_class;
    RadioButton checkedRadioButton, rb_status1, rb_status2, rb_status3, rb_build1, rb_build2, rb_build3, rb_build4, rb_Btype1, rb_Btype2, rb_Btype3, rb_Btype4, rb_Btype5, rb_internal1, rb_internal2, rb_internal3, rb_internal4, rb_internal5, rb_internal6, rb_internal7, rb_internal8, rb_internal9, rb_external1, rb_external2, rb_external3, rb_external4, rb_external5, rb_external6, rb_external7, rb_external8, rb_maintenance1, rb_maintenance2, rb_maintenance3, rb_maintenance4, rb_interiorD1, rb_interiorD2, rb_interiorD3, rb_interiorD4, rb_interiorD5, rb_interiorD6, rb_interiorD7, rb_interiorD8, rb_interiorD9, rb_kitchen1, rb_kitchen2, rb_kitchen3, rb_kitchen4, rb_kitchen5, rb_kitchen6, rb_Eclas1, rb_Eclas2, rb_Eclas3, rb_Eclas4, rb_Eclas5, rb_Eclas6, rb_Eclas7, rb_Eclas8, rb_cSanitart1, rb_cSanitart2, rb_cSanitart3, rb_cSanitart4, rb_cSanitart5, rb_cSanitart6, rb_cSanitart7, rb_cSanitart8;
    EditText et_deed, et_map, et_survey, et_nofloor, et_floorflat, et_typeunit, et_height;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    CheckBox cb_make1, cb_make2, cb_make3, cb_make4, cb_make5;
    CheckBox cb_finish1, cb_finish2, cb_finish3, cb_finish4, cb_finish5;
    CheckBox cb_Ifinishing1, cb_Ifinishing2, cb_Ifinishing3, cb_Ifinishing4, cb_Ifinishing5, cb_Ifinishing6, cb_Ifinishing7;
    CheckBox cb_Efinishing1, cb_Efinishing2, cb_Efinishing3, cb_Efinishing4, cb_Efinishing5, cb_Efinishing6, cb_Efinishing7,cb_Efinishing8,cb_Efinishing9,cb_Efinishing10,cb_Efinishing11;
    CheckBox cb_flooring1, cb_flooring2, cb_flooring3, cb_flooring4, cb_flooring5, cb_flooring6, cb_flooring7,cb_flooring8,cb_flooring9,cb_flooring10,cb_flooring11,cb_flooring12,cb_flooring13,cb_flooring14,cb_flooring15,cb_flooring16,cb_flooring17,cb_flooring18;
    int cb_flooring1Check, cb_flooring2Check, cb_flooring3Check, cb_flooring4Check, cb_flooring5Check, cb_flooring6Check, cb_flooring7Check,cb_flooring8Check,cb_flooring9Check,cb_flooring10Check,cb_flooring11Check,cb_flooring12Check,cb_flooring13Check,cb_flooring14Check,cb_flooring15Check,cb_flooring16Check,cb_flooring17Check,cb_flooring18Check;
    int cb_Efinishing1Check, cb_Efinishing2Check, cb_Efinishing3Check, cb_Efinishing4Check, cb_Efinishing5Check, cb_Efinishing6Check, cb_Efinishing7Check,cb_Efinishing8Check,cb_Efinishing9Check,cb_Efinishing10Check,cb_Efinishing11Check;
    int cb_Ifinishing1Check, cb_Ifinishing2Check, cb_Ifinishing3Check, cb_Ifinishing4Check, cb_Ifinishing5Check, cb_Ifinishing6Check, cb_Ifinishing7Check;
    int cb_finish1Check, cb_finish2Check, cb_finish3Check, cb_finish4Check, cb_finish5Check;
    int cb_make1Check, cb_make2Check, cb_make3Check, cb_make4Check, cb_make5Check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other4);
        getid();
        setListener();
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("hashmap====", String.valueOf(hm));
        rg_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        rg_class.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_class = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_buildarea.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_buildarea = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_Btype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_Btype = (String) checkedRadioButton.getText();
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

        rg_Sclass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_Sclass = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_kitchen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_kitchen = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_Idecorator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_Idecorator = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });

        rg_Bmaintenance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_Bmaintenance = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });
    }

    public void getid() {
        next = (TextView) findViewById(R.id.next);
        previous = (TextView) findViewById(R.id.tv_previous);

        et_deed = (EditText) findViewById(R.id.et_deed);
        et_map = (EditText) findViewById(R.id.et_map);
        et_survey = (EditText) findViewById(R.id.et_survey);
        et_nofloor = (EditText) findViewById(R.id.et_nofloor);
        et_floorflat = (EditText) findViewById(R.id.et_floorflat);
        et_typeunit = (EditText) findViewById(R.id.et_typeunit);
        et_height = (EditText) findViewById(R.id.et_height);

        rg_status = (RadioGroup) findViewById(R.id.rg_consstatus);
        rg_buildarea = (RadioGroup) findViewById(R.id.rg_buildArea);
        rg_Btype = (RadioGroup) findViewById(R.id.rg_Btype);
        rg_internal = (RadioGroup) findViewById(R.id.rg_internal);
        rg_external = (RadioGroup) findViewById(R.id.rg_external);
        rg_Bmaintenance = (RadioGroup) findViewById(R.id.rg_bMaintenence);
        rg_Idecorator = (RadioGroup) findViewById(R.id.rg_Idecorator);
        rg_kitchen = (RadioGroup) findViewById(R.id.rg_kitchen);
        rg_class = (RadioGroup) findViewById(R.id.rg_class);
        rg_Sclass = (RadioGroup) findViewById(R.id.rg_cSanitary);


        rb_status1 = (RadioButton) findViewById(R.id.rb_status1);
        rb_status2 = (RadioButton) findViewById(R.id.rb_status2);
        rb_status3 = (RadioButton) findViewById(R.id.rb_status3);

        rb_build1 = (RadioButton) findViewById(R.id.rb_build1);
        rb_build2 = (RadioButton) findViewById(R.id.rb_build2);
        rb_build3 = (RadioButton) findViewById(R.id.rb_build3);
        rb_build4 = (RadioButton) findViewById(R.id.rb_build4);

        rb_Btype1 = (RadioButton) findViewById(R.id.rb_Btype1);
        rb_Btype2 = (RadioButton) findViewById(R.id.rb_Btype2);
        rb_Btype3 = (RadioButton) findViewById(R.id.rb_Btype3);
        rb_Btype4 = (RadioButton) findViewById(R.id.rb_Btype4);
        rb_Btype5 = (RadioButton) findViewById(R.id.rb_Btype5);

        rb_internal1 = (RadioButton) findViewById(R.id.rb_internal1);
        rb_internal2 = (RadioButton) findViewById(R.id.rb_internal2);
        rb_internal3 = (RadioButton) findViewById(R.id.rb_internal3);
        rb_internal4 = (RadioButton) findViewById(R.id.rb_internal4);
        rb_internal5 = (RadioButton) findViewById(R.id.rb_internal5);
        rb_internal6 = (RadioButton) findViewById(R.id.rb_internal6);
        rb_internal7 = (RadioButton) findViewById(R.id.rb_internal7);
        rb_internal8 = (RadioButton) findViewById(R.id.rb_internal8);
        rb_internal9 = (RadioButton) findViewById(R.id.rb_internal9);

        rb_external1 = (RadioButton) findViewById(R.id.rb_external1);
        rb_external2 = (RadioButton) findViewById(R.id.rb_external2);
        rb_external3 = (RadioButton) findViewById(R.id.rb_external3);
        rb_external4 = (RadioButton) findViewById(R.id.rb_external4);
        rb_external5 = (RadioButton) findViewById(R.id.rb_external5);
        rb_external6 = (RadioButton) findViewById(R.id.rb_external6);
        rb_external7 = (RadioButton) findViewById(R.id.rb_external7);
        rb_external8 = (RadioButton) findViewById(R.id.rb_external8);

        rb_maintenance1 = (RadioButton) findViewById(R.id.rb_maintenence1);
        rb_maintenance2 = (RadioButton) findViewById(R.id.rb_maintenence2);
        rb_maintenance3 = (RadioButton) findViewById(R.id.rb_maintenence3);
        rb_maintenance4 = (RadioButton) findViewById(R.id.rb_maintenence4);

        rb_interiorD1 = (RadioButton) findViewById(R.id.rb_Idecorator1);
        rb_interiorD2 = (RadioButton) findViewById(R.id.rb_Idecorator2);
        rb_interiorD3 = (RadioButton) findViewById(R.id.rb_Idecorator3);
        rb_interiorD4 = (RadioButton) findViewById(R.id.rb_Idecorator4);
        rb_interiorD5 = (RadioButton) findViewById(R.id.rb_Idecorator5);
        rb_interiorD6 = (RadioButton) findViewById(R.id.rb_Idecorator6);
        rb_interiorD7 = (RadioButton) findViewById(R.id.rb_Idecorator7);
        rb_interiorD8 = (RadioButton) findViewById(R.id.rb_Idecorator8);
        rb_interiorD9 = (RadioButton) findViewById(R.id.rb_Idecorator9);

        rb_kitchen1 = (RadioButton) findViewById(R.id.rb_kitchen1);
        rb_kitchen2 = (RadioButton) findViewById(R.id.rb_kitchen2);
        rb_kitchen3 = (RadioButton) findViewById(R.id.rb_kitchen3);
        rb_kitchen4 = (RadioButton) findViewById(R.id.rb_kitchen4);
        rb_kitchen5 = (RadioButton) findViewById(R.id.rb_kitchen5);
        rb_kitchen6 = (RadioButton) findViewById(R.id.rb_kitchen6);

        rb_Eclas1 = (RadioButton) findViewById(R.id.rb_class1);
        rb_Eclas2 = (RadioButton) findViewById(R.id.rb_class2);
        rb_Eclas3 = (RadioButton) findViewById(R.id.rb_class3);
        rb_Eclas4 = (RadioButton) findViewById(R.id.rb_class4);
        rb_Eclas5 = (RadioButton) findViewById(R.id.rb_class5);
        rb_Eclas6 = (RadioButton) findViewById(R.id.rb_class6);
        rb_Eclas7 = (RadioButton) findViewById(R.id.rb_class7);
        rb_Eclas8 = (RadioButton) findViewById(R.id.rb_class8);

        rb_cSanitart1 = (RadioButton) findViewById(R.id.rb_cSanitart1);
        rb_cSanitart2 = (RadioButton) findViewById(R.id.rb_cSanitart2);
        rb_cSanitart3 = (RadioButton) findViewById(R.id.rb_cSanitart3);
        rb_cSanitart4 = (RadioButton) findViewById(R.id.rb_cSanitart4);
        rb_cSanitart5 = (RadioButton) findViewById(R.id.rb_cSanitart5);
        rb_cSanitart6 = (RadioButton) findViewById(R.id.rb_cSanitart6);
        rb_cSanitart7 = (RadioButton) findViewById(R.id.rb_cSanitart7);
        rb_cSanitart8 = (RadioButton) findViewById(R.id.rb_cSanitart8);

        cb_make1 = (CheckBox) findViewById(R.id.cb_make1);
        cb_make2 = (CheckBox) findViewById(R.id.cb_make2);
        cb_make3 = (CheckBox) findViewById(R.id.cb_make3);
        cb_make4 = (CheckBox) findViewById(R.id.cb_make4);
        cb_make5 = (CheckBox) findViewById(R.id.cb_make5);

        cb_finish1 = (CheckBox) findViewById(R.id.cb_finish1);
        cb_finish2 = (CheckBox) findViewById(R.id.cb_finish2);
        cb_finish3 = (CheckBox) findViewById(R.id.cb_finish3);
        cb_finish4 = (CheckBox) findViewById(R.id.cb_finish4);
        cb_finish5 = (CheckBox) findViewById(R.id.cb_finish5);

        cb_Ifinishing1=(CheckBox)findViewById(R.id.cb_Ifinishing1);
        cb_Ifinishing2=(CheckBox)findViewById(R.id.cb_Ifinishing2);
        cb_Ifinishing3=(CheckBox)findViewById(R.id.cb_Ifinishing3);
        cb_Ifinishing4=(CheckBox)findViewById(R.id.cb_Ifinishing4);
        cb_Ifinishing5=(CheckBox)findViewById(R.id.cb_Ifinishing5);
        cb_Ifinishing6=(CheckBox)findViewById(R.id.cb_Ifinishing6);
        cb_Ifinishing7=(CheckBox)findViewById(R.id.cb_Ifinishing7);

        cb_Efinishing1=(CheckBox)findViewById(R.id.cb_Efinishing1);
        cb_Efinishing2=(CheckBox)findViewById(R.id.cb_Efinishing2);
        cb_Efinishing3=(CheckBox)findViewById(R.id.cb_Efinishing3);
        cb_Efinishing4=(CheckBox)findViewById(R.id.cb_Efinishing4);
        cb_Efinishing5=(CheckBox)findViewById(R.id.cb_Efinishing5);
        cb_Efinishing6=(CheckBox)findViewById(R.id.cb_Efinishing6);
        cb_Efinishing7=(CheckBox)findViewById(R.id.cb_Efinishing7);
        cb_Efinishing8=(CheckBox)findViewById(R.id.cb_Efinishing8);
        cb_Efinishing9=(CheckBox)findViewById(R.id.cb_Efinishing9);
        cb_Efinishing10=(CheckBox)findViewById(R.id.cb_Efinishing10);
        cb_Efinishing11=(CheckBox)findViewById(R.id.cb_Efinishing11);

        cb_flooring1=(CheckBox)findViewById(R.id.cb_flooring1);
        cb_flooring2=(CheckBox)findViewById(R.id.cb_flooring2);
        cb_flooring3=(CheckBox)findViewById(R.id.cb_flooring3);
        cb_flooring4=(CheckBox)findViewById(R.id.cb_flooring4);
        cb_flooring5=(CheckBox)findViewById(R.id.cb_flooring5);
        cb_flooring6=(CheckBox)findViewById(R.id.cb_flooring6);
        cb_flooring7=(CheckBox)findViewById(R.id.cb_flooring7);
        cb_flooring8=(CheckBox)findViewById(R.id.cb_flooring8);
        cb_flooring9=(CheckBox)findViewById(R.id.cb_flooring9);
        cb_flooring10=(CheckBox)findViewById(R.id.cb_flooring10);
        cb_flooring11=(CheckBox)findViewById(R.id.cb_flooring11);
        cb_flooring12=(CheckBox)findViewById(R.id.cb_flooring12);
        cb_flooring13=(CheckBox)findViewById(R.id.cb_flooring13);
        cb_flooring14=(CheckBox)findViewById(R.id.cb_flooring14);
        cb_flooring15=(CheckBox)findViewById(R.id.cb_flooring15);
        cb_flooring16=(CheckBox)findViewById(R.id.cb_flooring16);
        cb_flooring17=(CheckBox)findViewById(R.id.cb_flooring17);
        cb_flooring18=(CheckBox)findViewById(R.id.cb_flooring18);
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
        if (et_deed.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter title deed", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_map.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter map area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();

        } else if (et_survey.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter site survey", Snackbar.LENGTH_SHORT);
            mySnackbar.show();

        } else if (rg_status.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select construction status", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_buildarea.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select build up area", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_Btype.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select building type", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (et_height.getText().toString().equals("")) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter height", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_internal.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select internal appearance", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_external.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select external appearance", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_Bmaintenance.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select building maintenance", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_Idecorator.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select interior decoration", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_kitchen.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select kitchen type", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_class.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select class of electrical fitting", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (rg_Sclass.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select class sanitory", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (!cb_make1.isChecked() && !cb_make2.isChecked() && !cb_make3.isChecked() && !cb_make4.isChecked() && !cb_make5.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select roof make", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else if (!cb_finish1.isChecked() && !cb_finish2.isChecked() && !cb_finish3.isChecked() && !cb_finish4.isChecked() && !cb_finish5.isChecked()) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select roof finishing", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else {
            putintoHashMap();
            intent = new Intent(Other4.this, Other5.class);
            startActivity(intent);
        }
    }

    public void putintoHashMap() {
        hm.put("selected_status", selected_status);
        hm.put("selected_buildarea", selected_buildarea);
        hm.put("deed", et_deed.getText().toString());
        hm.put("mapp", et_map.getText().toString());
        hm.put("heightt", et_height.getText().toString());
        hm.put("survey", et_survey.getText().toString());
        hm.put("totalflats", et_nofloor.getText().toString());
        hm.put("floor", et_floorflat.getText().toString());
        hm.put("unittype", et_typeunit.getText().toString());
        hm.put("selected_internal", selected_internal);
        hm.put("selected_external", selected_external);
        hm.put("selected_Bmaintenance", selected_Bmaintenance);
        hm.put("selected_Idecorator", selected_Idecorator);
        hm.put("selected_kitchen", selected_kitchen);
        hm.put("selected_Sclass", selected_Sclass);
        hm.put("selected_Btype", selected_Btype);
        hm.put("selected_class", selected_class);

        if (cb_make1.isChecked()) {
            cb_make1Check = 1;
            hm.put("cb_make1", String.valueOf(cb_make1Check));
        } else {
            cb_make1Check = 0;
            hm.put("cb_make1", String.valueOf(cb_make1Check));
        }

        if (cb_make2.isChecked()) {
            cb_make2Check = 1;
            hm.put("cb_make2", String.valueOf(cb_make2Check));
        } else {
            cb_make1Check = 0;
            hm.put("cb_make2", String.valueOf(cb_make2Check));
        }

        if (cb_make3.isChecked()) {
            cb_make3Check = 1;
            hm.put("cb_make3", String.valueOf(cb_make3Check));
        } else {
            cb_make3Check = 0;
            hm.put("cb_make3", String.valueOf(cb_make3Check));
        }

        if (cb_make4.isChecked()) {
            cb_make4Check = 1;
            hm.put("cb_make4", String.valueOf(cb_make4Check));
        } else {
            cb_make4Check = 0;
            hm.put("cb_make4", String.valueOf(cb_make4Check));
        }

        if (cb_make5.isChecked()) {
            cb_make5Check = 1;
            hm.put("cb_make5", String.valueOf(cb_make5Check));
        } else {
            cb_make5Check = 0;
            hm.put("cb_make5", String.valueOf(cb_make5Check));
        }

        //==========

        if (cb_finish1.isChecked()) {
            cb_finish1Check = 1;
            hm.put("cb_finish1", String.valueOf(cb_finish1Check));
        } else {
            cb_finish1Check = 0;
            hm.put("cb_finish1", String.valueOf(cb_finish1Check));
        }

        if (cb_finish2.isChecked()) {
            cb_finish2Check = 1;
            hm.put("cb_finish2", String.valueOf(cb_finish2Check));
        } else {
            cb_finish2Check = 0;
            hm.put("cb_finish2", String.valueOf(cb_finish2Check));
        }

        if (cb_finish3.isChecked()) {
            cb_finish3Check = 1;
            hm.put("cb_finish3", String.valueOf(cb_finish3Check));
        } else {
            cb_finish3Check = 0;
            hm.put("cb_finish3", String.valueOf(cb_finish3Check));
        }

        if (cb_finish4.isChecked()) {
            cb_finish4Check = 1;
            hm.put("cb_finish4", String.valueOf(cb_finish4Check));
        } else {
            cb_finish4Check = 0;
            hm.put("cb_finish4", String.valueOf(cb_finish4Check));
        }

        if (cb_finish5.isChecked()) {
            cb_finish5Check = 1;
            hm.put("cb_finish5", String.valueOf(cb_finish5Check));
        } else {
            cb_finish5Check = 0;
            hm.put("cb_finish5", String.valueOf(cb_finish5Check));
        }

        //======================

        if (cb_Ifinishing1.isChecked()) {
            cb_Ifinishing1Check = 1;
            hm.put("cb_Ifinishing1", String.valueOf(cb_Ifinishing1Check));
        } else {
            cb_Ifinishing1Check = 0;
            hm.put("cb_Ifinishing1", String.valueOf(cb_Ifinishing1Check));
        }
        if (cb_Ifinishing2.isChecked()) {
            cb_Ifinishing2Check = 1;
            hm.put("cb_Ifinishing2", String.valueOf(cb_Ifinishing2Check));
        } else {
            cb_Ifinishing2Check = 0;
            hm.put("cb_Ifinishing2", String.valueOf(cb_Ifinishing2Check));
        }

        if (cb_Ifinishing3.isChecked()) {
            cb_Ifinishing3Check = 1;
            hm.put("cb_Ifinishing3", String.valueOf(cb_Ifinishing3Check));
        } else {
            cb_Ifinishing3Check = 0;
            hm.put("cb_Ifinishing3", String.valueOf(cb_Ifinishing3Check));
        }

        if (cb_Ifinishing4.isChecked()) {
            cb_Ifinishing4Check = 1;
            hm.put("cb_Ifinishing4", String.valueOf(cb_Ifinishing4Check));
        } else {
            cb_Ifinishing4Check = 0;
            hm.put("cb_Ifinishing4", String.valueOf(cb_Ifinishing4Check));
        }

        if (cb_Ifinishing5.isChecked()) {
            cb_Ifinishing5Check = 1;
            hm.put("cb_Ifinishing5", String.valueOf(cb_Ifinishing5Check));
        } else {
            cb_Ifinishing5Check = 0;
            hm.put("cb_Ifinishing5", String.valueOf(cb_Ifinishing5Check));
        }

        if (cb_Ifinishing6.isChecked()) {
            cb_Ifinishing6Check = 1;
            hm.put("cb_Ifinishing6", String.valueOf(cb_Ifinishing6Check));
        } else {
            cb_Ifinishing6Check = 0;
            hm.put("cb_Ifinishing6", String.valueOf(cb_Ifinishing6Check));
        }

        if (cb_Ifinishing7.isChecked()) {
            cb_Ifinishing7Check = 1;
            hm.put("cb_Ifinishing7", String.valueOf(cb_Ifinishing7Check));
        } else {
            cb_Ifinishing7Check = 0;
            hm.put("cb_Ifinishing7", String.valueOf(cb_Ifinishing7Check));
        }

        //===============cb_Efinishing1

        if(cb_Efinishing1.isChecked()){
            cb_Efinishing1Check=1;
            hm.put("cb_Efinishing1", String.valueOf(cb_Efinishing1Check));
        }
        else {
            cb_Efinishing1Check=0;
            hm.put("cb_Efinishing1", String.valueOf(cb_Efinishing1Check));
        }


        if(cb_Efinishing2.isChecked()){
            cb_Efinishing2Check=1;
            hm.put("cb_Efinishing2", String.valueOf(cb_Efinishing2Check));
        }
        else {
            cb_Efinishing2Check=0;
            hm.put("cb_Efinishing2", String.valueOf(cb_Efinishing2Check));
        }


        if(cb_Efinishing3.isChecked()){
            cb_Efinishing3Check=1;
            hm.put("cb_Efinishing3", String.valueOf(cb_Efinishing3Check));
        }
        else {
            cb_Efinishing3Check=0;
            hm.put("cb_Efinishing3", String.valueOf(cb_Efinishing3Check));
        }


        if(cb_Efinishing4.isChecked()){
            cb_Efinishing4Check=1;
            hm.put("cb_Efinishing4", String.valueOf(cb_Efinishing4Check));
        }
        else {
            cb_Efinishing4Check=0;
            hm.put("cb_Efinishing4", String.valueOf(cb_Efinishing4Check));
        }


        if(cb_Efinishing5.isChecked()){
            cb_Efinishing5Check=1;
            hm.put("cb_Efinishing5", String.valueOf(cb_Efinishing5Check));
        }
        else {
            cb_Efinishing5Check=0;
            hm.put("cb_Efinishing5", String.valueOf(cb_Efinishing5Check));
        }


        if(cb_Efinishing6.isChecked()){
            cb_Efinishing6Check=1;
            hm.put("cb_Efinishing6", String.valueOf(cb_Efinishing6Check));
        }
        else {
            cb_Efinishing6Check=0;
            hm.put("cb_Efinishing6", String.valueOf(cb_Efinishing6Check));
        }


        if(cb_Efinishing7.isChecked()){
            cb_Efinishing7Check=1;
            hm.put("cb_Efinishing7", String.valueOf(cb_Efinishing7Check));
        }
        else {
            cb_Efinishing7Check=0;
            hm.put("cb_Efinishing7", String.valueOf(cb_Efinishing7Check));
        }


        if(cb_Efinishing8.isChecked()){
            cb_Efinishing8Check=1;
            hm.put("cb_Efinishing8", String.valueOf(cb_Efinishing8Check));
        }
        else {
            cb_Efinishing8Check=0;
            hm.put("cb_Efinishing8", String.valueOf(cb_Efinishing8Check));
        }


        if(cb_Efinishing9.isChecked()){
            cb_Efinishing9Check=1;
            hm.put("cb_Efinishing9", String.valueOf(cb_Efinishing9Check));
        }
        else {
            cb_Efinishing9Check=0;
            hm.put("cb_Efinishing9", String.valueOf(cb_Efinishing9Check));
        }


        if(cb_Efinishing10.isChecked()){
            cb_Efinishing10Check=1;
            hm.put("cb_Efinishing10", String.valueOf(cb_Efinishing10Check));
        }
        else {
            cb_Efinishing10Check=0;
            hm.put("cb_Efinishing10", String.valueOf(cb_Efinishing10Check));
        }


        if(cb_Efinishing11.isChecked()){
            cb_Efinishing11Check=1;
            hm.put("cb_Efinishing11", String.valueOf(cb_Efinishing11Check));
        }
        else {
            cb_Efinishing11Check=0;
            hm.put("cb_Efinishing11", String.valueOf(cb_Efinishing11Check));
        }

        //=========================
        if(cb_flooring1.isChecked()){
            cb_flooring1Check=1;
            hm.put("cb_flooring1", String.valueOf(cb_flooring1Check));
        }
        else {
            cb_flooring1Check=0;
            hm.put("cb_flooring1", String.valueOf(cb_flooring1Check));
        }

        if(cb_flooring2.isChecked()){
            cb_flooring2Check=1;
            hm.put("cb_flooring2", String.valueOf(cb_flooring2Check));
        }
        else {
            cb_flooring2Check=0;
            hm.put("cb_flooring2", String.valueOf(cb_flooring2Check));
        }

        if(cb_flooring3.isChecked()){
            cb_flooring3Check=1;
            hm.put("cb_flooring3", String.valueOf(cb_flooring3Check));
        }
        else {
            cb_flooring3Check=0;
            hm.put("cb_flooring3", String.valueOf(cb_flooring3Check));
        }
        if(cb_flooring4.isChecked()){
            cb_flooring4Check=1;
            hm.put("cb_flooring4", String.valueOf(cb_flooring4Check));
        }
        else {
            cb_flooring4Check=0;
            hm.put("cb_flooring4", String.valueOf(cb_flooring4Check));
        }

        if(cb_flooring5.isChecked()){
            cb_flooring5Check=1;
            hm.put("cb_flooring5", String.valueOf(cb_flooring5Check));
        }
        else {
            cb_flooring5Check=0;
            hm.put("cb_flooring5", String.valueOf(cb_flooring5Check));
        }

        if(cb_flooring6.isChecked()){
            cb_flooring6Check=1;
            hm.put("cb_flooring6", String.valueOf(cb_flooring6Check));
        }
        else {
            cb_flooring6Check=0;
            hm.put("cb_flooring6", String.valueOf(cb_flooring6Check));
        }

        if(cb_flooring7.isChecked()){
            cb_flooring7Check=1;
            hm.put("cb_flooring7", String.valueOf(cb_flooring7Check));
        }
        else {
            cb_flooring7Check=0;
            hm.put("cb_flooring7", String.valueOf(cb_flooring7Check));
        }

        if(cb_flooring8.isChecked()){
            cb_flooring8Check=1;
            hm.put("cb_flooring8", String.valueOf(cb_flooring8Check));
        }
        else {
            cb_flooring8Check=0;
            hm.put("cb_flooring8", String.valueOf(cb_flooring8Check));
        }

        if(cb_flooring9.isChecked()){
            cb_flooring9Check=1;
            hm.put("cb_flooring9", String.valueOf(cb_flooring9Check));
        }
        else {
            cb_flooring9Check=0;
            hm.put("cb_flooring9", String.valueOf(cb_flooring9Check));
        }

        if(cb_flooring10.isChecked()){
            cb_flooring10Check=1;
            hm.put("cb_flooring10", String.valueOf(cb_flooring10Check));
        }
        else {
            cb_flooring10Check=0;
            hm.put("cb_flooring10", String.valueOf(cb_flooring10Check));
        }


        if(cb_flooring11.isChecked()){
            cb_flooring11Check=1;
            hm.put("cb_flooring11", String.valueOf(cb_flooring11Check));
        }
        else {
            cb_flooring11Check=0;
            hm.put("cb_flooring11", String.valueOf(cb_flooring11Check));
        }

        if(cb_flooring12.isChecked()){
            cb_flooring12Check=1;
            hm.put("cb_flooring12", String.valueOf(cb_flooring12Check));
        }
        else {
            cb_flooring12Check=0;
            hm.put("cb_flooring12", String.valueOf(cb_flooring12Check));
        }

        if(cb_flooring13.isChecked()){
            cb_flooring13Check=1;
            hm.put("cb_flooring13", String.valueOf(cb_flooring13Check));
        }
        else {
            cb_flooring13Check=0;
            hm.put("cb_flooring13", String.valueOf(cb_flooring13Check));
        }

        if(cb_flooring14.isChecked()){
            cb_flooring14Check=1;
            hm.put("cb_flooring14", String.valueOf(cb_flooring14Check));
        }
        else {
            cb_flooring14Check=0;
            hm.put("cb_flooring14", String.valueOf(cb_flooring14Check));
        }


        if(cb_flooring15.isChecked()){
            cb_flooring15Check=1;
            hm.put("cb_flooring15", String.valueOf(cb_flooring15Check));
        }
        else {
            cb_flooring15Check=0;
            hm.put("cb_flooring15", String.valueOf(cb_flooring15Check));
        }

        if(cb_flooring16.isChecked()){
            cb_flooring16Check=1;
            hm.put("cb_flooring16", String.valueOf(cb_flooring16Check));
        }
        else {
            cb_flooring16Check=0;
            hm.put("cb_flooring16", String.valueOf(cb_flooring16Check));
        }

        if(cb_flooring17.isChecked()){
            cb_flooring17Check=1;
            hm.put("cb_flooring17", String.valueOf(cb_flooring17Check));
        }
        else {
            cb_flooring17Check=0;
            hm.put("cb_flooring17", String.valueOf(cb_flooring17Check));
        }

        if(cb_flooring18.isChecked()){
            cb_flooring18Check=1;
            hm.put("cb_flooring18", String.valueOf(cb_flooring18Check));
        }
        else {
            cb_flooring18Check=0;
            hm.put("cb_flooring18", String.valueOf(cb_flooring18Check));
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

                et_deed.setText(object.getString("deed"));
                et_deed.setText(object.getString("mapp"));
                et_survey.setText(object.getString("survey"));
                et_nofloor.setText(object.getString("totalflats"));
                et_floorflat.setText(object.getString("floor"));
                et_typeunit.setText(object.getString("unittype"));
                et_height.setText(object.getString("heightt"));
                et_map.setText(object.getString("mapp"));


                //======================

                if(object.getString("cb_flooring1").equals("1")){
                    cb_flooring1.setChecked(true);
                }
                else{
                    cb_flooring1.setChecked(false);
                }


                if(object.getString("cb_flooring2").equals("1")){
                    cb_flooring2.setChecked(true);
                }
                else{
                    cb_flooring2.setChecked(false);
                }

                if(object.getString("cb_flooring3").equals("1")){
                    cb_flooring3.setChecked(true);
                }
                else{
                    cb_flooring3.setChecked(false);
                }

                if(object.getString("cb_flooring4").equals("1")){
                    cb_flooring4.setChecked(true);
                }
                else{
                    cb_flooring4.setChecked(false);
                }

                if(object.getString("cb_flooring5").equals("1")){
                    cb_flooring5.setChecked(true);
                }
                else{
                    cb_flooring5.setChecked(false);
                }

                if(object.getString("cb_flooring6").equals("1")){
                    cb_flooring6.setChecked(true);
                }
                else{
                    cb_flooring6.setChecked(false);
                }

                if(object.getString("cb_flooring7").equals("1")){
                    cb_flooring7.setChecked(true);
                }
                else{
                    cb_flooring7.setChecked(false);
                }

                if(object.getString("cb_flooring8").equals("1")){
                    cb_flooring8.setChecked(true);
                }
                else{
                    cb_flooring8.setChecked(false);
                }

                if(object.getString("cb_flooring9").equals("1")){
                    cb_flooring9.setChecked(true);
                }
                else{
                    cb_flooring9.setChecked(false);
                }

                if(object.getString("cb_flooring10").equals("1")){
                    cb_flooring10.setChecked(true);
                }
                else{
                    cb_flooring10.setChecked(false);
                }
                if(object.getString("cb_flooring11").equals("1")){
                    cb_flooring11.setChecked(true);
                }
                else{
                    cb_flooring11.setChecked(false);
                }

                if(object.getString("cb_flooring12").equals("1")){
                    cb_flooring12.setChecked(true);
                }
                else{
                    cb_flooring12.setChecked(false);
                }

                if(object.getString("cb_flooring13").equals("1")){
                    cb_flooring13.setChecked(true);
                }
                else{
                    cb_flooring13.setChecked(false);
                }
                if(object.getString("cb_flooring14").equals("1")){
                    cb_flooring14.setChecked(true);
                }
                else{
                    cb_flooring14.setChecked(false);
                }

                if(object.getString("cb_flooring15").equals("1")){
                    cb_flooring15.setChecked(true);
                }
                else{
                    cb_flooring15.setChecked(false);
                }
                if(object.getString("cb_flooring16").equals("1")){
                    cb_flooring16.setChecked(true);
                }
                else{
                    cb_flooring16.setChecked(false);
                }
                if(object.getString("cb_flooring17").equals("1")){
                    cb_flooring17.setChecked(true);
                }
                else{
                    cb_flooring17.setChecked(false);
                }
                if(object.getString("cb_flooring18").equals("1")){
                    cb_flooring18.setChecked(true);
                }
                else{
                    cb_flooring18.setChecked(false);
                }



                if (object.getString("selected_status").equals("Built-up property in use")) {
                    rb_status1.setChecked(true);
                } else if (object.getString("selected_status").equals("Under construction")) {
                    rb_status2.setChecked(true);

                } else if (object.getString("selected_status").equals("No construction")) {
                    rb_status3.setChecked(true);

                } else {

                }

                if (object.getString("selected_Btype").equals("RCC Framed Structure")) {
                    rb_Btype1.setChecked(true);
                } else if (object.getString("selected_Btype").equals("Load bearing Pillar Beam column")) {
                    rb_Btype2.setChecked(true);

                } else if (object.getString("selected_Btype").equals("Ordinary brick wall structure")) {
                    rb_Btype3.setChecked(true);

                } else if (object.getString("selected_Btype").equals("Iron trusses &amp; Pillars")) {
                    rb_Btype4.setChecked(true);

                } else if (object.getString("selected_Btype").equals("Scrap abandoned structure")) {
                    rb_Btype5.setChecked(true);

                } else {

                }

                if (object.getString("selected_buildarea").equals("Covered Area")) {
                    rb_build1.setChecked(true);
                } else if (object.getString("selected_buildarea").equals("Floor Area")) {
                    rb_build2.setChecked(true);

                } else if (object.getString("selected_buildarea").equals("Super Area")) {
                    rb_build3.setChecked(true);

                } else if (object.getString("selected_buildarea").equals("Carpet Area")) {
                    rb_build4.setChecked(true);

                } else {

                }


                if (object.getString("selected_internal").equals("Excellent")) {
                    rb_internal1.setChecked(true);
                } else if (object.getString("selected_internal").equals("Very Good")) {
                    rb_internal2.setChecked(true);

                } else if (object.getString("selected_internal").equals("Good")) {
                    rb_internal3.setChecked(true);

                } else if (object.getString("selected_internal").equals("Ordinary")) {
                    rb_internal4.setChecked(true);

                } else if (object.getString("selected_internal").equals("Average")) {
                    rb_internal5.setChecked(true);

                } else if (object.getString("selected_internal").equals("Poor")) {
                    rb_internal6.setChecked(true);

                } else if (object.getString("selected_internal").equals("Under construction")) {
                    rb_internal7.setChecked(true);

                } else if (object.getString("selected_internal").equals("No construction")) {
                    rb_internal8.setChecked(true);

                } else if (object.getString("selected_internal").equals("No Survey")) {
                    rb_internal9.setChecked(true);

                } else {

                }


                if (object.getString("selected_external").equals("Excellent")) {
                    rb_external1.setChecked(true);
                } else if (object.getString("selected_external").equals("Very Good")) {
                    rb_external2.setChecked(true);
                } else if (object.getString("selected_external").equals("Good")) {
                    rb_external3.setChecked(true);
                } else if (object.getString("selected_external").equals("Ordinary")) {
                    rb_external4.setChecked(true);
                } else if (object.getString("selected_external").equals("Average")) {
                    rb_external5.setChecked(true);
                } else if (object.getString("selected_external").equals("Poor")) {
                    rb_external6.setChecked(true);
                } else if (object.getString("selected_external").equals("Under construction")) {
                    rb_external7.setChecked(true);
                } else if (object.getString("selected_external").equals("No construction")) {
                    rb_external8.setChecked(true);
                } else {
                }


                if (object.getString("selected_Bmaintenance").equals("Very Good")) {
                    rb_maintenance1.setChecked(true);
                } else if (object.getString("selected_Bmaintenance").equals("Average")) {
                    rb_maintenance2.setChecked(true);
                } else if (object.getString("selected_Bmaintenance").equals("Poor")) {
                    rb_maintenance3.setChecked(true);
                } else if (object.getString("selected_Bmaintenance").equals("Under construction")) {
                    rb_maintenance4.setChecked(true);
                } else {
                }

                if (object.getString("selected_Idecorator").equals("Excellent")) {
                    rb_interiorD1.setChecked(true);
                } else if (object.getString("selected_Idecorator").equals("Very Good")) {
                    rb_interiorD2.setChecked(true);
                } else if (object.getString("selected_Idecorator").equals("Good")) {
                    rb_interiorD3.setChecked(true);
                } else if (object.getString("selected_Idecorator").equals("Ordinary")) {
                    rb_interiorD4.setChecked(true);
                } else if (object.getString("selected_Idecorator").equals("Average")) {
                    rb_interiorD5.setChecked(true);
                } else if (object.getString("selected_Idecorator").equals("Poor")) {
                    rb_interiorD6.setChecked(true);
                } else if (object.getString("selected_Idecorator").equals("Under construction")) {
                    rb_interiorD7.setChecked(true);
                } else if (object.getString("selected_Idecorator").equals("No construction")) {
                    rb_interiorD8.setChecked(true);
                } else if (object.getString("selected_Idecorator").equals("No Survey")) {
                    rb_interiorD9.setChecked(true);
                } else {
                }


                if (object.getString("selected_kitchen").equals("Simple with no cupboard")) {
                    rb_kitchen1.setChecked(true);
                } else if (object.getString("selected_kitchen").equals("Ordinary with cupboard")) {
                    rb_kitchen2.setChecked(true);
                } else if (object.getString("selected_kitchen").equals("Normal Modular with chimney")) {
                    rb_kitchen3.setChecked(true);
                } else if (object.getString("selected_kitchen").equals("High end Modular with chimney")) {
                    rb_kitchen4.setChecked(true);
                } else if (object.getString("selected_kitchen").equals("Under construction")) {
                    rb_kitchen5.setChecked(true);
                } else if (object.getString("selected_kitchen").equals("No Survey")) {
                    rb_kitchen6.setChecked(true);
                } else {
                }

                if (object.getString("selected_class").equals("External")) {
                    rb_Eclas1.setChecked(true);
                } else if (object.getString("selected_class").equals("Internal")) {
                    rb_Eclas2.setChecked(true);
                } else if (object.getString("selected_class").equals("Ordinary fixtures &amp; fittings")) {
                    rb_Eclas3.setChecked(true);
                } else if (object.getString("selected_class").equals("Fancy lights")) {
                    rb_Eclas4.setChecked(true);
                } else if (object.getString("selected_class").equals("Chandeliers")) {
                    rb_Eclas5.setChecked(true);
                } else if (object.getString("selected_class").equals("Concealed lightning")) {
                    rb_Eclas6.setChecked(true);
                } else if (object.getString("selected_class").equals("Under construction")) {
                    rb_Eclas6.setChecked(true);
                } else if (object.getString("selected_class").equals("No Survey")) {
                    rb_Eclas6.setChecked(true);
                } else {
                }

                if (object.getString("selected_Sclass").equals("External")) {
                    rb_cSanitart1.setChecked(true);
                } else if (object.getString("selected_Sclass").equals("Internal")) {
                    rb_cSanitart2.setChecked(true);
                } else if (object.getString("selected_Sclass").equals("Excellent")) {
                    rb_cSanitart3.setChecked(true);
                } else if (object.getString("selected_Sclass").equals("Very Good")) {
                    rb_cSanitart4.setChecked(true);
                } else if (object.getString("selected_Sclass").equals("Good")) {
                    rb_cSanitart5.setChecked(true);
                } else if (object.getString("selected_Sclass").equals("Simple")) {
                    rb_cSanitart6.setChecked(true);
                } else if (object.getString("selected_Sclass").equals("Under construction")) {
                    rb_cSanitart7.setChecked(true);
                } else if (object.getString("selected_Sclass").equals("No Survey")) {
                    rb_cSanitart8.setChecked(true);
                } else {
                }


                if (object.getString("cb_make1").equals("1")) {
                    cb_make1.setChecked(true);
                } else {
                    cb_make1.setChecked(false);
                }

                if (object.getString("cb_make2").equals("1")) {
                    cb_make2.setChecked(true);
                } else {
                    cb_make2.setChecked(false);
                }

                if (object.getString("cb_make3").equals("1")) {
                    cb_make3.setChecked(true);
                } else {
                    cb_make3.setChecked(false);
                }

                if (object.getString("cb_make4").equals("1")) {
                    cb_make4.setChecked(true);
                } else {
                    cb_make4.setChecked(false);
                }

                if (object.getString("cb_make5").equals("1")) {
                    cb_make5.setChecked(true);
                } else {
                    cb_make5.setChecked(false);
                }

                //===========

                if (object.getString("cb_finish1").equals("1")) {
                    cb_finish1.setChecked(true);
                } else {
                    cb_finish1.setChecked(false);
                }

                if (object.getString("cb_finish2").equals("1")) {
                    cb_finish2.setChecked(true);
                } else {
                    cb_finish2.setChecked(false);
                }

                if (object.getString("cb_finish3").equals("1")) {
                    cb_finish3.setChecked(true);
                } else {
                    cb_finish3.setChecked(false);
                }

                if (object.getString("cb_finish4").equals("1")) {
                    cb_finish4.setChecked(true);
                } else {
                    cb_finish4.setChecked(false);
                }

                if (object.getString("cb_finish5").equals("1")) {
                    cb_finish5.setChecked(true);
                } else {
                    cb_finish5.setChecked(false);
                }

//==============
                if(object.getString("cb_Ifinishing1").equals("1")){
                    cb_Ifinishing1.setChecked(true);
                }
                else{
                    cb_Ifinishing1.setChecked(false);
                }

                if(object.getString("cb_Ifinishing2").equals("1")){
                    cb_Ifinishing2.setChecked(true);
                }
                else{
                    cb_Ifinishing2.setChecked(false);
                }

                if(object.getString("cb_Ifinishing3").equals("1")){
                    cb_Ifinishing3.setChecked(true);
                }
                else{
                    cb_Ifinishing3.setChecked(false);
                }

                if(object.getString("cb_Ifinishing4").equals("1")){
                    cb_Ifinishing4.setChecked(true);
                }
                else{
                    cb_Ifinishing4.setChecked(false);
                }

                if(object.getString("cb_Ifinishing5").equals("1")){
                    cb_Ifinishing5.setChecked(true);
                }
                else{
                    cb_Ifinishing5.setChecked(false);
                }

                if(object.getString("cb_Ifinishing5").equals("1")){
                    cb_Ifinishing6.setChecked(true);
                }
                else{
                    cb_Ifinishing6.setChecked(false);
                }

                if(object.getString("cb_Ifinishing7").equals("1")){
                    cb_Ifinishing7.setChecked(true);
                }
                else{
                    cb_Ifinishing7.setChecked(false);
                }

                //==================cb_Efinishing1
                if(object.getString("cb_Efinishing1").equals("1")){
                    cb_Efinishing1.setChecked(true);
                }
                else{
                    cb_Efinishing1.setChecked(false);
                }

                if(object.getString("cb_Efinishing2").equals("1")){
                    cb_Efinishing2.setChecked(true);
                }
                else{
                    cb_Efinishing2.setChecked(false);
                }

                if(object.getString("cb_Efinishing3").equals("1")){
                    cb_Efinishing3.setChecked(true);
                }
                else{
                    cb_Efinishing3.setChecked(false);
                }


                if(object.getString("cb_Efinishing4").equals("1")){
                    cb_Efinishing4.setChecked(true);
                }
                else{
                    cb_Efinishing4.setChecked(false);
                }

                if(object.getString("cb_Efinishing5").equals("1")){
                    cb_Efinishing5.setChecked(true);
                }
                else{
                    cb_Efinishing5.setChecked(false);
                }

                if(object.getString("cb_Efinishing6").equals("1")){
                    cb_Efinishing6.setChecked(true);
                }
                else{
                    cb_Efinishing6.setChecked(false);
                }

                if(object.getString("cb_Efinishing7").equals("1")){
                    cb_Efinishing7.setChecked(true);
                }
                else{
                    cb_Efinishing7.setChecked(false);
                }

                if(object.getString("cb_Efinishing8").equals("1")){
                    cb_Efinishing8.setChecked(true);
                }
                else{
                    cb_Efinishing8.setChecked(false);
                }

                if(object.getString("cb_Efinishing9").equals("1")){
                    cb_Efinishing9.setChecked(true);
                }
                else{
                    cb_Efinishing9.setChecked(false);
                }

                if(object.getString("cb_Efinishing10").equals("1")){
                    cb_Efinishing10.setChecked(true);
                }
                else{
                    cb_Efinishing10.setChecked(false);
                }

                if(object.getString("cb_Efinishing11").equals("1")){
                    cb_Efinishing11.setChecked(true);
                }
                else{
                    cb_Efinishing11.setChecked(false);
                }


            }


        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
