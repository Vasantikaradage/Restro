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
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.OfferNameForm;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.OFFER_TITLE;

public class DailyOffers extends Fragment {
    RecyclerView recyclerView;


    ArrayList<OfferNameForm> arrayListOfferTitle=new ArrayList<OfferNameForm>();
    RetrofitService mRetrofitService;
    IResult  mResultCallBack;
    FloatingActionButton btnAddOffer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_daily_offers,null);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_daily_offer);
        btnAddOffer=(FloatingActionButton)view.findViewById(R.id.btn_add_offer);


        init();

      //  recyclerView=(RecyclerView)view.findViewById(R.id.recycler_daily_offer);






    }

   /* @Override
    public void onResume() {
        super.onResume();
        init();
    }*/

    private void init() {

        btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAddNewOffer.class);

                startActivity(intent);
            }
        });

        initRetrofitCallback();

        ApiService apiService=RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        mRetrofitService = new RetrofitService(mResultCallBack,getActivity());
        mRetrofitService.retrofitData(OFFER_TITLE,(apiService.GetOfferTitle(1)));


    }

    private void initRetrofitCallback() {
        Log.d("","Vresponse"+"info");

        mResultCallBack =new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                Log.d("","Vresponse"+response);
                JsonObject  jsonObject=response.body();
                String value=jsonObject.toString();

                try {
                    JSONObject object=new JSONObject(value);
                    int status=object.getInt("status");
                    if(status==1)
                    {
                        JSONArray jsonArray=object.getJSONArray("Offer_List");

                        arrayListOfferTitle.clear();
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object1=jsonArray.getJSONObject(i);

                            OfferNameForm offerNameForm=new OfferNameForm();
                            offerNameForm.setOfferId(object1.getInt("Offer_Name_Id"));
                            offerNameForm.setOfferName(object1.getString("Offer_Name").toString());
                            arrayListOfferTitle.add(offerNameForm);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
          recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
          recyclerView.setHasFixedSize(true);

          DailyOffersAdapter dailyOffersAdapter=new DailyOffersAdapter(getActivity(),arrayListOfferTitle);
         recyclerView.setAdapter(dailyOffersAdapter);
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_employee,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add) {
            Intent intent = new Intent(getActivity(), ActivityAddNewOffer.class);

            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }*/
}
