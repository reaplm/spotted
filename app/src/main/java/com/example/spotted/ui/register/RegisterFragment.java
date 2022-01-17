package com.example.spotted.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.spotted.R;

public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;

    private EditText fName;
    private EditText lName;
    private EditText phone;
    private EditText email;
    private EditText password;
    private Button registerButton;
    private CheckBox rememberMeCheck;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        View root = inflater.inflate(R.layout.fragment_register, container, false);

        fName = root.findViewById(R.id.register_fname);
        lName = root.findViewById(R.id.register_lname);
        phone = root.findViewById(R.id.register_phone);
        email = root.findViewById(R.id.register_email);
        password = root.findViewById(R.id.register_password);
        registerButton = root.findViewById(R.id.register_button);
        rememberMeCheck = root.findViewById(R.id.register_rememberme);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return root;
    }
}
