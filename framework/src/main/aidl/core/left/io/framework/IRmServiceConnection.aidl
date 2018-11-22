// IRmServiceConnection.aidl
package core.left.io.framework;

import core.left.io.framework.IRmCommunicator;
import core.left.io.framework.application.data.remote.model.MeshData;
import core.left.io.framework.application.data.remote.model.BaseMeshData;

interface IRmServiceConnection {

    void setBroadCastActionString(in String actionString);

    void setServiceToCloseWithTask(in boolean isToCloseWithTask);

    void setProfile(in byte[] profileInfo);

    int sendMeshData(in MeshData meshData);

    void setRmCommunicator(IRmCommunicator iRmCommunicator);

    void setServiceForeground(in boolean isForeGround);

    boolean resetCommunicator(IRmCommunicator iRmCommunicator);

    List<BaseMeshData> getLivePeers();
}
