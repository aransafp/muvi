package com.aransafp.muvi.ui.home.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.vo.Resource

class MoviesViewModel(private val muviRepository: MuviRepository) : ViewModel() {

//    private var _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    fun getMovies(): LiveData<List<FilmEntity>> {
//        val result = MutableLiveData<List<FilmEntity>>()
//        viewModelScope.launch {
//            _isLoading.postValue(true)
//            try {
//                val movies = muviRepository.getMovies()
//                result.postValue(movies)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            _isLoading.postValue(false)
//        }
//
//        return result
//    }
    fun getMovis(): LiveData<Resource<PagedList<FilmEntity>>> = muviRepository.getListMovie()

}