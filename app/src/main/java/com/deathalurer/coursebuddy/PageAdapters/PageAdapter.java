package com.deathalurer.coursebuddy.PageAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.deathalurer.coursebuddy.Fragments.Fragment_Course_Completed;
import com.deathalurer.coursebuddy.Fragments.Fragment_Course_Enrolled;

/**
 * Created by Abhinav Singh on 19,May,2020
 */
public class PageAdapter extends FragmentPagerAdapter {
    int tabs;
    public PageAdapter(@NonNull FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Fragment_Course_Enrolled fragment1 = new Fragment_Course_Enrolled();
                return  fragment1;
            case 1:
                Fragment_Course_Completed fragment2 = new Fragment_Course_Completed();
                return  fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
