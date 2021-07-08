package com.kolis.test_catalog_app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolis.test_catalog_app.data.RepositoryImpl
import com.kolis.test_catalog_app.data.user.UserRepository
import com.kolis.test_catalog_app.data.user.UserRepositoryImpl
import com.kolis.test_catalog_app.extensions.observeOnce
import com.kolis.test_catalog_app.util.RequestResult

class LoginViewModel : ViewModel() {

    //inject
    private val repository = UserRepositoryImpl()

    fun signIn(login: String, password: String): LiveData<RequestResult> {
        val isPasswordCorrect = MutableLiveData<RequestResult>()
        repository.isPasswordCorrect(login, password, isPasswordCorrect)
        isPasswordCorrect.observeOnce(this, )
        return result
    }

}