package vn.edu.ou.zalo.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.activities.SignInActivity;
import vn.edu.ou.zalo.ui.activities.SignUpActivity;
import vn.edu.ou.zalo.ui.activities.ZaloActivity;
import vn.edu.ou.zalo.ui.viewmodels.AuthViewModel;

@AndroidEntryPoint
public class AuthFragment extends Fragment {
    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        Button loginButton = view.findViewById(R.id.fragment_auth_login_button);
        Button signUpButton = view.findViewById(R.id.fragment_auth_signup_button);

        loginButton.setOnClickListener(v -> {
            Intent intent = SignInActivity.newIntent(getActivity());
            startActivity(intent);
        });
        signUpButton.setOnClickListener(v -> {
            Intent intent = SignUpActivity.newIntent(getActivity());
            startActivity(intent);
        });
        return view;
    }
}