package com.vis.android.Activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.vis.android.Activities.General.Fragments.GeneralForm1;
import com.vis.android.Activities.General.Fragments.GeneralForm10;
import com.vis.android.Activities.General.Fragments.GeneralForm11;
import com.vis.android.Activities.General.Fragments.GeneralForm2;
import com.vis.android.Activities.General.Fragments.GeneralForm3;
import com.vis.android.Activities.General.Fragments.GeneralForm4;
import com.vis.android.Activities.General.Fragments.GeneralForm5;
import com.vis.android.Activities.General.Fragments.GeneralForm6;
import com.vis.android.Activities.General.Fragments.GeneralForm7;
import com.vis.android.Activities.General.Fragments.GeneralForm8;
import com.vis.android.Activities.General.Fragments.GeneralForm9;
import com.vis.android.Activities.General.Fragments.InitiateSurveyForm;
import com.vis.android.Activities.MultiStory.Fragments.MSForm1;
import com.vis.android.Activities.MultiStory.Fragments.MSForm10;
import com.vis.android.Activities.MultiStory.Fragments.MSForm11;
import com.vis.android.Activities.MultiStory.Fragments.MSForm2;
import com.vis.android.Activities.MultiStory.Fragments.MSForm3;
import com.vis.android.Activities.MultiStory.Fragments.MSForm4;
import com.vis.android.Activities.MultiStory.Fragments.MSForm5;
import com.vis.android.Activities.MultiStory.Fragments.MSForm6;
import com.vis.android.Activities.MultiStory.Fragments.MSForm7;
import com.vis.android.Activities.Undertaking.UndertakingFragmentOne;
import com.vis.android.Activities.Undertaking.UndertakingFragmentTwo;
import com.vis.android.Activities.Vacant.Fragments.VacantForm1;
import com.vis.android.Activities.Vacant.Fragments.VacantForm10;
import com.vis.android.Activities.Vacant.Fragments.VacantForm11;
import com.vis.android.Activities.Vacant.Fragments.VacantForm2;
import com.vis.android.Activities.Vacant.Fragments.VacantForm3;
import com.vis.android.Activities.Vacant.Fragments.VacantForm4;
import com.vis.android.Activities.Vacant.Fragments.VacantForm5;
import com.vis.android.Activities.Vacant.Fragments.VacantForm6;
import com.vis.android.Activities.Vacant.Fragments.VacantForm7;
import com.vis.android.Activities.Vacant.Fragments.VacantForm9;
import com.vis.android.CallingService.LocationUpdateService;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.Extras.GetBackFragment;
import com.vis.android.Fragments.AboutUs;
import com.vis.android.Fragments.CaseDetail;
import com.vis.android.Fragments.ComSurveyFragment;
import com.vis.android.Fragments.DashboardFragment;
import com.vis.android.Fragments.Help;
import com.vis.android.Fragments.MapLocatorActivity;
import com.vis.android.Fragments.NewCasesFragment;
import com.vis.android.Fragments.Notification;
import com.vis.android.Fragments.PriliminaryActivity;
import com.vis.android.Fragments.ReassignedCasesListFragment;
import com.vis.android.Fragments.SkippedSurveysFragment;
import com.vis.android.Fragments.Survey_typeActivitiy;
import com.vis.android.Fragments.SurveysNotRequiredListFragment;
import com.vis.android.Login.LoginActivity;
import com.vis.android.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends BaseActivity implements View.OnClickListener {
    public static TextView tv_caseid, tv_caseheader;
    public static String notifi = "0";
    RelativeLayout menu, about_us, help, rlSkippedSurveys, rlSurveyNotRequired, rlReassignCases, rl_casedetail;
    DrawerLayout mDrawerLayout;
    RelativeLayout rlSideList;
    RelativeLayout logout, rl_dots;
    TextView tv_header_text, tv_header;
    ImageView filter, notify;
    int exit_check = 0;
    Intent intent;
    RelativeLayout home, Scompleted, rlNewCases;
    Preferences pref;
    CustomLoader loader;
    TextView username, mobile;
    ProgressBar Progress;
    Fragment fragment;
    ImageView ivAttachment;
    private Handler mHandler;
    private Dialog emailDialog;
    private UserInteractionListener userInteractionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getid();
        setListener();

        try {
            //  mActivity.stopService(new Intent(mActivity, LocationUpdateService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }


        String notificationFragment = getIntent().getStringExtra("notificationFragment");
        String NewCasesFragment = getIntent().getStringExtra("NewCasesFragment");


        // If menuFragment is defined, then this activity was launched with a fragment selection
        if (notificationFragment != null) {
            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (notificationFragment.equals("notificationFragment")) {
                loadFragment(new Notification());
                tv_header_text.setVisibility(View.VISIBLE);
                rl_casedetail.setVisibility(View.GONE);
                Progress.setVisibility(View.GONE);
            }
        } else if (NewCasesFragment != null) {
            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (NewCasesFragment.equals("NewCasesFragment")) {
                loadFragment(new NewCasesFragment());
                tv_header_text.setVisibility(View.VISIBLE);
                rl_casedetail.setVisibility(View.GONE);
                Progress.setVisibility(View.GONE);
            }
        } else {
            // Activity was not launched with a menuFragment selected -- continue as if this activity was opened from a launcher (for example)
            tv_header_text.setVisibility(View.VISIBLE);
            //loadFragment(new DashboardFragment());
                displayView(0);
//            displayView(5);

            try {
                username.setText(pref.get(Constants.surveyor_name));
                mobile.setText(pref.get(Constants.surveyor_contact));
            } catch (Exception exp) {

            }
        }

        if (getIntent().getStringExtra("frag") != null && getIntent().getStringExtra("frag").equalsIgnoreCase("mapLocator")) {
            displayView(3);
        }


    }


    public void getid() {
        mHandler = new Handler();
        pref = new Preferences(this);

        pref.set(Constants.owner_name_check, "");
        pref.commit();

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        menu = (RelativeLayout) findViewById(R.id.rl_sidemenu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        rlSideList = (RelativeLayout) findViewById(R.id.side_list);
        tv_header_text = (TextView) findViewById(R.id.tv_header_text);
        filter = (ImageView) findViewById(R.id.iv_filter);
        logout = (RelativeLayout) findViewById(R.id.rl_logout);
        home = findViewById(R.id.rl_home);
        username = findViewById(R.id.tv_name);
        mobile = findViewById(R.id.tv_mob);
        Scompleted = findViewById(R.id.rl_Scompleted);
        rlNewCases = findViewById(R.id.rlNewCases);
        about_us = findViewById(R.id.rl_about);
        help = findViewById(R.id.rl_help);
        notify = findViewById(R.id.iv_notify);
        rlSkippedSurveys = findViewById(R.id.rlSkippedSurveys);
        rlSurveyNotRequired = findViewById(R.id.rlSurveyNotRequired);
        rlReassignCases = findViewById(R.id.rlReassignCases);

        rl_casedetail = findViewById(R.id.rl_casedetail);
        Progress = findViewById(R.id.Progress);
        tv_caseheader = findViewById(R.id.tv_caseheader);
        tv_caseid = findViewById(R.id.tv_caseid);
        rl_dots = findViewById(R.id.rl_dots);

        Progress.setVisibility(View.GONE);
        Progress.setMax(10);
    }

    public void setListener() {
        menu.setOnClickListener(this);
        filter.setOnClickListener(this);
        logout.setOnClickListener(this);
        home.setOnClickListener(this);
        Scompleted.setOnClickListener(this);
        rlNewCases.setOnClickListener(this);
        about_us.setOnClickListener(this);
        help.setOnClickListener(this);
        notify.setOnClickListener(this);
        rlSkippedSurveys.setOnClickListener(this);
        rlSurveyNotRequired.setOnClickListener(this);
        rlReassignCases.setOnClickListener(this);
        rl_dots.setOnClickListener(this);
    }
    //====================================== Load Fragment ============================================//

    private void loadFragment(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frame, frag);
        fragmentTransaction.commit();
    }

    public void displayView(int position) {
        switch (position) {
            case 0:
                GetBackFragment.Addpos(position);
                fragment = new DashboardFragment();
                tv_header_text.setVisibility(View.VISIBLE);
                rl_casedetail.setVisibility(View.GONE);
                Progress.setVisibility(View.GONE);
                rl_dots.setVisibility(View.GONE);
                notify.setVisibility(View.VISIBLE);

                break;

            case 1:
                GetBackFragment.Addpos(position);
                // tv_header_text.setText("");
                tv_caseheader.setText("Case Details");
                Log.e("notification","noti");
                fragment = new CaseDetail();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.VISIBLE);

                break;

            case 2:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Preliminary Details");

                fragment = new PriliminaryActivity();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.VISIBLE);

                break;

            case 3:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Map Locator");

                fragment = new MapLocatorActivity();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.VISIBLE);

                break;

            case 4:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Initiate Survey");

                fragment = new InitiateSurveyForm();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.VISIBLE);

                break;

            case 5:

