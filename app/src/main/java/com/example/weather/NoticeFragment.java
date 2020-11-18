package com.example.weather;

import android.os.Bundle;

import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NoticeFragment extends ListFragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notice, container, false);

        String[] values = new String[] {"1", "2", "3", "4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        return rootView;
    }

    //아이템 클릭 이벤트
    public void onListItemClick(ListView l, View v, int position, long id) {
        String strText = (String) l.getItemAtPosition(position);
        Log.d("Fragment: ", position + ": " + strText);
        Toast.makeText(this.getContext(), "클릭: " + position + " " + strText, Toast.LENGTH_LONG).show();
    }
}