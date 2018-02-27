package pl.futuredev.popularmoviesudacitynd.pojo;

import pl.futuredev.popularmoviesudacitynd.BuildConfig;

public class UrlManager {

    static final String API_KEY = BuildConfig.API_KEY;
    public static final String POPULAR = "movie/popular?api_key="+API_KEY;
    public static final String TOP_RATED = "movie/top_rated?api_key="+API_KEY;
    public static final String BASE_URL = "https://api.themoviedb.org";

}
