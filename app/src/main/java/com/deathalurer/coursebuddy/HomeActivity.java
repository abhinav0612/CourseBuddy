package com.deathalurer.coursebuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.deathalurer.coursebuddy.Fragments.Fragment_Home;
import com.deathalurer.coursebuddy.Fragments.Fragment_My_Courses;
import com.deathalurer.coursebuddy.Fragments.Fragment_Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity" ;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout layout;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Home()).commit();

        Intent intent = getIntent();
        if (intent.hasExtra(SignUp.USER_NAME)){
        Map<String,Object> user = new HashMap<>();
        user.put("Username",intent.getStringExtra(SignUp.USER_NAME));
        user.put("phoneNumber",intent.getStringExtra(SignUp.USER_EMAIL));
        user.put("email",intent.getStringExtra(SignUp.USER_PHONE));
        user.put("UserUniqueID",firebaseUser.getUid());
        user.put("college",intent.getStringExtra(SignUp.USER_COLLEGE));

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }
        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        }


//        for(int i =0; i< 10;i++){
//            Map<String,Object> map = new HashMap<>();
//            map.put("CourseName","Web Development");
//            map.put("CourseDescription","This is description.");
//            map.put("CourseIssuer","Udacity");
//            map.put("CourseEnrolledStudent","12");
//            map.put("CourseImage"," ");
//
//            db.collection("Courses")
//                    .add(map)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error adding document", e);
//                        }
//                    });
//        }

        BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.menu_home:
                            fragment = new Fragment_Home();
                            break;
                    case R.id.menu_course:
                        fragment = new Fragment_My_Courses();
                        break;
                    case R.id.menu_profile:
                        fragment = new Fragment_Profile();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
                return  true;
            }
        };


        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2  && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.e("______","Granted");
        }
    }
}
