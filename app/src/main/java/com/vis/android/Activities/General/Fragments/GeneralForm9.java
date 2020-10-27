package com.vis.android.Activities.General.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.SpinnerAdapter;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableGeneralForm;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;

public class GeneralForm9 extends BaseFragment implements View.OnClickListener {
    RelativeLayout rlSpinnerFinish, rlSpinnerType, rlBoundaryWidth, rlBoundaryHeight, rlBoundaryRunning,
            rlLiftMake, rlLiftCapacity, rlLiftCondition;


    //            rlPBInverterMake,   rlPBInverterCapacity,
//            rlPBInverterCondition,
//            rlPBDGMake, rlPBDGCapacity, rlPBDGCondition;
    LinearLayout llNotAvailableWithin, llAvailableWithin, llhvac, llGarden, llPowerB, llLiftElevator;
    TextView next, tvPrevious;
    Intent intent;
    Boolean edit_page = true;

    String makeHvac = "", capHvac = "", makePB = "", capPB = "", garBeauty, garOrdinary;

    int spinHvCon = 0, spinHvac = 0, spinGarden = 0, spinPBCon = 0, spinPB = 0;

    CheckBox cbGarBeautiful, cbGarOrdinary;


    //RadioGroup
    RadioGroup rgBoundaryWall, rgLift, rgLift1,
    //            rgGarden,
    rgParking,
            rgAvailableWithin, rgNotAvailableWithin;

    //EditText
    EditText etBoundaryRunningMtr, etBoundaryHeight, etBoundaryWidth, etLiftMake, etLiftCapacity,
    //            etPBInverterMake, etPBInverterCapacity,
//            etPBDGMake, etPBDGCapacity,
    etSecurityHutDesc, etPavementsDesc, etSpecialComments, etHVACMake, etPowerMake, etPbCapacity, etHVACCapacity;

    //CheckBox
    //cbGardenYes,cbGardenNo,cbGardenBeautiful,cbGardenOrdinary,cbOnGround,cbInBasement,cbOnStilt,cbOnRoad,cbAcuteParking,
//    CheckBox cbInverter,cbDgSet,cbNoPowerBackup;

    //cbGardenYesCheck,cbGardenNoCheck,cbGardenBeautifulCheck,cbGardenOrdinaryCheck,
//    int cbInverterCheck,cbDgSetCheck, cbOnGroundCheck,cbInBasementCheck,cbOnStiltCheck,cbOnRoadCheck,cbAcuteParkingCheck,cbNoPowerBackupCheck;

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    Spinner spinnerHvac, spinnerGarden, spinnerPB, spinnerHvacCondition, spinnerPBCondition, spinnerType, spinnerFinish, spinnerLiftCondition,
    //            spinnerPBInverterCondition,spinnerPBDGCondition,
    spinnerSecurityHut, spinnerPavements, spinnerBoundaryHeightFtMtr;

    String[] arrayType = {"Choose an item", "Brick Wall", "Brick Wall with RCC Pillars", "RCC Wall", "Brick wall with barbed wiring", "Brick Wall with RCC Pillars & barbed wiring",
            "Stone Wall", "Stone Wall with barbed wiring", "Temporary boundary", "Temporary boundary with barbed wiring", "Barbed wiring fitted in RCC Pillar foundation"};

    String[] arrayFinish = {"Choose an item", "Plastered", "Non plastered", "Plastered & painted", "Plastered but broken patches", "Stone finish"};

    String[] arrayLiftCondition = {"Choose an item", "Good", "Average", "Poor"};
    String[] arrayHVAC = {"Choose an item", "Yes", "No", "No, only individual ACs installed", "No information available since property was closed at the time of survey", "No information available since survey couldn't be done from inside"};
    String[] arrayGarden = {"Choose an item", "Yes", "No", "No, only individual ACs installed", "No information available since property was closed at the time of survey", "No information available since survey couldn't be done from inside"};
    String[] arrayPB = {"Choose an item", "Yes", "No", "No, only individual ACs installed", "No information available since property was closed at the time of survey", "No information available since survey couldn't be done from inside"};

    //    String[] arrayPbCondition = {"Choose an item","Good","Average","Poor"};
    String[] arrayHvacCond = {"Choose an item", "Good", "Average", "Poor"};
    String[] arrayPBCond = {"Choose an item", "Good", "Average", "Poor"};

    String[] arraySecurityHut = {"Choose an item", "Yes", "No"};

    String[] arrayPavements = {"Choose an item", "Yes", "No"};

    String[] arrayFtMtr = {"Ft.", "Mtr."};

    SpinnerAdapter spinnerAdapter;

    String typeSurveyChecker;

    LinearLayout llSecurityDesc, llPavementDesc;
    Preferences pref;
    // ProgressBar progressbar;

    //   TextView tv_caseheader,tv_caseid,tv_header;

    // RelativeLayout rl_casedetail;

    View v;

//    LinearLayout llInverter,llDGSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_generalform9, container, false);

        getid(v);

