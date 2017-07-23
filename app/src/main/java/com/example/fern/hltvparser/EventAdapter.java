package com.example.fern.hltvparser;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fern.hltvparser.domain.Event;

import java.util.List;

/**
 * Created by Fern on 06/03/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private Context context;
    private List<Event> events;

    public EventAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView event_name;
        TextView event_start;
        TextView event_end;

        ViewHolder(View itemView) {
            super(itemView);

            event_name = (TextView) itemView.findViewById(R.id.event_name);
            event_start = (TextView) itemView.findViewById(R.id.event_start);
            event_end = (TextView) itemView.findViewById(R.id.event_end);
        }

        private void setData(Event event) {
            event_name.setText(event.getEvent_name());
            event_start.setText(event.getEvent_start());
            event_end.setText(event.getEvent_end());
        }
    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from( context )
                .inflate(R.layout.item_event, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(events.get(position));
        holder.event_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MatchActivity.class);
                intent.putExtra("EVENT_URL", events.get(position).getEvent_url());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, events.get(position).getEvent_name(), Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
