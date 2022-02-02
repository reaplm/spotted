package com.example.spotted.ui.jobs;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotted.db.IJobDao;
import com.example.spotted.db.JobDao;
import com.example.spotted.models.Job;
import com.example.spotted.utils.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;

public class JobsViewModel extends ViewModel {
    private Context mContext;
    private IJobDao jobDao;
    private List<Job> likes;

    public JobsViewModel(Context context){
        mContext = context;
        jobDao = new JobDao();
        likes = PreferenceManager.getInstance(context).getLikes();
    }


    public LiveData<List<Job>> getJobs(){
        return jobDao.findAll();
    }
    public List<Job> getLikes() {
        return likes;
    }

}
