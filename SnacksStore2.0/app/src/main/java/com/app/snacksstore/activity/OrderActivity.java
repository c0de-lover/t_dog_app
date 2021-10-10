package com.app.snacksstore.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.app.snacksstore.MainActivity;
import com.app.snacksstore.R;
import com.app.snacksstore.dao.DBConnection;
import com.app.snacksstore.dao.SnacksCartService;
import com.app.snacksstore.utils.ToastHelper;

import java.io.File;

public class OrderActivity extends AppCompatActivity {
    private EditText u_address, u_name, u_phone;
    private TextView orderAmount, orderPay;
    private ImageView orderBack, orderMsg;
    private SnacksCartService snacksCartService;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        u_address = findViewById(R.id.u_address);
        u_name = findViewById(R.id.u_name);
        u_phone = findViewById(R.id.u_phone);
        orderAmount = findViewById(R.id.order_amount);
        orderPay = findViewById(R.id.order_pay);
        orderBack = findViewById(R.id.order_back);
        orderMsg = findViewById(R.id.order_message);

        snacksCartService = new SnacksCartService(new DBConnection(this));
        popUserInfo();
        Intent intent = getIntent();
        double totalAmount = intent.getDoubleExtra("totalAmount", 0.0);
        orderAmount.setText("合计:￥" + String.format(" %.1f 元", totalAmount));

        orderBack.setOnClickListener(v -> {
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            startActivity(new Intent(this, MainActivity.class));
        });
        orderMsg.setOnClickListener(v -> ToastHelper.showToast(this,
                "消息中心尚待开发！", Toast.LENGTH_SHORT));
        orderPay.setOnClickListener(v -> {
            String address = u_address.getText().toString().trim();
            String name = u_name.getText().toString().trim();
            String phone = u_phone.getText().toString().trim();
            if (address.length() == 0 || name.length() == 0 || phone.length() == 0) {
                ToastHelper.showToast(this, "请填写完整的信息！", Toast.LENGTH_LONG);
                return;
            }
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("address", address);
            editor.putString("name", name);
            editor.putString("phone", phone);
            editor.apply();
            ToastHelper.showToast(this, "支付中...", Toast.LENGTH_LONG);
            new Handler().postDelayed(() -> {
                snacksCartService.deleteAllChecked();
                startActivity(new Intent(this, com.app.snacksstore.activity.SuccessActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }, 1000);
        });
    }

    private void popUserInfo() {
        @SuppressLint("SdCardPath") String referPath = "/data/data/com.app.snacksstore/shared_prefs/userInfo.xml";
        File file = new File(referPath);
        if (file.exists()) {
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            String address = sharedPreferences.getString("address", "NOT FOUND");
            String name = sharedPreferences.getString("name", "NOT FOUND");
            String phone = sharedPreferences.getString("phone", "NOT FOUND");
            u_address.setText(address);
            u_name.setText(name);
            u_phone.setText(phone);
        }
    }
}
