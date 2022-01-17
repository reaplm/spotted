package com.example.spotted.services;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseService {
    private static FirebaseFirestore FIRESTORE;

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
}
