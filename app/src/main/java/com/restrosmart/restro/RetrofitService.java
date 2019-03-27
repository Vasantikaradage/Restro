package com.restrosmart.restro;

import android.content.Context;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Interfaces.IResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHREE on 23/10/2018.
 */

public class RetrofitService {

    IResult mResultCallback = null;
    Context mContext;



    public RetrofitService(IResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
       // mReasultArrayCallback=iResultArray;
    }

    //Retrofit JsonObject
    public void retrofitData(final int requestId, Call<JsonObject> url) {
        try {

            //     ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

            Call<JsonObject> call = url;


            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (mResultCallback != null) {
                        mResultCallback.notifySuccess(requestId, response);
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    //Toast.makeText(AddNewEmployee.this, "Error Registering Employee", Toast.LENGTH_SHORT).show();


                    if (mResultCallback != null)
                        mResultCallback.notifyError(requestId, t);

                }
            });
        } catch (Exception e) {

        }
    }


    //Retrofit JsonArray


}









