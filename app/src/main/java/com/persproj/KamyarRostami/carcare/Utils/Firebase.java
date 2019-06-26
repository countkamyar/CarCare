package com.persproj.KamyarRostami.carcare.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.persproj.KamyarRostami.carcare.DataModel.Cars;
import com.persproj.KamyarRostami.carcare.DataModel.Users;
import com.persproj.KamyarRostami.carcare.MainView.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Firebase {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Context mContext;

    public Firebase(FirebaseAuth mAuth, Context mContext) {
        this.mAuth = mAuth;
        this.mAuth.getCurrentUser();
        this.mContext = mContext;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private static final String TAG = "Firebase";

    /***
     *
     * add kaardane user e jadid
     * @param userid
     * @param name
     * @param surname
     * @param email
     * @param phone
     */
    public void addNewUser(String userid, String name, String surname, String email, String phone) {
        databaseReference = firebaseDatabase.getReference();
        try {
            Users users = new Users(name, surname, email, phone);
            databaseReference.child("users").child(userid).setValue(users);
            //   databaseReference.child("users").child(userid).child("name").setValue(name);
            Log.d(TAG, "addNewUser: userinfo " + databaseReference.child("users").child("name").getKey());

        } catch (NullPointerException e) {
            Log.e(TAG, "addNewUser: NullPointerException" + e.getMessage());
        }
    }

    /***
     * estekhraje etelat user bar asase commande message
     * @param userid
     * @param dataSnapshot
     * @param message
     * @return
     */
    public String retrieveProfileInfo(final String userid, DataSnapshot dataSnapshot, String message) {


        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.getKey().equals("users")) {
                Log.d(TAG, "onDataChange: user's info " + ds.child(userid).getValue(Users.class).getName());
                switch (message) {
                    case "name":
                        return ds.child(userid).getValue(Users.class).getName();
                    case "surname":
                        return ds.child(userid).getValue(Users.class).getSurname();
                    case "email":
                        return ds.child(userid).getValue(Users.class).getEmail();
                }
//                users.setEmail(ds.child(userid).getValue(Users.class).getName());
//                users.setSurname(ds.child(userid).getValue(Users.class).getSurname());
//                users.setEmail(ds.child(userid).getValue(Users.class).getEmail());
            }
        }

        return null;
    }

    /***
     * plate no ro michasboone
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @param command
     * @return
     */
    public String stringModifyPlate(String s1, String s2, String s3, String s4, String command) {
        if (command.equals("concat")) {
            Log.d(TAG, "stringModifyPlate: platenumber: " + s1 + s2 + s3 + s4);
            return s1 + s2 + s3 + s4;
        }

        return null;
    }

    /***
     * check mikone ke plate tekrari nabashe
     * @param plate
     * @param dataSnapshot
     * @return
     */
    public boolean check_plate(String plate, DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.child("cars").getChildren()) {
            Log.d(TAG, "check_plate: query: " + ds.hasChild(plate));
            if (ds.hasChild(plate)) {

                Toast.makeText(mContext, "Please check your entry!", Toast.LENGTH_SHORT).show();
                return false;

            }

        }

        return true;
    }

    /***
     * add kaardane etelaate car be db
     * @param userid
     * @param model
     * @param mileage
     * @param plate
     * @param usage
     */
    public void addCarToDB(String userid, String model, String mileage, String plate, String usage) {
        Date date = Calendar.getInstance().getTime();
        String formatedDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYMM");
            formatedDate = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Cars cars = new Cars(model, mileage, plate, usage, formatedDate);
            databaseReference.child("cars").child(userid).child(plate).setValue(cars);
            Log.d(TAG, "addCarToDB: car data: " + databaseReference.child("cars").child(userid).child(plate).getKey());
            Toast.makeText(mContext, "Car added successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);

        } catch (NullPointerException e) {
            Log.e(TAG, "addCarToDB:NullPointerException " + e.getMessage());

        }
    }

//    public int getDBMonth() {
//
//
//        return 0;
//    }

    public ArrayList<String> retreiveCarInfo(String userid, DataSnapshot dataSnapshot, String command) {
        ArrayList<String> carplate = new ArrayList<>();
        ArrayList<String> carmodel = new ArrayList<>();
        ArrayList<String> carmileage = new ArrayList<>();
        ArrayList<String> carusage = new ArrayList<>();
        Cars cars = new Cars();
        try {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                carplate.add(ds.child("plate").getValue().toString());
                carmodel.add(ds.child("model").getValue().toString());
                carmileage.add(ds.child("mileage").getValue().toString());
                carusage.add(ds.child("usage").getValue().toString());
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "retreiveCarInfo:NullPointerException " + e.getMessage());
        }

        switch (command) {
            case "model":
                return carmodel;
            case "mileage":
                return carmileage;
            case "usage":
                return carusage;
            default:
                return carplate;
        }
    }
}
