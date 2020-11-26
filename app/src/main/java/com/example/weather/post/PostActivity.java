//글쓰기 목록
package com.example.weather.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.MainActivity;
import com.example.weather.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    String TAG = this.getClass().getSimpleName();
    int post_count;
    ImageButton imgbtnPostBack;
    private FloatingActionButton fabtnPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imgbtnPostBack = findViewById(R.id.imgbtnPostBack);
        imgbtnPostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(PostActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        final ListView listView = findViewById(R.id.postList);
        final ArrayList<PostItem> postItems = new ArrayList<>();
        final ListViewAdapter adapter = new ListViewAdapter(this, postItems);
        listView.setAdapter(adapter);

        final ArrayList<Integer> post_no = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/post/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray results = response;

                            for(int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                                PostItem item = new PostItem(result.getString("post_id"), result.getString("post_title"), result.getString("post_content"), result.getString("reg_dt"));
                                postItems.add(item);
                            }
                            post_count = response.length();

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d(TAG, "Post GET FAIL");
                    }
                }
        );

        queue.add(jsonArrayRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                Intent intent = new Intent(PostActivity.this, PostDetailActivity.class);
                intent.putExtra("ID", postItems.get(position).getId());
                intent.putExtra("Title", postItems.get(position).getTitle());
                intent.putExtra("Content", postItems.get(position).getContent());
                intent.putExtra("PostNo", String.valueOf(post_count - position));
                intent.putExtra("PostCount", post_count);
                intent.putExtra("PostTime", postItems.get(position).getTime());
                startActivity(intent);
            }
        });

        fabtnPlus = findViewById(R.id.fabtnPlus);
        fabtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, PostWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}