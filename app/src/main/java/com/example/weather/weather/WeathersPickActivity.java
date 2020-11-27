package com.example.weather.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.weather.MainActivity;
import com.example.weather.R;
import com.example.weather.codi.CodiActivity;

public class WeathersPickActivity extends AppCompatActivity {

    ImageButton imgbtnBackWrite, imgbtnPick, imgbtnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weathers_pick);

        imgbtnBackWrite = findViewById(R.id.imgbtnBackWrite);
        imgbtnPick = findViewById(R.id.imgbtnPick);
        imgbtnGo = findViewById(R.id.imgbtnGo);

        imgbtnBackWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(WeathersPickActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        imgbtnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(WeathersPickActivity.this, WeathersPickDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        imgbtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(WeathersPickActivity.this, WeathersPickDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}