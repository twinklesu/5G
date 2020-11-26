package com.example.weather;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoticeViewFragment extends LinearLayout {
    TextView txtNoti;
    ImageView imageView;

    public NoticeViewFragment(Context context) {
        super(context);
        init(context);
    }

    public NoticeViewFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 지금 만든 객체(xml 레이아웃)를 인플레이션화(메모리 객체화)해서 붙여줌
    // LayoutInflater를 써서 시스템 서비스를 참조할 수 있음
    // 단말이 켜졌을 때 기본적으로 백그라운드에서 실행시키는 것을 시스템 서비스라고 함.

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_notice_view, this, true);

        txtNoti = findViewById(R.id.txtNoti);
        imageView = findViewById(R.id.imageView);
    }

    public void setNoti(String noti) {
        txtNoti.setText(noti);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}
