package core.left.io.sample.data;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import core.left.io.sample.LiveDataTestUtil;
import core.left.io.sample.data.mock.MockRemoteUserApi;
import io.left.core.sample.data.remote.RemoteApiProvider;
import io.left.core.sample.data.remote.user.RemoteUserApi;
import io.left.core.sample.data.remote.user.RemoteUserDataSource;
import io.left.core.sample.data.remote.user.UserEntityResponse;
import io.left.core.sample.ui.details.DetailsViewModelRemote;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-11 at 3:51 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-11 at 3:51 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-11 at 3:51 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
@RunWith(AndroidJUnit4.class)
public class DetailsViewModelRemoteTest extends InstrumentationTestCase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MockRetrofit mMockRetrofit;
    private Retrofit mRetrofit;
    private RemoteUserDataSource mDataSource;
    private DetailsViewModelRemote SUT;

    @Before
    public void init() throws Exception {

        //Original Retrofit
        mRetrofit = new Retrofit.Builder().baseUrl(RemoteApiProvider.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.trampoline()))
                .build();

        NetworkBehavior behavior = NetworkBehavior.create();

        //Mocked Retrofit
        mMockRetrofit = new MockRetrofit.Builder(mRetrofit)
                .networkBehavior(behavior)
                .build();

        //Mock Api delegate
        BehaviorDelegate<RemoteUserApi> mockRemoteUserApiBehaviorDelegate = mMockRetrofit.create(RemoteUserApi.class);
        //Mocked retrofit Api
        RemoteUserApi mockedRemoteUserApi = new MockRemoteUserApi(mockRemoteUserApiBehaviorDelegate);

        mDataSource = new RemoteUserDataSource(mockedRemoteUserApi);
        SUT = new DetailsViewModelRemote(mDataSource);
    }

    //We can precisely manipulate all response object even for Retrofit
    //If you are interested then please check:
    //https://github.com/riggaroo/android-retrofit-test-examples/blob/master/RetrofitTestExample/app/src/androidTest/java/za/co/riggaroo/retrofittestexample/QuoteOfTheDayMockAdapterTest.java
    //pretty nice link for retrofit tests to understand
    @Test
    public void remoteViewModel_emptyUsers_retrievingEmptyUsersProperly() throws InterruptedException {

        //action
        UserEntityResponse userEntityResponse = LiveDataTestUtil.getValue(SUT.getAllUsers());

        //assertion
        assertTrue(userEntityResponse.mUserEntities.isEmpty());

    }
}
