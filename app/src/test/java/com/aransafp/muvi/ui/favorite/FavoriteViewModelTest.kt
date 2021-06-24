package com.aransafp.muvi.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
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
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviRepository: MuviRepository

    @Mock
    private lateinit var observer: Observer<PagedList<FilmEntity>>

    @Mock
    private lateinit var pagedList: PagedList<FilmEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(muviRepository)
    }

    @Test
    fun getListFavoriteMovies() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(5)
        val films = MutableLiveData<PagedList<FilmEntity>>()
        films.value = dummyMovies

        `when`(muviRepository.getFavoriteMovies()).thenReturn(films)
        val filmEntities = viewModel.getListFavoriteMovies().value
        verify(muviRepository).getFavoriteMovies()

        TestCase.assertNotNull(filmEntities)
        TestCase.assertEquals(5, filmEntities?.size)

        viewModel.getListFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getListFavoriteTvShows() {
        val dummyTvShows = pagedList
        `when`(dummyTvShows.size).thenReturn(5)
        val films = MutableLiveData<PagedList<FilmEntity>>()
        films.value = dummyTvShows

        `when`(muviRepository.getFavoriteTvShows()).thenReturn(films)
        val filmEntities = viewModel.getListFavoriteTvShows().value
        verify(muviRepository).getFavoriteTvShows()

        TestCase.assertNotNull(filmEntities)
        TestCase.assertEquals(5, filmEntities?.size)

        viewModel.getListFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}