package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ou.zalo.R;

public class ContactsFragment extends Fragment {
    private static final List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createFragments();
    }

    private void createFragments() {
        fragmentClasses.add(FriendContactsFragment.class);
        fragmentClasses.add(GroupContactsFragment.class);
        fragmentClasses.add(OfficialAccountContactsFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        TabLayout tabLayout = view.findViewById(R.id.fragment_contacts_tab_layout);
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
            Fragment f = fragmentManager.findFragmentById(R.id.fragment_contacts_fragment_container);
            if (f == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_contacts_fragment_container, fragment)
                        .commit();
            }
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MenuHost menuHost = requireActivity();

        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.contacts_top_app_bar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}