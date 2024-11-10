package vn.edu.ou.zalo.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.activities.LoginActivity;
import vn.edu.ou.zalo.ui.activities.SignUpActivity;

public class AuthFragment extends Fragment {

    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        Button loginButton = view.findViewById(R.id.fragment_auth_login_button);
        Button signUpButton = view.findViewById(R.id.fragment_auth_signup_button);

        loginButton.setOnClickListener(v -> {
            Intent intent = LoginActivity.newIntent(getActivity());
            startActivity(intent);
        });
        signUpButton.setOnClickListener(v -> {
            Intent intent = SignUpActivity.newIntent(getActivity());
            startActivity(intent);
        });
        return view;
    }
}