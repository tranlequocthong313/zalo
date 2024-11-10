package vn.edu.ou.zalo.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.activities.AuthActivity;
import vn.edu.ou.zalo.ui.activities.ZaloActivity;
import vn.edu.ou.zalo.ui.viewmodels.AuthViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (authViewModel.isSignedIn()) {
            startActivity(ZaloActivity.newIntent(this));
        } else {
            startActivity(AuthActivity.newIntent(this));
        }
        finish();
    }
}