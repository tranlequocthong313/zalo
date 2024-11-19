package vn.edu.ou.zalo.ui.fragments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Friendship;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.listeners.OnFriendClickListener;
import vn.edu.ou.zalo.ui.fragments.viewholders.SearchUserViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<SearchUserViewHolder> {
    private List<User> users;
    private Map<String, Friendship.Status> statusMap = new HashMap<>();
    private final OnFriendClickListener listener;
    private User signedInUser = new User();

    public SearchAdapter(List<User> users, OnFriendClickListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_search_result_user, parent, false);

        return new SearchUserViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bindUser(user, signedInUser, statusMap);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateSearchResult(List<User> newUsers) {
        if (newUsers != null) {
            this.users = newUsers;
            notifyDataSetChanged();
        }
    }

    public void setStatusMap(Map<String, Friendship.Status> statusMap) {
        this.statusMap = statusMap;
        updateSearchResult(users);
    }

    public void setSignedInUser(User signedInUser) {
        this.signedInUser = signedInUser;
    }
}
