package com.vis.android.CallingService;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CallRecordingService extends Service {
    final MediaRecorder recorder = new MediaRecorder();
    boolean recording = false;
    int i = 0;
    String fname;

    BroadcastReceiver CallRecorder = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            // TODO Auto-generated method stub
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            i++;

            if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                Toast.makeText(getApplicationContext(), state, Toast.LENGTH_LONG).show();

                Toast.makeText(arg0, "Start CaLLED " + recording + fname, Toast.LENGTH_LONG).show();

                startRecording();
            }

            if (TelephonyManager.EXTRA_STATE_IDLE.equals(state) && recording == true) {
                Toast.makeText(getApplicationContext(), state, Toast.LENGTH_LONG).show();

                Toast.makeText(arg0, "STOP CaLLED :" + recording, Toast.LENGTH_LONG).show();
                stopRecording();
            }

            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                fname = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Toast.makeText(getApplicationContext(), state + " : " + fname, Toast.LENGTH_LONG).show();
            }
        }
    };

    BroadcastReceiver OutGoingNumDetector = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            fname = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        }
    };

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_LONG).show();

        IntentFilter RecFilter = new IntentFilter();
        RecFilter.addAction("android.intent.action.PHONE_STATE");
        registerReceiver(CallRecorder, RecFilter);
        IntentFilter OutGoingNumFilter = new IntentFilter();
        OutGoingNumFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(OutGoingNumDetector, OutGoingNumFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(CallRecorder);
        unregisterReceiver(OutGoingNumDetector);
        Toast.makeText(getApplicationContext(), "Destroyed", Toast.LENGTH_SHORT).show();
    }

    public void startRecording() {
        if (recording == false) {

            Toast.makeText(getApplicationContext(), "Recorder_Sarted"+fname, Toast.LENGTH_LONG).show();
            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            String file= Environment.getExternalStorageDirectory().toString();
            String filepath= file+"/11111111111111";
            File dir= new File(filepath);
            dir.mkdirs();

            filepath+="/"+fname+".3gp";
            recorder.setOutputFile(filepath);

            try {
                recorder.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            recorder.start();
            recording=true;
        }
    }

    public void stopRecording()
    {
        if(recording==true)
        {
            Toast.makeText(getApplicationContext(), "Recorder_Released from "+recording, Toast.LENGTH_LONG).show();

            recorder.stop();
            recorder.reset();
            recorder.release();
            recording=false;
            broadcastIntent();
        }
    }

    public void broadcastIntent()
    {
        Intent intent = new Intent();
        intent.setAction("com.async.android.Calling.CUSTOM_INTENT");
        sendBroadcast(intent);
        Toast.makeText(getApplicationContext(), "BroadCaste", Toast.LENGTH_LONG).show();
    }
}
