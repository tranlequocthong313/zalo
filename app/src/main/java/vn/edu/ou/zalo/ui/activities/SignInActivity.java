package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.ui.fragments.SignInFragment;

public class SignInActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, SignInActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return SignInFragment.newInstance();
    }
}