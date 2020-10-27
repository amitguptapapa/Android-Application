package com.vis.android.Extras;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.vis.android.Login.OTP;


public class SmsReceiver extends BroadcastReceiver {

    //==============================================   For Receiving otp    ===============================================//


    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody().split(":")[1];
                    message = message.substring(1, message.length());
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message:" + message);
                    Intent myIntent = new Intent("otp");
                    myIntent.putExtra("message",message);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }


}


