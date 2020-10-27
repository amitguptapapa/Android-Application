package com.vis.android.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vis.android.R;

public class Help extends Fragment implements View.OnClickListener {
    View v;
    RelativeLayout ques;
    TextView ans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.help, container, false);
        getid();
        setListener();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_ques:
                if(ans.getVisibility()==View.GONE){
                    ans.setVisibility(View.VISIBLE);
                }
                else{
                    ans.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void getid() {
        ques = v.findViewById(R.id.rl_ques);
        ans=v.findViewById(R.id.tv_ans);
    }

    public void setListener() {
        ques.setOnClickListener(this);
    }

}