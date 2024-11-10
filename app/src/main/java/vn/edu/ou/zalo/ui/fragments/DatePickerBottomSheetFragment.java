package vn.edu.ou.zalo.ui.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Field;
import java.util.Calendar;

import vn.edu.ou.zalo.R;

public class DatePickerBottomSheetFragment extends BottomSheetDialogFragment {
    private OnDateSelectedListener dateSelectedListener;

    public interface OnDateSelectedListener {
        void onDateSelected(int day, int month, int year);
    }

    public static DatePickerBottomSheetFragment newInstance(OnDateSelectedListener listener) {
        DatePickerBottomSheetFragment fragment = new DatePickerBottomSheetFragment();
        fragment.setDateSelectedListener(listener);
        return fragment;
    }

    public void setDateSelectedListener(OnDateSelectedListener dateSelectedListener) {
        this.dateSelectedListener = dateSelectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_picker_bottom_sheet, container, false);

        NumberPicker dayPicker = view.findViewById(R.id.day_picker);
        NumberPicker monthPicker = view.findViewById(R.id.month_picker);
        NumberPicker yearPicker = view.findViewById(R.id.year_picker);

        Calendar today = Calendar.getInstance();

        // Cài đặt các giá trị cho ngày, tháng, năm
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31); // Max giá trị mặc định là 31, sẽ cập nhật sau
        dayPicker.setValue(today.get(Calendar.DAY_OF_MONTH));

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(today.get(Calendar.MONTH) + 1);

        int currentYear = today.get(Calendar.YEAR);
        yearPicker.setMinValue(currentYear - 100);
        yearPicker.setMaxValue(currentYear);
        yearPicker.setValue(currentYear);

        // Function to update the days based on selected month and year
        updateDaysInMonth(dayPicker, monthPicker.getValue(), yearPicker.getValue());

        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            updateDaysInMonth(dayPicker, newVal, yearPicker.getValue());
        });

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            updateDaysInMonth(dayPicker, monthPicker.getValue(), newVal);
        });

        Button confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(v -> {
            int day = dayPicker.getValue();
            int month = monthPicker.getValue();
            int year = yearPicker.getValue();

            if (dateSelectedListener != null) {
                dateSelectedListener.onDateSelected(day, month, year);
            }
            dismiss();
        });

        return view;
    }

    private void updateDaysInMonth(NumberPicker dayPicker, int month, int year) {
        // Get the number of days in the selected month and year
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // month is 0-based in Calendar
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Set the day picker range
        dayPicker.setMaxValue(maxDay);

        // If the selected day is greater than the max days in the month, set it to the max day
        if (dayPicker.getValue() > maxDay) {
            dayPicker.setValue(maxDay);
        }
    }
}
