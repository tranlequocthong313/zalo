package vn.edu.ou.zalo.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.activities.SignUpEnterNameActivity;
import vn.edu.ou.zalo.ui.states.OtpUiState;
import vn.edu.ou.zalo.ui.viewmodels.OtpViewModel;

@AndroidEntryPoint
public class OtpFragment extends Fragment {
    private static final String ARGS_PHONE_NUMBER = "phone_number";
    private TextInputEditText[] otpDigits;
    private Button nextButton;
    @Inject
    OtpViewModel otpViewModel;
    private String phoneNumber;

    public static OtpFragment newInstance(String phoneNumber) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_PHONE_NUMBER, phoneNumber);
        OtpFragment fragment = new OtpFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.top_app_bar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());

        // Set the phone number
        TextView phoneNumberTextView = view.findViewById(R.id.fragment_otp_phone_number);
        assert getArguments() != null;
        phoneNumber = getArguments().getString(ARGS_PHONE_NUMBER);
        phoneNumberTextView.setText(phoneNumber);

        // Initialize OTP digit fields
        otpDigits = new TextInputEditText[]{
                view.findViewById(R.id.otp_digit_1),
                view.findViewById(R.id.otp_digit_2),
                view.findViewById(R.id.otp_digit_3),
                view.findViewById(R.id.otp_digit_4),
                view.findViewById(R.id.otp_digit_5),
                view.findViewById(R.id.otp_digit_6)
        };

        // Set up OTP input behavior
        for (int i = 0; i < otpDigits.length; i++) {
            setupOtpInput(otpDigits[i], i);
        }

        // Request focus for the first digit automatically
        otpDigits[0].requestFocus();

        nextButton = view.findViewById(R.id.fragment_otp_next_button);
        nextButton.setOnClickListener(v -> {
            if (!isFilledOtp()) {
                return; // Notify error message
            }
            otpViewModel.verifyOtp(getOtp());
        });

        otpViewModel.sendOtp(phoneNumber);
        otpViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);

        return view;
    }

    private String getOtp() {
        StringBuilder result = new StringBuilder();
        if (isFilledOtp()) {
            for (TextInputEditText digit : otpDigits) {
                result.append(digit.getText());
            }
        }
        return result.toString();
    }

    private void updateUi(OtpUiState otpUiState) {
        if (otpUiState.isVerifiedOtp()) {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            startActivity(SignUpEnterNameActivity.newIntent(getActivity(), user));
        } else {
            if (otpUiState.getErrorMessage() != null) {
                Toast.makeText(requireActivity(), otpUiState.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupOtpInput(TextInputEditText current, int index) {
        // Disable manual focus
        current.setOnTouchListener((v, event) -> true);

        // Handle text changes
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    // Move focus to the next digit if available
                    if (index < otpDigits.length - 1) {
                        otpDigits[index + 1].requestFocus();
                    }
                    nextButton.setEnabled(isFilledOtp());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Handle delete key presses
        current.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (Objects.requireNonNull(current.getText()).length() > 0) {
                    // If there is content in the current input, clear it
                    current.setText("");
                } else if (index > 0) {
                    // If the current input is empty, move to the previous field and clear its content
                    TextInputEditText previous = otpDigits[index - 1];
                    previous.setText(""); // Clear the content
                    previous.requestFocus(); // Move focus to the previous digit
                }
                return true; // Indicate that the key event has been handled
            }
            return false;
        });
    }

    private boolean isFilledOtp() {
        return Objects.requireNonNull(otpDigits[otpDigits.length - 1].getText()).length() > 0;
    }
}
