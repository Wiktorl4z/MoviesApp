package pl.futuredev.popularmoviesudacitynd.utils;

import org.json.JSONException;

import pl.futuredev.popularmoviesudacitynd.pojo.Movies;

public class JsonUtils {

    public static Movies parseMoviesJson(String json) throws JSONException {

        final Movies movies = new Movies();
        return movies;
    }
}
