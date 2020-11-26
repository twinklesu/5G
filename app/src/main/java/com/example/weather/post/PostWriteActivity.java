//글쓰기
package com.example.weather.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Map;

public class PostWriteActivity extends AppCompatActivity {

    String TAG = this.getClass().getSimpleName();
    ImageButton imgbtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);

        imgbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PostWriteActivity.this, PostActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        Button register = findViewById(R.id.btnOk);
        final EditText title = findViewById(R.id.txtTitle);
        final EditText context = findViewById(R.id.txtContent);

        Log.d(TAG, "onCreate");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click!");

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String user_id = sharedPreferences.getString("user_id", "null");

                Map<String, String> post_json = new HashMap<>();
                post_json.put("post_title", title.getText().toString());
                post_json.put("post_content", context.getText().toString());
                post_json.put("post_id", user_id); // 로그인 구현 완료 시 수정 필요 --> 수정 완료

                String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/post/";
                final JSONObject parameter = new JSONObject(post_json);

                RequestQueue queue = Volley.newRequestQueue(PostWriteActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        url,
                        parameter,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast toast = Toast.makeText(getApplicationContext(), "게시글이 등록 되었습니다.", Toast.LENGTH_LONG);
                                toast.show();

                                Log.d(TAG, "Post Success : " + parameter);

                                Intent intent = new Intent(PostWriteActivity.this, PostActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast toast = Toast.makeText(getApplicationContext(), "게시글이 정상적으로 등록되지 않았습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG);
                                toast.show();

                                error.printStackTrace();
                                Log.d(TAG, "Post Fail");
                            }
                        }
                );

                queue.add(jsonObjectRequest);
            }
        });
    }
}