package io.left.core.rmsample.data.local;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import io.left.core.rmsample.data.remote.UserEntity;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-08-09 at 1:29 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-08-09 at 1:29 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-08-09 at 1:29 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class MyInfoProvider {

    private Context mContext;

    public MyInfoProvider(Context context) {
        this.mContext = context;
    }

    public String getUsername(Context context) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type
            // values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;
        } else
            return null;
    }

    public UserEntity getMyProfileInfo() {

        UserEntity userEntity = new UserEntity();
        userEntity.mUserName = getUsername(mContext);

        return userEntity;
    }

}
