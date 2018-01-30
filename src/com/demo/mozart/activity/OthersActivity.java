package com.demo.mozart.activity;

import android.app.Activity;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.demo.mozart.R;
import com.demo.mozart.share.AppName;
import com.demo.mozart.share.PullAppNameParser;

import java.io.InputStream;
import java.util.List;

public class OthersActivity extends Activity implements OnClickListener {

    private static Vibrator mVibrator;
    private boolean mVibrating = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ob1:
                if (mVibrating) {
                    mVibrator.cancel();
                } else {
                    mVibrator.vibrate(10000);
                }
                mVibrating = !mVibrating;
                break;
            case R.id.ob2:
                initShareIntent();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void initShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "This is my share!");
        try {
            Intent chooser = Intent.createChooser(intent, "Select Share");
            chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Fail to share!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initShareAppNamesOrderList() {
        try {
            InputStream is = getAssets().open("shareAppNamesOrderList.xml");
            List<AppName> mAppNamesOrderList = new PullAppNameParser().parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
