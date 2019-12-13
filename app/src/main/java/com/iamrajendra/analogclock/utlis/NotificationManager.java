package com.iamrajendra.analogclock.utlis;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.iamrajendra.analogclock.R;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationManager {
    private Context context;
    private static final String CHANNEL_ID_DEFAULT = "default";
    public NotificationManager(Context context) {
        this.context = context;
    }




    public void showProgressNotification(String caption) {


    }


    private void createDefaultChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationManager nm = (android.app.NotificationManager)context. getSystemService(NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_DEFAULT,
                    "Default",
                    android.app.NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
        }
    }

    public void createNotification(String title,String content) {
        createDefaultChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_DEFAULT)
                .setSmallIcon(R.drawable.ic_clock_24dp)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        android.app.NotificationManager manager =
                (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(332, builder.build());
    }
}
