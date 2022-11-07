package com.mystihgreeh.go4lunch.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.model.Workmates.WorkmateHelper;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;
import com.mystihgreeh.go4lunch.viewModel.SettingsViewModel;

import java.util.Calendar;

public class NotificationWorker extends Worker {

    private static final int[] TIME_NOTIFICATION = {12, 00};
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference workmatesFirestore = db.collection("users");



    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        getRestaurantUser(userId);
        return Result.success();
    }




    // ---------------------------------------------------------------------------------------------
    //                                     NOTIFICATION
    // ---------------------------------------------------------------------------------------------

    private void configureNotification(Workmate user) {
        createNotificationChannel();
        showNotification(user);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getApplicationContext().getString(R.string.time_to_eat);
            CharSequence name = getApplicationContext().getString(R.string.time_to_eat);
            String description = getApplicationContext().getString(R.string.restaurant_notification);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void showNotification(Workmate user) {

        String channelId = getApplicationContext().getString(R.string.time_to_eat);
        //Get current User

        if (user != null && user.getRestaurantName() != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.drawable.ic_baseline_fastfood_24)
                    .setContentTitle(getApplicationContext().getString(R.string.time_to_eat))
                    .setContentText("Today, you're eating at " + user.getRestaurantName() + " with")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Time to eat"));
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            int NOTIFICATION_ID = 1;
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }


    // ---------------------------------------------------------------------------------------------
    //                                     USER IN FIRESTORE
    // ---------------------------------------------------------------------------------------------

    public void getRestaurantUser(String userId) {
        workmatesFirestore.document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    Workmate user = documentSnapshot.toObject(Workmate.class);
                    if (user != null && user.getRestaurantUid() != null) {
                        configureNotification(user);
                    }
                });
    }







}
