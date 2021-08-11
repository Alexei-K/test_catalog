package com.kolis.test_catalog_app.data.dress.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolis.test_catalog_app.data.DressModel
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
    val photoUrl: String,
    val amount: Int,
    val size: DressSize,
    val color: String
) {
    companion object {
        const val TABLE_NAME = "DressInCard"

        fun fromModel(_dressModel: DressInCartModel): DressInCartEntity {
            return DressInCartEntity(
                _dressModel.id,
                _dressModel.dressModel.id,
                _dressModel.dressModel.name,
                _dressModel.dressModel.oldPrice,
                _dressModel.dressModel.newPrice,
                _dressModel.dressModel.isLiked,
                _dressModel.dressModel.overallRating,
                _dressModel.dressModel.numberOfVotes,
                _dressModel.dressModel.timeTill,
                _dressModel.dressModel.description,
                _dressModel.dressModel.productCode,
                _dressModel.dressModel.category,
                _dressModel.dressModel.material,
                _dressModel.dressModel.country,
                _dressModel.dressModel.photoUrl,
                _dressModel.quantity,
                _dressModel.size,
                _dressModel.color
            )
        }
    }

    fun toModel(): DressInCartModel {
        return DressInCartModel(
            buyItemId,
            DressModel(
                dressId, name, oldPrice, newPrice, isLiked, overallRating, numberOfVotes, timeTill,
                //TODO implement saving lists to Room DB
                mutableListOf(), mutableListOf(),
                description, productCode, category, material, country
            ),
            amount,
            size,
            color

        )
    }
}