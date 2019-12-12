package com.iamrajendra.analogclock.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iamrajendra.analogclock.Application;
import com.iamrajendra.analogclock.R;
import com.iamrajendra.analogclock.viewpager.holder.ColorChangeListener;

public class SelectColorActivity extends AppCompatActivity implements ColorChangeListener, View.OnClickListener {
private RecyclerView recyclerView;
private ColorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_color);
        findViewById(R.id.close).setOnClickListener(this);
        recyclerView = findViewById(R.id.color_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ColorAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setColorChangeListener(this);

    }

    @Override
    public void onColorChange(int color) {
        ((Application)getApplication()).getSelectedColor().setValue(color);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.close){
            onBackPressed();
        }
    }
}
