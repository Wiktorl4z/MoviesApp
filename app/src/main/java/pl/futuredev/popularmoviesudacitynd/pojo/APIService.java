package pl.futuredev.popularmoviesudacitynd.pojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET(UrlManager.POPULAR)
    Call<MovieList> getPopularMovies();

    @GET(UrlManager.TOP_RATED)
    Call<MovieList> getTopRatedMovies();

}
