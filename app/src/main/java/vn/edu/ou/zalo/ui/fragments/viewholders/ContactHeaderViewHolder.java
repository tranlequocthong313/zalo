package vn.edu.ou.zalo.ui.fragments.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.ou.zalo.R;

public class ContactHeaderViewHolder extends RecyclerView.ViewHolder {
    private final TextView headerTextView;

    public ContactHeaderViewHolder(@NonNull View itemView) {
        super(itemView);

        headerTextView = itemView.findViewById(R.id.list_item_contact_header);
    }

    public void bindHeader(String headerTitle) {
        headerTextView.setText(headerTitle);
    }
}
