package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;

@AndroidEntryPoint
public class TimelineFragment extends Fragment {
    private static final List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createFragments();
    }

    private void createFragments() {
        fragmentClasses.add(InterestedTimelineFragment.class);
        fragmentClasses.add(OtherTimelineFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        swipeRefreshLayout = view.findViewById(R.id.fragment_timeline_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::refreshContent);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        TabLayout tabLayout = view.findViewById(R.id.fragment_timeline_tab_layout);
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

    private void refreshContent() {
        swipeRefreshLayout.setRefreshing(true);
        Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_timeline_fragment_container);
        if (fragment == null) {
            return;
        }
        if (fragment instanceof IRefreshable) {
            ((IRefreshable) fragment).refreshContent();
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            swipeRefreshLayout.setRefreshing(false);
        }, 1500);
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
            Fragment f = fragmentManager.findFragmentById(R.id.fragment_timeline_fragment_container);
            if (f != null) {
                fragmentManager.beginTransaction()
                        .remove(f)
                        .commit();
            }
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_timeline_fragment_container, fragment)
                    .commit();
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
                menuInflater.inflate(R.menu.timeline_top_app_bar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull android.view.MenuItem menuItem) {
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}