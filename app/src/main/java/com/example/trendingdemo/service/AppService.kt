package com.example.trendingdemo.service

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class AppService {

    companion object {

        const val END_POINT = "https://api.giphy.com/"
        const val API_Key = "EEjeWKnay8eNwJ091mC2ffGuQe96tdBN"

//        fun getAPIUri(): URI {
//            return URI.create(endPoint)
//        }
    }

}

interface ApiInterface {
    companion object {
        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build()
                )
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .setLenient()
                            .create()
                    )
                )
                .baseUrl(AppService.END_POINT)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

    // TODO: API EndpointsCall<JsonObject>

    @GET("/v1/gifs/trending")
    fun getTrending(
        @Query("api_key") apiKey: String
    ) : Call<JsonObject>

}