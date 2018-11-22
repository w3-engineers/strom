package core.left.io.framework.util;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-18 at 10:12 AM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-18 at 10:12 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-18 at 10:12 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

import android.text.format.DateUtils;

/**
 * Utility class to provide time related utility tasks
 */
public class TimeUtility {

    /**
     * Returns Time Ago String. Sample out put is
     * @param timeStamp
     * @return
     */
    public static String getTimeAgo(long timeStamp) {

        long now = System.currentTimeMillis();
        CharSequence ago = DateUtils.getRelativeTimeSpanString(timeStamp, now, DateUtils.MINUTE_IN_MILLIS);
        return ago.toString();

    }

}
