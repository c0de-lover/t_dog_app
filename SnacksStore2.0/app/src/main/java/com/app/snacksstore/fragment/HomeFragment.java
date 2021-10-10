package com.app.snacksstore.fragment;


import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.app.snacksstore.R;
import com.app.snacksstore.adapter.SnacksAdapter;
import com.app.snacksstore.entity.Snacks;
import com.app.snacksstore.utils.ToastHelper;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends BaseFragment {

    private ListView snacksListView;
    private List<Snacks> snacksList;
    private List<Snacks> snacksListShow;
    private ImageView toTopBtn;
    private EditText searchbarText;
    private TextView newMsgText;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        snacksListView = view.findViewById(R.id.snacks_list);
        toTopBtn = view.findViewById(R.id.button_2top);
        searchbarText = view.findViewById(R.id.searchbar_text);
        newMsgText = view.findViewById(R.id.new_msg_text);
        initListener();
        return view;
    }

    public void initData() {
        snacksList = initSnacksData();
        snacksListShow = snacksList;
        SnacksAdapter adapter = new SnacksAdapter(mContext, dbConnection, snacksListShow);
        snacksListView.setAdapter(adapter);
    }

    @Override
    public void refreshData() {
        SnacksAdapter adapter = ((SnacksAdapter) snacksListView.getAdapter());
        adapter.refreshList(snacksListShow);
    }

    private void initListener() {

        toTopBtn.setOnClickListener(v -> {
            //列表滑动回到顶部
            snacksListView.smoothScrollToPosition(0);
        });

        searchbarText.setOnEditorActionListener((v, actionId, event) -> {
            String search = v.getText().toString();
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (search.length() > 0) {
                    snacksListShow = snacksList.stream().filter(s -> s.getName().contains(search))
                            .collect(Collectors.toList());
                } else {
                    snacksListShow = snacksList;
                }
                refreshData();
            }
            return true;
        });
        searchbarText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                String content = searchbarText.getText().toString();
                if (content.length() == 0) {
                    snacksListShow = snacksList;
                    refreshData();
                }
            }
            return false;
        });


        newMsgText.setOnClickListener(v -> {
            ToastHelper.showToast(mContext, "消息中心尚待完善！", Toast.LENGTH_SHORT);
            // 消息中心
        });

    }

    private List<Snacks> initSnacksData() {
        List<Snacks> data = new LinkedList<>();
        data.add(new Snacks(1, "乐事薯片（袋装）", 75, 1014,
                8.9, "2022/4/1"));
        data.add(new Snacks(2, "乐事薯片（罐装）", 104, 678,
                7.9, "2022/4/1"));
        data.add(new Snacks(3, "好丽友派", 28, 628,
                2, "2022/2/13"));
        data.add(new Snacks(4, "百力滋", 41, 815,
                5.2, "2021/12/19"));
        data.add(new Snacks(5, "奥利奥夹心饼干（包）", 400, 678,
                1.5, "2022/3/14"));
        data.add(new Snacks(6, "华味瓜子", 115, 2612,
                6.6, "2021/12/18"));
        data.add(new Snacks(7, "洽洽瓜子", 198, 4024,
                14.5, "2021/11/18"));
        data.add(new Snacks(8, "波力海苔", 16, 245,
                14, "2021/12/26"));
        data.add(new Snacks(9, "台尚鳕鱼香丝", 38, 612,
                5, "2022/2/18"));
        data.add(new Snacks(10, "卫龙大面筋", 65, 180,
                3.2, "2021/10/30"));
        data.add(new Snacks(11, "阿尔卑斯软糖(条形）", 47, 712,
                4.5, "2022/4/12"));
        data.add(new Snacks(12, "德芙巧克力", 41, 977,
                8.3, "2022/3/22"));
        data.add(new Snacks(13, "旺仔qq糖", 70, 842,
                3.5, "2022/9/11"));
        data.add(new Snacks(14, "士力架", 31, 1014,
                4.4, "2022/8/11"));
        data.add(new Snacks(15, "费雷罗3粒", 32.4, 821,
                11, "2021/10/19"));
        data.add(new Snacks(16, "脆香米巧克力棒3支", 36, 915,
                16.5, "2021/12/13"));
        data.add(new Snacks(17, "彩虹糖", 40, 703,
                3.5, "2022/1/11"));
        return data;
    }

}
