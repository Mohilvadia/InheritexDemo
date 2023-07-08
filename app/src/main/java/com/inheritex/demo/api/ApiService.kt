package com.inheritex.demo.api

import com.inheritex.demo.data.NewsDataResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/// [ApiService] This interface is used for API service
interface ApiService {
    @GET("top-headlines")
    fun getNewsData(@Query("sources") source : String, @Query("apiKey") apiKey:String) : Observable<NewsDataResponse>
}