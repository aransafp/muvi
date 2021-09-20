package com.aransafp.muvi.api

import com.aransafp.muvi.data.source.remote.response.DetailResponse
import com.aransafp.muvi.data.source.remote.response.PopularMovieResponse
import com.aransafp.muvi.data.source.remote.response.PopularTvResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): PopularMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailsMovie(
        @Path("movie_id") movieId: Int
    ): DetailResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(): PopularTvResponse

    @GET("tv/{tv_id}")
    suspend fun getDetailsTvShow(
        @Path("tv_id") tvId: Int
    ): DetailResponse

}