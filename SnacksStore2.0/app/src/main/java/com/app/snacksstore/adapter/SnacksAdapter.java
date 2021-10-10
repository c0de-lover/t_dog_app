package com.app.snacksstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.app.snacksstore.R;
import com.app.snacksstore.dao.DBConnection;
import com.app.snacksstore.dao.SnacksCartService;
import com.app.snacksstore.entity.Snacks;
import com.app.snacksstore.entity.SnacksCart;
import com.app.snacksstore.utils.Common;
import com.app.snacksstore.utils.ToastHelper;


import java.util.List;

public class SnacksAdapter extends BaseAdapter {

    private final Context mContext;
    private final SnacksCartService snacksCartService;
    private List<Snacks> snacksList;

    public SnacksAdapter(Context mContext, DBConnection dbConnection, List<Snacks> snacksList) {
        this.mContext = mContext;
        this.snacksCartService = new SnacksCartService(dbConnection);
        this.snacksList = snacksList;
    }

    @Override
    public int getCount() {
        return snacksList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView snacks_image;
        TextView snacks_name;
        TextView snacks_price;
        TextView snacks_weight;
        TextView snacks_expire_date;
        ImageView snacks_buy;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.snacks_item, parent, false);
            holder = new ViewHolder();
            holder.snacks_image = convertView.findViewById(R.id.snacks_item_image);
            holder.snacks_name = convertView.findViewById(R.id.snacks_item_name);
            holder.snacks_price = convertView.findViewById(R.id.snacks_item_price);
            holder.snacks_weight = convertView.findViewById(R.id.snacks_item_weight);
            holder.snacks_expire_date = convertView.findViewById(R.id.snacks_item_expire_date);
            holder.snacks_buy = convertView.findViewById(R.id.snacks_item_buy);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Snacks snacks = snacksList.get(position);
        holder.snacks_image.setBackgroundResource(Common.getImageID(snacks.getId()));
        holder.snacks_name.setText(snacks.getName());
        holder.snacks_price.setText(String.format("  %.1f 元", snacks.getPrice()));
        holder.snacks_weight.setText(String.format("%.1fg  %.0f千焦", snacks.getWeight(), snacks.getHeat()));
        holder.snacks_expire_date.setText(snacks.getExpireDate());

        holder.snacks_buy.setOnClickListener(v -> {
            int total = snacksCartService.getCartNum(snacks.getId());
            boolean success;
            if (total == 0) {
                success = snacksCartService.insert(new SnacksCart(snacks, 1, false));
            } else {
                success = snacksCartService.appendCartNum(snacks.getId(), 1);
            }
            String name = snacks.getName().length() < 12 ? snacks.getName() : snacks.getName().substring(0, 10) + "...";
            if (success)
                ToastHelper.showToast(mContext, name + " 加入购物车成功！", Toast.LENGTH_SHORT);
            else
                ToastHelper.showToast(mContext, name + " 加入购物车失败！", Toast.LENGTH_SHORT);
        });
        return convertView;
    }

    public void refreshList(List<Snacks> snacksList) {
        this.snacksList = snacksList;
        this.notifyDataSetChanged();
    }
}
