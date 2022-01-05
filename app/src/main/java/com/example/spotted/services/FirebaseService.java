package com.example.spotted.services;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseService {
    private static FirebaseFirestore FIRESTORE;

    public static FirebaseFirestore getFirestore() {
        if (FIRESTORE == null) {
            FIRESTORE = FirebaseFirestore.getInstance();
        }

        return FIRESTORE;
    }
}
