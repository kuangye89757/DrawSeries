package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.kuangye.drawseries.R;

/**
 * 自定义View--Paint的详解
 */
public class CustomPaintView extends View {

    private Bitmap mBitmap;
    private Shader mShader;

    /**
     * 抗锯齿
     */
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomPaintView(Context context) {
        super(context);
        init();
    }

    public CustomPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.demo);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //别漏写
        super.onDraw(canvas);

        //drawBitmap
        canvas.drawBitmap(mBitmap, 700, 30, bitmapPaint);

        //drawRect
        paint.setColor(Color.parseColor("#009688"));
        canvas.drawRect(30, 30, 230, 180, paint);

        //drawLine
        paint.setARGB(100, 255, 0, 0);//setARGB(a,r,g,b)
        paint.setStrokeWidth(5);
        canvas.drawLine(300, 30, 450, 180, paint);

        //drawText
        paint.setColor(Color.parseColor("#E91E63"));
        paint.setTextSize(36);
        canvas.drawText("HenCoder", 500, 130, paint);

        /**************Shader着色器 (一套着色规则) 一般使用其子类************/
        /**
         * 构造方法：    线性渐变
             LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, Shader.TileMode tile) 。

           参数：
             x0 y0 x1 y1：渐变的两个端点的位置
             color0 color1 是端点的颜色
             tile：端点范围之外的着色规则
         */
        mShader = new LinearGradient(100, 100, 450, 450, Color.parseColor("#E91E63"),
                Color.parseColor("#0288E1"), Shader.TileMode.CLAMP);
        paint1.setShader(mShader);
        canvas.drawCircle(200, 400, 100, paint1);

        /**
         * 构造方法：    辐射渐变
             RadialGradient(float centerX, float centerY, float radius, int centerColor, int edgeColor, TileMode tileMode)。

           参数：
             centerX centerY：辐射中心的坐标
             radius：辐射半径
             centerColor：辐射中心的颜色
             edgeColor：辐射边缘的颜色
             tileMode：辐射范围之外的着色模式 (CLAMP\MIRROR\REPEAT)
         */
        mShader = new RadialGradient(450, 400, 100, Color.parseColor("#E91E63"),
                Color.parseColor("#0288E1"), Shader.TileMode.CLAMP);
        paint.setShader(mShader);
        canvas.drawCircle(450, 400, 100, paint);

        /**
         * 构造方法：    扫描渐变
             SweepGradient(float cx, float cy, int color0, int color1)

           参数：
             cx cy ：扫描的中心
             color0：扫描的起始颜色
             color1：扫描的终止颜色
         */
        mShader = new SweepGradient(700, 400, Color.parseColor("#E91E63"),
                Color.parseColor("#0288E1"));
        paint.setShader(mShader);
        canvas.drawCircle(700, 400, 100, paint);


        //重置Paint的所有属性,比new一个性能高
        paint.reset();

        //把src的所有属性全部复制过来
        paint1.set(paint);

        //批量设置flags相当于paint.setAntiAlias(true); paint.setDither(true);
        paint1.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);




    }
}
