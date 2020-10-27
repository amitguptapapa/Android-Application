package com.vis.android.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Notification extends Fragment implements View.OnClickListener {
    View v;

    NotificationAdapter adapter;

    Preferences preferences;

    CustomLoader loader;

    RecyclerView recyclerViewNotification;

    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.notification, container, false);
        getid();
        setListener();

        if (Utils.isNetworkConnectedMainThred(getActivity())){
            hitNotificationApi();
        }else {
            Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {



        }
    }

    public void getid() {

        recyclerViewNotification = v.findViewById(R.id.recyclerViewNotification);
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewNotification.setNestedScrollingEnabled(false);

        preferences = new Preferences(getActivity());
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    public void setListener() {

    }


    private void hitNotificationApi() {

        loader.show();

        String url = Utils.getCompleteApiUrl(getActivity(), R.string.ViewNotification);
        Log.v("hitNotificationApi", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {

            jsonObject.put("surveyor_id", preferences.get(Constants.surveyor_id));
            json_data.put("VIS", jsonObject);

            Log.v("hitNotificationApi", json_data.toString());

        } catch (Exception e) {

            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJson(response);
                    }

                    @Override
                    public void onError(ANError error) {

                        // handle error
                        if (error.getErrorCode() != 0) {
                            loader.cancel();
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            loader.cancel();
                            Toast.makeText(getActivity(), error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJson(JSONObject response) {

        Log.v("hitNotificationApi", response.toString());

        try {
            dataList.clear();

            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {

                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    HashMap hashMap = new HashMap();

                    if (!jsonObject1.getString("case_id").equalsIgnoreCase("")){

                        hashMap.put("id",jsonObject1.getString("id"));
                        hashMap.put("case_id",jsonObject1.getString("case_id"));
                        hashMap.put("remark",jsonObject1.getString("remark"));
                        hashMap.put("case_status",jsonObject1.getString("case_status"));
                        hashMap.put("date_time",jsonObject1.getString("date_time"));

                        dataList.add(hashMap);
                    }
                }

                Collections.reverse(dataList);

                adapter = new NotificationAdapter(dataList);
                recyclerViewNotification.setAdapter(adapter);

            } else {
                Toast.makeText(getActivity(), res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    public class NotificationAdapter extends RecyclerView.Adapter<HolderReferredDocuments> {
        ArrayList<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();

        public NotificationAdapter(ArrayList<HashMap<String, String>> banner) {
            alist = banner;
        }

        public HolderReferredDocuments onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderReferredDocuments(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderReferredDocuments holder, final int position) {

            holder.tvMessage.setText(alist.get(position).get("remark"));
            holder.tvTime.setText(alist.get(position).get("date_time"));


            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    preferences.set(Constants.case_id,alist.get(position).get("case_id"));
                    preferences.commit();


                    ((Dashboard)getActivity()).displayView(1);
                }
            });


//            holder.rlMain.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    preferences.set(Constants.case_id,alist.get(position).get("case_id"));
//                    preferences.commit();
//
//
//                    ((Dashboard)getActivity()).displayView(1);
//                }
//            });

        }

        public int getItemCount() {
            return alist.size();
        }
    }

    private static class HolderReferredDocuments extends RecyclerView.ViewHolder {

        TextView tvMessage,tvTime;
        RelativeLayout rlMain;

        public HolderReferredDocuments(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            rlMain = itemView.findViewById(R.id.rlMain);

        }
    }
}



