package com.persproj.KamyarRostami.carcare.DataModel;

public class Cars {
    public String model, mileage, plate, usage, date;


    public Cars(String model, String mileage, String plate, String usage, String date) {

        this.model = model;
        this.mileage = mileage;
        this.plate = plate;
        this.usage = usage;
        this.date = date;
    }

    public Cars() {

    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "model='" + model + '\'' +
                ", mileage='" + mileage + '\'' +
                ", plate='" + plate + '\'' +
                ", usage='" + usage + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
