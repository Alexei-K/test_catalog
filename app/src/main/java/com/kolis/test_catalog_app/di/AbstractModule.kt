package com.kolis.test_catalog_app.di

import com.kolis.test_catalog_app.data.dress.DressRepository
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import dagger.Binds
import dagger.Module

@Module
abstract class AbstractModule {
    @Binds
    abstract fun provideDressRepository(repository: DressRepository): DressRepositoryType
}