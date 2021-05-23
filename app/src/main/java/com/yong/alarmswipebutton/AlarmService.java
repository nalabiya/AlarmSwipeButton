package com.yong.alarmswipebutton;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;
import static com.yong.alarmswipebutton.MainActivity.context;

public class AlarmService extends Service {
    private AlarmReceiver mReceiver = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @SuppressLint("InvalidWakeLockTag")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "ScreenService2");

        PowerManager powerManager;

        PowerManager.WakeLock wakeLock;

        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);

        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "WAKELOCK");

        wakeLock.acquire(); // WakeLock 깨우기

        if(intent != null){
            if(intent.getAction()==null){
                if(mReceiver==null){
                    mReceiver =new AlarmReceiver();
                    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                    registerReceiver(mReceiver, filter);

                    Intent dialogIntent = new Intent(this, AlarmActivity.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                }
            }
        }
        return START_REDELIVER_INTENT;
    }
}