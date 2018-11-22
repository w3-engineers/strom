package io.left.core.sample.data.remote.user;

import java.util.List;

import core.left.io.framework.application.data.helper.BaseResponse;
import io.left.core.sample.data.database.user.UserEntity;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-25 at 3:18 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-25 at 3:18 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-25 at 3:18 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class UserEntityResponse extends BaseResponse {

    public List<UserEntity> mUserEntities;

    public UserEntityResponse(List<UserEntity> userEntities) {
        this.mUserEntities = userEntities;
    }

    public UserEntityResponse() {}
}
