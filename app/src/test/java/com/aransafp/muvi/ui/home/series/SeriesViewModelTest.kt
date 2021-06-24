package com.aransafp.muvi.ui.home.series

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.utils.TestCoroutineRule
import com.aransafp.muvi.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SeriesViewModelTest {

    private lateinit var viewModel: SeriesViewModel

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
        viewModel = SeriesViewModel(muviRepository)
    }

    @Test
    fun getTvShows() {
        testCoroutineRule.runBlockingTest {

            val dummyTvShows = Resource.success(pagedList)
            `when`(dummyTvShows.data?.size).thenReturn(5)
            val films = MutableLiveData<Resource<PagedList<FilmEntity>>>()
            films.value = dummyTvShows

            `when`(muviRepository.getListTvShow()).thenReturn(films)
            val filmEntites = viewModel.getTvShows().value?.data
            verify(muviRepository).getListTvShow()
            assertNotNull(filmEntites)
            TestCase.assertEquals(5, filmEntites?.size)

            viewModel.getTvShows().observeForever(observer)
            verify(observer).onChanged(dummyTvShows)

        }

    }
}