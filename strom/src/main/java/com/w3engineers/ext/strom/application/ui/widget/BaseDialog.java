package com.w3engineers.ext.strom.application.ui.widget;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/9/2018 at 1:09 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/9/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w3engineers.ext.strom.application.ui.base.BaseActivity;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Expect a custom layout and display Dialog Fragment
 */
public abstract class BaseDialog extends DialogFragment implements View.OnClickListener {

    private final static int DEFAULT_ID_VALUE = 0;
    private ViewDataBinding mViewDataBinding;
    private BaseActivity baseActivity;
    private CompositeDisposable mCompositeDisposable;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof BaseActivity){
            baseActivity = (BaseActivity)context;
        }
    }


    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int layoutId = getLayoutId();
        if (layoutId > DEFAULT_ID_VALUE) {
            mCompositeDisposable = new CompositeDisposable();

            return updateLayoutView(inflater, layoutId, container);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startUi();
    }


    private View updateLayoutView(LayoutInflater inflater, int layoutId, ViewGroup container) {
        View view = null;

        try {
            mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);

            view = mViewDataBinding.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    protected abstract int getLayoutId();
    protected abstract void startUi();

    @Override
    public void onClick(View v) {

    }

    /**
     * Return current viewDataBinding
     **/
    protected ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }
}
