package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.kuangye.drawseries.R;

import static android.graphics.PorterDuff.Mode.OVERLAY;
import static android.graphics.PorterDuff.Mode.SRC_IN;

/**
 * 自定义View--Bitmap
 */
public class CustomBitmapView extends View {

    private ColorFilter redColorFilter;
    private ColorFilter greenColorFilter;
    private PorterDuffColorFilter mPorterDuffColorFilter;
    private Xfermode xfermode;
    private Bitmap mBatManBitmap;
    private Shader mShader1;
    private Shader mShader2;
    private Shader mShader3;

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
    private RectF rectF = new RectF(500, 200, 800, 500);
    private Path path1 = new Path();
    private Path path2 = new Path();
    private Path path3 = new Path();
    private Path path4 = new Path();

    public CustomBitmapView(Context context) {
        super(context);
        init();
    }

    public CustomBitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBitmapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBatManBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.batman);

        //LightingColorFilter--模拟简单的光照效果
        redColorFilter = new LightingColorFilter(0x00ffff, 0x000000);
        greenColorFilter = new LightingColorFilter(0x00ffff, 0x003000);

        //PorterDuffColorFilter--单色过滤
        mPorterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor("#F5B910"), OVERLAY);

        //PorterDuffXfermode
        xfermode = new PorterDuffXfermode(SRC_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //别漏写
        super.onDraw(canvas);

        /***********************ComposeShader混合着色器**********************/
        //ComposeShader混合两种相同Shader需要关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        /**
         * 构造方法：
         BitmapShader(Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)

         参数：
         bitmap：用来做模板的 Bitmap 对象
         tileX：横向的 TileMode
         tileY：纵向的 TileMode
         */
        //shader1
        mShader1 = new BitmapShader(mBatManBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //shader2
        mShader2 = new LinearGradient(0, 0, 0, 100, Color.TRANSPARENT,
                Color.parseColor("#0288E1"), Shader.TileMode.CLAMP);

        //PorterDuff.Mode---16种效果
        mShader3 = new ComposeShader(mShader1, mShader2, OVERLAY);
        paint.setShader(mShader3);
        canvas.drawCircle(100, 100, 100, paint);


        /****************************ColorFilter****************************/

        /**
         * LightingColorFilter(int mul, int add)
         *    mul用来和目标像素相乘,add用来和目标像素相加
         R' = R * mul.R / 0xff + add.R
         G' = G * mul.G / 0xff + add.GB' = B * mul.B / 0xff + add.B
         */
        //红色被移除
        paint1.setColorFilter(redColorFilter);
        canvas.drawBitmap(mBatManBitmap, 250, 0, paint1);

        //绿色被移除
        paint1.setColorFilter(greenColorFilter);
        canvas.drawBitmap(mBatManBitmap, 500, 0, paint1);

        /**
         * PorterDuffColorFilter 单色的 ColorFilter
         */
        paint1.setColorFilter(mPorterDuffColorFilter);
        canvas.drawBitmap(mBatManBitmap, 0, 250, paint1);

        /**
         * setXfermode  并使用离屏缓冲Canvas.saveLayer()
         */
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(350, 350, 120, paint2);//绘制下层图
        paint2.setXfermode(xfermode); //取绘制的图的交集部分，显示上层
        canvas.drawBitmap(mBatManBitmap, 250, 250, paint2);//绘制上层图
        paint2.setXfermode(null);// 用完及时清除 Xfermode
        canvas.restoreToCount(saved);


        /****************************线条形状****************************/
        //setStrokeWidth 设置线宽
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(1);//默认为0,canvas会将线条宽度默认设置为1 发际线模式
        canvas.drawCircle(100, 600, 100, paint3);

        //setStrokeCap  设置线头 BUTT/ROUND/SQUARE
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStrokeWidth(20);
        canvas.drawArc(rectF, 180, -180, false, paint3);

        //setStrokeJoin 设置线条拐角 ROUND/BEVEL/MITER
        paint3.setStrokeCap(Paint.Cap.SQUARE);
        paint3.setStrokeWidth(30);
        path1.moveTo(300, 550);
        path1.rLineTo(60, 0);
        path1.rLineTo(-30, 50);
        paint3.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path1, paint3);

        path2.moveTo(450, 550);
        path2.rLineTo(60, 0);
        path2.rLineTo(-30, 50);
        paint3.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path2, paint3);

        //setStrokeMiter 设置线条拐角MITER类型的最大角度
        path3.moveTo(600, 550);
        path3.rLineTo(60, 0);
        path3.rLineTo(-30, 50);
        paint3.setStrokeJoin(Paint.Join.MITER);
        paint3.setStrokeMiter(10);//角度越小越尖 过尖会自动转为BEVEL
        canvas.drawPath(path3, paint3);


    }
}
