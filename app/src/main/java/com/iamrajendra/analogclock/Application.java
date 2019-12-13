package com.iamrajendra.analogclock;

import android.provider.Settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.iamrajendra.analogclock.firebase.DatabaseManager;

public class Application extends MultiDexApplication {
    private MutableLiveData<Integer> selectedColor =  new MutableLiveData<>();
    private String androidId;
    private DatabaseManager  manager;
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public DatabaseManager getManager() {
        return manager;
    }

    public String getAndroidId() {
        return androidId;
    }

    public MutableLiveData<Integer> getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(MutableLiveData<Integer> selectedColor) {
        this.selectedColor = selectedColor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application =this;
         androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID)+"com.iamrajendra.analogclock";
        MultiDex.install(this);
        manager  = new DatabaseManager(getAndroidId(),getApplicationContext());
    }




}
