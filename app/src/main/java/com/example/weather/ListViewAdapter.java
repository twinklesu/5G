package com.example.weather;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weather.post.PostActivity;

public class ListViewAdapter {
    TextView title;
    TextView id;
    TextView context ;

    public ListViewAdapter() {

    }

    public void addItem(String title, String id, String context) {
        this.title.setText(title);
        this.context.setText(context);
        this.id.setText(id);
    }
}
