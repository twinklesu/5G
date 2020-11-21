package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.weather.R;

public class JoinActivity2 extends AppCompatActivity {

    EditText edtNickName, edtName, edtTel, edtCertification;
    CheckBox radiobtnWoman, radiobtnMan;
    Button btnCertification, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        edtNickName = findViewById(R.id.edtNickName);
        edtName = findViewById(R.id.edtName);
        edtTel = findViewById(R.id.edtTel);
        edtCertification = findViewById(R.id.edtCertification);

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(JoinActivity2.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}