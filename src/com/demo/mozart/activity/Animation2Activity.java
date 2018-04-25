
package com.demo.mozart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.demo.mozart.R;
import com.demo.mozart.animation.PlayLoadingAnimation;

public class Animation2Activity extends Activity {

    private PlayLoadingAnimation mPlayLoadingAnimation;
    private ImageView mPlayLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation2);

        mPlayLoadingAnimation = new PlayLoadingAnimation();
        mPlayLoadingView = (ImageView) this.findViewById(R.id.play_loading);
        mPlayLoadingAnimation.addView(mPlayLoadingView);
        mPlayLoadingAnimation.startAlbumRotate();
    }



}
