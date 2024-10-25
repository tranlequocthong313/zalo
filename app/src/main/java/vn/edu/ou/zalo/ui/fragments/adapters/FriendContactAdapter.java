package vn.edu.ou.zalo.ui.fragments.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.viewholders.ContactHeaderViewHolder;
import vn.edu.ou.zalo.ui.fragments.viewholders.FriendContactViewHolder;

public class FriendContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_FRIEND = 1;

    private final List<Object> items = new ArrayList<>();

    public FriendContactAdapter(List<User> friends) {
        updateFriends(friends);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) instanceof String ? VIEW_TYPE_HEADER : VIEW_TYPE_FRIEND;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_contact_header, parent, false);
            return new ContactHeaderViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_friend, parent, false);
            return new FriendContactViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            String headerTitle = (String) items.get(position);
            ((ContactHeaderViewHolder) holder).bindHeader(headerTitle);
        } else {
            User friend = (User) items.get(position);
            ((FriendContactViewHolder) holder).bindFriend(friend);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateFriends(List<User> friends) {
        items.clear();

        if (friends == null || friends.isEmpty()) return;

        String currentHeader = "";

        for (User friend : friends) {
            String header = friend.getFullName().substring(0, 1).toUpperCase();

            if (!header.equals(currentHeader)) {
                items.add(header);
                currentHeader = header;
            }

            items.add(friend);
        }

        notifyDataSetChanged();
    }
}
