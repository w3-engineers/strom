package com.w3engineers.ext.strom.application.data.helper.local.base;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-06-22 at 3:22 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-06-22 at 3:22 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-06-22 at 3:22 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Any Dao which intends to have CUD(create, update, delete) operations.
 * Variable arguments and list type arguments both are supported
 */
public interface BaseDao<T> {

    /**
     * Insert vararg objects in the database.
     *
     * @param rows the objects to be inserted.
     * @return inserted rows id
     */
    @SuppressWarnings("unchecked")
    @Insert
    long[] insert(T... rows);//varargs
    /**
     * Insert vararg objects in the database.
     *
     * @param row the objects to be inserted.
     * @return inserted rows id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrUpdate(T row);//varargs

    /**
     * Insert vararg objects in the database.
     *
     * @param rows the objects to be inserted.
     * @return inserted rows id
     */
    @Insert
    long[] insert(List<T> rows);

    /**
     * Update object from the database. The update is on primary key
     *
     * @param objects the object to be updated
     * @return affected rows count
     */
    @SuppressWarnings("unchecked")
    @Update
    int update(T... objects);

    /**
     * Update object from the database. The update is on primary key
     *
     * @param objects the object to be updated
     * @return affected rows count
     */
    @Update
    int update(List<T> objects);

    /**
     * Delete an object from the database based on primary key
     * Developers can generate own deleteAll (normally wouldn't need much)
     * @param objects the object to be deleted
     * @return affected rows count
     */
    @SuppressWarnings("unchecked")
    @Delete
    int delete(T... objects);

    /**
     * Delete an object from the database based on primary key
     * @param objects the object to be deleted
     * @return affected rows count
     */
    @Delete
    int delete(List<T> objects);

}
