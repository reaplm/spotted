package com.example.spotted.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private List<String> fragmentTitles = new ArrayList<String>();

    public ViewPagerAdapter(){
        super();
    }
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    public CharSequence getPageTitle(int position)
    {
        return fragmentTitles.get(position);
        //return null;// display only the icon
    }

    public void addFragment(Fragment fragment, String title)
    {
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

}
