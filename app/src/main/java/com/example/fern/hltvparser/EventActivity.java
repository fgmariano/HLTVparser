package com.example.fern.hltvparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.fern.hltvparser.domain.Event;
import com.example.fern.hltvparser.extras.EventRequest;
import com.example.fern.hltvparser.extras.EventWorker;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private ArrayList<Event> events;
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        if(savedInstanceState != null) {
            events = savedInstanceState.getParcelableArrayList(Event.EVENTS_KEY);
            initViews();
            retrieveEventsStream();
        } else {
            events = new ArrayList<>();
            initViews();
            retrieveEvents();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Event.EVENTS_KEY, events);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        final RecyclerView eventRecyclerView = (RecyclerView) findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        eventRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        eventRecyclerView.addItemDecoration(divider);

        adapter = new EventAdapter(this, events);
        eventRecyclerView.setAdapter(adapter);
    }

    private void retrieveEvents() {
        new EventRequest(this).execute();
        retrieveEventsStream();
    }

    private void retrieveEventsStream() {
        new EventWorker(this).start();
    }

    public void updateLista(List<Event> e) {
        events.clear();
        events.addAll(e);
        adapter.notifyDataSetChanged();
    }
}
