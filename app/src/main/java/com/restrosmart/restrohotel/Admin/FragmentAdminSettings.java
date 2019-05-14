package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.restrosmart.restrohotel.R;

public class FragmentAdminSettings extends Fragment {

    private  View view;
    private RelativeLayout changePassword, privacyPolicy, termsAndCondition, userGuide,helpSupport;

    // ImageButton btn_change_password,btn_privacy_policy,btn_terms_of_use,btn_logout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, null);
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

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i = new Intent(getActivity(), ActivityCategoryMenu.class);
               // startActivity(i);
            }
        });

        userGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent i = new Intent(getActivity(), ActivityAdminLogin.class);
                startActivity(i);*/
            }
        });

        termsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        
        helpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    private void init() {
        userGuide = view.findViewById(R.id.rlUserGuide);
        privacyPolicy = view.findViewById(R.id.rlPrivacy);
        helpSupport=view.findViewById(R.id.rlSupport);
        changePassword = view.findViewById(R.id.rlChangePassword);
        termsAndCondition = view.findViewById(R.id.rlTermsConditions);
    }
}
