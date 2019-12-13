package com.iamrajendra.analogclock.utlis;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.TimePicker;

import com.iamrajendra.analogclock.AddPeriodActivity;
import com.iamrajendra.analogclock.R;

import java.util.Calendar;

public abstract  class TimeChooserDialog {
    private final TimePickerDialog startTimePicker;
    private final Calendar cal;
    private String title;


    public TimeChooserDialog(Activity context, String title, long time) {
        this.title = title;
         cal = Calendar.getInstance();
        cal.setTimeInMillis(time);


         startTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                onTimeSelected( generateTime(selectedHour,selectedMinute));
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);//Yes 24 hour time
        startTimePicker.setTitle(title);

    }

    private long generateTime(int selectedHour, int selectedMinute) {
        cal.set(Calendar.HOUR_OF_DAY,selectedHour);
        cal.set(Calendar.MINUTE,selectedMinute);
        return cal.getTimeInMillis();
    }

    public  void show(){
        startTimePicker.show();
    }

  public abstract  void   onTimeSelected(long time);
}
