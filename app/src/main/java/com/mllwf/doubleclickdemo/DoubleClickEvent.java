package com.mllwf.doubleclickdemo;

import android.os.Handler;
import android.view.View;

/**
 * Created by ML on 2017/4/7.
 */

public class DoubleClickEvent {

    private static long lastClickTime = 0;

    private static int type = 1;  //1：单击 2：双击

    public interface onSingleOrDoubleClickListener {

        void onSingleClick(View view);

        void onDoubleClick(View view);
    }

    private static onSingleOrDoubleClickListener onClickListener;

    public static void setOnClickListener(onSingleOrDoubleClickListener listener) {
        onClickListener = listener;
    }

    private static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        final long diffTime = currentTime - lastClickTime;
        lastClickTime = currentTime;
        if (diffTime > 0 && diffTime <= 250) {
            return true;
        }
        return false;
    }

    private static void registerClickListener(View view, final onSingleOrDoubleClickListener listener) {
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

    public static void doubleOrSingleClickView(View view) {

        registerClickListener(view, new onSingleOrDoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onSingleClick(view);
                }
            }

            @Override
            public void onDoubleClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onDoubleClick(view);
                }
            }
        });
    }
}
