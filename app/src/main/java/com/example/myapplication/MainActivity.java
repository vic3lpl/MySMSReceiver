package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Button button;
    Button button2;
    Button button3;
    TextView textView;
    GoogleApiClient googleApiClient;

    //private BroadcastReceiver receiver = new smsListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        textView = findViewById(R.id.smsContent);
        requestSmsPermission();
        IntentFilter intentFilter = new IntentFilter("com.app.sms");
        registerReceiver(myReceiver, intentFilter);

        googleApiClient = new GoogleApiClient.Builder(getBaseContext())
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();
        googleApiClient.connect();

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
                //readLineByLine();
                getPhoneNumberMethod2();
//                getHintPhoneNumber();
            }
        });

    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //if(intent.getAction().equalsIgnoreCase("com.app.sms")) {

            textView.setText(intent.getStringExtra("sms"));
            //}
        }
    };

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        String readPhoneStatePermission = Manifest.permission.READ_PHONE_STATE;
        String readSMSPermission = Manifest.permission.READ_SMS;
        String readPhoneNoPermission = Manifest.permission.READ_PHONE_NUMBERS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[4];
            permission_list[0] = permission;
            permission_list[1] = readPhoneNoPermission;
            permission_list[2] = readPhoneStatePermission;
            permission_list[3] = readSMSPermission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }


    }

    private void createTxtFile() throws IOException {

        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File(root.getAbsolutePath() + "/download");
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

        File dir = new File(root.getAbsolutePath() + "/download");
        //File path = this.getExternalFilesDir(null);
        File file = new File(dir, "myAZLIM.txt");
        if (file.exists()) {
            FileOutputStream stream = new FileOutputStream(file, true);
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
        File dir = new File(root.getAbsolutePath() + "/download");
        File file = new File(dir, "myAZLIM.txt");
        FileInputStream is;
        BufferedReader reader;
        ArrayList<String[]> sList = new ArrayList<>();
        String[] elements;
        if (file.exists()) {
            is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
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
        for (int c = 0; c < sList.size(); c++) {
            elements = sList.get(c);
            Log.d("azlim", elements[0]);
            Log.d("azlim", elements[1]);
        }
    }

    public void getPhoneNumberMethod2() {
        TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
//            String mPhoneNumber = tMgr.getLine1Number();
//            textView.setText(mPhoneNumber);
            SubscriptionManager subManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> subInfoList = subManager.getActiveSubscriptionInfoList();
            String nop = "";
            String nop2 = "";
            for(int i = 0; i < subInfoList.size(); i++ ) {
                int subID = subInfoList.get(i).getSubscriptionId();
                int simPosition = subInfoList.get(i).getSimSlotIndex();

                if(subManager.isNetworkRoaming(subID)) {
                    Log.d("azlim", "Simcard in slot " + simPosition + " has number == " + subInfoList.get(i).getNumber() + " and it is in ROAMING");
                    nop = subInfoList.get(i).getNumber();
                }else {
                    nop2 = nop2 +","+ subInfoList.get(i).getNumber();
                    Log.d("azlim", "Simcard in slot " + simPosition + " has number == " + subInfoList.get(i).getNumber() + " and it is HOME");
                }

            }
            textView.setText(nop + " " + nop2);
        }
    }

    public void getHintPhoneNumber() {
        HintRequest hintRequest =
                new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .build();
        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), 1008, null, 0, 0, 0, null);
        }  catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void applyGoogleCredential(Context context){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1008:
                if (resultCode == RESULT_OK) {
                    Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
//                    cred.getId====: ====+919*******
                    Log.d("azlim", cred.getId());
                    textView.setText(cred.getId().toString());


                } else {
                    // Sim Card not found!
                    Log.e("azlim", "1008 else");

                    return;
                }


                break;
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}