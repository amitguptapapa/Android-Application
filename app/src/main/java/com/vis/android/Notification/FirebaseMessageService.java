package com.vis.android.Notification;

/*
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

//Created by Mohammad Faiz on 3/29/2017.

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";
    String CHANNEL_ID = "com.vis.android";
    private NotificationManager mManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " + remoteMessage.getData() + "...." + remoteMessage.getFrom());

        if (remoteMessage.getData() != null) {

            Map<String, String> params = remoteMessage.getData();
            JSONObject json = new JSONObject(params);

            try {

                String message = json.getString("message");
                String type = json.getString("type");

                Log.e(TAG, "message: " + message);
                Log.e(TAG, "type: " + type);

                createNotification (message, getString(R.string.app_name),bookingId,type);

            } catch (JSONException e) {
                Log.e(TAG, "Json Exception: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
        }

        }

    }

    public void createNotification(String msg, String title,String id,String type) {

        Log.d("Step2", "Step2");

        Intent intent = null;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("message", msg);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                    title, NotificationManager.IMPORTANCE_HIGH);
            // Sets whether notifications posted to this channel should display notification lights
            androidChannel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true);
            // Sets the notification light color for notifications posted to this channel
            androidChannel.setLightColor(Color.GREEN);
            //androidChannel.setSound(null, null);

            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(androidChannel);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setTicker(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setSound(defaultUri)
                    .setContentIntent(contentIntent);

            getManager().notify(Integer.parseInt(bookingId), notification.build());

        } else {
            try {

                playNotificationSound();

                @SuppressLint({"NewApi", "LocalSuppress"}) NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle(title)
                        .setTicker(title)
                        .setContentText(msg)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentIntent(contentIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setLights(0xFF760193, 300, 1000)
                        .setAutoCancel(true).setVibrate(new long[]{200, 400});

                long timestamp = AppUtils.currentTimestamp();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify((int) timestamp
*/
/* ID of notification *//*
*/
/*
, notificationBuilder.build());

            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }


    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(this, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
*/

