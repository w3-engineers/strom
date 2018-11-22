package core.left.io.framework.application.ui.base;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import core.left.io.framework.util.helper.data.local.SharedPref;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-10-05 at 10:45 AM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework - Copy.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-10-05 at 10:45 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-10-05 at 10:45 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

/**
 * This class fetch the registration status and provide the delay mechanism automatically
 */
public class BaseSplashViewModel extends BaseRxAndroidViewModel {

    /**
     * Developers can override this value directly
     */
    protected long mDelay = 3 * 1000;

    private MutableLiveData<Integer> getStatus;

    public BaseSplashViewModel(Application application) {

        super(application);
        this.getStatus = new MutableLiveData<>();

    }

    /**
     * Return the livedata to observe also starts scheduler to wait for {@link #mDelay}.
     * this method should call only once. Calling multiple time will trigger LiveData multiple time
     * @param keyValue SharedPreference key for regsitration status
     * @param delay expected delay in mili seconds
     * @return live data which observes for registration status
     */
    public LiveData<Integer> getRegistrationStatusWithDelay(String keyValue, long delay) {
        mDelay = delay;
        return getRegistrationStatusWithDelay(keyValue);
    }

    /**     *
     * Return the livedata to observe also starts scheduler to wait for {@link #mDelay}.
     * this method should call only once. Calling multiple time will trigger LiveData multiple time
     * @param keyValue SharedPreference key for regsitration status
     * @return live data which observes for registration status
     */
    public LiveData<Integer> getRegistrationStatusWithDelay(String keyValue) {

        getCompositeDisposable().add(getRegStatus(keyValue).//On IO thread
                delay(mDelay, TimeUnit.MILLISECONDS, Schedulers.newThread()).//By default on computation thread, so forced to new thread
                subscribe(status -> getStatus.postValue(status)));//No thread specified as LiveData post things on UI thread on it's own

        return getStatus;
    }

    private Observable<Integer> getRegStatus(String key) {
        return Observable.fromCallable(() ->
                SharedPref.getSharedPref(getApplication().getApplicationContext()).readInt(key)).
                subscribeOn(Schedulers.io());
    }

}
