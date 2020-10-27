package com.vis.android.Activities.General;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vis.android.R;

/**
 * Created by mohammadshiblee on 10/04/18.
 */

public class SpinnerAdapter extends BaseAdapter {

    Context context;
    String alist[];
    LayoutInflater inflter;

    TextView tvSpinnerText;


    public SpinnerAdapter(Context applicationContext, String alist[]) {
        this.context = applicationContext;
        this.alist = alist;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return alist.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = inflter.inflate(R.layout.spinnerlayout, null);
//        tvSpinnerText = (TextView) convertView.findViewById(R.id.tvList);
//        tvSpinnerText.setText(alist[position]);
        convertView = inflter.inflate(R.layout.profession_adapter, null);
        TextView name = (TextView) convertView.findViewById(R.id.tv_profession);
        try {
            name.setText(alist[position]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;

        //            profession.setSelection(Integer.parseInt(profession_array_list.get(position).get("id")));
    }
}