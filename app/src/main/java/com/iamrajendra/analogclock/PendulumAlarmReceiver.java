package com.iamrajendra.analogclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.iamrajendra.analogclock.utlis.NotificationManager;

public class PendulumAlarmReceiver extends BroadcastReceiver {
    private String TAG = PendulumAlarmReceiver.class.getSimpleName();
    private NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: "+intent.getStringExtra("key"));
        notificationManager = new NotificationManager(context);
        notificationManager.createNotification(intent);
    }
}
