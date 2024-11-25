package vn.edu.ou.zalo.ui.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;
import java.util.Objects;

public class ZaloViewPagerAdapter extends FragmentStateAdapter {
    private final List<Class<? extends Fragment>> fragments;

    public ZaloViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Class<? extends Fragment>> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return Objects.requireNonNull(getFragment(position));
    }

    private Fragment getFragment(int index) {
        if (index >= fragments.size()) {
            return null;
        }
        Class<? extends Fragment> fragmentClass = fragments.get(index);
        if (fragmentClass == null) {
            return null;
        }

        try {
            return fragmentClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
