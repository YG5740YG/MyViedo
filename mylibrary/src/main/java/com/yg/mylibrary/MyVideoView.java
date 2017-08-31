package com.yg.mylibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class MyVideoView {
    /**
     * VideoView
     */
    private VideoView mVideoView;
    /**
     * 显示时间view
     */
    private TextView mVideoControllTextView;
    /**
     * 显示播放图标
     */
    private ImageView mVideoControllImageView;
//    private SeekBar mSeekBar;
    /**
     * 滚动条
     */
    private ProgressBar mProgressBar;
    Context mContext;
    /**
     * 播放器装载布局
     */
    View mVideoViewLayout;
    /**
     * 实例化
     */
    MyVideoView mMyVideoView;
    /**
     * 播放停止按钮父容器
     */
    LinearLayout mVideoContent;

    VideoData mVideoData;
    /**
     * 遮罩停止图片父布局
     */
    LinearLayout mVideoStopImageLayout;
    /**
     * 遮罩图片
     */
    ImageView mMaskImage;

    Bitmap mBitmap;
    String mImagePath;

    LinearLayout mVideoControl;
    TextView mQieHuan;
    TextView mLineFour;
    TextView mLineFive;
    Activity mActivity;
    public TextView getQieHuanView (){
        return mQieHuan;
    }
    public MyVideoView inintVideoData(){
        mVideoViewLayout= LayoutInflater.from(mContext).inflate(R.layout.fragment_video_test_layout,null);
        mVideoData=new VideoData();
        findView();
        setUp();
        mMyVideoView=this;
        setLineShow(false);
        return this;
    }
    public MyVideoView inintVideoData(Activity activity){
        mVideoViewLayout= LayoutInflater.from(mContext).inflate(R.layout.fragment_video_test_layout,null);
        mVideoData=new VideoData();
        findView();
        setUp();
        mMyVideoView=this;
        mActivity=activity;
        setLineShow(false);
        return this;
    }
    public void setOrientation(){
        if(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT==mActivity.getRequestedOrientation()){
           mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE==mActivity.getRequestedOrientation()){
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public LinearLayout getVideoControl(){
        return mVideoControl;
    }
    public void qiehuanClick(){
        mQieHuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOrientation();
            }
        });
    }

    private void findView() {
        mVideoView=(VideoView)mVideoViewLayout.findViewById(R.id.my_video_view);
        mVideoControllTextView=(TextView)mVideoViewLayout.findViewById(R.id.video_view_timecontrol_text_view);
//        mSeekBar=(SeekBar)mVideoViewLayout.findViewById(R.id.video_view_seeker_bar);
        mProgressBar=(ProgressBar) mVideoViewLayout.findViewById(R.id.video_view_progress_bar);
        mVideoControllImageView=(ImageView)mVideoViewLayout.findViewById(R.id.video_view_control_image_view);
        mVideoControllTextView=(TextView)mVideoViewLayout.findViewById(R.id.video_view_timecontrol_text_view);
        mVideoContent=(LinearLayout)mVideoViewLayout.findViewById(R.id.video_content);
        mVideoStopImageLayout=(LinearLayout)mVideoViewLayout.findViewById(R.id.product_video_big_stop_layout);
        mMaskImage=(ImageView)mVideoViewLayout.findViewById(R.id.image_mask);
        mVideoControl=(LinearLayout)mVideoViewLayout.findViewById(R.id.video_control_layout);
        mQieHuan=(TextView)mVideoViewLayout.findViewById(R.id.qiehuan);
        mLineFour=(TextView)mVideoViewLayout.findViewById(R.id.line4);
        mLineFive=(TextView)mVideoViewLayout.findViewById(R.id.line5);
    }
    public void setLineShow(Boolean flage){
        if(flage){
            mLineFour.setVisibility(View.VISIBLE);
            mLineFive.setVisibility(View.VISIBLE);
        }else{
            mLineFour.setVisibility(View.GONE);
            mLineFive.setVisibility(View.GONE);
        }
    }
    public MyVideoView(Context context){
        this.mContext=context;
    }
    public boolean IsPlaying(){
        return mVideoData.isVideoPlaying();
    }
    /**
     * 获取视频播放器
     * @return
     */
    public View getVideoViewLayout(){
        return mVideoViewLayout;
    }

    /**
     * 获取遮罩图片
     * @return
     */
    public ImageView getMaskImage(){
        return mMaskImage;
    }
    /**
     * 设置遮罩显示或者隐藏
     * @param show
     */
    public void setMastShow(boolean show){
        if(show) {
            mMaskImage.setVisibility(View.VISIBLE);
        }else{
            mMaskImage.setVisibility(View.GONE);
        }
    }
    public void setVideoPlayingValue(boolean playingFlage){
        mVideoData.setVideoPlaying(playingFlage);
    }
    /**
     * 设置遮罩停止按钮显示或者隐藏
     * @param show
     */
    public void setMaskStopImageShow(boolean show){
        if(show) {
            mVideoStopImageLayout.setVisibility(View.VISIBLE);
        }else{
            mVideoStopImageLayout.setVisibility(View.GONE);
        }
    }
    public MyVideoView setMaskImage(int mipmap){
        mMaskImage.setImageDrawable(mContext.getResources().getDrawable(mipmap));
        return this;
    }
    public MyVideoView setMaskImage(String imagePath){
        mImagePath=imagePath;
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(mImagePath);
                    mBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Message message=new Message();
                    message.what = 1;
                    handler.sendMessageDelayed(message,0);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return this;
    }
    private void setUp() {
        mVideoStopImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoData.setVideoPlaying(true);
                startVideo();
                setMaskStopImageShow(false);
                setChangeStopPlayImage(true);
            }
        });
        mVideoContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoData.isVideoPlaying()) {
                    mVideoData.setVideoPlaying(false);
                    setChangeStopPlayImage(false);
                    stopVideo();
                }else{
                    mVideoData.setVideoPlaying(true);
                    setChangeStopPlayImage(true);
                    setMaskStopImageShow(false);
                    startVideo();
                }
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        mProgressBar.setProgress(percent);
                        Log.d("ggg==========precent==",percent+"");
                    }
                });
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setChangeStopPlayImage(false);
                mProgressBar.setSecondaryProgress(0);
                mVideoControllTextView.setText(Tools.timeCount(0,mVideoData,false)+"/"+mVideoData.getDuration());
                mProgressBar.setProgress(0);
                stopVideo();
            }
        });
        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    if(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE==mActivity.getRequestedOrientation()){
                        mVideoControl.setVisibility(View.VISIBLE);
                    mVideoControl.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mVideoControl.setVisibility(View.GONE);
                        }
                    },5000);
                    }
                }
                return true;
            }
        });
        qiehuanClick();
    }
    /**
     *  设置播放路径
     * @param videoPath 视频路径
     */
    public MyVideoView setVideoPath(String videoPath){
        mVideoData.setVideoPath(videoPath);
        return this;
    }
    /**
     * 加载视频
     * @param
     */
    public MyVideoView loadeVideo(){
        setProgressDrawable(R.drawable.myseekbar);
        setThumb(R.drawable.seekbar_selector_thumb);
        mVideoView.setVideoPath(mVideoData.getVideoPath());
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
        handler.sendMessageDelayed(message,300);
        setVideoBackImage();
    }

    public void setVideoBackImage(){
        Message message=new Message();
        message.what = 2;
        handler.sendMessageDelayed(message,300);
    }
    /**
     * 停止播放
     */
    public void stopVideo(){
        mVideoView.pause();
        setMaskStopImageShow(true);
        mMyVideoView.setChangeStopPlayImage(false);
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
        mProgressBar.setProgressDrawable(mContext.getResources().getDrawable(seekDrawable));
    }
    /**
     * 设置视频控制器滑块样式
     * @param thumbDrawable
     */
    private void setThumb(int thumbDrawable){
//        mProgressBar.setThumb(mContext.getResources().getDrawable(thumbDrawable));
    }
    /**
     * 设置停止或者播放按钮的显示图片
     * @param SPImageFlage  true 显示播放，false显示关闭
     */
    public void setChangeStopPlayImage(boolean SPImageFlage){
        if(SPImageFlage){
            mVideoControllImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.product_video_stop));
        }else{
            mVideoControllImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.product_video_play));
        }
    }
    public void setHorizontalData(Object videoData){
        mVideoData=(VideoData)videoData ;
        mVideoView.seekTo(mVideoData.getCruntTime());
        mVideoControllTextView.setText(mVideoData.getCrrrentPosition()+"/"+mVideoData.getDuration());
        mMaskImage.setVisibility(View.GONE);
        Message message = new Message();
        message.what = 0;
        handler.sendMessageDelayed(message, 300);
    }
    public VideoData getVideoData(){
        return mVideoData;
    }

    /**
     * 设置播放的位置
     * @param currentTime
     */
    public void setVideoCurrentTime(int currentTime){
        mVideoView.seekTo(currentTime);
    }
    /**
     * 循环控制进度条
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mVideoData.setDuration(Tools.timeCount(getTotalTime(),mVideoData,true));
                    String CrrrentPosition=Tools.timeCount(getCruntTime(),mVideoData,false);
                    mVideoData.setCruntTime(getCruntTime());
                    mVideoData.setTotalTime(getTotalTime());
                    mVideoData.setCrrrentPosition(CrrrentPosition);
                    mProgressBar.setMax(mVideoView.getDuration()/1000);
                    mProgressBar.setSecondaryProgress(getCruntTime()/1000);
                    mVideoControllTextView.setText(CrrrentPosition+"/"+mVideoData.getDuration());
                    if(getTotalTime()>getCruntTime()&&mVideoData.isVideoPlaying()||getCruntTime()<1000){
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessageDelayed(message, 1000);
                    }
                    break;
                case 1:
                    mMaskImage.setImageBitmap(mBitmap);
                    break;
                case 2:
                    mVideoView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                    setMastShow(false);
                    setMaskStopImageShow(false);
                    setChangeStopPlayImage(true);
                    mMyVideoView.setVideoPlayingValue(true);
                default:
                    break;
            }
        }
    };
}
