package vn.edu.ou.zalo.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
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

        authViewModel.checkIsSignedIn();
        authViewModel.getUiState().observe(this, uiState -> {
            if (uiState.isLoading()) {
                return;
            }
            if (uiState.isSignedIn()) {
                startActivity(ZaloActivity.newIntent(this));
            } else {
                startActivity(AuthActivity.newIntent(this));
            }
            finish();
        });
    }
}