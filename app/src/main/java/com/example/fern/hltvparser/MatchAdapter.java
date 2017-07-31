package com.example.fern.hltvparser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fern.hltvparser.domain.Match;

import java.util.List;

/**
 * Created by Fern on 07/03/2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private Context context;
    private List<Match> matches;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView team1;
        TextView team2;
        TextView score1;
        TextView score2;
        TextView map;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);

            team1 = (TextView) itemView.findViewById(R.id.team1);
            team2 = (TextView) itemView.findViewById(R.id.team2);
            score1 = (TextView) itemView.findViewById(R.id.score1);
            score2 = (TextView) itemView.findViewById(R.id.score2);
            map = (TextView) itemView.findViewById(R.id.map);
            date = (TextView) itemView.findViewById(R.id.date);
        }

        private void setData(Match match) {
            team1.setText(match.getTeam1());
            team2.setText(match.getTeam2());
            score1.setText(match.getScore1());
            score2.setText(match.getScore2());
            map.setText(match.getMap());
            date.setText(match.getDate());
        }
    }

    public MatchAdapter(Context context, List<Match> matches) {
        this.context = context;
        this.matches = matches;
    }

    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from( context )
                .inflate(R.layout.item_match, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(matches.get(position));
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}
