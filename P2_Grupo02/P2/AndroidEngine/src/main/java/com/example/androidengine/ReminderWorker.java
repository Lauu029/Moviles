package com.example.androidengine;


import android.Manifest;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.util.Log;

        import androidx.annotation.NonNull;
        import androidx.core.app.ActivityCompat;
        import androidx.core.app.NotificationCompat;
        import androidx.core.app.NotificationManagerCompat;
        import androidx.work.Worker;
        import androidx.work.WorkerParameters;
//clase para que cuando el work acabe se creee la notificacion
public class ReminderWorker extends Worker {

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String title = getInputData().getString("title");
        String subtext = getInputData().getString("firstText");
        int notifications_icon = getInputData().getInt("notifications_icon", 1);
        String notifications_channel_id = getInputData().getString("notifications_channel_id");

        String package_name = getInputData().getString("package_name");

        Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(package_name);
        intent.putExtra("notification", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE );


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), notifications_channel_id)
                .setSmallIcon(notifications_icon)
                .setContentTitle(title)
                .setContentText(subtext)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(subtext)).
                setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        // notificationId is a unique int for each notification that you must define.
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling ActivityCompat#requestPermissions
            return Result.failure();
        }
        notificationManager.notify(1, builder.build());




        return Result.success();
    }
}