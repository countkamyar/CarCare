package com.persproj.KamyarRostami.carcare.User.MVVM;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String baseURL = "http://demo5877596.mockable.io";

    private RetrofitClient() {
    }

    public static Retrofit getRetrofit() {
        if (baseURL == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
