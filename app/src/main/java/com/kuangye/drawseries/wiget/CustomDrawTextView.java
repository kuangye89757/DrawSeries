package com.kuangye.drawseries.wiget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

/**
 * 自定义View--drawText
 */
public class CustomDrawTextView extends View {

    private String mText = "Hello HenCoder";

    /**
     * 抗锯齿
     */
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
        //别漏写
        super.onDraw(canvas);

        /***************************drawText************************/
        //drawXXX()都是以左上角作为基准点
        //drawText()是文字左下方比较接近的位置(首字左空隙的左边)为基准点,而y值就是指定的基线位置
        paint.setColor(Color.parseColor("#F8AEB5"));
        paint.setTextSize(64);
        canvas.drawText(mText, 200, 100, paint);

        /**
         * 构造方法： 截取文字及文字方向
         drawTextRun(CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint paint)

         参数：
         text：要绘制的文字
         start：从那个字开始绘制
         end：绘制到哪个字结束
         contextStart：上下文的起始位置。contextStart 需要小于等于 start
         contextEnd：上下文的结束位置。contextEnd 需要大于等于 end
         x：文字左边的坐标
         y：文字的基线坐标
         isRtl：是否是 RTL（Right-To-Left，从右向左）
         */
        canvas.drawTextRun(mText, 5, 10, 0, mText.length() - 1, 200, 300, false, paint);


        /**
         * 构造方法： 沿path进行文字的显示
         drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint)

         参数：
         text：文字
         path：路径
         hOffset：文字相对于Path的水平偏移量
         vOffset：文字相对于Path的竖直偏移量
         */
        paint.reset();
        path1.moveTo(20, 50);
        path1.rLineTo(160, 250);
        path1.rLineTo(100, -150);
        path1.rLineTo(200, 180);
        path1.rLineTo(300, -150);
        path1.rLineTo(400, 200);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        canvas.drawPath(path1, paint);

        paint.setColor(Color.parseColor("#185288"));
        paint.setTextSize(65);
        canvas.drawTextOnPath(mText, path1, 0, 0, paint);


        /**
         * StaticLayout 可以为文字设置宽度上限来让文字自动换行，也会在\n处主动换行
         * 构造方法:
         *  StaticLayout(CharSequence source, TextPaint paint, int width, Layout.Alignment align, float spacingmult, float spacingadd, boolean includepad)
         *
         * 参数：
         *   width 是文字区域的宽度，文字到达这个宽度后就会自动换行；
             align 是文字的对齐方向；
             spacingmult 是行间距的倍数，通常情况下填1就好；
             spacingadd 是行间距的额外增加值，通常情况下填 0 就好；
             includeadd 是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界
         */
        canvas.save();
        canvas.translate(50, 300);
        textPaint.setColor(Color.parseColor("#FF7534"));
        textPaint.setTextSize(65);
        String text1 = "Lorem Ipsum is simply dummy text of the printing \n and typesetting industry.";
        StaticLayout staticLayout = new StaticLayout(text1, textPaint, 600, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        staticLayout.draw(canvas);
        canvas.restore();


        /***************************Paint对文字绘制的辅助************************/
        //setTextSize(float textSize) 设置文字大小
        //setTypeface(Typeface typeface) 设置字体
        //setFakeBoldText(boolean fakeBoldText) 设置是否描粗
        //setStrikeThruText(boolean strikeThruText) 设置是否加删除线
        //setUnderlineText(boolean underlineText) 设置是否加下划线
        //setTextSkewX(float skewX) 设置文字倾斜度
        //setTextScaleX(float scaleX) 设置放缩
        //setLetterSpacing(float letterSpacing) 设置字符间距
        //setFontFeatureSettings(String settings) 使用css方式设置文字
        paint.setColor(Color.parseColor("#4695EE"));
        paint.setTextSize(65);
        paint.setTypeface(Typeface.SERIF);
        paint.setFakeBoldText(true);
        paint.setStrikeThruText(true);
        paint.setUnderlineText(true);
        paint.setTextSkewX(-0.5f);
        paint.setTextScaleX(1.2f);
        paint.setLetterSpacing(0f);
        paint.setFontFeatureSettings("smcp");
        canvas.drawText(mText, 400, 300, paint);

        //setTextAlign 设置文字的对齐方式
        //setTextLocale 设置语言区域
        paint1.setColor(Color.BLACK);
        paint1.setTextSize(65);
        paint1.setTextAlign(Paint.Align.CENTER);
        paint1.setTextLocale(Locale.CHINA);
        canvas.drawText("简体中文", 400, 900, paint1);

        paint1.setTextAlign(Paint.Align.LEFT);
        paint1.setTextLocale(Locale.TRADITIONAL_CHINESE);
        paint1.setColor(Color.parseColor("#881F4977"));
        canvas.drawText("简体中文", 400, 900 + 50, paint1);

        paint1.setTextAlign(Paint.Align.RIGHT);
        paint1.setColor(Color.parseColor("#88A2DDFA"));
        canvas.drawText("Hello", 400, 900 + 100, paint1);





    }
}
