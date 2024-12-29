package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.ui.fragments.OutgoingVideoCallFragment;

public class OutgoingVideoCallActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return OutgoingVideoCallFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, OutgoingVideoCallActivity.class);
    }
}