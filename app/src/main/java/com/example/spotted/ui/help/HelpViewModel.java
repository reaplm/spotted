package com.example.spotted.ui.help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel {
    private MutableLiveData<String> text = new MutableLiveData<>();

    public HelpViewModel(){
        text.postValue("This is Help and Feedback Fragment");
    }
    public LiveData<String> getText(){
        return text;
    }
}
