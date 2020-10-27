package com.vis.android.Activities.General;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.R;

public class DrawPlotActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout back,rl_casedetail,rl_dots;

    TextView tv_caseheader,tv_caseid,tv_header,tvDone,tvClear;

    Preferences pref;

    CustomLoader loader;

    SignaturePad signaturePad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_plot);

        init();
        setListeners();
    }

    private void init(){
        pref = new Preferences(mActivity);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        back = findViewById(R.id.rl_back);
        rl_dots = findViewById(R.id.rl_dots);

        tv_header = findViewById(R.id.tv_header);
        tv_caseheader = findViewById(R.id.tv_caseheader);
        tv_caseid = findViewById(R.id.tv_caseid);
        tvDone = findViewById(R.id.tvDone);
        tvClear = findViewById(R.id.tvClear);
        rl_casedetail = findViewById(R.id.rl_casedetail);

        rl_casedetail.setVisibility(View.VISIBLE);
        tv_header.setVisibility(View.GONE);
        rl_dots.setVisibility(View.GONE);

        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

        tv_caseheader.setText("Draw Plot Lines");

        signaturePad = findViewById(R.id.signaturePad);

    }

    private void setListeners(){
        back.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.tvDone:
                onBackPressed();
                break;

            case R.id.tvClear:
                signaturePad.clear();
                break;

        }
    }
}
