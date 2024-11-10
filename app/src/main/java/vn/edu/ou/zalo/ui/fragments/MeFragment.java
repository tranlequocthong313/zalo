package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.activities.AuthActivity;
import vn.edu.ou.zalo.ui.viewmodels.AuthViewModel;

@AndroidEntryPoint
public class MeFragment extends Fragment {
    @Inject
    AuthViewModel authViewModel;

    public static Fragment newInstance() {
        return new MeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        Button signoutButton = view.findViewById(R.id.fragment_me_signout_button);
        signoutButton.setOnClickListener(v -> {
            authViewModel.signOut();
            startActivity(AuthActivity.newIntent(getActivity()));
            requireActivity().finish();
        });
        return view;
    }
}