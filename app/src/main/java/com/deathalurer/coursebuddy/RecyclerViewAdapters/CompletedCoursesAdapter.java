package com.deathalurer.coursebuddy.RecyclerViewAdapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deathalurer.coursebuddy.Course;
import com.deathalurer.coursebuddy.Fragments.Fragment_Course;
import com.deathalurer.coursebuddy.R;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 18,May,2020
 */
public class CompletedCoursesAdapter extends RecyclerView.Adapter<CompletedCoursesAdapter.CoursesViewHolder> {
    private ArrayList<Course> mList;
    private Context mContext;
    private FragmentManager fragmentManager;


    public CompletedCoursesAdapter(ArrayList<Course> mList, Context mContext, FragmentManager fragmentManager) {
        this.mList = mList;
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view =  inflater.inflate(R.layout.course_item,parent,false);
        return new CoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        final Course course = mList.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.courseDescription.setText(course.getCourseDescription());
        holder.courseIssuer.setText(course.getCourseIssuer());
        Glide.with(mContext).load(course.getCourseImage()).into(holder.courseImage);
        holder.courseCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("CourseName",course.getCourseName());
                Fragment_Course fragment_course = new Fragment_Course();
                fragment_course.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment_course)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder{
        private CardView courseCardLayout;
        private ImageView courseImage;
        private TextView courseName,courseIssuer,courseDescription;

        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            courseCardLayout = itemView.findViewById(R.id.courseCardItem);
            courseImage = itemView.findViewById(R.id.courseIssuerImageCard);
            courseName = itemView.findViewById(R.id.courseNameCard);
            courseIssuer = itemView.findViewById(R.id.courseIssuerNameCard);
            courseDescription = itemView.findViewById(R.id.courseDescriptionCard);
        }
    }
}
