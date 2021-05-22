package com.yong.alarmswipebutton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.app.AlarmManager.RTC_WAKEUP;

public class MainActivity extends AppCompatActivity {
    public static Object context;
    ImageButton lightButton, cctvButton, alarmButton, detectModeButton;
    int alarmHour = 0, alarmMinute = 0;
    Calendar alarmCalendar;
    static boolean alarmActive = false;
    AlarmManager alarmManager;

    Intent alarmIntent;

    PendingIntent alarmPendingIntent, alarmCallPendingIntent, detectPendingIntent, dataPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        alarmHour = 19;
        alarmMinute = 39;

        TextView textview = (TextView)findViewById(R.id.maintext);
        textview.setText("알람 예정 시간 : " + alarmHour + " : " + alarmMinute);

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

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmCalendar.getTimeInMillis(), alarmCallPendingIntent);
        ///////////////////////////////////////////////////
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //    alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, calendar.getTimeInMillis( ), pendingIntent);

    } // 알람 설정
}