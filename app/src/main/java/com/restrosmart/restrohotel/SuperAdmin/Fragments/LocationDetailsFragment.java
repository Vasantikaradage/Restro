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
import android.widget.Button;

import com.restrosmart.restrohotel.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class LocationDetailsFragment extends Fragment {
    private  View view;
    private SearchableSpinner spCountry,spState,spCity,spArea;
    private Button btnNext;
    private TextInputEditText etvLattitude,etvLongitude;
    private  int countryId,stateId,cityId,areaId;
    private  String mLattitude,mLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_location_details, container, false);

        init();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (isValid()) {
                AccountDetailsFragment accountDetailsFragment = new AccountDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("countryId", countryId);
                bundle.putInt("stateId", stateId);
                bundle.putInt("cityId", cityId);
                bundle.putInt("areaId",areaId);
                bundle.putString("lattitude", mLattitude);
                bundle.putString("longitude",mLongitude);


                accountDetailsFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Begin Fragment transaction.
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the layout holder with the required Fragment object.
                fragmentTransaction.replace(R.id.flContainer, accountDetailsFragment);

                // To get back again
                fragmentTransaction.addToBackStack(null);

                // Commit the Fragment replace action.
                fragmentTransaction.commit();
            }
            // }
        });
        return  view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Location Details");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("countryId",countryId );
        outState.putInt("stateId", stateId);
        outState.putInt("cityId", cityId);
        outState.putInt("areaId", areaId);
        outState.putString("lattitude", mLattitude);
        outState.putString("longitude",mLongitude);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            countryId = savedInstanceState.getInt("countryId");
            stateId = savedInstanceState.getInt("stateId");
            cityId = savedInstanceState.getInt("cityId");
            areaId = savedInstanceState.getInt("areaId");
            mLattitude = savedInstanceState.getString("mLattitude");
            mLongitude=savedInstanceState.getString("mLongitude");

           /* etvHotelName.setText(mHotelName);
            etvHotelEmail.setText(mHotelEmail);
            etvHotelMob.setText(mHotelMob);
            etvHotelPhone.setText(mHotelPhone);
            etvHotelAddress.setText(mHotelAddress);*/

        }
    }


    private void init() {
        spCountry=view.findViewById(R.id.sp_country);
        spState=view.findViewById(R.id.sp_state);
        spArea=view.findViewById(R.id.sp_area);
        spCity=view.findViewById(R.id.sp_city );
        etvLattitude=view.findViewById(R.id.edt_hotel_lattitude);
        etvLongitude=view.findViewById(R.id.edt_hotel_longitude);
        btnNext=view.findViewById(R.id.btn_basic_location_details);
    }

}
