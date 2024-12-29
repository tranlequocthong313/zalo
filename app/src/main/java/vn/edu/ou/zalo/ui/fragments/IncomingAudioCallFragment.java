package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.ou.zalo.R;
import vn.edu.ou.zalo.ui.viewmodels.CallViewModel;

@AndroidEntryPoint
public class IncomingAudioCallFragment extends Fragment {
    private final static String ARGS_CALL_ID = "call_id";

    @Inject
    CallViewModel callViewModel;

    public static IncomingAudioCallFragment newInstance(String callId) {
        Bundle b = new Bundle();
        b.putString(ARGS_CALL_ID, callId);
        IncomingAudioCallFragment f = new IncomingAudioCallFragment();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incoming_audio_call, container, false);
        assert getArguments() != null;
        String callId = getArguments().getString(ARGS_CALL_ID);
        callViewModel.answerCall(callId);
        return view;
    }
}