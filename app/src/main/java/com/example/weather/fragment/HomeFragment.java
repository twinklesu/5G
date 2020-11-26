package com.example.weather.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.VoteActivity;
import com.example.weather.post.PostActivity;
import com.example.weather.post.PostWriteActivity;
import com.example.weather.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class HomeFragment extends Fragment {

    static final String TAG = HomeFragment.class.getSimpleName();
    private View rootView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Button goToBoard = getActivity().findViewById(R.id.moreButton1);
        goToBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getContext(), PostActivity.class); // 후에 연결 수정 필요
                startActivity(intent);
            }
        });

        Date date = new Date();

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, 100);
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        Log.d(TAG, location.toString());
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        Log.d(TAG, "위치 : " + longitude + ", " + latitude);

        LatXLngY temp = convertGRID_GPS(TO_GRID, latitude, longitude);

        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";    //동네예보조회

        String serviceKey = "9KSHD5LtoJANsIrfd8%2BHozU%2FSzL7EXty4XBhHas84GDqMsPgSoMXEQkOkMKI9EcOIYX%2FXsT7PxU4lENxllCvoA%3D%3D";
        String nx = Integer.toString((int) temp.x);    //위도
        String ny = Integer.toString((int) temp.y);    //경도
        String baseDate = SetParameter.setDate(date);    //조회하고싶은 날짜
        String baseTime = SetParameter.setTime(date);    //API 제공 시간
        String dataType = "JSON";    //타입 xml, json
        String numOfRows = "14";    //한 페이지 결과 수

        Log.d(TAG, "nx, ny : " + nx + "," + ny);

        RequestQueue queue = Volley.newRequestQueue(HomeFragment.this.getActivity());

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        try {
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); //경도
            urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); //위도
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));    /* 타입 */
            urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
            urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));    /* 한 페이지 결과 수 */
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = urlBuilder.toString();
        Log.d(TAG, "URL : " + url);

