package com.example.spotted.db;

import androidx.lifecycle.LiveData;

import com.example.spotted.models.Job;

import java.util.List;

public interface IJobDao {
    LiveData<List<Job>> findAll();
}
