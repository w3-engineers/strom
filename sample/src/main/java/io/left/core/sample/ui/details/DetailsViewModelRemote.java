package io.left.core.sample.ui.details;

import android.arch.lifecycle.MutableLiveData;

import com.w3engineers.ext.strom.application.ui.base.BaseRxViewModel;
import io.left.core.sample.data.database.user.UserDataSource;
import io.left.core.sample.data.remote.user.UserEntityResponse;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-05 at 1:39 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-05 at 1:39 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-05 at 1:39 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class DetailsViewModelRemote extends BaseRxViewModel {

    private UserDataSource mUserDataSource;
    private MutableLiveData<UserEntityResponse> mUserEntityResponseMutableLiveData = new MutableLiveData<>();

    public DetailsViewModelRemote(UserDataSource userDataSource) {
        this.mUserDataSource = userDataSource;

        getAllNetworkUsers();
    }

    private void getAllNetworkUsers() {
        getCompositeDisposable().add(mUserDataSource.getAllNetworkUsers()
        .subscribe(userEntityResponse -> {
            mUserEntityResponseMutableLiveData.postValue(userEntityResponse);
        }));
    }

    public MutableLiveData<UserEntityResponse> getAllUsers() {

        return mUserEntityResponseMutableLiveData;

    }
}
