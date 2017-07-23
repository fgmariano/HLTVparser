package com.example.fern.hltvparser.extras;

import android.os.SystemClock;
import java.lang.ref.WeakReference;

import com.example.fern.hltvparser.EventActivity;

/**
 * Created by Fern on 06/03/2017.
 */

public class EventWorker extends Thread {
    private WeakReference<EventActivity> activity;

    public EventWorker(EventActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public void run() {
        super.run();

        while(activity.get() != null) {
            SystemClock.sleep(60000);
            new EventRequest(activity.get()).execute();
        }
    }
}
