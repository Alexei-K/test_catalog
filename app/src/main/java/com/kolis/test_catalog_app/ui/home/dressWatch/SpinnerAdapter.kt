package com.kolis.test_catalog_app.ui.home.dressWatch

import android.content.Context
import android.widget.ArrayAdapter

class SpinnerAdapter(
    context: Context?, textViewResourceId: Int,
    private val values: List<String>
) : ArrayAdapter<String>(context!!, textViewResourceId, values) {
    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): String {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}