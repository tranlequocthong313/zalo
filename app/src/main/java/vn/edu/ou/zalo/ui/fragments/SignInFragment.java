package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.activities.ZaloActivity;
import vn.edu.ou.zalo.ui.viewmodels.AuthViewModel;

@AndroidEntryPoint
public class SignInFragment extends Fragment {
    @Inject
    AuthViewModel authViewModel;
    private EditText passwordEditText;
    private EditText phoneNumberEditText;
    private FloatingActionButton signInButton;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.top_app_bar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());

        phoneNumberEditText = view.findViewById(R.id.fragment_signin_phone_number_text_input_input);
        phoneNumberEditText.requestFocus();
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInButton.setEnabled(isValidInputs());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText = view.findViewById(R.id.fragment_signin_password_text_input_view);
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signInButton.setEnabled(isValidInputs());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signInButton = view.findViewById(R.id.fragment_signin_fab);
        signInButton.setOnClickListener(v -> authViewModel.signIn(phoneNumberEditText.getText().toString(), passwordEditText.getText().toString()));

        authViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState.isSignedIn()) {
                startActivity(ZaloActivity.newIntent(getActivity()));
                requireActivity().finish();
            } else {
                if (uiState.getErrorMessage() != null) {
                    Toast.makeText(getActivity(), uiState.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean isValidInputs() {
        return Objects.requireNonNull(phoneNumberEditText).length() > 0 && Objects.requireNonNull(passwordEditText).length() > 0;
    }
}