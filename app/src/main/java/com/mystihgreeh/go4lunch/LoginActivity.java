package com.mystihgreeh.go4lunch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;
import java.util.Collections;

public class LoginActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 123;
    private SignInButton googleSignInButton;
    private FirebaseAuth mAuth;
    private String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleSignInButton = findViewById(R.id.google_button);
        mAuth = FirebaseAuth.getInstance();

        googleSignInButton.setOnClickListener(v -> startSignInWithGoogle());

    }

    //SIGN IN WITH GOOGLE
    private void startSignInWithGoogle(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())) //GOOGLE
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }


}