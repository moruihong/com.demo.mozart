
package com.demo.mozart.animation;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class PlayLoadingAnimation1 {
    private static final String TAG = PlayLoadingAnimation1.class.getSimpleName();
    private static final boolean DEBUG = true;
    private static final int ROTATION_DURATION = 2000;

    private LinearInterpolator mLinearInterpolator = null;
    private ValueAnimator mRotateAnim = null;
    private View mRotateImage = null;
    private int mScreenWith;

    public PlayLoadingAnimation1() {

    }

    public void addView(View view, int with) {
        mRotateImage = view;
        mScreenWith = with;
    }

    public void startAlbumRotate() {
        Log.d(TAG, "startAlbumRotate");
        if (mRotateImage == null || mScreenWith == 0) {
            return;
        }

        endAlbumRotate();

        mRotateAnim = ValueAnimator.ofFloat(0, mScreenWith);// - mRotateImage.getWidth()
        mRotateAnim.setTarget(mRotateImage);
        mRotateAnim.setDuration(ROTATION_DURATION);
        mRotateAnim.setStartDelay(3000);
        mRotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        if (mLinearInterpolator == null) {
            mLinearInterpolator = new LinearInterpolator();
        }
        mRotateAnim.setInterpolator(mLinearInterpolator);

        mRotateAnim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotateImage.setTranslationX((Float) animation.getAnimatedValue());
            }
        });

        mRotateAnim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                if (DEBUG) {
                    Log.d(TAG, "onAnimationEnd");
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

    public void resetAlbumAnimtion() {}

    public void endAlbumRotate() {
        if (DEBUG) {
            Log.d(TAG, "endAlbumRotate");
        }
        if (mRotateAnim != null) {
            mRotateAnim.end();
            mRotateAnim = null;
        }
    }

    public void cancelAlbumRotate() {
        if (DEBUG) {
            Log.d(TAG, "endAlbumRotate");
        }
        if (mRotateAnim != null) {
            mRotateAnim.cancel();
            mRotateAnim = null;
        }
    }

    public void resumeAlbumRotate() {
        if (DEBUG) {
            Log.d(TAG, "endAlbumRotate");
        }
        if (mRotateAnim != null) {
            mRotateAnim.resume();
            mRotateAnim = null;
        }
    }

    public void releaseView() {
        mRotateImage = null;
    }
}
