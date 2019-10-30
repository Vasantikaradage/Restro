package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivityAddHotel;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVHotelDeatailAdapter;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import java.util.ArrayList;
import retrofit2.Response;
import static com.restrosmart.restrohotel.ConstantVariables.GET_SA_ALL_HOTEL;

public class FragmentHotelDetails extends Fragment {
    private View view;
    private FrameLayout btnAddHotel;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  RecyclerView rvHotelDetails;
    private  ArrayList<HotelForm> hotelFormArrayList;
    private   RVHotelDeatailAdapter rvHotelDeatailAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hotel_details, null);
        init();
        btnAddHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAddHotel.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void init() {
        rvHotelDetails = (RecyclerView) view.findViewById(R.id.recycler_hotel_details);
        btnAddHotel = (FrameLayout) view.findViewById(R.id.btn_add_hotel);
        hotelFormArrayList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_SA_ALL_HOTEL, (service.getSAHotelDetails()));
    }

    private void retrofitCallBack() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                String objectInfo=jsonObject.toString();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                rvHotelDetails.setLayoutManager(linearLayoutManager);
                rvHotelDetails.setHasFixedSize(true);
                rvHotelDetails.getLayoutManager().setMeasurementCacheEnabled(false);
                rvHotelDeatailAdapter=new RVHotelDeatailAdapter(getActivity(),hotelFormArrayList);
                rvHotelDetails.setAdapter(rvHotelDeatailAdapter);
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","RequestId"+requestId);
                Log.d("","RetrofitError"+error);
            }
        };
    }

}
