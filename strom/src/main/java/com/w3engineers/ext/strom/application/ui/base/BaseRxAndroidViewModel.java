package com.w3engineers.ext.strom.application.ui.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-10-05 at 12:09 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework - Copy.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-10-05 at 12:09 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-10-05 at 12:09 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public abstract class BaseRxAndroidViewModel extends AndroidViewModel {


    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public BaseRxAndroidViewModel(@NonNull Application application) {
        super(application);
    }

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
