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
    val dressModel: DressModel,
    val quantity: Int,
    val size: DressSize,
    val color: String
) : Parcelable {

}