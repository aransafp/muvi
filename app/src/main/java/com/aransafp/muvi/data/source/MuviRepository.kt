package com.aransafp.muvi.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.aransafp.muvi.data.source.local.LocalDataSource
import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.data.source.remote.ApiResponse
import com.aransafp.muvi.data.source.remote.RemoteDataSource
import com.aransafp.muvi.data.source.remote.response.DetailResponse
import com.aransafp.muvi.data.source.remote.response.PopularMovieResponse
import com.aransafp.muvi.data.source.remote.response.PopularTvResponse
import com.aransafp.muvi.utils.AppExecutors
import com.aransafp.muvi.utils.TMDBConst
import com.aransafp.muvi.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MuviRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MuviDataSource {

    companion object {
        @Volatile
        private var instance: MuviRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MuviRepository =
            instance ?: synchronized(this) {
                instance ?: MuviRepository(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }

    override fun getListMovie(): LiveData<Resource<PagedList<FilmEntity>>> {
        return object :
            NetworkBoundResource<PagedList<FilmEntity>, PopularMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<FilmEntity>> =
                localDataSource.getMovies().toLiveData(4)

            override fun shouldFetch(data: PagedList<FilmEntity>?): Boolean = data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<PopularMovieResponse>> {
                val result = MutableLiveData<ApiResponse<PopularMovieResponse>>()
                CoroutineScope(Dispatchers.Main).launch {
                    remoteDataSource.getListMovie().observeForever { movieResponses ->
                        result.postValue(movieResponses)
                    }
                }
                return result
            }

            override fun saveCallResult(data: PopularMovieResponse) {
                CoroutineScope(Dispatchers.Main).launch {
                    for (response in data.results) {
                        val movie = FilmEntity(
                            id = response.id,
                            title = response.title,
                            posterPath = response.posterPath,
                            releaseDate = response.releaseDate,
                            voteAverage = response.voteAverage,
                            filmType = TMDBConst.TYPE_MOVIE,
                        )
                        launch(Dispatchers.IO) { localDataSource.insertFilm(movie) }
                    }
                }
            }

        }.asLiveData()
    }

    override fun getListTvShow(): LiveData<Resource<PagedList<FilmEntity>>> {
        return object :
            NetworkBoundResource<PagedList<FilmEntity>, PopularTvResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<FilmEntity>> =
                localDataSource.getTvShows().toLiveData(4)

            override fun shouldFetch(data: PagedList<FilmEntity>?): Boolean = data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<PopularTvResponse>> {
                val result = MutableLiveData<ApiResponse<PopularTvResponse>>()
                CoroutineScope(Dispatchers.Main).launch {
                    remoteDataSource.getListTvShow().observeForever { tvResponses ->
                        result.postValue(tvResponses)
                    }
                }
                return result
            }

            override fun saveCallResult(data: PopularTvResponse) {
                CoroutineScope(Dispatchers.Main).launch {
                    for (response in data.results) {
                        val tvShow = FilmEntity(
                            id = response.id,
                            title = response.name,
                            posterPath = response.posterPath,
                            releaseDate = response.firstAirDate,
                            voteAverage = response.voteAverage,
                            filmType = TMDBConst.TYPE_TV_SHOW,
                        )
                        launch(Dispatchers.IO) { localDataSource.insertFilm(tvShow) }
                    }
                }
            }

        }.asLiveData()
    }

    override fun getDetailsMovie(id: Int): LiveData<Resource<DetailEntity>> {
        return object : NetworkBoundResource<DetailEntity, DetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailEntity> = localDataSource.getDetailsMovie(id)

            override fun shouldFetch(data: DetailEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<DetailResponse>> {
                val result = MutableLiveData<ApiResponse<DetailResponse>>()
                CoroutineScope(Dispatchers.Main).launch {
                    remoteDataSource.getDetailsMovie(id).observeForever { movie ->
                        result.postValue(movie)
                    }
                }
                return result
            }

            override fun saveCallResult(data: DetailResponse) {
                val genreItems = data.genres.map { genreItem -> genreItem.name }
                val genres = genreItems.joinToString(", ")

                CoroutineScope(Dispatchers.Main).launch {
                    val detail = DetailEntity(
                        id = data.id,
                        title = data.title,
                        posterPath = data.posterPath,
                        overview = data.overview,
                        releaseDate = data.releaseDate,
                        voteAverage = data.voteAverage,
                        genres = genres,
                        filmType = TMDBConst.TYPE_MOVIE
                    )
                    launch(Dispatchers.IO) { localDataSource.insertDetailsFilm(detail) }

                }
            }

        }.asLiveData()
    }

    override fun getDetailsTvShow(id: Int): LiveData<Resource<DetailEntity>> {
        return object : NetworkBoundResource<DetailEntity, DetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailEntity> = localDataSource.getDetailsTvShow(id)

            override fun shouldFetch(data: DetailEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<DetailResponse>> {
                val result = MutableLiveData<ApiResponse<DetailResponse>>()
                CoroutineScope(Dispatchers.Main).launch {
                    remoteDataSource.getDetailsTvShow(id).observeForever { tvShow ->
                        result.postValue(tvShow)
                    }
                }
                return result
            }

            override fun saveCallResult(data: DetailResponse) {
                val genreItems = data.genres.map { genreItem -> genreItem.name }
                val genres = genreItems.joinToString(", ")

                CoroutineScope(Dispatchers.Main).launch {
                    val detail = DetailEntity(
                        id = data.id,
                        title = data.title,
                        posterPath = data.posterPath,
                        overview = data.overview,
                        releaseDate = data.releaseDate,
                        voteAverage = data.voteAverage,
                        genres = genres,
                        filmType = TMDBConst.TYPE_TV_SHOW
                    )
                    launch(Dispatchers.IO) { localDataSource.insertDetailsFilm(detail) }
                }
            }

        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<FilmEntity>> =
        localDataSource.getFavoriteMovies().toLiveData(4)

    override fun getFavoriteTvShows(): LiveData<PagedList<FilmEntity>> =
        localDataSource.getFavoriteTvShows().toLiveData(4)

    override fun checkFilmIsFavorite(id: Int): LiveData<Boolean> =
        localDataSource.checkMovieFavorite(id)

    override suspend fun inserFavoriteFilm(id: Int) {
        localDataSource.addFavoriteFilm(id)
    }

    override suspend fun deleteFavoriteFilm(id: Int) {
        localDataSource.deleteFavoriteFilm(id)
    }
}