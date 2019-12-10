package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Models.CityForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.CountryForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.StateForm;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_CITY;
import static com.restrosmart.restrohotel.ConstantVariables.GET_COUNTRY;
import static com.restrosmart.restrohotel.ConstantVariables.GET_STATE;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_REGISTRATION;

public class HotelBasicDetailsFragment extends Fragment {
    private View view;
    private Button btnNext;
    private EditText etvHotelName, etvHotelMob, etvHotelPhone, etvHotelAddress, etvHotelEmail, etvArea;
    private String mHotelName, mHotelMob, mHotelPhone, mHotelAddress, mHotelEmail, mHotelType, mArea;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Spinner spCountry, spState, spCity;
    private int countryId, stateId, cityId;
    private Sessionmanager sessionmanager;
    private int hotelId;

    private ArrayList<CountryForm> countryFormArrayList;
    private ArrayList<StateForm> stateFormArrayList;
    private ArrayList<CityForm> cityFormArrayList;
    private HashMap<String, String> superAdminInfo;
    private SpinKitView skLoading;

    private int countryPos;
    private int statePos;
    private int cityPos, i;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_basic_hotel_details, container, false);

        init();
        skLoading.setVisibility(View.GONE);
        superAdminInfo = sessionmanager.getSuperAdminDetails();


        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryId = countryFormArrayList.get(i).getCountryId();
                setCountryPos(countryId);


                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                if (getCountryPos()!=0) {
                    mRetrofitService.retrofitData(GET_STATE, (service.getState(getCountryPos())));
                }else {
                    mRetrofitService.retrofitData(GET_STATE, (service.getState(countryId)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateId = stateFormArrayList.get(i).getStateId();
                setStatePos(stateId);

                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());

                if(getCountryPos()!=0){
                    mRetrofitService.retrofitData(GET_CITY, (service.getCity(getStatePos())));
                }else{
                mRetrofitService.retrofitData(GET_CITY, (service.getCity(stateId)));}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityId = cityFormArrayList.get(i).getCityId();
                setCityPos(cityId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skLoading.setVisibility(View.VISIBLE);

                if (isValid()) {

                    OtherDetailsFragment otherDetailsFragment = new OtherDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("hotelName", etvHotelName.getText().toString());
                    bundle.putString("hotelEmail", etvHotelEmail.getText().toString());
                    bundle.putString("hotelMob", etvHotelMob.getText().toString());
                    bundle.putString("hotelPhone", etvHotelPhone.getText().toString());
                    bundle.putString("hotelAddress", etvHotelAddress.getText().toString());
                    bundle.putInt("countryId", countryId);
                    bundle.putInt("stateId", stateId);
                    bundle.putInt("cityId", cityId);
                    bundle.putString("area", etvArea.getText().toString());
                    // bundle.putInt("sId", Integer.parseInt(superAdminInfo.get(EMP_ID)));

                    Toast.makeText(getActivity(), "success1", Toast.LENGTH_SHORT).show();

                    otherDetailsFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    // Begin Fragment transaction.
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the layout holder with the required Fragment object.
                    fragmentTransaction.replace(R.id.flContainer, otherDetailsFragment);

                    // To get back again
                    fragmentTransaction.addToBackStack(null);

                    // Commit the Fragment replace action.
                    fragmentTransaction.commit();

                }
                skLoading.setVisibility(View.GONE);
            }
        });
        return view;
    }

    public int getCountryPos() {
        return countryPos;
    }

    public void setCountryPos(int countryPos) {
        this.countryPos = countryPos;
    }

    public int getStatePos() {
        return statePos;
    }

    public void setStatePos(int statePos) {
        this.statePos = statePos;
    }

    public int getCityPos() {
        return cityPos;
    }

    public void setCityPos(int cityPos) {
        this.cityPos = cityPos;
    }


    private boolean isValid() {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (etvHotelName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter hotel name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvHotelAddress.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter hotel address ..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvHotelMob.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter Mobile No..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvHotelMob.getText().toString().length() < 10) {
            Toast.makeText(getActivity(), "Please enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } /*else if (etvHotelPhone.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter phone No..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvHotelPhone.getText().toString().length() < 12) {
            Toast.makeText(getActivity(), "Please enter valid phone no", Toast.LENGTH_SHORT).show();
            return false;
        }*/ else if (etvHotelEmail.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter Email Id..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etvHotelEmail.getText().toString().matches(emailPattern)) {
            Toast.makeText(getActivity(), "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvArea.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter hotel area ..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (countryId == 0) {
            Toast.makeText(getActivity(), "Please select country ..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (stateId == 0) {
            Toast.makeText(getActivity(), "Please select state ..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (cityId == 0) {
            Toast.makeText(getActivity(), "Please select city ..", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectInfo = jsonObject.toString();
                switch (requestId) {
                    case GET_COUNTRY:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                countryFormArrayList.clear();
                                JSONArray jsonArray = object.getJSONArray("countries");

                                CountryForm countryForm1 = new CountryForm();
                                countryForm1.setCountryId(0);
                                countryForm1.setCountryName("Select Country");
                                countryFormArrayList.add(countryForm1);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    CountryForm countryForm = new CountryForm();
                                    countryForm.setCountryId(object1.getInt("Country_Id"));
                                    countryForm.setCountryName(object1.getString("Country_Name"));
                                    countryFormArrayList.add(countryForm);
                                }

                                ArrayAdapter<CountryForm> countryFormArrayAdapter = new ArrayAdapter<CountryForm>(getActivity(), android.R.layout.simple_spinner_item, countryFormArrayList);
                                countryFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCountry.setAdapter(countryFormArrayAdapter);

                                if (getCountryPos() != 0) {
                                    for (i = 0; i < countryFormArrayList.size(); i++) {
                                        if (getCountryPos() == countryFormArrayList.get(i).getCountryId())
                                            break;
                                        spCountry.setSelection(getCountryPos());
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            skLoading.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case GET_STATE:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                stateFormArrayList.clear();


                                StateForm stateForm1 = new StateForm();
                                stateForm1.setStateId(0);
                                stateForm1.setStateName("Select State");
                                stateFormArrayList.add(stateForm1);

                                JSONArray jsonArray = object.getJSONArray("state");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    StateForm stateForm = new StateForm();
                                    stateForm.setStateId(object1.getInt("State_Id"));
                                    stateForm.setStateName(object1.getString("State_Name"));
                                    stateFormArrayList.add(stateForm);
                                }
                                ArrayAdapter<StateForm> stateFormArrayAdapter = new ArrayAdapter<StateForm>(getActivity(), android.R.layout.simple_spinner_item, stateFormArrayList);
                                stateFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spState.setAdapter(stateFormArrayAdapter);

                                if (getStatePos() != 0) {

                                    for (i = 0; i < stateFormArrayList.size(); i++) {
                                        if (getStatePos() == stateFormArrayList.get(i).getStateId())
                                            break;
                                        spState.setSelection(getStatePos());
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            skLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case GET_CITY:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                cityFormArrayList.clear();
                                CityForm cityForm1 = new CityForm();
                                cityForm1.setCityId(0);
                                cityForm1.setCityName("Select City");
                                cityFormArrayList.add(cityForm1);

                                JSONArray jsonArray = object.getJSONArray("cities");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    CityForm cityForm = new CityForm();
                                    cityForm.setCityId(object1.getInt("City_Id"));
                                    cityForm.setCityName(object1.getString("City_Name"));
                                    cityFormArrayList.add(cityForm);
                                }

                                ArrayAdapter<CityForm> cityFormArrayAdapter = new ArrayAdapter<CityForm>(getActivity(), android.R.layout.simple_spinner_item, cityFormArrayList);
                                cityFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCity.setAdapter(cityFormArrayAdapter);

                                if (getCityPos() != 0) {
                                    for (i = 0; i < cityFormArrayList.size(); i++) {
                                        if (getCityPos() == cityFormArrayList.get(i).getCityId())
                                            break;
                                        spCity.setSelection(getCityPos());

                                    }
                                }


                            } else {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            skLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case HOTEL_REGISTRATION:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(getActivity(), "Hotel Basic details added Successfully..", Toast.LENGTH_SHORT).show();
                                hotelId = object.getInt("Hotel_Id");
                                OtherDetailsFragment otherDetailsFragment = new OtherDetailsFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("hotelId", hotelId);
                                otherDetailsFragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                                // Begin Fragment transaction.
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                // Replace the layout holder with the required Fragment object.
                                fragmentTransaction.replace(R.id.flContainer, otherDetailsFragment);

                                // To get back again
                                fragmentTransaction.addToBackStack(null);

                                // Commit the Fragment replace action.
                                fragmentTransaction.commit();
                            } else {
                                Toast.makeText(getActivity(), object.getString("meassge"), Toast.LENGTH_SHORT).show();
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
        getActivity().setTitle("Hotel Basic Details");

        String value = String.valueOf(getCountryPos());
        Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();

        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_COUNTRY, (service.getCountry()));
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("hotelName", mHotelName);
        outState.putString("hotelEmail", mHotelEmail);
        outState.putString("hotelMob", mHotelMob);
        outState.putString("hotelPhone", mHotelPhone);
        outState.putString("hotelAddress", mHotelAddress);
        outState.putInt("country", countryId);
        outState.putInt("state", stateId);
        outState.putInt("city", cityId);
        outState.putString("area", mArea);

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
            mHotelType = savedInstanceState.getString("hotelType");
            countryId = savedInstanceState.getInt("countryId");
            stateId = savedInstanceState.getInt("stateId");
            cityId = savedInstanceState.getInt("cityId");
            mArea = savedInstanceState.getString("area");
            Toast.makeText(getActivity(), "mArea" + mHotelName, Toast.LENGTH_SHORT).show();


            etvHotelName.setText(mHotelName);
            etvHotelEmail.setText(mHotelEmail);
            etvHotelMob.setText(mHotelMob);
            etvHotelPhone.setText(mHotelPhone);
            etvHotelAddress.setText(mHotelAddress);
            etvArea.setText(mArea);
            int country = savedInstanceState.getInt("city");
            Toast.makeText(getActivity(), "city" + country, Toast.LENGTH_SHORT).show();


            spCountry.setSelection(countryId);
            spState.setSelection(savedInstanceState.getInt("state"));
            spCity.setSelection(savedInstanceState.getInt("city"));
        }
    }

    private void init() {
        etvHotelName = view.findViewById(R.id.edt_hotel_name);
        etvHotelEmail = view.findViewById(R.id.edt_email);
        etvHotelMob = view.findViewById(R.id.edt_hotel_mob);
        etvHotelPhone = view.findViewById(R.id.edt_hotel_phone);
        etvHotelAddress = view.findViewById(R.id.edt_hotel_address);
        btnNext = view.findViewById(R.id.btn_basic_hotel_details);
        etvArea = view.findViewById(R.id.edt_hotel_area);
        spCountry = view.findViewById(R.id.sp_country);
        spState = view.findViewById(R.id.sp_state);

        spCity = view.findViewById(R.id.sp_city);
        countryFormArrayList = new ArrayList<>();
        stateFormArrayList = new ArrayList<>();
        cityFormArrayList = new ArrayList<>();
        skLoading = view.findViewById(R.id.skLoading);
        sessionmanager = new Sessionmanager(getActivity());
    }
}
