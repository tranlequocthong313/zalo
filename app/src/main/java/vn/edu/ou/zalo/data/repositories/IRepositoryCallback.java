package vn.edu.ou.zalo.data.repositories;

public interface IRepositoryCallback<T> {
    void onSuccess(T data);

    void onFailure(Exception e);
}
