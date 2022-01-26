package com.example.spotted.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseService {
    private static FirebaseFirestore FIRESTORE;
    private static FirebaseAuth auth;

    public static FirebaseFirestore getFirestore() {
        if (FIRESTORE == null) {
            FIRESTORE = FirebaseFirestore.getInstance();

            //disable offline caching
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(false)
                    .build();
            FIRESTORE.setFirestoreSettings(settings);
        }

        return FIRESTORE;
    }
    public static FirebaseAuth getFirebaseAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }
    public static boolean isLoggedIn(){
        if(getFirebaseAuth().getCurrentUser() != null)
            return true;
        else
            return false;
    }

    public static void signOut(){
        getFirebaseAuth().signOut();
    }
}
