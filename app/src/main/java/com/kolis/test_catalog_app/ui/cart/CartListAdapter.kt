package com.kolis.test_catalog_app.ui.cart

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.kolis.test_catalog_app.R
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.kolis.test_catalog_app.data.dress.DressInCartModel
import java.util.*

class CartListAdapter : RecyclerView.Adapter<CartListAdapter.CartItemViewHolder>() {
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

        fun bind(model: DressInCartModel) {
            //TODO заглушка. Фото не отправляется на сервер и не получается с сервера.
            image.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    model.dressModel.getTestImageResource(), null
                )
            )

            name.text = model.dressModel.name
            secondaryInfo.text = itemView.resources.getString(R.string.color_size, model.color, model.size)
            //TODO create money util
            price.text = "$ " + String.format(Locale.US, "%.2f", model.dressModel.newPrice)
            amount.text = model.quantity.toString()
        }
    }
}