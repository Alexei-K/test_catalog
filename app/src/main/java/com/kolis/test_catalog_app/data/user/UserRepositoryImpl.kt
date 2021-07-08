package com.kolis.test_catalog_app.data.user

import androidx.lifecycle.MutableLiveData
import com.kolis.test_catalog_app.util.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserRepositoryImpl : UserRepository {
    override fun isPasswordCorrect(login: String, password: String): MutableLiveData<RequestResult> {
        val result = MutableLiveData<RequestResult>()
        GlobalScope.launch(Dispatchers.IO) {
            //simulation of IO delay
            delay(1000)
            if (password.length > 5) {
                result.postValue(RequestResult(true))
            } else {
                result.postValue(RequestResult(false, "Random mistake. Password is shorter than 5"))
            }
        }

        return result
    }
}