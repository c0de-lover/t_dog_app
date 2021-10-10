package com.app.snacksstore.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import com.app.snacksstore.R;
import com.app.snacksstore.activity.CustomEditTextDialog;
import com.app.snacksstore.adapter.SnacksCartListAdapter;
import com.app.snacksstore.dao.SnacksCartService;
import com.app.snacksstore.dao.SnacksInventoryService;
import com.app.snacksstore.entity.Snacks;
import com.app.snacksstore.entity.SnacksCart;
import com.app.snacksstore.activity.CustomConfirmDialog;
import com.app.snacksstore.utils.Common;
import com.app.snacksstore.utils.ToastHelper;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CartFragment extends BaseFragment {

    private ListView cart_list;
    private CheckBox cart_check_all;
    private TextView cart_money;
    private Button cart_budget, cart_buy, cart_del;
    private SnacksCartService snacksCartService;
    private SnacksInventoryService snacksInventoryService;

    // some data
    private boolean check_all = false;
    private double totalAmount;

    @Override
    public View initView() {
        this.snacksCartService = new SnacksCartService(dbConnection);
        this.snacksInventoryService = new SnacksInventoryService(dbConnection);
        View view = View.inflate(mContext, R.layout.fragment_cart, null);
        cart_list = view.findViewById(R.id.cart_list);
        cart_money = view.findViewById(R.id.cart_money);
        cart_check_all = view.findViewById(R.id.cart_check_all);
        cart_del = view.findViewById(R.id.cart_del);
        cart_budget = view.findViewById(R.id.cart_budget);
        cart_buy = view.findViewById(R.id.cart_buy);
        initListener();
        this.calculateTotal();
        return view;
    }

    public void initData() {
        snacksCartList = snacksCartService.getAllCart();
        cart_list.setAdapter(new SnacksCartListAdapter(mContext, dbConnection, snacksCartList, this));
    }

    private List<SnacksCart> snacksCartList;

    @Override
    public void refreshData() {
        SnacksCartListAdapter adapter = ((SnacksCartListAdapter) cart_list.getAdapter());
        snacksCartList = snacksCartService.getAllCart();
        adapter.refreshList(snacksCartList);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void updateTotalMount() {
        // 合计单击事件
        this.calculateTotal();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void initListener() {
        // 全选单击事件
        cart_check_all.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check_all = isChecked;
            if (snacksCartService.setCartCheckAll(check_all))
                if (check_all) ToastHelper.showToast(mContext, "全选", Toast.LENGTH_SHORT);
                else ToastHelper.showToast(mContext, "取消全选", Toast.LENGTH_SHORT);
            updateTotalMount();
            refreshData();
        });

        // 合计单击事件
        cart_money.setOnClickListener(v -> this.calculateTotal());

        // 删除单击事件
        cart_del.setOnClickListener(v -> {
            boolean checkOne = checkOne();
            if (!checkOne) {
                ToastHelper.showToast(mContext, "您未选中任何零食噢~", Toast.LENGTH_SHORT);
            } else {
                final CustomConfirmDialog customConfirmDialog = new CustomConfirmDialog(mContext, R.style.MyDialog);
                customConfirmDialog.setTitle("提示")
                        .setMessage("确认删除选中的零食吗？")
                        .setCancel("取消", Dialog::dismiss)
                        .setConfirm("确认", dialog -> {
                            if (snacksCartService.deleteAllChecked()) {
                                ToastHelper.showToast(mContext, "删除成功", Toast.LENGTH_SHORT);
                                refreshData();
                                updateTotalMount();
                                dialog.dismiss();
                            } else ToastHelper.showToast(mContext, "删除失败", Toast.LENGTH_SHORT);
                        }).show();
            }
        });

        // 结算单击事件
        cart_buy.setOnClickListener(v -> {
            boolean checkOne = checkOne();
            if (!checkOne) {
                ToastHelper.showToast(mContext, "您未选中任何零食噢~", Toast.LENGTH_SHORT);
            } else {
                snacksInventoryService.addFromCart(snacksCartService.getAllCartChecked());
                Intent intent = new Intent();
                intent.putExtra("totalAmount", totalAmount);
                intent.setClass(mContext, com.app.snacksstore.activity.OrderActivity.class);
                startActivity(intent);
            }
        });

        cart_budget.setOnClickListener(v -> {
            final CustomEditTextDialog customConfirmDialog = new CustomEditTextDialog(mContext, R.style.MyDialog);
            customConfirmDialog.setTitle("输入预算")
                    .setCancel("取消", Dialog::dismiss)
                    .setConfirm("确认", dialog -> {
                        CustomEditTextDialog editTextDialog = (CustomEditTextDialog) dialog;
                        String text = editTextDialog.getTextEdit();
                        if (text.length() == 0) {
                            ToastHelper.showToast(mContext, "请输入预算值", Toast.LENGTH_SHORT);
                            return;
                        }
                        budgetSolution(Double.parseDouble(text));
                        dialog.dismiss();
                    }).show();
        });
    }

    private void budgetSolution(double budget) {
        List<SnacksCart> cartList = snacksCartService.getAllCartChecked();
        cartList.sort(Comparator.comparingDouble(Snacks::getPrice));
        List<Double> price = Arrays.stream(cartList.stream().mapToDouble(Snacks::getPrice).toArray()).boxed().
                collect(Collectors.toList());
        List<Integer> count = Common.calculateBudget(budget, price);
        if (count == null) return;
        for (int i = 0; i < cartList.size(); ++i) {
            SnacksCart cart = cartList.get(i);
            snacksCartService.setCartNum(cart.getId(), count.get(i));
        }
        updateTotalMount();
        refreshData();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void calculateTotal() {
        totalAmount = snacksCartService.calSumPrice();
        cart_money.setText("合计:￥" + String.format(" %.1f 元", totalAmount));
    }

    private boolean checkOne() {
        snacksCartList = snacksCartService.getAllCart();
        boolean checkOne = false;
        for (SnacksCart cart : snacksCartList) {
            if (cart.isChecked()) {
                checkOne = true;
                break;
            }
        }
        return checkOne;
    }
}
