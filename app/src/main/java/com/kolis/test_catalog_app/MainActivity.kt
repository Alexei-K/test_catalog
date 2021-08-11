package com.kolis.test_catalog_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kolis.test_catalog_app.data.dress.DressRepository
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import com.kolis.test_catalog_app.ui.login.LoginActivity
import com.kolis.test_catalog_app.util.PrefConstants

class MainActivity : AppCompatActivity() {

    companion object {
        //TODO remove after implementing DAGGER or Koin
        var appContext: Context? = null
    }

    lateinit var navView: BottomNavigationView

    lateinit var dressRepository: DressRepositoryType

    override fun onCreate(savedInstanceState: Bundle?) {
        appContext = applicationContext
        checkIfLoggedIn()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = super.onCreateView(name, context, attrs)
        dressRepository = DressRepository(this)
        setupBadges()
        return view
    }

    private fun checkIfLoggedIn() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        if (!pref.getBoolean(PrefConstants.IS_LOGGED_PREF, false) && Constants.IS_LOGIN_REQUIRED) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    fun setupBadges() {
        dressRepository.countDressInCart().observe(this@MainActivity) { count ->
            val badge = navView.getOrCreateBadge(R.id.navigation_cart)
            badge.isVisible = count > 0
            badge.number = count
        }
    }
}