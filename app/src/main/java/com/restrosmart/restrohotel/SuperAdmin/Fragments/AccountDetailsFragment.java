package com.restrosmart.restrohotel.SuperAdmin.Fragments;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.restrosmart.restrohotel.R;

public  class AccountDetailsFragment extends Fragment {
    private  String mGstnNo,mHotelCgst,mHotelSgst;
    private  View view;
    private Button btnSave;
    private TextInputEditText edtGstnNo,edtHotelCgst,edtHotelSgst;
    private RadioButton radioButtonYes,radioButtonNo;
    private RadioGroup radioGroupGstn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_details, null);
        init();

        radioGroupGstn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioButtonYes.isChecked())
                {
                    edtGstnNo.setVisibility(View.VISIBLE);
                    edtHotelCgst.setVisibility(View.VISIBLE);
                    edtHotelSgst.setVisibility(View.VISIBLE);
                }
                else
                {
                    edtGstnNo.setVisibility(View.GONE);
                    edtHotelCgst.setVisibility(View.GONE);
                    edtHotelSgst.setVisibility(View.GONE);
                }

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Hotel Account Details");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("gstnNo",mGstnNo );
        outState.putString("hotelCgst", mHotelCgst);
        outState.putString("hotelSgst", mHotelSgst);
     /*   outState.putString("hotelRanking", mHotelRanking);
        outState.putString("hotelRating", mHotelRating);
        outState.putString("hotelVisitCnt",mHotelVisitCnt);
        outState.putString("hotelStatus",mHotelStatus);*/

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            mGstnNo = savedInstanceState.getString("gstnNo");
            mHotelCgst = savedInstanceState.getString("hotelCgst");
            mHotelSgst = savedInstanceState.getString("hotelSgst");
           /* mHotelRanking = savedInstanceState.getString("hotelRanking");
            mHotelRating = savedInstanceState.getString("hotelRating");
            mHotelVisitCnt=savedInstanceState.getString("hotelVisitCnt");
            mHotelStatus=savedInstanceState.getString("hotelStatus");*/

            edtGstnNo.setText(mGstnNo);
            edtHotelCgst.setText(mHotelCgst);
            edtHotelSgst.setText(mHotelSgst);
          /*  edtHotelRanking.setText(mHotelRanking);
            edtHotelRating.setText(mHotelRating);
            edtHotelVisitCnt.setText(mHotelVisitCnt);
            edtHotelStatus.setText(mHotelStatus);
*/

        }
    }





    private void init() {
        edtGstnNo=view.findViewById(R.id.edt_hote_gstn_no);
      //  edtHotelRating=view.findViewById(R.id.edt_hotel_rating);
        edtHotelCgst=view.findViewById(R.id.edt_hotel_cgst);
        edtHotelSgst=view.findViewById(R.id.edt_hotel_sgst);
//        edtHotelRegDate=view.findViewById(R.id.edt_hotel_reg_date);
//        edtHotelVisitCnt=view.findViewById(R.id.edt_hotel_visit_count);
//        edtHotelRanking=view.findViewById(R.id.edt_hotel_ranking);
//        edtHotelStatus=view.findViewById(R.id.edt_hotel_status);

        btnSave=view.findViewById(R.id.btn_account_hotel_details);
        radioButtonYes=view.findViewById(R.id.radio_btn_yes);
        radioButtonNo=view.findViewById(R.id.radio_btn_no);
        radioGroupGstn=view.findViewById(R.id.radio_group);


    }

}
