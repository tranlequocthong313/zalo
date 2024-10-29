package vn.edu.ou.zalo.ui.fragments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Message;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.viewholders.ReceivedMessageViewHolder;
import vn.edu.ou.zalo.ui.fragments.viewholders.SendMessageViewHolder;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_RECEIVED_MESSAGE = 0;
    private static final int VIEW_TYPE_SEND_MESSAGE = 1;
    public static final int MARGIN_DIFFERENT_SENDER_MESSAGE = 16;
    public static final int DEFAULT_MARGIN_MESSAGE = 2;

    private final List<Message> messages;
    private final User loginUser;

    public ChatAdapter(List<Message> messages, User loginUser) {
        this.messages = messages;
        this.loginUser = loginUser;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SEND_MESSAGE) {
            return new SendMessageViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_send_message_text, parent, false)
            );
        } else {
            return new ReceivedMessageViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_received_message_text, parent, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Message message = messages.get(position);
        int topMargin = shouldSplitMessage(position) ? MARGIN_DIFFERENT_SENDER_MESSAGE : DEFAULT_MARGIN_MESSAGE;

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        layoutParams.topMargin = (int) (holder.itemView.getContext().getResources().getDisplayMetrics().density * topMargin);
        holder.itemView.setLayoutParams(layoutParams);

        if (viewType == VIEW_TYPE_SEND_MESSAGE) {
            ((SendMessageViewHolder) holder)
                    .bindMessage(message,
                            shouldHideTimestamp(position),
                            shouldHideLikeButton(position)
                    );
        } else {
            ((ReceivedMessageViewHolder) holder)
                    .bindMessage(message,
                            shouldHideAvatar(position),
                            shouldHideTimestamp(position),
                            shouldHideLikeButton(position)
                    );
        }
    }

    private boolean shouldSplitMessage(int position) {
        if (position == messages.size() - 1) {
            return true;
        }
        return !messages.get(position).getSender()
                .equals(messages.get(position + 1).getSender());
    }

    private boolean shouldHideLikeButton(int position) {
        if (position == 0) {
            return false;
        }
        return messages.get(position - 1).getSender()
                .equals(messages.get(position).getSender());
    }

    private boolean shouldHideTimestamp(int position) {
        if (position == 0) {
            return false;
        }
        return messages.get(position - 1).getSender()
                .equals(messages.get(position).getSender());
    }

    private boolean shouldHideAvatar(int position) {
        if (position == messages.size() - 1) {
            return false;
        }
        return messages.get(position + 1).getSender()
                .equals(messages.get(position).getSender());
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (loginUser.equals(message.getSender())) {
            return VIEW_TYPE_SEND_MESSAGE;
        }
        return VIEW_TYPE_RECEIVED_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }
}
