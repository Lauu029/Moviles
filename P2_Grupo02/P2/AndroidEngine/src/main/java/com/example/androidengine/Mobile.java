package com.example.androidengine;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Mobile {
    private Context context_;
    private AdView adView_;
    private AdRequest adRequest_;
    private Activity myActivity_;
    private static final String CHANNEL_ID = "MasterMind";
    private RewardedAd rewardedAd_;

    public Mobile(Context c, Activity activity) {
        this.context_ = c;
        this.myActivity_ = activity;
        createNotificationChannel();
        MobileAds.initialize(this.context_, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Log.d("MainActivity", "Starting");
            }
        });
        adRequest_ = new AdRequest.Builder().build();
        RewardedAd.load(context_, "ca-app-pub-3940256099942544/5224354917", adRequest_, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d("MainActivity", loadAdError.toString());
                rewardedAd_ = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                rewardedAd_ = ad;
                Log.d("MainActivity", "Rewarded ad was loaded.");
            }
        });
    }

    public void loadAdBanner(AdView adView) {
        this.adView_ = adView;
        if (adView_ != null) {
            Log.d("MainActivity", "AdView found, loading ad...");
            adView_.loadAd(adRequest_);
            adView_.setVisibility(View.VISIBLE);
        } else {
            Log.e("MainActivity", "AdView is null");
        }
    }
    public void processImage(Bitmap bitmap) {
        // Aquí puedes realizar cualquier procesamiento adicional antes de compartir la imagen
        // Por ejemplo, mostrar la imagen en una ImageView
        // myImageView.setImageBitmap(bitmap);

        // Luego, puedes llamar al método shareImage para compartir la imagen
        System.out.println("Procesa imagen");
        shareImage(bitmap, "¡He superado un nuevo nivel en Mastermind!");
    }
    public void shareImage(Bitmap bitmap, String msj) {
        String pathBitmap = MediaStore.Images.Media.insertImage(context_.getContentResolver(), bitmap, "titulo", "descripcion");
        Uri uri = Uri.parse(pathBitmap);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, msj);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        this.myActivity_.startActivity(Intent.createChooser(shareIntent, "ActicityTitle"));
    }

    public void Init() {

    }

    public void LoadRewardedAd() {
        myActivity_.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rewardedAd_ != null) {
                    rewardedAd_.show(myActivity_, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d("MainActivity", "The user earned the reward.");
                            //int rewardAmount = rewardItem.getAmount();
                            //String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    System.out.println("The rewarded ad wasn't ready yet.");
                    Log.d("MainActivity", "The rewarded ad wasn't ready yet.");
                }
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context_.getString(R.string.channel_name);
            String description = context_.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = (NotificationManager) context_.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification(int icono) {

        Intent intent = new Intent(context_, myActivity_.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notification", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context_, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context_, CHANNEL_ID).
                setSmallIcon(icono).setContentTitle("MASTERMIND").
                setContentText("GANA 10 MONEDAS SI PULAS ESTA NOTIFICACION").
                setStyle(new NotificationCompat.BigTextStyle().bigText("Tienes muchos niveles que te esperan")).
                setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context_);

// notificationId is a unique int for each notification that you must define.
        if (ActivityCompat.checkSelfPermission(context_, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        notificationManager.notify(1, builder.build());
    }
    public void sendWork(WorkRequest work){
        WorkManager.getInstance(this.myActivity_.getApplicationContext()).enqueue(work);
    }
}
