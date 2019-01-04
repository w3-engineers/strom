package io.left.core.sample.data.remote.user;

import java.util.List;

import io.left.core.sample.data.database.user.UserEntity;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-25 at 11:45 AM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-25 at 11:45 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-25 at 11:45 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public interface RemoteUserApi {

//    @GET("/repos/googlesamples/android-architecture-components/contributors")
    @GET("/repos/{repo_owner}/{repo}/contributors")
    Single<Response<List<UserEntity>>> getAllContributors(@Path("repo_owner") String repoOwner,
                                                          @Path("repo") String repo);

}
