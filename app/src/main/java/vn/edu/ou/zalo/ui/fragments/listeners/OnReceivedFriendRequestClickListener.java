package vn.edu.ou.zalo.ui.fragments.listeners;

import vn.edu.ou.zalo.data.models.Friendship;

public interface OnReceivedFriendRequestClickListener {
    void onRefuse(Friendship friendship);
    void onAccept(Friendship friendship);
}
