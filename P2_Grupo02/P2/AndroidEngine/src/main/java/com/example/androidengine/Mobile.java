package com.example.androidengine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Mobile {
    private Context context_;
    private AdView adView_;
    private AdRequest adRequest_;
    private Activity myActivity_;
    public Mobile(Context c, Activity activity) {
        this.context_ = c;
        this.myActivity_=activity;
       MobileAds.initialize(this.context_, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Log.d("MainActivity", "Starting");
            }
        });

        adRequest_ = new AdRequest.Builder().build();
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
    public void shareImage(Bitmap bitmap, String msj){
        String pathBitmap= MediaStore.Images.Media.insertImage(context_.getContentResolver(),bitmap,"titulo","descripcion");
        Uri uri= Uri.parse(pathBitmap);
        Intent shareIntent = new Intent(android.content.Intent. ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT,msj);
        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
        this.myActivity_.startActivity(Intent.createChooser(shareIntent,"ActicityTitle"));

    public void Init() {

    }

    public void LoadRewardedAd(){
        mainActivity.runOnUiThread(new Runnable() {
            @Override public void run() {
                if (mRewardedAd != null) {
                    mRewardedAd.show(mainActivity, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            //int rewardAmount = rewardItem.getAmount();
                            //String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    System.out.println("The rewarded ad wasn't ready yet.");
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });
    }
}
