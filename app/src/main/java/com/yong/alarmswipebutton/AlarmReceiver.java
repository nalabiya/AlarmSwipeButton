package com.yong.alarmswipebutton;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ACTION_RESTART_SERVICE = "Restart";


    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "AlarmReceiver");



        if(intent.getAction().equals(ACTION_RESTART_SERVICE)) {

            Intent in = new Intent(context, AlarmService.class);
            context.startService(in);
        }
    }
}
