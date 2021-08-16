package com.kolis.test_catalog_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kolis.test_catalog_app.data.dress.DressRepositoryType

class HomeViewModelFactory(
    private val repository: DressRepositoryType
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}