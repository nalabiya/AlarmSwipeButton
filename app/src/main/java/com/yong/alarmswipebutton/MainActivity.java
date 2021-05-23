package com.yong.alarmswipebutton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.app.AlarmManager.RTC_WAKEUP;

public class MainActivity extends AppCompatActivity {
    public static Object context;

    int alarmHour = 0, alarmMinute = 0;
    Calendar alarmCalendar;
    AlarmManager alarmManager;

    Intent alarmIntent;

    PendingIntent alarmCallPendingIntent;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        alarmHour = 20;
        alarmMinute = 00;

        TextView textview = (TextView)findViewById(R.id.maintext);
        textview.setText("알람 예정 시간 : " + alarmHour + " : " + alarmMinute);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);


        PowerManager powerManager;

        PowerManager.WakeLock wakeLock;

        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);

        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WAKELOCK");

        wakeLock.acquire(); // WakeLock 깨우기

        setAlarm();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarm() {
        alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(System.currentTimeMillis());
        alarmCalendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        alarmCalendar.set(Calendar.MINUTE, alarmMinute);
        alarmCalendar.set(Calendar.SECOND, 0);
        // TimePickerDialog 에서 설정한 시간을 알람 시간으로 설정

        alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmIntent.setAction(AlarmReceiver.ACTION_RESTART_SERVICE);

        alarmCallPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), alarmCallPendingIntent);
        ///////////////////////////////////////////////////
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //    alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, calendar.getTimeInMillis( ), pendingIntent);

    } // 알람 설정
}