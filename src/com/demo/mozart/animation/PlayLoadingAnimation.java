
package com.demo.mozart.animation;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class PlayLoadingAnimation {
    private static final String TAG = PlayLoadingAnimation.class.getSimpleName();
    private static final boolean DEBUG = true;
    private static final float CIRCUMFERENCE = 359.0f;
    private static final int ROTATION_DURATION = 1000;

    private LinearInterpolator mLinearInterpolator = null;
    private float mRotateStart = 0f;
    private float mRotateEnd = 0f;
    private ValueAnimator mRotateAnim = null;
    private View mRotateImage = null;

    public PlayLoadingAnimation() {}

    public void addView(View view) {
        mRotateImage = view;
    }

    public void startAlbumRotate() {
        if (mRotateImage == null) {
            return;
        }

        endAlbumRotate();

        mRotateEnd = mRotateStart + CIRCUMFERENCE;
        mRotateAnim = ValueAnimator.ofFloat(0f, 1f).setDuration(ROTATION_DURATION);
        mRotateAnim.setStartDelay(300);
        mRotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        if (mLinearInterpolator == null) {
            mLinearInterpolator = new LinearInterpolator();
        }
        mRotateAnim.setInterpolator(mLinearInterpolator);

        mRotateAnim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float b = (Float) animation.getAnimatedValue();
                final float a = 1f - b;
                if (mRotateImage != null) {
                    mRotateImage.setRotation(a * mRotateStart + b * mRotateEnd);
                }
            }
        });

        mRotateAnim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                if (DEBUG) {
                    Log.d(TAG, "onAnimationEnd");
                }

                if (mRotateImage != null) {
                    mRotateStart = mRotateImage.getRotation();
                }
            }

            @Override
            public void onAnimationStart(android.animation.Animator animation) {
                if (DEBUG) {
                    Log.d(TAG, "onAnimationStart");
                }
            }
        });

        mRotateAnim.start();
    }

    public void resetAlbumAnimtion() {
        if (mRotateImage != null) {
            mRotateImage.setRotation(0.0f);
            mRotateStart = 0.0f;
        }
    }

    public void endAlbumRotate() {
        if (DEBUG) {
            Log.d(TAG, "clearAlbumRotate");
        }
        if (mRotateAnim != null) {
            mRotateAnim.end();
            mRotateAnim = null;
        }
    }

    public void clearView() {
        mRotateImage = null;
    }
}
