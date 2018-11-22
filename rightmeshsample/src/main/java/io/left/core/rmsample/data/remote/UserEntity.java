package io.left.core.rmsample.data.remote;

import com.google.gson.annotations.SerializedName;

import core.left.io.framework.util.collections.Matchable;
import io.left.core.util.helper.Constants;
import io.left.core.util.lib.gson.Exclude;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-08-07 at 12:43 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-08-07 at 12:43 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-08-07 at 12:43 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class UserEntity implements Matchable<String> {

    @SerializedName(Constants.Profile.userName)
    public String mUserName;

    @Exclude
    public String mUserId;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    @Override
    public String getMatcher() {
        return mUserId;
    }
}
