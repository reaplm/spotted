package com.example.spotted.ui.home;

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
import androidx.viewpager2.widget.ViewPager2;

import com.example.spotted.R;
import com.example.spotted.adapters.ViewPagerAdapter;
import com.example.spotted.ui.alerts.AlertsFragment;
import com.example.spotted.ui.jobs.JobsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;

    //TabLayout Fragments
    private AlertsFragment alertsFragment;
    private JobsFragment jobsFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup TabLayout and view pager
        mTabLayout = (TabLayout)root.findViewById(R.id.tablayout);
        mViewPager = (ViewPager2)root.findViewById(R.id.viewpager);
        setupViewPager();

        return root;
    }

    /**
     * Setup viewpager for TabLayout
     */
    public void setupViewPager()
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());

        //Create fragments for each tab
        alertsFragment = new AlertsFragment();
        jobsFragment = new JobsFragment();

        //add fragments to adapter
        adapter.addFragment(alertsFragment, "Alerts", R.drawable.ic_action_notification);
        adapter.addFragment(jobsFragment, "Jobs", R.drawable.ic_action_work);

        mViewPager.setAdapter(adapter);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) ->{
            tab.setIcon(adapter.getFragmentIcon(position));
            tab.setText(adapter.getPageTitle(position));
        }).attach();

    }

}