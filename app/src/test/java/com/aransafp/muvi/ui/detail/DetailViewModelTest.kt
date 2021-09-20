package com.aransafp.muvi.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.utils.DataDummy
import com.aransafp.muvi.utils.TestCoroutineRule
import com.aransafp.muvi.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private var dummyDetailMovie = DataDummy.generateDetailMovie()
    private var dummyDetailTvShow = DataDummy.generateDetailTvSeries()
    private var dummyMovieEntity = DataDummy.generateListMovie()[0]

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var muviRepository: MuviRepository

    @Mock
    private lateinit var observer: Observer<Resource<DetailEntity>>

    @Mock
    private lateinit var favoriteObserver: Observer<Boolean>

    @Mock
    private lateinit var pagedList: PagedList<FilmEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(muviRepository)
    }

    @Test
    fun getDetailMovie() {
        testCoroutineRule.runBlockingTest {
            val dummyMovieId = 337404
            val resource = Resource.success(dummyDetailMovie)
            val detail = MutableLiveData<Resource<DetailEntity>>()
            detail.value = resource
            `when`(muviRepository.getDetailsMovie(dummyMovieId)).thenReturn(detail)

            val result = viewModel.getDetailsMovie(dummyMovieId)
            verify(muviRepository).getDetailsMovie(dummyMovieId)

            assertNotNull(result)
            viewModel.getDetailsMovie(dummyMovieId).observeForever(observer)
            verify(observer).onChanged(resource)

        }
    }

    @Test
    fun getDetailTvSeries() {
        testCoroutineRule.runBlockingTest {
            val dummyTvId = 63174
            val resource = Resource.success(dummyDetailTvShow)
            val detail = MutableLiveData<Resource<DetailEntity>>()
            detail.value = resource
            `when`(muviRepository.getDetailsTvShow(dummyTvId)).thenReturn(detail)

            val result = viewModel.getDetailsTvShow(dummyTvId)
            verify(muviRepository).getDetailsTvShow(dummyTvId)

            assertNotNull(result)
            viewModel.getDetailsTvShow(dummyTvId).observeForever(observer)
            verify(observer).onChanged(resource)

        }
    }

    @Test
    fun isFilmFavorite() {
        val dummyMovieId = 337404
        val isFavorite = MutableLiveData<Boolean>()
        isFavorite.value = false
        `when`(muviRepository.checkFilmIsFavorite(dummyMovieId)).thenReturn(isFavorite)

        val result = viewModel.isFilmFavorite(dummyMovieId)
        verify(muviRepository).checkFilmIsFavorite(dummyMovieId)

        assertNotNull(result.value)
        assertEquals(isFavorite.value, result.value)

        viewModel.isFilmFavorite(dummyMovieId).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(isFavorite.value)
    }

    @Test
    fun addToFavorite() {
        testCoroutineRule.runBlockingTest {
            val id = 337404
            `when`(muviRepository.insertFavoriteFilm(id)).thenReturn(Unit)
            viewModel.addToFavorite(dummyMovieEntity)
            verify(muviRepository).insertFavoriteFilm(id)
        }
    }

    @Test
    fun deleteFromFavorite() {
        testCoroutineRule.runBlockingTest {
            val id = 337404
            `when`(muviRepository.deleteFavoriteFilm(id)).thenReturn(Unit)
            viewModel.deleteFromFavorite(dummyMovieEntity)
            verify(muviRepository).deleteFavoriteFilm(id)
        }
    }
}