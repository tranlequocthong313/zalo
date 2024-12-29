package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.models.CallSignalEvent;
import vn.edu.ou.zalo.data.repositories.ICallRepository;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;
import vn.edu.ou.zalo.domain.IDomainCallback;

public class AuthenticateCallServiceUseCase {
    private final ICallRepository callRepository;

    @Inject
    public AuthenticateCallServiceUseCase(ICallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public void execute(IDomainCallback<Void> callback) {
    }
}
