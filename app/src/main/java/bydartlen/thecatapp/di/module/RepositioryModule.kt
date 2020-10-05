package bydartlen.thecatapp.di.module

import android.content.Context
import bydartlen.thecatapp.data.CatDatabase
import bydartlen.thecatapp.data.FileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class RepositioryModule {

    @Provides
    fun provideCatDB(@ApplicationContext appContext: Context): CatDatabase = CatDatabase.getInstance(appContext)

    @Provides
    fun provideFileRepository(@ApplicationContext appContext: Context): FileRepository = FileRepository(appContext)

}