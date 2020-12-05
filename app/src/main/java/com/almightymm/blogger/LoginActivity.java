package com.almightymm.blogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText emailAddressEditText, passwordEditText;
    private Button loginButton, createAccountButton;
    private ProgressBar loginProgressBar;

    //firebase

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddressEditText = findViewById(R.id.EmailAddress);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.Login);
        createAccountButton = findViewById(R.id.CreateAccount);
        loginProgressBar = findViewById(R.id.LoginProgress);


        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Login Button Clicked !!!");
                String email = emailAddressEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    loginProgressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.d(TAG, "onComplete: Login Successful !!!");
                                Toast.makeText(LoginActivity.this, "Login Successful !!!", Toast.LENGTH_SHORT).show();
                                sentToMain();
                            }
                            else {
                                String errorMessage = task.getException().getMessage();
                                Log.d(TAG, "onComplete: Login Failed !!!");
                                Log.d(TAG, "onComplete:  Error : "+ errorMessage);
                                Toast.makeText(LoginActivity.this, "Login Failed !!!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "Error : "+errorMessage, Toast.LENGTH_SHORT).show();
                            }
                            loginProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
        });
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        sentToMain();
    }

    private void sentToMain() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            Log.d(TAG, "onStart: User Logged in !!!");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
