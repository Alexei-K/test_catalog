package com.kolis.test_catalog_app.data.dress.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolis.test_catalog_app.data.DressSize
import com.kolis.test_catalog_app.data.dress.DressInCartModel

@Entity(tableName = DressInCartEntity.TABLE_NAME)
data class DressInCartEntity(
    @PrimaryKey(autoGenerate = true) val buyItemId: Long = 0,
    val dressId: Long,
    val name: String,
    val oldPrice: Float,
    val newPrice: Float,
    var isLiked: Boolean = false,
    val overallRating: Long,
    val numberOfVotes: Long,
    val timeTill: Long = 0L,
    val description: String,
    val productCode: String,
    val category: String,
    val material: String,
    val country: String,
    val quantity: Int,
    val size: DressSize,
    val color: String
) {
    companion object {
        const val TABLE_NAME = "DressInCard"

        fun fromModel(dressModel: DressInCartModel): DressInCartEntity {
            return DressInCartEntity(
                0L,
                dressModel.dressModel.id,
                dressModel.dressModel.name,
                dressModel.dressModel.oldPrice,
                dressModel.dressModel.newPrice,
                dressModel.dressModel.isLiked,
                dressModel.dressModel.overallRating,
                dressModel.dressModel.numberOfVotes,
                dressModel.dressModel.timeTill,
                dressModel.dressModel.description,
                dressModel.dressModel.productCode,
                dressModel.dressModel.category,
                dressModel.dressModel.material,
                dressModel.dressModel.country,
                dressModel.quantity,
                dressModel.size,
                dressModel.color
            )
        }
    }
}