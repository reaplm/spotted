package com.example.spotted.ui.edit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.db.IUserDao;
import com.example.spotted.db.UserDao;
import com.example.spotted.services.FirebaseService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class EditViewModel extends ViewModel {
    private Context mContext;
    private String phone;
    private String  name;
    private String  email;
    private String  location;
    private Uri imageUri;
    private FirebaseUser user;

    IUserDao userDao;
    private MutableLiveData<Task> result;


    public EditViewModel(Context context){
        mContext = context;
        userDao = new UserDao();
        result = new MutableLiveData<>();
        initialize();
    }
    public String getName() {
        return  name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Uri getImageUri(){return imageUri;}
    public void setImageUri(Uri imageUri){
        this.imageUri = imageUri;
    }
    public LiveData<Task> getUpdateResult(){
        return result;
    }

    public void updateProfile(HashMap<String, String> userProps){
        userDao.updateProfile(userProps,new OnCompleteListener<Task>(){
            @Override
            public void onComplete(@NonNull Task<Task> task) {
                Intent intent = new Intent("ACTION_UPDATE_PROFILE");
                if(task.isSuccessful()) {
                    intent.putExtra("success", true);
                    intent.putExtra("message","Profile updated successfully!");
                   // resultMessage.postValue("Profile updated successfully!");
                }
                else {
                    if(task.getException() != null){
                        intent.putExtra("success", false);
                        intent.putExtra("message", task.getException().getMessage());
                        ////resultMessage.postValue(task.getException().getMessage());
                    }
                    else{
                        intent.putExtra("success", false);
                        intent.putExtra("message", "Failed to update profile");
                        //resultMessage.postValue("Failed to update profile");
                    }
                }
                mContext.sendBroadcast(intent);
                result.postValue(task);
            }
        });
    }

    public void initialize(){
        //load user info
        if(FirebaseService.isLoggedIn()){
            //firebase user
            user = FirebaseService.getFirebaseAuth().getCurrentUser();
            phone = user.getPhoneNumber();
            email = user.getEmail();

            if(user.getDisplayName() != null)
                name = user.getDisplayName();
            else
                name = "";

            if(user.getPhotoUrl() != null)
                imageUri = user.getPhotoUrl();


        }

    }
}
