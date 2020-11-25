//글쓰기 댓글 보이는 곳
package com.example.weather.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;

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

        Id = findViewById(R.id.txtNickName);
        Title = findViewById(R.id.txtPostDetailTitle);
        Content = findViewById(R.id.txtContent);

        Id.setText(id);
        Title.setText(title);
        Content.setText(content);
    }
}