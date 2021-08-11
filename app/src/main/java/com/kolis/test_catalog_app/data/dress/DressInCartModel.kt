package com.kolis.test_catalog_app.data.dress

import android.os.Parcelable
import com.kolis.test_catalog_app.data.DressModel
import com.kolis.test_catalog_app.data.DressSize
import kotlinx.android.parcel.Parcelize

/**
 * All info about dress, required to make purchase
 */

@Parcelize
data class DressInCartModel(
    val id: Long = 0L,
    val dressModel: DressModel,
    var quantity: Int,
    val size: DressSize,
    val color: String,
) : Parcelable {

}