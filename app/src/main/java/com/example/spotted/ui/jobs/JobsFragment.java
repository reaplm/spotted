package com.example.spotted.ui.jobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.spotted.R;
import com.example.spotted.adapters.JobsAdapter;
import com.example.spotted.models.Job;

import java.util.List;

public class JobsFragment  extends Fragment {
    private JobsViewModel jobsViewModel;
    private RecyclerView rvJobs;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyRvText;
    private JobsAdapter jobsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        jobsViewModel = new ViewModelProvider(this).get(JobsViewModel.class);

        View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        rvJobs = view.findViewById(R.id.rv_jobs);
        rvJobs.setLayoutManager(new LinearLayoutManager(getActivity()));
        jobsAdapter = new JobsAdapter(getActivity());
        rvJobs.setAdapter(jobsAdapter);

        swipeRefreshLayout = view.findViewById(R.id.jobs_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJobsList();
            }
        });

        emptyRvText = view.findViewById(R.id.empty_rv_text);
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
                    rvJobs.setVisibility(View.GONE);
                    emptyRvText.setVisibility(View.VISIBLE);
                }
                else{
                    //Load the data into recyclerview
                    jobsAdapter.setData(jobs);
                    rvJobs.setVisibility(View.VISIBLE);
                    emptyRvText.setVisibility(View.GONE);
                }
            }
        });
    }
}
