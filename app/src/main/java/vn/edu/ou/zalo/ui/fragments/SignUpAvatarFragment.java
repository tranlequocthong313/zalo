package vn.edu.ou.zalo.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;

public class SignUpAvatarFragment extends Fragment {
    private static final String ARGS_USER = "user";
    private ImageView avatarImageView;

    public static SignUpAvatarFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_USER, user);

        SignUpAvatarFragment fragment = new SignUpAvatarFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_avatar, container, false);
        avatarImageView = view.findViewById(R.id.fragment_signup_avatar_image_view);
        User user = getArguments() != null ? getArguments().getParcelable(ARGS_USER) : null;
        if (user != null) {
            String avatarUrl = generateAvatarUrl(user);
            user.setAvatarUrl(avatarUrl);
            loadAvatar(avatarUrl);
        }

        Button skipButton = view.findViewById(R.id.fragment_signup_avatar_skip_button);
        skipButton.setOnClickListener(v -> showSkipConfirmationDialog(user));

        return view;
    }

    private String generateAvatarUrl(User user) {
        String randomColor = generateRandomColor();
        String fullName = user.getFullName();
        return "https://ui-avatars.com/api/?name=" + fullName + "&background=" + randomColor + "&color=fff&size=240";
    }

    private String generateRandomColor() {
        Random random = new Random();
        int color = random.nextInt(0xFFFFFF + 1);
        return String.format("%06X", color);
    }

    private void loadAvatar(String avatarUrl) {
        Glide.with(this)
                .load(avatarUrl)
                .into(avatarImageView);
    }

    private void showSkipConfirmationDialog(User user) {
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.Theme_Zalo_AlertDialog)
                .setTitle(R.string.confrim)
                .setMessage(R.string.skip_avatar_confirm_description)
                .setPositiveButton(R.string.add_image, (dialog1, which) -> {
                    // TODO: Handle add image action
                })
                .setNegativeButton(R.string.skip, (dialog12, which) -> {
                    Log.d("SignUpAvatarFragment", "Finish Register Process: " + user.toString());
                })
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            TextView messageTextView = dialog.findViewById(android.R.id.message);
            if (messageTextView != null) {
                messageTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorOutline));
            }
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            positiveButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorError));
            negativeButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorOnSurfaceVariant));
        });

        dialog.show();
    }
}
