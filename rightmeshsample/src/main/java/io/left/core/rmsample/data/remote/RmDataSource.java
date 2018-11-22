package io.left.core.rmsample.data.remote;

import android.os.RemoteException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import core.left.io.framework.App;
import core.left.io.framework.application.data.remote.BaseRmDataSource;
import core.left.io.framework.application.data.remote.model.BaseMeshData;
import core.left.io.framework.application.data.remote.model.MeshAcknowledgement;
import core.left.io.framework.application.data.remote.model.MeshData;
import core.left.io.framework.application.data.remote.model.MeshPeer;
import io.left.core.rmsample.data.UserDataSource;
import io.left.core.rmsample.data.remote.model.UsableData;
import io.left.core.rmsample.data.remote.model.UserEntityResponse;
import io.left.core.util.helper.Constants;
import io.left.core.util.lib.gson.GSonHelper;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-08-07 at 12:41 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-08-07 at 12:41 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-08-07 at 12:41 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/
// TODO: 8/9/2018 Separate communicator with service and communicator with above App layer. This class
// now actually serving double purpose
public class RmDataSource extends BaseRmDataSource implements UserDataSource {

    private DataObserver mDataObserver;

    private final Map<Integer, AckObserver> mAckObserverMap;
    private final Map<String, UserEntity> mIdUserMap;

    private interface ProfileObserver {
        void onProfile(UserEntity userEntity);
        void onPeerGone(UserEntity userEntity);
    }

    private interface AckObserver {
        void onDataAckReceived(UserEntity userEntity);
        void onError(UserEntity userEntity);
    }

    private ProfileObserver mProfileObserver;
    private static RmDataSource mRmDataSource;
    private static final Object lock = new Object();

    private RmDataSource(byte[] profileInfo) {

        super(App.getContext(), profileInfo);

        mAckObserverMap = new ConcurrentHashMap<>();
        mIdUserMap = new ConcurrentHashMap<>();
    }

    public static synchronized RmDataSource getRmDataSource(UserEntity profileInfo) {

        if(mRmDataSource == null) {

            synchronized (lock) {

                if(mRmDataSource == null) {//In the blocking time object might have constructed from different thread
                    byte[] bytes = Objects.requireNonNull(GSonHelper.toJson(profileInfo)).getBytes();

                    mRmDataSource = new RmDataSource(bytes);
                }
            }
        }

        return mRmDataSource;
    }

    @Override
    public Single<UserEntity> pingUser(UserEntity userEntity) {

        return Single.create(e -> {

            AckObserver ackObserver = new AckObserver() {
                @Override
                public void onDataAckReceived(UserEntity userEntity) {
                    e.onSuccess(userEntity);
                }

                @Override
                public void onError(UserEntity userEntity) {
                    e.onError(new Throwable("Something wrong"));
                }
            };

//            e.setCancellable(() -> mIdUserMap.remove(userEntity.mUserId));

            MeshData meshData = new MeshData();
            meshData.mType = Constants.MeshDataType.PING;
            meshData.mData = "Hello".getBytes();
            meshData.mMeshPeer = new MeshPeer(userEntity.mUserId);
            try {
                mAckObserverMap.put(sendMeshData(meshData), ackObserver);
            } catch (RemoteException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void setDataListener(DataObserver dataObserver) {
        mDataObserver = dataObserver;
    }

    // Backpressure is buffer as we want all the peer properly and do not want to miss any intermediate
    // events
    @Override
    public Flowable<UserEntityResponse> getAllUsers() {
        return Flowable.create(e -> {

                    mProfileObserver = new ProfileObserver() {
                        @Override
                        public void onProfile(UserEntity userEntity) {

                            UserEntityResponse userEntityResponse = new UserEntityResponse(userEntity);
                            userEntityResponse.state = UserEntityResponse.ADDED;

                            e.onNext(userEntityResponse);
                        }

                        @Override
                        public void onPeerGone(UserEntity userEntity) {

                            UserEntityResponse userEntityResponse = new UserEntityResponse(userEntity);
                            userEntityResponse.state = UserEntityResponse.GONE;

                            e.onNext(userEntityResponse);

                        }
                    };

                    e.setCancellable(() -> mProfileObserver = null);

                },
                BackpressureStrategy.BUFFER);
    }


    @Override
    protected void onAttachedToService() {
        super.onAttachedToService();

        List<BaseMeshData> livePeers = getLivePeers();
        Timber.d("Live Peers retrieved. Size %d", livePeers.size());

        for(BaseMeshData baseMeshData : livePeers) {
            processPeerData(baseMeshData);
        }
    }

    //Direct RM part - should be transported in separate class
    @Override
    protected void onPeer(BaseMeshData baseMeshData) {
        // TODO: 8/8/2018 Use gson to prepare model from byte data
        Timber.d("Peer Event");

        processPeerData(baseMeshData);
    }

    private void processPeerData(BaseMeshData baseMeshData) {

        UserEntity userEntity = GSonHelper.fromJson(new String(baseMeshData.mData), UserEntity.class);

        if(mProfileObserver != null && userEntity != null) {

            //Ensuring single entry of same Id. Double check
            mIdUserMap.put(baseMeshData.mMeshPeer.getPeerId(), userEntity);

            userEntity.setUserId(baseMeshData.mMeshPeer.getPeerId());

            mProfileObserver.onProfile(userEntity);
        }
    }

    @Override
    protected void onPeerGone(MeshPeer meshPeer) {

        if(meshPeer != null && meshPeer.getPeerId() != null) {

            UserEntity userEntity = mIdUserMap.remove(meshPeer.getPeerId());
            Timber.d("%s Peer removed::based on::%s", userEntity, meshPeer.getPeerId());

            //Expected item count 1 only
            if(mProfileObserver != null && userEntity != null) {
                mProfileObserver.onPeerGone(userEntity);
            }
        }

    }

    @Override
    protected void onData(MeshData meshData) {

        if (meshData != null ) {
            Timber.d("Inside onData %s %s %s", meshData, meshData.mData, meshData.mMeshPeer);

            if( meshData.mData != null && meshData.mMeshPeer != null) {

                String st = new String(meshData.mData);
                UserEntity userEntity = mIdUserMap.get(meshData.mMeshPeer.toString());
                if(userEntity != null) {
                    mDataObserver.onDataReceived(new UsableData(userEntity, st));
                }

            }
        }

    }

    @Override
    protected void onAcknowledgement(MeshAcknowledgement meshAcknowledgement) {

        if(meshAcknowledgement != null) {

            AckObserver ackObserver = mAckObserverMap.remove(meshAcknowledgement.id);
            if (ackObserver != null) {

                ackObserver.onDataAckReceived(
                        mIdUserMap.get(meshAcknowledgement.mMeshPeer.getPeerId()));

            }
        }
    }
}
