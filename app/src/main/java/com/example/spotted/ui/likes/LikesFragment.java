package com.example.spotted.ui.likes;

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
import com.example.spotted.adapters.FavouritesAdapter;
import com.example.spotted.adapters.JobsAdapter;
import com.example.spotted.models.Job;

import java.util.List;

public class LikesFragment extends Fragment {

    private LikesViewModel likesViewModel;
    private RecyclerView rvJobs;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyRvText;
    private FavouritesAdapter favsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        likesViewModel =  new ViewModelProvider(this, new LikesViewModelFactory(requireActivity()))
                .get(LikesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_likes, container, false);

        rvJobs = root.findViewById(R.id.rv_jobs);
        rvJobs.setLayoutManager(new LinearLayoutManager(getActivity()));
        favsAdapter = new FavouritesAdapter(getActivity());
        rvJobs.setAdapter(favsAdapter);

        swipeRefreshLayout = root.findViewById(R.id.jobs_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJobsList();
            }
        });

        emptyRvText = root.findViewById(R.id.empty_rv_text);
        //Load data
        getJobsList();

        return root;
    }

    private void getJobsList() {
        swipeRefreshLayout.setRefreshing(true);


        likesViewModel.getLikes().observe(getViewLifecycleOwner(), new Observer<List<Job>>() {
            @Override
            public void onChanged(List<Job> jobs) {
                swipeRefreshLayout.setRefreshing(false);

                if(jobs.isEmpty()){
                    rvJobs.setVisibility(View.GONE);
                    emptyRvText.setVisibility(View.VISIBLE);
                }
                else{
                    //Load the data into recyclerview
                    favsAdapter.setData(jobs);
                    rvJobs.setVisibility(View.VISIBLE);
                    emptyRvText.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        likesViewModel.onResume();
    }
}