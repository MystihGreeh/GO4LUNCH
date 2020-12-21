package com.mystihgreeh.go4lunch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
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