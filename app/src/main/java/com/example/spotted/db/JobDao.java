package com.example.spotted.db;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotted.models.Job;
import com.example.spotted.services.FirebaseService;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JobDao implements IJobDao{

    FirebaseFirestore db;
    private IJobDao jobDao;

    public JobDao(){
        db = FirebaseService.getFirestore();
        jobDao = new JobDao();
    }


    @Override
    public LiveData<List<Job>> findAll() {
        MutableLiveData<List<Job>> data = new MutableLiveData<>();
        List<Job> jobs = new ArrayList<>();

        try{
            CollectionReference collection = db.collection("jobs");

            collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error);
                        return;
                    }

                    if (snapshot != null && !snapshot.isEmpty()) {

                        for (DocumentSnapshot doc : snapshot.getDocuments()) {
                            Job job = doc.toObject(Job.class);
                            job.setId(doc.getId());
                            jobs.add(job);
                        }

                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                    data.setValue(jobs);
                }
            });
        }
        catch(Exception ex){

        }
        return data;
    }
}
