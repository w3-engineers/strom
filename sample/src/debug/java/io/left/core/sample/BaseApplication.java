package io.left.core.sample;

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

import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

import com.w3engineers.ext.strom.App;
import timber.log.Timber;

/**
 * All debug related tasks. parent class {@link App}
 */
public class BaseApplication extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
    }

    /**
     * Automatically display notification if memory leak occurs
     */
    protected void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.

            return;
        }
        //Strict mode has an issue from Oreo (8.0+). Waiting for Leakcanary update on fix
        enabledStrictMode();
        LeakCanary.install(this);
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
    }

    @Override
    protected void plantTimber() {
        Timber.plant(new Timber.DebugTree() {
            //Add line number and method name with tag
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                //The brace will generate clickable link in Logcat window
                //Stability depends on developers comfort level
                return "(" + element.getFileName() + ':' + element.getLineNumber() + "):"+element.getMethodName();
            }
        });
    }
}