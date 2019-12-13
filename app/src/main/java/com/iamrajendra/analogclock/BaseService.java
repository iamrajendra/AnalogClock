package com.iamrajendra.analogclock;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class BaseService extends IntentService {
    private String caption="";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BaseService(String name) {
        super(name);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        Notification notification=   createNotifiaction();
        startForeground(2, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification createNotifiaction() {
        String NOTIFICATION_CHANNEL_ID = createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_clock_24dp)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               ;
        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification updateMessage(String updateMessage) {
        String NOTIFICATION_CHANNEL_ID = createNotificationChannel();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(updateMessage)
                .setSmallIcon(R.drawable.ic_clock_24dp)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        return notification;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);

        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        return NOTIFICATION_CHANNEL_ID;
    }



   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
//        startTimer();
        return START_STICKY;
    }*/

    public static void  startService(Activity activity,Class t){
        if (!isRunning(activity,t)) {
            Intent intent = new Intent(activity, t);
            activity.startService(intent);
        }

    }


    private static boolean isRunning(Activity activity, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }
}
