package com.app.snacksstore.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    public static Toast mToast = null;

    public static void showToast(Context context, String text, int duration) {
        initializeToast(context, text, duration);
        mToast.show();
    }

    public static void showToast(Context context, String text, int duration, int gravity) {
        initializeToast(context, text, duration);
        mToast.setGravity(gravity, 0, 0);
        mToast.show();
    }

    private static void initializeToast(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
