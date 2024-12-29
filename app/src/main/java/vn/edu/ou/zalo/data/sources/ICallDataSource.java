package vn.edu.ou.zalo.data.sources;

import vn.edu.ou.zalo.data.models.CallPacket;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface ICallDataSource {
    void sendMessageToOtherUser(CallPacket callPacket, IRepositoryCallback<Void> cb);

    void listenEvents(User signedInUser, IRepositoryCallback<CallPacket> cb);
}
