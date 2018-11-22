package io.left.core.rmsample.data.remote.model;

import core.left.io.framework.application.data.helper.BaseResponse;
import io.left.core.rmsample.data.remote.UserEntity;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-08-10 at 11:07 AM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-08-10 at 11:07 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-08-10 at 11:07 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class UserEntityResponse extends BaseResponse {

    public static final int ADDED = 1;
    public static final int GONE = 2;

    public UserEntity mUserEntity;
    public int state;

    public UserEntityResponse(UserEntity userEntity) {
        this.mUserEntity = userEntity;
    }
}
