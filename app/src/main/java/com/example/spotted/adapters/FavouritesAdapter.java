package com.example.spotted.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotted.R;
import com.example.spotted.models.Job;
import com.example.spotted.utils.PreferenceManager;
import com.example.spotted.utils.RandomColorGenerator;


import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesAdapterViewHolder> {
    private List<Job> jobs;
    private Context context;
    private RecyclerView mRecyclerView;
    private RandomColorGenerator randomColorGenerator;

    public FavouritesAdapter(Context context)
    {
        this.context = context;
        randomColorGenerator = new RandomColorGenerator();
        jobs = new ArrayList<>();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public FavouritesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Setup your layout here
        int id = R.layout.job_item;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(id, parent, false);

        return new FavouritesAdapter.FavouritesAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesAdapterViewHolder holder, int position) {
        Job job = jobs.get(position);

        holder.title.setText(jobs.get(position).getTitle());
        holder.company.setText(jobs.get(position).getCompany());
        holder.location.setText(jobs.get(position).getLocation());
        holder.postedDate.setText(jobs.get(position).getPostedString());
        holder.closingDate.setText("Closes "+jobs.get(position).getClosingString());

        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.setTint(randomColorGenerator.getColor());
        holder.roundButton.setBackground(circle);
        holder.roundButton.setText(jobs.get(position).getTitle().substring(0, 1).toUpperCase());

        holder.likeCheckbox.setChecked(job.isLiked());
        holder.likeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    job.setLiked(true);
                    PreferenceManager.getInstance(context).addLike(job);
                }
                else{
                    job.setLiked(false);
                    jobs.remove(job);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, jobs.size());
                    PreferenceManager.getInstance(context).removeLike(job);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void setData(List<Job> data) {
        if (jobs != null) {
            jobs.clear();
            jobs.addAll(data);
            notifyDataSetChanged();

        } else {
            //First initialization
            jobs = data;
        }
    }


    public class FavouritesAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView company;
        private TextView location;
        private TextView postedDate;
        private TextView closingDate;
        private Button roundButton;
        private CheckBox likeCheckbox;

        public FavouritesAdapterViewHolder(View itemView) {
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