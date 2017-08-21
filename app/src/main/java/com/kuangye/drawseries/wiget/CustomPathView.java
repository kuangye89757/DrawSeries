package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kuangye.drawseries.R;

/**
 * 自定义View--线条的使用
 */
public class CustomPathView extends View {

    /**
     * 抗锯齿
     */
    private Paint lovePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint trianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path lovePath = new Path();
    private Path rectPath = new Path();
    private Path linePath = new Path();
    private Path quadPath = new Path();
    private Path arcPath = new Path();
    private Path trianglePath = new Path();
    private Path circlePath = new Path();

    /**
     * 矩形
     */
    private RectF rectF = new RectF(100, 100, 200, 200);
    private RectF rectF1 = new RectF(200, 100, 300, 200);
    private RectF rectF2 = new RectF(100, 500, 500, 900);

    public CustomPathView(Context context) {
        super(context);
        init();
    }

    public CustomPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        lovePath.addArc(rectF, -225, 225);
        lovePath.arcTo(rectF1, -180, 225, false);
        lovePath.lineTo(200, 242);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //别漏写
        super.onDraw(canvas);

        /**绘制一个爱心*/
        lovePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        canvas.drawPath(lovePath, lovePaint);

        /**
         * 第一组： addXxx() ——添加子图形(封闭图形,除addPath之外)
         *      使用path的addXxx()的方法  加上canvas.drawPath 来绘制 (等同于canvas.drawXxx方法)
         */
        //绘制矩形
        rectPaint.setColor(Color.BLUE);
        rectPaint.setStyle(Paint.Style.FILL);//实心
        rectPath.addRect(350, 100, 600, 250, Path.Direction.CCW);//顺时针 (CW clockwise) 和逆时针 (CCW counter-clockwise)
        canvas.drawPath(rectPath, rectPaint);


        /**
         * 第二组：xxxTo() ——画线（直线或曲线） r开头表示相对
         */
        pathPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(10);

        //普通线条
        linePath.moveTo(100, 300);//定位起点
        linePath.lineTo(500, 400);//绝对
        linePath.rLineTo(100, 0);//相对
        canvas.drawPath(linePath, pathPaint);

        //贝塞尔曲线
        quadPath.moveTo(100, 300);//定位起点
        quadPath.quadTo(200, 400, 500, 450);//绝对
        quadPath.rQuadTo(500, 450, 100, 0);//相对
        pathPaint.setColor(Color.BLACK);
        canvas.drawPath(quadPath, pathPaint);


        /**绘制弧形*/
        arcPath.moveTo(100, 300);//定位起点
        arcPath.lineTo(200, 500);

        //在某个区域(100, 500, 500, 900)内画弧线 开始角度为-90度 根据x,y参考系在y轴负坐标到x正坐标
        //显示在android界面的位置 并扫描过90度 的弧线
//        arcPath.arcTo(rectF2, -90, 180, true);//startAngle开始角度 sweepAngle扫描过的角度  true为抬一下笔移动过去
//        arcPath.arcTo(rectF2, -90, 90, false);//startAngle开始角度 sweepAngle扫描过的角度  false为拖着笔过去
        arcPath.arcTo(rectF2, 0, 180, false);//从x轴正向到x轴负向  false为拖着笔过去
        arcPath.close();//封闭绘制(由当前位置向当前子图形的起点(100, 300)绘制一条直线)
        pathPaint.setColor(Color.BLUE);
        canvas.drawPath(arcPath, pathPaint);


        /**绘制三角形*/
        trianglePaint.setStyle(Paint.Style.FILL);//填充(Path会自动封闭)
        trianglePaint.setColor(Color.GREEN);
        trianglePath.moveTo(100,950);
        trianglePath.rLineTo(300,0);
        trianglePath.rLineTo(-200,100);
        canvas.drawPath(trianglePath,trianglePaint);


        //Path.FillType的用法,详见readme.txt
        circlePaint.setColor(Color.parseColor("#1EA78C"));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePath.addCircle(500,1000,100, Path.Direction.CCW);//逆时针绘制
        circlePath.addCircle(650,1000,100, Path.Direction.CW);//顺时针绘制
        circlePath.setFillType(Path.FillType.EVEN_ODD);
//        circlePath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
//        circlePath.setFillType(Path.FillType.WINDING);
        canvas.drawPath(circlePath,circlePaint);
    }
}
