package com.example.weather.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.R;
import com.example.weather.VoteActivity;
import com.example.weather.post.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LankFragment extends Fragment {
    String TAG = LankFragment.class.getSimpleName();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_lank, container, false);
        setLank(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setLank(View view) {
        final LankingListViewAdapter adapter;
        final ArrayList<LankingItem> lankingItems = new ArrayList<>();
        adapter = new LankingListViewAdapter(getContext(), lankingItems);
        ListView listView = view.findViewById(R.id.lanking_listView);
        listView.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/join/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("results");

                            for (int i = 0; i < result.length(); i++) {
                                JSONObject member = result.getJSONObject(i);
                                LankingItem lankingItem = new LankingItem(member.getString("user_id"), String.valueOf(2 + i));
                                lankingItems.add(lankingItem);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Lank fail");
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }
}