package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.mindrot.jbcrypt.BCrypt;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.activities.SignUpEnterMorePersonalInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String VALID_PASSWORD_REGEX_PATTERN = "^[A-Za-z\\s]{2,40}$";
    private static final String ARGS_USER = "user";
    private Button nextButton;



    // TODO: Rename and change types and number of parameters
    public static SignUpPasswordFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_USER, user);

        SignUpPasswordFragment fragment = new SignUpPasswordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        User user = getArguments().getParcelable(ARGS_USER);
        View view = inflater.inflate(R.layout.fragment_sign_up_password, container, false);

        TextInputEditText passwordEditText = view.findViewById(R.id.fragment_signup_enter_password_text_input_view);
        TextInputEditText confirmPasswordEditText = view.findViewById(R.id.fragment_signup_confirm_password_text_input_view);

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                assert user != null;
                user.setHashedPassword(charSequence.toString());
                checkPasswordsMatch(passwordEditText, confirmPasswordEditText);
                nextButton.setEnabled(isValidPass(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPasswordsMatch(passwordEditText, confirmPasswordEditText); // Kiểm tra khớp mật khẩu
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        passwordEditText.requestFocus();

        nextButton = view.findViewById(R.id.fragment_signup_add_password_info_next_button);
        nextButton.setOnClickListener(v -> {
            if (isPasswordValid(user.getHashedPassword()) && arePasswordsMatching(passwordEditText, confirmPasswordEditText)) {
                startActivity(SignUpEnterMorePersonalInfoActivity.newIntent(getActivity(), user));
            } else {
                Toast.makeText(getContext(), "Mật khẩu không hợp lệ hoặc không khớp!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6; // Thí dụ: mật khẩu phải dài ít nhất 6 ký tự
    }
    private boolean arePasswordsMatching(TextInputEditText passwordEditText, TextInputEditText confirmPasswordEditText) {
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        return password.equals(confirmPassword);
    }


    private void checkPasswordsMatch(TextInputEditText passwordEditText, TextInputEditText confirmPasswordEditText) {
        if (arePasswordsMatching(passwordEditText, confirmPasswordEditText)) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }
    private boolean isValidPass(String password) {
        return password.matches(VALID_PASSWORD_REGEX_PATTERN);
    }
}