package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.fragment.HomeFragment;
import com.example.weather.post.PostActivity;
import com.example.weather.post.PostWriteActivity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;

public class VoteActivity extends Activity {

    String TAG = VoteActivity.class.getSimpleName();

    //TextView txtText;
    CheckBox fogeun, fewCold, cold, dry, sunny, veryWindy, veryColdAlmostDie, bigDegreeDiff;
    CheckBox mustang,trench, padding, hoodie, jacket, coat, neat, longPadding;
    String user_id, weather, clothe;
    ImageButton imgbtnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vote);

        //UI 객체생성
        //txtText = (TextView)findViewById(R.id.txtText);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        //txtText.setText(data);

        imgbtnCancle = findViewById(R.id.imgbtnCancle);
        imgbtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(VoteActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        fogeun = findViewById(R.id.chkbox1);
        fewCold = findViewById(R.id.chkbox3);
        cold = findViewById(R.id.chkbox5);
        dry = findViewById(R.id.chkbox7);

        sunny = findViewById(R.id.chkbox2);
        veryWindy = findViewById(R.id.chkbox4);
        veryColdAlmostDie = findViewById(R.id.chkbox6);
        bigDegreeDiff = findViewById(R.id.chkbox8);

        mustang = findViewById(R.id.chkbox9);
        trench = findViewById(R.id.chkbox11);
        padding = findViewById(R.id.chkbox13);
        hoodie = findViewById(R.id.chkbox15);

        jacket = findViewById(R.id.chkbox10);
        coat = findViewById(R.id.chkbox12);
        neat = findViewById(R.id.chkbox14);
        longPadding = findViewById(R.id.chkbox16);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_id = sharedPreferences.getString("user_id", null);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/weather-survey/";

        HashMap<String, String> weather_json = new HashMap<>();
        weather_json.put("user_id", user_id);

        CheckBox weathers[] = { fogeun, fewCold, cold, dry, sunny, veryWindy, veryColdAlmostDie, bigDegreeDiff };
        CheckBox clothes[] = { mustang,trench, padding, hoodie, jacket, coat, neat, longPadding };

        for(CheckBox w : weathers) {
            if (w.isChecked()) weather = w.getText().toString();
        }

        weather_json.put("weather", weather);

        JSONObject parameter = new JSONObject(weather_json);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast toast = Toast.makeText(VoteActivity.this, "설문이 완료 되었습니다.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Vote is fail");
                    }
                }
        );

        HashMap<String, String> clothes_json = new HashMap<>();
        clothes_json.put("user_id", user_id);

        for(CheckBox c : clothes) if(c.isChecked()) clothe = c.getText().toString();

        clothes_json.put("fashion", clothe);

        JSONObject parameter_clothe = new JSONObject(clothes_json);

        String fashion_url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/fashion-survey/";

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST,
                fashion_url,
                parameter_clothe,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(VoteActivity.this, "설문 응답 중 오류가 발생했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG);
                        toast.show();

                        error.printStackTrace();
                    }
                });
        queue.add(jsonObjectRequest);
        queue.add(jsonObjectRequest1);

        //액티비티(팝업) 닫기
        finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}