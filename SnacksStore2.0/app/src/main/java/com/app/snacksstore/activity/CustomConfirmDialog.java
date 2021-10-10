package com.app.snacksstore.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.app.snacksstore.R;


public class CustomConfirmDialog extends BaseConfirmDialog {

    public CustomConfirmDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId, R.id.dialog_cancel, R.id.dialog_confirm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);

        this.setContentWidth(0.8f);
        this.initContentView(findViewById(R.id.dialog_title), findViewById(R.id.dialog_message),
                findViewById(R.id.dialog_cancel), findViewById(R.id.dialog_confirm));
    }

}
