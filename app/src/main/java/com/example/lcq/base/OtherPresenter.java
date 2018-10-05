package com.example.lcq.base;


import com.example.lcq.mvp.IModel;
import com.example.lcq.mvp.IPresenter;
import com.example.lcq.mvp.IView;

import java.lang.ref.WeakReference;

/**
 * Created by lcq on 2016/11/26.
 * 17:21
 *
 * com.example.lcq
 */

public abstract class OtherPresenter<M extends IModel, V extends IView> implements IPresenter {
    private WeakReference actReference;
    protected V iView;
    protected M iModel;

    public M getiModel() {
        iModel = loadModel(); //使用前先进行初始化
        return iModel;
    }

    @Override
    public void attachView(IView iView) {
        actReference = new WeakReference(iView);
    }

    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }

    @Override
    public V getIView() {
        return (V) actReference.get();
    }

    public abstract M loadModel();
}
