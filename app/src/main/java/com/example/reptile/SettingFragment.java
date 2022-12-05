package com.example.reptile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reptile.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    FragmentSettingBinding viewBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentSettingBinding.inflate(getLayoutInflater());

        viewBinding.btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        return viewBinding.getRoot();
    }
}