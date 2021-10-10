package com.app.snacksstore.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.app.snacksstore.R;

public class CustomEditTextDialog extends BaseConfirmDialog {

    private EditText mTextEdit;

    public CustomEditTextDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId, R.id.dialog_cancel, R.id.dialog_confirm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        this.setContentWidth(0.8f);
        this.mTextEdit = findViewById(R.id.edittext);
        this.initContentView(findViewById(R.id.title), this.mTextEdit,
                findViewById(R.id.dialog_cancel), findViewById(R.id.dialog_confirm));
    }

    public String getTextEdit() {
        return mTextEdit.getText().toString();
    }

}
