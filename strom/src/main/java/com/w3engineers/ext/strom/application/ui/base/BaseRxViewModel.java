package com.w3engineers.ext.strom.application.ui.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-10 at 5:14 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-10 at 5:14 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-10 at 5:14 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

/**
 * Abstract class. All common ViewModel related task happens here. Internally disposable is managed
 * through this base class.
 */
public abstract class BaseRxViewModel extends ViewModel {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if(mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
