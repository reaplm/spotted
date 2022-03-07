package com.example.spotted;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.spotted.ui.profile.ProfileActivity;
import com.example.spotted.utils.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuId = item.getItemId();
                View parentLayout = findViewById(android.R.id.content);
                switch (menuId) {
                    case R.id.nav_home:
                        navController.navigate(R.id.nav_home);
                        break;
                    case R.id.nav_likes:
                        navController.navigate(R.id.nav_likes);
                        break;
                    case R.id.nav_about:
                        navController.navigate(R.id.nav_about);
                        break;
                    case R.id.nav_settings:
                        navController.navigate(R.id.nav_settings);
                        break;
                   case R.id.nav_logout:
                        signOut();
                       showSnackBar("You have been logged out.");
                        break;
                    case R.id.nav_login:
                        navController.navigate(R.id.nav_login);
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        //Onclick for nav_header
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseService.isLoggedIn()){
                    //Start profile activity
                    Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                    startActivity(intent);
                }
                else{
                    //go to login
                    navController.navigate(R.id.nav_login);
                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //Listen for broadcasts
        IntentFilter filter = new IntentFilter();
        filter.addAction("ACTION_LOGIN");
        filter.addAction("ACTION_REGISTRATION");
        filter.addAction("ACTION_UPDATE_PROFILE");
        registerReceiver(broadcastReceiver, filter);

        //Update UI
        updateLoginMenu();
        updateHeader();

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
                String message = intent.getStringExtra("message");
                switch (action){
                    case "ACTION_LOGIN": case   "ACTION_REGISTRATION":
                        if(success){
                            updateHeader();
                            updateLoginMenu();
                            showSnackBar("You have successfully logged in!");
                        }
                        else{
                            if(intent.getStringExtra("error") != null){
                                showDialog(action.split("_")[1], intent.getStringExtra("message") );
                            }
                            else{
                                showDialog(action.split("_")[1], "Sorry, an error occured.");
                            }

                        }

                    break;
                    case "ACTION_UPDATE_PROFILE":
                        if(success){
                            System.out.println("Profile saved successfully");

                            //update the header
                            updateHeader();
                        }
                        else{
                            System.out.println("Failed to save profile");
                        }
                        showSnackBar(message);
                        break;
                }
            }

        });
    }
    private void signOut() {
        FirebaseService.signOut();

        //update drawer menu
        updateLoginMenu();
        updateHeader();

    }
    private void updateLoginMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if(FirebaseService.isLoggedIn()){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_logout);
        }
        else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
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
    private void showDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setNeutralButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showSnackBar(String message){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar
                .make(parentLayout, message,
                        Snackbar.LENGTH_LONG).show();
    }
}