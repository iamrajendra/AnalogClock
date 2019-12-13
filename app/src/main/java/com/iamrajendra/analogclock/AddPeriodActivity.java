package com.iamrajendra.analogclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;

import com.design.gambler.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.iamrajendra.analogclock.model.PeriodTableV2;
import com.iamrajendra.analogclock.utlis.Date;
import com.iamrajendra.analogclock.utlis.TimeChooserDialog;
import com.iamrajendra.analogclock.viewpager.SelectColorActivity;

import java.util.Calendar;

public class AddPeriodActivity extends AppCompatActivity implements View.OnClickListener {
    private PeriodTableV2 periodTableV2 = new PeriodTableV2();
    Calendar cal;

    // TODO: 12-12-2019  time selection is not correctly working
    View.OnClickListener timeDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()) {

                case R.id.start_time:

                    TimeChooserDialog timePickerDialog = new TimeChooserDialog(AddPeriodActivity.this, getResources().getString(R.string.start_time), periodTableV2.getStartDate()) {
                        @Override
                        public void onTimeSelected(long time) {
                            periodTableV2.setStartDate(time);
                            setTime(periodTableV2);
                        }
                    };

                    timePickerDialog.show();

                    break;
                case R.id.end_time:
                    TimeChooserDialog endTime = new TimeChooserDialog(AddPeriodActivity.this, getResources().getString(R.string.end_time), periodTableV2.getEndDate()) {
                        @Override
                        public void onTimeSelected(long time) {
                            periodTableV2.setEndDate(time);
                            setTime(periodTableV2);
                        }
                    };

                    endTime.show();


                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application application = (Application) getApplication();

        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, getIntent().getIntExtra("day", 0));
        cal.setTimeInMillis(application.getManager().endTime);
        cal.set(Calendar.DAY_OF_WEEK, getIntent().getIntExtra("day", 0));
        periodTableV2.setEndDate(cal.getTimeInMillis());
        cal.setTimeInMillis(application.getManager().starTime);
        cal.set(Calendar.DAY_OF_WEEK, getIntent().getIntExtra("day", 0));
        periodTableV2.setStartDate(cal.getTimeInMillis());
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


        setTime(periodTableV2);


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

                ((Application) getApplication()).getManager().insert(periodTableV2, new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        new Message(getApplicationContext()).success("Save is saved successfully").show();
                        finish();
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        new Message(getApplicationContext()).error("Something went wrong").show();
                        finish();

                    }
                });
            }
        }

        if (v.getId()==R.id.close){
            onBackPressed();
        }
    }


    public void setTime(PeriodTableV2 time) {
        ((TextView) findViewById(R.id.startTime_tv)).setText(Date.getTime(Date.TIME, time.getStartDate()));
        ((TextView) findViewById(R.id.endTime_tv)).setText(Date.getTime(Date.TIME, time.getEndDate()));
    }


}
