package com.deathalurer.coursebuddy;

import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Created by Abhinav Singh on 10,May,2020
 */
public class Fragment_Profile_Edit extends Fragment {
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
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userPhone = view.findViewById(R.id.userPhone);
        userImage = view.findViewById(R.id.updatePicture);
        updateProfile = view.findViewById(R.id.updateProfileButton);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user = mAuth.getCurrentUser();
        db.collection("Users")
                .whereEqualTo("UserUniqueID",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        documentID = task.getResult().getDocuments().get(0).getId();
                        userName.setText(task.getResult().getDocuments().get(0).get("Username").toString());
                        userEmail.setText(task.getResult().getDocuments().get(0).get("Email").toString());
                        userPhone.setText(task.getResult().getDocuments().get(0).get("Phone Number").toString());
                    }
                });

        final String name,email,phone,oldPassword,newPassword;
        name = userName.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        phone = userPhone.getText().toString().trim();
        oldPassword = userOldPassword.getText().toString().trim();
        newPassword = userNewPassword.getText().toString().trim();

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.isEmpty() || email.isEmpty()){
                    Toast.makeText(getActivity(),"Fields can not be left empty",Toast.LENGTH_SHORT).show();
                }
                else if(!newPassword.isEmpty()){

                }
                else {
                    db.collection("Users").document(documentID)
                            .update(
                                    "UserName",name,
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
