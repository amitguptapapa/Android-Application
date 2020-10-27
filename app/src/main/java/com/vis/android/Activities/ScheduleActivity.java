package com.vis.android.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.vis.android.CallingService.CallRecordingService;
import com.vis.android.Common.Constants;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Common.Preferences;
import com.vis.android.Common.Utils;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.Extras.TimePickerDialogFixed;
import com.vis.android.Fragments.DashboardFragment;
import com.vis.android.Fragments.MapLocatorActivity;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.vis.android.Fragments.CaseDetail.case_id;
import static com.vis.android.Fragments.CaseDetail.lead_schedule_staus;
import static com.vis.android.Fragments.CaseDetail.schedule_status;

public class ScheduleActivity extends BaseActivity implements View.OnClickListener {
    ReasonAdapter reason_reason;
    ReshcheduleAdapter rsh_adapter;
    RelativeLayout dots, back, rl_casedetail;
    TextView tv_caseheader, reasonn, tv_header,remark;
    TextView reschedule_date, reschedule_time;
    Intent intent;
    static final int DATE_DIALOG_ID = 999;
    SimpleDateFormat simpleDateFormat;
    private int myear;
    private int mmonth;
    private int mday;
    TextView tv_date, tv_caseid;
    RelativeLayout date;
    RelativeLayout time;
    static TextView tv_time;
    String reject_reason, selected_reason = "",checkSchedule="",
            customer_mobile_number_arr, co_alternate_mobile, co_person_number;
    Preferences pref;
    CustomLoader loader;
    RelativeLayout container;
    Dialog dialog;
    String status = "0";
    EditText et_remark,et_ownername,et_mobile,et_acctype,et_personname,et_email,et_mobilee,et_alternate,et_relation,et_comment;
    String check_status = "0";
    RelativeLayout rl_schedule,rlReason,rlRemarks,
            rl_four,rl_one,rl_Pdetail,rl_CPdetail;
    TextView tv_submit;
    Spinner reason;
    TextView tv_reasons,tv_resc,tvRescDate,tvRescTime,tvRescheduleHeading,
            tv_personaldetail,tv_CoPersonaldetail;

    ImageView iv_phonecall,iv_four,iv_phonecall1,iv_phonecall2,iv_one;

    ImageView iv_phn, iv_phn2;
    TextView number;
    int callstatus = 0;
    CallAdapter adater;
    String scheduledStatus="";

    public static ArrayList<HashMap<String, String>> cal_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> alternat_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> co_number_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> reschedule_array_list = new ArrayList<HashMap<String, String>>();
    public static ArrayList<HashMap<String, String>> reassign_array_list = new ArrayList<HashMap<String, String>>();

    Dialog emailDialog;
    private ArrayList<String> selectedMembersList = new ArrayList<>();
    ImageView ivAttachment;

    String encodedString1 = "",picturePath = "",filename = "", ext = "";

    Uri picUri, fileUri;

    private static final String IMAGE_DIRECTORY_NAME = "VIS";

    private static final int SELECT_PICTURE = 1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 2;

    Bitmap bitmap;

    int captureType = 0;
    private String status_contact1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        //Log.v("lead_status===", lead_schedule_staus);

        getid();
        setListener();

        iv_one.setBackgroundResource(R.mipmap.up_arrow);
        iv_four.setBackgroundResource(R.mipmap.down_arrow);


