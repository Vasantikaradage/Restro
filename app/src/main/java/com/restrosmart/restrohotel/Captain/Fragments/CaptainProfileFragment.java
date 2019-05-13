package com.restrosmart.restrohotel.Captain.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restrosmart.restrohotel.R;

public class CaptainProfileFragment extends Fragment {

    private View view;
    private ImageView ivCapProfileImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_captain_profile, container, false);

        init();
        return view;
    }

    private void init() {

    }
}
