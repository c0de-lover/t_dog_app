package com.app.snacksstore;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.app.snacksstore.fragment.BaseFragment;
import com.app.snacksstore.fragment.CartFragment;
import com.app.snacksstore.fragment.HomeFragment;
import com.app.snacksstore.fragment.UserFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private Map<Integer, BaseFragment> fragmentsMap;
    private BaseFragment onFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        setContentView(R.layout.activity_main);

        rg_main = findViewById(R.id.rg_main);
        initFragments();
        rg_main.check(R.id.rg_home);
    }

    private void initUI() {
        //解决软键盘弹出时底部布局上移
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void initFragments() {
        fragmentsMap = new HashMap<>(3);
        fragmentsMap.put(R.id.rg_home, new HomeFragment());
        fragmentsMap.put(R.id.rg_cart, new CartFragment());
        fragmentsMap.put(R.id.rg_user, new UserFragment());
        // 设置监听
        rg_main.setOnCheckedChangeListener((group, checkedId) -> {
            BaseFragment targetFragment = fragmentsMap.get(checkedId);
            switchFragment(onFragment, targetFragment);
        });
    }


    private void switchFragment(Fragment originFragment, BaseFragment targetFragment) {
        if (onFragment != targetFragment) {
            onFragment = targetFragment;
            if (targetFragment != null) {
                // 开启事务
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // 判断targetFragment是否有添加
                if (!targetFragment.isAdded()) {
                    // 隐藏当前的fragment
                    if (originFragment != null) {
                        transaction.hide(originFragment);
                    }
                    transaction.add(R.id.main_frame, targetFragment).commit();
                } else {
                    if (originFragment != null) {
                        transaction.hide(originFragment);
                    }
                    transaction.show(targetFragment).commit();
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
