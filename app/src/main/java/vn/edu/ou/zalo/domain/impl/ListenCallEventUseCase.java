package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.CallPacket;
import vn.edu.ou.zalo.data.repositories.ICallRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class ListenCallEventUseCase {
    private final ICallRepository callRepository;

    @Inject
    public ListenCallEventUseCase(ICallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public void execute(IDomainCallback<CallPacket> callback) {
        callRepository.listenEvents(new IRepositoryCallback<CallPacket>() {
            @Override
            public void onSuccess(CallPacket data) {
                callback.onSuccess(data);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
