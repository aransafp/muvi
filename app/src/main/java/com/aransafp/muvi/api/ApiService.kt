package com.aransafp.muvi.api

import com.aransafp.muvi.data.source.remote.response.DetailResponse
import com.aransafp.muvi.data.source.remote.response.PopularMovieResponse
import com.aransafp.muvi.data.source.remote.response.PopularTvResponse
import com.aransafp.muvi.utils.Const
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/popular?api_key=${Const.API_KEY}")
    suspend fun getPopularMovies(): PopularMovieResponse

    @GET("movie/{movie_id}?api_key=${Const.API_KEY}")
    suspend fun getDetailsMovie(@Path("movie_id") movieId: Int): DetailResponse

    @GET("tv/popular?api_key=${Const.API_KEY}")
    suspend fun getPopularTvShows(): PopularTvResponse

    @GET("tv/{tv_id}?api_key=${Const.API_KEY}")
    suspend fun getDetailsTvShow(@Path("tv_id") tvId: Int): DetailResponse

}