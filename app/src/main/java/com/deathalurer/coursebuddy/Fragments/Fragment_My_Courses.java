package com.deathalurer.coursebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.deathalurer.coursebuddy.PageAdapters.PageAdapter;
import com.deathalurer.coursebuddy.R;
import com.google.android.material.tabs.TabLayout;

/**
 * Created by Abhinav Singh on 19,May,2020
 */
public class Fragment_My_Courses extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_courses_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.coursesTabLayout);
        viewPager = view.findViewById(R.id.coursesViewPager);

        initializeTabLayoutWithViewPager();

    }
    void initializeTabLayoutWithViewPager(){
        tabLayout.addTab(tabLayout.newTab().setText("Enrolled"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        adapter = new PageAdapter(getFragmentManager(),tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        getChildFragmentManager().beginTransaction().replace(R.id.childFrameLayout,new Fragment_Course_Enrolled())
                .addToBackStack(null)
                .commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        getChildFragmentManager().beginTransaction().replace(R.id.childFrameLayout,new Fragment_Course_Enrolled())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        getChildFragmentManager().beginTransaction().replace(R.id.childFrameLayout,new Fragment_Course_Completed())
                                .commit();
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //getFragmentManager().
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("Method","On resume my courses fragment");
       //initializeTabLayoutWithViewPager();
//        getFragmentManager().beginTransaction()
//                .replace(R.id.frameLayout,new Fragment_Course_Enrolled())
//                .commit();
    }
}
