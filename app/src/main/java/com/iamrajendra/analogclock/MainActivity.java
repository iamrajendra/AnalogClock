package com.iamrajendra.analogclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.iamrajendra.analogclock.model.DayModel;
import com.iamrajendra.analogclock.utlis.Date;
import com.iamrajendra.analogclock.viewpager.DayPager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private ViewPager viewPager;
    Intent intent  = new Intent();
    private View viewSelectedMenu;
    private View viewMainMenu;
    private ImageButton delete;
    private  DayPager dayPager;

    public ImageButton getDelete() {
        return delete;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMainMenu = findViewById(R.id.toolbar);
        viewSelectedMenu = findViewById(R.id.selected_toolbar);
        viewPager = findViewById(R.id.viewpager);
        dayPager = new DayPager(getSupportFragmentManager());
        viewPager.setAdapter(dayPager);
        viewPager.setCurrentItem(Date.today()-2);
        // TODO: 12-12-2019 we have to complete the spinner thing
        AppCompatSpinner appCompatSpinner = findViewById(R.id.weekOfMonth);
        appCompatSpinner.setSelection(Date.weekOfMonth());
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);
        delete = findViewById(R.id.delete);
        findViewById(R.id.add_period).setOnClickListener(this);
        findViewById(R.id.settings).setOnClickListener(this);


           }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.settings:
                intent.setClass(this,SettingsActivity.class);
                break;
            case R.id.add_period:
                intent.setClass(this,AddPeriodActivity.class);
                intent.putExtra("day",dayPager.getItemByPosition(viewPager.getCurrentItem()).getDayId());
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        if (viewPager!=null) viewPager.setCurrentItem(Date.today());
    }

    public void selectMenuSate(Boolean aBoolean) {
        viewMainMenu.setVisibility(aBoolean?View.GONE:View.VISIBLE);
        viewSelectedMenu.setVisibility(aBoolean?View.VISIBLE:View.GONE);
    }
}

