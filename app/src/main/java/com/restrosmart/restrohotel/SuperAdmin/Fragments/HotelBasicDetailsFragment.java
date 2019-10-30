package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
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
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Admin.ActivityNewAddEmployee;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Models.CityForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.CountryForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelTypeForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.StateForm;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class HotelBasicDetailsFragment extends Fragment {
    private View view;
    private Button btnNext;
    private TextInputEditText etvHotelName, etvHotelMob, etvHotelPhone, etvHotelAddress, etvHotelEmail,etvArea;
    private String mHotelName, mHotelMob, mHotelPhone, mHotelAddress, mHotelEmail, mHotelType;
   // private SearchableSpinner spHoteltype;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private ArrayList<HotelTypeForm> hotelTypeFormArrayList;

    private SearchableSpinner spCountry, spState, spCity;

   // private TextInputEditText /*etvLattitude, etvLongitude*/etvArea,etvHotelAddress;
    private int countryId, stateId, cityId, areaId;
    private String mLattitude, mLongitude;
    private Sessionmanager sessionmanager;

    private ArrayList<CountryForm> countryFormArrayList;
    private  ArrayList<StateForm>  stateFormArrayList;
    private  ArrayList<CityForm> cityFormArrayList;
    private HashMap<String,String> superAdminInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_basic_hotel_details, container, false);

        init();
        superAdminInfo=sessionmanager.getSuperAdminDetails();

        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_COUNTRY, (service.getCountry()));
       /* initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(HOTEL_TYPE, (service.getSAHotelType()));*/

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryId = countryFormArrayList.get(i).getCountryId();

                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService.retrofitData(GET_STATE, (service.getState(countryId)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateId=stateFormArrayList.get(i).getStateId();

                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService.retrofitData(GET_CITY, (service.getCity(stateId)));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityId=cityFormArrayList.get(i).getCityId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (isValid()) {
             /*   OtherDetailsFragment locationDetailsFragment = new OtherDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("hotelName", etvHotelName.getText().toString());
                bundle.putString("hotelEmail", etvHotelEmail.getText().toString());
                bundle.putString("hotelMob", etvHotelMob.getText().toString());
                bundle.putString("hotelPhone", etvHotelPhone.getText().toString());
                bundle.putString("hotelAddress", etvHotelAddress.getText().toString());
                bundle.putInt("countryId", countryId);
                bundle.putInt("stateId", stateId);
                bundle.putInt("cityId", cityId);
                bundle.putInt("areaId", areaId);*/


              //  locationDetailsFragment.setArguments(bundle);

                if(isValid())

                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService.retrofitData(HOTEL_REGISTRATION, (service.hotelRegistration(etvHotelName.getText().toString(),
                        etvHotelMob.getText().toString(),
                        etvHotelPhone.getText().toString(),
                        etvHotelEmail.getText().toString(),
                        countryId,
                        stateId,
                        cityId,
                        etvArea.getText().toString(),
                        etvHotelAddress.getText().toString(),
                        Integer.parseInt((superAdminInfo.get(EMP_ID))))));
                OtherDetailsFragment locationDetailsFragment = new OtherDetailsFragment();

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
        } else if (etvHotelPhone.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter phone No..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvHotelPhone.getText().toString().length() < 12) {
            Toast.makeText(getActivity(), "Please enter valid phone no", Toast.LENGTH_SHORT).show();
            return false;
        }else if (etvHotelEmail.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter Email Id..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etvHotelEmail.getText().toString().matches(emailPattern)) {
            Toast.makeText(getActivity(), "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (etvArea.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter hotel area ..", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if(countryId==0)
        {
            Toast.makeText(getActivity(), "Please select country ..", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if(stateId==0)
        {
            Toast.makeText(getActivity(), "Please select state ..", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if(cityId==0)
        {
            Toast.makeText(getActivity(), "Please select city ..", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

   /* private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectResponse = jsonObject.toString();

                try {
                    JSONObject object = new JSONObject(objectResponse);
                    int status = object.getInt("status");
                    if (status == 1) {

                        JSONArray jsonArray = object.getJSONArray("hoteltype");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HotelTypeForm hotelTypeForm = new HotelTypeForm();
                            hotelTypeForm.setHotelTypeId(object1.getInt("Hotel_Type_Id"));
                            hotelTypeForm.setHotelTypeName(object1.getString("Hotel_Type_Name"));
                            hotelTypeFormArrayList.add(hotelTypeForm);
                        }

                        ArrayAdapter<HotelTypeForm> typeFormArrayAdapter = new ArrayAdapter<HotelTypeForm>(getActivity(), android.R.layout.simple_spinner_dropdown_item, hotelTypeFormArrayList);
                        typeFormArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spHoteltype.setAdapter(typeFormArrayAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "RequestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }
*/


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


                            }
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


                            }
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


                            }
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
                            }
                            else {
                                Toast.makeText(getActivity(), "Something weng Wrong..", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e)
                        {
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("hotelName", mHotelName);
        outState.putString("hotelEmail", mHotelEmail);
        outState.putString("hotelMob", mHotelMob);
        outState.putString("hotelPhone", mHotelPhone);
        outState.putString("hotelAddress", mHotelAddress);
        outState.putString("hotelType", mHotelType);
        outState.putInt("countryId", countryId);
        outState.putInt("stateId", stateId);
        outState.putInt("cityId", cityId);
        outState.putInt("areaId", areaId);

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
            areaId = savedInstanceState.getInt("areaId");

            etvHotelName.setText(mHotelName);
            etvHotelEmail.setText(mHotelEmail);
            etvHotelMob.setText(mHotelMob);
            etvHotelPhone.setText(mHotelPhone);
            etvHotelAddress.setText(mHotelAddress);
        }
    }

    private void init() {
        etvHotelName = view.findViewById(R.id.edt_hotel_name);
        etvHotelEmail = view.findViewById(R.id.edt_email);
        etvHotelMob = view.findViewById(R.id.edt_hotel_mob);
        etvHotelPhone = view.findViewById(R.id.edt_hotel_phone);
        etvHotelAddress = view.findViewById(R.id.edt_hotel_address);
       // spHoteltype = view.findViewById(R.id.sp_hotel_type);
        btnNext = view.findViewById(R.id.btn_basic_hotel_details);
        hotelTypeFormArrayList=new ArrayList<>();
        etvArea=view.findViewById(R.id.edt_hotel_area);

        spCountry = view.findViewById(R.id.sp_country);
        spState = view.findViewById(R.id.sp_state);

        spCity = view.findViewById(R.id.sp_city);
        countryFormArrayList = new ArrayList<>();
        stateFormArrayList=new ArrayList<>();
        cityFormArrayList=new ArrayList<>();

        sessionmanager=new Sessionmanager(getActivity());
    }
}
