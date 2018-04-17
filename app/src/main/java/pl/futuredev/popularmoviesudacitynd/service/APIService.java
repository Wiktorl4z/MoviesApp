package pl.futuredev.popularmoviesudacitynd.service;

import pl.futuredev.popularmoviesudacitynd.models.MovieList;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET(UrlManager.POPULAR)
    Call<MovieList> getPopularMoviesList();

    @GET(UrlManager.TOP_RATED)
    Call<MovieList> getTopRatedMovies();

}