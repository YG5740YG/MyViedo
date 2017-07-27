package com.yg.mylibrary;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class Tools {
    /**
     * 把时间戳转换成需要的时间形式
     * @param timeValue 时间戳
     * @return
     */
    public static String timeCount(int timeValue,VideoData videoData){
        String timeStringValue="";
        int  mDay = timeValue / (24 * 60 * 60 * 1000);
        int mHour = (timeValue / (60 * 60 * 1000) - mDay * 24);
        int  mMin = ((timeValue / (60 * 1000)) - mDay * 24 * 60 - mHour * 60);
        int   mS = (timeValue / 1000 - mDay * 24 * 60 * 60 - mHour * 60 * 60 - mMin * 60);
        if(videoData.getCounter()==0){
            if(mDay>0){
                videoData.setDayFlage(true);
            }
            if (mHour>0){
                videoData.setHourFlage(true);
            }
            if(mMin>0){
                videoData.setMinFlage(true);
            }
        }
        if(videoData.isHourFlage()) {
            if (mHour < 10) {
                timeStringValue = "0" + mHour + ":";
            }else  if(mHour==0){
                timeStringValue ="00:";
            }
            else {
                timeStringValue = mHour + ":";
            }
        }
        if(videoData.isMinFlage()) {
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
