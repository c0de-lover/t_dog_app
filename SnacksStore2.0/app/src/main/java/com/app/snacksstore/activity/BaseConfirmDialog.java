package com.app.snacksstore.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;


public class BaseConfirmDialog extends Dialog implements View.OnClickListener {
    protected String title, message, cancel, confirm;

    protected BaseOnCancelListener cancelListener;
    protected BaseOnConfirmListener confirmListener;
    protected int cancelID, confirmID;

    public BaseConfirmDialog(@NonNull Context context, int themeResId,
                             int cancelID, int confirmID) {
        super(context, themeResId);
        this.cancelID = cancelID;
        this.confirmID = confirmID;
    }


    public BaseConfirmDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public BaseConfirmDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public BaseConfirmDialog setCancel(String cancel, BaseOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener = listener;
        return this;
    }

    public BaseConfirmDialog setConfirm(String confirm, BaseOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == cancelID) {
            if (cancelListener != null) {
                cancelListener.onCancel(this);
            }
        } else if (id == confirmID) {
            if (confirmListener != null) {
                confirmListener.onConfirm(this);
            }
        }
    }

    @FunctionalInterface
    public interface BaseOnCancelListener {
        void onCancel(BaseConfirmDialog dialog);
    }

    @FunctionalInterface
    public interface BaseOnConfirmListener {
        void onConfirm(BaseConfirmDialog dialog);
    }

    protected void initContentView(TextView mTitle, TextView mMessage, TextView mCancel, TextView mConfirm) {
        if (title != null && !title.isEmpty()) {
            mTitle.setText(title);
        }
        if (message != null && !message.isEmpty()) {
            mMessage.setText(message);
        }
        if (cancel != null && !cancel.isEmpty()) {
            mCancel.setText(cancel);
        }
        if (confirm != null && !confirm.isEmpty()) {
            mConfirm.setText(confirm);
        }
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    protected void setContentWidth(float widthScale) {
        WindowManager manager = getWindow().getWindowManager();
        Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        Point point = new Point();
        display.getSize(point);
        attributes.width = (int) (point.x * widthScale);
        getWindow().setAttributes(attributes);
    }
}
