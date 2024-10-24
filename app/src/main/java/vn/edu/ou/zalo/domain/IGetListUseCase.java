package vn.edu.ou.zalo.domain;

import java.util.List;

public interface IGetListUseCase<T> {
    List<T> execute();
}
