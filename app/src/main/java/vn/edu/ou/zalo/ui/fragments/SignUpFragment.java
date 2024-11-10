package vn.edu.ou.zalo.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.activities.LoginActivity;
import vn.edu.ou.zalo.ui.activities.OtpActivity;

public class SignUpFragment extends Fragment {
    private String phoneNumber;
    private boolean isAgreedTerms;
    private boolean isAgreedSocialTerms;
    private Button nextButton;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.top_app_bar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());

        EditText phoneNumberEditText = view.findViewById(R.id.fragment_signup_phone_number_text_input_view);
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumber = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmit();
            }
        });
        phoneNumberEditText.requestFocus();

        CheckBox termCheckbox = view.findViewById(R.id.fragment_signup_terms_of_service);
        CheckBox socialTermCheckbox = view.findViewById(R.id.fragment_signup_social_terms_of_service);
        termCheckbox.setOnCheckedChangeListener((btn, isChecked) -> {
            isAgreedTerms = isChecked;
            enableSubmit();
        });
        socialTermCheckbox.setOnCheckedChangeListener((btn, isChecked) -> {
            isAgreedSocialTerms = isChecked;
            enableSubmit();
        });

        nextButton = view.findViewById(R.id.fragment_signup_next_button);
        nextButton.setOnClickListener(v -> {
            if (!isValidRegistration()) {
                return;
            }
            Intent i = OtpActivity.newIntent(getActivity(), phoneNumber);
            startActivity(i);
        });

        TextView loginTextView = view.findViewById(R.id.fragment_signup_login_text_view);
        loginTextView.setOnClickListener(v -> {
            Intent i = LoginActivity.newIntent(getActivity());
            startActivity(i);
        });

        return view;
    }

    private boolean isValidRegistration() {
        return phoneNumber != null && isAgreedTerms && isAgreedSocialTerms; // TODO: check more detail
    }

    private void enableSubmit() {
        nextButton.setEnabled(isValidRegistration());
    }
}