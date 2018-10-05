package com.example.lcq.presenter;

import com.example.lcq.activity.MainActivity;
import com.example.lcq.base.BasePresenter;
import com.example.lcq.contract.MainContract;
import com.example.lcq.model.MainModel;
import com.example.lcq.mvp.IModel;
import com.example.lcq.utils.LogUtils;

import java.util.HashMap;

/**
 * Created by GaoSheng on 2016/11/26.
 * 20:51
 *
 * @VERSION V1.4
 * com.example.gs.mvpdemo.presenter
 */

public class MainPresenter extends BasePresenter<MainActivity> implements
        MainContract.MainPresenter {


    @Override
    public void query() {
        ((MainModel) getiModelMap().get("login")).query(new MainModel
                .InfoHint() {
            @Override
            public void successInfo(String str) {
                getIView().Success(str);  //成功
            }

            @Override
            public void failInfo(String str) {
                LogUtils.e("LoginPresenter.failInfo", str);

                getIView().Fail(str);  //失败
            }
        });
    }

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MainModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("login", models[0]);
        return map;
    }
}
