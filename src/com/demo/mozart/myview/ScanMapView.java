
package com.demo.mozart.myview;

import com.demo.mozart.R;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ScanMapView extends View {

    private final static String TAG = "ScanMapView";
    private float height;
    Paint paint;
    protected boolean isRunning = true;
    private int color;
    public static int COLOR_GREEN = Color.parseColor("#ff0ba959");// Color.parseColor("#ff0f9862");
    public static int COLOR_YELLOW = Color.parseColor("#ffcb8800");
    public static int COLOR_RED = Color.parseColor("#ffc54900");
    private Bitmap mLineBitmap;
    private float lineY;
    private Path mPath;
    private Bitmap mBgBitmap;
    private boolean mIsShowMap = true;
    private ValueAnimator mValueAnimator;

    public ScanMapView(Context context) {
        super(context);
        initView();
    }

    public ScanMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ScanMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        paint = new Paint();
        color = COLOR_GREEN;
        paint.setColor(color);
        paint.setAlpha(255);
        height = getResources().getDimension(R.dimen.height); // 1080p,�����ֱ��ʵĶ�Ҫ����
        mPath = new Path();
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public void setMap(Drawable drawable) {
        mBgBitmap = drawableToBitmap(drawable);
    }

    public void setShowMap(boolean isShow) {
        mIsShowMap = isShow;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = getLeft();
        int right = getRight();
        int top = getTop();
        int bottom = getBottom();
        paint.reset();
        paint.setColor(color);

        if (mIsShowMap) {
            int count = 6;
            for (int i = 0; i < count; i++) {
                canvas.save();
                mPath.reset();
                mPath.addRect(left, lineY + getLineBitmap().getHeight() * i / count, right, lineY
                        + getLineBitmap().getHeight() * (i + 1) / count, Path.Direction.CCW);
                paint.setAlpha(255 - i * 50);
                canvas.clipPath(mPath);
                canvas.drawBitmap(getBgBitmap(), left, top, paint);
                canvas.restore();
            }
        }

        paint.setAlpha(255);
        canvas.drawBitmap(getLineBitmap(), left, lineY, paint);

    }

    public void startLineAnimation() {
        // ValueAnimator anim = ValueAnimator.ofFloat(height, -bitmap.getHeight());
        mValueAnimator = ValueAnimator.ofFloat(height, -(getLineBitmap().getHeight() / 3));
        mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator anim) {
                lineY = (Float) anim.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.start();
    }

    public void stopLineAnimation() {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            if (isInTouchRect(x, y)) {
                setPressed(true);
            }
            break;
        case MotionEvent.ACTION_MOVE:
            if (isInTouchRect(x, y)) {
            }
            break;
        case MotionEvent.ACTION_UP:
            if (isInTouchRect(x, y) && isPressed()) {
            }
            setPressed(false);
            break;
        }
        return true;
    }

    public boolean isInTouchRect(int x, int y) {

        return true;
    }

    public void onPause() {
        isRunning = false;
    }

    public void onResume() {
        isRunning = true;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    private Bitmap getLineBitmap() {
        if (mLineBitmap == null) {
            mLineBitmap = drawableToBitmap(getResources().getDrawable(R.drawable.scanline));
        }
        return mLineBitmap;
    }

    private Bitmap getBgBitmap() {
        if (mBgBitmap == null) {
            mBgBitmap = drawableToBitmap(getResources().getDrawable(R.drawable.map));
        }
        return mBgBitmap;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mLineBitmap == null) {
            mLineBitmap = drawableToBitmap(getResources().getDrawable(R.drawable.scanline));
        }
        /*
         * if (map == null) { map = drawableToBitmap(getResources().getDrawable(R.drawable.map)); }
         */
        lineY = height - mLineBitmap.getHeight();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mLineBitmap != null) {
            mLineBitmap.recycle();
            mLineBitmap = null;
        }
        if (mBgBitmap != null) {
            mBgBitmap.recycle();
            mBgBitmap = null;
        }
    }
}
