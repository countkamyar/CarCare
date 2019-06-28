package com.persproj.KamyarRostami.carcare.User.MVVM;

import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Singleton pattern
public class CarsRepository {
    private static CarsRepository instance;
    private ArrayList<CarModel> carModels = new ArrayList<>();
    private Soservice soservice;
    private static final String TAG = "CarsRepository";
    public static CarsRepository getInstance() {
        if (instance == null) {
            instance = new CarsRepository();
        }
        return instance;
    }

    /***
     * setting data as livedata list and return it
     * @return
     */
    public MutableLiveData<List<CarModel>> getcarmodeldata(final Context context) {
        final MutableLiveData<List<CarModel>> carmodeldata = new MutableLiveData<>();

        try {
            soservice = RetrofitClient.getRetrofit().create(Soservice.class);
            Call<List<CarModel>> call = soservice.getAllPosts();
            call.enqueue(new Callback<List<CarModel>>() {
                @Override
                public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                    Log.d(TAG, "onResponse: successful"+response.body());
                    carmodeldata.setValue(response.body());
                    Toast.makeText(context, "Data received successfully!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<CarModel>> call, Throwable t) {
                    Log.d(TAG, "onResponse: failed");
                    setCarModels();
                    carmodeldata.setValue(carModels);
                    Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            setCarModels();
            carmodeldata.setValue(carModels);
        }
        Log.d(TAG, "getcarmodeldata: returned data "+carmodeldata.getValue());
        return carmodeldata;
    }

    //setting random data
    public void setCarModels() {
        carModels.add(new CarModel("Benz", "300", "2018", "5"));
        carModels.add(new CarModel("BMW", "300", "2019", "4"));
        carModels.add(new CarModel("Audi", "250", "2017", "2"));
        carModels.add(new CarModel("Dena", "150", "2015", "2"));
        carModels.add(new CarModel("L-90", "140", "2009", "2"));
        carModels.add(new CarModel("Samand", "140", "2003", "1"));
    }
}
