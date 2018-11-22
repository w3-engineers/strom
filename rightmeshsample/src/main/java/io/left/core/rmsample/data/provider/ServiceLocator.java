package io.left.core.rmsample.data.provider;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-05 at 6:24 PM].
 * <br>Email: azim@w3engineers.com
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

import android.content.Context;

import core.left.io.framework.App;
import core.left.io.framework.application.data.BaseServiceLocator;
import core.left.io.framework.application.data.remote.BaseRmDataSource;
import io.left.core.rmsample.data.UserDataSource;
import io.left.core.rmsample.data.local.MyInfoProvider;
import io.left.core.rmsample.data.remote.RmDataSource;
import io.left.core.rmsample.ui.users.UsersViewModel;

/**
 * Service instance provider. We might upgrade this class later to provide various type of DataSource generator
 */
// TODO: 7/5/2018 We can generate a general interface and deploy common service locator for network, db, db+nework. Might be in next version
public class ServiceLocator extends BaseServiceLocator {

    private final static ServiceLocator SERVICE_LOCATOR = new ServiceLocator();

    private ServiceLocator() {}


    public static ServiceLocator getInstance() {
        return SERVICE_LOCATOR;
    }

    public UsersViewModel getUsersModel(Context context) {

        UserDataSource userDataSource = RmDataSource.getRmDataSource(new MyInfoProvider(context).getMyProfileInfo());
        return new UsersViewModel(userDataSource);

    }

    @Override
    public BaseRmDataSource getRmDataSource() {
        return RmDataSource.getRmDataSource(new MyInfoProvider(App.getContext()).getMyProfileInfo());
    }
}