        setListener();
        populateSinner();
        conditions();

//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));
//
//        progressbar.setMax(10);
//        progressbar.setProgress(8);
        Arrays.sort(arrayType, 1, arrayType.length);
        Arrays.sort(arrayFinish, 1, arrayFinish.length);

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!edit_page) {
            setEditiblity();
        }

        return v;
    }


    public void setEditiblity() {
        cbGarBeautiful.setClickable(false);
        cbGarOrdinary.setClickable(false);

        rgBoundaryWall.setClickable(false);
        rgLift.setClickable(false);
        rgLift1.setClickable(false);

        rgParking.setClickable(false);

        rgAvailableWithin.setClickable(false);
        rgNotAvailableWithin.setClickable(false);

        spinnerHvac.setEnabled(false);
        spinnerGarden.setEnabled(false);
        spinnerPB.setEnabled(false);
        spinnerHvacCondition.setEnabled(false);
        spinnerPBCondition.setEnabled(false);
        spinnerType.setEnabled(false);
        spinnerFinish.setEnabled(false);
        spinnerLiftCondition.setEnabled(false);

        spinnerSecurityHut.setEnabled(false);
        spinnerPavements.setEnabled(false);
        spinnerBoundaryHeightFtMtr.setEnabled(false);

        etBoundaryRunningMtr.setEnabled(false);
        etBoundaryHeight.setEnabled(false);
        etBoundaryWidth.setEnabled(false);
        etLiftMake.setEnabled(false);
        etLiftCapacity.setEnabled(false);

        etSecurityHutDesc.setEnabled(false);
        etPavementsDesc.setEnabled(false);
        etSpecialComments.setEnabled(false);
        etHVACMake.setEnabled(false);
        etPowerMake.setEnabled(false);
        etPbCapacity.setEnabled(false);
        etHVACCapacity.setEnabled(false);

        for (int i = 0; i < rgBoundaryWall.getChildCount(); i++)
            rgBoundaryWall.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgLift.getChildCount(); i++)
            rgLift.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgLift1.getChildCount(); i++)
            rgLift1.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgParking.getChildCount(); i++)
            rgParking.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgAvailableWithin.getChildCount(); i++)
            rgAvailableWithin.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgNotAvailableWithin.getChildCount(); i++)
            rgNotAvailableWithin.getChildAt(i).setClickable(false);


    }

    public void getid(View v) {
//        tv_header = findViewById(R.id.tv_header);
//        rl_casedetail = findViewById(R.id.rl_casedetail);
//        tv_caseheader = findViewById(R.id.tv_caseheader);
//        tv_caseid = findViewById(R.id.tv_caseid);
//
//        progressbar = (ProgressBar)findViewById(R.id.Progress);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        // back = findViewById(R.id.rl_back);
        next = v.findViewById(R.id.next);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        //  dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);
        llNotAvailableWithin = (LinearLayout) v.findViewById(R.id.llNotAvailableWithin);
        llAvailableWithin = (LinearLayout) v.findViewById(R.id.llAvailableWithin);
        llhvac = (LinearLayout) v.findViewById(R.id.llhvac);
        llGarden = (LinearLayout) v.findViewById(R.id.llGarden);
        llPowerB = (LinearLayout) v.findViewById(R.id.llPowerB);
        llLiftElevator = (LinearLayout) v.findViewById(R.id.llLiftElevator);
        // dots = (RelativeLayout) v.findViewById(R.id.rl_dots);
        rlSpinnerFinish = (RelativeLayout) v.findViewById(R.id.rlSpinnerFinish);
        rlSpinnerType = (RelativeLayout) v.findViewById(R.id.rlSpinnerType);
        rlBoundaryWidth = (RelativeLayout) v.findViewById(R.id.rlBoundaryWidth);
        rlBoundaryHeight = (RelativeLayout) v.findViewById(R.id.rlBoundaryHeight);
        rlBoundaryRunning = (RelativeLayout) v.findViewById(R.id.rlBoundaryRunning);
        rlLiftMake = (RelativeLayout) v.findViewById(R.id.rlLiftMake);
        rlLiftCapacity = (RelativeLayout) v.findViewById(R.id.rlLiftCapacity);
        rlLiftCondition = (RelativeLayout) v.findViewById(R.id.rlLiftCondition);
        rgBoundaryWall = v.findViewById(R.id.rgBoundaryWalls);
//        rlPBInverterMake =  v.findViewById(R.id.rlPBInverterMake);
//        rlPBInverterCapacity =  v.findViewById(R.id.rlPBInverterCapacity);
//        rlPBInverterCondition =  v.findViewById(R.id.rlPBInverterCondition);
        spinnerHvac = v.findViewById(R.id.spinnerHvac);
        spinnerGarden = v.findViewById(R.id.spinnerGarden);
        spinnerPB = v.findViewById(R.id.spinnerPB);
        spinnerHvacCondition = v.findViewById(R.id.spinnerHvacCondition);
        spinnerPBCondition = v.findViewById(R.id.spinnerPBCondition);

////        rlPBDGMake=  v.findViewById(R.id.rlPBDGMake);
//        rlPBDGCapacity=  v.findViewById(R.id.rlPBDGCapacity);
//        rlPBDGCondition=  v.findViewById(R.id.rlPBDGCondition);

//        llInverter=  v.findViewById(R.id.llInverter);
//        llDGSet=  v.findViewById(R.id.llDGSet);

        //RadioGroup

        rgLift = v.findViewById(R.id.rgLift);
        rgLift1 = v.findViewById(R.id.rgLift1);
//        rgGarden = v.findViewById(R.id.rgGarden);
        rgParking = v.findViewById(R.id.rgParking);
        rgAvailableWithin = v.findViewById(R.id.rgAvailableWithin);
        rgNotAvailableWithin = v.findViewById(R.id.rgNotAvailableWithin);

        //EditText
        etBoundaryRunningMtr = v.findViewById(R.id.etBoundaryRunningMtr);
        etBoundaryHeight = v.findViewById(R.id.etBoundaryHeight);
        etBoundaryWidth = v.findViewById(R.id.etBoundaryWidth);
        etLiftMake = v.findViewById(R.id.etLiftMake);
        etLiftCapacity = v.findViewById(R.id.etLiftCapacity);
        cbGarBeautiful = v.findViewById(R.id.cbGarBeautiful);
        cbGarOrdinary = v.findViewById(R.id.cbGarOrdinary);
//        etPBInverterMake = v.findViewById(R.id.etPBInverterMake);
//        etPBInverterCapacity = v.findViewById(R.id.etPBInverterCapacity);
//        etPBDGMake = v.findViewById(R.id.etPBDGMake);
//        etPBDGCapacity = v.findViewById(R.id.etPBDGCapacity);
        etSecurityHutDesc = v.findViewById(R.id.etSecurityHutDesc);
        etPavementsDesc = v.findViewById(R.id.etPavementsDesc);
        etSpecialComments = v.findViewById(R.id.etSpecialComments);
        etHVACMake = v.findViewById(R.id.etHVACMake);
        etPowerMake = v.findViewById(R.id.etPowerMake);
        etPbCapacity = v.findViewById(R.id.etPbCapacity);
        etHVACCapacity = v.findViewById(R.id.etHVACCapacity);

        //CheckBox
//        cbInverter = v.findViewById(R.id.cbInverter);
//        cbDgSet = v.findViewById(R.id.cbDgSet);
//        cbGardenYes = v.findViewById(R.id.cbGardenYes);
//        cbGardenNo = v.findViewById(R.id.cbGardenNo);
//        cbGardenBeautiful = v.findViewById(R.id.cbGardenBeautiful);
//        cbGardenOrdinary = v.findViewById(R.id.cbGardenOrdinary);
        //cbAvailableWithin = v.findViewById(R.id.cbAvailableWithin);
//        cbOnGround = v.findViewById(R.id.cbOnGround);
//        cbInBasement = v.findViewById(R.id.cbInBasement);
//        cbOnStilt = v.findViewById(R.id.cbOnStilt);
        //  cbNotAvailableWithin = v.findViewById(R.id.cbNotAvailableWithin);
//        cbOnRoad = v.findViewById(R.id.cbOnRoad);
//        cbAcuteParking = v.findViewById(R.id.cbAcuteParking);
//        cbNoPowerBackup = v.findViewById(R.id.cbNoPowerBackup);

        spinnerType = v.findViewById(R.id.spinnerType);
        spinnerFinish = v.findViewById(R.id.spinnerFinish);
        spinnerLiftCondition = v.findViewById(R.id.spinnerLiftCondition);
//        spinnerPBInverterCondition = v.findViewById(R.id.spinnerPBInverterCondition);
//        spinnerPBDGCondition = v.findViewById(R.id.spinnerPBDGCondition);
        spinnerSecurityHut = v.findViewById(R.id.spinnerSecurityHut);
        spinnerPavements = v.findViewById(R.id.spinnerPavements);
        spinnerBoundaryHeightFtMtr = v.findViewById(R.id.spinnerBoundaryHeightFtMtr);

        llSecurityDesc = v.findViewById(R.id.llSecurityDesc);
        llPavementDesc = v.findViewById(R.id.llPavementDesc);

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayFtMtr);
        spinnerBoundaryHeightFtMtr.setAdapter(spinnerAdapter);
    }

    public void setListener() {
        //  back.setOnClickListener(this);
        next.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        // dots.setOnClickListener(this);

        rgBoundaryWall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int pos = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (pos == 1 || pos == 2) {
                        etBoundaryRunningMtr.setEnabled(false);
                        etBoundaryHeight.setEnabled(false);
                        etBoundaryWidth.setEnabled(false);

                        spinnerFinish.setEnabled(false);
                        spinnerType.setEnabled(false);

                        rlSpinnerFinish.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                        rlSpinnerType.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                        rlBoundaryRunning.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                        rlBoundaryHeight.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                        rlBoundaryWidth.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                    } else {
                        etBoundaryRunningMtr.setEnabled(true);
                        etBoundaryHeight.setEnabled(true);
                        etBoundaryWidth.setEnabled(true);

                        spinnerFinish.setEnabled(true);
                        spinnerType.setEnabled(true);

                        rlSpinnerFinish.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
                        rlSpinnerType.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
                        rlBoundaryRunning.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
                        rlBoundaryHeight.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
                        rlBoundaryWidth.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
                    }
                }
            }
        });

        rgLift.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int pos = radioGroup.indexOfChild(radioButton);

                if (pos > -1) {

                    llLiftElevator.setVisibility(View.VISIBLE);
                    if (radioButton.isChecked()) {
                        if (pos == 2) {
                            etLiftMake.setEnabled(false);
                            etLiftCapacity.setEnabled(false);
                            spinnerLiftCondition.setEnabled(false);

                            rlLiftCapacity.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                            rlLiftCondition.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                            rlLiftMake.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));

                        } else {
                            etLiftMake.setEnabled(true);
                            etLiftCapacity.setEnabled(true);
                            spinnerLiftCondition.setEnabled(true);

                            rlLiftCapacity.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
                            rlLiftCondition.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
                            rlLiftMake.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
                        }
                        rgLift1.getChildAt(0).setEnabled(false);
                        rgLift1.getChildAt(1).setEnabled(false);
                        rgLift1.clearCheck();
                    }
                }
            }
        });
        rgLift1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int pos = radioGroup.indexOfChild(radioButton);
                if (pos > -1) {

                    llLiftElevator.setVisibility(View.GONE);
                    if (radioButton.isChecked()) {

                        etLiftMake.setEnabled(false);
                        etLiftCapacity.setEnabled(false);
                        spinnerLiftCondition.setEnabled(false);

                        rlLiftCapacity.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                        rlLiftCondition.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
                        rlLiftMake.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));

                        rgLift.getChildAt(0).setEnabled(false);
                        rgLift.getChildAt(1).setEnabled(false);
                        rgLift.getChildAt(2).setEnabled(false);
                        rgLift.clearCheck();
                    }
                }
            }
        });


