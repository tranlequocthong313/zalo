package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.ui.fragments.IncomingVideoCallFragment;

public class IncomingVideoCallActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, IncomingVideoCallActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return IncomingVideoCallFragment.newInstance();
    }
}