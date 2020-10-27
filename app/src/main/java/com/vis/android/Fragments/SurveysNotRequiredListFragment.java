package com.vis.android.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.vis.android.Extras.BaseFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

////fragment_surveys_not_required_list

public class SurveysNotRequiredListFragment extends BaseFragment implements View.OnClickListener {
    public static ArrayList<HashMap<String, String>> lead_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> case_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> comm_array_list = new ArrayList<HashMap<String, String>>();
    View v;
    TextView survey_completed;
    ListView list;
    DashboardAdapter adapter;
    Intent intent;
    CustomLoader loader;
    Preferences pref;
    String a = "";
    String page = "", status = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_surveys_not_required_list, container, false);
        getid(v);
        setListener();
        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            Hit_GetLeads_Api();

        } else {
            Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

        }

        final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkConnectedMainThred(getActivity())) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    Hit_GetLeads_Api();

                } else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                }

                pullToRefresh.setRefreshing(false);
            }
        });
      /*  try {
            case_array_list.clear();
            a = String.valueOf(DatabaseController.fetchAllDataSpecificColumn("column_new", "generalform"));
            Log.v("a========", a);

            JSONArray array = new JSONArray(a);
            for (int i = 0; i < array.length(); i++) {

                JSONObject data_object = array.getJSONObject(i);
                HashMap<String, String> hash = new HashMap<String, String>();
                hash.put("page", data_object.getString("page"));
                case_array_list.add(hash);
                Log.v("getcolumnet========", case_array_list.toString());
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }*/


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    pref.set(Constants.case_id, lead_array_list.get(i).get("case_id"));
                    pref.commit();

                    String id = ((TextView) view.findViewById(R.id.survey_completed)).getText().toString();

                    if (id.equals("0%")) {
                      //  intent = new Intent(getActivity(), CaseDetail.class);
                        intent = new Intent(getActivity(), Dashboard.class);

                        pref.set(Constants.case_id, lead_array_list.get(i).get("id"));
                        pref.commit();

                        intent.putExtra("support_manager_name", lead_array_list.get(i).get("support_manager_name"));
                        intent.putExtra("support_manager_email", lead_array_list.get(i).get("support_manager_email"));
                        intent.putExtra("support_manager_phone", lead_array_list.get(i).get("support_manager_phone"));
                        intent.putExtra("support_owner_name", lead_array_list.get(i).get("support_owner_name"));
                        intent.putExtra("support_owner_email", lead_array_list.get(i).get("support_owner_email"));
                        intent.putExtra("support_owner_phone", lead_array_list.get(i).get("support_owner_phone"));
                        intent.putExtra("support_co_person_name", lead_array_list.get(i).get("support_co_person_name"));
                        intent.putExtra("support_co_person_email", lead_array_list.get(i).get("support_co_person_email"));
                        intent.putExtra("support_co_person_phone", lead_array_list.get(i).get("support_co_person_phone"));
                        intent.putExtra("support_bus_asso_name", lead_array_list.get(i).get("support_bus_asso_name"));
                        intent.putExtra("support_bus_asso_email", lead_array_list.get(i).get("support_bus_asso_email"));
                        intent.putExtra("support_bus_asso_phone", lead_array_list.get(i).get("support_bus_asso_phone"));

                        intent.putExtra("case_u_id", lead_array_list.get(i).get("case_u_id"));
                        intent.putExtra("case_id", lead_array_list.get(i).get("id"));
                        intent.putExtra("user_email", lead_array_list.get(i).get("user_email"));
                        intent.putExtra("lead_schedule_status", lead_array_list.get(i).get("lead_schedule_status"));
                        intent.putExtra("customer_mobile_number", lead_array_list.get(i).get("customer_mobile_number"));
                        intent.putExtra("customer_mobile_number_arr", lead_array_list.get(i).get("customer_mobile_number_arr"));
                        intent.putExtra("co_alternate_mobile", lead_array_list.get(i).get("co_alternate_mobile"));
                        intent.putExtra("landmark_lat", lead_array_list.get(i).get("landmark_lat"));
                        intent.putExtra("landmark_long", lead_array_list.get(i).get("landmark_long"));
                        intent.putExtra("asset_ownername", lead_array_list.get(i).get("asset_ownername"));
                        intent.putExtra("customer_name", lead_array_list.get(i).get("customer_name"));
                        intent.putExtra("asset_type", lead_array_list.get(i).get("asset_type"));
                        intent.putExtra("schedule_date", lead_array_list.get(i).get("schedule_date"));
                        intent.putExtra("asset_address", lead_array_list.get(i).get("asset_address"));
                        intent.putExtra("status", lead_array_list.get(i).get("status"));
                        intent.putExtra("purpose_of_assignment", lead_array_list.get(i).get("purpose_of_assignment"));
                        //  intent.putExtra("type_of_property", lead_array_list.get(i).get("type_of_property"));
                        intent.putExtra("type_of_valuation", lead_array_list.get(i).get("type_of_valuation"));
                        //  intent.putExtra("pincode", lead_array_list.get(i).get("pincode"));
                        intent.putExtra("date", lead_array_list.get(i).get("assigned_date"));
                        intent.putExtra("land_area", lead_array_list.get(i).get("land_area"));
                        intent.putExtra("covered_area", lead_array_list.get(i).get("covered_area"));
                        intent.putExtra("assignment", lead_array_list.get(i).get("assignment"));
                        intent.putExtra("person_name", lead_array_list.get(i).get("person_name"));
                        intent.putExtra("relation_with_owner", lead_array_list.get(i).get("relation_with_owner"));
                        intent.putExtra("comment", lead_array_list.get(i).get("comment"));
                        intent.putExtra("type_of_account", lead_array_list.get(i).get("type_of_account"));
                        // intent.putExtra("assets_no", lead_array_list.get(i).get("assets_no"));
                        intent.putExtra("latitude", lead_array_list.get(i).get("latitude"));
                        intent.putExtra("longitude", lead_array_list.get(i).get("longitude"));
                        intent.putExtra("business_associate_id", lead_array_list.get(i).get("business_associate_id"));
                        intent.putExtra("person_number", lead_array_list.get(i).get("person_number"));
                        intent.putExtra("alternate_mobile", lead_array_list.get(i).get("alternate_mobile"));
                        intent.putExtra("co_person_number", lead_array_list.get(i).get("co_person_number"));

                     /*   if (lead_array_list.contains("in_case_type")) {
                            intent.putExtra("in_case_type", lead_array_list.get(i).get("in_case_type"));
                        }
                        if (lead_array_list.contains("nat_of_misc")) {
                            intent.putExtra("nat_of_misc", lead_array_list.get(i).get("nat_of_misc"));
                        }
                        if (lead_array_list.contains("fitting_assets")) {
                            intent.putExtra("fitting_assets", lead_array_list.get(i).get("fitting_assets"));
                        }
                        if (lead_array_list.contains("address_located")) {
                            intent.putExtra("address_located", lead_array_list.get(i).get("address_located"));
                        }
                        if (lead_array_list.contains("land_area")) {
                            intent.putExtra("land_area", lead_array_list.get(i).get("land_area"));
                        }
                        if (lead_array_list.contains("covered_area")) {
                            intent.putExtra("covered_area", lead_array_list.get(i).get("covered_area"));
                        }
                        if (lead_array_list.contains("plant_other")) {
                            intent.putExtra("plant_other", lead_array_list.get(i).get("plant_other"));
                        }
                        if (lead_array_list.contains("estimated_prise")) {
                            intent.putExtra("estimated_prise", lead_array_list.get(i).get("estimated_prise"));
                        }*/

                        intent.putExtra("id", lead_array_list.get(i).get("id"));
                        intent.putExtra("nature_assets", lead_array_list.get(i).get("nature_assets"));
                        intent.putExtra("category_assets", lead_array_list.get(i).get("category_assets"));
                        intent.putExtra("type_of_assets", lead_array_list.get(i).get("type_of_assets"));
                        intent.putExtra("lead_status", lead_array_list.get(i).get("lead_status"));
                        intent.putExtra("in_case_type", lead_array_list.get(i).get("in_case_type"));
                        intent.putExtra("nat_of_misc", lead_array_list.get(i).get("nat_of_misc"));
                        intent.putExtra("fitting_assets", lead_array_list.get(i).get("fitting_assets"));
                        intent.putExtra("address_located", lead_array_list.get(i).get("address_located"));
                        intent.putExtra("land_area", lead_array_list.get(i).get("land_area"));
                        intent.putExtra("covered_area", lead_array_list.get(i).get("covered_area"));
                        intent.putExtra("plant_other", lead_array_list.get(i).get("plant_other"));
                        intent.putExtra("estimated_prise", lead_array_list.get(i).get("estimated_prise"));

                        // intent.putExtra("financial_security", lead_array_list.get(i).get("financial_security"));
                        intent.putExtra("reporting_services", lead_array_list.get(i).get("reporting_services"));
                        //  intent.putExtra("payment_mode", lead_array_list.get(i).get("payment_mode"));
                        //intent.putExtra("indi_plant_other", lead_array_list.get(i).get("indi_plant_other"));
                        //  intent.putExtra("indi_moveable_assets", lead_array_list.get(i).get("indi_moveable_assets"));
                        //intent.putExtra("indi_vehicle", lead_array_list.get(i).get("indi_vehicle"));
                        //  intent.putExtra("indi_stocks", lead_array_list.get(i).get("indi_stocks"));
                        //  intent.putExtra("machinery_gross", lead_array_list.get(i).get("machinery_gross"));
                        //intent.putExtra("plant", lead_array_list.get(i).get("plant"));
                        // intent.putExtra("net_block", lead_array_list.get(i).get("net_block"));


                        if (lead_array_list.get(i).get("category_id").equals("1")) {
                            intent.putExtra("category_id", lead_array_list.get(i).get("category_id"));
                            intent.putExtra("manager_name", lead_array_list.get(i).get("manager_name"));
                            intent.putExtra("bank_name", lead_array_list.get(i).get("bank_name"));
                            intent.putExtra("designation", lead_array_list.get(i).get("designation"));
                            intent.putExtra("manager_off_email", lead_array_list.get(i).get("manager_off_email"));
                        } else if (lead_array_list.get(i).get("category_id").equals("2")) {
                            intent.putExtra("category_id", lead_array_list.get(i).get("category_id"));
                            intent.putExtra("manager_name", lead_array_list.get(i).get("manager_name"));
                            intent.putExtra("manager_off_email", lead_array_list.get(i).get("manager_off_email"));
                            intent.putExtra("mobile_no", lead_array_list.get(i).get("mobile_no"));
                        } else if (lead_array_list.get(i).get("category_id").equals("3")) {
                            intent.putExtra("category_id", lead_array_list.get(i).get("category_id"));
                            intent.putExtra("manager_name", lead_array_list.get(i).get("manager_name"));
                            intent.putExtra("manager_off_email", lead_array_list.get(i).get("manager_off_email"));
                            intent.putExtra("mobile_no", lead_array_list.get(i).get("mobile_no"));
                            intent.putExtra("designation", lead_array_list.get(i).get("designation"));
                            intent.putExtra("company_name", lead_array_list.get(i).get("company_name"));
                        } else if (lead_array_list.get(i).get("category_id").equals("4")) {
                            intent.putExtra("category_id", lead_array_list.get(i).get("category_id"));
                            intent.putExtra("manager_name", lead_array_list.get(i).get("manager_name"));
                            intent.putExtra("manager_off_email", lead_array_list.get(i).get("manager_off_email"));
                            intent.putExtra("mobile_no", lead_array_list.get(i).get("mobile_no"));
                        }

                        startActivity(intent);

                    } else if (id.equals("10%")) {
                        ((Dashboard)mActivity).displayView(6);
                        //intent = new Intent(getActivity(), GeneralForm1.class);
                        //startActivity(intent);
                    }  else if (id.equals("20%")) {
                        ((Dashboard)mActivity).displayView(7);
                    } else if (id.equals("30%")) {
                        ((Dashboard)mActivity).displayView(8);
                    } else if (id.equals("40%")) {
                        ((Dashboard)mActivity).displayView(9);
                    } else if (id.equals("50%")) {
                        ((Dashboard)mActivity).displayView(10);
                    } else if (id.equals("60%")) {
                        ((Dashboard)mActivity).displayView(11);
                    } else if (id.equals("70%")) {
                        ((Dashboard)mActivity).displayView(12);
                    } else if (id.equals("80%")) {
                        ((Dashboard)mActivity).displayView(13);
                    } else if (id.equals("90%")) {
                        ((Dashboard)mActivity).displayView(14);
                    } else if (id.equals("100%")) {
                        ((Dashboard)mActivity).displayView(15);
                    } else if (id.equals("110%")) {
                        ((Dashboard)mActivity).displayView(16);
                    } else {
                        Toast.makeText(getActivity(), "No value found!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    public void getid(View v) {
        pref = new Preferences(getActivity());
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        list = (ListView) v.findViewById(R.id.lv_dashboard);
    }

    public void setListener() {

    }

    //********************************** GetLeads api *********************************//
  /*  private void Hit_GetLeads_Api() {

        String url = Utils.getCompleteApiUrl(getActivity(), R.string.get_Leads);
        Log.v("Hit_GetLeads_Api", url);

        JSONObject json_data = new JSONObject();

        try {
            json_data.put("surveyor_id", pref.get(Constants.surveyor_id));

            Log.v("request", json_data.toString());

        } catch (JSONException e) {

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

        Log.v("response", response.toString());
        lead_array_list.clear();
        try {

            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String res_msg = jsonObject.getString("response_message");


            if (res_code.equals("1")) {
                JSONArray array_case_datails = jsonObject.getJSONArray("case_datails");

                for (int i = 0; i < array_case_datails.length(); i++) {

                    JSONObject data_object = array_case_datails.getJSONObject(i);
                    JSONObject object_Pdetail = data_object.getJSONObject("Personal_Detail");
                    JSONObject object_Cdetail = data_object.getJSONObject("case_datail");
                    JSONObject object_Comdetail = data_object.getJSONObject("Commercial_detail");
                    HashMap<String, String> hash = new HashMap<String, String>();

                    if (object_Pdetail.get("status").toString().equals("0") || object_Pdetail.get("status").toString().equals("1")) {
                        hash.put("case_id", object_Pdetail.get("case_id").toString());
                        hash.put("customer_name", object_Pdetail.get("customer_name").toString());
                        hash.put("person_name", object_Pdetail.get("person_name").toString());
                        hash.put("user_email", object_Pdetail.get("user_email").toString());
                        hash.put("person_number", object_Pdetail.get("person_number").toString());
                        hash.put("alternate_mobile", object_Pdetail.get("alternate_mobile").toString());
                        hash.put("comment", object_Pdetail.get("comment").toString());
                        hash.put("asset_ownername", object_Pdetail.get("asset_ownername").toString());
                        hash.put("asset_address", object_Pdetail.get("asset_address").toString());
                        hash.put("status", object_Pdetail.get("status").toString());
                        hash.put("schedule_date", object_Pdetail.get("schedule_date").toString());
                        hash.put("type_of_property", object_Pdetail.get("type_of_property").toString());
                        hash.put("type_of_valuation", object_Pdetail.get("type_of_valuation").toString());
                        hash.put("pincode", object_Pdetail.get("pincode").toString());
                        hash.put("assigned_date", object_Pdetail.get("assigned_date").toString());
                        hash.put("lead_schedule_status", object_Pdetail.get("lead_schedule_status").toString());

                        hash.put("land_area", object_Cdetail.get("land_area").toString());
                        hash.put("covered_area", object_Cdetail.get("covered_area").toString());
                        hash.put("assignment", object_Cdetail.get("assignment").toString());

                        hash.put("machinery_gross", object_Comdetail.get("machinery_gross").toString());
                        hash.put("plant", object_Comdetail.get("plant").toString());
                        hash.put("net_block", object_Comdetail.get("net_block").toString());
                        lead_array_list.add(hash);
                        adapter = new DashboardAdapter(getActivity(), lead_array_list, a);
                        list.setAdapter(adapter);
                    } else {

                    }

                }

            } else {
                Toast.makeText(getActivity(), res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }*/

    public void Hit_GetLeads_Api() {
        String url = Utils.getCompleteApiUrl(getActivity(), R.string.getLeads);
        Log.v("Hit_GetLeads_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("surveyor_id", pref.get(Constants.surveyor_id));
            json_data.put("VIS", jsonObject);

            Log.v("request", json_data.toString());

        } catch (JSONException e) {

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
        Log.v("response", response.toString());
        lead_array_list.clear();
        try {

            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String res_msg = jsonObject.getString("response_message");


            if (res_code.equals("1")) {
                JSONArray array_case_datails = jsonObject.getJSONArray("case_datails");

                for (int i = 0; i < array_case_datails.length(); i++) {

                    JSONObject data_object = array_case_datails.getJSONObject(i);
                    JSONObject object_Pdetail = data_object.getJSONObject("Personal_Detail");
                    JSONObject object_Cdetail = data_object.getJSONObject("case_datail");
                    JSONObject object_Comdetail = data_object.getJSONObject("Commercial_detail");
                    JSONObject objectCreatedBy = data_object.getJSONObject("created_by");
                    JSONObject objectSupport = data_object.getJSONObject("support");

                    if (object_Cdetail.getString("survey_not_required_status").equalsIgnoreCase("1")) {

                        HashMap<String, String> hash = new HashMap<String, String>();
                        hash.put("support_manager_name", objectSupport.get("manager_name").toString());
                        hash.put("support_manager_email", objectSupport.get("manager_email").toString());
                        hash.put("support_manager_phone", objectSupport.get("manager_phone").toString());
                        hash.put("support_owner_name", objectSupport.get("owner_name").toString());
                        hash.put("support_owner_email", objectSupport.get("owner_email").toString());
                        hash.put("support_owner_phone", objectSupport.get("owner_phone").toString());
                        hash.put("support_co_person_name", objectSupport.get("co_person_name").toString());
                        hash.put("support_co_person_email", objectSupport.get("co_person_email").toString());
                        hash.put("support_co_person_phone", objectSupport.get("co_person_phone").toString());
                        hash.put("support_bus_asso_name", objectSupport.get("bus_asso_name").toString());
                        hash.put("support_bus_asso_email", objectSupport.get("bus_asso_email").toString());
                        hash.put("support_bus_asso_phone", objectSupport.get("bus_asso_phone").toString());

                        // if (object_Pdetail.get("status").toString().equals("1") || object_Pdetail.get("status").toString().equals("3")) {
                        hash.put("case_u_id", object_Pdetail.get("case_u_id").toString());
                        hash.put("case_id", object_Pdetail.get("case_id").toString());
                        hash.put("customer_name", object_Pdetail.get("customer_name").toString());
                        hash.put("person_name", object_Pdetail.get("person_name").toString());
                        hash.put("user_email", object_Pdetail.get("user_email").toString());
                        hash.put("customer_mobile_number", object_Pdetail.get("customer_mobile_number").toString());
                        hash.put("customer_mobile_number_arr", object_Pdetail.get("customer_mobile_number_arr").toString());
                        hash.put("alternate_mobile", object_Pdetail.get("alternate_mobile").toString());
                        hash.put("comment", object_Pdetail.get("comment").toString());
                        hash.put("asset_ownername", object_Pdetail.get("asset_ownername").toString());
                        hash.put("asset_address", object_Pdetail.get("asset_address").toString());
                        hash.put("status", object_Pdetail.get("status").toString());
                        hash.put("schedule_date", object_Pdetail.get("schedule_date").toString());
                        hash.put("schedule_time", object_Pdetail.get("schedule_time").toString());
                        //  hash.put("type_of_property", object_Pdetail.get("type_of_property").toString());
                        hash.put("type_of_valuation", object_Pdetail.get("type_of_valuation").toString());
                        //  hash.put("pincode", object_Pdetail.get("pincode").toString());
                        hash.put("assigned_date", object_Pdetail.get("assigned_date").toString());
                        hash.put("lead_schedule_status", object_Pdetail.get("lead_schedule_status").toString());
                        hash.put("purpose_of_assignment", object_Pdetail.get("purpose_of_assignment").toString());
                        hash.put("relation_with_owner", object_Pdetail.get("relation_with_owner").toString());
                        hash.put("type_of_account", object_Pdetail.get("type_of_account").toString());
                        // hash.put("assets_no", object_Pdetail.get("assets_no").toString());
                        hash.put("latitude", object_Pdetail.get("latitude").toString());
                        hash.put("longitude", object_Pdetail.get("longitude").toString());
                        hash.put("co_alternate_mobile", object_Pdetail.get("co_alternate_mobile").toString());
                        hash.put("business_associate_id", object_Pdetail.get("business_associate_id").toString());
                        hash.put("person_number", object_Pdetail.get("person_number").toString());
                        hash.put("co_person_number", object_Pdetail.get("co_person_number").toString());

                        hash.put("id", object_Cdetail.get("id").toString());
                        hash.put("nature_assets", object_Cdetail.get("nature_assets").toString());
                        hash.put("category_assets", object_Cdetail.get("category_assets").toString());
                        hash.put("type_of_assets", object_Cdetail.get("type_of_assets").toString());
                        hash.put("lead_status", object_Cdetail.get("lead_status").toString());
                        hash.put("in_case_type", object_Cdetail.get("in_case_type").toString());
                        hash.put("nat_of_misc", object_Cdetail.get("nat_of_misc").toString());
                        hash.put("fitting_assets", object_Cdetail.get("fitting_assets").toString());
                        hash.put("address_located", object_Cdetail.get("address_located").toString());
                        hash.put("land_area", object_Cdetail.get("land_area").toString());
                        hash.put("covered_area", object_Cdetail.get("covered_area").toString());
                        hash.put("plant_other", object_Cdetail.get("plant_other").toString());
                        hash.put("estimated_prise", object_Cdetail.get("estimated_prise").toString());
                        hash.put("landmark_lat", object_Cdetail.get("landmark_lat").toString());
                        hash.put("landmark_long", object_Cdetail.get("landmark_long").toString());

                        //   hash.put("financial_security", object_Comdetail.get("financial_security").toString());
                        hash.put("reporting_services", object_Comdetail.get("reporting_services").toString());
                        // hash.put("payment_mode", object_Comdetail.get("payment_mode").toString());
                        //   hash.put("indi_plant_other", object_Comdetail.get("indi_plant_other").toString());
                        //hash.put("indi_moveable_assets", object_Comdetail.get("indi_moveable_assets").toString());
                        // hash.put("indi_vehicle", object_Comdetail.get("indi_vehicle").toString());
                        //   hash.put("indi_stocks", object_Comdetail.get("indi_stocks").toString());
                        // hash.put("machinery_gross", object_Comdetail.get("machinery_gross").toString());
                        //hash.put("plant", object_Comdetail.get("plant").toString());
                        // hash.put("net_block", object_Comdetail.get("net_block").toString());

                        if (objectCreatedBy.get("category_id").toString().equals("1")) {
                            hash.put("category_id", objectCreatedBy.get("category_id").toString());
                            hash.put("manager_name", objectCreatedBy.get("manager_name").toString());
                            hash.put("bank_name", objectCreatedBy.get("bank_name").toString());
                            hash.put("designation", objectCreatedBy.get("designation").toString());
                            hash.put("manager_off_email", objectCreatedBy.get("manager_off_email").toString());
                        } else if (objectCreatedBy.get("category_id").toString().equals("2")) {
                            hash.put("category_id", objectCreatedBy.get("category_id").toString());
                            hash.put("manager_name", objectCreatedBy.get("manager_name").toString());
                            hash.put("manager_off_email", objectCreatedBy.get("email").toString());
                            hash.put("mobile_no", objectCreatedBy.get("mobile_no").toString());
                        } else if (objectCreatedBy.get("category_id").toString().equals("3")) {
                            hash.put("category_id", objectCreatedBy.get("category_id").toString());
                            hash.put("manager_name", objectCreatedBy.get("manager_name").toString());
                            hash.put("manager_off_email", objectCreatedBy.get("email").toString());
                            hash.put("mobile_no", objectCreatedBy.get("mobile_no").toString());
                            hash.put("designation", objectCreatedBy.get("designation").toString());
                            hash.put("company_name", objectCreatedBy.get("company_name").toString());
                        } else if (objectCreatedBy.get("category_id").toString().equals("4")) {
                            hash.put("category_id", objectCreatedBy.get("category_id").toString());
                            hash.put("manager_name", objectCreatedBy.get("manager_name").toString());
                            hash.put("manager_off_email", objectCreatedBy.get("email").toString());
                            hash.put("mobile_no", objectCreatedBy.get("mobile_no").toString());
                        }

                        lead_array_list.add(hash);
                    }

                    adapter = new DashboardAdapter(getActivity(), lead_array_list, a);
                    list.setAdapter(adapter);

                }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public class DashboardAdapter extends BaseAdapter {
        LayoutInflater inflter;
        Context context;
        String part1 = "", part2 = "";
        ArrayList<HashMap<String, String>> alist;
        ImageView triangle;
        Preferences pref;
        View status_view;
        String a = "";
        LinearLayout address;
        LinearLayout rl_address, rl_type;

        TextView tv_caseno, Oner_name, asset_type, asset_add, assignd_date, status, schedule_date, state, assigned_date;

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
            triangle = v.findViewById(R.id.traingle);
            address = v.findViewById(R.id.ll_address);
            tv_caseno = v.findViewById(R.id.tv_caseno);
            rl_address = v.findViewById(R.id.rl_address);
            rl_type = v.findViewById(R.id.rl_type);
        }

        public void setValues(final int position) {

            Oner_name.setText(alist.get(position).get("customer_name"));

            if (alist.get(position).get("address_located").equals("not_selected")) {
                rl_address.setVisibility(View.GONE);
            } else {
                rl_address.setVisibility(View.VISIBLE);
                asset_add.setText(alist.get(position).get("address_located"));
            }

            if (alist.get(position).get("type_of_assets").equals("not_selected")) {
                rl_type.setVisibility(View.GONE);
            } else {
                rl_type.setVisibility(View.VISIBLE);
                asset_type.setText(alist.get(position).get("type_of_assets"));
            }
            status.setText(alist.get(position).get("status"));
            assigned_date.setText(alist.get(position).get("assigned_date"));
            tv_caseno.setText(alist.get(position).get("case_u_id"));

            status_view.setBackgroundColor(Color.MAGENTA);

          /*  if (alist.get(position).get("status").equals("1")) {
                Log.v("status1", alist.get(position).get("status"));
                status_view.setBackgroundColor(Color.YELLOW);
            } else if (alist.get(position).get("status").equals("3")) {
                Log.v("status2", alist.get(position).get("status"));
                status_view.setBackgroundColor(Color.GREEN);
            } else {
                Log.v("status3", alist.get(position).get("status"));
            }*/
            if (alist.get(position).get("schedule_date").equals("")) {
                schedule_date.setText("Not scheduled yet");
            } else {
                schedule_date.setText(alist.get(position).get("schedule_date") + " " + alist.get(position).get("schedule_time"));

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");
                String currentDate = df.format(c.getTime());
                System.out.println("Current time => " + currentDate);

              /*  String currentString = alist.get(position).get("schedule_date");
                String[] separated = currentString.split(",");
                part1 = separated[0];
                part2 = separated[1];

                try {
                    DateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                    DateFormat targetFormat = new SimpleDateFormat("dd MM yyyy");
                    Date date = originalFormat.parse(part1);
                    String formattedDate = targetFormat.format(date);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
                    Date date1 = sdf.parse(currentDate);
                    Date date2 = sdf.parse(formattedDate);

                    if (date1.equals(date2)) {

                    } else if (date1.before(date2)) {
                        Log.v("date-----2", "date1 before date2");
                    } else if (date1.after(date2)) {
                        triangle.setVisibility(View.VISIBLE);
                        Log.v("date-----3", "date1 after date2");
                    } else {
                        triangle.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {

                }*/


            }

         /*   address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getActivity(), MapLocatorActivity.class);
                    startActivity(intent);
                }
            });*/
        }
    }
}

