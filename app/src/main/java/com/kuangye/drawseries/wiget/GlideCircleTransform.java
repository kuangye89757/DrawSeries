package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class GlideCircleTransform extends BitmapTransformation {
    private Paint mPaint;
    private Paint mBorderPaint;
    private float mBorderWidth;
    private int mBgColor;

    public GlideCircleTransform(Context context) {
        this(context, 0, 0);
    }

    public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
        this(context, borderWidth, borderColor, 0);
    }

    public GlideCircleTransform(Context context, int borderWidth, int borderColor, int bgColor) {
        super(context);

        if (borderWidth != 0) {
            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
        }
        mBgColor = bgColor;

        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }

        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Shader shader1 = new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (mBgColor != 0) {
            //背景色
            Bitmap result1 = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            result1.eraseColor(mBgColor);
            Shader shader2 = new BitmapShader(result1, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            mPaint.setShader(new ComposeShader(shader2, shader1, PorterDuff.Mode.SRC_OVER));
        } else {
            mPaint.setShader(shader1);
        }


        float r = size / 2f;
        Canvas canvas = new Canvas(result);
        canvas.drawCircle(r, r, r, mPaint);
        if (mBorderWidth != 0) {
            //边框
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
