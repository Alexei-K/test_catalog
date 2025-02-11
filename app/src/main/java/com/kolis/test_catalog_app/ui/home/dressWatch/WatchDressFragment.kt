package com.kolis.test_catalog_app.ui.home.dressWatch

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kolis.test_catalog_app.App
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.data.DressModel
import com.kolis.test_catalog_app.data.DressSize
import com.kolis.test_catalog_app.data.dress.DressInCartModel
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import com.kolis.test_catalog_app.ui.utils.DefaultLoadingSpinner
import com.kolis.test_catalog_app.util.toDollars
import kotlinx.android.synthetic.main.fragment_watch_dress.*
import javax.inject.Inject

class WatchDressFragment : Fragment() {

    @Inject
    lateinit var repository: DressRepositoryType

    private val viewModel: WatchDressViewModel by viewModels {
        WatchDressViewModelFactory(repository)
    }

    private val args: WatchDressFragmentArgs by navArgs()
    lateinit var dressModel: DressModel

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dressModel = args.model
        return inflater.inflate(R.layout.fragment_watch_dress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
        initListeners()
    }

    private fun fillData() {
        Glide.with(dressImage.context)
            .load(dressModel.photoUrl)
            .placeholder(DefaultLoadingSpinner(dressImage.context))
            .into(dressImage)

        productName.text = dressModel.name
        setUpDiscountPrice()
        numberOfMarks.text = "(" + dressModel.numberOfVotes.toInt() + ")"
        rating.rating = dressModel.getAvgMark()
        description.text = dressModel.description
        productCode.text = getString(R.string.product_code, dressModel.productCode)
        productCategory.text = getString(R.string.category, dressModel.category)
        productMaterial.text = getString(R.string.material, dressModel.material)
        productCountry.text = getString(R.string.country, dressModel.country)
        sizeSpinner.adapter = SpinnerAdapter(
            context, android.R.layout.simple_spinner_item,
            dressModel.sizes.map { it.nameShort }
        )
        colorSpinner.adapter = SpinnerAdapter(
            context, android.R.layout.simple_spinner_item,
            dressModel.colors.map { it.first }
        )

    }

    private fun setUpDiscountPrice() {
        if (dressModel.newPrice >= dressModel.oldPrice) {
            priceActual.text = dressModel.newPrice.toDollars()
            priceOld.visibility = View.INVISIBLE
        } else {
            priceActual.text = dressModel.newPrice.toDollars()
            priceOld.text = dressModel.oldPrice.toDollars()
            priceOld.paintFlags = priceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            priceOld.visibility = View.VISIBLE
        }
    }

    private fun initListeners() {
        closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }

        addToCart.setOnClickListener {
            viewModel.addToCart(
                DressInCartModel(
                    0L,
                    dressModel,
                    (addToCartQuantity.selectedItem as String).toInt(),
                    DressSize.byName(sizeSpinner.selectedItem.toString()),
                    colorSpinner.selectedItem.toString()
                )
            )
        }
    }
}