package com.kolis.test_catalog_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.kolis.test_catalog_app.data.dress.DressRepository
import com.kolis.test_catalog_app.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val adapter = DressListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.controller = Navigation.findNavController(view)
        val db = DressRepository(requireContext())
        db.allDressesLD.observe(viewLifecycleOwner, {
            adapter.setModelsList(it)
            adapter.filter.filter(binding.dressSearch.query)
        })
        view.recipesRecycleView.adapter = adapter
        binding.dressSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}