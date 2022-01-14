package com.example.spotted.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.spotted.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * View pager adapter for Tablayout
 */
public class ViewPagerAdapter extends FragmentStateAdapter{
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private List<String> fragmentTitles = new ArrayList<String>();
    private List<Integer> fragmentIcons = new ArrayList<Integer>();

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    public List<Fragment> getFragments() {
        return fragments;
    }

    public CharSequence getPageTitle(int position)
    {
        return fragmentTitles.get(position);
        //return null;// display only the icon
    }
    public int getFragmentIcon(int position){
        return fragmentIcons.get(position);
    }
    public void addFragment(Fragment fragment, String title, int icon)
    {
        fragments.add(fragment);
        fragmentTitles.add(title);
        fragmentIcons.add(icon);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
