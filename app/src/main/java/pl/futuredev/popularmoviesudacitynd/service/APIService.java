package pl.futuredev.popularmoviesudacitynd.service;

import pl.futuredev.popularmoviesudacitynd.models.MovieList;
import pl.futuredev.popularmoviesudacitynd.models.Review;
import pl.futuredev.popularmoviesudacitynd.models.Trailer;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET(UrlManager.POPULAR)
    Call<MovieList> getPopularMoviesList();

    @GET(UrlManager.TOP_RATED)
    Call<MovieList> getTopRatedMovies();

    @GET(UrlManager.REVIEW_URL)
    Call<Review> getReview(@Path("movie_id") String id);

    @GET(UrlManager.VIDEOS_URL)
    Call<Trailer> getTrailer(@Path("movie_id") String id);

}
