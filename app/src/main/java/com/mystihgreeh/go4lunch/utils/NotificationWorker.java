package com.mystihgreeh.go4lunch.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.common.base.Joiner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;

import java.util.ArrayList;

public class NotificationWorker extends Worker {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference workmatesFirestore = db.collection("users");
    private Workmate currentUser;
    private String currentUserRestaurantId;
    private ArrayList<String> users;



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

    private void configureNotification(Workmate user, ArrayList<String> workmates) {
        createNotificationChannel();
        showNotification(user, workmates);

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


    private void showNotification(Workmate user, ArrayList<String> workmates) {

        String channelId = getApplicationContext().getString(R.string.time_to_eat);
        //Get current User

        if (user != null && user.getRestaurantName() != null && users.isEmpty()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.drawable.ic_baseline_fastfood_24)
                    .setContentTitle(getApplicationContext().getString(R.string.time_to_eat))
                    .setContentText(getApplicationContext().getString(R.string.today) + " " + currentUser.getRestaurantName())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(getApplicationContext().getString(R.string.today)
                            + " "    + currentUser.getRestaurantName() + ", " + currentUser.getRestaurantAddress()));
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            int NOTIFICATION_ID = 1;
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.drawable.ic_baseline_fastfood_24)
                    .setContentTitle(getApplicationContext().getString(R.string.time_to_eat))
                    .setContentText(getApplicationContext().getString(R.string.today) + " " + currentUser.getRestaurantName())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(getApplicationContext().getString(R.string.today) +
                                    " " + currentUser.getRestaurantName() + ", " + currentUser.getRestaurantAddress()
                            + " " + getApplicationContext().getString(R.string.with) + " " + convertListToString(workmates)));
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
                        currentUser = user;
                        currentUserRestaurantId = user.getRestaurantUid();
                        fetchUsers(currentUserRestaurantId);
                    }
                });

    }


    // ---------------------------------------------------------------------------------------------
    //                      ALL WORKMATES EATING IN THE SAME RESTAURANT AS USER
    // ---------------------------------------------------------------------------------------------


    private void fetchUsers(String restaurantId) {
        users = new ArrayList<>();
        workmatesFirestore.whereEqualTo("restaurantUid", restaurantId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<String> list = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        list.add(documentSnapshot.toObject(Workmate.class).getUsername());
                    }
                    users.addAll(list);
                    configureNotification(currentUser, users);});

    }

    public static String convertListToString(ArrayList<String> listString) {
        return Joiner.on(", ").join(listString);
    }


}
