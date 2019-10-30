package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayFlavour;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVCuisineAdapter;
import com.restrosmart.restrohotel.SuperAdmin.Models.CityForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelTypeForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.StateForm;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_CITY;
import static com.restrosmart.restrohotel.ConstantVariables.GET_COUNTRY;
import static com.restrosmart.restrohotel.ConstantVariables.GET_STATE;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_CUISINE;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_TYPE;

public class OtherDetailsFragment extends Fragment {
    private View view;
    private SearchableSpinner spHotelType, spState, spCity;
    private Button btnNext;
    private TextInputEditText /*etvLattitude, etvLongitude*/etvArea, etvHotelAddress, etvHotelTableCount;
    private int hoteltypeId, stateId, cityId, areaId;
    private String mLattitude, mLongitude, mHotelAddress;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private ArrayList<HotelTypeForm> hotelTypeFormArrayList;
    private ArrayList<StateForm> stateFormArrayList;
    private ArrayList<CityForm> cityFormArrayList;
    private RecyclerView rvCuisine;
    private ArrayList<CuisineForm> cuisineFormArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_other_details, container, false);

        init();
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(HOTEL_TYPE, (service.getSAHotelType()));
        mRetrofitService.retrofitData(HOTEL_CUISINE, (service.getCuisine()));


        spHotelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hoteltypeId = hotelTypeFormArrayList.get(i).getHotelTypeId();


//                initRetrofitCallBack();
//                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
//                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
//                mRetrofitService.retrofitData(GET_STATE, (service.getState(hoteltypeId)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                stateId=stateFormArrayList.get(i).getStateId();
//
//                initRetrofitCallBack();
//                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
//                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
//                mRetrofitService.retrofitData(GET_CITY, (service.getCity(stateId)));
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                cityId=cityFormArrayList.get(i).getCityId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (isValid()) {
                AccountDetailsFragment accountDetailsFragment = new AccountDetailsFragment();

                Bundle bundle = new Bundle();
//                bundle.putInt("hoteltypeId", hoteltypeId);
//                bundle.putInt("stateId", stateId);
//                bundle.putInt("cityId", cityId);
//                bundle.putInt("areaId", areaId);
//                bundle.putString("hotelAddress", etvHotelAddress.getText().toString());

                //  bundle.putString("lattitude", mLattitude);
                //  bundle.putString("longitude", mLongitude);


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
        return view;
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectResponse = jsonObject.toString();
                switch (requestId) {
                    case HOTEL_TYPE:
                        try {
                            JSONObject object = new JSONObject(objectResponse);
                            int status = object.getInt("status");
                            if (status == 1) {

                                JSONArray jsonArray = object.getJSONArray("hoteltype");

                                hotelTypeFormArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    HotelTypeForm hotelTypeForm = new HotelTypeForm();
                                    hotelTypeForm.setHotelTypeId(object1.getInt("Hotel_Type_Id"));
                                    hotelTypeForm.setHotelTypeName(object1.getString("Hotel_Type_Name"));
                                    hotelTypeFormArrayList.add(hotelTypeForm);
                                }

                                ArrayAdapter<HotelTypeForm> typeFormArrayAdapter = new ArrayAdapter<HotelTypeForm>(getActivity(), android.R.layout.simple_spinner_item, hotelTypeFormArrayList);
                                typeFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spHotelType.setAdapter(typeFormArrayAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case HOTEL_CUISINE:

                        JSONObject object = null;
                        try {
                            object = new JSONObject(objectResponse);
                            int status = object.getInt("status");
                            if (status == 1) {

                                JSONArray jsonArray = object.getJSONArray("cuisine");
                                cuisineFormArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    CuisineForm cuisineForm = new CuisineForm();
                                    cuisineForm.setCuisineId(object1.getInt("Cuisine_Id"));
                                    cuisineForm.setCuisineName(object1.getString("Cuisine_Name"));
                                    cuisineFormArrayList.add(cuisineForm);
                                }

                                if (cuisineFormArrayList != null && cuisineFormArrayList.size() > 0) {
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    rvCuisine.setHasFixedSize(true);
                                    rvCuisine.setLayoutManager(linearLayoutManager);
                                    RVCuisineAdapter rvCuisineAdapter = new RVCuisineAdapter(getActivity(), cuisineFormArrayList);
                                    rvCuisine.setAdapter(rvCuisineAdapter);

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;


                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "RequestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Other Details");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       /* outState.putInt("hoteltypeId", hoteltypeId);
        outState.putInt("stateId", stateId);
        outState.putInt("cityId", cityId);
        outState.putInt("areaId", areaId);
        outState.putString("hotelAddress", mHotelAddress);*/

        // outState.putString("lattitude", mLattitude);
        //  outState.putString("longitude", mLongitude);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

           /* hoteltypeId = savedInstanceState.getInt("hoteltypeId");
            stateId = savedInstanceState.getInt("stateId");
            cityId = savedInstanceState.getInt("cityId");
            areaId = savedInstanceState.getInt("areaId");*/
            //    mLattitude = savedInstanceState.getString("mLattitude");
            //   mLongitude = savedInstanceState.getString("mLongitude");

           /* etvHotelName.setText(mHotelName);
            etvHotelEmail.setText(mHotelEmail);
            etvHotelMob.setText(mHotelMob);
            etvHotelPhone.setText(mHotelPhone);
            etvHotelAddress.setText(mHotelAddress);*/

        }
    }


    private void init() {
        spHotelType = view.findViewById(R.id.sp_hotel_type);
        //  spState = view.findViewById(R.id.sp_state);

        //  spCity = view.findViewById(R.id.sp_city);
        //  etvLattitude = view.findViewById(R.id.edt_hotel_lattitude);
        // etvLongitude = view.findViewById(R.id.edt_hotel_longitude);
        //   etvArea=view.findViewById(R.id.edt_hotel_area);
        btnNext = view.findViewById(R.id.btn_basic_location_details);
        etvHotelTableCount = view.findViewById(R.id.edt_hotel_table_count);

        // etvHotelAddress=view.findViewById(R.id.edt_hotel_address);

        hotelTypeFormArrayList = new ArrayList<>();
        rvCuisine = view.findViewById(R.id.rv_cuisine);
        cuisineFormArrayList = new ArrayList<>();
        //  stateFormArrayList=new ArrayList<>();
        // cityFormArrayList=new ArrayList<>();
    }

}
