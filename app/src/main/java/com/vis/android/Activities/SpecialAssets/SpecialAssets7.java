package com.vis.android.Activities.SpecialAssets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.vis.android.Activities.SpecialAssets.SpecialAssets1.hm;

public class SpecialAssets7 extends AppCompatActivity implements View.OnClickListener{
    Intent intent;

    //layout
    RelativeLayout rlTvsign;
    LinearLayout mContent;
    RelativeLayout rlCross;
    Dialog dialog1;
    public static String tempDir;
    private String uniqueId;
    public String current = null;
    private Bitmap mBitmap;
    View mView;
    File mypath;
    signature mSignature;
    ImageView mClear;
    Button mGetSign, mCancel;
    String encodedString;
    TextView next;

    EditText etUndertakingName, etUndertakingMobile;

    byte[] byteFormat;
    String imgString = "";
    ImageView signImage;


    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets7);
        getid();
        setListener();
        getid();
        setListener();

        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rlTvsign=(RelativeLayout)findViewById(R.id.rlTvsign);
        rlTvsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*byteFormat = Base64.decode(imgString, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                mView.setImageBitmap(decodedImage);*/

                SignatureDialog();
            }
        });


    }

    public void getid(){
        next=(TextView)findViewById(R.id.next);

        signImage = (ImageView) findViewById(R.id.imageView1);

        etUndertakingMobile = findViewById(R.id.etUndertakingMobile);
        etUndertakingName = findViewById(R.id.etUndertakingName);

    }
    public void setListener(){
        next.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                validation();

                break;
        }
    }

    public void validation(){
        if (etUndertakingName.getText().toString().isEmpty()){
           // Toast.makeText(SpecialAssets7.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Name", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etUndertakingName.requestFocus();
        }
        else if (etUndertakingMobile.getText().toString().isEmpty()){
            //Toast.makeText(SpecialAssets7.this, "Please Enter Mobile No.", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Mobile No.", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etUndertakingMobile.requestFocus();
        }
        else if (etUndertakingMobile.getText().toString().length()<10){
            //Toast.makeText(SpecialAssets7.this, "Please Enter Valid Mobile No.", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Enter Valid Mobile No.", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etUndertakingMobile.requestFocus();
        }
        else if (signImage.getDrawable() == null){
            //Toast.makeText(SpecialAssets7.this, "Please Do Signature", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Do Signature", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

        else {
            putIntoHm();
            Intent intent=new Intent(SpecialAssets7.this,SpecialAssets8.class);
            startActivity(intent);
            return;
        }
    }

    public void putIntoHm() {

        signImage.buildDrawingCache();
        Bitmap bitmapp = signImage.getDrawingCache();

        ByteArrayOutputStream streamm=new ByteArrayOutputStream();
        bitmapp.compress(Bitmap.CompressFormat.PNG, 90, streamm);
        byte[] image=streamm.toByteArray();
        String img_str = Base64.encodeToString(image, 0);
        System.out.println("string:"+img_str);


        hm.put("undertakingName", etUndertakingName.getText().toString());
        hm.put("undertakingMobile", etUndertakingMobile.getText().toString());
        hm.put("signature1", img_str);

    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getSpecialAssets();
        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);


                etUndertakingName.setText(object.getString("undertakingName"));
                etUndertakingMobile.setText(object.getString("undertakingMobile"));

                byteFormat = Base64.decode(object.getString("signature1"), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
                signImage.setImageBitmap(decodedImage);

            }
        }
    }



        public class signature extends View
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v)
        {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);

                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                // String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                encodedString=getEncoded64ImageStringFromBitmap(mBitmap);
                Log.v("log_tag","url: " + encodedString);
                signImage.setImageBitmap(mBitmap);


            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
        }


        //method to convert string into base64
        public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byteFormat = stream.toByteArray();
            // get the base 64 string
            imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

            Log.v("log_tagq",imgString);

            return imgString;
        }

        public void clear()
        {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float eventX = event.getX();
            float eventY = event.getY();


            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++)
                    {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY)
        {
            if (historicalX < dirtyRect.left)
            {
                dirtyRect.left = historicalX;
            }
            else if (historicalX > dirtyRect.right)
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top)
            {
                dirtyRect.top = historicalY;
            }
            else if (historicalY > dirtyRect.bottom)
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    private void SignatureDialog() {
        dialog1 = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.signature);
        Window window = dialog1.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams layoutParams = dialog1.getWindow().getAttributes();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath= new File(directory,current);

        rlCross=(RelativeLayout)dialog1.findViewById(R.id.rlCross);
        mContent = (LinearLayout)dialog1.findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mClear = (ImageView)dialog1.findViewById(R.id.clear);
        mGetSign = (Button)dialog1.findViewById(R.id.getsign);
        //mGetSign.setEnabled(false);
        mCancel = (Button)dialog1.findViewById(R.id.cancel);
        mView = mContent;


        mClear.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                imgString = "";

                Log.v("log_tagi", "string:"+imgString);
                //mGetSign.setEnabled(false);
            }
        });
        mGetSign.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Saved");
                boolean error = captureSignature();
                if(!error){
                    mView.setDrawingCacheEnabled(true);
                    mSignature.save(mView);
                    Bundle b = new Bundle();
                    b.putString("status", "done");
                    Intent intent = new Intent();
                    intent.putExtras(b);
                    setResult(RESULT_OK,intent);
                    dialog1.dismiss();

                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Canceled");
                Bundle b = new Bundle();
                b.putString("status", "cancel");
                Intent intent = new Intent();
                intent.putExtras(b);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        rlCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
        dialog1.setCanceledOnTouchOutside(true);


    }



    private boolean captureSignature() {

        boolean error = false;
        String errorMessage = "";

        if(error){
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 50);
            toast.show();
        }

        return error;
    }

    @SuppressLint("WrongConstant")
    private boolean prepareDirectory()
    {
        try
        {
            if (makedirs())
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean makedirs()
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory())
        {
            File[] files = tempdir.listFiles();
            for (File file : files)
            {
                if (!file.delete())
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }

    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:",String.valueOf(todaysDate));
        return(String.valueOf(todaysDate));

    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));

    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
