package vn.edu.ou.zalo.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;

@AndroidEntryPoint
public abstract class SingleFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSurfaceContainerHigh));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorSurfaceBright));

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_fragment_fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.activity_fragment_fragment_container, fragment)
                    .commit();
        }
    }

    protected abstract Fragment createFragment();
}
