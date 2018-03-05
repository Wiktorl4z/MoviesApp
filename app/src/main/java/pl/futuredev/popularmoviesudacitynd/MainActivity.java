package pl.futuredev.popularmoviesudacitynd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.futuredev.popularmoviesudacitynd.models.Movie;
import pl.futuredev.popularmoviesudacitynd.models.MovieList;
import pl.futuredev.popularmoviesudacitynd.models.APIService;
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

    @BindView(R.id.button_test)
    Button buttonTest;
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

        buttonTest.setOnClickListener((View v) -> {
            try {
                service.getPopularMoviesList().enqueue(new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                        movie = response.body().results;

                        adapter = new MovieAdapter(movie, MainActivity.this::onClick);
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<MovieList> call, Throwable t) {
                        Log.d(CLASS_TAG, t.getLocalizedMessage());
                    }
                });
            } catch (Exception e) {
                Log.d(CLASS_TAG, e.toString());
            }
        });

    }


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
                return true;
            case R.id.popular:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.button_test)
    public void onViewClicked() {
    }


    @Override
    public void onClick(int clickedItemIndex) {
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        toast.show();
    }
}
