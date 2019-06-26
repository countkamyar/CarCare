package com.persproj.KamyarRostami.carcare.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.persproj.KamyarRostami.carcare.MainView.MainActivity;
import com.persproj.KamyarRostami.carcare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Sign_in_Activity extends AppCompatActivity {
    private TextView sign_in_1;
    private TextView sign_in_2;
    private Button send_button;
    private EditText enter_number;
    private Button enter;
    private TextView retry_button;
    private EditText enter_code;
    private Context mcontext = Sign_in_Activity.this;
    private String TAG = "Sign_in_Activity";
    private FirebaseAuth firebaseAuth;
    private String verificationcode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findviews();
        firebaselogin();

        //listener button e send code
        sendsms();
        //listener button e sign in
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String code = enter_code.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationcode, code);
                signinwiththephone(credential);
            }
        });
        retry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendsms();
            }
        });

    }

    /**
     * listener button e send code
     */
    public void sendsms() {
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enter_number.getText().length() < 13) {
                    Toast.makeText(mcontext, "Try again with a valid number!", Toast.LENGTH_SHORT).show();
                } else {
                    // retry bad az 60 sanie
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            retry_button.setClickable(true);
                        }
                    }, 60000);

                    final String number = enter_number.getText().toString();
                    Log.d(TAG, "onClick: number " + number);
                    sendsms(number);

                }
            }
        });
    }

    /**
     * bind kardane view ha
     */
    private void findviews() {
        send_button = (Button) findViewById(R.id.send_btn);
        enter_number = (EditText) findViewById(R.id.signin_edit_text1);
        enter = (Button) findViewById(R.id.enter_code_btn);
        enter_code = (EditText) findViewById(R.id.signin_edit_text2);
        retry_button = (TextView) findViewById(R.id.retry_btn);
        sign_in_1 = (TextView) findViewById(R.id.signin_text1);
        sign_in_2 = (TextView) findViewById(R.id.signin_text2);
    }

    /**
     * taghire ui bad az ersale movafaghiat amize code
     */
    private void uichange() {
        sign_in_1.setVisibility(View.GONE);
        sign_in_2.setVisibility(View.VISIBLE);
        enter_number.setVisibility(View.GONE);
        enter.setVisibility(View.VISIBLE);
        send_button.setVisibility(View.GONE);
        enter_code.setVisibility(View.VISIBLE);
        retry_button.setVisibility(View.VISIBLE);
        retry_button.setClickable(false);
    }

    /**
     * ersale code be shomare morede nazar
     *
     * @param number
     */
    private void sendsms(String number) {
        Log.d(TAG, "sendsms: gotten phone number:" + number);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                Sign_in_Activity.this, // Activity (for callback binding)
                mCallback
        );
    }

    /**
     * handle kardane ersale code
     */
    public void firebaselogin() {

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                uichange();
                verificationcode = s;
                Log.d(TAG, "onCodeSent: verification code: " + s);
                Toast.makeText(mcontext, "A code has been sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.e(TAG, "onVerificationFailed: " + e.getMessage() + " ", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(mcontext, "Invalid Request", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(mcontext, "TooManyRequests", Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

    public void signinwiththephone(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(mcontext,"Sign in successful",Toast.LENGTH_SHORT).show();
                            Intent gotomain = new Intent(mcontext,MainActivity.class);
                            startActivity(gotomain);
                            finish();
                        }
                        else {
                            Toast.makeText(mcontext,"Incorrect Code",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setContentView(R.layout.activity_sign_in);
        findviews();
        firebaselogin();

        //listener button e send code
        sendsms();
        //listener button e sign in
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String code = enter_code.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationcode, code);
                signinwiththephone(credential);
            }
        });
        retry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendsms();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
