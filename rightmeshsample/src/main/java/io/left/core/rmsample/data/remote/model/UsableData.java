package io.left.core.rmsample.data.remote.model;

import io.left.core.rmsample.data.remote.UserEntity;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-09-12 at 1:32 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework - Copy.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-09-12 at 1:32 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-09-12 at 1:32 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class UsableData {

    public UserEntity mPeerUserEntity;
    public String mData;

    public UsableData(UserEntity peerUserEntity, String data) {
        mPeerUserEntity = peerUserEntity;
        mData = data;
    }
}
