/*
 * Class Name: FreeDraw
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */
package com.cmput301f18t25.healthx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ThumbnailUtils;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * This is the class to free draw and erase
 *
 *
 * @author Sandy
 * @version 1.0
 *
 */

public class FreeDraw extends View {

    Bitmap mBitmap;
    Bitmap newBitmap;

    Canvas mCanvas;
    Canvas qCanvas;
    Paint mPaint;
    public int width;
    public int height;

    private Path mPath;
    private Paint mBitmapPaint;
    Context context;
    private Paint circlePaint;
    private Path circlePath;

    public FreeDraw(Context context) {
        super(context);
        this.context = context;


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);
        setDrawingCacheEnabled( true );
    }

    public FreeDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);

        setDrawingCacheEnabled( true );

    }

    public void setCanvasBitmap(Bitmap bitmap) {


        newBitmap = bitmap;

        qCanvas = new Canvas(newBitmap);
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        mBitmap = Bitmap.createBitmap(newBitmap.getWidth(), newBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        qCanvas = new Canvas(newBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap( newBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath( mPath,  mPaint);
        canvas.drawPath( circlePath,  circlePaint);

    }
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;

            circlePath.reset();
            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
        }
    }
    private void touch_up() {
        mPath.lineTo(mX, mY);
        circlePath.reset();

        qCanvas.drawPath(mPath,  mPaint);

        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }


    public Bitmap getBitmap()
    {

        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);


        return bmp;
    }

    public void clear(){
        setDrawingCacheEnabled(false);
        // don't forget that one and the match below,
        // or you just keep getting a duplicate when you save.

        setCanvasBitmap(newBitmap);
        invalidate();

        setDrawingCacheEnabled(true);


    }

    public Bitmap saveDrawing()
    {
        /** NOTE that's an incredibly useful trick for cropping/resizing squares
         * while handling all memory problems etc
         * http://stackoverflow.com/a/17733530/294884
         * you can now save the bitmap to a file, or display it in an ImageView: */
        Bitmap userBitmap = getDrawingCache();

        userBitmap = ThumbnailUtils.extractThumbnail(userBitmap, 500, 500);

        return  userBitmap;

    }
}
