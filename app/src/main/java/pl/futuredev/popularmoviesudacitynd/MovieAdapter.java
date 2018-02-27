package pl.futuredev.popularmoviesudacitynd;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.futuredev.popularmoviesudacitynd.pojo.MovieList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private MovieList data;

    public MovieAdapter(MovieList items) {
        this.data = items;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitleName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewTitleName = (TextView) itemView.findViewById(R.id.tv_textView);
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

        TextView textViewMovieTitle = holder.textViewTitleName;
        textViewMovieTitle.setText("Title: " + data.get(listPosition).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
