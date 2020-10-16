package com.kolis.test_catalog_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.data.DressRepositoryImpl
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    val adapter = DressListAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val db = DressRepositoryImpl()
        db.allDressesLD.observe(viewLifecycleOwner, Observer {
            adapter.setModelsList(it)
        })
        root.recipesRecycleView.adapter = adapter
        return root
    }
}