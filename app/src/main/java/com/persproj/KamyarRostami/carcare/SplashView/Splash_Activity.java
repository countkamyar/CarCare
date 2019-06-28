package com.persproj.KamyarRostami.carcare.SplashView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.persproj.KamyarRostami.carcare.MainView.MainActivity;
import com.persproj.KamyarRostami.carcare.R;
import com.persproj.KamyarRostami.carcare.Services.CheckCarServiceTime;
import com.persproj.KamyarRostami.carcare.User.Sign_in_Activity;
import com.persproj.KamyarRostami.carcare.Utils.NetworkHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash_Activity extends AppCompatActivity {
    Handler handler = new Handler();
    private AlertDialog alertDialog;
    private String stringExtra = null;
    private static final String TAG = "Splash_Activity";
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        //receiving cnnection status from service
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                stringExtra = intent.getStringExtra(CheckCarServiceTime.MYSERVICECODE);
                Log.d(TAG, "onReceive: stringextra: " + stringExtra);
                connectionCheck(stringExtra);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        checkCarMileage();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, new IntentFilter(CheckCarServiceTime.MYSERVICEMESSAGE));

    }

    /***
     * start e service
     */
    private void checkCarMileage() {
        Intent intent = new Intent(this, CheckCarServiceTime.class);
        startService(intent);
    }

    /***
     * start e intent be main activity
     */
    private void startApp() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // No user is signed in
                    Intent intent2 = new Intent(Splash_Activity.this, Sign_in_Activity.class);
                    startActivity(intent2);
                    finish();
                }

            }
        }, 5000);
    }

    /***
     * checking connection status based on received broadcast
     * @param stringExtra
     */
    private void connectionCheck(String stringExtra) {
        Log.d(TAG, "connectionCheck:boolean: " + stringExtra.equals("DB connection error"));
        if (NetworkHelper.hasNetworkAccess(this) && stringExtra.equals("DB connection ok")) {
            startApp();
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
        } else {
            alertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.network_dialog_title)
                    .setMessage(R.string.network_dialog_message)
                    .setIcon(R.drawable.ic_error)
                    .setNeutralButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    }).create();
            alertDialog.show();
        }
    }

}
