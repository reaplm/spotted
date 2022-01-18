package com.example.spotted;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotted.adapters.ViewPagerAdapter;
import com.example.spotted.services.FirebaseService;
import com.example.spotted.services.LocalBroadcastManager;
import com.example.spotted.ui.jobs.JobsFragment;
import com.example.spotted.ui.alerts.AlertsFragment;
import com.example.spotted.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fabAlert = findViewById(R.id.fab_add_alert);
        fabAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_likes, R.id.nav_settings,
                R.id.nav_about, R.id.nav_login)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Listen for broadcasts
        IntentFilter filter = new IntentFilter("ACTION_LOGIN");
        filter.addAction("ACTION_LOGIN");
        registerReceiver(broadcastReceiver, filter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    /**
     * Listen for broadcasts from edit profile activity
     */
    BroadcastReceiver broadcastReceiver = new LocalBroadcastManager(){
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUi(intent);
        }
    };
    /**
     * Update UI from async tasks
     * @param intent
     */
    public void updateUi(Intent intent){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String action = intent.getAction();
                Boolean success = intent.getBooleanExtra("success",false);
                switch(action){
                    case "ACTION_LOGIN":
                        if(success){
                            Toast.makeText(getApplicationContext(), "Login successful!",
                                    Toast.LENGTH_LONG).show();
                            updateHeader();
                            updateLoginMenu();
                        }
                        else{
                            if(intent.getStringExtra("error") != null){
                                Toast.makeText(getApplicationContext(), intent.getStringExtra("error"),
                                        Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Login failed",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                }

            }
        });
    }

    private void updateLoginMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem nav_login = menu.findItem(R.id.nav_login);

        if(FirebaseService.getFirebaseAuth().getCurrentUser() != null){
            nav_login.setTitle(R.string.menu_logout);
        }
        else{
            nav_login.setTitle(R.string.menu_login);
        }

    }

    private void updateHeader(){
        //Header
        FirebaseUser user = FirebaseService.getFirebaseAuth().getCurrentUser();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView header_title= headerView.findViewById(R.id.nav_header_title);

        if (user != null) {
            if(user.getDisplayName() != null)
                header_title.setText(user.getDisplayName().split(" ")[0]);
            else
                header_title.setText(user.getEmail());
        } else {
            header_title.setText("");
        }


    }
}