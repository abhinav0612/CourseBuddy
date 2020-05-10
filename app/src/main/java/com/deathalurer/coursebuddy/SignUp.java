package com.deathalurer.coursebuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {
    private Button signUp,signUpWithGoogle;
    private TextView signIn;
    private EditText userName,userEmail,userPassword,userPhone;
    private FirebaseAuth mAuth;
    public static final String USER_NAME  = "UserName";
    public static final String USER_EMAIL  = "UserEmail";
    public static final String USER_PHONE  = "UserPhone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp = findViewById(R.id.signUpButton);
        signIn = findViewById(R.id.goToSignIn);
        userName = findViewById(R.id.signUpName);
        userEmail = findViewById(R.id.signUpEmail);
        userPhone = findViewById(R.id.signupPhone);
        userPassword = findViewById(R.id.signUpPassword);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = userName.getText().toString().trim();
                final String email = userEmail.getText().toString().trim();
                final String phone = userPhone.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                if(name.isEmpty() || email.isEmpty() || password.isEmpty())
                    Toast.makeText(getBaseContext(),"Please fill all details!",Toast.LENGTH_SHORT).show();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getBaseContext(), "Registration Successful",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(),HomeActivity.class);
                            if(!phone.isEmpty())
                                intent.putExtra(USER_PHONE,phone);
                            else
                                intent.putExtra(USER_PHONE,"");
                            intent.putExtra(USER_NAME,name);
                            intent.putExtra(USER_EMAIL,email);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
