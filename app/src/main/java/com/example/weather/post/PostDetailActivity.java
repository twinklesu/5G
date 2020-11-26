//글쓰기 댓글 보이는 곳
package com.example.weather.post;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;
import com.example.weather.fragment.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PostDetailActivity extends AppCompatActivity {

    String TAG = PostDetailActivity.class.getSimpleName();

    TextView Id, Title, Content, Time;
    ImageButton imgbtnPostDetailBack;
    int post_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        String title = intent.getStringExtra("Title");
        String content = intent.getStringExtra("Content");
        String time = intent.getStringExtra("PostTime");
        final String post_no = intent.getStringExtra("PostNo");
        Id = findViewById(R.id.txtNickName);
        Title = findViewById(R.id.txtPostDetailTitle);
        Content = findViewById(R.id.txtContent);
        Time = findViewById(R.id.txtTime);

        final EditText comment = findViewById(R.id.edtComment);
        ImageButton comment_btn = findViewById(R.id.btnComment);

        Id.setText(id);
        Title.setText(title);
        Content.setText(content);
        Time.setText(formatTimeString(timeToMill(time)));

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

                                recreate();
                                comment.setText("");
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

        post_count = intent.getIntExtra("PostCount", 0);
        setComment(post_no);

        imgbtnPostDetailBack = findViewById(R.id.imgbtnPostDetailBack);
        imgbtnPostDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PostDetailActivity.this, PostActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    void setComment(String post_no) {
        ListView listView = findViewById(R.id.commentListView);
        final ArrayList<CommentItem> commentItems = new ArrayList<>();
        final CommentListViewAdapter adapter = new CommentListViewAdapter(this, commentItems);
        listView.setAdapter(adapter);

        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/get-post-comment/" + post_no;

        Log.d(TAG, "post_no" + post_no);

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject result = response.getJSONObject(i);
                                CommentItem commentItem = new CommentItem(result.getString("comment"), result.getString("user_id"));
                                commentItems.add(commentItem);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Comment GET FAIL");
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }


    public static String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < HomeFragment.TIME_MAXIMUM.SEC) {
            msg = "방금 전";
        } else if ((diffTime /= HomeFragment.TIME_MAXIMUM.SEC) < HomeFragment.TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= HomeFragment.TIME_MAXIMUM.MIN) < HomeFragment.TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= HomeFragment.TIME_MAXIMUM.HOUR) < HomeFragment.TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= HomeFragment.TIME_MAXIMUM.DAY) < HomeFragment.TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }

        return msg;
    }

    public long timeToMill(String time) {
        String parseString = time.replace("T", " ").replace("+09:00", "");
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = fm.parse(parseString);
        } catch (ParseException e) {
            Log.d(TAG, "", e);
        }

        return date.getTime();
    }
}