package com.iamrajendra.analogclock;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;

import com.design.gambler.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.iamrajendra.analogclock.model.PeriodTableV2;
import com.iamrajendra.analogclock.utlis.Date;
import com.iamrajendra.analogclock.viewpager.SelectColorActivity;

import java.util.Calendar;

public class EditPeriodActivity  extends AppCompatActivity implements View.OnClickListener {

    private PeriodTableV2 periodTableV2 = new PeriodTableV2();
    final Calendar cal = Calendar.getInstance();
    View.OnClickListener timeDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final int hour = cal.get(Calendar.HOUR_OF_DAY);
            final int minute = cal.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;

            switch (v.getId()) {

                case R.id.start_time:

                    mTimePicker = new TimePickerDialog(EditPeriodActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                            cal.set(Calendar.MINUTE, selectedMinute);
                            periodTableV2.setStartDate(cal.getTime().getTime());
                            ((TextView)findViewById(R.id.startTime_tv)).setText(Date.getTime(Date.TIME,cal.getTime()));
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle(getResources().getString(R.string.start_time));
                    mTimePicker.show();
                    break;
                case R.id.end_time:
                    mTimePicker = new TimePickerDialog(EditPeriodActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                            cal.set(Calendar.MINUTE, selectedMinute);
                            periodTableV2.setEndDate(cal.getTimeInMillis());
                            ((TextView)findViewById(R.id.endTime_tv)).setText(Date.getTime(Date.TIME,cal.getTime()));

                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle(getResources().getString(R.string.end_time));
                    mTimePicker.show();

                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cal.set(Calendar.WEEK_OF_MONTH,getIntent().getIntExtra("day",0));
        setContentView(R.layout.activity_add_period);
        findViewById(R.id.start_time).setOnClickListener(timeDialog);
        findViewById(R.id.end_time).setOnClickListener(timeDialog);
        findViewById(R.id.select_color).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
        ((Application) getApplication()).getSelectedColor().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer color) {
                ((CardView) findViewById(R.id.select_color)).setCardBackgroundColor(getResources().getColor(color));
                periodTableV2.setColor(color);
            }
        });
        periodTableV2.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new Message(getApplicationContext()).error(s).show();
            }
        });

        updateData();
    }

    private void updateData() {
String uid  = getIntent().getStringExtra("uid");
        ((Application)getApplication()).getManager().populateSingleValue(uid, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                periodTableV2 = task.getResult().toObject(PeriodTableV2.class);
                periodTableV2.setUid(task.getResult().getId());
                updateUI(periodTableV2);
            }
        });

    }

    private void updateUI(PeriodTableV2 periodTableV2) {
        ((EditText)findViewById(R.id.title)).setText(periodTableV2.getTitle());
        ((EditText)findViewById(R.id.des)).setText(periodTableV2.getDescription());
        ((TextView)findViewById(R.id.startTime_tv)).setText(Date.getTime(Date.TIME,periodTableV2.getStartDate()));
        ((TextView)findViewById(R.id.endTime_tv)).setText(Date.getTime(Date.TIME,periodTableV2.getEndDate()));
        ((CardView)findViewById(R.id.select_color)).setCardBackgroundColor(getResources().getColor(periodTableV2.getColor()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.select_color) {
            Intent intent = new Intent(this, SelectColorActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.save) {
            String title = ((EditText) findViewById(R.id.title)).getText().toString();
            String des = ((EditText) findViewById(R.id.des)).getText().toString();
            periodTableV2.setTitle(title);
            periodTableV2.setDescription(des);
            if (periodTableV2.isValid()) {

                ((Application) getApplication()).getManager().update(periodTableV2, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        new Message(getApplicationContext()).success("Information is updated successfully").show();
                        finish();
                    }
                });
            }
        }

        if (v.getId()==R.id.close){
            onBackPressed();
        }
    }



}
