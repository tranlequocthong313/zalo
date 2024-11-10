package vn.edu.ou.zalo.domain.impl;

import javax.inject.Inject;

import vn.edu.ou.zalo.data.repositories.IAuthRepository;

public class SignOutUseCase {
    private final IAuthRepository authRepository;

    @Inject
    public SignOutUseCase(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute() {
        authRepository.signOut();
    }
}
