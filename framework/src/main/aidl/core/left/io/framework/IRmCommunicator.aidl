// IRmServiceConnection.aidl
package core.left.io.framework;

import core.left.io.framework.application.data.remote.model.BaseMeshData;
import core.left.io.framework.application.data.remote.model.MeshData;
import core.left.io.framework.application.data.remote.model.MeshPeer;
import core.left.io.framework.application.data.remote.model.MeshAcknowledgement;

interface IRmCommunicator {

    void onLibraryInitSuccess();

    void onServiceDestroy();

    void onProfileInfo(in BaseMeshData baseMeshData);

    void onPeerRemoved(in MeshPeer meshPeer);

    void onMeshData(in MeshData meshData);

    void onMeshAcknowledgement(in MeshAcknowledgement meshAcknowledgement);
}
