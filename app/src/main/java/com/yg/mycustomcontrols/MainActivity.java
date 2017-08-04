package com.yg.mycustomcontrols;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.yg.mylibrary.Calendar.MyCalendarView;
import com.yg.mylibrary.MyVideoView;

public class MainActivity extends AppCompatActivity implements MyCalendarView.OnListen{
    MyVideoView mMyVideoView;
    String mImagePath;
    MyCalendarView mMyCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.content);
         mMyVideoView=new MyVideoView(this)
                .inintVideoData()
                .setVideoPath("https://img2.ch999img.com//pic/product/opic/201706162136041.mp4")
                .loadeVideo()
//                .setMaskImage(R.mipmap.ic_launcher_round);
        .setMaskImage("https://img2.ch999img.com/pic/product/440x440/20170214152618892.jpg");
        linearLayout.addView(mMyVideoView.getVideoViewLayout());
        mMyCalendarView=new MyCalendarView(getApplicationContext(),0);
        mMyCalendarView.setOnListen(this);
        linearLayout.addView(mMyCalendarView);
        if(savedInstanceState!=null){
            mMyVideoView.setHorizontalData(savedInstanceState.getSerializable("videoData"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("videoData",mMyVideoView.getVideoData());
    }

    @Override
    public void onLastMonthListener() {
        mMyCalendarView.getCanlendarView().setLastMonth();
        mMyCalendarView.getTextSelectMonth().setText(mMyCalendarView.getCanlendarView().getDate());
    }

    @Override
    public void onNextMonthListener() {
        mMyCalendarView.getCanlendarView().setNextMonth();
        mMyCalendarView.getTextSelectMonth().setText(mMyCalendarView.getCanlendarView().getDate());
    }
}
