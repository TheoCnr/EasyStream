package main;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;

public class TheMovieDB {
    private final String API_URL = "https://api.themoviedb.org/3";
    private final String API_KEY = "e19c69ceca18e691746326be70001c3a";

    private ArrayList<Movie> moveList;
    
    private static TheMovieDB instance;
    
    public static TheMovieDB getIntance() {
        if (instance == null) {
            instance = new TheMovieDB();
        }
        return instance;
    }
    
    public void refresh() {
        try {
            HttpResponse<JsonNode> request = Unirest.get(this.API_URL + "/movie/popular")
                    .queryString("api_key", this.API_KEY)
                    .queryString("language", "fr")
                    .queryString("region", "FR")
                    .asJson();
            
            JSONObject responsejson = request.getBody().getObject();
            JSONArray results = responsejson.getJSONArray("results");
            moveList = new ArrayList<>();
                       
            for (int i = 0; i < results.length(); i++) {
                results.getJSONObject(i);
                 moveList.add(
                         new Movie(
                                 results.getJSONObject(i).optInt("id"),
                                 results.getJSONObject(i).getString("title"),
                                 results.getJSONObject(i).getString("poster_path"),
                                 results.getJSONObject(i).getString("overview")
                         )
                 );
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Movie search(String name, int year)
    {
        try {
            HttpResponse<JsonNode> request = Unirest.get(this.API_URL + "/search/movie")
                    .queryString("api_key", this.API_KEY)
                    .queryString("language", "fr")
                    .queryString("region", "FR")
                    .queryString("query", name)
                    .queryString("year", year)
                    .asJson();
            
            JSONObject responsejson = request.getBody().getObject();
            JSONArray results = responsejson.getJSONArray("results");
            Movie film = new Movie(
                results.getJSONObject(0).optInt("id"),
                results.getJSONObject(0).getString("title"),
                results.getJSONObject(0).getString("poster_path"),
                results.getJSONObject(0).getString("overview"));
            
            return film;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<Movie> getMovies() {
        return this.moveList;
    }
}