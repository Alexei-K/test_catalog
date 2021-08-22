package com.kolis.test_catalog_app.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kolis.test_catalog_app.App
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import com.kolis.test_catalog_app.databinding.FragmentCartBinding
import com.kolis.test_catalog_app.util.toDollars
import javax.inject.Inject

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var repository: DressRepositoryType

    @Inject
    lateinit var adapter: CartListAdapter

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(repository)
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCartBinding.inflate(inflater)
        setupView()
        return binding.root
    }

    private fun setupView() {

        cartViewModel.getAllCartItems().observe(viewLifecycleOwner) {
            adapter.setModelsList(it)
        }

        cartViewModel.getTotalCartPrice().observe(viewLifecycleOwner) { totalPrice ->
            binding.totalPrice.text = resources.getString(R.string.total_price, totalPrice.toDollars())
        }

        binding.cartRecycler.adapter = adapter
    }
}