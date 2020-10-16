package com.kolis.test_catalog_app.ui.home.dressWatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kolis.test_catalog_app.R

class WatchDressFragment : Fragment() {
    private lateinit var homeViewModel: WatchDressViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(WatchDressViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_watch_dress, container, false)

        return root
    }
}