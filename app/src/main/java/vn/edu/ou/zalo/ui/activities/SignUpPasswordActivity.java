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
import vn.edu.ou.zalo.ui.fragments.SignUpEnterNameFragment;
import vn.edu.ou.zalo.ui.fragments.SignUpFragment;
import vn.edu.ou.zalo.ui.fragments.SignUpPasswordFragment;

public class SignUpPasswordActivity extends SingleFragmentActivity {
    private static final String EXTRA_USER = "vn.edu.ou.zalo.ui.activities.SignUpEnterNameActivity.user";

    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, SignUpPasswordActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        // Lấy thông tin User từ Intent và truyền vào Fragment
        User user = getIntent().getParcelableExtra(EXTRA_USER);
        return SignUpPasswordFragment.newInstance(user);
    }
}
