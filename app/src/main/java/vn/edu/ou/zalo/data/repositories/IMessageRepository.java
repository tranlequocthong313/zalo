package vn.edu.ou.zalo.data.repositories;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Message;

public interface IMessageRepository {
    void getMessages(Map<String, String> query, IRepositoryCallback<List<Message>> callback);
}
