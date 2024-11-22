package vn.edu.ou.zalo.ui.fragments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.listeners.OnChatRoomItemClickListener;
import vn.edu.ou.zalo.ui.fragments.viewholders.ChatRoomViewHolder;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomViewHolder> {
    private List<ChatRoom> chatRooms;
    private User signedInUser;
    private final OnChatRoomItemClickListener listener;

    public ChatRoomsAdapter(List<ChatRoom> chatRooms, User signedInUser, OnChatRoomItemClickListener listener) {
        this.chatRooms = chatRooms;
        this.signedInUser = signedInUser;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chat_room, parent, false);

        return new ChatRoomViewHolder(itemView, signedInUser, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        ChatRoom chatRoom = chatRooms.get(position);
        holder.bindChatRoom(chatRoom);
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateChatRooms(List<ChatRoom> newChatRooms) {
        if (newChatRooms != null) {
            this.chatRooms = newChatRooms;
            notifyDataSetChanged();
        }
    }
}
