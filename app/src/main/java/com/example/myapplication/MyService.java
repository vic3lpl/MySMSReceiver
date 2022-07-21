package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MyService extends Service {
    int myInt = 0;
    String myFlag = "cont";
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
//        onTaskRemoved(intent);
        //continueMyTask(intent);
        //Toast.makeText(getApplicationContext(),"This is a service running in Background",Toast.LENGTH_SHORT).show();
        Log.d("azlim", String.valueOf(myInt++));
        startReadSMS(intent);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
//        Intent restartServicIntent = new Intent(getApplicationContext(), this.getClass());
//        restartServicIntent.setPackage(getPackageName());
//        startService(restartServicIntent);
        super.onTaskRemoved(rootIntent);
        myFlag = "Stop";
        stopSelf();
        //stopService(restartServicIntent);
    }

    void continueMyTask(Intent rootIntent){
        if(myFlag.equals("cont")){
            Intent restartServicIntent = new Intent(getApplicationContext(), this.getClass());
            restartServicIntent.setPackage(getPackageName());
            startService(restartServicIntent);
        }

    }

    void startReadSMS(Intent intent){
        String ff = intent.getExtras().getString("content");
        Intent intent2 = new Intent();
        intent2.setAction("com.app.sms");
        intent2.putExtra("sms",ff);
        sendBroadcast(intent2);
        Log.d("azlim","MyService " + ff);
    }
}