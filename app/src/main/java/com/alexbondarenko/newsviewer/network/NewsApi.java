package com.alexbondarenko.newsviewer.network;

import com.alexbondarenko.newsviewer.pojo.news.News;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("everything?%20&apiKey=4d40bb2dc6454fdd8aa228ce9024b332")
    Call<News> getNews(@Query("q") String request, @Query("sortBy") String sortType, @Query("language")String language);

}


