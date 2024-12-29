package vn.edu.ou.zalo.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.CallEvent;
import vn.edu.ou.zalo.ui.fragments.ChatRoomsFragment;
import vn.edu.ou.zalo.ui.fragments.ContactsFragment;
import vn.edu.ou.zalo.ui.fragments.DiscoveryFragment;
import vn.edu.ou.zalo.ui.fragments.MeFragment;
import vn.edu.ou.zalo.ui.fragments.TimelineFragment;
import vn.edu.ou.zalo.ui.viewmodels.CallViewModel;

@AndroidEntryPoint
public class ZaloActivity extends AppCompatActivity {
    private static final int FILTER_ICON_MARGIN = 12;

    @Inject
    CallViewModel callViewModel;
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private boolean isBottomNavItemSelectedProgrammatically = false;
    private static final List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();
    private static final List<Integer> menuItemIds = new ArrayList<>();

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, ZaloActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zalo);

        setupTopAppBar();

        setupMainContent();

        setupBottomNavigation();

//        setupCallEvent();
    }

    @SuppressLint("RestrictedApi")
    private void setupTopAppBar() {
        MaterialToolbar toolbar = findViewById(R.id.top_app_bar);
        Menu menu = toolbar.getMenu();

        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
            for (MenuItem item : menuBuilder.getVisibleItems()) {
                int iconMarginPx = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        FILTER_ICON_MARGIN,
                        getResources().getDisplayMetrics()
                );
                if (item.getIcon() == null || item.getItemId() == R.id.menu_item_qr_code) {
                    continue;
                }
                Drawable icon = new InsetDrawable(item.getIcon(), iconMarginPx, 0, iconMarginPx, 0);
                item.setIcon(icon);
            }
        }

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = SearchActivity.newIntent(this);
            startActivity(intent);
        });

        toolbar.setOnClickListener(v -> {
            Intent intent = SearchActivity.newIntent(this);
            startActivity(intent);
        });
    }

    private void setupMainContent() {
        initializeFragmentScreens();
        setupViewPager();
    }

    private void initializeFragmentScreens() {
        fragmentClasses.add(ChatRoomsFragment.class);
        fragmentClasses.add(ContactsFragment.class);
        fragmentClasses.add(DiscoveryFragment.class);
        fragmentClasses.add(TimelineFragment.class);
        fragmentClasses.add(MeFragment.class);
        menuItemIds.add(R.id.navigation_messages);
        menuItemIds.add(R.id.navigation_contacts);
        menuItemIds.add(R.id.navigation_discovery);
        menuItemIds.add(R.id.navigation_timeline);
        menuItemIds.add(R.id.navigation_me);
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.fragment_container);
        viewPager.setAdapter(
                new ZaloViewPagerAdapter(this, new ArrayList<>(fragmentClasses))
        );
        viewPager.setOffscreenPageLimit(1);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Integer menuItemId = menuItemIds.get(position);

                if (!isBottomNavItemSelectedProgrammatically && bottomNavigationView.getSelectedItemId() != menuItemId) {
                    isBottomNavItemSelectedProgrammatically = true;
                    bottomNavigationView.setSelectedItemId(menuItemId);
                }
            }
        });
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.activity_chat_input);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Integer itemId = item.getItemId();
            if (menuItemIds.contains(itemId)) {
                int index = menuItemIds.indexOf(itemId);
                if (viewPager.getCurrentItem() != index) {
                    isBottomNavItemSelectedProgrammatically = true;
                    viewPager.setCurrentItem(index, false);
                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    isBottomNavItemSelectedProgrammatically = false;
                });
                return true;
            }
            return false;
        });
    }

//    private void setupCallEvent() {
//        callViewModel.listenCallEvent();
//        callViewModel.getUiState().observe(this, uiState -> {
//            if (uiState.isLoading()) {
//                return;
//            }
//            if (uiState.getErrorMessage() != null) {
//                Log.d("CallEvent", uiState.getErrorMessage());
//            }
//            CallEvent callEvent = uiState.getCallEvent();
//            if (callEvent == null) {
//                return;
//            }
//            if (callEvent == CallEvent.INCOMING_CALL && !uiState.isVideoCall()) {
//                startActivity(IncomingAudioCallActivity.newIntent(this, uiState.getCallId()));
//            }
//        });
//    }
}
