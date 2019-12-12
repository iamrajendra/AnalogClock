package com.iamrajendra.analogclock.viewpager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.iamrajendra.analogclock.R;
import com.iamrajendra.analogclock.model.PeriodTableV2;
import com.iamrajendra.analogclock.viewpager.holder.PeriodHolder;

import java.util.ArrayList;
import java.util.List;

public class PeriodAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PeriodTableV2> periodModels;
    private MutableLiveData<Boolean> selectedEnable =  new MutableLiveData<>();
   private static  List<PeriodTableV2> selectedList = new ArrayList<>();

    public MutableLiveData<Boolean> getSelectedEnable() {
        return selectedEnable;
    }



    public List<PeriodTableV2> getSelectedList() {
       return selectedList;
    }


    public List<PeriodTableV2> getPeriodModels() {
        return periodModels;
    }


    private  OnItemClickListener<PeriodTableV2> clickListener;

    public void setClickListener(OnItemClickListener<PeriodTableV2> clickListener) {
        this.clickListener = clickListener;
    }

    public OnItemClickListener<PeriodTableV2> getClickListener() {
        return clickListener;
    }

    public PeriodAdapter() {
        periodModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PeriodHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PeriodHolder)holder).bind(this,position);


    }

    @Override
    public int getItemCount() {
        return periodModels.size();
    }

    public void update(List<PeriodTableV2> v2s) {
        periodModels.clear();
        periodModels.addAll(v2s);
        notifyDataSetChanged();
    }
}
