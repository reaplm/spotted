package com.example.spotted.ui.register;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.spotted.R;
import com.example.spotted.services.FirebaseService;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.AuthResult;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;

    private EditText fName;
    private EditText lName;
    private EditText phone;
    private EditText email;
    private EditText password;
    private Button registerButton;
    private CheckBox rememberMeCheck;
    private LinearProgressIndicator progressIndicator;


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
        progressIndicator = root.findViewById(R.id.linear_progress_indicator);
        progressIndicator.setVisibility(View.INVISIBLE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(View.VISIBLE);
                registerViewModel.submitRegistration(email.getText().toString(),
                        password.getText().toString());
            }
        });

        registerViewModel.getAuthResult().observe(getViewLifecycleOwner(), new Observer<Task<AuthResult>>() {
            @Override
            public void onChanged(Task<AuthResult> authResultTask) {
                progressIndicator.setVisibility(View.INVISIBLE);
                if(authResultTask.isComplete()){
                    if(authResultTask.isSuccessful()){ //registration was successful
                        if(FirebaseService.getFirebaseAuth().getCurrentUser() != null){
                            showDialog("Registration Successful","You have successfully logged in!");
                        }
                        //Hide Keyboard
                        hideKeyboard(getActivity());
                        Navigation.findNavController(root).navigate(R.id.nav_home);
                    }
                    else {

                        showDialog("Registration Failed", authResultTask.getException().getMessage());
                        System.out.println(authResultTask.getException().getMessage());
                    }
                }
                else{ showDialog("Registration Failed", "Something went wrong");}
            }
        });

        return root;
    }
    private void showDialog(String title, String message){
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
