package core.left.io.sample.data;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import core.left.io.sample.LiveDataTestUtil;
import io.left.core.sample.data.database.user.LocalUserDataSource;
import io.left.core.sample.data.database.user.UserEntity;
import io.left.core.sample.data.local.database.Database;
import io.left.core.sample.data.remote.user.UserEntityResponse;
import io.left.core.sample.ui.details.DetailsViewModelLocal;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-11 at 3:51 PM].
 * <br>Email: azim@w3engineers.com
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
public class DetailsViewModelLocalTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private Database mDatabase;
    private DetailsViewModelLocal SUT;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                Database.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        LocalUserDataSource dataSource = new LocalUserDataSource(mDatabase.userDao());
        SUT = new DetailsViewModelLocal(dataSource);
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void localViewModel_emptyUsers_retrievingEmptyUsersProperly() throws InterruptedException {

        //action
        UserEntityResponse userEntityResponse = LiveDataTestUtil.getValue(SUT.getAllUsers());

        //assertion
        assertTrue(userEntityResponse.mUserEntities.isEmpty());

    }

    @Test
    public void localViewModel_users_retrievingUsersProperly() throws InterruptedException {

        //arrange
        UserEntity userEntity1 = new UserEntity("user 1");
        UserEntity userEntity2 = new UserEntity("user 2");
        SUT.updateOrInsert(userEntity1);
        SUT.updateOrInsert(userEntity2);

        //action
        UserEntityResponse userEntityResponse = LiveDataTestUtil.getValue(SUT.getAllUsers());

        //assertion
        assertThat(userEntityResponse.mUserEntities.size(), is(2));

    }
}
