package com.kolis.test_catalog_app.data.dress

import androidx.lifecycle.LiveData
import com.kolis.test_catalog_app.data.DressModel
import com.kolis.test_catalog_app.ui.login.OnPasswordCheckObserver
import java.util.*

interface DressRepositoryType {
    fun addDress(model: DressModel?)

    val allDressesLD: LiveData<List<DressModel>>

    //Cart data
    fun addDressToCart(dress: DressInCartModel)
    fun removeFromCart(dress: DressInCartModel)
    fun isAnyDressInCart(): LiveData<Boolean>

    /**
     * Returns amount of positions in cart (ex. 3 same T-shirts will return 1)
     */
    fun countDressInCart(): LiveData<Int>
    fun dressesInCart(): LiveData<List<DressInCartModel>>
    fun totalCartPrice(): LiveData<Float>

    //TODO Remove later
    fun addProfile(login: String?, password: String?)
    fun isPasswordRight(login: String?, password: String?, observer: OnPasswordCheckObserver)
}