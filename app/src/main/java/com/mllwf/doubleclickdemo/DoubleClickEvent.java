package com.mllwf.doubleclickdemo;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ML on 2017/4/7.
 */

public class DoubleClickEvent {

    public static long lastClickTime = 0;

    public interface onDoubleClickListener {
        void onSingleClick(View view);

        void onDoubleClick(View view);
    }

    public static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        final long diffTime = currentTime - lastClickTime;
        lastClickTime = currentTime;
        if (diffTime > 0 && diffTime < 300) {
            return true;
        }
        return false;
    }

    private static int type = 1;  //1：单击 2：双击

    public static void registerDoubleClickListener(View view, final onDoubleClickListener listener) {
        if (view == null || listener == null) {
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (isDoubleClick()) {
                    type = 2;
                    listener.onDoubleClick(v);
                } else {
                    //这里不能用Thread.sleep(),应该这个休眠是整个线程的休眠，即无法实现想要的延时效果，又会使页面卡顿！
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (type == 1) {
                                listener.onSingleClick(v);
                            } else {
                                //如果是双击事件，记得初始化type的值！
                                type = 1;
                            }
                        }
                    }, 300);
                    //延迟300毫秒是为了判断后续是否有双击事件，如果没有就执行单击事件！
                }
            }
        });
    }

    //TODO:_____________________________________________________________________________________________________

    public static void doubleOrClickText(final Context context, TextView textView, final String content) {
        if (textView == null) {
            return;
        }
        DoubleClickEvent.registerDoubleClickListener(textView, new DoubleClickEvent.onDoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (mListener != null) {
                    mListener.singleClick(view);
                } else {
                    System.out.println("没有注册点击事件");
                }
            }

            @Override
            public void onDoubleClick(View view) {
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface onSingleClickListener {
        void singleClick(View view);
    }

    public static onSingleClickListener mListener;

    public static void setSingleClickListener(onSingleClickListener listener) {
        mListener = listener;
    }

}
