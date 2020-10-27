package com.vis.android.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vis.android.Common.CustomLoader;
import com.vis.android.Extras.BaseActivity;
import com.vis.android.Fragments.PriliminaryActivity;
import com.vis.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GalleryActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout rl_back,rl_dots,rlDownload;

    TextView tv_header;

    ProgressBar Progress;

    ViewPager pager;

    FullScreenImageAdapter fullScreenImageAdapter;

    //String imageUrl = "";

    ImageView ivDownload;

    CustomLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        init();
        setListeners();

    }

    private void init(){

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        //imageUrl = PriliminaryActivity.documentImagesList.get(0).trim();

        Progress = findViewById(R.id.Progress);
        Progress.setVisibility(View.GONE);

        rlDownload = findViewById(R.id.rlDownload);
       // rlDownload.setVisibility(View.VISIBLE);

        rl_back = findViewById(R.id.rl_back);
        rl_dots = findViewById(R.id.rl_dots);
        rl_dots.setVisibility(View.GONE);

        ivDownload = findViewById(R.id.ivDownload);
        ivDownload.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        tv_header = findViewById(R.id.tv_header);
        tv_header.setText("Documents");

        pager = findViewById(R.id.pager);

        if (DocumentsActivity.act == 1){
            fullScreenImageAdapter = new FullScreenImageAdapter(mActivity, DocumentsActivity.documentImagesList);
        }else {
            fullScreenImageAdapter = new FullScreenImageAdapter(mActivity, PriliminaryActivity.documentImagesList);
        }

        pager.setAdapter(fullScreenImageAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //imageUrl = PriliminaryActivity.documentImagesList.get(position).trim();

               // tvHeader.setText((position+1)+" of "+AppConstants.separated.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setListeners(){
        rl_back.setOnClickListener(this);
        rlDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.rlDownload:

               // String[] urlSplit = imageUrl.split("/");

               // Log.v("asfadddfsfasf",urlSplit[7]);

               // getBitmapFromURL(imageUrl, urlSplit[7]);

                break;

        }
    }

    public class FullScreenImageAdapter extends PagerAdapter {

        private Activity _activity;
        private ArrayList<String> data;
        private LayoutInflater inflater;

        // constructor
        public FullScreenImageAdapter(Activity activity,
                                      ArrayList<String> imagePaths) {
            this._activity = activity;
            this.data = imagePaths;
        }

        @Override
        public int getCount() {
            return this.data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imgDisplay;
            TextView tvCaption;
            Button btnAddCaption;

            inflater = (LayoutInflater) _activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.inflate_gallery, container,
                    false);

            imgDisplay = viewLayout.findViewById(R.id.imageView);
            tvCaption = viewLayout.findViewById(R.id.tvCaption);
            btnAddCaption = viewLayout.findViewById(R.id.btnAddCaption);

            try {
                tvCaption.setText(PriliminaryActivity.captionList.get(position).trim());

                if (PriliminaryActivity.from == 2){
                    btnAddCaption.setVisibility(View.VISIBLE);
                }

                if (PriliminaryActivity.from == 1){
                    try {
                        Picasso.with(getApplicationContext()).load(data.get(position).trim()).into(imgDisplay);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (PriliminaryActivity.from == 2){
                    try {
                        Picasso.with(getApplicationContext()).load(data.get(position).trim()).into(imgDisplay);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            try{
                tvCaption.setText(DocumentsActivity.captionList.get(position).trim());

                if (DocumentsActivity.from == 2){
                    btnAddCaption.setVisibility(View.VISIBLE);
                }

                if (DocumentsActivity.from == 1){
                    try {
                        Picasso.with(getApplicationContext()).load(data.get(position).trim()).into(imgDisplay);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (DocumentsActivity.from == 2){
                    try {
                        Picasso.with(getApplicationContext()).load(data.get(position).trim()).into(imgDisplay);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            ((ViewPager) container).addView(viewLayout);

            return viewLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }
    }

    public void getBitmapFromURL(String src, String fileName) {
        try {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            createDirectoryAndSaveFile(myBitmap, fileName);

           // return myBitmap;
        } catch (Exception e) {
            Toast.makeText(mActivity, "Failed to download document", Toast.LENGTH_SHORT).show();
            loader.cancel();
            e.printStackTrace();
           // return null;
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/VIS Documents");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/VIS Documents/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/VIS Documents/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            Toast.makeText(mActivity, "Document downloaded successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mActivity, "Failed to download document", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        loader.cancel();
    }


}
