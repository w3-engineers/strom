package core.left.io.sample.data.mock;

import java.util.ArrayList;
import java.util.List;

import io.left.core.sample.data.database.user.UserEntity;
import io.left.core.sample.data.remote.user.RemoteUserApi;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.mock.BehaviorDelegate;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-27 at 3:28 PM].
 * <br>Email:
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-27 at 3:28 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-27 at 3:28 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class MockRemoteUserApi implements RemoteUserApi {

    private BehaviorDelegate<RemoteUserApi> mRemoteUserApiBehaviorDelegate;

    public MockRemoteUserApi(BehaviorDelegate<RemoteUserApi> remoteUserApiBehaviorDelegate) {
        this.mRemoteUserApiBehaviorDelegate = remoteUserApiBehaviorDelegate;
    }

    @Override
    public Single<Response<List<UserEntity>>> getAllContributors(String repoOwner, String repo) {

        List<UserEntity> userEntities = new ArrayList<>();

        return mRemoteUserApiBehaviorDelegate.returningResponse(userEntities).getAllContributors
                ("repoOwner", "repo");
    }
}
