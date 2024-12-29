package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.CallSignalEvent;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.data.repositories.ICallRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class MakeCallUseCase {
    private final ICallRepository callRepository;

    @Inject
    public MakeCallUseCase(ICallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public void execute(User to, boolean isVideoCall, IDomainCallback<Void> callback) {
        callRepository.makeCall(to, isVideoCall, new IRepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