        tv_header.setVisibility(View.GONE);
        rl_casedetail.setVisibility(View.VISIBLE);
        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));
        Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        tv_time.setText(simpleDateFormat.format(calander.getTime()));

        setCurrentDateOnView();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });

        try {
           /* reschedule_array_list.clear();
            JSONArray array_remark = new JSONArray(pref.get(Constants.array_remarks));

            for (int j = 0; j < array_remark.length(); j++) {

                JSONObject data_object = array_remark.getJSONObject(j);
                String name = data_object.getString("name");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("name", name);
                reschedule_array_list.add(hmap);
            }*/

            reassign_array_list.clear();
            JSONArray reason_list = new JSONArray(pref.get(Constants.cancel_array));

            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("Cancel_reason", "Select Reason");
            reassign_array_list.add(hash);

            for (int j = 0; j < reason_list.length(); j++) {
                JSONObject data_object = reason_list.getJSONObject(j);
                String Cancel_reason = data_object.getString("Cancel_reason");
                String id = data_object.getString("id");
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("Cancel_reason", Cancel_reason);
                hmap.put("id", id);
                reassign_array_list.add(hmap);

                reason_reason = new ReasonAdapter(this, reassign_array_list);
                reason.setAdapter(reason_reason);


            }

        } catch (Exception e) {

        }
        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selected_reason = reassign_array_list.get(position).get("Cancel_reason");
                selected_reason = reassign_array_list.get(position).get("id");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (lead_schedule_staus.equals("not_schedule_yet")) {
            //container.setVisibility(View.VISIBLE);
            rl_schedule.setVisibility(View.VISIBLE);
            checkSchedule="Scheduled";
        } else if (lead_schedule_staus.equals("Lead Not Scheduled")) {
           // container.setVisibility(View.GONE);
            rl_schedule.setVisibility(View.GONE);
            checkSchedule="Rescheduled";
        } else {

        }

        if (Utils.isNetworkConnectedMainThred(ScheduleActivity.this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            Hit_Reschedule_Api("1");

        } else {
            Toast.makeText(ScheduleActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

        }
    }

    public void getid() {
        tv_resc = findViewById(R.id.tv_resc);
        tvRescDate = findViewById(R.id.tvRescDate);
        tvRescTime = findViewById(R.id.tvRescTime);
        tvRescheduleHeading = findViewById(R.id.tvRescheduleHeading);
        tv_personaldetail = findViewById(R.id.tv_personaldetail);
        tv_CoPersonaldetail = findViewById(R.id.tv_CoPersonaldetail);

        rlReason = findViewById(R.id.rlReason);
        rlRemarks = findViewById(R.id.rlRemarks);
        rl_four = findViewById(R.id.rl_four);
        rl_one = findViewById(R.id.rl_one);
        rl_Pdetail = findViewById(R.id.rl_Pdetail);
        rl_CPdetail = findViewById(R.id.rl_CPdetail);

        rl_schedule = findViewById(R.id.rl_reschedule);
        container = findViewById(R.id.rl_container);
        pref = new Preferences(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        back = (RelativeLayout) findViewById(R.id.rl_back);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        date = findViewById(R.id.ll_date);
        time = findViewById(R.id.ll_time);
        reason = findViewById(R.id.spinner_reason);
        et_remark = findViewById(R.id.et_remark);
        et_acctype = findViewById(R.id.et_acctype);
        et_personname = findViewById(R.id.et_personname);
        et_email = findViewById(R.id.et_email);
        et_mobilee = findViewById(R.id.et_mobilee);
        et_alternate = findViewById(R.id.et_alternate);
        et_relation = findViewById(R.id.et_relation);
        et_comment = findViewById(R.id.et_comment);
        et_ownername = findViewById(R.id.et_ownername);
        iv_phonecall = findViewById(R.id.iv_phonecall);
        iv_four = findViewById(R.id.iv_four);
        iv_phonecall1 = findViewById(R.id.iv_phonecall1);
        iv_phonecall2 = findViewById(R.id.iv_phonecall2);
        iv_one = findViewById(R.id.iv_one);
        et_mobile = findViewById(R.id.et_mobile);
        rl_casedetail = findViewById(R.id.rl_casedetail);
        tv_header = findViewById(R.id.tv_header);
        tv_caseheader = findViewById(R.id.tv_caseheader);
        reschedule_date = findViewById(R.id.tv_sdate);
        reschedule_time = findViewById(R.id.tv_stime);
        reasonn = findViewById(R.id.reasonn);
        remark = findViewById(R.id.remark);
        dots = findViewById(R.id.rl_dots);
        tv_caseid = findViewById(R.id.tv_caseid);
        tv_submit = findViewById(R.id.tv_submit);
    }

    public void setListener() {
        back.setOnClickListener(this);
        dots.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_phonecall.setOnClickListener(this);
        iv_phonecall1.setOnClickListener(this);
        iv_phonecall2.setOnClickListener(this);
        tv_personaldetail.setOnClickListener(this);
        tv_CoPersonaldetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.iv_phonecall:
                status_contact1 = "1";
                callstatus = 0;
                CallingDialog();
                break;

            case R.id.iv_phonecall1:
                status_contact1 = "3";
                callstatus = 0;
                CallingDialog();
                break;

            case R.id.iv_phonecall2:
                status_contact1 = "2";
                callstatus = 0;
                CallingDialog();
                break;

            case R.id.tv_submit:
                if (schedule_status.equalsIgnoreCase("reschedule") || schedule_status.equalsIgnoreCase("schedule")){
                    if (reason.getSelectedItemPosition() > 0 && et_remark.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Please enter remarks", Toast.LENGTH_SHORT).show();
                    } else if (reason.getSelectedItemPosition() > 0 && !et_remark.getText().toString().isEmpty()){
                        if (Utils.isNetworkConnectedMainThred(ScheduleActivity.this)) {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            Hit_Reschedule_Api("2");

                        } else {
                            Toast.makeText(ScheduleActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
//                        Toast.makeText(ScheduleActivity.this, "Case "+schedule_status+"d", Toast.LENGTH_SHORT).show();
//
//                        intent = new Intent(ScheduleActivity.this, Dashboard.class);
//                        startActivity(intent);
                     }
                }
                else {

                        if (Utils.isNetworkConnectedMainThred(ScheduleActivity.this)) {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            Hit_Reschedule_Api("2");

                        } else {
                            Toast.makeText(ScheduleActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                        }

                       /* intent = new Intent(ScheduleActivity.this, MapLocatorActivity.class);
                        startActivity(intent);*/
//                       if(lead_schedule_staus.equalsIgnoreCase("not_schedule_yet")){
//                           Toast.makeText(ScheduleActivity.this, "Case Scheduled", Toast.LENGTH_SHORT).show();
//                       }else {
                           Toast.makeText(ScheduleActivity.this, scheduledStatus, Toast.LENGTH_SHORT).show();
//                       }
                    Log.v("school",schedule_status);
                    intent = new Intent(ScheduleActivity.this, Dashboard.class);
                    startActivity(intent);


                }

                break;

            case R.id.tv_reschedule:
                RescheduleDialog();
                break;

            case R.id.tv_proceed:

                intent = new Intent(ScheduleActivity.this, Dashboard.class);
                startActivity(intent);
                break;


            case R.id.rl_dots:
                showPopup(view);
                break;


            case R.id.tv_personaldetail:
                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_one.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rl_Pdetail.setVisibility(View.VISIBLE);
                    iv_one.setBackgroundResource(R.mipmap.up_arrow);

                }

                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }

                break;

            case R.id.tv_CoPersonaldetail:
                if (rl_CPdetail.getVisibility() == View.VISIBLE) {
                    rl_CPdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {
                    rl_CPdetail.setVisibility(View.VISIBLE);
                    iv_four.setBackgroundResource(R.mipmap.up_arrow);

                }
                if (rl_Pdetail.getVisibility() == View.VISIBLE) {
                    rl_Pdetail.setVisibility(View.GONE);
                    iv_four.setBackgroundResource(R.mipmap.down_arrow);
                } else {

                }


                break;
        }
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        if (pref.get(Constants.survey_not_required_status).equalsIgnoreCase("1")){
            popup.getMenu().getItem(4).setVisible(false);
        }else {
            //  popup.getMenu().getItem(4).setVisible(false);
        }

        popup.getMenu().getItem(5).setVisible(false);
        popup.getMenu().getItem(6).setVisible(false);
        popup.getMenu().getItem(7).setVisible(false);
        popup.getMenu().getItem(8).setVisible(false);
        popup.getMenu().getItem(9).setVisible(false);
        popup.getMenu().getItem(10).setVisible(false);
        popup.getMenu().getItem(11).setVisible(false);
        popup.getMenu().getItem(12).setVisible(false);
        popup.getMenu().getItem(13).setVisible(false);
        popup.getMenu().getItem(14).setVisible(false);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homee:
                        intent = new Intent(ScheduleActivity.this, Dashboard.class);
                        startActivity(intent);
                        return true;

                    case R.id.survey_not_required:
                        intent = new Intent(ScheduleActivity.this, SurveyNotRequiredActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.reassign:
                        intent = new Intent(ScheduleActivity.this, ReassignActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.reschedule:
                        intent = new Intent(ScheduleActivity.this, ScheduleActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.calling:
                        CallDialog();
                        return true;

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


    public void RescheduleDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.reschedule_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        // reschedule_array_list.clear();
        TextView submit = (TextView) dialog.findViewById(R.id.tv_submit);
        ImageView cancel = (ImageView) dialog.findViewById(R.id.iv_close);
        LinearLayout date = (LinearLayout) dialog.findViewById(R.id.ll_date);
        LinearLayout time = (LinearLayout) dialog.findViewById(R.id.ll_time);
        tv_date = (TextView) dialog.findViewById(R.id.tv_date);
        tv_time = (TextView) dialog.findViewById(R.id.tv_time);
        ListView list = dialog.findViewById(R.id.lv_reschedule);

        rsh_adapter = new ReshcheduleAdapter(this, reschedule_array_list);
        list.setAdapter(rsh_adapter);

        Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        tv_time.setText(simpleDateFormat.format(calander.getTime()));

        setCurrentDateOnView();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_status.equals("0")) {
                    Toast.makeText(ScheduleActivity.this, "Please select reason for cancellation", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    if (Utils.isNetworkConnectedMainThred(ScheduleActivity.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        Hit_PostSchedule_Api(reject_reason, "2");

                    } else {
                        Toast.makeText(ScheduleActivity.this, "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });
    }

    public class ReasonAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String, String>> alist;
        LayoutInflater inflter;


        public ReasonAdapter(Context applicationContext, ArrayList<HashMap<String, String>> alist) {
            this.context = applicationContext;
            this.alist = alist;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflter.inflate(R.layout.surveyor_adapter, null);

            tv_reasons = (TextView) convertView.findViewById(R.id.tv_name);
            tv_reasons.setTextColor(getResources().getColor(R.color.greyy));
            tv_reasons.setText(alist.get(position).get("Cancel_reason"));
            return convertView;
        }
    }

    public class ReshcheduleAdapter extends BaseAdapter {
        LayoutInflater inflter;
        Context context;
        ArrayList<HashMap<String, String>> alist;
        RadioGroup rg_reject;
        RadioButton checkedRadioButton, rb_one;
        RelativeLayout rlCheck;

        TextView name;
        String selected_reason;
        ImageView iv_unselected, iv_selected;
        private CheckBox checkBox;
        private TextView reason;
        private int selectedPosition = -1;


        public ReshcheduleAdapter(Context context, ArrayList<HashMap<String, String>> alist) {

            inflter = (LayoutInflater.from(context));
            this.context = context;
            this.alist = alist;


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
            view = inflter.inflate(R.layout.reschedule_adapter, null);

            getid(view);
            name.setText(alist.get(i).get("name"));
            checkBox.setTag(i);

            if (selectedPosition == i) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }


            checkBox.setOnClickListener(onStateChangedListener(checkBox, i));


            return view;
        }

        private View.OnClickListener onStateChangedListener(final CheckBox checkBox, final int position) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        check_status = "1";
                        selectedPosition = position;
                        Log.v("remark========", alist.get(selectedPosition).get("name"));
                        reject_reason = alist.get(selectedPosition).get("name");
                    } else {
                        check_status = "0";
                        selectedPosition = -1;
                    }
                    notifyDataSetChanged();
                }
            };
        }

        public void getid(View v) {
            rlCheck = v.findViewById(R.id.rlCheck);
            name = v.findViewById(R.id.name);
            checkBox = (CheckBox) v.findViewById(R.id.check);
            reason = (TextView) v.findViewById(R.id.name);

        }

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @SuppressLint("ValidFragment")
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialogFixed(getActivity() ,this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            tv_time.setText(updateTime(hourOfDay, minute));
        }
    }


    // function to get am and pm from time
    private static String updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        return aTime;
    }

    // display current date
    public void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);
        // tv_date.setText(new StringBuilder().append(myear).append("-").append(mmonth + 1).append("-").append(mday));
        CharSequence strDate = null;
        Time chosenDate = new Time();
        chosenDate.set(mday, mmonth, myear);
        long dtDob = chosenDate.toMillis(true);

        strDate = DateFormat.format("dd MMM yyyy", dtDob);
        Log.v("date======", String.valueOf(strDate));
        tv_date.setText(strDate);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, myear, mmonth,
                        mday) {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (year < myear)
                            view.updateDate(myear, mmonth, mday);

                        if (monthOfYear < mmonth && year == myear)
                            view.updateDate(myear, mmonth, mday);

                        if (dayOfMonth < mday && year == myear && monthOfYear == mmonth)
                            view.updateDate(myear, mmonth, mday);

                    }
                };

                return _date;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;

            CharSequence strDate = null;
            Time chosenDate = new Time();
            chosenDate.set(mday, mmonth, myear);
            long dtDob = chosenDate.toMillis(true);

            strDate = DateFormat.format("dd MMM yyyy", dtDob);
            Log.v("date======", String.valueOf(strDate));
            tv_date.setText(strDate);

            // tv_date.setText(new StringBuilder().append(mday).append("-").append(mmonth + 1).append("-").append(myear).append(" "));

        }
    };

    //************************************ Post Schedule ******************************************//
    private void Hit_PostSchedule_Api(String remark, String type) {

        String url = Utils.getCompleteApiUrl(this, R.string.post_schedule);
        Log.v("Hit_PostSchedule_Api", url);

        JSONObject json_data = new JSONObject();

        try {
            json_data.put("case_id", case_id);
            json_data.put("surveyor_id", pref.get(Constants.surveyor_id));
            json_data.put("date", tv_date.getText().toString());
            json_data.put("time", tv_time.getText().toString());
            json_data.put("type", type);
            json_data.put("remark", remark);

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
                        parseJsonPost(response);
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
                            Toast.makeText(ScheduleActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonPost(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {
                if (status.equals("1")) {
                    lead_schedule_staus = jsonObject.getString("lead_schedule_status");
                    reschedule_time.setText(tv_date.getText().toString() + "," + tv_time.getText().toString());
                    check_status = "0";
                    dialog.dismiss();
                   // container.setVisibility(View.VISIBLE);
                    rl_schedule.setVisibility(View.VISIBLE);
                    //add.setVisibility(View.GONE);
                } else {
                    Toast.makeText(ScheduleActivity.this, res_msg, Toast.LENGTH_SHORT).show();
                    intent = new Intent(ScheduleActivity.this, Dashboard.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(ScheduleActivity.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();

            }


            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ScheduleActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }

    //*******************************************************************************************//

    private void Hit_Reschedule_Api(String type) {

        String url = Utils.getCompleteApiUrl(this, R.string.reschedule);
        Log.v("Hit_Reschedule_Api", url);

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
            jsonObject.put("case_id", case_id);
            jsonObject.put("sur_id", pref.get(Constants.surveyor_id));
            jsonObject.put("re_schdual_date", tv_date.getText().toString());
            jsonObject.put("re_schdual_time", tv_time.getText().toString());
            if (schedule_status.equalsIgnoreCase("not_schedule_yet")){
                jsonObject.put("re_assign_reason", "");
            }else {
                jsonObject.put("re_assign_reason", selected_reason);
            }
            jsonObject.put("remark", et_remark.getText().toString());
            jsonObject.put("type", type);
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
                        parseJsonReschedule(response);
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
                            Toast.makeText(ScheduleActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });

    }

    private void parseJsonReschedule(JSONObject response) {

        Log.v("response", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String msg = jsonObject.getString("response_code");
            Log.v("res_msg", res_msg);

            if (msg.equals("1")) {

                if (jsonObject.getString("type").equals("1")) {

                    et_ownername.setText(jsonObject.getString("customer_name"));
                    et_mobile.setText(jsonObject.getString("customer_mobile_number"));

                    et_acctype.setText(jsonObject.getString("customer_account_type"));

                    et_personname.setText(jsonObject.getString("co_persone_name"));
                    et_email.setText(jsonObject.getString("co_persone_email"));
                    et_mobilee.setText(jsonObject.getString("co_persone_number"));

                    et_alternate.setText(jsonObject.getString("co_persone_alternate_number"));

                    et_relation.setText(jsonObject.getString("relation_with_owner"));
                    et_comment.setText(jsonObject.getString("comment"));


                    if (jsonObject.getString("schedule_status").equals("reschedule")) {
                        container.setVisibility(View.VISIBLE);
                        reschedule_date.setText(jsonObject.getString("reschedule_date"));
                        reschedule_time.setText(jsonObject.getString("reschedule_time"));
                        reasonn.setText(jsonObject.getString("reschedule_reason"));
                        remark.setText(jsonObject.getString("remark"));
                        rlReason.setVisibility(View.VISIBLE);
                        rlRemarks.setVisibility(View.VISIBLE);
                        tvRescheduleHeading.setText("Re-schedule Survey");
                        tv_caseheader.setText("Re-schedule Survey");
                        tv_submit.setText("Re-schedule Survey");
                        tvRescDate.setText("Re-schedule Date");
                        tvRescTime.setText("Re-schedule Time");
                        scheduledStatus="Case Rescheduled";

                    } else if (jsonObject.getString("schedule_status").equals("schedule")){
                        container.setVisibility(View.VISIBLE);
                        reschedule_date.setText(jsonObject.getString("reschedule_date"));
                        reschedule_time.setText(jsonObject.getString("reschedule_time"));
                        reasonn.setText(jsonObject.getString("reschedule_reason"));
                        remark.setText(jsonObject.getString("remark"));

                        rlRemarks.setVisibility(View.VISIBLE);
                        rlReason.setVisibility(View.VISIBLE);

                        tv_resc.setText("Re-schedule Survey");
                        tvRescDate.setText("Schedule Date");
                        tvRescTime.setText("Schedule Time");
                       // rlReason.setVisibility(View.GONE);
                        tvRescheduleHeading.setText("Re-schedule Survey");
                        tv_caseheader.setText("Re-schedule Survey");
                        tv_submit.setText("Re-schedule Survey");
                        scheduledStatus="Case Rescheduled";
                    }else {
                        container.setVisibility(View.GONE);
                        rlRemarks.setVisibility(View.VISIBLE);
                        rlReason.setVisibility(View.GONE);

                        tv_resc.setText("Schedule Survey");
                        tvRescDate.setText("Schedule Date");
                        tvRescTime.setText("Schedule Time");
                        // rlReason.setVisibility(View.GONE);
                        tvRescheduleHeading.setText("Schedule Survey");
                        tv_caseheader.setText("Schedule Survey");
                        tv_submit.setText("Schedule Survey");
                        scheduledStatus="Case Scheduled";

                    }

                   /* try {
                        cal_list.clear();
                        alternat_list.clear();
                        co_number_list.clear();
                        customer_mobile_number_arr = jsonObject.getString("customer_mobile_number");
                        co_alternate_mobile =  jsonObject.getString("co_alternate_mobile");
                        co_person_number = jsonObject.getString("co_person_number");

                        JSONArray call_list = new JSONArray(customer_mobile_number_arr);
                        JSONArray alternate_list = new JSONArray(co_alternate_mobile);
                        JSONArray phone_list = new JSONArray(co_person_number);
                        for (int j = 0; j < call_list.length(); j++) {
                            JSONObject data_object1 = call_list.getJSONObject(j);
                            String number = data_object1.getString("number");
                            HashMap<String, String> hmap = new HashMap<>();
                            hmap.put("number", number);
                            cal_list.add(hmap);
                        }

                        for (int j = 0; j < alternate_list.length(); j++) {
                            JSONObject data_object1 = alternate_list.getJSONObject(j);
                            String number = data_object1.getString("number");
                            HashMap<String, String> hmap = new HashMap<>();
                            hmap.put("number", number);
                            alternat_list.add(hmap);
                        }

                        for (int j = 0; j < phone_list.length(); j++) {
                            JSONObject data_object1 = phone_list.getJSONObject(j);
                            String number = data_object1.getString("number");
                            HashMap<String, String> hmap = new HashMap<>();
                            hmap.put("number", number);
                            co_number_list.add(hmap);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/


                } else {
//                    if(lead_schedule_staus.equalsIgnoreCase("not_schedule_yet")){
//                        Toast.makeText(ScheduleActivity.this, "Case Scheduled", Toast.LENGTH_SHORT).show();
//                    }else {
                        Toast.makeText(ScheduleActivity.this, scheduledStatus, Toast.LENGTH_SHORT).show();
//                    }
                    Log.v("schoolA",schedule_status);
                    intent = new Intent(ScheduleActivity.this, Dashboard.class);
                    startActivity(intent);
////                    if (DashboardFragment.scheduleCheck.equalsIgnoreCase("1")){
////                        intent = new Intent(ScheduleActivity.this, Dashboard.class);
////                    }else {
//////                        intent = new Intent(ScheduleActivity.this, MapLocatorActivity.class);
////                    }
//                    Toast.makeText(ScheduleActivity.this, res_msg, Toast.LENGTH_SHORT).show();
//                    startActivity(intent);

                }


            } else {
                Toast.makeText(ScheduleActivity.this, res_msg, Toast.LENGTH_SHORT).show();
                loader.cancel();
            }

            loader.cancel();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ScheduleActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            loader.cancel();
        }
    }


    //Call popup
    public void CallDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.call_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        LinearLayout manager = dialog.findViewById(R.id.ll_manager);
        LinearLayout owner = dialog.findViewById(R.id.ll_owner);
        ImageView close = dialog.findViewById(R.id.iv_close);
        final TextView tvName1,tvName2,tvName3,tvName4,tvMobile1,tvMobile2,tvMobile3,tvMobile4;

        ImageView ivEmail1,ivEmail2,ivEmail3,ivEmail4,iv_call1,iv_call2,iv_call3,iv_call4;
        ivEmail1 = dialog.findViewById(R.id.ivEmail1);
        ivEmail2 = dialog.findViewById(R.id.ivEmail2);
        ivEmail3 = dialog.findViewById(R.id.ivEmail3);
        ivEmail4 = dialog.findViewById(R.id.ivEmail4);
        iv_call1 = dialog.findViewById(R.id.iv_call1);
        iv_call2 = dialog.findViewById(R.id.iv_call2);
        iv_call3 = dialog.findViewById(R.id.iv_call3);
        iv_call4 = dialog.findViewById(R.id.iv_call4);
        tvName1 = dialog.findViewById(R.id.tvName1);
        tvName2 = dialog.findViewById(R.id.tvName2);
        tvName3 = dialog.findViewById(R.id.tvName3);
        tvName4 = dialog.findViewById(R.id.tvName4);
        tvMobile1 = dialog.findViewById(R.id.tvMobile1);
        tvMobile2 = dialog.findViewById(R.id.tvMobile2);
        tvMobile3 = dialog.findViewById(R.id.tvMobile3);
        tvMobile4 = dialog.findViewById(R.id.tvMobile4);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Intent intent = getIntent();

        try {
            if (pref.get(Constants.support_manager_name).equals("")
                    || pref.get(Constants.support_manager_name).equals("null")){
                tvName1.setText("NA");
            }else {
                tvName1.setText(pref.get(Constants.support_manager_name));
            }

            if (pref.get(Constants.support_owner_name).equals("")
                    || pref.get(Constants.support_owner_name).equals("null")){
                tvName2.setText("NA");
            }else {
                tvName2.setText(pref.get(Constants.support_owner_name));
            }

            if (pref.get(Constants.support_co_person_name).equals("")
                    || pref.get(Constants.support_co_person_name).equals("null")){
                tvName4.setText("NA");
            }else {
                tvName4.setText(pref.get(Constants.support_co_person_name));
            }

            if (pref.get(Constants.support_bus_asso_name).equals("")
                    || pref.get(Constants.support_bus_asso_name).equals("null")){
                tvName3.setText("NA");
            }else {
                tvName3.setText(pref.get(Constants.support_bus_asso_name));
            }

            if (pref.get(Constants.support_manager_phone).equals("")
                    || pref.get(Constants.support_manager_phone).equals("null")){
                tvMobile1.setText("NA");
            }else {
                tvMobile1.setText(pref.get(Constants.support_manager_phone));
            }

            if (pref.get(Constants.support_owner_phone).equals("")
                    || pref.get(Constants.support_owner_phone).equals("null")){
                tvMobile2.setText("NA");
            }else {
                tvMobile2.setText(pref.get(Constants.support_owner_phone));
            }

            if (pref.get(Constants.support_co_person_phone).equals("")
                    || pref.get(Constants.support_co_person_phone).equals("null")){
                tvMobile4.setText("NA");
            }else {
                tvMobile4.setText(pref.get(Constants.support_co_person_phone));
            }

            if (pref.get(Constants.support_bus_asso_phone).equals("")
                    || pref.get(Constants.support_bus_asso_phone).equals("null")){
                tvMobile3.setText("NA");
            }else {
                tvMobile3.setText(pref.get(Constants.support_bus_asso_phone));
            }

            if (!tvMobile1.getText().toString().equalsIgnoreCase("NA")){
                call(iv_call1, tvMobile1.getText().toString());
            }
            if (!tvMobile2.getText().toString().equalsIgnoreCase("NA")){
                call(iv_call2, tvMobile2.getText().toString());
            }
            if (!tvMobile3.getText().toString().equalsIgnoreCase("NA")){
                call(iv_call3, tvMobile3.getText().toString());
            }
            if (!tvMobile4.getText().toString().equalsIgnoreCase("NA")){
                call(iv_call4, tvMobile4.getText().toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ivEmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName1.getText().toString().equalsIgnoreCase("NA")){
                    dialog.dismiss();
                    AlertEmailPopup("1",tvName1.getText().toString());
                }
            }
        });
        ivEmail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName2.getText().toString().equalsIgnoreCase("NA")){
                    dialog.dismiss();
                    AlertEmailPopup("2",tvName2.getText().toString());
                }
            }
        });
        ivEmail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName3.getText().toString().equalsIgnoreCase("NA")){
                    dialog.dismiss();
                    AlertEmailPopup("3",tvName3.getText().toString());
                }
            }
        });
        ivEmail4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvName4.getText().toString().equalsIgnoreCase("NA")){
                    dialog.dismiss();
                    AlertEmailPopup("4",tvName4.getText().toString());
                }
            }
        });

    }

    //Email popup
    public void AlertEmailPopup(final String type, String name) {
        emailDialog = new Dialog(this);
        emailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailDialog.setCancelable(true);
        emailDialog.setContentView(R.layout.alert_email_popup);
        Window window = emailDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        emailDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        emailDialog.show();

        ImageView close;
        final ChipsInput chipInputTo;
        final EditText etSubject,etMessage;
        Button btnBrowse,btnSubmit;

        close = emailDialog.findViewById(R.id.iv_close);
        chipInputTo = emailDialog.findViewById(R.id.chipInputTo);
        etSubject = emailDialog.findViewById(R.id.etSubject);
        etMessage = emailDialog.findViewById(R.id.etMessage);
        btnBrowse = emailDialog.findViewById(R.id.btnBrowse);
        btnSubmit = emailDialog.findViewById(R.id.btnSubmit);
        ivAttachment = emailDialog.findViewById(R.id.ivAttachment);

        selectedMembersList.clear();

        chipInputTo.setChipDeletable(true);
        chipInputTo.setChipHasAvatarIcon(false);
        chipInputTo.setShowChipDetailed(false);
        chipInputTo.addChip(name,"");

        chipInputTo.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chipInterface, int i) {
                selectedMembersList.add(String.valueOf(chipInterface.getLabel()));

            }

            @Override
            public void onChipRemoved(ChipInterface chipInterface, int i) {
                selectedMembersList.remove(chipInterface.getLabel());
            }

            @Override
            public void onTextChanged(CharSequence charSequence) {

                if (charSequence.toString().contains(" ")){
                    if (Utils.isValidEmail(charSequence.toString().trim())){
                        chipInputTo.addChip(charSequence.toString().trim(),"");
                    } else {
                        String[] seqSplit = charSequence.toString().split(" ");

                        //  charSequence.toString().replace(" ","");

                        //    chipInputTo.getEditText().setText(charSequence);

                        if (seqSplit.length == 1){
                            Toast.makeText(ScheduleActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!Utils.isValidEmail(chipInputTo.getEditText().getText().toString())){
                    Toast.makeText(CaseDetail.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }else*/ if (etSubject.getText().toString().isEmpty()){
                    Toast.makeText(ScheduleActivity.this, "Please enter subject", Toast.LENGTH_SHORT).show();
                }else if (etMessage.getText().toString().isEmpty()){
                    Toast.makeText(ScheduleActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                }else {
                    if (Utils.isNetworkConnectedMainThred(getApplicationContext())){
                        hitEmailApi(type,etSubject.getText().toString().trim(),etMessage.getText().toString().trim());
                    }else {
                        Toast.makeText(ScheduleActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraGalleryDialogAttachment();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialog.dismiss();
                CallDialog();
            }
        });

    }

    private void call(View view, final String number){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));//"tel:"+number.getText().toString();
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
    }

    private void hitEmailApi(String type,String subject, String content) {

        loader.show();

        String url = Utils.getCompleteApiUrl(this, R.string.SendSupportEmail);

        Log.v("hitEmailApi", url);

        // selectedMembersList.remove(0);

        JSONArray emailArray = new JSONArray();

        //emailArray.put(selectedMembersList);

        for (int i = 0;i<selectedMembersList.size();i++){
            try {
                emailArray.put(i,selectedMembersList.get(i).trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject json_data = new JSONObject();

        try {
           /* int chunkCount = encodedString1.length() / 4000;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= encodedString1.length()) {
                    Log.v("###encodedString-1", encodedString1.substring(4000 * i));
                } else {

                    Log.v("###encodedString-2", encodedString1.substring(4000 * i, max));
                }
            }*/

            jsonObject.put("type", type);
            jsonObject.put("email_array", emailArray);
            jsonObject.put("subject", subject);
            jsonObject.put("content", content);
            jsonObject.put("attachment", encodedString1);
            jsonObject.put("case_id", case_id);
            jsonObject.put("surveyor_id", pref.get(Constants.surveyor_id));

            json_data.put("VIS", jsonObject);

            Log.v("hitEmailApi", json_data.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(url)
                .addJSONObjectBody(json_data)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonEmail(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        loader.cancel();
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

    private void parseJsonEmail(JSONObject response) {

        Log.v("res:hitEmailApi", response.toString());

        try {
            JSONObject jsonObject = response.getJSONObject("VIS");
            String res_msg = jsonObject.getString("response_message");
            String res_code = jsonObject.getString("response_code");

            if (res_code.equals("1")) {
                Toast.makeText(ScheduleActivity.this, res_msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ScheduleActivity.this, res_msg, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ScheduleActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        loader.cancel();
        emailDialog.dismiss();
    }

    public void showCameraGalleryDialogAttachment() {

        captureType = 2;

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.camera_gallery_popup);

        dialog.show();

        RelativeLayout rrCancel = (RelativeLayout) dialog.findViewById(R.id.rr_cancel);
        LinearLayout llCamera = (LinearLayout) dialog.findViewById(R.id.ll_camera);
        LinearLayout llGallery = (LinearLayout) dialog.findViewById(R.id.ll_gallery);

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                dialog.dismiss();
            }
        });


        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                dialog.dismiss();
            }
        });

        rrCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // AppUtils.showToastSort(mActivity,getString(R.string.permission_camera));

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        }

        else {

            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
        // start the image capture Intent
    }

    public Uri getOutputMediaFileUri(int type) {
        //return Uri.fromFile(getOutputMediaFile(type));

        return FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".provider", getOutputMediaFile(type));

    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    //method to convert string into base64
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        //String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);

        return imgString;
    }

    public static String getFileType(String path) {
        String fileType = null;
        fileType = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase();
        return fileType;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                picturePath = fileUri.getPath().toString();
                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                Log.d("filename_camera", filename);
                String selectedImagePath = picturePath;
                Uri uri = Uri.parse(picturePath);
                ext = "jpg";
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 500;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(picturePath));
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();

                encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

              if (captureType == 2){
                    ivAttachment.setVisibility(View.VISIBLE);
                    ivAttachment.setImageBitmap(rotatedBitmap);
                }

            }
        }else if (requestCode == SELECT_PICTURE) {
            if (data != null) {

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    encodedString1  = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);

                    //  encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mArrayUri.get(0));

                     if (captureType == 2){
                            ivAttachment.setVisibility(View.VISIBLE);
                            ivAttachment.setImageBitmap(bitmap);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //   encodedString1 = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                }


            } else {
                Toast.makeText(ScheduleActivity.this, "Unable to select image", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void CallingDialog() {
        cal_list.clear();
        co_number_list.clear();
        alternat_list.clear();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.calling_dialog);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //final int poss;
        ImageView close = dialog.findViewById(R.id.iv_close);
        final Button cal = dialog.findViewById(R.id.btn_call);
        final Button dnt_cal = dialog.findViewById(R.id.btn_notcall);
        TextView header = dialog.findViewById(R.id.tv_header);
        final ListView lv_call = dialog.findViewById(R.id.lv_calls);

        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("number", et_mobile.getText().toString());
        cal_list.add(hmap);

        HashMap<String, String> hmapp = new HashMap<>();
        hmapp.put("number", et_mobilee.getText().toString());
        co_number_list.add(hmap);

        HashMap<String, String> hmappp = new HashMap<>();
        hmap.put("number", et_alternate.getText().toString());
        alternat_list.add(hmap);


        if (status_contact1.equals("1")) {
            header.setText("Customer Calling Numbers");
            adater = new CallAdapter(this, cal_list);
            lv_call.setAdapter(adater);

            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // pos = position;
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);
                    for (int i = 0; i < lv_call.getChildCount(); i++) {
                        if (position == i) {
                            // lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.red));
                            callstatus = 1;
                            lv_call.setAdapter(adater);
                           /* view.setTag(R.color.view_colour);
                            int ColorId = Integer.parseInt(view.getTag().toString());
                            if (ColorId == R.color.view_colour) {
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.trans_white));
                            }
                            else{
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                            }*/


                        } else {
                            callstatus = 2;
                            lv_call.setAdapter(adater);
                            //   lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);


                        }
                    }

                }
            });

        } else if (status_contact1.equals("2")){
            header.setText("Coordinating Person Alternate Numbers");
            adater = new CallAdapter(this, alternat_list);
            lv_call.setAdapter(adater);
            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + alternat_list.get(position).get("number")));//"tel:"+number.getText().toString();
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);*/

                  //  pos = position;
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);
                    for (int i = 0; i < lv_call.getChildCount(); i++) {
                        if (position == i) {
                            lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                            callstatus = 1;
                            lv_call.setAdapter(adater);


                            // iv_phn.setBackgroundResource(R.mipmap.phone_white);
                        } else {
                            callstatus = 2;
                            lv_call.setAdapter(adater);
                            lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            //   iv_phn.setBackgroundResource(R.drawable.ic_telephone);

                        }
                    }
                }
            });
        }
        else if (status_contact1.equals("3")){
            header.setText("Coordinating Person Numbers");
            adater = new CallAdapter(this, co_number_list);
            lv_call.setAdapter(adater);
            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + alternat_list.get(position).get("number")));//"tel:"+number.getText().toString();
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);*/

                   // pos = position;
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);

                    try {
                        for (int i = 0; i < lv_call.getChildCount(); i++) {
                            if (position == i) {
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                                callstatus = 1;
                                lv_call.setAdapter(adater);


                                // iv_phn.setBackgroundResource(R.mipmap.phone_white);
                            } else {
                                callstatus = 2;
                                lv_call.setAdapter(adater);
                                lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                //   iv_phn.setBackgroundResource(R.drawable.ic_telephone);

                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                if (status_contact1.equals("1")){
                    //callIntent.setData(Uri.parse("tel:" + cal_list.get(pos).get("number")));//"tel:"+number.getText().toString();
                    callIntent.setData(Uri.parse("tel:" + cal_list.get(0).get("number")));//"tel:"+number.getText().toString();
                }else if (status_contact1.equals("2")){
                    callIntent.setData(Uri.parse("tel:" + alternat_list.get(0).get("number")));//"tel:"+number.getText().toString();
                }else if (status_contact1.equals("3")){
                    callIntent.setData(Uri.parse("tel:" + co_number_list.get(0).get("number")));//"tel:"+number.getText().toString();
                }
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

                Intent intent = new Intent(mActivity,CallRecordingService.class);
                startService(intent);

            }
        });




           /* header.setText("Customer Calling Number");
            adater = new CallAdapter(this, cal_list);
            lv_call.setAdapter(adater);

            lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cal.setVisibility(view.VISIBLE);
                    dnt_cal.setVisibility(view.GONE);
                    for (int i = 0; i < lv_call.getChildCount(); i++) {
                        if (position == i) {
                            // lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.red));
                            callstatus = 1;
                            lv_call.setAdapter(adater);
                           *//* view.setTag(R.color.view_colour);
                            int ColorId = Integer.parseInt(view.getTag().toString());
                            if (ColorId == R.color.view_colour) {
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.trans_white));
                            }
                            else{
                                lv_call.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.view_colour));
                            }*//*


                        } else {
                            callstatus = 2;
                            lv_call.setAdapter(adater);
                            //   lv_call.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);


                        }
                    }

                }
            });


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);

                callIntent.setData(Uri.parse("tel:" + et_mobile.getText().toString()));//"tel:"+number.getText().toString();

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

                Intent intent = new Intent(mActivity,CallRecordingService.class);
                startService(intent);

            }
        });*/


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    public class CallAdapter extends BaseAdapter {
        LayoutInflater inflter;
        Context context;

        ArrayList<HashMap<String, String>> alist;


        public CallAdapter(Context context, ArrayList<HashMap<String, String>> alist) {
            inflter = (LayoutInflater.from(context));
            this.context = context;
            this.alist = alist;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.call_adapter, null);
            number = view.findViewById(R.id.tv_number);
            iv_phn = view.findViewById(R.id.iv_phn);
            iv_phn2 = view.findViewById(R.id.iv_phn2);

            if (callstatus == 1) {
                view.setBackgroundColor(getResources().getColor(R.color.greyLight));
                iv_phn2.setVisibility(View.VISIBLE);
                iv_phn.setVisibility(View.GONE);
                number.setTextColor(getResources().getColor(R.color.app_header));
            } else {
                iv_phn2.setVisibility(View.GONE);
                iv_phn.setVisibility(View.VISIBLE);
            }
            iv_phn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + et_mobile.getText().toString()));//"tel:"+number.getText().toString();
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);

                }
            });

            number.setText(alist.get(i).get("number"));
            return view;
        }

    }


}
