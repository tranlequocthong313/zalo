package vn.edu.ou.zalo.domain;

public interface IGetDetailUseCase<T> {
    void execute(String id, IDomainCallback<T> callback);
}
