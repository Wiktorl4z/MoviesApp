package pl.futuredev.popularmoviesudacitynd;

import com.squareup.moshi.FromJson;

import pl.futuredev.popularmoviesudacitynd.pojo.Movie;
import pl.futuredev.popularmoviesudacitynd.pojo.MovieList;
import retrofit2.Response;

public class MovieListJsonAdapter {


    @FromJson
    Movie eventFromJson(MovieList eventJson) {
        Movie movie = new Movie();


        return movie;
    }

}
