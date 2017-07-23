package com.example.fern.hltvparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import com.example.fern.hltvparser.domain.Match;
import com.example.fern.hltvparser.MatchAdapter;
import com.example.fern.hltvparser.extras.MatchWorker;
import com.example.fern.hltvparser.extras.MatchRequest;

import com.example.fern.hltvparser.domain.Event;

public class MatchActivity extends AppCompatActivity {
    private ArrayList<Match> matches;
    private MatchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        if(savedInstanceState != null) {
            matches = savedInstanceState.getParcelableArrayList(Match.MATCH_KEY);
            initViews();
            retrieveMatchesStream();
        } else {
            matches = new ArrayList<>();
            initViews();
            retrieveMatches();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Match.MATCH_KEY, matches);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        RecyclerView matchRecyclerView = (RecyclerView) findViewById(R.id.matchRecyclerView);
        matchRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        matchRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(this, mLayoutManager.getOrientation());
        matchRecyclerView.addItemDecoration(divider);

        adapter = new MatchAdapter(this, matches);
        matchRecyclerView.setAdapter(adapter);
    }

    private void retrieveMatches() {
        new MatchRequest(this, getIntent().getStringExtra("EVENT_URL")).execute();
        retrieveMatchesStream();
    }

    private void retrieveMatchesStream() {
        new MatchWorker(this, getIntent().getStringExtra("EVENT_URL")).start();
    }

    public void updateLista(List<Match> e) {
        matches.clear();
        matches.addAll(e);
        adapter.notifyDataSetChanged();
    }
}
