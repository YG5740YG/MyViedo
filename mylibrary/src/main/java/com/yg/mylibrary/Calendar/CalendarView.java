package com.yg.mylibrary.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.CursorAnchorInfo;

import com.yg.mylibrary.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class CalendarView extends View {

    // 列的数量
    private static final int NUM_COLUMNS    =   7;
    // 行的数量
    private static final int NUM_ROWS       =   6;

    /**
     * 可选日期数据
     */
    private List<String> mOptionalDates = new ArrayList<>();

    /**
     * 已选日期数据
     */
    private List<String> mSelectedDates = new ArrayList<>();

    // 背景颜色
    private int mBgColor = Color.WHITE;
    // 天数默认颜色
    private int mDayNormalColor = Color.BLACK;
    // 天数不可选颜色
    private int mDayNotOptColor = Color.BLUE;
    // 天数选择后颜色
    private int mDayPressedColor = Color.RED;
    // 天数字体大小
    private int mDayTextSize = 16;
    // 是否可以被点击状态
    private boolean mClickable = true;

    private DisplayMetrics mMetrics;
    private Paint mPaint;
    private Paint mPaintBlack;
    private Paint mPaintBackGroundBlue;
    private Paint mPaintBackGroundRed;
    private Paint mPaintBackGroundJu;
    private Paint mPaintBackGroundWhite;
    private int mCurYear;
    private int mCurMonth;
    private int mCurDate;

    private int mSelYear;
    private int mSelMonth;
    private int mSelDate;
    private int mColumnSize;
    private int mRowSize;
    private int[][] mDays;

    // 当月一共有多少天
    private int mMonthDays;
    // 当月第一天位于周几
    private int mWeekNumber;
    // 已选中背景Bitmap
//    private Bitmap mBgOptBitmap;
    // 未选中背景Bitmap
//    private Bitmap mBgNotOptBitmap;

    private String selectData="-1";
    Date dateStart;
    Date dataEnd;
    /**
     * 是否给当天标识颜色
     */
    int isShowCurDayPaint=1;

    int mPaintBackGroundBlueColor=Color.BLUE;
    int mPaintBackGroundJuColor=Color.parseColor("#ff6700");
    int mPaintBackGroundRedColor=Color.RED;
    int mPaintBackGroundWhiteColor=Color.WHITE;

    int mCruRow=0;
    int mColumn=0;

    int mLongTime=1500;
    boolean mBoolean=true;

    Context mContext;
    List<Integer>mRiceNum;
    int mRiceKey;
    LinkedHashMap<String ,Integer>mLinkedHashMap;
    public CalendarView(Context context) {
        super(context);
        mContext=context;
        init();
    }
    public CalendarView setShowCurDay(int showCurDay){
        isShowCurDayPaint=showCurDay;
        return this;
    }
    public CalendarView setmPaintBackGroundBlueColor(int color){
        mPaintBackGroundBlueColor=color;
        return this;
    }
    public CalendarView setmPaintBackGroundJuColor(int color){
        mPaintBackGroundJuColor=color;
        return this;
    }
    public CalendarView setmPaintBackGroundRedColor(int color){
        mPaintBackGroundRedColor=color;
        return this;
    }
    public void reDraw(){
        invalidate();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    private void init() {
        mRiceNum=new ArrayList<>();
        mLinkedHashMap=new LinkedHashMap<>();
        // 获取手机屏幕参数
        mMetrics = getResources().getDisplayMetrics();
        // 创建画笔
        mPaint = new Paint();
        mPaintBlack=new Paint();
        mPaintBackGroundBlue=new Paint();
        mPaintBackGroundJu=new Paint();
        mPaintBackGroundRed=new Paint();
        mPaintBackGroundWhite=new Paint();
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        mCurYear    =   calendar.get(Calendar.YEAR);//年
        mCurMonth   =   calendar.get(Calendar.MONTH);//月
        mCurDate    =   calendar.get(Calendar.DATE);//日
        setSelYTD(mCurYear, mCurMonth, mCurDate);

        // 获取背景Bitmap
//        mBgOptBitmap    = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_bg_course_optional);
//        mBgNotOptBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_bg_course_not_optional);
    }
    /**
     * 设置可选择日期
     * @param dates 日期数据
     */
    public CalendarView setOptionalDate(List<String> dates){
        this.mOptionalDates = dates;
        return this;
    }
    public void setDataAndRice(){
        if(mOptionalDates.size()>0&&mRiceNum.size()>0) {
            for (int i=0;i<mOptionalDates.size();i++) {
                mLinkedHashMap.put(mOptionalDates.get(i),mRiceNum.get(i));
            }
        }
        invalidate();
    }
    public CalendarView setRiceNum(List<Integer>riceNum){
        mRiceNum=new ArrayList<>();
        mRiceNum=riceNum;
        return this;
    }
    public int getmCurYear(){
        return mCurYear;
    }
    public int getmCurMonth(){
        return mCurMonth;
    }
    public int getmCurDate(){
        return mCurDate;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mRiceKey=0;
        initSize();
        // 绘制背景
        mPaint.setColor(mBgColor);
        //接口获取数据为蓝色
        mPaintBackGroundBlue.setColor(mPaintBackGroundBlueColor);
//        mPaintBackGroundBlue.setStyle(Paint.Style.STROKE);
        //当天颜色设置为橘色
        mPaintBackGroundJu.setColor(mPaintBackGroundJuColor);
        //点击选择颜色变为红色
        mPaintBackGroundRed.setColor(mPaintBackGroundRedColor);
        //白色
        mPaintBackGroundWhite.setColor(mPaintBackGroundWhiteColor);
        mDays = new int[6][7];
        // 设置绘制字体大小
        mPaint.setTextSize(mDayTextSize * mMetrics.scaledDensity);
        mPaintBlack.setTextSize(12* mMetrics.scaledDensity);
        // 设置绘制字体颜色

        String dayStr;
        // 获取当月一共有多少天
        mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
        // 获取当月第一天位于周几
        mWeekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);

        for(int day = 0; day < mMonthDays; day++){
            //绘制文字颜色
            mPaintBlack.setColor(Color.BLACK);
            dayStr = String.valueOf(day + 1);
            int column  =  (day + mWeekNumber - 1) % 7;
            int row     =  (day + mWeekNumber - 1) / 7;
            mDays[row][column] = day + 1;
            int startX = (int) (mColumnSize * column + (mColumnSize - mPaint.measureText(dayStr)) / 2);
            int startY = (int) (mRowSize * row + mRowSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            if(mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))||selectData.equals(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
                if(day+1<10){
                    canvas.drawCircle(startX+12,startY-15,35,mPaintBackGroundRed);
                }else {
                    canvas.drawCircle(startX + 28, startY - 15, 35, mPaintBackGroundRed);
                }
                mPaintBlack.setColor(Color.WHITE);
                canvas.drawText(dayStr, startX, startY, mPaintBlack);
                mSelectedDates.remove(getSelData(mSelYear, mSelMonth, mDays[row][column]));
            }else if(day+1==mCurDate&&mCurMonth==mSelMonth&&mCurYear==mSelYear&&isShowCurDayPaint==0){
                if(day+1<10) {
                    canvas.drawCircle(startX + 12, startY - 15, 35, mPaintBackGroundJu);
                }else{
                    canvas.drawCircle(startX + 28, startY - 15, 35, mPaintBackGroundJu);
                }
                mPaintBlack.setColor(Color.WHITE);
                canvas.drawText(dayStr, startX, startY, mPaintBlack);
            }
            // 判断是否为接口给的天数
            else if(mOptionalDates!= null && mOptionalDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
                // 可选，继续判断是否是点击过的
//                if(!mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
                // 没有点击过，绘制默认背景
//                    canvas.drawBitmap(mBgNotOptBitmap, startX - (mBgNotOptBitmap.getWidth() / 3), startY - (mBgNotOptBitmap.getHeight() / 2), mPaint);
//                    mPaint.setColor(mDayNormalColor);
//                }else{
                // 点击过，绘制点击过的背景
//                    canvas.drawBitmap(mBgOptBitmap, startX- (mBgOptBitmap.getWidth() / 3), startY - (mBgOptBitmap.getHeight() / 2), mPaint);
//                    mPaint.setColor(mDayPressedColor);
//                }
                mPaintBlack.setColor(Color.WHITE);
                if(isShowCurDayPaint==1) {
                    // 绘制天数
                    if(day+1<10){
                        canvas.drawCircle(startX,startY-15,35,mPaintBackGroundBlue);
                    }else {
                        canvas.drawCircle(startX+6, startY - 15, 35, mPaintBackGroundBlue);
                    }
                    mPaintBlack.setTextSize(11* mMetrics.scaledDensity);
                    canvas.drawText(dayStr, startX-10, startY - 16, mPaintBlack);
                    Paint newPain=new Paint();
                    newPain.setColor(Color.WHITE);
                    newPain.setTextSize(8*mMetrics.scaledDensity);
                    if(mLinkedHashMap.get(getSelData(mSelYear, mSelMonth, mDays[row][column]))<10) {
                        if(day+1<10) {
                            canvas.drawText(mContext.getString(R.string.calendar_rice_num, mLinkedHashMap.get(getSelData(mSelYear, mSelMonth, mDays[row][column])) + ""), startX - 16, startY + 10, newPain);
                        }else{
                            canvas.drawText(mContext.getString(R.string.calendar_rice_num, mLinkedHashMap.get(getSelData(mSelYear, mSelMonth, mDays[row][column])) + ""), startX - 8, startY + 10, newPain);
                        }
                    }else{
                        if(day+1<10) {
                            canvas.drawText(mContext.getString(R.string.calendar_rice_num,mLinkedHashMap.get(getSelData(mSelYear, mSelMonth, mDays[row][column]))+""), startX-18, startY + 10, newPain);
                        }else{
                            canvas.drawText(mContext.getString(R.string.calendar_rice_num, mLinkedHashMap.get(getSelData(mSelYear, mSelMonth, mDays[row][column])) + ""), startX - 18, startY + 10, newPain);
                        }
                    }
                    Log.d("gg===========12=",mRiceKey+""+getSelData(mSelYear, mSelMonth, mDays[row][column]));
                    mRiceKey++;
                }else{
                    // 绘制天数
                    if(day+1<10){
                        canvas.drawCircle(startX+12,startY-15,40,mPaintBackGroundBlue);
                    }else {
                        canvas.drawCircle(startX + 28, startY - 15, 40, mPaintBackGroundBlue);
                    }
                    canvas.drawText(dayStr, startX, startY, mPaintBlack);
                }
            }else if(isShowCurDayPaint==1){
                canvas.drawCircle(startX+12,startY-15,35,mPaintBackGroundWhite);
                mPaintBlack.setColor(Color.BLACK);
                canvas.drawText(dayStr, startX, startY, mPaintBlack);
            }
            else{
                mPaint.setColor(mDayNotOptColor);
                canvas.drawText(dayStr, startX, startY, mPaintBlack);
            }
        }
    }
    public void setLongTime(int time){
        this.mLongTime=time;
    }


    private int downX = 0,downY = 0, upX=0,upY=0;
    private class DataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if(mBoolean) {
                        performClick();
                        onLongClick(downX,downY);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch(eventCode){
            case MotionEvent.ACTION_DOWN:
                mBoolean=true;
                dateStart=new Date(System.currentTimeMillis());
                downX = (int) event.getX();
                downY = (int) event.getY();
                Message message=new Message();
                message.what=0;
                DataHandler dataHandler=new DataHandler();
                dataHandler.sendMessageDelayed(message,mLongTime);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("gg============1234856","");
                break;
            case MotionEvent.ACTION_UP:
                dataEnd=new Date(System.currentTimeMillis());
                if(dataEnd.getTime()-dateStart.getTime()<mLongTime) {
                    mBoolean=false;
                    if (!mClickable) return true;
                     upX = (int) event.getX();
                     upY = (int) event.getY();
                        performClick();
                        onClick((upX + downX) / 2, (upY + downY) / 2);
                }
                break;
        }
        return true;
    }

    /**
     * 点击事件
     */
    private void onClick(int x, int y){
        int row = y / mRowSize;
        int column = x / mColumnSize;
        setSelYTD(mSelYear, mSelMonth, mDays[row][column]);
        if(isShowCurDayPaint==0) {
            // 判断是否点击过
            boolean isSelected = mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mSelDate));
            // 判断是否可以添加
//        boolean isCanAdd = mOptionalDates.contains(getSelData(mSelYear, mSelMonth, mSelDate));
            if (isSelected) {
                mSelectedDates.remove(getSelData(mSelYear, mSelMonth, mSelDate));
            }
//        else if(isCanAdd){
            else {
                mSelectedDates.add(getSelData(mSelYear, mSelMonth, mSelDate));
            }
            invalidate();
            if (mListener != null) {
                // 执行回调
                mListener.onClickDateListener(mSelYear, (mSelMonth + 1), mSelDate);
            }
        }else{
            if(mSelYear<mCurYear){
                return;
            }else {
                if(mSelYear==mCurYear) {
                    if (mSelMonth < mCurMonth) {
                        return;
                    } else {
                        if (mCurDate >= mDays[row][column] && mSelMonth == mCurMonth) {
                            return;
                        }
                    }
                }
            }
            if(mOptionalDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
                mOptionalDates.remove(getSelData(mSelYear, mSelMonth, mDays[row][column]));
                mLinkedHashMap.remove(getSelData(mSelYear, mSelMonth, mDays[row][column]));
            }else{
                mOptionalDates.add(getSelData(mSelYear, mSelMonth, mDays[row][column]));
                mLinkedHashMap.put(getSelData(mSelYear, mSelMonth, mDays[row][column]),1);
            }
            invalidate();
            if (mListener != null) {
                // 执行回调
                mListener.onClickDateListener(mSelYear, (mSelMonth + 1), mSelDate);
            }
        }
    }

    /**
     * 长按点击事件
     */
    private void onLongClick(int x, int y){
        int row = y / mRowSize;
        int column = x / mColumnSize;
        mCruRow=row;
        mColumn=column;
        setSelYTD(mSelYear, mSelMonth, mDays[row][column]);
        if(mLongClickListener != null){
            // 执行回调
            mLongClickListener.onLongClickDateListener(mSelYear, (mSelMonth + 1), mSelDate);
        }
    }


    public void setSelectData(int mSelYear, int mSelMonth,int mSelDate){
//        mSelectedDates.add(getSelData(mSelYear, mSelMonth, mSelDate));
        selectData=getSelData(mSelYear,mSelMonth-1,mSelDate);

        invalidate();
    }
    /**
     * 初始化列宽和高
     */
    private void initSize() {
        // 初始化每列的大小
        mColumnSize = getWidth() / NUM_COLUMNS;
        // 初始化每行的大小
        mRowSize = getHeight() / NUM_ROWS+20;
    }

    public void setAddMore(){
        if(!mOptionalDates.contains(getSelData(mSelYear, mSelMonth, mDays[mCruRow][mColumn]))) {
            mOptionalDates.add(getSelData(mSelYear, mSelMonth, mDays[mCruRow][mColumn]));
            invalidate();
        }
    }
    /**
     * 设置已选日期数据
     */
    public void setSelectedDates(List<String> dates){
        this.mSelectedDates = dates;
    }

    /**
     * 获取已选日期数据
     */
    public List<String> getSelectedDates(){
        return mSelectedDates;
    }

    /**
     * 设置日历是否可以点击
     */
    @Override
    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    /**
     * 设置年月日
     * @param year  年
     * @param month 月
     * @param date  日
     */
    private void setSelYTD(int year, int month, int date){
        this.mSelYear   =   year;
        this.mSelMonth  =   month;
        this.mSelDate   =   date;
    }

    /**
     * 设置上一个月日历
     */
    public void setLastMonth(){
        int year    =   mSelYear;
        int month   =   mSelMonth;
        int day     =   mSelDate;
        // 如果是1月份，则变成12月份
        if(month == 0){
            year = mSelYear-1;
            month = 11;
        }else if(DateUtils.getMonthDays(year, month) == day){
            //　如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month-1;
            day = DateUtils.getMonthDays(year, month);
        }else{
            month = month-1;
        }
        setSelYTD(year,month,day);

        invalidate();
    }

    /**
     * 设置下一个日历
     */
    public void setNextMonth(){
        int year    =   mSelYear;
        int month   =   mSelMonth;
        int day     =   mSelDate;
        // 如果是12月份，则变成1月份
        if(month == 11){
            year = mSelYear+1;
            month = 0;
        }else if(DateUtils.getMonthDays(year, month) == day){
            //　如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = DateUtils.getMonthDays(year, month);
        }else{
            month = month + 1;
        }
        setSelYTD(year,month,day);

        invalidate();
    }

    /**
     * 获取当前展示的年和月份
     * @return 格式：2016-06
     */
    public String getDate(){
        String data;
        if((mSelMonth + 1) < 10){
            data = mSelYear + "-0" + (mSelMonth + 1);
        }else{
            data = mSelYear + "-" + (mSelMonth + 1);
        }
        return data;
    }

    /**
     * 获取当前展示的日期
     * @return 格式：20160606
     */
    private String getSelData(int year, int month, int date){
        String monty, day;
        month = (month + 1);

        // 判断月份是否有非0情况
        if((month) < 10) {
            monty = "0" + month;
        }else{
            monty = String.valueOf(month);
        }
        // 判断天数是否有非0情况
        if((date) < 10){
            day = "0" + (date);
        }else{
            day = String.valueOf(date);
        }
        return year + monty + day;
    }

    private OnClickListener mListener;

    private OnLongClickListener mLongClickListener;

    public interface OnClickListener{
        void onClickDateListener(int year, int month, int day);
    }

    public interface OnLongClickListener{
        void onLongClickDateListener(int year, int month, int day);
    }

    /**
     * 设置点击回调
     */
    public void setOnClickDate(OnClickListener listener){
        this.mListener = listener;
    }

    /**
     * 设置长按点击回调
     */
    public void setOnLongClickDate(OnLongClickListener longClickListener){
        this.mLongClickListener = longClickListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        recyclerBitmap(mBgOptBitmap);
//        recyclerBitmap(mBgNotOptBitmap);
    }

    /**
     * 释放Bitmap资源
     */
    private void recyclerBitmap(Bitmap bitmap) {
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}

