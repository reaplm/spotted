package com.example.spotted.ui.jobs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.db.IJobDao;
import com.example.spotted.db.JobDao;
import com.example.spotted.models.Job;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;

public class JobsViewModel extends ViewModel {

    private IJobDao jobDao;

    public JobsViewModel(){
        jobDao = new JobDao();
    }


    public LiveData<List<Job>> getJobs(){
        return jobDao.findAll();
    }

}
