package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kuangye.drawseries.R;

/**
 * 自定义View--形状的使用
 */
public class CustomShapeView extends View {

    /**
     * 抗锯齿
     */
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 矩形区域 (用左上和右下两个坐标点表示)
     *      Rect是使用int类型作为数值
     *      RectF是使用float类型作为数值
     */
    private RectF rectF = new RectF(500, 0, 800, 200);
    private RectF rectF1 = new RectF(100, 850, 400, 1000);
    private RectF rectF2 = new RectF(500, 850, 800, 1000);
    private RectF rectF3 = new RectF(200, 1100, 350, 1200);

    /**
     * 点集合
     */
    float[] points = {0, 0, 0, 100, 700, 100, 800, 200, 700, 200, 800, 666, 666, 666, 666};

    /**
     * 线集合
     */
    float[] lines = {0, 0, 500, 550, 550, 650, 545, 648, 700, 550, 666, 666, 666, 666};

    public CustomShapeView(Context context) {
        super(context);
    }

    public CustomShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //别漏写
        super.onDraw(canvas);

        /**绘制圆*/
        circlePaint.setColor(Color.RED);//设置颜色
        canvas.drawCircle(200, 100, 100, circlePaint);

        /**绘制椭圆*/
        canvas.drawOval(rectF, circlePaint);

        /**绘制矩形*/
        rectPaint.setColor(Color.BLUE);
        rectPaint.setStyle(Paint.Style.FILL);//实心
        canvas.drawRect(100, 300, 400, 500, rectPaint);//四点坐标,并非距离

        rectPaint.setStyle(Paint.Style.STROKE);//空心
        canvas.drawRect(500, 300, 800, 500, rectPaint);

        /**绘制点*/
        pointPaint.setStrokeCap(Paint.Cap.ROUND);//圆点
        pointPaint.setStrokeWidth(10);//宽度为10
        canvas.drawPoint(100, 550, pointPaint);

        /**绘制线*/
        linePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        linePaint.setStrokeWidth(10);
        canvas.drawLine(100, 650, 400, 550, linePaint);//x:100-->400 y:650-->550

        /**绘制一系列点*/
        pointPaint.setColor(Color.CYAN);
        pointPaint.setStrokeWidth(20);//宽度为20
        canvas.drawPoints(points, 3, 8, pointPaint);//从第3个之后的8个坐标构成的(x,y)点

        /**绘制一个对勾*/
        canvas.drawLines(lines, 2, 8, linePaint);//从第2个之后的8个坐标构成的(x,y)点连成的线

        /**绘制圆角矩形*/
        rectPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        rectPaint.setStyle(Paint.Style.FILL);//实心
        canvas.drawRoundRect(rectF1, 50, 50, rectPaint);

        rectPaint.setStyle(Paint.Style.STROKE);//空心
        canvas.drawRoundRect(rectF2, 20, 20, rectPaint);


        /**绘制扇形*/
        arcPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPurple));
        arcPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rectF3,-110,100,true,rectPaint);
    }
}
