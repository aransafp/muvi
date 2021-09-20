package com.aransafp.muvi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val muviRepository: MuviRepository) : ViewModel() {

    fun getDetailsMovie(id: Int) = muviRepository.getDetailsMovie(id)

    fun getDetailsTvShow(id: Int) = muviRepository.getDetailsTvShow(id)

    fun isFilmFavorite(id: Int): LiveData<Boolean> = muviRepository.checkFilmIsFavorite(id)

    fun addToFavorite(film: FilmEntity) {
        viewModelScope.launch {
            muviRepository.insertFavoriteFilm(film.id)
        }
    }

    fun deleteFromFavorite(film: FilmEntity) {
        viewModelScope.launch {
            muviRepository.deleteFavoriteFilm(film.id)
        }
    }

}