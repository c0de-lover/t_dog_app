package com.app.snacksstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.snacksstore.R;

public class UserToolsAdapter extends BaseAdapter {

    private final Context mContext;
    private final int[] icons;
    private final String[] names;

    public UserToolsAdapter(Context mContext, int[] icons, String[] names) {
        this.mContext = mContext;
        this.icons = icons;
        this.names = names;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        ImageView icon = view.findViewById(R.id.user_item_icon);
        TextView name = view.findViewById(R.id.user_item_name);

        icon.setBackgroundResource(icons[position]);
        name.setText(names[position]);
        return view;
    }
}
