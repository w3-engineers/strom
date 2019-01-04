package io.left.core.sample.ui.datainput;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;

import com.w3engineers.ext.strom.application.ui.base.BaseRxViewModel;
import io.left.core.sample.data.database.user.UserDataSource;
import io.left.core.sample.data.database.user.UserEntity;
import io.reactivex.Completable;
import io.reactivex.functions.Action;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-05 at 1:39 PM].
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
public class DataInputViewModel extends BaseRxViewModel {

    private UserDataSource mUserDataSource;
    private LiveData<UserEntity> mMUserEntityLiveData;

    public DataInputViewModel(UserDataSource userDataSource) {
        this.mUserDataSource = userDataSource;
    }

    public LiveData<UserEntity> getUser(long id) {
        return LiveDataReactiveStreams.fromPublisher(mUserDataSource.getUser(id).distinctUntilChanged());
    }

    public Completable insertOrUpdate(UserEntity userEntity) {

        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                mUserDataSource.insertOrUpdateUser(userEntity);
            }
        });
    }
}
