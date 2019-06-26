package com.persproj.KamyarRostami.carcare.User.MVVM;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.persproj.KamyarRostami.carcare.CarView.MileageCalculator;

import java.util.ArrayList;
import java.util.List;

public class Cars_ViewModel extends ViewModel {

    private MutableLiveData<List<CarModel>> cars;
    private CarsRepository repository;
    private MutableLiveData<Boolean> carupdating = new MutableLiveData<>();
    private MileageCalculator mileageCalculator;
    private static final String TAG = "Cars_ViewModel";


    public void init(Context context) {
        if (cars != null) {
            return;
        }
        repository = CarsRepository.getInstance();
        cars = repository.getcarmodeldata(context);
        carupdating.setValue(false);
    }

    public void addnewValue(final CarModel carModel) {
        carupdating.setValue(true);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<CarModel> currentcars = cars.getValue();
                currentcars.add(carModel);
                cars.postValue(currentcars);
                carupdating.postValue(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public MutableLiveData<Boolean> getCarupdating() {
        return carupdating;
    }

    public LiveData<List<CarModel>> getcars() {
        return cars;
    }

    public ArrayList<String> getImageUrl() {
        ArrayList<String> imageUrl = new ArrayList<>();

        mileageCalculator = new MileageCalculator();

        for (int i = 0; i < cars.getValue().size(); i++) {
            try {
                Log.d(TAG, "getImageUrl:carmodel: " + cars.getValue().get(i).getCarModel());
                imageUrl.add(mileageCalculator.carUrl(cars.getValue().get(i).getCarModel()));
            } catch (NullPointerException e) {
                Log.e(TAG, "getImageUrl: ", e);
            }
        }
        return imageUrl;
    }
}
