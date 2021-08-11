package com.kolis.test_catalog_app.ui.cart

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kolis.test_catalog_app.MainActivity
import com.kolis.test_catalog_app.data.dress.DressRepository
import com.kolis.test_catalog_app.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val repository = DressRepository(MainActivity.appContext!!)
    private val adapter: CartListAdapter = CartListAdapter(repository)

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCartBinding.inflate(inflater)
        setupRecycler()
        return binding.root
    }

    private fun setupRecycler() {
        cartViewModel.getAllCartItems().observe(viewLifecycleOwner) {
            adapter.setModelsList(it)
        }
        binding.cartRecycler.adapter = adapter
    }
}