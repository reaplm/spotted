package com.example.spotted.ui.edit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigator;

import com.example.spotted.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class EditActivity extends AppCompatActivity {
    private EditText name;
    private EditText phone;
    private TextView email;
    private EditText location;
    private LinearProgressIndicator indicator;

    private FloatingActionButton uploadFab;
    private ImageView profileImage;;

    private EditViewModel editViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Profile");
        setContentView(R.layout.activity_edit_profile);

        editViewModel = new ViewModelProvider(this, new EditViewModelFactory(this))
                .get(EditViewModel.class);

        name = findViewById(R.id.edit_user_profile_name);
        phone = findViewById(R.id.edit_user_profile_phone);
        email = findViewById(R.id.edit_user_profile_email);
        location = findViewById(R.id.edit_user_profile_location);
        uploadFab = findViewById(R.id.edit_profile_fab);
        profileImage = findViewById(R.id.profile_pic);
        indicator = findViewById(R.id.linear_progress_indicator);

        name.setText(editViewModel.getName());
        phone.setText(editViewModel.getPhone());
        email.setText(editViewModel.getEmail());
        location.setText(editViewModel.getLocation());


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editViewModel.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editViewModel.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editViewModel.setPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        uploadFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editViewModel.getUpdateResult().observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                indicator.setVisibility(View.GONE);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                //save profile
                //prepare auth properties
                indicator.setVisibility(View.VISIBLE);
                HashMap props = new HashMap();
                props.put("DisplayName", editViewModel.getName());

                editViewModel.updateProfile(props);

                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

}

