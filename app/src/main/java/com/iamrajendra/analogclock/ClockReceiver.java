package com.iamrajendra.analogclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.Locale;

public class ClockReceiver extends BroadcastReceiver {
    private TextToSpeech textToSpeech;

    @Override
    public void onReceive(final Context context, Intent intent) {
        /*Intent intent1  = new Intent(context,TimeSpeaker.class);
        context.startService(intent1);*/
         WorkManager  workManager = WorkManager.getInstance(context);
        OneTimeWorkRequest compressionWork =
                new OneTimeWorkRequest.Builder(ClockWork.class)

                        .build();
        workManager.enqueue(compressionWork);

        setTime(context);
    }

    private void setTime(Context context) {
       SharedPreferences sharedPreferences = context.getSharedPreferences("setting",0);

       if (!sharedPreferences.getBoolean("key",false)){
           return;
       }

        Intent intent = new Intent(context, ClockReceiver.class);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 111, intent, 0);

        Calendar calendar  = Calendar.getInstance();
        calendar.set(Calendar.SECOND,00);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR)+1);

        AlarmManager    alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        alarmManager.cancel(alarmIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmIntent);
        }


    }
}
