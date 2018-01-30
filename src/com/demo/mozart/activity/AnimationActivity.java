
package com.demo.mozart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.demo.mozart.R;

public class AnimationActivity extends Activity implements View.OnClickListener {

    private Animation mAnim;
    private View mView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation);

        mView = findViewById(R.id.tv);

        /*mAnim = AnimationUtils.loadAnimation(this, R.anim.home_animation);
        mAnimationTopRightView = (ImageView) this.findViewById(R.id.animation_top_right);
        mAnimationTopRightView.startAnimation(mAnim);

        mPlayLoadingAnimation = new PlayLoadingAnimation();
        mPlayLoadingView = (ImageView) this.findViewById(R.id.play_loading);
        mPlayLoadingAnimation.addView(mPlayLoadingView);
        mPlayLoadingAnimation.startAlbumRotate();

        mPlayLoadingAnimation1 = new PlayLoadingAnimation1();
        mPlayLoadingView1 = (ImageView) this.findViewById(R.id.play_loading1);
        mPlayLoadingAnimation1.addView(mPlayLoadingView1, getWindowManager().getDefaultDisplay().getWidth());
        mPlayLoadingAnimation1.startAlbumRotate();*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alpha:
                mAnim = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.alpha_anim);
                break;
            case R.id.scale:
                mAnim = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.scale_anim);
                break;
            case R.id.rotate:
                mAnim = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.rotate_anim);
                break;
            case R.id.trans:
                mAnim = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.translate_anim);
                break;
        }

        if (mAnim != null) {
            mView.startAnimation(mAnim);
        }

    }
}
