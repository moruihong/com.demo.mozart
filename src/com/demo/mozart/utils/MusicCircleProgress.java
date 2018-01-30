package com.demo.mozart.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MusicCircleProgress extends View {
    private RectF mSectionRect = new RectF();
    protected float mSectionWidth = 42;
    protected float mSectionHeight = 8;
    protected float mRadius;

    protected int mMaxProgress;
    protected int mProgress;

    protected float mCenterX;
    protected float mCenterY;

    private Paint mPaint;
    private int mColor1;
    private int mColor2;
    private int mInactiveColor;
    float[] hsvColorA;
    float[] hsvColorB;
    {
        mMaxProgress = 60;
        mProgress = 0;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mColor1 = Color.parseColor("#fff73a5e");
        mColor2 = Color.parseColor("#ffff9767");
        mInactiveColor = Color.parseColor("#ffcccccc");

        hsvColorA = new float[3];
        hsvColorA[0] = Color.red(mColor1);
        hsvColorA[1] = Color.green(mColor1);
        hsvColorA[2] = Color.blue(mColor1);

        hsvColorB = new float[3];
        hsvColorB[0] = Color.red(mColor2);
        hsvColorB[1] = Color.green(mColor2);
        hsvColorB[2] = Color.blue(mColor2);

        mPaint.setColor(mColor1); // Set default
    }

    public MusicCircleProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MusicCircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicCircleProgress(Context context) {
        super(context);
    }

    private void updateDimensions(int width, int height) {
        // Update center position
        mCenterX = width / 2.0f;
        mCenterY = height / 2.0f;

        // Find shortest dimension
        int diameter = Math.min(width, height);

        float outerRadius = diameter / 2;
        float sectionHeight = 42;// outerRadius * mRingBias;
        float sectionWidth = 9;// sectionHeight / mSectionRatio;

        mRadius = outerRadius - sectionHeight / 2;
        mSectionRect.set(-sectionWidth / 2, -sectionHeight / 2, sectionWidth / 2, sectionHeight / 2);
        mSectionHeight = sectionHeight;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (width > height) {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
        updateDimensions(getWidth(), getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateDimensions(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Center our canvas
        canvas.translate(mCenterX, mCenterY);

        float rotation = 360.0f / mMaxProgress;
        for (int i = 0; i < mMaxProgress; ++i) {
            canvas.save();

            canvas.rotate(i * rotation);
            canvas.translate(0, -mRadius);

            if (i < mProgress) {
                float bias = (float) i / (float) (mMaxProgress - 1);
                int color = interpolateColor(mColor1, mColor2, bias);
                mPaint.setColor(color);
            } else {
                canvas.scale(1.0f, 1.0f);
                mPaint.setColor(mInactiveColor);
            }

            canvas.drawRect(mSectionRect, mPaint);
            canvas.restore();
        }
        super.onDraw(canvas);
    }

    private float interpolate(float a, float b, float bias) {
        return (a + ((b - a) * bias));
    }

    private int interpolateColor(int colorA, int colorB, float bias) {
        int[] hsvColor11 = new int[3];
        hsvColor11[0] = (int) interpolate(hsvColorA[0], hsvColorB[0], bias);
        hsvColor11[1] = (int) interpolate(hsvColorA[1], hsvColorB[1], bias);
        hsvColor11[2] = (int) interpolate(hsvColorA[2], hsvColorB[2], bias);

        // NOTE For some reason the method HSVToColor fail in edit mode. Just
        // use the start color for now
        if (isInEditMode())
            return colorA;

        return Color.argb(0xff, hsvColor11[0], hsvColor11[1], hsvColor11[2]);
    }

    /**
     * Get max progress
     *
     * @return Max progress
     */
    public float getMax() {
        return mMaxProgress;
    }

    /**
     * Set max progress
     *
     * @param max
     */
    public void setMax(int max) {
        int newMax = Math.max(max, 1);
        if (newMax != mMaxProgress)
            mMaxProgress = newMax;

        updateProgress(mProgress);
        invalidate();
    }

    /**
     * Get Progress
     *
     * @return progress
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * Set progress
     *
     * @param progress
     */
    public void setProgress(int progress) {
        updateProgress(progress);
    }

    /**
     * Update progress internally. Clamp it to a valid range and invalidate the view if necessary
     *
     * @param progress
     * @return true if progress was changed and the view needs an update
     */
    public boolean updateProgress(int progress) {
        // Clamp progress
        progress = Math.max(0, Math.min(mMaxProgress, progress));

        if (progress != mProgress) {
            mProgress = progress;
            invalidate();
            return true;
        }

        return false;
    }
}
