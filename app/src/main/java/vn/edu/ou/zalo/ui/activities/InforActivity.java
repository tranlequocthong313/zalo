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
import vn.edu.ou.zalo.ui.fragments.InforFragment;
import vn.edu.ou.zalo.ui.fragments.SignInFragment;

public class InforActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, InforActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return InforFragment.newInstance();
    }
}