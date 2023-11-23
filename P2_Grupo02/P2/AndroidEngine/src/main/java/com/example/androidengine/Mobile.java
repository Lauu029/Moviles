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