//        cbInverter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    llInverter.setVisibility(View.VISIBLE);
//                    cbNoPowerBackup.setChecked(false);
//                }
//                else
//                {
//                    llInverter.setVisibility(View.GONE);
//                }
//            }
//        });

        cbGarBeautiful.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    garBeauty = "Beautiful";
                } else {
                    garBeauty = "";
                }
            }
        });
        cbGarOrdinary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    garOrdinary = "Ordinary";
                } else {
                    garOrdinary = "";
                }
            }
        });


//        cbDgSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    llDGSet.setVisibility(View.VISIBLE);
//                    cbNoPowerBackup.setChecked(false);
//                }
//                else
//                {
//                    llDGSet.setVisibility(View.GONE);
//                }
//            }
//        });


//        cbNoPowerBackup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//
//                    cbDgSet.setChecked(false);
//                    cbInverter.setChecked(false);
//
////                    llInverter.setVisibility(View.GONE);
////                    llDGSet.setVisibility(View.GONE);
//
////                    etPBInverterMake.setEnabled(false);
////                    etPBInverterCapacity.setEnabled(false);
////                    spinnerPBInverterCondition.setEnabled(false);
////
////                    etPBDGMake.setEnabled(false);
////                    etPBDGCapacity.setEnabled(false);
////                    spinnerPBDGCondition.setEnabled(false);
//
////                    rlPBInverterCapacity.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
////                    rlPBInverterCondition.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
////                    rlPBInverterMake.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
//
////                    rlPBDGCapacity.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
////                    rlPBDGCondition.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
////                    rlPBDGMake.setBackground(getResources().getDrawable(R.drawable.rectangle_corner_disabled));
//
//                }else {
//                    etPBInverterMake.setEnabled(true);
//                    etPBInverterCapacity.setEnabled(true);
//                    spinnerPBInverterCondition.setEnabled(true);
//
//                    etPBDGMake.setEnabled(true);
//                    etPBDGCapacity.setEnabled(true);
//                    spinnerPBDGCondition.setEnabled(true);
//
////                    rlPBInverterCapacity.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
////                    rlPBInverterCondition.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
////                    rlPBInverterMake.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
//
////                    rlPBDGCapacity.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
////                    rlPBDGCondition.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
////                    rlPBDGMake.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
//                }
//            }
//        });

        rgParking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int pos = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (pos == 0) {
                        llAvailableWithin.setVisibility(View.VISIBLE);
                        llNotAvailableWithin.setVisibility(View.GONE);
                    } else if (pos == 2) {
                        llAvailableWithin.setVisibility(View.GONE);
                        llNotAvailableWithin.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

     /*   cbNoPowerBackup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    cbDgSet.setChecked(false);
                    cbInverter.setChecked(false);
                }
            }
        });
*/
        /*cbDgSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    cbNoPowerBackup.setChecked(false);
                }
            }
        });

        cbInverter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    cbNoPowerBackup.setChecked(false);
                }
            }
        });*/


        spinnerHvac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                  @Override
                                                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                      spinHvac = spinnerHvac.getSelectedItemPosition();
//                                                           Log.v("shapeSelected", shapeSelected);
                                                      switch (spinHvac) {
                                                          case 1: {
                                                              llhvac.setVisibility(View.VISIBLE);
                                                              etHVACMake.setText(makeHvac);
                                                              etHVACCapacity.setText(capHvac);
                                                              spinnerHvacCondition.setSelection(spinHvCon);
                                                              break;
                                                          }
                                                          default: {
                                                              llhvac.setVisibility(View.GONE);
                                                              etHVACMake.setText("");
                                                              etHVACCapacity.setText("");
                                                              spinnerHvacCondition.setSelection(0);


                                                          }

                                                      }//selected_property = String.valueOf(property_array_list.get(i));
//                                                              selected_unit3 = unit_array_list.get(i).get("symbol");

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                  }

                                                  @Override
                                                  public void onNothingSelected(AdapterView<?> adapterView) {

                                                  }
                                              }
        );
        spinnerHvacCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                           @Override
                                                           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                               spinHvCon = spinnerHvacCondition.getSelectedItemPosition();
                                                           }

                                                           @Override
                                                           public void onNothingSelected(AdapterView<?> adapterView) {

                                                           }
                                                       }
        );
        spinnerGarden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                        spinGarden = spinnerGarden.getSelectedItemPosition();
//                                                           Log.v("shapeSelected", shapeSelected);
                                                        switch (spinGarden) {
                                                            case 1: {
                                                                llGarden.setVisibility(View.VISIBLE);
                                                                if (garBeauty.equalsIgnoreCase("Beautiful")) {
                                                                    cbGarBeautiful.setChecked(true);
                                                                } else {
                                                                    cbGarBeautiful.setChecked(false);
                                                                }
                                                                if (garOrdinary.equalsIgnoreCase("Ordinary")) {
                                                                    cbGarOrdinary.setChecked(true);
                                                                } else {
                                                                    cbGarOrdinary.setChecked(false);
                                                                }
                                                                break;
                                                            }
                                                            default: {
                                                                llGarden.setVisibility(View.GONE);
                                                                garBeauty = "";
                                                                garOrdinary = "";


                                                            }

                                                        }//selected_property = String.valueOf(property_array_list.get(i));
