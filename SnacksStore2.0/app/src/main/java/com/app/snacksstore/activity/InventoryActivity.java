package com.app.snacksstore.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.app.snacksstore.MainActivity;
import com.app.snacksstore.R;
import com.app.snacksstore.adapter.InventoryListAdapter;
import com.app.snacksstore.dao.DBConnection;
import com.app.snacksstore.dao.SnacksInventoryService;
import com.app.snacksstore.entity.SnacksCart;
import com.app.snacksstore.utils.ToastHelper;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {
    private ListView inventory_list;
    private Button inventory_del, inventory_back;
    private DBConnection dbConnection;
    private SnacksInventoryService snacksInventoryService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        inventory_list = findViewById(R.id.inventory_list);
        inventory_del = findViewById(R.id.inventory_del);
        inventory_back = findViewById(R.id.inventory_back);
        dbConnection = new DBConnection(this);
        snacksInventoryService = new SnacksInventoryService(dbConnection);
        initData();
        initListener();
    }

    public void refreshData() {
        InventoryListAdapter adapter = ((InventoryListAdapter) inventory_list.getAdapter());
        adapter.refreshList(snacksInventoryService.getAllCart());
    }

    private void initData() {
        List<SnacksCart> snacksCartList = snacksInventoryService.getAllCart();
        inventory_list.setAdapter(new InventoryListAdapter(this, dbConnection, snacksCartList));
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void initListener() {

        // 删除单击事件
        inventory_del.setOnClickListener(v -> {
            boolean checkOne = checkOne();
            if (!checkOne) {
                ToastHelper.showToast(this, "您未选中任何零食噢~", Toast.LENGTH_SHORT);
            } else {
                final CustomConfirmDialog customConfirmDialog = new CustomConfirmDialog(this, R.style.MyDialog);
                customConfirmDialog.setTitle("提示")
                        .setMessage("确认删除选中的零食吗？")
                        .setCancel("取消", Dialog::dismiss)
                        .setConfirm("确认", dialog -> {
                            if (snacksInventoryService.deleteAllChecked()) {
                                ToastHelper.showToast(this, "删除成功", Toast.LENGTH_SHORT);
                                refreshData();
                                dialog.dismiss();
                            } else ToastHelper.showToast(this, "删除失败", Toast.LENGTH_SHORT);
                        }).show();
            }
        });
        inventory_back.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        });
    }

    private boolean checkOne() {
        boolean checkOne = false;
        List<SnacksCart> snacksCartList = snacksInventoryService.getAllCart();
        for (SnacksCart cart : snacksCartList) {
            if (cart.isChecked()) {
                checkOne = true;
                break;
            }
        }
        return checkOne;
    }
}
