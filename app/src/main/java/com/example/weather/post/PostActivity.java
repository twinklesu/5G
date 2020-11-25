//글쓰기 목록
package com.example.weather.post;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    String TAG = this.getClass().getSimpleName();
    int post_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final ListView listView = findViewById(R.id.postList);
        final ArrayList<PostItem> postItems = new ArrayList<>();
        final ListViewAdapter adapter = new ListViewAdapter(this, postItems);
        listView.setAdapter(adapter);

        final ArrayList<Integer> post_no = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/post/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            for(int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                                PostItem item = new PostItem(result.getString("post_id"), result.getString("post_title"), result.getString("post_content"));
                                postItems.add(item);
                            }
                            post_count = response.getInt("count");

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

        queue.add(jsonObjectRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                Intent intent = new Intent(PostActivity.this, PostDetailActivity.class);
                intent.putExtra("ID", postItems.get(position).getId());
                intent.putExtra("Title", postItems.get(position).getTitle());
                intent.putExtra("Content", postItems.get(position).getContent());
                intent.putExtra("PostNo", String.valueOf(position + post_count));
                startActivity(intent);
            }
        });

        ImageButton write = findViewById(R.id.imageButton);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, PostWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}