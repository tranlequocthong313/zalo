package vn.edu.ou.zalo.data.repositories;

import vn.edu.ou.zalo.data.models.CallPacket;
import vn.edu.ou.zalo.data.models.User;

public interface ICallRepository {
    void makeCall(User to, boolean isVideoCall, IRepositoryCallback<Void> cb);

    void listenEvents(IRepositoryCallback<CallPacket> cb);
}
