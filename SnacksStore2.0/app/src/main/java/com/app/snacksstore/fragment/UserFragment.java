package com.app.snacksstore.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.app.snacksstore.R;
import com.app.snacksstore.adapter.UserToolsAdapter;

public class UserFragment extends BaseFragment {

    private GridView user_tools;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        user_tools = view.findViewById(R.id.user_tools);
        LinearLayout my_order = view.findViewById(R.id.my_order);
        my_order.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), com.app.snacksstore.activity.InventoryActivity.class));
        });
        return view;
    }

    public void initData() {
        int[] icons = {R.drawable.tools1, R.drawable.tools2, R.drawable.tools3, R.drawable.tools4,
                R.drawable.tools5, R.drawable.tools6, R.drawable.tools7, R.drawable.tools8,
                R.drawable.tools9, R.drawable.tools10, R.drawable.tools11, R.drawable.tools12};
        String[] names = {"客户服务", "寄件服务", "主题换肤", "零食转售",
                "我的乐园", "有奖晒单", "发票服务", "口袋会员",
                "拍拍回收", "我的预约", "我的拼购", "小程序"};
        user_tools.setAdapter(new UserToolsAdapter(mContext, icons, names));

    }

    @Override
    public void refreshData() {

    }
}
