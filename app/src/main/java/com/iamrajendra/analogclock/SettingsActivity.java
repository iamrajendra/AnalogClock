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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;


import com.iamrajendra.analogclock.utlis.NotificationManager;
import com.iamrajendra.analogclock.utlis.PendulumAlarmManager;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        findViewById(R.id.close).setOnClickListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close) {
            onBackPressed();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
        private static final String TAG = String.class.getSimpleName();

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            SwitchPreferenceCompat preferenceCompat = findPreference("clock");
            preferenceCompat.setOnPreferenceClickListener(this);

            SwitchPreferenceCompat test = findPreference("test");
            test.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {


                    return false;
                }
            });

        }
//eb749245515in speed post

        @Override
        public boolean onPreferenceClick(Preference preference) {
            Log.i(TAG, "onPreferenceClick: " + ((SwitchPreferenceCompat) preference).getSharedPreferences().getBoolean("clock", false));
            boolean flag = ((SwitchPreferenceCompat) preference).getSharedPreferences().getBoolean("clock", false);
            controlSpeakingClock(flag);
            return false;
        }

        private void controlSpeakingClock(boolean flag) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("setting", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("key",flag);
            editor.commit();

            if (!flag) return;
            Intent intent = new Intent(getContext(), ClockReceiver.class);

            PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 111, intent, 0);

            Calendar calendar  = Calendar.getInstance();
            calendar.set(Calendar.SECOND,00);
            calendar.set(Calendar.MINUTE,00);
            calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR)+1);

            AlarmManager

                    alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);


            alarmManager.cancel(alarmIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmIntent);
            }

        }
    }
}