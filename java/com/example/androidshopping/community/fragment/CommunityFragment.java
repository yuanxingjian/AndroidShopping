package com.example.androidshopping.community.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


import com.example.androidshopping.base.BaseFragment;

import static android.content.ContentValues.TAG;

/**
 * 作用：发现的Fragment
 */
public class CommunityFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG,"发现的Fragment的UI被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"发现的Fragment的数据被初始化了");
        textView.setText("发现内容");
    }
}
