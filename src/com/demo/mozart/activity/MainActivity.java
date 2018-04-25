package com.demo.mozart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.demo.mozart.R;

public class MainActivity extends BaseActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void doClick(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b0:
                doClick(OthersActivity.class);
                break;
            case R.id.b1:
                doClick(SeekBarTestActivity.class);
                break;
            case R.id.b2:
                doClick(AnimationActivity.class);
                break;
            case R.id.b2_1:
                doClick(Animation2Activity.class);
                break;
            case R.id.b3:
                doClick(ViewTestActivity.class);
                break;
            case R.id.b4:
                doClick(ServiceTestActivity.class);
                break;
            case R.id.b5:
                doClick(ProviderTestActivity.class);
                break;
        }
    }

}
