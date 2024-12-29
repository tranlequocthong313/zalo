package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.data.models.User;
import vn.edu.ou.zalo.ui.viewmodels.CallViewModel;

@AndroidEntryPoint
public class OutgoingAudioCallFragment extends Fragment {
    private final static String ARGS_TO = "to";

    @Inject
    CallViewModel callViewModel;

    public static OutgoingAudioCallFragment newInstance(User to) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_TO, to);
        OutgoingAudioCallFragment f = new OutgoingAudioCallFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calling_audio_call, container, false);

        ImageView avatarImageView = view.findViewById(R.id.fragment_calling_audio_call_avatar);
        TextView nameTextView = view.findViewById(R.id.fragment_calling_audio_call_name);
        ImageView endButton = view.findViewById(R.id.fragment_calling_audio_call_end_button);
        endButton.setOnClickListener(v -> requireActivity().finish());
        ImageView backImageView = view.findViewById(R.id.fragment_calling_audio_call_back_button);
        backImageView.setOnClickListener(v -> requireActivity().finish());

        assert getArguments() != null;
        User to = getArguments().getParcelable(ARGS_TO);
        if (to != null) {
            nameTextView.setText(to.getFullName());
            Glide.with(avatarImageView)
                    .load(to.getAvatarUrl())
                    .signature(new ObjectKey(to.getId()))
                    .into(avatarImageView);
            callViewModel.makeCall(to, false);
        }

        return view;
    }
}