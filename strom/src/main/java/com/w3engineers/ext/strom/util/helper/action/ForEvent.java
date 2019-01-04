package com.w3engineers.ext.strom.util.helper.action;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-17 at 12:14 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-17 at 12:14 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-17 at 12:14 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

/**
 * Intended to observe any particular action through live data. This Sub class facilitates to preserve
 * source data so that you can check for which data the response is.
 * (Developers comment) https://medium.com/google-developers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
public class ForEvent<U, T> extends Event<T> {

    private U mForData;

    public ForEvent(U forData, T content) {
        super(content);
        this.mForData = forData;

    }

    public U getForData() {
        return mForData;
    }

}
