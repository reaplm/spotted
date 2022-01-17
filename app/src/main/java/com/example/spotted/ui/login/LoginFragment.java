package com.example.spotted.ui.login;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotted.R;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;

    private EditText email;
    private EditText password;
    private TextView registerLink;
    private Button loginButton;
    private CheckBox rememberMeCheck;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        registerLink = root.findViewById(R.id.register_link);
        loginButton = root.findViewById(R.id.login_button);
        rememberMeCheck = root.findViewById(R.id.login_rememberme);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;
    }
}
