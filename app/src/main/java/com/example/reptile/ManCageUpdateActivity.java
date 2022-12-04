package com.example.reptile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.reptile.databinding.ActivityManCageUpdateBinding;

public class ManCageUpdateActivity extends AppCompatActivity {

    ActivityManCageUpdateBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_man_cage_update);


        Intent intent = getIntent();
        String myData = intent.getStringExtra("group_name");


        SharedPreferences preferences = getSharedPreferences("group", MODE_PRIVATE);
        String grp_name = preferences.getString("grp_name","NO_GROUP");
        // 그룹 새로 등록하기 전까지 처음 등록한 group이름 유지

        System.out.println(grp_name);

        viewBinding = ActivityManCageUpdateBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        String[] items = getResources().getStringArray(R.array.spinner_sort);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ManCageUpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, items);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        viewBinding.spinnerSort.setAdapter(dataAdapter);

        viewBinding.spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //viewBinding.testSpinner.setText(items[position]);
                //Toast.makeText(CageUpdateActivity.this, "Item is selected.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //viewBinding.testSpinner.setText(items[0]);
            }

        });
    }
}