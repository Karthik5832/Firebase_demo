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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);

        TextInputEditText emailUsername_edit = findViewById(R.id.email_username_edit);
        TextInputEditText password_edit = findViewById(R.id.password_edit);
        Button login = findViewById(R.id.login_button_edit);
        TextView signUp = findViewById(R.id.signUp_edit);
        ImageButton googleSignButton = findViewById(R.id.google_sign);
        ImageButton twitterSignButton = findViewById(R.id.twitter_sign);
        ImageButton facebookSignButton = findViewById(R.id.facebook_sign);
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = emailUsername_edit.getText().toString();
                String passWord = password_edit.getText().toString();

                if(emailUser.isBlank()){
                    Log.e("Email/userName", "Enter the email/username");
                    AlertDialogUtils.showAlertDialog(LoginActivity.this, LoginActivity.this.getString(R.string.error_invalid_email));
                } else if (passWord.isBlank()) {
                    Log.e("Password","Enter the password");
                    AlertDialogUtils.showAlertDialog(LoginActivity.this,LoginActivity.this.getString(R.string.error_invalid_password));
                } else{
                    Log.e("Email/UserName", emailUser);
                    Log.e("Password", passWord);
                    progressDialog.show();
                    loginWithEmailAndPassword(emailUser, passWord);

                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        googleSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Google sign button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        twitterSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Twitter sign button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        facebookSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Facebook sign button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loginWithEmailAndPassword(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    AlertDialogUtils.showAlertDialog(LoginActivity.this, LoginActivity.this.getString(R.string.error_login));
                }
            }
        });
    }
}
