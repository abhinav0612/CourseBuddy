package com.deathalurer.coursebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deathalurer.coursebuddy.AddCertificateDialog;
import com.deathalurer.coursebuddy.Certificate;
import com.deathalurer.coursebuddy.R;
import com.deathalurer.coursebuddy.RecyclerViewAdapters.CertificatesAdapter;
import com.deathalurer.coursebuddy.RecyclerViewAdapters.ReviewRecyclerAdapter;
import com.deathalurer.coursebuddy.Review;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 19,May,2020
 */
public class Fragment_Certificate extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private RecyclerView recyclerView;
    private ArrayList<Certificate> list;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.certificates_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewCertificate);
        fab = view.findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();

        getCertificates();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCertificateDialog dialog = new AddCertificateDialog();
                dialog.show(getChildFragmentManager(),"Dialog");
            }
        });

    }

    void getCertificates(){
        Bundle arguments = getArguments();
        if( arguments != null && getArguments().containsKey("UserId")){
            fab.setVisibility(View.INVISIBLE);
            db.collection("Users")
                    .document(arguments.getString("UserId"))
                    .collection("Certificates")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots != null){
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                    Certificate r = documentSnapshot.toObject(Certificate.class);
                                    list.add(r);
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                CertificatesAdapter adapter = new CertificatesAdapter(getContext(),list);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        else{
            fab.setVisibility(View.VISIBLE);
            db.collection("Users")
                    .whereEqualTo("UserUniqueID",user.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots!= null){
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                    db.collection("Users")
                                            .document(documentSnapshot.getId())
                                            .collection("Certificates")
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    if(queryDocumentSnapshots != null){
                                                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                                            Certificate r = documentSnapshot.toObject(Certificate.class);
                                                            list.add(r);
                                                        }
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                        CertificatesAdapter adapter = new CertificatesAdapter(getContext(),list);
                                                        recyclerView.setAdapter(adapter);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    });
        }

    }
}
