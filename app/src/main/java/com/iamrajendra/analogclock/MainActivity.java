package com.iamrajendra.analogclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private Intent intent;
    private Switch aSwitch;
    private SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        AnalogClock analogClock = findViewById(R.id.analogClock);

        sharedPreferences = getSharedPreferences("setting",0);

        aSwitch = findViewById(R.id.active_s);
        aSwitch.setOnCheckedChangeListener(this);

        aSwitch.setChecked(sharedPreferences.getBoolean("key",false));
        intent = new Intent(this, ClockReceiver.class);

        alarmIntent = PendingIntent.getBroadcast(this, 111, intent, 0);

        setTime();


//        workManager();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTime() {
        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.SECOND,00);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR)+1);

        alarmManager.cancel(alarmIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmIntent);
        }


    }



    @Override
    protected void onStop() {
        super.onStop();
//        if (alarmManager != null) {
//            alarmManager.cancel(alarmIntent);
//        }


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("key",isChecked);
        edit.commit();
        if (isChecked){
            if (alarmIntent!=null)
            setTime();
        }else {
if (alarmIntent!=null)
            alarmManager.cancel(alarmIntent);
        }

    }
}
