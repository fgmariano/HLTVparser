package com.example.fern.hltvparser.extras;

import android.os.AsyncTask;

import com.example.fern.hltvparser.EventActivity;
import com.example.fern.hltvparser.domain.Event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

            Element events = doc.getElementsByClass("ongoing-events-holder").first();
            try {
                Element bigEvents = events.getElementsByClass("big-events").first();
                for(Element el : bigEvents.getElementsByTag("a")) {
                    Event e = new Event();
                    String[] dates = getDates(el);
                    e.setEvent_name(el.getElementsByClass("big-event-name").text());
                    e.setEvent_url(el.attr("href"));
                    e.setEvent_start(dates[0]);
                    e.setEvent_end(dates[1]);
                    eventList.add(e);
                }
            } catch (Exception err) {

            }
            Element minor = events.getElementsByClass("ongoing-event-holder").first();
            for(Element el : minor.getElementsByTag("a")) {
                Event e = new Event();
                String[] dates = getDates(el);
                e.setEvent_name(el.getElementsByClass("text-ellipsis").text());
                e.setEvent_url(el.attr("href"));
                e.setEvent_start(dates[0]);
                e.setEvent_end(dates[1]);
                eventList.add(e);
            }
            return eventList;
        } catch(IOException e) {
            e.printStackTrace();
            return eventList;
        }
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        super.onPostExecute(events);

        if(activity.get() != null) {
            activity.get().updateLista(events);
        }
    }

    public String[] getDates(Element el) {
        String[] arr = new String[2];
        Elements span = el.getElementsByAttribute("data-unix");
        arr[0] = new SimpleDateFormat("dd/MM").format(Long.parseLong(span.get(0).attr("data-unix")));
        try {
            arr[1] = new SimpleDateFormat("dd/MM").format(Long.parseLong(span.get(1).attr("data-unix")));
        } catch(IndexOutOfBoundsException e) {
            arr[1] = "----";
        }
        return arr;
    }
}
