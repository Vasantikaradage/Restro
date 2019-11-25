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
import android.widget.Spinner;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVCuisineAdapter;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVTagsAdapter;
import com.restrosmart.restrohotel.SuperAdmin.Interfaces.CuisineListener;
import com.restrosmart.restrohotel.SuperAdmin.Interfaces.TagListener;
import com.restrosmart.restrohotel.SuperAdmin.Models.CityForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelTypeForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.StateForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.TagsForm;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;


import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_CUISINE;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_OTHER_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_TAGS;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_TYPE;

public class OtherDetailsFragment extends Fragment {
    private View view;
    private Spinner spHotelType, spState, spCity;
    private Button btnNext;
    private TextInputEditText /*etvLattitude, etvLongitude*/etvArea, etvHotelAddress, etvHotelTableCount;
    private int hoteltypeId, tableCount, cityId, areaId;
    private String mLattitude, mLongitude, mHotelAddress, mCuisine, mTags;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private ArrayList<HotelTypeForm> hotelTypeFormArrayList;
    private ArrayList<StateForm> stateFormArrayList;
    private ArrayList<CityForm> cityFormArrayList;
    private RecyclerView rvCuisine, rvTags;
    private ArrayList<CuisineForm> cuisineFormArrayList;
    private ArrayList<TagsForm> tagsFormArrayList;

    private JSONArray cuisineArray, tagArray;
    private ArrayList<CuisineForm> arrayListCheckedCuisine;



    private  ArrayList<CuisineForm> getArrayListCheckedCuisinePos;
    private ArrayList<TagsForm> arrayListCheckedTags;
    private   Bundle bundle;
    private SpinKitView skLoading;

