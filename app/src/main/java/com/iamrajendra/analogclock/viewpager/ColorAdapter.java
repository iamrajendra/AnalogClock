package com.iamrajendra.analogclock.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iamrajendra.analogclock.R;
import com.iamrajendra.analogclock.model.ColorModel;
import com.iamrajendra.analogclock.viewpager.holder.ColorChangeListener;
import com.iamrajendra.analogclock.viewpager.holder.ColorHolder;

import java.util.Arrays;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ColorModel> colorModels;
    private ColorChangeListener colorChangeListener;

    public void setColorChangeListener(ColorChangeListener colorChangeListener) {
        this.colorChangeListener = colorChangeListener;
    }

    public ColorChangeListener getColorChangeListener() {
        return colorChangeListener;
    }

    public List<ColorModel> getColorModels() {
        return colorModels;
    }

    public ColorAdapter() {
        colorModels = Arrays.asList(
                new ColorModel(1, R.color.md_amber_400, R.color.md_amber_600, R.color.md_amber_800),
                new ColorModel(1, R.color.md_green_400, R.color.md_green_600, R.color.md_green_800),
                new ColorModel(1, R.color.md_deep_orange_400, R.color.md_deep_orange_600, R.color.md_deep_orange_800),
                new ColorModel(1, R.color.md_blue_400, R.color.md_blue_600, R.color.md_blue_800),
                new ColorModel(1, R.color.md_yellow_400, R.color.md_yellow_600, R.color.md_yellow_800)
                ,
                new ColorModel(1, R.color.md_cyan_400, R.color.md_cyan_600, R.color.md_cyan_800),
                new ColorModel(1, R.color.md_green_400, R.color.md_green_600, R.color.md_green_800),
                new ColorModel(1, R.color.md_deep_purple_400, R.color.md_deep_purple_600, R.color.md_deep_purple_800),
                new ColorModel(1, R.color.md_light_blue_400, R.color.md_light_blue_600, R.color.md_light_blue_800),
                new ColorModel(1, R.color.md_lime_A400, R.color.md_lime_600, R.color.md_lime_800)


        );
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_color_item, parent, false);
        return new ColorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ColorHolder colorHolder = (ColorHolder) holder;
        colorHolder.bind(this,position);

    }

    @Override
    public int getItemCount() {
        return colorModels.size();
    }
}
