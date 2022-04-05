package com.example.spotted.ui.help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.services.FirebaseService;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HelpViewModel extends ViewModel {
    private String bodyText;
    private List<String> topics;
    private String email;

    public HelpViewModel(){
        topics = new ArrayList<>();
        if(FirebaseService.isLoggedIn()){
            String email = FirebaseService.getFirebaseAuth()
                    .getCurrentUser().getEmail();

        }
    }
    public void setBodyText(String text){
        bodyText = text;
    }
    public void addTopic(String topic){
        topics.add(topic);
    }
    public void removeTopic(String topic){
        topics.remove(topics.indexOf(topic));
    }
    public void submit(){
        //Get user email

    }
}
