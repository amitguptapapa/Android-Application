package com.vis.android.CallingService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(BroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, LocationUpdateService.class));;
    }
}