package com.app.snacksstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.app.snacksstore.MainActivity;
import com.app.snacksstore.R;

public class SuccessActivity extends AppCompatActivity {

    private TextView payBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        payBack = findViewById(R.id.pay_back);
        payBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        });
    }
}
