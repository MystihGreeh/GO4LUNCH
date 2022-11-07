package com.mystihgreeh.go4lunch.ui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.databinding.ActivitySettingsBinding;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.utils.NotificationWorker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SettingsActivity extends Activity {

    ActivitySettingsBinding binding;
    private static final int TIME_NOTIFICATION = 12;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        loadNotificationSettings();
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings(view);
                if (binding.notificationCheckbox.isChecked()) {
                    setNotificationForNoon();
                } else disableNotifications();
            }
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
                    .getBoolean("notifications", true);

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

        Toast.makeText(this,"Notifications Setting saved!",Toast.LENGTH_LONG).show();

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
                        .setInitialDelay(10, TimeUnit.SECONDS)
                        .addTag("Notification")
                        .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(notificationRequest);
    }

    /*public void setNotificationForNoon(){
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
    }*/

    public void disableNotifications(){
        WorkManager.getInstance().cancelAllWorkByTag("Notification");
    }
}