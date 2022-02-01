package com.example.spotted.ui.likes;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.models.Job;
import com.example.spotted.utils.PreferenceManager;

import java.util.List;

public class LikesViewModel extends ViewModel {

    private Context mContext;
    private MutableLiveData<List<Job>> likes = new MutableLiveData<>();

    public LikesViewModel(Context context){
        mContext = context;


    }

    public LiveData<List<Job>> getLikes() {
        return likes;
    }

    public void onResume(){
        //Get likes
        likes.setValue(PreferenceManager.getInstance(mContext).getLikes());
    }

}