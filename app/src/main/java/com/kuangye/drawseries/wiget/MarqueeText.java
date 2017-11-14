package com.kuangye.drawseries.wiget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * @author shijie9
 * @date 2017/11/14
 */

public class MarqueeText extends View {
    private Paint secondPaint;
//    private String title = "晚上6点,银海国际酒店的三楼,600多平方的伊甸园里,布置简约而又充满的年轻点气息,";
        private String title = "晚上6点,";
    private int strWidth;
    private static final int STEP = 5; //移动步长
    private static final int X_OFFSET = 100; //X偏移量
    private static final int SPACE_TIME = 50; //间隔时间

    private int preX = 0; //前一个text的位移
    private int nextX = 0;
    private boolean isScrollOut = true;//滚出
    private boolean isScrollIn = false;//滚进
    private int screenWidth;
    private boolean isMeasure;
    private Paint mPaint;
    private boolean isFirst;
    private boolean flag;
    private int preDistance;//前一个text移动过的距离
    private int nextDistance;//后一个text移动过的距离
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (isScrollOut) {
                    //滚出操作
                    preX -= STEP;//前一个text位置逐级递减
                    preDistance += STEP;

                    if (strWidth >= screenWidth) {
                        //文字宽度大于屏宽 若前一个text移动过的距离 大于文字长度 - 偏移量 且 小于文字宽度时 开始滚进
                        if (preDistance >= Math.abs(strWidth - X_OFFSET) && preDistance < strWidth && !isFirst) {
                            isFirst = true;
                            isScrollIn = true;
                            isScrollOut = true;
                            nextX = screenWidth;//下一个text初始位置
                        }
                    } else {
                        //否则前一个text位置滚动到偏移量位置 开始滚进
                        if (Math.abs(preX) <= X_OFFSET && !isFirst) {
                            isFirst = true;
                            isScrollIn = true;
                            isScrollOut = true;
                            nextX = screenWidth;//下一个text初始位置
                        }
                    }

                    if (strWidth >= screenWidth) {
                        //文字宽度大于屏宽 前一个text移动过的距离大于文字宽度时 不再滚出
                        if (preDistance >= strWidth) {
                            preDistance = 0;
                            isFirst = false;
                            isScrollOut = false;
                            flag = false;
                        }
                    } else {
                        //否则前一个text位置到头时 不再滚出
                        if (preX <= 0) {
                            isFirst = false;
                            isScrollOut = false;
                            flag = false;
                        }
                    }

                    if (isScrollIn) {
                        //滚进操作
                        nextX -= STEP;//下一个text位置逐级递减
                        nextDistance += STEP;
                    }
                } else {
                    //滚进操作
                    nextX -= STEP;
                    nextDistance += STEP;

                    if (strWidth >= screenWidth) {
                        //文字宽度大于屏宽 若后一个text移动过的距离 大于文字长度 - 偏移量 且 小于屏宽时 开始滚进
                        if (nextDistance >= Math.abs(strWidth - X_OFFSET) && nextDistance < strWidth && !flag) {
                            flag = true;
                            isScrollOut = true;
                            isScrollIn = true;
                            preX = screenWidth;//前一个text初始位置
                        }
                    } else {
                        //否则前一个text位置滚动到偏移量位置 开始滚进
                        if (Math.abs(nextX) <= X_OFFSET && !flag) {
                            flag = true;
                            isScrollOut = true;
                            isScrollIn = true;
                            preX = screenWidth;//下一个text初始位置
                        }
                    }

                    if (isScrollOut) {
                        //滚出操作
                        preX -= STEP;//前一个text位置逐级递减
                        preDistance += STEP;
                    }
                }
                invalidate();
                mHandler.sendEmptyMessageDelayed(1, SPACE_TIME);
            }
        }
    };

    public void clearHandler() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    public MarqueeText(Context context) {
        this(context, null);
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.YELLOW);

        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setTextSize(30);
        secondPaint.setColor(Color.YELLOW);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();

        preX = screenWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(title)) {
            if (isScrollOut) {
                //滚出绘制
                canvas.drawText(title, preX, 24, mPaint);
            }

            if (isScrollIn) {
                //滚进绘制
                canvas.drawText(title, nextX, 24, secondPaint);
            }
        }

        //测量文字宽度
        if (!isMeasure) {
            strWidth = (int) mPaint.measureText(title);
            isMeasure = true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(screenWidth, dip2px(getContext(), 40));
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            mHandler.sendEmptyMessage(1);
        } else {
            mHandler.removeCallbacks(null);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}