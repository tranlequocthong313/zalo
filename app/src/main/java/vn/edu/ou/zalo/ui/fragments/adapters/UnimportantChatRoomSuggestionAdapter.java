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
import vn.edu.ou.zalo.ui.fragments.viewholders.UnimportantChatRoomSuggestionViewHolder;

public class UnimportantChatRoomSuggestionAdapter extends RecyclerView.Adapter<UnimportantChatRoomSuggestionViewHolder> {
    private final List<ChatRoom> chatRooms;

    public UnimportantChatRoomSuggestionAdapter(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    @NonNull
    @Override
    public UnimportantChatRoomSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chat_suggestion, parent, false);

        return new UnimportantChatRoomSuggestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UnimportantChatRoomSuggestionViewHolder holder, int position) {
        ChatRoom chatR = chatRooms.get(position);
        holder.bindSuggestion(chatR);
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateSuggestion(List<ChatRoom> newSuggestions) {
        if (newSuggestions != null) {
            this.chatRooms.clear();
            this.chatRooms.addAll(newSuggestions);
            notifyDataSetChanged();
        }
    }
}
