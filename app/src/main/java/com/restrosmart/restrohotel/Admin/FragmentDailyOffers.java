package com.restrosmart.restrohotel.Admin;

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
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.DailyOffersAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;

import com.restrosmart.restrohotel.Model.OfferNameForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.OFFER_TITLE;

public class FragmentDailyOffers extends Fragment {

    private RecyclerView recyclerView;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private FloatingActionButton btnAddOffer;
    private Sessionmanager sessionmanager;
    private String hotelId, branchId;
    private View view;

    private RelativeLayout rlDailyOffer,rlPromoCode,rlScratchCard,rlRushHours;

    private  RecyclerView rvOfferType;
    private  ArrayList<OfferNameForm> offerNameFormArrayList;

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

        initRetrofitCallback();
        ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(OFFER_TITLE, (apiService.GetOffer()));
    }

    private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                String offerObjectInfo=jsonObject.toString();

                try {
                    JSONObject object=new JSONObject(offerObjectInfo);
                    int status=object.getInt("status");


                    if(status==1)
                    {
                        JSONArray jsonArray=object.getJSONArray("OfferDetails");
                        for(int i=0; i<jsonArray.length(); i++)
                        {

                            JSONObject object1=jsonArray.getJSONObject(i);
                            OfferNameForm offerNameForm=new OfferNameForm();
                            offerNameForm.setOfferId(object1.getInt("OfferTypeId"));
                            offerNameForm.setOfferName(object1.getString("OfferTypeName"));
                            offerNameForm.setOfferDescription(object1.getString("OfferTypeDiscrp"));
                            offerNameForm.setOfferImage(object1.getString("OfferTypeImg"));
                            offerNameFormArrayList.add(offerNameForm);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rvOfferType.setLayoutManager(linearLayoutManager);
                        rvOfferType.getLayoutManager().setMeasurementCacheEnabled(false);
                        rvOfferType.setHasFixedSize(true);

                        DailyOffersAdapter dailyOffersAdapter = new DailyOffersAdapter(getActivity(), offerNameFormArrayList);
                        rvOfferType.setAdapter(dailyOffersAdapter);
                       // dailyOffersAdapter.notifyDataSetChanged();
                    }
                    else
                    {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","requestId"+requestId);
                Log.d("","RetrofitError"+error);
            }
        };
    }

    private void init() {
        /*rlDailyOffer= view.findViewById(R.id.relative_daily_offer);
        rlPromoCode=view.findViewById(R.id.relative_promo_code);
        rlRushHours=view.findViewById(R.id.relative_rush_hours);
        rlScratchCard=view.findViewById(R.id.relative_scratch_card);*/
        rvOfferType=view.findViewById(R.id.rv_offer_type);
        sessionmanager=new Sessionmanager(getActivity());
        offerNameFormArrayList=new ArrayList<>();
    }
}
