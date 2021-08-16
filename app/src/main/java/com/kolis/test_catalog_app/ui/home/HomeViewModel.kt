package com.kolis.test_catalog_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.google.firebase.firestore.FirebaseFirestore
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import com.kolis.test_catalog_app.data.dress.pagination.FirebaseDressPaginationSource

class HomeViewModel (val repository: DressRepositoryType): ViewModel() {

    val allDressLiveData = Pager(PagingConfig(20)) {
        FirebaseDressPaginationSource(repository)
    }.liveData.cachedIn(viewModelScope)

}