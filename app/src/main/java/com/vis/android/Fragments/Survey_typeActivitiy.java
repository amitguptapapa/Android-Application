package com.vis.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.Fragments.GeneralForm1;
import com.vis.android.Activities.GroupHousing.GroupHousing1;
import com.vis.android.Activities.Industrial.Industrial1;
import com.vis.android.Activities.MultiStory.MultiStory1;
import com.vis.android.Activities.OtherThanFlats.Other1;
import com.vis.android.Activities.SpecialAssets.SpecialAssets1;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Survey_typeActivitiy extends BaseFragment implements View.OnClickListener {
    LinearLayout general;
    LinearLayout multi;
    LinearLayout other;
    LinearLayout industrial;
    LinearLayout special;
    LinearLayout township;
    // RelativeLayout rl_dots, rl_back;
    RelativeLayout rl_generalSurvey, rl_vacantLand, rl_MultiStoried, rl_Industrial, rl_IndustrialLand,
            rl_IndustrialPlant, rl_GroupHousing, rl_InventoryStock, rl_ShoppingMall, rl_School, rl_Hospitals, rl_Hotel, rl_Vehicles, rl_Jewellery, rl_Commercial, rl_Furniture, rl_Multiplex, rl_CurrentAssets, rl_IntangibleAssets, rl_LargeInfrastructure, rl_Financial;
    ImageView iv_generalSurvey, iv_vacantLand, iv_MultiStoried, iv_Industrial, iv_IndustrialLand,
            iv_IndustrialPlant, iv_GroupHousing, iv_InventoryStock, iv_ShoppingMall, iv_School, iv_Hospitals, iv_Hotel, iv_Vehicles, iv_Jewellery, iv_Commercial, iv_Furniture, iv_Multiplex, iv_CurrentAssets, iv_IntangibleAssets, iv_LargeInfrastructure, iv_Financial;
    TextView tv_generalSurvey, tv_vacantLand, tv_MultiStoried, tv_Industrial, tv_IndustrialLand,
            tv_IndustrialPlant, tv_GroupHousing, tv_InventoryStock, tv_ShoppingMall, tv_School, tv_Hospitals, tv_Hotel, tv_Vehicles, tv_Jewellery, tv_Commercial, tv_Furniture, tv_Multiplex, tv_CurrentAssets, tv_IntangibleAssets, tv_LargeInfrastructure, tv_Financial;
    Intent intent;
    Preferences pref;
    TextView tv_general, tv_other;
    ImageView iv_other, ivSync;
    String status = "0";
    int pageNumber;

    // TextView tv_caseheader,tv_caseid,tv_header;
    // RelativeLayout rl_casedetail;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_survey_type_activitiy, container, false);

        getid(v);
        setListener();
        setDefaultSurvey();
        Log.v("type_of_assets", pref.get(Constants.type_of_assets));
        Log.v("type_of_assets", pref.get(Constants.defAssest));
        if (pref.get(Constants.submitStatus).equalsIgnoreCase("1")) {
            ivSync.setVisibility(View.VISIBLE);
        } else {
            ivSync.setVisibility(View.GONE);
        }
        if (pref.get(Constants.selected_property).equals("Multi-Storied Flats")) {
            tv_other.setText("Multi-Storied Flats");
            iv_other.setBackgroundResource(R.mipmap.multi_storied_flat_survey);
            status = "1";
        } else if (pref.get(Constants.selected_property).equals("Property Other Than Flats")) {
            tv_other.setText("Property Other Than Flats");
            iv_other.setBackgroundResource(R.mipmap.other_thanflats);
            status = "2";
        } else if (pref.get(Constants.selected_property).equals("Industrial")) {
            tv_other.setText("Industrial");
            iv_other.setBackgroundResource(R.mipmap.inndustrial_propery_survey);
            status = "3";
        } else if (pref.get(Constants.selected_property).equals("Special Assets")) {
            tv_other.setText("Special Assets");
            iv_other.setBackgroundResource(R.mipmap.special_asset_survey);
            status = "4";
        } else if (pref.get(Constants.selected_property).equals("Group Housing")) {
            tv_other.setText("Group Housing");
            iv_other.setBackgroundResource(R.mipmap.grouphousing_survey);
            status = "5";
        } else {
            status = "0";
        }

        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /* case R.id.ll_general:
                intent = new Intent(Survey_typeActivitiy.this, GeneralForm1.class);
                startActivity(intent);
                break;

           case R.id.ll_multi:
                intent = new Intent(Survey_typeActivitiy.this, MultiStory1.class);
                startActivity(intent);
                break;

            case R.id.ll_other:
                intent = new Intent(Survey_typeActivitiy.this, Other1.class);
                startActivity(intent);
                break;

            case R.id.ll_industrial:
                intent = new Intent(Survey_typeActivitiy.this, Industrial1.class);
                startActivity(intent);
                break;

            case R.id.ll_special:
                intent = new Intent(Survey_typeActivitiy.this, SpecialAssets1.class);
                startActivity(intent);
                break;

            case R.id.ll_township:
                intent = new Intent(Survey_typeActivitiy.this, GroupHousing1.class);
                startActivity(intent);
                break;
*/
           /* case R.id.rl_back:
                onBackPressed();
                break;*/

            case R.id.rl_generalSurvey:
                Log.v("status=====", status);
                pageNumber=6;
                hitLastSavedAssetFormApi();
                ((Dashboard) mActivity).displayView(6);
                break;
            case R.id.rl_vacantLand:
                pageNumber=19;
                hitLastSavedAssetFormApi();
                ((Dashboard) mActivity).displayView(19);
                break;
            case R.id.rl_MultiStoried:

                pageNumber=32;
                hitLastSavedAssetFormApi();
                ((Dashboard) mActivity).displayView(32);
                break;
            default:
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.alert_toast,
                        (ViewGroup) v.findViewById(R.id.toast_layout_root));

                /*ImageView image = (ImageView) layout.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_camera);*/
                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("This survey form will be added soon. Kindly start your survey with General Survey Form for now.");

                Toast toast = new Toast(mActivity);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
