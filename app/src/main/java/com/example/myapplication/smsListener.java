package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;

public class smsListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if(bundle != null){
                try{
                    String msgBody = "";
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                        Log.d("azlim",msgBody);
                    }
                    startReadSMSService(context, msgBody);
                }catch (Exception e){

                }
            }
        }
    }

    void startReadSMSService(Context context, String smsContent){
        Intent serviceIntent = new Intent(context.getApplicationContext(), MyService.class);
        serviceIntent.putExtra("content",smsContent);
        context.startService(serviceIntent);
        //TextView textView =
        //context.startService(new Intent(context.getApplicationContext(), MyService.class));
    }
}