//               ? if(pref.get(Constants.surveyChoosen).equalsIgnoreCase("")) {
                    GetBackFragment.Addpos(position);
                    tv_caseheader.setText("Survey");

                    fragment = new Survey_typeActivitiy();
                    tv_header_text.setVisibility(View.GONE);
                    rl_casedetail.setVisibility(View.VISIBLE);
                    Progress.setVisibility(View.VISIBLE);
                    rl_dots.setVisibility(View.VISIBLE);
                    notify.setVisibility(View.VISIBLE);
//                }else
//                    displayView(6);
//                    displayView(Integer.parseInt(pref.get(Constants.surveyChoosen)));
                break;

            case 6:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm1();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(1);

                break;

            case 7:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm2();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(2);

                break;

            case 8:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm3();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(3);

                break;

            case 9:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm4();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(4);

                break;

            case 10:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm5();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(5);

                break;

            case 11:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm6();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(6);

                break;

            case 12:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm7();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(7);

                break;

            case 13:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm8();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(8);

                break;

            case 14:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm9();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(9);

                break;

            case 15:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm10();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(10);

                break;

            case 16:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm11();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(10);

                break;

            case 17:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Undertaking Form");

                fragment = new UndertakingFragmentOne();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.GONE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(10);

                break;

            case 18:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Undertaking Form");

                fragment = new UndertakingFragmentTwo();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.GONE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(10);

                break;
            //************************Vacant Forms****************************************
            case 19:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm1();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(1);
                break;
            case 20:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm2();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(2);

                break;
            case 21:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm3();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(3);

                break;

            case 22:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm4();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(4);

                break;

            case 23:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm5();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(5);

                break;

            case 24:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm6();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(6);

                break;

            case 25:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm7();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(7);

                break;

            case 26:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm9();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(8);

                break;

            case 27:
                GetBackFragment.Addpos(position);
                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm10();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(9);

                break;

            case 28:
                GetBackFragment.Addpos(position);
                tv_caseheader.setText("Vacant Land Form");

                fragment = new VacantForm11();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(10);

                break;

            /*case 29:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("General Survey Form");

                fragment = new GeneralForm11();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(10);

                break;

            case 30:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Undertaking Form");

                fragment = new UndertakingFragmentOne();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.GONE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(10);

                break;

            case 31:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("Undertaking Form");

                fragment = new UndertakingFragmentTwo();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.GONE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(10);*/


            //************************MultiStory Forms****************************************
            case 32:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm1();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(1);
                break;
            case 33:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm2();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(2);

                break;
            case 34:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm3();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(3);

                break;

            case 35:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm4();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(4);

                break;

            case 36:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm5();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(5);

                break;

            case 37:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm6();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(6);

                break;

            case 38:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm7();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(7);

                break;

          /*  case 39:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm8();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(8);

                break;
            case 40:
                GetBackFragment.Addpos(position);

                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm9();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(8);

                break;*/

            case 41:
                GetBackFragment.Addpos(position);
                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm10();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(9);

                break;
