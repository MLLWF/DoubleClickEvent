package com.mllwf.doubleclickdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = (TextView) findViewById(R.id.text);
        DoubleClickEvent.doubleOrClickText(MainActivity.this, tv, "触发双击事件");
        DoubleClickEvent.setSingleClickListener(new DoubleClickEvent.onSingleClickListener() {
            @Override
            public void singleClick(View view) {
                if (view == tv) {
                    System.out.println(tv.getText().toString());//
                }
            }
        });
    }
}
