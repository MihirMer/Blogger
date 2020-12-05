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

public class RegisterActivity extends AppCompatActivity {

    private EditText EmailEditText, passwordEditText, confPasswordEditText;
    private Button RegisterButton, alreadyHaveAccountButton;
    private ProgressBar RegProgressBar;
    private static final String TAG = "RegisterActivity";

    //firebase
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EmailEditText = findViewById(R.id.EmailAddress1);
        passwordEditText = findViewById(R.id.Password1);
        confPasswordEditText = findViewById(R.id.ConfPassword1);
        RegisterButton = findViewById(R.id.Reg);
        alreadyHaveAccountButton = findViewById(R.id.alreadyHaveAccoount);
        RegProgressBar = findViewById(R.id.RegProgressBar);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = EmailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confPassword = confPasswordEditText.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confPassword)){
                    if (password.equals(confPassword)){
                        RegProgressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    sendToMain();
                                }
                                else {
                                    String error=task.getException().getMessage();
                                    Log.d(TAG, "onComplete: Error : " +error);
                                    Toast.makeText(RegisterActivity.this, "Error : "+error, Toast.LENGTH_SHORT).show();
                                }
                                RegProgressBar.setVisibility(View.INVISIBLE);

                            }
                        });
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Password doesn't matched !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        super.onStart();
        sendToMain();
    }
    private void sendToMain() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, "onStart: User Logged in !!!");
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
