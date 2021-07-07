package com.kolis.test_catalog_app.ui.login

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    fun signIn(login: String, password: String): Boolean {

        //TODO implement
        return if (login.length < 3) false else true
    }

}