//                                                              selected_unit3 = unit_array_list.get(i).get("symbol");

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                                    }
                                                }
        );

        spinnerPBCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                         @Override
                                                         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                             spinPBCon = spinnerPBCondition.getSelectedItemPosition();
                                                         }

                                                         @Override
                                                         public void onNothingSelected(AdapterView<?> adapterView) {

                                                         }
                                                     }
        );


        spinnerPB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                    spinPB = spinnerPB.getSelectedItemPosition();
//                                                           Log.v("shapeSelected", shapeSelected);
                                                    switch (spinPB) {
                                                        case 1: {
                                                            llPowerB.setVisibility(View.VISIBLE);
                                                            etPowerMake.setText(makePB);
                                                            etPbCapacity.setText(capPB);
                                                            spinnerPBCondition.setSelection(spinPBCon);
                                                            break;
                                                        }
                                                        default: {
                                                            llPowerB.setVisibility(View.GONE);
                                                            etPowerMake.setText("");
                                                            etPbCapacity.setText("");
                                                            spinnerPBCondition.setSelection(0);


                                                        }

                                                    }//selected_property = String.valueOf(property_array_list.get(i));
//                                                              selected_unit3 = unit_array_list.get(i).get("symbol");

//                                                           hitGetAreaMeasurementApi(pref.get(Constants.case_id),property_array_list.get(i).get("id"));
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                }
                                            }
        );


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.rl_back:
                onBackPressed();
                break;*/

            case R.id.tvPrevious:
              /*  intent=new Intent(GeneralForm9.this,GeneralForm8.class);
                startActivity(intent);*/
                //  ((Dashboard)mActivity).displayView(13);
                mActivity.onBackPressed();
                break;

            case R.id.next:
                if (edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(15);

                break;

        /*    case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;*/
        }
    }

    private void validation() {
        makeHvac = etHVACMake.getText().toString();
        capHvac = etHVACCapacity.getText().toString();
        makePB = etPowerMake.getText().toString();
        capPB = etPbCapacity.getText().toString();
        if (rgBoundaryWall.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Boundary Wall", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvBound);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (((RadioButton) rgBoundaryWall.getChildAt(0)).isChecked() && etBoundaryRunningMtr.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Boundary Wall Running Mtr", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBoundaryRunningMtr.requestFocus();
        } else if (spinHvac == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Select HVAC Systems", Snackbar.LENGTH_SHORT);
            snackbar.show();
            spinnerHvac.requestFocus();


        } else if ((spinHvac == 1 && (spinHvCon == 0 || makeHvac.equalsIgnoreCase("") || capHvac.equalsIgnoreCase("")))) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter HVAC Systems Details", Snackbar.LENGTH_SHORT);
            snackbar.show();
            spinnerHvac.requestFocus();
        } else if (spinGarden == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Gardens and Landscapings", Snackbar.LENGTH_SHORT);
            snackbar.show();
            spinnerGarden.requestFocus();


        } else if ((spinGarden == 1 && (garBeauty.equalsIgnoreCase("") && garOrdinary.equalsIgnoreCase("")))) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Garden Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
            spinnerGarden.requestFocus();
        } else if (spinPB == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Select PowerBackup", Snackbar.LENGTH_SHORT);
            snackbar.show();
            spinnerPB.requestFocus();


        } else if ((spinPB == 1 && (spinPBCon == 0 || makePB.equalsIgnoreCase("") || capPB.equalsIgnoreCase("")))) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter PowerBackup Details", Snackbar.LENGTH_SHORT);
            snackbar.show();
            spinnerPB.requestFocus();
        } else if (((RadioButton) rgBoundaryWall.getChildAt(0)).isChecked() && etBoundaryHeight.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Boundary Wall Height", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBoundaryHeight.requestFocus();
        } else if (((RadioButton) rgBoundaryWall.getChildAt(0)).isChecked() && etBoundaryWidth.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Boundary Wall Width", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etBoundaryWidth.requestFocus();
        } else if (((RadioButton) rgBoundaryWall.getChildAt(0)).isChecked() && spinnerType.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvTypee);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (((RadioButton) rgBoundaryWall.getChildAt(0)).isChecked() && spinnerFinish.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvFin);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgLift.getCheckedRadioButtonId() == -1 && rgLift1.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Lift", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvLift);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if ((((RadioButton) rgLift.getChildAt(0)).isChecked()
                || ((RadioButton) rgLift.getChildAt(1)).isChecked())
                && etLiftMake.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Lift Make", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLiftMake.requestFocus();
        } else if ((((RadioButton) rgLift.getChildAt(0)).isChecked()
                || ((RadioButton) rgLift.getChildAt(1)).isChecked())
                && etLiftCapacity.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Lift Capacity", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etLiftCapacity.requestFocus();
        } else if ((((RadioButton) rgLift.getChildAt(0)).isChecked()
                || ((RadioButton) rgLift.getChildAt(1)).isChecked())
                && spinnerLiftCondition.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvLiftCond);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }
