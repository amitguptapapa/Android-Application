package com.vis.android.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vis.android.Activities.Dashboard;
import com.vis.android.Common.AppContext;
import com.vis.android.Fragments.Notification;
import com.vis.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


import static android.content.Context.NOTIFICATION_SERVICE;
import static android.util.Log.e;

/**
 * Created by Mohammad Faiz on 3/29/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "ID: " + remoteMessage.getMessageId());
        Log.e(TAG, "Type: " + remoteMessage.getMessageType());
        Log.e(TAG, "Data: " + remoteMessage.getData());
        Log.e(TAG, "Body: " + remoteMessage.getNotification().getBody());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData());

                //JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(jsonObject);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);

            try {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                intent.putExtra("notificationFragment", "notificationFragment");

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                String channelId = "Default";
                NotificationCompat.Builder builder = new  NotificationCompat.Builder(getApplicationContext(), channelId)
                        .setSmallIcon(R.mipmap.vis_iconn)
                        .setContentTitle("VIS")
                        .setContentText(message).setAutoCancel(true).setContentIntent(pendingIntent);;
                NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                }

                manager.notify(0, builder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }


        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {

            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            long time= System.currentTimeMillis();

            String message = json.getString("message");
            String imageUrl = json.getString("largeIcon");
            String title = json.getString("title");
            String id = json.getString("id");
            String add_date = json.getString("add_date");
            String datetime = String.valueOf(time);
            String type = json.getString("type");

            e(TAG, "message: " + message);
            e(TAG, "largeIcon: " + imageUrl);
            e(TAG, "title: " + title);
            e(TAG, "id: " + id);
            e(TAG, "add_date: " + add_date);
            e(TAG, "datetime: " + datetime);
            e(TAG, "type: " + type);

            ContentValues mContentValues = new ContentValues();

           /* mContentValues.put(TablePush.pushColumn.message.toString(), json.get("message").toString());
            mContentValues.put(TablePush.pushColumn.largeIcon.toString(), json.get("largeIcon").toString());
            mContentValues.put(TablePush.pushColumn.title.toString(), json.get("title").toString());
            mContentValues.put(TablePush.pushColumn.id.toString(), json.get("id").toString());
            mContentValues.put(TablePush.pushColumn.add_date.toString(),  json.get("add_date").toString());
            mContentValues.put(TablePush.pushColumn.datetime.toString(),  datetime);
            mContentValues.put(TablePush.pushColumn.type.toString(),  json.get("type").toString());
            DatabaseController.insertUpdateData(mContentValues, TablePush.push, TablePush.pushColumn.id.toString(),json.get("id").toString());
*/
            Intent resultIntent = null;

            /*if(type.equals("2"))
            {
                // app is in background, show the notification in notification tray
                resultIntent = new Intent(getApplicationContext(), Notification.class);
                resultIntent.putExtra("message", message);
                resultIntent.putExtra("type", type);
                resultIntent.putExtra("id", id);
            }
            else
            {
                resultIntent = new Intent(getApplicationContext(), Notification.class);
                resultIntent.putExtra("message", message);
                resultIntent.putExtra("type", type);
                resultIntent.putExtra("id", id);
            }*/

//            resultIntent = new Intent(getApplicationContext(), Notification.class);
//            resultIntent.putExtra("message", message);
//            resultIntent.putExtra("type", type);
//            resultIntent.putExtra("id", id);

            Intent intent = null;
            intent = new Intent(this, Notification.class);
            intent.putExtra("message", message);
            intent.putExtra("type", type);
            intent.putExtra("id", id);
            intent.putExtra("notificationFragment", "notificationFragment");

            if (TextUtils.isEmpty(imageUrl)) {
                showNotificationMessage(getApplicationContext(), title, message,datetime,intent,type);
            } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, message,datetime,intent, imageUrl,type);
            }

        } catch (JSONException e) {
            e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent,String type) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent,type);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl,String type) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl,type);
    }
}