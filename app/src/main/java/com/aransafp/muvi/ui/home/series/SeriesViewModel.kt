package com.aransafp.muvi.ui.home.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.vo.Resource

class SeriesViewModel(private val muviRepository: MuviRepository) : ViewModel() {


    fun getTvShows(): LiveData<Resource<PagedList<FilmEntity>>> = muviRepository.getListTvShow()
}