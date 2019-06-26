package com.persproj.KamyarRostami.carcare.User.MVVM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//public class CarModel  {
//    private String car_model,speed,creation_date,guarantee;
//
//    public CarModel(String car_model, String speed, String creation_date, String guarantee) {
//        this.car_model = car_model;
//        this.speed = speed;
//        this.creation_date = creation_date;
//        this.guarantee = guarantee;
//    }
//
//    public String getCar_model() {
//        return car_model;
//    }
//
//    public void setCar_model(String car_model) {
//        this.car_model = car_model;
//    }
//
//    public String getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(String speed) {
//        this.speed = speed;
//    }
//
//    public String getCreation_date() {
//        return creation_date;
//    }
//
//    public void setCreation_date(String creation_date) {
//        this.creation_date = creation_date;
//    }
//
//    public String getGuarantee() {
//        return guarantee;
//    }
//
//    public void setGuarantee(String guarantee) {
//        this.guarantee = guarantee;
//    }
//
//    @Override
//    public String toString() {
//        return "CarModel{" +
//                "car_model='" + car_model + '\'' +
//                ", speed='" + speed + '\'' +
//                ", creation_date='" + creation_date + '\'' +
//                ", guarantee='" + guarantee + '\'' +
//                '}';
//    }}
public class CarModel {

    @SerializedName("car_model")
    @Expose
    private String carModel;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("guarantee")
    @Expose
    private String guarantee;

    public String getCarModel() {
        return carModel;
    }

    public CarModel(String carModel, String speed, String creationDate, String guarantee) {
        this.carModel = carModel;
        this.speed = speed;
        this.creationDate = creationDate;
        this.guarantee = guarantee;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

}
