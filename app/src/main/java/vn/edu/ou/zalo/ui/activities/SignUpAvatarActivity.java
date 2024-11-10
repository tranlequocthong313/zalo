package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.SignUpAvatarFragment;

public class SignUpAvatarActivity extends SingleFragmentActivity {
    private static final String EXTRA_USER = "vn.edu.ou.zalo.ui.activities.SignUpAvatarFragment.user";

    public static Intent newIntent(Context context, User user) {
        return new Intent(context, SignUpAvatarActivity.class).putExtra(EXTRA_USER, user);
    }

    @Override
    protected Fragment createFragment() {
        return SignUpAvatarFragment.newInstance(getIntent().getParcelableExtra(EXTRA_USER));
    }
}