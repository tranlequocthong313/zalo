package vn.edu.ou.zalo.data.repositories;

import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.Post;

public interface IMessageRepository {
    List<Message> getMessages(Map<String, String> query);
}
