package com.demo.mozart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class ShareTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "ShareTestActivity!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
