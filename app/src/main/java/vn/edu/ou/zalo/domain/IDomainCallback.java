package vn.edu.ou.zalo.domain;

public interface IDomainCallback<T> {
    void onSuccess(T data);

    void onFailure(Exception e);
}
