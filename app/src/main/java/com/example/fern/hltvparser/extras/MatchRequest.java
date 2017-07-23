package com.example.fern.hltvparser.extras;

import android.os.AsyncTask;

import com.example.fern.hltvparser.MatchActivity;
import com.example.fern.hltvparser.domain.Match;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fern on 07/03/2017.
 */

public class MatchRequest extends AsyncTask<Void, Void, List<Match>> {
    private WeakReference<MatchActivity> activity;
    private String url;

    public MatchRequest(MatchActivity activity, String url) {
        this.activity = new WeakReference<>(activity);
        this.url = url;
    }

    public String getRedirect(String url) {
        try {
            Response response = Jsoup.connect(url).followRedirects(true).execute();
            Document doc = Jsoup.connect(response.url().toString()).get();
            Elements results = doc.getElementsByClass("tab_unselected");
            for(Element e : results) {
                if(e.text().equals("Results")) {
                    return "http://www.hltv.org"+e.attr("href");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "Not found";
    }

    @Override
    protected List<Match> doInBackground(Void... voids) {
        Document doc = null;
        List<Match> matchList = new ArrayList<>();

        try {
            doc = Jsoup.connect(getRedirect(url)).get();
            Elements teams = doc.select("div[style=float:left;width:275px;]");
            Elements scores = doc.select("div[style=float:left;width:75px;text-align:center;font-weight:bold;]");
            Elements maps = doc.select("div[style=float:left;width:60px;text-align:center;font-weight:normal;]");

            for(int x = 1; x < teams.size(); x++) {
                Match match = new Match();
                Element team = teams.get(x);
                Element score = scores.get(x-1);
                Element map = maps.get(x-1);
                match.setTeam1(team.text().split(" vs ")[0]);
                match.setTeam2(team.text().split(" vs ")[1]);
                match.setScore1(score.text().split(" - ")[0]);
                match.setScore2(score.text().split(" - ")[1]);
                match.setMap(map.text());
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
}
