package com.example.fern.hltvparser.extras;

import android.os.AsyncTask;

import com.example.fern.hltvparser.MatchActivity;
import com.example.fern.hltvparser.domain.Match;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fern on 07/03/2017.
 */

public class MatchRequest extends AsyncTask<Void, Void, List<Match>> {
    private WeakReference<MatchActivity> activity;
    private String url;
    private final String RESULT = "https://www.hltv.org/results?event=";

    public MatchRequest(MatchActivity activity, String url) {
        this.activity = new WeakReference<>(activity);
        this.url = url;
    }

    @Override
    protected List<Match> doInBackground(Void... voids) {
        Document doc = null;
        List<Match> matchList = new ArrayList<>();
        try {
            doc = Jsoup.connect(RESULT+getEventId(url)).get();

            Elements results = doc.getElementsByClass("result-con");
            for(Element e : results) {
                Match match = new Match();
                match.setTeam1(e.getElementsByClass("team1").first().text());
                match.setTeam2(e.getElementsByClass("team2").first().text());
                Elements result = e.getElementsByClass("result-score").first().getElementsByTag("span");
                match.setScore1(result.get(0).text());
                match.setScore2(result.get(1).text());
                match.setDate(e.getElementsByClass("date-cell").first().text());
                match.setMap(getMap(e.getElementsByClass("map-text").first().text()));
                matchList.add(match);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return matchList;
    }

    @Override
    protected void onPostExecute(List<Match> matches) {
        super.onPostExecute(matches);

        if(activity.get() != null) {
            activity.get().updateLista(matches);
        }
    }

    public String getMap(String map) {
        switch (map) {
            case "inf":
                return "Inferno";
            case "cch":
                return "Cache";
            case "mrg":
                return "Mirage";
            case "ovp":
                return "Overpass";
            case "cbl":
                return "Cobblestone";
            case "trn":
                return "Train";
            case "nuke":
                return "Nuke";
            default:
                return "Unknown";
        }
    }

    public String getEventId(String url) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()) {
            return matcher.group(0);
        } else {
            return "0";
        }
    }
}
