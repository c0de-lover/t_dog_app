package com.app.snacksstore;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏设置，隐藏窗口所有装饰
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        setContentView(R.layout.activity_loading);
        // 伪加载
        new Handler().postDelayed(() -> {
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            finish();
        }, 2000);
    }
}
