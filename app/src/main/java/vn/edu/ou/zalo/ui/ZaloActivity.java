package vn.edu.ou.zalo.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.fragments.ChatRoomsFragment;
import vn.edu.ou.zalo.ui.fragments.ContactsFragment;
import vn.edu.ou.zalo.ui.fragments.DiscoveryFragment;
import vn.edu.ou.zalo.ui.fragments.MeFragment;
import vn.edu.ou.zalo.ui.fragments.TimelineFragment;

@AndroidEntryPoint
public class ZaloActivity extends AppCompatActivity {
    private static final int FILTER_ICON_MARGIN = 12;

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private boolean isBottomNavItemSelectedProgrammatically = false;
    private static final List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();
    private static final List<Integer> menuItemIds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zalo);

        setupTopAppBar();

        setupMainContent();

        setupBottomNavigation();
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
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Integer itemId = item.getItemId();
            if (menuItemIds.contains(itemId)) {
                int index = menuItemIds.indexOf(itemId);
                if (viewPager.getCurrentItem() != index) {
                    isBottomNavItemSelectedProgrammatically = true;
                    viewPager.setCurrentItem(index);
                }

                new Handler(Looper.getMainLooper()).post(() -> {
                    isBottomNavItemSelectedProgrammatically = false;
                });
                return true;
            }
            return false;
        });
    }
}
