package com.example.androidengine;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
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

    public void Init(Engine e) {
        //Activity main = iEngine_.getMainActivity();
        /*main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdView adView_= (AdView) main.findViewById(com.example.androidgame.R.id.adView);
                if (adView_ != null) {
                    Log.d("MainActivity", "AdView found, loading ad...");
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView_.loadAd(adRequest);
                    adView_.setVisibility(View.VISIBLE);
                } else {
                    Log.e("MainActivity", "AdView is null");
                }
            }
        });*/
    }
}
