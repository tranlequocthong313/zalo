package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.ui.fragments.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return SignUpFragment.newInstance();
    }
}