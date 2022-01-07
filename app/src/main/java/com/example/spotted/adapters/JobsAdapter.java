package com.example.spotted.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotted.R;
import com.example.spotted.models.Job;

import java.util.ArrayList;
import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobsAdapterViewHolder> {

    private List<Job> jobs;
    private Context mContext;

    public JobsAdapter(Context context){
        mContext = context;
        jobs = new ArrayList<>();
    }

    @NonNull
    @Override
    public JobsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.job_item, parent, false);

        return new JobsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsAdapterViewHolder holder, int position) {

        holder.title.setText(jobs.get(position).getTitle());
        holder.company.setText(jobs.get(position).getCompany());
        holder.location.setText(jobs.get(position).getLocation());
        holder.postedDate.setText(jobs.get(position).getPostedString());
        holder.closingDate.setText("Closes "+jobs.get(position).getClosingString());
        holder.roundButton.setText(jobs.get(position).getTitle().substring(0, 1).toUpperCase());
        holder.likeCheckbox.setChecked(jobs.get(position).isLiked());
        holder.likeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void setData(List<Job> data){
        if(jobs.isEmpty()){
            jobs = data;
        }
        else{
            jobs.clear();
            jobs.addAll(data);
        }
        notifyDataSetChanged();
    }

    public class JobsAdapterViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView company;
        private TextView location;
        private TextView postedDate;
        private TextView closingDate;
        private Button roundButton;
        private CheckBox likeCheckbox;


        public JobsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.job_item_title);
            company = itemView.findViewById(R.id.job_item_company);
            location = itemView.findViewById(R.id.job_item_location);
            postedDate = itemView.findViewById(R.id.job_item_posted_date);
            closingDate = itemView.findViewById(R.id.job_item_closing_date);
            roundButton = itemView.findViewById(R.id.job_item_round_btn);
            likeCheckbox = itemView.findViewById(R.id.job_item_like_btn);
        }
    }
}
