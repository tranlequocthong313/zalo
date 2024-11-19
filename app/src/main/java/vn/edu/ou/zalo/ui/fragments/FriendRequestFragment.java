package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ou.zalo.R;

public class FriendRequestFragment extends Fragment {
    private static final List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();

    public static FriendRequestFragment newInstance() {
        return new FriendRequestFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createFragments();
    }

    private void createFragments() {
        fragmentClasses.add(ReceivedFriendRequestFragment.class);
        fragmentClasses.add(SentFriendRequestFragment.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_request, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.top_app_bar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());

        TabLayout tabLayout = view.findViewById(R.id.fragment_friend_request_tab_layout);
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

        try {
            Fragment fragment = fragmentClass.newInstance();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//            Fragment f = fragmentManager.findFragmentById(R.id.fragment_friend_request_fragment_container);
//            if (f != null) {
//                fragmentManager.beginTransaction()
//                        .remove(f)
//                        .commit();
//            }
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_friend_request_fragment_container, fragment)
                    .commit();
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}