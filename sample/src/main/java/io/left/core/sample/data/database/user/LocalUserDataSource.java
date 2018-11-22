/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.left.core.sample.data.database.user;

import io.left.core.sample.data.remote.user.UserEntityResponse;
import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

/**
 * Using the Room database as a data source.
 */

public class LocalUserDataSource implements UserDataSource {

    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    @Override
    public Flowable<UserEntityResponse> getAllUsers() {
        Timber.d("Sending DB request");
        return mUserDao.getAllUsers();
    }

    @Override
    public Single<UserEntityResponse> getAllNetworkUsers() {
        return null;
    }

    @Override
    public Flowable<UserEntity> getUser(long id) {
        return mUserDao.getUser(id);
    }

    @Override
    public long insertOrUpdateUser(UserEntity userEntity) {
        return mUserDao.insertOrUpdate(userEntity);
    }

    @Override
    public int delete(UserEntity userEntity) {
        return mUserDao.delete(userEntity);
    }
}
