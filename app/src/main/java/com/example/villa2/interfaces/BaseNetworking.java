package com.example.villa2.interfaces;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class BaseNetworking {
    public static ApiInterface services;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ApiInterface apiServices() {
        services = APIClient.getApiClient().create(ApiInterface.class);
        return services;
    }
}