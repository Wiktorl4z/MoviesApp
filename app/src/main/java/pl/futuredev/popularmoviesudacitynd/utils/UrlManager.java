package pl.futuredev.popularmoviesudacitynd.utils;

import pl.futuredev.popularmoviesudacitynd.BuildConfig;

public class UrlManager {

    public static final String API_KEY = BuildConfig.API_KEY;
    // https://api.themoviedb.org/3/movie/popular?api_key
    public static final String POPULAR = "3/movie/popular?api_key=" + API_KEY;
    public static final String TOP_RATED = "3/movie/top_rated?api_key=" + API_KEY;
    public static final String BASE_URL = "https://api.themoviedb.org";
    // https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key
    public static final String VIDEOS_URL = "3/movie/{movie_id}/videos?api_key=" + API_KEY;
    // https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key
    public static final String REVIEW_URL = "3/movie/{movie_id}/reviews?api_key=" + API_KEY;
    // https://api.themoviedb.org/3/movie/441614/images?api_key=
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    public static final String REVIEW = "https://www.themoviedb.org/review/";
    // https://img.youtube.com/vi/<insert-youtube-video-id-here>/0.jpg
    public static final String THUMBNAIL = "https://img.youtube.com/vi/";


}
