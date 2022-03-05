package com.example.spotted.ui.profile;

import androidx.lifecycle.ViewModel;

import com.example.spotted.services.FirebaseService;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel extends ViewModel {
    private FirebaseUser user;
    private String phone;
    private String email;
    private String displayName;


    public ProfileViewModel(){
        if(FirebaseService.isLoggedIn()){
            user = FirebaseService.getFirebaseAuth().getCurrentUser();
            phone = user.getPhoneNumber() == null ? "78514962" : user.getPhoneNumber() ;
            email = user.getEmail();

            if(user.getDisplayName() != null)
                displayName = user.getDisplayName().split(" ")[0];
        }

    }


    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
    public String getDisplayName(){
        return displayName;
    }


}
