package com.example.weather.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weather.R;

import java.util.ArrayList;
import java.util.List;

public class CommentListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<CommentItem> items;

    public CommentListViewAdapter(Context context, ArrayList<CommentItem> items) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View commentItem = inflater.inflate(R.layout.activity_comment_view, parent, false);
        CommentItem data = items.get(position);

        TextView id = commentItem.findViewById(R.id.txtCmtNickNm);
        TextView comment = commentItem.findViewById(R.id.txtCmtView);

        id.setText(data.getId());
        comment.setText(data.getComment());

        return commentItem;
    }
}
