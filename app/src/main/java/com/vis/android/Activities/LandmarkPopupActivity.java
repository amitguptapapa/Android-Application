package com.vis.android.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vis.android.CallingService.LocationUpdateService;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.Fragments.MapLocatorActivity;
import com.vis.android.R;

public class LandmarkPopupActivity extends BaseActivity {

    ImageView ivClose;

    TextView tvMessage;

    Button btnSubmit;

    Preferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_landmark_popup);

        ivClose = findViewById(R.id.ivClose);
        tvMessage = findViewById(R.id.tvMessage);
        btnSubmit = findViewById(R.id.btnSubmit);

        pref = new Preferences(mActivity);

        try {
            tvMessage.setText("Dear Abc,\n" +
                    "Please capture the Landmark for the Case:\n" +
                    pref.get(Constants.caseIdService)+" as the property is only 2KMs left from your current location.");

        }catch (Exception e){
            e.printStackTrace();
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // stopService(new Intent(mActivity, LocationUpdateService.class));

                finish();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, Dashboard.class).putExtra("frag","mapLocator"));
            }
        });
    }

}
