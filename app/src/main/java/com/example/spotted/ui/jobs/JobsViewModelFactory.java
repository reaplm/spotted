package com.example.spotted.ui.jobs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class JobsViewModelFactory implements ViewModelProvider.Factory {
    private Context mContext;

    public JobsViewModelFactory(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)  new JobsViewModel(mContext);
    }
}
