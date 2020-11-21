package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.weather.fragment.HomeFragment;
import com.example.weather.fragment.LankFragment;
import com.example.weather.fragment.MyPageFragment;
import com.example.weather.fragment.NoticeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    HomeFragment HomeFragment;
    LankFragment LankFragment;
    NoticeFragment NoticeFragment;
    MyPageFragment MyPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment = new HomeFragment();
        LankFragment = new LankFragment();
        NoticeFragment = new NoticeFragment();
        MyPageFragment = new MyPageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_home :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment).commit();

                        return true;
                    case R.id.tab_lank :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, LankFragment).commit();

                        return true;
                    case R.id.tab_notice :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, NoticeFragment).commit();

                        return true;
                    case R.id.tab_mypage :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, MyPageFragment).commit();

                        return true;
                }
                return false;
            }
        });
    }
}