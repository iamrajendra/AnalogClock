package com.iamrajendra.analogclock.viewpager.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iamrajendra.analogclock.R;
import com.iamrajendra.analogclock.model.ColorModel;
import com.iamrajendra.analogclock.viewpager.ColorAdapter;

public class ColorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private View viewPrimary, viewSecondary, viewHeigher;
    private ImageView imageViewPrimary, imageViewSecondary, imageViewHigher;
    private ColorAdapter adapter;
    private int pos;
    private  int color;

    public ColorHolder(@NonNull View itemView) {
        super(itemView);
        viewPrimary = itemView.findViewById(R.id.primary);
        viewSecondary = itemView.findViewById(R.id.secondary);
        viewHeigher = itemView.findViewById(R.id.higher);
        imageViewPrimary = itemView.findViewById(R.id.primary_check);
        imageViewSecondary = itemView.findViewById(R.id.secondary_check);
        imageViewHigher = itemView.findViewById(R.id.higher_check);
        viewPrimary.setOnClickListener(this);
        viewSecondary.setOnClickListener(this);
        viewHeigher.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.primary:
                adapter.getColorModels().get(pos).setSelected(1);
           color=     adapter. getColorModels().get(pos).getPrimary();
                break;
            case R.id.secondary:
                adapter.getColorModels().get(pos).setSelected(2);
                color=     adapter. getColorModels().get(pos).getSecondary();

                break;
            case R.id.higher:
                adapter.getColorModels().get(pos).setSelected(3);
                color=     adapter. getColorModels().get(pos).getHeigher();

                break;
        }

        for (int i = 0; i < adapter.getColorModels().size(); i++) {

            if (i!=pos){
                adapter.getColorModels().get(i).setSelected(-1);
            }
        }
        if (adapter.getColorChangeListener()!=null){
            adapter.getColorChangeListener().onColorChange(color);
        }
        adapter.notifyDataSetChanged();


    }

    public void bind(ColorAdapter colorAdapter, int position) {
        adapter = colorAdapter;
        pos =position;
        viewPrimary.setBackgroundColor(itemView.getResources().getColor(colorAdapter.getColorModels().get(position).getPrimary()));
        viewSecondary.setBackgroundColor(itemView.getResources().getColor(colorAdapter.getColorModels().get(position).getSecondary()));
        viewHeigher.setBackgroundColor(itemView.getResources().getColor(colorAdapter.getColorModels().get(position).getHeigher()));
        imageViewPrimary.setVisibility(colorAdapter.getColorModels().get(position).getSelected() == 1 ? View.VISIBLE : View.GONE);
        imageViewSecondary.setVisibility(colorAdapter.getColorModels().get(position).getSelected() == 2 ? View.VISIBLE : View.GONE);
        imageViewHigher.setVisibility(colorAdapter.getColorModels().get(position).getSelected() == 3 ? View.VISIBLE : View.GONE);
    }
}
