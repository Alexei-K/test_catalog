package com.kolis.test_catalog_app.data

import android.os.Parcelable
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.kolis.test_catalog_app.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DressModel(
    val id: Int,
    val name: String,
    val oldPrice: Float,
    val newPrice: Float,
    var isLiked: Boolean = false,
    val overallRating: Long,
    val numberOfVotes: Long,
    val timeTill: Long = 0L
) : Parcelable {

    companion object {
        val sampleList = arrayListOf(
            DressModel(0, "Scaridian dress", 100.00f, 50.00f, false, 80, 83, 1602798596177),
            DressModel(1, "Wool dress", 200.00f, 180.00f, false, 100, 12, 1602885996177),
            DressModel(2, "Cream cotton dress", 150.00f, 100.00f, true, 120, 28, 1602962396177),
            DressModel(3, "Black dress", 120.00f, 120.00f, true, 2, 1),
            DressModel(4, "Scaridian dress", 120.00f, 50.00f, false, 40, 24),
            DressModel(5, "Black dress", 1000.00f, 250.00f, true, 45, 12),
            DressModel(6, "Scaridian dress", 20.00f, 20.00f, false, 54, 40, 1609796996177),
            DressModel(7, "Wool dress", 160.00f, 16.00f, true, 80, 58),
            DressModel(8, "Scaridian dress", 120.00f, 120.00f, false, 100, 26, 1607890006177),
            DressModel(9, "Wool dress", 120.00f, 50.00f, true, 120, 40),
            DressModel(10, "Scaridian dress", 160.00f, 50.00f, true, 80, 16),
            DressModel(1, "Black dress", 200.00f, 50.00f, false, 40, 40)
        )

        fun fromFirebaseDocument(document: QueryDocumentSnapshot): DressModel {

            return DressModel(
                (document["id"] as Long).toInt(),
                document["name"] as String,
                (document["oldPrice"] as Double).toFloat(),
                (document["newPrice"] as Double).toFloat(),
                document["isLiked"] as Boolean,
                document["overallRating"] as Long,
                document["numberOfVotes"] as Long,
                document["timeTill"] as Long
            )
        }
    }

    fun getAvgMark(): Float = overallRating.toFloat() / numberOfVotes.toFloat()
    fun getTestImageResource(): Int {
        return when (id % 4) {
            0 -> R.drawable.image_test
            1 -> R.drawable.image_test2
            2 -> R.drawable.image_test3
            3 -> R.drawable.image_test4
            else -> R.drawable.image_test4
        }

    }

    fun toMap(): Map<String, Any> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "oldPrice" to oldPrice,
            "newPrice" to newPrice,
            "isLiked" to isLiked,
            "overallRating" to overallRating,
            "numberOfVotes" to numberOfVotes,
            "timeTill" to timeTill
        )
    }
}