package com.example.spotted.ui.settings;

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

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private IUserDao userDao;
    private MutableLiveData<Task> taskResult;
    private Uri photoUrl;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Settings");

        userDao = new UserDao();
        taskResult = new MutableLiveData<>();

        if(FirebaseService.getFirebaseAuth().getCurrentUser() != null)
            photoUrl = FirebaseService.getFirebaseAuth().getCurrentUser().getPhotoUrl();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void deleteUser(){
        userDao.deleteProfile(new OnCompleteListener<Task>() {
            @Override
            public void onComplete(@NonNull Task<Task> task) {
                taskResult.postValue(task);
            }
        });
    }
    public LiveData<Task> getDeleteResult(){
        return taskResult;
    }
    public Uri getPhotoUrl(){
        return photoUrl;
    }
}