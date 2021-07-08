package com.kolis.test_catalog_app.data.user

import androidx.lifecycle.MutableLiveData
import com.kolis.test_catalog_app.util.RequestResult

class UserRepositoryImpl : UserRepository {
    override fun isPasswordCorrect(login: String, password: String, result: MutableLiveData<RequestResult>) {
        if (password.length > 5) {
            result.postValue(RequestResult(true))
        } else {
            result.postValue(RequestResult(false, "Random mistake. Password is shorter than 5"))
        }
    }
}