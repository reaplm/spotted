package com.example.spotted.ui.profile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotted.R;
import com.example.spotted.services.LocalBroadcastManager;
import com.example.spotted.ui.edit.EditActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends AppCompatActivity {
    private ProfileViewModel profileViewModel;
    private TextView email;
    private TextView phone;
    private TextView displayName;
    private ImageView profileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_camera_filled);

        FloatingActionButton fab = findViewById(R.id.edit_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditActivity.class);

                startActivity(intent);
            }
        });

        phone = findViewById(R.id.profile_phone_text);
        email = findViewById(R.id.profile_email_text);
        displayName = findViewById(R.id.profile_name);
        profileImage = findViewById(R.id.profile_pic);

        profileViewModel.getDisplayName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                phone.setText(s);
            }
        });
        profileViewModel.getEmail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                email.setText(s);
            }
        });
        profileViewModel.getDisplayName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                displayName.setText(s);
            }
        });
        profileViewModel.getPhotoUrl().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                profileImage.setImageURI(uri);
            }
        });




        //Listen for update profile broadcasts
        IntentFilter filter = new IntentFilter("ACTION_UPDATE_PROFILE");
        filter.addAction("ACTION_UPDATE_PROFILE");
        registerReceiver(broadcastReceiver, filter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Update ui after returning from edit profile
     * @param intent containing task
     */
    public void updateUi(Intent intent){
        View parentLayout = findViewById(R.id.profile_linearlayout);
        String action = intent.getAction();
        boolean success = intent.getBooleanExtra("success",false);
        String message = intent.getStringExtra("message");
        switch(action){
            case "ACTION_UPDATE_PROFILE":
                if(success){
                    System.out.println("Profile saved successfully");
                    profileViewModel.initialize();
                }
                else{
                    System.out.println("Failed to save profile");
                }
                Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
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
}
