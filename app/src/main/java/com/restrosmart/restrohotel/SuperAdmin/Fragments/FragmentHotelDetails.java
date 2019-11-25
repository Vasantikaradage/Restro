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
import android.widget.LinearLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivityAddHotel;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVHotelDeatailAdapter;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelImageForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.TagsForm;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;
import static com.restrosmart.restrohotel.ConstantVariables.GET_SA_ALL_HOTEL;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class FragmentHotelDetails extends Fragment {
    private View view;
    private FrameLayout btnAddHotel;
    private  RetrofitService mRetrofitService;
    private SpinKitView skLoading;
    private IResult mResultCallBack;
    private  RecyclerView rvHotelDetails;
    private  ArrayList<HotelForm> hotelFormArrayList;
    private   RVHotelDeatailAdapter rvHotelDeatailAdapter;
    private Sessionmanager sessionmanager;
    private HashMap<String, String> superAdminInfo;
    private  ArrayList<CuisineForm> cuisineFormArrayList;
    private  ArrayList<TagsForm> tagsFormArrayList;
    private  ArrayList<HotelImageForm> hotelImageFormArrayList;
    private LinearLayout llNoHotelData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hotel_details, null);
        init();
        superAdminInfo = sessionmanager.getSuperAdminDetails();

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
        sessionmanager=new Sessionmanager(getActivity());
        cuisineFormArrayList=new ArrayList<>();
        tagsFormArrayList=new ArrayList<>();
        hotelImageFormArrayList=new ArrayList<>();
        skLoading=view.findViewById(R.id.skLoading);
        llNoHotelData=view.findViewById(R.id.llNoHotelData);
    }

    @Override
    public void onResume() {
        super.onResume();
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_SA_ALL_HOTEL, (service.getSAHotelDetails(Integer.parseInt(superAdminInfo.get(EMP_ID)))));
    }

    private void retrofitCallBack() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                String objectInfo=jsonObject.toString();

                try {
                    JSONObject object=new JSONObject(objectInfo);
                    int status=object.getInt("status");
                    if(status==1){

                        JSONArray jsonArray=object.getJSONArray("Hotellist");

                        hotelFormArrayList.clear();
                        hotelImageFormArrayList.clear();
                        cuisineFormArrayList.clear();
                        tagsFormArrayList.clear();
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject hotelObject=jsonArray.getJSONObject(i);
                            HotelForm hotelForm=new HotelForm();
                            hotelForm.setHotelName(hotelObject.getString("Hotel_Name"));
                            hotelForm.setHotelAddress(hotelObject.getString("Hotel_Address"));
                            hotelForm.setHotelMob(hotelObject.getString("Hotel_Mob"));
                            hotelForm.setGstnNo(hotelObject.getString("Hotel_Gstinno"));
                            hotelForm.setHotelArea(hotelObject.getString("Hotel_Area"));
                            hotelForm.setHotelCity(hotelObject.getString("Hotel_City"));
                            hotelForm.setHotelEmail(hotelObject.getString("Hotel_Email"));
                            hotelForm.setHotelCountry(hotelObject.getString("Hotel_Country"));
                            hotelForm.setHotelState(hotelObject.getString("Hotel_State"));
                            hotelForm.setHotelPhone(hotelObject.getString("Hotel_Phone"));

                            JSONArray cuisineArray=hotelObject.getJSONArray("cusines");
                            cuisineFormArrayList=new ArrayList<>();
                            for(int c=0; c<cuisineArray.length(); c++)
                            {
                                JSONObject cuisineObject=cuisineArray.getJSONObject(c);
                                CuisineForm cuisineForm=new CuisineForm();
                                cuisineForm.setCuisineId(cuisineObject.getInt("Cuisine_Id"));
                                cuisineForm.setCuisineName(cuisineObject.getString("Cuisine_Name"));
                                cuisineFormArrayList.add(cuisineForm);
                            }
                            hotelForm.setCuisineFormArrayList(cuisineFormArrayList);
                            JSONArray tagsArray=hotelObject.getJSONArray("tags");
                            tagsFormArrayList=new ArrayList<>();
                            for (int t=0;t<tagsArray.length(); t++)
                            {
                                JSONObject  tagObject=tagsArray.getJSONObject(t);
                                TagsForm tagsForm=new TagsForm();
                                tagsForm.setTagId(tagObject.getInt("Tag_Id"));
                                tagsForm.setTagName(tagObject.getString("Tag_Name"));
                                tagsFormArrayList.add(tagsForm);
                            }

                            JSONArray imageArray=hotelObject.getJSONArray("images");
                            hotelForm.setTagsFormArrayList(tagsFormArrayList);
                            hotelImageFormArrayList=new ArrayList<>();
                            for(int x=0; x<imageArray.length(); x++)
                            {
                                JSONObject imageObject=imageArray.getJSONObject(x);
                                HotelImageForm hotelImageForm=new HotelImageForm();
                                hotelImageForm.setHotelImageId(imageObject.getInt("Hotel_Img_Id"));
                                hotelImageForm.setHotelImageName(imageObject.getString("Hotel_Img"));
                                hotelImageFormArrayList.add(hotelImageForm);
                            }

                            hotelForm.setHotelImageFormArrayList(hotelImageFormArrayList);
                            hotelForm.setGstnNo(hotelObject.getString("Hotel_Gstinno"));
                            hotelForm.setHotelCgst(hotelObject.getString("Hotel_CGST"));
                            hotelForm.setHotelSgst(hotelObject.getString("Hotel_SGST"));
                            hotelForm.setHotelRanking(hotelObject.getString("Hotel_Ranking"));
                            hotelForm.setHotelRating(hotelObject.getString("Hotel_Avg_rating"));
                            hotelForm.setHoteltype(hotelObject.getString("Hotel_Type_Name"));
                            hotelForm.setHotelTableCount(hotelObject.getString("Hotel_Table_Count"));
                            hotelForm.setHotelStartTime(hotelObject.getString("Start_Time"));
                            hotelForm.setHotelEndTime(hotelObject.getString("End_Time"));

                            hotelFormArrayList.add(hotelForm);

                        }

                        if(hotelFormArrayList.size()!=0 ) {
                            llNoHotelData.setVisibility(View.GONE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            rvHotelDetails.setLayoutManager(linearLayoutManager);
                            rvHotelDetails.setHasFixedSize(true);
                            rvHotelDetails.getLayoutManager().setMeasurementCacheEnabled(false);
                            rvHotelDeatailAdapter = new RVHotelDeatailAdapter(getActivity(), hotelFormArrayList);
                            rvHotelDetails.setAdapter(rvHotelDeatailAdapter);
                        }


                    }
                    else
                    {
                        llNoHotelData.setVisibility(View.VISIBLE);

                    }
                    skLoading.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","RequestId"+requestId);
                Log.d("","RetrofitError"+error);
            }
        };
    }

}
