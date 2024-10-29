package vn.edu.ou.zalo.domain;

import vn.edu.ou.zalo.data.models.ChatRoom;

public interface IGetDetailUseCase<T> {
    default T execute() {
        return execute(null);
    }

    T execute(String id);
}
