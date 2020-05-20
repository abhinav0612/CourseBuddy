package com.deathalurer.coursebuddy.RecyclerViewAdapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deathalurer.coursebuddy.EnrolledStudent;
import com.deathalurer.coursebuddy.Fragments.Friend_Details_Fragment;
import com.deathalurer.coursebuddy.R;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 20,May,2020
 */
public class EnrolledStudentAdapter extends RecyclerView.Adapter<EnrolledStudentAdapter.StudentViewHolder> {
    private ArrayList<EnrolledStudent> studentList;
    private Context mContext;
    private FragmentManager fragmentManager;

    public EnrolledStudentAdapter(ArrayList<EnrolledStudent> studentList, Context mContext,FragmentManager fragmentManager) {
        this.studentList = studentList;
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.enrolled_student_item,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {
        holder.studentName.setText(studentList.get(position).getStudentName());
        holder.studentCollege.setText(studentList.get(position).getStudentCollege());
        holder.showStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Friend_Details_Fragment fragment = new Friend_Details_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("UserId",studentList.get(position).getStudentReference().getId());
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.frameLayout,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{
        private TextView studentName,studentCollege;
        private RelativeLayout showStudentDetails;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            studentCollege = itemView.findViewById(R.id.studentCollege);
            showStudentDetails = itemView.findViewById(R.id.studentItemLayout);

        }
    }
}
