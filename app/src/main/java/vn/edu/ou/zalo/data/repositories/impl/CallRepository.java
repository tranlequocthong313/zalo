package vn.edu.ou.zalo.data.repositories.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.CallEvent;
import vn.edu.ou.zalo.data.models.CallPacket;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.ICallRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.data.sources.IAuthDataSource;
import vn.edu.ou.zalo.data.sources.ICallDataSource;

public class CallRepository implements ICallRepository {
    private final ICallDataSource callDataSource;
    private final IAuthDataSource authDataSource;

    @Inject
    public CallRepository(ICallDataSource callDataSource, IAuthDataSource authDataSource) {
        this.callDataSource = callDataSource;
        this.authDataSource = authDataSource;
    }

    @Override
    public void makeCall(User to, boolean isVideoCall, IRepositoryCallback<Void> cb) {
        authDataSource.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User from) {
                callDataSource.sendMessageToOtherUser(new CallPacket(from.getId(), null, CallEvent.START_CALL), cb);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }

    @Override
    public void listenEvents(IRepositoryCallback<CallPacket> cb) {
        authDataSource.getSignedInUser(new IRepositoryCallback<User>() {
            @Override
            public void onSuccess(User data) {
                callDataSource.listenEvents(data, cb);
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }
}
