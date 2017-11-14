package com.kuangye.drawseries.wiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.kuangye.drawseries.R;

/**
 * Created by shijie9 on 2017/8/30.
 */

public class CampImageView extends View {

    private Paint paint;
    private Drawable mCampBg;
    private Bitmap mCampBitMap;
    private Bitmap mTeamBitMap;

    public CampImageView(Context context) {
        this(context, null, 0);
    }

    public CampImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CampImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyConfig(context, attrs);
        init();
    }

    /**
     * 初始化配置
     * @param context
     * @param attrs
     */
    private void applyConfig(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CampImageView);

        /**阵营背景图*/
        mCampBg = a.getDrawable(R.styleable.CampImageView_camp_bg);
        mCampBitMap = ((BitmapDrawable) mCampBg).getBitmap();
        a.recycle();

    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTeamBitMap = ((BitmapDrawable) getResources().getDrawable(
                R.mipmap.ic_ad_focus)).getBitmap();//默认球队图
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mCampBitMap, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(mTeamBitMap, Math.abs(mCampBitMap.getWidth() - mTeamBitMap.getWidth()) / 2,
                Math.abs(mCampBitMap.getHeight() - mTeamBitMap.getHeight()) / 2, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }


    /**
     * 设置阵容球队
     */
    public void setTeamBitMap(Bitmap teamBitMap) {
        this.mTeamBitMap = teamBitMap;
        invalidate();
    }
}
