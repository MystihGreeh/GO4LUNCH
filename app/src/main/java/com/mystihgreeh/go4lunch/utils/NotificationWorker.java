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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.model.Workmates.WorkmateHelper;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;
import com.mystihgreeh.go4lunch.viewModel.SettingsViewModel;

import java.util.Calendar;

public class NotificationWorker extends Worker {

    private static final int[] TIME_NOTIFICATION = {12, 00};
    private PendingIntent pendingIntentAlarm;
    private SettingsViewModel settingsViewModel;
    private WorkmateHelper workmateHelper;
    private WorkmatesRepository mWorkmateRepository;
    private static Workmate user;
    private String restaurantUser;
    String userUid;



    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        configureNotification();
        return Result.success();
    }




    // ---------------------------------------------------------------------------------------------
    //                                     NOTIFICATION
    // ---------------------------------------------------------------------------------------------

    private void configureNotification() {
        createNotificationChannel();
        showNotification(getApplicationContext());
        //enableNotifications();
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


    private void enableNotifications() {
        Calendar notificationTime = Calendar.getInstance();
        notificationTime.set(Calendar.HOUR_OF_DAY, TIME_NOTIFICATION[0]);
        notificationTime.set(Calendar.MINUTE, TIME_NOTIFICATION[1]);
        notificationTime.set(Calendar.MILLISECOND, 0);

        Calendar calendar = Calendar.getInstance();
        if (notificationTime.before(calendar)) {
            notificationTime.add(Calendar.DATE, 1);
        }
        PackageManager pm = getApplicationContext().getPackageManager();

        AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, notificationTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentAlarm);
    }

    private void showNotification(Context context) {


        String channelId = context.getString(R.string.time_to_eat);
        //Get current User
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        getRestaurantUser(userId);

        if (restaurantUser != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_baseline_fastfood_24)
                    .setContentTitle(context.getString(R.string.time_to_eat))
                    .setContentText("Today, you're eating at ")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Time to eat"));
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            int NOTIFICATION_ID = 1;
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else {

        }
    }

    public void getRestaurantUser(String userId) {
        workmateHelper.workmatesFirebase.document(userId).get()
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful ()) {
                        if (task.getResult().exists()){
                            restaurantUser = getActualUser().getRestaurantUid();
                        }

                    }
                });
    }

    public Workmate getActualUser() {
        return user;
    }



}
