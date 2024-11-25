package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.SignUpEnterNameFragment;

public class SignUpEnterNameActivity extends SingleFragmentActivity {
    private static final String EXTRA_USER = "vn.edu.ou.zalo.ui.activities.SignUpEnterNameActivity.user";

    public static Intent newIntent(Context context, User user) {
        return new Intent(context, SignUpEnterNameActivity.class)
                .putExtra(EXTRA_USER, user);
    }

    @Override
    protected Fragment createFragment() {
        return SignUpEnterNameFragment.newInstance(getIntent().getParcelableExtra(EXTRA_USER));
    }
}
