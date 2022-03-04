package com.example.spotted.ui.edit;

import androidx.lifecycle.ViewModel;

import com.example.spotted.services.FirebaseService;
import com.google.firebase.auth.FirebaseUser;

public class EditViewModel extends ViewModel {
    private String phone;
    private String  name;
    private String  email;
    private String  location;
    private String imageUri;
    private FirebaseUser user;

    public EditViewModel(){
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
    public String getImageUri(){return imageUri;}
    public void setImageUri(String imageUri){
        this.imageUri = imageUri;
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
                imageUri = user.getPhotoUrl().toString();


        }

    }
}
