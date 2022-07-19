package com.example.spotted.ui.profile;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.services.FirebaseService;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel extends ViewModel {
    private FirebaseUser user;
    private MutableLiveData<String> phone;
    private MutableLiveData<String> email;
    private MutableLiveData<String> displayName;
    private MutableLiveData<Uri> photoUrl;

    public ProfileViewModel(){
        phone = new MutableLiveData<>();
        email = new MutableLiveData<>();
        displayName = new MutableLiveData<>();
        photoUrl = new MutableLiveData<>();
       initialize();

    }


    public LiveData<String> getEmail(){
        return email;
    }
    public LiveData<String> getPhone(){
        return phone;
    }
    public LiveData<String> getDisplayName(){
        return displayName;
    }
    public  LiveData<Uri> getPhotoUrl(){ return photoUrl;}

    public void initialize(){
        //load user info
        if(FirebaseService.isLoggedIn()){
            user = FirebaseService.getFirebaseAuth().getCurrentUser();
            email.postValue(user.getEmail());

            if(!user.getPhoneNumber().isEmpty())
                phone.postValue(user.getPhoneNumber());
            else
                phone.postValue("+267 78514962");

            if(!user.getDisplayName().isEmpty())
                displayName.postValue(user.getDisplayName());
            else
                displayName.postValue("");

            if(user.getPhotoUrl() != null)
                photoUrl.postValue(user.getPhotoUrl());
        }

    }

}
