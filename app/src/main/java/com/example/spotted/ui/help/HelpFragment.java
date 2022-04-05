package com.example.spotted.ui.help;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotted.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HelpFragment extends Fragment {
    private HelpViewModel helpViewModel;
    private CheckBox designCheckbox;
    private CheckBox notifCheckbox;
    private CheckBox langCheckbox;
    private CheckBox adsCheckbox;
    private CheckBox subsCheckbox;
    private CheckBox otherCheckbox;
    private EditText bodyText;
    private FloatingActionButton attachmentFab;
    private Button feedbackSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        helpViewModel = new ViewModelProvider(getActivity()).get(HelpViewModel.class);

        designCheckbox = root.findViewById(R.id.help_design_chk);
        notifCheckbox = root.findViewById(R.id.help_notification_chk);
        langCheckbox = root.findViewById(R.id.help_language_chk);
        adsCheckbox = root.findViewById(R.id.help_ads_chk);
        subsCheckbox = root.findViewById(R.id.help_subscription_chk);
        otherCheckbox = root.findViewById(R.id.help_other_chk);
        bodyText = root.findViewById(R.id.help_feedback_edit);
        attachmentFab = root.findViewById(R.id.help_attacment_fab);
        feedbackSubmit = root.findViewById(R.id.feedback_submit_button);

        designCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    helpViewModel.addTopic(designCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
                else {
                    helpViewModel.removeTopic(designCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                }
            }
        });
        notifCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    helpViewModel.addTopic(notifCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
                else {
                    helpViewModel.removeTopic(notifCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                }
            }
        });
        langCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    helpViewModel.addTopic(langCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
                else {
                    helpViewModel.removeTopic(langCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                }
            }
        });
        adsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    helpViewModel.addTopic(adsCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
                else {
                    helpViewModel.removeTopic(adsCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                }
            }
        });
        subsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    helpViewModel.addTopic(subsCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
                else {
                    helpViewModel.removeTopic(subsCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                }
            }
        });
        otherCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    helpViewModel.addTopic(otherCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
                else {
                    helpViewModel.removeTopic(otherCheckbox.getText().toString());
                    compoundButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                }
            }
        });
        bodyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                helpViewModel.setBodyText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        feedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpViewModel.submit();
            }
        });

        return root;
    }
}
