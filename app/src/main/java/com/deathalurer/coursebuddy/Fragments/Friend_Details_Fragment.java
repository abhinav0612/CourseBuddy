package com.deathalurer.coursebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deathalurer.coursebuddy.R;
import com.deathalurer.coursebuddy.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Abhinav Singh on 21,May,2020
 */
public class Friend_Details_Fragment extends Fragment {
    private static final String TAG = "Friend_Details_Fragment";
    private ImageView friendImage;
    private TextView friendName,friendCollegeName,friendBio,friendEnrolledCount,friendCompletedCount;
    private CardView viewFriendCertificates;
    private FirebaseFirestore db;
    private String friendId="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.friend_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        friendName = view.findViewById(R.id.friendUserName);
        friendCollegeName = view.findViewById(R.id.friendUserCollege);
        friendBio = view.findViewById(R.id.friendUserBio);
        friendImage = view.findViewById(R.id.friendUserImage);
        friendEnrolledCount = view.findViewById(R.id.friendEnrolled);
        friendCompletedCount = view.findViewById(R.id.friendCompleted);
        viewFriendCertificates = view.findViewById(R.id.friendCertificates);
        db = FirebaseFirestore.getInstance();
        friendId = getArguments().getString("UserId");

        db.collection("Users")
                .document(friendId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        friendName.setText(user.getUsername());
                        friendEnrolledCount.setText("Course Enrolled: " + user.getCourseEnrolled().size());
                        friendCompletedCount.setText("Course Completed: "+ user.getCourseCompleted().size());
                        Log.e(TAG, "" + user.getBio() + user.getCollege() );
                        friendBio.setText(documentSnapshot.getString("bio"));
                        friendCollegeName.setText(documentSnapshot.getString("college"));
                        Glide.with(getContext()).load(user.getProfileImage()).apply(RequestOptions.circleCropTransform())
                                .into(friendImage);
                    }
                });

        viewFriendCertificates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Certificate fragment = new Fragment_Certificate();
                Bundle bundle = new Bundle();
                bundle.putString("UserId",friendId);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
