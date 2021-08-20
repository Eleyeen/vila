package com.example.villa2.interfaces;



import com.example.villa2.models.CheckInResponse;
import com.example.villa2.models.CheckoutModel;
import com.example.villa2.models.ServicingModel;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("VillaTwoCheckIn")
    Call<CheckInResponse> checkIn();


    @POST("VillaTwoCheckOut")
    Call<CheckoutModel> checkout();


    @POST("VillaTwoServicing")
    Call<ServicingModel> servicing();


}
