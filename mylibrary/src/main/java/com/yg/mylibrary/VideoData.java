package com.yg.mylibrary;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class VideoData implements Serializable {
    /**
     * 记录第一次加载数据时对时间进行类型处理
     */
    private int mCounter;
    /**
     * 获取播放总时长
     */
    String mDuration;
    /**
     * 获取当前总时长，String
     */
    String mCrrrentPosition;

    public String getCrrrentPosition() {
        return mCrrrentPosition;
    }

    public void setCrrrentPosition(String crrrentPosition) {
        mCrrrentPosition = crrrentPosition;
    }

    /**
     * 是否正在播放
     */
    private boolean mIsVideoPlaying;
    /**
     * 是否具有天
     */
    boolean mDayFlage;
    /**
     * 是否有小时
     */
    boolean mHourFlage;
    /**
     * 是否有分钟
     */
    boolean mMinFlage;
    /**
     * 视频播放路径
     */
    String mVideoPath;

    public int getCruntTime() {
        return mCruntTime;
    }

    public void setCruntTime(int cruntTime) {
        mCruntTime = cruntTime;
    }

    private int mCruntTime;
    private int mTotalTime;

    public int getTotalTime() {
        return mTotalTime;
    }

    public void setTotalTime(int totalTime) {
        mTotalTime = totalTime;
    }

    public int getCounter() {
        return mCounter;
    }

    public void setCounter(int counter) {
        mCounter = counter;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public boolean isVideoPlaying() {
        return mIsVideoPlaying;
    }

    public void setVideoPlaying(boolean videoPlaying) {
        mIsVideoPlaying = videoPlaying;
    }

    public boolean isDayFlage() {
        return mDayFlage;
    }

    public void setDayFlage(boolean dayFlage) {
        mDayFlage = dayFlage;
    }

    public boolean isHourFlage() {
        return mHourFlage;
    }

    public void setHourFlage(boolean hourFlage) {
        mHourFlage = hourFlage;
    }

    public boolean isMinFlage() {
        return mMinFlage;
    }

    public void setMinFlage(boolean minFlage) {
        mMinFlage = minFlage;
    }

    public String getVideoPath() {
        return mVideoPath;
    }

    public void setVideoPath(String videoPath) {
        mVideoPath = videoPath;
    }

    public VideoData(){
        mCounter=0;
        mIsVideoPlaying=false;
        mDayFlage=false;
        mHourFlage=false;
        mMinFlage=false;
    }
}
