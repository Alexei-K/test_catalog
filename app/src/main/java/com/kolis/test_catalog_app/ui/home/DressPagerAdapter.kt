package com.kolis.test_catalog_app.ui.home

import android.content.res.Resources
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.data.DressModel
import com.kolis.test_catalog_app.databinding.ItemProductBinding
import com.kolis.test_catalog_app.ui.utils.DefaultLoadingSpinner
import com.kolis.test_catalog_app.util.toDollars

class DressPagerAdapter(
    private val navController: NavController
) : PagingDataAdapter<DressModel, DressPagerAdapter.DressViewHolder>(Companion) {

    var filterQuery: CharSequence? = null
        set(value) {
            field = if (value == null || value.trim().isEmpty()) {
                null
            } else {
                value
            }
        }

    companion object : DiffUtil.ItemCallback<DressModel>() {
        override fun areItemsTheSame(oldItem: DressModel, newItem: DressModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DressModel, newItem: DressModel): Boolean {
            return oldItem == newItem
        }
    }

    suspend fun filterAndSubmitData(data: PagingData<DressModel>) {
        filterQuery?.let { filterQuery ->
            submitData(data.filter { it.contains(filterQuery) })
        } ?: kotlin.run { submitData(data) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DressViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val bind = ItemProductBinding.inflate(layoutInflater, parent, false)
        return DressViewHolder(bind)
    }

    override fun onBindViewHolder(holder: DressViewHolder, position: Int) {
        holder.bind(
            getItem(position) ?: return
        )
    }

    private fun getFormattedRemainingTime(timeInMillis: Long, res: Resources): String {
        val days = timeInMillis / (1000 * 60 * 60 * 24)
        val hours = (timeInMillis - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes = (timeInMillis - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
        return if (days > 0) {
            res.getQuantityString(
                R.plurals.time_remaining, days.toInt(),
                days.toInt(), hours.toInt(), minutes.toInt()
            )
        } else {
            //done due to limitations of plurals in English
            res.getString(
                R.string.time_remain_zero_days,
                hours.toInt(), minutes.toInt()
            )
        }
    }

    inner class DressViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: DressModel) {
            Glide.with(binding.root.context)
                .load(model.photoUrl)
                .placeholder(DefaultLoadingSpinner(binding.root.context))
                .into(binding.productPhoto)

            setupLikeButton(model)
            binding.productName.text = model.name
            setupPrice(model)
            binding.numberOfMarks.text = "(" + model.numberOfVotes.toInt() + ")"
            binding.rating.rating = model.getAvgMark()

            itemView.setOnClickListener { v: View? ->
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationWatchDress(model)
                navController.navigate(action)
            }
        }

        private fun setupLikeButton(model: DressModel) {
            binding.likePhoto.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    if (model.isLiked) R.drawable.like else R.drawable.dislike, null
                )
            )

            binding.likePhoto.setOnClickListener { v: View? ->
                model.isLiked = !model.isLiked
                binding.likePhoto.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        if (model.isLiked) R.drawable.like else R.drawable.dislike,
                        null
                    )
                )
            }
        }

        private fun setupPrice(model: DressModel) {
            if (model.newPrice >= model.oldPrice) {
                binding.priceActual.text = model.newPrice.toDollars()
                binding.priceOld.visibility = View.INVISIBLE
                binding.timeRemaining.visibility = View.GONE
            } else {
                binding.priceActual.text = model.newPrice.toDollars()
                binding.priceOld.text = model.oldPrice.toDollars()
                binding.priceOld.paintFlags = binding.priceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.priceOld.visibility = View.VISIBLE
                if (model.timeTill > System.currentTimeMillis()) {
                    binding.timeRemaining.text = getFormattedRemainingTime(
                        model.timeTill - System.currentTimeMillis(),
                        binding.root.resources
                    )
                    binding.timeRemaining.visibility = View.VISIBLE
                } else {
                    binding.timeRemaining.visibility = View.GONE
                }
            }
        }
    }
}
