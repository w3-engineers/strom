package io.left.core.sample.data.database.user;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.databinding.Bindable;
import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import core.left.io.framework.application.data.helper.local.base.BaseEntity;
import io.left.core.sample.BR;
import io.left.core.sample.data.local.database.ColumnNames;
import io.left.core.sample.data.local.database.TableNames;

/*
 * ****************************************************************************
 * * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
 * *
 * * Created by:
 * * Name : Ahmed Mohmmad Ullah (Azim)
 * * Date : 2/15/18
 * * Email : azim@w3engineers.com
 * *
 * * Purpose: Sample of ROOM model
 * *
 * * Last Edited by : Mohd. Asfaq-E-Azam on 12/21/17.
 * * History: Comment Added
 * * 1:
 * * 2:
 * *
 * * Last Reviewed by : SUDIPTA KUMAR PAIK on 3/19/18.
 * ****************************************************************************
 */

@Entity(tableName = TableNames.USERS, indices = {@Index(value = {ColumnNames.ID}, unique = true)})
public class UserEntity extends BaseEntity {

    @Ignore
    public boolean mIsFromNetwork;

    @Bindable
    @ColumnInfo(name = ColumnNames.USER_NAME)
    @SerializedName("login")
    private String mUserName;

    public UserEntity(String userName) {
        mUserName = userName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
        //Two way binding to receive update from XML
        notifyPropertyChanged(BR.userName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUserName);
    }

    protected UserEntity(Parcel in) {
        this.mUserName = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };


    public UserEntity copy() {
        UserEntity userEntity = new UserEntity(mUserName);
        userEntity = (UserEntity) copy(userEntity);
        return userEntity;
    }
}