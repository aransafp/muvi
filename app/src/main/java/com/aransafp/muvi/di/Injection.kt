package com.aransafp.muvi.di

import android.content.Context
import com.aransafp.muvi.data.source.MuviRepository
import com.aransafp.muvi.data.source.local.LocalDataSource
import com.aransafp.muvi.data.source.local.database.MuviDatabase
import com.aransafp.muvi.data.source.remote.RemoteDataSource
import com.aransafp.muvi.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MuviRepository {

        val database = MuviDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.muviDao())
        val appExecutors = AppExecutors()

        return MuviRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

}