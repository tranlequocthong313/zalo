package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;

import vn.edu.ou.zalo.R;

public class LoginFragment extends Fragment {

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        MaterialToolbar toolbar = view.findViewById(R.id.top_app_bar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());

        EditText phoneNumberEditText = view.findViewById(R.id.fragment_login_phone_number_text_input);
        phoneNumberEditText.requestFocus();

        return view;
    }
}