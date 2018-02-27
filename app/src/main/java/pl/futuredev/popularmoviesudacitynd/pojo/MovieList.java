package pl.futuredev.popularmoviesudacitynd.pojo;

import java.util.List;

import com.squareup.moshi.Json;

public class MovieList {

    @Json(name = "page")
    private Integer page;
    @Json(name = "total_results")
    private Integer totalResults;
    @Json(name = "total_pages")
    private Integer totalPages;
    @Json(name = "results")
    private List<Movie> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
