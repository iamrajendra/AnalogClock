package com.iamrajendra.analogclock.utlis;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.iamrajendra.analogclock.ClockReceiver;
import com.iamrajendra.analogclock.PendulumAlarmReceiver;
import com.iamrajendra.analogclock.model.PeriodTableV2;

public class PendulumAlarmManager {
    private  PendingIntent pendingIntent;
    private final AlarmManager alarmManager;
    Intent intent;
private Context context;

    public PendulumAlarmManager(Context context) {
        this.context = context;
        intent = new Intent(context, PendulumAlarmReceiver.class);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


    }

    public void createAlarm(String key, long time) {
        intent.putExtra("key",key);
        pendingIntent = PendingIntent.getBroadcast(context, 111, intent, 0);

        alarmManager.cancel(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent);
        }

    }

    public void createAlarm(PeriodTableV2 periodTableV2) {
        intent.putExtra("key",periodTableV2.getUid());
        intent.putExtra("title",periodTableV2.getTitle());
        intent.putExtra("des",Date.getTime(Date.TIME,periodTableV2.getStartDate())+":"+Date.getTime(Date.TIME,periodTableV2.getEndDate())+" "+periodTableV2.getDescription());

        pendingIntent = PendingIntent.getBroadcast(context, 111, intent, 0);

//        alarmManager.cancel(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    periodTableV2.getStartDate(),
                    pendingIntent);
        }
    }
}
