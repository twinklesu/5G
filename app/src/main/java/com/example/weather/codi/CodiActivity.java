package com.example.weather.codi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.weather.LoginActivity;
import com.example.weather.MainActivity;
import com.example.weather.R;

public class CodiActivity extends AppCompatActivity {

    Button btnBack, btnImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codi);

        btnBack = findViewById(R.id.btnBack);
        btnImg = findViewById(R.id.btnImg);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(CodiActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(CodiActivity.this, CodiDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}