package pl.futuredev.popularmoviesudacitynd.pojo;

import pl.futuredev.popularmoviesudacitynd.BuildConfig;

public class UrlManager {

    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String POPULAR = "3/movie/popular?api_key=" + API_KEY;
    public static final String TOP_RATED = "3/movie/top_rated?api_key=" + API_KEY;
    public static final String BASE_URL = "https://api.themoviedb.org";
    // https://api.themoviedb.org/3/movie/441614/images?api_key=
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780";

}
