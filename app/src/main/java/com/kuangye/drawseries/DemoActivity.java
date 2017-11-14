package com.kuangye.drawseries;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kuangye.drawseries.wiget.GlideCircleTransform;

public class DemoActivity extends AppCompatActivity {

    private ImageView campImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        campImageView = (ImageView) findViewById(R.id.btn);


        int borderColor = ContextCompat.getColor(this, R.color.interact_head_border);
        int bgColor = ContextCompat.getColor(this, R.color.colorAccent);
        GlideCircleTransform circleTransform = new GlideCircleTransform(this, 1, borderColor, bgColor);

        Glide.with(DemoActivity.this)
                .load("http://www.sinaimg.cn/ty/nba/player/NBA_1x1/6c60282d-165a-4cba-8e5a-4f2d9d4c5905.png")
                .asBitmap()
                .dontAnimate()
                .transform(circleTransform)
                .into(campImageView);
    }
}
