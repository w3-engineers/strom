package com.w3engineers.ext.strom.application.ui.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/*
 * ****************************************************************************
 * * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
 * *
 * * Created by:
 * * Name : Azizul Islam
 * * Date : 10/13/17
 * * Email : azizul@w3engineers.com
 * *
 * * Purpose: Child view holder class inherit this class
 * *
 * * Last Edited by : SUDIPTA KUMAR PAIK on 03/16/18.
 * * History:
 * * 1:
 * * 2:
 * *
 * * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/16/18.
 * ****************************************************************************
 */

/**
 * @deprecated Developers are encouraged to use {@link BaseAdapter.BaseAdapterViewHolder}
 */
@Deprecated
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected ViewDataBinding mViewDataBinding;

    /**
     * This class automatically set's itself the root views click listener. Most of the time this is beneficial.
     * If you face any problem with this default behavior you should call the overriden constructor
     * @param viewDataBinding
     */
    public BaseViewHolder(ViewDataBinding viewDataBinding) {
        this(viewDataBinding, false);
    }

    /**
     * Use this constructor to reset default click listener.
     * @param viewDataBinding
     * @param isResetDefaultListener
     */
    public BaseViewHolder(ViewDataBinding viewDataBinding, boolean isResetDefaultListener) {
        super(viewDataBinding.getRoot());

        mViewDataBinding = viewDataBinding;
        if(!isResetDefaultListener) {
            mViewDataBinding.getRoot().setOnClickListener(this);
        }
    }

    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * Child class have to implement this method.
     **/
    public abstract void bind(T item);

    /**
     * To set click listener on any view, You can pass multiple view at a time
     *
     * @param views View as params
     */
    protected void setClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }
}