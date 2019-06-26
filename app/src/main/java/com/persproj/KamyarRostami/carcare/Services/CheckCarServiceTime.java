package com.persproj.KamyarRostami.carcare.Services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.util.Log;

import com.persproj.KamyarRostami.carcare.CarView.MileageCalculator;
import com.persproj.KamyarRostami.carcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class CheckCarServiceTime extends IntentService {
    private DatabaseReference databaseReference;
    private MileageCalculator mileageCalculator;
    private FirebaseAuth mAuth;
    private static final String TAG = "CheckCarServiceTime";
    private NotificationManager notificationManager;
    public static final int NOTIFICATION_ID = 1;
    private String CHANNEL_ID = "MY_CHANNEL_ID";
    public static final String MYSERVICEMESSAGE = "Error in DB";
    public static final String MYSERVICECODE = "Service code";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        mAuth = FirebaseAuth.getInstance();
        mileageCalculator = new MileageCalculator(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public CheckCarServiceTime() {
        super("CheckCarServiceTime");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent messageintent = new Intent(MYSERVICEMESSAGE);
        if (checkFirebaseConnection()) {
            try {
                if(mAuth.getCurrentUser().getUid()!=null){
                checkCars(mAuth.getCurrentUser().getUid());}
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageintent.putExtra(MYSERVICECODE,"DB connection ok");
        } else {
            messageintent.putExtra(MYSERVICECODE, "DB connection error");
        }
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageintent);
    }


    private boolean checkFirebaseConnection() {
        try {
            URL url = new URL("https://firebase.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int code = connection.getResponseCode();
            Log.d(TAG, "checkFirebaseConnection: responseCode " + code);
            if (code == 403) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {

            Log.d(TAG, "checkFirebaseConnection: error: " + e.getMessage());
            return false;
        }
    }

    private void checkCars(String userID) {

        Query query = databaseReference.child(getString(R.string.car_db_name)).child(userID).orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    try {
                        String string = ds.child("date").getValue().toString();
                        String substring = string.substring(Math.max(string.length() - 2, 0));
                        Log.d(TAG, "onDataChange: substringdata: " + substring);
                        Log.d(TAG, "onDataChange: " + mileageCalculator.mileageCalculator(Integer.parseInt(ds.child("usage").getValue().toString()),
                                ds.child("model").getValue().toString(), Integer.parseInt(substring)));
                        if (mileageCalculator.mileageCalculator(Integer.parseInt(ds.child("usage").getValue().toString()),
                                ds.child("model").getValue().toString(), Integer.parseInt(substring))) {
                            //send a message containing cars plate and alert to push notification
                            pushNotification(ds.child("plate").getValue().toString(), ds.child("model").getValue().toString());
                        }
                    } catch (NullPointerException e) {
                        Log.e(TAG, "onDataChange:NullPointerException " + e.getMessage());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());

            }
        });

    }


    // .setLargeIcon(Picasso.get().load(mileageCalculator.carUrl(carModel)))
    private void pushNotification(String carPlate, String carModel) {
        Intent intent = new Intent(this, CardView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Log.d(TAG, "pushNotification: Notification called");
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_title) + carPlate)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_message)))
                .setContentText(carPlate)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
