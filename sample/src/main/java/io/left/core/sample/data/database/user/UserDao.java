package io.left.core.sample.data.database.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import com.w3engineers.ext.strom.application.data.helper.local.base.BaseDao;
import io.left.core.sample.data.local.database.ColumnNames;
import io.left.core.sample.data.local.database.TableNames;
import io.left.core.sample.data.remote.user.UserEntityResponse;
import io.reactivex.Flowable;

/*
 * ****************************************************************************
 * * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
 * *
 * * Created by:
 * * Name : Ahmed Mohmmad Ullah (Azim)
 * * Date : 2/15/18
 * * Email : azim@w3engineers.com
 * *
 * * Purpose: Data Access Object for the users table.
 * *
 * * Last Edited by : Ahmed Mohmmad Ullah (Azim) on 12/21/17.
 * * History: Comment Added
 * * 1:
 * * 2:
 * *
 * * Last Reviewed by : SUDIPTA KUMAR PAIK on 3/19/18.
 * ****************************************************************************
 */
@Dao
public abstract class UserDao implements BaseDao<UserEntity> {


    @Query("SELECT * FROM " + TableNames.USERS)
    protected abstract Flowable<List<UserEntity>> getAllUsersList();

    Flowable<UserEntityResponse> getAllUsers() {
        return getAllUsersList().distinctUntilChanged().map(UserEntityResponse::new);
    }

    @Query("SELECT * FROM " + TableNames.USERS + " WHERE " + ColumnNames.ID + " = :id")
    abstract Flowable<UserEntity> getUser(long id);

    @Query("SELECT * FROM " + TableNames.USERS + " WHERE " + ColumnNames.ID + " = :id")
    abstract UserEntity getUserById(long id);

    /**
     * Delete all users.
     */
    @Query("DELETE FROM " + TableNames.USERS)
    abstract void deleteAllUsers();
}