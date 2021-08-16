package com.kolis.test_catalog_app.data.dress.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.kolis.test_catalog_app.data.DressModel
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class FirebaseDressPaginationSource(private val repository: DressRepositoryType) : PagingSource<QuerySnapshot, DressModel>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, DressModel> {

        val async = CoroutineScope(Dispatchers.IO).async {
            try {
                // Step 1
                val currentPage = params.key ?: Tasks.await(
                    repository.loadDresses(10L, null)
                )

                // Step 2
                val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

                // Step 3
                val nextPage = Tasks.await(
                    repository.loadDresses(10L, lastDocumentSnapshot)
                )

                // Step 4
                val modelList = mutableListOf<DressModel>()
                for (document in currentPage) {
                    modelList.add(DressModel.fromFirebaseDocument(document as QueryDocumentSnapshot))
                }
                LoadResult.Page(
                    data = modelList,
                    prevKey = null,
                    nextKey = if (nextPage.documents.isEmpty()) null else nextPage
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

        return async.await()
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, DressModel>): QuerySnapshot? {
        return null
    }
}
