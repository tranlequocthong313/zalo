package vn.edu.ou.zalo.ui.fragments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.listeners.OnAddFriendClickListener;
import vn.edu.ou.zalo.ui.fragments.viewholders.SearchUserViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<SearchUserViewHolder> {
    private List<User> users;
    private final OnAddFriendClickListener listener;

    public SearchAdapter(List<User> users, OnAddFriendClickListener listener) {
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
        holder.bindUser(user);
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
}
