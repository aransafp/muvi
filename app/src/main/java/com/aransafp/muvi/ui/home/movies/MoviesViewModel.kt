package com.aransafp.muvi.ui.home.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.vo.Resource

class MoviesViewModel(private val muviRepository: MuviRepository) : ViewModel() {


    fun getMovies(): LiveData<Resource<PagedList<FilmEntity>>> = muviRepository.getListMovie()

}