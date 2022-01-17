package com.example.spotted.ui.register;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.db.IUserDao;
import com.example.spotted.db.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.HashMap;

public class RegisterViewModel extends ViewModel {
    private IUserDao userDao;
    private LiveData<String> userEmail;
    private MutableLiveData<Task<AuthResult>> authResult = null;
    private MutableLiveData<Task> updateResult = null;

    public RegisterViewModel(){
        userDao = new UserDao();
        authResult = new MutableLiveData<>();
        updateResult = new MutableLiveData<>();
    }
    public void submitRegistration(String email, String password){
        userDao.createUserWithEmailAndPassword(email, password,
                new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        authResult.postValue(task);
                    }
                });
    }
    public LiveData<Task<AuthResult>>  getAuthResult(){
        return authResult;
    }

}