//                Toast.makeText(mActivity,"Survey will be available soon Please proceed with General Survey",Toast.LENGTH_LONG).show();

        }
    }

    public void getid(View v) {
        pref = new Preferences(mActivity);
        if(!pref.get(Constants.surveyChoosenPage).equalsIgnoreCase("")) {
            ((Dashboard) mActivity).displayView(Integer.parseInt(pref.get(Constants.surveyChoosenPage)));
//            displayView(Integer.parseInt(pref.get(Constants.surveyChoosen)));
        }
        general = (LinearLayout) v.findViewById(R.id.ll_general);
        multi = (LinearLayout) v.findViewById(R.id.ll_multi);
        other = (LinearLayout) v.findViewById(R.id.ll_other);
        industrial = (LinearLayout) v.findViewById(R.id.ll_industrial);
        special = (LinearLayout) v.findViewById(R.id.ll_special);
        township = (LinearLayout) v.findViewById(R.id.ll_township);
//        rl_dots = v.findViewById(R.id.rl_dots);
//        rl_dots.setVisibility(View.GONE);
//        rl_back = v.findViewById(R.id.rl_back);
        rl_generalSurvey = v.findViewById(R.id.rl_generalSurvey);
        rl_vacantLand = v.findViewById(R.id.rl_vacantLand);
        rl_MultiStoried = v.findViewById(R.id.rl_MultiStoried);
        rl_Industrial = v.findViewById(R.id.rl_Industrial);
        rl_IndustrialLand = v.findViewById(R.id.rl_IndustrialLand);
        rl_IndustrialPlant = v.findViewById(R.id.rl_IndustrialPlant);
        rl_GroupHousing = v.findViewById(R.id.rl_GroupHousing);
        rl_InventoryStock = v.findViewById(R.id.rl_InventoryStock);
        rl_ShoppingMall = v.findViewById(R.id.rl_ShoppingMall);
        rl_School = v.findViewById(R.id.rl_School);
        rl_Commercial = v.findViewById(R.id.rl_Commercial);
        rl_Furniture = v.findViewById(R.id.rl_Furniture);
        rl_Multiplex = v.findViewById(R.id.rl_Multiplex);
        rl_Hospitals = v.findViewById(R.id.rl_Hospitals);
        rl_Hotel = v.findViewById(R.id.rl_Hotel);
        rl_Vehicles = v.findViewById(R.id.rl_Vehicles);
        rl_Jewellery = v.findViewById(R.id.rl_Jewellery);
        rl_CurrentAssets = v.findViewById(R.id.rl_CurrentAssets);
        rl_IntangibleAssets = v.findViewById(R.id.rl_IntangibleAssets);
        rl_LargeInfrastructure = v.findViewById(R.id.rl_LargeInfrastructure);
        rl_Financial = v.findViewById(R.id.rl_Financial);
        iv_generalSurvey = v.findViewById(R.id.iv_generalSurvey);
        iv_vacantLand = v.findViewById(R.id.iv_vacantLand);
        iv_MultiStoried = v.findViewById(R.id.iv_MultiStoried);
        iv_Industrial = v.findViewById(R.id.iv_Industrial);
        iv_IndustrialLand = v.findViewById(R.id.iv_IndustrialLand);
        iv_IndustrialPlant = v.findViewById(R.id.iv_IndustrialPlant);
        iv_GroupHousing = v.findViewById(R.id.iv_GroupHousing);
        iv_InventoryStock = v.findViewById(R.id.iv_InventoryStock);
        iv_ShoppingMall = v.findViewById(R.id.iv_ShoppingMall);
        iv_School = v.findViewById(R.id.iv_School);
        iv_Commercial = v.findViewById(R.id.iv_Commercial);
        iv_Furniture = v.findViewById(R.id.iv_Furniture);
        iv_Multiplex = v.findViewById(R.id.iv_Multiplex);
        iv_Hospitals = v.findViewById(R.id.iv_Hospitals);
        iv_Hotel = v.findViewById(R.id.iv_Hotel);
        iv_Vehicles = v.findViewById(R.id.iv_Vehicles);
        iv_Jewellery = v.findViewById(R.id.iv_Jewellery);
        iv_CurrentAssets = v.findViewById(R.id.iv_CurrentAssets);
        iv_IntangibleAssets = v.findViewById(R.id.iv_IntangibleAssets);
        iv_LargeInfrastructure = v.findViewById(R.id.iv_LargeInfrastructure);
        iv_Financial = v.findViewById(R.id.iv_Financial);
        tv_generalSurvey = v.findViewById(R.id.tv_generalSurvey);
        tv_vacantLand = v.findViewById(R.id.tv_vacantLand);
        tv_MultiStoried = v.findViewById(R.id.tv_MultiStoried);
        tv_Industrial = v.findViewById(R.id.tv_Industrial);
        tv_IndustrialLand = v.findViewById(R.id.tv_IndustrialLand);
        tv_IndustrialPlant = v.findViewById(R.id.tv_IndustrialPlant);
        tv_GroupHousing = v.findViewById(R.id.tv_GroupHousing);
        tv_InventoryStock = v.findViewById(R.id.tv_InventoryStock);
        tv_ShoppingMall = v.findViewById(R.id.tv_ShoppingMall);
        tv_School = v.findViewById(R.id.tv_School);
        tv_Commercial = v.findViewById(R.id.tv_Commercial);
        tv_Furniture = v.findViewById(R.id.tv_Furniture);
        tv_Multiplex = v.findViewById(R.id.tv_Multiplex);
        tv_Hospitals = v.findViewById(R.id.tv_Hospitals);
        tv_Hotel = v.findViewById(R.id.tv_Hotel);
        tv_Vehicles = v.findViewById(R.id.tv_Vehicles);
        tv_Jewellery = v.findViewById(R.id.tv_Jewellery);
        tv_CurrentAssets = v.findViewById(R.id.tv_CurrentAssets);
        tv_IntangibleAssets = v.findViewById(R.id.tv_IntangibleAssets);
        tv_LargeInfrastructure = v.findViewById(R.id.tv_LargeInfrastructure);
        tv_Financial = v.findViewById(R.id.tv_Financial);


        tv_general = v.findViewById(R.id.tv_general);
        tv_other = v.findViewById(R.id.tv_other);
        ivSync = v.findViewById(R.id.ivSync);

//        tv_header = v.findViewById(R.id.tv_header);
//        rl_casedetail = v.findViewById(R.id.rl_casedetail);
//        tv_caseheader = v.findViewById(R.id.tv_caseheader);
//        tv_caseid = v.findViewById(R.id.tv_caseid);
    }

    public void setListener() {
        general.setOnClickListener(this);
        multi.setOnClickListener(this);
        other.setOnClickListener(this);
        industrial.setOnClickListener(this);
        special.setOnClickListener(this);
        township.setOnClickListener(this);


        rl_generalSurvey.setOnClickListener(this);
        rl_vacantLand.setOnClickListener(this);
        rl_MultiStoried.setOnClickListener(this);
        rl_Industrial.setOnClickListener(this);
        rl_IndustrialLand.setOnClickListener(this);
        rl_IndustrialPlant.setOnClickListener(this);
        rl_GroupHousing.setOnClickListener(this);
        rl_InventoryStock.setOnClickListener(this);
        rl_ShoppingMall.setOnClickListener(this);
        rl_School.setOnClickListener(this);
        rl_Hospitals.setOnClickListener(this);
        rl_Hotel.setOnClickListener(this);
        rl_Vehicles.setOnClickListener(this);
        rl_Jewellery.setOnClickListener(this);
        rl_Commercial.setOnClickListener(this);
        rl_Multiplex.setOnClickListener(this);
        rl_CurrentAssets.setOnClickListener(this);
        rl_IntangibleAssets.setOnClickListener(this);
        rl_LargeInfrastructure.setOnClickListener(this);
        rl_LargeInfrastructure.setOnClickListener(this);
        rl_Furniture.setOnClickListener(this);
        rl_Financial.setOnClickListener(this);
        //rl_back.setOnClickListener(this);
    }

    public void setDefaultSurvey() {
        switch (pref.get(Constants.type_of_assets).toLowerCase()) {
            case "residential apartment in multistoried building":
                rl_MultiStoried.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_MultiStoried.setTextColor(getResources().getColor(R.color.app_theme));
                iv_MultiStoried.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_MultiStoried.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "residential apartment in low rise building":
                rl_MultiStoried.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_MultiStoried.setTextColor(getResources().getColor(R.color.app_theme));
                iv_MultiStoried.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_MultiStoried.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "residential house (plotted development)":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "residential builder floor":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "residential plot/land":
                rl_vacantLand.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_vacantLand.setTextColor(getResources().getColor(R.color.app_theme));
                iv_vacantLand.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_vacantLand.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                iv_vacantLand.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_vacantLand.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "mansion":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "kothi":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "villa":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "group housing society":
                rl_GroupHousing.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_GroupHousing.setTextColor(getResources().getColor(R.color.app_theme));
                iv_GroupHousing.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_GroupHousing.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "commercial office unit":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "commercial shop unit":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "commercial floor unit":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "commercial office (independent)":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "commercial shop (independent)":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "commercial floor (independent plotted development)":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "commercial land & building":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "hotel/ resort":
                rl_Hotel.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Hotel.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Hotel.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Hotel.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);


                break;
            case "shopping mall":
                rl_ShoppingMall.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_ShoppingMall.setTextColor(getResources().getColor(R.color.app_theme));
                iv_ShoppingMall.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_ShoppingMall.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "shopping complex":
                rl_ShoppingMall.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_ShoppingMall.setTextColor(getResources().getColor(R.color.app_theme));
                iv_ShoppingMall.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_ShoppingMall.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "anchor store":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "restaurant":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "amusement park":
                break;
            case "multiplex":
                rl_Multiplex.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Multiplex.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Multiplex.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Multiplex.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "cinema hall":
                rl_Multiplex.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Multiplex.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Multiplex.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Multiplex.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "godown":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "stadium":
                rl_LargeInfrastructure.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_LargeInfrastructure.setTextColor(getResources().getColor(R.color.app_theme));
                iv_LargeInfrastructure.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_LargeInfrastructure.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "club":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "institutional plot/land":
                rl_School.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_School.setTextColor(getResources().getColor(R.color.app_theme));
                iv_School.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_School.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "institutional land & building":
                rl_School.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_School.setTextColor(getResources().getColor(R.color.app_theme));
                iv_School.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_School.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "educational institution (school/ college/ university)":
                rl_School.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_School.setTextColor(getResources().getColor(R.color.app_theme));
                iv_School.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_School.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "hospital":
                rl_Hospitals.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Hospitals.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Hospitals.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Hospitals.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "multi speciality hospital":
                rl_Hospitals.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Hospitals.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Hospitals.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Hospitals.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "nursing home":
                rl_Hospitals.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Hospitals.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Hospitals.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Hospitals.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "old age home":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "industrial plot":
                rl_vacantLand.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_vacantLand.setTextColor(getResources().getColor(R.color.app_theme));
                iv_vacantLand.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_vacantLand.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "industrial project land & building":
                rl_IndustrialLand.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_IndustrialLand.setTextColor(getResources().getColor(R.color.app_theme));
                iv_IndustrialLand.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_IndustrialLand.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "manufacturing unit":
                rl_Industrial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Industrial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Industrial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Industrial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "small/ mid-scale manufacturing unit":
                rl_Industrial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Industrial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Industrial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Industrial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "industrial plant":
                rl_Industrial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Industrial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Industrial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Industrial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "industrial project":
                rl_Industrial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Industrial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Industrial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Industrial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "infrastructure project":
                rl_LargeInfrastructure.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_LargeInfrastructure.setTextColor(getResources().getColor(R.color.app_theme));
                iv_LargeInfrastructure.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_LargeInfrastructure.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "large industrial project":
                rl_Industrial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Industrial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Industrial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Industrial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "industrial plant & machinery":
                rl_IndustrialPlant.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_IndustrialPlant.setTextColor(getResources().getColor(R.color.app_theme));
                iv_IndustrialPlant.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_IndustrialPlant.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "general machinery items":
                rl_IndustrialPlant.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_IndustrialPlant.setTextColor(getResources().getColor(R.color.app_theme));
                iv_IndustrialPlant.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_IndustrialPlant.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "sez":
                rl_LargeInfrastructure.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_LargeInfrastructure.setTextColor(getResources().getColor(R.color.app_theme));
                iv_LargeInfrastructure.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_LargeInfrastructure.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "warehouse":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "agricultural land":
                rl_vacantLand.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_vacantLand.setTextColor(getResources().getColor(R.color.app_theme));
                iv_vacantLand.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_vacantLand.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "farm house":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "financial securities/ instruments":
                rl_Financial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Financial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Financial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Financial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "current assets":
                rl_CurrentAssets.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_CurrentAssets.setTextColor(getResources().getColor(R.color.app_theme));
                iv_CurrentAssets.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_CurrentAssets.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "stocks":
                rl_InventoryStock.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_InventoryStock.setTextColor(getResources().getColor(R.color.app_theme));
                iv_InventoryStock.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_InventoryStock.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "jewellery":
                rl_Jewellery.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Jewellery.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Jewellery.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Jewellery.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "truck":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "bus":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "car":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "aircraft":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "helicopter":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "ship":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "boat":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "motorcycle":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "scooter":
                rl_Vehicles.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Vehicles.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Vehicles.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Vehicles.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "fittings & fixtures":
                rl_Furniture.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Furniture.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Furniture.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Furniture.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "furniture":
                rl_Furniture.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Furniture.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Furniture.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Furniture.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "brand":
                rl_IntangibleAssets.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_IntangibleAssets.setTextColor(getResources().getColor(R.color.app_theme));
                iv_IntangibleAssets.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_IntangibleAssets.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "good will":
                rl_IntangibleAssets.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_IntangibleAssets.setTextColor(getResources().getColor(R.color.app_theme));
                iv_IntangibleAssets.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_IntangibleAssets.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "highway/ expressway":
                rl_LargeInfrastructure.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_LargeInfrastructure.setTextColor(getResources().getColor(R.color.app_theme));
                iv_LargeInfrastructure.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_LargeInfrastructure.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "it/ office space":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "expo cum exhibition center":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "petrol pump":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "agri mart":
                rl_Commercial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Commercial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Commercial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Commercial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "cold storage":
                rl_Industrial.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_Industrial.setTextColor(getResources().getColor(R.color.app_theme));
                iv_Industrial.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_Industrial.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);

                break;
            case "govt. department/ ministry office":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "public sector unit office":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "police station":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            case "fire station":
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                break;
            default:
                rl_generalSurvey.setBackground(getResources().getDrawable(R.drawable.ic_icon_selected));
                tv_generalSurvey.setTextColor(getResources().getColor(R.color.app_theme));
                iv_generalSurvey.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen._60sdp);
                iv_generalSurvey.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen._60sdp);


        }
    }
    private void hitLastSavedAssetFormApi() {

        String url = Utils.getCompleteApiUrl(mActivity, R.string.LastSavedAssetForm);

        Log.v("hitGetLastSaved", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        try {

            jsonObject.put("case_id", pref.get(Constants.case_id));
            jsonObject.put("type", "2");//1 get , 2 post.
            jsonObject1.put("surveyType", "2");//1 unchanged , 2 changed
            jsonObject1.put("surveyChoosenPage", Integer.toString(pageNumber));
            jsonObject1.put("assestChoosen", pref.get(Constants.type_of_assets));//which Asset is choosen

            jsonObject.put("lastSavedAssetForm", jsonObject1.toString());
            json_data.put("VIS", jsonObject);
            Log.v("hitGetLastSaved", json_data.toString());

        } catch (JSONException e) {
            Log.v("exception====", e.toString());
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonGetLastSaved(response);
                    }

                    @Override
                    public void onError(ANError error) {

                        // handle error
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }
    private void parseJsonGetLastSaved(JSONObject response) {
        Log.v("resp:HitGetLastSaved", response.toString());
        pref.set(Constants.surveyChoosen,Integer.toString(pageNumber));
        pref.set(Constants.assestStatus,"Changed");
        pref.commit();     try {

            /*JSONObject jsonObject = response.getJSONObject("VIS");
            String res_code = jsonObject.getString("response_code");
            String res_msg = jsonObject.getString("response_message");




            loader.cancel();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
