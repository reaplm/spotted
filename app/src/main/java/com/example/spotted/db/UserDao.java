package com.example.spotted.db;

import com.example.spotted.services.FirebaseService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

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
}
