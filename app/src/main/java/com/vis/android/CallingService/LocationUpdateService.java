package com.vis.android.CallingService;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.vis.android.Activities.LandmarkPopupActivity;
import com.vis.android.Activities.ScheduleActivity;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.Notification.Config;
import com.vis.android.Notification.NotificationUtils;
import com.vis.android.R;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by deepshikha on 24/11/16.
 */

public class LocationUpdateService extends Service implements LocationListener {

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 10000;
    public static String str_receiver = "com.vis.android.CallingService.receiver";
    Intent intent;

    double measuredDistance = 0;

    Preferences pref;

    public LocationUpdateService() {

    }

    public LocationUpdateService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
        //return START_REDELIVER_INTENT;
    }

   /* @Override public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(
                getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmService.set(ELAPSED_REALTIME, elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("EXIT", "ondestroy!");

        Intent broadcastIntent = new Intent(this, RestartService.class);

        sendBroadcast(broadcastIntent);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        pref = new Preferences(getApplicationContext());

        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);
        intent = new Intent(str_receiver);
//        fn_getlocation();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null){

                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }

            }


            if (isGPSEnable){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }
            }

            //////////////

            //showNotification();

        }

    }

    private class TimerTaskToGetLocation extends TimerTask{
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });

        }
    }

    private void fn_update(Location location){

        intent.putExtra("latitude",location.getLatitude()+"");
        intent.putExtra("longitude",location.getLongitude()+"");

        measuredDistance = calculateDistance(location.getLatitude(),location.getLongitude(),
                Double.parseDouble(pref.get(Constants.endLat)),Double.parseDouble(pref.get(Constants.endLng)));

        if (measuredDistance >= 1000){

            Log.v("measureDist",measuredDistance/1000+" km");

            double inKm = measuredDistance/1000;

            intent.putExtra("distance",String.format("%.2f",inKm)+" km");
          //  tvDistance.setText("Measured Distance: "+String.format("%.2f",inKm)+" km");
        }else {
         //   Log.v("measureDist",measuredDistance+" m");
          //  tvDistance.setText("Measured Distance: "+String.format("%.2f",measuredDistance)+" m");

            intent.putExtra("distance",String.format("%.2f",measuredDistance)+" m");
        }

        intent.putExtra("distanceMeasured", String.valueOf(measuredDistance));

        if (measuredDistance >= 2000 && measuredDistance <=2300){
            showNotification();
        }

        sendBroadcast(intent);
    }

    public double calculateDistance(Double startLat,Double startLng,Double endLat,Double endLng) {
        int Radius=6371000;//radius of earth in mtr
        double dLat = Math.toRadians(endLat-startLat);
        double dLon = Math.toRadians(endLng-startLng);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));

        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
    }


    private void showNotification(){
        Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
        //   pushNotification.putExtra("message", "Test Message");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);

        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());

        notificationUtils.playNotificationSound();

        try {
            Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
            intent.putExtra("notificationFragment", "ScheduleActivity");

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new  NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.mipmap.vis_iconn)
                    .setContentTitle("Test")
                    .setAutoCancel(false)
                    .setContentText("Latitude: "+latitude+" Longitude: "+longitude)
                    .setContentIntent(pendingIntent)
                    //   .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
            }

            manager.notify(0, builder.build());

            startActivity(new Intent(getApplicationContext(), LandmarkPopupActivity.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}