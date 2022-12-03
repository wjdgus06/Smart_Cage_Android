package com.example.reptile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.reptile.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(viewBinding.containerFragment.getId(), new HomeFragment()).commitAllowingStateLoss();

        viewBinding.navBottom.setOnItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements NavigationBarView.OnItemSelectedListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    transaction.replace(viewBinding.containerFragment.getId(), new HomeFragment()).commitAllowingStateLoss();
                    break;
                case R.id.menu_recent:
                    transaction.replace(viewBinding.containerFragment.getId(), new ListFragment()).commitAllowingStateLoss();
                    break;
                case R.id.menu_search:
                    transaction.replace(viewBinding.containerFragment.getId(), new NotificationFragment()).commitAllowingStateLoss();
                    break;
                case R.id.menu_setting:
                    transaction.replace(viewBinding.containerFragment.getId(), new SettingFragment()).commitAllowingStateLoss();
                    break;

            }
            return true;
        }
    }
}