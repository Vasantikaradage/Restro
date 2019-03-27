package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.JsonObject;
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

/**
 * Created by SHREE on 30/10/2018.
 */

public  class ActivityAddNewOffer extends AppCompatActivity {

    IResult mResultCallBack;
    RetrofitService mRetrofitService;
    ArrayList<OfferNameForm> arrayListOfferTitle=new ArrayList<OfferNameForm>();

    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_offer);
        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTopToolbar.setTitle("Add New Offer");
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

     //   ActionBar actionBar = getActionBar();
     //   actionBar.setDisplayHomeAsUpEnabled(true);

      //  recyclerView=(RecyclerView)findViewById(R.id.recycler_offertitle);

        init();

    }
    private void init() {

        initRetrofitCallback();

        ApiService apiService= RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        mRetrofitService = new RetrofitService(mResultCallBack,ActivityAddNewOffer.this);
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
                      //  callAdapter();

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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
