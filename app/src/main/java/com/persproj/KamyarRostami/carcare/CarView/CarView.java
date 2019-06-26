package com.persproj.KamyarRostami.carcare.CarView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.persproj.KamyarRostami.carcare.Utils.Firebase;
import com.persproj.KamyarRostami.carcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CarView extends AppCompatActivity {
    private MileageCalculator mileageCalculator;
    private Context mContext = CarView.this;
    private String TAG = "CarView";
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<String> imageUrl = new ArrayList<>();
    private ArrayList<String> carModel = new ArrayList<>();
    private ArrayList<String> carPlate = new ArrayList<>();
    private ArrayList<String> carMileage = new ArrayList<>();
    private ArrayList<String> carUsage = new ArrayList<>();
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_view);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mileageCalculator = new MileageCalculator(mContext);
        firebase = new Firebase(mAuth, mContext);
        user = mAuth.getCurrentUser();
        boolean b = mileageCalculator.mileageCalculator(1000, "Audi", 1);
        Log.d(TAG, "onCreate: blooean mileagecalculator: " + b);
        retreiveData();
    }

    /***
     * data car ha ro az db migire
     */
    public void retreiveData() {
        Log.d(TAG, "retreiveData: hi");
        databaseReference.child("cars").child(user.getUid()).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carMileage = firebase.retreiveCarInfo(user.getUid(), dataSnapshot, "mileage");
                carModel = firebase.retreiveCarInfo(user.getUid(), dataSnapshot, "model");
                carPlate = firebase.retreiveCarInfo(user.getUid(), dataSnapshot, "plate");
                carUsage = firebase.retreiveCarInfo(user.getUid(), dataSnapshot, "usage");
                assignRecyclerValues(carPlate, carModel, carUsage, carMileage);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void assignRecyclerValues(ArrayList<String> carPlate, ArrayList<String> carModel, ArrayList<String> carUsage, ArrayList<String> carMileage) {


        for(int i =0;i<carModel.size();i++){
            imageUrl.add(mileageCalculator.carUrl(carModel.get(i)));
        }
        Log.d(TAG, "assignRecyclerValues: image URL:"+imageUrl);
        RecyclerView recyclerView = findViewById(R.id.car_recycle_view);
        Car_View_Adapter adapter = new Car_View_Adapter(mContext,imageUrl,carPlate,carModel,carMileage,carUsage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
