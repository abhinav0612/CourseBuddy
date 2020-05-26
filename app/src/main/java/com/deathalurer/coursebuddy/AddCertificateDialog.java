package com.deathalurer.coursebuddy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Abhinav Singh on 24,May,2020
 */
public class AddCertificateDialog extends AppCompatDialogFragment {
    private TextView add,cancel;
    private EditText name_et,credentials_et;
    private Spinner issuer;
    private String issuerName = "",imageUrl="";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser mUser;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        View view = inflater1.inflate(R.layout.add_cetificate_dialog,null);
        builder.setView(view);

        add = view.findViewById(R.id.addButton);
        cancel = view.findViewById(R.id.cancelButton);
        name_et = view.findViewById(R.id.addCertificateNameEt);
        credentials_et = view.findViewById(R.id.addCertificateCredentialsEt);
        issuer = view.findViewById(R.id.addCertificateCredentialsSpinner);

        initSpinner();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name,credentials;
                name = name_et.getText().toString().trim();
                credentials = credentials_et.getText().toString().trim();
                if (name.isEmpty() || credentials .isEmpty() || issuerName.isEmpty()){
                    Toast.makeText(getContext(),"Fill all details.",Toast.LENGTH_SHORT).show();
                }
                else {
                    db = FirebaseFirestore.getInstance();
                    mAuth = FirebaseAuth.getInstance();
                    mUser = mAuth.getCurrentUser();
                    db.collection("Users")
                            .whereEqualTo("UserUniqueID",mUser.getUid())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    Certificate certificate = new Certificate(name,issuerName,credentials,imageUrl);
                                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                    documentSnapshot.getReference().collection("Certificates")
                                            .add(certificate)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(getActivity(),"Certificated Added.",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    dismiss();
                                }
                            });
                }
            }
        });

        return  builder.create();
    }

    void initSpinner(){
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.issuer,
//                android.R.layout.simple_spinner_item);
        ArrayList<String> issuerList = new ArrayList<>();
        issuerList.add("Select Issuer");
        issuerList.add("Coursera");
        issuerList.add("EDX");
        issuerList.add("Udacity");
        issuerList.add("Udemy");


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,issuerList){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issuer.setAdapter(adapter1);
        issuer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                issuerName = issuer.getItemAtPosition(position).toString();
                switch (issuerName){
                    case "Coursera":
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/course-buddy.appspot.com/o/coursera.jfif?alt=media&token=f8f327d5-189f-4d51-89ad-c125e6d564f9";
                        break;
                    case "Udemy":
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/course-buddy.appspot.com/o/udacity.png?alt=media&token=b5b21051-5f99-4b92-8544-2ad68bdca8ad";
                        break;
                    case "EDX":
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/course-buddy.appspot.com/o/edx.png?alt=media&token=3dcff9fa-d380-47fd-982b-7bf117966c5a";
                        break;
                    case "Udacity":
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/course-buddy.appspot.com/o/udacity.png?alt=media&token=b5b21051-5f99-4b92-8544-2ad68bdca8ad";
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