    private  int getHoteltypeId;
    private  int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_other_details, container, false);

       bundle = getArguments();

       /* if (bundle != null) {

            hotelId = bundle.getInt("hotelId");
        }*/

        init();
       /* initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(HOTEL_TYPE, (service.getSAHotelType()));
        mRetrofitService.retrofitData(HOTEL_CUISINE, (service.getCuisine()));
        mRetrofitService.retrofitData(HOTEL_TAGS, (service.getTags()));*/


        spHotelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hoteltypeId = hotelTypeFormArrayList.get(i).getHotelTypeId();
                setGetHoteltypeId(hoteltypeId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skLoading.setVisibility(View.VISIBLE);

                AccountDetailsFragment accountDetailsFragment = new AccountDetailsFragment();

                bundle.putInt("hoteltypeId", hoteltypeId);
                bundle.putInt("tableCount", Integer.parseInt(etvHotelTableCount.getText().toString()));
                bundle.putString("mCuisine", cuisineArray.toString());
                bundle.putString("mTags", tagArray.toString());


                accountDetailsFragment.setArguments(bundle);
                Toast.makeText(getActivity(), "success2", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Begin Fragment transaction.
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the layout holder with the required Fragment object.
                fragmentTransaction.replace(R.id.flContainer, accountDetailsFragment);

                // To get back again
                fragmentTransaction.addToBackStack(null);

                // Commit the Fragment replace action.
                fragmentTransaction.commit();
                skLoading.setVisibility(View.GONE);


//                initRetrofitCallBack();
//                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
//                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
//                mRetrofitService.retrofitData(HOTEL_OTHER_DETAILS, (service.otherHotelDetails(hoteltypeId,
//                        etvHotelTableCount.getText().toString(),
//                        hotelId,
//                        cuisineArray.toString(),
//                        tagArray.toString())));
            }
        });
        return view;
    }

    public ArrayList<CuisineForm> getGetArrayListCheckedCuisinePos() {
        return getArrayListCheckedCuisinePos;
    }

    public void setGetArrayListCheckedCuisinePos(ArrayList<CuisineForm> getArrayListCheckedCuisinePos) {
        this.getArrayListCheckedCuisinePos = getArrayListCheckedCuisinePos;
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
                                HotelTypeForm hotelTypeForm1 = new HotelTypeForm();
                                hotelTypeForm1.setHotelTypeId(0);
                                hotelTypeForm1.setHotelTypeName("Select Hotel type");
                                hotelTypeFormArrayList.add(hotelTypeForm1);

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

                                if (getGetHoteltypeId() != 0) {
                                    for (i = 0; i < hotelTypeFormArrayList.size(); i++) {
                                        if (getGetHoteltypeId() == hotelTypeFormArrayList.get(i).getHotelTypeId())
                                            break;
                                        spHotelType.setSelection(getGetHoteltypeId());
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
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
                                    CuisineForm   cuisineForm = new CuisineForm();
                                    cuisineForm.setCuisineId(object1.getInt("Cuisine_Id"));
                                    cuisineForm.setCuisineName(object1.getString("Cuisine_Name"));
                                    cuisineForm.setSelected(false);
                                    cuisineFormArrayList.add(cuisineForm);
                                }

                                if (cuisineFormArrayList != null && cuisineFormArrayList.size() > 0) {
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    rvCuisine.setHasFixedSize(true);
                                    rvCuisine.setLayoutManager(linearLayoutManager);

                                    RVCuisineAdapter rvCuisineAdapter = new RVCuisineAdapter(getActivity(), cuisineFormArrayList, arrayListCheckedCuisine,getArrayListCheckedCuisinePos,new CuisineListener() {

                                        @Override
                                        public void getCuisineListenerPosition(ArrayList<CuisineForm> arrayList) {
                                            if (arrayList != null && arrayList.size() != 0) {
                                                arrayListCheckedCuisine.clear();
                                               /* for (int i = 0; i < arrayList.size(); i++) {
                                                    CuisineForm cuisineForm = new CuisineForm();
                                                    cuisineForm.setCuisineId(cuisineFormArrayList.get(i).getCuisineId());
                                                    arrayListCheckedCuisine.add(cuisineForm);
                                                }*/

                                                for (int i = 0; i < arrayList.size(); i++) {
                                                    if (arrayList.get(i).isSelected()) {
                                                        arrayListCheckedCuisine.add(arrayList.get(i));
                                                    }
                                                }
                                            }

                                            cuisineArray = new JSONArray();
                                            setGetArrayListCheckedCuisinePos(arrayListCheckedCuisine);
                                         //  getArrayListCheckedCuisinePos = arrayListCheckedCuisine;
                                            for (int x = 0; x < arrayListCheckedCuisine.size(); x++) {
                                                try {
                                                    JSONObject object = new JSONObject();
                                                    object.put("cuisineId", arrayListCheckedCuisine.get(x).getCuisineId());
                                                    cuisineArray.put(object);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                    rvCuisine.setAdapter(rvCuisineAdapter);

                                }
                            } else {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();


                            }
                            skLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case HOTEL_TAGS:
                        try {
                            JSONObject tagObject = new JSONObject(objectResponse);
                            int status = tagObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray = tagObject.getJSONArray("tags");
                                tagsFormArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject tagObjectInfo = jsonArray.getJSONObject(i);
                                    TagsForm tagForm = new TagsForm();
                                    tagForm.setTagId(tagObjectInfo.getInt("Tag_Id"));
                                    tagForm.setTagName(tagObjectInfo.getString("Tag_Name"));
                                    tagForm.setSelected(false);
                                    tagsFormArrayList.add(tagForm);
                                }

                                if (tagsFormArrayList != null && tagsFormArrayList.size() > 0) {
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    rvTags.setHasFixedSize(true);
                                    rvTags.setLayoutManager(linearLayoutManager);
                                    RVTagsAdapter rvTagsAdapter = new RVTagsAdapter(getActivity(), tagsFormArrayList, new TagListener() {

                                        @Override
                                        public void getTagListenerPosition(ArrayList<TagsForm> arrayList) {
                                            if (arrayList != null && arrayList.size() != 0) {
                                                arrayListCheckedTags.clear();
                                                for (int i = 0; i < arrayList.size(); i++) {
                                                    if (arrayList.get(i).isSelected()) {
                                                        arrayListCheckedTags.add(arrayList.get(i));
                                                    }
                                                }
//                                                for (int i = 0; i < arrayList.size(); i++) {
//                                                    TagsForm tagsForm = new TagsForm();
//                                                    tagsForm.setTagId(arrayList.get(i).getTagId());
//                                                    arrayListCheckedTags.add(tagsForm);
//                                                }
                                            }
                                            tagArray = new JSONArray();
                                            for (int x = 0; x < arrayListCheckedTags.size(); x++) {
                                                try {
                                                    JSONObject object = new JSONObject();
                                                    object.put("tagId", arrayListCheckedTags.get(x).getTagId());
                                                    tagArray.put(object);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                    rvTags.setAdapter(rvTagsAdapter);

                                }
                            } else {
                                Toast.makeText(getActivity(), tagObject.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            skLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case HOTEL_OTHER_DETAILS:
                        try {
                            JSONObject otherDetailsJsonObj = new JSONObject(objectResponse);
                            int status = otherDetailsJsonObj.getInt("status");
                            if (status == 1) {
                                Toast.makeText(getActivity(), "Hotel Other Details Added Successfully..", Toast.LENGTH_SHORT).show();
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

                            } else {
                                Toast.makeText(getActivity(), otherDetailsJsonObj.getString("message"), Toast.LENGTH_SHORT).show();
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

        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(HOTEL_TYPE, (service.getSAHotelType()));
        mRetrofitService.retrofitData(HOTEL_CUISINE, (service.getCuisine()));
        mRetrofitService.retrofitData(HOTEL_TAGS, (service.getTags()));

        String info= String.valueOf(getGetArrayListCheckedCuisinePos());

        Toast.makeText(getContext(), "info"+info, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hoteltypeId", hoteltypeId);
        outState.putInt("tableCount", Integer.parseInt(etvHotelTableCount.getText().toString()));
        outState.putInt("hotelType",spHotelType.getSelectedItemPosition());
      //  outState.putString("cuisineArray", cuisineArray.toString());
       // outState.putString("tagArray", tagArray.toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            hoteltypeId = savedInstanceState.getInt("hoteltypeId");
            tableCount = savedInstanceState.getInt("tableCount");
            spHotelType.setSelection(savedInstanceState.getInt("hotelType"));
         //   mCuisine = savedInstanceState.getString("cuisineArray");
          //  mTags = savedInstanceState.getString("tagArray");

            String table = String.valueOf(tableCount);
            etvHotelTableCount.setText(table);


           /* etvHotelName.setText(mHotelName);
            etvHotelEmail.setText(mHotelEmail);
            etvHotelMob.setText(mHotelMob);
            etvHotelPhone.setText(mHotelPhone);
            etvHotelAddress.setText(mHotelAddress);*/

        }
    }


    private void init() {
       // cuisineArray=new JSONArray();
      //  tagArray=new JSONArray();
        spHotelType = view.findViewById(R.id.sp_hotel_type);
        //  spState = view.findViewById(R.id.sp_state);

        //  spCity = view.findViewById(R.id.sp_city);
        //  etvLattitude = view.findViewById(R.id.edt_hotel_lattitude);
        // etvLongitude = view.findViewById(R.id.edt_hotel_longitude);
        //   etvArea=view.findViewById(R.id.edt_hotel_area);
        btnNext = view.findViewById(R.id.btn_basic_other_details);
        etvHotelTableCount = view.findViewById(R.id.edt_hotel_table_count);
        rvTags = view.findViewById(R.id.rv_tags);

        // etvHotelAddress=view.findViewById(R.id.edt_hotel_address);

        hotelTypeFormArrayList = new ArrayList<>();
        rvCuisine = view.findViewById(R.id.rv_cuisine);
        cuisineFormArrayList = new ArrayList<>();
        tagsFormArrayList = new ArrayList<>();
        //  stateFormArrayList=new ArrayList<>();
        // cityFormArrayList=new ArrayList<>();

        arrayListCheckedCuisine = new ArrayList<>();
       //getArrayListCheckedCuisinePos=new ArrayList<>();

        arrayListCheckedTags = new ArrayList<>();
        skLoading = view.findViewById(R.id.skLoading);
    }

    public int getGetHoteltypeId() {
        return getHoteltypeId;
    }

    public void setGetHoteltypeId(int getHoteltypeId) {
        this.getHoteltypeId = getHoteltypeId;
    }

}
