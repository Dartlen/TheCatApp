package bydartlen.thecatapp.di.module

import bydartlen.thecatapp.data.network.CatEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCatEndPoint(): CatEndPoint {
        return CatEndPoint.getService()
    }
}