package com.example.lcq.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lcq.R;
import com.example.lcq.base.BaseActivity;
import com.example.lcq.contract.MainContract;
import com.example.lcq.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.MainView {
    TextView tv_click;

    @Override
    protected MainPresenter loadPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        tv_click = findViewById(R.id.tv_click);
        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_click.setText("请求中");
                mPresenter.query();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void Success(String str) {
        tv_click.setText(str);
    }

    @Override
    public void Fail(String failMsg) {
        tv_click.setText(failMsg);
    }
}
