package com.restrosmart.restro.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Adapter.DailyOffersAdapter;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.DeleteResult;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.OfferForm;
import com.restrosmart.restro.Model.OfferNameForm;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.OFFER_TITLE;
import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;

public class DailyOffers extends Fragment {
    ArrayList<OfferForm> arrayListOfferTitle = new ArrayList<OfferForm>();
    private RecyclerView recyclerView;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private FloatingActionButton btnAddOffer;
    private Sessionmanager sessionmanager;
    private String hotelId, branchId;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_daily_offers, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAddNewOffer.class);
                startActivity(intent);
            }
        });
        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();

        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);

        initRetrofitCallback();
        ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(OFFER_TITLE, (apiService.GetOffer(Integer.parseInt(hotelId),
                Integer.parseInt(branchId))));
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_daily_offer);
        btnAddOffer = (FloatingActionButton) view.findViewById(R.id.btn_add_offer);

    }

    @Override
    public void onResume() {
        super.onResume();
        initRetrofitCallback();
        ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(OFFER_TITLE, (apiService.GetOffer(Integer.parseInt(hotelId),
                Integer.parseInt(branchId))));
    }

    private void initRetrofitCallback() {
        Log.d("", "Vresponse" + "info");

        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                Log.d("", "Vresponse" + response);
                JsonObject jsonObject = response.body();
                String value = jsonObject.toString();
                try {
                    JSONObject object = new JSONObject(value);
                    int status = object.getInt("status");
                    if (status == 1) {
                        JSONArray jsonArray = object.getJSONArray("data");

                        arrayListOfferTitle.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            OfferForm offerForm = new OfferForm();
                            offerForm.setOffer_Id(object1.getInt("Offer_Id"));
                            offerForm.setOffer_Name(object1.getString("Offer_Name").toString());
                            offerForm.setOffer_Value(object1.getString("Offer_Value").toString());
                            offerForm.setOffer_From(object1.getString("Offer_From").toString());
                            offerForm.setOffer_To(object1.getString("Offer_To").toString());
                            offerForm.setMenu_Name(object1.getString("Menu_Name").toString());
                            offerForm.setMenu_Descrip(object1.getString("Menu_Descrip").toString());
                            offerForm.setMenu_Image_Name(object1.getString("Menu_Image_Name").toString());
                            offerForm.setStatus(object1.getInt("Status"));
                            arrayListOfferTitle.add(offerForm);
                        }
                        callAdapter();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void callAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        recyclerView.setHasFixedSize(true);

        DailyOffersAdapter dailyOffersAdapter = new DailyOffersAdapter(getActivity(), arrayListOfferTitle, new DeleteResult() {
            @Override
            public void getDeleteInfoCallBack() {
                initRetrofitCallback();
                ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService.retrofitData(OFFER_TITLE, (apiService.GetOffer(Integer.parseInt(hotelId),
                        Integer.parseInt(branchId))));
            }
        });
        recyclerView.setAdapter(dailyOffersAdapter);
        dailyOffersAdapter.notifyDataSetChanged();
    }
}
