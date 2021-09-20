package com.aransafp.muvi.ui.home.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.utils.TestCoroutineRule
import com.aransafp.muvi.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
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
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var muviRepository: MuviRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<FilmEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<FilmEntity>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(muviRepository)
    }

    @Test
    fun getMovies() {
        testCoroutineRule.runBlockingTest {

            val dummyMovies = Resource.success(pagedList)
            `when`(dummyMovies.data?.size).thenReturn(5)
            val films = MutableLiveData<Resource<PagedList<FilmEntity>>>()
            films.value = dummyMovies

            `when`(muviRepository.getListMovie()).thenReturn(films)
            val filmEntities = viewModel.getMovies().value?.data
            verify(muviRepository).getListMovie()
            assertNotNull(filmEntities)
            assertEquals(5, filmEntities?.size)

            viewModel.getMovies().observeForever(observer)
            verify(observer).onChanged(dummyMovies)

        }
    }
}