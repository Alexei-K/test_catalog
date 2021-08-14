package com.kolis.test_catalog_app.ui.home

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.kolis.test_catalog_app.ui.home.DressListAdapter.DressViewHolder
import com.kolis.test_catalog_app.data.DressModel
import androidx.navigation.NavController
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.kolis.test_catalog_app.R
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.kolis.test_catalog_app.ui.utils.DefaultLoadingSpinner
import com.kolis.test_catalog_app.util.toDollars

class DressListAdapter : RecyclerView.Adapter<DressViewHolder>(), Filterable {
    private var dressList = mutableListOf<DressModel>()
    private var filteredDressList = mutableListOf<DressModel>()
    lateinit var controller: NavController
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DressViewHolder {
        return DressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: DressViewHolder, position: Int) {
        holder.bind(filteredDressList[position])
    }

    override fun getItemCount(): Int {
        return filteredDressList.size
    }

    fun setModelsList(modelList: List<DressModel>) {
        dressList = modelList.toMutableList()
        notifyDataSetChanged()
    }

    inner class DressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pictureIV: ImageView = itemView.findViewById(R.id.product_photo)
        var isLikedIV: ImageView = itemView.findViewById(R.id.likePhoto)
        var productNameTV: TextView = itemView.findViewById(R.id.productName)
        var newPriceTV: TextView = itemView.findViewById(R.id.priceActual)
        var oldPriceTV: TextView = itemView.findViewById(R.id.priceOld)
        var numberOfVotesTV: TextView = itemView.findViewById(R.id.numberOfMarks)
        var timeRemainingTV: TextView = itemView.findViewById(R.id.timeRemaining)
        var ratingBar: RatingBar = itemView.findViewById(R.id.rating)

        fun bind(model: DressModel) {

            Glide.with(pictureIV.context)
                .load(model.photoUrl)
                .placeholder(DefaultLoadingSpinner(pictureIV.context))
                .addListener(getLoadListener())
                .into(pictureIV)

            isLikedIV.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    if (model.isLiked) R.drawable.like else R.drawable.dislike, null
                )
            )

            isLikedIV.setOnClickListener { v: View? ->
                model.isLiked = !model.isLiked
                isLikedIV.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        if (model.isLiked) R.drawable.like else R.drawable.dislike,
                        null
                    )
                )
            }

            productNameTV.text = model.name

            if (model.newPrice >= model.oldPrice) {
                newPriceTV.text = model.newPrice.toDollars()
                oldPriceTV.visibility = View.INVISIBLE
                timeRemainingTV.visibility = View.GONE
            } else {
                newPriceTV.text = model.newPrice.toDollars()
                oldPriceTV.text = model.oldPrice.toDollars()
                oldPriceTV.paintFlags = oldPriceTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                oldPriceTV.visibility = View.VISIBLE
                if (model.timeTill > System.currentTimeMillis()) {
                    timeRemainingTV.text = getRemainingTimeText(
                        model.timeTill - System.currentTimeMillis(),
                        timeRemainingTV
                    )
                    timeRemainingTV.visibility = View.VISIBLE
                } else {
                    timeRemainingTV.visibility = View.GONE
                }
            }

            val votes = model.numberOfVotes
            numberOfVotesTV.text = "(" + votes.toInt() + ")"

            ratingBar.rating = model.getAvgMark()

            itemView.setOnClickListener { v: View? ->
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationWatchDress(model)
                controller.navigate(action)
            }

        }

        private fun getLoadListener(): RequestListener<Drawable> = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.e("TAG_ ", "Error loading image $e")
                e?.printStackTrace()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d("TAG_ ", "Image loaded successfully $resource")
                return false
            }
        }

    }

    private fun getRemainingTimeText(timeInMillis: Long, v: View): String {
        val days = timeInMillis / (1000 * 60 * 60 * 24)
        val hours = (timeInMillis - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes = (timeInMillis - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
        return if (days > 0) {
            v.resources.getQuantityString(
                R.plurals.time_remaining, days.toInt(),
                days.toInt(), hours.toInt(), minutes.toInt()
            )
        } else {
            //done due to limitations of plurals in English
            v.resources.getString(
                R.string.time_remain_zero_days,
                hours.toInt(), minutes.toInt()
            )
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                constraint ?: return FilterResults().also {
                    it.values = mutableListOf<DressModel>()
                }

                if (constraint.trim().isEmpty()) {
                    return FilterResults().also {
                        it.values = dressList
                    }
                }

                return FilterResults().also { result ->
                    result.values = dressList.filter { dress ->
                        dress.contains(constraint)
                    }.toMutableList()
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDressList = results?.values as MutableList<DressModel>
                notifyDataSetChanged()
            }
        }
    }
}
