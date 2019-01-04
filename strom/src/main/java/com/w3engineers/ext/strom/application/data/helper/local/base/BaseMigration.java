package com.w3engineers.ext.strom.application.data.helper.local.base;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-06-26 at 4:11 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-06-26 at 4:11 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-06-26 at 4:11 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

/**
 * To add DB migration create this object by putting targeted version to write the query script.
 * Will add these objects as varargs during db creation. Then managing of this migration queries will
 * be done by {@link BaseDatabase}
 */
public class BaseMigration {

    public BaseMigration(int targetedVersion, String queryScript) {
        mTargetedVersion = targetedVersion;
        mQueryScript = queryScript;
    }

    public int getTargetedVersion() {
        return mTargetedVersion;
    }

    public String getQueryScript() {
        return mQueryScript;
    }

    private int mTargetedVersion;
    private String mQueryScript;

}
