package com.example.spotted.ui.login;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.spotted.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
                //navigate to registration
                Navigation.findNavController(root)
                        .navigate(R.id.action_nav_login_to_nav_register);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(getActivity());
                loginViewModel.submitLogin(email.getText().toString(),
                        password.getText().toString());
            }
        });

        loginViewModel.getAuthResult().observe(getViewLifecycleOwner(), new Observer<Task<AuthResult>>() {
            @Override
            public void onChanged(Task<AuthResult> authResultTask) {
                if(authResultTask.isComplete()){
                    if(authResultTask.isSuccessful()){ //registration was successful
                        Toast.makeText(getContext(), "authResultTask.getException().getMessage()",
                                Toast.LENGTH_LONG).show();
                        //go home
                        Navigation.findNavController(root)
                                .navigate(R.id.action_nav_login_to_nav_home);
                    }
                    else {

                        showErrorDialog(authResultTask.getException().getMessage());
                        //System.out.println(authResultTask.getException().getMessage());
                    }
                }
                else{ System.out.println("Task not complete");}
            }
        });
        return root;
    }
    private void showErrorDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);

        builder.setNeutralButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
