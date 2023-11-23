package com.example.androidengine;

import android.content.Context;
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

    public Mobile(Context c) {
        this.context_ = c;
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
