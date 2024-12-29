package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import vn.edu.ou.zalo.R;

public class IncomingVideoCallFragment extends Fragment {
    public static IncomingVideoCallFragment newInstance() {
        return new IncomingVideoCallFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_incoming_video_call, container, false);
    }
}