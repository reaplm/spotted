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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigator;

import com.example.spotted.BuildConfig;
import com.example.spotted.R;
import com.example.spotted.services.FirebaseService;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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


    final String[] CAMERAPERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    final String[] GALLERYPERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private String currentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Profile");
        setContentView(R.layout.activity_edit_profile);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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
        profileImage.setImageURI(editViewModel.getImageUri());


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
                showBottomSheetDialog();
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
    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_upload_image);

        LinearLayout camera = bottomSheetDialog.findViewById(R.id.camera);
        LinearLayout gallery = bottomSheetDialog.findViewById(R.id.gallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                requestCameraPermissionLauncher.launch(CAMERAPERMISSIONS);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                requestGalleryPermissionLauncher.launch(GALLERYPERMISSIONS);
            }
        });

        bottomSheetDialog.show();
    }
    /**
     * Method for processing captured image from camera
     *
     */

    /**
     * Register the permissions callback, which handles the user's response to the
     * system permissions dialog. Save the return value, an instance of
     * ActivityResultLauncher, as an instance variable.
     */
    private ActivityResultLauncher<String[]> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                if (isGranted.containsValue(false)) {//One or all permission were denied
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    alertDialog("Spotted Permissions",
                            "Camera and External Storage permissions are required for this feature");
                } else {
                    // Permission is granted. Continue the action or workflow
                    //start camera activity
                    //dispatchTakePictureIntent();
                    dispatchTakePictureIntent();

                }
            });
    /**
     * Camera result launcher
     */
    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    handleCameraImage(result.getData());
                } else {
                    alertDialog("Camera", "Request cancelled or something went wrong");

                }
            });
    //Handle camera result after taking picture
    private void handleCameraImage(Intent data){
        Bitmap image = BitmapFactory.decodeFile(currentPhotoPath);
        profileImage.setImageBitmap(image);
        editViewModel.setImageUri(Uri.parse(currentPhotoPath));
    }
    //Create file to store captured image
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/profile");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    //Take the image
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error while creating image",
                        Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraActivityResultLauncher.launch(takePictureIntent);

            }
        }
    }
    /**
     * Methods for processing select from gallery
     */
    /**
     * Gallery result launcher
     */
    /**
     * Register the permissions callback, which handles the user's response to the
     * system permissions dialog. Save the return value, an instance of
     * ActivityResultLauncher, as an instance variable.
     */
    private ActivityResultLauncher<String[]> requestGalleryPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                if (isGranted.containsValue(false)) {//One or all permission were denied
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    alertDialog("Spotted Permissions",
                            "External Storage permissions are required for this feature");
                } else {
                    // Permission is granted. Continue the action or workflow
                    //start camera activity
                    //dispatchTakePictureIntent();
                    dispatchSelectImageIntent();

                }
            });
    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    handleResultImage(result.getData());
                } else {
                    alertDialog("Gallery", "Request cancelled or something went wrong");

                }
            });

    //    //Handle select from gallery result
    private void handleResultImage(Intent data) {
        Uri selectedImageUri = data.getData();
        profileImage.setImageURI(selectedImageUri);
        editViewModel.setImageUri(selectedImageUri);
        saveSelectedImage(selectedImageUri);
    }

    private void saveSelectedImage(Uri selectedImageUri) {
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                //save to folder

                // Configure byte output stream
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                InputStream inputStream = this.getContentResolver()
                        .openInputStream(selectedImageUri);

                OutputStream outputStream = new FileOutputStream(photoFile);
                Bitmap bitmapImage = BitmapFactory.decodeStream(inputStream);
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

                if (inputStream == null) {
                    throw new IOException("Unable to open input stream for uri: " + selectedImageUri);
                }

                // Write the bytes of the bitmap to file
                outputStream.write(bytes.toByteArray());
                outputStream.close();
            }
        } catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(this, "Error while creating image",
                    Toast.LENGTH_LONG).show();
        }
    }
    //Select image from gallery
    private void dispatchSelectImageIntent() {
        Intent selectPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        selectPictureIntent.setType("image/*");

        galleryActivityResultLauncher.launch(Intent.createChooser(selectPictureIntent, "Select Picture"));
    }
    /**
     * Display alery dialog
     * @param title dialog title
     * @param message dialog body
     */
    public void alertDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("OK", null);

        builder.show();
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

                if(!currentPhotoPath.isEmpty() && currentPhotoPath != null)
                    props.put("PhotoUri", currentPhotoPath);

                editViewModel.updateProfile(props);

                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

}

