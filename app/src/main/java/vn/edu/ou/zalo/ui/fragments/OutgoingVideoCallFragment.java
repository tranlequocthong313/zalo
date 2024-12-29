package vn.edu.ou.zalo.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.ou.zalo.R;

public class OutgoingVideoCallFragment extends Fragment {

    public static OutgoingVideoCallFragment newInstance() {
        return new OutgoingVideoCallFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calling_video_call, container, false);
    }
}