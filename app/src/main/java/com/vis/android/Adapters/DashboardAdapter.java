package com.vis.android.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ankita-pc on 1/3/18.
 */

public class DashboardAdapter extends BaseAdapter {
    LayoutInflater inflter;
    Context context;
    String part1 = "", part2 = "";
    ArrayList<HashMap<String, String>> alist;
    ImageView image;
    Preferences pref;
    View status_view;
    String a = "";
    String page="";
    TextView survey_completed,Oner_name, asset_type, asset_add, assignd_date, status, schedule_date, state, assigned_date;

    public DashboardAdapter(Context context, ArrayList<HashMap<String, String>> alist, String a) {

        inflter = (LayoutInflater.from(context));
        this.context = context;
        this.alist = alist;
        this.a = a;


    }

    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int i) {
        return alist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.history_adapter, null);

        getid(view);
        setValues(i);

        return view;
    }

    public void getid(View v) {
        pref = new Preferences(context);
        Oner_name = v.findViewById(R.id.tv_onrname);
        asset_type = v.findViewById(R.id.tv_type);
        asset_add = v.findViewById(R.id.tv_aadd);
        assignd_date = v.findViewById(R.id.tv_date);
        status = v.findViewById(R.id.tv_status);
        schedule_date = v.findViewById(R.id.sdate);
        state = v.findViewById(R.id.tv_state);
        status_view = v.findViewById(R.id.view);
        assigned_date = v.findViewById(R.id.asign_date);
        survey_completed = v.findViewById(R.id.survey_completed);
    }

    public void setValues(final int position) {
        //  Log.v("string-----",a);
        try {
            JSONArray array = new JSONArray(a);
            for (int i = 0; i < array.length(); i++) {
                JSONObject data_object = array.getJSONObject(i);
                String page = data_object.getString("page");
                String id_new = data_object.getString("id_new");
                if(id_new.equals(alist.get(position).get("case_id")))
                {
                Log.v("page====",page);
                    survey_completed.setText(Integer.valueOf(page)*10+"%");
                    pref.set(Constants.page_no,page);
                    pref.commit();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Oner_name.setText(alist.get(position).get("customer_name"));
        asset_type.setText(alist.get(position).get("type_of_property"));
        asset_add.setText(alist.get(position).get("asset_address"));
        status.setText(alist.get(position).get("status"));
        assigned_date.setText(alist.get(position).get("assigned_date"));

        if (alist.get(position).get("status").equals("0")) {
            Log.v("status1", alist.get(position).get("status"));
            status_view.setBackgroundColor(Color.YELLOW);
        } else if (alist.get(position).get("status").equals("1")) {
            Log.v("status2", alist.get(position).get("status"));
            status_view.setBackgroundColor(Color.GREEN);
        } else {
            Log.v("status3", alist.get(position).get("status"));
        }
        if (alist.get(position).get("schedule_date").equals("null")) {
            schedule_date.setText("Not scheduled yet");
        } else {
            schedule_date.setText(alist.get(position).get("schedule_date"));
            String currentString = alist.get(position).get("schedule_date");
          /*  try {
            String[] separated = currentString.split(",");
            part1=separated[0];
            part2=separated[1];


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Log.v("part1=====", String.valueOf(sdf));
            Date date1 = null;
            Date date2 = null;

                //date1 = sdf.parse("2018-03-14");
                date1 = sdf.parse(part1);
                date2 = sdf.parse("2018-03-13");


            System.out.println("date1 : " + sdf.format(date1));
            System.out.println("date2 : " + sdf.format(date2));

            if (date1.compareTo(date2) > 0) {
                System.out.println("Date1 is after Date2");
            } else if (date1.compareTo(date2) < 0) {
                System.out.println("Date1 is before Date2");
            } else if (date1.compareTo(date2) == 0) {
                System.out.println("Date1 is equal to Date2");
            } else {
                System.out.println("How to get here?");
            }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }


    }
}
