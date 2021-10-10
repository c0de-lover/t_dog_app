package com.app.snacksstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.app.snacksstore.R;
import com.app.snacksstore.dao.DBConnection;
import com.app.snacksstore.dao.SnacksCartService;
import com.app.snacksstore.entity.SnacksCart;
import com.app.snacksstore.fragment.CartFragment;
import com.app.snacksstore.utils.Common;

import java.util.List;

public class SnacksCartListAdapter extends BaseAdapter {

    private final Context mContext;
    private final SnacksCartService snacksCartService;
    private List<SnacksCart> snacksCartList;
    private final CartFragment cartFragment;

    public SnacksCartListAdapter(Context mContext, DBConnection dbConnection,
                                 List<SnacksCart> snacksCartList, CartFragment cartFragment) {
        this.mContext = mContext;
        this.snacksCartService = new SnacksCartService(dbConnection);
        this.snacksCartList = snacksCartList;
        this.cartFragment = cartFragment;
    }

    @Override
    public int getCount() {
        return snacksCartList.size();
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
        CheckBox cart_check;
        ImageView cart_image;
        TextView cart_name;
        TextView cart_weight;
        TextView cart_price;
        TextView cart_num;
        ImageView cart_plus, cart_minus;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
            holder = new ViewHolder();
            holder.cart_check = convertView.findViewById(R.id.cart_check);
            holder.cart_image = convertView.findViewById(R.id.cart_image);
            holder.cart_name = convertView.findViewById(R.id.cart_name);
            holder.cart_weight = convertView.findViewById(R.id.cart_weight);
            holder.cart_price = convertView.findViewById(R.id.cart_price);
            holder.cart_num = convertView.findViewById(R.id.cart_num);
            holder.cart_plus = convertView.findViewById(R.id.cart_plus);
            holder.cart_minus = convertView.findViewById(R.id.cart_minus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SnacksCart cart = snacksCartList.get(position);
        holder.cart_check.setChecked(cart.isChecked());
        holder.cart_image.setBackgroundResource(Common.getImageID(cart.getId()));
        holder.cart_name.setText(cart.getName());
        holder.cart_weight.setText(String.format("%.1fg  %.0f千焦", cart.getWeight(), cart.getHeat()));
        holder.cart_price.setText(String.format(" %.1f元  %s", cart.getPrice(), cart.getExpireDate()));
        holder.cart_num.setText(String.valueOf(cart.getNum()));

        holder.cart_plus.setOnClickListener(v -> {
            int id = cart.getId();
            snacksCartService.appendCartNum(id, 1);
            holder.cart_num.setText(String.valueOf(snacksCartService.getCartNum(id)));
            cartFragment.updateTotalMount();
        });

        holder.cart_minus.setOnClickListener(v -> {
            int id = cart.getId();
            snacksCartService.appendCartNum(id, -1);
            holder.cart_num.setText(String.valueOf(snacksCartService.getCartNum(id)));
            cartFragment.updateTotalMount();
        });

        holder.cart_check.setOnClickListener(v -> {
            snacksCartService.updateCartChecked(cart.getId(), holder.cart_check.isChecked());
            cartFragment.updateTotalMount();
        });
        return convertView;
    }

    public void refreshList(List<SnacksCart> snacksCartList) {
        this.snacksCartList = snacksCartList;
        this.notifyDataSetChanged();
    }

}
