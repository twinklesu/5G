package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.prefs.PreferenceChangeEvent;

public class LoginActivity extends AppCompatActivity {

    String TAG = LoginActivity.class.getSimpleName();

    private Button btnLogin, btnSignUp, btnForgot, btnNaver, btnFacebook, btnKakao;
    private EditText edtLoginId, edtLoginPw;
    private CheckBox chkBoxAutoLogin;
    private EditText loginId, loginPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginId = findViewById(R.id.edtLoginId);
        edtLoginPw = findViewById(R.id.edtLoginPw);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        loginId = findViewById(R.id.edtLoginId);
        loginPw = findViewById(R.id.edtLoginPw);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/get-pw-by-id/" + loginId.getText();

                Log.d(TAG, "lgoin url : " +  url);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject result = response.getJSONObject(0);
                                    String userPw = result.getString("user_password");

                                    if (loginPw.getText().toString().equals(userPw)) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "환영합니다 " + loginId.getText().toString() + "님!", Toast.LENGTH_LONG);
                                        toast.show();

                                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user_id", loginId.getText().toString());
                                        editor.apply();

                                        Intent i= new Intent(LoginActivity.this, MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    }
                                    else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요.", Toast.LENGTH_LONG);
                                        toast.show();

                                        loginPw.setText("");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast toast = Toast.makeText(getApplicationContext(), "아이디를 다시 확인해주세요.", Toast.LENGTH_LONG);
                                toast.show();

                                loginId.setText("");

                                error.printStackTrace();
                                Log.d(TAG, "Login Error");
                            }
                        }
                );

                queue.add(jsonArrayRequest);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LoginActivity.this, JoinActivity1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}