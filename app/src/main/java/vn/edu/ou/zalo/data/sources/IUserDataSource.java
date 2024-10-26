package vn.edu.ou.zalo.data.sources;

import java.util.List;

import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;

public interface IUserDataSource {
    List<User> getUsers();

    User getLoginUser();
}
