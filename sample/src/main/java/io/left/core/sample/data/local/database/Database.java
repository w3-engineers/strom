package io.left.core.sample.data.local.database;

import android.content.Context;

import com.w3engineers.ext.strom.application.data.helper.local.base.BaseDatabase;
import com.w3engineers.ext.strom.application.data.helper.local.base.BaseMigration;
import io.left.core.sample.BuildConfig;
import io.left.core.sample.R;
import io.left.core.sample.data.database.user.UserDao;
import io.left.core.sample.data.database.user.UserEntity;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-07-05 at 3:45 PM].
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-07-05 at 3:45 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-07-05 at 3:45 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

@android.arch.persistence.room.Database(entities = {UserEntity.class},
        version = BuildConfig.VERSION_CODE)//DB version will be aligned with App version,
// migration will be given by developer only when schema changes occur
public abstract class Database extends BaseDatabase {



    public abstract UserDao userDao();

    public static Database getInstance(Context context) {
        if (sInstance == null) {
            synchronized (Database.class) {
                if (sInstance == null) {
                    sInstance = createDb(context, context.getString(R.string.app_name), Database.class
                            , 1, new BaseMigration(1, null));//normally initial version is always 1
                }
            }
        }
        return (Database) sInstance;
    }

}
