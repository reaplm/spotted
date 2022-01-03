package com.example.spotted;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.spotted.adapters.ViewPagerAdapter;
import com.example.spotted.ui.jobs.JobsFragment;
import com.example.spotted.ui.alerts.AlertsFragment;
import com.example.spotted.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;

    //TabLayout Fragments
    private HomeFragment homeFragment;
    private AlertsFragment alertsFragment;
    private JobsFragment jobsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
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
                R.id.nav_home, R.id.nav_gallery, R.id.nav_settings,
                R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Setup TabLayout
        mTabLayout = (TabLayout)findViewById(R.id.tablayout);
        mViewPager = (ViewPager2)findViewById(R.id.viewpager);
        setupViewPager();


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
     * Setup viewpager for TabLayout
     */
    public void setupViewPager()
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        //Create fragments for each tab
        homeFragment = new HomeFragment();
        alertsFragment = new AlertsFragment();
        jobsFragment = new JobsFragment();

        adapter.addFragment(homeFragment, "Home", R.drawable.ic_action_home);
        adapter.addFragment(alertsFragment, "Alerts", R.drawable.ic_action_alert);
        adapter.addFragment(jobsFragment, "Jobs", R.drawable.ic_action_work);

        mViewPager.setAdapter(adapter);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) ->{
            tab.setIcon(adapter.getFragmentIcon(position));
            tab.setText(adapter.getPageTitle(position));
        }).attach();
    }

}