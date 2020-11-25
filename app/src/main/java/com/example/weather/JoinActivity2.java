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
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;

import org.json.JSONObject;

import java.util.HashMap;

public class JoinActivity2 extends AppCompatActivity {

    String TAG = JoinActivity2.class.getSimpleName();

    EditText edtNickName, edtName, edtTel, edtCertification;
    RadioButton radiobtnWoman, radiobtnMan;
    Button btnCertification, btnSignUp;
    int user_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        edtNickName = findViewById(R.id.edtNickName);
        edtName = findViewById(R.id.edtName);
        edtTel = findViewById(R.id.edtTel);
        edtCertification = findViewById(R.id.edtCertification);

        radiobtnMan = findViewById(R.id.radiobtnMan);
        radiobtnWoman = findViewById(R.id.radiobtnWoman);

        btnSignUp = findViewById(R.id.btnSignUp);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/join/";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String user_id = sharedPreferences.getString("join_id", "null");
        final String user_pw = sharedPreferences.getString("join_pw", "null");

        final HashMap<String, String> join_json = new HashMap<>();

        if(radiobtnMan.isSelected()) user_sex = 1;
        else if(radiobtnWoman.isSelected()) user_sex = 0;

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                join_json.put("user_id", user_id);
                join_json.put("user_password", user_pw);
                join_json.put("user_name", edtName.getText().toString());
                join_json.put("user_sex", String.valueOf(user_sex));
                join_json.put("user_tel", edtTel.getText().toString());

                JSONObject parameter = new JSONObject(join_json);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        url,
                        parameter,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast toast = Toast.makeText(getApplicationContext(), "회원가입이 완료 되었습니다.\n 환영합니다 " + user_id + "님!", Toast.LENGTH_LONG);
                                toast.show();

                                Intent i= new Intent(JoinActivity2.this, LoginActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Log.d(TAG,"Join FAIL");
                            }
                        }
                );

                queue.add(jsonObjectRequest);
            }
        });
    }
}