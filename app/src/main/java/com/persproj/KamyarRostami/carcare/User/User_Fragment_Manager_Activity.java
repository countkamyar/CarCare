package com.persproj.KamyarRostami.carcare.User;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.persproj.KamyarRostami.carcare.R;
import com.persproj.KamyarRostami.carcare.User.MVVM.Carfragment;

public class User_Fragment_Manager_Activity extends AppCompatActivity {
    private String TAG = "User_Fragment_Manager_Activity";

    //private FirebaseDatabase firebaseDatabase;
//private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__fragment__manager_);
        Intent intent = getIntent();
        String nav_id = intent.getStringExtra(String.valueOf(R.string.navid));
        Log.d(TAG, "onCreate: nav_id: " + nav_id);
        switch (nav_id) {
            case "1":
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag_contain, new Profilefragment());
                fragmentTransaction.commit();
                break;
            case "2":
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.frag_contain, new Carfragment());
                fragmentTransaction3.commit();
                break;
            case "3":
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.frag_contain, new Aboutfragment());
                fragmentTransaction2.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
