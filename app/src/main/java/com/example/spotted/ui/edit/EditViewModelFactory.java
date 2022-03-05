package com.example.spotted.ui.edit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EditViewModelFactory implements ViewModelProvider.Factory {
    private Context mContext;

    public EditViewModelFactory(Context context){mContext = context; }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditViewModel(mContext);
    }
}
