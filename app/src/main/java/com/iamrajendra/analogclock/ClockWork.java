package com.iamrajendra.analogclock;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.Locale;

public class ClockWork extends Worker {
    private TextToSpeech textToSpeech;

    public ClockWork(@NonNull final Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    speak();
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @NonNull
    @Override
    public Result doWork() {



       return Result.success();
    }

    private Result speak() {
        Calendar calendar  = Calendar.getInstance();
        int hr = calendar.get(Calendar.HOUR);
        int min  = calendar.get(Calendar.MINUTE);
        Log.e("rajendra", "doWork: min "+min );

        int speechStatus = textToSpeech.speak("Time is "+hr+"O clock", TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
            return Result.failure();
        }
        return Result.success();
    }
}
