package com.persproj.KamyarRostami.carcare.User;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.persproj.KamyarRostami.carcare.DataModel.Users;
import com.persproj.KamyarRostami.carcare.Utils.Firebase;
import com.persproj.KamyarRostami.carcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profilefragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String TAG = "Profilefragment";
    private EditText name, surname, emailaddress, phonenumber;
    private Button save_btn;
    private Users users;

    public static Profilefragment newInstance() {
        Profilefragment profilefragment = new Profilefragment();
        return profilefragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final Firebase firebasemethods = new Firebase(firebaseAuth, getActivity());
        init(v);
        showProfileInfo(user, firebasemethods);
        if (user != null) {
            final String uid = user.getUid();
            Log.d(TAG, "onCreateView: uid " + uid);
            final String phonenum = user.getPhoneNumber();
            Log.d(TAG, "onCreateView: phonenum " + phonenum);
            phonenumber.setText(phonenum);

        }
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkedittext() && user != null) {
                    firebasemethods.addNewUser(user.getUid(), name.getText().toString(), surname.getText().toString(), emailaddress.getText().toString(),
                            user.getPhoneNumber());
                } else {
                    Toast.makeText(getActivity(), "Make sure you fill in the information correctly!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public void init(View v) {
        name = (EditText) v.findViewById(R.id.editprofile_name);
        surname = (EditText) v.findViewById(R.id.editprofile_surname);
        emailaddress = (EditText) v.findViewById(R.id.editprofile_email);
        phonenumber = (EditText) v.findViewById(R.id.editprofile_phone);
        save_btn = (Button) v.findViewById(R.id.profile_save_btn);
    }

    /***
     * neshan dadane etelate pishfarz dar db
     * @param user
     * @param firebase
     */
    public void showProfileInfo(final FirebaseUser user, final Firebase firebase) {
        try {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        name.setText(firebase.retrieveProfileInfo(user.getUid(), dataSnapshot, "name"));
                        surname.setText(firebase.retrieveProfileInfo(user.getUid(), dataSnapshot, "surname"));
                        emailaddress.setText(firebase.retrieveProfileInfo(user.getUid(), dataSnapshot, "email"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled: " + databaseError.getMessage()
                    );
                }
            });


        } catch (NullPointerException e) {
            Log.e(TAG, "showProfileInfo: null pointer exception: " + e.getMessage());
        }

    }

    /***
     * editext hengame upload null nabashad
     * @return
     */
    public boolean checkedittext() {
        if (name.getText().toString().length() != 0 && surname.getText().toString().length() != 0
                && emailaddress.getText().toString().length() != 0 && emailaddress.getText().toString().contains("@")) {
            return true;
        }

        return false;

    }

}
