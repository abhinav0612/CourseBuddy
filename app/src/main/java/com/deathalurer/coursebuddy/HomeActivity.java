package com.deathalurer.coursebuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.deathalurer.coursebuddy.Fragments.Fragment_Home;
import com.deathalurer.coursebuddy.Fragments.Fragment_Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
        Map<String,String> user = new HashMap<>();
        user.put("Username",intent.getStringExtra(SignUp.USER_NAME));
        user.put("Phone Number",intent.getStringExtra(SignUp.USER_EMAIL));
        user.put("Email",intent.getStringExtra(SignUp.USER_PHONE));
        user.put("UserUniqueID",firebaseUser.getUid());

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
                switch (item.getItemId()){
                    case R.id.menu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Home()).commit();
                    case R.id.menu_course:
                        Toast.makeText(getBaseContext(),"Home",Toast.LENGTH_SHORT).show();
                    case R.id.menu_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment_Profile()).commit();
                }
                return false;
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }
}
