package com.aransafp.muvi.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.di.Injection
import com.aransafp.muvi.ui.detail.DetailViewModel
import com.aransafp.muvi.ui.favorite.FavoriteViewModel
import com.aransafp.muvi.ui.home.movies.MoviesViewModel
import com.aransafp.muvi.ui.home.series.SeriesViewModel

class ViewModelFactory(private val muviRepository: MuviRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                return MoviesViewModel(muviRepository) as T
            }
            modelClass.isAssignableFrom(SeriesViewModel::class.java) -> {
                return SeriesViewModel(muviRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(muviRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return FavoriteViewModel(muviRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}