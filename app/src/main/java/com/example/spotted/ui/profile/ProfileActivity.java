package com.example.spotted.ui.profile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotted.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {
    private ProfileViewModel profileViewModel;
    private TextView email;
    private TextView phone;
    private TextView displayName;
    private ListView listView;

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

            }
        });

        phone = findViewById(R.id.profile_phone_text);
        email = findViewById(R.id.profile_email_text);
        displayName = findViewById(R.id.profile_name);


        phone.setText(profileViewModel.getPhone());
        email.setText(profileViewModel.getEmail());
        displayName.setText(profileViewModel.getDisplayName());

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
}
