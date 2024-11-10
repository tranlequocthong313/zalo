package vn.edu.ou.zalo.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.activities.SignUpAvatarActivity;

public class SignUpEnterMorePersonalInfoFragment extends Fragment {

    public static final String GENDER_CHOICE_BOTTOM_SHEET_TAG = "GenderChoiceBottomSheet";
    public static final String DATE_PICKER_BOTTOM_SHEET_TAG = "DatePickerBottomSheetFragment";
    private static final String ARGS_USER = "user";

    private TextInputEditText genderEditText;
    private TextInputEditText birthdayEditText;
    private Button nextButton;
    private static final Map<User.Gender, Integer> genderTexts = Map.of(
            User.Gender.MALE, R.string.male,
            User.Gender.FEMALE, R.string.female,
            User.Gender.UNKNOWN, R.string.prefer_not_to_say
    );
    private User user;

    public static SignUpEnterMorePersonalInfoFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_USER, user);

        SignUpEnterMorePersonalInfoFragment fragment = new SignUpEnterMorePersonalInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        assert getArguments() != null;
        user = getArguments().getParcelable(ARGS_USER);

        View view = inflater.inflate(R.layout.fragment_sign_up_enter_more_personal_info, container, false);

        birthdayEditText = view.findViewById(R.id.fragment_signup_enter_more_personal_info_birthday_text_input_view);
        birthdayEditText.setOnClickListener(v -> openBirthdayBottomSheet());

        genderEditText = view.findViewById(R.id.fragment_signup_enter_more_personal_info_gender_text_input_view);
        genderEditText.setOnClickListener(v -> openGenderChoiceBottomSheet());

        nextButton = view.findViewById(R.id.fragment_signup_enter_more_personal_info_next_button);
        nextButton.setOnClickListener(v -> {
            startActivity(SignUpAvatarActivity.newIntent(getActivity(), user));
        });

        return view;
    }

    private boolean isValidInputs() {
        return Objects.requireNonNull(birthdayEditText.getText()).length() > 0 && Objects.requireNonNull(genderEditText.getText()).length() > 0;
    }

    private void openBirthdayBottomSheet() {
        DatePickerBottomSheetFragment datePickerBottomSheet = DatePickerBottomSheetFragment.newInstance((day, month, year) -> {
            String selectedDate = day + "/" + month + "/" + year;
            if (isAgeValid(day, month, year)) {
                birthdayEditText.setText(selectedDate);
                nextButton.setEnabled(isValidInputs());
                user.setBirthdate(new Date(year, month, day).getTime());
            } else {
                Toast.makeText(getContext(), "Birthday must >= 14", Toast.LENGTH_SHORT).show(); // TODO: for dev purpose
                birthdayEditText.setText("");
            }

        });
        datePickerBottomSheet.show(requireActivity().getSupportFragmentManager(), DATE_PICKER_BOTTOM_SHEET_TAG);
    }

    private void openGenderChoiceBottomSheet() {
        GenderChoiceBottomSheetFragment genderChoiceBottomSheet = GenderChoiceBottomSheetFragment.newInstance(gender -> {
            Integer textId = genderTexts.get(gender);
            assert textId != null;
            genderEditText.setText(getResources().getString(textId));
            nextButton.setEnabled(isValidInputs());
            user.setGender(gender);
        });
        genderChoiceBottomSheet.show(getChildFragmentManager(), GENDER_CHOICE_BOTTOM_SHEET_TAG);
    }

    private boolean isAgeValid(int day, int month, int year) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(year, month - 1, day);

        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age >= 14;
    }
}
