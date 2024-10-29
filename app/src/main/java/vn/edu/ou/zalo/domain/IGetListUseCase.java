package vn.edu.ou.zalo.domain;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Message;

public interface IGetListUseCase<T> {
    default List<T> execute() {
       return execute(null);
    }

    List<T> execute(Map<String, String> query);
}
