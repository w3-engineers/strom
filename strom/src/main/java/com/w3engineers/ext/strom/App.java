package com.w3engineers.ext.strom;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;

import com.w3engineers.ext.strom.util.helper.Toaster;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Anjan Debnath
* * Date : 10/25/17
* * Email : anjan@w3engineers.com
* *
* * Purpose: Base Application class
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 03/08/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/08/18.
* ****************************************************************************
*/

/**
 * All application layer common tasks
 */
public class App extends MultiDexApplication {
    private static App sContext;

    public static Context getContext() {

        if(sContext != null) {
            return sContext;
        }
        return null;
    }

    @Keep
    protected void plantTimber() {
        Timber.plant(new Timber.Tree() {

            @Override
            protected void log(int priority, String tag, String message, Throwable t) { }

            @Override
            protected boolean isLoggable(String tag, int priority) {
                //Do not like to log during release
                return false;
            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        //init timber
        plantTimber();

        //init crashlytics
//        todo check has string or not
        Fabric.with(this, new Crashlytics());

        Toaster.init(getResources().getColor(R.color.accent));
    }
}