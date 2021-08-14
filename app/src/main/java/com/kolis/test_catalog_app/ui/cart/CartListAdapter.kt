package com.kolis.test_catalog_app.ui.cart

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.kolis.test_catalog_app.R
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.kolis.test_catalog_app.data.dress.DressInCartModel
import com.kolis.test_catalog_app.data.dress.DressRepository
import com.kolis.test_catalog_app.data.dress.DressRepositoryType
import com.kolis.test_catalog_app.ui.utils.DefaultLoadingSpinner
import com.kolis.test_catalog_app.util.toDollars
import java.util.*

class CartListAdapter(private val dressRepository: DressRepositoryType) : RecyclerView.Adapter<CartListAdapter.CartItemViewHolder>() {
    private var dressList = mutableListOf<DressInCartModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(dressList[position])
    }

    override fun getItemCount(): Int {
        return dressList.size
    }

    fun setModelsList(modelList: List<DressInCartModel>) {
        dressList = modelList.toMutableList()
        notifyDataSetChanged()
    }

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var name: TextView = itemView.findViewById(R.id.name)
        var secondaryInfo: TextView = itemView.findViewById(R.id.secondaryInfo)
        var price: TextView = itemView.findViewById(R.id.price)
        var amount: TextView = itemView.findViewById(R.id.amount)
        var add: ImageView = itemView.findViewById(R.id.add)
        var remove: ImageView = itemView.findViewById(R.id.remove)

        fun bind(model: DressInCartModel) {
            Glide.with(image.context)
                .load(model.dressModel.photoUrl)
                .placeholder(DefaultLoadingSpinner(image.context))
                .into(image)

            name.text = model.dressModel.name
            secondaryInfo.text = itemView.resources.getString(R.string.color_size, model.color, model.size)
            price.text = (model.dressModel.newPrice * model.quantity).toDollars()
            amount.text = model.quantity.toString()

            add.setOnClickListener {
                dressRepository.addDressToCart(model.also {
                    it.quantity++
                })
            }

            remove.setOnClickListener {
                model.quantity--
                if (model.quantity == 0) {
                    dressRepository.removeFromCart(model)
                    Toast.makeText(
                        remove.context,
                        remove.resources.getString(R.string.removed_from_cart, model.dressModel.name.capitalize()),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    dressRepository.addDressToCart(model)
                }
            }
        }
    }
}