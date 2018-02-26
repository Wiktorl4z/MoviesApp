package pl.futuredev.popularmoviesudacitynd;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpConnector {

    private static Retrofit retrofit;
    private static final String API_KEY = BuildConfig.API_KEY;

    private HttpConnector() {
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            return createRetrofit();
        }
        return retrofit;
    }

    public static <T> T getService(Class<T> clazz) {
        return getRetrofit().create(clazz);
    }

    @NonNull
    private static Retrofit createRetrofit() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClientBuilder.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=en-US&page=1")
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
