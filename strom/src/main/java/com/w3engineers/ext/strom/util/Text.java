package com.w3engineers.ext.strom.util;

import android.text.Editable;
import android.text.TextUtils;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-10-09 at 10:19 AM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework - Copy.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-10-09 at 10:19 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-10-09 at 10:19 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class Text {

    /**
     * Convenient method to save developer time
     * @param text
     * @return
     */
    public static boolean isNotEmpty(String text) {
        return !TextUtils.isEmpty(text);
    }

    /**
     * Convenient method to save developer time
     * @param text
     * @return
     */
    public static boolean isNotEmpty(CharSequence text) {
        return !TextUtils.isEmpty(text);
    }

    /**
     * Convenient method to save developer time
     * @param text
     * @return
     */
    public static boolean isNotEmpty(Editable text) {
        return !TextUtils.isEmpty(text);
    }

}
