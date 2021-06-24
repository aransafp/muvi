package com.aransafp.muvi.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.FilmEntity

class FavoriteViewModel(private val muviRepository: MuviRepository) : ViewModel() {

    fun getListFavoriteMovies(): LiveData<PagedList<FilmEntity>> =
        muviRepository.getFavoriteMovies()

    fun getListFavoriteTvShows(): LiveData<PagedList<FilmEntity>> =
        muviRepository.getFavoriteTvShows()
}