//        else if (!cbInverter.isChecked() && !cbDgSet.isChecked() && !cbNoPowerBackup.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Power Backup", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvPower);
//            targetView.getParent().requestChildFocus(targetView,targetView);
//
//        }
//        else if (cbInverter.isChecked()&& etPBInverterMake.getText().toString().isEmpty()){
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Make", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etPBInverterMake.requestFocus();
//        }
//        else if (cbInverter.isChecked() && etPBInverterCapacity.getText().toString().isEmpty()){
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Capacity", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etPBInverterCapacity.requestFocus();
//        }
//
//        else if (cbDgSet.isChecked()&& etPBDGMake.getText().toString().isEmpty()){
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Make", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etPBDGMake.requestFocus();
//        }
//        else if (cbDgSet.isChecked() && etPBDGCapacity.getText().toString().isEmpty()){
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Power Backup Capacity", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etPBDGCapacity.requestFocus();
//        }
//        else if (cbInverter.isChecked()&& spinnerPBInverterCondition.getSelectedItemPosition() == 0){
//            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvPbCond);
//            targetView.getParent().requestChildFocus(targetView,targetView);
//
//        }
//        else if (cbDgSet.isChecked()&& spinnerPBDGCondition.getSelectedItemPosition() == 0){
//            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvPbCond);
//            targetView.getParent().requestChildFocus(targetView,targetView);
//
//        }
//        else if (rgGarden.getCheckedRadioButtonId()==-1){
//            Snackbar snackbar = Snackbar.make(next, "Please Select Garden/ Landscaping", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.rgGarden);
//            targetView.getParent().requestChildFocus(targetView,targetView);
//
//        }
        else if (rgParking.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Parking facilities", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.rgParking);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }
      /*  else if (!cbGardenYes.isChecked()
                && !cbGardenNo.isChecked()
                && !cbGardenBeautiful.isChecked()
                && !cbGardenOrdinary.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Garden/ Landscaping", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvGard);
            targetView.getParent().requestChildFocus(targetView,targetView);

        }*/
        else if (llAvailableWithin.getVisibility() == View.VISIBLE && rgAvailableWithin.getCheckedRadioButtonId() == -1) {

            Snackbar snackbar = Snackbar.make(next, "Please Select Parking Facilities", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvPark);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llNotAvailableWithin.getVisibility() == View.VISIBLE && rgNotAvailableWithin.getCheckedRadioButtonId() == -1) {

            Snackbar snackbar = Snackbar.make(next, "Please Select Parking Facilities", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvPark);
            targetView.getParent().requestChildFocus(targetView, targetView);

        }
/*
        else if (!cbAvailableWithin.isChecked()
                && !cbOnGround.isChecked()
                && !cbInBasement.isChecked()
                && !cbOnStilt.isChecked()
                && !cbNotAvailableWithin.isChecked()
                && !cbOnRoad.isChecked()
                && !cbAcuteParking.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Parking Facilities", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvPark);
            targetView.getParent().requestChildFocus(targetView,targetView);

        }
*/
        else if (spinnerSecurityHut.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvSec);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llSecurityDesc.getVisibility() == View.VISIBLE && etSecurityHutDesc.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Security Hut Description", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSecurityHutDesc.requestFocus();
        } else if (spinnerPavements.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvPav);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llPavementDesc.getVisibility() == View.VISIBLE && etPavementsDesc.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Pavements Description", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etPavementsDesc.requestFocus();
        }
        /*else if (etSpecialComments.getText().toString().isEmpty()){
            Snackbar snackbar = Snackbar.make(next, "Please Enter Special Comments if any", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etSpecialComments.requestFocus();
        }*/

        else {
            putIntoHm();
            ((Dashboard) mActivity).displayView(15);
            return;
        }
    }

    public void putIntoHm() {
        String type = String.valueOf(spinnerType.getSelectedItemPosition());
        hm.put("typeSpinner", type);
        String finish = String.valueOf(spinnerFinish.getSelectedItemPosition());
        hm.put("finishSpinner", finish);
        String liftCondition = String.valueOf(spinnerLiftCondition.getSelectedItemPosition());
        hm.put("liftConditionSpinner", liftCondition);
//        String pbInverterCondition = String.valueOf(spinnerPBInverterCondition.getSelectedItemPosition());
//        hm.put("pbInvConditionSpinner",pbInverterCondition);
//        String pbDGCondition = String.valueOf(spinnerPBDGCondition.getSelectedItemPosition());
//        hm.put("pbDGConditionSpinner",pbDGCondition);
        String securityHut = String.valueOf(spinnerSecurityHut.getSelectedItemPosition());
        hm.put("securityHutSpinner", securityHut);
        String pavements = String.valueOf(spinnerPavements.getSelectedItemPosition());
        hm.put("pavementsSpinner", pavements);


        if (securityHut.equals("1")) {
            hm.put("securityHutDesc", etSecurityHutDesc.getText().toString().trim());
        } else {
            hm.put("securityHutDesc", "");
        }

        if (pavements.equals("1")) {
            hm.put("pavementsDesc", etPavementsDesc.getText().toString().trim());
        } else {
            hm.put("pavementsDesc", "");
        }

        hm.put("boundaryRunningMtr", etBoundaryRunningMtr.getText().toString().trim());
        hm.put("boundaryHeight", etBoundaryHeight.getText().toString().trim());
        hm.put("boundaryWidth", etBoundaryWidth.getText().toString().trim());
        hm.put("liftMake", etLiftMake.getText().toString().trim());
        hm.put("liftCapacity", etLiftCapacity.getText().toString().trim());
//        hm.put("powerBackupInverterMake", etPBInverterMake.getText().toString().trim());
//        hm.put("powerBackupInverterCapacity", etPBInverterCapacity.getText().toString().trim());
        hm.put("specialCommentsIfAny", etSpecialComments.getText().toString().trim());


//        hm.put("powerBackupDGMake", etPBDGMake.getText().toString().trim());
//        hm.put("powerBackupDGCapacity", etPBDGCapacity.getText().toString().trim());

//        if (cbInverter.isChecked()){
//            cbInverterCheck = 1;
//            hm.put("cbInverter", String.valueOf(cbInverterCheck));
//        }else
//        {
//            cbInverterCheck = 0;
//            hm.put("cbInverter", String.valueOf(cbInverterCheck));
//        }
//        if (cbNoPowerBackup.isChecked()){
//            cbNoPowerBackupCheck = 1;
//            hm.put("cbNoPowerBackup", String.valueOf(cbNoPowerBackupCheck));
//        }else
//        {
//            cbNoPowerBackupCheck = 0;
//            hm.put("cbNoPowerBackup", String.valueOf(cbNoPowerBackupCheck));
//        }
//
//
//        if (cbDgSet.isChecked()){
//            cbDgSetCheck = 1;
//            hm.put("cbDgSet", String.valueOf(cbDgSetCheck));
//        } else
//        {
//            cbDgSetCheck = 0;
//            hm.put("cbDgSet", String.valueOf(cbDgSetCheck));
//        }
        hm.put("HVACoption", Integer.toString(spinHvac));
        hm.put("HVACmake", makeHvac);
        hm.put("HVACcap", capHvac);
        hm.put("spinHvCon", Integer.toString(spinHvCon));

        hm.put("PBoption", Integer.toString(spinPB));
        hm.put("PBmake", makePB);
        hm.put("PBcap", capPB);
        hm.put("spinPBCon", Integer.toString(spinPBCon));

        hm.put("Gardenoption", Integer.toString(spinGarden));
        hm.put("garBeauty", garBeauty);
        hm.put("garOrdinary", garOrdinary);







        /*if (cbGardenYes.isChecked()){
            cbGardenYesCheck = 1;
            hm.put("cbGardenYes", String.valueOf(cbGardenYesCheck));
        }
        else
        {
            cbGardenYesCheck = 0;
            hm.put("cbGardenYes", String.valueOf(cbGardenYesCheck));
        }
        if (cbGardenNo.isChecked()) {
            cbGardenNoCheck = 1;
            hm.put("cbGardenNo", String.valueOf(cbGardenNoCheck));
        }
        else
        {
            cbGardenNoCheck = 0;
            hm.put("cbGardenNo", String.valueOf(cbGardenNoCheck));
        }
        if (cbGardenBeautiful.isChecked()){
            cbGardenBeautifulCheck = 1;
            hm.put("cbGardenBeautiful", String.valueOf(cbGardenBeautifulCheck));
        }
        else
        {
            cbGardenBeautifulCheck = 0;
            hm.put("cbGardenBeautiful", String.valueOf(cbGardenBeautifulCheck));
        }
        if (cbGardenOrdinary.isChecked()){
            cbGardenOrdinaryCheck = 1;
            hm.put("cbGardenOrdinary", String.valueOf(cbGardenOrdinaryCheck));
        }
        else
        {
            cbGardenOrdinaryCheck = 0;
            hm.put("cbGardenOrdinary", String.valueOf(cbGardenOrdinaryCheck));
        }*/


      /*  if (cbAvailableWithin.isChecked()){
            cbAvailableWithinCheck = 1;
            hm.put("cbAvailableWithinn", String.valueOf(cbAvailableWithinCheck));
        }else
        {
            cbAvailableWithinCheck = 0;
            hm.put("cbAvailableWithinn", String.valueOf(cbAvailableWithinCheck));
        }*/
       /* if (cbOnGround.isChecked()){
            cbOnGroundCheck = 1;
            hm.put("cbOnGroundd", String.valueOf(cbOnGroundCheck));
        } else
        {
            cbOnGroundCheck = 0;
            hm.put("cbOnGroundd", String.valueOf(cbOnGroundCheck));
        }
        if (cbInBasement.isChecked()){
            cbInBasementCheck = 1;
            hm.put("cbInBasemennt", String.valueOf(cbInBasementCheck));
        } else
        {
            cbInBasementCheck = 0;
            hm.put("cbInBasemennt", String.valueOf(cbInBasementCheck));
        }
        if (cbOnStilt.isChecked()) {
            cbOnStiltCheck = 1;
            hm.put("cbOnStiltt", String.valueOf(cbOnStiltCheck));
        } else
        {
            cbOnStiltCheck = 0;
            hm.put("cbOnStiltt", String.valueOf(cbOnStiltCheck));
        }*/
     /*   if (cbNotAvailableWithin.isChecked()){
            cbNotAvailableWithinCheck = 1;
            hm.put("cbNotAvailableWithhin", String.valueOf(cbNotAvailableWithinCheck));
        }
        else
        {
            cbNotAvailableWithinCheck = 0;
            hm.put("cbNotAvailableWithhin", String.valueOf(cbNotAvailableWithinCheck));
        }*/
        /*if (cbOnRoad.isChecked()){
            cbOnRoadCheck = 1;
            hm.put("cbOnRoaad", String.valueOf(cbOnRoadCheck));
        }else
        {
            cbOnRoadCheck = 0;
            hm.put("cbOnRoaad", String.valueOf(cbOnRoadCheck));
        }
        if (cbAcuteParking.isChecked()){
            cbAcuteParkingCheck = 1;
            hm.put("cbAcuteParkiing", String.valueOf(cbAcuteParkingCheck));
        }
        else
        {
            cbAcuteParkingCheck = 0;
            hm.put("cbAcuteParkiing", String.valueOf(cbAcuteParkingCheck));
        }*/

        int selectedIdBoundaryWall = rgBoundaryWall.getCheckedRadioButtonId();
        View radioButtonBoundaryWall = v.findViewById(selectedIdBoundaryWall);
        int idx = rgBoundaryWall.indexOfChild(radioButtonBoundaryWall);
        RadioButton r = (RadioButton) rgBoundaryWall.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonBoundaryWalll", selectedText);

        int selectedIdLift = rgLift.getCheckedRadioButtonId();
        View radioButtonLift = v.findViewById(selectedIdLift);
        int idx2 = rgLift.indexOfChild(radioButtonLift);
        if (idx2 > -1) {
            RadioButton r2 = (RadioButton) rgLift.getChildAt(idx2);
            String selectedText2 = r2.getText().toString();
            hm.put("radioButtonLifft", selectedText2);
        }

        int selectedIdLift1 = rgLift1.getCheckedRadioButtonId();
        View radioButtonLift1 = v.findViewById(selectedIdLift1);
        int idx21 = rgLift1.indexOfChild(radioButtonLift1);
        if (idx21 > -1) {
            RadioButton r21 = (RadioButton) rgLift1.getChildAt(idx21);
            String selectedText21 = r21.getText().toString();
            hm.put("radioButtonLifft1", selectedText21);
        }
//        int selectedIdGarden = rgGarden.getCheckedRadioButtonId();
//        View radioButtonGarden = v.findViewById(selectedIdGarden);
//        int idx22 = rgGarden.indexOfChild(radioButtonGarden);
//        RadioButton r22 = (RadioButton)  rgGarden.getChildAt(idx22);
//        String selectedText22 = r22.getText().toString();
//        hm.put("radioButtonGarden", String.valueOf(idx22));
//
        int selectedIdParking = rgParking.getCheckedRadioButtonId();
        View radioButtonParking = v.findViewById(selectedIdParking);
        int idx232 = rgParking.indexOfChild(radioButtonParking);
        RadioButton r232 = (RadioButton) rgParking.getChildAt(idx232);
        String selectedText232 = r232.getText().toString();
        hm.put("radioButtonParking", String.valueOf(idx232));

        if (llAvailableWithin.getVisibility() == View.VISIBLE) {
            int selectedIdAvail = rgAvailableWithin.getCheckedRadioButtonId();
            View radioButtonAvail = v.findViewById(selectedIdAvail);
            int idx432 = rgAvailableWithin.indexOfChild(radioButtonAvail);
            RadioButton r432 = (RadioButton) rgAvailableWithin.getChildAt(idx432);
            String selectedText432 = r432.getText().toString();
            hm.put("radioButtonAvailableWithin", String.valueOf(idx432));

        } else {
            hm.put("radioButtonAvailableWithin", "na");
        }

        if (llNotAvailableWithin.getVisibility() == View.VISIBLE) {
            int selectedIdNotAvail = rgNotAvailableWithin.getCheckedRadioButtonId();
            View radioButtonNotAvail = v.findViewById(selectedIdNotAvail);
            int idx532 = rgNotAvailableWithin.indexOfChild(radioButtonNotAvail);
            RadioButton r532 = (RadioButton) rgNotAvailableWithin.getChildAt(idx532);
            //   String selectedText532 = r532.getText().toString();
            hm.put("radioButtonNotAvail", String.valueOf(idx532));
        } else {
            hm.put("radioButtonNotAvail", "na");
        }


        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        //DatabaseController.removeTable(TableGeneralForm.attachment);
        // DatabaseController.deleteColumnData("column1");

        ContentValues contentValues = new ContentValues();

        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column9.toString(), jsonArrayData.toString());

        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "9");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getTableOne("column9", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("column9"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);


                if (object.getString("typeSpinner").equals("0")) {
                    spinnerType.setSelection(0);
                } else if (object.getString("typeSpinner").equals("1")) {
                    spinnerType.setSelection(1);
                } else if (object.getString("typeSpinner").equals("2")) {
                    spinnerType.setSelection(2);
                } else if (object.getString("typeSpinner").equals("3")) {
                    spinnerType.setSelection(3);
                } else if (object.getString("typeSpinner").equals("4")) {
                    spinnerType.setSelection(4);
                } else if (object.getString("typeSpinner").equals("5")) {
                    spinnerType.setSelection(5);
                } else if (object.getString("typeSpinner").equals("6")) {
                    spinnerType.setSelection(6);
                } else if (object.getString("typeSpinner").equals("7")) {
                    spinnerType.setSelection(7);
                } else if (object.getString("typeSpinner").equals("8")) {
                    spinnerType.setSelection(8);
                } else if (object.getString("typeSpinner").equals("9")) {
                    spinnerType.setSelection(9);
                } else if (object.getString("typeSpinner").equals("10")) {
                    spinnerType.setSelection(10);
                }


                if (object.getString("finishSpinner").equals("0")) {
                    spinnerFinish.setSelection(0);
                } else if (object.getString("finishSpinner").equals("1")) {
                    spinnerFinish.setSelection(1);
                } else if (object.getString("finishSpinner").equals("2")) {
                    spinnerFinish.setSelection(2);
                } else if (object.getString("finishSpinner").equals("3")) {
                    spinnerFinish.setSelection(3);
                } else if (object.getString("finishSpinner").equals("4")) {
                    spinnerFinish.setSelection(4);
                } else if (object.getString("finishSpinner").equals("5")) {
                    spinnerFinish.setSelection(5);
                }


                if (object.getString("liftConditionSpinner").equals("0")) {
                    spinnerLiftCondition.setSelection(0);
                } else if (object.getString("liftConditionSpinner").equals("1")) {
                    spinnerLiftCondition.setSelection(1);
                } else if (object.getString("liftConditionSpinner").equals("2")) {
                    spinnerLiftCondition.setSelection(2);
                } else if (object.getString("liftConditionSpinner").equals("3")) {
                    spinnerLiftCondition.setSelection(3);
                }


//                if (object.getString("pbInvConditionSpinner").equals("0")){
//                    spinnerPBInverterCondition.setSelection(0);
//                }
//                else if (object.getString("pbInvConditionSpinner").equals("1")){
//                    spinnerPBInverterCondition.setSelection(1);
//                }
//                else if (object.getString("pbInvConditionSpinner").equals("2")){
//                    spinnerPBInverterCondition.setSelection(2);
//                }
//                else if (object.getString("pbInvConditionSpinner").equals("3")){
//                    spinnerPBInverterCondition.setSelection(3);
//                }
//
//                if (object.getString("pbDGConditionSpinner").equals("0")){
//                    spinnerPBDGCondition.setSelection(0);
//                }
//                else if (object.getString("pbDGConditionSpinner").equals("1")){
//                    spinnerPBDGCondition.setSelection(1);
//                }
//                else if (object.getString("pbDGConditionSpinner").equals("2")){
//                    spinnerPBDGCondition.setSelection(2);
//                }
//                else if (object.getString("pbDGConditionSpinner").equals("3")){
//                    spinnerPBDGCondition.setSelection(3);
//                }


                if (object.getString("securityHutSpinner").equals("0")) {
                    spinnerSecurityHut.setSelection(0);
                } else if (object.getString("securityHutSpinner").equals("1")) {
                    spinnerSecurityHut.setSelection(1);
                } else if (object.getString("securityHutSpinner").equals("2")) {
                    spinnerSecurityHut.setSelection(2);
                }

                if (object.getString("pavementsSpinner").equals("0")) {
                    spinnerPavements.setSelection(0);
                } else if (object.getString("pavementsSpinner").equals("1")) {
                    spinnerPavements.setSelection(1);
                } else if (object.getString("pavementsSpinner").equals("2")) {
                    spinnerPavements.setSelection(2);
                }


                etBoundaryRunningMtr.setText(object.getString("boundaryRunningMtr"));
                etBoundaryHeight.setText(object.getString("boundaryHeight"));
                etBoundaryWidth.setText(object.getString("boundaryWidth"));
                etLiftMake.setText(object.getString("liftMake"));
                etLiftCapacity.setText(object.getString("liftCapacity"));
//                etPBInverterMake.setText(object.getString("powerBackupInverterMake"));
//                etPBInverterCapacity.setText(object.getString("powerBackupInverterCapacity"));
//
//
//                etPBDGMake.setText(object.getString("powerBackupDGMake"));
//                etPBDGCapacity.setText(object.getString("powerBackupDGCapacity"));

                etSecurityHutDesc.setText(object.getString("securityHutDesc"));
                etPavementsDesc.setText(object.getString("pavementsDesc"));
                etSpecialComments.setText(object.getString("specialCommentsIfAny"));


//                if (object.getString("cbInverter").equals("1")){
//                    cbInverter.setChecked(true);
//                }else {
//                    cbInverter.setChecked(false);
//                }
//                if (object.getString("cbDgSet").equals("1")){
//                    cbDgSet.setChecked(true);
//                }else {
//                    cbDgSet.setChecked(false);
//                }
//                if (object.getString("cbNoPowerBackup").equals("1")){
//                    cbNoPowerBackup.setChecked(true);
//                }else {
//                    cbNoPowerBackup.setChecked(false);
//                }

                if (object.has("HVACoption")) {
                    spinHvac = Integer.parseInt(object.getString("HVACoption"));
                    spinnerHvac.setSelection(spinHvac);
                } else
                    spinHvac = 0;

                if (object.has("spinHvCon")) {
                    spinHvCon = Integer.parseInt(object.getString("spinHvCon"));
                    spinnerHvacCondition.setSelection(spinHvCon);
                } else
                    spinHvCon = 0;

                if (object.has("Gardenoption")) {
                    spinGarden = Integer.parseInt(object.getString("Gardenoption"));
                    spinnerGarden.setSelection(spinGarden);
                } else
                    spinGarden = 0;

                if (object.has("garBeauty")) {
                    garBeauty = object.getString("garBeauty");
                } else
                    garBeauty = "";

                if (object.has("garOrdinary")) {
                    garOrdinary = object.getString("garOrdinary");
                } else
                    garOrdinary = "";


                if (object.has("PBoption")) {
                    spinPB = Integer.parseInt(object.getString("PBoption"));
                    spinnerPB.setSelection(spinPB);
                } else
                    spinPB = 0;

                if (object.has("spinPBCon")) {
                    spinPBCon = Integer.parseInt(object.getString("spinPBCon"));
                    spinnerPBCondition.setSelection(spinPBCon);
                } else
                    spinPBCon = 0;

                if (object.has("HVACmake")) {
                    makeHvac = object.getString("HVACmake");
                } else
                    makeHvac = "";

                if (object.has("HVACcap")) {
                    capHvac = object.getString("HVACcap");
                } else
                    capHvac = "";


                if (object.has("PBmake")) {
                    makePB = object.getString("PBmake");
                } else
                    makePB = "";

                if (object.has("PBcap")) {
                    capPB = object.getString("PBcap");
                } else
                    capPB = "";



               /* if (object.getString("cbGardenYes").equals("1")){
                    cbGardenYes.setChecked(true);
                }else {
                    cbGardenYes.setChecked(false);
                }
                if (object.getString("cbGardenNo").equals("1")){
                    cbGardenNo.setChecked(true);
                }else {
                    cbGardenNo.setChecked(false);
                }
                if (object.getString("cbGardenBeautiful").equals("1")){
                    cbGardenBeautiful.setChecked(true);
                }else {
                    cbGardenBeautiful.setChecked(false);
                }
                if (object.getString("cbGardenOrdinary").equals("1")){
                    cbGardenOrdinary.setChecked(true);
                }else {
                    cbGardenOrdinary.setChecked(false);
                }*/
              /*  if (object.getString("cbAvailableWithinn").equals("1")){
                    cbAvailableWithin.setChecked(true);
                }else {
                    cbAvailableWithin.setChecked(false);
                }*/
           /*     if (object.getString("cbOnGroundd").equals("1")){
                    cbOnGround.setChecked(true);
                }else {
                    cbOnGround.setChecked(false);
                }
                if (object.getString("cbInBasemennt").equals("1")){
                    cbInBasement.setChecked(true);
                }else {
                    cbInBasement.setChecked(false);
                }
                if (object.getString("cbOnStiltt").equals("1")){
                    cbOnStilt.setChecked(true);
                }else {
                    cbOnStilt.setChecked(false);
                }*/
             /*   if (object.getString("cbNotAvailableWithhin").equals("1")){
                    cbNotAvailableWithin.setChecked(true);
                }else {
                    cbNotAvailableWithin.setChecked(false);
                }*/
           /*     if (object.getString("cbOnRoaad").equals("1")){
                    cbOnRoad.setChecked(true);
                }else {
                    cbOnRoad.setChecked(false);
                }
                if (object.getString("cbAcuteParkiing").equals("1")){
                    cbAcuteParking.setChecked(true);
                }else {
                    cbAcuteParking.setChecked(false);
                }*/


                if (object.getString("radioButtonBoundaryWalll").equals("Yes")) {
                    ((RadioButton) rgBoundaryWall.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonBoundaryWalll").equals("No")) {
                    ((RadioButton) rgBoundaryWall.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonBoundaryWalll").equals("Common boundary wall of a complex")) {
                    ((RadioButton) rgBoundaryWall.getChildAt(2)).setChecked(true);
                }

                if (object.has("radioButtonLifft")) {
                    if (object.getString("radioButtonLifft").equals("Passenger")) {
                        ((RadioButton) rgLift.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonLifft").equals("Commercial")) {
                        ((RadioButton) rgLift.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonLifft").equals("No Lift")) {
                        ((RadioButton) rgLift.getChildAt(2)).setChecked(true);
                    }
                }
                if (object.has("radioButtonLifft1")) {
                    if (object.getString("radioButtonLifft1").equals("No information available since property was closed at the time of survey")) {
                        ((RadioButton) rgLift1.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonLifft1").equals("No information available since survey couldn't be done from inside")) {
                        ((RadioButton) rgLift1.getChildAt(1)).setChecked(true);
                    }
                }


//                if (object.getString("radioButtonGarden").equals("0")){
//                    ((RadioButton)rgGarden.getChildAt(0)).setChecked(true);
//                }
//                else if (object.getString("radioButtonGarden").equals("1")){
//                    ((RadioButton)rgGarden.getChildAt(1)).setChecked(true);
//                }
//
//                else if (object.getString("radioButtonGarden").equals("2")){
//                    ((RadioButton)rgGarden.getChildAt(2)).setChecked(true);
//                }
//
//                else if (object.getString("radioButtonGarden").equals("3")){
//                    ((RadioButton)rgGarden.getChildAt(3)).setChecked(true);
//                }

//
                if (object.getString("radioButtonParking").equals("0")) {
                    ((RadioButton) rgParking.getChildAt(0)).setChecked(true);
                    llAvailableWithin.setVisibility(View.VISIBLE);
                    llNotAvailableWithin.setVisibility(View.GONE);
                } else if (object.getString("radioButtonParking").equals("2")) {
                    ((RadioButton) rgParking.getChildAt(2)).setChecked(true);
                    llAvailableWithin.setVisibility(View.GONE);
                    llNotAvailableWithin.setVisibility(View.VISIBLE);
                }

                //((RadioButton)rgParking.getChildAt(Integer.parseInt(object.getString("radioButtonGarden")))).setChecked(true);

                try {
                    if (!object.getString("radioButtonNotAvail").equalsIgnoreCase("na")) {
                        ((RadioButton) rgNotAvailableWithin.getChildAt(Integer.parseInt(object.getString("radioButtonNotAvail")))).setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                try {
                    if (!object.getString("radioButtonAvailableWithin").equalsIgnoreCase("na")) {
                        ((RadioButton) rgAvailableWithin.getChildAt(Integer.parseInt(object.getString("radioButtonAvailableWithin")))).setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    private void conditions() {
        spinnerSecurityHut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int s = adapterView.getSelectedItemPosition();
                if (s == 1) {
                    llSecurityDesc.setVisibility(View.VISIBLE);
                } else if (s == 0 || s == 2) {
                    llSecurityDesc.setVisibility(View.GONE);
                }

             /*   ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if(spinnerSecurityHut.getSelectedItemPosition() == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerPavements.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int s = adapterView.getSelectedItemPosition();
                if (s == 1) {
                    llPavementDesc.setVisibility(View.VISIBLE);
                } else if (s == 0 || s == 2) {
                    llPavementDesc.setVisibility(View.GONE);
                }

              /*  ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if(spinnerPavements.getSelectedItemPosition() == 0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void populateSinner() {

   /*     //Spinner IsLandMerged
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayType);

        spinnerType.setAdapter(adapter);*/
        spinnerAdapter = new SpinnerAdapter(mActivity, arrayType);
        spinnerType.setAdapter(spinnerAdapter);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerType.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ////
     /*   ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayFinish);

        spinnerFinish.setAdapter(adapter1);*/
        spinnerAdapter = new SpinnerAdapter(mActivity, arrayFinish);
        spinnerFinish.setAdapter(spinnerAdapter);


        spinnerFinish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              /*  ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerFinish.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }
*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ////
       /* ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayLiftCondition);

        spinnerLiftCondition.setAdapter(adapter2);*/

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayLiftCondition);
        spinnerLiftCondition.setAdapter(spinnerAdapter);

        spinnerLiftCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
//                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
//                if (spinnerLiftCondition.getSelectedItemPosition() == 0) {
//                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /////
        /*ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayPbCondition);

        spinnerPBInverterCondition.setAdapter(adapter3);*/
//        spinnerAdapter = new SpinnerAdapter(mActivity, arrayPbCondition);
//        spinnerPBInverterCondition.setAdapter(spinnerAdapter);
//
//        spinnerAdapter = new SpinnerAdapter(mActivity, arrayPbCondition);
//        spinnerPBDGCondition.setAdapter(spinnerAdapter);

//        spinnerPBInverterCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//               /* ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
//                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
//                if (spinnerPBInverterCondition.getSelectedItemPosition() == 0) {
//                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
//                }*/
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        ////
       /* ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arraySecurityHut);

        spinnerSecurityHut.setAdapter(adapter4);*/

        spinnerAdapter = new SpinnerAdapter(mActivity, arraySecurityHut);
        spinnerSecurityHut.setAdapter(spinnerAdapter);

   /*     spinnerSecurityHut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerSecurityHut.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        /////
        /*ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayPavements);

        spinnerPavements.setAdapter(adapter5);*/
        spinnerAdapter = new SpinnerAdapter(mActivity, arrayPavements);
        spinnerPavements.setAdapter(spinnerAdapter);


    /*    spinnerPavements.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //((TextView) adapterView.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen._7sdp));
                if (spinnerPavements.getSelectedItemPosition() == 0) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        spinnerAdapter = new SpinnerAdapter(mActivity, arrayHVAC);
        spinnerHvac.setAdapter(spinnerAdapter);
        spinnerHvac.setSelection(spinHvac);

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayHvacCond);
        spinnerHvacCondition.setAdapter(spinnerAdapter);
        spinnerHvacCondition.setSelection(spinHvCon);

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayGarden);
        spinnerGarden.setAdapter(spinnerAdapter);
        spinnerGarden.setSelection(spinGarden);


        spinnerAdapter = new SpinnerAdapter(mActivity, arrayPB);
        spinnerPB.setAdapter(spinnerAdapter);
        spinnerPB.setSelection(spinPB);

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayPBCond);
        spinnerPBCondition.setAdapter(spinnerAdapter);
        spinnerPBCondition.setSelection(spinPBCon);


    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        //if Required then only
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.general_one:
                        ((Dashboard) mActivity).displayView(6);
                        /*intent=new Intent(GeneralForm3.this, GeneralForm1.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_two:
                        ((Dashboard) mActivity).displayView(7);
                        return true;

                    case R.id.general_three:
                        ((Dashboard) mActivity).displayView(8);
                        return true;

                    case R.id.general_four:
                        ((Dashboard) mActivity).displayView(9);
                        return true;

                    case R.id.general_five:
                        ((Dashboard) mActivity).displayView(10);
                        return true;

                    case R.id.general_six:
                        ((Dashboard) mActivity).displayView(11);
                        return true;

                    case R.id.general_seven:
                        ((Dashboard) mActivity).displayView(12);
                        return true;

                    case R.id.general_eight:
                        ((Dashboard) mActivity).displayView(13);
                        return true;

                    case R.id.general_nine:
                        ((Dashboard) mActivity).displayView(14);
                        return true;

                    case R.id.general_ten:
                        ((Dashboard) mActivity).displayView(15);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}