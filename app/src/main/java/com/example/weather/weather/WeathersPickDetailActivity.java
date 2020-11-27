package com.example.weather.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.weather.MainActivity;
import com.example.weather.R;

public class WeathersPickDetailActivity extends AppCompatActivity {

    ImageButton imgbtnBackWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weathers_pick_detail);

        imgbtnBackWeather = findViewById(R.id.imgbtnBackWeather);
        imgbtnBackWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(WeathersPickDetailActivity.this, WeathersPickActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}