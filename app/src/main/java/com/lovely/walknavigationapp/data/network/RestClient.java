package com.lovely.walknavigationapp.data.network;

import com.lovely.walknavigationapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Rest Client for android
 */
public final class RestClient {

    private static Retrofit retrofit = null;
    private static Retrofit googleWebServices = null;

    /**
     * Prevent instantiation
     */
    private RestClient() {
    }

    /**
     * Gets retrofit builder.
     *
     * @return object of Retrofit
     */
    static Retrofit getRetrofitBuilder() {


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.GOOGLE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient().build())
                    .build();
        }
        return retrofit;
    }

    /**
     * Gets google Api interface.
     *
     * @return object of google com.lovely.walknavigationapp.data.network.ApiInterface
     */
    public static ApiInterface getGoogleApiInterface() {
        if (googleWebServices == null) {

            try {
                googleWebServices = new Retrofit.Builder()
                        .baseUrl(BuildConfig.GOOGLE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient().build())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return googleWebServices.create(ApiInterface.class);
    }

    /**
     * @return object of OkHttpClient.Builder
     */
    private static OkHttpClient.Builder httpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(getLoggingInterceptor());
        return httpClient;
    }

    /**
     * Method to get object of HttpLoggingInterceptor
     *
     * @return object of HttpLoggingInterceptor
     */
    private static HttpLoggingInterceptor getLoggingInterceptor() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        //logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return logging;
    }
}
