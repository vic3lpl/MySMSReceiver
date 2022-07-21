package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    //private BroadcastReceiver receiver = new smsListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        requestSmsPermission();
        IntentFilter intentFilter = new IntentFilter("com.app.sms");
        registerReceiver(myReceiver, intentFilter);
        //registerReceiver(receiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startService(new Intent(getApplicationContext(),MyService.class));
            }
        });

    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //if(intent.getAction().equalsIgnoreCase("com.app.sms")) {
                textView = findViewById(R.id.smsContent);
                textView.setText(intent.getStringExtra("sms"));
            //}
        }
    };

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        Toast.makeText(getApplicationContext(), "onResumed called", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("azlim","app paused");
        Toast.makeText(getApplicationContext(), "onPause called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("azlim","app Stop");
        Toast.makeText(getApplicationContext(), "onDestroy called", Toast.LENGTH_SHORT).show();
    }

}