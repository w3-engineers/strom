package com.w3engineers.ext.strom.application.data;

import java.net.HttpURLConnection;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-26 at 3:36 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-26 at 3:36 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-26 at 3:36 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public abstract class BaseResponse {

    public static final int RESPONSE_SUCCESS = HttpURLConnection.HTTP_OK;
    public static final int RESPONSE_PARAMETER_ILLEGAL = 1;//Caller did not set parameter properly
    public static final int RESPONSE_COULD_NOT_REACH_SERVER = 2;//Caller did not set parameter properly

    public int mResponseCode = RESPONSE_SUCCESS;

    public String mResponseMessage;
}
