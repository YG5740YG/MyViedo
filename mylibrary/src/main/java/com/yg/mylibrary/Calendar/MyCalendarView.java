package com.yg.mylibrary.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yg.mylibrary.R;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class MyCalendarView extends RelativeLayout implements View.OnClickListener{
    View topView;
    private TextView mTextSelectMonth;
    /**
     * 上一个月
     */
    private ImageButton mLastMonthView;
    /**
     * 下一个月
     */
    private ImageButton mNextMonthView;

    public OnListen mOnListen;

    public CalendarView mCalendarView;
    public MyCalendarView(Context context,int modelType) {
        super(context);
        inint(modelType);
    }

    public MyCalendarView(Context context, AttributeSet attrs,int modelType) {
        super(context, attrs);
        inint(modelType);
    }

    public MyCalendarView(Context context, AttributeSet attrs, int defStyleAttr,int modelType) {
        super(context, attrs, defStyleAttr);
        inint(modelType);
    }
    public void inint(int modelType){
        topView= LayoutInflater.from(getContext()).inflate(R.layout.calendar_top_view,null);
        mCalendarView=(CalendarView)topView.findViewById(R.id.calendar_view) ;
        mTextSelectMonth = (TextView)topView.findViewById(R.id.txt_select_month);
        mLastMonthView = (ImageButton)topView.findViewById(R.id.img_select_last_month);
        mNextMonthView = (ImageButton)topView.findViewById(R.id.img_select_next_month);
        mCalendarView.setShowCurDay(modelType).reDraw();
        mLastMonthView.setOnClickListener(this);
        mNextMonthView.setOnClickListener(this);
        addView(topView);
    }
    public CalendarView getCanlendarView(){
        return mCalendarView;
    }
    public TextView getTextSelectMonth(){
        return mTextSelectMonth;
    }
    public ImageButton getLastMonthView(){
        return mLastMonthView;
    }
    public ImageButton getNextMonthView(){
        return mNextMonthView;
    }
    public void  setOnListen(OnListen onListen){
        mOnListen=onListen;
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_select_last_month) {
            mOnListen.onLastMonthListener();
        } else if (i == R.id.img_select_next_month) {
          mOnListen.onNextMonthListener();
        }
    }
   public interface OnListen{
       void onLastMonthListener();
       void onNextMonthListener();
    }
}
