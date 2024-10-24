package vn.edu.ou.zalo.ui.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.ChatRoom;
import vn.edu.ou.zalo.ui.fragments.adapters.ChatRoomsAdapter;
import vn.edu.ou.zalo.ui.states.ChatRoomUiState;
import vn.edu.ou.zalo.ui.viewmodels.ChatRoomsViewModel;

@AndroidEntryPoint
public class ChatRoomsFragment extends Fragment {
    private static final String TAG = "ChatRoomsFragment";

    private static final int FILTER_ICON_MARGIN = 10;

    @Inject
    ChatRoomsViewModel chatRoomsViewModel;
    private ChatRoomsAdapter chatRoomsAdapter;
    private RecyclerView recyclerView;

    public static ChatRoomsFragment newInstance() {
        return new ChatRoomsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_rooms, container, false);

        ImageView filterImageView = view.findViewById(R.id.fragment_chat_rooms_filter);
        recyclerView = view.findViewById(R.id.fragment_chat_rooms_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        filterImageView.setOnClickListener(v -> showMenu(v, R.menu.filter_chat_room_menu));

        chatRoomsViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.isLoading()) {
                // TODO: Show loading indicator
            } else {
                // TODO: Hide loading indicator
                if (uiState.getErrorMessage() != null) {
                    // TODO: Show error message
                    Toast.makeText(getActivity(), uiState.getErrorMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    updateUi(uiState);
                    Log.i(TAG, "Fetch Chat Rooms: " + uiState.getChatRooms());
                }
            }
        });


        return view;
    }

    @SuppressLint("RestrictedApi")
    private void showMenu(View view, int menu) {
        PopupMenu popupMenu = new PopupMenu(requireActivity(), view);
        popupMenu.getMenuInflater().inflate(menu, popupMenu.getMenu());
        if (popupMenu.getMenu() instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) popupMenu.getMenu();
            menuBuilder.setOptionalIconsVisible(true);
            for (MenuItem item : menuBuilder.getVisibleItems()) {
                int iconMarginPx = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        FILTER_ICON_MARGIN,
                        getResources().getDisplayMetrics()
                );
                if (item.getIcon() == null) {
                    continue;
                }
                Drawable icon = new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0);
                item.setIcon(icon);
            }
        }
        popupMenu.show();
    }

    private void updateUi(ChatRoomUiState uiState) {
        List<ChatRoom> chatRooms = uiState.getChatRooms() != null ? uiState.getChatRooms() : new ArrayList<>();

        if (recyclerView.getAdapter() == null) {
            chatRoomsAdapter = new ChatRoomsAdapter(chatRooms);
            recyclerView.setAdapter(chatRoomsAdapter);
        } else {
            chatRoomsAdapter.updateChatRooms(chatRooms);
        }
    }
}