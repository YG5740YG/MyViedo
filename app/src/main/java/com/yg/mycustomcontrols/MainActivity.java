package com.yg.mycustomcontrols;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.yg.mylibrary.MyVideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.content);
        MyVideoView mMyVideoView=new MyVideoView(this)
                .inintVideoData()
                .setVideoPath("https://img2.ch999img.com//pic/product/opic/201706162136041.mp4")
                .loadeVideo();
        linearLayout.addView(mMyVideoView.getVideoViewLayout());
    }
}
