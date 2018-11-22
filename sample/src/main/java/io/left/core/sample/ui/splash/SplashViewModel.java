package io.left.core.sample.ui.splash;

import android.app.Application;

import core.left.io.framework.application.ui.base.BaseSplashViewModel;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-10-05 at 10:45 AM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework - Copy.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-10-05 at 10:45 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-10-05 at 10:45 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class SplashViewModel extends BaseSplashViewModel {

    public SplashViewModel(Application application) {
        super(application);
        mDelay = 2 * 1000;
    }

}
