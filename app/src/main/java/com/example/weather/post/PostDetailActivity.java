//글쓰기 댓글 보이는 곳
package com.example.weather.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PostDetailActivity extends AppCompatActivity {

    TextView Id, Title, Content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        String title = intent.getStringExtra("Title");
        String content = intent.getStringExtra("Content");
        final String post_no = intent.getStringExtra("PostNo");
        Id = findViewById(R.id.txtNickName);
        Title = findViewById(R.id.txtPostDetailTitle);
        Content = findViewById(R.id.txtContent);

        final EditText comment = findViewById(R.id.edtComment);
        ImageButton comment_btn = findViewById(R.id.btnComment);

        Id.setText(id);
        Title.setText(title);
        Content.setText(content);

        final String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/post-comment/";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_id = sharedPreferences.getString("user_id", "null");

        HashMap<String, Integer> postno_json = new HashMap<>();
        postno_json.put("post_no", Integer.parseInt(post_no));
        final HashMap<String, String> comment_json = new HashMap<>();
        comment_json.put("user_id",user_id);

        final RequestQueue queue = Volley.newRequestQueue(this);

        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_json.put("comment", comment.getText().toString());

                JSONObject parameter = new JSONObject(comment_json);
                try {
                    parameter.put("post_no", Integer.parseInt(post_no));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        url,
                        parameter,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast toast = Toast.makeText(PostDetailActivity.this, "댓글이 작성 되었습니다.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast toast = Toast.makeText(PostDetailActivity.this, "댓글 작성 중 오류가 발생했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG);
                                toast.show();

                                error.printStackTrace();
                            }
                        }
                );

                queue.add(jsonObjectRequest);
            }
        });
    }
}