package com.example.spotted.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.db.IUserDao;
import com.example.spotted.db.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginViewModel extends ViewModel {
    private IUserDao userDao;
    private MutableLiveData<Task<AuthResult>> authResult = null;

    public LoginViewModel(){
        userDao = new UserDao();
        authResult = new MutableLiveData<>();
    }
    public void submitLogin(String email, String password){
        userDao.signInWithEmailAndPassword(email, password, new OnCompleteListener<AuthResult>(){
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