case 42:
                GetBackFragment.Addpos(position);
                tv_caseheader.setText("MultiStoried Form");

                fragment = new MSForm11();
                tv_header_text.setVisibility(View.GONE);
                rl_casedetail.setVisibility(View.VISIBLE);
                Progress.setVisibility(View.VISIBLE);
                rl_dots.setVisibility(View.VISIBLE);
                notify.setVisibility(View.GONE);

                Progress.setProgress(9);

                break;


        }

        if (fragment != null) {

            //Utils.hideKeyboard(mActivity);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.popBackStack(fragment.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

            FragmentTransaction tx = fragmentManager.beginTransaction();

            //tx.add(R.id.container,fragment).addToBackStack(fragment.toString());
            tx.replace(R.id.frame, fragment).addToBackStack(fragment.toString());

            tx.commit();
            // ====to clear unused memory==

            closeDrawer();

            System.gc();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_dots:
                if (CaseDetail.status.equals("1")) {
                    Toast.makeText(mActivity, "Please accept the case first", Toast.LENGTH_SHORT).show();
                } else {
                    showPopup(view);
                }

                break;

            case R.id.rl_sidemenu:
                openDrawer();
                break;

            case R.id.iv_filter:
                FilterDialog();
                break;

            case R.id.rl_logout:
                closeDrawer();
                LogoutDialog();
                break;

            case R.id.rl_home:
                closeDrawer();
                tv_header_text.setVisibility(View.VISIBLE);
                rl_casedetail.setVisibility(View.GONE);
                tv_header_text.setText("Dashboard");
                loadFragment(new DashboardFragment());
                break;

            case R.id.rl_Scompleted:
                closeDrawer();
                tv_header_text.setVisibility(View.VISIBLE);
                rl_casedetail.setVisibility(View.GONE);
                tv_header_text.setText("Completed Surveys");
                loadFragment(new ComSurveyFragment());
                break;

            case R.id.rlNewCases:
                closeDrawer();
                tv_header_text.setVisibility(View.VISIBLE);
                tv_header_text.setText("New Cases");
                rl_casedetail.setVisibility(View.GONE);
                loadFragment(new NewCasesFragment());
                break;

            case R.id.rl_about:
                closeDrawer();
                tv_header_text.setVisibility(View.VISIBLE);
                tv_header_text.setText("About Us");
                rl_casedetail.setVisibility(View.GONE);
                loadFragment(new AboutUs());
                break;

            case R.id.rl_help:
                closeDrawer();
                tv_header_text.setVisibility(View.VISIBLE);
                tv_header_text.setText("Help");
                rl_casedetail.setVisibility(View.GONE);
                loadFragment(new Help());
                break;

            case R.id.iv_notify:
                tv_header_text.setVisibility(View.VISIBLE);
                tv_header_text.setText("Notification");
                rl_casedetail.setVisibility(View.GONE);
                loadFragment(new Notification());
                break;

            case R.id.rlSkippedSurveys:
                closeDrawer();
                tv_header_text.setVisibility(View.VISIBLE);
                tv_header_text.setText("Skipped Surveys");
                rl_casedetail.setVisibility(View.GONE);
                loadFragment(new SkippedSurveysFragment());
                break;

            case R.id.rlSurveyNotRequired:
                closeDrawer();
                tv_header_text.setVisibility(View.VISIBLE);
                tv_header_text.setText("Surveys Not Required");
                rl_casedetail.setVisibility(View.GONE);
                loadFragment(new SurveysNotRequiredListFragment());
                break;

            case R.id.rlReassignCases:
                closeDrawer();
                tv_header_text.setVisibility(View.VISIBLE);
                rl_casedetail.setVisibility(View.GONE);
                tv_header_text.setText("Reassigned Cases");
                loadFragment(new ReassignedCasesListFragment());
                break;

            default:
                break;
        }
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(rlSideList);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        try {
            int ast = GetBackFragment.Lastpos();

            Log.v("asasfas", String.valueOf(ast));

            if (tv_header_text.getVisibility() == View.VISIBLE && tv_header_text.getText().toString().equalsIgnoreCase("Dashboard")) {
                if (exit_check == 0) {
                    Toast.makeText(this, "Please Press again to exit", Toast.LENGTH_SHORT).show();
                    exit_check = 1;
                } else {
                    finish();
                    finishAffinity();
                }
            } else if (tv_caseheader.getText().toString().equalsIgnoreCase("Preliminary Details")) {
                displayView(1);
                exit_check = 0;
            } else if (tv_caseheader.getText().toString().equalsIgnoreCase("Map Locator")) {
                displayView(2);
                exit_check = 0;
            } else if (tv_caseheader.getText().toString().equalsIgnoreCase("Case Details")) {
                displayView(0);
                exit_check = 0;
            } else if (ast == -1) {
                if (exit_check == 0) {
                    Toast.makeText(this, "Please Press again to exit", Toast.LENGTH_SHORT).show();
                    exit_check = 1;
                } else {
                    finish();
                    finishAffinity();
                }
            } else {
                if (ast == 1) {
                    long g = GetBackFragment.LastUUID();
                    if (g != 0) {

                    }
                }else if(ast==5){
                    ast=4;
                }
                displayView(ast);
                GetBackFragment.Removepos();
                GetBackFragment.Removepos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDrawer() {

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mDrawerLayout.closeDrawer(rlSideList);
            }
        }, 100);
    }


    //StartConfirmation popup
    public void LogoutDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.logout_dialog);

        dialog.show();

        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                if (Utils.isNetworkConnectedMainThred(Dashboard.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    Hit_Logout_Api();

                } else {
                    Toast.makeText(Dashboard.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
//*********************************** Logout api ************************************//


    private void Hit_Logout_Api() {

        String url = Utils.getCompleteApiUrl(this, R.string.log_out);

        Log.v("Hit_LogoutApi", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("user_id", pref.get(Constants.user_id));
            // jsonObject.put("device_id", Utils.getDeviceID(this));
            jsonObject.put("device_id", pref.get(Constants.fcmId));
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

    private void parseJson(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                pref.set(Constants.user_id, "");
                pref.commit();
                Toast.makeText(Dashboard.this, res_msg, Toast.LENGTH_SHORT).show();
                intent = new Intent(Dashboard.this, LoginActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(Dashboard.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }
            loader.cancel();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(Dashboard.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }


    //StartConfirmation popup
    public void FilterDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.filter_dialog);

        dialog.show();

        final RelativeLayout rl_submit = dialog.findViewById(R.id.rl_submit);
        final RelativeLayout rl_today = dialog.findViewById(R.id.rl_today);
        final RelativeLayout rl_yesterday = dialog.findViewById(R.id.rl_yesterday);
        final RelativeLayout rl_week = dialog.findViewById(R.id.rl_week);
        final RelativeLayout rl_month = dialog.findViewById(R.id.rl_month);
        final RelativeLayout rl_year = dialog.findViewById(R.id.rl_year);
        final TextView tv_today = dialog.findViewById(R.id.tv_today);
        final TextView tv_yesterday = dialog.findViewById(R.id.tv_yesterday);
        final TextView tv_week = dialog.findViewById(R.id.tv_week);
        final TextView tv_month = dialog.findViewById(R.id.tv_month);
        final TextView tv_year = dialog.findViewById(R.id.tv_year);
        rl_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_today.setBackgroundColor(getResources().getColor(R.color.app_header));
                tv_today.setTextColor(getResources().getColor(R.color.white));
            }
        });

        rl_yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_yesterday.setBackgroundColor(getResources().getColor(R.color.app_header));
                tv_yesterday.setTextColor(getResources().getColor(R.color.white));
            }
        });

        rl_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_week.setBackgroundColor(getResources().getColor(R.color.app_header));
                tv_week.setTextColor(getResources().getColor(R.color.white));
            }
        });

        rl_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_month.setBackgroundColor(getResources().getColor(R.color.app_header));
                tv_month.setTextColor(getResources().getColor(R.color.white));
            }
        });

        rl_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_year.setBackgroundColor(getResources().getColor(R.color.app_header));
                tv_year.setTextColor(getResources().getColor(R.color.white));
            }
        });

        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void displayview(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frame, frag);
        fragmentTransaction.commit();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        if (DashboardFragment.scheduleCheck.equalsIgnoreCase("1")) {
            popup.getMenu().getItem(2).setVisible(false);
        } else {
            popup.getMenu().getItem(2).setVisible(true);
        }

        if (pref.get(Constants.survey_not_required_status).equalsIgnoreCase("1")) {
            popup.getMenu().getItem(4).setVisible(false);
        } else {
            //  popup.getMenu().getItem(4).setVisible(false);
        }
