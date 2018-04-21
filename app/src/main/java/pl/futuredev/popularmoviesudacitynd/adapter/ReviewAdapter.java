package pl.futuredev.popularmoviesudacitynd.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.futuredev.popularmoviesudacitynd.R;
import pl.futuredev.popularmoviesudacitynd.models.ReviewList;
import pl.futuredev.popularmoviesudacitynd.utils.UrlManager;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewList> reviewLists;
    private final ReviewAdapterOnClickHandler listClickHandler;

    public interface ReviewAdapterOnClickHandler {
        void onClick(int clickedItemIndex);
    }

    public ReviewAdapter(List<ReviewList> reviewLists, ReviewAdapterOnClickHandler listClickHandler) {
        this.reviewLists = reviewLists;
        this.listClickHandler = listClickHandler;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView reviewTextView;
        TextView reviewAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            this.reviewTextView = itemView.findViewById(R.id.tv_review);
            this.reviewAuthor = itemView.findViewById(R.id.tv_author_review);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            listClickHandler.onClick(clickPosition);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_review, parent, false);
        view.setFocusable(true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        TextView reviewTextView = holder.reviewTextView;
        TextView reviewAuthor = holder.reviewAuthor;

        String reviewUrl = UrlManager.REVIEW;
        String reviewText = reviewLists.get(listPosition).getContent();
        String author = reviewLists.get(listPosition).getAuthor();

        reviewTextView.setText(reviewText);
        reviewAuthor.setText(author);
    }

    @Override
    public int getItemCount() {
        return reviewLists.size();
    }
}