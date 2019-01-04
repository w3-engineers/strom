package core.left.io.sample.ui.details;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-11 at 11:02 AM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-11 at 11:02 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-11 at 11:02 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import core.left.io.util.LiveDataTestUtil;
import core.left.io.util.TestObserver;
import core.left.io.util.rule.RxSchedulerRule;
import io.left.core.sample.data.database.user.UserDataSource;
import io.left.core.sample.data.database.user.UserEntity;
import io.left.core.sample.data.remote.user.UserEntityResponse;
import io.left.core.sample.ui.details.DetailsViewModelLocal;
import io.reactivex.Flowable;

import static org.mockito.Mockito.when;

/**
 * Unit test for {@link DetailsViewModelLocal}
 */

public class DetailsViewModelLocalTest {


    //Ensure synchronous behavior for LiveData
    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    //Ensure synchronous behavior for RxJava
    @Rule
    public RxSchedulerRule mRxSchedulerRule = new RxSchedulerRule();

    @Mock
    private UserDataSource mDataSource;

    private DetailsViewModelLocal SUT;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //https://medium.com/@nicolas.duponchel/testing-viewmodel-in-mvvm-using-livedata-and-rxjava-b27878495220
    //If you are interested then check above article. It helped a lot.
    @Test
    public void localViewModel_emptyUsers_retrievingEmptyUsersProperly() throws InterruptedException {
        //arrange
        UserEntityResponse userEntityResponse = new UserEntityResponse(new ArrayList<>());
        when(mDataSource.getAllUsers()).thenReturn(Flowable.just(userEntityResponse));
        SUT = new DetailsViewModelLocal(mDataSource);

        //action
        TestObserver<UserEntityResponse> testObserver = LiveDataTestUtil.testObserve(SUT.getAllUsers());

        //assertion
        Truth.assert_().that(testObserver.observedvalues.get(0).mUserEntities).isEmpty();
    }


    @Test
    public void localViewModel_users_retrievingExistingUsersProperly() throws InterruptedException {

        //arrange
        UserEntity userEntity1 = new UserEntity("user 1");
        UserEntity userEntity2 = new UserEntity("user 2");
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity1);
        userEntities.add(userEntity2);
        UserEntityResponse userEntityResponse = new UserEntityResponse(userEntities);
        when(mDataSource.getAllUsers()).thenReturn(Flowable.just(userEntityResponse));
        SUT = new DetailsViewModelLocal(mDataSource);
        SUT.updateOrInsert(userEntity1);
        SUT.updateOrInsert(userEntity2);

        //action
        TestObserver<UserEntityResponse> testObserver = LiveDataTestUtil.testObserve(SUT.getAllUsers());

        //assertion
        Truth.assert_().that(testObserver.observedvalues.get(0).mUserEntities.size()).isEqualTo(userEntities.size());

    }

}