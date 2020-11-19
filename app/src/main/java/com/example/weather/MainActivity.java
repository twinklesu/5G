package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        //연동
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.notification);
        images.add(R.drawable.home);
        images.add(R.drawable.user);

        for (int i=0; i<3; i++) tab.getTabAt(i).setIcon(images.get(i));
    }
}