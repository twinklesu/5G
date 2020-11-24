package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity1 extends AppCompatActivity {

    private Button btnNext, btnSignIn, btnForgotPw;
    private EditText edtId, edtPw, edtPwChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join1);

        edtId = findViewById(R.id.edtId);
        edtPw = findViewById(R.id.edtPw);
        edtPwChk = findViewById(R.id.edtPwChk);

        btnNext = findViewById(R.id.btnNext);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final RequestQueue queue = Volley.newRequestQueue(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/validate-id/" + edtId.getText().toString();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                    Boolean result = response.optBoolean("message");

                                    if (result) {
                                        if (edtPw.getText().toString().equals(edtPwChk.getText().toString())) {
                                            editor.putString("join_id", edtId.getText().toString());
                                            editor.putString("join_pw", edtPw.getText().toString());
                                            editor.apply();

                                            Intent i= new Intent(JoinActivity1.this, JoinActivity2.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                        }
                                        else {
                                            Toast toast = Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다. 다시 한번 확인하세요.", Toast.LENGTH_LONG);
                                            toast.show();
                                            edtPwChk.setText("");
                                        }
                                    }
                                    else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "ID가 중복입니다. 다른 ID를 입력하세요.", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                queue.add(jsonObjectRequest);
            }
        });
    }
}