package vn.edu.ou.zalo.data.sources;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.repositories.IRepositoryCallback;

public interface IMessageDataSource {
    void getMessages(Map<String, String> query, IRepositoryCallback<List<Message>> dataSourceCallback);
}
