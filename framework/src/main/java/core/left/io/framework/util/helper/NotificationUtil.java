package core.left.io.framework.util.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import core.left.io.framework.R;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-08-16 at 4:50 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-08-16 at 4:50 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-08-16 at 4:50 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class NotificationUtil {

    private static String getId(Context context) {
        return context.getString(R.string.app_name) + "_notification_id";
    }

    public static NotificationCompat.Builder getBuilder(Context context) {
        return getBuilder(context, getId(context));
    }

    public static NotificationCompat.Builder getBuilder(Context context, String id) {

        if(context == null || TextUtils.isEmpty(id)) {
            return null;
        }

        context = context.getApplicationContext();

        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(id, context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
            builder = new NotificationCompat.Builder(context, id);
        } else {
            //noinspection deprecation
            builder = new NotificationCompat.Builder(context);
        }

        return builder;
    }

}
