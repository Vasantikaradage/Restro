package com.restrosmart.restro.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.restrosmart.restro.R;

public class AdminSettings extends Fragment {

    private  View view;
    private LinearLayout changePassword, privacy, termsofuse, logout;

    // ImageButton btn_change_password,btn_privacy_policy,btn_terms_of_use,btn_logout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_settings, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ActivityChangePassword.class);
                startActivity(i);
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i = new Intent(getActivity(), ActivityCategoryMenu.class);
               // startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), AdminLogin.class);
                startActivity(i);
            }
        });
    }

    private void init() {
        logout = view.findViewById(R.id.linear_logout);
        privacy = view.findViewById(R.id.linear_privacy_policy);
        changePassword = view.findViewById(R.id.linear_change_password);
        termsofuse = view.findViewById(R.id.linear_terms_of_use);
    }
}
