package com.yg.mylibrary;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class MyVideoView {
    private VideoView mVideoView;
    private TextView mVideoControllTextView;
    private ImageView mVideoControllImageView;
    private SeekBar mSeekBar;
    private int mCounter=0;
    String mDuration;
    private boolean mIsVideoPlaying=false;
    boolean mDayFlage=false;
    boolean mHourFlage=false;
    boolean mMinFlage=false;
    Context mContext;
    View mVideoViewLayout;
    String mVideoPath;
    MyVideoView mMyVideoView;
    public View getVideoViewLayout(){
        return mVideoViewLayout;
    }
    public MyVideoView(Context context){
        this.mContext=context;
    }
    public MyVideoView inintVideoData(){
        mVideoViewLayout= LayoutInflater.from(mContext).inflate(R.layout.fragment_video_test_layout,null);
        findView();
        setUp();
        mMyVideoView=this;
        return this;
    }
    private void findView() {
        mVideoView=(VideoView)mVideoViewLayout.findViewById(R.id.my_video_view);
        mVideoControllTextView=(TextView)mVideoViewLayout.findViewById(R.id.video_view_timecontrol_text_view);
        mSeekBar=(SeekBar)mVideoViewLayout.findViewById(R.id.video_view_seeker_bar);
        mVideoControllImageView=(ImageView)mVideoViewLayout.findViewById(R.id.video_view_control_image_view);
        mVideoControllTextView=(TextView)mVideoViewLayout.findViewById(R.id.video_view_timecontrol_text_view);
    }
    private void setUp() {
        mVideoControllImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsVideoPlaying) {
                    mIsVideoPlaying=false;
                    stopVideo();
                    mVideoControllImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.product_video_stop));
                }else{
                    mIsVideoPlaying=true;
                    startVideo();
                    mVideoControllImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.product_video_play));
                }
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        // 获得当前播放时间和当前视频的长度
                        int time = ((getCruntTime() * 100) / getTotalTime());
                        // 设置进度条的主要进度，表示当前的播放时间
//                                       mSeekBar.setProgress(time);
                        // 设置进度条的次要进度，表示视频的缓冲进度
                        mSeekBar.setProgress(percent);
                    }
                });
            }
        });
    }
    /**
     *  设置播放路径
     * @param videoPath 视频路径
     */
    public MyVideoView setVideoPath(String videoPath){
        this.mVideoPath=videoPath;
        return this;
    }
    /**
     * 加载视频
     * @param
     */
    public MyVideoView loadeVideo(){
        setProgressDrawable(R.drawable.myseekbar);
        setThumb(R.drawable.seekbar_selector_thumb);
        mVideoView.setVideoPath(mVideoPath);
        mVideoView.requestFocus();
        return this;
    }
    /**
     * 开始播放
     */
    public void startVideo(){
        mVideoView.start();
        Message message=new Message();
        message.what = 0;
        handler.sendMessageDelayed(message,200);
    }

    /**
     * 停止播放
     */
    public void stopVideo(){
        mVideoView.pause();
    }

    /**
     * 获取视频时间总长
     * @return
     */
    public int getTotalTime(){
        return mVideoView.getDuration();
    }

    /**
     * 获取视频当前时间
     * @return
     */
    public int getCruntTime(){
        return mVideoView.getCurrentPosition();
    }

    /**
     * 设置视频控制器进度条样式
     * @param seekDrawable
     */
    public void setProgressDrawable(int seekDrawable){
        mSeekBar.setProgressDrawable(mContext.getResources().getDrawable(seekDrawable));
    }
    /**
     * 设置视频控制器滑块样式
     * @param thumbDrawable
     */
    private void setThumb(int thumbDrawable){
        mSeekBar.setThumb(mContext.getResources().getDrawable(thumbDrawable));
    }

    /**
     * 循环控制进度条
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if(mCounter==0) {
                        mDuration = timeCount(getTotalTime());
                    }
                    String CrrrentPosition=timeCount(getCruntTime());
                    mSeekBar.setMax(mVideoView.getDuration()/1000);
                    mSeekBar.setSecondaryProgress(getCruntTime()/1000);
                    mVideoControllTextView.setText(CrrrentPosition+"/"+mDuration);
                    if(getTotalTime()!=getCruntTime()){
                        mCounter++;
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessageDelayed(message, 1000);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 把时间戳转换成需要的时间形式
     * @param timeValue 时间戳
     * @return
     */
    public String timeCount(int timeValue){
        String timeStringValue="";
        int  mDay = timeValue / (24 * 60 * 60 * 1000);
        int mHour = (timeValue / (60 * 60 * 1000) - mDay * 24);
        int  mMin = ((timeValue / (60 * 1000)) - mDay * 24 * 60 - mHour * 60);
        int   mS = (timeValue / 1000 - mDay * 24 * 60 * 60 - mHour * 60 * 60 - mMin * 60);
        if(mCounter==0){
            if(mDay>0){
                mDayFlage=true;
            }
            if (mHour>0){
                mHourFlage=true;
            }
            if(mMin>0){
                mMinFlage=true;
            }
        }
        if(mHourFlage) {
            if (mHour < 10) {
                timeStringValue = "0" + mHour + ":";
            }else  if(mHour==0){
                timeStringValue ="00:";
            }
            else {
                timeStringValue = mHour + ":";
            }
        }
        if(mMinFlage) {
            if (mMin < 10) {
                timeStringValue = timeStringValue + "0" + mMin + ":";
            }else if(mMin==0){
                timeStringValue ="00:";
            }
            else {
                timeStringValue = timeStringValue + mMin + ":";
            }
        }
        if (mS < 10) {
            timeStringValue=timeStringValue+"0" + mS;
        } else {
            timeStringValue=timeStringValue+mS;
        }
        return timeStringValue;
    }
}
