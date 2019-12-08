package com.iamrajendra.analogclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeSpeaker extends BaseService {

    private TextToSpeech textToSpeech;


    private    Calendar   mTime = Calendar.getInstance();


    public TimeSpeaker() {
        super(TimeSpeaker.class.getSimpleName());
    }

    private void speakTimer(int currentHr) {
        int speechStatus = textToSpeech.speak("Time is "+currentHr+"O clock", TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

       textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                    speakTimer(mTime.get(Calendar.HOUR));
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateMessage("I am a speaking clock");

    }





    @Override
    public void onDestroy() {
        super.onDestroy();
       /* unregisterReceiver(mIntentReceiver);*/
    }
}
