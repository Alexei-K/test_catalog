package com.kolis.test_catalog_app.ui.home.dressWatch

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.kolis.test_catalog_app.R
import kotlinx.android.synthetic.main.fragment_watch_dress.*
import java.util.*

class WatchDressFragment : Fragment() {
    private lateinit var viewModel: WatchDressViewModel
    val args: WatchDressFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(WatchDressViewModel::class.java)
        viewModel.dressModel = args.model

        val root = inflater.inflate(R.layout.fragment_watch_dress, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
        initListeners()
    }

    private fun fillData() {
        dressImage.setImageDrawable(
            ResourcesCompat.getDrawable(dressImage.resources,
                viewModel.dressModel.getTestImageResource(), null)
        )
        productName.text = viewModel.dressModel.name
        if (viewModel.dressModel.newPrice >= viewModel.dressModel.oldPrice) {
            priceActual.text = getMoneyFormated(viewModel.dressModel.newPrice)
            priceOld.setVisibility(View.INVISIBLE)
        } else {
            priceActual.text = getMoneyFormated(viewModel.dressModel.newPrice)
            priceOld.text = getMoneyFormated(viewModel.dressModel.oldPrice)
            priceOld.paintFlags = priceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            priceOld.visibility = View.VISIBLE
        }
        val votes: Long = viewModel.dressModel.numberOfVotes
        numberOfMarks.setText("(" + votes.toInt() + ")")
        rating.rating = viewModel.dressModel.getAvgMark()


    }

    private fun getMoneyFormated(amount:Float): String {
        return "$ " + String.format(
            Locale.US,
            "%.2f",
            amount
        )
    }

    private fun initListeners() {
        closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}