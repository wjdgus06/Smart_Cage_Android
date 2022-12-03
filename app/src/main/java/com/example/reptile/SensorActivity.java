package com.example.reptile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.reptile.databinding.ActivityRoroSensorBinding;

public class SensorActivity extends AppCompatActivity {

    ActivityRoroSensorBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityRoroSensorBinding.inflate(getLayoutInflater());

        String repName = getIntent().getStringExtra("cageName");
        viewBinding.pageTitle.setText(repName+"'s 센서");

        String[] items = {"온도/습도/조도", "온도", "습도", "조도"};

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        viewBinding.spinnerSensor.setAdapter(dataAdapter);

        viewBinding.spinnerSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // viewBinding.spinnerTest.setText(items[position]);
                // 통계 이미지 만들어서 변경하도록 해야함
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //viewBinding.spinnerTest.setText(items[0]);
            }

        });

        viewBinding.btnBack.setOnClickListener(view -> {
            finish(); // CageActivity 를 finish() 하지 않았기 때문에 이전 화면으로 돌아감
        });

        setContentView(viewBinding.getRoot());
    }
}