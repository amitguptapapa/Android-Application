package com.vis.android.Activities.MultiStory;

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

import static com.vis.android.Activities.MultiStory.MultiStory1.hmap;

public class MultiStory4 extends AppCompatActivity implements View.OnClickListener {
    TextView next;
    Intent intent;
    TextView previous;
    RadioGroup rg_demand;
    RadioButton checkedRadioButton,rb_demand1,rb_demand2,rb_demand3,rb_demand4;
    EditText et_year,et_price,et_minrate,et_maxrate,et_name1,et_contact1,et_sale1,et_Rrate1,et_comment1,et_name2,et_contact2,et_sale2,et_Rrate2,et_comment2,et_name3,et_contact3,et_sale3,et_Rrate3,et_comment3;
    ArrayList<HashMap<String, String>> alistt = new ArrayList<HashMap<String, String>>();
    String selected_demand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_story4);
        getid();
        setListener();
        Log.v("hashmap===4", String.valueOf(hmap));
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rg_demand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    selected_demand = (String) checkedRadioButton.getText();
                    Log.v("*******checkedProperty", String.valueOf(checkedRadioButton.getText()));

                }
            }
        });
    }

    public void getid() {
        next = (TextView) findViewById(R.id.next);
        previous = (TextView) findViewById(R.id.tv_previous);

        rg_demand=(RadioGroup)findViewById(R.id.rg_demand);

        et_year=(EditText)findViewById(R.id.et_year);
        et_price=(EditText)findViewById(R.id.et_price);
        et_minrate=(EditText)findViewById(R.id.et_minrate);
        et_maxrate=(EditText)findViewById(R.id.et_maxrate);
        et_name1=(EditText)findViewById(R.id.et_name1);
        et_contact1=(EditText)findViewById(R.id.et_contact1);
        et_sale1=(EditText)findViewById(R.id.et_sale1);
        et_Rrate1=(EditText)findViewById(R.id.et_Rrate1);
        et_comment1=(EditText)findViewById(R.id.et_comment1);
        et_name2=(EditText)findViewById(R.id.et_name2);
        et_contact2=(EditText)findViewById(R.id.et_contact2);
        et_sale2=(EditText)findViewById(R.id.et_sale2);
        et_Rrate2=(EditText)findViewById(R.id.et_Rrate2);
        et_comment2=(EditText)findViewById(R.id.et_comment2);
        et_name3=(EditText)findViewById(R.id.et_name3);
        et_contact3=(EditText)findViewById(R.id.et_contact3);
        et_sale3=(EditText)findViewById(R.id.et_sale3);
        et_Rrate3=(EditText)findViewById(R.id.et_Rrate3);
        et_comment3=(EditText)findViewById(R.id.et_comment3);

        rb_demand1=(RadioButton)findViewById(R.id.rb_demand1);
        rb_demand2=(RadioButton)findViewById(R.id.rb_demand2);
        rb_demand3=(RadioButton)findViewById(R.id.rb_demand3);
        rb_demand4=(RadioButton)findViewById(R.id.rb_demand4);
    }

    public void setListener() {
        next.setOnClickListener(this);
        previous.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                insertDB();
                intent = new Intent(MultiStory4.this, MultiStory5.class);
                startActivity(intent);
                break;

            case R.id.tv_previous:
                onBackPressed();
                break;
        }
    }
    /*
    et_year,et_price,et_minrate,et_maxrate,et_name1,et_contact1,et_sale1,et_Rrate1,et_comment1,et_name2,et_contact2,et_sale2,et_Rrate2,et_comment2,et_name3,et_contact3,et_sale3,et_Rrate3,et_comment3
     */
    public void checkpass(){
        if (rg_demand.getCheckedRadioButtonId() == -1) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please select Demand &amp; Supply condition", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_year.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter year of purchase", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_price.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter purchase price", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_minrate.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter min rate", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_name1.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_contact1.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information contact", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_sale1.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information sale", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_Rrate1.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information rate", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_comment1.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information comment", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_name2.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_contact2.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information contact", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_sale2.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information sale", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_Rrate2.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information rate", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_comment2.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information comment", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_name3.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information name", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_contact3.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information contact", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_sale3.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information sale", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_Rrate3.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information rate", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else if(et_comment3.getText().toString().equals("")){
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_main),
                    "Please enter local information comment", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
    }

    public void insertDB() {
        hmap.put("selected_demand", selected_demand);
        hmap.put("year", et_year.getText().toString());
        hmap.put("price", et_price.getText().toString());
        hmap.put("min_rate", et_minrate.getText().toString());
        hmap.put("max_rate", et_maxrate.getText().toString());
        hmap.put("name1", et_name1.getText().toString());
        hmap.put("contact1", et_contact1.getText().toString());
        hmap.put("sale1", et_sale1.getText().toString());
        hmap.put("rate1", et_Rrate1.getText().toString());
        hmap.put("comment1", et_comment1.getText().toString());
        hmap.put("name2", et_name2.getText().toString());
        hmap.put("contact2", et_contact2.getText().toString());
        hmap.put("sale2", et_sale2.getText().toString());
        hmap.put("rate2", et_Rrate2.getText().toString());
        hmap.put("comment2", et_comment2.getText().toString());
        hmap.put("name3", et_name3.getText().toString());
        hmap.put("contact3", et_contact3.getText().toString());
        hmap.put("sale3", et_sale3.getText().toString());
        hmap.put("rate3", et_Rrate3.getText().toString());
        hmap.put("comment3", et_comment3.getText().toString());
    }

    public void selectDB() throws JSONException {

        alistt = DatabaseController.getSubCat();

        if (alistt != null) {

            Log.v("getfromdb=====", String.valueOf(alistt));

            JSONArray array = new JSONArray(alistt.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (object.getString("selected_demand").equals("Very Good")) {
                    rb_demand1.setChecked(true);
                } else if (object.getString("selected_demand").equals("Good")) {
                    rb_demand2.setChecked(true);
                }
                else if (object.getString("selected_demand").equals("Average")) {
                    rb_demand3.setChecked(true);
                }
                else if (object.getString("selected_demand").equals("Low")) {
                    rb_demand4.setChecked(true);
                }
                else {

                }

                et_year.setText(object.getString("year"));
                et_price.setText(object.getString("price"));
                et_minrate.setText(object.getString("min_rate"));
                et_maxrate.setText(object.getString("max_rate"));
                et_name1.setText(object.getString("name1"));
                et_contact1.setText(object.getString("contact1"));
                et_sale1.setText(object.getString("sale1"));
                et_Rrate1.setText(object.getString("rate1"));
                et_comment1.setText(object.getString("comment1"));

                et_name2.setText(object.getString("name2"));
                et_contact2.setText(object.getString("contact2"));
                et_sale2.setText(object.getString("sale2"));
                et_Rrate2.setText(object.getString("rate2"));
                et_comment2.setText(object.getString("comment2"));

                et_name3.setText(object.getString("name3"));
                et_contact3.setText(object.getString("contact3"));
                et_sale3.setText(object.getString("sale3"));
                et_Rrate3.setText(object.getString("rate3"));
                et_comment3.setText(object.getString("comment3"));

            }

        }
    }
    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
