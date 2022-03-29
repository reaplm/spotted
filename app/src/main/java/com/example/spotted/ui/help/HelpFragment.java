package com.example.spotted.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotted.R;

public class HelpFragment extends Fragment {
    private HelpViewModel helpViewModel;
    private TextView helpText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        helpText = root.findViewById(R.id.help_text);

        helpViewModel = new ViewModelProvider(getActivity()).get(HelpViewModel.class);

        helpViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helpText.setText(s);
            }
        });

        return root;
    }
}
