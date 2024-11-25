package vn.edu.ou.zalo.ui.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.states.ChatRoomUiState;
import vn.edu.ou.zalo.ui.viewmodels.ChatRoomsViewModel;

@AndroidEntryPoint
public class ChatRoomsFragment extends Fragment {
    private static final int FILTER_ICON_MARGIN = 10;

    @Inject
    ChatRoomsViewModel chatRoomsViewModel;

    private static final List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();
    private ConstraintLayout tabLayoutContainer;
    private FrameLayout emptyView;
    private FrameLayout mainContentView;

    public static ChatRoomsFragment newInstance() {
        return new ChatRoomsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createFragments();
    }

    private void createFragments() {
        fragmentClasses.add(FocusedChatRoomsFragment.class);
        fragmentClasses.add(OtherChatRoomsFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_rooms, container, false);

        TabLayout tabLayout = view.findViewById(R.id.fragment_chat_rooms_tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIndex = tab.getPosition();
                renderFragment(tabIndex);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ImageButton filterImageButtonView = view.findViewById(R.id.fragment_chat_rooms_filter);
        filterImageButtonView.setOnClickListener(v -> showMenu(v, R.menu.filter_chat_room_menu));

        tabLayoutContainer = view.findViewById(R.id.fragment_chat_rooms_tab_layout_container);
        emptyView = view.findViewById(R.id.fragment_chat_rooms_empty_view);
        mainContentView = view.findViewById(R.id.chat_rooms_fragment_container);

        chatRoomsViewModel.listenChatRoom();
        chatRoomsViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private void updateUi(ChatRoomUiState uiState) {
        if (uiState.isLoading()) {
            return;
        }

        if (uiState.isFocusedEmpty() && uiState.isOtherEmpty()) {
            tabLayoutContainer.setVisibility(View.GONE);
            mainContentView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            tabLayoutContainer.setVisibility(View.VISIBLE);
            mainContentView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            renderFragment(0);
        }
    }

    private void renderFragment(int index) {
        if (index >= fragmentClasses.size()) {
            return;
        }
        Class<? extends Fragment> fragmentClass = fragmentClasses.get(index);
        if (fragmentClass == null) {
            return;
        }

        try {
            Fragment fragment = fragmentClass.newInstance();

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.chat_rooms_fragment_container, fragment)
                    .commit();
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MenuHost menuHost = requireActivity();

        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.chat_rooms_top_app_bar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}