package com.yg.mylibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    /**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }
}
