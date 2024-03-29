package com.example.spotted.db;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.HashMap;

public interface IUserDao {
    void signInWithEmailAndPassword(String email, String password,
                                    OnCompleteListener<AuthResult> onCompleteListener);
    void createUserWithEmailAndPassword(String email, String password,
                                    OnCompleteListener<AuthResult> onCompleteListener);
    void updateProfile(HashMap<String, String> props, OnCompleteListener<Task> onCompleteListener);
    void deleteProfile(OnCompleteListener<Task> onCompleteListener);
}
