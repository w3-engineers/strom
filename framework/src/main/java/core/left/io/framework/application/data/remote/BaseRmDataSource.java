package core.left.io.framework.application.data.remote;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import java.util.List;

import core.left.io.framework.App;
import core.left.io.framework.IRmCommunicator;
import core.left.io.framework.IRmServiceConnection;
import core.left.io.framework.application.data.remote.model.BaseMeshData;
import core.left.io.framework.application.data.remote.model.MeshAcknowledgement;
import core.left.io.framework.application.data.remote.model.MeshData;
import core.left.io.framework.application.data.remote.model.MeshPeer;
import core.left.io.framework.application.data.remote.service.BaseRmService;
import core.left.io.framework.util.Utility;
import core.left.io.framework.util.lib.mesh.ProfileManager;
import timber.log.Timber;

import static core.left.io.framework.util.Utility.getMyProcessIdList;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-08-07 at 1:11 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-08-07 at 1:11 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-08-07 at 1:11 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
// TODO: 8/8/2018 Add profile info to be sent from BaseService tyo manage discovery policy
public abstract class BaseRmDataSource extends IRmCommunicator.Stub {

    /**
     * Called upon receiving any Peer data
     * @param profileInfo
     */
    protected abstract void onPeer(BaseMeshData profileInfo);

    /**
     * Calls upon disappearing of peers
     * @param meshPeer
     */
    protected abstract void onPeerGone(MeshPeer meshPeer);

    /**
     * Upon receiving any data from any peer
     * @param meshData
     */
    protected abstract void onData(MeshData meshData);

    /**
     * Upon receiving Data delivery acknowledgement
     * @param meshAcknowledgement
     */
    protected abstract void onAcknowledgement(MeshAcknowledgement meshAcknowledgement);

    private IRmServiceConnection mIRmServiceConnection;

    //Android automatically maintain single service inside the system
    private byte[] mProfileInfo;

    private Context mContext;

    protected BaseRmDataSource(Context context, byte[] profileInfo) {

        //intentional hard string
        if(context == null)
            throw new NullPointerException("Context can not be null");

        mContext = context.getApplicationContext();
        this.mProfileInfo = profileInfo;
        initService(mContext);

    }

    private void initService(Context context) {

        Intent intent = new Intent(context, BaseRmService.class);

        //Normally this should not be required but found in some devices service restarts upon
        // calling start service. e.g: Symphony ZVi
        if(!Utility.isServiceRunning(context, BaseRmService.class)) {
            context.startService(intent);
        }
        context.bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);//check flag

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mIRmServiceConnection = IRmServiceConnection.Stub.asInterface(service);
            Timber.d("Service connected:%s", name.toShortString());

            try {

                mIRmServiceConnection.setRmCommunicator(BaseRmDataSource.this);
                mIRmServiceConnection.setProfile(mProfileInfo);

                onAttachedToService();

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Timber.d("Service disconnected:%s", name.toShortString());

        }
    };

    /**
     * Retrieve peers which are live with their profile info
     * @return {@link List} of {@link BaseMeshData} which represents live peers in the network
     */
    public List<BaseMeshData> getLivePeers() {

        try {

            return mIRmServiceConnection.getLivePeers();

        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }

        return null;
    }

    /**
     * To check underlying service properly initiated or not
     * @return true if connected
     */
    public boolean isServiceConnected() {
        return mIRmServiceConnection != null;
    }

    /**
     * If service is not initiated properly then this method throws {@link IllegalStateException}.
     * Before using the method check service initiation through {@link #isServiceConnected()}
     * @param isForeGround
     */
    public void setServiceForeground(boolean isForeGround) {

        if(mIRmServiceConnection == null) {
            throw new IllegalStateException("Service not initiated properly");
        }

        try {
            mIRmServiceConnection.setServiceForeground(isForeGround);

            if(mContext != null) {

                if (isForeGround) {
                    mContext.unbindService(mServiceConnection);
                } else {
                    initService(mContext);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    //AIDL remote call back. These abstracts AIDL complexity from app layer

    public final int sendMeshData(MeshData meshData) throws RemoteException {

        if(mIRmServiceConnection != null && meshData != null && meshData.mData != null &&
                meshData.mType != ProfileManager.MY_PROFILE_INFO_TYPE) {

            return mIRmServiceConnection.sendMeshData(meshData);

        } else {
            throw new IllegalStateException("Illegal state");
        }
    }

    @Override
    public final void onMeshData(MeshData meshData) throws RemoteException {
        onData(meshData);
    }

    @Override
    public final void onMeshAcknowledgement(MeshAcknowledgement meshAcknowledgement) throws RemoteException {
        onAcknowledgement(meshAcknowledgement);
    }

    @Override
    public final void onLibraryInitSuccess() throws RemoteException {
        onRmOn();
    }

    @Override
    public final void onProfileInfo(BaseMeshData profileInfo) throws RemoteException {
        onPeer(profileInfo);
    }

    @Override
    public final void onPeerRemoved(MeshPeer meshPeer) {
        onPeerGone(meshPeer);
    }

    // FIXME: 8/27/2018 Upon closing and restarting the application everything found fine except
    // other side node was not getting the event of handleDataReceived() although deliveryAck was being
    // generated properly at sender side and such PI was not properly showing at other side
    // We might check this later or developers can dig this issue later. Keeping for now due to time constraint
    @Override
    public final void onServiceDestroy() throws RemoteException {
        //remote AIDL service is destroyed, do some work if application needs to do anything

        //To kill RM library service, current app's local service and current app's process
        List<Integer> myProcessIdList = getMyProcessIdList(App.getContext());
        for(int pid : myProcessIdList) {
            Process.killProcess(pid);
        }
        Process.killProcess(Process.myPid());

        onRmOff();
    }

    /**
     * Overridable method to receive the event of Library init
     * @throws RemoteException
     */
    protected void onRmOn() { }

    /**
     * Overridable method to receive the event of library destroy
     * @throws RemoteException
     */
    protected void onRmOff() { }

    /**
     * Convenient method so that developers can get the service attached event.
     */
    protected void onAttachedToService() { }

}
