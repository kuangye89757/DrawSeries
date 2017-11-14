package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author shijie9
 * @date 2017/11/10
 */
public class MarqueeTextView extends AppCompatTextView implements Runnable, View.OnTouchListener {
    public MarqueeTextView(Context context) {
        super(context);
    }

    private static final String TAG = "MarqueeTextView";
    // 设置跑马灯重复的次数，次数
    private int circleTimes = Integer.MAX_VALUE;
    //记录已经重复了多少遍
    private int hasCircled = 0;
    private int currentScrollPos = 0;
    // 跑马灯走一遍需要的时间（秒数）
    private int circleSpeed = 100;
    // 文字的宽度
    private int textWidth = 0;

    private boolean isMeasured = false;
    private boolean flag = false;

    private static final int STEP = 20;//步长

    // 构造方法
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }

    /**
     * 画笔工具
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasured) {
            getTextWidth();
            isMeasured = true;
        }
    }

    @Override
    public void setVisibility(int visibility) {
        // 二次进入时初始化成员变量
        flag = false;
        isMeasured = false;
        this.hasCircled = 0;
        super.setVisibility(visibility);
    }

    /**
     * 获取文本显示长度
     */
    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        Log.i(TAG, str);
        if (TextUtils.isEmpty(str)) {
            textWidth = 0;
        }
        textWidth = (int) paint.measureText(str);
    }

    @Override
    public void run() {
        if (textWidth != 0) {
            // 起始滚动位置
            currentScrollPos += STEP;
            scrollTo(currentScrollPos, 0);
            // 判断滚动一次
            if (currentScrollPos >= textWidth) {
                // 从屏幕右侧开始出现
                currentScrollPos = -this.getWidth();
                //记录的滚动次数大设定的次数代表滚动完成，这个控件就可以隐藏了
                if (hasCircled >= this.circleTimes) {
                    this.setVisibility(View.GONE);
                    flag = true;
                }
                hasCircled += 1;
            }

            if (!flag) {
                // 滚动时间间隔
                postDelayed(this, circleSpeed);
            }
        }
    }

    public void startScrollShow() {
        if (this.getVisibility() == View.GONE) {
            this.setVisibility(View.VISIBLE);
        }
        this.removeCallbacks(this);
        post(this);
    }

    private void stopScroll() {
        this.removeCallbacks(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_SCROLL:
                stopScroll();
                break;
            default:
                startScrollShow();
                break;
        }
        return true;
    }

}
