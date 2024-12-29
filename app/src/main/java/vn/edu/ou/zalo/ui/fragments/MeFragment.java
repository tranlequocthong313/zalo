package vn.edu.ou.zalo.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.Post;
import vn.edu.ou.zalo.data.models.Story;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.activities.AuthActivity;
import vn.edu.ou.zalo.ui.activities.InforActivity;
import vn.edu.ou.zalo.ui.fragments.adapters.PostAdapter;
import vn.edu.ou.zalo.ui.fragments.adapters.StoryAdapter;
import vn.edu.ou.zalo.ui.states.TimelineUiState;
import vn.edu.ou.zalo.ui.viewmodels.AuthViewModel;
import vn.edu.ou.zalo.ui.viewmodels.TimelineViewModel;

@AndroidEntryPoint
public class MeFragment extends Fragment {
    @Inject
    AuthViewModel authViewModel;
    @Inject
    TimelineViewModel timelineViewModel;

    public static Fragment newInstance() {
        return new MeFragment();
    }
    private TextView usernameTextView;
    private ImageView avatar;
    private ConstraintLayout blockToInfor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        TextView signoutButton = view.findViewById(R.id.fragment_me_signout_button);


        avatar = view.findViewById(R.id.fragment_me_avatar);
        timelineViewModel.fetchData();
        timelineViewModel.getUiState().observe(getViewLifecycleOwner(), this::updateUi);
        usernameTextView = view.findViewById(R.id.fragment_me_fullname);

        blockToInfor = view.findViewById(R.id.block_to_infor);
        blockToInfor.setOnClickListener(v ->{
          startActivity(InforActivity.newIntent(getActivity()));
      });

        signoutButton.setOnClickListener(v -> {
            authViewModel.signOut();
            startActivity(AuthActivity.newIntent(getActivity()));
            requireActivity().finish();
        });
        return view;
    }
    private void updateUi(TimelineUiState timelineUiState) {
        if (timelineUiState.isLoading()) {
            return;
        }
        User signedInuser = timelineUiState.getSignedInUser();
        if (signedInuser != null && signedInuser.getAvatarUrl() != null) {
            Glide.with(avatar.getContext())
                    .load(signedInuser.getAvatarUrl())
                    .into(avatar);
        }
        if (signedInuser != null && signedInuser.getFullName() != null) {
            usernameTextView.setText(signedInuser.getFullName());
        }

    }

}