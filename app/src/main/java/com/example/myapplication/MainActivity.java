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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button button2;
    Button button3;
    TextView textView;
    //private BroadcastReceiver receiver = new smsListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        requestSmsPermission();
        IntentFilter intentFilter = new IntentFilter("com.app.sms");
        registerReceiver(myReceiver, intentFilter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createTxtFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //startService(new Intent(getApplicationContext(),MyService.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    appendTxtFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    readLineByLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private void createTxtFile() throws IOException {

        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File (root.getAbsolutePath() + "/download");
        //File path = this.getExternalFilesDir(null);
        File file = new File(dir, "myAZLIM.txt");
        FileOutputStream stream = new FileOutputStream(file);
        try {
            stream.write("1,DDMMYYY,Status \n".getBytes());
            stream.write("2,DDMMYYY,Status \n".getBytes());
            stream.write("3,DDMMYYY,Status \n".getBytes());
            stream.write("4,DDMMYYY,Status \n".getBytes());
            stream.write("5,DDMMYYY,Status \n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
    }

    private void appendTxtFile() throws IOException {

        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File (root.getAbsolutePath() + "/download");
        //File path = this.getExternalFilesDir(null);
        File file = new File(dir, "myAZLIM.txt");
        if(file.exists()){
            FileOutputStream stream = new FileOutputStream(file,true);
            try {
                stream.write("6,DDMMYYY,Status \n".getBytes());
                stream.write("7,DDMMYYY,Status \n".getBytes());
                stream.write("8,DDMMYYY,Status \n".getBytes());
                stream.write("9,DDMMYYY,Status \n".getBytes());
                stream.write("10,DDMMYYY,Status \n".getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                stream.close();
            }
        }

    }

    private void readLineByLine() throws IOException {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/download");
        File file = new File(dir, "myAZLIM.txt");
        FileInputStream is;
        BufferedReader reader;
        ArrayList<String[]> sList = new ArrayList<>();
        String[] elements;
        if (file.exists()) {
            is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while(line != null){
                //Log.d("azlim", line);

                elements = line.split(",");
                sList.add(elements);
                //Log.d("azlim",sList.toString());
                line = reader.readLine();
                if (line == null) {
                    break;
                }
            }
        }
        Collections.reverse(sList);
        for (int c = 0; c < sList.size(); c++){
            elements = sList.get(c);
            Log.d("azlim", elements[0]);
            Log.d("azlim", elements[1]);
        }
    }

    private void loadData() {

        //sList.add()
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