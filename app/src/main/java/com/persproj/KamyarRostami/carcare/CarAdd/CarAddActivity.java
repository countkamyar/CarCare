package com.persproj.KamyarRostami.carcare.CarAdd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.persproj.KamyarRostami.carcare.Utils.Firebase;
import com.persproj.KamyarRostami.carcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarAddActivity extends AppCompatActivity {
    private Context mContext = CarAddActivity.this;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Firebase firebasemethods;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String TAG = "CarAddActivity";
    private Spinner car_model_spinner;
    private NumberPicker mileage, usage;
    private EditText plate1, plate2, plate3, plate4;
    private Button add_btn;
    private String plate_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add);
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = mFirebaseAuth.getCurrentUser();
        firebasemethods = new Firebase(mFirebaseAuth, mContext);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        init();
        add_btn_click();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /***
     * initialize kardane widget ha
     */
    public void init() {
        car_model_spinner = (Spinner) findViewById(R.id.car_model_spinner);
        mileage = (NumberPicker) findViewById(R.id.mileage_picker);
        usage = (NumberPicker) findViewById(R.id.usage_picker);
        add_btn = (Button) findViewById(R.id.car_add_btn);
        plate1 = (EditText) findViewById(R.id.plate1);
        plate2 = (EditText) findViewById(R.id.plate2);
        plate3 = (EditText) findViewById(R.id.plate3);
        plate4 = (EditText) findViewById(R.id.plate4);
        mileage.setMinValue(10);
        mileage.setMaxValue(1000000);
        usage.setMinValue(10);
        usage.setMaxValue(5000);
    }

    /***
     * checking plate entry
     */
    public void check_plate_entry() {
        plate_number = firebasemethods.stringModifyPlate(plate1.getText().toString(), plate2.getText().toString(),
                plate3.getText().toString(), plate4.getText().toString(), "concat");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (plate_number.length() != 8) {
                    Toast.makeText(mContext, "Please check your entry!", Toast.LENGTH_SHORT).show();
                }
                if (firebasemethods.check_plate(plate_number, dataSnapshot) && plate_number.length() == 8) {
                    try {
                        firebasemethods.addCarToDB(user.getUid(), String.valueOf(car_model_spinner.getSelectedItem()),
                                String.valueOf(mileage.getValue()), plate_number, String.valueOf(usage.getValue()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /***
     * vaghti add button click mishe
     */
    public void add_btn_click() {
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check_plate_entry();

            }
        });
    }
}
