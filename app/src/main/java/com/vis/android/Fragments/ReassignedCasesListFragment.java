package com.vis.android.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReassignedCasesListFragment extends Fragment {

    RecyclerView recyclerView;

    CustomLoader loader;

    SwipeRefreshLayout pullToRefresh;

    public static ArrayList<HashMap<String, String>> case_array_list = new ArrayList<HashMap<String, String>>();

    Adapter adapter;

    Preferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_skipped_surveys, container, false);

        init(v);

        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            hitReassignCaseList();

        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

        }

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    hitReassignCaseList();

                } else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }

                pullToRefresh.setRefreshing(false);
            }
        });

        return v;
    }

    private void init(View v){
        pref = new Preferences(getActivity());

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        pullToRefresh = v.findViewById(R.id.pullToRefresh);

        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    public void hitReassignCaseList() {
        String url = Utils.getCompleteApiUrl(getActivity(), R.string.ReassignCaseList);

        Log.v("hitReassignCaseList", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("surveyor_id", pref.get(Constants.surveyor_id));
            json_data.put("VIS", jsonObject);

            Log.v("hitReassignCaseList", json_data.toString());

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
                            Log.d("onError errorCode", "onError errorCode : " + error.getErrorCode());
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

    public void parseJson(JSONObject response) {
        Log.v("res:hitReassignCaseList", response.toString());

        case_array_list.clear();

        try {

            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String res_msg = jsonObject.getString("response_message");


            if (res_code.equals("1")) {
                JSONArray array_case_datails = jsonObject.getJSONArray("case_data");

                for (int i = 0; i < array_case_datails.length(); i++) {

                    JSONObject data_object = array_case_datails.getJSONObject(i);

                    HashMap<String, String> hash = new HashMap<String, String>();
                    hash.put("case_id", data_object.get("case_id").toString());
                    hash.put("case_u_id", data_object.get("case_u_id").toString());
                    hash.put("type_of_assets", data_object.get("type_of_assets").toString());
                    hash.put("asset_address", data_object.get("asset_address").toString());
                    hash.put("assigned_date", data_object.get("assigned_date").toString());
                    hash.put("customer_name", data_object.get("customer_name").toString());
                    hash.put("schedule_date", data_object.get("schedule_date").toString());
                    hash.put("reassign_date_time", data_object.get("reassign_date_time").toString());
                    hash.put("reassign_reason", data_object.get("reassign_reason").toString());
                    hash.put("reassign_remark", data_object.get("reassign_remark").toString());

                    case_array_list.add(hash);

                }

                adapter = new Adapter(case_array_list);
                recyclerView.setAdapter(adapter);

            } else {
                Toast.makeText(getActivity(), res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();
            }

            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            loader.cancel();
        }
    }

    public class Adapter extends RecyclerView.Adapter<HolderReferredDocuments> {
        ArrayList<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();

        public Adapter(ArrayList<HashMap<String, String>> banner) {
            alist = banner;
        }

        public HolderReferredDocuments onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HolderReferredDocuments(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adapter, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final HolderReferredDocuments holder, final int position) {

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.rl_main.getLayoutParams();
            params.topMargin = (int) getResources().getDimension(R.dimen._5sdp);

            holder.view.setBackgroundColor(getResources().getColor(R.color.app_header));
            holder.rl_survey.setVisibility(View.GONE);
            holder.rlSkippedDate.setVisibility(View.GONE);
            holder.llReassignDateTime.setVisibility(View.VISIBLE);
            holder.llReassignReason.setVisibility(View.VISIBLE);
            holder.llReassignRemark.setVisibility(View.VISIBLE);

            holder.tv_caseno.setText(alist.get(position).get("case_id"));
            holder.tv_onrname.setText(alist.get(position).get("customer_name"));
            holder.tv_type.setText(alist.get(position).get("type_of_assets"));
            holder.tv_aadd.setText(alist.get(position).get("asset_address"));
            holder.asign_date.setText(alist.get(position).get("assigned_date"));
            holder.sdate.setText(alist.get(position).get("schedule_date"));
            holder.tvReassignDateTime.setText(alist.get(position).get("reassign_date_time"));
            holder.tvReassignReason.setText(alist.get(position).get("reassign_reason"));
            holder.tvReassignRemark.setText(alist.get(position).get("reassign_remark"));

        }

        public int getItemCount() {
            return alist.size();
        }
    }

    private static class HolderReferredDocuments extends RecyclerView.ViewHolder {

        TextView tv_caseno,tv_onrname,tv_type,tv_aadd,asign_date,sdate,tvReassignDateTime,tvReassignReason,tvReassignRemark;
        LinearLayout rl_survey,rlSkippedDate,llReassignDateTime,llReassignReason,llReassignRemark;
        View view;
        RelativeLayout rl_main;

        public HolderReferredDocuments(View itemView) {
            super(itemView);
            tv_caseno = itemView.findViewById(R.id.tv_caseno);
            tv_onrname = itemView.findViewById(R.id.tv_onrname);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_aadd = itemView.findViewById(R.id.tv_aadd);
            asign_date = itemView.findViewById(R.id.asign_date);
            sdate = itemView.findViewById(R.id.sdate);
            rl_survey = itemView.findViewById(R.id.rl_survey);
            view = itemView.findViewById(R.id.view3);
            llReassignDateTime = itemView.findViewById(R.id.llReassignDateTime);
            rlSkippedDate = itemView.findViewById(R.id.rlSkippedDate);
            tvReassignDateTime = itemView.findViewById(R.id.tvReassignDateTime);
            llReassignReason = itemView.findViewById(R.id.llReassignReason);
            tvReassignReason = itemView.findViewById(R.id.tvReassignReason);
            llReassignRemark = itemView.findViewById(R.id.llReassignRemark);
            tvReassignRemark = itemView.findViewById(R.id.tvReassignRemark);
            rl_main = itemView.findViewById(R.id.rl_main);

        }
    }
}
