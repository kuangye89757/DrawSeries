package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View--drawText
 */
public class CustomDrawTextView extends View {

    private String mText = "Hello HenCoder";

    /**
     * 抗锯齿
     */
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 矩形
     */
    private RectF rectF = new RectF(500, 200, 800, 500);

    /**
     * 路径
     */
    private Path path1 = new Path();
    private Path path2 = new Path();
    private Path path3 = new Path();
    private Path path4 = new Path();

    public CustomDrawTextView(Context context) {
        super(context);
        init();
    }

    public CustomDrawTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDrawTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //别漏写
        super.onDraw(canvas);

        /***********************drawText**********************/
        //drawXXX()都是以左上角作为基准点
        //drawText()是文字左下方比较接近的位置为基准点,而y值就是指定的基线位置
        paint.setColor(Color.parseColor("#F8AEB5"));
        paint.setTextSize(64);
        canvas.drawText(mText, 200, 100, paint);

    }
}
