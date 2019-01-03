package com.w3engineers.ext.strom.application.data.helper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * ============================================================================
 * <br>Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-06-04 at 3:51 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: User should use {@link Service} by extending {@link BaseService}
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. Azim on [2018-06-04 at 3:51 PM].
 * <br>2. Azim
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. Azim on [2018-06-04 at 3:51 PM].
 * <br>2. Azim
 * <br>============================================================================<br>
 **/


 /**
  * Skeleton class to generate scope to update or extend native service life.
  * Developers must not call super class method. It would create an infinite loop.
 * e.g: super.oncreate(), super.onStartCommand() should not be called
 **/
public abstract class BaseService extends Service {

    protected void onCreateTask() {
        super.onCreate();
    };

    protected int onStartCommandTask(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    };

    protected abstract IBinder onBindTask(Intent intent);
    protected boolean onUnbindTask(Intent intent) {
        return super.onUnbind(intent);
    };
    protected void onDestroyTask() {}
    protected void onRemovedTask(Intent rootIntent) {}

    @Override
    public final void onCreate() {
        super.onCreate();
        onCreateTask();
    }


    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        return onStartCommandTask(intent, flags, startId);
    }

    @Nullable
    @Override
    public final IBinder onBind(Intent intent) {
        return onBindTask(intent);
    }

    @Override
    public final boolean onUnbind(Intent intent) {
        return onUnbindTask(intent);
    }

    @Override
    public final void onDestroy() {
        onDestroyTask();
    }

    @Override
    public final void onTaskRemoved(Intent rootIntent) {
        onRemovedTask(rootIntent);
    }
}
