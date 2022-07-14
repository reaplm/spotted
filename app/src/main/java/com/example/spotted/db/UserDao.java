package com.example.spotted.db;

import android.net.Uri;

import com.example.spotted.services.FirebaseService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.HashMap;

public class UserDao implements IUserDao{
    @Override
    public void signInWithEmailAndPassword(String email, String password,
                                           OnCompleteListener<AuthResult> onCompleteListener) {
        // SignIn
        Task<AuthResult> resultTask = FirebaseService.getFirebaseAuth()
                .signInWithEmailAndPassword(email, password);

        if(onCompleteListener != null)
            resultTask.addOnCompleteListener(onCompleteListener);

    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password,
                                               OnCompleteListener<AuthResult> onCompleteListener) {
        // Attempt registration [ If successful the user is automatically logged in ]
        Task<AuthResult> resultTask = FirebaseService.getFirebaseAuth()
                .createUserWithEmailAndPassword(email, password);

        if(onCompleteListener != null)
            resultTask.addOnCompleteListener(onCompleteListener);

    }
    @Override
    public void updateProfile(HashMap<String, String> props, OnCompleteListener<Task> onCompleteListener) {
        FirebaseUser user = FirebaseService.getFirebaseAuth().getCurrentUser();

        UserProfileChangeRequest.Builder profileUpdatesBuilder = new UserProfileChangeRequest.Builder();

        if(props.containsKey("DisplayName"))
            profileUpdatesBuilder.setDisplayName(props.get("DisplayName"));

        if(props.containsKey("PhotoUri"))
            profileUpdatesBuilder.setPhotoUri(Uri.parse(props.get("PhotoUri")));

        UserProfileChangeRequest profileUpdates = profileUpdatesBuilder.build();

        Task task = user.updateProfile(profileUpdates);

        if(onCompleteListener != null)
            task.addOnCompleteListener(onCompleteListener);

    }

    @Override
    public void deleteProfile(OnCompleteListener<Task>  onCompleteListener) {
        if(FirebaseService.isLoggedIn()){
            Task task = FirebaseService.getFirebaseAuth().getCurrentUser().delete();

            if(onCompleteListener != null)
                task.addOnCompleteListener(onCompleteListener);
        }
    }
}
