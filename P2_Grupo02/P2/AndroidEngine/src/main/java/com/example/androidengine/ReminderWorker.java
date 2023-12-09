package com.example.androidengine;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ReminderWorker extends Worker {
     Mobile mobile;
    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters params,Mobile mobile) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        mobile.createNotification(0);
        Log.d("GAME","HOLAAAAAAA K LOKO");
        return Result.success();
    }

}