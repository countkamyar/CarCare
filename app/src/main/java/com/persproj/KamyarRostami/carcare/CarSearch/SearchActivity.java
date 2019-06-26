package com.persproj.KamyarRostami.carcare.CarSearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.persproj.KamyarRostami.carcare.DataModel.Cars;
import com.persproj.KamyarRostami.carcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private ListView carlistview;
    private EditText searchbar;
    private List<Cars> mCarlist;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    private static final String TAG = "SearchActivity";
    private CarListAdapter carListAdapter;
    private Context mContext = SearchActivity.this;
    private Cars car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        closeKeyboard();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        initTextListener();
    }

    /***
     * method e bastan e keyboard hengame start activity
     */
    private void closeKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /***
     * bad az etmame vared karadane keyword
     */
    private void initTextListener() {
        Log.d(TAG, "initTextListener: initializing");
        mCarlist = new ArrayList<>();
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = searchbar.getText().toString().toLowerCase(Locale.getDefault());
                searchFormatch(text);
            }
        });
    }

    /***
     * search bar asase keyword
     * @param keyWord
     */
    private void searchFormatch(final String keyWord) {
        Log.d(TAG, "searchFormatch: searching for a match" + keyWord);
        mCarlist.clear();
        if (keyWord.length() == 0) {
//            Toast.makeText(mContex, "Try to search a username instead!", Toast.LENGTH_SHORT).show();
        } else {
            Query query = databaseReference.child(getString(R.string.car_db_name)).orderByChild("plate");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Log.d(TAG, "onDataChange: value: " + ds.child("plate").getValue());
                            if (ds.child("plate").getValue().equals(keyWord)) {
                                car = new Cars();
                                Log.d(TAG, "onDataChange: found!");
                                //user ro be list add miknim
                                try {
                                    car.setPlate(ds.child("plate").getValue().toString());
                                    car.setModel(ds.child("model").getValue().toString());
                                    car.setMileage(ds.child("mileage").getValue().toString());
                                    car.setUsage(ds.child("usage").getValue().toString());
                                    car.setDate(ds.child("date").getValue().toString());
                                    mCarlist.add(car);
                                }catch (NullPointerException e){
                                    Log.e(TAG, "onDataChange: NullPointerException "+e.getMessage() );
                                }

                                Log.d(TAG, "onDataChange: mcarlist " + car.getPlate());
                                //userlistview ro update miknim
                                updateUserslistview();
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void init() {
        searchbar = (EditText) findViewById(R.id.search_bar);
        carlistview = (ListView) findViewById(R.id.search_result_list);
        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference();
    }

    /***
     * update list baraye namayesh result dar listview
     */
    public void updateUserslistview() {
        Log.d(TAG, "updateUserslistview: updating user's list");
        carListAdapter = new CarListAdapter(mContext, R.layout.single_car_view, mCarlist);
        carlistview.setAdapter(carListAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
