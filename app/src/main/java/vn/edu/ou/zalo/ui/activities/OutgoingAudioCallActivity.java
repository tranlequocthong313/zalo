package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.fragments.OutgoingAudioCallFragment;

public class OutgoingAudioCallActivity extends SingleFragmentActivity {
    private static final String EXTRA_TO = "vn.edu.ou.zalo.to";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorOnPrimaryContainer));
    }

    public static Intent newIntent(Context context, User to) {
        return new Intent(context, OutgoingAudioCallActivity.class)
                .putExtra(EXTRA_TO, to);
    }

    @Override
    protected Fragment createFragment() {
        User to = getIntent().getParcelableExtra(EXTRA_TO);
        return OutgoingAudioCallFragment.newInstance(to);
    }
}