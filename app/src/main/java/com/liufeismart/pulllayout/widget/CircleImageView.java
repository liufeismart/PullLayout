package com.liufeismart.pulllayout.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *
 * android circle imageView
 *
 * @author Block Cheng
 *
 */
public class CircleImageView extends ImageView {

    Path path;
    public PaintFlagsDrawFilter mPaintFlagsDrawFilter;// 毛边过滤
    Paint paint;
    private int strokeWidth=1;

    private int strokeColor =Color.YELLOW;

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public CircleImageView setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public CircleImageView setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }


    public void init() {
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0,
                Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(Color.WHITE);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        int radius = this.getWidth() / 2;
        Path path = new Path();
        path.addCircle(radius, radius, radius, Path.Direction.CW);
        canvas.clipPath(path); // 裁剪区域
        canvas.save();
        if (strokeWidth > 0) {
            canvas.drawColor(strokeColor);
            path.addCircle(radius - strokeWidth, radius - strokeWidth, radius
                    + strokeWidth, Path.Direction.CW);
            canvas.clipPath(path);
            canvas.save();
        }
        super.onDraw(canvas);
    }
}
