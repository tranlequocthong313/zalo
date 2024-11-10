package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.activities.SignUpEnterMorePersonalInfoActivity;

public class SignUpEnterNameFragment extends Fragment {
    private static final String VALID_NAME_REGEX_PATTERN = "^[A-Za-z\\s]{2,40}$";
    private static final String ARGS_USER = "user";

    private Button nextButton;

    public static SignUpEnterNameFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_USER, user);

        SignUpEnterNameFragment fragment = new SignUpEnterNameFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        User user = getArguments().getParcelable(ARGS_USER);

        View view = inflater.inflate(R.layout.fragment_sign_up_enter_name, container, false);

        TextInputEditText nameEditText = view.findViewById(R.id.fragment_signup_enter_name_text_input_view);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                assert user != null;
                user.setFullName(s.toString());
                nextButton.setEnabled(isValidName(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        nameEditText.requestFocus();

        nextButton = view.findViewById(R.id.fragment_signup_enter_name_next_button);
        nextButton.setOnClickListener(v -> {
            startActivity(SignUpEnterMorePersonalInfoActivity.newIntent(getActivity(), user));
        });

        return view;
    }

    private boolean isValidName(String name) {
        return name.matches(VALID_NAME_REGEX_PATTERN);
    }
}