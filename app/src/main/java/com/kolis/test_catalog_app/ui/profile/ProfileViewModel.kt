package com.kolis.test_catalog_app.ui.profile

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.kolis.test_catalog_app.ui.login.LoginActivity
import com.kolis.test_catalog_app.util.PrefConstants

class ProfileViewModel : ViewModel() {
    fun onLeaveProfile(fragment: ProfileFragment) {
        val pref: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(fragment.requireContext())
        pref.edit().putBoolean(PrefConstants.IS_LOGGED_PREF, false).apply()
        if(!pref.getBoolean(PrefConstants.IS_REMEMBER_ME_PREF, false)){
            pref.edit().remove(PrefConstants.USER_NAME_PREF).apply()
        }
        pref.edit().putString(PrefConstants.USER_PASSWORD_PREF, "").apply()
        fragment.startActivity(Intent(fragment.context, LoginActivity::class.java))
        fragment.requireActivity().finish()
    }
}