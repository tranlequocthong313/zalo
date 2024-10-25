package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;

@AndroidEntryPoint
public class OfficialAccountContactsFragment extends Fragment {
    public static OfficialAccountContactsFragment newInstance() {
        return new OfficialAccountContactsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_official_account_contacts, container, false);
    }
}