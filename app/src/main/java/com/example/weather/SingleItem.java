package com.example.weather;

public class SingleItem {
    String noti;
    int resId;

    public SingleItem(String noti) {
        this.noti = noti;
        this.resId = resId;
    }

    public String getNoti() {
        return noti;
    }

    public void setNoti(String noti) {
        this.noti = noti;
    }

    public int getRedsd() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "SingleItem{" +
                "noti='" + noti + '\'' +
                ", resId=" + resId +
                '}';
    }
}
