package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.fragments.OtpFragment;

public class OtpActivity extends SingleFragmentActivity {
    private static final String EXTRA_PHONE_NUMBER = "vn.edu.ou.zalo.ui.activities.OtpActivity.phone_number";

    public static Intent newIntent(Context context, String phoneNumber) {
        Intent i = new Intent(context, OtpActivity.class);
        i.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return OtpFragment.newInstance(getIntent().getStringExtra(EXTRA_PHONE_NUMBER));
    }
}