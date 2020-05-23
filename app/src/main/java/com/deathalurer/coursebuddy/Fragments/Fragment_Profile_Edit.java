package com.deathalurer.coursebuddy.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Abhinav Singh on 10,May,2020
 */
public class Fragment_Profile_Edit extends Fragment {
    private static final String TAG = "Fragment_Profile_Edit";
    private EditText userName,userEmail,userPhone,userBio,userCollege;
    private TextView updateImage;
    private ImageView userImage;
    private Button updateProfile;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String documentID;
    private FirebaseStorage storage;
    private StorageReference storageReference,myReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_edit_fragment,container,false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        userName = view.findViewById(R.id.et_userName);
        userEmail = view.findViewById(R.id.et_userEmail);
        userImage = view.findViewById(R.id.profilePicture);
        userPhone = view.findViewById(R.id.et_userPhone);
        updateImage = view.findViewById(R.id.updatePicture);
        userCollege = view.findViewById(R.id.et_userCollege);
        userBio = view.findViewById(R.id.et_userBio);
        updateProfile = view.findViewById(R.id.updateProfileButton);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myReference = storageReference.child("images/"+ UUID.randomUUID().toString()+".jpg");

        user = mAuth.getCurrentUser();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document : task.getResult()){
                            if(document.getString("UserUniqueID").equals(user.getUid())){
                                documentID = document.getId();
                                userName.setText(document.getString("Username"));
                                userPhone.setText(document.getString("phoneNumber"));
                                userEmail.setText(document.getString("email"));
                                userBio.setText(document.getString("bio"));
                                userCollege.setText(document.getString("college"));
                            }
                        }
                    }
                });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,phone,bio,college;
                name = userName.getText().toString().trim();
                email = userEmail.getText().toString().trim();
                phone = userPhone.getText().toString().trim();
                college = userCollege.getText().toString().trim();
                bio = userBio.getText().toString().trim();
                if(name.isEmpty() || email.isEmpty()){
                    Toast.makeText(getActivity(),"Name and E-mail can not be left empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    db.collection("Users").document(documentID)
                            .update(
                                    "Username",name,
                                    "email",email,
                                    "phoneNumber",phone,
                                    "bio",bio,
                                    "college",college)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(),"Profile Updated",Toast.LENGTH_SHORT).show();
                                    getFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Profile())
                                            .commit();
                                }
                            });

                }
            }
        });

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data!=null && data.getData() !=null){
            userImage.setImageURI(data.getData());
            InputStream in = null;
            try {
                in = getActivity().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] uploadData = bos.toByteArray();

            UploadTask uploadTask = myReference.putBytes(uploadData);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i("_______"," " + taskSnapshot.getStorage().getPath());
//                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//
//                        }
//                    });
                    Toast.makeText(getActivity(),"Image Uploaded",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"Image Upload Failed",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
