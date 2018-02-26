package pl.futuredev.popularmoviesudacitynd.pojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebService {

    @GET("")
    Call<List<Movies>> getData();

}
