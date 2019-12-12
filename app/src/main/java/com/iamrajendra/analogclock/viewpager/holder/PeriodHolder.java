package com.iamrajendra.analogclock.viewpager.holder;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.iamrajendra.analogclock.R;
import com.iamrajendra.analogclock.animation.CardViewAnimation;
import com.iamrajendra.analogclock.model.PeriodTableV2;
import com.iamrajendra.analogclock.utlis.Date;
import com.iamrajendra.analogclock.viewpager.PeriodAdapter;

public class PeriodHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private TextView textViewShorDate, textViewLongDate, textViewTitle, textViewDescription;
    private Button button;
    private Group group;
    private PeriodTableV2 periodModel;
    private int pos;
    private PeriodAdapter adapter;
    private ImageView imageViewCheck;

    public PeriodHolder(@NonNull View itemView) {
        super(itemView);
        textViewShorDate = itemView.findViewById(R.id.short_date);
        textViewLongDate = itemView.findViewById(R.id.range_date);
        textViewDescription = itemView.findViewById(R.id.des);
        textViewTitle = itemView.findViewById(R.id.title);
        button = itemView.findViewById(R.id.button);
        group = itemView.findViewById(R.id.group);
        imageViewCheck = itemView.findViewById(R.id.check);
        button.setOnClickListener(this);
    }


    public void bind(PeriodAdapter periodAdapter, final int position) {
        pos = position;
        adapter = periodAdapter;
        periodModel = periodAdapter.getPeriodModels().get(position);
        textViewDescription.setText(periodModel.getDescription());
        textViewShorDate.setText(Date.getTime(Date.TIME, periodModel.getStartDate()));
        textViewLongDate.setText(Date.getTime(Date.TIME, periodModel.getStartDate()) + " - " + Date.getTime(Date.TIME, periodModel.getEndDate()));
        textViewTitle.setText(periodModel.getTitle());
        imageViewCheck.setVisibility(periodModel.isSelected() ? View.VISIBLE : View.GONE);
        itemView.setBackgroundColor(itemView.getResources().getColor(periodModel.getColor()));
        setVisisbility(periodModel.isVisible());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelectedList().isEmpty()) {

                    for (int i = 0; i < adapter.getPeriodModels().size(); i++) {
                        if (position == i) {
                            adapter.getPeriodModels().get(i).setVisible(!adapter.getPeriodModels().get(i).isVisible());
                        } else {
                            adapter.getPeriodModels().get(i).setVisible(false);
                        }
                    }
                    adapter.notifyItemChanged(position);
                } else {
                    updateSelected();
                }
            }
        });

        itemView.setOnLongClickListener(this);
    }

    private void setVisisbility(boolean visisbility) {
// TODO: 12-12-2019  height of the item when it expand and colapse
        if (visisbility) {
            CardViewAnimation.expandView(itemView, group, measureHeight(periodModel.getDescription()));
        } else {
            CardViewAnimation.collapseView(itemView, group, 150);
        }
    }

    private int measureHeight(String description) {
        if (description==null |description.isEmpty())
        return 450;
       else  return  450 + description.length();
    }


    @Override
    public void onClick(View v) {
        if (adapter.getClickListener() != null) {
            adapter.getClickListener().onClick(periodModel, pos);
        }

    }

    @Override
    public boolean onLongClick(View v) {
        updateSelected();
        return false;
    }

    private void updateSelected() {
        adapter.getPeriodModels().get(pos).setSelected(!adapter.getPeriodModels().get(pos).isSelected());
        adapter.notifyItemChanged(pos);
        adapter.getSelectedList().clear();
        for (int i = 0; i < adapter.getPeriodModels().size(); i++) {
            if (adapter.getPeriodModels().get(i).isSelected()) {
                adapter.getSelectedList().add(adapter.getPeriodModels().get(i));
            } else {
                adapter.getSelectedList().remove(adapter.getPeriodModels().get(i));
            }
        }
        if (!adapter.getSelectedList().isEmpty()) {
            adapter.getSelectedEnable().setValue(true);
        } else {
            adapter.getSelectedEnable().setValue(false);
        }
    }
}
