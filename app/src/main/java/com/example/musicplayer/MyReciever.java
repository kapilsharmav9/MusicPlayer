package com.example.musicplayer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"chal gya",Toast.LENGTH_SHORT).show();
        TelephonyManager tm=(TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
      int state=  tm.getCallState();
      if (state==TelephonyManager.CALL_STATE_OFFHOOK)
      {
          Toast.makeText(context,"call recieve ",Toast.LENGTH_SHORT).show();

      }if (state==TelephonyManager.CALL_STATE_IDLE)
      {
          Toast.makeText(context,"No call ",Toast.LENGTH_SHORT).show();

      }
        if (state==TelephonyManager.CALL_STATE_RINGING)
        {
            Toast.makeText(context,"ringing ",Toast.LENGTH_SHORT).show();

        }
    }
}
