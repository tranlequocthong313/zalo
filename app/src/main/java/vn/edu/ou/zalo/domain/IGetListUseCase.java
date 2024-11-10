package vn.edu.ou.zalo.domain;

import java.util.List;
import java.util.Map;

public interface IGetListUseCase<T> {
    void execute(Map<String, String> query, IDomainCallback<List<T>> callback);
}
