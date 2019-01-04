package io.left.core.sample.data.remote.user;

import com.w3engineers.ext.strom.application.data.BaseResponse;
import io.left.core.sample.data.database.user.UserDataSource;
import io.left.core.sample.data.database.user.UserEntity;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-25 at 11:27 AM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-25 at 11:27 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-25 at 11:27 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class RemoteUserDataSource implements UserDataSource {

    private RemoteUserApi mRemoteUserApi;

    public RemoteUserDataSource(RemoteUserApi remoteUserApi) {
        this.mRemoteUserApi = remoteUserApi;
    }

    @Override
    public Flowable<UserEntity> getUser(long id) {
        return null;
    }

    @Override
    public Flowable<UserEntityResponse> getAllUsers() { return null; }

    @Override
    public Single<UserEntityResponse> getAllNetworkUsers() {
        if(mRemoteUserApi == null) {
            UserEntityResponse userEntityResponse = new UserEntityResponse();
            userEntityResponse.mResponseCode = BaseResponse.RESPONSE_PARAMETER_ILLEGAL;
            return Single.just(userEntityResponse);
        }

        return mRemoteUserApi.getAllContributors("googlesamples", "android-architecture-components")
                .map(userEntitiesResponse -> {

                    if(userEntitiesResponse != null) {

                        UserEntityResponse userEntityResponse = new UserEntityResponse();
                        userEntityResponse.mResponseCode = userEntitiesResponse.code();
                        if(userEntitiesResponse.code() == BaseResponse.RESPONSE_SUCCESS) {

                            for (UserEntity userEntity : userEntitiesResponse.body()) {
                                userEntity.mIsFromNetwork = true;
                            }

                            userEntityResponse.mUserEntities = userEntitiesResponse.body();

                            return userEntityResponse;
                        }

                        userEntityResponse.mResponseMessage = userEntitiesResponse.message();
                        return userEntityResponse;
                    }

                    return null;
                }).onErrorResumeNext(throwable -> {
                    UserEntityResponse userEntityResponse = new UserEntityResponse();
                    userEntityResponse.mResponseCode = BaseResponse.RESPONSE_COULD_NOT_REACH_SERVER;
                    userEntityResponse.mResponseMessage = throwable.toString();
                    return Single.just(userEntityResponse);
                });
    }

    @Override
    public long insertOrUpdateUser(UserEntity userEntity) {
        return 0;
    }

    @Override
    public int delete(UserEntity userEntity) {
        return 0;
    }

}
