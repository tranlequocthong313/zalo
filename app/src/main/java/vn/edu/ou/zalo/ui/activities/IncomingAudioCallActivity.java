package vn.edu.ou.zalo.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.ui.fragments.IncomingAudioCallFragment;

public class IncomingAudioCallActivity extends SingleFragmentActivity {
    private static final String EXTRA_CALL_ID = "vn.edu.ou.zalo.call_id";

    public static Intent newIntent(Context context, String callId) {
        return new Intent(context, IncomingAudioCallActivity.class)
                .putExtra(EXTRA_CALL_ID, callId);
    }

    @Override
    protected Fragment createFragment() {
        String callId = getIntent().getStringExtra(EXTRA_CALL_ID);
        return IncomingAudioCallFragment.newInstance(callId);
    }
}