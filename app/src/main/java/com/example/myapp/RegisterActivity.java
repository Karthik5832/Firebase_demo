package com.example.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.utils.AlertDialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDoalog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextInputEditText user = findViewById(R.id.register_username_edit);
        TextInputEditText email = findViewById(R.id.register_email_edit);
        TextInputEditText password = findViewById(R.id.register_password_edit);
        Button button = findViewById(R.id.register_signUp_button_edit);
        TextView registerLogin = findViewById(R.id.alreadyUser_edit);
        ImageButton googleSignButton = findViewById(R.id.google_sign);
        ImageButton twitterSignButton = findViewById(R.id.twitter_sign);
        ImageButton facebookSignButton = findViewById(R.id.facebook_sign);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDoalog = new ProgressDialog(RegisterActivity.this);
        progressDoalog.setMessage("Please wait....");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = user.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if(userName.isBlank()){
                    AlertDialogUtils.showAlertDialog(RegisterActivity.this, RegisterActivity.this.getString(R.string.error_invalid_username));
                    Log.e("UserName", "Enter the user name");
                } else if (userEmail.isBlank()) {
                    AlertDialogUtils.showAlertDialog(RegisterActivity.this, RegisterActivity.this.getString(R.string.error_invalid_email));
                    Log.e("UserEmail", "Enter the email");
                } else if (userPassword.isBlank()) {
                    AlertDialogUtils.showAlertDialog(RegisterActivity.this, RegisterActivity.this.getString(R.string.error_invalid_password));
                    Log.e("UserPassword", "Enter the password");
                } else {
                    Log.e("User Name", userName);
                    Log.e("User Email", userEmail);
                    Log.e("User Password", userPassword);
                    progressDoalog.show();
                    register(userEmail, userPassword);

                }
            }
        });
        registerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        googleSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Google sign button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        twitterSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Twitter sign button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        facebookSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Facebook sign button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void register(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDoalog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    AlertDialogUtils.showAlertDialog(RegisterActivity.this, RegisterActivity.this.getString(R.string.error_register));
                }

            }
        });
    }
}
