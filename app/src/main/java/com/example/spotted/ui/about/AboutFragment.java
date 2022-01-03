package com.example.spotted.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotted.R;
import com.example.spotted.ui.home.HomeViewModel;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aboutViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
       View view = inflater.inflate(R.layout.fragment_about, container, false);
       textView = view.findViewById(R.id.about_text);
       return view;
    }


}
