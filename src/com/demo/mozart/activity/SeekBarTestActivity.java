
package com.demo.mozart.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;

import com.demo.mozart.R;

public class SeekBarTestActivity extends Activity {

    ListView mListView;

    public class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 1;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                Button b = new Button(SeekBarTestActivity.this);
                LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                b.setLayoutParams(params);
                b.setText("asdfadsfasdfasdf");
                b.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        MemoryInfo memoryInfo = new MemoryInfo();
                        Debug.getMemoryInfo(memoryInfo);
                        Log.d("morh", "" + memoryInfo.getTotalPss());
                    }
                });
                convertView = b;
            }
            return convertView;
        }

    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_seekbar_test);

        mListView = (ListView) findViewById(R.id.mylist);
        mListView.setAdapter(new myAdapter());
    }

}
