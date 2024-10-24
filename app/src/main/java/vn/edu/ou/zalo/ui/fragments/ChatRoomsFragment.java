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
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;

@AndroidEntryPoint
public class ChatRoomsFragment extends Fragment {
    public static final String TAG = "ChatRoomFragment";

    private static final int FILTER_ICON_MARGIN = 10;

    private static final List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();
    private static final List<Fragment> fragments = new ArrayList<>();

    public static ChatRoomsFragment newInstance() {
        return new ChatRoomsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        renderFragment(0);

        return view;
    }

    private void renderFragment(int index) {
        if (index >= fragmentClasses.size()) {
            return;
        }
        Class<? extends Fragment> fragmentClass = fragmentClasses.get(index);
        if (fragmentClass == null) {
            return;
        }

        Fragment fragment = index < fragments.size() ? fragments.get(index) : null;
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                fragments.add(fragment);
            } catch (IllegalAccessException | java.lang.InstantiationException e) {
                Log.e(TAG, "Something went wrong!");
                throw new RuntimeException(e);
            }
        }

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Fragment hostingFragment = fragmentManager.findFragmentById(R.id.chat_rooms_fragment_container_nested_scroll_view);
        if (hostingFragment != null) {
            fragmentManager.beginTransaction()
                    .remove(hostingFragment)
                    .commit();
        }
        fragmentManager.beginTransaction()
                .add(R.id.chat_rooms_fragment_container_nested_scroll_view, fragment)
                .commit();
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
}