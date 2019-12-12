package com.iamrajendra.analogclock.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iamrajendra.analogclock.Application;
import com.iamrajendra.analogclock.EditPeriodActivity;
import com.iamrajendra.analogclock.MainActivity;
import com.iamrajendra.analogclock.R;
import com.iamrajendra.analogclock.model.DayModel;
import com.iamrajendra.analogclock.model.PeriodTableV2;
import com.iamrajendra.analogclock.utlis.Date;

import java.util.ArrayList;
import java.util.List;

public class DayFragment extends Fragment implements OnItemClickListener<PeriodTableV2> {
    private String TAG = DayFragment.class.getSimpleName();
    private static DayFragment dayFragment;
    private PeriodAdapter periodAdapter;
    private RecyclerView recyclerView;
    private View emptyView;

    public static DayFragment getDayFragment(int position, DayModel dayModel) {
        dayFragment = new DayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putSerializable("data",dayModel);
        dayFragment.setArguments(bundle);
        return dayFragment;


    }



    private void createData() {
        final int week = ((DayModel)getArguments().getSerializable("data")).getDayId();
        ((Application) getActivity().getApplication()).getManager().getpMutableLiveData().observe(this, new Observer<List<PeriodTableV2>>() {
            @Override
            public void onChanged(List<PeriodTableV2> periodTableV2s) {
                List<PeriodTableV2> v2s = new ArrayList<>();
                for (PeriodTableV2 v2 : periodTableV2s) {

                    if (week == Date.dayOfWeek(v2.getStartDate())) {
                        v2s.add(v2);
                    }
                }
                updateDaa(v2s);

            }
        });
    }

    private void updateDaa(List<PeriodTableV2> v2s) {
        if (v2s.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            periodAdapter.update(v2s);
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.day_layout_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.plist);
        emptyView = getView().findViewById(R.id.empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        periodAdapter = new PeriodAdapter();
        recyclerView.setAdapter(periodAdapter);
        periodAdapter.setClickListener(this);
        periodAdapter.getSelectedEnable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                MainActivity mainActivity  = (MainActivity) getActivity();
                mainActivity.selectMenuSate(aBoolean);
            }
        });
        createData();
        MainActivity mainActivity  = (MainActivity) getActivity();
        mainActivity.getDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         Application application  = (Application) getActivity().getApplication();
         application.getManager().delete(periodAdapter.getSelectedList());
                MainActivity mainActivity  = (MainActivity) getActivity();
                mainActivity.selectMenuSate(false);

            }
        });
/*
        final int week = ((DayModel)getArguments().getSerializable("data")).getDayId();
        if (week == Date.today()) {
          int pos = getArguments().getInt("position");
          mainActivity.getViewPager().setCurrentItem(pos);
            Log.i(TAG, "onViewCreated: day pos"+pos);
        }
*/

    }


    @Override
    public void onClick(PeriodTableV2 tableV2, int pos) {
        Intent intent  = new Intent(getContext(), EditPeriodActivity.class);
        intent.putExtra("uid",tableV2.getUid());
        startActivity(intent);
    }
}
