package com.vis.android.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.R;

public class AboutUs extends Fragment implements View.OnClickListener{
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.about_us, container, false);
        getid();
        setListener();

        return v;
    }
    @Override
    public void onClick(View v) {

    }
    public void getid() {

    }

    public void setListener() {

    }

}
