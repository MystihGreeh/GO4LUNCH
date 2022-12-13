package com.mystihgreeh.go4lunch.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.databinding.ActivitySettingsBinding;
import com.mystihgreeh.go4lunch.utils.NotificationWorker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SettingsActivity extends Activity {

    ActivitySettingsBinding binding;
    private static final int TIME_NOTIFICATION = 12;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1;


    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        notificationPermission();
        loadNotificationSettings();
        binding.saveButton.setOnClickListener(view -> {
            saveSettings(view);
            if (binding.notificationCheckbox.isChecked()) {
                setNotificationForNoon();
            } else disableNotifications();
            finish();
        });

    }

    private void initView() {
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }


    // ---------------------------------------------------------------------------------------------
    //                                     SHARED PREFERENCES
    // ---------------------------------------------------------------------------------------------


    private void loadNotificationSettings(){
        SharedPreferences sharedPreferences= this
                .getSharedPreferences("notificationSettings", Context.MODE_PRIVATE);

        if(sharedPreferences!= null) {
            boolean notifications = sharedPreferences
                    .getBoolean("notifications", false);

            binding.notificationCheckbox.setChecked(notifications);



        } else {
            binding.notificationCheckbox.setChecked(false);
            Toast.makeText(this,"Use the default app setting",Toast.LENGTH_LONG).show();
        }
    }

    // Called when user click to Save button
    public void saveSettings(View view){
        SharedPreferences sharedPreferences= this
                .getSharedPreferences("notificationSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifications", binding.notificationCheckbox.isChecked());
        // Save.
        editor.apply();
        Toast.makeText(this,getApplicationContext().getString(R.string.setting_saved),Toast.LENGTH_LONG).show();

    }

    // ---------------------------------------------------------------------------------------------
    //                                 WORKMANAGER SET ON CHECKBOX CLICKED
    // ---------------------------------------------------------------------------------------------


    public void onCheckboxClicked(View view) {

    }


    public void setNotificationForNoon(){
        Calendar notificationTime = Calendar.getInstance();
        notificationTime.set(Calendar.HOUR_OF_DAY, TIME_NOTIFICATION);
        notificationTime.set(Calendar.MINUTE, 0);
        notificationTime.set(Calendar.MILLISECOND, 0);

        Calendar calendar = Calendar.getInstance();

        if (notificationTime.before(calendar)) {
            notificationTime.add(Calendar.HOUR_OF_DAY, 24);
        }

        long timeDiff = notificationTime.getTimeInMillis() - calendar.getTimeInMillis();

        WorkRequest notificationRequest =
                new PeriodicWorkRequest.Builder(NotificationWorker.class, 24, TimeUnit.HOURS)
                        .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                        .addTag("Notification")
                        .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(notificationRequest);
    }

    public void disableNotifications(){
        WorkManager.getInstance().cancelAllWorkByTag("Notification");
    }

    public void notificationPermission() {
        int locationPermissionCheck = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (Build.VERSION.SDK_INT >= 33) {
                locationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);
            }
        }

        if (locationPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,getApplicationContext().getString(R.string.nottification_denied),Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.notificationCheckbox.setChecked(false);
                if (Build.VERSION.SDK_INT >= 33) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
                }
            }
        } else {
            Toast.makeText(this,getApplicationContext().getString(R.string.nottification_granted),Toast.LENGTH_LONG).show();
        }

    }



}