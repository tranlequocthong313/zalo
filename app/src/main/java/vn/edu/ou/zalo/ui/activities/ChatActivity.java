package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.ChatFragment;

@AndroidEntryPoint
public class ChatActivity extends SingleFragmentActivity {
    private static final String EXTRA_CHAT_ROOM_ID = "vn.edu.ou.zalo.ui.activities.chat_room_id";
    private static final String EXTRA_USER = "vn.edu.ou.zalo.ui.activities.user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorSurfaceBright, getTheme()));
    }

    @Override
    protected Fragment createFragment() {
        String chatRoomId = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA_CHAT_ROOM_ID);
        User user = Objects.requireNonNull(getIntent().getExtras()).getParcelable(EXTRA_USER);
        Fragment fragment = null;
        if (chatRoomId != null) {
            fragment = ChatFragment.newInstance(chatRoomId);
        }
        if (user != null) {
            fragment = ChatFragment.newInstance(user);
        }
        return fragment;
    }

    public static Intent newIntent(Context context, String chatRoomId) {
        return new Intent(context, ChatActivity.class)
                .putExtra(EXTRA_CHAT_ROOM_ID, chatRoomId);
    }

    public static Intent newIntent(Context context, User user) {
        return new Intent(context, ChatActivity.class)
                .putExtra(EXTRA_USER, user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}