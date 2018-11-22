package io.left.core.rmsample.data.local;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import core.left.io.framework.application.data.remote.model.MeshData;
import io.left.core.rmsample.R;
import io.left.core.rmsample.data.provider.ServiceLocator;
import timber.log.Timber;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-11-09 at 10:43 AM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-11-09 at 10:43 AM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-11-09 at 10:43 AM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
public class OnTaskClearMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Store data inside DB and send notification or any action upon receiving message

        try {

            if(intent != null) {

                new Thread(() -> {

                    //generate string constant for key
                    MeshData meshData = intent.getParcelableExtra(context.getString(R.string.mesh_data));
                    if(meshData != null && meshData.mData != null && meshData.mMeshPeer != null) {
                        Timber.d("Message inside broadcast from %s, content: %s",
                                meshData.mMeshPeer.toString(), new String(meshData.mData));
                    }

                }).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Initiating Rm Data Source
        ServiceLocator.getInstance().getRmDataSource();

    }

}
