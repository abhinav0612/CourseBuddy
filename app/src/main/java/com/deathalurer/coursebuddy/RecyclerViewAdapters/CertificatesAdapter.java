package com.deathalurer.coursebuddy.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deathalurer.coursebuddy.Certificate;
import com.deathalurer.coursebuddy.R;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 19,May,2020
 */
public class CertificatesAdapter extends RecyclerView.Adapter<CertificatesAdapter.CertificateViewHolder> {
    private Context mContext;
    private ArrayList<Certificate> mList;

    public CertificatesAdapter(Context mContext, ArrayList<Certificate> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CertificateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.certificate_card,parent,false);
        return new CertificateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CertificateViewHolder holder, int position) {
        Certificate c = mList.get(position);
        holder.courseName.setText(c.getCourseName());
        holder.courseIssuer.setText(c.getCourseIssuer());
        holder.courseCredentials.setText(c.getCourseCredentials());
        Glide.with(mContext).load(c.getCourseImage()).into(holder.courseImage);

        holder.courseCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(holder.courseCredentials.getText().toString()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CertificateViewHolder extends RecyclerView.ViewHolder{
        private TextView courseName,courseIssuer,courseCredentials;
        private ImageView courseImage;

        public CertificateViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.certificateName);
            courseIssuer = itemView.findViewById(R.id.certificateIssuerName);
            courseCredentials = itemView.findViewById(R.id.certificateCredentials);
            courseImage = itemView.findViewById(R.id.certificateIssuerImage);
        }
    }
}
