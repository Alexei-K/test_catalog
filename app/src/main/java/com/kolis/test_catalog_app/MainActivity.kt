package com.kolis.test_catalog_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.firebase.ui.auth.FirebaseUiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kolis.test_catalog_app.data.DressModel
import com.kolis.test_catalog_app.ui.login.LoginActivity
import com.kolis.test_catalog_app.util.PrefConstants

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        checkIfLoggedIn()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    private fun checkIfLoggedIn() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        if (!pref.getBoolean(PrefConstants.IS_LOGGED_PREF, false) && Constants.IS_LOGIN_REQUIRED) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}