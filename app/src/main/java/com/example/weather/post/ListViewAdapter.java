package com.example.weather.post;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weather.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private String TAG = this.getClass().getSimpleName();
    Context context;
    ArrayList<PostItem> items;

    public ListViewAdapter(Context context, ArrayList<PostItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View postItem = inflater.inflate(R.layout.activity_post_view, parent, false);
        PostItem data = items.get(position);

        TextView title = postItem.findViewById(R.id.txtPostTitle);
        TextView content = postItem.findViewById(R.id.txtPostContent);
        TextView nickname = postItem.findViewById(R.id.txtNickNm);

        title.setText(data.getTitle());
        content.setText(data.getContent());
        nickname.setText(data.getId());

        postItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: my position is " + Integer.toString(position));
            }
        });

        return postItem;
    }
}
