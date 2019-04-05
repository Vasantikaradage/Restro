package com.restrosmart.restrohotel.Interfaces;


import com.google.gson.JsonObject;

import retrofit2.Response;

/**
 * Created by Admin on 17-11-2017.
 */

public interface IResult {

    void notifySuccess(int requestId, Response<JsonObject> response);
    void notifyError(int requestId, Throwable error);


}
