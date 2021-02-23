package com.mystihgreeh.go4lunch.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mystihgreeh.go4lunch.R;

import java.util.Collections;

public class LoginActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 123;
    private final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initIdentification();

    }

    private void initIdentification(){
        SignInButton googleSignInButton = findViewById(R.id.google_button);
        LoginButton facebookSignInButton = findViewById(R.id.facebook_button);
        googleSignInButton.setOnClickListener(v -> startSignInWithGoogle());
        facebookSignInButton.setOnClickListener(v -> startSignInWithFacebook());
        // Start appropriate activity
        if (this.isCurrentUserLogged()){
            this.startMainActivity();
        }
    }

    // 3 - Launching Profile Activity
    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //SIGN IN WITH GOOGLE
    private void startSignInWithGoogle(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())) //GOOGLE
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }


    private void startSignInWithFacebook(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Collections.singletonList( new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build())) //GOOGLE
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }





    //GET USER
    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }


    @Override
    public void onResume() {
        super.onResume();
        initIdentification();
    }
}