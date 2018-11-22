package core.left.io.framework.application.data.remote.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import core.left.io.framework.IRmCommunicator;
import core.left.io.framework.IRmServiceConnection;
import core.left.io.framework.R;
import core.left.io.framework.application.data.remote.model.BaseMeshData;
import core.left.io.framework.application.data.remote.model.MeshAcknowledgement;
import core.left.io.framework.application.data.remote.model.MeshData;
import core.left.io.framework.application.data.remote.model.MeshPeer;
import core.left.io.framework.util.BroadcastUtil;
import core.left.io.framework.util.Text;
import core.left.io.framework.util.lib.mesh.IMeshCallBack;
import core.left.io.framework.util.lib.mesh.MeshConfig;
import core.left.io.framework.util.lib.mesh.MeshProvider;
import timber.log.Timber;


/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-08-07 at 1:10 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-08-07 at 1:10 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-08-07 at 1:10 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
// TODO: 8/8/2018 Should override Baservice to stop services properly???
public class BaseRmService extends Service implements IMeshCallBack {

    private List<IRmCommunicator> mIRmCommunicators;
    private MeshProvider mMeshProvider;
    private boolean mIsServiceToCloseWithTask;
    private boolean mIsTaskCleared;
    private String mBroadcastActionString;

    @Override
    public void onCreate() {
        super.onCreate();
        mIRmCommunicators = new ArrayList<>();
        mBroadcastActionString = getApplicationContext().getPackageName();

        initMesh();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null) {
            String action = intent.getAction();

            if(Text.isNotEmpty(action)) {

                switch (action) {
                    case BaseRmServiceNotificationHelper.ACTION_STOP_SERVICE:

                        shutTheService();

                        break;
                }

            }
        }

        return START_STICKY;
    }

    private void initMesh() {
        MeshConfig meshConfig = new MeshConfig();

        //Please do not modify here, this is an auto generated property from developers
        //local.properties class
        meshConfig.mSsid = getString(R.string.rm_ssid);
        meshConfig.mPort = getResources().getInteger(R.integer.port_number);
        Timber.d("Local Remote Service initiating with port number:%d ssid:%s", meshConfig.mPort,
                meshConfig.mSsid);

        mMeshProvider = MeshProvider.getInstance();
        mMeshProvider.setMeshConfig(meshConfig);
        mMeshProvider.setIMeshCallBack(this);
        mMeshProvider.start(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Any task is available now as connection is being established
        mIsTaskCleared = false;
        return mIRmServiceConnection;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        //Any task is available now as connection is being established
        mIsTaskCleared = false;
    }

    private final IRmServiceConnection.Stub mIRmServiceConnection = new IRmServiceConnection.Stub() {

        @Override
        public List<BaseMeshData> getLivePeers() {
            return mMeshProvider.getLivePeers();
        }

        @Override
        public void setBroadCastActionString(String actionString) throws RemoteException {
            if(Text.isNotEmpty(actionString)) {
                mBroadcastActionString = actionString;
            }
        }

        @Override
        public void setServiceToCloseWithTask(boolean isToCloseWithTask) throws RemoteException {
            mIsServiceToCloseWithTask = isToCloseWithTask;
        }

        @Override
        public void setProfile(byte[] profileInfo) throws RemoteException {
            mMeshProvider.setProfileInfo(profileInfo);
        }

        @Override
        public int sendMeshData(MeshData meshData) throws RemoteException {
            return mMeshProvider.sendData(meshData);
        }

        @Override
        public void setRmCommunicator(IRmCommunicator iRmCommunicator) throws RemoteException {

            //Any task is available now as connection is being established
            mIsTaskCleared = false;
            mIRmCommunicators.add(iRmCommunicator);

        }

        @Override
        public void setServiceForeground(boolean isForeGround) throws RemoteException {

            if(isForeGround) {
                new BaseRmServiceNotificationHelper(BaseRmService.this).startForegroundService();
            } else {
                new BaseRmServiceNotificationHelper(BaseRmService.this).stopForegroundService();
            }
        }

        @Override
        public boolean resetCommunicator(IRmCommunicator iRmCommunicator) throws RemoteException {
            return mIRmCommunicators.remove(iRmCommunicator);
        }
    };

    @Override
    public void onMesh(MeshData meshData) {

        if(mIsTaskCleared) {//No task is there need to broadcast data

            Intent intent = new Intent(mBroadcastActionString);
            intent.putExtra(getString(R.string.mesh_data), meshData);
            BroadcastUtil.sendUniCast(getApplicationContext(), intent);

        } else {
            for (IRmCommunicator iRmCommunicator : mIRmCommunicators) {

                if (iRmCommunicator != null) {
                    try {
                        iRmCommunicator.onMeshData(meshData);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        // TODO: 11/5/2018 delete instance as this one is a dead instance
                    }
                }

            }
        }

    }

    @Override
    public void onMesh(MeshAcknowledgement meshAcknowledgement) {

        for(IRmCommunicator iRmCommunicator : mIRmCommunicators) {

            if(iRmCommunicator != null) {
                try {
                    iRmCommunicator.onMeshAcknowledgement(meshAcknowledgement);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void onProfileInfo(BaseMeshData baseMeshData) {

        for(IRmCommunicator iRmCommunicator : mIRmCommunicators) {

            if(iRmCommunicator != null) {
                try {
                    iRmCommunicator.onProfileInfo(baseMeshData);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onPeerRemoved(MeshPeer meshPeer) {

        for(IRmCommunicator iRmCommunicator : mIRmCommunicators) {

            if(iRmCommunicator != null) {
                try {
                    iRmCommunicator.onPeerRemoved(meshPeer);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onInitSuccess(MeshPeer selfMeshPeer) {

        for(IRmCommunicator iRmCommunicator : mIRmCommunicators) {

            if(iRmCommunicator != null) {
                try {
                    iRmCommunicator.onLibraryInitSuccess();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onInitFailed(int reason) {
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if(mIsServiceToCloseWithTask) {
            shutTheService();
        }

        mIsTaskCleared = true;
    }

    private void shutTheService() {

        //Removing notification if present
        new BaseRmServiceNotificationHelper(this).stopForegroundService();

        //Stopping mesh
        if(mMeshProvider != null) {
            mMeshProvider.stop();
            mMeshProvider = null;
        }

        //Stopping service
        super.stopSelf();

        //Sending call back to app layer so that corresponding process can be cleared
        for(IRmCommunicator iRmCommunicator : mIRmCommunicators) {

            if(iRmCommunicator != null) {
                try {
                    iRmCommunicator.onServiceDestroy();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
