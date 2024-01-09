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
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.concurrent.TimeUnit;

public class Mobile {
    private Context context_;
    private AdView adView_;
    private AdRequest adRequest_;
    private Activity myActivity_;
    private static final String CHANNEL_ID = "MasterMind";
    private RewardedAd rewardedAd_;
    private RewardedAddEarned rewardEarnedMethod_;
    private InterstitialAd mInterstitialAd;

    public Mobile(Context c, Activity activity) {
        this.context_ = c;
        this.myActivity_ = activity;
        createNotificationChannel();
        MobileAds.initialize(this.context_, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        adRequest_ = new AdRequest.Builder().build();


        InterstitialAd.load(context_, "ca-app-pub-3940256099942544/1033173712", adRequest_,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("COJONES", "inter on Ad Loaded");
                        if(mInterstitialAd!=null)Log.d("COJONES", "VIVA LA VIDA");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d("COJONES", "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d("COJONES", "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e("COJONES", "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d("COJONES", "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d("COJONES", "Ad showed fullscreen content.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("COJONES", "No se ha cargado");
                        mInterstitialAd = null;
                    }
                });

        RewardedAd.load(context_, "ca-app-pub-3940256099942544/5224354917", adRequest_, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                //Log.d("MainActivity", loadAdError.toString());
                rewardedAd_ = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                rewardedAd_ = rewardedAd;
               // Log.d("MainActivity", "Rewarded ad was loaded.");
            }
        });
    }

    public void assignRewardPrice(RewardedAddEarned r) {
        this.rewardEarnedMethod_ = r;
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
                            rewardEarnedMethod_.OnRewardedEarned();
                        }
                    });
                } else {
                    System.out.println("The rewarded ad wasn't ready yet.");
                    Log.d("MainActivity", "The rewarded ad wasn't ready yet.");
                }
            }
        });
        loadNewRewardedAd();
    }
    public void LoadIntertitialAdd() {


        myActivity_.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(myActivity_) ;

                } else {
                    System.out.println("The rewarded ad wasn't ready yet.");
                    Log.d("COJONES", "The rewarded ad wasn't ready yet.");
                }
            }
        });
        loadNewLoadIntertitial();

    }
    private void loadNewLoadIntertitial() {
        myActivity_.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InterstitialAd.load(context_, "ca-app-pub-3940256099942544/1033173712", adRequest_,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                mInterstitialAd = interstitialAd;
                                Log.i("COJONES", "inter on Ad Loaded");
                                if(mInterstitialAd!=null)Log.d("COJONES", "VIVA LA VIDA");

                                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                    @Override
                                    public void onAdClicked() {
                                        // Called when a click is recorded for an ad.
                                        Log.d("COJONES", "Ad was clicked.");
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when ad is dismissed.
                                        // Set the ad reference to null so you don't show the ad a second time.
                                        Log.d("COJONES", "Ad dismissed fullscreen content.");
                                        mInterstitialAd = null;
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when ad fails to show.
                                        Log.e("COJONES", "Ad failed to show fullscreen content.");
                                        mInterstitialAd = null;
                                    }

                                    @Override
                                    public void onAdImpression() {
                                        // Called when an impression is recorded for an ad.
                                        Log.d("COJONES", "Ad recorded an impression.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when ad is shown.
                                        Log.d("COJONES", "Ad showed fullscreen content.");
                                    }
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                Log.d("COJONES", "No se ha cargado");
                                mInterstitialAd = null;
                            }
                        });

            }
        });
    }

    private void loadNewRewardedAd() {
        myActivity_.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RewardedAd.load(context_, "ca-app-pub-3940256099942544/5224354917", adRequest_,
                        new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                Log.d("MainActivity", loadAdError.toString());
                                rewardedAd_ = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                rewardedAd_ = rewardedAd;
                                Log.d("MainActivity", "Rewarded ad was loaded.");
                            }
                        });
            }
        });
    }

    public void processImage(Bitmap bitmap) {

        shareImage(bitmap, "¡He superado un nuevo nivel en Mastermind!");
    }
    //compartir una imagen
    public void shareImage(Bitmap bitmap, String msj) {
            String pathBitmap = MediaStore.Images.Media.insertImage(context_.getContentResolver(), bitmap, "titulo", "descripcion");
        if (bitmap != null && context_ != null && pathBitmap!=null) {
            Uri uri = Uri.parse(pathBitmap);
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, msj);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            this.myActivity_.startActivity(Intent.createChooser(shareIntent, "ActicityTitle"));
        }else
        {
            Log.w("SHARE", "Se ha producido un error al generar la captura");
        }
    }
    //se crea un notificacion Chnnel
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if(ContextCompat.checkSelfPermission(context_,Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED){
            myActivity_.requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 12345);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
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

    //Se programa una notificacion dado el tiempo,el timeUnit son si queremos minutos segundos o horas,el icono,
    //el titulo de la notificacion ,y el texto que queremos que apararezca
    public void programNotification(int time, TimeUnit timeUnit, int icon, String title, String firstText) {

        WorkRequest request = new OneTimeWorkRequest.Builder(ReminderWorker.class)
                .setInitialDelay(time, timeUnit)
                .setInputData(new Data.Builder()
                        .putString("title", title)
                        .putString("firstText", firstText)
                        .putInt("notifications_icon", icon)
                        .putString("notifications_channel_id", CHANNEL_ID)
                        .putString("package_name", myActivity_.getPackageName())
                        .build())
                .build();

        // Programa la tarea para ser ejecutada por el WorkManager
        WorkManager.getInstance(this.myActivity_.getApplicationContext()).enqueue(request);
    }

    public void sendWork(WorkRequest work) {
        WorkManager.getInstance(this.myActivity_.getApplicationContext()).enqueue(work);
    }
}
