package io.left.core.rmsample.ui.users;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import core.left.io.framework.application.ui.base.BaseRxViewModel;
import core.left.io.framework.util.helper.action.Event;
import io.left.core.rmsample.data.UserDataSource;
import io.left.core.rmsample.data.remote.UserEntity;
import io.left.core.rmsample.data.remote.model.UsableData;
import io.left.core.rmsample.data.remote.model.UserEntityResponse;
import timber.log.Timber;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-08-07 at 12:33 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-08-07 at 12:33 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-08-07 at 12:33 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class UsersViewModel extends BaseRxViewModel {

    private LiveData<UserEntityResponse> mUsersLiveData;
    private MutableLiveData<Event<UserEntity>> mPingData = new MutableLiveData<>();
    private MutableLiveData<Event<UsableData>> mData = new MutableLiveData<>();
    private UserDataSource mUserDataSource;

    public UsersViewModel(@NonNull UserDataSource userDataSource) {
        mUsersLiveData = LiveDataReactiveStreams.fromPublisher(userDataSource.
                getAllUsers());

        userDataSource.setDataListener(usableData -> mData.postValue(new Event<>(usableData)));

        mUserDataSource = userDataSource;
    }


    public LiveData<UserEntityResponse> getUsersLiveData() {
        return mUsersLiveData;
    }

    public LiveData<Event<UserEntity>> getPingData() {
        return mPingData;
    }

    public void ping(UserEntity userEntity) {

        if(userEntity != null) {
            getCompositeDisposable().add(mUserDataSource.pingUser(userEntity).
                    subscribe(userEntity1 -> {
                        mPingData.postValue(new Event<>(userEntity));
                    }, Timber::d)
            );
        }
    }

    public LiveData<Event<UsableData>> getData() {
        return mData;
    }
}
