package io.left.core.sample.ui.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;

import core.left.io.framework.application.ui.base.BaseRxViewModel;
import core.left.io.framework.util.helper.action.ForEvent;
import io.left.core.sample.data.database.user.UserDataSource;
import io.left.core.sample.data.database.user.UserEntity;
import io.left.core.sample.data.remote.user.UserEntityResponse;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
public class DetailsViewModelLocal extends BaseRxViewModel {

    private UserDataSource mUserDataSource;
    private LiveData<UserEntityResponse> mUserEntityListLiveData;
    private MutableLiveData<ForEvent<UserEntity, Long>> mUpdateOrInsertEvent = new MutableLiveData<>();
    private MutableLiveData<ForEvent<UserEntity, Integer>> mDeleteUserEvent = new MutableLiveData<>();

    public MutableLiveData<ForEvent<UserEntity, Long>> getUpdateOrInsertEvent() {
        return mUpdateOrInsertEvent;
    }

    public MutableLiveData<ForEvent<UserEntity, Integer>> getDeleteEvent() {
        return mDeleteUserEvent;
    }

    public DetailsViewModelLocal(UserDataSource userDataSource) {
        this.mUserDataSource = userDataSource;

        mUserEntityListLiveData = LiveDataReactiveStreams.fromPublisher(
                mUserDataSource.getAllUsers());
    }

    public LiveData<UserEntityResponse> getAllUsers() {

        return mUserEntityListLiveData;

    }

    public void updateOrInsert(UserEntity userEntity) {

        if(userEntity == null)
            return;

        //Rx at model layer turning to LiveData for UI invoker
        getCompositeDisposable().add(Observable.fromCallable(() -> mUserDataSource.insertOrUpdateUser(userEntity))
                .subscribeOn(Schedulers.io())
                //Background thread call. So, posting value
                .subscribe(updateCount -> {
                            Timber.d(updateCount.toString());
                            mUpdateOrInsertEvent.postValue(new ForEvent<>(userEntity, updateCount));
                        },
                        throwable -> Timber.d("Could not update: %s. Exception: %s",
                                userEntity.getUserName(), throwable.toString())));
    }

    public void delete(UserEntity userEntity) {

        if(userEntity == null)
            return;

        //Rx at model layer turning to LiveData for UI invoker
        getCompositeDisposable().add(Observable.fromCallable(() -> mUserDataSource.delete(userEntity))
                .subscribeOn(Schedulers.io())
                //Background thread call. So, posting value
                .subscribe(updateCount -> mDeleteUserEvent.postValue(new ForEvent<>(userEntity, updateCount)),
                        throwable -> Timber.d("Could not update: %s. Exception: %s",
                                userEntity.getUserName(), throwable.toString())));
    }
}
