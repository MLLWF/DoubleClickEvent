package com.mllwf.doubleclickdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = (TextView) findViewById(R.id.text);
        DoubleClickEvent.doubleOrSingleClickView(tv);
        DoubleClickEvent.setOnClickListener(new DoubleClickEvent.onSingleOrDoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (view instanceof TextView) {
                    Toast.makeText(MainActivity.this, "点击事件", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDoubleClick(View view) {
                Toast.makeText(MainActivity.this, "双击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
