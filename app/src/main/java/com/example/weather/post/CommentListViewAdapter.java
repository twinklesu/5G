package com.example.weather.post;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.fragment.HomeFragment;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentListViewAdapter extends BaseAdapter {
    String TAG = CommentListViewAdapter.class.getSimpleName();

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
        TextView time = commentItem.findViewById(R.id.txtCmtTime);

        id.setText(data.getId());
        comment.setText(data.getComment());
        time.setText(formatTimeString(timeToMill(data.getTime())));

        return commentItem;
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
