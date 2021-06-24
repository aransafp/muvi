package com.aransafp.muvi.data.source.local.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.utils.Const

@Dao
interface MuviDao {
    @Query("SELECT * FROM films WHERE filmType = '${Const.TYPE_MOVIE}'")
    fun getMovies(): DataSource.Factory<Int, FilmEntity>

    @Query("select * from films where filmType = '${Const.TYPE_TV_SHOW}'")
    fun getTvShows(): DataSource.Factory<Int, FilmEntity>

    @Query("select * from details where id = :movieId and filmType = '${Const.TYPE_MOVIE}'")
    fun getDetailsMovie(movieId: Int): LiveData<DetailEntity>

    @Query("select * from details where id = :tvShowId and filmType = '${Const.TYPE_TV_SHOW}'")
    fun getDetailsTvShow(tvShowId: Int): LiveData<DetailEntity>

    @Query("select * from films where filmType= '${Const.TYPE_MOVIE}' and isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, FilmEntity>

    @Query("select * from films where filmType= '${Const.TYPE_TV_SHOW}' and isFavorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, FilmEntity>

    @Insert
    fun insertFilm(film: FilmEntity)

    @Insert
    fun insertDetail(detailFilm: DetailEntity)

    @Query("update films set isFavorite = 1 where id = :id")
    fun addFavorite(id: Int)

    @Query("update films set isFavorite = 0 where id = :id")
    fun removeFavorite(id: Int)

    @Query("select isFavorite == 1 from films where id = :id")
    fun checkFilmIsFavorite(id: Int): LiveData<Boolean>
}