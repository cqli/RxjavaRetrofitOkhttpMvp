package com.example.lcq.contract;

/**
 * Created by lcq on 2016/11/26.
 * 18:28
 *
 * com.example.lcq
 * 契约类,定义用到的一些接口方法
 */

public class MainContract {

    public interface MainView {


        void Success(String str);

        void Fail(String failMsg);
    }


    public interface MainPresenter {
        void query();
    }
}
