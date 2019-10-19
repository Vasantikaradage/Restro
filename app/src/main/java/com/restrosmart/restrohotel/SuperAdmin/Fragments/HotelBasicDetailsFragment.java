package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.restrosmart.restrohotel.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class HotelBasicDetailsFragment extends Fragment {
    private View view;
    private Button btnNext;
    private TextInputEditText etvHotelName,etvHotelMob,etvHotelPhone,etvHotelAddress,etvHotelEmail;
    private  String mHotelName,mHotelMob,mHotelPhone,mHotelAddress,mHotelEmail,mHotelType;
    private SearchableSpinner spHoteltype;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_basic_hotel_details, container, false);

        init();

        spHoteltype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (isValid()) {
                    LocationDetailsFragment locationDetailsFragment = new LocationDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("hotelName", etvHotelName.getText().toString());
                    bundle.putString("hotelEmail", etvHotelEmail.getText().toString());
                    bundle.putString("hotelMob", etvHotelMob.getText().toString());
                    bundle.putString("hotelPhone", etvHotelPhone.getText().toString());
                    bundle.putString("hotelAddress", etvHotelAddress.getText().toString());


                    locationDetailsFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    // Begin Fragment transaction.
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the layout holder with the required Fragment object.
                    fragmentTransaction.replace(R.id.flContainer, locationDetailsFragment);

                    // To get back again
                    fragmentTransaction.addToBackStack(null);

                    // Commit the Fragment replace action.
                    fragmentTransaction.commit();
                }
           // }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Hotel Basic Details");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("hotelName",mHotelName );
        outState.putString("hotelEmail", mHotelEmail);
        outState.putString("hotelMob", mHotelMob);
        outState.putString("hotelPhone", mHotelPhone);
        outState.putString("hotelAddress", mHotelAddress);
        outState.putString("hotelType",mHotelType);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            mHotelName = savedInstanceState.getString("hotelName");
            mHotelEmail = savedInstanceState.getString("hotelEmail");
            mHotelMob = savedInstanceState.getString("hotelMob");
            mHotelPhone = savedInstanceState.getString("hotelPhone");
            mHotelAddress = savedInstanceState.getString("hotelAddress");
            mHotelType=savedInstanceState.getString("hotelType");

           etvHotelName.setText(mHotelName);
            etvHotelEmail.setText(mHotelEmail);
            etvHotelMob.setText(mHotelMob);
            etvHotelPhone.setText(mHotelPhone);
            etvHotelAddress.setText(mHotelAddress);

        }
    }

    private void init() {
        etvHotelName=view.findViewById(R.id.edt_hotel_name);
        etvHotelEmail=view.findViewById(R.id.edt_email);
        etvHotelMob=view.findViewById(R.id.edt_hotel_mob);
        etvHotelPhone=view.findViewById(R.id.edt_hotel_phone);
        etvHotelAddress=view.findViewById(R.id.edt_hotel_address);
        spHoteltype=view.findViewById(R.id.sp_hotel_type);

        btnNext=view.findViewById(R.id.btn_basic_hotel_details);
    }
}
