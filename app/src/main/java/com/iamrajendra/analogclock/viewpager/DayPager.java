package com.iamrajendra.analogclock.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.iamrajendra.analogclock.model.DayModel;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DayPager extends FragmentPagerAdapter {
    private List<DayModel> dayModels;

    public List<DayModel> getDayModels() {
        return dayModels;
    }

    public DayPager(@NonNull FragmentManager fm) {
        super(fm);
        dayModels = Arrays.asList(

                new DayModel(1, "Monday", Calendar.MONDAY),
                new DayModel(2, "Tuesday",Calendar.TUESDAY),
                new DayModel(3, "Wednesday",Calendar.WEDNESDAY),
                new DayModel(4, "Thursday",Calendar.THURSDAY),
                new DayModel(5, "Friday",Calendar.FRIDAY),
                new DayModel(6, "Saturday",Calendar.SATURDAY),
                new DayModel(7, "Sunday",Calendar.SUNDAY)
        );
    }

    public DayPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return DayFragment.getDayFragment(position,dayModels.get(position));
    }

    @Override
    public int getCount() {
        return dayModels.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return dayModels.get(position).getDay();
    }

public DayModel getItemByPosition(int position){
    return  dayModels.get(position);
}
}
