package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weather.R;

public class JoinActivity1 extends AppCompatActivity {

    private Button btnNext, btnSignIn, btnForgotPw;
    private EditText edtId, edtPw, edtPwChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join1);

        edtId = findViewById(R.id.edtId);
        edtPw = findViewById(R.id.edtPw);

        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(JoinActivity1.this, JoinActivity2.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}