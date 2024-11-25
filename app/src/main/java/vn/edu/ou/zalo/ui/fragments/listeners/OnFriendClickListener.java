package vn.edu.ou.zalo.ui.fragments.listeners;

import vn.edu.ou.zalo.data.models.User;

public interface OnFriendClickListener {
    void onAddFriendClick(User friend);
    void onItemClick(User friend);
}
