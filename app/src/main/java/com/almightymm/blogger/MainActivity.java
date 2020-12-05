package com.almightymm.blogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private  FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Blogger");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: User not Logged in !!!");
        sendToLogin();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Logout:
                logout();
                return true;
            case R.id.settings:
                sendToSettings();
                return true;
            default:
                return false;
        }
    }

    private void logout() {
        Log.d(TAG, "logout: User Logged out !!!");
        firebaseAuth.signOut();
        sendToLogin();
    }
    private void sendToLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void sendToSettings() {
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
    }
}
