package com.w3engineers.ext.strom.util.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

import timber.log.Timber;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-11-09 at 10:57 AM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-11-09 at 10:57 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-11-09 at 10:57 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class BroadcastUtil {


    //https://commonsware.com/blog/2017/04/11/android-o-implicit-broadcast-ban.html
    /**
     * Send broadcast (Oreo safe)
     * @param context
     * @param intent
     */
    public static void sendImplicitBroadcast(Context context, Intent intent) {
        if(context == null || intent == null)
            return;

        PackageManager pm=context.getPackageManager();
        List<ResolveInfo> matches=pm.queryBroadcastReceivers(intent, 0);

        for (ResolveInfo resolveInfo : matches) {

            sendImplicitBroadcast(context, intent, resolveInfo.activityInfo.applicationInfo.packageName,
                    resolveInfo.activityInfo.name);
        }
    }


    /**
     * Send only current application specific. By nature a secured approach.
     * @param context
     * @param intent
     */
    public static void sendUniCast(Context context, Intent intent) {
        if(context == null || intent == null)
            return;

        String myPackageName = context.getApplicationContext().getPackageName();
        Timber.d(myPackageName);

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> matches = pm.queryBroadcastReceivers(intent, 0);

        for (ResolveInfo resolveInfo : matches) {

            if(myPackageName.equals(resolveInfo.activityInfo.applicationInfo.packageName)) {
                sendImplicitBroadcast(context, intent, myPackageName, resolveInfo.activityInfo.name);
                break;
            }
        }
    }

    private static void sendImplicitBroadcast(Context context, Intent intent, String packageName,
                                              String className) {

        Intent explicit = new Intent(intent);
        ComponentName cn = new ComponentName(packageName,
                className);

        explicit.setComponent(cn);
        context.sendBroadcast(explicit);

    }


}
