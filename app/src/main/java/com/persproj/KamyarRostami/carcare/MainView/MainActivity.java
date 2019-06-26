package com.persproj.KamyarRostami.carcare.MainView;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.persproj.KamyarRostami.carcare.CarAdd.CarAddActivity;
import com.persproj.KamyarRostami.carcare.CarSearch.SearchActivity;
import com.persproj.KamyarRostami.carcare.CarView.CarView;
import com.persproj.KamyarRostami.carcare.R;
import com.persproj.KamyarRostami.carcare.User.Sign_in_Activity;
import com.persproj.KamyarRostami.carcare.User.User_Fragment_Manager_Activity;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Context mcontext = MainActivity.this;
    private DrawerLayout drawerLayout;
    private ImageButton menu1, menu2, menu3;
    private Handler handler;
    private FirebaseAuth mAuth;
    private NavigationView navigationView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        findviews();
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        setonclick();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    public void findviews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu1 = (ImageButton) findViewById(R.id.menu1);
        menu2 = (ImageButton) findViewById(R.id.menu2);
        menu3 = (ImageButton) findViewById(R.id.menu3);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

    }

    public void setonclick() {
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mcontext, CarAddActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(mcontext, CarView.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(mcontext, SearchActivity.class);
                startActivity(intent3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_profile:
                        Intent fragintent = new Intent(mcontext, User_Fragment_Manager_Activity.class);
                        fragintent.putExtra(String.valueOf(R.string.navid), "1");
                        startActivity(fragintent);
                        break;
                    case R.id.nav_cars:
                        Intent fragintent3 = new Intent(mcontext, User_Fragment_Manager_Activity.class);
                        fragintent3.putExtra(String.valueOf(R.string.navid), "2");
                        startActivity(fragintent3);
                        break;
                    case R.id.nav_about:
                        Intent fragintent2 = new Intent(mcontext, User_Fragment_Manager_Activity.class);
                        fragintent2.putExtra(String.valueOf(R.string.navid), "3");
                        startActivity(fragintent2);
                        break;
                    case R.id.nav_signout:
                        mAuth.signOut();
                        Intent intent = new Intent(mcontext, Sign_in_Activity.class);
                        startActivity(intent);
                        finish();
                }
                return false;
            }
        });
    }

    boolean press = false;

    public void onBackPressed() {
        if (press) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else {
            press = true;
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    press = false;
                }
            }, 1500);
        }


    }

}
