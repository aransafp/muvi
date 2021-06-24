package com.aransafp.muvi.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aransafp.muvi.api.ApiConfig
import com.aransafp.muvi.data.source.remote.response.DetailResponse
import com.aransafp.muvi.data.source.remote.response.PopularMovieResponse
import com.aransafp.muvi.data.source.remote.response.PopularTvResponse
import com.aransafp.muvi.utils.IdlingResource

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply {
                    instance = this
                }
            }
    }

    suspend fun getListMovie(): LiveData<ApiResponse<PopularMovieResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<PopularMovieResponse>>()
        val data = ApiConfig.getApiService().getPopularMovies()
        result.postValue(ApiResponse.success(data))
        IdlingResource.decerement()
        return result
    }

    suspend fun getListTvShow(): LiveData<ApiResponse<PopularTvResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<PopularTvResponse>>()
        val data = ApiConfig.getApiService().getPopularTvShows()
        result.postValue(ApiResponse.success(data))
        IdlingResource.decerement()
        return result
    }

    suspend fun getDetailsMovie(id: Int): LiveData<ApiResponse<DetailResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<DetailResponse>>()
        val data = ApiConfig.getApiService().getDetailsMovie(id)
        result.postValue(ApiResponse.success(data))
        IdlingResource.decerement()
        return result
    }

    suspend fun getDetailsTvShow(id: Int): LiveData<ApiResponse<DetailResponse>> {
        IdlingResource.increment()
        val result = MutableLiveData<ApiResponse<DetailResponse>>()
        val data = ApiConfig.getApiService().getDetailsTvShow(id)
        result.postValue(ApiResponse.success(data))
        IdlingResource.decerement()
        return result
    }

}