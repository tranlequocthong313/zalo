package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.SignUpEnterMorePersonalInfoFragment;
import vn.edu.ou.zalo.ui.fragments.SignUpEnterNameFragment;

public class SignUpEnterMorePersonalInfoActivity extends SingleFragmentActivity {
    private static final String EXTRA_USER = "vn.edu.ou.zalo.ui.activities.SignUpEnterMorePersonalInfoActivity.user";

    public static Intent newIntent(Context context, User user) {
        return new Intent(context, SignUpEnterMorePersonalInfoActivity.class)
                .putExtra(EXTRA_USER, user);
    }

    @Override
    protected Fragment createFragment() {
        return SignUpEnterMorePersonalInfoFragment.newInstance(getIntent().getParcelableExtra(EXTRA_USER));
    }
}
