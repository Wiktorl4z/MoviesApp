package pl.futuredev.popularmoviesudacitynd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.futuredev.popularmoviesudacitynd.pojo.Movie;
import pl.futuredev.popularmoviesudacitynd.pojo.MovieList;
import pl.futuredev.popularmoviesudacitynd.pojo.UrlManager;
import retrofit2.http.Url;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> data;

    public MovieAdapter(List<Movie> items) {
        this.data = items;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        ImageView imageView = holder.imageView;

        String imageUrl = UrlManager.IMAGE_BASE_URL;
        String urlId = imageUrl + data.get(listPosition).getId();
        String urlIdWithKey = urlId + UrlManager.API_KEY;

        Context context = holder.imageView.getContext();

        Picasso.with(context).load(urlIdWithKey).into(imageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
