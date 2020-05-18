package com.deathalurer.coursebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.deathalurer.coursebuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Created by Abhinav Singh on 10,May,2020
 */
public class Fragment_Profile_Edit extends Fragment {
    private static final String TAG = "Fragment_Profile_Edit";
    private EditText userName,userEmail,userPhone,userOldPassword,userNewPassword;
    private TextView userImage;
    private Button updateProfile;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String documentID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_edit_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        userName = view.findViewById(R.id.et_userName);
        userEmail = view.findViewById(R.id.et_userEmail);
        userPhone = view.findViewById(R.id.et_userPhone);
        userImage = view.findViewById(R.id.updatePicture);
        userOldPassword = view.findViewById(R.id.oldPassword);
        userNewPassword = view.findViewById(R.id.newPassword);
        updateProfile = view.findViewById(R.id.updateProfileButton);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user = mAuth.getCurrentUser();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document : task.getResult()){
                            //Log.d(TAG, document.getId()+" >> " + document.getString("UserUniqueID"));
                            if(document.getString("UserUniqueID").equals(user.getUid())){
                                documentID = document.getId();
                                userName.setText(document.getString("Username"));
                                userPhone.setText(document.getString("Phone Number"));
                                userEmail.setText(document.getString("Email"));
                            }
                        }
                    }
                });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,phone,oldPassword,newPassword;
                name = userName.getText().toString().trim();
                email = userEmail.getText().toString().trim();
                phone = userPhone.getText().toString().trim();
//                oldPassword = userOldPassword.getText().toString().trim();
//                newPassword = userNewPassword.getText().toString().trim();
                if(name.isEmpty() || email.isEmpty()){
                    Toast.makeText(getActivity(),"Fields can not be left empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    db.collection("Users").document(documentID)
                            .update(
                                    "Username",name,
                                    "Email",email,
                                    "Phone Number",phone)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(),"Profile Updated",Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });

    }
}