//        for(int i=0;i<15;i++){
            Log.v("My_Menu",Integer.toString(popup.getMenu().size()));
//        }
        popup.getMenu().getItem(5).setVisible(true);
        popup.getMenu().getItem(6).setVisible(true);
        popup.getMenu().getItem(7).setVisible(false);
        popup.getMenu().getItem(8).setVisible(false);
        popup.getMenu().getItem(9).setVisible(false);
        popup.getMenu().getItem(10).setVisible(false);
        popup.getMenu().getItem(11).setVisible(false);
        popup.getMenu().getItem(12).setVisible(false);
        popup.getMenu().getItem(13).setVisible(false);
        popup.getMenu().getItem(14).setVisible(false);
        popup.getMenu().getItem(15).setVisible(false);
        popup.getMenu().getItem(16).setVisible(false);


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homee:
                        intent = new Intent(mActivity, Dashboard.class);
                        startActivity(intent);
                        return true;

                    case R.id.survey_not_required:
                        intent = new Intent(mActivity, SurveyNotRequiredActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.reassign:
                        intent = new Intent(mActivity, ReassignActivity.class);
                        startActivity(intent);
                        //SurveyorList();
                        // Toast.makeText(PriliminaryActivity.this, "re-assign", Toast.LENGTH_SHORT).show();

                        return true;

                    case R.id.reschedule:
                        intent = new Intent(mActivity, ScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.changeSurvey:
                        Log.v("My_Survey","Working");
                        pref.set(Constants.surveyChoosen,"");
                        ((Dashboard) mActivity).displayView(5);
                        pref.commit();
                        return true;

                    case R.id.calling:
                        CaseDetail.CallDialog();
                        return true;

                    case R.id.documents:

                        startActivity(new Intent(mActivity, DocumentsActivity.class));

                        return true;
// Document changes
                    /*case R.id.doc:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://valuerservice.com/uploads/0692351837.jpg"));
                        startActivity(intent);
                        return true;*/

                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onDestroy() {
        stopService(new Intent(mActivity, LocationUpdateService.class));
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (userInteractionListener != null)
            userInteractionListener.onUserInteraction();
    }

    public void setUserInteractionListener(UserInteractionListener userInteractionListener) {
        this.userInteractionListener = userInteractionListener;
    }

    public interface UserInteractionListener {
        void onUserInteraction();
    }
}

