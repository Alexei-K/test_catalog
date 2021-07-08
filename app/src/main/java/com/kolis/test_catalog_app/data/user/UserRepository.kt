package com.kolis.test_catalog_app.data.user

import androidx.lifecycle.MutableLiveData
import com.kolis.test_catalog_app.util.RequestResult

interface UserRepository {
    fun isPasswordCorrect (login:String, password:String, result: MutableLiveData<RequestResult>)
}