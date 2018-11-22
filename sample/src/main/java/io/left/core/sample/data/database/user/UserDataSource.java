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

/**
 * Access point for managing user data. Source may vary but data dispatch format would be same.
 * Except that direct network responses would be send back by Single normally as we expect a response or
 * error rather a continuous stream. Although handling any push or chat kind of message then the wiring
 * should be through Flowable if we explicitly want data to directly populate at UI rather DB via. Like
 * chat.
 */
public interface UserDataSource {

    /**
     * Gets the user from the data source.
     *
     * @return the user from the data source.
     * @param id
     */
    Flowable<UserEntity> getUser(long id);
    /**
     * Gets all the users from the data source. Normally from persistence storage
     *
     * @return users from the data source.
     */
    Flowable<UserEntityResponse> getAllUsers();

    /**
     * Normally to retrieve from Network dicontinous source
     * @return
     */
    Single<UserEntityResponse> getAllNetworkUsers();

    /**
     * Inserts the user into the data source, or, if this is an existing user, updates it.
     *
     * @param userEntity the user to be inserted or updated.
     */
    long insertOrUpdateUser(UserEntity userEntity);

    /**
     * Deletes all users from the data source.
     */
    int delete(UserEntity userEntity);
}