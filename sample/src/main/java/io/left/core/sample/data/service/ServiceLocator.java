package io.left.core.sample.data.service;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-05 at 6:24 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-05 at 6:24 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-05 at 6:24 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

import android.app.Application;
import android.content.Context;

import io.left.core.sample.data.database.user.LocalUserDataSource;
import io.left.core.sample.data.local.database.Database;
import io.left.core.sample.data.remote.RemoteApiProvider;
import io.left.core.sample.data.remote.user.RemoteUserDataSource;
import io.left.core.sample.ui.details.DetailsViewModelLocal;
import io.left.core.sample.ui.details.DetailsViewModelRemote;
import io.left.core.sample.ui.splash.SplashViewModel;

/**
 * Service instance provider. We might upgrade this class later to provide various type of DataSource generator
 */
// TODO: 7/5/2018 We can generate a general interface and deploy common service locator for netowrk, db, db+nework. Might be in next version
public class ServiceLocator {

    private final static ServiceLocator SERVICE_LOCATOR = new ServiceLocator();

    private ServiceLocator() {}


    public static ServiceLocator getInstance() {
        return SERVICE_LOCATOR;
    }

    public DetailsViewModelLocal getDetailsModelLocal(Context context) {

        if(context == null) {
            return null;
        }
        return new DetailsViewModelLocal(new LocalUserDataSource(Database.getInstance(context).userDao()));

    }

    public DetailsViewModelRemote getDetailsModelRemote() {

        return new DetailsViewModelRemote(new RemoteUserDataSource(RemoteApiProvider.getRemoteUserApi()));

    }

    public SplashViewModel getSplashViewModel(Application application) {

        return new SplashViewModel(application);

    }
}
