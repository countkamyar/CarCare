package com.persproj.KamyarRostami.carcare.User.MVVM;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Soservice {
    @GET("/cars")
    Call<List<CarModel>> getAllPosts();
}
