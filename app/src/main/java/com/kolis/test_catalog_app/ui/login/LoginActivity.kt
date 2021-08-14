package com.kolis.test_catalog_app.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kolis.test_catalog_app.Constants
import com.kolis.test_catalog_app.MainActivity
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.data.dress.DressRepository
import com.kolis.test_catalog_app.data.user.UserModel
import com.kolis.test_catalog_app.util.PrefConstants
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerLoginListener()
        checkRegistration()
        setContentView(R.layout.activity_register)
        val pager = findViewById<ViewPager>(R.id.register_viewPager)
        val tabs = findViewById<TabLayout>(R.id.register_tabs)
        setUpAdapter(pager)
        tabs.setupWithViewPager(pager)
    }

    private fun registerLoginListener() {
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            if (auth.currentUser?.isAnonymous == false) {
                FirebaseFirestore.getInstance().collection(Constants.USERS_PATH).document(auth.uid!!).set(
                    UserModel(
                        auth.uid!!,
                        auth.currentUser?.displayName!!,
                        Constants.NEW_USER_IS_ADMIN,
                        auth.currentUser?.email,
                        auth.currentUser?.phoneNumber,
                        auth.currentUser?.photoUrl?.toString(),
                    )
                ).addOnFailureListener { exception: Exception? -> Log.d(DressRepository.TAG, "User upload to firebase failed: ${exception?.message} ") }
            }
        }
    }

    private fun checkRegistration() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        if (pref.getBoolean(PrefConstants.IS_LOGGED_PREF, false) || !Constants.IS_LOGIN_REQUIRED) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setUpAdapter(pager: ViewPager) {
        val adapter = InfoTabsAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT)
        val firstFragment = StartInfoFragment()
        setFragmentBundle(
            firstFragment, R.drawable.registration_1,
            getString(R.string.welcome_to_fluxstore), getString(R.string.register_screen_text)
        )
        adapter.addFragment(0, firstFragment)
        val secondFragment = StartInfoFragment()
        setFragmentBundle(
            secondFragment, R.drawable.registration_2,
            getString(R.string.second_register_screen_title), getString(R.string.register_screen_text)
        )
        adapter.addFragment(1, secondFragment)
        val thirdFragment = LoginFragment()
        adapter.addFragment(2, thirdFragment)
        pager.adapter = adapter
    }

    private fun setFragmentBundle(fragment: StartInfoFragment, imgId: Int, title: String, text: String) {
        val bundle = Bundle()
        bundle.putInt(StartInfoFragment.INFO_IMAGE, imgId)
        bundle.putString(StartInfoFragment.INFO_TITLE, title)
        bundle.putString(StartInfoFragment.INFO_TEXT, text)
        fragment.arguments = bundle
    }
}