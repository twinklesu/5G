package com.example.weather.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weather.R;

import java.util.ArrayList;

public class LankingListViewAdapter extends BaseAdapter {

    String TAG = LankingListViewAdapter.class.getSimpleName();

    Context context;
    ArrayList<LankingItem> items;

    public LankingListViewAdapter(Context context, ArrayList<LankingItem> items) {
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
        View lankingItem = inflater.inflate(R.layout.laking_list_view, parent, false);
        LankingItem data = items.get(position);

        TextView id = lankingItem.findViewById(R.id.laking_id);
        TextView lank = lankingItem.findViewById(R.id.lank);

        id.setText(data.getId());
        lank.setText(data.getLank());

        lankingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: my position is " + Integer.toString(position));
            }
        });

        return lankingItem;
    }
}
