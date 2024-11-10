package vn.edu.ou.zalo.domain;

import vn.edu.ou.zalo.data.models.User;

public interface ICreateUseCase<T,V> {
    void execute(T obj, IDomainCallback<V> callback);
}
