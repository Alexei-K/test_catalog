package com.kolis.test_catalog_app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.kolis.test_catalog_app.App
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import com.kolis.test_catalog_app.databinding.FragmentHomeBinding
import com.kolis.test_catalog_app.extensions.postLastValue
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    @Inject
    lateinit var repository: DressRepositoryType

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(repository)
    }
    private lateinit var adapter: DressPagerAdapter

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

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
        adapter = DressPagerAdapter(Navigation.findNavController(binding.root))
        binding.root.recipesRecycleView.adapter = adapter

        homeViewModel.allDressLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.filterAndSubmitData(it)
            }
        }

        binding.dressSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterQuery = newText
                homeViewModel.allDressLiveData.postLastValue()
                return false
            }
        })
    }
}