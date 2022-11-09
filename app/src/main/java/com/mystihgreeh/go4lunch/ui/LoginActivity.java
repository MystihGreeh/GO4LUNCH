package com.mystihgreeh.go4lunch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mystihgreeh.go4lunch.R;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 123;
    private List<AuthUI.IdpConfig> providers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupProviders();
        initIdentification();
    }

    private void initIdentification() {
        ImageView loginButton = findViewById(R.id.main_activity_button_login);
        loginButton.setOnClickListener(v -> startSignIn());
        if (this.isCurrentUserLogged()) {
            this.startMainActivity();
        }
    }

    private void setupProviders(){
                providers = Arrays.asList(
                    new AuthUI.IdpConfig.TwitterBuilder().build(),
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
        );
    }
    //------------SIGN IN WITH GOOGLE - FACEBOOK - TWITTER AND EMAIL ----------------------

    private void startSignIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.FirebaseUiTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                startMainActivity();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }

        }
    }

    // ---------------------------------------------------------------------------------------

    // LAUNCHING MAIN ACTIVITY
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //GET USER
    @Nullable
    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    protected Boolean isCurrentUserLogged() {
        return (this.getCurrentUser() != null);
    }

}