package com.example.spotted.ui.profile;

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


    public ProfileViewModel(){
        phone = new MutableLiveData<>();
        email = new MutableLiveData<>();
        displayName = new MutableLiveData<>();
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

    public void initialize(){
        //load user info
        if(FirebaseService.isLoggedIn()){
            user = FirebaseService.getFirebaseAuth().getCurrentUser();
            email.postValue(user.getEmail());

            if(user.getPhoneNumber() != null)
                phone.postValue(user.getPhoneNumber());
            else
                phone.postValue("78514962");

            if(user.getDisplayName() != null)
                displayName.postValue(user.getDisplayName().split(" ")[0]);
            else
                displayName.postValue("");
        }

    }

}
