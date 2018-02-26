package pl.futuredev.popularmoviesudacitynd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.futuredev.popularmoviesudacitynd.pojo.Movies;
import pl.futuredev.popularmoviesudacitynd.pojo.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private WebService service;
    private Movies movies;
    private static final String CLASS_TAG = "TestActivity";
    static List<Movies> items;

    @BindView(R.id.button_test)
    Button buttonTest;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        movies = null;
        service = HttpConnector.getService(WebService.class);

        buttonTest.setOnClickListener(v -> {
            try {
                service.getData().enqueue(new Callback<List<Movies>>() {
                    @Override
                    public void onResponse(Call<List<Movies>> call, Response<List<Movies>> response) {
                        items = response.body();

                        adapter = new MovieAdapter(items);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Movies>> call, Throwable t) {
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
}
