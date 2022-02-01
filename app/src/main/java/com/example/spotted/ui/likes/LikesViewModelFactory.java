package com.example.spotted.ui.likes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LikesViewModelFactory implements ViewModelProvider.Factory {
    private Context mContext;
    public LikesViewModelFactory(Context context){mContext = context;}
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LikesViewModel(mContext);
    }
}