//        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
//                url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, "Success : " + response.toString());
//                        try {
//                            JSONObject result = (JSONObject) response.get("result");
//                            JSONObject body = (JSONObject) result.get("body");
//                            JSONObject items = (JSONObject) body.get("items");
//                            JSONArray item = (JSONArray) items.get("item");
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Log.d(TAG, "FAIL");
//            }
//        }
//        );
//        queue.add(jsonArrayRequest);

        setBoardPost();
        setWeather(url);
        setVoteResult();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");

        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton vote = (ImageButton) rootView.findViewById(R.id.vote);
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VoteActivity.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static int TO_GRID = 0;
    public static int TO_GPS = 1;

    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y) {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

        //
        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
        //


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        } else {
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            } else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                } else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;
    }

    class LatXLngY {
        public double lat;
        public double lng;

        public double x;
        public double y;

    }

    static class SetParameter {

        static String setTime(Date today) {
            String base_time;
            SimpleDateFormat time = new SimpleDateFormat("HH");

            int nowTime = Integer.parseInt(time.format(today));

            Log.d(TAG, "Time : " + time.format(today));

            // Base_time : 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300
            if (nowTime >= 2 && nowTime < 5) base_time = "0200";
            else if (nowTime >= 5 && nowTime < 8) base_time = "0500";
            else if (nowTime >= 8 && nowTime < 11) base_time = "0800";
            else if (nowTime >= 11 && nowTime < 14) base_time = "1100";
            else if (nowTime >= 14 && nowTime < 17) base_time = "1400";
            else if (nowTime >= 17 && nowTime < 20) base_time = "1700";
            else if (nowTime >= 20 && nowTime < 23) base_time = "2000";
            else base_time = "2300";

            return base_time;
        }

        static String setDate(Date today) {
            String base_date;
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat time = new SimpleDateFormat("HH");
            int nowTime = Integer.parseInt(time.format(today));

            Date dDate = new Date();
            dDate = new Date(dDate.getTime() + (1000 * 60 * 60 * 24 * -1));

            if (nowTime == 0 || nowTime == 1)
                base_date = date.format(dDate);
            else base_date = date.format(today);

            return base_date;
        }
    }

    void setBoardPost() {
        String url = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/recent-post-5/";

        RequestQueue queue = Volley.newRequestQueue(HomeFragment.this.getActivity());

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Success Set Post : " + response.toString());
                        try {
                            JSONArray results = (JSONArray) response.get("results");

                            ArrayList<JSONObject> result = new ArrayList<>();
                            ArrayList<String> setTitle = new ArrayList<>();
                            ArrayList<String> setTime = new ArrayList<>();

                            for(int i = 0; i < 5; i++) result.add((JSONObject) results.get(i));
                            for(int i = 0; i < 5; i++) setTitle.add(result.get(i).get("post_title").toString());
                            for (int i = 0; i < 5; i++) setTime.add(formatTimeString(timeToMill(result.get(i).get("reg_dt").toString())));


                            ArrayList<TextView> setTitletextViewArrayList = new ArrayList<>();
                            setTitletextViewArrayList.add((TextView) getActivity().findViewById(R.id.title1));
                            setTitletextViewArrayList.add((TextView) getActivity().findViewById(R.id.title2));
                            setTitletextViewArrayList.add((TextView) getActivity().findViewById(R.id.title3));
                            setTitletextViewArrayList.add((TextView) getActivity().findViewById(R.id.title4));
                            setTitletextViewArrayList.add((TextView) getActivity().findViewById(R.id.title5));
                            for (int i = 0; i < setTitletextViewArrayList.size(); i++) {
                                setTitletextViewArrayList.get(i).setText(setTitle.get(i));
                            }


                            ArrayList<TextView> setTimeTextViewArrayList = new ArrayList<>();
                            setTimeTextViewArrayList.add((TextView) getActivity().findViewById(R.id.time1));
                            setTimeTextViewArrayList.add((TextView) getActivity().findViewById(R.id.time2));
                            setTimeTextViewArrayList.add((TextView) getActivity().findViewById(R.id.time3));
                            setTimeTextViewArrayList.add((TextView) getActivity().findViewById(R.id.time4));
                            setTimeTextViewArrayList.add((TextView) getActivity().findViewById(R.id.time5));
                            for (int i = 0; i < setTimeTextViewArrayList.size(); i++) {
                                setTimeTextViewArrayList.get(i).setText(setTime.get(i));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d(TAG, "SET POST FAIL");
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    void setWeather(String url) {
        RequestQueue queue = Volley.newRequestQueue(HomeFragment.this.getActivity());

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Success : " + response.toString());
                        try {
                            TextView t = (TextView) getActivity().findViewById(R.id.weatherMeteorological);
                            TextView t_max = (TextView) getActivity().findViewById(R.id.temp_highest);
                            TextView t_min = (TextView) getActivity().findViewById(R.id.temp_min);

                            JSONObject result = (JSONObject) response.get("response");
                            JSONObject body = (JSONObject) result.get("body");
                            JSONObject items = (JSONObject) body.get("items");
                            JSONArray item = (JSONArray) items.get("item");

                            Log.d(TAG, "item : " + item.get(4).toString());

                            JSONObject t3h = (JSONObject) item.get(4);
                            t.setText(t3h.get("fcstValue").toString() + "℃");

                            Log.d(TAG, "t3h : " + t3h.get("fcstValue").toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d(TAG, "FAIL");
                    }
                });

        queue.add(jsonArrayRequest);
    }

    public static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
    public static String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        Log.d(TAG, Long.toString(regTime) + " " + Long.toString(curTime));
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = "방금 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
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

    public void setVoteResult() {
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url_fashion = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/fashion-result/";
        String url_weather = "http://weather.eba-eqpgap7p.ap-northeast-2.elasticbeanstalk.com/weather-result/";

        final TextView weather_result = getActivity().findViewById(R.id.weatherAgeGender);
        final ArrayList<TextView> fashion_result = new ArrayList<>();
        fashion_result.add((TextView) getActivity().findViewById(R.id.codi_recommend1));
        fashion_result.add((TextView) getActivity().findViewById(R.id.codi_recommend2));
        fashion_result.add((TextView) getActivity().findViewById(R.id.codi_recommend3));
        fashion_result.add((TextView) getActivity().findViewById(R.id.codi_recommend4));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url_weather,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("weather");
                            weather_result.setText(result.getJSONArray(0).getString(0) + " / " + result.getJSONArray(1).getString(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "weather result fail");
                    }
                }
        );

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET,
                url_fashion,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("fashion");
                            for (int i = 0; i < result.length(); i++) {
                                fashion_result.get(i).setText(result.getJSONArray(i).getString(0));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "fashion result fail");
                    }
                }
        );

        queue.add(jsonObjectRequest);
        queue.add(jsonObjectRequest1);
    }
}