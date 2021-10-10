package com.app.snacksstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.app.snacksstore.dao.DBConnection;

public abstract class BaseFragment extends Fragment {

    public Context mContext;
    protected DBConnection dbConnection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        dbConnection = new DBConnection(mContext);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbConnection.close();
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            refreshData();
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    // 初始化视图
    public abstract View initView();

    // 初始化数据
    public abstract void initData();

    // 刷新数据
    public abstract void refreshData();
}
