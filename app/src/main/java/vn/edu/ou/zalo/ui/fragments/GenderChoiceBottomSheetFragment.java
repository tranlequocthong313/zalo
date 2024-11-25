package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;

public class GenderChoiceBottomSheetFragment extends BottomSheetDialogFragment {

    public interface OnGenderSelectedListener {
        void onGenderSelected(User.Gender gender);
    }

    private OnGenderSelectedListener genderSelectedListener;

    public static GenderChoiceBottomSheetFragment newInstance(OnGenderSelectedListener listener) {
        GenderChoiceBottomSheetFragment fragment = new GenderChoiceBottomSheetFragment();
        fragment.setGenderSelectedListener(listener);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gender_choice_bottom_sheet, container, false);

        TextView maleOption = view.findViewById(R.id.fragment_gender_choice_male);
        TextView femaleOption = view.findViewById(R.id.fragment_gender_choice_female);
        TextView notSpecifiedOption = view.findViewById(R.id.fragment_gender_choice_not_share);

        maleOption.setOnClickListener(v -> selectGender(User.Gender.MALE));
        femaleOption.setOnClickListener(v -> selectGender(User.Gender.FEMALE));
        notSpecifiedOption.setOnClickListener(v -> selectGender(User.Gender.UNKNOWN));

        return view;
    }

    private void selectGender(User.Gender gender) {
        if (genderSelectedListener != null) {
            genderSelectedListener.onGenderSelected(gender);
        }
        dismiss();
    }

    public void setGenderSelectedListener(OnGenderSelectedListener genderSelectedListener) {
        this.genderSelectedListener = genderSelectedListener;
    }
}
