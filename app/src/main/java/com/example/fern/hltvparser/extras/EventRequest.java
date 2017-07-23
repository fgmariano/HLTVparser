package com.example.fern.hltvparser.extras;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.example.fern.hltvparser.EventActivity;
import com.example.fern.hltvparser.domain.Event;

/**
 * Created by Fern on 06/03/2017.
 */

public class EventRequest extends AsyncTask<Void, Void, List<Event>> {
    private WeakReference<EventActivity> activity;

    public EventRequest(EventActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected List<Event> doInBackground(Void... voids) {
        Document doc = null;
        List<Event> eventList = new ArrayList<>();

        try {
            doc = Jsoup.connect("http://www.hltv.org/events/ongoing/").get();
            Element events = doc.getElementsByClass("hotmatchbox").first();
            Elements event = events.select("div[style=padding:5px]");
            for(Element eventDetails : event) {
                Event e = new Event();
                Element eventTitle = eventDetails.getElementsByClass("eventheadline").first();
                Element link = eventTitle.getElementsByTag("a").first();
                e.setEvent_name(link.text());
                e.setEvent_url("http://hltv.org"+link.attr("href"));

                Element eventStart = eventDetails.getElementsByClass("values").get(0);
                e.setEvent_start(eventStart.text());

                Element eventEnd = eventDetails.getElementsByClass("values").get(1);
                e.setEvent_end(eventEnd.text());

                eventList.add(e);
            }
            return eventList;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        super.onPostExecute(events);

        if(activity.get() != null) {
            activity.get().updateLista(events);
        }
    }
}
