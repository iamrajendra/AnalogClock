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
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.iamrajendra.analogclock.firebase.DatabaseManager;
import java.util.Calendar;
import java.util.Set;

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
        private  final String TAG = String.class.getSimpleName();
        private DatabaseManager manager;
        SharedPreferences sharedPreferences ;


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            manager = Application.getApplication().getManager();
            sharedPreferences= getActivity().getSharedPreferences("setting", 0);
            SwitchPreferenceCompat preferenceCompat = findPreference("clock");
            preferenceCompat.setOnPreferenceClickListener(this);

            SwitchPreferenceCompat test = findPreference("noti");
            test.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                    boolean flag = ((SwitchPreferenceCompat) preference).getSharedPreferences().getBoolean("clock", false);

                    editor.putBoolean("noti",flag);
                    editor.commit();
                    if (flag)
                    manager.initAlarm();
                    return false;
                }
            });

        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            Log.i(TAG, "onPreferenceClick: " + ((SwitchPreferenceCompat) preference).getSharedPreferences().getBoolean("clock", false));
            boolean flag = ((SwitchPreferenceCompat) preference).getSharedPreferences().getBoolean("clock", false);
            controlSpeakingClock(flag);
            return false;
        }

        private void controlSpeakingClock(boolean flag) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("key", flag);
            editor.commit();

            if (!flag) return;
            Intent intent = new Intent(getContext(), ClockReceiver.class);

            PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 111, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 00);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 1);

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