package com.app.snacksstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.snacksstore.R;
import com.app.snacksstore.dao.DBConnection;
import com.app.snacksstore.dao.SnacksInventoryService;
import com.app.snacksstore.entity.SnacksCart;
import com.app.snacksstore.utils.Common;

import java.util.List;

public class InventoryListAdapter extends BaseAdapter {

    private final Context mContext;
    private final SnacksInventoryService snacksInventoryService;
    private List<SnacksCart> inventoryList;

    public InventoryListAdapter(Context mContext, DBConnection dbConnection,
                                List<SnacksCart> inventoryList) {
        this.mContext = mContext;
        this.snacksInventoryService = new SnacksInventoryService(dbConnection);
        this.inventoryList = inventoryList;
    }

    @Override
    public int getCount() {
        return inventoryList.size();
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
        CheckBox inventory_check;
        ImageView inventory_image;
        TextView inventory_name;
        TextView inventory_weight;
        TextView inventory_price;
        TextView inventory_num;
        ImageView inventory_plus, inventory_minus;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.inventory_item, parent, false);
            holder = new ViewHolder();
            holder.inventory_check = convertView.findViewById(R.id.inventory_check);
            holder.inventory_image = convertView.findViewById(R.id.inventory_image);
            holder.inventory_name = convertView.findViewById(R.id.inventory_name);
            holder.inventory_weight = convertView.findViewById(R.id.inventory_weight);
            holder.inventory_price = convertView.findViewById(R.id.inventory_price);
            holder.inventory_num = convertView.findViewById(R.id.inventory_num);
            holder.inventory_plus = convertView.findViewById(R.id.inventory_plus);
            holder.inventory_minus = convertView.findViewById(R.id.inventory_minus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SnacksCart cart = inventoryList.get(position);
        holder.inventory_check.setChecked(cart.isChecked());
        holder.inventory_image.setBackgroundResource(Common.getImageID(cart.getId()));
        holder.inventory_name.setText(cart.getName());
        holder.inventory_weight.setText(String.format("%.1fg  %.0f千焦", cart.getWeight(), cart.getHeat()));
        holder.inventory_price.setText(String.format(" %.1f元  %s", cart.getPrice(), cart.getExpireDate()));
        holder.inventory_num.setText(String.valueOf(cart.getNum()));

        holder.inventory_plus.setOnClickListener(v -> {
            int id = cart.getId();
            snacksInventoryService.appendCartNum(id, 1);
            holder.inventory_num.setText(String.valueOf(snacksInventoryService.getCartNum(id)));
        });

        holder.inventory_minus.setOnClickListener(v -> {
            int id = cart.getId();
            snacksInventoryService.appendCartNum(id, -1);
            holder.inventory_num.setText(String.valueOf(snacksInventoryService.getCartNum(id)));
        });

        holder.inventory_check.setOnClickListener(v -> {
            snacksInventoryService.updateCartChecked(cart.getId(), holder.inventory_check.isChecked());
        });
        return convertView;
    }

    public void refreshList(List<SnacksCart> inventoryList) {
        this.inventoryList = inventoryList;
        this.notifyDataSetChanged();
    }

}
