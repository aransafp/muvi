package com.aransafp.muvi.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.aransafp.muvi.data.source.local.LocalDataSource
import com.aransafp.muvi.data.source.local.entity.DetailEntity
import com.aransafp.muvi.data.source.local.entity.FilmEntity
import com.aransafp.muvi.data.source.remote.RemoteDataSource
import com.aransafp.muvi.utils.*
import com.aransafp.muvi.vo.Resource
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class MuviRepositoryTest {

    private lateinit var muviRepository: FakeMuviRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    @Before
    fun setUp() {
        muviRepository = FakeMuviRepository(remote, local, appExecutors)
    }

    @Test
    fun getListMovie() {
        testCoroutineRule.runBlockingTest {
            val dummyMovies = DataDummy.generateListMovie()
            val dataSourceFactory =
                mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FilmEntity>
            `when`(local.getMovies()).thenReturn(dataSourceFactory)
            muviRepository.getListMovie()

            val result = Resource.success(PagedListUtil.mockPagedList(dummyMovies))
            verify(local).getMovies()
            assertNotNull(result)
        }
    }

    @Test
    fun getListTvShow() {
        testCoroutineRule.runBlockingTest {
            val dummyTvShows = DataDummy.generateListTvSeries()
            val dataSourceFactory =
                mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FilmEntity>
            `when`(local.getTvShows()).thenReturn(dataSourceFactory)
            muviRepository.getListTvShow()

            val result = Resource.success(PagedListUtil.mockPagedList(dummyTvShows))
            verify(local).getTvShows()
            assertNotNull(result.data)
        }
    }

    @Test
    fun getDetailsMovie() {
        testCoroutineRule.runBlockingTest {
            val id = 337404
            val response = DataDummy.generateDetailMovie()
            val detail = MutableLiveData<DetailEntity>()
            detail.value = response
            `when`(local.getDetailsMovie(id)).thenReturn(detail)

            val result = muviRepository.getDetailsMovie(id)
            verify(local).getDetailsMovie(id)
            assertNotNull(result)

        }
    }

    @Test
    fun getDetailsTvShow() {
        testCoroutineRule.runBlockingTest {
            val id = 337404
            val response = DataDummy.generateDetailTvSeries()
            val detail = MutableLiveData<DetailEntity>()
            detail.value = response
            `when`(local.getDetailsTvShow(id)).thenReturn(detail)

            val result = muviRepository.getDetailsTvShow(id)
            verify(local).getDetailsTvShow(id)
            assertNotNull(result)
        }
    }

    @Test
    fun getFavoriteMovies() {
        val response = DataDummy.generateListMovie()
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FilmEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        muviRepository.getFavoriteMovies()

        val result = PagedListUtil.mockPagedList(response)
        verify(local).getFavoriteMovies()

        assertNotNull(result)
        assertEquals(response.size, result.size)
    }

    @Test
    fun getFavoriteTvShows() {
        val response = DataDummy.generateListTvSeries()
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FilmEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        muviRepository.getFavoriteTvShows()

        val result = PagedListUtil.mockPagedList(response)
        verify(local).getFavoriteTvShows()

        assertNotNull(result)
        assertEquals(response.size, result.size)
    }

    @Test
    fun checkFilmIsFavorite() {
        val dummyIsFavorite = false
        val movieId = 337404
        val isFavorite = MutableLiveData<Boolean>()
        isFavorite.value = false
        `when`(local.checkMovieFavorite(movieId)).thenReturn(isFavorite)

        val result = LiveDataTestUtil.getValue(muviRepository.checkFilmIsFavorite(movieId))
        verify(local).checkMovieFavorite(movieId)
        assertEquals(dummyIsFavorite, result)
    }

    @Test
    fun insertFavoriteFilm() {
        testCoroutineRule.runBlockingTest {
            val id = 337404
            `when`(local.addFavoriteFilm(id)).thenReturn(Unit)
            muviRepository.inserFavoriteFilm(id)

            verify(local).addFavoriteFilm(id)
        }
    }

    @Test
    fun deleteFavoriteFilm() {
        testCoroutineRule.runBlockingTest {
            val id = 337404
            `when`(local.deleteFavoriteFilm(id)).thenReturn(Unit)
            muviRepository.deleteFavoriteFilm(id)

            verify(local).deleteFavoriteFilm(id)
        }
    }
}