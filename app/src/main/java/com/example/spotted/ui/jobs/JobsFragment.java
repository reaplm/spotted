package com.example.spotted.ui.jobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.spotted.R;
import com.example.spotted.models.Job;

import java.util.List;

public class JobsFragment  extends Fragment {
    private JobsViewModel jobsViewModel;
    private RecyclerView rvJobs;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        jobsViewModel = new ViewModelProvider(this).get(JobsViewModel.class);

        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        rvJobs = view.findViewById(R.id.rv_jobs);
        swipeRefreshLayout = view.findViewById(R.id.jobs_swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJobsList();
            }
        });

        //Load data
        getJobsList();
        
        return view;
    }

    private void getJobsList() {
        swipeRefreshLayout.setRefreshing(true);

        jobsViewModel.getJobs().observe(getViewLifecycleOwner(), new Observer<List<Job>>() {
            @Override
            public void onChanged(List<Job> jobs) {
                swipeRefreshLayout.setRefreshing(false);

                if(jobs.isEmpty()){

                }
                else{
                    //Load the data into recyclerview
                }
            }
        });
    }
}
