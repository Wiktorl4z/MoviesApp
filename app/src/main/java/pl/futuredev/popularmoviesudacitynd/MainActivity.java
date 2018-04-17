package pl.futuredev.popularmoviesudacitynd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.futuredev.popularmoviesudacitynd.adapter.MovieAdapter;
import pl.futuredev.popularmoviesudacitynd.service.APIService;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.models.MovieList;
import pl.futuredev.popularmoviesudacitynd.service.HttpConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private APIService service;
    private MovieList movieList;
    private List<Movie> movie;
    private static final String CLASS_TAG = "TestActivity";
    static List<MovieList> items;
    private Toast toast;

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        service = HttpConnector.getService(APIService.class);
        popularMoviesListFromService();
    }

    private void popularMoviesListFromService() {
        service.getPopularMoviesList().enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                if (response.isSuccessful()) {
                    movie = response.body().results;
                    adapter = new MovieAdapter(movie, MainActivity.this::onClick);
                    recyclerView.setAdapter(adapter);
                } else {
                    try {
                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    };

    private void topRatedMoviesFromService() {
        service.getTopRatedMovies().enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                if (response.isSuccessful()) {
                    movie = response.body().results;
                    adapter = new MovieAdapter(movie, MainActivity.this::onClick);
                    recyclerView.setAdapter(adapter);
                } else {
                    try {
                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.top_rated:
                topRatedMoviesFromService();
                return true;
            case R.id.popular:
                popularMoviesListFromService();
                return true;
            case R.id.favourite:
            //    favouriteMoviesFromContentProvider();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

 /*   private void favouriteMoviesFromContentProvider() {
        movie = response.body().results;
        adapter = new MovieAdapter(movie, MainActivity.this::onClick);
        recyclerView.setAdapter(adapter);
    }*/

    @Override
    public void onClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", movie.get(clickedItemIndex));
        startActivity(intent);
    }
}