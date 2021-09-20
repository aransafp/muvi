package com.aransafp.muvi.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.aransafp.muvi.data.source.local.database.MuviDao
import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity

class LocalDataSource private constructor(private val muviDao: MuviDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(muviDao: MuviDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(muviDao)
    }

    suspend fun insertFilm(film: FilmEntity) {
        muviDao.insertFilm(film)
    }

    suspend fun insertDetailsFilm(detail: DetailEntity) {
        muviDao.insertDetail(detail)
    }

    fun getMovies(): DataSource.Factory<Int, FilmEntity> {
        return muviDao.getMovies()
    }

    fun getDetailsMovie(movieId: Int): LiveData<DetailEntity> {
        return muviDao.getDetailsMovie(movieId)
    }

    fun getTvShows(): DataSource.Factory<Int, FilmEntity> {
        return muviDao.getTvShows()
    }

    fun getDetailsTvShow(tvShowId: Int): LiveData<DetailEntity> {
        return muviDao.getDetailsTvShow(tvShowId)
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, FilmEntity> {
        return muviDao.getFavoriteMovies()
    }

    fun getFavoriteTvShows(): DataSource.Factory<Int, FilmEntity> {
        return muviDao.getFavoriteTvShows()
    }

    fun checkMovieFavorite(id: Int): LiveData<Boolean> {
        return muviDao.checkFilmIsFavorite(id)
    }

    fun addFavoriteFilm(id: Int) {
        muviDao.addFavorite(id)
    }

    fun deleteFavoriteFilm(id: Int) {
        muviDao.removeFavorite(id)
    }
}