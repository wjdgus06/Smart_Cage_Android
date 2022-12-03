package com.example.reptile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reptile.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    FragmentHomeBinding viewBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentHomeBinding.inflate(getLayoutInflater());

        viewBinding.btnMoveRoro.setOnClickListener(view -> {
            Intent intent = new Intent (getContext(), RoroActivity.class);
            startActivity(intent);
        });
        return viewBinding.getRoot();
    }
}