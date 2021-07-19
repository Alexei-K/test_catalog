package com.kolis.test_catalog_app.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kolis.test_catalog_app.data.user.UserRepositoryImpl
import com.kolis.test_catalog_app.util.PrefConstants
import com.kolis.test_catalog_app.util.RequestResult

class LoginViewModel : ViewModel() {

    //inject
    private val repository = UserRepositoryImpl()

    fun signIn(login: String, password: String): LiveData<RequestResult> {
        return repository.isPasswordCorrect(login, password)
    }

    fun saveLoginPassword(login: String, password: String, pref: SharedPreferences) {
        pref.edit().putBoolean(PrefConstants.IS_LOGGED_PREF, true).apply()
        pref.edit().putString(PrefConstants.USER_NAME_PREF, login).apply()
        pref.edit().putString(PrefConstants.USER_PASSWORD_PREF, password).apply()
    }

}