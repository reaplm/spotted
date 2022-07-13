package com.example.spotted.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.db.IUserDao;
import com.example.spotted.db.UserDao;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private IUserDao userDao;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Settings");

        userDao = new UserDao();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void deleteUser(){
        userDao.deleteProfile();
    }
}