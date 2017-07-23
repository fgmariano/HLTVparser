package com.example.fern.hltvparser.extras;

import android.os.SystemClock;
import java.lang.ref.WeakReference;

import com.example.fern.hltvparser.MatchActivity;

/**
 * Created by Fern on 07/03/2017.
 */

public class MatchWorker extends Thread {
    private WeakReference<MatchActivity> activity;
    private String url;

    public MatchWorker(MatchActivity activity, String url) {
        this.activity = new WeakReference<>(activity);
        this.url = url;
    }

    @Override
    public void run() {
        super.run();

        while(activity.get() != null) {
            SystemClock.sleep(60000);
            new MatchRequest(activity.get(), url).execute();
        }
    }
}
