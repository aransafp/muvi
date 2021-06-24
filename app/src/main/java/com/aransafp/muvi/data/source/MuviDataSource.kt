package com.aransafp.muvi.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.vo.Resource

interface MuviDataSource {

    fun getListMovie(): LiveData<Resource<PagedList<FilmEntity>>>

    fun getListTvShow(): LiveData<Resource<PagedList<FilmEntity>>>

    fun getDetailsMovie(id: Int): LiveData<Resource<DetailEntity>>

    fun getDetailsTvShow(id: Int): LiveData<Resource<DetailEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<FilmEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<FilmEntity>>

    fun checkFilmIsFavorite(id: Int): LiveData<Boolean>

    suspend fun inserFavoriteFilm(id: Int)

    suspend fun deleteFavoriteFilm(id: Int)

}