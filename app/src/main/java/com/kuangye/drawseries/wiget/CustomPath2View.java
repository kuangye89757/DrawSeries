package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.kuangye.drawseries.R;

import static android.graphics.BlurMaskFilter.Blur.NORMAL;

/**
 * 自定义View--Path
 */
public class CustomPath2View extends View {

    private PathEffect dashPathEffect;
    private PathEffect cornerPathEffect;
    private PathEffect discretePathEffect;
    private PathEffect pathDashPathEffect;
    private PathEffect sumPathEffect;
    private PathEffect composePathEffect;
    private Bitmap mBatManBitmap;
    private String mText = "Hello HenCoder";

    /**
     * 抗锯齿
     */
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 矩形
     */
    private Path path1 = new Path();
    private Path path2 = new Path();
    private Path path3 = new Path();
    private Path path4 = new Path();
    private Path path5 = new Path();

    public CustomPath2View(Context context) {
        super(context);
        init();
    }

    public CustomPath2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPath2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBatManBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.batman);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //别漏写
        super.onDraw(canvas);

        /*********************setPathEffect设置图形的轮廓***************/
        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //DashPathEffect [虚线] 参数1:float{线长,线间距} 参数2:偏移量
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        dashPathEffect = new DashPathEffect(new float[]{10, 5}, 10);
        paint.setPathEffect(dashPathEffect);
        canvas.drawCircle(650, 350, 100, paint);

        //cornerPathEffect  [所有拐角变成圆角] 参数:圆弧半径
        path1.rLineTo(160, 250);
        path1.rLineTo(100, -150);
        path1.rLineTo(200, 180);
        path1.rLineTo(300, -150);
        path1.rLineTo(400, 200);
        paint.setColor(Color.RED);
        cornerPathEffect = new CornerPathEffect(60);
        paint.setPathEffect(cornerPathEffect);
        canvas.drawPath(path1, paint);

        //DiscretePathEffect  [随机偏移] 参数1:最大的段长 参数2:偏移量
        path2.rLineTo(160, 300);
        path2.rLineTo(100, -200);
        path2.rLineTo(200, 230);
        path2.rLineTo(300, -200);
        path2.rLineTo(400, 250);
        paint.setColor(Color.BLUE);
        discretePathEffect = new DiscretePathEffect(20, 5);
        paint.setPathEffect(discretePathEffect);
        canvas.drawPath(path2, paint);


        //PathDashPathEffect 使用Path图形来做Dash  St:TRANSLATE/ROTATE/MORPH
        path3.lineTo(20, 20);
        path3.rLineTo(-10, 20);
        path3.close();

        pathDashPathEffect = new PathDashPathEffect(path3, 40, 0, PathDashPathEffect.Style.TRANSLATE);
        path4.rLineTo(160, 350);
        path4.rLineTo(100, -250);
        path4.rLineTo(200, 280);
        path4.rLineTo(300, -250);
        path4.rLineTo(400, 300);
        paint2.setColor(Color.parseColor("#009F7D"));
        paint2.setPathEffect(pathDashPathEffect);
        canvas.drawPath(path4, paint2);

        //SumPathEffect 组合效果的PathEffect
        path5.rLineTo(160, 400);
        path5.rLineTo(100, -300);
        path5.rLineTo(200, 320);
        path5.rLineTo(300, -300);
        path5.rLineTo(400, 350);
        paint.setColor(Color.parseColor("#68217A"));
        sumPathEffect = new SumPathEffect(dashPathEffect,cornerPathEffect);
//        paint.setPathEffect(sumPathEffect);
//        canvas.drawPath(path5, paint);


        //ComposePathEffect  组合效果的PathEffect
        //先对目标Path使用一个PathEffect，改变后的Path再使用另一个PathEffect
        composePathEffect = new ComposePathEffect(dashPathEffect,cornerPathEffect);
        paint.setPathEffect(composePathEffect);
        canvas.drawPath(path5, paint);


        /*************************setShadowLayer***********************/
        //setShadowLayer(float radius, float dx, float dy, int shadowColor)

        //绘制内容加阴影 (在绘制层下方附加效果)
        paint3.setShadowLayer(10,0,0,Color.RED);
        paint3.setTextSize(64);
        canvas.drawText(mText,200,200,paint3);

        /*************************setMaskFilter***********************/
        /**
         * 构造方法：  在绘制层上方附加效果
         BlurMaskFilter(float radius, BlurMaskFilter.Blur style)

         参数：
         radius ：模糊的范围
         style ：模糊的类型  NORMAL/SOLID/INNER/OUTER
         */
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(20, NORMAL);
        paint4.setMaskFilter(blurMaskFilter);
        canvas.drawBitmap(mBatManBitmap,200,300,paint4);

        /**
         * 构造方法：  浮雕效果
         EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius)

         参数：
         direction ：一个3个元素的数组，指定了光源的方向
         ambient ：环境光的强度 0~1
         specular ：炫光的系数
         blurRadius  ：光线的范围
         */
        EmbossMaskFilter embossMaskFilter = new EmbossMaskFilter(new float[]{0, 1, 1}, 0.2f, 8, 10);
        paint4.setMaskFilter(embossMaskFilter);
        canvas.drawBitmap(mBatManBitmap,500,300,paint4);

    }
}
